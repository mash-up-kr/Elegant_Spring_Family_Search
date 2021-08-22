package mashup.spring.elegant.search.domain.search.factory

import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.dto.SearchDto
import org.elasticsearch.index.query.QueryBuilder

/**
 * Abstract Factory Pattern 을 적용해
 * Search Type 별로 쿼리 팩토리를 묶는다.
 * Impl List
 * FunctionTypeQueryFactory : Base + Feature + Function Score 를 적용시킨 쿼리를 만들어낸다.
 */

interface TypeQueryFactory {
    fun build(type: SearchType, dto :SearchDto.Request) : QueryBuilder
}