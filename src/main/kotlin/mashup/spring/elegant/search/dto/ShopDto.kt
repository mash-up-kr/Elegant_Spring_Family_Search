package mashup.spring.elegant.search.dto

import mashup.spring.elegant.search.domain.search.Shop
import org.jetbrains.annotations.NotNull
import org.springframework.data.elasticsearch.core.geo.GeoPoint


data class ShopDto(

    @NotNull
    val id: Long,
    @NotNull
    val category: String,
    @NotNull
    val shopName: String,
    @NotNull
    val review: Shop.Review,
    @NotNull
    val createdDate: String,
    @NotNull
    val menuList: List<Shop.Menu>,
    @NotNull
    val deliveryTime: Shop.Range,
    @NotNull
    val deliveryTip: Shop.Range,
    @NotNull
    val deliveryArea: List<String>,
    @NotNull
    val minAmount: Int,
    @NotNull
    val location: GeoPoint,
    @NotNull
    val openHours : List<Shop.Open>

) {
}