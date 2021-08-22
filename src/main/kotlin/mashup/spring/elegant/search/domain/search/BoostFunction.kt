package mashup.spring.elegant.search.domain.search

import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


/**
 * 파라미터 없는 쿼리
 */
@Component
class BoostFunction{

    fun getReviewBooster()  = arrayOf(
        FunctionScoreQueryBuilder.FilterFunctionBuilder(
            FieldValueFactorFunctionBuilder(ShopField.REVIEW_COUNT.field)
                .factor(REVIEW_CNT_FACTOR)
                .modifier(FieldValueFactorFunction.Modifier.LOG1P)
        ),
        FunctionScoreQueryBuilder.FilterFunctionBuilder(
            ScoreFunctionBuilders
                .linearDecayFunction(ShopField.REVIEW_AVG.field,
                                     REVIEW_AVG_ORIGIN,
                                     REVIEW_AVG_SCALE,
                                     REVIEW_AVG_OFFSET,
                                     REVIEW_AVG_DECAY)
        )
    )

    fun getNewShopBooster () = arrayOf(
        FunctionScoreQueryBuilder.FilterFunctionBuilder(
            QueryBuilders.rangeQuery(ShopField.CREATED_DATE.field).gte(NEW_FILTER_FROM).lte(NEW_FILTER_TO),
            ScoreFunctionBuilders
                .gaussDecayFunction(ShopField.CREATED_DATE.field,
                                    NEW_SCORE_ORIGIN,
                                    NEW_SCORE_SCALE,
                                    NEW_SCORE_OFFSET,
                                    NEW_SCORE_DECAY)
        )
    )

    /**
     * Review Count Boost Constant
     */
    @Value("\${search.review.count.factor}")
    private val REVIEW_CNT_FACTOR = 0f

    /**
     * Review Average Boost Constant
     */
    @Value("\${search.review.avg.origin}")
    private val REVIEW_AVG_ORIGIN = 0
    @Value("\${search.review.avg.scale}")
    private val REVIEW_AVG_SCALE = 0
    @Value("\${search.review.avg.offset}")
    private val REVIEW_AVG_OFFSET = 0
    @Value("\${search.review.avg.decay}")
    private val REVIEW_AVG_DECAY = 0.0

    @Value("\${search.new_shop.filter.from}")
    private val NEW_FILTER_FROM = ""
    @Value("\${search.new_shop.filter.to}")
    private val NEW_FILTER_TO = ""
    @Value("\${search.new_shop.score.origin}")
    private val NEW_SCORE_ORIGIN = ""
    @Value("\${search.new_shop.score.scale}")
    private val NEW_SCORE_SCALE = ""
    @Value("\${search.new_shop.score.offset}")
    private val NEW_SCORE_OFFSET = ""
    @Value("\${search.new_shop.score.decay}")
    private val NEW_SCORE_DECAY = 0.0

}




