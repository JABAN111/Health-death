import com.google.protobuf.Empty
import mobile.log.grpc.LogPingResponse
import mobile.log.grpc.LogRequest
import mobile.log.grpc.LogServiceGrpcKt

class LogService : LogServiceGrpcKt.LogServiceCoroutineImplBase() {
    override suspend fun log(request: LogRequest) : Empty {
        println("Received log request: ${request.message}")

        return Empty.getDefaultInstance()//NOTE: возможно есть проще способ, ибо в go это просто nil
    }

    override suspend fun ping(request: Empty) : LogPingResponse {
        println("Received ping request")
        return LogPingResponse.newBuilder().setMessage("Pong").build()
    }
}