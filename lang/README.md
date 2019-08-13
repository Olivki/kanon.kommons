## kommons.lang

[![Download](https://api.bintray.com/packages/olivki/kanon.kommons/lang/images/download.svg)](https://bintray.com/olivki/kanon.kommons/lang/_latestVersion)

This module contains various utilities for working with Kotlin in general, it also contains extensions to the Kotlin std-lib.

## Installation

Gradle

- Groovy

  ```groovy
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation "moe.kanon.kommons:kommons.lang:${LATEST_VERSION}"
  }
  ```

- Kotlin

  ```kotlin
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation(group = "moe.kanon.kommons", name = "kommons.lang", version = "${LATEST_VERSION}")
  }
  ```

Maven

```xml
<dependency>
  <groupId>moe.kanon.kommons</groupId>
  <artifactId>kommons.lang</artifactId>
  <version>${LATEST_VERSION}</version>
  <type>pom</type>
</dependency>
```