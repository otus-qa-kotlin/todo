import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val allureVersion = "2.24.0"
val spekVersion = "2.0.18"
val kotestVersion = "5.5.5"

plugins {
    kotlin("jvm") version "1.9.21"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("io.qameta.allure") version "2.11.2"
    id("io.gitlab.arturbosch.detekt") version "1.23.4"
    id("org.owasp.dependencycheck") version "8.1.0"
    application
    checkstyle
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
    implementation("org.owasp:dependency-check-gradle:9.0.9")
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-framework-engine:5.6.2")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")
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