package mashup.spring.elegant.search.domain.search.factory

import mashup.spring.elegant.search.domain.search.enums.SearchType
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder

/**
 * SearchType 별로 펑션 쿼리 빌더를 만들어내는 팩토리
 * Impl List
 * BasicFunctionQueryFactory : Review 와 NewShop 에 대한 Function Score 를 적용시킨다.
 */
interface FunctionQueryFactory {
    fun create(type : SearchType, query : BoolQueryBuilder) : FunctionScoreQueryBuilder
}