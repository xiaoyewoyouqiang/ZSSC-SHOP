package com.qf.utils;

import com.qf.constants.ShopConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OrderUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * @param userId 用户的id
     * @return
     */
    public String createOrderId(Integer userId) {

        // 订单的规则 年月日+用户id后四位+四位随机送+流水号

        // 1.获取时间
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // 2.获取用户后四位
        String userIdsSuffix = getUserIdSuffix(userId.toString());

        // 3.获取四位随机数
        String randomNumber = RandomStringUtils.random(4, false, true);

        // 4.流水号
        Integer orderIndex = 1;
        // 4.1 判断这个流水号这个key是否存在
        if (redisTemplate.hasKey(ShopConstants.ORDER_INDEX)) {
            // 如果存在就自增
            orderIndex = redisTemplate.opsForValue().increment(ShopConstants.ORDER_INDEX).intValue();
        } else {
            // 不存在就插入
            redisTemplate.opsForValue().set(ShopConstants.ORDER_INDEX, orderIndex.toString());
        }

        // 组合起来
        StringBuffer buffer = new StringBuffer();
        buffer.append(date).append(userIdsSuffix).append(randomNumber).append(orderIndex);

        // 返回订单编号
        return buffer.toString();
    }

    // 根据真实的用户id来获取用户id后四位
    public String getUserIdSuffix(String userId) { // 20

        StringBuffer buffer = new StringBuffer(userId); // 创建一个buffer用来接收用户id后四位

        // 1.先判断用户id的长度是否大于四位
        if (buffer.length() < 4) {
            // 如果不够四位，则需要给前面不零
            for (int i = buffer.length(); i < 4; i++) {
                buffer.insert(0, "0"); //往前面不零， 少几个0，补几个0
            }
            return buffer.toString();
        } else {
            return buffer.substring((buffer.length() - 4));
        }
    }

    public static void main(String[] args) {

//        String uid = "1234";
//
//        String userIdSuffix = getUserIdSuffix(uid);
//        System.out.println(userIdSuffix);
//
//        String random = RandomStringUtils.random(4);
//        System.out.println(RandomStringUtils.random(4, false, true));

    }
}
