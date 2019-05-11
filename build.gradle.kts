import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import name.remal.gradle_plugins.plugins.publish.bintray.RepositoryHandlerBintrayExtension
import name.remal.gradle_plugins.dsl.extensions.*

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("name.remal:gradle-plugins:1.0.129")
    }
}

plugins {
    kotlin("jvm").version("1.3.31")

    `maven-publish`
}

apply(plugin = "name.remal.maven-publish-bintray")

// Project Specific Variables
project.group = "moe.kanon.kommons"
project.description = "Various extensions and utilities for the Kotlin programming language."
project.version = "0.6.0"
val gitUrl = "https://gitlab.com/Olivki/kanon-kommons/"

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    apply(plugin = "name.remal.maven-publish-bintray")

    repositories {
        mavenCentral()
        jcenter()
    }

    project.afterEvaluate {
        publishing.publications.withType<MavenPublication> {
            pom {
                name.set(project.name)
                description.set(project.description)
                url.set(gitUrl)

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        email.set("oliver@berg.moe")
                        id.set("Olivki")
                        name.set("Oliver Berg")
                    }
                }

                scm {
                    url.set(gitUrl)
                }
            }
        }

        publishing.repositories.convention[RepositoryHandlerBintrayExtension::class.java].bintray {
            owner = "olivki"
            repositoryName = "kanon.kommons"
            packageName = project.group.toString()
        }
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
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

// make sure that the correct sub-projects inherit from the core project
configure(subprojects.filterNot { it.name == "generator" || it.name == "core" }) {
    dependencies {
        implementation(project(":core"))
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "4.10.3"
    distributionType = Wrapper.DistributionType.BIN
}
