package mashup.spring.elegant.search.domain.search

import mashup.spring.elegant.search.dto.ShopDto
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.GeoPointField
import org.springframework.data.elasticsearch.core.geo.GeoPoint

@Document(indexName = "shop")
class Shop(
    @Id
    val id: Long,

    @Field(type = FieldType.Keyword)
    val category: String,

    @Field(type = FieldType.Text)
    val shopName: String,

    @Field(type = FieldType.Object)
    val review: Review,

    @Field(type = FieldType.Date, pattern = ["yyyy-MM-dd"])
    val createdDate: String,

    @Field(type = FieldType.Nested)
    val menuList: List<Menu>,

    @Field(type = FieldType.Integer_Range)
    val deliveryTime: Range,

    @Field(type = FieldType.Integer_Range)
    val deliveryTip: Range,

    @Field(type = FieldType.Keyword)
    val deliveryArea: List<String>,

    @Field(type = FieldType.Integer)
    val minAmount: Int,

    @Field(type = FieldType.Object)
    @GeoPointField
    val location: GeoPoint,

    @Field(type = FieldType.Object)
    val openHours : List<Open>

) {
    companion object{
        fun from(dto: ShopDto) : Shop = Shop(
            id = dto.id,
            category = dto.category,
            shopName = dto.shopName,
            review = dto.review,
            createdDate = dto.createdDate,
            menuList = dto.menuList,
            deliveryTime = dto.deliveryTime,
            deliveryTip = dto.deliveryTip,
            deliveryArea = dto.deliveryArea,
            minAmount = dto.minAmount,
            location = dto.location,
            openHours = dto.openHours
        )
    }




    /**
     * Inner class
     */

    class Range(
        @Field(type = FieldType.Integer)
        val gte : Int,
        @Field(type = FieldType.Integer)
        val lte : Int
    ){}

    class Menu(
        @Field(type = FieldType.Text)
        val name: String,
        @Field(type = FieldType.Text)
        val content: String
    ){}

    class Review(
        @Field(type = FieldType.Float)
        val avg: Float,
        @Field(type = FieldType.Integer)
        val count: Int
    ){}

    class Open(
        @Field(type = FieldType.Keyword)
        val week: String,

        @Field(type = FieldType.Integer_Range)
        val hour: Range
    ){}
}