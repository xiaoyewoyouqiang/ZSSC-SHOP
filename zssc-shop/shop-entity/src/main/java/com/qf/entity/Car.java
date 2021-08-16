package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

@Data
@TableName("t_car")
public class Car {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer gid;

    private Integer uid;

    private Integer count;
}
