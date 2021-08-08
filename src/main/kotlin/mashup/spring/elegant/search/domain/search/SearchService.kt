package mashup.spring.elegant.search.domain.search

import mashup.spring.elegant.search.dto.Location
import mashup.spring.elegant.search.dto.SearchResult
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional(readOnly = true)
@Service
class SearchService(
    private val template : ElasticsearchRestTemplate
) {


    fun searchByKeyword(keyword : String, lat: Long, lon: Long) : List<SearchResult>{

        //todo: 쿼리 빌딩하고 등등
        //todo: 상수는 yml로 빼기
        //keyword -> shopname, menu.name, menu.content
        //Delivery area 내에 현재 위치 있어야
        //todo: 위도경도->동으로 변환하는 api 호출하기

        return arrayListOf()
    }

    fun searchByCategory(keyword : String, lat: Long, lon: Long) : List<SearchResult>{


        // todo: 쿼리 빌딩하고 응답 예쁘게 만들어주기

        return arrayListOf()
    }
}