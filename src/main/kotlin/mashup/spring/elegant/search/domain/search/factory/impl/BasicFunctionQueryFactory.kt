package mashup.spring.elegant.search.domain.search.factory.impl

import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.domain.search.factory.FunctionQueryFactory
import mashup.spring.elegant.search.domain.search.boost.getNewShopBooster
import mashup.spring.elegant.search.domain.search.boost.getReviewBooster
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.*
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.*
import org.springframework.stereotype.Component

@Component
class BasicFunctionQueryFactory :FunctionQueryFactory {

    private val reviewFunctions by lazy { getReviewBooster() }
    private val newShopFunctions by lazy { getNewShopBooster() }

    override fun create(type: SearchType, query: BoolQueryBuilder): FunctionScoreQueryBuilder
    = when(type){
        SearchType.KEYWORD, SearchType.CATEGORY -> {

            val functions : ArrayList<FilterFunctionBuilder> = ArrayList()
            functions.addAll(reviewFunctions)
            functions.addAll(newShopFunctions)

            QueryBuilders.functionScoreQuery(query, functions.toTypedArray()).scoreMode(ScoreMode.SUM)
        }
    }
}