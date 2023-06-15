
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
    implementation("junit:junit:4.13.2")
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.5")
    implementation("io.kotest:kotest-extensions-allure-jvm:4.4.3")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("allure.results.directory", project.buildDir.toString() + "/allure-results")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}


application {
    mainClass.set("MainKt")
}
