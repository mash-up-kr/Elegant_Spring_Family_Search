package mashup.spring.elegant.search.config


import mashup.spring.elegant.search.util.getEnvironment
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate

import org.springframework.data.elasticsearch.client.RestClients

import org.springframework.data.elasticsearch.client.ClientConfiguration




@Configuration
class ElasticSearchConfig (
    @Value("\${es.url}")
    private val hostName: String,
    @Value("\${es.port}")
    private val port: Int
){
    // Elastic Search 와 통신하기 위한 rest client

    private val username: String = getEnvironment("ES_USERNAME")
    private val password: String = getEnvironment("ES_PASSWORD")

    @Bean
    fun client(): RestHighLevelClient? {
        val clientConfiguration: ClientConfiguration = ClientConfiguration.builder()
            .connectedTo("$hostName:$port")
            .withBasicAuth(username, password)
            .build()
        return RestClients.create(clientConfiguration).rest()
    }

    @Bean
    fun elasticsearchTemplate(): ElasticsearchRestTemplate? {
        return ElasticsearchRestTemplate(client())
    }
}