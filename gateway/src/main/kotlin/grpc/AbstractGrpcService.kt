package grpc

import io.grpc.ManagedChannel
import io.ktor.util.logging.*
import java.io.Closeable
import java.util.concurrent.TimeUnit

abstract class AbstractGrpcService(
    private val channel: ManagedChannel
) : Closeable {
    val log = KtorSimpleLogger("com.example.RequestTracePlugin")


    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
    abstract suspend fun ping(): String
}