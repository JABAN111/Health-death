package grpc

import com.google.protobuf.Empty
import grpc.AbstractGrpcService
import io.grpc.ManagedChannel
import mobile.schedule.grpc.ScheduleServiceGrpcKt

class ScheduleService(
    channel: ManagedChannel
) : AbstractGrpcService(
    channel
){
    private val client = ScheduleServiceGrpcKt.ScheduleServiceCoroutineStub(channel)

    override suspend fun ping(): String  {
        log.debug("pinging schedule service")
        val ans = client.ping(Empty.getDefaultInstance())
        return ans.message
    }

}