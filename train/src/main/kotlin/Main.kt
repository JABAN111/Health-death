package mobile.train

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionServiceV1
import kotlinx.coroutines.runBlocking

fun main() {
    val server: Server = ServerBuilder
        .forPort(23212)
        .addService(TrainService())
        .addService(ProtoReflectionServiceV1.newInstance())
        .build()
        .start()

    println("Server started on port 23212")
    Runtime.getRuntime().addShutdownHook(Thread {
        println("Shutting down server...")
        server.shutdown()
    })

    runBlocking {
        server.awaitTermination()
    }
}