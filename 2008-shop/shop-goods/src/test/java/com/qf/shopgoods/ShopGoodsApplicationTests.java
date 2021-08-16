package com.qf.shopgoods;

import com.qf.entity.Goods;
import com.qf.entity.GoodsPic;
import com.qf.service.IGoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ShopGoodsApplicationTests {

	@Autowired
	private IGoodsService goodsService;
	@Test
	void contextLoads() {

		Goods goods = new Goods();
		goods.setGname("冰箱");
		goods.setGdesc("双开门");
		goods.setGtype(1);
		goods.setTempPng("a.png|b.png|3.png");
		goods.setGprice(new BigDecimal(899.9));


		// 2.拆分
		String[] pntArray= goods.getTempPng().split("\\|");

		System.out.println(Arrays.toString(pntArray));
		// 3.准备一个集合，用来装商品的图片对象
		List<GoodsPic> goodsPicList = new ArrayList<>();

		// 4、把商品的图片封装成一个对象，全部放到list中
		for(int i =0;i<pntArray.length;i++){
			GoodsPic goodsPic = new GoodsPic();
			goodsPic.setPng(pntArray[i]);
			goodsPic.setGid(goods.getId());
			goodsPicList.add(goodsPic);
		}

		// 5、把商品图片的集合设置到商品对象中
		goods.setGoodsPicList(goodsPicList);

		boolean b = goodsService.addGoods(goods);
		System.out.println(b);

	}

}
