## kanon.kommons
Currently being entirely restructured.

## Credits

- [Commons Lang 3](https://commons.apache.org/proper/commons-lang/) by the [Apache Software Foundation](https://www.apache.org/).

  - [Booleans.kt](https://gitlab.com/Olivki/kanon-kommons/blob/master/src/main/kotlin/lang/Booleans.kt#L78) is taken from [BooleanUtils.java](https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/commons/lang3/BooleanUtils.java#L546).

  `Commons Lang 3` is under the Apache 2.0 License.

- [Humanizer.jvm](https://github.com/MehdiK/Humanizer.jvm) by [MehdiK](https://github.com/MehdiK).

  - Various functions and classes from the `/lang/humanize` package. 

  `Humanizer.jvm` is under the Apache 2.0 License.

## Installation

Gradle

- Groovy

  ```groovy
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation "moe.kanon.kommons:kanon.kommons:0.6.0-alpha"
  }
  ```

- Kotlin

  ```kotlin
  repositories {
      jcenter()
  }
  
  dependencies {
      implementation(group = "moe.kanon.kommons", name = "moe.kanon", version = "0.6.0-alpha")
  }
  ```

Maven

```xml
<dependency>
  <groupId>moe.kanon.kommons</groupId>
  <artifactId>kanon.kommons</artifactId>
  <version>0.6.0-alpha</version>
  <type>pom</type>
</dependency>
```

## License

````
Copyright 2019 Oliver Berg

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````