package org.example.es.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class Elasticsearch_Doc_Update {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        UpdateRequest request = new UpdateRequest();

        request.index("es_test").id("1001");

//        User user = new User("zhangsan", 155, "nan");
//
//        String userJson = new ObjectMapper().writeValueAsString(user);

        request.doc(XContentType.JSON,"sex","nv");

        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);

        System.out.println(response.getIndex());
        System.out.println(response.getId());
        System.out.println(response.getResult());


        esClient.close();
    }
}
