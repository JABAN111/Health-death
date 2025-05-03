package logger.repository

import logger.model.Log

class LoggerRepositoryImpl: LoggerRepository {
    private val logDb = mutableMapOf<Long, Log>()

    override fun save(log: Log) {
        logDb[log.id] = log
        println("звуки эмуляции бд: $log")
    }
}