package mashup.spring.elegant.search.controller

import mashup.spring.elegant.search.domain.search.SearchService
import mashup.spring.elegant.search.dto.SearchResult
import org.jetbrains.annotations.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@RequestMapping("/api/v1/search")
@RestController
class SearchController (
    private val searchService: SearchService
){

    @GetMapping("/keyword/{keyword}")
    fun searchByKeyword(
        @PathVariable keyword : String,
        @RequestParam lat : Long,
        @RequestParam lon : Long
    ) : ResponseEntity<Any>{

        val searchByKeyword : List<SearchResult> = searchService.searchByKeyword(keyword, lat, lon)

        //todo: 로깅하고 응답 가공하고 이것저것하기
        //todo: 25개 페이징 처리도 필요

        return ResponseEntity.ok(searchByKeyword)
    }

    @GetMapping("/keyword/{category}")
    fun searchByCategory(
        @PathVariable category : String,
        @RequestParam lat : Long,
        @RequestParam lon : Long
    ) : ResponseEntity<Any>{

        val searchByCategory : List<SearchResult> = searchService.searchByCategory(category, lat, lon)

        //todo: 로깅하고 응답 가공하고 이것저것하기
        //todo: 페이징
        return ResponseEntity.ok(searchByCategory)
    }

}