package mobile.log

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionServiceV1
import kotlinx.coroutines.runBlocking

fun main() {
    val server: Server = ServerBuilder
        .forPort(23211)//TODO: configure
        .addService(LogService())
        .addService(ProtoReflectionServiceV1.newInstance())// нужно для включения рефлексии(чтобы grpccurl/grpcui работали)
        .build()
        .start()

    println("Server started on port 23211")
    Runtime.getRuntime().addShutdownHook(Thread {
        println("Shutting down server...")
        server.shutdown()
    })

    runBlocking {
        server.awaitTermination()
    }
}