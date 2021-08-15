package mashup.spring.elegant.search.controller

import mashup.spring.elegant.search.domain.search.Shop
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController (
    @Value("\${server.echo}")
    private val echo : String,
    private val elasticsearchTemplate : ElasticsearchRestTemplate
){

    @Value("\${es.url}")
    private val esUrl = "localhost"

    @GetMapping("")
    fun healthCheck() = echo

    @GetMapping("/es")
    fun esHealth(): String {

        elasticsearchTemplate.indexOps(Shop::class.javaObjectType).create()

        return "success"
    }
}