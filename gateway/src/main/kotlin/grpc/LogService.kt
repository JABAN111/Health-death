package grpc

import com.google.protobuf.Empty
import grpc.AbstractGrpcService
import io.grpc.ManagedChannel
import mobile.log.grpc.LogServiceGrpcKt

class LogService(
    channel: ManagedChannel,
) : AbstractGrpcService(channel) {
    private val client = LogServiceGrpcKt.LogServiceCoroutineStub(channel)

    override suspend fun ping(): String  {
        log.debug("pinging log service")
        val ans = client.ping(Empty.getDefaultInstance())
        return ans.message
    }

}