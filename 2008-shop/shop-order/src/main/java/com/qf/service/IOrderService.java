package com.qf.service;

import com.baomidou.mybatisplus.service.IService;
import com.qf.entity.Order;
import com.qf.entity.SeckillGoods;

import java.util.List;
import java.util.Map;

public interface IOrderService extends IService<Order>{

    String createOrder(Integer id, Integer addressId);

    Order getOrderById(Integer userIdSuffix, String oid);

    Integer updateOrderStatus(Map<String,String> map);

    List<Order> getOrderListByUserId(Integer userId);

    void createSeckillOrder(SeckillGoods seckillGoods);
}
