## core

[![Download](https://api.bintray.com/packages/olivki/kanon.kommons/core/images/download.svg)](https://bintray.com/olivki/kanon.kommons/core/_latestVersion)

This is the core module that all the `kommons.xxx` modules include. It includes some basic functionality that all modules may have some use for.

## Installation

Gradle

- Groovy

  ```groovy
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation "moe.kanon.kommons:core:${LATEST_VERSION}"
  }
  ```

- Kotlin

  ```kotlin
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation(group = "moe.kanon.kommons", name = "core", version = "${LATEST_VERSION}")
  }
  ```

Maven

```xml
<dependency>
  <groupId>moe.kanon.kommons</groupId>
  <artifactId>core</artifactId>
  <version>${LATEST_VERSION}</version>
  <type>pom</type>
</dependency>
```