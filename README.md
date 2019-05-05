## kanon.kommons
`kanon.kommons` is a “utility” library for Kotlin. It mainly consists of extension functions, utility functions, and a lot of random things that I made for personal use, because I thought other people might have some use for it, for a learning experience, or just because I felt like making something like it. 

While this project *did* start off aiming to be something “”professional“” it has since become more of a general dumping ground, if you find something in here that you find it interesting and want to use, you can either use the dependency as shown down below, or just take the function into your own code base, just make sure that credits are given where credits are due, this is not just because I “want” any credits, but as some of the features in this project are ports from other languages / projects, and as such they deserve the appropriate credit *(morally, and legally speaking)*.

As mentioned above, certain parts of this “library” contains things made entirely for the sake of education / learning experience, and those are not necessarily intended to be used beyond the scope of education.

To find out what this project offers, just take a look inside of the source, as creating a list of everything would be a bother, and would generally end up being out-of-date anyways, as features are added on a “i-feel-like-it” basis.

Generally most things in this library are documented, but there is no guarantee that *everything* will be documented. The quality of the documentation may also wary wildly, as some of it is documentation ported from the original sources *(if a feature is ported from say, Java, then the documentation will also be directly ported from the same place)* or it may be written fully by myself, and if so, I can not guarantee that the quality of my documentation is very good.

The general “motto” for this project is, and will always be “because I can”.

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