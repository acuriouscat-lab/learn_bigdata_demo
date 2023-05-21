package org.example.es.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class Elasticsearch_Doc_BulkDelete {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        BulkRequest request = new BulkRequest();

        request.add(new DeleteRequest("es_test", "10011"))
                .add(new DeleteRequest("es_test", "10021"));

        BulkResponse responses = esClient.bulk(request, RequestOptions.DEFAULT);

        //打印结果信息
        System.out.println("took:" + responses.getTook());
        System.out.println("items:" + responses.getItems());


        esClient.close();
    }
}
