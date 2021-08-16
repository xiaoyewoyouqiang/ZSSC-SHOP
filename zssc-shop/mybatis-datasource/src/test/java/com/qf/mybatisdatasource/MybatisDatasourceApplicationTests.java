package com.qf.mybatisdatasource;

import com.qf.config.DataSourceProperties1;
import com.qf.config.DataSourceProperties2;
import com.qf.config.DynamicDataSourceConfig;
import com.qf.entity.OrderDetail;
import com.qf.mapper.OrderMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MybatisDatasourceApplicationTests {

    @Autowired
    private DataSourceProperties1 dataSourceProperties1;

    @Autowired
    private DataSourceProperties2 dataSourceProperties2;

    //	@Autowired
    private DataSource xxxxxx;

    @Test
    void contextLoads() {
        System.out.println(dataSourceProperties1);
        System.out.println(dataSourceProperties1.getUrl());
        System.out.println(dataSourceProperties2);
        System.out.println(dataSourceProperties2.getUrl());
    }

    @Test
    void test() {
        System.out.println(xxxxxx);
    }

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testAddOrder() {

        Integer userId = 1234;

        // 1.设置数据源
        DynamicDataSourceConfig.setDbName(userId);

        // 2.计算出插入到那个表
        // 分表的规则:用户后四位/数据库的数量|表的数量
        Integer tabIndex = userId / 2 % 2; // 0,1

        // 2.插入订单
        com.qf.entity.Order order = new com.qf.entity.Order();
        order.setPhone("abc");

        Integer insert = orderMapper.addOrder(order, (tabIndex+1));

        // isnert into t_order_${index} (name)

        // 3.插入订单详情
        List<OrderDetail> orderDetailList =new ArrayList<>();

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setGpng("1.png");

        OrderDetail orderDetai2 = new OrderDetail();
        orderDetai2.setGpng("2.png");

        orderDetailList.add(orderDetail1);
        orderDetailList.add(orderDetai2);

        orderMapper.batchDelOrderDetail(orderDetailList,(tabIndex+1));


        System.out.println();


    }

    @Test
    public void test3() {

        // 1创建一个ThreadLocal
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        // 2.给ThreadLocal设置数据
        threadLocal.set("abc"); // 设置

        xxxAbc(threadLocal);
    }

    private void xxxAbc(ThreadLocal<String> threadLocal) {
        String s = threadLocal.get();
        System.out.println(s);
    }

}
