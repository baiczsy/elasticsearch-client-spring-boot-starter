package com.github.es.client.spring.boot.autoconfigure;

import com.github.elasticsearch.client.connection.ElasticsearchClientFactory;
import com.github.elasticsearch.client.connection.RestClientClusterConfiguration;
import com.github.elasticsearch.client.connection.RestClientConfiguration;
import com.github.elasticsearch.client.connection.RestClientPoolConfig;
import com.github.elasticsearch.client.core.ElasticsearchNode;
import com.github.es.client.spring.boot.autoconfigure.properties.ElasticsearchClientProperties;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangl
 * @date 2019-05-13
 */
@Configuration
@ConditionalOnClass({GenericObjectPool.class, RestHighLevelClient.class})
@EnableConfigurationProperties(ElasticsearchClientProperties.class)
public class ElasticsearchClientConfiguration {

    private final ElasticsearchClientProperties properties;

    public ElasticsearchClientConfiguration(ElasticsearchClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ElasticsearchClientFactory elasticsearchClientFactory() {
        RestClientPoolConfig poolConfig = getPoolConfig();
        RestClientConfiguration clientConfiguration = getClientConfiguration();
        ElasticsearchClientFactory factory = new ElasticsearchClientFactory(clientConfiguration, poolConfig);
        setDefaultHeaders(factory);
        return factory;
    }

    private RestClientPoolConfig getPoolConfig() {
        RestClientPoolConfig poolConfig = new RestClientPoolConfig();
        ElasticsearchClientProperties.Pool pool = properties.getPool();
        if(pool != null){
            poolConfig.setMinIdle(pool.getMinIdle());
            poolConfig.setMaxIdle(pool.getMaxIdle());
            poolConfig.setMaxTotal(pool.getMaxActive());
            poolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
        }
        return poolConfig;
    }

    private RestClientConfiguration getClientConfiguration() {
        List<ElasticsearchNode> hosts = createHosts();
        RestClientConfiguration configuration = new RestClientClusterConfiguration(hosts);
        return customizeConfiguration(configuration);
    }

    private List<ElasticsearchNode> createHosts() {
        if(properties.getNodes() == null){
            throw new ElasticsearchException("Undefined elasticsearch nodes.");
        }
        List<ElasticsearchNode> hosts = new ArrayList<>();
        try {
            for (String node : properties.getNodes()) {
                String[] hostPort = node.split(":");
                hosts.add(new ElasticsearchNode(hostPort[0], Integer.parseInt(hostPort[1])));
            }
            return hosts;
        } catch (NumberFormatException e) {
            throw new ElasticsearchException(e);
        }
    }

    private RestClientConfiguration customizeConfiguration(RestClientConfiguration configuration) {
        ElasticsearchClientProperties.Client client = properties.getClient();
        if(client != null){
            configuration.setConnectTimeout(client.getConnectTimeout());
            configuration.setConnectionRequestTimeout(client.getConnectionRequestTimeout());
            configuration.setSocketTimeout(client.getSocketTimeout());
            configuration.setMaxConnTotal(client.getMaxConnTotal());
            configuration.setMaxConnPerRoute(client.getMaxConnPerRoute());
        }
        return configuration;
    }

    private void setDefaultHeaders(ElasticsearchClientFactory factory) {
        ElasticsearchClientProperties.Client client = properties.getClient();
        if(client != null){
            Map<String, String> headers = client.getHeaders();
            if (headers != null) {
                factory.setDefaultHeaders(headers);
            }
        }
    }
}
