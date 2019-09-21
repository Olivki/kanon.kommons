## kommons.io

[![Download](https://api.bintray.com/packages/olivki/kanon.kommons/io/images/download.svg)](https://bintray.com/olivki/kanon.kommons/io/_latestVersion)

This module contains utilities for working with IO in Kotlin.

The biggest part of this module is taken up by the `Path` extensions, which is a port of all the functions found in the `Files` class in the Java std-lib, plus some more custom functions.

`moe.kanon.kommons.io.BufferedImages.kt` is mainly a wrapper for the [imgscalr](https://github.com/rkalla/imgscalr) library. 

## Installation

Gradle

- Groovy

  ```groovy
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation "moe.kanon.kommons:kommons.io:${LATEST_VERSION}"
  }
  ```

- Kotlin

  ```kotlin
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation(group = "moe.kanon.kommons", name = "kommons.io", version = "${LATEST_VERSION}")
  }
  ```

Maven

```xml
<dependency>
  <groupId>moe.kanon.kommons</groupId>
  <artifactId>kommons.io</artifactId>
  <version>${LATEST_VERSION}</version>
  <type>pom</type>
</dependency>
```