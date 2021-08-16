package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillGoods implements Serializable{

    private Integer gid;

    private Integer uid;

    private Integer count;

    private Date createTime;

    private Integer addressId;
}
