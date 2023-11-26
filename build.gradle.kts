import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val allureVersion = "2.24.0"
val aspectJVersion = "1.9.20.1"
val exposedVersion = "0.44.1"
val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}
plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testImplementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    testImplementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    testImplementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    testImplementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    testImplementation("org.xerial:sqlite-jdbc:3.44.0.0")
    agent("org.aspectj:aspectjweaver:${aspectJVersion}")
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
tasks.test {
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
}