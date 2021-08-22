package mashup.spring.elegant.search.domain.search.factory.impl

import mashup.spring.elegant.search.domain.search.addLocationCondition
import mashup.spring.elegant.search.domain.search.addRequiredConditions
import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.domain.search.factory.FunctionQueryFactory
import mashup.spring.elegant.search.domain.search.factory.QueryBaseFactory
import mashup.spring.elegant.search.domain.search.factory.TypeQueryFactory
import mashup.spring.elegant.search.dto.SearchDto
import org.elasticsearch.index.query.QueryBuilder
import org.springframework.stereotype.Component

/**
 * Abstract Factory Impl
 */
@Component
class FunctionTypeQueryFactory (
    private val queryFactory : QueryBaseFactory,
    private val featureFactory : MustFeatureQueryFactory,
    private val functionFactory : FunctionQueryFactory
): TypeQueryFactory{


    override fun build(type: SearchType, dto: SearchDto.Request): QueryBuilder
        = when(type){
            SearchType.KEYWORD, SearchType.CATEGORY ->{

                val boolQuery = queryFactory.create(type, dto.term)
                                                            .addLocationCondition(dto.lat, dto.lon)
                                                            .addRequiredConditions(dto.area)

                val featureQuery = featureFactory.create(type, dto.feature, boolQuery)

                functionFactory.create(type, featureQuery)
            }
        }


}