package com.vanniuner.transactionTest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vanniuner.transactionTest.dto.OrderDto;
import com.vanniuner.transactionTest.entity.OrderHead;
import com.vanniuner.transactionTest.entity.OrderItem;
import com.vanniuner.transactionTest.mapper.OrderHeadMapper;
import com.vanniuner.transactionTest.mapper.OrderItemMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import lombok.extern.slf4j.Slf4j;

/**
 * @author LZJ
 * @time 2022-09-12 10:08:34
 **/
@Slf4j
@Component
@Scope(value = "prototype")
public class MulTransationService {

    @Autowired
    OrderHeadMapper orderHeadMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    DataSourceTransactionManager transactionManager;

    // make subthread sync
    volatile Boolean commitOrNot = true;

    public void binessdo(OrderDto orderDto){
        List<Object> taskSourceList = new ArrayList<>();
        taskSourceList.add(orderDto.getOrderHead());
        orderDto.getOrderItems().forEach(orderItemFromReq -> taskSourceList.add(orderItemFromReq));
        CountDownLatch countDownLatch = new CountDownLatch(taskSourceList.size());

        // sync doing task
        List<CompletableFuture<TransactionStatus>> furtureList = taskSourceList.stream()
                .map(sourceTask -> CompletableFuture.supplyAsync(() -> {
                    return handleTask(sourceTask, countDownLatch);
                })).collect(Collectors.toList());

        log.info("\n==== [log]: {}", "main thread wait");
        // wait all thread done
        CompletableFuture.allOf(furtureList.toArray(new CompletableFuture[furtureList.size()])).join();
        log.info("\n==== [log]: {}", "join over");
    }

    // every sub thread
    public TransactionStatus handleTask(Object sourceData, CountDownLatch countDownLatch) {
        if (!commitOrNot) return null;
        TransactionDefinition txDef =
            new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);

        try {
            if (sourceData instanceof OrderHead) {
                orderHeadMapper.insert((OrderHead) sourceData);
            }
            if (sourceData instanceof OrderItem) {
                OrderItem orderItem = (OrderItem) sourceData;
                orderItemMapper.insert(orderItem);
                if (orderItem.getAmount() == 4) {
                    // int i = 1 / 0;
                    // log.info("\n==== [error]: {}", i);
                }
            }
        } catch (Exception e) {
            log.error("{}", e);
            commitOrNot = false;
        } finally {
            countDownLatch.countDown();
        }
        try {countDownLatch.await();}
        catch (InterruptedException e) {log.error("{}", e);}
        // commit or rollback?
        if (commitOrNot) {
            log.info("\n==== [commit]: {}", JSON.toJSONString(sourceData, SerializerFeature.PrettyFormat));
            transactionManager.commit(txStatus);
        } else {
            log.info("\n==== [rollback]: {}", JSON.toJSONString(sourceData, SerializerFeature.PrettyFormat));
            transactionManager.rollback(txStatus);
        }
        return txStatus;
    }


}
