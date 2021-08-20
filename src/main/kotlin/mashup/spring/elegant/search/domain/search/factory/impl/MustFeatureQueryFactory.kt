package mashup.spring.elegant.search.domain.search.factory.impl

import mashup.spring.elegant.search.domain.search.SearchType
import mashup.spring.elegant.search.domain.search.factory.FeatureQueryFactory
import mashup.spring.elegant.search.dto.Feature
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.springframework.stereotype.Component

@Component
class MustFeatureQueryFactory : FeatureQueryFactory{
    override fun create(type: SearchType, features: List<Feature>, query: BoolQueryBuilder): BoolQueryBuilder
        = when(type){
            SearchType.CATEGORY, SearchType.KEYWORD->{
                features.stream()
                    .forEach{query.must(QueryBuilders.matchQuery(it.field, true))}
                query
            }
        }
}