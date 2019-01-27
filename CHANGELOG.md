## 0.6.0 (2019-01-xx)

It's finally time for the addition of some *actual* utilities, and not just facades for syntatic sugar!

* ### /io/Paths.kt
  * #### Typealias
    * {---} Removed the `PathStream` typealias.

  * #### Properties
    * {---}{+++} Changed the return type of `entries` to `Sequence<Path>`.

        Originally it was `Stream<Path>`, but as this library is made for Kotlin use, and the fact that `Sequence` is generally more powerful than `Stream` means that it makes more sense for it to be a `Sequence`. *(The use of `Stream` in Kotlin is also not really supported.)*

  * #### Operators
    * {+++} Added the `contains(String)` operator, which allows for checking if a directory contains any files with the specified `fileName`.

* ### /humanize/
  * #### File
    * Added the `humanize` package, this will contain various utilities for "humanizing" data, largely based on [Humanizer.jvm](https://github.com/MehdiK/Humanizer.jvm) and the original [Humanizer](https://github.com/Humanizr/Humanizer) for .NET. *(Go [here](https://gitlab.com/Olivki/humanizer-jvm) if you want a working version of `Humanizer.jvm`.)*

* ### /humanize/CaseConverter.kt
  * #### File
    * Added the `CaseConverter.kt` file which adds utilities for converting *to* and *from* various cases.

* ### /humanize/RomanNumerals.kt
  * #### File
    * Added the `RomanNumerals.kt` file. This provides utilities for converting numbers *to* and *from* Roman numerals.

* ### /collections/Maps.kt
  * #### File
    * Added the `Maps.kt` file. This provides utilities for various `Map` implementations.

## 0.5.2 (2019-01-23)

* ### /io/KPath.kt
  * #### File
    * {+++} Added the new `KPath` object.

      This object acts as a utility for creating new `Path` instances, it's purpose is to resemble the syntax used for creating `File` instances.

      Note that while the object *is* called KPath, the instances it creates are simply just normal `Path` instances. (It uses the `pathOf` top level function.)

* ### /io/Paths.kt
  * #### Documentation
    * Ported over the original Java documentation for `pathOf(String, vararg String)` to make it clearer what it actually does.
    * Fixed the casing for code blocks in the documentation. *(Kotlin -> kotlin)*

  * #### Functions
    * {+++} Added the `pathOf(URI)` top-level function.

## 0.5.1 (2019-01-22)

* ### /dom/NodeList.kt
  * #### Functions
    * {---} Removed the `iterator` function that was added in 0.4.0, as it did not work.
    * {---} Removed the `asSequence` function that was added in 0.4.0, as it did not work.
    * {+++} Added a working version of the `iterator` function.
    * {+++} Added a working version of the `asSequence` function.

* ### /dom/Node.kt
  * #### Functions
    * {+++} Added the  `toSerializedString` function.

## 0.5.0 (2019-01-21)

* ### /dom/Node.kt
  * #### Functions
    * {+++} Added the `isEqual` infix function.

  * #### Operators
    * {+++} Added the `contains(Node)` operator function.
    * {+++} Added the `contains(String)` operator function.

* ### /dom/NodeList.kt
  * #### Functions
    * {+++} Added the `indexOf(Node)` function.
    * {+++} Added the `indexOf(String, Boolean = false)` function.
    * {+++} Added the `getOrNull(Int)` function. This function has the same behaviour as the pre-0.5.0 `get(Int)` operator. It does the exact same thing as the inbuilt `item(Int)` function does, but it has a more obvious name of what it does.
    * {+++} Added the `getOrNull(String, Boolean = false)` function. This function has the same behaviour as the pre-0.5.0 `get(String, Boolean = false)` operator, there exists no inbuilt variant for this.

  * #### Operators
    * {+++} Added the `contains(Node)` operator function.
    * {+++} Added the `contains(String)` operator function.
    * {---} The old `get(Int)` has been renamed to `getOrNull(Int)` and is no longer an operator.
    * {+++} Added the `get(Int)` operator, this returns a non-null value, and will throw a `KotlinNullPointerException` if the given index is out of bounds. If you want to work with a `nullable` type, either use `getOrNull(Int)` or the inbuilt `item(Int)` function.
    * {---} The old `get(String, Boolean = false)` has been renamed to `getOrNull(String, Boolean = false)` and is no longer an operator.
    * {+++} Added the `get(String, Boolean = false)` operator, this returns a non-null value, and will throw a `KotlinNullPointerException` if the there exists no `Node` that matches the specified name. If you want to work with a `nullable` type, use `getOrNull(String, Boolean = false)`.

* ### /dom/NamedNodeMap
  * #### Functions
    * {+++} Added the `getOrNull(Int)` function. This function has the same behaviour as the pre-0.5.0 `get(Int)` operator. It does the exact same thing as the inbuilt `item(Int)` function does, but it has a more obvious name of what it does.
    * {+++} Added the `getOrNull(String)` function. This function has the same behaviour as the pre-0.5.0 `get(String)` operator.
    * {+++} Added the `getOrNull(String, String)` function. This function has the same behaviour as the pre-0.5.0 `get(String, String)` operator.

  * #### Operators
    * {---} The old `get(Int)` has been renamed to `getOrNull(Int)` and is no longer an operator.
    * {+++} Added the `get(Int)` operator, this returns a non-null value, and will throw a `KotlinNullPointerException` if the given index is out of bounds. If you want to work with a `nullable` type, either use `getOrNull(Int)` or the inbuilt `item(Int)` function.
    * {---} The old `get(String)` has been renamed to `getOrNull(String)` and is no longer an operator.
    * {+++} Added the `get(String)` operator, this returns a non-null value, and will throw a `KotlinNullPointerException` if the there exists no `Node` that matches the specified name. If you want to work with a `nullable` type, use `getOrNull(String)`.
    * {---} The old `get(String, String)` has been renamed to `getOrNull(String, String)` and is no longer an operator.
    * {+++} Added the `get(String, String)` operator, this returns a non-null value, and will throw a `KotlinNullPointerException` if the there exists no `Node` that matches the specified parameters. If you want to work with a `nullable` type, use `getOrNull(String, String)`.

## 0.4.1 (2019-01-20)

* ### /dom/Document.kt
  * #### Functions
    * {+++} Added the `appendTextContainer` function.

* ### /dom/Element.kt
  * #### Functions
    * {+++} Added the `appendTextContainer` function.

## 0.4.0 (2019-01-20)

* ### /dom/NamedNodeMaps.kt
  * #### File
    * `NamedNodeMaps.kt` has been renamed to `NamedNodeMap.kt`.

* ### /dom/NamedNodeMap.kt
  * #### Functions
    * {+++} Added the `forEach` function, to allow for easier iteration.
    * {+++} Added the `toMap` function which converts the `NamedNodeMap` into a `Map` in the order of `node.nodeName:node`.
    * {+++} Added the `toSet` function which converts the `NamedNodeMap` to a `Set` containing all the node instances in the node map.
    * {+++} Added the `get(String, String)` operator which allows getting Nodes that are registered under specific namespaces.
    * {+++} Added the `isEmpty` function which checks if the node map has any entries.
    * {+++} Added the `iterator` function.
    * {+++} Added the `plusAssign` operator to allow cleaner addition of nodes.
    * {+++} Added the `minusAssign` operator to allow cleaner removal of nodes.
    * {+++} Added the `putAll` function.
    * {+++} Added the `asSequence` function.

* ### ExperimentalFeature.kt
  * #### File
    * {+++} Added the new `ExperimentalFeature` interface. This will be used to mark experimental features in such a way that the user will be able to understand that they are using an experimental feature.

* ### /collections/Arrays.kt
  * #### Annotations
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
    * {+++} Added the `AttributeMap` class. This class acts as a sort of facade replacement for the `getAttributes(): NamedNodeMap` method in the `Element` class.

  * ### Operators
    * {---}{+++} All operators that were previously for `Element` have been migrated to `AttributeMap` and can be accessed using `element.attributeMap`.

* ### /dom/Node.kt
  * ### Functions
    * {---} `set(ChildType, String)` has been removed as it was deemed too clunky and ugly.

* ### /dom/NodeList.kt
  * #### File
    * {+++} Added the new `NodeList.kt` file. This file provides extensions for the `NodeList` class in the `org.w3c.dom` package.

* ### /dom/Documents.kt
  * #### File
    * `Documents.kt` has been renamed to `Document.kt`.

* ### /dom/Document.kt
  * #### Operators
    * {---} `set(ChildType, String)` has been removed as it was deemed too clunky and ugly.
    * {---} `plusAssign(String)` has been removed as it was deemed too ambiguous.

  * #### Misc
    * {---} `ChildType` has been removed as the functions that used it were removed.

## 0.3.1 (2019-01-16)

* ### /dom/Documents.kt
  * #### Functions
    * {+++} Added missing explicit type inference to the `Node` class for `set(...)` in `Documents.kt`.

## 0.3.0 (2019-01-16)

* ### /dom/Elements.kt
  * #### File
    * {+++} Added the new `Elements.kt` file. This file provides extensions for the `Element` class in the `org.w3c.dom` package.

* ### /dom/NamedNodeMaps.kt
  * #### File
    * {+++} Added the new `NamedNodeMaps.kt` file. This file provides extensions for the `NamedNodeMap` class in the `org.w3c.dom` package.

* ### /dom/Documents.kt
  * #### File
    * {+++} Added the new `Documents.kt` file. This file provides extensions for the `Document` class in the `org.w3c.dom` package.

* ### /dom/Nodes.kt
  * #### File
    * {+++} Added the new `Nodes.kt` file. This file provides extensions for the `Node` class in the `org.w3c.dom` package.

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
    * {+++} Added the default value of `StandardCharsets.UTF_8` to the `charset` parameter on `Path.linesStream(..)`.
    * Renamed `Path.copy(Path, ...)` to `Path.copyTo(Path, ...)`.
    * {+++} Added the parameter `keepName` with the default value of `false` to `Path.copyTo(...)`.
    * Renamed `Path.rename(String, ...)` to `Path.renameTo(String, ...)`.
    * Renamed `Path.move(...)` to `Path.moveTo(...)`.
    * {+++} Added the parameter `keepName` with the default value of `false` to `Path.moveTo(...)`.

  * #### Operators
    * Changed the `File.unaryPlus()` operator to `File.not()`.
    * {---} Removed the `String.unaryPlus()` operator. *(Felt bloated, and would definitely cause some clashes with some Kotlin DSL libraries.)*
    * {+++} Added the `Path.not()` operator to convert `Path` to `File`.

## 0.1.0-beta (2019-01-09)
Initial release.