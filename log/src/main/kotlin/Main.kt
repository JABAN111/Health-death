import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPubSub
import redis.clients.jedis.UnifiedJedis

fun main() {
    // Установка значения
    val jedis = UnifiedJedis("redis://localhost:6379")
    val res1 = jedis.set("bike:1", "Deimos")
    println(res1) // OK

    val res2 = jedis["bike:1"]
    println(res2) // Deimos

    // Подписчик в отдельном потоке
    val subscriberThread = Thread {
        val jSubscriber = Jedis("localhost", 6379)
        jSubscriber.subscribe(object : JedisPubSub() {
            override fun onMessage(channel: String, message: String) {
                println("Получено сообщение на канале $channel: $message")
            }
        }, "info")
    }
    subscriberThread.start()

    // Даем немного времени подписчику подключиться
    Thread.sleep(1000)

    // Публикация сообщений
//    val jPublisher = Jedis("localhost", 6379)
//    repeat(5) {
//        jPublisher.publish("channel", "test message $it")
//        Thread.sleep(500) // для наглядности
//
//    }

    // Завершение
//    Thread.sleep(2000) // ждем, чтобы сообщения были получены
    subscriberThread.interrupt() // завершение потока, если надо

}


//package mobile.log
//
//import redis.clients.jedis.Jedis
//import redis.clients.jedis.JedisPubSub
//import redis.clients.jedis.UnifiedJedis
//
//
//fun main() {
//
//
//    val jedis = UnifiedJedis("redis://localhost:6379")
//
//    val res1 = jedis.set("bike:1", "Deimos")
//    println(res1) // OK
//
//    val res2 = jedis["bike:1"]
//    println(res2) // Deimos
//
//    val jPublisher = Jedis()
//    var cnt = 2
//    do {
//        jPublisher.publish("channel", "test message")
//        cnt--
//    }while (cnt != 0)
//
//    val jSubscriber = Jedis()
//    jSubscriber.subscribe(object : JedisPubSub() {
//        override fun onMessage(channel: String, message: String) {
//            println(channel)
//            println(message)
//        }
//    }, "channel")
//    // Code that interacts with Redis...
////    jedis.close()
////    val redisClient = RedisClient.create("redis://localhost:6379")
////    val connection: StatefulRedisConnection<String, String> = redisClient.connect()
////    val commands: RedisCommands<String, String> = connection.sync()
////    println(commands.get("foo"))
////    commands.set("lol","idiot?")
////    val record: Map<String, String> = commands.hgetall("recordName")
////    commands.rpush("lol","sho")
////    println(commands.get("lol"))
////    val pubSubConnection: StatefulRedisPubSubConnection<String, String> = redisClient.connectPubSub()
//
//
////    val portEnv = System.getenv("LOG_PORT")
////    if (portEnv.isNullOrEmpty()) {
////        println("Port is required")
////        exitProcess(1)
////    }
////    val port = portEnv.toInt()
////
////    val server: Server = ServerBuilder
////        .forPort(port)
////        .addService(LogService())
////        .addService(ProtoReflectionServiceV1.newInstance())// нужно для включения рефлексии(чтобы grpccurl/grpcui работали)
////        .build()
////        .start()
////
////    println("Server started on port $port")
////    Runtime.getRuntime().addShutdownHook(Thread {
////        println("Shutting down server...")
////        server.shutdown()
////    })
////
////    runBlocking {
////        server.awaitTermination()
////    }
//}