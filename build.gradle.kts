import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.23"
    application
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.bukreev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.23")
    implementation("org.antlr:antlr4-runtime:4.13.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("dev.bukreev.types.MainKt")
}
tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set("typechecker.jar")
        mergeServiceFiles()
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}