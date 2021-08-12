package mashup.spring.elegant.search.domain.cache

class CacheKey(
    private val cacheGroup: CacheGroup,
    private val id: String
) {
    fun key(): String {
        return cacheGroup.name + "_" + id
    }

    fun print() {
        print(key())
    }
}