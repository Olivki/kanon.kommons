[TOC levels=1-6]: # "### Table of Contents"

### Table of Contents
- [Paths.kt](#pathskt)
    - [Name Differences](#name-differences)
    - [Motivations](#motivations)
    - [Code Snippets](#code-snippets)
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

```Kotlin
println("Does image exist? ${(File("foo/bar/foobar.jpg").exists()}")
```

**Path**:

```Kotlin
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


### Code Snippets

*For more extensive examples, please look in the
[examples directory]().*

#### Creating a Path

Using the **/** operator:

```Kotlin
val image = "foo"/"bar"/"foobar.jpg"
```

Using the top level function:

```Kotlin
val image = pathOf("foo/bar/foobar.jpg")
```

Using the String extension method:

```Kotlin
val image = "foo/bar/foobar.jpg".toPath()
```

Using the inbuilt Java `java.io.File` to `java.nio.Path` method:

```Kotlin
val image = File("foo/bar/", "foobar.jpg").toPath()
```

Both `String` and `File` have also been provided with a extension
operator overloader for the `unaryPlus()` operator. So one could
replicate the two above code pieces using that instead, which would look
like this;

```Kotlin
val filePath = +File("foo/bar/", "foobar.jpg") // This is now a Path instance, not a File one.
val stringPath = +"foo/bar/foobar.jpg" // This is now a Path instance, not a String one.
```

