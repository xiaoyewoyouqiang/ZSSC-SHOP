package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@TableName("t_goods")
public class Goods implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String gname;

    private Integer gtype;

    private BigDecimal gprice;

    private String gdesc;

    @TableField(exist = false)
    private String tempPng;

    @TableField(exist = false)
    private List<GoodsPic> goodsPicList;
}
