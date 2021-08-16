package com.qf.shopsso;

import org.apache.commons.lang.RandomStringUtils;
import org.bouncycastle.jcajce.provider.keystore.BC;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class ShopSsoApplicationTests {

    @Test
    void contextLoads() {

//        for (int i = 0; i < 10; i++) {
//            String random = RandomStringUtils.random(6, false, true);
//            System.out.println(random);
//        }


        // 明文
        String password = "123456";

        // 加密
        String gensalt = BCrypt.gensalt();
        System.out.println(gensalt);

        String hashpw = BCrypt.hashpw(password, gensalt);
        System.out.println(hashpw);

        String newPassword = "123456";

        boolean checkpw = BCrypt.checkpw(newPassword, hashpw);
        System.out.println(checkpw);

    }


}
