plugins {
    kotlin("jvm") version "2.1.0"
    id("com.google.protobuf") version "0.9.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("app.cash.sqldelight") version "2.1.0"
    application
}

application {
    mainClass.set("mobile.train.MainKt")
}
tasks.shadowJar {
    archiveBaseName.set("app")
    archiveClassifier.set("")
    archiveVersion.set("")
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "mobile.user.MainKt"
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
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("app.cash.sqldelight:sqlite-driver:2.0.2")

// https://mvnrepository.com/artifact/redis.clients/jedis
    implementation("redis.clients:jedis:5.2.0")
    implementation(files("../logger-lib/build/libs/loglib-1.jar"))

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

sqldelight {
    databases {
        create("UserAttrDatabase") {
            deriveSchemaFromMigrations.set(true)
            packageName.set("database")
            dialect("app.cash.sqldelight:postgresql-dialect:2.0.2")
            srcDirs("src/main/sqldelight")
        }
    }
}

sourceSets {
    main {
        proto {
            setSrcDirs(listOf("../proto/user"))
        }
    }
}
