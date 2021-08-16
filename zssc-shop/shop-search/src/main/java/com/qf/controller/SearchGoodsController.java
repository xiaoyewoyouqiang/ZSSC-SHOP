package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.service.IGoodsSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/searchGoodsController")
@Slf4j
public class SearchGoodsController {

    @Autowired
    private IGoodsSearchService goodsSearchService;

    @RequestMapping("/searchGoodsList")
    public String searchGoodsList(String keyword,Integer psort, ModelMap modelMap) throws Exception {

        List<Goods> goodsList = goodsSearchService.searchGoodsList(keyword,psort);
        modelMap.put("goodsList",goodsList);
        log.debug("{}",keyword);
        log.debug("{}",goodsList);
        return "searchList"; // 展示搜索到的视图页面
    }
}
