package mashup.spring.elegant.search.service

import mashup.spring.elegant.search.domain.search.BoostFunction
import mashup.spring.elegant.search.domain.search.ResultMapper
import mashup.spring.elegant.search.domain.search.Shop
import mashup.spring.elegant.search.domain.search.ShopField.*
import mashup.spring.elegant.search.dto.SearchDto.*
import mashup.spring.elegant.search.util.translateDay
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.*
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders.*
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.*
import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional(readOnly = true)
@Service
class ProtoSearchService(
    private val template : ElasticsearchRestTemplate,
    @Qualifier("IdScoreMapper")
    private val mapper : ResultMapper,
    booster : BoostFunction
) {
    companion object{
        const val DEFAULT_PAGE_SIZE =  25
    }

    /**
     * Category Search Boost
     */
    private val reviewFunctions by lazy { booster.getReviewBooster() }
    private val newShopFunctions by lazy { booster.getNewShopBooster() }


    //todo: takeout 같은 추가적인 검색 쿼리는 분리가 필요할 것같다.
    // 쿼리 빌딩은 따로 빼고 서치 서비스에서 추가적인 정보를 넣는 것으로 해야겠다.

    fun searchByKeyword(dto : Request) : List<Result>{

        /**
         * 메서드 내에서 DTO 중 사용 안하는 필드를 IDE 가 체크
         */
        val keyword = dto.term
        val area = dto.area
        val lat = dto.lat
        val lon = dto.lon
        val page = dto.page
        //val isTakeout = dto.isTakeout

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
            .addLocationCondition(lat, lon)
            //.takeOut(isTakeout)
            .addRequiredConditions(area) // 배달가능지역, 오픈시간 체크
            .toFunctionQuery(functions, ScoreMode.SUM)
            .makeSearchQuery(page)


        val result = template.search(query, Shop::class.java)

        return mapper.map(result)
    }




    fun searchByCategory(dto : Request) : List<Result>{

        /**
         * 메서드 내에서 DTO 중 사용 안하는 필드를 IDE 가 체크
         */
        val category = dto.term
        val area = dto.area
        val lat = dto.lat
        val lon = dto.lon
        val page = dto.page
        //val isTakeout = dto.isTakeout


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
                matchQuery(CATEGORY.field, category).boost(CATEGORY_BOOST))
            .addLocationCondition(lat, lon)
            //.takeOut(isTakeout)
            .addRequiredConditions(area) // 배달가능지역, 오픈시간 체크
            .toFunctionQuery(functions, ScoreMode.SUM)
            .makeSearchQuery(page)


        val result = template.search(query, Shop::class.java)

        return mapper.map(result)
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

    private fun BoolQueryBuilder.addLocationCondition(lat : Double, lon : Double)
       = this.should(geoDistanceQuery(LOCATION.field)
                        .distance(LOCATION_LIMIT)
                        .point(lat,lon)
                        .boost(LOCATION_BOOST))



    private fun BoolQueryBuilder.toFunctionQuery(functions :ArrayList<FilterFunctionBuilder>,
                                                 mode: ScoreMode = ScoreMode.SUM)
       = functionScoreQuery(this, functions.toTypedArray()).scoreMode(mode)

    private fun BoolQueryBuilder.takeOut(isTakeOut : Boolean)
       = this.must(matchQuery(TAKE_OUT.field, isTakeOut))


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

    /**
     * Category Search Boost
     */
    @Value("\${search.category.boost.name}")
    private val CATEGORY_BOOST = 0f

}