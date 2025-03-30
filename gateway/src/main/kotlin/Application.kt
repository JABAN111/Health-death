package mobile

import io.ktor.server.application.*
import mobile.config.ConfigProvider
//import mobile.config.mustLoadConfig
//import kotlin.system.exitProcess
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val defaultPath = "config.yaml"

fun main(args: Array<String>) {
//    var path = defaultPath
//    System.getenv()["configPath"]?.let {
//        println("Taking config from path: $it")
//        path = it
//    }
//
//    path = "../config/gateway/config.yaml"
//    val httpConfig = mustLoadConfig(path)
//    println("Loaded config: $httpConfig")

    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    println(environment.config)
    val ports = environment.config.propertyOrNull("ktor.deployment.port")?.getString() ?: "8080"
    println(ports)
    val port = environment.config.propertyOrNull("services.diary.url")?.getString() ?: "8080"
    println(port)

//    val config = ConfigProvider.appConfig // Получаем конфигурацию
//    val configPath = System.getenv()["configPath"] ?: "../config/gateway/config.yaml"
//    val httpConfig = mustLoadConfig(configPath)

//    println("Loaded config: $httpConfig")
    configureHTTP()
    configureRouting()
}
