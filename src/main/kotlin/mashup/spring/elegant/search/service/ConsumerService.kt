package mashup.spring.elegant.search.service

import mashup.spring.elegant.search.domain.search.Shop
import mashup.spring.elegant.search.dto.ShopDto
import mashup.spring.elegant.search.repository.ShopRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.validation.Valid


@Service
@Transactional
class ConsumerService (
    private val shopRepository: ShopRepository
){

    //변경감지: 병합
    fun index(@Valid dto: ShopDto): Long{
        val save = shopRepository.save(Shop.from(dto))
        return save.id
    }

    //TODO: 일부 변경 가능한 modify 만들기

}