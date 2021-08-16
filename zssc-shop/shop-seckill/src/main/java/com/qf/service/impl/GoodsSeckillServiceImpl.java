package com.qf.service.impl;

import com.qf.entity.Goods;
import com.qf.entity.Order;
import com.qf.entity.SeckillException;
import com.qf.entity.SeckillGoods;
import com.qf.mapper.GoodsSeckillMapper;
import com.qf.mapper.OrderMapper;
import com.qf.service.IGoodsSeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class GoodsSeckillServiceImpl implements IGoodsSeckillService {

    @Autowired
    private GoodsSeckillMapper goodsSeckillMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String seckillGoodsKey = "seckill_";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct // 创建这个bean之后调用
    public void init() {
        System.out.println("GoodsSeckillServiceImpl.init");
        // 模拟定时任务把秒杀的商品放入到redis中
        // key是秒杀的商品，value商品的库存，超时时间可以取秒杀的时长
        stringRedisTemplate.opsForValue().set(seckillGoodsKey + "15", "100", 10, TimeUnit.MINUTES);
    }


    @Override
    public void seckillGoods(Integer id) {

        // 先判断用户是否登录
//        checkUserLogin();

        // 判断秒杀是否开始
        checkSeckillGoodsTime(id);

        // 1.判断库存
//        Goods goods = checkGoodsGstock(id);
        Goods goods = checkGoodsGstockRedis(id);

        // 2.扣减库存
//        updateGoodsGsotck(goods);
        updateGoodsGstockRedis(goods);

        // 3.生成订单
        createOrder(goods);
    }

    private void updateGoodsGstockRedis(Goods goods) {
        Long decrement = stringRedisTemplate.opsForValue().decrement(seckillGoodsKey + goods.getId());
    }

    private Goods checkGoodsGstockRedis(Integer id) {
        // 1.根据商品id从reids中查询库存
        Integer gstock = Integer.parseInt(stringRedisTemplate.opsForValue().get(seckillGoodsKey + id));

        if (gstock <= 0) {
            throw new SeckillException("商品太火爆了，已经被抢完了，下次再次。。。。");
        }
        return new Goods(id, gstock.intValue());
    }

    private void checkSeckillGoodsTime(Integer id) {

        // 判断秒杀是否开始，查看redis中是否存在这个商品
        if (!stringRedisTemplate.hasKey(seckillGoodsKey + id)) {
            throw new SeckillException("秒杀还没开始");
        }
    }


    private void createOrder(Goods goods) {
        log.info("{}", "生成订单");

//        Order order = new Order(null, goods.getId(), goods.getGname(), new Date(), 16);

//        orderMapper.insert(order);


        // 封装秒杀的商品对象
        SeckillGoods seckillGoods = new SeckillGoods(goods.getId(), 17, 1, new Date(),1);


        // 把订单对象发送给订单系统，由订单系统来生成订单
        rabbitTemplate.convertAndSend("seckill-order-exchange", "order.create", seckillGoods);
    }

    private void updateGoodsGsotck(Goods goods) {
        log.info("{}", "扣减库存");
        Integer status = goodsSeckillMapper.updateGoodsGstock(goods);// 使用乐观锁控制多线程扣减库存的问题
        log.debug("status:{}", status);
        if (status <= 0) {
            throw new SeckillException("商品太火爆了，已经被抢完了，下次再次。。。。");
        }
    }

    private Goods checkGoodsGstock(Integer id) {
        log.info("{}", "判断库存");
        Goods goods = new Goods();
        goods.setId(id);
        goods = goodsSeckillMapper.selectOne(goods);

        if (goods == null) {
            throw new SeckillException("商品不存在或者该商品不是秒杀的商品");
        }

        if (goods.getGstock() <= 0) {
            throw new SeckillException("商品库存不足");
        }
        return goods;
    }
}
