package org.example.es.index;

import org.apache.http.HttpHost;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.util.Arrays;

public class Elasticsearch_Index_Search {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 创建索引 -- 索引客户端
        IndicesClient indices = esClient.indices();

        // 创建索引请求
        CreateIndexRequest esRequest = new CreateIndexRequest("es_test");

        GetIndexResponse response = indices.get(new GetIndexRequest("es_test"),RequestOptions.DEFAULT);

        System.out.println(Arrays.toString(response.getIndices()));

        esClient.close();
    }
}
