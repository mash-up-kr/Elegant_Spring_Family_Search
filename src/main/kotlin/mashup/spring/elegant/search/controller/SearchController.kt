package mashup.spring.elegant.search.controller

import mashup.spring.elegant.search.domain.search.SearchService
import mashup.spring.elegant.search.dto.SearchResult
import org.jetbrains.annotations.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@RequestMapping("/api/v1/search")
@RestController
class SearchController (
    private val searchService: SearchService
){

    @GetMapping("/keyword")
    fun searchByKeyword(@Valid dto : SearchDto) : ResponseEntity<Any>{

        val searchByKeyword = searchService.searchByKeyword(dto)

        //todo: 로깅하고 응답 래핑하고 키워드 검색 로깅하고 이것저것하기
        return ResponseEntity.ok(searchByKeyword)
    }

    @GetMapping("/category")
    fun searchByCategory(
        @Valid dto : SearchDto
    ) : ResponseEntity<Any>{

        val searchByCategory : List<SearchResult> = searchService.searchByCategory(dto)
        //todo: 로깅하고 응답 가공하고 이것저것하기
        return ResponseEntity.ok(searchByCategory)
    }

    class SearchDto(

        // todo: 최소 검색 글자 수 하드코딩 제거 필요, 메세지도!

        @Size(min = 2)
        val term : String,

        @NotNull
        val lat : Double,

        @NotNull
        val lon : Double,

        @NotBlank//공백 허용 x
        val area : String,

        @Min(value=0)
        val page : Int
    )

}