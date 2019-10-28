package cn.iszh.estools.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: EsClient
 * @packageName: cn.iszh.estools.config
 * @description: ES客户端Bean配置文件
 * @date: 2019-09-12 9:29
 **/
@Configuration
@Order(1)
public class EsClient {
    private final static Logger log = LoggerFactory.getLogger(EsClient.class);

    /**
     * 使用冒号隔开ip和端口 10.0.0.1:9200,10.2.2.123:9200
     * 自动注入的es服务器集群地址
     */
    @Value("${elasticSearch.ip}")
    String[] ipAddress;

    /**
     * 自定义IP地址格式
     */
    private static final int ADDRESS_LENGTH = 2;
    /**
     * 使用的传输协议
     */
    private static final String HTTP_SCHEME = "http";
    /**
     * restClient 启动的线程数
     */
    private static final int IO_THREAD_COUNT = 100;

    private static final int CONNECT_TIMEOUT = 100000;

    private static final int SOCKET_TIMEOUT = 300000;

    private static final int CONNECTION_REQUEST_TIMEOUT = 100000;

    private static final int SO_TIMEOUT = 100000;

    @Bean(name = "restClient")
    public RestClient restClient() {
        return getRestClient().build();
    }

    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient() {
        return new RestHighLevelClient(getRestClient());
    }

    private RestClientBuilder getRestClient() {
        HttpHost[] hosts = new HttpHost[ipAddress.length];
        for (int i = 0; i < ipAddress.length; i++) {
            hosts[i] = makeHttpHost(ipAddress[i]);
        }
        RestClientBuilder builder = RestClient.builder(hosts);

        builder.setRequestConfigCallback((RequestConfig.Builder requestConfigBuilder) -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT);
            return requestConfigBuilder;
        });
        builder.setHttpClientConfigCallback((HttpAsyncClientBuilder httpClientBuilder) -> {
            httpClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(IO_THREAD_COUNT)// 线程数配置
                    .setConnectTimeout(CONNECT_TIMEOUT).setSoTimeout(SO_TIMEOUT).build());
            return httpClientBuilder;
        });
        return builder;
    }

    /**
     * 将特定格式的字符串转换成httphost对象返回
     *
     * @param s
     * @return
     */
    private HttpHost makeHttpHost(String s) {
        String[] address = s.split(":");
        if (address.length == ADDRESS_LENGTH) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, HTTP_SCHEME);
        } else {
            throw new RuntimeException("ES集群地址配置文件解析异常！");
        }
    }
}
