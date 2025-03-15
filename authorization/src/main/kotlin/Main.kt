package mobile.auth

import io.grpc.Server
import io.grpc.ServerBuilder
import com.google.protobuf.Empty
import io.grpc.protobuf.services.ProtoReflectionServiceV1
import kotlinx.coroutines.runBlocking


fun main() {
    val server: Server = ServerBuilder
        .forPort(23210)
        .addService(AuthorizationService())
        .addService(ProtoReflectionServiceV1.newInstance())
        .build()
        .start()

    println("Server started on port 23210")
    Runtime.getRuntime().addShutdownHook(Thread {
        println("Shutting down server...")
        server.shutdown()
    })

    runBlocking {
      server.awaitTermination()
    }
}
