package com.dedu.es.controller;

import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.elasticsearch.client.indices.CreateIndexRequest;

import java.io.IOException;

@RestController()
@RequestMapping("/api")
public class TestController {

    @Autowired
    private RestHighLevelClient client;

    @RequestMapping("")
    public String createEsIndex() {
        // 1.创建索引
        CreateIndexRequest request = new CreateIndexRequest("java_client_index");
        IndicesClient indices = client.indices();
        try {
            CreateIndexResponse createIndexResponse = indices.create(request, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse.index());
        } catch (IOException e) {
            System.out.println("创建索引异常");
        }
        return "SUCCESS";
    }
}
