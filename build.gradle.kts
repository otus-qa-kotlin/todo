
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    id("io.qameta.allure") version "2.11.2"
    id("io.kotest.multiplatform") version "5.0.2"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

val allureVersion = "2.24.0"

repositories {
    mavenCentral()
}

tasks.test {
    ignoreFailures = true
    useJUnitPlatform()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
    testImplementation ("io.qameta.allure:allure-junit5:2.24.0")
    testImplementation ("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-framework-engine:5.6.2")

    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.18")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:2.0.18")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")
    testImplementation("io.kotest:kotest-framework-datatest:5.5.5")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

