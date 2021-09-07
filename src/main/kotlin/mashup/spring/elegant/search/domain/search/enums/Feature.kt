package mashup.spring.elegant.search.domain.search.enums

import mashup.spring.elegant.search.domain.model.ShopField.*

/**
 * Feature 를 다루기 위한 Enum
 * API 에 대해 ElasticSearch Query 를 숨긴다.
 */
enum class Feature(
    val field : String
) {
    TAKEOUT("takeout"),
    ONLYONE("onlyone"), //일인분
    DISASTER("disaster")

}