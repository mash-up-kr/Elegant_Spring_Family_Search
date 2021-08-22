package mashup.spring.elegant.search.dto

import mashup.spring.elegant.search.domain.search.enums.Category
import mashup.spring.elegant.search.domain.search.enums.Feature
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

        @NotBlank//공백 허용 x
        val area : String,

        @Min(value=0)
        val page : Int,

        val feature : List<Feature>
    )
    class CategoryRequest(

        @Size(min = 2)
        val category: Category,

        @NotNull
        val lat : Double,

        @NotNull
        val lon : Double,

        @NotBlank//공백 허용 x
        val area : String,

        @Min(value=0)
        val page : Int,

        val feature : List<Feature>
    ){
        fun toRequest() = Request(category.field, lat, lon, area, page, feature)
    }



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