package mobile

import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import database.UserDatabase
import com.logger.Logger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.util.logging.*
import mobile.auth.repository.impl.UserRepositoryImpl
import mobile.auth.service.impl.AuthServiceImpl
import mobile.auth.service.impl.UserServiceImpl
import mobile.rest.configureGrpcClients
import mobile.rest.configureHTTP
import mobile.rest.configureRouting

val log = KtorSimpleLogger("mobile.gateway.ApplicationKt")

fun getPostgresDataSource(jdbcURL: String, username: String, password: String) = HikariDataSource(HikariConfig().apply {
    this.jdbcUrl = jdbcURL
    driverClassName = "org.postgresql.Driver"
    this.username = username
    this.password = password
})

fun getDriver(jdbcURL: String, username: String, password: String) =
    getPostgresDataSource(jdbcURL, username, password).asJdbcDriver()
fun main(args: Array<String>) {
    EngineMain.main(args)
}


fun Application.module() {
    val loggerHost = environment.config.property("services.log.keydbHost").getString()
    val loggerPort = environment.config.property("services.log.keydbPort").getString().toInt()
    val kdbLogger = Logger.getLogger(
        host = loggerHost,
        port = loggerPort
    )

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    log.info("Starting application")
    log.debug("Debug level is enabled")

    val jdbcURL = System.getenv("AUTH_SERVICE_JDBC_URL")
    val dbUsername = System.getenv("AUTH_SERVICE_DB_USERNAME")
    val dbPassword = System.getenv("AUTH_SERVICE_DB_PASSWORD")
    if (jdbcURL.isNullOrEmpty() || dbUsername.isNullOrEmpty() || dbPassword.isNullOrEmpty()) {
        throw RuntimeException("Env AUTH_SERVICE_JDBC_URL: $jdbcURL or AUTH_SERVICE_DB_USERNAME: $dbUsername or AUTH_SERVICE_DB_PASSWORD: $dbPassword not specified")
    }

    val driver = getDriver(jdbcURL = jdbcURL, username = dbUsername, password = dbPassword)

    UserDatabase.Schema.migrate(
        driver = driver,
        oldVersion = 0,
        newVersion = UserDatabase.Schema.version
    )

    val db = UserDatabase(driver)
    val queries = db.userQueries


    val userRepository = UserRepositoryImpl(queries)
    val userService = UserServiceImpl(userRepository)
    val authService = AuthServiceImpl(secret, issuer, audience, userService)

    configureGrpcClients()
    configureHTTP()
    configureRouting(secret, issuer, audience, myRealm, authService, kdbLogger)
}
