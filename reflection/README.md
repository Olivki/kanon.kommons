## kommons.reflection

[![Download](https://api.bintray.com/packages/olivki/kanon.kommons/reflection/images/download.svg)](https://bintray.com/olivki/kanon.kommons/reflection/_latestVersion)

This module contains utilities for working with reflection in Kotlin.

## TODO

- Utils for converting a `KType` to `Type` *(there does exist an inbuilt property for converting a `KType` to a `Type`, but `KType` instances created using the `typeOf` function do **not** work with that extension property. This behaviour might change in Kotlin 1.4, so this might not be implemented)*.
- Utils for converting a `Type` to a `KType`.

## Installation

Gradle

- Groovy

  ```groovy
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation "moe.kanon.kommons:kommons.reflection:${LATEST_VERSION}"
  }
  ```

- Kotlin

  ```kotlin
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation(group = "moe.kanon.kommons", name = "kommons.reflection", version = "${LATEST_VERSION}")
  }
  ```

Maven

```xml
<dependency>
  <groupId>moe.kanon.kommons</groupId>
  <artifactId>kommons.reflection</artifactId>
  <version>${LATEST_VERSION}</version>
  <type>pom</type>
</dependency>
```