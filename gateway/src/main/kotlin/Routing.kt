package mobile

import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.system.exitProcess

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/health") {
            call.respondText("OK")
        }

        get("/api/v1") {
            call.respondText("API v1")
        }
    }
}
