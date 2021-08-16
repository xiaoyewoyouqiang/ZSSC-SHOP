package com.qf.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.qf.constants.ShopConstants;
import com.qf.entity.Goods;
import com.qf.entity.GoodsPic;
import com.qf.entity.ResultEntity;
import com.qf.service.IGoodsPicService;
import com.qf.service.IGoodsService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IGoodsPicService goodsPicService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/addGoods")
    public ResultEntity addGoods(@RequestBody Goods goods){
        System.out.println(goods);

        // 1.添加商品和商品图片
        boolean insert = goodsService.insert(goods);

        // 2.同步到es
        rabbitTemplate.convertAndSend(ShopConstants.GOODS_EXCHANGE,ShopConstants.GOODS_ROUTING_KEY,goods);

        return ResultEntity.responseClinet(insert);
    }

    @RequestMapping("/getGoodsPage")
    public Page<Goods> getGoodsPage(@RequestBody Page<Goods> page){
        page = goodsService.selectPage(page, null);


        // 获取当前页要展示的数据
        List<Goods> records = page.getRecords();
        for (Goods record : records) {
            Integer id = record.getId(); // 商品id

            // 根据商品id查询这个视频的图片
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("gid",id);
            List<GoodsPic> goodsPicList= goodsPicService.selectList(entityWrapper);

            // 把商品的图片设置到商品对象中
            record.setGoodsPicList(goodsPicList);
        }


//        // 1.设置分页参数
//        PageHelper.startPage(page.getCurrent(),page.getSize());
//
//        // 2.查询当前页要展示的数据
//        List<Goods> goodsList = goodsService.getGoodsPage();
//
//        System.out.println(goodsList);
//        System.out.println(goodsList.getClass());

        return page;
    }

    @RequestMapping("/getGoodsById/{id}")
    public Goods getGoodsById(@PathVariable Integer id){
        return goodsService.selectById(id);
    }

    @RequestMapping("/updateGoods")
    public ResultEntity updateGoods(@RequestBody Goods goods){
        return ResultEntity.responseClinet(goodsService.updateById(goods));
    }

}
