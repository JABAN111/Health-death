package mobile.schedule

import com.google.protobuf.Empty
import mobile.schedule.grpc.PingResponse
import mobile.schedule.grpc.ScheduleServiceGrpcKt

class ScheduleService : ScheduleServiceGrpcKt.ScheduleServiceCoroutineImplBase() {
    override suspend fun ping(request: Empty): PingResponse {
        return PingResponse.newBuilder().setMessage("Pong!").build()
    }
}