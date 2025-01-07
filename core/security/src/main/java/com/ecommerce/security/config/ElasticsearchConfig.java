//package com.ecommerce.security.config;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import lombok.Value;
//import org.apache.http.HttpHost;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestClient;
//
//@Configuration
//public class ElasticsearchConfig {
//
//    @Value("${spring.elasticsearch.uris}")
//    private String elasticsearchUris;
//
//    @Bean
//    public ElasticsearchClient client() {
//        RestClient.Builder builder = RestClient.builder(new HttpHost(elasticsearchUris.split(":")[0], Integer.parseInt(elasticsearchUris.split(":")[1]), "http"));
//        return new ElasticsearchClient(builder);
//    }
//
//    @Bean
//    public ElasticsearchRestTemplate elasticsearchRestTemplate() {
//        return new ElasticsearchRestTemplate(client());
//    }
//}
//
