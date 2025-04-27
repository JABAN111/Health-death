package mobile

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT
                .require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build())
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
        authenticate("auth-jwt") {
            get("/auth") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
            }
        }
        post("/login") {
//            val user = call.receive<User>()
            // TODO some validation
            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", "jaba TODO")
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(secret))
            call.respond(hashMapOf("token" to token))
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

