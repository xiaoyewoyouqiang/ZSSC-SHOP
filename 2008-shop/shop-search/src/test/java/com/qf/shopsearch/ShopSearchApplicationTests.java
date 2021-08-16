package com.qf.shopsearch;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.gson.Gson;
import com.qf.entity.Goods;
import com.qf.entity.GoodsPic;
import com.qf.feign.api.IGoodsService;
import com.qf.service.IGoodsSearchService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ShopSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    void testCreateIndex() throws Exception {

        // 1.创建一个请求
        CreateIndexRequest indexRequest = new CreateIndexRequest();
        indexRequest.index("shop-index"); // 设置一个索引的名字

        // 2.创建一个Settings
        Settings.Builder settings = Settings.builder();
        settings.put("number_of_shards", 3); // 3个分片
        settings.put("number_of_replicas", 1); // 1 个备份

        // 3.把settings设置给rquest对象
        indexRequest.settings(settings);

        // 4.创建mappings
        XContentBuilder mappings = JsonXContent.contentBuilder();
        mappings.startObject()
                .startObject("properties")
                .startObject("id")
                .field("type", "integer")
                .endObject()
                .startObject("gname")
                .field("type", "text") // text,xworkd
                .field("analyzer", "ik_max_word") // text,xworkd
                .endObject()
                .startObject("gdesc")
                .field("type", "text")
                .field("analyzer", "ik_max_word") // text,xworkd
                .endObject()
                .startObject("gprice")
                .field("type", "double")
                .endObject()
                .startObject("gtype")
                .field("type", "integer")
                .endObject()
                .startObject("gpng")
                .field("type", "keyword")
                .endObject()
                .endObject()
                .endObject();

        // 5.把mappings设置给index
        indexRequest.mapping("shop-type", mappings);


        CreateIndexResponse response = client.indices().create(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.toString());

    }


    @Test
    public void testAddGoods() throws Exception {

        // 1.准备数据
        Goods goods = new Goods();
        goods.setGname("华为手机");
        goods.setGdesc("手机 huawei mate30");
        goods.setGprice(BigDecimal.valueOf(5000.0));
        goods.setGtype(2);
        goods.setId(10);
        goods.setTempPng("a.png|b.png|c.png");

        // 添加到ES
        IndexRequest indexRequest = new IndexRequest("shop-index", "shop-type");


        // 把对象转成json字符串
        String json = new Gson().toJson(goods);

        indexRequest.source(json, XContentType.JSON);

        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);

        DocWriteResponse.Result result = index.getResult();
        System.out.println(result.toString());


        System.out.println(index);

    }


    @Autowired
    private IGoodsSearchService goodsSearchService;

    @Autowired
    private IGoodsService goodsService;

    @Test
    public void syncGoodsTOEs() throws Exception {

        Page<Goods> goodsPage = goodsService.getGoodsPage(new Page<>());

        List<Goods> records = goodsPage.getRecords();

        for (Goods goods : records) {
            List<GoodsPic> goodsPicList = goods.getGoodsPicList();
            for (GoodsPic goodsPic : goodsPicList) {
                String png = goodsPic.getPng();
                String tempPng = goods.getTempPng();
                if (StringUtils.isEmpty(tempPng)) {
                    goods.setTempPng(png);
                } else {
                    goods.setTempPng(goods.getTempPng() + "|" + png);
                }
            }
            System.out.println(goods);
            goodsSearchService.addGoods(goods);
        }
    }

    @Test
    public void testClearEs()throws  Exception{

        DeleteByQueryRequest request = new DeleteByQueryRequest("shop-index");
        request.setDocTypes("shop-type");
        request.setQuery(QueryBuilders.matchAllQuery());

        BulkByScrollResponse response= client.deleteByQuery(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

}
