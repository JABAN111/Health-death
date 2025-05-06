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


    /**
     * TODO Комментарий удалить, он скорее для Кирилла, который собирался бд делать)
     * Как выглядят примерно сообщения
     * curl localhost:23200/api/ping
     * {"pingResults":["Diary Service: Pong","Log Service: Pong","Schedule Service: Pong!","Train Service: Pong","User Service: Pong"]}%
     * В логе у микросервиса log:
     *
     *      звуки эмуляции бд: logger.model.Log@59585b4d
     *      2025-05-03T10:02:40.412449506Z Получено сообщение на канале info: {"message":"pinged: [Diary Service: Pong, Log Service: Pong, Schedule Service: Pong!, Train Service: Pong, User Service: Pong]","time":"10:02:40.405903089"}
     *
     *
     */
    private fun handleMessage(channel: String, message: String) {
        // TODO здесь СИНХРОННАЯ логика для сохранения в постгрю нужна
        //  попытка втащить сюда корутину для оптимизации потенциально черевата
        loggerService.save(channel, message)
        println("Получено сообщение на канале $channel: $message")
    }
}