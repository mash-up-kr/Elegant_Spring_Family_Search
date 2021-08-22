package mashup.spring.elegant.search.domain.search.boost
import org.springframework.beans.factory.annotation.Value

/**
 * Search 필드에 사용되는 Boost 값을 @Value 로 값을 읽어오기 위한 파일
 */


/**
 *  Keyword Search Boost
 */

@Value("\${search.keyword.boost.shop_name}")
val SHOP_NAME_BOOST = 0f
@Value("\${search.keyword.boost.menu_name}")
val MENU_NAME_BOOST = 0f
@Value("\${search.keyword.boost.menu_content}")
val MENU_CONTENT_BOOST = 0f

/**
 * Category Search Boost
 */

@Value("\${search.category.boost.name}")
val CATEGORY_BOOST = 0f

/**
 * Common Search Boost
 */

@Value("\${search.keyword.boost.location}")
val LOCATION_BOOST = 0f
@Value("\${search.keyword.location.limit}")
val LOCATION_LIMIT = "0km"



