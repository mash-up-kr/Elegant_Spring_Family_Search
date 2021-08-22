package mashup.spring.elegant.search.service

import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.dto.SearchDto

interface SearchService {
    fun search(type: SearchType, dto : SearchDto.Request) : List<SearchDto.Result>
}