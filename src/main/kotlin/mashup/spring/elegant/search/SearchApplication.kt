package mashup.spring.elegant.search

import mashup.spring.elegant.search.config.YamlPropertySourceFactory
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.PropertySource

//현재 유레카클라이언트 미사용
//@EnableEurekaClient
@SpringBootApplication
@PropertySource("classpath:search.yaml", factory = YamlPropertySourceFactory::class)
class SearchApplication
fun main(args: Array<String>) {
	runApplication<SearchApplication>(*args)
}
