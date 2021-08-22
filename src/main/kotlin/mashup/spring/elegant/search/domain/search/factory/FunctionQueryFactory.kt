package mashup.spring.elegant.search.domain.search.factory

import mashup.spring.elegant.search.domain.search.enums.SearchType
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder

interface FunctionQueryFactory {
    fun create(type : SearchType, query : BoolQueryBuilder) : FunctionScoreQueryBuilder
}