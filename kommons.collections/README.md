## kommons.collections

[![Download](https://api.bintray.com/packages/olivki/kanon.kommons/collections/images/download.svg)](https://bintray.com/olivki/kanon.kommons/collections/_latestVersion)

This module contains various utilities for working with collections in Kotlin, and some new data structures.

## Credits
- A lot of the functions inside of `Iterables.kt` are taken/adapted from [30 Seconds of Kotlin](https://github.com/IvanMwiruki/30-seconds-of-kotlin).

## Installation

Gradle

- Groovy

  ```groovy
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation "moe.kanon.kommons:kommons.collections:${LATEST_VERSION}"
  }
  ```

- Kotlin

  ```kotlin
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation(group = "moe.kanon.kommons", name = "kommons.collections", version = "${LATEST_VERSION}")
  }
  ```

Maven

```xml
<dependency>
  <groupId>moe.kanon.kommons</groupId>
  <artifactId>kommons.collections</artifactId>
  <version>${LATEST_VERSION}</version>
  <type>pom</type>
</dependency>
```