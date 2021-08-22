package mashup.spring.elegant.search.domain.search

enum class ShopField (
    val field : String
){

    SHOP_ID("shop_id"),
    CATEGORY("category"),
    SHOP_NAME("shop_name"),
    REVIEW_AVG("review.avg"),
    REVIEW_COUNT("review.count"),
    CREATED_DATE("created_date"),
    MENU_NAME("menu.name"),
    MENU_CONTENT("menu.content"),
    DELIVERY_TIME("delivery_time"), //빼도 될것같은데?
    DELIVERY_TIP("delivery_tip"), //빼도 될것같은데?
    DELIVERY_AREA("delivery_area"),
    MIN_AMOUNT("min_amount"), //빼도 될것같은데?
    LOCATION("location"),
    OPEN_HOUR_WEEK("open_hour.week"),
    OPEN_HOUR_HOUR("open_hour.hour"),
    TAKE_OUT("take_out"); // 나중에 필요


}