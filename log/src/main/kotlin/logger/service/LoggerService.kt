package logger.service

import com.logger.Wrapper

interface LoggerService {
    fun save(logLevel: String, logData: String)
}