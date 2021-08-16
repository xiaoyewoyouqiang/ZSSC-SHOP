package com.qf.handler;

import com.qf.entity.SeckillException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // 表示这是一个全局的异常
@Slf4j
public class ShopExceptionHandler {

    // 处理系统异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> systemException(Exception e) {

        // 1.记录异常信息
        log.error("系统异常", e);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "eror");
        map.put("msg", "系统正在维护，请联系管理员。。。");
        return map;
    }

    // 处理业务异常
    @ExceptionHandler(SeckillException.class)
    @ResponseBody
    public Map<String, Object> businessException(SeckillException e) {

        // 1.记录异常信息
        log.error("业务异常", e);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "eror");
        map.put("msg", e.getMsg());

        return map;
    }


}
