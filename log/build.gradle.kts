plugins {
    kotlin("jvm") version "1.8.20"
    id("com.google.protobuf") version "0.9.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

application {
    mainClass.set("mobile.log.MainKt")
}

tasks.shadowJar {
    archiveBaseName.set("app")
    archiveClassifier.set("")
    archiveVersion.set("")
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "mobile.log.MainKt"
    }
}
tasks.named("compileKotlin") {
    dependsOn("generateProto")
}
tasks.jar {
    enabled = false
}

repositories {
    mavenCentral()
}

dependencies {
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
            setSrcDirs(listOf("../proto/log"))
        }
    }
}
