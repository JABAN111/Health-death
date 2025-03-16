package mobile.train

import com.google.protobuf.Empty
import mobile.diary.grpc.DiaryServiceGrpcKt
import mobile.diary.grpc.PingResponse

class DiaryService : DiaryServiceGrpcKt.DiaryServiceCoroutineImplBase() {
    override suspend fun ping(request: Empty): PingResponse {
        return PingResponse.newBuilder()
            .setMessage("Pong")
            .build()
    }
}