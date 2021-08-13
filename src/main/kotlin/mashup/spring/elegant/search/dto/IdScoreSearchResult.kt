package mashup.spring.elegant.search.dto

class IdScoreSearchResult (
    shopId : Long,
    val score : Float
): SearchResult(shopId){

}
