package mashup.spring.elegant.search.domain.search
import org.springframework.beans.factory.annotation.Value
/**
 * Boost 값을 @Value 로 값을 읽어오기 위한 파일
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



/**
 * Function Booster
 */


    /**
     * Review Count Boost Constant
     */
@Value("\${search.review.count.factor}")
val REVIEW_CNT_FACTOR = 0f

    /**
     * Review Average Boost Constant
     */
@Value("\${search.review.avg.origin}")
val REVIEW_AVG_ORIGIN = 0
@Value("\${search.review.avg.scale}")
val REVIEW_AVG_SCALE = 0
@Value("\${search.review.avg.offset}")
val REVIEW_AVG_OFFSET = 0
@Value("\${search.review.avg.decay}")
val REVIEW_AVG_DECAY = 0.0

@Value("\${search.new_shop.filter.from}")
val NEW_FILTER_FROM = ""
@Value("\${search.new_shop.filter.to}")
val NEW_FILTER_TO = ""
@Value("\${search.new_shop.score.origin}")
val NEW_SCORE_ORIGIN = ""
@Value("\${search.new_shop.score.scale}")
val NEW_SCORE_SCALE = ""
@Value("\${search.new_shop.score.offset}")
val NEW_SCORE_OFFSET = ""
@Value("\${search.new_shop.score.decay}")
val NEW_SCORE_DECAY = 0.0