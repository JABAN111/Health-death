package logger.model

import java.time.LocalTime

class Log {
    var id: Long = 0
    lateinit var logLevel: String
    lateinit var message: String
    // NOTE: это инициализировать руками не нужно, передается из dto
    lateinit var createdAt: LocalTime
}