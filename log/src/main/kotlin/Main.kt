package mobile.log

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionServiceV1
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess


fun main() {

    val portEnv = System.getenv("LOG_PORT")
    if (portEnv.isNullOrEmpty()) {
        println("Port is required")
        exitProcess(1)
    }
    val port = portEnv.toInt()

    val server: Server = ServerBuilder
        .forPort(port)
        .addService(LogService())
        .addService(ProtoReflectionServiceV1.newInstance())// нужно для включения рефлексии(чтобы grpccurl/grpcui работали)
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