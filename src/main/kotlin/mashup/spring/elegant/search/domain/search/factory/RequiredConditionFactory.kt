package mashup.spring.elegant.search.domain.search.factory

import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.dto.SearchDto
import org.elasticsearch.index.query.BoolQueryBuilder

interface RequiredConditionFactory {
    fun create(type: SearchType, dto: SearchDto.SingleTermRequest, query: BoolQueryBuilder) : BoolQueryBuilder
}