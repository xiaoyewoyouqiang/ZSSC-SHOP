package com.qf.service;

import com.baomidou.mybatisplus.service.IService;
import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsService extends IService<Goods>{
    boolean addGoods(Goods goods);

    List<Goods> getGoodsPage();
}
