package org.example.es.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.example.es.User;

import java.util.Arrays;

public class Elasticsearch_Doc_Insert {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        IndexRequest request = new IndexRequest();

        request.index("es_test").id("1001");

        User user = new User("zhangsan", 155, "nan");

        String userJson = new ObjectMapper().writeValueAsString(user);

        request.source(userJson, XContentType.JSON);

        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);

        System.out.println(response.getIndex());
        System.out.println(response.getId());
        System.out.println(response.getResult());


        esClient.close();
    }
}
