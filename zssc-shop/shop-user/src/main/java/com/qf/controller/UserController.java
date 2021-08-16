package com.qf.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import com.qf.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/addUser")
    public ResultEntity addUser(@RequestBody User user) {
        return ResultEntity.responseClinet(userService.insert(user));
    }

    @RequestMapping("/getUserPage")
    public Page<User> getUserPage(@RequestBody Map<String, Object> map) {
        log.debug("{}", map);
        System.out.println(map);

        // 0.从map去除我们需要的参数
        Object current = map.get("current");
        Object size = map.get("size");
        Object username = map.get("username");
        Object email = map.get("email");
        Object phone = map.get("phone");


        // 1.创建一个分页对象
        Page<User> page = new Page<>();
        if (!StringUtils.isEmpty(current)) {
            // current存在值
            page.setCurrent(Integer.parseInt(current.toString()));
        }

        if (!StringUtils.isEmpty(size)) {
            page.setSize(Integer.parseInt(size.toString()));
        }


        // 2.创建一个条件查询的对象
        EntityWrapper<User> entityWrapper = new EntityWrapper();
        if (!StringUtils.isEmpty(username)) {
            entityWrapper.like("username",username.toString());
        }
        if (!StringUtils.isEmpty(email)) {
            entityWrapper.like("email",email.toString());
        }
        if (!StringUtils.isEmpty(phone)) {
            entityWrapper.like("phone",phone.toString());
        }

        // 3.查询数据
        return userService.selectPage(page, entityWrapper);
    }

    @RequestMapping("/getUserById/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.selectById(id);
    }

    @RequestMapping("/updateUser")
    public ResultEntity updateUser(@RequestBody User user) {
        return ResultEntity.responseClinet(userService.updateById(user));
    }

    @RequestMapping("/userBatchDel")
    public ResultEntity userBatchDel(@RequestParam("userIdList") ArrayList<Integer> userIdList){
        log.debug("{}",userIdList);
        return ResultEntity.responseClinet(userService.deleteBatchIds(userIdList));
    }

    @RequestMapping("/getUserByUsername")
    public User getUserByUsername(String username){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("username",username);
        return userService.selectOne(entityWrapper);
    }

    @RequestMapping("/getUserByEmail")
    public User getUserByEmail(String emailStr){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("email",emailStr);
        return userService.selectOne(entityWrapper);
    }
}
