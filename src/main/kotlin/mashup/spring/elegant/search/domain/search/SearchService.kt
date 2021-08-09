package mashup.spring.elegant.search.domain.search

import mashup.spring.elegant.search.dto.SearchResult
import org.apache.lucene.util.QueryBuilder
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional(readOnly = true)
@Service
class SearchService(
    private val template : ElasticsearchRestTemplate
) {


    fun searchByKeyword(keyword : String,
                        lat: Double,
                        lon: Double,
                        dong: String,
                        page : Int) : List<SearchResult>{

        //todo: 쿼리 빌딩하고 등등
        //todo: 상수는 yml로 빼기
        //keyword -> shopname-1, menu.name-2, menu.content-3
        //Delivery area 내에 현재 위치 있어야
        //todo: 위도경도->동으로 변환하는 api 호출하기

        /**
        *  1. 어느 기준으로 쿼리 날릴지 설정
         *  2. 쿼리 기준은 변경될 수 있음
         *  3. 페이징 쿼리 빌딩하기
         *  4. 쿼리 날려서 결과 가져오기
         *  5. SearchResult에 담아서 보여주기
         */


        val buildQuery = NativeSearchQueryBuilder()
            .withQuery(
                QueryBuilders
                    .matchQuery("shop_name", keyword).boost(3.0f)
            )
            .withQuery(
                QueryBuilders
                    .matchQuery("menu.name", keyword).boost(2.0f)
            )
            .withQuery(
                QueryBuilders
                    .matchQuery("menu.content", keyword).boost(1.0f)
            )
            .withQuery(
                QueryBuilders
                    .boolQuery()
                    .must(
                        QueryBuilders
                            .matchQuery("deliver_area", "dong")
                    )
            )
            .withQuery(
                QueryBuilders
                    .functionScoreQuery(
                        FieldValueFactorFunctionBuilder("review.avg")
                            .factor(3.0f)//magic number
                            .modifier(FieldValueFactorFunction.Modifier.LOG)
                    )
            )
            .withQuery(
                QueryBuilders
                    .functionScoreQuery(
                        FieldValueFactorFunctionBuilder("review.count")
                            .factor(2.0f)//magic number
                            .modifier(FieldValueFactorFunction.Modifier.LOG)
                    )
            )
            .withPageable(PageRequest.of(page,25))
            .build()

        val result = ArrayList<SearchResult>()
        val searchHits = template.search(buildQuery, Shop::class.java)

        //todo: Logger 교체
        println(searchHits.getSearchHit(0).content.menu_list.get(0).name)

        for(hit in searchHits){
            val id = hit.content.shop_id
            val score = hit.score
            result.add(SearchResult(id, score))
        }

        return result
    }

    fun searchByCategory(keyword : String, lat: Double, lon: Double) : List<SearchResult>{


        // todo: 쿼리 빌딩하고 응답 예쁘게 만들어주기

        return arrayListOf()
    }


}