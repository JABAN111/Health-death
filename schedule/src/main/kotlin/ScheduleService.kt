package mobile.schedule

import com.google.protobuf.Empty
import mobile.schedule.grpc.SchedulePingResponse
import mobile.schedule.grpc.ScheduleServiceGrpcKt

class ScheduleService : ScheduleServiceGrpcKt.ScheduleServiceCoroutineImplBase() {
    override suspend fun ping(request: Empty): SchedulePingResponse {
        return SchedulePingResponse.newBuilder().setMessage("Pong!").build()
    }
}