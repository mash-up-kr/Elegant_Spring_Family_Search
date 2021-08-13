package mashup.spring.elegant.search.dto

class IdScoreSearchResult (
    private val shopId : Long,
    private val score : Float
): SearchResult(shopId){

}
