package org.example.es.index;

import org.apache.http.HttpHost;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;

public class Elasticsearch_Index_Create {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 创建索引 -- 索引客户端
        IndicesClient indices = esClient.indices();

        // 创建索引请求
        CreateIndexRequest esRequest = new CreateIndexRequest("es_test");

        CreateIndexResponse response = indices.create(esRequest, RequestOptions.DEFAULT);

        System.out.println(response.isAcknowledged());

        esClient.close();
    }
}
