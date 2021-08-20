package mashup.spring.elegant.search.domain.search

import mashup.spring.elegant.search.service.BasicSearchService
import mashup.spring.elegant.search.util.translateDay
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder
import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import java.time.LocalDate

/**
 * Method Extension for Query Building
 */

fun BoolQueryBuilder.addRequiredConditions(area: String): BoolQueryBuilder = this.must(QueryBuilders.matchQuery(ShopField.DELIVERY_AREA.field, area))
    .must(QueryBuilders.matchQuery(ShopField.OPEN_HOUR_WEEK.field, translateDay(LocalDate.now().dayOfWeek)))
    .must(
        QueryBuilders.rangeQuery(ShopField.OPEN_HOUR_HOUR.field)
            .gte(LocalDateTime.now().hourOfDay)
            .lte(LocalDateTime.now().hourOfDay)
            .relation("contains"))

fun BoolQueryBuilder.addLocationCondition(lat : Double, lon : Double): BoolQueryBuilder = this.should(
    QueryBuilders.geoDistanceQuery(ShopField.LOCATION.field)
        .distance(LOCATION_LIMIT)
        .point(lat,lon)
        .boost(LOCATION_BOOST))

fun QueryBuilder.makeSearchQuery(page: Int, size : Int)
        = NativeSearchQueryBuilder()
    .withQuery(this)
    .withPageable(
        PageRequest.of(page, size)
    )
    .build()
