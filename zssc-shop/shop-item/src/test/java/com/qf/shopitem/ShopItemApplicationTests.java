package com.qf.shopitem;

import com.baomidou.mybatisplus.plugins.Page;
import com.qf.config.RabbitMQConfig;
import com.qf.entity.Goods;
import com.qf.entity.GoodsPic;
import com.qf.entity.User;
import com.qf.feign.api.IGoodsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ShopItemApplicationTests {


    @Autowired
    private Configuration configuration;

    @Test
    void contextLoads() throws Exception {

        User user = new User();
        user.setUsername("qfAdmin");
        user.setSex(1);

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user1 = new User();
            user1.setId(i + 1);
            user1.setUsername("name_" + i);
            user1.setPassword("pw_" + i);
            users.add(user1);
        }

        // 1.准备数据
        Map<String, Object> map = new HashMap<>();
        map.put("username", "张三");
        map.put("age", 8);
        map.put("user", user);
        map.put("userList", users);


        // 2.找到这个模板
        Template template = configuration.getTemplate("demo.ftl");

        // 3、把数据填充到Map中
        template.process(map, new FileWriter("./demo.html"));

        System.out.println("模板生成挖完成。。。。");
    }

    @Test
    void testItem() throws Exception {

        String gpng = "http://192.168.147.101:8080/group1/M00/00/00/wKiTZWAXWnOANDMCAAB37pPjBqw433.jpg|http://192.168.147.101:8080/group1/M00/00/00/wKiTZWAXWnWACctxAAFgG9FAl00359.jpg|http://192.168.147.101:8080/group1/M00/00/00/wKiTZWAXWnmAaqITAAHkYwiA9lY910.jpg";


        Template template = configuration.getTemplate("goodsItemTemplate.ftl");

        Map<String, Object> map = new HashMap<>();
        map.put("gname", "美的(Midea)639升对开门双开门冰箱");
        map.put("gprice", "5499");
        map.put("pngList", gpng.split("\\|"));
        String gid = "10";


        template.process(map, new FileWriter("./" + gid + ".html"));


        System.out.println("模板生成挖完成。。。。");
    }

    @Autowired
    private IGoodsService goodsService;

    @Test
    public void testBatchCreateItem() throws Exception {

        // 1.查询所有的商品
        Page<Goods> goodsPage = goodsService.getGoodsPage(new Page<>());
        List<Goods> records = goodsPage.getRecords();

        Template template = configuration.getTemplate("goodsItemTemplate.ftl");

        for (Goods goods : records) {


            Map<String, Object> map = new HashMap<>();
            map.put("gname", goods.getGname());
            map.put("gprice", goods.getGprice());


            List<GoodsPic> goodsPicList = goods.getGoodsPicList();
            for (GoodsPic goodsPic : goodsPicList) {
                String png = goodsPic.getPng();
                String tempPng = goods.getTempPng();
                if (StringUtils.isEmpty(tempPng)) {
                    goods.setTempPng(png);
                } else {
                    goods.setTempPng(goods.getTempPng() + "|" + png);
                }

                map.put("pngList", goods.getTempPng().split("\\|"));
                System.out.println(goods);
                String path = ShopItemApplicationTests.class.getClassLoader().getResource("static").getPath();
                template.process(map, new FileWriter(path+ File.separator+ goods.getId() + ".html"));
            }
        }
    }

    @Test
    public void testStaticPath(){

//        String path = resource.getPath();
//        System.out.println(path);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testAddMqQueue(){

        for(int i =0;i<10;i++){
//            rabbitTemplate.convertAndSend("mq-demo","","hello_"+i);
            rabbitTemplate.convertAndSend("mq-demo","hello_"+i);
        }
    }

    @Test
    public void testAddMqQueue3(){
        System.out.println("ShopItemApplicationTests.testAddMqQueue3");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
