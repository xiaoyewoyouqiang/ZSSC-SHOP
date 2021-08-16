package com.qf.shopemail;

import com.qf.entity.Email;
import com.qf.service.IEmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
class ShopEmailApplicationTests {

    @Autowired
    private IEmailService emailService;

    @Test
    void contextLoads() throws MessagingException {

        Email email = new Email();
        email.setTitle("2008生成新用户在注册");
        email.setContent("验证码为:123123,<a href='http://www.baidu.com'>百度</a>");
        email.setToUser("854569279@qq.com");
        email.setCcUser("1151237414@qq.com");
        emailService.sendEmail(email);
    }

}
