import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
    id("io.qameta.allure") version "2.11.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
    implementation("io.github.serpro69:kotlin-faker:1.15.0")
    implementation("io.qameta.allure:allure-junit-platform:2.24.0")
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

// Define the version of AspectJ
val aspectJVersion = "1.9.20.1"

// Define configuration for AspectJ agent
val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

dependencies {
    // Add aspectjweaver dependency
    agent("org.aspectj:aspectjweaver:${aspectJVersion}")
}

// Configure javaagent for test execution
tasks.test {
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
}