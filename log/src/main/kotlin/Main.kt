import com.logger.LogType
import grpc.LogService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionServiceV1
import kotlinx.coroutines.runBlocking
import logger.RedisSubscriber
import logger.repository.LoggerRepository
import logger.repository.LoggerRepositoryImpl
import logger.service.LoggerService
import logger.service.LoggerServiceImpl
import kotlin.system.exitProcess


fun main() {
    val portEnv = System.getenv("LOG_PORT")
    if (portEnv.isNullOrEmpty()) {
        println("Port is required")
        exitProcess(1)
    }

    val keyDbHostEnv = System.getenv("KEYDB_HOST")
    val keyDbPortEnv = System.getenv("KEYDB_PORT")

    if (keyDbHostEnv.isNullOrEmpty()) {
        println("KEYDB_HOST env is required")
        exitProcess(1)
    }

    if (keyDbPortEnv.isNullOrEmpty()) {
        println("KEYDB_PORT env is required")
        exitProcess(1)
    }

    val keyDbPort = keyDbPortEnv.toInt()

    val loggerRepository: LoggerRepository = LoggerRepositoryImpl()
    val loggerService: LoggerService = LoggerServiceImpl(loggerRepository)

    for (type in LogType.entries) {
        val subscriber = RedisSubscriber(
            channel = type.channelName,
            host = keyDbHostEnv,
            port = keyDbPort,
            loggerService = loggerService
        )

        val threadName = "redis-subscriber-${type.channelName}"
        Thread(subscriber, threadName).apply {
            isDaemon = true
            start()
        }

        println("Started subscriber for channel='${type.channelName}' on thread='$threadName'")
    }


    val port = portEnv.toInt()

    val server: Server = ServerBuilder
        .forPort(port)
        .addService(LogService())
        .addService(ProtoReflectionServiceV1.newInstance()) // нужно для включения рефлексии(чтобы grpccurl/grpcui работали)
        .build()
        .start()

    println("Server started on port $port")
    Runtime.getRuntime().addShutdownHook(Thread {
        println("Shutting down server...")
        server.shutdown()
    })

    runBlocking {
        server.awaitTermination()
    }
}