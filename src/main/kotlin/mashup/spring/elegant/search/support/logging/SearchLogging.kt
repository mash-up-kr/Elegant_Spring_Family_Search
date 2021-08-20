package mashup.spring.elegant.search.support.logging

enum class SearchLogging(private val desc: String) {
    SEARCH_KEYWORD("키워드 검색");

    fun print() = "[$name] $desc"
    fun print(key: String, value: String) = "[$name] $desc, $key : $value"
}