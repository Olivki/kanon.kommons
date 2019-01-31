## kanon.kommons
Various extension methods and extension properties and utilities for the Kotlin programming language.

## Word of Caution
**Please note that this library is still very experimental, and still in the development phase, and thus, MINOR versions can contain breaking changes.**

Currently, the only thing that's unlikely to be changed (except for additions of features) is the `Paths.kt` file.

If you are unsure if something you're using may have changed when updating, or you just want to get a better understanding of *what* has changed, and *how* it's changed, please read the [changelog](./CHANGELOG.md).

## What this library adds
- Extension methods & properties for the `Path.java` class to make it less clunky to use. The available extensions are *all* of the methods available in `Files.java` and some additional ones. *(These extensions may be given their own library at a later point.)*
- Extension methods & properties for classes that reside in `org.w3c.dom` to make them work better with the Kotlin syntatic sugar.

## Installation

Gradle

```groovy
repositories {
    jcenter()
}

dependencies {
    // groovy
    implementation "moe.kanon.kommons:kanon.kommons:0.6.0"
    // kotlin
    implementation("moe.kanon.kommons:kanon.kommons:0.6.0")
}
```

Maven

```xml
<dependency>
  <groupId>moe.kanon.kommons</groupId>
  <artifactId>kanon.kommons</artifactId>
  <version>0.6.0</version>
  <type>pom</type>
</dependency>
```