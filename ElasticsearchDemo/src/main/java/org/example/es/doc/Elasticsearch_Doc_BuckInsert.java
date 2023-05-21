package org.example.es.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class Elasticsearch_Doc_BuckInsert {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        BulkRequest request = new BulkRequest();

        request.add(new IndexRequest().index("es_test").id("1001").source(XContentType.JSON, "name", "zhangsan", "age",30,"sex","男"));
        request.add(new IndexRequest().index("es_test").id("1002").source(XContentType.JSON, "name", "lisi", "age",30,"sex","女"));
        request.add(new IndexRequest().index("es_test").id("1003").source(XContentType.JSON, "name", "wangwu", "age",40,"sex","男"));
        request.add(new IndexRequest().index("es_test").id("1004").source(XContentType.JSON, "name", "wangwu1", "age",40,"sex","女"));
        request.add(new IndexRequest().index("es_test").id("1005").source(XContentType.JSON, "name", "wangwu2", "age",50,"sex","男"));
        request.add(new IndexRequest().index("es_test").id("1006").source(XContentType.JSON, "name", "wangwu3", "age",50,"sex","男"));
        request.add(new IndexRequest().index("es_test").id("1007").source(XContentType.JSON, "name", "wangwu44", "age",60,"sex","男"));
        request.add(new IndexRequest().index("es_test").id("1008").source(XContentType.JSON, "name", "wangwu555", "age",60,"sex","男"));


        BulkResponse responses = esClient.bulk(request, RequestOptions.DEFAULT);

        //打印结果信息
        System.out.println("took:" + responses.getTook());
        System.out.println("items:" + responses.getItems());


        esClient.close();
    }
}
