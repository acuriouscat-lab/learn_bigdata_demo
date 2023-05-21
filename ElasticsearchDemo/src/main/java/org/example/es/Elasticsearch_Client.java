package org.example.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class Elasticsearch_Client {
    public static void main(String[] args) throws IOException {
        RestClientBuilder client = RestClient.builder(new HttpHost("localhost", 9200, "http"));

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(client);

        restHighLevelClient.close();
    }
}
