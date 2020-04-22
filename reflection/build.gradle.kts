/*
 * Copyright 2019-2020 Oliver Berg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    kotlin("kapt")
}

description = "Provides some basic utilities for working with reflection in Kotlin"
version = "0.6.0" // TODO: This version has not been released yet
extra["packageName"] = "reflection"

dependencies {
    compile(kotlin("reflect"))

    // tests
    testImplementation(group = "io.kotlintest", name = "kotlintest-runner-junit5", version = "3.1.11")
    testImplementation(group = "org.slf4j", name = "slf4j-simple", version = "1.8.0-beta2")

    testCompileOnly(group = "com.google.auto.service", name = "auto-service", version = "1.0-rc4")
    kaptTest(group = "com.google.auto.service", name = "auto-service", version = "1.0-rc4")
}