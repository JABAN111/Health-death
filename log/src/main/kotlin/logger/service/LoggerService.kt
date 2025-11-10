package logger.service

interface LoggerService {
    fun save(logLevel: String, logData: String)
}