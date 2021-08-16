package com.qf.aop.annocation;

import java.lang.annotation.*;

@Documented // 生成API文档
@Target({ElementType.METHOD}) // 这注解可以添加到那些个元素上
@Retention(RetentionPolicy.RUNTIME) // 注解的作用域
public @interface LoginUser {
}
