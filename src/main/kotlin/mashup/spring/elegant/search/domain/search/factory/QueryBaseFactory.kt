package mashup.spring.elegant.search.domain.search.factory

import mashup.spring.elegant.search.domain.search.enums.SearchType
import org.elasticsearch.index.query.BoolQueryBuilder

/**
 * 어느 필드에 대해 검색할지를 정하는 쿼리 필더를 만들어내는 팩토리
 * Impl List
 * BasicQueryBaseFactory : Keyword 는 가게 이름과 메뉴에, Category 는 카테고리 이름에 쿼리한다.
 */
interface QueryBaseFactory {

    fun create(type : SearchType, term : String) : BoolQueryBuilder
}