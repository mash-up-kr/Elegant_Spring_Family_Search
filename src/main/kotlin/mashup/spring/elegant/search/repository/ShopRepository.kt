package mashup.spring.elegant.search.repository

import mashup.spring.elegant.search.domain.search.Shop
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ShopRepository : ElasticsearchRepository<Shop, Long> {




}