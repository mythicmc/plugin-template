import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    // TODO: If you don't want Kotlin support, remove this comment.
    kotlin("jvm") version "1.9.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.kyori.blossom") version "2.0.0"
    id("org.ajoberstar.grgit.service") version "5.2.0"
}

group = "org.mythicmc"
version = "1.0.0-alpha.0${getVersionMetadata()}"
// TODO: description = ""

repositories {
    mavenCentral()
    // TODO: Uncomment what you need and remove the rest (complete the dependencies block first?).
    // For Paper and/or Velocity: maven(url = "https://repo.papermc.io/repository/maven-public/")
    // For Spigot and/or Bungee: maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    // For Spigot and/or Bungee: maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    // TODO: Uncomment the dependencies you need and remove the rest.
    // NOTE: Paper and Spigot APIs are mutually exclusive!
    /* NOTE:
        You cannot bundle Adventure for use on Bungee/Spigot AND use Paper/Velocity's native
        Adventure support in the same project due to the shadow JAR relocating your class
        references! If you only use Paper/Velocity native Adventure a few times, you could
        use reflection and reference classes as Class.forName(n + "et.kyori.adventure").. lol
    */
    // Adventure for Spigot and Bungee: implementation("net.kyori:adventure-api:4.14.0")
    // Needed for Spigot plugins: implementation("net.kyori:adventure-platform-bukkit:4.3.0")
    // Needed for Bungee plugins: implementation("net.kyori:adventure-platform-bungeecord:4.3.0")

    // Velocity: compileOnly("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
    // Velocity: annotationProcessor("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
    // Bungee: compileOnly("net.md-5:bungeecord-api:1.16-R0.5-SNAPSHOT")
    // Spigot: compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
    // Paper 1.16 and older: compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    // Paper 1.17 and newer: compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly(fileTree(mapOf("dir" to "lib", "includes" to listOf("*.jar"))))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

sourceSets {
    main {
        blossom {
            resources {
                property("version", project.version.toString())
            }
        }
    }
}

tasks.getByName<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    // Automatically relocate all added dependencies
    isEnableRelocation = true
    relocationPrefix = "org.mythicmc.templateplugin.shadow"
}

fun getVersionMetadata(): String {
    if (project.hasProperty("skipVersionMetadata")) return ""

    val grgit = try { grgitService.service.orNull?.grgit } catch (e: Exception) { null }
    if (grgit != null) {
        val head = grgit.head() ?: return "+unknown" // No head, fresh git repo
        var id = head.abbreviatedId
        val tag = grgit.tag.list().find { head.id == it.commit.id }

        // If we're on a tag and the tree is clean, don't put any metadata
        if (tag != null && grgit.status().isClean) {
            return ""
        }
        // Flag the build if the tree isn't clean
        if (!grgit.status().isClean) {
            id += "-dirty"
        }

        return "+git.$id"
    }

    return "+unknown"
}