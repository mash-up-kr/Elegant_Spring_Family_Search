package mashup.spring.elegant.search.domain.search.boost

import org.springframework.beans.factory.annotation.Value

/**
 * Function Boost 값을 설정파일로부터 읽어오기 위한 파일
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