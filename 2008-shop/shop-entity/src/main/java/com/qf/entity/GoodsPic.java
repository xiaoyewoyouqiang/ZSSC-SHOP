package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_goods_pic")
public class GoodsPic implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String png;

    private Integer gid;
}
