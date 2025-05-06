package com.logger

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import redis.clients.jedis.Jedis
import java.time.LocalTime

class Logger private constructor(
    private val jedis: Jedis
) {
    companion object {
        private var logger: Logger? = null

        fun getLogger(host: String = "localhost", port: Int = 6379): Logger {
            return logger ?: Logger(Jedis(host, port)).also { logger = it }
        }
    }

    fun info(message: String) {
        log(LogType.INFO, message)
    }

    fun warn(message: String) {
        log(LogType.WARN, message)
    }

    fun error(message: String) {
        log(LogType.ERROR, message)
    }

    private fun log(type: LogType, message: String) {
        val wrapper = Wrapper(message)
        val jsonMessage = Json.encodeToString(wrapper)
        jedis.publish(type.channelName, jsonMessage)
    }
}

enum class LogType(val channelName: String) {
    INFO("info"),
    WARN("warn"),
    ERROR("error")
}

@Serializable
data class Wrapper(
    val message: String,
    val time: String = LocalTime.now().toString()
)
