package mashup.spring.elegant.search.domain.search.enums

import mashup.spring.elegant.search.domain.search.enums.ShopField.*

/**
 * Feature 를 다루기 위한 Enum
 * ElasticSearch Query 를 숨긴다.
 */
enum class Feature(
    val field : String
) {
    TAKEOUT(TAKE_OUT.field),
    ONLYONE(ONLY_ONE.field), //일인분
    DISASTER(DISASTER.field)

}