package logger.service

import com.logger.Wrapper
import kotlinx.serialization.json.Json
import logger.model.Log
import logger.repository.LoggerRepository
import java.time.LocalTime

class LoggerServiceImpl(
    private val repository: LoggerRepository
) : LoggerService {

    override fun save(logLevel: String, logData: String) {
        val logDto = parseWrapper(logData)

        val log = Log().apply {
            this.logLevel = logLevel
            this.message = logDto.message
            this.createdAt = LocalTime.parse(logDto.time)
        }

        repository.save(log)
    }


    private fun parseWrapper(json: String): Wrapper {
        return Json.decodeFromString(json)
    }

}