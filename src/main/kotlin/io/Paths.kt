/*
 * Copyright 2019 Oliver Berg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package moe.kanon.kextensions.io

import moe.kanon.kextensions.collections.addAll
import moe.kanon.kextensions.collections.removeAll
import moe.kanon.kextensions.math.plus
import java.io.*
import java.math.BigInteger
import java.net.URI
import java.nio.channels.ByteChannel
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.*
import java.nio.file.attribute.*
import java.util.function.BiPredicate
import java.util.stream.Stream
import kotlin.streams.toList

/*
    A collection of top-level & extension functions & properties for the [Path] class introduced in Java 7.
    Kotlin provides extension functions for the [File] class by default, but none for the [Path] class, which is less
    than ideal, for by all means, the [File] should be considered to be `@Deprecated`, even thought it is not marked
    as such, but that is more to do with the fact that Oracle tends to dislike marking things as `@Deprecated`.
        (Only a handful of classes have ever been marked as `@Deprecated` in the entire life-span of Java.)

    [File] should optimally actually never be used in any new code that's using any JDK version above that of Java 6.
 */

// Transformers.
/**
 * Creates a [Path] from the given string [path].
 */
public fun pathOf(path: String) = Paths.get(path)!!

/**
 * A top level function for creating a [Path] instance from [Strings][String].
 *
 * It accepts a base [path], and any additional paths to be appended onto the base can be declared in the [more] vararg.
 */
public fun pathOf(path: String, vararg more: String) = Paths.get(path, *more)!!

/**
 * Creates a [Path] from [this] [String].
 */
public fun String.toPath() = Paths.get(this)!!

/**
 * Creates a [Path] from [this] [URI].
 */
public fun URI.toPath() = Paths.get(this)!!

// Operators
// - Credits for the original implementation of this goes to superbobry.
// - Div "hack".
// -- Path
// TODO: Documentation for these operators.
public operator fun Path.div(other: String): Path = div(+other)

public operator fun Path.div(other: Path): Path = resolve(other)!!

// -- String
public operator fun String.div(other: String): Path = div(+other)

public operator fun String.div(other: Path): Path = +this / other

// Plus. (+)
// Might change these back to '!', not sure.
/**
 * Converts [this] [String] into a [Path].
 */
public operator fun String.unaryPlus(): Path = toPath()

/**
 * Converts [this] [File] into a [Path].
 */
public operator fun File.unaryPlus(): Path = toPath()

// TODO: Move from here
public val Path.fileStore get() = Files.getFileStore(this)!!

public val Path.isHidden get() = Files.isHidden(this)

public val Path.contentType get() = Files.probeContentType(this)!!

// Functions
// Misc
public fun Path.isSameFile(other: Path) = Files.isSameFile(this, other)

public infix fun Path.sameFile(other: Path) = isSameFile(other)

// TODO: to here to appropriate positions.

// Streams
/**
 * Opens a file, returning an input stream to read from the file. The stream will not be buffered, and is not required
 * to support the [mark][InputStream.mark] or [reset][InputStream.reset] methods. The stream will be safe for access by multiple
 * concurrent threads. Reading commences at the beginning of the file. Whether the returned stream is
 * *asynchronously closeable* and/or *interruptible* is highly file system provider specific and therefore
 * not specified.
 *
 * The [options] parameter determines how the file is opened.
 * If no options are present then it is equivalent to opening the file with the [READ][StandardOpenOption.READ] option.
 * In addition to the `READ` option, an implementation may also support additional implementation specific options.
 *
 * @throws  IllegalArgumentException If an invalid combination of options is specified.
 * @throws  UnsupportedOperationException If an unsupported option is specified.
 * @throws  IOException If an I/O error occurs.
 * @throws  SecurityException In the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the file.
 */
public fun Path.newInputStream(vararg options: OpenOption): InputStream = Files.newInputStream(this, *options)!!

/**
 * Opens or creates a file, returning an output stream that may be used to write bytes to the file.
 * The resulting stream will not be buffered. The stream will be safe for access by multiple concurrent threads.
 * Whether the returned stream is *asynchronously closeable* and/or *interruptible* is highly file system provider
 * specific and therefore not specified.
 *
 * This method opens or creates a file in exactly the manner specified by the [newByteChannel] method with the
 * exception that the [READ][StandardOpenOption.READ] option may not be present in the array of options.
 * If no options are present then this method works as if the [CREATE][StandardOpenOption.CREATE],
 * [TRUNCATE_EXISTING][StandardOpenOption.TRUNCATE_EXISTING], and [WRITE][StandardOpenOption.WRITE] options are present.
 *
 * In other words, it opens the file for writing, creating the file if it doesn't exist, or initially truncating an
 * existing [regular-file][isRegularFile] to a size of `0` if it exists.
 *
 * **Usage Examples:**
 * ```Kotlin
 *      val path = ...
 *
 *      // Truncate and overwrite an existing file, or create the file if it doesn't initially exist.
 *      var out = path.newOutputStream()
 *
 *      // Append to an existing file, fail if the file does not exist.
 *      out = path.newOutputStream(StandardOpenOption.APPEND)
 *
 *      // Append to an existing file, create file if it doesn't initially exist.
 *     out = path.newOutputStream(StandardOpenOption.CREATE, StandardOpenOption.APPEND);
 *
 *     // always create new file, failing if it already exists.
 *     out = path.newOutputStream(StandardOpenOption.CREATE_NEW);
 * ```
 *
 * @throws  IllegalArgumentException If an invalid combination of options is specified.
 * @throws  UnsupportedOperationException If an unsupported option is specified.
 * @throws  IOException If an I/O error occurs.
 * @throws  SecurityException In the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the file. The
 * [checkDelete(String)][SecurityManager.checkDelete] method is invoked to check delete access if the file is
 * opened with the `DELETE_ON_CLOSE` option.
 */
public fun Path.newOutputStream(vararg options: OpenOption): OutputStream = Files.newOutputStream(this, *options)!!

/**
 * Opens or creates a file, returning a seekable byte channel to access the file.
 *
 * The [options] parameter determines how the file is opened. The [READ][StandardOpenOption.READ] and
 * [WRITE][StandardOpenOption.WRITE] options determine if the file should be opened for reading and/or writing.
 * If neither option (or the [APPEND][StandardOpenOption.APPEND] option) is present then the file is opened for
 * reading. By default reading or writing commence at the beginning of the file.
 *
 * An implementation may also support additional implementation specific options.
 *
 * The [attributes] parameter is optional [file-attributes][FileAttribute] to set atomically when a new file is created.
 *
 * In the case of the default provider, the returned seekable byte channel is a [FileChannel][java.nio.channels.FileChannel].
 *
 * **Usage Examples:**
 * ```Kotlin
 *  val path = ...
 *
 *  // Open file for reading.
 *  val rbc = path.newByteChannel(EnumSet.of(READ)));
 *
 *  // Open file for writing to the end of an existing file, creating the file if it doesn't already exist.
 *  val wbc = path.newByteChannel(EnumSet.of(CREATE, APPEND));
 *
 *  // Create file with initial permissions, opening it for both reading and writing.
 *  val attributes = arrayOf(...)
 *  val sbc = path.newByteChannel(
 *          setOf(StandardOpenOption.CREATE_NEW, StandardOpenOption.READ, StandardOpenOption.WRITE),
 *          *attributes
 *  )
 * ```
 *
 * @throws  IllegalArgumentException If the [options] set contains an invalid combination of options.
 * @throws  UnsupportedOperationException If an unsupported open option is specified or the array contains attributes
 * that cannot be set atomically when creating the file.
 * @throws  FileAlreadyExistsException If a file of that name already exists and the
 * [CREATE_NEW][StandardOpenOption.CREATE_NEW] option is specified *(optional specific exception)*.
 * @throws  IOException If an I/O error occurs.
 * @throws  SecurityException In the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the path if the file
 * is opened for reading. The [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write
 * access to the path if the file is opened for writing. The [checkDelete(String)][SecurityManager.checkDelete]
 * method is invoked to check delete access if the file is opened with the `DELETE_ON_CLOSE` option.
 *
 * @see java.nio.channels.FileChannel.open
 */
public fun Path.newByteChannel(options: Set<OpenOption>, vararg attributes: FileAttribute<*>): ByteChannel =
        Files.newByteChannel(this, options.toMutableSet(), *attributes)!!

/**
 * Opens or creates a file, returning a seekable byte channel to access the file.
 *
 * This method opens or creates a file in exactly the manner specified
 * by the [newByteChannel(options, vararg attributes)][Path.newByteChannel] method.
 *
 * @throws  IllegalArgumentException If the set contains an invalid combination of options
 * @throws  UnsupportedOperationException If an unsupported open option is specified
 * @throws  FileAlreadyExistsException If a file of that name already exists and the
 * [CREATE_NEW][StandardOpenOption.CREATE_NEW] option is specified *(optional specific exception)*.
 * @throws  IOException If an I/O error occurs.
 * @throws  SecurityException In the case of the default provider, and a security manager is installed,
 * the [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the path if the file is
 * opened for reading. The [checkWrite(String)][SecurityManager.checkWrite]  method is invoked to check write
 * access to the path if the file is opened for writing. The [checkDelete(String)][SecurityManager.checkDelete]
 * method is invoked to check delete access if the file is opened with the `DELETE_ON_CLOSE` option.
 *
 * @see java.nio.channels.FileChannel.open
 */
public fun Path.newByteChannel(vararg options: OpenOption): ByteChannel = Files.newByteChannel(this, *options)!!

public typealias PathDirectoryStream = DirectoryStream<Path>

public typealias PathDirectoryFilter = DirectoryStream.Filter<Path>

// This is a port of the one that exists in Files.java.
internal class AcceptAllFilter private constructor() : PathDirectoryFilter {
    
    override fun accept(entry: Path) = true
    
    companion object {
        internal val FILTER = AcceptAllFilter()
    }
}

/**
 * Opens a directory, returning a [DirectoryStream] to iterate over
 * the entries in the directory. The elements returned by the directory
 * stream's [iterator][DirectoryStream] are of type
 * [Path], each one representing an entry in the directory. The [Path]
 * objects are obtained as if by [resolving][Path.resolve] the
 * name of the directory entry against [this]. The entries returned by
 * the iterator are filtered by the given [filter][DirectoryStream.Filter].
 * **By default, the [dirFilter] is set to [AcceptAllFilter].**
 *
 * When not using the try-with-resources construct, then directory
 * stream's [close][DirectoryStream.close] method should be invoked after iteration is
 * completed so as to free any resources held for the open directory.
 *
 * Where the filter terminates due to an uncaught error or runtime
 * exception then it is propagated to the [hasNext][Iterator.hasNext] or [next][Iterator.next] method. Where an
 * [IOException] is thrown, it results in the `hasNext` or
 * `next` method throwing a [DirectoryIteratorException] with the `IOException` as the cause.
 *
 * When an implementation supports operations on entries in the
 * directory that execute in a race-free manner then the returned directory
 * stream is a [SecureDirectoryStream].
 *
 * **Usage Example:**
 *
 * Suppose we want to iterate over the files in a directory that are larger than 8k.
 *
 * ```Kotlin
 *      val filter = PathDirectoryFilter { it.size > 8192L }
 *      // The explicit cast is not needed, just there to signify that 'dir' needs to be a Path instance.
 *      val dir: Path = ...
 *      dir.newDirectoryStream(filter).use { ... }
 * ```
 *
 * @return  A new and open [DirectoryStream] instance.
 *
 * @throws  NotDirectoryException If the file could not otherwise be opened because it is not
 *          a directory *(optional specific exception)*.
 * @throws  IOException If an I/O error occurs.
 * @throws  SecurityException In the case of the default provider, and a security manager is
 * installed, the [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the directory.
 */
public fun Path.newDirectoryStream(dirFilter: PathDirectoryFilter = AcceptAllFilter.FILTER): PathDirectoryStream =
        Files.newDirectoryStream(this, dirFilter)!!

/**
 * Opens a directory, returning a [DirectoryStream] to iterate over
 * the entries in the directory. The elements returned by the directory
 * stream's [iterator][DirectoryStream] are of type
 * [Path], each one representing an entry in the directory. The [Path]
 * objects are obtained as if by [resolving][Path.resolve] the
 * name of the directory entry against [this]. The entries returned by
 * the iterator are filtered by matching the [String] representation
 * of their file names against the given [globbing][glob] pattern.
 *
 * For example, suppose we want to iterate over the files ending with ".java" in a directory:
 *
 * ```Kotlin
 *      // The explicit cast is not needed, just there to signify that 'dir' needs to be a Path instance.
 *      val dir: Path = ...
 *      dir.newDirectoryStream("*.java").use { ... }
 * ```
 *
 * The globbing pattern is specified by the [getPathMatcher][FileSystem.getPathMatcher] function.
 *
 * When not using the try-with-resources construct, then directory
 * stream's [close][DirectoryStream] method should be invoked after iteration is
 * completed so as to free any resources held for the open directory.
 *
 * When an implementation supports operations on entries in the
 * directory that execute in a race-free manner then the returned directory
 * stream is a [SecureDirectoryStream].
 *
 * @return  A new and open [DirectoryStream] instance.
 *
 * @throws  java.util.regex.PatternSyntaxException If the pattern is invalid
 * @throws  NotDirectoryException If the file could not otherwise be opened because it is not
 * a directory *(optional specific exception)*.
 * @throws  IOException If an I/O error occurs
 * @throws  SecurityException In the case of the default provider, and a security manager is
 * installed, the [checkRead(String)][SecurityManager.checkRead]
 * function is invoked to check read access to the directory.
 */
public fun Path.newDirectoryStream(glob: String): PathDirectoryStream = Files.newDirectoryStream(this, glob)!!

// File System Changes
// Creation Functions
// - Files
/**
 * Creates a new and empty file, failing if the file already exists. The
 * check for the existence of the file and the creation of the new file if
 * it does not exist are a single operation that is atomic with respect to
 * all other filesystem activities that might affect the directory.
 *
 * The [attributes] parameter is optional
 * [file-attributes][FileAttribute] to set atomically when creating the file. Each attribute
 * is identified by its [name][FileAttribute.name]. If more than one
 * attribute of the same name is included in the array then all but the last
 * occurrence is ignored.
 *
 * @return  The newly created file.
 *
 * @throws  UnsupportedOperationException If the array contains an attribute that cannot be set atomically
 * when creating the file.
 * @throws  FileAlreadyExistsException If a file of that name already exists
 * *(optional specific exception)*.
 * @throws  IOException If an I/O error occurs or the parent directory does not exist.
 * @throws  SecurityException In the case of the default provider, and a security manager is
 * installed, the [checkWrite(String)][SecurityManager.checkWrite]
 * method is invoked to check write access to the new file.
 */
public fun Path.createFile(vararg attributes: FileAttribute<*>) = Files.createFile(this, *attributes)!!

/**
 * Creates a new directory. The check for the existence of the file and the
 * creation of the directory if it does not exist are a single operation
 * that is atomic with respect to all other filesystem activities that might
 * affect the directory. The [createDirectories]
 * method should be used where it is required to create all nonexistent
 * parent directories first.
 *
 * The [attributes] parameter is optional
 * [file-attributes][FileAttribute] to set atomically when creating the directory. Each
 * attribute is identified by its [name][FileAttribute.name]. If more
 * than one attribute of the same name is included in the array then all but
 * the last occurrence is ignored.
 *
 * @return  The newly created directory.
 *
 * @throws  UnsupportedOperationException If the array contains an attribute that cannot be set atomically
 * when creating the directory.
 * @throws  FileAlreadyExistsException If a directory could not otherwise be created because a file of
 * that name already exists *(optional specific exception)*.
 * @throws  IOException If an I/O error occurs or the parent directory does not exist.
 * @throws  SecurityException In the case of the default provider, and a security manager is
 * installed, the [checkWrite(String)][SecurityManager.checkWrite]
 * method is invoked to check write access to the new directory.
 */
public fun Path.createDirectory(vararg attributes: FileAttribute<*>) =
        Files.createDirectory(this, *attributes)!!

/**
 * Creates a directory by creating all nonexistent parent directories first.
 * Unlike the [createDirectory][Path.createDirectory] method, an exception
 * is not thrown if the directory could not be created because it already
 * exists.
 *
 * The [attributes] parameter is optional
 * [file-attributes][FileAttribute] to set atomically when creating the nonexistent
 * directories. Each file attribute is identified by its
 * [name][FileAttribute.name]. If more than one attribute of the same name is
 * included in the array then all but the last occurrence is ignored.
 *
 * If this method fails, then it may do so after creating some, but not
 * all, of the parent directories.
 *
 * @return  The newly created directory.
 *
 * @throws  UnsupportedOperationException If the array contains an attribute that cannot be set atomically
 *  when creating the directory.
 * @throws  FileAlreadyExistsException If [this] exists but is not a directory *(optional specific exception)*
 * @throws  IOException If an I/O error occurs.
 * @throws  SecurityException In the case of the default provider, and a security manager is
 * installed, the [checkWrite(String)][SecurityManager.checkWrite]
 * method is invoked prior to attempting to create a directory and
 * its [checkRead(String)][SecurityManager.checkRead] is
 * invoked for each parent directory that is checked. If
 * [this] is not an absolute path then its
 * [toAbsolutePath][Path.toAbsolutePath] may need to be invoked to get its absolute path.
 * This may invoke the security manager's
 * [checkPropertyAccess(String)][SecurityManager.checkPropertyAccess]
 * method to check access to the system property `user.dir`
 */
public fun Path.createDirectories(vararg attributes: FileAttribute<*>) =
        Files.createDirectories(this, *attributes)!!

/**
 * Creates a new empty file in [this] directory, using the given
 * [name] and [extension] strings to generate its name. The resulting
 * [Path] is associated with the same [FileSystem] as the given
 * directory.
 *
 * The details as to how the name of the file is constructed is
 * implementation dependent and therefore not specified. Where possible
 * the [name] and [extension] are used to construct candidate
 * names in the same manner as the
 * [createTempFile(String, String, File)][java.io.File.createTempFile] method.
 *
 * As with the [File.createTempFile] methods, this method is only
 * part of a temporary-file facility. Where used as a *work files*,
 * the resulting file may be opened using the
 * [DELETE_ON_CLOSE][StandardOpenOption.DELETE_ON_CLOSE] option so that the
 * file is deleted when the appropriate `close` method is invoked.
 * Alternatively, a [shutdown-hook][Runtime.addShutdownHook], or the
 * [deleteOnExit][java.io.File.deleteOnExit] mechanism may be used to delete the
 * file automatically.
 *
 * The [attributes] parameter is optional
 * [file-attributes][FileAttribute] to set atomically when creating the file. Each attribute
 * is identified by its [name][FileAttribute.name]. If more than one
 * attribute of the same name is included in the array then all but the last
 * occurrence is ignored. When no file attributes are specified, then the
 * resulting file may have more restrictive access permissions to files
 * created by the [createTempFile(String, String, File)][java.io.File.createTempFile]
 * method.
 *
 * @return  The path to the newly created file that did not exist before this method was invoked.
 *
 * @throws  IllegalArgumentException
 *          if the prefix or suffix parameters cannot be used to generate
 *          a candidate file name
 * @throws  UnsupportedOperationException
 *          if the array contains an attribute that cannot be set atomically
 *          when creating the directory
 * @throws  IOException
 *          if an I/O error occurs or [this] does not exist
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkWrite(String)][SecurityManager.checkWrite]
 *          method is invoked to check write access to the file.
 *
 * @see Files.createTempFile
 */
public fun Path.createTempFile(name: String, extension: String, vararg attributes: FileAttribute<*>) =
        Files.createTempFile(this, name, extension, *attributes)!!

/**
 * Creates a new directory in the specified directory, using the given
 * prefix to generate its name.  The resulting [Path] is associated
 * with the same [FileSystem] as the given directory.
 *
 * The details as to how the name of the directory is constructed is
 * implementation dependent and therefore not specified. Where possible
 * the `prefix` is used to construct candidate names.
 *
 * As with the [createTempFile][Path.createTempFile] methods, this method is only
 * part of a temporary-file facility. A
 * [shutdown-hook][Runtime.addShutdownHook], or the [deleteOnExit][java.io.File.deleteOnExit] mechanism may be
 * used to delete the directory automatically.
 *
 * The [attributes] parameter is optional
 * [file-attributes][FileAttribute] to set atomically when creating the directory. Each
 * attribute is identified by its [name][FileAttribute.name]. If more
 * than one attribute of the same name is included in the array then all but
 * the last occurrence is ignored.
 *
 * @return  The path to the newly created directory that did not exist before this method was invoked.
 *
 * @throws  IllegalArgumentException
 *          if the prefix cannot be used to generate a candidate directory name
 * @throws  UnsupportedOperationException
 *          if the array contains an attribute that cannot be set atomically
 *          when creating the directory
 * @throws  IOException
 *          if an I/O error occurs or `dir` does not exist
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkWrite(String)][SecurityManager.checkWrite]
 *          method is invoked to check write access when creating the
 *          directory.
 *
 * @see Files.createTempDirectory
 */
public fun Path.createTempDirectory(name: String, vararg attributes: FileAttribute<*>) =
        Files.createTempDirectory(this, name, *attributes)!!

// - Links
public fun Path.createSymbolicLink(target: Path, vararg attributes: FileAttribute<*>) =
        Files.createSymbolicLink(this, target, *attributes)!!

// -- Property?
public fun Path.readSymbolicLink(): Path = Files.readSymbolicLink(this)!!

public fun Path.createLink(target: Path): Path = Files.createLink(this, target)!!

// Deletion Functions
public fun Path.delete() = Files.delete(this)

public fun Path.deleteIfExists(): Boolean = Files.deleteIfExists(this)

// Location Changes
public fun Path.move(target: Path, vararg options: CopyOption): Path = Files.move(this, target, *options)!!

/**
 * Renames [this] path to the given [name].
 *
 * @throws IOException If [this] does not exist on the file system.
 * @see Path.name
 */
public fun Path.rename(name: String, vararg options: CopyOption): Path = move(this.parent / name, *options)

/**
 * The file name of the path in [String] format.
 *
 * The setter for this property uses the [rename] function with the [StandardCopyOption.COPY_ATTRIBUTES] option selected.
 *
 * If you want more control over the renaming process, use [rename].
 *
 * @see Path.getFileName
 * @see Path.simpleName
 * @see Path.extension
 * @see Path.rename
 */
public var Path.name: String
    get() = this.fileName.toString()
    set(name) {
        rename(name, StandardCopyOption.COPY_ATTRIBUTES)
    }

/**
 * The name of the path with the extension trimmed from it in [String] format.
 *
 * If the path is a directory, the full name will be returned.
 *
 * @throws IOException If the [simpleName] inside of the setter contains a '.' character.
 * @see Path.name
 * @see Path.extension
 */
@Suppress("LiftReturnOrAssignment")
public var Path.simpleName: String
    get() = when {
        this.isDirectory -> name
        else -> name.substringBeforeLast('.')
    }
    set(simpleName) = when {
        this.name.substringBeforeLast('.') == name || isDirectory -> name = simpleName
        simpleName.contains('.') -> throw IOException("\"Path.simpleName\" does not support the usage of '.' inside of the setter ($simpleName), use \"Path.name\" or \"Path.extension\" for such actions.")
        else -> this.name = "$simpleName.${this.extension}"
    }

// This is quite a different implementation of how to get the extension of a file compared to the one provided by
// JetBrains for the File class. Reason for this, is that I really hate the idea of "failing silently", if something
// goes wrong, errors should be thrown, not just thrown to the void and leave it to the programmer to essentially guess
// what actually went wrong.
/**
 * The extension of the path in [String] format.
 *
 * @throws IOException When accessing the getter this will be thrown if the path has no extension or it's a directory. When accessing the setter, this will be thrown if the path is that of a directory.
 * @see Path.name
 * @see Path.simpleName
 */
public var Path.extension: String
    get() = when {
        this.name.substringBeforeLast('.') != name -> name.substringBeforeLast('.')
        this.isDirectory -> throw IOException("Can't get the extension of a directory. (${toString()})")
        else -> throw IOException("\"${toString()}\" does not have an extension.")
    }
    set(extension) {
        if (isDirectory) throw IOException("Can't change the extension of a directory. (${toString()})")
        
        this.name = "$this.simpleName.$extension"
    }

public fun Path.copy(target: Path, vararg options: CopyOption): Path = Files.copy(this, target, *options)!!

// File Attributes
/**
 * @see Path.attributes
 */
public fun <V : FileAttributeView> Path.getFileAttributeView(type: Class<V>, vararg options: LinkOption): V =
        Files.getFileAttributeView(this, type, *options)!!

/**
 * @see Path.attributes
 */
public fun <A : BasicFileAttributes> Path.readAttributes(type: Class<A>, vararg options: LinkOption): A =
        Files.readAttributes(this, type, *options)!!

/**
 * @see Path.attributes
 */
public fun Path.readAttributes(attributes: String, vararg options: LinkOption): Map<String, Any> =
        Files.readAttributes(this, attributes, *options)

/**
 * @see Path.attributes
 */
public fun Path.setAttribute(attribute: String, value: Any?, vararg options: LinkOption): Path =
        Files.setAttribute(this, attribute, value, *options)!!

public fun Path.getAttribute(attribute: String, vararg options: LinkOption): Any =
        Files.getAttribute(this, attribute, *options)!!

public class AttributeMap internal constructor(
        private val path: Path,
        private val original: Map<String, Any>
) : MutableMap<String, Any> by original.toMutableMap() {
    
    override fun put(key: String, value: Any): Any? = path.setAttribute(key, value)
    
    // TODO: Figure out how to actually remove attributes.
    override fun remove(key: String): Any? {
        throw NotImplementedError("AttributeMap does not currently support removal operations.")
    }
    
    override fun get(key: String): Any? = path.getAttribute(key)
    
    override fun putAll(from: Map<out String, Any>) {
        for (attribute in from) {
            this += attribute.toPair()
        }
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
public val Path.attributes: AttributeMap get() = AttributeMap(this, readAttributes("*"))

// Permissions
public fun Path.getPosixFilePermissions(vararg options: LinkOption): Set<PosixFilePermission> =
        Files.getPosixFilePermissions(this, *options)!!.toSet()

public fun Path.setPosixFilePermissions(vararg permissions: PosixFilePermission) {
    this.permissions = permissions.toSet()
}

public var Path.permissions: Set<PosixFilePermission>
    get() = this.getPosixFilePermissions()
    public set(perms) {
        Files.setPosixFilePermissions(this, perms)!!
    }

// Owner
public fun Path.getOwner(vararg options: LinkOption): UserPrincipal = Files.getOwner(this, *options)!!

public var Path.owner: UserPrincipal
    get() = getOwner()
    set(newOwner) {
        Files.setOwner(this, newOwner)
    }

// TODO: Group together with the other link functions & properties
public val Path.isSymbolicLink: Boolean get() = Files.isSymbolicLink(this)

// TODO: Group together with the other directories
public fun Path.isDirectory(vararg options: LinkOption): Boolean = Files.isDirectory(this, *options)

public val Path.isDirectory: Boolean get() = this.isDirectory()

// Regular File
public fun Path.isRegularFile(vararg options: LinkOption): Boolean = Files.isRegularFile(this, *options)

public val Path.isRegularFile: Boolean get() = this.isRegularFile()

// Last Modified Time
public fun Path.getLastModifiedTime(vararg options: LinkOption): FileTime =
        Files.getLastModifiedTime(this, *options)!!

public var Path.lastModifiedTime: FileTime
    get() = this.getLastModifiedTime()
    set(newTime) {
        Files.setLastModifiedTime(this, newTime)!!
    }

// Size
public val Path.size: Long get() = Files.size(this)

// Exists
public fun Path.exists(vararg options: LinkOption): Boolean = Files.exists(this, *options)

public val Path.exists get() = this.exists()

// Not Exists
public fun Path.notExists(vararg options: LinkOption): Boolean = Files.notExists(this, *options)

public val Path.notExists: Boolean get() = this.notExists()

// Accessibility
public val Path.isReadable: Boolean get() = Files.isReadable(this)

public var Path.isWritable: Boolean
    get() = Files.isWritable(this)
    public set(writable) {
        if (writable) {
            val perms = permissions.toMutableSet()
            
            permissions = perms.removeAll(
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.GROUP_WRITE,
                    PosixFilePermission.OTHERS_WRITE
            )
        } else {
            val perms = permissions.toMutableSet()
            
            permissions = perms.addAll(
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.GROUP_WRITE,
                    PosixFilePermission.OTHERS_WRITE
            )
        }
    }

public val Path.isExecutable: Boolean get() = Files.isExecutable(this)

// File Tree Visitors
public typealias PathVisitor = FileVisitor<in Path>

public fun Path.walkFileTree(options: Set<FileVisitOption>, maxDepth: Int, visitor: PathVisitor): Path =
        Files.walkFileTree(this, options, maxDepth, visitor)!!

public fun Path.walkFileTree(visitor: PathVisitor): Path = Files.walkFileTree(this, visitor)!!

// Buffered Things
public fun Path.newBufferedReader(charset: Charset = StandardCharsets.UTF_8): BufferedReader =
        Files.newBufferedReader(this, charset)!!

public fun Path.newBufferedWriter(
        charset: Charset = StandardCharsets.UTF_8,
        vararg options: OpenOption
): BufferedWriter = Files.newBufferedWriter(this, charset, *options)!!

// Copy
public fun Path.copy(outputStream: OutputStream): Long = Files.copy(this, outputStream)

// Reading Functions
public fun Path.readAllBytes(): ByteArray = Files.readAllBytes(this)!!

public fun Path.readAllLines(charset: Charset = StandardCharsets.UTF_8): List<String> =
        Files.readAllLines(this, charset)!!.toList()

// Writing Functions
public fun Path.writeBytes(bytes: ByteArray, vararg options: OpenOption): Path = Files.write(this, bytes, *options)

public fun Path.writeLines(
        lines: Iterable<CharSequence>,
        charset: Charset = StandardCharsets.UTF_8,
        vararg options: OpenOption
): Path = Files.write(this, lines, charset, *options)!!

/**
 * Appends the given [line] to [this] File using the given [charset] with the given [options].
 */
public fun Path.writeLine(line: String, charset: Charset = StandardCharsets.UTF_8, vararg options: OpenOption): Path {
    val encoder = charset.newEncoder()
    val out = newOutputStream(*options)
    BufferedWriter(OutputStreamWriter(out, encoder)).use { writer ->
        writer.append(line)
        writer.newLine()
    }
    return this
}

// Streams
public typealias PathStream = Stream<Path>

public typealias PathMatcher = BiPredicate<Path, BasicFileAttributes>

// I really don't like the original name choice for this method. "list" doesn't really entail what it does, and it's
// quite frankly just wrong, because the return type is NOT a list, but a Stream.
//  While the action that the method *does* is "listing all entries", that's a really vague reason to name a method such
// a bad name. And thus, I changed the name.
public val Path.entries: PathStream
    get() {
        this.checkIfDirectory()
        return Files.list(this)!!
    }

public val Path.children: List<Path> get() = this.entries.toList()

public fun Path.walk(maxDepth: Int = Int.MAX_VALUE, vararg options: FileVisitOption): PathStream =
        Files.walk(this, maxDepth, *options)!!

public fun Path.find(maxDepth: Int = Int.MAX_VALUE, matcher: PathMatcher, vararg options: FileVisitOption) =
        Files.find(this, maxDepth, matcher, *options)!!

public val Path.lines: Stream<String> get() = Files.lines(this)

public fun Path.lines(charset: Charset): Stream<String> = Files.lines(this, charset)!!

// Fully custom operations
/**
 * Returns whether or not [path] is a child of [this].
 *
 * @throws IOException If [this] isn't a directory.
 */
public operator fun Path.contains(path: Path): Boolean = this.children.contains(path)

/**
 * Attempts to recursively delete all the files inside of [this].
 *
 * @throws IOException If [this] doesn't exist, and if [this] isn't a directory.
 */
public fun Path.cleanDirectory(deleteDirectories: Boolean = false) {
    this.checkIfDirectory("Can't clean a non-directory. (${toString()})")
    
    for (child in this) {
        if (child.isDirectory) {
            child.cleanDirectory()
            
            if (deleteDirectories) continue
        }
        
        child.delete()
    }
}

/**
 * Attempts to read the contents of [this] file into a string.
 *
 * @throws IOException If [this] doesn't exist on the file system.
 */
public fun Path.pathToString(charset: Charset = StandardCharsets.UTF_8): String {
    this.checkIfExists()
    return this.readAllLines(charset)
        .joinToString(System.lineSeparator())
}

/**
 * Returns the size of this directory as a [BigInteger].
 *
 * @throws IOException If this path doesn't exist, or if it's not a directory.
 */
public val Path.directorySize: BigInteger
    get() {
        this.checkIfDirectory()
        
        var size = BigInteger.ZERO
        
        for (child in this) {
            if (child.isSymbolicLink) continue
            
            size += child.size
        }
        
        return size
    }


// Check Functions
/**
 * Checks if [this] path actually exists, if not, it throws an [IOException] with the [message].
 */
public fun Path.checkIfExists(message: String = "File \"${toString()}\" doesn't exist!") {
    if (notExists) throw IOException(message)
}

/**
 * Checks if [this] path is a directory, if not, it throws an [IOException] with the [message].
 */
public fun Path.checkIfDirectory(message: String = "\"$name\" needs to be a directory!") {
    this.checkIfExists()
    if (!isDirectory) throw IOException(message)
}