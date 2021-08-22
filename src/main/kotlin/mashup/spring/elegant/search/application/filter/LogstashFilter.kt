package mashup.spring.elegant.search.application.filter

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply
import mashup.spring.elegant.search.support.logging.SearchLogging

class LogstashFilter : Filter<ILoggingEvent>() {
    override fun decide(event: ILoggingEvent): FilterReply {
        return if (event.message.startsWith("[${SearchLogging.SEARCH_KEYWORD}]")){
            FilterReply.ACCEPT
        } else {
            FilterReply.DENY
        }
    }
}