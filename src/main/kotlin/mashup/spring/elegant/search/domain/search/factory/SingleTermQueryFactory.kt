package mashup.spring.elegant.search.domain.search.factory

import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.dto.SearchDto
import org.elasticsearch.index.query.QueryBuilder

/**
 * Abstract Factory Pattern 을 적용해서
 * 검색어를 하나만 사용하는 Search Type 별로 쿼리 팩토리를 묶는다.
 * Impl List
 * FunctionalSingleTermQueryFactory : 최종 쿼리가 Functional Score 인 쿼리를 만들어내는 팩토리
 */

interface SingleTermQueryFactory {
    fun build(type: SearchType, dto :SearchDto.SingleTermRequest) : QueryBuilder
}