plugins {
    kotlin("jvm")
}

group = "eu.virtusdevelops"
version = "unspecified"

repositories {
    mavenCentral()


    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}