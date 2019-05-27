pluginManagement {
    repositories {
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        mavenCentral() 
        maven(url = "https://plugins.gradle.org/m2/")
    }
}

include(
    "core",
    "generator",
    "kommons.assertions",
    "kommons.collections",
    "kommons.func",
    "kommons.io",
    "kommons.lang",
    "kommons.reflection"
)

rootProject.name = "kanon.kommons"

enableFeaturePreview("STABLE_PUBLISHING")