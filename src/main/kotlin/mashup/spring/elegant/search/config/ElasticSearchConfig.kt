package mashup.spring.elegant.search.config


import mashup.spring.elegant.search.util.getEnvironment
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
    private val hostName: String,
    @Value("\${es.port}")
    private val port: Int
) {
    private val username: String = getEnvironment("ES_USERNAME")
    private val password: String = getEnvironment("ES_PASSWORD")

    @Bean
    fun elasticsearchTemplate() = ElasticsearchRestTemplate(restHighLevelClient())

    private fun restHighLevelClient() = RestHighLevelClient(getRestClientBuilder())

    private fun getRestClientBuilder() =
        RestClient.builder(HttpHost(hostName, port, "https"))
            .setHttpClientConfigCallback {
                    httpClientBuilder -> httpClientBuilder
                .setDefaultCredentialsProvider(getCredentialProvider())
            }

    private fun getCredentialProvider(): BasicCredentialsProvider {
        val credentialsProvider = BasicCredentialsProvider()
        credentialsProvider.setCredentials(
            AuthScope.ANY,
            UsernamePasswordCredentials(username, password)
        )
        return credentialsProvider
    }
}