package com.qf.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qf.entity.Goods;
import com.qf.entity.GoodsPic;
import com.qf.mapper.GoodsMapper;
import com.qf.mapper.GoodsPicMapper;
import com.qf.service.IGoodsPicService;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private IGoodsPicService goodsPicService;

    @Override
    public List<Goods> getGoodsPage() {
        return baseMapper.getGoodsPage();
    }

    @Override
    public boolean addGoods(Goods goods) {

        return baseMapper.addGoods(goods) > 0 ? true : false;
    }

    @Override
    public boolean insert(Goods goods) {

        // 6.插入商品(插入商品图片)
        baseMapper.insert(goods); // 自动完成主键回填

        // 1.获取图片的路径
        String tempPng = goods.getTempPng();

        // 2.拆分
        System.out.println(goods);
        String[] pntArray = tempPng.split("\\|");

        // 3.准备一个集合，用来装商品的图片对象
        List<GoodsPic> goodsPicList = new ArrayList<>();

        // 4、把商品的图片封装成一个对象，全部放到list中
        for (int i = 0; i < pntArray.length; i++) {
            GoodsPic goodsPic = new GoodsPic();
            goodsPic.setPng(pntArray[i]);
            goodsPic.setGid(goods.getId());
            goodsPicList.add(goodsPic);
        }

        // 5、把商品图片的集合设置到商品对象中
        goods.setGoodsPicList(goodsPicList);


        // 7.再插入图片
        boolean b = goodsPicService.insertBatch(goodsPicList);

        return b;
    }
}
