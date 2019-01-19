# kanon.kExtensions [![Download](https://api.bintray.com/packages/olivki/kanon/kanon.kextensions/images/download.svg) ](https://bintray.com/olivki/kanon/kanon.kextensions/_latestVersion)

Various extension methods and extension properties and utilities for the Kotlin programming language.

# Word of Caution
**Please note that this library is still very experimental, and thus breaking changes can be made in MINOR versions.**

**That means that this library does NOT fully comply with the guidelines set forth for semantic versioning.**

Reminder that semantic versioning follows the following pattern: `MAJOR.MINOR.PATCH`

Here's a list of what kind of breaking changes each version can contain:

- **MAJOR:** This will generally contain **major** breaking changes of already existing functions, be it removal, renaming, or just general refactoring.
- **MINOR:** This *can* contain minor refactoring of functions, be it renaming of already existing functions, or the removal of one or two functions.
- **PATCH:** *Generally*, this will not contain any breaking changes, as these are reserved for bug fixes. Breaking changes may appear here if a function is found to be causing a problem, and no good solution is found.

If you are unsure if something you're using may have changed when updating, or you just want to get a better understanding of *what* has changed, and *how* it's changed, please read the [changelog](./CHANGELOG.md).

# Installation

Gradle

```
repositories {
    jcenter()
}

dependencies {
    compile "moe.kanon.kextensions:kanon.kextensions:1.0.0"
}
```

Maven

```
<dependency>
  <groupId>moe.kanon.kextensions</groupId>
  <artifactId>kanon.kextensions</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```