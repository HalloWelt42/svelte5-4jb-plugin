plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.2.20"
    id("org.jetbrains.intellij.platform") version "2.17.0"
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Generated lexers are checked in under src/main/gen.
sourceSets.main {
    java.srcDirs("src/main/java", "src/main/gen")
}

dependencies {
    intellijPlatform {
        local(providers.gradleProperty("platformLocalPath"))
        bundledPlugin("JavaScript")
        bundledPlugin("com.intellij.css")
        bundledPlugin("org.jetbrains.plugins.sass")

        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
    }

    testImplementation("junit:junit:4.13.2")
}

kotlin {
    jvmToolchain(21)
}

intellijPlatform {
    pluginConfiguration {
        name = "svelte-community"
        version = providers.gradleProperty("pluginVersion")
        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
        }
    }
    pluginVerification {
        // This plugin reuses JetBrains' own (MIT) Svelte language sources, which lean on
        // internal/experimental platform APIs by nature. Only fail on real compatibility
        // problems, not on internal/experimental/deprecated API usage.
        failureLevel = listOf(
            org.jetbrains.intellij.platform.gradle.tasks.VerifyPluginTask.FailureLevel.COMPATIBILITY_PROBLEMS,
            org.jetbrains.intellij.platform.gradle.tasks.VerifyPluginTask.FailureLevel.INVALID_PLUGIN,
        )
        ides {
            local(providers.gradleProperty("platformLocalPath"))
        }
    }
}

// Optional: open a project/file in the runIde sandbox for manual verification, e.g.
//   ./gradlew runIde -PopenProject=/path/to/a/svelte/file.svelte
tasks.runIde {
    val openProject = providers.gradleProperty("openProject")
    if (openProject.isPresent) {
        argumentProviders.add(CommandLineArgumentProvider { listOf(openProject.get()) })
    }
}

// Ship the vendored Svelte Language Server (Node) as loose files inside the plugin,
// so the LSP client can launch it for svelte2tsx-based type checking.
// Reproducible from language-server/package.json + package-lock.json via `npm ci`.
tasks.named<org.jetbrains.intellij.platform.gradle.tasks.PrepareSandboxTask>("prepareSandbox") {
    from(layout.projectDirectory.dir("language-server/node_modules")) {
        into("${rootProject.name}/language-server/node_modules")
    }
}
