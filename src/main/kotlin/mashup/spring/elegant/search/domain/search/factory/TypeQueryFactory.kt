package mashup.spring.elegant.search.domain.search.factory

import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.dto.SearchDto
import org.elasticsearch.index.query.QueryBuilder

/**
 * Abstract Factory Pattern
 */

interface TypeQueryFactory {
    fun build(type: SearchType, dto :SearchDto.Request) : QueryBuilder
}