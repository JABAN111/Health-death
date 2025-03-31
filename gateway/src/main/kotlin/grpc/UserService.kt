package grpc

import com.google.protobuf.Empty
import grpc.AbstractGrpcService
import io.grpc.ManagedChannel
import mobile.user.grpc.UserServiceGrpcKt

class UserService(
    channel: ManagedChannel
) : AbstractGrpcService(channel) {

    private val client = UserServiceGrpcKt.UserServiceCoroutineStub(channel)

    override suspend fun ping(): String  {
        log.debug("pinging user service")
        val ans = client.ping(Empty.getDefaultInstance())
        return ans.message
    }

}