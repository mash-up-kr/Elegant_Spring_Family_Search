package mashup.spring.elegant.search.domain.search.boost

import mashup.spring.elegant.search.domain.model.ShopField
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders


/**
 * Boost Function 을 모아놓은 파일
 */

/**
 * Review 개수와 평점을 위한 부스터 펑션
 */
fun getReviewBooster()  = arrayOf(
    FunctionScoreQueryBuilder.FilterFunctionBuilder(
        FieldValueFactorFunctionBuilder(ShopField.REVIEW_COUNT.field)
            .factor(REVIEW_CNT_FACTOR)
            .modifier(FieldValueFactorFunction.Modifier.LOG1P)
    ),
    FunctionScoreQueryBuilder.FilterFunctionBuilder(
        ScoreFunctionBuilders
            .linearDecayFunction(
                ShopField.REVIEW_AVG.field,
                REVIEW_AVG_ORIGIN,
                REVIEW_AVG_SCALE,
                REVIEW_AVG_OFFSET,
                REVIEW_AVG_DECAY
            )
    )
)

/**
 * 신규 입점 가게를 위한 부스터 펑션
 */

fun getNewShopBooster () = arrayOf(
    FunctionScoreQueryBuilder.FilterFunctionBuilder(
        QueryBuilders.rangeQuery(ShopField.CREATED_DATE.field).gte(NEW_FILTER_FROM).lte(NEW_FILTER_TO),
        ScoreFunctionBuilders
            .gaussDecayFunction(
                ShopField.CREATED_DATE.field,
                NEW_SCORE_ORIGIN,
                NEW_SCORE_SCALE,
                NEW_SCORE_OFFSET,
                NEW_SCORE_DECAY
            )
    )
)