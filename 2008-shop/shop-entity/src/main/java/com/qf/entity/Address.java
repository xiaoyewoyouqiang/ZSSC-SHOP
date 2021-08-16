package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.omg.CORBA.INTERNAL;

@Data
@TableName("t_address")
public class Address {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String phone;

    private String address;

    private Integer uid;

    private Integer def;
}
