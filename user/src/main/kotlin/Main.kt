package mobile.train

import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import database.UserAttrDatabase
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
    val dbUsername = "user"
    val dbPassword = "user"
    if (jdbcURL.isNullOrEmpty()){
        throw RuntimeException("Env USER_SERVICE_JDBC_URL: $jdbcURL")
    }

    val driver = getDriver(
        jdbcURL = jdbcURL,
        username = dbUsername ,
        password = dbPassword
    )

    UserAttrDatabase.Schema.migrate(
        driver = driver,
        oldVersion = 0,
        newVersion = UserAttrDatabase.Schema.version
    )


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