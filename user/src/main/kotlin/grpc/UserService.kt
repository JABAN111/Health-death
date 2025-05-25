package grpc

import com.google.protobuf.Empty
import mapper.toModel
import mobile.user.grpc.OnBoardingData
import mobile.user.grpc.OnBoardingResponse
import mobile.user.grpc.PushOnboardingRequest
import mobile.user.grpc.UserPingResponse
import mobile.user.grpc.UserServiceGrpcKt
import service.OnBoardingService


class UserService(
    private val onBoardingService: OnBoardingService
) : UserServiceGrpcKt.UserServiceCoroutineImplBase(){
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

    override suspend fun pushOnboardingData(request: PushOnboardingRequest): OnBoardingResponse {
        val userId: String = request.user.userId
        val onBoardingData = request.toModel()
        onBoardingService.create(userId, onBoardingData)
        return super.pushOnboardingData(request)//TODO
    }
}

