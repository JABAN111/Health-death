package logger

import logger.service.LoggerService
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPubSub

class RedisSubscriber(
    private val host: String = "localhost",
    private val port: Int = 6379,
    private val channel: String,
    private val loggerService: LoggerService
) : Runnable {

    private val jedis = Jedis(host, port)

    override fun run() {
        jedis.subscribe(object : JedisPubSub() {
            override fun onMessage(channel: String, data: String) {
                handleMessage(channel, data)
            }
        }, channel)
    }

    private fun handleMessage(channel: String, message: String) {
        // TODO здесь СИНХРОННАЯ логика для сохранения в постгрю нужна
        //  попытка втащить сюда корутину для оптимизации потенциально черевата
        loggerService.save(channel, message)
        println("Получено сообщение на канале $channel: $message")
    }
}