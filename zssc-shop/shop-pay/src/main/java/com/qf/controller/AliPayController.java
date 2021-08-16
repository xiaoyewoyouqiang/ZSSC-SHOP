package com.qf.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Order;
import com.qf.feign.api.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/aliPayController")
public class AliPayController {

    private String appId = "2021000117699195"; // 获取沙箱应用appid：https://openhome.alipay.com/platform/appDaily.htm?tab=info

    private String serverUrl = "https://openapi.alipaydev.com/gateway.do";

//    下载工具上传私钥和公钥
    private String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCpyzTQZo3SPbhVhOFYE+i9Shu0VXbK9tuoEBiPN/L9jt2ISRRCkLAUq4WcahqXl24moyDkXMr3+iF4CW+mYaYJhlUK0VqNUyhl/g3NT8EXsLzFaMsQ1wrqj1oDWLM0vRp3+o0XP9Ju39yqhexYCmfg2oZjkK+23BEhJx/wTPXOg/8/F4r/zEbf6gBNbvu0WJ2q5g2MfGuSHBhiPov0Alh2G8J6QMdObEdK77eiMbhBQ6iIzpYPDSUFL5RO1Z/b4xzUxFY0bJwISExZqmemwLom5imBoBtGMEeF2pVYjJWRvJd/aha0r9h1lwUwJdzuHOCsLgclw2uyYbimt6SvNpXzAgMBAAECggEBAJVHnjh3YcrtDqrJeCGq51280Ry8YwPRA5AADsekBhPdTnHq5lAby2Nw5bh+JMEQlvyLQXjlCGH63QAfM7VRdtw66XvoUjecXBFHyrSuoPKGJYFeP+KfXe6ZeCcnDe+ZLrM7al59czKdyPF3azKAe5HaLntNEt+vHbRLJK5ty1LzlZQzQaAXWLos6bubTdgP4UaeFZyDgIwHsEHw1DAutWKG0uBFUAA3BG6zrj2O0HVuGhbKQy+hTYBgNY5/1LARJ7XprMxRGZRYl8NxrfjhcWeVnXO09TSnWOkRu19UoH1GfYaumXnMa2Obd5xKAqfQzGGQVfjYe1wzVSJrXiVZQUECgYEA0M2UZGoFfn8lmkLgrJn+qE7+RXABURN+eiHEHfHNP5TOUC+2vnB940ZlIObkbIE8PkVEBBLfiI5iZlg1sMDkS/KIkuNN/SkddfmlcYx6gVyeeNBLTstbw3phLTcWFfeF/KWGk//ZFvLGH2D8f3gqBjv5J//xK4/jqlkSgBnLblMCgYEA0CxZZFYjzBuXcwskZ3ZQOzNO7Kbv2fQZr+RjY0rHjG+9MuuolnnDoOs889MG9qXxFjmxr7Ri53VirVzWk86gLnYhLpDj4ECtN1Rgdm+C0X/p0r8n7fNTYe8pJZaDXtzOBJ6y4+4qx9r/FyLh35QecCJM1tNChO1CDCmy/BZRBeECgYAiMHdK1d1ecB1NHARD3tdG4eBaqFNe3i1IfDzrlktATPJXSOjIWA0U6NLoR0ZBygt+OsM/U4PMjVvpXCo2be8zzFu2iYecm13akm0XoaPxiKPeLmUWWtAEuwYsbCeuoyAKVWQq2arHSitMugm+KYPgD3Plq8/mjSH/I6SW2yJnRwKBgDGbL94SDMu5k3htokopTmsdCeSozyUP3wMkGG7zxeN4SQyWTYE2GOECzW6IzfL+jhmuu+HTyXHAbjhDlDp6lX8c1UFkc3jf0KcLq0Ttw62qnoyC0sAIKMmAlTyIxltXoJxHOPfJrFYdoIXh4RNHF42/DrJuez5zzzZYxXb+1mXhAoGBAJve2Hf8qNWiOeYd86Buge6MAvD/7Hibb4nMfpEpCSCYtcJF/5D04tDONumF2h9VoEEXTnFFRNB+OZDC++GTwtJRWzbqdflFGyWYTpsYlPoFx7MwwwAAhL6himoH7pMw3gyQCNb+W1PUY7P5gnjpVxYU7e+5tkz9WlFMNc0Xk55S";

    private String FORMAT = "JSON";

    private String CHARSET = "utf8";

    private String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqcs00GaN0j24VYThWBPovUobtFV2yvbbqBAYjzfy/Y7diEkUQpCwFKuFnGoal5duJqMg5FzK9/oheAlvpmGmCYZVCtFajVMoZf4NzU/BF7C8xWjLENcK6o9aA1izNL0ad/qNFz/Sbt/cqoXsWApn4NqGY5CvttwRIScf8Ez1zoP/PxeK/8xG3+oATW77tFidquYNjHxrkhwYYj6L9AJYdhvCekDHTmxHSu+3ojG4QUOoiM6WDw0lBS+UTtWf2+Mc1MRWNGycCEhMWapnpsC6JuYpgaAbRjBHhdqVWIyVkbyXf2oWtK/YdZcFMCXc7hzgrC4HJcNrsmG4prekrzaV8wIDAQAB";

    private String SIGN_TYPE = "RSA2";

    @Autowired
    private IOrderService orderService;

    // 在这个方法里面对接支付宝
    @RequestMapping("/pay")
    public void pay(String orderId, HttpServletResponse httpResponse) throws Exception {
        System.out.println("AliPayController.pay");

        // 先根据订单id查询订单对象
        Order order = orderService.getOrderById(orderId);

        // 1.封装请求支付宝的参数
        AlipayClient alipayClient = new DefaultAlipayClient(
                serverUrl, // 支付宝的网关地址,注意我们这里dev测试
                appId, // 商家的appid
                APP_PRIVATE_KEY, // 商家私钥
                FORMAT, // 数据传递的格式
                CHARSET, // 请求数据的编码
                ALIPAY_PUBLIC_KEY, // 支付宝的公钥
                SIGN_TYPE // 签名的类型
        );  //获得初始化的AlipayClient

        // 2.创建一个请求对象
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); //创建API对应的request

        // 3.设置同步异步跳转的地址
        alipayRequest.setReturnUrl("http://localhost/shop-home"); // 支付成功后跳转baidu，同步跳转

        alipayRequest.setNotifyUrl("http://jztbbt.natappfree.cc/shop-pay/aliPayController/updateOrderStatus"); //异步通知商家的订单交易的情况

        // 4、订单的信息
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":" + orderId + "," + // 商家生成的订单号
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + order.getTotalPrice() + "," + // 订单的金额
                "    \"subject\":\"2008-shop\"," + // 订单的标题
                "    \"body\":\"具体商品的信息\"," + // 订单的描述
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }" +
                "  }"); //填充业务参数


        String form = "";
        try {
            // 获取一个登陆页面
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
            System.out.println(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        // 把登录页面响应给用户
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form); //直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

    }

    // 支付宝调用这个接口会传递很多参数
    @RequestMapping("/updateOrderStatus")
    public void updateOrderStatus(HttpServletRequest request) throws Exception {

        // 先做验签

        // 1.先获取异步接收的参数
        Map<String, String[]> parameterMap = request.getParameterMap();

        // 2.准备一个新的map
        Map<String, String> paramsMap = new HashMap<>(); // ...  //将异步通知中收到的所有参数都存放到 map 中

        // 3.把接收到的所有参数重新放入到paramsMap中
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String key = entry.getKey();
            String[] value = entry.getValue();

            // 设置到新的map中
            paramsMap.put(key, value[0]);
        }

        // 4.完成验签
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);  //调用SDK验证签名
        if (signVerified) {
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            System.out.println("这个请求是支付宝发送的");

            // 修改的订单的状态
            String orderId = paramsMap.get("out_trade_no");
            String orderStatus = paramsMap.get("trade_status");
            if ("TRADE_SUCCESS".equals(orderStatus)) { // 订单支付成功
                // 修改订单的状态为已支付
                Map<String, String> map = new HashMap<>();
                map.put("orderId", orderId);
                map.put("status", "2");
                int update = orderService.updateOrderStatus(map);
            }

        } else {
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            System.out.println("这个请求是非法的");
        }

    }
}
