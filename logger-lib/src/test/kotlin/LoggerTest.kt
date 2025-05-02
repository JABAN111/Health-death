import com.logger.Logger
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class LoggerTest {

    @Test
    fun info() {
        val log = Logger.getLogger()
        log.info("service started")
    }

    @Test
    fun warn() {
        val log = Logger.getLogger()
        log.warn("user input was invalid")
    }

    @Test
    fun error() {
        val log = Logger.getLogger()
        log.error("fatal error, we will cry...")
    }
}