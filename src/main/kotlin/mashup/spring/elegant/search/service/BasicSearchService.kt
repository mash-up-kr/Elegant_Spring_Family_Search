package mashup.spring.elegant.search.service

import mashup.spring.elegant.search.domain.model.Shop
import mashup.spring.elegant.search.domain.search.*
import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.domain.search.factory.SingleTermQueryFactory
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
    private val singleFactory: SingleTermQueryFactory
): SearchService{

    companion object{
        const val DEFAULT_PAGE_SIZE =  25
    }

    /**
     * 검색어가 하나인 경우
     */
    override fun singleTermSearch(type: SearchType, dto: SearchDto.SingleTermRequest) : List<SearchDto.Result>{

        val query = singleFactory
                                        .build(type, dto)
                                        .makeSearchQuery(dto.page, DEFAULT_PAGE_SIZE)

        val result = template.search(query, Shop::class.java)

        return mapper.map(result)
    }

}