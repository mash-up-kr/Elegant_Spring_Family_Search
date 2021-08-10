package mashup.spring.elegant.search

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

//현재 유레카클라이언트 미사용
//@EnableEurekaClient
@SpringBootApplication
class SearchApplication

fun main(args: Array<String>) {
	runApplication<SearchApplication>(*args)
}
