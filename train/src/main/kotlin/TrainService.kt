package mobile.train

import com.google.protobuf.Empty
import mobile.train.grpc.TrainPingResponse
import mobile.train.grpc.TrainServiceGrpcKt

class TrainService : TrainServiceGrpcKt.TrainServiceCoroutineImplBase() {

    override suspend fun ping(request: Empty): TrainPingResponse {
        return TrainPingResponse.newBuilder()
            .setMessage("Pong")
            .build()
    }
}
