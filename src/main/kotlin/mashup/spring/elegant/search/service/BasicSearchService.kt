package mashup.spring.elegant.search.service

import mashup.spring.elegant.search.domain.search.*
import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.domain.search.factory.TypeQueryFactory
import mashup.spring.elegant.search.domain.search.mapper.ResultMapper
import mashup.spring.elegant.search.dto.SearchDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.stereotype.Service

@Service
class BasicSearchService (
    private val template : ElasticsearchRestTemplate,
    @Qualifier("IdScoreMapper")
    private val mapper : ResultMapper,
    private val typeFactory: TypeQueryFactory
): SearchService{

    companion object{
        const val DEFAULT_PAGE_SIZE =  25
    }

    override fun search(type: SearchType, dto: SearchDto.Request) : List<SearchDto.Result>{

        val query = typeFactory
                                        .build(type, dto)
                                        .makeSearchQuery(dto.page, DEFAULT_PAGE_SIZE)

        val result = template.search(query, Shop::class.java)

        return mapper.map(result)
    }


}