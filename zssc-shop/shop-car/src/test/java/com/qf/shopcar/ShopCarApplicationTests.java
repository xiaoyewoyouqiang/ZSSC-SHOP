package com.qf.shopcar;

import com.qf.domain.CarGoods;
import com.qf.mapper.CarMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class ShopCarApplicationTests {

	@Autowired
	private CarMapper carMapper;

	@Test
	void contextLoads() {
		List<CarGoods> carList = carMapper.getMySQLUserCarList(17);
		for (CarGoods map : carList) {
			System.out.println(map);
		}
	}

}
