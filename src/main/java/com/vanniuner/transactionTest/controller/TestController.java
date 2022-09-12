package com.vanniuner.transactionTest.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.vanniuner.transactionTest.dto.OrderDto;
import com.vanniuner.transactionTest.entity.OrderHead;
import com.vanniuner.transactionTest.entity.OrderItem;
import com.vanniuner.transactionTest.mapper.OrderHeadMapper;
import com.vanniuner.transactionTest.service.MulTransationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author LZJ
 * @time 2022-09-10 20:28:17
 **/
@Slf4j
@RestController
public class TestController {

    @Autowired
    OrderHeadMapper orderMapper;

    @Autowired
    MulTransationService mulTransationService;

    /**
     * 健康检查
     */
    @RequestMapping("/healthCheck")
    public ResponseEntity<String> welcome() {
        List<OrderHead> selectList = orderMapper.selectList(null);
        log.info("\n==== [log]: {}", JSON.toJSONString(selectList, SerializerFeature.PrettyFormat));
        return ResponseEntity.ok("pong1");
    }

    /**
     * 健康检查
     */
    @RequestMapping("/createOrder")
    public ResponseEntity<String> createOrder(OrderDto orderDto) {
        // 模拟一个订单
        OrderHead orderHead = new OrderHead();
        Long orderId = IdWorker.getId();
        orderHead.setId(orderId);
        orderHead.setCustId("17");
        orderHead.setAmount(88);
        orderHead.setEmail("demo.163.com");
        orderHead.setOrderNo("SO17");
        orderDto = new OrderDto(orderHead);
        List<OrderItem> orderItems = new ArrayList<>();
        // 模拟5个货品行
        for (int i = 0; i < 5; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setItemNo(orderId + i + "");
            orderItem.setAmount(i);
            orderItem.setUnit("公斤");
            orderItems.add(orderItem);
        }
        orderDto.setOrderItems(orderItems);
        mulTransationService.binessdo(orderDto);
        return ResponseEntity.ok("pong");
    }

}
