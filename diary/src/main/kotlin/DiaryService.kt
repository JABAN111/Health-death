package mobile.train

import com.google.protobuf.Empty
import mobile.diary.grpc.DiaryPingResponse
import mobile.diary.grpc.DiaryServiceGrpcKt

class DiaryService : DiaryServiceGrpcKt.DiaryServiceCoroutineImplBase() {
    override suspend fun ping(request: Empty): DiaryPingResponse {
        return DiaryPingResponse.newBuilder()
            .setMessage("Pong")
            .build()
    }
}