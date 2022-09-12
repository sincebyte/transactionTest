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
public class OrderHead {

    /**
     * 主键ID
     */
    Long id;

    /**
     *  订单编号
     */
    String orderNo;

    /**
     *  金额
     */
    Integer amount;

    /**
     *  邮箱
     */
    String email;

    /**
     *  客户id
     */
    String custId;

    /**
     *  URRENT_TIMESTAMP
     */
    Date createdAt;
}
