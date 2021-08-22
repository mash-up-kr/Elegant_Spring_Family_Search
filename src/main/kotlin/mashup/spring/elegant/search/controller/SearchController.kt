package mashup.spring.elegant.search.controller

import mashup.spring.elegant.search.domain.search.SearchService
import mashup.spring.elegant.search.dto.SearchDto
import mashup.spring.elegant.search.dto.SearchDto.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.Valid

@RequestMapping("/api/v1/search")
@RestController
class SearchController (
    private val searchService: SearchService
){

    @GetMapping("/keyword")
    fun searchByKeyword(@Valid dto : SearchDto.Request) : ResponseEntity<Any>{

        val searchByKeyword = searchService.searchByKeyword(dto)

        println("Search Time: ${LocalDateTime.now()}, Search Keyword: $dto.term")

        return ResponseEntity.ok(searchByKeyword)
    }

    @GetMapping("/category")
    fun searchByCategory(
        @Valid dto : SearchDto.Request
    ) : ResponseEntity<Any>{

        val searchByCategory : List<Result> = searchService.searchByCategory(dto)
        //todo: 로깅하고 응답 가공하고 이것저것하기
        return ResponseEntity.ok(searchByCategory)
    }

}