plugins {
    id("com.gtnewhorizons.gtnhconvention")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    testing {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }
}

minecraft {
    javaCompatibilityVersion = 21

    //extraRunJvmArguments.add("-Dorg.lwjgl.util.Debug=true")
    //extraRunJvmArguments.addAll("-Dlegacy.debugClassLoading=true", "-Dlegacy.debugClassLoadingFiner=false", "-Dlegacy.debugClassLoadingSave=true")
}



for (jarTask in listOf(tasks.jar, tasks.shadowJar, tasks.sourcesJar)) {
    jarTask.configure {
        manifest {
            attributes("Lwjgl3ify-Aware" to true)
        }
    }
}

tasks.runClient { enabled = false }
tasks.runServer { enabled = false }
tasks.runClient17 { enabled = false }
tasks.runServer17 { enabled = false }


tasks.withType<JavaExec>().configureEach {
    if (name.startsWith("runClient") || name.startsWith("runServer")) {
        environment("LD_PRELOAD", "/usr/lib/apitrace/wrappers/egltrace.so")
        doFirst { logger.lifecycle("APITRACE: LD_PRELOAD set to ${environment["LD_PRELOAD"]}, workingDir=$workingDir") }
    }
}
