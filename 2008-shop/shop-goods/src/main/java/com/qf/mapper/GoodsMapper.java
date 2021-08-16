package com.qf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qf.entity.Goods;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {
    int addGoods(Goods goods);

    List<Goods> getGoodsPage();
}
