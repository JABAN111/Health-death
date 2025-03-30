plugins {
    kotlin("jvm") version "1.8.20"
    id("io.ktor.plugin") version "2.2.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.google.protobuf") version "0.9.4"
    application
}

group = "mobile"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "io.ktor.server.netty.EngineMain"
    }
}

tasks {
    distZip {
        dependsOn(shadowJar)
    }
    distTar {
        dependsOn(shadowJar)
    }
    startScripts {
        dependsOn(shadowJar)
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.2.3")
    implementation("io.ktor:ktor-server-swagger:2.2.3")
    implementation("io.ktor:ktor-server-netty:2.2.3")
    implementation("ch.qos.logback:logback-classic:1.2.6")
    implementation("io.ktor:ktor-server-config-yaml:2.2.3")
    testImplementation("io.ktor:ktor-server-test-host:2.2.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.20")

    implementation("io.grpc:grpc-kotlin-stub:1.4.1")
    implementation("io.grpc:grpc-protobuf:1.71.0")
    implementation("io.grpc:grpc-stub:1.71.0")
    implementation("io.grpc:grpc-services:1.71.0")

    runtimeOnly("io.grpc:grpc-netty-shaded:1.71.0")

    implementation("com.google.protobuf:protobuf-kotlin:4.30.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
}
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.12"
    }

    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.57.2"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.0:jdk8@jar"
        }
    }

    generateProtoTasks {
        all().configureEach {
            plugins {
                create("grpc")
                create("grpckt")
            }
        }
    }
}

sourceSets {
    main {
        proto {
            setSrcDirs(listOf("../proto/schedule"))
        }
    }
}