package mashup.spring.elegant.search.domain.search.factory

import mashup.spring.elegant.search.domain.search.enums.SearchType
import org.elasticsearch.index.query.BoolQueryBuilder

interface QueryBaseFactory {

    fun create(type : SearchType, term : String) : BoolQueryBuilder
}