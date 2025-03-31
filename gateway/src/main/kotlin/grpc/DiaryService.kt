package grpc

import com.google.protobuf.Empty
import io.grpc.ManagedChannel
import mobile.diary.grpc.DiaryServiceGrpcKt


class DiaryService(
    channel: ManagedChannel
) : AbstractGrpcService(channel) {
    private val client: DiaryServiceGrpcKt.DiaryServiceCoroutineStub = DiaryServiceGrpcKt.DiaryServiceCoroutineStub(channel)

    override suspend fun ping(): String  {
        log.debug("pinging diary service")
        val ans = client.ping(Empty.getDefaultInstance())
        return ans.message
    }

}