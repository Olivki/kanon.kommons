/*
 * Copyright 2019-2020 Oliver Berg
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

import moe.kanon.kommons.CloseableSequence
import moe.kanon.kommons.io.pathMatcherOf
import moe.kanon.kommons.io.requireDirectory
import moe.kanon.kommons.io.requireFileExistence
import moe.kanon.kommons.io.resourceOf
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.Reader
import java.io.UncheckedIOException
import java.math.BigInteger
import java.net.URI
import java.net.URL
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
import java.nio.file.StandardOpenOption.CREATE
import java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
import java.nio.file.StandardOpenOption.WRITE
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileAttribute
import java.nio.file.attribute.FileTime
import java.nio.file.spi.FileSystemProvider
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import java.util.NoSuchElementException

private const val BUFFER_SIZE = 8192

// -- FACTORY FUNCTIONS -- \\
/**
 * Converts a path string to a [Path].
 *
 * @return the resulting `Path`.
 *
 * @throws InvalidPathException if the path string cannot be converted to a `Path`.
 *
 * @see FileSystem.getPath
 */
fun pathOf(path: String): Path = Paths.get(path)

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
 * @return the resulting `Path`.
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
 * Note that while this function is very convenient, using it will imply an assumed reference to the default `FileSystem`
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
fun pathOf(parent: Path, vararg more: String): Path = parent.resolve(*more)

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
 * Returns a [Path] that points towards the resource with the given [name], or `null` if none is found.
 *
 * @receiver the object which [class][Any.javaClass] is used to retrieve the resource
 *
 * @see [Class.getResource]
 */
fun Any.resourcePathOf(name: String): Path? = this.resourceOf(name)?.toPath()

/**
 * Returns a new [Path] based on `this` uri.
 */
fun URI.toPath(): Path = Paths.get(this)

/**
 * Returns a new [Path] that points towards `this` url.
 */
fun URL.toPath(): Path = this.toURI().toPath()

// -- EXTENSIONS -- \\
/**
 * Resolve the given [path][other] against `this` path.
 *
 * If the `other` parameter is an [absolute][Path.isAbsolute] path then this function trivially returns `other`. If
 * `other` is an *empty path* then this method trivially returns `this` path. Otherwise this function considers `this`
 * path to be a directory and resolves the given path against `this` path. In the simplest case, the given path does not
 * have a [root][Path.getRoot] component, in which case this function *joins* the given path to `this` path and returns
 * a resulting path that [ends][Path.endsWith] with the given path. Where the given path has a root component then
 * resolution is highly implementation dependent and therefore unspecified.
 *
 * @param [other] the path to resolve against this path
 *
 * @return the resulting path
 */
operator fun Path.plus(other: Path): Path = this.resolve(other)

/**
 * Converts a given [path string][other] to a [path][Path] and resolves it against `this` path in exactly the manner
 * specified by the [resolve(Path)][Path.resolve] function.
 *
 * For example, suppose that the name  separator is `"/"` and a path represents `"foo/bar"`, then invoking this method
 * with the path string `"gus"` will result in the path `"foo/bar/gus"`.
 *
 * @param [other] the path string to resolve against this path
 *
 * @return the resulting path
 *
 * @throws [InvalidPathException] if the path string cannot be converted to a Path.
 */
operator fun Path.plus(other: String): Path = this.resolve(other)

// resolve
/**
 * Resolve the given path against this path.
 *
 * If any of the paths in the given [paths] parameter is an [absolute][Path.isAbsolute] path then this function
 * will trivially return *just* that path.  If any of the given `paths` is an *empty path* then that path will be
 * skipped over when joining them all together. Otherwise this function considers `this` path to be a directory and
 * resolves the given `paths` against `this` path. In the simplest case, the given `paths` does not have a
 * [root][Path.getRoot] component, in which case this function *joins* the given `paths` to `this` path and returns a
 * resulting path that [ends][Path.endsWith] with the given `paths`. Where the given `paths` has a root component then
 * resolution is highly implementation dependent and therefore unspecified.
 *
 * @receiver the [Path] to resolve against
 *
 * @param [paths] the paths to resolve against `this` path
 *
 * @return the resulting path
 */
fun Path.resolve(vararg paths: Path): Path = paths.fold(this) { parent, path -> parent.resolve(path) }

/**
 * Converts a given [path strings][paths] to a [path][Path] and resolves it against `this` path in exactly the manner
 * specified by the [resolve(Path)][Path.resolve] function.
 *
 * For example, suppose that the name separator is `"/"` and a path represents `"foo/bar"`, then invoking this function
 * with the path strings `{"gus", "dus", "fus"}` will result in the `Path` `"foo/bar/gus/dus/fus"`.
 *
 * @receiver the [path][Path] to resolve against
 *
 * @param [paths] the path strings to resolve against `this` path
 *
 * @return the resulting path
 *
 * @throws [InvalidPathException] if any of the paths in the given [paths] cannot be converted to a [path][Path]
 */
fun Path.resolve(vararg paths: String): Path = paths.fold(this) { parent, path -> parent.resolve(path) }

/**
 * Resolves the given [paths] against `this` path and returns the result.
 *
 * The way that the path is resolved is dependant on what type is contained within the given `paths`.
 *
 * **NOTE:** This function *only* resolves against [Path] and [String] types, if any other types are encountered within
 * the given `paths` then a [IllegalArgumentException] will be thrown. This behaviour is because Kotlin does not have
 * union types, which means that we need to accept [Any] and then manually filter out any wrong types at runtime rather
 * than at compile time.
 *
 * @receiver the [Path] to resolve against
 *
 * @param [paths] the paths to resolve against `this` *(should only contain [String] and/or [Path] types)*
 *
 * @return the resulting path
 *
 * @throws [IllegalArgumentException] if the given [paths] argument contains a type that is not a [String] or a [Path]
 */
fun Path.resolve(vararg paths: Any): Path = paths.fold(this) { parent, path ->
    when (path) {
        is Path -> parent.resolve(path)
        is String -> parent.resolve(path)
        else -> throw IllegalArgumentException("resolve(paths) only accepts Path or String types")
    }
}

// resolveSibling
/**
 * Resolves the given path against this path's [parent][Path.getParent] path.
 *
 * This is useful where a file name needs to be *replaced* with another file name. For example, suppose that the name
 * separator is `"/"` and a path represents `"dir1/dir2/foo"`, then invoking this function with the `Path`s
 * `{"bar", "gus"}` will result in the `Path` `"dir1/dir2/bar/gus"`. If `this` path does not have a parent path, or any
 * of the paths inside of [paths] is [absolute][Path.isAbsolute], then this functions returns that path. If any of the
 * paths inside of `paths` is an empty path then that path is skipped over.
 *
 * @param [paths] the paths to resolve against this path's parent
 *
 * @return the resulting path
 */
fun Path.resolveSiblings(vararg paths: Path): Path =
    paths.fold(this) { parent, path -> parent.resolveSibling(path) }

/**
 * Converts the given [path strings][paths] to a [path][Path] and resolves it against `this` path's
 * [parent][Path.getParent] path in exactly the manner specified by the [resolveSibling(Path)][Path.resolveSibling]
 * function.
 *
 * @param [paths] the path strings to resolve against `this` path's parent
 *
 * @return the resulting path
 *
 * @throws [InvalidPathException] if any of the paths in the given [paths] cannot be converted to a [path][Path]
 *
 * @see FileSystem.getPath
 */
fun Path.resolveSiblings(vararg paths: String): Path =
    paths.fold(this) { parent, path -> parent.resolveSibling(path) }

/**
 * The [name][Path.getFileName] of `this` file.
 *
 * Invoking the setter is equivalent to invoking [renameTo] with [COPY_ATTRIBUTES][StandardCopyOption.COPY_ATTRIBUTES]
 * set for the `options` parameter.
 *
 * @see [Path.getFileName]
 * @see [Path.renameTo]
 */
var Path.name: String
    get() = fileName.toString()
    set(name) {
        renameTo(name, StandardCopyOption.COPY_ATTRIBUTES)
    }

/**
 * The name of `this` file with its extension removed.
 *
 * The extension is assumed to be the contents after the *last* occurrence of the `.` character.
 *
 * If the path is a directory, [name] will be returned.
 *
 * @throws [NoSuchFileException] if `this` file does not exist
 */
var Path.simpleName: String
    get() = if (hasExtension) name.substringBeforeLast('.') else name
    set(value) {
        requireFileExistence(this) { "Can't set the extension of non-existent file; '$this'" }

        renameTo(extension?.let{ "$value.$it" } ?: value)
    }

/**
 * The extension of `this` path.
 *
 * The extension is assumed to be the contents after the *last* occurrence of the `.` character.
 *
 * If `this` path does not [have an extension][hasExtension] then the `get` will return `null`.
 *
 * Invoking the setter of `this` property with `null` as the `value` is equivalent to invoking [renameTo] with the
 * `name` parameter set to [simpleName] and the `options` parameter set to
 * [COPY_ATTRIBUTES][StandardCopyOption.COPY_ATTRIBUTES].
 *
 * @throws [NoSuchFileException] if `this` file does not exist
 * @throws [IOException] if an i/o exception occurs
 */
var Path.extension: String?
    get() = if (hasExtension) name.substringAfterLast('.') else null
    set(value) {
        requireFileExistence(this) { "Can't set the extension of non-existent file; '$this'" }

        renameTo(value?.let { "$simpleName.$it" } ?: simpleName, StandardCopyOption.COPY_ATTRIBUTES)
    }

/**
 * Returns `true` if this path has an extension, otherwise `false`.
 *
 * The presence of an extension is determined by whether if the [name] of this path contains the `.` character, but
 * only if the `.` character is *not* the first nor last character.
 *
 * Note that if this path [is a directory][isDirectory] then this will *always* return `false`.
 */
val Path.hasExtension: Boolean
    get() = when {
        isDirectory -> false
        else -> name.let { '.' in it && !it.startsWith('.') && it.lastIndexOf('.') != it.lastIndex }
    }

/**
 * Writes the given [string] to `this` [file][Path].
 *
 * Characters are encoded into bytes using the given [charset].
 *
 * All characters are written to the file *as is*, including any line separators. No extra characters are added.
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
 *
 * @param [options] Specifies how the file is created or opened.
 *
 * If this parameter is empty, then this function works as if the [CREATE][StandardOpenOption.CREATE],
 * [TRUNCATE_EXISTING][StandardOpenOption.TRUNCATE_EXISTING] and [WRITE][StandardOpenOption.WRITE] options had been
 * passed. In other words, it opens the file for writing, creating the file if it doesn't exist, or initially
 * truncating an existing [regular-file][Path.isRegularFile] to a size of `0`.
 *
 * @throws [IllegalArgumentException] if [options] contains an invalid combination of options
 * @throws [IOException] if an I/O error occurs writing to or creating the file, or the text cannot be encoded using
 * the specified charset
 * @throws [UnsupportedOperationException] if an unsupported option is specified
 * @throws [SecurityException] In the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] function is invoked to check write access to the file. The
 * [checkDelete(String)][SecurityManager.checkDelete] function is invoked to check delete access if the file is opened
 * with the [DELETE_ON_CLOSE][StandardOpenOption.DELETE_ON_CLOSE] option.
 */
fun Path.writeString(
    string: CharSequence,
    charset: Charset,
    vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)
): Path = this.writeBytes(string.toString().toByteArray(charset), *options)

/**
 * Writes the given [string] to `this` [file][Path].
 *
 * Characters are encoded into bytes using the given [charset].
 *
 * All characters are written to the file *as is*, including any line separators. No extra characters are added.
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
 *
 * @param [options] Specifies how the file is created or opened.
 *
 * If this parameter is empty, then this function works as if the [CREATE][StandardOpenOption.CREATE],
 * [TRUNCATE_EXISTING][StandardOpenOption.TRUNCATE_EXISTING] and [WRITE][StandardOpenOption.WRITE] options had been
 * passed. In other words, it opens the file for writing, creating the file if it doesn't exist, or initially
 * truncating an existing [regular-file][Path.isRegularFile] to a size of `0`.
 *
 * @throws [IllegalArgumentException] if [options] contains an invalid combination of options
 * @throws [IOException] if an I/O error occurs writing to or creating the file, or the text cannot be encoded using
 * the specified charset
 * @throws [UnsupportedOperationException] if an unsupported option is specified
 * @throws [SecurityException] In the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] function is invoked to check write access to the file. The
 * [checkDelete(String)][SecurityManager.checkDelete] function is invoked to check delete access if the file is opened
 * with the [DELETE_ON_CLOSE][StandardOpenOption.DELETE_ON_CLOSE] option.
 */
fun Path.writeString(
    string: String,
    vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)
): Path = this.writeString(string, StandardCharsets.UTF_8, *options)

/**
 * Reads all characters from `this` [file][Path] into a [string][String], decoding from bytes to characters using the
 * given [charset].
 *
 * This function ensures that the file is closed when all content have been read or an I/O error, or other runtime
 * exception, is thrown.
 *
 * This function reads all content including the line separators in the middle and/or at the end. The resulting string
 * will contain line separators as they appear in `this` file.
 *
 * **NOTE:** This function is intended for simple cases where it is appropriate and convenient to read the content of a
 * file into a `String`. It is not intended for reading very large files.
 *
 * @return a [String] containing the content read from `this` file
 *
 * @throws [IOException] if an I/O error occurs reading from `this` file or a malformed or unmappable byte sequence is
 * read
 * @throws [OutOfMemoryError] if `this` file is extremely large, for example larger than `2GB`
 * @throws [SecurityException] In the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] function is invoked to check read access to `this` file.
 *
 * @see readLinesToList
 */
@JvmOverloads
fun Path.readString(charset: Charset = StandardCharsets.UTF_8): String {
    requireFileExistence(this)
    return this.readBytes().toString(charset)
}

/**
 * Return a lazily populated [Sequence], the elements of which are the entries of `this` [directory][Path]
 *
 * The listing is not recursive.
 *
 * The elements of the sequence are [Path] objects that are obtained as if by [resolving(Path)][Path.resolve] the name
 * of the directory entry against `this` [Path]. Some file systems maintain special links to the directory itself and
 * the directory's parent directory. Entries representing these links are not included.
 *
 * The sequence is *weakly consistent*. It is thread safe but does not freeze the directory while iterating, so it may
 * *(or may not)* reflect updates to the directory that occur after returning from this function.
 *
 * The returned sequence encapsulates a [DirectoryStream].
 *
 * If an [IOException] is thrown when accessing the directory after this function has returned, it is wrapped in an
 * [UncheckedIOException] which will be thrown from the function that caused the access to take place.
 *
 * @return the [Sequence] describing the contents of `this` directory
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

            return Sequence { iterator }
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
    maxDepth: Int,
    vararg options: FileVisitOption,
    crossinline predicate: (Path) -> Boolean
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
inline fun Path.filter(crossinline predicate: (Path) -> Boolean): List<Path> =
    this.filter(Int.MAX_VALUE, predicate = predicate)

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
 * @throws IOException if an I/O error occurs opening the file
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the file
 *
 * @see Path.linesSequence
 * @see readLinesToList
 * @see newBufferedReader
 * @see java.io.BufferedReader.lines
 */
@Deprecated(
    "Ambiguous usage of a property",
    replaceWith = ReplaceWith("readLinesToList()", "moe.kanon.kommons.io.paths"),
    level = DeprecationLevel.ERROR
)
val Path.lines: List<String>
    get() = this.readLinesToList()

/**
 * Reads all the lines from this [file][Path] into a [Sequence].
 *
 * Unlike [readLines(Path, Charset)][readLinesToList], this method does not read all lines into a [List], but instead
 * populates lazily as the stream is consumed.
 *
 * Bytes from the file are decoded into characters using the specified charset and the same line terminators as
 * specified by [readLinesToList] are supported.
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
 * @see readLinesToList
 * @see newBufferedReader
 * @see java.io.BufferedReader.lines
 */
@Deprecated(
    "Name deviates from already known functions",
    replaceWith = ReplaceWith("readLines()", "moe.kanon.kommons.io.paths"),
    level = DeprecationLevel.ERROR
)
@JvmOverloads
fun Path.linesSequence(charset: Charset = StandardCharsets.UTF_8): Sequence<String> {
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
 * Reads all the lines from `this` [file][Path].
 *
 * This method ensures that the file is closed when all bytes have been read or an I/O error, or other runtime
 * exception, is thrown. Bytes from the file are decoded into characters using the specified [charset].
 *
 * This method recognizes the following as line terminators:
 *
 * - `\u000D` followed by `\u000A`.
 * - **`CARRIAGE RETURN`** followed by **`LINE FEED`**.
 * - `\u000A`, **`LINE FEED`**.
 * - `\u000D`, **`CARRIAGE RETURN`**.
 *
 * Additional Unicode line terminators may be recognized in future releases.
 *
 * Note that this method is intended for simple cases where it is convenient to read all lines in a single operation.
 * It is not intended for reading in large files.
 *
 * @param charset the charset to use for decoding
 *
 * ([UTF-8][StandardCharsets.UTF_8] by default)
 *
 * @return the lines from the file as a [List]
 *
 * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the file
 *
 * @see newBufferedReader
 */
@JvmOverloads
fun Path.readLinesToList(charset: Charset = StandardCharsets.UTF_8): List<String> =
    this.mapLines(charset) { it.toList() }

/**
 * Returns a lazily populated [CloseableSequence] of the lines of `this` file, the returned sequence may only be
 * iterated once.
 *
 * Note that the returned sequence **MUST** be closed after use, otherwise a stream to `this` file will be permantly
 * kept open.
 *
 * Bytes from the file are decoded into characters using the specified charset and the same line terminators as
 * specified by [readLinesToList] are supported.
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
 * @see readLinesToList
 * @see newBufferedReader
 * @see java.io.BufferedReader.lines
 */
@JvmOverloads
fun Path.readLines(charset: Charset = StandardCharsets.UTF_8): CloseableSequence<String> {
    val reader = this.newBufferedReader(charset)
    try {
        return CloseableLinesSequence(reader)
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
 * Returns the result of invoking [block] with the sequence returned from [readLines], and then closing the sequence.
 *
 * This function is a "wrapper" function instead of doing `file.readLines().use { ... }`, one can just do
 * `file.mapLines { ... }`.
 */
@JvmOverloads
inline fun <R> Path.mapLines(charset: Charset = StandardCharsets.UTF_8, block: (Sequence<String>) -> R): R =
    this.readLines(charset).use(block)

/**
 * Returns the contents of the line of `this` file at the given [index], or throws an [IndexOutOfBoundsException] if
 * `this` file does not have a line at `index`.
 */
@JvmOverloads
fun Path.readLine(index: Int, charset: Charset = StandardCharsets.UTF_8): String =
    this.mapLines(charset) { it.elementAt(index) }

/**
 * Returns the contents of the line of `this` file at the given [index], or `null` if `this` file does not have a line
 * at `index`.
 */
@JvmOverloads
fun Path.readLineOrNull(index: Int, charset: Charset = StandardCharsets.UTF_8): String? =
    this.mapLines(charset) { it.elementAtOrNull(index) }

// from the 'ReadWrite.kt' file in the kotlin std-lib, but implemented with the 'CloseableSequence' interface
private class CloseableLinesSequence(private val reader: BufferedReader) : CloseableSequence<String> {
    override fun close() {
        reader.close()
    }

    override fun iterator(): Iterator<String> {
        return object : Iterator<String> {
            private var nextValue: String? = null
            private var done = false

            override fun hasNext(): Boolean {
                if (nextValue == null && !done) {
                    nextValue = reader.readLine()
                    if (nextValue == null) done = true
                }
                return nextValue != null
            }

            override fun next(): String {
                if (!hasNext()) throw NoSuchElementException()
                val answer = nextValue
                nextValue = null
                return answer!!
            }
        }
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
infix fun Path.matchesGlobPattern(globPattern: String): Boolean = this.pathMatcherOf("glob", globPattern).matches(this)

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
@JvmOverloads
@Deprecated("Does not work", level = DeprecationLevel.ERROR)
fun Path.cleanDirectory(
    globPattern: String = "*",
    maxDepth: Int = Int.MAX_VALUE,
    deleteDirectories: Boolean = false,
    vararg options: FileVisitOption
) {
    requireDirectory(this) { "Can't clean a non-directory <${toString()}>" }

    this.walkFileTree(options.toSet(), maxDepth, object : SimplePathVisitor() {
        override fun visitFile(file: Path, attributes: BasicFileAttributes): FileVisitResult {
            // Only delete the file if it matches the specified globPattern.
            if (pathMatcherOf("glob", globPattern).matches(file)) file.deleteIfExists()

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
@JvmOverloads
inline fun Path.eachLine(charset: Charset = StandardCharsets.UTF_8, action: (String) -> Unit) {
    requireFileExistence(this)
    for (line in readLinesToList(charset)) action(line)
}

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
@JvmOverloads
fun Path.createDateDirectories(date: LocalDate = LocalDate.now()): Path {
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
@JvmOverloads
fun Path.createDateTimeDirectories(dateTime: LocalDateTime = LocalDateTime.now()): Path {
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
    requireFileExistence(this)
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
    requireFileExistence(this)
    return if (this.isDirectory) this.apply(action) else this
}

/**
 * Overwrites the bytes of `this` file with the bytes of the specified [source] file.
 *
 * If `this` path does not point to an already existing file, one will be created.
 *
 * @receiver the file to overwrite the bytes of
 *
 * @param [source] the file to use the bytes from for overwriting `this` file
 *
 * @throws [NoSuchFileException] if the specified [source] file does not exist on the file-system
 */
fun Path.overwriteBytesWith(source: Path): Path {
    requireFileExistence(source) { "Source does not exist! <$source>" }
    return this.writeBytes(source.readBytes(), WRITE, CREATE, TRUNCATE_EXISTING)
}

/**
 * Returns a new a [FileSystem] based on `this` file, with the specified [env] variables and the specified [classLoader].
 */
@JvmName("createFileSystemFrom")
@JvmOverloads
fun Path.createFileSystem(
    env: Map<String, String> = emptyMap(),
    classLoader: ClassLoader? = null
): FileSystem = FileSystems.newFileSystem(this.toUri(), env, classLoader)

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

/**
 * Creates an empty file at `this` path, or updates the [lastModifiedTime][Path.getLastModifiedTime] of the file to the
 * current computer time if a file already exists at `this` path.
 */
fun Path.touch(): Path = apply {
    if (this.exists) {
        this.lastModifiedTime = FileTime.fromMillis(System.currentTimeMillis())
    } else {
        this.createFile()
    }
}