package mashup.spring.elegant.search.config

import org.springframework.core.io.support.PropertySourceFactory
import kotlin.Throws
import java.io.IOException
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource

class YamlPropertySourceFactory : PropertySourceFactory {
    @Throws(IOException::class)
    override fun createPropertySource(name: String?, encodedResource: EncodedResource): PropertySource<*> {
        val factory = YamlPropertiesFactoryBean()
        factory.setResources(encodedResource.resource)
        val properties = factory.getObject()
        return PropertiesPropertySource(encodedResource.resource.filename!!, properties!!)
    }
}