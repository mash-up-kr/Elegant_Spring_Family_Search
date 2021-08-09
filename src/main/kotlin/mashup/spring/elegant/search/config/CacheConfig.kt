package mashup.spring.elegant.search.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.boot.autoconfigure.cache.CacheType
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.util.concurrent.TimeUnit


@Configuration
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val cache: Cache = CaffeineCache(
            CacheType.CAFFEINE.name,
            Caffeine.newBuilder()
                .initialCapacity(512)
                .maximumSize(1024)
                .expireAfterWrite(5, TimeUnit.HOURS)
                .build()
        )
        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(listOf(cache))
        return cacheManager
    }
}
