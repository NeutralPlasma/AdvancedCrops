import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    kotlin("jvm")
    id("com.gradleup.shadow") version "8.3.5"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
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


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

paper {
    main = "eu.virtusdevelops.advancedcrops.plugin.AdvancedCrops"
    foliaSupported = false
    apiVersion = "1.21"
    authors = listOf("VirtusDevelops")

    name = "AdvancedCrops"
    description = "Advanced crops for your server."

    load = BukkitPluginDescription.PluginLoadOrder.STARTUP


    serverDependencies {
        register("Vault"){
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
        }
    }
}

bukkit {
    main = "eu.virtusdevelops.advancedcrops.plugin.AdvancedCrops"
    apiVersion = "1.21"
    foliaSupported = false
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    authors = listOf("VirtusDevelops")
    depend = listOf("Vault")
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








