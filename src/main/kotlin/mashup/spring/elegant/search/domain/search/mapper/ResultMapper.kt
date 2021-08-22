package mashup.spring.elegant.search.domain.search.mapper

import mashup.spring.elegant.search.domain.search.Shop
import mashup.spring.elegant.search.dto.SearchDto
import org.springframework.data.elasticsearch.core.SearchHits

/**
 * Elastic Rest Template 의 검색 결과인 SearchHits<T> 를
 * 원하는 결과로 매핑시키는 매퍼
 * Impl List
 * IdScoreMapper : Id와 Score 를 포함하는 결과를 만들어낸다.
 */
interface ResultMapper {

    fun map(hits : SearchHits<Shop>): List<SearchDto.Result>

}