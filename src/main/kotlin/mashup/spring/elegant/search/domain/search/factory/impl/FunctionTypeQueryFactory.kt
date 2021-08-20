package mashup.spring.elegant.search.domain.search.factory.impl

import mashup.spring.elegant.search.domain.search.SearchType
import mashup.spring.elegant.search.domain.search.factory.FunctionQueryFactory
import mashup.spring.elegant.search.domain.search.factory.QueryBaseFactory
import mashup.spring.elegant.search.domain.search.factory.TypeQueryFactory
import mashup.spring.elegant.search.dto.SearchDto
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilder

/**
 * Abstract Factory Impl
 */

class FunctionTypeQueryFactory (
    private val queryFactory : QueryBaseFactory,
    private val featureFactory : MustFeatureQueryFactory,
    private val functionFactory : FunctionQueryFactory
): TypeQueryFactory{

    // When 사용 이유 : 새로운 Search Type 이 추가되었을 경우 when 에서 컴파일 오류 난다.
    override fun build(type: SearchType, dto: SearchDto.Request): QueryBuilder
        = when(type){
            SearchType.KEYWORD, SearchType.CATEGORY ->{
                val boolQuery = queryFactory.create(type, dto.term)
                val featureQuery = featureFactory.create(type, dto.feature, boolQuery)

                functionFactory.create(type, featureQuery)
            }
        }


}