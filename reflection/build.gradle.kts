plugins {
    kotlin("kapt")
}

description = "Provides some basic utilities for working with reflection in Kotlin"
version = "0.5.0"
extra["packageName"] = "reflection"

dependencies {
    compile(kotlin("reflect"))

    // tests
    testImplementation(group = "io.kotlintest", name = "kotlintest-runner-junit5", version = "3.1.11")
    testImplementation(group = "org.slf4j", name = "slf4j-simple", version = "1.8.0-beta2")

    testCompileOnly(group = "com.google.auto.service", name = "auto-service", version = "1.0-rc4")
    kaptTest(group = "com.google.auto.service", name = "auto-service", version = "1.0-rc4")
}