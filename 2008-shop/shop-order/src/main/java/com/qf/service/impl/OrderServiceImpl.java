package com.qf.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.netflix.discovery.converters.Auto;
import com.qf.config.DynamicDataSourceConfig;
import com.qf.constants.ShopConstants;
import com.qf.domain.CarGoods;
import com.qf.entity.*;
import com.qf.feign.api.IAddressService;
import com.qf.feign.api.ICarService;
import com.qf.feign.api.IGoodsService;
import com.qf.mapper.OrderMapper;
import com.qf.service.IOrderDetailService;
import com.qf.service.IOrderService;
import com.qf.utils.OrderUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private IAddressService addressService;

    @Autowired
    private ICarService carService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private OrderUtils orderUtils;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IGoodsService goodsService;

    @Override
    public String createOrder(Integer uid, Integer addressId) {

        // 1.先根据地址id查询地址信息
        Address address = addressService.getAddressById(addressId);

        // 2、查询用户的购物车
        List<CarGoods> carGoodsList = carService.getCarGoodsList(uid);

        // 计算总价
        BigDecimal totalPrice = new BigDecimal(0.0);
        for (CarGoods carGoods : carGoodsList) {
            Integer count = carGoods.getCount();
            BigDecimal gprice = carGoods.getGprice();
            BigDecimal multiply = gprice.multiply(BigDecimal.valueOf(count));
            totalPrice = totalPrice.add(multiply);
        }

        // 先生成订单id
        String orderId = orderUtils.createOrderId(uid);

        // 3.封装
        Order order = new Order();
        order.setId(orderId);
        order.setUid(uid);
        order.setAddress(address.getAddress());
        order.setUsername(address.getUsername());
        order.setPhone(address.getPhone());
        order.setCreateTime(new Date());
        order.setStatus(1); // 未支付
        order.setPayType(1);
        order.setTotalPrice(totalPrice); // 总价


        // 获取用户id后四位
        Integer userIdSuffix = Integer.parseInt(orderUtils.getUserIdSuffix(uid.toString()));

        // 设置数据源并且返回订单表的编号
        Integer tableIndex = DynamicDataSourceConfig.setDbName(userIdSuffix);

        // 4.插入订单表
        Integer insert = baseMapper.createOrder(order, tableIndex);

        // 5.插入订单详情表
        List<OrderDetail> orderDetailList = new ArrayList<>();

        for (CarGoods carGoods : carGoodsList) {

            BigDecimal gprice = carGoods.getGprice();
            Integer count = carGoods.getCount();

            // 创建一个订单详情
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOid(order.getId());// 这里需要订单id
            orderDetail.setGid(carGoods.getId());
            orderDetail.setGdesc(carGoods.getGdesc());
            orderDetail.setGname(carGoods.getGname());
            orderDetail.setGprice(gprice);
            orderDetail.setCount(count);
            orderDetail.setGpng(carGoods.getGoodsPicList().get(0).getPng());
            orderDetail.setSubtotal(gprice.multiply(BigDecimal.valueOf(count)));

            orderDetailList.add(orderDetail);
        }

        // 批量插入订单详情
        baseMapper.batchInsertOrderDetail(orderDetailList, tableIndex);

        // 把订单对象写入MQ，开始超时的倒计时
        rabbitTemplate.convertAndSend(ShopConstants.ORDER_EXCHANGE, "order.ttl", order);


        return orderId;
    }

    @Override
    public Order getOrderById(Integer userIdSuffix, String oid) {

        // 1.设置数据源,并且返回表的编号
        Integer tableIndex = DynamicDataSourceConfig.setDbName(userIdSuffix);

        // 2.调用sql语句查询
        return baseMapper.getOrderById(oid, tableIndex);
    }

    @Override
    public Integer updateOrderStatus(Map<String, String> map) {

        // 1.获取订单id
        String orderId = map.get("orderId");

        // 2.根据订单id获取用户id后四位 20210226xxxx11113333
        String userIdSuffix = orderId.substring(8, 8 + 4);

        // 3.设置数据源,并且返回表的编号
        Integer tableIndex = DynamicDataSourceConfig.setDbName(Integer.parseInt(userIdSuffix));

        // 2.修改订单的状态
        return baseMapper.updateOrderStatus(tableIndex, map);
    }

    @Override
    public List<Order> getOrderListByUserId(Integer userId) {


        // 1.根据真实用户id获取后四位
        String userIdSuffix = orderUtils.getUserIdSuffix(userId.toString());

        // 2.设置数据源
        Integer tableIndex = DynamicDataSourceConfig.setDbName(Integer.parseInt(userIdSuffix));

        // 3.查询数据
        return baseMapper.getOrderListByUserId(tableIndex, userId);
    }

    @Override
    public void createSeckillOrder(SeckillGoods seckillGoods) {

        // 1.先根据地址id查询地址信息
        Address address = addressService.getAddressById(seckillGoods.getAddressId());

        // 2、根据商品id查询商品的信息
        Goods goods = goodsService.getGoodsById(seckillGoods.getGid());

        // 3、计算总价
        BigDecimal totalPrice = new BigDecimal(0.0);
        totalPrice = goods.getGprice().multiply(BigDecimal.valueOf(seckillGoods.getCount()));

        // 4、先生成订单id
        String orderId = orderUtils.createOrderId(seckillGoods.getUid()); // 0017

        // 4.封装
        Order order = new Order();
        order.setId(orderId);
        order.setUid(seckillGoods.getUid());
        order.setAddress(address.getAddress());
        order.setUsername(address.getUsername());
        order.setPhone(address.getPhone());
        order.setCreateTime(new Date());
        order.setStatus(1); // 未支付
        order.setPayType(1);
        order.setTotalPrice(totalPrice); // 总价

        // 获取用户id后四位
        Integer userIdSuffix = Integer.parseInt(orderUtils.getUserIdSuffix(seckillGoods.getUid().toString()));

        // 设置数据源并且返回订单表的编号
        Integer tableIndex = DynamicDataSourceConfig.setDbName(userIdSuffix);

        // 4.插入订单表
        Integer insert = baseMapper.createOrder(order, tableIndex);

        // 5.插入订单详情表
        List<OrderDetail> orderDetailList = new ArrayList<>();

        // 创建一个订单详情
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOid(order.getId());// 这里需要订单id
        orderDetail.setGid(goods.getId());
        orderDetail.setGdesc(goods.getGdesc());
        orderDetail.setGname(goods.getGname());
        orderDetail.setGprice(goods.getGprice());
        orderDetail.setCount(seckillGoods.getCount());
        orderDetail.setGpng("xxxx.png");
        orderDetail.setSubtotal(totalPrice);

        orderDetailList.add(orderDetail);

        // 批量插入订单详情
        baseMapper.batchInsertOrderDetail(orderDetailList, tableIndex);

        // 把订单对象写入MQ，开始超时的倒计时
        rabbitTemplate.convertAndSend(ShopConstants.ORDER_EXCHANGE, "order.ttl", order);
    }

}
