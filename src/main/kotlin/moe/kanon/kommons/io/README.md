[TOC levels=1-6]: # "### Table of Contents"

### Table of Contents
- [Paths.kt](#pathskt)
    - [Name Differences](#name-differences)
    - [Motivations](#motivations)
    - [Code Snippets & Examples](#code-snippets--examples)
        - [Creating a Path](#creating-a-path)


# Paths.kt

The Paths file contains extension methods & properties for the `Path`
class implemented in Java 1.7, to make working with it easier and less
clunky.

### Name Differences

*'path' is used in place of any path instance.*

<table>
  <thead>
    <tr>
      <th>Original Name</th>
      <th>New Name</th>
    </tr>
  </thead>
  <tbody>
    <tr>
       <td>Files.copy(path, inputStream, ...)</td>
       <td>path.transform(inputStream, ...) </td>
    </tr>
  </tbody>
</table>


### Motivations

As noted in comments in the `Paths.kt` file, the `java.io.File` class
should by all means by considered to be deprecated, and one should never
use it in new development when working with a Java version that's >1.6,
unless, you're working with a legacy system that's built around it.

The biggest problem with `java.nio.Path` is that one has to use
`java.nio.Files` to do essentially anything at all with it.

This results in rather clunky syntax, and ends up making `java.io.File`
being preferable to use because it does not force one to write clunky
code.

For example:

**File**:

```kotlin
println("Does image exist? ${File("foo/bar/foobar.jpg").exists()}")
```

**Path**:

```kotlin
println("Does image exist? ${Files.exists(Paths.get("foo/bar/foobar.jpg"))}")
```

The **File** example is a lot cleaner, and it's easier to understand
*what* it does with just a quick glance. The fact that the **Path**
example is also actually working on *three* different classes
(`java.nio.Path`, `java.nio.Paths`, `java.nio.Files`), rather than the
*one* class that's used in the **File** example. (`java.io.File`).

So, while the `Path` class is definitely a step-up in terms of features
and in how it's made, it's sadly not as intuitive to use as the `File`
class is, which is probably one of the reasons why the `File` class is
still so widely used even today.

In Java, there's not much that can be done regarding this, unless they
were to rewrite the entire thing, but that's not going to happen due to
several reasons.

But in Kotlin, we can make the `Path` class act much like that of the
`File` class with the usage of extension methods and properties.


### Code Snippets & Examples

#### Creating a Path

kanon.kommons offers a variety of different ways to actually go about creating a Path instance:

```kotlin
// Via the top-level function. (There exists two versions of this function, 
// one which accepts String(s), and the other accepts a URI.)
pathOf("foo/bar")
// The string variant also accepts more than one string
pathOf("foo", "bar")

// Via the "KPath" object.
// The aim of the KPath object is to create a syntax that is similar to that of
// creating a normal File instance.
// The has the same abilities as the pathOf function, and some more.
KPath("foo/bar")
KPath("foo", "bar")
// It also supports the same syntax that you'd use when creating a File that's a child of another File.
KPath(parentPath, "bar")
// This simply uses the "resolve" function from the parentPath, 
// and it's therefore recommended to just use that instead.
// If you want use this syntax to create a Path with a File as a parent, you can do something like:
KPath(!parentFile, "bar") // This uses another feature of kanon.kextensions which is covered further down.

// Via the "not" operator.
// kanon.kextensions introduces a transformation function to the "not" 
// operator when used on a Path or File object.
// On a File object it converts it into a Path (by calling the "toPath" method.)
// On a Path object it converts it into a File (by calling the "toFile" method.)
// So:
val file = File("foo/bar") // This is currently a File object.
file.linesStream() // This will not compile, as File does not have the linesStream() function.
(!file).linesStream() // This will however compile, as the file has now been converted to a path.
// It works the same way on any Path instances, except that it converts the path into a File.
// This feature might be a bit controversial, namely due to the fact that it's using the "not" operator
// to accomplish this. I however feel it fitting, for the "not" operator is generally used for
// transformative operations.

// Via the "div" operator.
// This is a neat little hack shamelessly taken from the user "superbobry" on GitHub.
"foo" / "bar" // This will create a valid Path file, it's essentially the same as doing
// pathOf("foo", "bar") / KPath("foo", "bar").
// This method also supports mixing path instances and strings within it, so you could do:
parentPath / "bar"
// or
"foo" / documentPath

// There's of course also the functions provided in the core Java library:
Paths.get("foo/bar")
// &
Paths.get("foo", "bar")
```

For more information regarding any of these functions, refer to the documentation.