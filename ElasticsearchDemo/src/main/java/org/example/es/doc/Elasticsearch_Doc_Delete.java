package org.example.es.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class Elasticsearch_Doc_Delete {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        DeleteRequest request = new DeleteRequest();

        request.index("es_test").id("1001");


        DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());
        System.out.println(response.getIndex());
        System.out.println(response.getId());
        System.out.println(response.getResult());


        esClient.close();
    }
}
