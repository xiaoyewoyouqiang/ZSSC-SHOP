package com.qf.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qf.aop.annocation.LoginUser;
import com.qf.constants.ShopConstants;
import com.qf.domain.CarGoods;
import com.qf.entity.Car;
import com.qf.entity.Goods;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import com.qf.service.ICarService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/carController")
public class CarController {

    @Autowired
    private ICarService carService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct // 在controller之前执行
    public void init() {
        // 设置key的序列化方式为字符串
        redisTemplate.setKeySerializer(new StringRedisSerializer());
    }

    @RequestMapping("/test")
    public String test() {
        System.out.println("CarController.test");
        return "test";
    }

    @RequestMapping("/addCarMySQL")
    @ResponseBody
    public ResultEntity addCarMySQL(@RequestBody Car car) {
        return ResultEntity.success(carService.insert(car));
    }

    @RequestMapping("/getCarGoodsList/{uid}")
    @ResponseBody
    public List<CarGoods> getCarGoodsList(@PathVariable Integer uid) {
        return carService.getMySQLUserCarList(uid);
    }


    /**
     * @param user 当前登录的用户，如果没有登录就是null
     * @param car
     * @return
     */
    @RequestMapping("/addCar")
    @LoginUser //代表这个接口上需要注入当前登录的用户(如果登录就注入user，没有登录就注入null)
    @ResponseBody
    public ResultEntity addCar(@CookieValue(name = ShopConstants.ANON_ID, required = false) String anonId, User user, Car car, HttpServletResponse response) {

        System.out.println("CarController.addCar");
        // 判断用户是否登录
        if (user.getId() != null) {
            // 把购车添加到数据库
            car.setUid(user.getId());
            boolean insert = carService.insert(car);
        } else {

            // 1、生成匿名用户的唯一标识
            if (StringUtils.isEmpty(anonId)) { // 如果没有匿名用户的唯一标识才创建一个uuid
                anonId = UUID.randomUUID().toString();

                // 3、把匿名用户的唯一标识写到浏览器中
                Cookie cookie = new Cookie(ShopConstants.ANON_ID, anonId);
                cookie.setPath("/"); // 解决跨域的问题
                cookie.setMaxAge(60 * 60 * 24 * 7); // cookie的有效时间

                response.addCookie(cookie);
            }

            // 2、把购车添加到reids中(hash)
            Integer gid = car.getGid();
            Integer count = car.getCount();
            redisTemplate.opsForHash().put(anonId, gid, count);
        }

        return ResultEntity.success();
    }


    @RequestMapping("/getUserCarList")
    @LoginUser
    public String getUserCarList(User user, @CookieValue(name = ShopConstants.ANON_ID, required = false) String anonId, ModelMap modelMap) {

        List<CarGoods> carList = new ArrayList<>();

        if (user.getId() != null) {
            // 从数据库中查询
            carList = carService.getMySQLUserCarList(user.getId());
        } else {
            // 从redis查询
            if (!StringUtils.isEmpty(anonId)) {

                // 根据key获取所有的field集合
                Set keys = redisTemplate.opsForHash().keys(anonId);

                // 判断当前匿名用户是否存在购物车
                if (keys != null && !keys.isEmpty()) {

                    // 遍历购物车的商品
                    for (Object gid : keys) {

                        // 根据key和商品的id获取商品的数量
                        Object count = redisTemplate.opsForHash().get(anonId, gid);

                        // 根据商品的id查询商品的信息
                        CarGoods carGoods = carService.getCarGoodsById(gid);

                        // 把商品的数量封装到CarGoods对象中
                        carGoods.setCount(Integer.parseInt(count.toString()));

                        // 添加到集合中
                        carList.add(carGoods);
                    }
                }

            }
        }
        System.out.println(carList);
        modelMap.put("carList", carList);
        return "carList"; // 购物车列表
    }


    @RequestMapping("/updateCar")
    @LoginUser
    @ResponseBody
    public ResultEntity updateCar(User user, @CookieValue(name = ShopConstants.ANON_ID, required = false) String anonId, Integer gid, Integer count) {

        if (user.getId() != null) {

            // 修改数据库
            Car car = new Car();
            car.setCount(count);

            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("gid", gid);
            entityWrapper.eq("uid", user.getId());

            // 根据用户id和商品id来修改购物车的中商品的数量
            carService.update(car, entityWrapper);

        } else {
            redisTemplate.opsForHash().put(anonId, gid, count);
        }
        return ResultEntity.success();
    }


    @RequestMapping("/deleteCar")
    @LoginUser
    @ResponseBody
    public ResultEntity deleteCar(User user, @CookieValue(name = ShopConstants.ANON_ID, required = false) String anonId, Integer gid) {

        if (user.getId() != null) {

            // 根据用户id和商品的id删除
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("gid", gid);
            entityWrapper.eq("uid", user.getId());
            carService.delete(entityWrapper);

        } else {

            // 删除用户购物车中的某一件商品
            redisTemplate.opsForHash().delete(anonId, gid);
        }
        return ResultEntity.success();
    }

    @RequestMapping("/shoCarNum")
    @LoginUser
    @ResponseBody
    public ResultEntity shoCarNum(User user, @CookieValue(name = ShopConstants.ANON_ID, required = false) String anonId) {

        int count = 0;

        if (user.getId() != null) {

            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("uid", user.getId());
            count = carService.selectCount(entityWrapper);

        } else {
            count = redisTemplate.opsForHash().keys(anonId).size();
        }
        return ResultEntity.success(count);
    }

    @RequestMapping("/clearUserCar")
    @ResponseBody
    public ResultEntity clearUserCar(Integer uid) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("uid", uid); // 把这个用户下等购物车全部删除
        return ResultEntity.success(carService.delete(entityWrapper));
    }
}
