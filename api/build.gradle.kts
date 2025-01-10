plugins {
    kotlin("jvm")
    id("com.gradleup.shadow") version "8.3.5"
}

group = "eu.virtusdevelops"
version = "0.0.1"
val minecraftVersion: String = "1.21.4"


dependencies {
    compileOnly("io.papermc.paper:paper-api:$minecraftVersion-R0.1-SNAPSHOT")
    implementation(project(":shared"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}