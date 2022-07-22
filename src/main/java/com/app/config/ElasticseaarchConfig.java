package com.app.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticseaarchConfig {

    private static String schema = "http"; // 使用的协议
    private static List<HttpHost> hostList = new ArrayList<>();
    private static String hosts = "127.0.0.1"; // 集群地址，多个用,隔开
    private static int port = 9200; // 使用的端口号

    private static int connectTimeOut = 1000; // 连接超时时间
    private static int socketTimeOut = 30000; // 连接超时时间
    private static int connectionRequestTimeOut = 500; // 获取连接的超时时间

    private static int maxConnectNum = 100; // 最大连接数
    private static int maxConnectPerRoute = 100; // 最大路由连接数

    static {
        String[] hostStrs = hosts.split(",");
        for (String host : hostStrs) {
            hostList.add(new HttpHost(host, port, schema));
        }
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        // 主机ip和端口号以及协议
        HttpHost[] httpHosts = hostList.stream().toArray(HttpHost[]::new);
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHosts));
        return restHighLevelClient;
    }

}