package mashup.spring.elegant.search.controller

import mashup.spring.elegant.search.service.ProtoSearchService
import mashup.spring.elegant.search.dto.SearchDto
import mashup.spring.elegant.search.dto.SearchDto.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/api/v1/search")
@RestController
class SearchController (
    private val protoSearchService: ProtoSearchService
){

    @GetMapping("/keyword")
    fun searchByKeyword(@Valid dto : SearchDto.Request) : ResponseEntity<Any>{

        //Request Dto -> Service 에게 건네주는 dto 로 변환하기 (feature 검증)

        val searchByKeyword = protoSearchService.searchByKeyword(dto)




        //todo: Logging 들어갈 부분

        return ResponseEntity.ok(searchByKeyword)
    }

    @GetMapping("/category")
    fun searchByCategory(
        @Valid dto : SearchDto.Request
    ) : ResponseEntity<Any>{

        val searchByCategory : List<Result> = protoSearchService.searchByCategory(dto)
        //todo: 로깅하고 응답 가공하고 이것저것하기
        return ResponseEntity.ok(searchByCategory)
    }

}