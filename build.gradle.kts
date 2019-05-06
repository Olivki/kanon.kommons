import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm").version("1.3.31")

    id("com.adarshr.test-logger").version("1.6.0") // For pretty-printing for tests.
    id("com.jfrog.bintray").version("1.8.4") // For publishing to BinTray.
    id("org.jetbrains.dokka").version("0.9.17") // The KDoc engine.
    id("com.github.ben-manes.versions").version("0.20.0") // For checking for new dependency versions.

    `maven-publish`
}

// Project Specific Variables
project.group = "moe.kanon.kommons"
project.description = "Various extensions and utilities for the Kotlin programming language."
project.version = "0.6.0"
val artifactName = "kanon.kommons"
val gitUrl = "https://gitlab.com/Olivki/kanon-kommons"

// General Tasks
repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    // Normal Dependencies
    compile(kotlin("stdlib-jdk8"))

    // Test Dependencies
    testImplementation(group = "io.kotlintest", name = "kotlintest-runner-junit5", version = "3.1.11")
    testImplementation(group = "org.slf4j", name = "slf4j-simple", version = "1.8.0-beta2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    with(kotlinOptions) {
        freeCompilerArgs = listOf(
            "-Xuse-experimental=kotlin.Experimental",
            "-XXLanguage:+InlineClasses",
            "-Xjvm-default=enable",
            "-Xuse-experimental=kotlin.contracts.ExperimentalContracts"
        )
    }
}

subprojects {
    repositories {
        mavenCentral()
        jcenter()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        with(kotlinOptions) {
            freeCompilerArgs = listOf(
                "-Xuse-experimental=kotlin.Experimental",
                "-XXLanguage:+InlineClasses",
                "-Xjvm-default=enable",
                "-Xuse-experimental=kotlin.contracts.ExperimentalContracts"
            )
        }
    }
}

// Test Tasks
testlogger {
    setTheme("mocha")
}

tasks.withType<Test> {
    useJUnitPlatform()
}