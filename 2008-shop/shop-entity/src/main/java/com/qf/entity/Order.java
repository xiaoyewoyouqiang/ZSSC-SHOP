package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_order")
public class Order implements Serializable{

    @TableId(type = IdType.INPUT)
    private String id; // 自己生成的订单id长度超过了10位，所以这里需要用字符串

    private Integer uid;

    private Date createTime; // 自动完成驼峰转下换线

    private String address;

    private String phone;

    private String username;

    private BigDecimal totalPrice;

    // 1:支付宝，2:微信
    private Integer payType;

    // 1:未支付，2：已支付，3:已取消，4：已超时，5：已发货，6：已收到
    private Integer status;

    @TableField(exist = false)
    private List<OrderDetail> orderDetailList;

}
