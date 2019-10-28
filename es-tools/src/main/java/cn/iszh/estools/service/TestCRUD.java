package cn.iszh.estools.service;

import cn.iszh.estools.config.EsIndexEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: TestCRUD
 * @packageName: cn.iszh.estools.service
 * @description: 测试ES_CRUD操作
 * @date: 2019-09-12 14:10
 **/
@Service("testCrud")
public class TestCRUD {
    @Resource
    private RestHighLevelClient highLevelClient;

    public void searchData() throws IOException {
        // 请求内容
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(0).size(10).query(QueryBuilders.matchAllQuery());
        // 请求体
        SearchRequest searchRequest = new SearchRequest(EsIndexEnum.INDEX_STUDENT);
        searchRequest.source(searchSourceBuilder);
        // 发起请求
        SearchResponse searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 解析数据
        SearchHit[] list = searchResponse.getHits().getHits();
        Arrays.stream(list).forEach(item -> {
            System.out.println(item.getSourceAsMap().toString());
        });
    }
}
