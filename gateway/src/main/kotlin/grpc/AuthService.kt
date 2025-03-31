package grpc

import com.google.protobuf.Empty
import io.grpc.ManagedChannel
import mobile.auth.grpc.AuthorizationGrpcKt

class AuthService(
    channel: ManagedChannel
) : AbstractGrpcService(channel) {
    private val client: AuthorizationGrpcKt.AuthorizationCoroutineStub = AuthorizationGrpcKt.AuthorizationCoroutineStub(channel)
    override suspend fun ping(): String  {
        log.debug("pinging auth service")
        val ans = client.ping(Empty.getDefaultInstance())
        return ans.message
    }

}