package mobile

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import grpc.AbstractGrpcService
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.statuspages.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Application.configureRouting() {

    install(ContentNegotiation) {
        json()
    }

    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            val currentTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
//            TODO: create ExceptionWrapper
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Not found", "time" to currentTime))
        }
    }

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


        get("/ping") {
            this@configureRouting.log.debug("/ping")

            val pingResults = mutableListOf<String>()

            val authPing = pingService(authClient)
            val diaryPing = pingService(diaryClient)
            val logPing = pingService(logClient)
            val schedulePing = pingService(scheduleClient)
            val trainPing = pingService(trainClient)
            val userPing = pingService(userClient)

            pingResults.add("Authorization Service: $authPing")
            pingResults.add("Diary Service: $diaryPing")
            pingResults.add("Log Service: $logPing")
            pingResults.add("Schedule Service: $schedulePing")
            pingResults.add("Train Service: $trainPing")
            pingResults.add("User Service: $userPing")

            call.respond(HttpStatusCode.OK, mapOf("pingResults" to pingResults))
        }
    }
}

private suspend fun pingService(client: AbstractGrpcService): String {
    return runCatching {
        log.debug("Pinging client {}", client)
        client.ping()
    }.getOrElse {
        "Unavailable"
    }.takeIf { it.isNotBlank() } ?: "Unavailable"
}

