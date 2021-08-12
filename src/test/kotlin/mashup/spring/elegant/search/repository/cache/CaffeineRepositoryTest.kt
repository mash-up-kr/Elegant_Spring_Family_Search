package mashup.spring.elegant.search.repository.cache

import mashup.spring.elegant.search.domain.cache.CacheGroup
import mashup.spring.elegant.search.domain.cache.CacheKey
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class CaffeineRepositoryTest {

    @Autowired
    private lateinit var caffeineRepository: CaffeineRepository

    @DisplayName("카페인 캐시 저장 후 검색 테스트")
    @Test
    fun cacheSaveAndFindTest() {
        val cacheKey = CacheKey(CacheGroup.REALTIME, "id")
        val cacheValue = "test data"

        caffeineRepository.save(cacheKey, cacheValue)

        val findValue = caffeineRepository.findOne(cacheKey, String::class.java)

        assertEquals(cacheValue, findValue)
    }
}