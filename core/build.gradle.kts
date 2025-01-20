plugins {
    kotlin("jvm")
}

group = "eu.virtusdevelops"
version = "unspecified"
val minecraftVersion: String = "1.21.4"


dependencies {
    testImplementation(kotlin("test"))
    compileOnly("io.papermc.paper:paper-api:$minecraftVersion-R0.1-SNAPSHOT")

    implementation(project(":api"))
    implementation(project(":shared"))
    implementation(libs.hikariCP)
    implementation(libs.virtusCore)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}