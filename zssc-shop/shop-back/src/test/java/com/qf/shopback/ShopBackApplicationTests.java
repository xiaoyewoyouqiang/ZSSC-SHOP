package com.qf.shopback;

import com.qf.feign.api.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j // 实例化一个log对象
class ShopBackApplicationTests {

    @Test
    void contextLoads() {
        String name = "admin";
        String pw = "123";

        log.info("名字:{},年龄:{}", name, pw);

    }

}
