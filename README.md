多线程本地事务demo

``` mysql
DROP TABLE IF EXISTS order_head;
CREATE TABLE order_head
(
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    order_no VARCHAR(30) NULL DEFAULT NULL COMMENT '订单编号',
    amount INT(11) NULL DEFAULT NULL COMMENT '金额',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    cust_id VARCHAR(50) NULL DEFAULT NULL COMMENT '客户id',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    PRIMARY KEY (id)
);
DROP TABLE IF EXISTS order_item;
CREATE TABLE order_item
(
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    item_no VARCHAR(30) NULL DEFAULT NULL COMMENT '商品编号',
    order_id BIGINT(20) NULL DEFAULT NULL COMMENT '订单Id',
    amount INT(11) NULL DEFAULT NULL COMMENT '数量',
    unit VARCHAR(30) NULL DEFAULT NULL COMMENT '单位',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    PRIMARY KEY (id)
);

delete from order_head;
delete from order_item;

SELECT * FROM order_head order by id desc LIMIT 1 ;
SELECT * FROM order_item order by id desc LIMIT 10;
```
