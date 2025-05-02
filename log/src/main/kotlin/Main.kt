import com.logger.LogType
import com.logger.Logger
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionServiceV1
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess


fun main() {
    for (type in LogType.entries) {
        val subscriber = RedisSubscriber(channel = type.channelName)

        val threadName = "redis-subscriber-${type.channelName}"
        Thread(subscriber, threadName).apply {
            isDaemon = true
            start()
        }

        println("Started subscriber for channel='${type.channelName}' on thread='$threadName'")
    }

    // FIXME
    val portEnv = "8080"//System.getenv("LOG_PORT")
    if (portEnv.isNullOrEmpty()) {
        println("Port is required")
        exitProcess(1)
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