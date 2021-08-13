package mashup.spring.elegant.search.domain.search

import mashup.spring.elegant.search.controller.SearchController.*
import mashup.spring.elegant.search.domain.search.ShopField.*
import mashup.spring.elegant.search.dto.SearchResult
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery
import org.elasticsearch.index.query.QueryBuilders.*
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional



@Transactional(readOnly = true)
@Service
class SearchService(
    private val template : ElasticsearchRestTemplate,
    private val mapper : ResultMapper,
) {
    companion object{
        const val DEFAULT_PAGE_SIZE =  25
        val reviewFunctions = getReviewBooster()

        /**
         *  Keyword Search Boost
         */
        const val SHOP_NAME_BOOST = 5.0f
        const val MENU_NAME_BOOST = 5.0f
        const val MENU_CONTENT_BOOST = 5.0f
        const val LOCATION_BOOST = 5.0f
        const val LOCATION_LIMIT = "5km"
    }


    fun searchByKeyword(dto : SearchDto) : List<SearchResult>{


        /**
         * DTO 중 사용 안하는 필드 IDE 가 체크
         */
        val keyword = dto.term
        val area = dto.area
        val lat = dto.lat
        val lon = dto.lon
        val page = dto.page

        /**
         * Building Keyword Search Query
         */
        val searchQueryBuilder = boolQuery()
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
            .must(
                matchQuery(DELIVERY_AREA.field, area))

        /**
         * Building Boost Function List
         */
        val functions : ArrayList<FilterFunctionBuilder> = ArrayList()
        functions.addAll(reviewFunctions)

        /**
         * Query 와 Score Function 조합
         */
        val queryBuilder =functionScoreQuery(
                                                        searchQueryBuilder,
                                                        functions.toTypedArray()
                                                    )
                                                    .scoreMode(FunctionScoreQuery.ScoreMode.MULTIPLY)
        /**
         * 검색 실행
         */
        val result = template.search(makeSearchQuery(queryBuilder, page),Shop::class.java)

        return mapper.map(result)
    }




    fun searchByCategory(dto : SearchDto) : List<SearchResult>{


        // todo: 쿼리 빌딩하고 응답 예쁘게 만들어주기

        return arrayListOf()
    }




    private fun makeSearchQuery(queryBuilder: FunctionScoreQueryBuilder, page: Int) = NativeSearchQueryBuilder()
        .withQuery(queryBuilder)
        .withPageable(
            PageRequest.of(page, DEFAULT_PAGE_SIZE)
        )
        .build()




}