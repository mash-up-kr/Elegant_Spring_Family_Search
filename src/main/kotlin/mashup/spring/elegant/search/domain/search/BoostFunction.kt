package mashup.spring.elegant.search.domain.search

import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders


/**
 * 파라미터 없는 쿼리
 */


fun getReviewBooster()  = arrayOf(
    FunctionScoreQueryBuilder.FilterFunctionBuilder(
        FieldValueFactorFunctionBuilder(ShopField.REVIEW_COUNT.field)
            .factor(1.0f)
            .modifier(FieldValueFactorFunction.Modifier.LOG1P)
    ),
    FunctionScoreQueryBuilder.FilterFunctionBuilder(
        ScoreFunctionBuilders
            .linearDecayFunction(ShopField.REVIEW_AVG.field, 5, 1, 1, 0.75)
    )
)
