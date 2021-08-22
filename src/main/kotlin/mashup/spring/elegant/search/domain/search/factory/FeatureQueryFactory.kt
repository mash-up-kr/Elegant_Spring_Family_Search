package mashup.spring.elegant.search.domain.search.factory

import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.domain.search.enums.Feature
import org.elasticsearch.index.query.BoolQueryBuilder

/**
 * Feature 검색을 위한 쿼리 빌더를 만들어내는 팩토리
 * Impl List
 * MustFeatureQueryFactory : feature 들을 must 조건으로 엮는다.
 */
interface FeatureQueryFactory {
    fun create(type : SearchType, features: List<Feature>, query : BoolQueryBuilder) : BoolQueryBuilder
}