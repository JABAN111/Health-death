import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPubSub

class RedisSubscriber(
    private val host: String = "localhost",
    private val port: Int = 6379,
    private val channel: String
) : Runnable {

    private val jedis = Jedis(host, port)

    override fun run() {
        jedis.subscribe(object : JedisPubSub() {
            override fun onMessage(channel: String, message: String) {
                handleMessage(channel, message)
            }
        }, channel)
    }

    private fun handleMessage(channel: String, message: String) {
        // TODO здесь СИНХРОННАЯ логика для сохранения в постгрю нужна
        println("Получено сообщение на канале $channel: $message")
    }
}