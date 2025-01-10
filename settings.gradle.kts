
// The settings file is the entry point of every Gradle build.
// Its primary purpose is to define the subprojects.
// It is also used for some aspects of project-wide configuration, like managing plugins, dependencies, etc.
// https://docs.gradle.org/current/userguide/settings_file_basics.html

val user = extra["nexusUser"]?.toString() ?: error("Invalid nexus user")
val password = extra["nexusPassword"]?.toString() ?: error("Invalid nexus password")

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://repo.papermc.io/repository/maven-public/")

        maven {
            url = uri("https://nexus.virtusdevelops.eu/repository/maven-releases/")
            isAllowInsecureProtocol = true
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
