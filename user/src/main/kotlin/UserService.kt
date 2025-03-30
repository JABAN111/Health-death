package mobile.train

import com.google.protobuf.Empty
import mobile.user.grpc.PingResponse
import mobile.user.grpc.UserServiceGrpcKt


class TrainService : UserServiceGrpcKt.UserServiceCoroutineImplBase(){
    override suspend fun ping(request: Empty): PingResponse {
        return PingResponse.newBuilder()
            .setMessage("Pong")
            .build()
    }


    override suspend fun test(request: PingResponse): PingResponse {
        return PingResponse.newBuilder()
            .setMessage("Test")
            .build()
    }
}

