package com.qf.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.qf.entity.Goods;
import com.qf.entity.ResultEntity;
import com.qf.feign.api.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/goodsController")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/getGoodsPage")
    public String getGoodsPage(Page<Goods> page, ModelMap modelMap){
        page = goodsService.getGoodsPage(page);
        modelMap.put("page",page);
        modelMap.put("url","http://localhost/shop-back/goodsController/getGoodsPage");
        return "goods/goodsList";
    }

    @RequestMapping("/addGoods")
    @ResponseBody
    public ResultEntity addGoods(Goods goods){
        System.out.println(goods);
        return goodsService.addGoods(goods);
    }
}
