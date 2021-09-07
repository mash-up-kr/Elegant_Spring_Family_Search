package mashup.spring.elegant.search.domain.search.factory.impl

import mashup.spring.elegant.search.domain.search.addLocationCondition
import mashup.spring.elegant.search.domain.search.addAcceptableConditions
import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.domain.search.factory.FunctionQueryFactory
import mashup.spring.elegant.search.domain.search.factory.QueryBaseFactory
import mashup.spring.elegant.search.domain.search.factory.RequiredConditionFactory
import mashup.spring.elegant.search.domain.search.factory.SingleTermQueryFactory
import mashup.spring.elegant.search.dto.SearchDto
import org.elasticsearch.index.query.QueryBuilder
import org.joda.time.LocalDateTime
import org.springframework.stereotype.Component

/**
 * Abstract Factory Impl
 */
@Component
class FunctionalSingleTermQueryFactory (
    private val queryFactory : QueryBaseFactory,
    private val featureFactory : MustFeatureQueryFactory,
    private val functionFactory : FunctionQueryFactory,
    private val conditionFactory: RequiredConditionFactory
): SingleTermQueryFactory{


    override fun build(type: SearchType, dto: SearchDto.SingleTermRequest): QueryBuilder
        = when(type){
            SearchType.KEYWORD, SearchType.CATEGORY ->{

                val boolQuery = queryFactory.create(type, dto.term)

                val conditioned = conditionFactory.create(type,dto, boolQuery)

                val featureQuery = featureFactory.create(type, dto.feature, conditioned)

                functionFactory.create(type, featureQuery)
            }
        }


}