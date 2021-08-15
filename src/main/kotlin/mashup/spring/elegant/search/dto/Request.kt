package mashup.spring.elegant.search.dto

import org.jetbrains.annotations.NotNull
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SearchDto {
    /**
     * Search Controller
     */
    class Request(

        @Size(min = 2)
        val term : String,

        @NotNull
        val lat : Double,

        @NotNull
        val lon : Double,

        @NotNull
        val isTakeout : Boolean,

        @NotBlank//공백 허용 x
        val area : String,

        @Min(value=0)
        val page : Int
    )


    /**
     * Search Service
     */
    abstract class Result(
        val shopId: Long
    )

    class IdScoreResult(
        shopId: Long,
        val score: Float
    ) : Result(shopId)



}