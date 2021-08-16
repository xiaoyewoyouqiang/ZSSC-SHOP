package com.qf.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.qf.constants.ShopConstants;
import com.qf.entity.*;
import com.qf.feign.api.ICarService;
import com.qf.feign.api.IUserService;
import com.qf.utils.JWTUtils;
import com.qf.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/ssoUserController")
@Slf4j
public class SSOUserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ICarService carService;

    @PostConstruct
    public void init(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
    }

    @RequestMapping("/validaUsername")
    public Map<String, Object> validaUsername(@RequestParam("param") String username) {

        // 1.查询用户是否存在
        User user = userService.getUserByUsername(username);

        Map<String, Object> map = new HashMap<>();
        // 2.判断用户是否为空
        if (user != null) {
            map.put("status", "n");// 用户已经存在
            map.put("info", "该用户名已存在");

        } else {
            map.put("status", "y");// 用户已经存在
            map.put("info", "用户名可以使用");
        }
        return map;
    }

    @RequestMapping("/validaEmail")
    public Map<String, Object> validaEmail(@RequestParam("param") String emailStr) {

        // 1.判断该邮箱是否已经被注册
        User user = userService.getUserByEmail(emailStr);


        Map<String, Object> map = new HashMap<>();
        // 2.判断用户是否为空
        if (user != null) {
            map.put("status", "n");// 用户已经存在
            map.put("info", "该邮箱已经被注册");

        } else {
            map.put("status", "y");// 用户已经存在
            map.put("info", "邮箱可以使用");
        }
        return map;
    }

    @RequestMapping("/sendEmail")
    public String sendEmail(String emailStr) {

        // 生成一个随机验证码
        String code = RandomStringUtils.random(6, false, true);

        // 把验证码保存到redis中 ,把邮箱和验证码绑定
        stringRedisTemplate.opsForValue().set(ShopConstants.SSO_REGISTER_KEY + emailStr, code, 20, TimeUnit.SECONDS);

        // 1.创建一个邮箱对象
        Email email = new Email();
        email.setTitle("2008电商新用户注册");
        email.setContent("您的验证码为:" + code);
        email.setToUser(emailStr);

        // 2.调用发送邮件的服务(另一个模块)，异步形式
        rabbitTemplate.convertAndSend(ShopConstants.EMAIL_EXCHANGE, ShopConstants.EMAIL_ROUTING_KEY, email);

        // 3.业务的处理
        return "ok";

    }

    @RequestMapping("/registerUser")
    public ResultEntity registerUser(User user, String code) {

        // 验证用户输入的验证码是否正确

        // 1.从redis中查询该邮箱对象的验证码
        String reidsCode = stringRedisTemplate.opsForValue().get(ShopConstants.SSO_REGISTER_KEY + user.getEmail());

        // 2.判断是否为空
        if (reidsCode == null) {
            throw new ShopException(10002, "验证码已失效");
        }

        // 3、对比验证码是否一样的
        if (!reidsCode.equals(code)) {
            throw new ShopException(10003, "验证码有误");
        }

        // 4、这里虽然说是前台做了用户名唯一的校验，但是在前后端分离的项目中，后台还是要做校验的(不能相信前台)

        //  用户名是否被注册，邮箱是否被注册

        // 5.把新的用户添加到数据库中
        // 密码加密
        user.setPassword(PasswordUtils.encode(user.getPassword()));
        userService.addUser(user);

        // 5.注册成功了
        return ResultEntity.success();

    }

    @RequestMapping("/getUserByUsername")
    public ResultEntity getUserByUsername(String username) {

        // 1、查询用户名是否存在
        User user = userService.getUserByUsername(username);

        // 2.判断用户是否存在
        if (user == null) {
            return ResultEntity.error("该用户没有被注册");
        }

        // 创建一个Map把id和用户名保存进去
        Map<String, String> payLoadMap = new HashMap<>();
        payLoadMap.put("id", user.getId().toString());
        payLoadMap.put("username", user.getUsername());

        // 把id和用户名揉到token中
        String token = JWTUtils.createToken(payLoadMap, 5); // token的超时时间是5m

        // 3.存在就就用户用户的邮箱发送修改密码的连接
        Email email = new Email();
        email.setTitle("2008电商用户密码修改");
        email.setContent("点击<a href = 'http://localhost/shop-sso/toChangePasswordPage?token=" + token + "'>这里</a>修改密码");
        email.setToUser(user.getEmail());

        // 4.调用邮件服务把邮件发送出去
        rabbitTemplate.convertAndSend(ShopConstants.EMAIL_EXCHANGE, ShopConstants.EMAIL_ROUTING_KEY, email);

        // 处理用户邮箱首页地址
        String email1 = user.getEmail(); // 854569279@qq.com
        String ehome = "http://mail." + email1.substring(email1.lastIndexOf("@") + 1);

        String eurl = email1.replaceAll(email1.substring(0, email1.lastIndexOf("@") - 4), "*****");
        System.out.println(eurl);

        Map<String, String> map = new HashMap<>();
        map.put("ehome", ehome);
        map.put("eurl", eurl);


        // 5、提示用户，修改密码的连接以发送到你的邮箱,请的登录查看
        return ResultEntity.success(map);
    }

    @RequestMapping("/updateUserPassword")
    public ResultEntity updateUserPassword(String token, String password) {

        // 1.判断token是否为空
        if (StringUtils.isEmpty(token)) {
            return ResultEntity.error("token不能为空");
        }

        // 2.校验tokne的合法性
        DecodedJWT decodedJWT = JWTUtils.checkToken(token);

        // 3.从token中获取用户id
        String uid = decodedJWT.getClaim("id").asString();

        // 4.修改用户的密码
        User user = new User();
        user.setId(Integer.parseInt(uid));
        user.setPassword(PasswordUtils.encode(password)); // 密码加密
        ResultEntity resultEntity = userService.updateUser(user);

        return resultEntity;
    }

    @RequestMapping("/loginUser")
    public ResultEntity loginUser(String username, String password, @CookieValue(name = ShopConstants.ANON_ID, required = false) String anonId) {

        // 1、根据用户名查询对象
        User user = userService.getUserByUsername(username);

        // 2.判断用户是否为空
        if (user == null) {
            return ResultEntity.error("用户名不存在");
        }

        // 3.密码的比对
        if (!PasswordUtils.checkpw(password, user.getPassword())) {
            return ResultEntity.error("用户名获密码错误");
        }

        // 4.登录成功
        Map<String, String> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("id", user.getId().toString());
        String token = JWTUtils.createToken(map, 1 * 60 * 24 * 7); // token用户的标识

        // 合并购物车(reids中的数据---》MySQL)
        if (!StringUtils.isEmpty(anonId)) {

            // 1.先获取用户在redis中的购物车数据

            // 根据匿名id查询商品的id
            Set<Object> keys = redisTemplate.opsForHash().keys(anonId);

            if (keys != null && !keys.isEmpty()) {
                for (Object gid : keys) {
                    // 再根据匿名id和商品id查询商品的数量
                    Object count = redisTemplate.opsForHash().get(anonId, gid);

                    // 把redis中的购物车添加到MySQL
                    Car car = new Car();
                    car.setUid(user.getId());
                    car.setGid(Integer.parseInt(gid.toString()));
                    car.setCount(Integer.parseInt(count.toString()));

                    // 调用购物车服务的接口添加到MySQL中
                    carService.addCarMySQL(car);

                    // 清空redis中的购物车
                }
                    redisTemplate.delete(anonId);
            }
        }

        // 5、响应给用户
        return ResultEntity.success(token);
    }

}
