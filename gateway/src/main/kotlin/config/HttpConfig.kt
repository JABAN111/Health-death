package mobile.config

import mobile.schedule.grpc.ScheduleServiceGrpcKt


data class AppConfig(
    val diaryAddress: String,
    val authorizationAddress: String,
    val logAddress: String,
    val scheduleAddress: String,
    val trainAddress: String,
    val userAddress: String,
)

///**
// * Функция для загрузки конфигурации приложения.
// * Приоритетно берет данные из переменных окружения, если их нет, то из файла с конфигом.
// *
// * В случае отсутствия указания на файл падает с ошибкой, не давая стартануть gateway
// * @param configPath путь к файлу конфигурации
// *
// * @throws ConfigException.Missing если не хватает данных для конфигурации
// */
//fun mustLoadConfig(configPath: String): AppConfig {
//    val env = System.getenv()
//
//    val file = File(configPath.trim())
//    if (!file.exists()) {
//        throw IllegalArgumentException("Config file not found")
//    }
//
//    // Парсим YAML файл
//    val fileConfig = ConfigFactory.parseFile(file).resolve()
//    val config = ConfigFactory.systemEnvironment().withFallback(fileConfig).resolve()
//
//    return AppConfig(
//        diaryAddress = env["DIARY_ADDRESS"] ?: config.getString("services.diary.url"),
//        authorizationAddress = env["AUTHORIZATION_ADDRESS"] ?: config.getString("services.authorization.url"),
//        logAddress = env["LOG_ADDRESS"] ?: config.getString("services.log.url"),
//        scheduleAddress = env["SCHEDULE_ADDRESS"] ?: config.getString("services.schedule.url"),
//        trainAddress = env["TRAIN_ADDRESS"] ?: config.getString("services.train.url"),
//        userAddress = env["USER_ADDRESS"] ?: config.getString("services.user.url")
//    )
//}
//

object ConfigProvider {

//    private val config: Config = ConfigFactory.load("application.yaml")

//    val appConfig: AppConfig = AppConfig(
//        diaryAddress = config.getString("services.diary.url"),
//        authorizationAddress = config.getString("services.authorization.url"),
//        logAddress = config.getString("services.log.url"),
//        scheduleAddress = config.getString("services.schedule.url"),
//        trainAddress = config.getString("services.train.url"),
//        userAddress = config.getString("services.user.url")
//    )
}
