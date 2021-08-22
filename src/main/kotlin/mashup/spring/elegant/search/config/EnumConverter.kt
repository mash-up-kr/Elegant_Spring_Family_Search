package mashup.spring.elegant.search.config

import mashup.spring.elegant.search.domain.search.enums.Category
import mashup.spring.elegant.search.domain.search.enums.Feature
import org.springframework.core.convert.converter.Converter

class FeatureEnumConverter : Converter<String, Feature> {
    override fun convert(source: String): Feature {
        return Feature.valueOf(source.uppercase())
    }
}

class CategoryEnumConverter : Converter<String, Category> {
    override fun convert(source: String): Category {
        return Category.valueOf(source.uppercase())
    }
}