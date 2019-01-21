## 0.4.2 (2019-01-xx)

* ### /dom/Node.kt
  * #### Function
    * Added the `isEqual` infix function.

## 0.4.1 (2019-01-20)

* ### /dom/Document.kt
  * #### Function
    * Added the `appendTextContainer` function.

* ### /dom/Element.kt
  * #### Function
    * Added the `appendTextContainer` function.

## 0.4.0 (2019-01-20)

* ### /dom/NamedNodeMaps.kt
  * #### File
    * `NamedNodeMaps.kt` has been renamed to `NamedNodeMap.kt`.

* ### /dom/NamedNodeMap.kt
  * #### Functions
    * Added the `forEach` function, to allow for easier iteration.
    * Added the `toMap` function which converts the `NamedNodeMap` into a `Map` in the order of `node.nodeName:node`.
    * Added the `toSet` function which converts the `NamedNodeMap` to a `Set` containing all the node instances in the node map.
    * Added the `get(String, String)` operator which allows getting Nodes that are registered under specific namespaces.
    * Added the `isEmpty` function which checks if the node map has any entries.
    * Added the `iterator` function.
    * Added the `plusAssign` operator to allow cleaner addition of nodes.
    * Added the `minusAssign` operator to allow cleaner removal of nodes.
    * Added the `putAll` function.
    * Added the `asSequence` function.

* ### ExperimentalFeature.kt
  * #### File
    * Added the new `ExperimentalFeature` interface. This will be used to mark experimental features in such a way that the user will be able to understand that they are using an experimental feature.

* ### /collections/Arrays.kt
  * #### Annotation
    * The operator function `IntArray.contains` has been marked with `ExperimentalFeature`.

* ### /math/BigIntegers.kt
  * #### File
    * `BigIntegers.kt` has been renamed to `BigInteger.kt`.

* ### /dom/Nodes.kt
  * #### File
    * `Nodes.kt` has been renamed to `Node.kt`.

* ### /dom/Elements.kt
  * #### File
    * `Elements.kt` has been renamed to `Element.kt`.

* ### /dom/Element.kt
  * ### Classes
    * Added the `AttributeMap` class. This class acts as a sort of facade replacement for the `getAttributes(): NamedNodeMap` method in the `Element` class.

  * ### Operators
    * All operators that were previously for `Element` have been migrated to `AttributeMap` and can be accessed using `element.attributeMap`.

* ### /dom/Node.kt
  * ### Functions
    * `set(ChildType, String)` has been removed as it was deemed too clunky and ugly.

* ### /dom/NodeList.kt
  * #### File
    * Added the new `NodeList.kt` file. This file provides extensions for the `NodeList` class in the `org.w3c.dom` package.

* ### /dom/Documents.kt
  * #### File
    * `Documents.kt` has been renamed to `Document.kt`.

* ### /dom/Document.kt
  * #### Operators
    * `set(ChildType, String)` has been removed as it was deemed too clunky and ugly.
    * `plusAssign(String)` has been removed as it was deemed too ambiguous.

  * #### Misc
    * `ChildType` has been removed as the functions that used it were removed.

## 0.3.1 (2019-01-16)

* ### /dom/Documents.kt
  * #### Functions
    * Added missing explicit type inference to the `Node` class for `set(...)` in `Documents.kt`.

## 0.3.0 (2019-01-16)

* ### /dom/Elements.kt
  * #### File
    * Added the new `Elements.kt` file. This file provides extensions for the `Element` class in the `org.w3c.dom` package.

* ### /dom/NamedNodeMaps.kt
  * #### File
    * Added the new `NamedNodeMaps.kt` file. This file provides extensions for the `NamedNodeMap` class in the `org.w3c.dom` package.

* ### /dom/Documents.kt
  * #### File
    * Added the new `Documents.kt` file. This file provides extensions for the `Document` class in the `org.w3c.dom` package.

* ### /dom/Nodes.kt
  * #### File
    * Added the new `Nodes.kt` file. This file provides extensions for the `Node` class in the `org.w3c.dom` package.

## 0.2.0 (2019-01-11)

* ### /io/Paths.kt
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