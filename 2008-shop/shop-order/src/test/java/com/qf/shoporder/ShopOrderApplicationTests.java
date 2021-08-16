package com.qf.shoporder;

import com.qf.entity.Order;
import com.qf.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

//@SpringBootTest
class ShopOrderApplicationTests {

    @Autowired
    private IOrderService orderService;


    @Test
    public void testGetOrderListByUserId() {
        List<Order> orderList = orderService.getOrderListByUserId(22);
        for (Order order : orderList) {
            System.out.println(order);
        }

    }

    @Test
    void contextLoads() {

        BigDecimal price = new BigDecimal(2999.9);
        Integer count = 3;

        BigDecimal gprice2 = new BigDecimal(100);

        BigDecimal multiply = price.multiply(BigDecimal.valueOf(count));
        System.out.println(multiply);
        System.out.println(price);

        BigDecimal add = price.add(gprice2);
        System.out.println(add);

        String oid = "2021022600178888345";

        String substring = oid.substring(8, 8 + 4);
        System.out.println(substring);
    }

    @Test
    public void testOrderDB() {

        Integer uid = 1234; // 用户id|
        Integer uid2 = 6313;
        Integer dbCount = 2; // 数据库的数量

        // 模拟数据库需要扩容4次，(扩容成倍增长)
        for (int i = 1; i <= 6; i++) {
            int dbIndex = uid % dbCount;
            int dbIndex2 = uid2 % dbCount;
            System.out.println("第[" + i + "]次库容,【1234】这个用户的订单是存在【" + dbIndex + "】库");
            System.out.println("第[" + i + "]次库容,【6313】这个用户的订单是存在【" + dbIndex2 + "】库");
            dbCount *= 2;
            System.out.println("-----------------------------");
        }

    }
}
