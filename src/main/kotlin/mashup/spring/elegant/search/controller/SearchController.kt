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
        @RequestParam lat : Double,
        @RequestParam lon : Double,
        @RequestParam dong: String,
        @RequestParam page : Int
    ) : ResponseEntity<Any>{

        val searchByKeyword : List<SearchResult> =
            searchService.searchByKeyword(keyword, lat, lon, dong, page)

        //todo: 로깅하고 응답 가공하고 이것저것하기

        return ResponseEntity.ok(searchByKeyword)
    }

    @GetMapping("/category/{category}")
    fun searchByCategory(
        @PathVariable category : String,
        @RequestParam lat : Double,
        @RequestParam lon : Double
    ) : ResponseEntity<Any>{

        val searchByCategory : List<SearchResult> = searchService.searchByCategory(category, lat, lon)

        //todo: 로깅하고 응답 가공하고 이것저것하기
        return ResponseEntity.ok(searchByCategory)
    }

}