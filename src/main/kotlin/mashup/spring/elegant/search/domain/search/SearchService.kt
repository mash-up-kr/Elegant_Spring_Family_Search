package mashup.spring.elegant.search.domain.search

import mashup.spring.elegant.search.controller.SearchController.*
import mashup.spring.elegant.search.domain.search.ShopField.*
import mashup.spring.elegant.search.dto.SearchResult
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery
import org.elasticsearch.index.query.QueryBuilders.*
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.*
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional



@Transactional(readOnly = true)
@Service
class SearchService(
    private val template : ElasticsearchRestTemplate,
    private val mapper : ResultMapper
) {
    companion object{
        const val defaultPageSize =  25

    }


    fun searchByKeyword(dto : SearchDto) : List<SearchResult>{

        val keyword = dto.term
        val area = dto.area
        val lat = dto.lat
        val lon = dto.lon
        val page = dto.page

        val searchQueryBuilder = boolQuery()
            .should(
                matchQuery(SHOP_NAME.field, keyword).boost(5.0f))
            .should(
                matchQuery(MENU_NAME.field, keyword).boost(2.0f))
            .should(
                matchQuery(MENU_CONTENT.field, keyword).boost(1.0f))
            .should(
                geoDistanceQuery(LOCATION.field)
                    .distance("5km")
                    .point(lat,lon)
                    .boost(1.0f))
            .must(
                matchQuery(DELIVERY_AREA.field, area))

        val functions = arrayOf(
            FilterFunctionBuilder(FieldValueFactorFunctionBuilder(REVIEW_COUNT.field)
                                    .factor(1.0f)  //magic number
                                    .modifier(FieldValueFactorFunction.Modifier.LOG1P)),
            FilterFunctionBuilder(ScoreFunctionBuilders
                                      .linearDecayFunction(REVIEW_AVG.field,5,1,1,0.75))
        )

        //todo : boost parameter 등 책임 분리 필요
        val queryBuilder =
           functionScoreQuery(searchQueryBuilder,functions)
            .scoreMode(FunctionScoreQuery.ScoreMode.MULTIPLY)

        val result = template.search(
                                                NativeSearchQueryBuilder()
                                                     .withQuery(queryBuilder)
                                                     .withPageable(
                                                         PageRequest.of(page,defaultPageSize))
                                                    .build(),
                                                Shop::class.java)

        return mapper.map(result)
    }

    fun searchByCategory(dto : SearchDto) : List<SearchResult>{


        // todo: 쿼리 빌딩하고 응답 예쁘게 만들어주기

        return arrayListOf()
    }


}