package mashup.spring.elegant.search.util

import java.time.DayOfWeek
import java.time.DayOfWeek.*

fun translateDay(day : DayOfWeek) = when(day){
    MONDAY -> "월요일"
    TUESDAY -> "화요일"
    WEDNESDAY -> "수요일"
    THURSDAY -> "목요일"
    FRIDAY -> "금요일"
    SATURDAY -> "토요일"
    SUNDAY -> "일요일"

}