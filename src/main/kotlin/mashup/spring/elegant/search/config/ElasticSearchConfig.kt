package mashup.spring.elegant.search.config


import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate


@Configuration
class ElasticSearchConfig (
    @Value("\${es.url}")
    private val HOST: String,
    @Value("\${es.port}")
    private val PORT: Int,
    @Value("\${es.scheme}")
    private val SCHEME: String,
    @Value("\${es.username}")
    private val USERNAME: String,
    @Value("\${es.password}")
    private val PASSWORD: String
) {
//    private val USERNAME: String = getEnvironment("ES_USERNAME")
//    private val PASSWORD: String = getEnvironment("ES_PASSWORD")

    @Bean
    fun elasticsearchTemplate() = ElasticsearchRestTemplate(restHighLevelClient())

    private fun restHighLevelClient() = RestHighLevelClient(getRestClientBuilder())

    private fun getRestClientBuilder() =
        RestClient.builder(HttpHost(HOST, PORT, SCHEME))
            .setHttpClientConfigCallback {
                    httpClientBuilder -> httpClientBuilder
                .setDefaultCredentialsProvider(getCredentialProvider())
            }.apply { println("USERNAME : " + USERNAME) }

    private fun getCredentialProvider(): BasicCredentialsProvider {
        val credentialsProvider = BasicCredentialsProvider()
        credentialsProvider.setCredentials(
            AuthScope.ANY,
            UsernamePasswordCredentials(USERNAME, PASSWORD)
        )
        return credentialsProvider
    }
}