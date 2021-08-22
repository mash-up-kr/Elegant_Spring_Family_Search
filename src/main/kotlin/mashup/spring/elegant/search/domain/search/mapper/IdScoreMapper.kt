package mashup.spring.elegant.search.domain.search.mapper


import mashup.spring.elegant.search.domain.search.Shop
import mashup.spring.elegant.search.dto.SearchDto
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.stereotype.Component
import kotlin.streams.toList

@Component("IdScoreMapper")
class IdScoreMapper : ResultMapper {

    override fun map(hits: SearchHits<Shop>) = hits.stream()
                                                .map { SearchDto.IdScoreResult(it.content.shop_id, it.score) }
                                                .toList()
}