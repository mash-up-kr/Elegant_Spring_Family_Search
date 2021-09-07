package mashup.spring.elegant.search.domain.model

/**
 * Elasticsearch 내 필드 이름 Enum 클래스
 */
enum class ShopField (
    val field : String
){

    SHOP_ID("shop_id"), // type: Long
    CATEGORY("category"), // type: ENUM (String)
    SHOP_NAME("shop_name"), // type: String
    REVIEW_AVG("review.avg"), // type: Float
    REVIEW_COUNT("review.count"), // type: Int
    CREATED_DATE("created_date"), // type: String(yyyy-mm-dd)
    MENU_NAME("menu.name"), // type: String -> Nested
    MENU_CONTENT("menu.content"), // type: String -> Nested
    DELIVERY_TIME("delivery_time"), //빼도 될것같은데?
    DELIVERY_TIP("delivery_tip"), //빼도 될것같은데?
    DELIVERY_AREA("delivery_area"), // type: String
    MIN_AMOUNT("min_amount"), //빼도 될것같은데?
    LOCATION("location"), // type: GeoPoint (lat:Double, lon:Double)
    OPEN_HOUR("open_hour"),
    OPEN_HOUR_WEEK("open_hour.week"), // type: String
    OPEN_HOUR_HOUR("open_hour.hour"), // type: Range
    FEATURE("feature")


}