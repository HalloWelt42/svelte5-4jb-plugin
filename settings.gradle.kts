plugins {
    // Lets Gradle auto-provision the JDK 21 toolchain the IntelliJ platform requires
    // (downloaded into the Gradle user cache, no system-wide install).
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "svelte5-4jb-plugin"
