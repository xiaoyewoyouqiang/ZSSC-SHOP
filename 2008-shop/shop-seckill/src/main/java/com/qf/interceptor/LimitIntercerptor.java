package com.qf.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import com.qf.entity.SeckillException;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractTraceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class LimitIntercerptor implements HandlerInterceptor {

    // 创建一个令牌桶对象，初始化令牌桶的数量是10个
    private RateLimiter rateLimiter = RateLimiter.create(10);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String seckillGoodsKey = "seckill_%s_%s";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("request = [" + request + "], response = [" + response + "], handler = [" + handler + "]");

        // 在这里来来完成限流操作

//        rateLimiter.acquire(); // 获取一个令牌,如果令牌桶中没有令牌会等待。
//        rateLimiter.acquire(2); // 一次从灵令牌桶中获取2个令牌

        boolean b = rateLimiter.tryAcquire(2, TimeUnit.SECONDS);// 从令牌桶中获取一个令牌，设置一个等待时间
        if (b) {
            // 1.获取用户的id和商品id
            String gid = request.getParameter("gid");
            String uid = request.getParameter("uid");

            // 构建key值
            String key = String.format(seckillGoodsKey, gid, uid);

            // 2.判断key是否存在
//            Boolean keyExists = stringRedisTemplate.hasKey(key);
            Boolean keyExists = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 30, TimeUnit.MINUTES);// 如果不存在返回0，入股

            synchronized (this) {
                if (!keyExists) {
                    Integer userCount = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
                    if (userCount >= 10) {
                        throw new SeckillException("你访问的次数已经超过限制，请稍后再试");
                    }
                    stringRedisTemplate.opsForValue().increment(key);// seckill_16_10 7
                }
                return true;
            }
        }
        System.out.println("令牌桶中没有令牌了，当前请求应该被限制才行");
        throw new SeckillException("当前抢购人数太多，请稍后再试。。。abc");
    }
}
