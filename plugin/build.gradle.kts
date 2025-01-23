import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    kotlin("jvm")
    id("com.gradleup.shadow") version "8.3.5"
    id("de.eldoria.plugin-yml.bukkit") version "0.6.0"
}

group = "eu.virtusdevelops"
version = "0.0.1"
val minecraftVersion: String = "1.21.4"



dependencies {
    compileOnly("io.papermc.paper:paper-api:$minecraftVersion-R0.1-SNAPSHOT")

    implementation(project(":api"))
    implementation(project(":core"))
    implementation(project(":shared"))
    implementation(libs.virtusCore)
    implementation(libs.bundles.cloudEcosystem)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}


val authorsList = listOf("VirtusDevelops")




bukkit {
    name = "AdvancedCrops"
    main = "eu.virtusdevelops.advancedcrops.plugin.AdvancedCrops"
    apiVersion = "1.21"
    foliaSupported = false
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    authors = authorsList
    depend = listOf("Vault")
    libraries = listOf(
        "com.h2database:h2:2.3.232"
    )
}



tasks {
    shadowJar {
        archiveClassifier.set("")
        archiveBaseName.set("AdvancedCrops")
        dependsOn(":api:shadowJar")
    }

    build {
        dependsOn(shadowJar)
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}








