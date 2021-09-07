package mashup.spring.elegant.search.controller

import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.dto.SearchDto
import mashup.spring.elegant.search.service.SearchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/api/v1/search")
@RestController
class V1SearchController (
    private val searchService: SearchService
){
    @GetMapping("/keyword")
    fun searchByKeyword(@Valid dto : SearchDto.SingleTermRequest) : ResponseEntity<Any>{

        val searchByKeyword = searchService.singleTermSearch(SearchType.KEYWORD, dto)

        //todo: 실시간 검색 Logging 들어갈 부분

        return ResponseEntity.ok(searchByKeyword)
    }


    @GetMapping("/category")
    fun searchByCategory(@Valid categoryDto : SearchDto.CategoryRequest) : ResponseEntity<Any>{

        val dto = categoryDto.toRequest()
        val searchByKeyword = searchService.singleTermSearch(SearchType.CATEGORY, dto)

        //todo: 카테고리 검색은 실시간 검색어 로깅 X

        return ResponseEntity.ok(searchByKeyword)
    }


}