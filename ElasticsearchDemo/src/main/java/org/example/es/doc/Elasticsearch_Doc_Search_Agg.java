package org.example.es.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class Elasticsearch_Doc_Search_Agg {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        SearchRequest request = new SearchRequest();

        request.indices("es_test");


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(AggregationBuilders.max("maxAge1").field("age"));

        request.source(sourceBuilder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("agg:" + response.getAggregations().toString());
        System.out.println(response);

        esClient.close();
    }
}
