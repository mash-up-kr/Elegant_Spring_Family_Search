package mashup.spring.elegant.search.domain.search

import mashup.spring.elegant.search.dto.SearchResult
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.stereotype.Component

@Component
interface ResultMapper {

    fun map(hits : SearchHits<Shop>): List<SearchResult>

}