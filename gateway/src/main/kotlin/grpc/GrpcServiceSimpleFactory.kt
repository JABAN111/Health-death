package grpc

import io.grpc.ManagedChannelBuilder

enum class GrpcServiceType {
    AUTH, DIARY, LOG, SCHEDULE, TRAIN, USER;
}

class GrpcServiceSimpleFactory {
    companion object {
        fun createService(type: GrpcServiceType, host: String, port: Int): AbstractGrpcService {
            val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build()
            return when (type) {
                GrpcServiceType.AUTH -> AuthService(channel)
                GrpcServiceType.DIARY -> DiaryService(channel)
                GrpcServiceType.LOG -> LogService(channel)
                GrpcServiceType.SCHEDULE -> ScheduleService(channel)
                GrpcServiceType.TRAIN -> TrainService(channel)
                GrpcServiceType.USER -> UserService(channel)
            }
        }
    }
}


