package mobile.rest

import grpc.*
import io.ktor.server.application.*
//TODO: сделать просто возвращаемым куском
lateinit var diaryClient: DiaryService
lateinit var logClient: LogService
lateinit var scheduleClient: ScheduleService
lateinit var trainClient: TrainService
lateinit var userClient: UserService


/**
 * Данный модуль нужен исключительно для первичной конфигурации grpcClient
 * Берет переменные окружения(конфигурируются через docker compose), если пусты, то проверяет application.yaml
 *
 * В resources/application.yaml конфигурация лежит для локального запуска. При необходимости вы можете обновить application.yaml
 * Или передать переменные окружения через export VARIABLE_NAME
 * З.Ы. для удаления есть команда unset VARIABLE_NAME
 *
 */
fun Application.configureGrpcClients() {
    val diaryHost = System.getenv("DIARY_ADDRESS") ?: environment.config.propertyOrNull("services.diary.host")?.getString() ?: "diary"
    val diaryPort = System.getenv("DIARY_PORT")?.toInt() ?: environment.config.propertyOrNull("services.diary.port")?.getString()?.toInt() ?: 8080

    val logHost = System.getenv("LOG_ADDRESS") ?: environment.config.propertyOrNull("services.log.host")?.getString() ?: "log"
    val logPort = System.getenv("LOG_PORT")?.toInt() ?: environment.config.propertyOrNull("services.log.port")?.getString()?.toInt() ?: 8080

    val scheduleHost = System.getenv("SCHEDULE_ADDRESS") ?: environment.config.propertyOrNull("services.schedule.host")?.getString() ?: "schedule"
    val schedulePort = System.getenv("SCHEDULE_PORT")?.toInt() ?: environment.config.propertyOrNull("services.schedule.port")?.getString()?.toInt() ?: 8080

    val trainHost = System.getenv("TRAIN_ADDRESS") ?: environment.config.propertyOrNull("services.train.host")?.getString() ?: "train"
    val trainPort = System.getenv("TRAIN_PORT")?.toInt() ?: environment.config.propertyOrNull("services.train.port")?.getString()?.toInt() ?: 8080

    val userHost = System.getenv("USER_ADDRESS") ?: environment.config.propertyOrNull("services.user.host")?.getString() ?: "user"
    val userPort = System.getenv("USER_PORT")?.toInt() ?: environment.config.propertyOrNull("services.user.port")?.getString()?.toInt() ?: 8080

    diaryClient = GrpcServiceSimpleFactory.createService(GrpcServiceType.DIARY, diaryHost, diaryPort) as DiaryService
    logClient = GrpcServiceSimpleFactory.createService(GrpcServiceType.LOG, logHost, logPort) as LogService
    scheduleClient = GrpcServiceSimpleFactory.createService(GrpcServiceType.SCHEDULE, scheduleHost, schedulePort) as ScheduleService
    trainClient = GrpcServiceSimpleFactory.createService(GrpcServiceType.TRAIN, trainHost, trainPort) as TrainService
    userClient = GrpcServiceSimpleFactory.createService(GrpcServiceType.USER, userHost, userPort) as UserService
}
