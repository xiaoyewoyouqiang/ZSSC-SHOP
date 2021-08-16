package com.qf.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qf.constants.ShopConstants;
import com.qf.entity.ShopException;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class JWTUtils {

    /**
     * 生成token
     *
     * @param paylaod 给token中设置的数据
     * @return
     */
    public static String createToken(Map<String, String> paylaod, Integer time) {
        // 创建一个JWTbuilder
        JWTCreator.Builder builder = JWT.create();

        // 给Token设置一个有效时间
        Calendar calendar = Calendar.getInstance(); // 后去日历类
        if (time == null) {
            calendar.add(Calendar.MINUTE, 30); // 超时时间是30分钟
        } else {
            calendar.add(Calendar.MINUTE, time); // 超时时间是30分钟
        }

        // 设置负载(用户的数据可以放在负载)【第二部分】
        Set<Map.Entry<String, String>> entries = paylaod.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }

        String token = builder
                .withExpiresAt(calendar.getTime()) // 过期时间
                .sign(Algorithm.HMAC256(ShopConstants.JWT_SIGN));

        return token;
    }

    //校验token
    public static DecodedJWT verify(String token) throws Exception {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(ShopConstants.JWT_SIGN)).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        return verify;
    }

    // 校验token是否合法
    public static DecodedJWT checkToken(String token) throws  ShopException {
        try {
           return verify(token);
        } catch (SignatureVerificationException e) {
            log.debug("签名不一致的异常", e);
            throw new ShopException(1007, "签名不一致的异常");
        } catch (TokenExpiredException e) {
            log.debug("令牌过期的异常", e);
            throw new ShopException(1008, "令牌过期的异常");
        } catch (Exception e) {
            log.debug("token认证失败", e);
            throw new ShopException(1009, "token认证失败");
        }
    }

    // 获取token中的数据
    public static String  getWithClaim(String token, String key) {
        try {
            DecodedJWT verify = verify(token);
            return verify.getClaim(key).asString();
        } catch (Exception e) {
//            e.printStackTrace();
            log.debug("签名异常", e);
        }
        return null;
    }
}
