package mobile

import io.ktor.server.application.*
import io.ktor.util.logging.*
import mobile.auth.repository.impl.UserRepositoryImpl
import mobile.auth.service.impl.AuthServiceImpl
import mobile.auth.service.impl.UserServiceImpl
import mobile.rest.configureGrpcClients
import mobile.rest.configureHTTP
import mobile.rest.configureRouting

val log = KtorSimpleLogger("mobile.gateway.ApplicationKt")


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    log.info("Starting application")
    log.debug("Debug level is enabled")
    //TODO: DI можно тут оставить, можно куда-либо вынести. Но идейно тут не мешает
    val userRepository = UserRepositoryImpl()
    val userService = UserServiceImpl(userRepository)
    val authService = AuthServiceImpl(secret, issuer, audience, userService)

    configureGrpcClients()
    configureHTTP()
    configureRouting(secret, issuer, audience, myRealm, authService)
}
