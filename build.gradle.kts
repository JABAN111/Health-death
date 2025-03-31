buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
}

tasks.register("buildAllShadowJars") {
    description = "Builds shadowJar for all subprojects"
    group = "build"
    dependsOn(subprojects.mapNotNull { it.tasks.findByName("shadowJar") })
}

val runningProcesses = mutableListOf<Process>()

tasks.register("runAll") {
    description = "Runs all subprojects applications concurrently"
    group = "application"
    dependsOn("buildAllShadowJars")

    doLast {
        subprojects.forEach { proj ->
            val shadowJarTask = proj.tasks.findByName("shadowJar")
            if (shadowJarTask != null) {
                val jarFile = shadowJarTask.outputs.files.singleFile
                println("Starting ${proj.name} from ${jarFile.absolutePath}")
                Thread {
                    exec {
                        commandLine("java", "-jar", jarFile.absolutePath)
                    }
                }.start()
            }
        }
    }
}
