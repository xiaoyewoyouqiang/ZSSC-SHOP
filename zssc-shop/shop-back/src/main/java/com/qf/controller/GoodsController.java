package com.qf.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.qf.entity.Goods;
import com.qf.entity.ResultEntity;
import com.qf.feign.api.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/goodsController")
@Slf4j
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
//----------------------------------------------------------//
    @RequestMapping("/getGoodsById/{id}")
    public String getGoodsById(@PathVariable Integer id, Model model){
        Goods goods = goodsService.getGoodsById(id);
        model.addAttribute("goods",goods);
        return "goods/updateGoods";
    }

    @RequestMapping("/updateGoods")
    @ResponseBody
    public ResultEntity updateGoods(Goods goods){
        return goodsService.updateGoods(goods);
    }

    @RequestMapping("/goodsBatchDel")
    @ResponseBody
    public ResultEntity goodsBatchDel(@RequestParam("goodsIdList[]") ArrayList<Integer> goodsIdList){
        log.debug("{}",goodsIdList);
        return goodsService.goodsBatchDel(goodsIdList);
    }
}
