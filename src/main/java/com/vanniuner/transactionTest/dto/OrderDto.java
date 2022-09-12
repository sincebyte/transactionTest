package com.vanniuner.transactionTest.dto;

import java.util.List;

import com.vanniuner.transactionTest.entity.OrderHead;
import com.vanniuner.transactionTest.entity.OrderItem;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LZJ
 * @time 2022-09-12 10:10:29
 **/
@Data
@NoArgsConstructor
public class OrderDto {

    OrderHead orderHead;

    List<OrderItem> orderItems;

    public OrderDto(OrderHead orderHead) {
        this.orderHead = orderHead;
    }

}
