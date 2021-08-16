package com.qf.handler;

import com.qf.entity.ResultEntity;
import com.qf.entity.ShopException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice // 表示这是一个全局的异常
@Slf4j
public class ShopExceptionHandler {

    // 处理系统异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultEntity systemException(Exception e){

        // 1.记录异常信息
        log.error("系统异常",e);

        return ResultEntity.error("系统正在维护，请联系管理员。。。");
    }

    // 处理业务异常
    @ExceptionHandler(ShopException.class)
    @ResponseBody
    public ResultEntity businessException(ShopException e){

        // 1.记录异常信息
        log.error("业务异常",e);

        return ResultEntity.error(e.getMsg());
    }


}
