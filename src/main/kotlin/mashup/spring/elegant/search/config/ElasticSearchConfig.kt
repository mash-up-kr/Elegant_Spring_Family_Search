package mashup.spring.elegant.search.config


import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class ElasticSearchConfig (
    @Value("\${es.url}")
    private val hostName: String,
    @Value("\${es.port}")
    private val port: Int
){
    // Elastic Search 와 통신하기 위한 rest client
    @Bean
    fun restHighLevelClient() : RestHighLevelClient =
        RestHighLevelClient(RestClient
                                .builder(
                                    HttpHost(hostName, port, "http")))

}