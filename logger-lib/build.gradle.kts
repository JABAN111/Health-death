plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "1.4.21"
}

group = "com.logger"
version = "1"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/redis.clients/jedis
    implementation("redis.clients:jedis:5.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}