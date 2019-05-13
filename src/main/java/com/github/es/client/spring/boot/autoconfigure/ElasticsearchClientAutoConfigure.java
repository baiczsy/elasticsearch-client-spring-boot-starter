package com.github.es.client.spring.boot.autoconfigure;

import com.github.elasticsearch.client.connection.ElasticsearchClientFactory;
import com.github.elasticsearch.client.core.RestClientOperations;
import com.github.elasticsearch.client.core.RestClientTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author wangl
 * @date 2019-05-13
 */
@Configuration
@ConditionalOnClass(RestClientOperations.class)
@Import(ElasticsearchClientConfiguration.class)
public class ElasticsearchClientAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    public RestClientTemplate restClientTemplate(ElasticsearchClientFactory factory){
        return new RestClientTemplate(factory);
    }
}
