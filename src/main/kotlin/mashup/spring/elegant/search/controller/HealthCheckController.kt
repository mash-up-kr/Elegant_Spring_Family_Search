package mashup.spring.elegant.search.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController (
    @Value("\${server.echo}")
    private val echo : String
){
    @GetMapping("")
    fun healthCheck() = echo
}