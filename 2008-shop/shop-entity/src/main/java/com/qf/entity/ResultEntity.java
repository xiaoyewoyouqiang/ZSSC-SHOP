package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultEntity<T> {

    private static final String SUECCSS = "success";
    private static final String ERROR = "error";

    private String status; // 表示这次请求是成功还是失败

    private String msg; // 失败的原因

    private T data; // 返回的数据


    // 只返回成功的状态
    public static <T> ResultEntity<T> success() {
        return new ResultEntity(SUECCSS, null, null);
    }

    // 成功有数据
    public static <T> ResultEntity<T> success(T t) {
        return new ResultEntity(SUECCSS, null, t);
    }

    public static <T> ResultEntity<T> error(String msg) {
        return new ResultEntity(ERROR, msg, null);
    }

    public static ResultEntity responseClinet(boolean flag){
        if (flag) {
            return ResultEntity.success();
        } else {
            return ResultEntity.error("添加用户失败");
        }
    }

}
