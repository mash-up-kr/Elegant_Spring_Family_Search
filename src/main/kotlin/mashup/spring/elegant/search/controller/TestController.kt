package mashup.spring.elegant.search.controller

import mashup.spring.elegant.search.domain.search.SearchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/echo")
@RestController
class TestController (
    private val searchService: SearchService
){
    @GetMapping
    fun echo() = "Hi"

}