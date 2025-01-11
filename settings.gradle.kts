
// The settings file is the entry point of every Gradle build.
// Its primary purpose is to define the subprojects.
// It is also used for some aspects of project-wide configuration, like managing plugins, dependencies, etc.
// https://docs.gradle.org/current/userguide/settings_file_basics.html

val user = System.getenv("NEXUS1_USERNAME")
    ?: extra["nexusUser"]?.toString()
    ?: error("Invalid nexus user")

val password = System.getenv("NEXUS1_PASSWORD")
    ?: extra["nexusPassword"]?.toString()
    ?:  error("Invalid nexus password")

println("Resolved Nexus username: $user")
println("Resolved Nexus password: ${if (password.isNotEmpty()) "****-****" else "EMPTY"}")
println("Resolved Nexus password length: ${password.length}")




dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://repo.papermc.io/repository/maven-public/")

        maven {
            url = uri("https://nexus.virtusdevelops.eu/repository/maven-releases/")
            isAllowInsecureProtocol = false
            credentials{
                username = user
                password = password
            }
        }

        mavenLocal()

    }

}


plugins {
    // Use the Foojay Toolchains plugin to automatically download JDKs required by subprojects.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}


include(":api")
include(":plugin")
include(":shared")
include(":core")

rootProject.name = "AdvancedCrops"
