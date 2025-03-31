package mobile

import io.ktor.server.application.*
import io.ktor.util.logging.*


val log = KtorSimpleLogger("mobile.gateway.ApplicationKt")

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    log.info("Starting application")
    log.debug("Debug level is enabled")
    configureGrpcClients()
    configureHTTP()
    configureRouting()//TODO routing split & reconfigure for api of frontend
}
