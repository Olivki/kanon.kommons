## 0.4.0 (2019-01-xx)

* ### NamedNodeMaps.kt
  * #### File
    * `NamedNodeMaps.kt` has been renamed to `_NamedNodeMap.kt`.

* ### _NamedNodeMap.kt
  * #### Functions
    * Added the `forEach` function, to allow for easier iteration.
    * Added the `toMap` function which converts the `NamedNodeMap` into a `Map` in the order of `node.nodeName:node`.
    * Added the `toSet` function which converts the `NamedNodeMap` to a `Set` containing all the node instances in the node map.

  * #### Properties
    * Added the `isEmpty` property which checks if the node map has any entries.

## 0.3.1 (2019-01-16)

* ### Documents.kt
  * #### Functions
    * Added missing explicit type inference to the `Node` class for `set(...)` in `Documents.kt`.

## 0.3.0 (2019-01-16)

* ### Elements.kt
  * #### File
    * Added the new `Elements.kt` file. This file provides extensions for the `Element` class in the `org.w3c.dom` package.

* ### NamedNodeMaps.kt
  * #### File
    * Added the new `NamedNodeMaps.kt` file. This file provides extensions for the `NamedNodeMap` class in the `org.w3c.dom` package.

* ### Documents.kt
  * #### File
    * Added the new `Documents.kt` file. This file provides extensions for the `Document` class in the `org.w3c.dom` package.

* ### Nodes.kt
  * #### File
    * Added the new `Nodes.kt` file. This file provides extensions for the `Node` class in the `org.w3c.dom` package.

## 0.2.0 (2019-01-11)

* ### Paths.kt
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