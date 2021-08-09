package mashup.spring.elegant.search.repository.cache


import mashup.spring.elegant.search.domain.cache.CacheKey
import org.springframework.boot.autoconfigure.cache.CacheType
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Repository


@Repository
class CaffeineRepository(
    private val caffeineCacheManager: CacheManager
) {
    private val cache = caffeineCacheManager.getCache(CacheType.CAFFEINE.name)

    fun <T> findOne(cacheKey: CacheKey, type: Class<T>): T? {
        return cache?.get(cacheKey.key(), type)
    }

    fun save(cacheKey: CacheKey, objects: Any?) = cache?.put(cacheKey.key(), objects)

    fun delete(cacheKey: CacheKey) = cache?.evict(cacheKey.key())

}