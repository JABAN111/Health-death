package mobile.train

import com.google.protobuf.Empty
import mobile.train.grpc.PingResponse
import mobile.train.grpc.Train
import mobile.train.grpc.TrainServiceGrpcKt

class TrainService : TrainServiceGrpcKt.TrainServiceCoroutineImplBase() {

    override suspend fun ping(request: Empty): PingResponse {
        return PingResponse.newBuilder()
            .setMessage("Pong")
            .build()
    }
}
