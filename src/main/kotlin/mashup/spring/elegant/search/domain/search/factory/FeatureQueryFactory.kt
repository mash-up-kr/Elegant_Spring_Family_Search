package mashup.spring.elegant.search.domain.search.factory

import mashup.spring.elegant.search.domain.search.SearchType
import mashup.spring.elegant.search.dto.Feature
import org.elasticsearch.index.query.BoolQueryBuilder

interface FeatureQueryFactory {
    fun create(type : SearchType,features: List<Feature>, query : BoolQueryBuilder) : BoolQueryBuilder
}