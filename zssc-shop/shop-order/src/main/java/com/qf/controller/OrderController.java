package com.qf.controller;

import com.qf.aop.annocation.LoginUser;
import com.qf.domain.CarGoods;
import com.qf.entity.*;
import com.qf.feign.api.IAddressService;
import com.qf.feign.api.ICarService;
import com.qf.service.IOrderService;
import com.qf.utils.OrderUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orderController")
public class OrderController {

    @Autowired
    private IAddressService addressService;

    @Autowired
    private ICarService carService;

    @Autowired
    private IOrderService orderService;


    @RequestMapping("/orderConfirm")
    @LoginUser
    public String orderConfirm(User user, ModelMap modelMap) {

        // 1.判断用户是否登录
        if (user.getId() == null) {
            return "redirect:http://localhost/shop-sso/toLoginUserPage";
        }

        // 2.在根据用户的id查询用户的地址
        List<Address> addresses = addressService.getAddressListByUid(user.getId());


        // 查询用户购物车的信息
        List<CarGoods> carGoodsList = carService.getCarGoodsList(user.getId());

        // 计算总价
        BigDecimal totalPrice = new BigDecimal(0.0);
        for (CarGoods carGoods : carGoodsList) {

            Integer count = carGoods.getCount(); // 商品的数量
            BigDecimal gprice = carGoods.getGprice(); // 商品的价格

            // 计算商品的小计
            BigDecimal multiply = gprice.multiply(BigDecimal.valueOf(count));

            totalPrice = totalPrice.add(multiply);
        }


        // 3. 把地址信息放入到域对象中
        modelMap.put("addressList", addresses);
        modelMap.put("carGoodsList", carGoodsList);
        modelMap.put("totalPrice", totalPrice);

        // 4.跳转到订单的确认页面
        return "orderConfirm";
    }

    @RequestMapping("/createOrder")
    @LoginUser
    public String createOrder(User user, Integer addressId) {
        System.out.println("userId = [" + user.getId() + "]");

        // 1.判断用户是否登录
        if (user.getId() == null) {
//            return ResultEntity.error("你还没有登录，请先登录。");
            throw new ShopException(2003, "你还没有登录，请先登录");
        }

        // 2.生成订单
        String orderId = orderService.createOrder(user.getId(), addressId);

        //  清空购物车
//        carService.clearUserCar(user.getId());

        // 3、跳转到第三方支付平台
        // 跳转到支付模块
//        return ResultEntity.success(orderId);
          return "redirect:http://localhost/shop-pay/aliPayController/pay?orderId=" + orderId;
    }

    @RequestMapping("/getOrderById/{id}")
    @ResponseBody
    public Order getOrderById(@PathVariable String id) {

        // 1.根据订单id获取用户id后四位 20210226xxxx11113333
        String userIdSuffix = id.substring(8, 8 + 4);

        // 2.根据用户id后四位查询订单对象
        Order order = orderService.getOrderById(Integer.parseInt(userIdSuffix), id);

        // 3.返回
        return order;
    }

    @RequestMapping("/updateOrderStatus")
    @ResponseBody
    public Integer updateOrderStatus(@RequestBody Map<String, String> map) {
        // 4.修改的订单的状态
        return orderService.updateOrderStatus(map);
    }

    @RequestMapping("/getOrderListByUserId")
    @LoginUser
    public String getOrderListByUserId(User user, ModelMap modelMap) {

        // 判断用户是否已经登录
        if (user.getId() == null) {
            throw new ShopException(3001, "请先登录。。。");
        }

        // 1.根据用户id查询订单
        List<Order> orderList = orderService.getOrderListByUserId(user.getId());

        // 2.把集合放入到域中
        modelMap.put("orderList", orderList);

        // 3.跳转到订单的显示页面
        return "userOrderList";
    }
}
