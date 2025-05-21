package mobile.train

import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import getDriver
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionServiceV1
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

fun getPostgresDataSource(jdbcURL: String, username: String, password: String) = HikariDataSource(HikariConfig().apply {
    this.jdbcUrl = jdbcURL
    driverClassName = "org.postgresql.Driver"
    this.username = username
    this.password = password
})

fun getDriver(jdbcURL: String, username: String, password: String) =
    getPostgresDataSource(jdbcURL, username, password).asJdbcDriver()


fun main() {


    val portEnv = System.getenv("USER_PORT")
    if (portEnv.isNullOrEmpty()) {
        println("Port is required")
        exitProcess(1)
    }
    val port = portEnv.toInt()

    val jdbcURL = System.getenv("USER_SERVICE_JDBC_URL")
    val dbUsername = System.getenv("USER_SERVICE_DB_USERNAME")
    val dbPassword = System.getenv("USER_SERVICE_DB_PASSWORD")
    if (jdbcURL.isNullOrEmpty() || dbUsername.isNullOrEmpty() || dbPassword.isNullOrEmpty()) {
        throw RuntimeException("Env USER_SERVICE_JDBC_URL: $jdbcURL or USER_SERVICE_DB_USERNAME: $dbUsername or USER_SERVICE_DB_PASSWORD: $dbPassword not specified")
    }

    val driver = getDriver(jdbcURL = jdbcURL, username = dbUsername, password = dbPassword)




    val server: Server = ServerBuilder
        .forPort(port)
        .addService(TrainService())
        .addService(ProtoReflectionServiceV1.newInstance())
        .build()
        .start()

    println("Server started on port $port")
    Runtime.getRuntime().addShutdownHook(Thread {
        println("Shutting down server...")
        server.shutdown()
    })

    runBlocking {
        server.awaitTermination()
    }
}