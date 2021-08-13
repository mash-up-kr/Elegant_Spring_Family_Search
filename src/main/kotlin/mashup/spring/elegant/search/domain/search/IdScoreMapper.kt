package mashup.spring.elegant.search.domain.search


import mashup.spring.elegant.search.dto.SearchDto
import org.springframework.context.annotation.Primary
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.stereotype.Component
import kotlin.streams.toList

@Component
@Primary
class IdScoreMapper : ResultMapper{

    override fun map(hits: SearchHits<Shop>) = hits.stream()
                                                .map { SearchDto.IdScoreResult(it.content.shop_id, it.score) }
                                                .toList()
}