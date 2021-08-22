package mashup.spring.elegant.search.domain.search.mapper

import mashup.spring.elegant.search.domain.search.Shop
import mashup.spring.elegant.search.dto.SearchDto
import org.springframework.data.elasticsearch.core.SearchHits

interface ResultMapper {

    fun map(hits : SearchHits<Shop>): List<SearchDto.Result>

}