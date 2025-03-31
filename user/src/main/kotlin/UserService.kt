package mobile.train

import com.google.protobuf.Empty
import mobile.user.grpc.UserPingResponse
import mobile.user.grpc.UserServiceGrpcKt


class TrainService : UserServiceGrpcKt.UserServiceCoroutineImplBase(){
    override suspend fun ping(request: Empty): UserPingResponse {
        return UserPingResponse.newBuilder()
            .setMessage("Pong")
            .build()
    }


    override suspend fun test(request: UserPingResponse): UserPingResponse {
        return UserPingResponse.newBuilder()
            .setMessage("Test")
            .build()
    }
}

