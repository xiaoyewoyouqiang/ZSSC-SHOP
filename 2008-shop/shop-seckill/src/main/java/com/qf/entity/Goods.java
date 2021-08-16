package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("t_goods")
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String gname;

    private String gdesc;

    private String gprice;

    private Integer gstock;

    private String gpic;

    private Integer gtype;

    public Goods(Integer id, Integer gstock) {
        this.id = id;
        this.gstock = gstock;
    }

}
