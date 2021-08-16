package com.qf.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.qf.entity.ResultEntity;
import com.qf.entity.ShopException;
import com.qf.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @RequestMapping("/getUserByToken")
    public ResultEntity getUserByToken(@RequestHeader(name = "Authorization",required = false) String token) throws Exception{
        log.debug("{}",token);

        // 1、校验
        DecodedJWT decodedJWT = JWTUtils.verify(token);

        // 2、从Token中获取用户的信息
        String username = decodedJWT.getClaim("username").asString();

        // 3、要把用户的信息响应给浏览器
        return ResultEntity.success(username);
    }
}
