//val ktor_version: String by project
//val kotlin_version: String by project
//val logback_version: String by project
//val log4j_version: String by project

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
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

tasks.shadowJar {
    archiveBaseName.set("app")
    archiveClassifier.set("")
    archiveVersion.set("")
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "io.ktor.server.netty.EngineMain"
    }
}
tasks.named("compileKotlin") {
    dependsOn("generateProto")
}
tasks.jar {
    enabled = false
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.x.x")
    implementation("io.ktor:ktor-server-content-negotiation:2.x.x")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.x.x")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("io.ktor:ktor-server-status-pages:2.3.4")

    implementation("io.ktor:ktor-features:1.6.8")
    implementation("io.ktor:ktor-server-swagger:2.2.3")
    implementation("io.ktor:ktor-server-netty:2.2.3")
    implementation("ch.qos.logback:logback-classic:1.4.12")
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
            setSrcDirs(listOf("../proto/diary", "../proto/train",
                "../proto/authorization", "../proto/log","../proto/schedule", "../proto/user"))
        }
        java.srcDirs("build/generated/source/proto/main/grpc", "build/generated/source/proto/main/java")
    }
}