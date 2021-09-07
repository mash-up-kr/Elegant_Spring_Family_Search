package mashup.spring.elegant.search.config

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.format.FormatterRegistry
import org.springframework.context.annotation.Configuration

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(FeatureEnumConverter())
        registry.addConverter(CategoryEnumConverter())
    }
}