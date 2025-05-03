package mobile.rest

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.logger.Logger
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import grpc.AbstractGrpcService
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import mobile.auth.dto.UserDto
import mobile.auth.service.AuthService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Application.configureRouting(
    secret: String,
    issuer: String,
    audience: String,
    myRealm: String,
    authService: AuthService,
    kdbLogger: Logger
) {

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

    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
    routing {
        route("/auth") {
//login
            post("/sign-in") {
                val user = call.receive<UserDto>()
                val jwtResponse = authService.signIn(user)
                call.respond(jwtResponse)
            }
//registration
            post("/sign-up") {
                val user = call.receive<UserDto>()
                val jwtResponse = authService.signUp(user)
                this@configureRouting.log.debug("user saved with username: {}", jwtResponse)
                call.respond(jwtResponse)
            }
        }
        route("/api") {
            //diary
            route("/diary") {
                get("/unsafe-ping") {
                    val ping = pingService(diaryClient)
                    call.respond(ping)
                }


                authenticate("auth-jwt") {
                    get("/safe-ping") {
                        val ping = pingService(diaryClient)
                        call.respond(ping)
                    }
                }
            }

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

                val diaryPing = pingService(diaryClient)
                val logPing = pingService(logClient)
                val schedulePing = pingService(scheduleClient)
                val trainPing = pingService(trainClient)
                val userPing = pingService(userClient)

                pingResults.add("Diary Service: $diaryPing")
                pingResults.add("Log Service: $logPing")
                pingResults.add("Schedule Service: $schedulePing")
                pingResults.add("Train Service: $trainPing")
                pingResults.add("User Service: $userPing")

                kdbLogger.info("pinged: $pingResults")

                call.respond(HttpStatusCode.OK, mapOf("pingResults" to pingResults))
            }
        }
    }
}

private suspend fun pingService(client: AbstractGrpcService): String {
    return runCatching {
        client.ping()
    }.getOrElse {
        "Unavailable"
    }.takeIf { it.isNotBlank() } ?: "Unavailable"
}