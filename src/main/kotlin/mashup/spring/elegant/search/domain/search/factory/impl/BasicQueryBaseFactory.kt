package mashup.spring.elegant.search.domain.search.factory.impl

import mashup.spring.elegant.search.domain.search.boost.CATEGORY_BOOST
import mashup.spring.elegant.search.domain.search.boost.MENU_CONTENT_BOOST
import mashup.spring.elegant.search.domain.search.boost.MENU_NAME_BOOST
import mashup.spring.elegant.search.domain.search.boost.SHOP_NAME_BOOST
import mashup.spring.elegant.search.domain.search.enums.SearchType
import mashup.spring.elegant.search.domain.model.ShopField
import mashup.spring.elegant.search.domain.search.factory.QueryBaseFactory
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.springframework.stereotype.Component

@Component
class BasicQueryBaseFactory : QueryBaseFactory {

    override fun create(type: SearchType, term: String): BoolQueryBuilder = when (type) {

        SearchType.KEYWORD -> QueryBuilders.boolQuery()
            .should(
                QueryBuilders.matchQuery(ShopField.SHOP_NAME.field, term).boost(SHOP_NAME_BOOST)
            )
            .should(
                QueryBuilders.matchQuery(ShopField.MENU_NAME.field, term).boost(MENU_NAME_BOOST)
            )
            .should(
                QueryBuilders.matchQuery(ShopField.MENU_CONTENT.field, term).boost(MENU_CONTENT_BOOST)
            )


        SearchType.CATEGORY -> QueryBuilders.boolQuery()
            .should(
                QueryBuilders.matchQuery(ShopField.CATEGORY.field, term).boost(CATEGORY_BOOST)
            )
    }
}