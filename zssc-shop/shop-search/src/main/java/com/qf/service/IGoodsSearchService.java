package com.qf.service;

import com.qf.entity.Goods;

import java.io.IOException;
import java.util.List;

// 这个接口中的方法就是操作ES的
public interface IGoodsSearchService {

    public void addGoods(Goods goods) throws IOException;

    List<Goods> searchGoodsList(String keyword, Integer psort) throws Exception;
}
