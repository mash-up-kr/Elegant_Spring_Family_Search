package mashup.spring.elegant.search.domain.search
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.*
import org.springframework.data.elasticsearch.core.geo.GeoPoint

@Document(indexName = "shop3")
class Shop(
    @Field(type = FieldType.Auto, name = "shop_id")
    var shop_id: Long,
    @Field(type = FieldType.Auto, name = "category")
    var category: List<Category>,
    @Field(type = FieldType.Auto, name = "shop_name")
    var shop_name: String,
    @Field(type = FieldType.Auto, name = "review")
    var review: Review,
    @Field(type = FieldType.Auto, name = "created_date")
    var created_date: String,
    @Field(type = FieldType.Auto, name = "menu")
    var menu: List<Menu>,
    @Field(type = FieldType.Auto, name = "delivery_time")
    var delivery_time: Range,
    @Field(type = FieldType.Auto, name = "delivery_tip")
    var delivery_tip: Range,
    @Field(type = FieldType.Auto, name = "delivery_area")
    var delivery_area: List<String>,
    @Field(type = FieldType.Auto, name = "min_amount")
    var min_amount: Int,
    @Field(type = FieldType.Auto, name = "location")
    var location: GeoPoint,
    @Field(type = FieldType.Auto, name = "open_hour")
    var open_hour : List<Open>,
    @Field(type = FieldType.Auto, name = "take_out")
    var take_out : Boolean

){


    /**
     * Inner class
     */

    class Range(
        @Field(type = FieldType.Integer, name = "gte")
        val gte : Int,
        @Field(type = FieldType.Integer, name = "lte")
        val lte : Int
    ){}

    class Menu(
        @Field(type = FieldType.Text, name ="name")
        val name: String,
        @Field(type = FieldType.Text, name ="content")
        val content: String
    ){}

    class Review(
        @Field(type = FieldType.Float, name="avg")
        val avg: Float,
        @Field(type = FieldType.Integer, name="count")
        val count: Int
    ){}

    class Open(
        @Field(type = FieldType.Keyword, name="week")
        val week: String,

        @Field(type = FieldType.Integer_Range, name="hour")
        val hour: Range
    ){}
}
