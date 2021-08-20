package mashup.spring.elegant.search.service

import mashup.spring.elegant.search.domain.search.*
import mashup.spring.elegant.search.domain.search.factory.FeatureQueryFactory
import mashup.spring.elegant.search.domain.search.factory.FunctionQueryFactory
import mashup.spring.elegant.search.domain.search.factory.QueryBaseFactory
import mashup.spring.elegant.search.dto.SearchDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class BasicSearchService (
    private val template : ElasticsearchRestTemplate,
    @Qualifier("IdScoreMapper")
    private val mapper : ResultMapper,
    private val queryFactory : QueryBaseFactory,
    private val featureFactory : FeatureQueryFactory,
    private val functionFactory : FunctionQueryFactory
): SearchService{

    companion object{
        const val DEFAULT_PAGE_SIZE =  25
    }

    override fun search(type: SearchType, dto: SearchDto.Request) : List<SearchDto.Result>{

        val query = queryFactory.create(type, dto.term)
            .addLocationCondition(dto.lat, dto.lon)
            .addRequiredConditions(dto.area)

        val featuredQuery = featureFactory.create(type, query)

        val functionQuery = functionFactory.create(type, featuredQuery)

        val result = template.search(functionQuery.makeSearchQuery(dto.page, DEFAULT_PAGE_SIZE), Shop::class.java)

        return mapper.map(result)
    }


//    /**
//     * Method Extension for Query Building
//     */
//
//    private fun BoolQueryBuilder.addRequiredConditions(area: String)
//            = this.must(QueryBuilders.matchQuery(ShopField.DELIVERY_AREA.field, area))
//        .must(QueryBuilders.matchQuery(ShopField.OPEN_HOUR_WEEK.field, translateDay(LocalDate.now().dayOfWeek)))
//        .must(
//            QueryBuilders.rangeQuery(ShopField.OPEN_HOUR_HOUR.field)
//                  .gte(LocalDateTime.now().hourOfDay)
//                  .lte(LocalDateTime.now().hourOfDay)
//                  .relation("contains"))
//
//    private fun BoolQueryBuilder.addLocationCondition(lat : Double, lon : Double)
//            = this.should(
//        QueryBuilders.geoDistanceQuery(ShopField.LOCATION.field)
//                              .distance(LOCATION_LIMIT)
//                              .point(lat,lon)
//                              .boost(LOCATION_BOOST))
//
//    private fun FunctionScoreQueryBuilder.makeSearchQuery(page: Int)
//            = NativeSearchQueryBuilder()
//        .withQuery(this)
//        .withPageable(
//            PageRequest.of(page, DEFAULT_PAGE_SIZE)
//        )
//        .build()
//
//    /**
//     *  Keyword Search Boost
//     */
//
//    @Value("\${search.keyword.boost.shop_name}")
//    private val SHOP_NAME_BOOST = 0f
//    @Value("\${search.keyword.boost.menu_name}")
//    private val MENU_NAME_BOOST = 0f
//    @Value("\${search.keyword.boost.menu_content}")
//    private val MENU_CONTENT_BOOST = 0f
//    @Value("\${search.keyword.boost.location}")
//    private val LOCATION_BOOST = 0f
//    @Value("\${search.keyword.location.limit}")
//    private val LOCATION_LIMIT = "0km"
//
//    /**
//     * Category Search Boost
//     */
//    @Value("\${search.category.boost.name}")
//    private val CATEGORY_BOOST = 0f
}