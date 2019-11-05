## kommons.func

[![Download](https://api.bintray.com/packages/olivki/kanon.kommons/func/images/download.svg)](https://bintray.com/olivki/kanon.kommons/func/_latestVersion)

This module provides some basic functional constructs and abstract data types to Kotlin.

The data types provided are currently the following; `Option`, `Either`, `Try`, `Tuple0..24`, `TupleN`, and several extension functions for each data type to integrate them nicely with the Kotlin std-lib.

This was mainly made as an exercise, if you want to make use of abstract data types and functional concepts in Kotlin, I'd highly recommend using [arrow](https://github.com/arrow-kt/arrow) instead of this library.

## Installation

Gradle

- Groovy

  ```groovy
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation "moe.kanon.kommons:kommons.func:${LATEST_VERSION}"
  }
  ```

- Kotlin

  ```kotlin
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation(group = "moe.kanon.kommons", name = "kommons.func", version = "${LATEST_VERSION}")
  }
  ```

Maven

```xml
<dependency>
  <groupId>moe.kanon.kommons</groupId>
  <artifactId>kommons.func</artifactId>
  <version>${LATEST_VERSION}</version>
  <type>pom</type>
</dependency>
```