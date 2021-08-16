package com.qf.aop.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.qf.aop.annocation.LoginUser;
import com.qf.entity.User;
import com.qf.utils.JWTUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect // 标识这是一个切面
public class LoginUserImpl {

    @Autowired
    private HttpServletRequest request;

    // 在加了loginUser注解的方法之前执行
    @Around("@annotation(loginUser)")
    public Object loginUser(ProceedingJoinPoint point, LoginUser loginUser) throws Exception {
        System.out.println("LoginUserImpl.loginUser");

        User user = null; // 封装的登录的对象

        // 1.获取token
        String token = request.getHeader("Authorization");

        // 如果token请求头中没有获取到，再看看地址栏中是否存在
        if(StringUtils.isEmpty(token)){
            token = request.getParameter("token");
        }

        Object[] args = point.getArgs(); //因为方法有多个形参

        if (!StringUtils.isEmpty(token)) {

            // 2.解析token
            DecodedJWT verify = JWTUtils.verify(token);

            // 3.从token中获取uid
            String id = verify.getClaim("id").asString();

            // 4.把uid封装到User对象中
            user = new User();
            user.setId(Integer.parseInt(id));

            // 5、把user对象传递到目标方法的形参(因为要修改目标方法的参数，所以只能用环绕来完成)
            // 5.1 获取目标方法的形参
//            Object[] args = point.getArgs(); //因为方法有多个形参

            // 2.循环遍历数组，找到user的参数
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null && args[i].getClass() == User.class) {
                    args[i] = user;
                    break; // 替换完成就马上返回
                }
            }
        }

        try {
            // 放行
            return point.proceed(args);// 放行
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
