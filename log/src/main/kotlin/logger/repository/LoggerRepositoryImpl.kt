package logger.repository

import database.LogQueries
import logger.model.Log

class LoggerRepositoryImpl(
    private val logQueries: LogQueries
) : LoggerRepository {

    override fun save(log: Log) {
        logQueries.insertLog(log_level = log.logLevel, message = log.message)
    }
}