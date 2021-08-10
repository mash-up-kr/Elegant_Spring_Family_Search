package mashup.spring.elegant.search.domain.search

//외부 설정 파일로 뺄 수도 있음
enum class Category(
    val categoryName: String
) {
    KOREAN("한식"),
    WESTERN("양식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    SNACK("분식"),
    MrUM("엄준식")

}