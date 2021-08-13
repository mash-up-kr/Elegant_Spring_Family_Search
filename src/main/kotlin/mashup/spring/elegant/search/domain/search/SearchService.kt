package mashup.spring.elegant.search.domain.search

import mashup.spring.elegant.search.domain.search.ShopField.*
import mashup.spring.elegant.search.dto.SearchDto.*
import mashup.spring.elegant.search.util.translateDay
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.*
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders.*
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.*
import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional(readOnly = true)
@Service
class SearchService(
    private val template : ElasticsearchRestTemplate,
    private val mapper : ResultMapper,
    booster : BoostFunction
) {
    companion object{
        const val DEFAULT_PAGE_SIZE =  25
    }

    /**
     *  Keyword Search Boost
     */
    @Value("\${search.keyword.boost.shop_name}")
    private val SHOP_NAME_BOOST = 0f
    @Value("\${search.keyword.boost.menu_name}")
    private val MENU_NAME_BOOST = 0f
    @Value("\${search.keyword.boost.menu_content}")
    private val MENU_CONTENT_BOOST = 0f
    @Value("\${search.keyword.boost.location}")
    private val LOCATION_BOOST = 0f
    @Value("\${search.keyword.location.limit}")
    private val LOCATION_LIMIT = "0km"

    private val reviewFunctions by lazy { booster.getReviewBooster() }
    private val newShopFunctions by lazy { booster.getNewShopBooster() }

    /**
     * Category Search Boost
     */


    fun searchByKeyword(dto : Request) : List<Result>{


        /**
         * DTO 중 사용 안하는 필드를 IDE 가 체크
         */
        val keyword = dto.term
        val area = dto.area
        val lat = dto.lat
        val lon = dto.lon
        val page = dto.page

        /**
         * Building Boost Function List
         */
        val functions : ArrayList<FilterFunctionBuilder> = ArrayList()
        functions.addAll(reviewFunctions)
        functions.addAll(newShopFunctions)

        /**
         * Building Keyword Search Query
         */
        val query = boolQuery()
            .should(
                matchQuery(SHOP_NAME.field, keyword).boost(SHOP_NAME_BOOST))
            .should(
                matchQuery(MENU_NAME.field, keyword).boost(MENU_NAME_BOOST))
            .should(
                matchQuery(MENU_CONTENT.field, keyword).boost(MENU_CONTENT_BOOST))
            .should(
                geoDistanceQuery(LOCATION.field)
                    .distance(LOCATION_LIMIT)
                    .point(lat,lon)
                    .boost(LOCATION_BOOST))
            .addRequiredConditions(area) // 배달가능지역, 오픈시간 체크
            .toFunctionQuery(functions, ScoreMode.SUM)
            .makeSearchQuery(page)

        /**
         * 검색 실행
         */
        val result = template.search(query,Shop::class.java)

        return mapper.map(result)
    }




    fun searchByCategory(dto : Request) : List<Result>{


        // todo: 쿼리 빌딩하고 응답 예쁘게 만들어주기

        return arrayListOf()
    }





    private fun FunctionScoreQueryBuilder.makeSearchQuery(page: Int)
        = NativeSearchQueryBuilder()
            .withQuery(this)
            .withPageable(
                PageRequest.of(page, DEFAULT_PAGE_SIZE)
            )
            .build()


    private fun BoolQueryBuilder.addRequiredConditions(area: String)
       = this.must(matchQuery(DELIVERY_AREA.field, area))
        .must(matchQuery(OPEN_HOUR_WEEK.field, translateDay(LocalDate.now().dayOfWeek)))
        .must(rangeQuery(OPEN_HOUR_HOUR.field)
                  .gte(LocalDateTime.now().hourOfDay)
                  .lte(LocalDateTime.now().hourOfDay)
                  .relation("contains"))



    private fun BoolQueryBuilder.toFunctionQuery(functions :ArrayList<FilterFunctionBuilder>,
                                                 mode: ScoreMode = ScoreMode.SUM)
        = functionScoreQuery(this, functions.toTypedArray()).scoreMode(mode)


}