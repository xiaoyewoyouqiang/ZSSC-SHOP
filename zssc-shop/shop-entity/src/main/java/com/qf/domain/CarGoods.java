package com.qf.domain;

import com.qf.entity.GoodsPic;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarGoods {

    private Integer id;

    private String gname;

    private Integer gtype;

    private BigDecimal gprice;

    private String gdesc;

    private Integer count;

    private List<GoodsPic> goodsPicList;
}
