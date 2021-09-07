package mashup.spring.elegant.search.domain.search

import mashup.spring.elegant.search.domain.search.boost.LOCATION_BOOST
import mashup.spring.elegant.search.domain.search.boost.LOCATION_LIMIT
import mashup.spring.elegant.search.domain.model.ShopField
import mashup.spring.elegant.search.util.translateDay
import org.apache.lucene.search.join.ScoreMode
import org.elasticsearch.common.geo.ShapeRelation
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders.*
import org.joda.time.LocalDateTime
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import java.time.*

/**
 * 쿼리 빌딩을 위한 확장 함수를 모아놓은 파일
 */

/**
 *  영업 시간, 배달가능동 조건 추가
 */
fun BoolQueryBuilder.addAcceptableConditions(area: String, now: java.time.LocalDateTime): BoolQueryBuilder =
    this.filter(termQuery(ShopField.DELIVERY_AREA.field, area))
        .filter(
            nestedQuery(
                ShopField.OPEN_HOUR.field, boolQuery()
                    .filter(termQuery(ShopField.OPEN_HOUR_WEEK.field, translateDay(LocalDate.now().dayOfWeek)))
                    .filter(
                        rangeQuery(ShopField.OPEN_HOUR_HOUR.field)
                            .gte(LocalDateTime.now())
                            .lte(LocalDateTime.now())
                            .relation(ShapeRelation.CONTAINS.relationName)
                    ), ScoreMode.None
        )
    )


/**
 * 가까운 가게일수록 should 쿼리로 score 가 높게 측정된다.
 */
fun BoolQueryBuilder.addLocationCondition(lat: Double, lon: Double): BoolQueryBuilder = this.should(
    geoDistanceQuery(ShopField.LOCATION.field)
        .distance(LOCATION_LIMIT)
        .point(lat, lon)
        .boost(LOCATION_BOOST)
)

/**
 *  QueryBuilder 를 페이징처리해서 쿼리로 만든다.
 */
fun QueryBuilder.makeSearchQuery(page: Int, size: Int) = NativeSearchQueryBuilder()
    .withQuery(this)
    .withPageable(
        PageRequest.of(page, size)
    )
    .build()
