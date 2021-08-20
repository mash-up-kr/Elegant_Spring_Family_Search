package mashup.spring.elegant.search.application.filter

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply

class LogstashFilter : Filter<ILoggingEvent>() {
    override fun decide(event: ILoggingEvent): FilterReply {
        // 일단 컨셉만 로깅 방법은 추후에 클래스로 분리하여 관리 할 것.
        return if (event.message.startsWith("SEARCH")){
            FilterReply.ACCEPT
        } else {
            FilterReply.DENY
        }
    }
}