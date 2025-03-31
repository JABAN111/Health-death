package grpc

import com.google.protobuf.Empty
import grpc.AbstractGrpcService
import io.grpc.ManagedChannel
import mobile.train.grpc.TrainServiceGrpcKt

class TrainService(
    channel: ManagedChannel
) : AbstractGrpcService(channel) {
    private val client: TrainServiceGrpcKt.TrainServiceCoroutineStub = TrainServiceGrpcKt.TrainServiceCoroutineStub(channel)

    override suspend fun ping(): String  {
        log.debug("pinging train service")
        val ans = client.ping(Empty.getDefaultInstance())
        return ans.message
    }



}