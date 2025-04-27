val ktor_version = "3.1.2"

plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "1.5.0"
    id("io.ktor.plugin") version "3.1.2"
    id("com.gradleup.shadow") version "8.3.6"
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
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")

    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
    implementation("io.ktor:ktor-server-swagger:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    implementation("ch.qos.logback:logback-classic:1.4.12")
    implementation("org.mindrot:jbcrypt:0.4")

    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
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