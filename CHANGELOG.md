## 0.6.0 (2019-02-xx)

**0.6.0 comes with the biggest breaking change of them all, which is the change of the name!**
I know this really isn't something you should do with something that's *already* out there, but "kextensions" just *really* didn't roll off the tongue very nicely, and I was growing more and more frustrated with it.

- ### Everything

  - #### General Changes

    - All wrapper functions have been marked as `inline`.
      This is so that the generated code *directly* refers to the function that's being wrapped, rather than the wrapper function.

- ### Gradle

  - #### General Changes

    - Switched from the Groovy based DSL to the Kotlin based DSL.

- ### /io/Paths.kt
  - #### General Changes

    - All instances of `Stream<Path>` /  `PathStream` have been changed to `Sequence<Path>` to better accommodate the Kotlin language. 

  - #### Documentation

    - Changed how the `@param` for parameters with default values are presented.

  - #### Annotations

    - `@file:JvmName` has been changed from `"FilesWrapper"` to `"PathUtils"`.

  - #### Typealias's

    - {---} Removed the `PathStream = Stream<Path>` type-alias.
    - {---} Removed the `PathMatcher = BiPredicate<Path, BasicFileAttributes>` type-alias.
    - {+++} Added the `PathBiPredicate = (file: Path, attributes: BasicFileAttributes) -> Boolean` type-alias.
    - {+++} Added the `SimplePathVisitor = SimpleFileVisitor<Path>` type-alias.

  - #### Properties

    - {---}{+++} Changed the return type of `entries` to `Sequence<Path>`.

        Originally it was `Stream<Path>`, but as this library is made for Kotlin use, and the fact that `Sequence` is generally more powerful than `Stream` means that it makes more sense for it to be a `Sequence`. *(The use of `Stream` in Kotlin is also not really supported.)*

    - Reworked the `get` function of the `directorySize` property to use the `walkFileTree` function rather than a simple `for-each` loop.

    - {---} Deprecated the `Path.children: List<Path>` property.
        Reason for deprecation is that the property really doesn't add much in terms of usability, and the name for it is rather confusing when you also have the `Path.entries` property. If you want to still specifically have a `List` of paths rather than a `Sequence`, please do `path.entries.toList()`. Do note that the use cases of having this as a `List` rather than a `Sequence` are *very* limited, so make sure that you're not doing something that could be easier done with a `Sequence`. *(This can be something like manually filtering via a `for-each` loop)* .

    - {+++} Added the `Path.allEntries` property.
        This will *recursively* list *all* entries of the receiver directory, this means also the contents of any sub-directories, and any content of the sub-directories of the said sub-directories, repeat ad nauseam.
        This is just a "facade" for the `Path.walk(...)` function, called with all the parameters set to their default values.

  - #### Operators

    - {+++} Added the `Path.contains(String)` operator, this allows for checking if a certain file is a child of the `Path receiver`.
      **Note:** This uses *glob syntax*, if you are not familiar with it, see [What is a Glob](https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob).
    - {+++} Added the `Path.get(String)` operator.

  - #### Functions

    - {+++} Added the `Path.filter((Path, BasicFileAttributes) -> Boolean, Int = Int.MAX_VALUE, vararg FileVisitOption): List<Path>` function.
      The purpose of this function is to serve as a replacement for the old `Path.find(...)` function that has existed since `v0.1.0-beta`. It generally works the same way as the old one, except that the `BiPredicate<...>` that the old one used has been replaced with a higher-order function.
      The `maxDepth: Int = Int.MAX_VALUE` parameter has also been moved to be *behind* the `matcher` parameter, as it was a default value located at the start, this meant that you would need to do `path.find(matcher = {...})` if you didn't want to specify a specific `maxDepth`. So, as to make the syntax nicer, it was moved, which allows one to now do `path.find({...})` instead.
      The function has also had it's name changed from `find` to `filter`, this is to avoid confusion regarding how this function *actually* works.
      Generally, most people used to Kotlin will assume that a function called `find` will return *the first occurrence* of something, if it failed it will either throw an exception or return null. (`Iterable<T>.find` and `Iterable<T>.findOrNull` respectively.), this can cause confusion if one were to *just* go by the name of the `Files.find(...)` function, because that function behaves more akin to the `Iterable<T>.filter` function, which returns a `List<T>` with elements that matched the given `predicate`, and thus it's been renamed to represent this.
      This function also does ***not*** return a `Sequence<Path>` but rather a `List<Path>`, if you need a filtered `Sequence<Path>` use one of two following:
      - `Path.entries.filter { ... }` Will give you a filtered `Sequence<Path>` of *only* the entries contained within the `receiver` directory, essentially this walks the directory with a max depth of 0.
      - `Path.allEntries.filter { ... }` Will give you a filtered `Sequence<Path>` of *all* the entries contained within the `receiver` directory, essentially this walks the directory with a max depth of `Int.MAX_VALUE`.
        If this is what you want, it might be better to use the `Path.walkFileTree` function instead, for more thorough control.
    - {+++} Added the `Path.filter((Path) -> Boolean)` function.
      This function just calls `Path.filter((Path) -> Boolean)` function with all the parameters with default values unchanged.
      This enables one to do `path.filter { ... }` rather than `path.filter({ ... })`.
    - {---} Deprecated the `Path.find(Int = Int.MAX_VALUE, BiPredicate<Path, BasicFileAttributes>, vararg FileVisitOption): Stream<Path>` function, in favor of the newly added `Path.filter(Path -> Boolean, Int = Int.MAX_VALUE, vararg FileVisitOption): List<Path>` function.
    - {+++} Added the `createTemporaryDirectory(String? = null, vararg FileAttribute<*>)` top-level function, this allows one to create a temporary directory inside the default temporary-file directory.
      *("Temporary" was used to stay in style with `createTemporaryFile`.)*
    - {+++} Added the `createTemporaryFile(String? = null, vararg FileAttribute<*>)`  top-level function, this allows one to create a temporary file inside the default temporary-file directory.
      *("Temporary" had to be used instead of "Temp" because the std-lib for Kotlin already comes with a `createTempFile` , which uses the old `File` class, and those functions take automatic priority, orz.)*
    - Replaced all `String` parameters of `Path.createTempFile(String, String, vararg FileAttribute<*>)` to `String?` and set the default value of them to `null`. 
      This is because I had missed the fact that they were intentionally `nullable` when I first ported the function over.
    - Replaced the `name: String` parameter of `Path.createTempDirectory(String, vararg FileAttribute<*>)` with `name: String?` and set the default value of it to `null`.
      This is because I had missed the fact that it was intentionally `nullable` when I first ported the function over.
    - Changed the parameter names for `Path.createTempFile(String? = null, String? = null, vararg FileAttribute<*>)`.
      - `name` to `prefix`.
      - `extension` to `suffix`.
    - {+++} Added the `seperator: String` parameter with the default value of `System.lineSeperator()` to `Path.readToString(Charset = StandardCharsets.UTF_8)`.
    - Renamed `Path.linesStream(Charset = StandardCharsets_UTF.8)` to `Path.linesAsSequence`.
      **Reminder** that this function should ***only*** be used if you ***need*** to use a a different `Charset` than `UTF-8`. Otherwise, use the `Path.lines` property.
    - {+++} Reworked the `Path.cleanDirectory(Boolean = false)` function to use the `Path.walkFileTree(...)` function.
      - {+++} Added the following parameters to the `Path.cleanDirectory(...)` function:
        - `globPattern: String = "*"`.
          This glob pattern determines which files to delete, if a file doesn't match it, it *will not* be deleted.
          **Note:** When `deleteDirectories` is `true`, any and all directories that the walker encounters *will* be deleted. Basically, when deleting directories, it completely ignores whether or not the directory matches the specified `globPattern`.
        - `maxDepth: Int = Int.MAX_VALUE`.
          This determines the maximum number of levels this function should visit.
          `maxDepth = 0` means that only the starting file is visited.
          `maxDepth = Int.MAX_VALUE` can be used to indicate that *all* sub-directories should be visited.
        - `vararg options: FileVisitOption`.
      - The old `deleteDirectories: Boolean = false` parameter has been moved to the end, just before the `vararg` parameter.
    - {+++} Added the `getPathMatcher(syntax: String, pattern: String)` top-level function.
      This is just a "facade" to be used instead of doing `FileSystems.getDefault().getPathMatcher(...)`.
      It *is* different from the parent function in the way that this function splits `syntax` and `pattern` up into two different parameters, whereas the original has them as one string; `syntaxAndPattern`. This was done to make it easier to see that you *need* to supply two values.
    - {+++} Added the `Path.launch(launchWith: LaunchType = LaunchType.DEFAULT_VIEWER)` function.
      This function allows you to open/launch the current  `Path receiver` with either the system's default viewer for the current file, or the system's default editor for the current file.
      Do note that if the Java Desktop API does *not* support the current platform, a `UnsupportedDesktopException` will be thrown.
    - {+++} Added the `Sequence<Path>.filterByGlob(String)` function.
      This allows one to filter sequences that are *specifically* made for `Path`s by a [glob pattern](https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob).
    - {+++} Added the `Path.getOrNull(String)` function.
    - {+++} Added the `Path.deleteOnShutdown()` function.
      This is nothing more than a port of the way that the `File` class does it.
    - {+++} Added the `Path.eachLine(Charset, (String) -> Unit)` function.
      This acts like the  `.forEach(...)` function, but specific for the lines of a line, assumed to be in plain-text.
    - Renamed `Path.createSymbolicLink` to `Path.createSymbolicLinkTo`.
    - Renamed `Path.createLink` to `Path.createLinkTo`.
    - Renamed `Path.checkIfExists(...)` to `Path.requireExistence(...)`.
    - Renamed `Path.checkIfDirectory(...)` to `Path.requireDirectory(...)`.
    - {+++} Reworked `Path.requireExistence(...)` & `Path.requireDirectory(...)` to be more like the functions found in the std-lib for Kotlin in the `Preconditions.kt` file.
      This means that each function has been split up into two different functions, one accepting a closure for the message to send, and the other having the previously default value for the `message` parameter.
    - {+++} Added the `requireDesktop(() -> Any)` & `requireDesktop()` functions.
    - {+++} Added the `Path.createChildFile(String, vararg FileAttribute<*>)` function.
    - {+++} Added the `Path.createChildDirectory(String, vararg FileAttribute<*>)` function.

- ### /humanize/
  - #### File

    - {+++} Added the `humanize` package, this will contain various utilities for "humanizing" data, largely based on [Humanizer.jvm](https://github.com/MehdiK/Humanizer.jvm) and the original [Humanizer](https://github.com/Humanizr/Humanizer) for .NET. *(Go [here](https://gitlab.com/Olivki/humanizer-jvm) if you want a working version of `Humanizer.jvm`.)*

- ### /humanize/CaseConverter.kt
  - #### File

    - {+++} Added the `CaseConverter.kt` file which adds utilities for converting *to* and *from* various cases.

- ### /humanize/RomanNumerals.kt
  - #### File

    - {+++} Added the `RomanNumerals.kt` file. This provides utilities for converting numbers *to* and *from* Roman numerals.

- ### /collections/Maps.kt
  - #### File

    - {+++} Added the `Maps.kt` file. This provides utilities for various `Map` implementations.

- ### /misc/
  - #### File

    - {+++} Added the `misc` package. This package will contain files that aren't "big" enough to really varant their own packages.

- ### /misc/Exceptions.kt
  - #### File

    - {+++} Added the `Exceptions.kt` file. This provides utilities for `Exception`s.

  - #### Functions

    - {+++} Added the `multiCatch(vararg KClass)` closure function. This function "simulates" the behaviour of the multi-catch feature in Java for Kotlin. Credit goes to carleslc for the original code.

- ### /math/BigInteger.kt

  - #### Documentation

    - {+++} Added documentation to all of the functions for `BigInteger.kt`.

  - #### Functions

    - {+++} 

- ### /dom/Element.kt

  * #### Annotations

    * Removed the `ExperimentalFeature` annotation from the `AttributeMap` class and the `attributeMap` property.

- ### /misc/Reflection.kt

  - #### File

    - Added the `Reflection.kt` class.
      This class will house utilities for operations regarding Kotlin reflection.

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