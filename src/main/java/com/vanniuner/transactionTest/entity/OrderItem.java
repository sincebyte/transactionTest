package com.vanniuner.transactionTest.entity;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LZJ
 * @time 2022-09-10 20:41:07
 **/
@Data
@NoArgsConstructor
public class OrderItem {

    /**
     * 主键ID
     */
    Long id;

    /**
     *  商品编号
     */
    String itemNo;

    /**
     *  订单Id
     */
    Long orderId;

    /**
     *  单位
     */
    String unit;

    /**
     *  数量
     */
    Integer amount;

    /**
     *  URRENT_TIMESTAMP
     */
    Date createdAt;
}
