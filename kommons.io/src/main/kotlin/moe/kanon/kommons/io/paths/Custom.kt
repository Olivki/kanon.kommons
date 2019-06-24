/*
 * Copyright 2019 Oliver Berg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmMultifileClass
@file:JvmName("KFiles")

package moe.kanon.kommons.io.paths

import moe.kanon.kommons.io.pathMatcherOf
import moe.kanon.kommons.io.requireDirectory
import moe.kanon.kommons.io.requireExistence
import moe.kanon.kommons.io.requireRegularFile
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Reader
import java.io.UncheckedIOException
import java.math.BigInteger
import java.net.URI
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.DirectoryIteratorException
import java.nio.file.DirectoryStream
import java.nio.file.FileAlreadyExistsException
import java.nio.file.FileSystem
import java.nio.file.FileSystemNotFoundException
import java.nio.file.FileSystems
import java.nio.file.FileVisitOption
import java.nio.file.FileVisitResult
import java.nio.file.InvalidPathException
import java.nio.file.NoSuchFileException
import java.nio.file.NotDirectoryException
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.PathMatcher
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileAttribute
import java.nio.file.spi.FileSystemProvider
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import java.util.stream.Stream

// -- FACTORY FUNCTIONS -- \\
/**
 * Converts a path string, or a sequence of strings that when joined form a path string, to a [Path].
 *
 * If [more] does not specify any elements then the value of the [first] parameter is the path string to convert.
 * If `more` specifies one or more elements then each non-empty string, including `first`, is considered to be a
 * sequence of name elements *(see [Path])* and is joined to form a path string. The details as to how the Strings are
 * joined is provider specific but typically they will be joined using the [name-separator][FileSystem.getSeparator]
 * as the separator. For example, if the name separator is "`/`" and `getPath("/foo","bar","gus")` is invoked, then the
 * path string `"/foo/bar/gus"` is converted to a `Path`.
 *
 * A `Path` representing an empty path is returned if `first` is the empty string and `more` does not contain any
 * non-empty strings.
 *
 * The `Path` is obtained by invoking the [getPath][FileSystem.getPath] method of the [default][FileSystems.getDefault]
 * [FileSystem].
 *
 * Note that while this method is very convenient, using it will imply an assumed reference to the default `FileSystem`
 * and limit the utility of the calling code. Hence it should not be used in library code intended for flexible reuse.
 * A more flexible alternative is to use an existing `Path` instance as an anchor, such as:
 *
 * ```kotlin
 *      val dir: Path = ...
 *      val path = dir.resolve("file")
 * ```
 *
 * @param first the path string or initial part of the path string
 * @param more additional strings to be joined to form the path string.
 *
 * @return The resulting `Path`.
 *
 * @throws InvalidPathException if the path string cannot be converted to a `Path`.
 *
 * @see FileSystem.getPath
 */
fun pathOf(first: String, vararg more: String): Path = Paths.get(first, *more)

/**
 * Converts a path string, or a sequence of strings that when joined form a path string, to a [Path].
 *
 * If [more] does not specify any elements then the value of the [parent] parameter is the path string to convert.
 * If `more` specifies one or more elements then each non-empty string, including `first`, is considered to be a
 * sequence of name elements *(see [Path])* and is joined to form a path string. The details as to how the Strings are
 * joined is provider specific but typically they will be joined using the [name-separator][FileSystem.getSeparator]
 * as the separator. For example, if the name separator is "`/`" and `getPath("/foo","bar","gus")` is invoked, then the
 * path string `"/foo/bar/gus"` is converted to a `Path`.
 *
 * A `Path` representing an empty path is returned if `first` is the empty string and `more` does not contain any
 * non-empty strings.
 *
 * The `Path` is obtained by invoking the [getPath][FileSystem.getPath] method of the [default][FileSystems.getDefault]
 * [FileSystem].
 *
 * Note that while this method is very convenient, using it will imply an assumed reference to the default `FileSystem`
 * and limit the utility of the calling code. Hence it should not be used in library code intended for flexible reuse.
 * A more flexible alternative is to use an existing `Path` instance as an anchor, such as:
 *
 * ```kotlin
 *      val dir: Path = ...
 *      val path = dir.resolve("file")
 * ```
 *
 * @param parent the path string or initial part of the path string
 * @param more additional strings to be joined to form the path string.
 *
 * @return the resulting `Path`.
 *
 * @throws InvalidPathException if the path string cannot be converted to a `Path`.
 *
 * @see FileSystem.getPath
 */
fun pathOf(parent: Path, vararg more: String): Path = Paths.get(parent.toString(), *more)

/**
 * Converts the given [URI] to a [Path] instance.
 *
 * This method iterates over the [installed][FileSystemProvider.installedProviders] providers to locate the provider
 * that is identified by the `URI` [scheme][URI.getScheme] of the given `URI`. `URI` schemes are compared without
 * regard to case. If the provider is found then its [getPath][FileSystemProvider.getPath] method is invoked to convert
 * the `URI`.
 *
 * In the case of the default provider, identified by the `URI` scheme "file", the given `URI` has a non-empty path
 * component, and undefined query and fragment components. Whether the authority component may be present is platform
 * specific. The returned `Path` is associated with the [default][FileSystems.getDefault] file system.
 *
 * The default provider provides a similar *round-trip* guarantee to the [java.io.File] class. For a given `Path` *p*
 * it is guaranteed that
 *
 * > `Paths.get(`*p*`.toUri()).equals(`*p*`.toAbsolutePath())`
 *
 * so long as the original `Path`, the `URI`, and the new `Path` are all created in *(possibly different invocations
 * of)* the same  Java virtual machine. Whether other providers make any guarantees is provider specific and therefore
 * unspecified.
 *
 * @param uri the `URI` to convert.
 *
 * @return the resulting `Path`.
 *
 * @throws IllegalArgumentException if preconditions on the `uri` parameter do not hold. The format of the `URI` is
 * provider specific.
 * @throws FileSystemNotFoundException the file system, identified by the `URI`, does not exist and cannot be created
 * automatically, or the provider identified by the `URI`'s scheme component is not installed
 * @throws SecurityException if a security manager is installed and it denies an unspecified permission to access the
 * file system
 */
fun pathOf(uri: URI): Path = Paths.get(uri)

/**
 * Returns a new [Path] based on `this` string.
 */
fun String.toPath(): Path = Paths.get(this)

/**
 * Returns a new [Path] based on `this` uri.
 */
fun URI.toPath(): Path = Paths.get(this)

// -- EXTENSIONS -- \\
/**
 * The file name of `this` [file][Path] in [String] format.
 *
 * The setter for this property uses the [renameTo] function with the [StandardCopyOption.COPY_ATTRIBUTES] option selected.
 *
 * If you want more control over the renaming process, use [renameTo].
 *
 * @see Path.getFileName
 * @see Path.simpleName
 * @see Path.extension
 * @see Path.renameTo
 */
var Path.name: String
    get() = this.fileName.toString()
    set(name) {
        renameTo(name, StandardCopyOption.COPY_ATTRIBUTES)
    }

/**
 * The name of `this` [file][Path] with the extension trimmed from it in [String] format.
 *
 * If the path is a directory, the full name will be returned.
 *
 * @throws IOException if the [simpleName] inside of the setter contains a `'.'` character.
 *
 * @see Path.name
 * @see Path.extension
 */
var Path.simpleName: String
    get() = when {
        this.isDirectory -> name
        else -> name.substringBeforeLast('.')
    }
    set(simpleName) = when {
        this.name.substringBeforeLast('.') == name || isDirectory -> name = simpleName
        simpleName.contains('.') -> throw IOException(
            "\"Path.simpleName\" does not support the usage of '.' " +
                "inside of the setter ($simpleName), use \"Path.name\" " +
                "or \"Path.extension\" for such actions."
        )
        else -> this.name = "$simpleName.${this.extension}"
    }

/**
 * The extension of the path in [String] format.
 *
 * @throws IOException when accessing the getter this will be thrown if the path has no extension or it's a directory,
 * when accessing the setter, this will be thrown if the path is that of a directory.
 *
 * @see Path.name
 * @see Path.simpleName
 */
var Path.extension: String
    get() = when {
        this.name.substringBeforeLast('.') != name -> name.substringBeforeLast('.')
        this.isDirectory -> throw IOException("Path <$this> is a directory, and directories do not have extensions")
        else -> throw IOException("Path <$this> does not have an extension.")
    }
    set(extension) {
        requireRegularFile(this) { "Path <$this> is a directory, and directories do not have extensions" }

        this.name = "$simpleName.$extension"
    }

private class AttributeMap internal constructor(
    private val path: Path,
    private val original: Map<out String, Any>
) : MutableMap<String, Any> by original.toMutableMap() {

    override fun put(key: String, value: Any): Any? = path.setAttribute(key, value)

    // TODO: Figure out how to actually remove attributes.
    override fun remove(key: String): Any? {
        throw NotImplementedError("AttributeMap does not currently support removal operations.")
    }

    override fun get(key: String): Any? = path.getAttribute(key)

    override fun putAll(from: Map<out String, Any>) {
        for (attribute in from) this += attribute.toPair()
    }

    override fun clear() {
        throw IOException("Invalid operation; Can't clear an attribute map.")
    }

    override fun containsValue(value: Any): Boolean = original.any { it.value == value }

    // This is a really nasty way of checking whether or not the attribute actually exists, and using exception
    // catching for this should generally be avoided when possible.
    override fun containsKey(key: String): Boolean = try {
        path.getAttribute(key)
        true
    } catch (e: IllegalArgumentException) {
        false
    }
}

/**
 * A [Map] of all the attributes of the file that this path points to.
 *
 * The order of the map is: `attribute_name:attribute`
 *
 * @see readAttributes
 */
val Path.attributes: Map<String, Any> get() = AttributeMap(this, this.readAttributes("*"))

/**
 * Writes the given [line] to `this` [file][Path].
 *
 * **Example Usage:**
 *
 * Say we want to create a text file containing a list, using the multi-line strings feature in Kotlin, we can do this:
 *
 * ```kotlin
 * val path: Path = ...
 * path.writeLine(
 *      """
 *      | 1. ..
 *      | 2. ...
 *      | 3. ...
 *      | 4. ...
 *      """.trimMargin()
 * )
 * ```
 */
fun Path.writeLine(line: String, charset: Charset, vararg options: OpenOption): Path = apply {
    val encoder = charset.newEncoder()
    val out = this.newOutputStream(*options)
    BufferedWriter(OutputStreamWriter(out, encoder)).use { writer ->
        writer.append(line)
        writer.newLine()
    }
}

/**
 * Writes the given [line] to `this` [file][Path].
 *
 * **Example Usage:**
 *
 * Say we want to create a text file containing a list, using the multi-line strings feature in Kotlin, we can do this:
 *
 * ```kotlin
 * val path: Path = ...
 * path.writeLine(
 *      """
 *      | 1. ..
 *      | 2. ...
 *      | 3. ...
 *      | 4. ...
 *      """.trimMargin()
 * )
 * ```
 */
fun Path.writeLine(line: String, vararg options: OpenOption): Path =
    this.writeLine(line, StandardCharsets.UTF_8, *options)

/**
 * Return a lazily populated [Sequence], the elements of which are the entries in this [directory][Path]
 *
 * The listing is not recursive.
 *
 * The elements of the stream are [Path] objects that are obtained as if by [resolving(Path)][Path.resolve] the name of
 * the directory entry against this [Path]. Some file systems maintain special links to the directory itself and the
 * directory's parent directory. Entries representing these links are not included.
 *
 * The stream is *weakly consistent*. It is thread safe but does not freeze the directory while iterating, so it may
 * *(or may not)* reflect updates to the directory that occur after returning from this method.
 *
 * The returned stream encapsulates a [DirectoryStream].
 *
 * If timely disposal of file system resources is required, the `try-with-resources` construct should be used to ensure
 * that the stream's [close][Stream.close] method is invoked after the stream operations are completed.
 *
 * Operating on a closed stream behaves as if the end of stream has been reached. Due to read-ahead, one or more
 * elements may be returned after the stream has been closed.
 *
 * If an [IOException] is thrown when accessing the directory after this method has returned, it is wrapped in an
 * [UncheckedIOException] which will be thrown from the method that caused the access to take place.
 *
 * @return the [Sequence] describing the contents of the directory
 *
 * @throws NotDirectoryException if the file could not otherwise be opened because it is not a directory.
 * *(optional specific exception)*.
 * @throws IOException if an I/O error occurs when opening the directory
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the directory
 *
 * @see newDirectoryStream
 */
val Path.entries: Sequence<Path>
    get() {
        val directoryStream = this.newDirectoryStream()
        try {
            val delegate = directoryStream.iterator()

            // Re-wrap DirectoryIteratorException to UncheckedIOException
            val iterator = object : Iterator<Path> {
                override fun hasNext(): Boolean {
                    try {
                        return delegate.hasNext()
                    } catch (e: DirectoryIteratorException) {
                        throw UncheckedIOException(e.cause)
                    }

                }

                override fun next(): Path {
                    try {
                        return delegate.next()
                    } catch (e: DirectoryIteratorException) {
                        throw UncheckedIOException(e.cause)
                    }

                }
            }

            return iterator.asSequence()
        } catch (e: Error) {
            try {
                directoryStream.close()
            } catch (ex: IOException) {
                try {
                    e.addSuppressed(ex)
                } catch (ignore: Throwable) {
                }
            }

            throw e
        } catch (e: RuntimeException) {
            try {
                directoryStream.close()
            } catch (ex: IOException) {
                try {
                    e.addSuppressed(ex)
                } catch (ignore: Throwable) {
                }
            }

            throw e
        }

    }

/**
 * Returns a [List] containing any files that match the given [predicate].
 *
 * This method walks the file tree in exactly the manner specified by the [walk] method. For each file encountered,
 * the given [predicate] is invoked with its `Path` and [BasicFileAttributes]. The `Path` object is obtained as if
 * by [resolving(Path)][Path.resolve] the relative path against `this` and is only included in the returned `Sequence` if
 * the `predicate` returns true.
 *
 * If an [IOException] is thrown when accessing the directory after returned from this method, it is wrapped in an
 * [UncheckedIOException] which will be thrown from the method that caused the access to take place.
 *
 * @param maxDepth the maximum number of directory levels to search
 *
 * ([Int.MAX_VALUE] by default)
 * @param predicate the function used to decide whether this [file][Path] should be included in the returned stream
 * @param options options to configure the traversal
 *
 * @throws IllegalArgumentException if [maxDepth] is negative
 * @throws SecurityException if the security manager denies access to the starting file. In the case of the default
 * provider, the [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the directory
 * @throws IOException if an I/O error is thrown when accessing the starting file
 *
 * @see walk
 */
@JvmName("filterChildren")
inline fun Path.filter(
    crossinline predicate: (Path) -> Boolean,
    maxDepth: Int = Int.MAX_VALUE,
    vararg options: FileVisitOption
): List<Path> {
    val tempMatches: MutableList<Path> = ArrayList()

    this.walkFileTree(options.toSet(), maxDepth, object : SimplePathVisitor() {
        override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            // If the file we're visiting is somehow null, just skip over it.
            if (file == null) return FileVisitResult.CONTINUE

            if (predicate(file)) tempMatches.add(file)

            return FileVisitResult.CONTINUE
        }

        // We just skip over any files we can't access.
        override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult = FileVisitResult.CONTINUE
    })

    return tempMatches.toList()
}

/**
 * Returns a [List] containing any files that match the given [predicate].
 *
 * This method walks the file tree in exactly the manner specified by the [walk] method. For each file encountered,
 * the given [predicate] is invoked with its `Path` and [BasicFileAttributes]. The `Path` object is obtained as if
 * by [resolving(Path)][Path.resolve] the relative path against `this` and is only included in the returned `Sequence` if
 * the `predicate` returns true.
 *
 * @param predicate the function used to decide whether this [file][Path] should be included in the returned stream
 *
 * @throws SecurityException if the security manager denies access to the starting file. In the case of the default
 * provider, the [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the directory.
 * @throws IOException if an I/O error is thrown when accessing the starting file.
 *
 * @see walk
 */
@JvmName("filterChildren")
inline fun Path.filter(crossinline predicate: (Path) -> Boolean): List<Path> = this.filter(predicate, Int.MAX_VALUE)

/**
 * Read all lines from a file as a [Sequence]. Bytes from the file are decoded into characters using the
 * [UTF-8][StandardCharsets.UTF_8] [charset][Charset].
 *
 * This property works as if invoking it were equivalent to evaluating the expression:
 *
 * ```kotlin
 *  val path: Path = ...
 *  path.linesAsSequence(StandardCharsets.UTF_8)
 * ```
 *
 * @throws IOException If an I/O error occurs opening the file.
 * @throws SecurityException In the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the file.
 *
 * @see Path.linesAsSequence
 * @see readLines
 * @see newBufferedReader
 * @see java.io.BufferedReader.lines
 */
val Path.lines: Sequence<String> get() = this.linesAsSequence()

/**
 * Reads all the lines from this [file][Path] into a [Sequence].
 *
 * Unlike [readAllLines(Path, Charset)][readLines], this method does not read all lines into a [List], but instead
 * populates lazily as the stream is consumed.
 *
 * Bytes from the file are decoded into characters using the specified charset and the same line terminators as
 * specified by [readLines] are supported.
 *
 * After this method returns, then any subsequent I/O exception that occurs while reading from the file or when a
 * malformed or unmappable byte sequence is read, is wrapped in an [UncheckedIOException] that will be thrown from the
 * [Sequence] method that caused the read to take place. In case an [IOException] is thrown when closing the file, it is
 * also wrapped as an [UncheckedIOException].
 *
 * The returned sequence encapsulates a [Reader].
 *
 * @param charset the charset to use for decoding
 *
 * ([UTF_8][StandardCharsets.UTF_8] by default)
 *
 * @throws IOException if an I/O error occurs opening the file
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the file
 *
 * @see Path.lines
 * @see readLines
 * @see newBufferedReader
 * @see java.io.BufferedReader.lines
 */
@Throws(IOException::class)
fun Path.linesAsSequence(charset: Charset = StandardCharsets.UTF_8): Sequence<String> {
    val reader = this.newBufferedReader(charset)
    try {
        return reader.lineSequence()
    } catch (e: Error) {
        try {
            reader.close()
        } catch (ex: IOException) {
            try {
                e.addSuppressed(ex)
            } catch (ignore: Throwable) {
            }
        }

        throw e
    } catch (e: RuntimeException) {
        try {
            reader.close()
        } catch (ex: IOException) {
            try {
                e.addSuppressed(ex)
            } catch (ignore: Throwable) {
            }
        }

        throw e
    }
}

/**
 * Returns a [PathMatcher] instance from the [fileSystem][Path.getFileSystem] used by `this` path.
 *
 * For more information regarding how a path matcher works, see [FileSystem.getPathMatcher].
 *
 * @receiver the [Path] instance to use the underlying `fileSystem` of
 *
 * @param [syntax] the syntax to use
 * @param [pattern] the pattern to use
 *
 * @see Path.getFileSystem
 * @see FileSystem.getPathMatcher
 */
@JvmName("getPathMatcherFrom")
fun Path.pathMatcherOf(syntax: String, pattern: String): PathMatcher =
    this.fileSystem.getPathMatcher("$syntax:$pattern")

/**
 * Returns whether or not `this` file matches the specified [globPattern].
 *
 * @param [globPattern] the glob pattern to match the files in `this` directory against
 *
 * If you are unfamiliar with *glob syntax*, see [What is a Glob](https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob).
 *
 * For a more thorough explanation, see the documentation for [FileSystem.getPathMatcher].
 *
 * @receiver the directory of which to check through
 */
fun Path.matchesGlobPattern(globPattern: String): Boolean = this.pathMatcherOf("glob", globPattern).matches(this)

/**
 * Checks whether the [other] path is a child of this [directory][Path].
 */
@JvmName("isChild")
operator fun Path.contains(other: Path): Boolean = this.entries.contains(other)

/**
 * Returns whether or not `this` [directory][Path] has any children that matches the specified [globPattern].
 *
 * This function *only* checks inside of itself, and not inside of any of it's children; if the former is the
 * behaviour you're looking for, refer to [Path.allEntries].
 *
 * @param globPattern the glob pattern to match the files in `this` directory against
 *
 * If you are unfamiliar with *glob syntax*, see [What is a Glob](https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob).
 *
 * For a more thorough explanation, see the documentation for [FileSystem.getPathMatcher].
 *
 * @exception NoSuchFileException if `this` file doesn't actually exist
 * @exception NotDirectoryException if `this` file is **not** a directory
 *
 * @see FileSystem.getPathMatcher
 */
@JvmName("hasChild")
operator fun Path.contains(globPattern: String): Boolean {
    requireDirectory(this)
    return this.entries.any { it.matchesGlobPattern(globPattern) }
}

/**
 * Returns the first file that matches the given [globPattern], or if none is found, a [FileNotFoundException] will be
 * thrown.
 *
 * @param globPattern the glob pattern to match the files in `this` directory against
 *
 * If you are unfamiliar with *glob syntax*, see [What is a Glob](https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob).
 *
 * For a more thorough explanation, see the documentation for [FileSystem.getPathMatcher].
 *
 * @receiver the directory of which to check through
 *
 * @exception NoSuchFileException if `this` file doesn't actually exist, or if there exists no child that matches the
 * given `globPattern`
 * @exception NotDirectoryException if `this` file is **not** a directory
 *
 * @see Path.getOrNull
 */
@JvmName("getChild")
operator fun Path.get(globPattern: String): Path = this.getOrNull(globPattern) ?: throw FileNotFoundException(
    "No file matching the glob pattern <$globPattern> could be found in <$this>"
)

/**
 * Returns the first file that matches the given [globPattern], or `null` if none is found.
 *
 * @param globPattern the glob pattern to match the files in `this` directory against
 *
 * If you are unfamiliar with *glob syntax*, see [What is a Glob](https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob).
 *
 * For a more thorough explanation, see the documentation for [FileSystem.getPathMatcher].
 *
 * @receiver the directory of which to check through
 *
 * @exception NoSuchFileException if `this` file doesn't actually exist
 * @exception NotDirectoryException if `this` file is **not** a directory
 *
 * @see Path.get
 */
@JvmName("getChildOrNull")
fun Path.getOrNull(globPattern: String): Path? {
    requireDirectory(this)
    return this.entries.firstOrNull { it.matchesGlobPattern(globPattern) }
}

/**
 * Returns a sequence containing only elements matching the given [globPattern].
 *
 * This function only allows looking *downwards* into the hierarchy, and not *upwards*. This means that glob
 * patterns that are designed to match something that's in a *parent* directory or higher, will just return an empty
 * `Sequence`.
 *
 * @receiver the [Sequence] to filter
 *
 * @param globPattern the glob pattern to match the files in `this` directory against
 *
 * If you are unfamiliar with *glob syntax*, see [What is a Glob](https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob).
 *
 * For a more thorough explanation, see the documentation for [FileSystem.getPathMatcher].
 */
@JvmName("filterChildrenByGlob")
fun Sequence<Path>.filterByGlob(globPattern: String): Sequence<Path> =
    this.filter { it.fileSystem.pathMatcherOf("glob", globPattern).matches(it) }

/**
 * Attempts to recursively delete all the files inside of this [directory][Path], and any files inside sub-directories.
 *
 * The [maxDepth] parameter is the maximum number of levels of directories to visit. A value of `0` means that only the
 * starting file is visited, unless denied by the security manager. A value of [MAX_VALUE][Integer.MAX_VALUE] may be
 * used to indicate that all levels should be visited. The `visitFile` method is invoked for all files, including
 * directories, encountered at `maxDepth`, unless the basic file attributes cannot be read, in which case the
 * `visitFileFailed` method is invoked.
 *
 * **Note:** When `deleteDirectories` is `true`, any and all directories that the walker encounters *will* be deleted.
 * Basically, when deleting directories, it completely ignores whether or not the directory matches the specified
 * `globPattern`.
 *
 * @param globPattern the glob pattern to match any encountered files against, if it's a correct match, the file will
 * be deleted
 *
 * If you are unfamiliar with *glob syntax*, see [What is a Glob](https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob).
 *
 * For a more thorough explanation, see the documentation for [FileSystem.getPathMatcher].
 *
 * ("`*`" by default) `// This means that ALL files that are encountered will be deleted.`
 * @param maxDepth the maximum number of directory levels to visit
 *
 * ([Int.MAX_VALUE] by default)
 * @param deleteDirectories whether or not any and all sub-directories of the directory should also be deleted
 *
 * (`false` by default.)
 *
 * @param options options to configure the traversal
 *
 * @exception NoSuchFileException if `this` file doesn't actually exist
 * @exception NotDirectoryException if `this` file is **not** a directory
 */
@JvmOverloads fun Path.cleanDirectory(
    globPattern: String = "*",
    maxDepth: Int = Int.MAX_VALUE,
    deleteDirectories: Boolean = false,
    vararg options: FileVisitOption
) {
    requireDirectory(this) { "Can't clean a non-directory <${toString()}>" }

    this.walkFileTree(options.toSet(), maxDepth, object : SimplePathVisitor() {
        override fun visitFile(file: Path, attributes: BasicFileAttributes): FileVisitResult {
            // Only delete the file if it matches the specified globPattern.
            if (moe.kanon.kommons.io.pathMatcherOf("glob", globPattern).matches(file)) file.deleteIfExists()

            return FileVisitResult.CONTINUE
        }

        // We just skip over any files we can't access.
        override fun visitFileFailed(file: Path, exc: IOException): FileVisitResult = FileVisitResult.CONTINUE

        // After all the files in the directory have been visited, delete the directory if deleteDirectories is true.
        override fun postVisitDirectory(directory: Path, exception: IOException?): FileVisitResult {
            // If deleteDirectories is false, then just skip the deletion part.
            if (!deleteDirectories) return FileVisitResult.CONTINUE else directory.deleteIfExists()

            if (exception != null) throw exception

            return FileVisitResult.CONTINUE
        }
    })
}

/**
 * Reads the contents of this [file][Path] into a singular [String].
 *
 * @param charset the charset to use for decoding
 *
 * ([UTF-8][StandardCharsets.UTF_8] by default)
 * @param separator the string to use for separating the lines
 *
 * ([System.lineSeparator] by default)
 *
 * @see readLines
 */
@JvmOverloads fun Path.readToString(
    charset: Charset = StandardCharsets.UTF_8,
    separator: String = System.lineSeparator()
): String {
    requireExistence(this)
    return this.readLines(charset).joinToString(separator)
}

/**
 * Calculates the size of this [directory][Path], returning the size as a [BigInteger].
 *
 * **Note:** This will ignore any symbolic links when calculating the size.
 *
 * @exception NotDirectoryException if the `receiver` `Path` is *not* a directory.
 */
val Path.directorySize: BigInteger
    get() {
        requireDirectory(this)

        var size = BigInteger.ZERO

        this.walkFileTree(visitor = object : SimplePathVisitor() {
            override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
                // If the file we're visiting is somehow null, just skip over it.
                size += file?.size?.toBigInteger() ?: return FileVisitResult.CONTINUE
                return FileVisitResult.CONTINUE
            }

            // We just skip over any files we can't access.
            override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult = FileVisitResult.CONTINUE
        })

        return size
    }

/**
 * Performs the given [action] on each individual line of this `file`, using the given [charset].
 *
 * @receiver the file from which to read the lines
 *
 * @throws [NoSuchFileException] if `this` file does *not* exist
 */
inline fun Path.eachLine(charset: Charset, action: (String) -> Unit) {
    requireExistence(this)
    for (line in linesAsSequence(charset)) action(line)
}

/**
 * Performs the given [action] on each individual line of this `file`, using the given [charset].
 *
 * @receiver the file from which to read the lines
 *
 * @throws [NoSuchFileException] if `this` file does *not* exist
 */
inline fun Path.eachLine(action: (String) -> Unit) = this.eachLine(StandardCharsets.UTF_8, action)

/**
 * Creates a new file with the given [fileName], using `this` directory as the root, and returns the result.
 *
 * @receiver the [Path] to use as the root directory for the new file.
 *
 * @return the newly created file, or the file that already existed.
 *
 * @throws [FileAlreadyExistsException] if there already exists a directory in `this` directory with the specified
 * [name].
 * @throws [NoSuchFileException] if `this` file does *not* exist.
 * @throws [NotDirectoryException] if `this` file is *not* a directory.
 */
fun Path.createChildFile(fileName: String, vararg attributes: FileAttribute<*>): Path {
    requireDirectory(this)
    return this.resolve(fileName).createFile(*attributes)
}

/**
 * Returns the file with the specified [fileName] stored in `this` directory, or creates a new file using the
 * specified `fileName` and returns that.
 *
 * @receiver the [Path] to use as the root directory for the new file.
 *
 * @return the file stored under the specified [fileName], or the newly created file.
 *
 * @throws [NoSuchFileException] if `this` file does *not* exist.
 * @throws [NotDirectoryException] if `this` file is *not* a directory.
 *
 * @see createChildFile
 */
fun Path.getOrCreateChildFile(fileName: String, vararg attributes: FileAttribute<*>): Path {
    requireDirectory(this)
    return if (this.resolve(name).notExists) this.resolve(fileName).createFile(*attributes) else this.resolve(name)
}

/**
 * Creates a new directory with the given [name], using `this` directory as the root, and returns the result.
 *
 * Note that this function will throw a [FileAlreadyExistsException] if there already exists a directory with the
 * specified `name`, use [getOrCreateChildDirectory] if you are unsure whether a directory already exists with the specified
 * `name`.
 *
 * @receiver the [Path] to use as the root directory for the new directory.
 *
 * @return the newly created `directory`.
 *
 * @throws [FileAlreadyExistsException] if there already exists a directory in `this` directory with the specified
 * [name].
 * @throws [NoSuchFileException] if `this` file does not exist.
 * @throws [NotDirectoryException] if `this` file is not a directory.
 *
 * @see getOrCreateChildDirectory
 */
fun Path.createChildDirectory(name: String, vararg attributes: FileAttribute<*>): Path {
    requireDirectory(this)
    return this.resolve(name).createDirectory(*attributes)
}

/**
 * Returns the directory with the specified [name] stored in `this` directory, or creates a new directory using the
 * specified `name` and returns that.
 *
 * @receiver the [Path] to use as the root directory for the new file.
 *
 * @return the directory stored under the specified [name], or the newly created directory.
 *
 * @throws [NoSuchFileException] if `this` file does *not* exist.
 * @throws [NotDirectoryException] if `this` file is *not* a directory.
 *
 * @see createChildDirectory
 */
fun Path.getOrCreateChildDirectory(name: String, vararg attributes: FileAttribute<*>): Path {
    requireDirectory(this)
    return if (this.resolve(name).notExists) this.resolve(name).createDirectory(*attributes) else this.resolve(name)
}

/**
 * Creates *(if not already existing)* a series of directories reflecting the given [date], up to the day unit, under
 * `this` directory.
 *
 * If the given [date] is that of `2018-05-25` then the following directories will be created;
 *
 * `./2018/05/25/`
 *
 * @receiver The `directory` that should act as the parent for the date directories.
 *
 * @param [date] The date to create the directories from.
 *
 * ([LocalDate.now] by default)
 *
 * @return The last directory in the chain of the newly created directories.
 *
 * @throws [NoSuchFileException] If the `Path` receiver does not have an existing file on the `file-system`.
 * @throws [NotDirectoryException] If the `Path` receiver is *not* a directory.
 */
@JvmOverloads fun Path.createDateDirectories(date: LocalDate = LocalDate.now()): Path {
    requireDirectory(this)
    val text = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
    return this.resolve(text).createDirectories()
}

/**
 * Creates *(if not already existing)* a series of directories reflecting the given [date and time][dateTime], up to the
 * seconds, under `this` directory.
 *
 * If the given [date and time][dateTime] is that of `2018-05-25T20:16:03` then the following directories will be
 * created;
 *
 * `./2018/05/25/20/16/03/`
 *
 * @receiver the `directory` that should act as the parent for the date directories.
 *
 * @param [dateTime] the date and time to create the directories from.
 *
 * ([LocalDateTime.now] by default)
 *
 * @return the last directory in the chain of the newly created directories.
 *
 * @throws [NoSuchFileException] if the `Path` receiver does not have an existing file on the `file-system`.
 * @throws [NotDirectoryException] if the `Path` receiver is *not* a directory.
 */
@JvmOverloads fun Path.createDateTimeDirectories(dateTime: LocalDateTime = LocalDateTime.now()): Path {
    requireDirectory(this)
    val text = dateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm/ss"))
    return this.resolve(text).createDirectories()
}

/**
 * Executes the specified [action] if `this` file [is a regular file][Path.isRegularFile].
 *
 * @receiver the [Path] to check against.
 *
 * @return if `this` file is a regular file, then it returns `this` file with the specified [action] applied to it,
 * otherwise it just returns `this` file.
 *
 * @throws [NoSuchFileException] if `this` file does not exist on the `file-system`.
 */
inline fun Path.ifRegularFile(action: (Path) -> Unit): Path {
    requireExistence(this)
    return if (this.isRegularFile) this.apply(action) else this
}

/**
 * Executes the specified [action] if `this` file [is a directory][Path.isDirectory].
 *
 * @receiver the [Path] to check against.
 *
 * @return if `this` file is a directory, then it returns `this` file with the specified [action] applied to it,
 * otherwise it just returns `this` file.
 *
 * @throws [NoSuchFileException] if `this` file does not exist on the file-system
 */
inline fun Path.ifDirectory(action: (Path) -> Unit): Path {
    requireExistence(this)
    return if (this.isDirectory) this.apply(action) else this
}

/**
 * Overwrites the bytes of `this` file with the bytes of the specified [source].
 *
 * If `this` file does not exist, it will be created.
 *
 * @receiver the file to overwrite the bytes of
 *
 * @param [source] the file to use the bytes from for overwriting `this` file
 *
 * @throws [NoSuchFileException] if the specified [source] file does not exist on the file-system
 */
fun Path.overwriteBytes(source: Path): Path {
    requireExistence(source)
    return this.writeBytes(
        source.readBytes(),
        StandardOpenOption.WRITE,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
    )
}

/**
 * Creates and returns a [FileSystem] based on `this` file, with the specified [env] variables and the specified
 * [classLoader].
 */
@JvmName("createFileSystemFrom")
@JvmOverloads fun Path.createFileSystem(
    env: Map<String, String> = emptyMap(),
    classLoader: ClassLoader? = null
): FileSystem = FileSystems.newFileSystem(toUri(), env, classLoader)

/**
 * Returns `this` directory if it exists, otherwise creates a new directory and returns that.
 *
 * @receiver the directory to retrieve or create
 *
 * @throws UnsupportedOperationException if the array contains an attribute that cannot be set atomically
 * when creating the directory
 * @throws FileAlreadyExistsException if a directory could not otherwise be created because this [file][Path] of
 * that name already exists *(optional specific exception)*
 * @throws IOException if an I/O error occurs or the parent directory does not exist
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the new directo
 */
fun Path.getOrCreateDirectory(vararg attributes: FileAttribute<*>): Path =
    if (this.exists) this else this.createDirectory(*attributes)

/**
 * Returns `this` file if it exists, otherwise creates a new directory and returns that.
 *
 * @receiver the file to retrieve or create
 *
 * @throws UnsupportedOperationException if the array contains an attribute that cannot be set atomically when creating
 * the file
 * @throws FileAlreadyExistsException if this [file][Path] of that name already exists. *(optional specific exception)*.
 * @throws IOException if an I/O error occurs or the parent directory does not exist
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the new file
 */
fun Path.getOrCreateFile(vararg attributes: FileAttribute<*>): Path =
    if (this.exists) this else this.createFile(*attributes)