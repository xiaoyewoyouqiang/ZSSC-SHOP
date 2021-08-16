package com.qf.feign.api;

import com.qf.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("shop-order")
public interface IOrderService {

    @RequestMapping("/orderController/getOrderById/{id}")
    public Order getOrderById(@PathVariable("id") String id);


    @RequestMapping("/orderController/updateOrderStatus")
    Integer updateOrderStatus(@RequestBody Map<String, String> map);
}
