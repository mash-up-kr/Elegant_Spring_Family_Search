package mashup.spring.elegant.search.domain.search.factory.impl

import mashup.spring.elegant.search.domain.search.addAcceptableConditions
import mashup.spring.elegant.search.domain.search.addLocationCondition
import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.domain.search.factory.RequiredConditionFactory
import mashup.spring.elegant.search.dto.SearchDto
import mashup.spring.elegant.search.dto.SearchDto.SingleTermRequest
import org.elasticsearch.index.query.BoolQueryBuilder
import org.joda.time.LocalDateTime
import org.springframework.stereotype.Component

@Component
class BasicRequiredConditionFactory : RequiredConditionFactory {
    override fun create(type: SearchType, dto: SingleTermRequest, query: BoolQueryBuilder): BoolQueryBuilder = when (type) {
        SearchType.KEYWORD, SearchType.CATEGORY -> {
            query.addLocationCondition(dto.lat, dto.lon)
            query.addAcceptableConditions(dto.area, LocalDateTime.now())
        }
    }

}