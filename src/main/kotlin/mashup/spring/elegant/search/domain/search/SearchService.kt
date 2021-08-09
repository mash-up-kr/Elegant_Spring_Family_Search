package mashup.spring.elegant.search.domain.search

import mashup.spring.elegant.search.controller.SearchController.*
import mashup.spring.elegant.search.dto.SearchResult
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional(readOnly = true)
@Service
class SearchService(
    private val template : ElasticsearchRestTemplate
) {


    fun searchByKeyword(dto : SearchDto) : List<SearchResult>{

        val keyword = dto.term
        val area = dto.area

        //todo : boost parameter 등 책임 분리 필요
        //todo : dto 위도 경도 기반 검색 쿼리 추가 필요
        val queryBuilder = QueryBuilders
            .functionScoreQuery(
                QueryBuilders.boolQuery()
                    .should(QueryBuilders
                                .matchQuery("shop_name", keyword).boost(5.0f))
                    .should(QueryBuilders
                                .matchQuery("menu.name", keyword).boost(2.0f))
                    .should(
                        QueryBuilders
                            .matchQuery("menu.content", keyword).boost(1.0f))
                    .must(
                        QueryBuilders
                            .matchQuery("delivery_area", area)
                    ),
                FieldValueFactorFunctionBuilder("review.count")
                    .factor(1.0f)  //magic number
                    .modifier(FieldValueFactorFunction.Modifier.LOG))
            .scoreMode(FunctionScoreQuery.ScoreMode.MULTIPLY)



        // todo: functional 하게 수정하기
        val searchHits = template.search(NativeSearchQuery(queryBuilder), Shop::class.java)

        val result = ArrayList<SearchResult>()

        for(hit in searchHits){
            val id = hit.content.shop_id
            val score = hit.score
            result.add(SearchResult(id, score))
        }

        return result
    }

    fun searchByCategory(dto : SearchDto) : List<SearchResult>{


        // todo: 쿼리 빌딩하고 응답 예쁘게 만들어주기

        return arrayListOf()
    }


}