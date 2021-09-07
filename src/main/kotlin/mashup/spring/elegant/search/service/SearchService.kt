package mashup.spring.elegant.search.service

import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.dto.SearchDto

/**
 * Search Service 의 책임
 * 1. 추상 팩토리 객체들에 의존해서 다른 도메인의 쿼리를 알맞은 추상 팩토리 객체로 라우팅한다.
 * 2. 도메인 별로 페이징 적용 여부를 결정한다.(ex.검색 리스트로 보여주는 경우에는 페이징 O, 지도에서 보여주는 검색인 경우 페이징 X)
 * 3. Result mapper 를 결정한다. (ex. 검색 리스트로 보여주는 경우는 순서가 중요하기 때문에 score 와 함께, 지도에서 평면상에 보여준 경우는 score 없이)
 * 4. 쿼리 팩토리에서 만들어진 쿼리를 실행한다.
 */
interface SearchService {
    fun singleTermSearch(type: SearchType, dto : SearchDto.SingleTermRequest) : List<SearchDto.Result>
}