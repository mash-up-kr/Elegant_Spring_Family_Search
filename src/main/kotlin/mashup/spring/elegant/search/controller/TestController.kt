package mashup.spring.elegant.search.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/health")
@RestController
class TestController (
    @Value("\${server.echo}")
    private val echo : String
){
    @GetMapping
    fun health() = echo


}