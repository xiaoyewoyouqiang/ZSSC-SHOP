package com.qf.shopuser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class ShopUserApplicationTests {

//	@Autowired
//	private DataSource dataSource;

	@Test
	void contextLoads() {
		System.out.println("ABCDEa123abc".hashCode());
		System.out.println("ABCDFB123abc".hashCode());

	}

}
