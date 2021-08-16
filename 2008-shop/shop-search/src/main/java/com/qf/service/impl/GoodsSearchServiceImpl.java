package com.qf.service.impl;

import com.google.gson.Gson;
import com.qf.entity.Goods;
import com.qf.service.IGoodsSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class GoodsSearchServiceImpl implements IGoodsSearchService {

    @Autowired
    private RestHighLevelClient client;

    @Value("${es.index}")
    private String shopIndex;

    @Value("${es.type}")
    private String shopType;


    // 添加数据到ES中
    @Override
    public void addGoods(Goods goods) throws IOException {

        // 1、创建一个索引
        IndexRequest indexRequest = new IndexRequest(shopIndex, shopType);

        // 2.把对象转成JSON字符串
        String json = new Gson().toJson(goods);

        // 3.设置发送给es数据的类型
        indexRequest.source(json, XContentType.JSON);

        // 4.发送给ES
        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
    }

    // 商品名字和商品的描述都要匹配关键字
    @Override
    public List<Goods> searchGoodsList(String keyword, Integer psort) throws Exception {

        // 1.创建一个查询的请求对象
        SearchRequest request = new SearchRequest(shopIndex); // 设置索引
        request.types(shopType); // 设置type

        // 2.设置条件
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 只要是gname或者gdesc中包含关键字就都可以,设置查询条件
        if (StringUtils.isEmpty(keyword)) {
            builder.query(QueryBuilders.matchAllQuery()); // 没有关键字就查询全部
        } else {
            builder.query(QueryBuilders.multiMatchQuery(keyword, "gname", "gdesc"));
        }

        // 设置高亮的属性
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("gname", 30);
        highlightBuilder.preTags("<font color ='red'>");
        highlightBuilder.postTags("</font>");

        // 设置排序规则(默认值，价格(1)，名字(2))
        if (psort != null) {
            if("1".equals(psort.toString())){
                builder.sort("gprice", SortOrder.DESC);
            }else if("2".equals(psort.toString())){
                builder.sort("id", SortOrder.DESC);
            }
        }

        // /把高亮的属性设置builder
        builder.highlighter(highlightBuilder);

        // 3.把查询条件设置到req中
        request.source(builder);

        // 4.发送请求
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);

        // 5.准备一个集合
        List<Goods> goodsList = new ArrayList<>();

        // 6、获取查询出来的结果集
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {

            // 获取商品的信息
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
           
            // 创建一个商品对象
            Goods goods = new Goods();

            // 把map中的数据拷贝到goods中
            org.apache.commons.beanutils.BeanUtils.populate(goods, sourceAsMap);

            // 获取高亮的字段，其中有些数据是没有高高亮字段的
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField gnameHingh = highlightFields.get("gname");
            if (gnameHingh != null) {
                // 说明当前这条数据有高亮字段
                goods.setGname(gnameHingh.getFragments()[0].toString());
            }
            goodsList.add(goods);
        }

        return goodsList;
    }
}
