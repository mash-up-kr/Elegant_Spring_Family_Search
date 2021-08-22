package mashup.spring.elegant.search.domain.search.enums


/**
 * Api 에 구체적인 Category 이름을 숨기기 위한 Enum
 * ElasticSearch Query 검색어를 숨긴다.
 */
enum class Category(
    val field: String
) {
    KOREAN("한식"),
    WESTERN("양식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    SNACK("분식"),
    MrUM("엄준식")

}