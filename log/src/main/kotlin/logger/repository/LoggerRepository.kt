package logger.repository

import logger.model.Log

interface LoggerRepository {
    fun save(log: Log)
}