package com.qf.feign.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.qf.entity.Goods;
import com.qf.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;

@FeignClient("shop-goods")
public interface IGoodsService {

    @RequestMapping("/goods/addGoods")
    public ResultEntity addGoods(@RequestBody Goods goods);

    @RequestMapping("/goods/getGoodsPage")
    public Page<Goods> getGoodsPage(@RequestBody Page<Goods> page);

    @RequestMapping("/goods/getGoodsById/{id}")
    public Goods getGoodsById(@PathVariable("id") Integer id);

    @RequestMapping("/goods/updateGoods")
    public ResultEntity updateGoods(@RequestBody Goods goods);

    @RequestMapping("/goods/goodsBatchDel")
    ResultEntity goodsBatchDel(@RequestParam("goodsIdList") ArrayList<Integer> goodsIdList);


}
