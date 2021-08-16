package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_order")
public class Order {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private Date createTime; // 自动完成驼峰转下换线

    private String address;

    private String phone;

    private String username;

    private BigDecimal totalPrice;

    private Integer payType;

    private Integer status;

}
