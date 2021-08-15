package mashup.spring.elegant.search.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class HealthCheckController (
    @Value("\${server.echo}")
    private val echo : String,
){

    @Value("\${es.url}")
    private val esUrl = "localhost"

    @GetMapping("")
    fun healthCheck() = echo

    @GetMapping("/es")
    fun esHealth(): ResponseEntity<String> {
        val restTemplate: RestTemplate = RestTemplate()

        return restTemplate.getForEntity(esUrl, String::class.javaObjectType)
    }
}