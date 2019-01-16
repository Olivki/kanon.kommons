## 0.3.0 (2019-01-16)

* ### [Elements.kt](https://gitlab.com/Olivki/kanon-kextensions/blob/master/src/main/kotlin/dom/Elements.kt)
  * #### File
    * Added the new [Elements.kt](https://gitlab.com/Olivki/kanon-kextensions/blob/master/src/main/kotlin/dom/Elements.kt) file. This file provides extensions for the `Element` class in the `org.w3c.dom` package.
* ### [NamedNodeMaps.kt](https://gitlab.com/Olivki/kanon-kextensions/blob/master/src/main/kotlin/dom/NamedNodeMaps.kt)
  * #### File
    * Added the new [NamedNodeMaps.kt](https://gitlab.com/Olivki/kanon-kextensions/blob/master/src/main/kotlin/dom/NamedNodeMaps.kt) file. This file provides extensions for the `NamedNodeMap` class in the `org.w3c.dom` package.
* ### [Documents.kt](https://gitlab.com/Olivki/kanon-kextensions/blob/master/src/main/kotlin/dom/Documents.kt)
  * #### File
    * Added the new [NamedNodeMaps.kt](https://gitlab.com/Olivki/kanon-kextensions/blob/master/src/main/kotlin/dom/Documents.kt) file. This file provides extensions for the `Document` class in the `org.w3c.dom` package.
* ### [Nodes.kt](https://gitlab.com/Olivki/kanon-kextensions/blob/master/src/main/kotlin/dom/Nodes.kt)
  * #### File
    * Added the new [NamedNodeMaps.kt](https://gitlab.com/Olivki/kanon-kextensions/blob/master/src/main/kotlin/dom/Nodes.kt) file. This file provides extensions for the `Node` class in the `org.w3c.dom` package.

## 0.2.0 (2019-01-11)

* ### [Paths.kt](https://gitlab.com/Olivki/kanon-kextensions/blob/master/src/main/kotlin/io/Paths.kt)

  * #### Documentation
    * Tidied up all the ported over documentation.
    * Ported over all of the documentation from `Files.java`.

  * #### Functions
    * Renamed `Path.pathToString(...)` to `Path.readToString(...)`.
    * Renamed `Path.readAllBytes(...)` to `Path.readBytes(...)`.
    * Renamed `Path.readAllLines(...)` to `Path.readLines(...)`.
    * Renamed `Path.lines(...)` to `Path.linesStream(...)`.
    * Added the default value of `StandardCharsets.UTF_8` to the `charset` parameter on `Path.linesStream(..)`.
    * Renamed `Path.copy(Path, ...)` to `Path.copyTo(Path, ...)`.
    * Added the parameter `keepName` with the default value of `false` to `Path.copyTo(...)`.
    * Renamed `Path.rename(String, ...)` to `Path.renameTo(String, ...)`.
    * Renamed `Path.move(...)` to `Path.moveTo(...)`.
    * Added the parameter `keepName` with the default value of `false` to `Path.moveTo(...)`.

  * #### Operators
    * Changed the `File.unaryPlus()` operator to `File.not()`.
    * Removed the `String.unaryPlus()` operator. *(Felt bloated, and would definitely cause some clashes with some Kotlin DSL libraries.)*
    * Added the `Path.not()` operator to convert `Path` to `File`.

## 0.1.0-beta (2019-01-09)
Initial release.