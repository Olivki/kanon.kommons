## kanon.kExtensions
Various extension methods and extension properties and utilities for the Kotlin programming language.

## Word of Caution
**Please note that this library is still very experimental, and still in the development phase, and thus, MINOR versions can contain breaking changes.**

Currently, the only thing that's unlikely to be changed (except for additions of features) is the `Paths.kt` file.

If you are unsure if something you're using may have changed when updating, or you just want to get a better understanding of *what* has changed, and *how* it's changed, please read the [changelog](./CHANGELOG.md).

## Installation

Gradle

```
repositories {
    jcenter()
}

dependencies {
    compile "moe.kanon.kextensions:kanon.kextensions:0.5.0"
}
```

Maven

```
<dependency>
  <groupId>moe.kanon.kextensions</groupId>
  <artifactId>kanon.kextensions</artifactId>
  <version>0.5.0</version>
  <type>pom</type>
</dependency>
```