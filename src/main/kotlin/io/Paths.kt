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
/**
 * Creates a symbolic link to a target *(optional operation)*.
 *
 * The [target] parameter is the target of the link. It may be an
 * [absolute][Path.isAbsolute] or relative path and may not exist. When
 * the target is a relative path then file system operations on the resulting
 * link are relative to the path of the link.
 *
 * The [attributes] parameter is optional
 * [attributes][FileAttribute] to set atomically when creating the link. Each attribute is
 * identified by its [name][FileAttribute.name]. If more than one attribute
 * of the same name is included in the array then all but the last occurrence
 * is ignored.
 *
 * Where symbolic links are supported, but the underlying [FileStore]
 * does not support symbolic links, then this may fail with an
 * [IOException]. Additionally, some operating systems may require that the
 * Java virtual machine be started with implementation specific privileges to
 * create symbolic links, in which case this method may throw [IOException].
 *
 * @return  The path to the symbolic link.
 *
 * @throws  UnsupportedOperationException
 *          if the implementation does not support symbolic links or the
 *          array contains an attribute that cannot be set atomically when
 *          creating the symbolic link
 * @throws  FileAlreadyExistsException
 *          if a file with the name already exists *(optional specific
 *          exception)*
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager
 *          is installed, it denies [LinkPermission]`("symbolic")`
 *          or its [checkWrite(String)][SecurityManager.checkWrite]
 *          method denies write access to the path of the symbolic link.
 */
public fun Path.createSymbolicLink(target: Path, vararg attributes: FileAttribute<*>) =
    Files.createSymbolicLink(this, target, *attributes)!!

/**
 * Reads the target of a symbolic link *(optional operation)*.
 *
 * If the file system supports `symbolic
 * links` then this method is used to read the target of the link, failing
 * if the file is not a symbolic link. The target of the link need not exist.
 * The returned [Path] object will be associated with the same file
 * system as [this].
 *
 * @return  A [Path] object representing the target of the link.
 *
 * @throws  UnsupportedOperationException
 *          if the implementation does not support symbolic links
 * @throws  NotLinkException
 *          if the target could otherwise not be read because the file
 *          is not a symbolic link *(optional specific exception)*
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager
 *          is installed, it checks that `FilePermission` has been
 *          granted with the "`readlink`" action to read the link.
 */
public fun Path.readSymbolicLink(): Path = Files.readSymbolicLink(this)!!

/**
 * Creates a new link (directory entry) for an existing file *(optional
 * operation)*.
 *
 * The [this] locates the directory entry to create.
 * The [existing] parameter is the path to an existing file. This
 * method creates a new directory entry for the file so that it can be
 * accessed using [this] as the path. On some file systems this is
 * known as creating a "hard link". Whether the file attributes are
 * maintained for the file or for each directory entry is file system
 * specific and therefore not specified. Typically, a file system requires
 * that all links (directory entries) for a file be on the same file system.
 * Furthermore, on some platforms, the Java virtual machine may require to
 * be started with implementation specific privileges to create hard links
 * or to create links to directories.
 *
 * @return  The path to the link (directory entry).
 *
 * @throws  UnsupportedOperationException
 *          if the implementation does not support adding an existing file
 *          to a directory
 * @throws  FileAlreadyExistsException
 *          if the entry could not otherwise be created because a file of
 *          that name already exists *(optional specific exception)*
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager
 *          is installed, it denies [LinkPermission]<tt>("hard")</tt>
 *          or its [checkWrite(String)][SecurityManager.checkWrite]
 *          method denies write access to either the link or the
 *          existing file.
 */
public fun Path.createLink(existing: Path): Path = Files.createLink(this, existing)!!

// Deletion Functions
/**
 * Deletes a file.
 *
 * An implementation may require to examine the file to determine if the
 * file is a directory. Consequently this method may not be atomic with respect
 * to other file system operations.  If the file is a symbolic link then the
 * symbolic link itself, not the final target of the link, is deleted.
 *
 * If the file is a directory then the directory must be empty. In some
 * implementations a directory has entries for special files or links that
 * are created when the directory is created. In such implementations a
 * directory is considered empty when only the special entries exist.
 * This method can be used with the [walkFileTree][Path.walkFileTree]
 * method to delete a directory and all entries in the directory, or an
 * entire *file-tree* where required.
 *
 * On some operating systems it may not be possible to remove a file when
 * it is open and in use by this Java virtual machine or other programs.
 *
 * @throws  NoSuchFileException
 *          if the file does not exist *(optional specific exception)*
 * @throws  DirectoryNotEmptyException
 *          if the file is a directory and could not otherwise be deleted
 *          because the directory is not empty <i>(optional specific
 *          exception)</i>
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkDelete(String)][SecurityManager.checkDelete] method
 *          is invoked to check delete access to the file
 */
public fun Path.delete() = Files.delete(this)

/**
 * Deletes a file if it exists.
 *
 * As with the [delete(Path)][Path.delete] method, an
 * implementation may need to examine the file to determine if the file is a
 * directory. Consequently this method may not be atomic with respect to
 * other file system operations.  If the file is a symbolic link, then the
 * symbolic link itself, not the final target of the link, is deleted.
 *
 * If the file is a directory then the directory must be empty. In some
 * implementations a directory has entries for special files or links that
 * are created when the directory is created. In such implementations a
 * directory is considered empty when only the special entries exist.
 *
 * On some operating systems it may not be possible to remove a file when
 * it is open and in use by this Java virtual machine or other programs.
 *
 * @return  `true` if the file was deleted by this method;
 * `         false` if the file could not be deleted because it did not
 *          exist
 *
 * @throws  DirectoryNotEmptyException
 *          if the file is a directory and could not otherwise be deleted
 *          because the directory is not empty <i>(optional specific
 *          exception)</i>
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkDelete(String)][SecurityManager.checkDelete] method
 *          is invoked to check delete access to the file.
 */
public fun Path.deleteIfExists(): Boolean = Files.deleteIfExists(this)

// Location Changes
/**
 * Move or rename a file to a target file.
 *
 * By default, this method attempts to move the file to the target
 * file, failing if the target file exists except if the source and
 * target are the [same][Path.isSameFile] file, in which case this method
 * has no effect. If the file is a symbolic link then the symbolic link
 * itself, not the target of the link, is moved. This method may be
 * invoked to move an empty directory. In some implementations a directory
 * has entries for special files or links that are created when the
 * directory is created. In such implementations a directory is considered
 * empty when only the special entries exist. When invoked to move a
 * directory that is not empty then the directory is moved if it does not
 * require moving the entries in the directory.  For example, renaming a
 * directory on the same [FileStore] will usually not require moving
 * the entries in the directory. When moving a directory requires that its
 * entries be moved then this method fails (by throwing an
 * [IOException]). To move a *file tree* may involve copying rather
 * than moving directories and this can be done using the
 * [walkFileTree] method in conjunction with the [copy] utility method.
 *
 *
 * An implementation of this interface may support additional
 * implementation specific options.
 *
 * Moving a file will copy the
 * [last-modified-time][BasicFileAttributes.lastModifiedTime] to the target
 * file if supported by both source and target file stores. Copying of file
 * timestamps may result in precision loss. An implementation may also
 * attempt to copy other file attributes but is not required to fail if the
 * file attributes cannot be copied. When the move is performed as
 * a non-atomic operation, and an [IOException] is thrown, then the
 * state of the files is not defined. The original file and the target file
 * may both exist, the target file may be incomplete or some of its file
 * attributes may not been copied from the original file.
 *
 * **Usage Examples:**
 *
 * Suppose we want to rename a file to "newname", keeping the file in the
 * same directory: **Just use [rename][Path.rename], or just set the [name][Path.name] property.**
 *
 * Alternatively, suppose we want to move a file to new directory, keeping
 * the same file name, and replacing any existing file of that name in the
 * directory:
 *
 * ```Kotlin
 *     val source: Path = ...
 *     val newDir: Path = ...
 *     source.move(newDir / source.name, StandardCopyOptions.REPLACE_EXISTING)
 * ```
 *
 * @param   target
 *          The path to the target file (may be associated with a different
 *          provider to the source path)
 * @param   options
 *          Options specifying how the move should be done
 *
 * @return  the path to the target file
 *
 * @throws  UnsupportedOperationException
 *          if the array contains a copy option that is not supported
 * @throws  FileAlreadyExistsException
 *          if the target file exists but cannot be replaced because the
 *          `REPLACE_EXISTING` option is not specified <i>(optional
 *          specific exception)</i>
 * @throws  DirectoryNotEmptyException
 *          the `REPLACE_EXISTING` option is specified but the file
 *          cannot be replaced because it is a non-empty directory
 *          *(optional specific exception)*
 * @throws  AtomicMoveNotSupportedException
 *          if the options array contains the `ATOMIC_MOVE` option but
 *          the file cannot be moved as an atomic file system operation.
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkWrite(String)][SecurityManager.checkWrite]
 *          method is invoked to check write access to both the source and
 *          target file.
 */
public fun Path.move(target: Path, vararg options: CopyOption): Path = Files.move(this, target, *options)!!

/**
 * Renames [this] path to the given [name].
 *
 * @throws IOException If [this] does not exist on the file system.
 * @see Path.name
 */
public fun Path.rename(name: String, vararg options: CopyOption): Path = move(this.resolveSibling(name), *options)

/**
 * Returns the [FileStore] representing the file store where a file
 * is located.
 *
 * Once a reference to the `FileStore` is obtained it is
 * implementation specific if operations on the returned `FileStore`,
 * or [FileStoreAttributeView] objects obtained from it, continue
 * to depend on the existence of the file. In particular the behavior is not
 * defined for the case that the file is deleted or moved to a different
 * file store.
 *
 * @return  The file store where the file is stored.
 *
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to the file, and in
 *          addition it checks [RuntimePermission]`
 *          ("getFileStoreAttributes")`
 */
public val Path.fileStore get() = Files.getFileStore(this)!!

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
        simpleName.contains('.') -> throw IOException(
            "\"Path.simpleName\" does not support the usage of '.' " +
                    "inside of the setter ($simpleName), use \"Path.name\" " +
                    "or \"Path.extension\" for such actions."
        )
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

/**
 * Copy a file to a target file.
 *
 * This method copies a file to the target file with the
 * [options] parameter specifying how the copy is performed. By default, the
 * copy fails if the target file already exists or is a symbolic link,
 * except if the source and target are the [same][Path.isSameFile] file, in
 * which case the method completes without copying the file. File attributes
 * are not required to be copied to the target file. If symbolic links are
 * supported, and the file is a symbolic link, then the final target of the
 * link is copied. If the file is a directory then it creates an empty
 * directory in the target location (entries in the directory are not
 * copied). This method can be used with the
 * [walkFileTree][Path.walkFileTree] method to copy a directory and all entries in the directory,
 * or an entire *file-tree* where required.
 *
 *
 * An implementation of this interface may support additional
 * implementation specific options.
 *
 * Copying a file is not an atomic operation. If an [IOException]
 * is thrown, then it is possible that the target file is incomplete or some
 * of its file attributes have not been copied from the source file. When
 * the `REPLACE_EXISTING` option is specified and the target file
 * exists, then the target file is replaced. The check for the existence of
 * the file and the creation of the new file may not be atomic with respect
 * to other file system activities.
 *
 * **Usage Example:**
 *
 * Suppose we want to copy a file into a directory, giving it the same file
 * name as the source file:
 *
 * ```Kotlin
 *     val source: Path = ...
 *     val newDir: Path = ...
 *     source.copy(newDir / source.name)
 * ```
 *
 * @param   target
 *          the path to the target file (may be associated with a different
 *          provider to the source path)
 * @param   options
 *          options specifying how the copy should be done
 *
 * @return  The path to the target file.
 *
 * @throws  UnsupportedOperationException
 *          if the array contains a copy option that is not supported
 * @throws  FileAlreadyExistsException
 *          if the target file exists but cannot be replaced because the
 *          `REPLACE_EXISTING` option is not specified <i>(optional
 *          specific exception)</i>
 * @throws  DirectoryNotEmptyException
 *          the `REPLACE_EXISTING` option is specified but the file
 *          cannot be replaced because it is a non-empty directory
 *          *(optional specific exception)*
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to the source file, the
 *          [checkWrite(String)][SecurityManager.checkWrite] is invoked
 *          to check write access to the target file. If a symbolic link is
 *          copied the security manager is invoked to check
 * [LinkPermission]`("symbolic")`.
 */
public fun Path.copy(target: Path, vararg options: CopyOption): Path = Files.copy(this, target, *options)!!

/**
 * Tests if two paths locate the same file.
 *
 * If both [Path] objects are [equal(Object)][Path.equals]
 * then this method returns `true` without checking if the file exists.
 * If the two [Path] objects are associated with different providers
 * then this method returns `false`. Otherwise, this method checks if
 * both [Path] objects locate the same file, and depending on the
 * implementation, may require to open or access both files.
 *
 * If the file system and files remain static, then this method implements
 * an equivalence relation for non-null [Paths][Path].
 *
 * - It is *reflexive*: for [Path] `f`, `isSameFile(f, f)` should return `true`.
 * - It is *symmetric*: for two [Paths][Path] `f` and `g`, `isSameFile(f, g)` will equal `isSameFile(g, f)`.
 * - It is *transitive*: for three [Paths][Path] `f`, `g`, and `h`, if `isSameFile(f, g)` returns `true` and
 * `isSameFile(g, h)` returns `true`, then `isSameFile(f, h)` will also return `true`.
 *
 * @return  `true` if, and only if, the two paths locate the same file.
 *
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to both files.
 *
 * @see java.nio.file.attribute.BasicFileAttributes.fileKey
 */
public fun Path.isSameFile(other: Path) = Files.isSameFile(this, other)

/**
 * Tests if two paths locate the same file.
 *
 * If both [Path] objects are [equal(Object)][Path.equals]
 * then this method returns `true` without checking if the file exists.
 * If the two [Path] objects are associated with different providers
 * then this method returns `false`. Otherwise, this method checks if
 * both [Path] objects locate the same file, and depending on the
 * implementation, may require to open or access both files.
 *
 * If the file system and files remain static, then this method implements
 * an equivalence relation for non-null [Paths][Path].
 *
 * - It is *reflexive*: for [Path] `f`, `f sameFile f` should return `true`.
 * - It is *symmetric*: for two [Paths][Path] `f` and `g`, `f sameFile g` will equal `g sameFile f`.
 * - It is *transitive*: for three [Paths][Path] `f`, `g`, and `h`, if `f sameFile g` returns `true` and
 * `g sameFile h` returns `true`, then `f sameFile h` will return also `true`.
 *
 * @return  `true` if, and only if, the two paths locate the same file.
 *
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to both files.
 *
 * @see java.nio.file.attribute.BasicFileAttributes.fileKey
 */
public infix fun Path.sameFile(other: Path) = isSameFile(other)

/**
 * Tells whether or not a file is considered *hidden*.
 *
 * The exact definition of hidden is platform or provider dependent. On UNIX for
 * example a file is considered to be hidden if its name begins with a
 * period character ('.'). On Windows a file is considered hidden if it
 * isn't a directory and the DOS [hidden][DosFileAttributes.isHidden]
 * attribute is set.
 *
 * Depending on the implementation this method may require to access
 * the file system to determine if the file is considered hidden.
 *
 * @return  `true` if the file is considered hidden.
 *
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to the file.
 */
public val Path.isHidden get() = Files.isHidden(this)

/**
 * Probes the content type of a file.
 *
 * This method uses the installed [java.nio.file.spi.FileTypeDetector] implementations
 * to probe the given file to determine its content type. Each file type
 * detector's [probeContentType][java.nio.file.spi.FileTypeDetector.probeContentType] is
 * invoked, in turn, to probe the file type. If the file is recognized then
 * the content type is returned. If the file is not recognized by any of the
 * installed file type detectors then a system-default file type detector is
 * invoked to guess the content type.
 *
 * A given invocation of the Java virtual machine maintains a system-wide
 * list of file type detectors. Installed file type detectors are loaded
 * using the service-provider loading facility defined by the [ServiceLoader]
 * class. Installed file type detectors are loaded using the system class
 * loader. If the system class loader cannot be found then the extension class
 * loader is used; If the extension class loader cannot be found then the
 * bootstrap class loader is used. File type detectors are typically installed
 * by placing them in a JAR file on the application class path or in the
 * extension directory, the JAR file contains a provider-configuration file
 * named [java.nio.file.spi.FileTypeDetector] in the resource directory
 * `META-INF/services`, and the file lists one or more fully-qualified
 * names of concrete subclass of [FileTypeDetector][java.nio.file.spi.FileTypeDetector] that have a zero
 * argument constructor. If the process of locating or instantiating the
 * installed file type detectors fails then an unspecified error is thrown.
 * The ordering that installed providers are located is implementation
 * specific.
 *
 * The return value of this method is the string form of the value of a
 * Multipurpose Internet Mail Extension (MIME) content type as
 * defined by <a href="http://www.ietf.org/rfc/rfc2045.txt"><i>RFC&nbsp;2045:
 * Multipurpose Internet Mail Extensions (MIME) Part One: Format of Internet
 * Message Bodies</i></a>. The string is guaranteed to be parsable according
 * to the grammar in the RFC.
 *
 * @return  The content type of the file, or `null` if the content
 *          type cannot be determined
 *
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          If a security manager is installed and it denies an unspecified
 *          permission required by a file type detector implementation.
 */
public val Path.contentType get() = Files.probeContentType(this)!!

// File Attributes
/**
 * Returns a file attribute view of a given type.
 *
 * A file attribute view provides a read-only or updatable view of a
 * set of file attributes. This method is intended to be used where the file
 * attribute view defines type-safe methods to read or update the file
 * attributes. The `type` parameter is the type of the attribute view
 * required and the method returns an instance of that type if supported.
 * The [BasicFileAttributeView] type supports access to the basic
 * attributes of a file. Invoking this method to select a file attribute
 * view of that type will always return an instance of that class.
 *
 * The `options` array may be used to indicate how symbolic links
 * are handled by the resulting file attribute view for the case that the
 * file is a symbolic link. By default, symbolic links are followed. If the
 * option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then
 * symbolic links are not followed. This option is ignored by implementations
 * that do not support symbolic links.
 *
 * **Usage Example:**
 *
 * Suppose we want read or set a file's ACL, if supported:
 *
 * ```Kotlin
 *     val path: Path = ...
 *     val view = path.getFileAttributeView(AclFileAttributeView.class);
 *     if (view != null) {
 *         val acl = view.getAcl();
 *         ...
 *     }
 * ```
 *
 * @param   V
 *          The `FileAttributeView` type
 * @param   type
 *          the [Class] object corresponding to the file attribute view
 * @param   options
 *          options indicating how symbolic links are handled
 *
 * @return  a file attribute view of the specified type, or `null` if
 *          the attribute view type is not available
 */
public fun <V : FileAttributeView> Path.getFileAttributeView(type: Class<V>, vararg options: LinkOption): V =
    Files.getFileAttributeView(this, type, *options)!!

/**
 * Reads a file's attributes as a bulk operation.
 *
 * The `type` parameter is the type of the attributes required
 * and this method returns an instance of that type if supported. All
 * implementations support a basic set of file attributes and so invoking
 * this method with a  `type` parameter of
 * `BasicFileAttributes.class` will not throw
 * [UnsupportedOperationException].
 *
 * The `options` array may be used to indicate how symbolic links
 * are handled for the case that the file is a symbolic link. By default,
 * symbolic links are followed and the file attribute of the final target
 * of the link is read. If the option
 * [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * It is implementation specific if all file attributes are read as an
 * atomic operation with respect to other file system operations.
 *
 * **Usage Example:**
 *
 * Suppose we want to read a file's attributes in bulk:
 *
 * ```Kotlin
 *    val path: Path = ...
 *    val attrs = path.readAttributes(BasicFileAttributes.class);
 * ```
 *
 * Alternatively, suppose we want to read file's POSIX attributes without
 * following symbolic links:
 *
 * ```Kotlin
 *    val attrs = path.readAttributes(PosixFileAttributes.class, NOFOLLOW_LINKS);
 * ```
 *
 * @param   A
 *          The `BasicFileAttributes` type
 * @param   type
 *          the `Class` of the file attributes required
 *          to read
 * @param   options
 *          options indicating how symbolic links are handled
 *
 * @return  the file attributes
 *
 * @throws  UnsupportedOperationException
 *          if an attributes of the given type are not supported
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, a security manager is
 *          installed, its [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to the file. If this
 *          method is invoked to read security sensitive attributes then the
 *          security manager may be invoke to check for additional permissions.
 */
public fun <A : BasicFileAttributes> Path.readAttributes(type: Class<A>, vararg options: LinkOption): A =
    Files.readAttributes(this, type, *options)!!

/**
 * Reads a set of file attributes as a bulk operation.
 *
 * The `attributes` parameter identifies the attributes to be read
 * and takes the form:
 * <blockquote>
 * [*view-name***:**]*attribute-list*
 * </blockquote>
 * where square brackets [...] delineate an optional component and the
 * character `':'` stands for itself.
 *
 * *view-name* is the [name][FileAttributeView.name] of a
 * [FileAttributeView] that identifies a set of file attributes. If not
 * specified then it defaults to `"basic"`, the name of the file
 * attribute view that identifies the basic set of file attributes common to
 * many file systems.
 *
 * The *attribute-list* component is a comma separated list of
 * zero or more names of attributes to read. If the list contains the value
 * `"*"` then all attributes are read. Attributes that are not supported
 * are ignored and will not be present in the returned map. It is
 * implementation specific if all attributes are read as an atomic operation
 * with respect to other file system operations.
 *
 *
 * The `options` array may be used to indicate how symbolic links
 * are handled for the case that the file is a symbolic link. By default,
 * symbolic links are followed and the file attribute of the final target
 * of the link is read. If the option
 * [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * @param   attributes
 *          the attributes to read
 * @param   options
 *          options indicating how symbolic links are handled
 *
 * @return  a map of the attributes returned; The map's keys are the
 *          attribute names, its values are the attribute values
 *
 * @throws  UnsupportedOperationException
 *          if the attribute view is not available
 * @throws  IllegalArgumentException
 *          if no attributes are specified or an unrecognized attributes is
 *          specified
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, its [checkRead(String)][SecurityManager.checkRead]
 *          method denies read access to the file. If this method is invoked
 *          to read security sensitive attributes then the security manager
 *          may be invoke to check for additional permissions.
 */
public fun Path.readAttributes(attributes: String, vararg options: LinkOption): Map<String, Any> =
    Files.readAttributes(this, attributes, *options)

/**
 * Sets the value of a file attribute.
 *
 * The `attribute` parameter identifies the attribute to be set
 * and takes the form:
 *
 * > `[view-name:]attribute-name`
 *
 * where square brackets `[...]` delineate an optional component and the
 * character `':'` stands for itself.
 *
 * *view-name* is the [name][FileAttributeView.name] of a
 * [FileAttributeView] that identifies a set of file attributes. If not
 * specified then it defaults to `"basic"`, the name of the file
 * attribute view that identifies the basic set of file attributes common to
 * many file systems. *attribute-name* is the name of the attribute
 * within the set.
 *
 * The [options] array may be used to indicate how symbolic links
 * are handled for the case that the file is a symbolic link. By default,
 * symbolic links are followed and the file attribute of the final target
 * of the link is set. If the option
 * [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * **Usage Example:**
 *
 * Suppose we want to set the DOS "hidden" attribute:
 *
 * ```Kotlin
 *    val path: Path = ...
 *    path.setAttribute("dos:hidden", true)
 * ```
 *
 * *or*
 *
 * ```Kotlin
 *    val path: Path = ...
 *    path.attributes["dos:hidden"] = true
 * ```
 *
 * @param   attribute
 *          the attribute to set
 * @param   value
 *          the attribute value
 * @param   options
 *          options indicating how symbolic links are handled
 *
 * @return  the [Path] parameter
 *
 * @throws  UnsupportedOperationException
 *          if the attribute view is not available
 * @throws  IllegalArgumentException
 *          if the attribute name is not specified, or is not recognized, or
 *          the attribute value is of the correct type but has an
 *          inappropriate value
 * @throws  ClassCastException
 *          if the attribute value is not of the expected type or is a
 *          collection containing elements that are not of the expected
 *          type
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, its [checkWrite(String)][SecurityManager.checkWrite]
 *          method denies write access to the file. If this method is invoked
 *          to set security sensitive attributes then the security manager
 *          may be invoked to check for additional permissions.
 */
public fun Path.setAttribute(attribute: String, value: Any?, vararg options: LinkOption): Path =
    Files.setAttribute(this, attribute, value, *options)!!

/**
 * Reads the value of a file attribute.
 *
 * The [attributes] parameter identifies the attribute to be read
 * and takes the form:
 *
 * > `[view-name:]attribute-name`
 *
 * where square brackets `[...]` delineate an optional component and the
 * character `':'` stands for itself.
 *
 * *view-name* is the [name][FileAttributeView.name] of a
 * [FileAttributeView] that identifies a set of file attributes. If not
 * specified then it defaults to `"basic"`, the name of the file
 * attribute view that identifies the basic set of file attributes common to
 * many file systems. *attribute-name* is the name of the attribute.
 *
 * The `options` array may be used to indicate how symbolic links
 * are handled for the case that the file is a symbolic link. By default,
 * symbolic links are followed and the file attribute of the final target
 * of the link is read. If the option
 * [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * **Usage Example:**
 *
 * Suppose we require the user ID of the file owner on a system that
 * supports a "`unix`" view:
 *
 * ```Kotlin
 *    val path: Path = ...
 *    val uid = path.getAttribute("unix:uid") as Int
 * ```
 *
 * *or*
 * ```Kotlin
 *    val path: Path = ...
 *    val uid = path.attributes["unix:uid"] as Int
 * ```
 *
 * @param   attribute
 *          the attribute to read
 * @param   options
 *          options indicating how symbolic links are handled
 *
 * @return  The attribute value.
 *
 * @throws  UnsupportedOperationException
 *          if the attribute view is not available
 * @throws  IllegalArgumentException
 *          if the attribute name is not specified or is not recognized
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, its [checkRead(String)][SecurityManager.checkRead]
 *          method denies read access to the file. If this method is invoked
 *          to read security sensitive attributes then the security manager
 *          may be invoked to check for additional permissions.
 */
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
/**
 * Returns a file's POSIX file permissions.
 *
 * The [Path] parameter is associated with a [FileSystem]
 * that supports the [PosixFileAttributeView]. This attribute view
 * provides access to file attributes commonly associated with files on file
 * systems used by operating systems that implement the Portable Operating
 * System Interface (POSIX) family of standards.
 *
 * The [options] array may be used to indicate how symbolic links
 * are handled for the case that the file is a symbolic link. By default,
 * symbolic links are followed and the file attribute of the final target
 * of the link is read. If the option
 * [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * @param   options
 *          options indicating how symbolic links are handled
 *
 * @return  the file permissions
 *
 * @throws  UnsupportedOperationException
 *          if the associated file system does not support the
 * `         PosixFileAttributeView`
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, a security manager is
 *          installed, and it denies [RuntimePermission]<tt>("accessUserInformation")</tt>
 *          or its [checkRead(String)][SecurityManager.checkRead] method
 *          denies read access to the file.
 */
public fun Path.getPosixFilePermissions(vararg options: LinkOption): Set<PosixFilePermission> =
    Files.getPosixFilePermissions(this, *options)!!.toSet()

/**
 * A property variant of [getPosixFilePermissions] and [Files.setPosixFilePermissions].
 *
 * Use this one if you don't need to use any of the additional LinkOptions from [getPosixFilePermissions].
 *
 * @see Files.setPosixFilePermissions
 */
public var Path.permissions: Set<PosixFilePermission>
    get() = this.getPosixFilePermissions()
    public set(perms) {
        Files.setPosixFilePermissions(this, perms)!!
    }

// Owner
/**
 * Returns the owner of a file.
 *
 * The [Path] parameter is associated with a file system that
 * supports [FileOwnerAttributeView]. This file attribute view provides
 * access to a file attribute that is the owner of the file.
 *
 * @param   options
 *          options indicating how symbolic links are handled
 *
 * @return  A user principal representing the owner of the file.
 *
 * @throws  UnsupportedOperationException
 *          if the associated file system does not support the
 * `         FileOwnerAttributeView`
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, it denies [RuntimePermission]<tt>("accessUserInformation")</tt>
 *          or its [checkRead(String)][SecurityManager.checkRead] method
 *          denies read access to the file.
 */
public fun Path.getOwner(vararg options: LinkOption): UserPrincipal = Files.getOwner(this, *options)!!

/**
 * A property variant of [getOwner] and [Files.setOwner].
 *
 * Use this one if you don't need to use any of the additional LinkOptions from [getOwner].
 *
 * @see Files.setOwner
 */
public var Path.owner: UserPrincipal
    get() = getOwner()
    set(newOwner) {
        Files.setOwner(this, newOwner)
    }

// is[...] functions.
/**
 * Tests whether a file is a symbolic link.
 *
 * Where it is required to distinguish an I/O exception from the case
 * that the file is not a symbolic link then the file attributes can be
 * read with the
 * [readAttributes(Path,Class,LinkOption[])][readAttributes] method and the file type tested with the
 * [BasicFileAttributes.isSymbolicLink] method.
 *
 * @return  `true` if the file is a symbolic link; `false` if
 *          the file does not exist, is not a symbolic link, or it cannot
 *          be determined if the file is a symbolic link or not.
 *
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, its [checkRead(String)][SecurityManager.checkRead]
 *          method denies read access to the file.
 */
public val Path.isSymbolicLink: Boolean get() = Files.isSymbolicLink(this)

/**
 * Tests whether a file is a directory.
 *
 * **Use the property variant of this unless you need to set specific [LinkOptions][LinkOption].**
 *
 * The [options] array may be used to indicate how symbolic links
 * are handled for the case that the file is a symbolic link. By default,
 * symbolic links are followed and the file attribute of the final target
 * of the link is read. If the option
 * [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * Where it is required to distinguish an I/O exception from the case
 * that the file is not a directory then the file attributes can be
 * read with the
 * [readAttributes(Path, Class, vararg LinkOption)][readAttributes] method and the file type tested with the
 * [BasicFileAttributes.isDirectory] method.
 *
 * @param   options
 *          options indicating how symbolic links are handled
 *
 * @return  `true` if the file is a directory; `false` if
 *          the file does not exist, is not a directory, or it cannot
 *          be determined if the file is a directory or not.
 *
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, its [checkRead(String)][SecurityManager.checkRead]
 *          method denies read access to the file.
 */
public fun Path.isDirectory(vararg options: LinkOption): Boolean = Files.isDirectory(this, *options)

/**
 * Tests whether a file is a directory.
 *
 * **This property variant follows symbolic links, if you want a different behaviour, please use the function variant.**
 *
 * Where it is required to distinguish an I/O exception from the case
 * that the file is not a directory then the file attributes can be
 * read with the
 * [readAttributes(Path, Class, vararg LinkOption)][readAttributes] method and the file type tested with the
 * [BasicFileAttributes.isDirectory] method.
 *
 * @return  `true` if the file is a directory; `false` if
 *          the file does not exist, is not a directory, or it cannot
 *          be determined if the file is a directory or not.
 *
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, its [checkRead(String)][SecurityManager.checkRead]
 *          method denies read access to the file.
 */
public val Path.isDirectory: Boolean get() = this.isDirectory()

// Regular File
/**
 * Tests whether a file is a regular file with opaque content.
 *
 * The [options] array may be used to indicate how symbolic links
 * are handled for the case that the file is a symbolic link. By default,
 * symbolic links are followed and the file attribute of the final target
 * of the link is read. If the option
 * [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * Where it is required to distinguish an I/O exception from the case
 * that the file is not a regular file then the file attributes can be
 * read with the
 * [readAttributes(Path, Class, vararg LinkOption)][readAttributes] method and the file type tested with the
 * [BasicFileAttributes.isRegularFile] method.
 *
 * @param   options
 *          options indicating how symbolic links are handled
 *
 * @return  `true` if the file is a regular file; `false` if
 *          the file does not exist, is not a regular file, or it
 *          cannot be determined if the file is a regular file or not.
 *
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, its [checkRead(String)][SecurityManager.checkRead]
 *          method denies read access to the file.
 */
public fun Path.isRegularFile(vararg options: LinkOption): Boolean = Files.isRegularFile(this, *options)

/**
 * Tests whether a file is a regular file with opaque content.
 *
 * Where it is required to distinguish an I/O exception from the case
 * that the file is not a regular file then the file attributes can be
 * read with the
 * [readAttributes(Path, Class, vararg LinkOption)][readAttributes] method and the file type tested with the
 * [BasicFileAttributes.isRegularFile] method.
 *
 * @return  `true` if the file is a regular file; `false` if
 *          the file does not exist, is not a regular file, or it
 *          cannot be determined if the file is a regular file or not.
 *
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, its [checkRead(String)][SecurityManager.checkRead]
 *          method denies read access to the file.
 */
public val Path.isRegularFile: Boolean get() = this.isRegularFile()

// Last Modified Time
/**
 * Returns a file's last modified time.
 *
 * The [options] array may be used to indicate how symbolic links
 * are handled for the case that the file is a symbolic link. By default,
 * symbolic links are followed and the file attribute of the final target
 * of the link is read. If the option
 * [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * @param   options
 *          options indicating how symbolic links are handled
 *
 * @return  a `FileTime` representing the time the file was last
 *          modified, or an implementation specific default when a time
 *          stamp to indicate the time of last modification is not supported
 *          by the file system
 *
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, its [checkRead(String)][SecurityManager.checkRead]
 *          method denies read access to the file.
 *
 * @see BasicFileAttributes.lastModifiedTime
 */
public fun Path.getLastModifiedTime(vararg options: LinkOption): FileTime =
    Files.getLastModifiedTime(this, *options)!!

/**
 * A property variant of [getLastModifiedTime] and [Files.setLastModifiedTime].
 *
 * Use this one if you don't need to use any of the additional LinkOptions from [getLastModifiedTime].
 *
 * @see Files.setPosixFilePermissions
 */
public var Path.lastModifiedTime: FileTime
    get() = this.getLastModifiedTime()
    set(newTime) {
        Files.setLastModifiedTime(this, newTime)!!
    }

// Size
/**
 * Returns the size of a file (in bytes). The size may differ from the
 * actual size on the file system due to compression, support for sparse
 * files, or other reasons. The size of files that are not
 * [regular][isRegularFile] files is implementation specific and
 * therefore unspecified.
 *
 * @return  the file size, in bytes
 *
 * @throws  IOException
 *          if an I/O error occurs
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, its [checkRead(String)][SecurityManager.checkRead]
 *          method denies read access to the file.
 *
 * @see BasicFileAttributes.size
 */
public val Path.size: Long get() = Files.size(this)

// Exists
/**
 * Tests whether a file exists.
 *
 * The [options] parameter may be used to indicate how symbolic links
 * are handled for the case that the file is a symbolic link. By default,
 * symbolic links are followed. If the option
 * [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * Note that the result of this method is immediately outdated. If this
 * method indicates the file exists then there is no guarantee that a
 * subsequent access will succeed. Care should be taken when using this
 * method in security sensitive applications.
 *
 * @param   options
 *          options indicating how symbolic links are handled
 * .
 * @return  `true` if the file exists; `false` if the file does
 *          not exist or its existence cannot be determined.
 *
 * @throws  SecurityException
 *          In the case of the default provider, the
 * [         SecurityManager#checkRead(String)] is invoked to check
 *          read access to the file.
 *
 * @see #notExists
 */
public fun Path.exists(vararg options: LinkOption): Boolean = Files.exists(this, *options)

/**
 * Tests whether a file exists.
 *
 * Note that the result of this method is immediately outdated. If this
 * method indicates the file exists then there is no guarantee that a
 * subsequent access will succeed. Care should be taken when using this
 * method in security sensitive applications.
 *
 * @return  `true` if the file exists; `false` if the file does
 *          not exist or its existence cannot be determined.
 *
 * @throws  SecurityException
 *          In the case of the default provider, the
 * [         SecurityManager#checkRead(String)] is invoked to check
 *          read access to the file.
 *
 * @see #notExists
 */
public val Path.exists get() = this.exists()

// Not Exists
/**
 * Tests whether the file located by this path does not exist. This method
 * is intended for cases where it is required to take action when it can be
 * confirmed that a file does not exist.
 *
 * The [options] parameter may be used to indicate how symbolic links
 * are handled for the case that the file is a symbolic link. By default,
 * symbolic links are followed. If the option
 * [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * Note that this method is not the complement of the
 * [exists][Path.exists] method. Where it is not possible to determine if a file exists
 * or not then both methods return `false`. As with the `exists`
 * method, the result of this method is immediately outdated. If this
 * method indicates the file does exist then there is no guarantee that a
 * subsequent attempt to create the file will succeed. Care should be taken
 * when using this method in security sensitive applications.
 *
 * @param options Options indicating how symbolic links are handled.
 *
 * @return `true` if the file does not exist; `false` if the file exists or its existence cannot be determined.
 *
 * @throws SecurityException In the case of the default provider, the [SecurityManager#checkRead(String)]
 * is invoked to check read access to the file.
 */
public fun Path.notExists(vararg options: LinkOption): Boolean = Files.notExists(this, *options)

/**
 * Tests whether the file located by this path does not exist. This method
 * is intended for cases where it is required to take action when it can be
 * confirmed that a file does not exist.
 *
 * The [options] parameter may be used to indicate how symbolic links
 * are handled for the case that the file is a symbolic link. By default,
 * symbolic links are followed. If the option
 * [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * Note that this method is not the complement of the
 * [exists][Path.exists] method. Where it is not possible to determine if a file exists
 * or not then both methods return `false`. As with the `exists`
 * method, the result of this method is immediately outdated. If this
 * method indicates the file does exist then there is no guarantee that a
 * subsequent attempt to create the file will succeed. Care should be taken
 * when using this method in security sensitive applications.
 *
 * @return `true` if the file does not exist; `false` if the file exists or its existence cannot be determined.
 *
 * @throws SecurityException In the case of the default provider, the [SecurityManager#checkRead(String)]
 * is invoked to check read access to the file.
 */
public val Path.notExists: Boolean get() = this.notExists()

// Accessibility
/**
 * Tests whether a file is readable.
 *
 * This method checks that a file exists
 * and that this Java virtual machine has appropriate privileges that would
 * allow it open the file for reading. Depending on the implementation, this
 * method may require to read file permissions, access control lists, or
 * other file attributes in order to check the effective access to the file.
 * Consequently, this method may not be atomic with respect to other file
 * system operations.
 *
 * Note that the result of this method is immediately outdated, there is
 * no guarantee that a subsequent attempt to open the file for reading will
 * succeed (or even that it will access the same file). Care should be taken
 * when using this method in security sensitive applications.
 *
 * @return  `true` if the file exists and is readable; `false`
 *          if the file does not exist, read access would be denied because
 *          the Java virtual machine has insufficient privileges, or access
 *          cannot be determined
 *
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          is invoked to check read access to the file.
 */
public val Path.isReadable: Boolean get() = Files.isReadable(this)

/**
 * A property variant of [Files.isWritable] and a custom setter.
 *
 * When setting this to `true` or `false`, the appropriate permissions of the file will be updated.
 *
 * @throws SecurityException In the case of the default provider, and a security manager is installed,
 * the [checkWrite(String)][SecurityManager.checkWrite] is invoked to check write access to the file.
 *
 * @see Files.isWritable
 * @see Path.permissions
 */
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

/**
 * Tests whether a file is executable.
 *
 * This method checks that a file exists
 * and that this Java virtual machine has appropriate privileges to
 * [execute][Runtime.exec] the file. The semantics may differ when checking
 * access to a directory. For example, on UNIX systems, checking for
 * execute access checks that the Java virtual machine has permission to
 * search the directory in order to access file or subdirectories.
 *
 * Depending on the implementation, this method may require to read file
 * permissions, access control lists, or other file attributes in order to
 * check the effective access to the file. Consequently, this method may not
 * be atomic with respect to other file system operations.
 *
 * Note that the result of this method is immediately outdated, there is
 * no guarantee that a subsequent attempt to execute the file will succeed
 * (or even that it will access the same file). Care should be taken when
 * using this method in security sensitive applications.
 *
 * @return  `true` if the file exists and is executable; `false`
 *          if the file does not exist, execute access would be denied because
 *          the Java virtual machine has insufficient privileges, or access
 *          cannot be determined
 *
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the
 * [checkExec(String)][SecurityManager.checkExec] is invoked to check execute access to the file.
 */
public val Path.isExecutable: Boolean get() = Files.isExecutable(this)

// File Tree Visitors
public typealias PathVisitor = FileVisitor<in Path>

/**
 * Walks a file tree.
 *
 * This method walks a file tree rooted at [this] file. The
 * file tree traversal is *depth-first* with the given
 * [FileVisitor] invoked for each file encountered. File tree traversal
 * completes when all accessible files in the tree have been visited, or a
 * visit method returns a result of
 * [TERMINATE][FileVisitResult.TERMINATE]. Where a visit method terminates due an [IOException],
 * an uncaught error, or runtime exception, then the traversal is terminated
 * and the error or exception is propagated to the caller of this method.
 *
 * For each file encountered this method attempts to read its
 * [BasicFileAttributes][java.nio.file.attribute.BasicFileAttributes]. If the file is not a
 * directory then the [visitFile][FileVisitor.visitFile] method is
 * invoked with the file attributes. If the file attributes cannot be read,
 * due to an I/O exception, then the
 * [visitFileFailed][FileVisitor.visitFileFailed] method is invoked with the I/O exception.
 *
 * Where the file is a directory, and the directory could not be opened,
 * then the `visitFileFailed` method is invoked with the I/O exception,
 * after which, the file tree walk continues, by default, at the next
 * *sibling* of the directory.
 *
 * Where the directory is opened successfully, then the entries in the
 * directory, and their *descendants* are visited. When all entries
 * have been visited, or an I/O error occurs during iteration of the
 * directory, then the directory is closed and the visitor's
 * [postVisitDirectory][FileVisitor.postVisitDirectory] method is invoked.
 * The file tree walk then continues, by default, at the next *sibling*
 * of the directory.
 *
 * By default, symbolic links are not automatically followed by this
 * method. If the [options] parameter contains the
 * [FOLLOW_LINKS][FileVisitOption.FOLLOW_LINKS] option then symbolic links are
 * followed. When following links, and the attributes of the target cannot
 * be read, then this method attempts to get the `BasicFileAttributes`
 * of the link. If they can be read then the `visitFile` method is
 * invoked with the attributes of the link (otherwise the `visitFileFailed`
 * method is invoked as specified above).
 *
 * If the [options] parameter contains the
 * [FOLLOW_LINKS][FileVisitOption.FOLLOW_LINKS] option then this method keeps
 * track of directories visited so that cycles can be detected. A cycle
 * arises when there is an entry in a directory that is an ancestor of the
 * directory. Cycle detection is done by recording the
 * [file-key][java.nio.file.attribute.BasicFileAttributes.fileKey] of directories,
 * or if file keys are not available, by invoking the
 * [isSameFile][Path.isSameFile] method to test if a directory is the same file as an
 * ancestor. When a cycle is detected it is treated as an I/O error, and the
 * [visitFileFailed][FileVisitor.visitFileFailed] method is invoked with
 * an instance of [FileSystemLoopException].
 *
 * **[options] is set to [emptySet] by default.**
 *
 * The [maxDepth] parameter is the maximum number of levels of
 * directories to visit. A value of `0` means that only the starting
 * file is visited, unless denied by the security manager. A value of
 * [MAX_VALUE][Integer.MAX_VALUE] may be used to indicate that all
 * levels should be visited. The `visitFile` method is invoked for all
 * files, including directories, encountered at `maxDepth`, unless the
 * basic file attributes cannot be read, in which case the
 * `visitFileFailed` method is invoked.
 *
 * **[maxDepth] is set to [MAX_VALUE][Integer.MAX_VALUE] by default.**
 *
 * If a visitor returns a result of `null` then
 * [NullPointerException] is thrown.
 *
 * When a security manager is installed and it denies access to a file
 * (or directory), then it is ignored and the visitor is not invoked for
 * that file (or directory).
 *
 * @param   options
 *          options to configure the traversal
 * @param   maxDepth
 *          the maximum number of directory levels to visit
 * @param   visitor
 *          the file visitor to invoke for each file
 *
 * @return  the starting file
 *
 * @throws  IllegalArgumentException
 *          if the `maxDepth` parameter is negative
 * @throws  SecurityException
 *          If the security manager denies access to the starting file.
 *          In the case of the default provider, the
 *          [checkRead(String)][SecurityManager.checkRead] method is invoked
 *          to check read access to the directory.
 * @throws  IOException
 *          if an I/O error is thrown by a visitor method
 */
public fun Path.walkFileTree(
    options: Set<FileVisitOption> = emptySet(),
    maxDepth: Int = Int.MAX_VALUE,
    visitor: PathVisitor
): Path = Files.walkFileTree(this, options, maxDepth, visitor)

// Buffered Things
/**
 * Opens a file for reading, returning a [BufferedReader] that may be
 * used to read text from the file in an efficient manner. Bytes from the
 * file are decoded into characters using the specified charset. Reading
 * commences at the beginning of the file.
 *
 * The `Reader` methods that read from the file throw
 * [IOException] if a malformed or unmappable byte sequence is read.
 *
 * @param charset The charset to use for decoding. *(Set to [UTF_8][StandardCharsets.UTF_8] by default)*
 *
 * @return A new buffered reader, with default buffer size, to read text from the file.
 *
 * @throws  IOException
 *          if an I/O error occurs opening the file
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to the file.
 *
 * @see Path.readAllLines
 */
public fun Path.newBufferedReader(charset: Charset = StandardCharsets.UTF_8): BufferedReader =
    Files.newBufferedReader(this, charset)!!

/**
 * Opens or creates a file for writing, returning a `BufferedWriter`
 * that may be used to write text to the file in an efficient manner.
 *
 * The [options] parameter specifies how the the file is created or
 * opened. If no options are present then this method works as if the
 * [TRUNCATE_EXISTING][StandardOpenOption.TRUNCATE_EXISTING], [CREATE][StandardOpenOption.CREATE], and
 * [WRITE][StandardOpenOption.WRITE] options are present. In other words, it
 * opens the file for writing, creating the file if it doesn't exist, or
 * initially truncating an existing [regular-file][Path.isRegularFile] to
 * a size of `0` if it exists.
 *
 * The `Writer` methods to write text throw [IOException]
 * if the text cannot be encoded using the specified charset.
 *
 * @param charset The charset to use for encoding. *(Set to [UTF_8][StandardCharsets.UTF_8] by default)*
 * @param options Options specifying how the file is opened
 *
 * @return A new [BufferedWriter], with default buffer size, to write text to the file.
 *
 * @throws  IOException
 *          if an I/O error occurs opening or creating the file
 * @throws  UnsupportedOperationException
 *          if an unsupported option is specified
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkWrite(String)][SecurityManager.checkWrite]
 *          method is invoked to check write access to the file.
 *
 * @see Path.writeBytes
 */
public fun Path.newBufferedWriter(
    charset: Charset = StandardCharsets.UTF_8,
    vararg options: OpenOption
): BufferedWriter = Files.newBufferedWriter(this, charset, *options)!!

// Copy
/**
 * Copies all bytes from an input stream to [this] file. On return, the input stream will be at end of the stream.
 *
 * By default, the copy fails if the target file already exists or is a
 * symbolic link. If the
 * [REPLACE_EXISTING][StandardCopyOption.REPLACE_EXISTING] option is specified, and the target file already exists,
 * then it is replaced if it is not a non-empty directory. If the target
 * file exists and is a symbolic link, then the symbolic link is replaced.
 * In this release, the `REPLACE_EXISTING` option is the only option
 * required to be supported by this method. Additional options may be
 * supported in future releases.
 *
 * If an I/O error occurs reading from the input stream or writing to
 * [this] file, then it may do so after the target file has been created and
 * after some bytes have been read or written. Consequently the input
 * stream may not be at end of stream and may be in an inconsistent state.
 * It is strongly recommended that the input stream be promptly closed if an
 * I/O error occurs.
 *
 * This method may block indefinitely reading from the [inputStream] (or
 * writing to the file). The behavior for the case that the input stream is
 * *asynchronously closed* or the thread interrupted during the copy is
 * highly input stream and file system provider specific and therefore not
 * specified.
 *
 * **Usage example**:
 *
 * Suppose we want to capture a web page and save it to a file:
 *
 * ```Kotlin
 *     val path: Path = ...
 *     val uri = URI.create("http://java.sun.com/")
 *     uri.toURL().openStream().use { path.transform(it) }
 * ```
 *
 * @param   inputStream
 *          the input stream to read from
 * @param   options
 *          options specifying how the copy should be done
 *
 * @return  the number of bytes read or written
 *
 * @throws  IOException
 *          if an I/O error occurs when reading or writing
 * @throws  FileAlreadyExistsException
 *          if the target file exists but cannot be replaced because the
 *          `REPLACE_EXISTING` option is not specified <i>(optional
 *          specific exception)</i>
 * @throws  DirectoryNotEmptyException
 *          The `REPLACE_EXISTING` option is specified but the file
 *          cannot be replaced because it is a non-empty directory
 *          *(optional specific exception)*
 * @throws  UnsupportedOperationException
 *          if `options` contains a copy option that is not supported
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkWrite(String)][SecurityManager.checkWrite]
 *          method is invoked to check write access to the file. Where the
 *          `REPLACE_EXISTING` option is specified, the security
 *          manager's [checkDelete(String)][SecurityManager.checkDelete]
 *          method is invoked to check that an existing file can be deleted.
 */
public fun Path.transform(inputStream: InputStream, vararg options: CopyOption): Long =
    Files.copy(inputStream, this, *options)

/**
 * Copies all bytes from a file to an output stream.
 *
 * If an I/O error occurs reading from the file or writing to the output
 * stream, then it may do so after some bytes have been read or written.
 * Consequently the output stream may be in an inconsistent state. It is
 * strongly recommended that the output stream be promptly closed if an I/O
 * error occurs.
 *
 * This method may block indefinitely writing to the output stream (or
 * reading from the file). The behavior for the case that the output stream
 * is *asynchronously closed* or the thread interrupted during the copy
 * is highly output stream and file system provider specific and therefore
 * not specified.
 *
 * Note that if the given output stream is [java.io.Flushable]
 * then its [flush][java.io.Flushable.flush] method may need to invoked
 * after this method completes so as to flush any buffered output.
 *
 * @param   outputStream
 *          the output stream to write to
 *
 * @return  the number of bytes read or written
 *
 * @throws  IOException
 *          if an I/O error occurs when reading or writing
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to the file.
 */
public fun Path.copy(outputStream: OutputStream): Long = Files.copy(this, outputStream)

// Reading Functions
/**
 * Reads all the bytes from a file.
 *
 * The method ensures that the file is
 * closed when all bytes have been read or an I/O error, or other runtime
 * exception, is thrown.
 *
 * Note that this method is intended for simple cases where it is
 * convenient to read all bytes into a byte array. It is not intended for
 * reading in large files.
 *
 * @return  a byte array containing the bytes read from the file
 *
 * @throws  IOException
 *          if an I/O error occurs reading from the stream
 * @throws  OutOfMemoryError
 *          if an array of the required size cannot be allocated, for
 *          example the file is larger that `2GB`
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to the file.
 */
public fun Path.readAllBytes(): ByteArray = Files.readAllBytes(this)!!

/**
 * Read all lines from a file. This method ensures that the file is
 * closed when all bytes have been read or an I/O error, or other runtime
 * exception, is thrown. Bytes from the file are decoded into characters
 * using the specified charset.
 *
 * This method recognizes the following as line terminators:
 * <ul>
 *   <li> <code>&#92;u000D</code> followed by <code>&#92;u000A</code>,
 *     CARRIAGE RETURN followed by LINE FEED </li>
 *   <li> <code>&#92;u000A</code>, LINE FEED </li>
 *   <li> <code>&#92;u000D</code>, CARRIAGE RETURN </li>
 * </ul>
 * Additional Unicode line terminators may be recognized in future
 * releases.
 *
 * Note that this method is intended for simple cases where it is
 * convenient to read all lines in a single operation. It is not intended
 * for reading in large files.
 *
 * @param   charset The charset to use for decoding. *(Set to [UTF_8][StandardCharsets.UTF_8] by default)*
 *
 * @return  the lines from the file as a `List`; whether the
 * `         List` is modifiable or not is implementation dependent and
 *          therefore not specified
 *
 * @throws  IOException
 *          if an I/O error occurs reading from the file or a malformed or
 *          unmappable byte sequence is read
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to the file.
 *
 * @see newBufferedReader
 */
public fun Path.readAllLines(charset: Charset = StandardCharsets.UTF_8): List<String> =
    Files.readAllLines(this, charset)!!.toList()

// Writing Functions
/**
 * Writes bytes to a file.
 *
 * The [options] parameter specifies how the
 * the file is created or opened. If no options are present then this method
 * works as if the [CREATE][StandardOpenOption.CREATE],
 * [WRITE][StandardOpenOption.WRITE], and [TRUNCATE_EXISTING][StandardOpenOption.TRUNCATE_EXISTING] options are present.
 * In other words, it opens the file for writing, creating the file if it doesn't exist, or
 * initially truncating an existing [regular-file][.isRegularFile] to
 * a size of `0`. All bytes in the byte array are written to the file.
 * The method ensures that the file is closed when all bytes have been
 * written (or an I/O error or other runtime exception is thrown). If an I/O
 * error occurs then it may do so after the file has created or truncated,
 * or after some bytes have been written to the file.
 *
 * **Usage example**:
 *
 * By default the method creates a new file or
 * overwrites an existing file. Suppose you instead want to append bytes
 * to an existing file:
 *
 * ```Kotlin
 *     Path path = ...
 *     byte[] bytes = ...
 *     Files.write(path, bytes, StandardOpenOption.APPEND);
 * ```
 *
 * @param   bytes
 *          the byte array with the bytes to write
 * @param   options
 *          options specifying how the file is opened
 *
 * @return  the path
 *
 * @throws  IOException
 *          if an I/O error occurs writing to or creating the file
 * @throws  UnsupportedOperationException
 *          if an unsupported option is specified
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkWrite(String)][SecurityManager.checkWrite]
 *          method is invoked to check write access to the file.
 */
public fun Path.writeBytes(bytes: ByteArray, vararg options: OpenOption): Path = Files.write(this, bytes, *options)

/**
 * Write lines of text to a file. Each line is a char sequence and is
 * written to the file in sequence with each line terminated by the
 * platform's line separator, as defined by the system property
 * `line.separator`. Characters are encoded into bytes using the specified
 * charset.
 *
 * The `options` parameter specifies how the the file is created
 * or opened. If no options are present then this method works as if the
 * [CREATE][StandardOpenOption.CREATE],
 * [StandardOpenOption#WRITE WRITE][* StandardOpenOption#TRUNCATE_EXISTING TRUNCATE_EXISTING}, and {@link] options are present. In other words, it
 * opens the file for writing, creating the file if it doesn't exist, or
 * initially truncating an existing [regular-file][.isRegularFile] to
 * a size of `0`. The method ensures that the file is closed when all
 * lines have been written (or an I/O error or other runtime exception is
 * thrown). If an I/O error occurs then it may do so after the file has
 * created or truncated, or after some bytes have been written to the file.
 *
 * @param lines An object to iterate over the char sequences.
 * @param charset The charset to use for decoding. *(Set to [UTF_8][StandardCharsets.UTF_8] by default)*
 * @param options Options specifying how the file is opened.
 *
 * @throws  IOException
 *          if an I/O error occurs writing to or creating the file, or the
 *          text cannot be encoded using the specified charset
 * @throws  UnsupportedOperationException
 *          if an unsupported option is specified
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkWrite(String)][SecurityManager.checkWrite]
 *          method is invoked to check write access to the file.
 */
public fun Path.writeLines(
    lines: Iterable<CharSequence>,
    charset: Charset = StandardCharsets.UTF_8,
    vararg options: OpenOption
): Path = Files.write(this, lines, charset, *options)!!

/**
 * Appends a [String] to this [Path].
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

/**
 * Return a lazily populated [Stream], the elements of which are the entries in the directory.
 * The listing is not recursive.
 *
 * The elements of the stream are [Path] objects that are
 * obtained as if by [resolving(Path)][Path.resolve] the name of the
 * directory entry against `dir`. Some file systems maintain special
 * links to the directory itself and the directory's parent directory.
 * Entries representing these links are not included.
 *
 * The stream is *weakly consistent*. It is thread safe but does
 * not freeze the directory while iterating, so it may (or may not)
 * reflect updates to the directory that occur after returning from this
 * method.
 *
 * The returned stream encapsulates a [DirectoryStream].
 * If timely disposal of file system resources is required, the
 * `try`-with-resources construct should be used to ensure that the
 * stream's [close][Stream.close] method is invoked after the stream
 * operations are completed.
 *
 * Operating on a closed stream behaves as if the end of stream
 * has been reached. Due to read-ahead, one or more elements may be
 * returned after the stream has been closed.
 *
 * If an [IOException] is thrown when accessing the directory
 * after this method has returned, it is wrapped in an
 * [UncheckedIOException] which will be thrown from the method that caused
 * the access to take place.
 *
 * @return  The [Stream] describing the contents of the directory.
 *
 * @throws  NotDirectoryException
 *          if the file could not otherwise be opened because it is not
 *          a directory *(optional specific exception)*
 * @throws  IOException
 *          if an I/O error occurs when opening the directory
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to the directory.
 *
 * @see newDirectoryStream
 */
public val Path.entries: PathStream
    get() {
        this.checkIfDirectory()
        return Files.list(this)!!
    }

public val Path.children: List<Path> get() = this.entries.toList()

/**
 * Return a `Stream` that is lazily populated with
 * [Path] by walking the file tree rooted at a given starting file.  The
 * file tree is traversed *depth-first*, the elements in the stream
 * are [Path] objects that are obtained as if by
 * [resolving(Path)][Path.resolve] the relative path against `start`.
 *
 * The `stream` walks the file tree as elements are consumed.
 * The `Stream` returned is guaranteed to have at least one
 * element, the starting file itself. For each file visited, the stream
 * attempts to read its [BasicFileAttributes]. If the file is a
 * directory and can be opened successfully, entries in the directory, and
 * their *descendants* will follow the directory in the stream as
 * they are encountered. When all entries have been visited, then the
 * directory is closed. The file tree walk then continues at the next
 * *sibling* of the directory.
 *
 * The stream is *weakly consistent*. It does not freeze the
 * file tree while iterating, so it may (or may not) reflect updates to
 * the file tree that occur after returned from this method.
 *
 * By default, symbolic links are not automatically followed by this
 * method. If the `options` parameter contains the
 * [FOLLOW_LINKS][FileVisitOption.FOLLOW_LINKS] option then symbolic links are
 * followed. When following links, and the attributes of the target cannot
 * be read, then this method attempts to get the `BasicFileAttributes`
 * of the link.
 *
 * If the `options` parameter contains the
 * [FOLLOW_LINKS][FileVisitOption.FOLLOW_LINKS] option then the stream keeps
 * track of directories visited so that cycles can be detected. A cycle
 * arises when there is an entry in a directory that is an ancestor of the
 * directory. Cycle detection is done by recording the
 * [file-key][java.nio.file.attribute.BasicFileAttributes.fileKey] of directories,
 * or if file keys are not available, by invoking the
 * [isSameFile][#isSameFile] method to test if a directory is the same file as an
 * ancestor. When a cycle is detected it is treated as an I/O error with
 * an instance of [FileSystemLoopException].
 *
 * The `maxDepth` parameter is the maximum number of levels of
 * directories to visit. A value of `0` means that only the starting
 * file is visited, unless denied by the security manager. A value of
 * [MAX_VALUE][Integer.MAX_VALUE] may be used to indicate that all
 * levels should be visited.
 *
 * When a security manager is installed and it denies access to a file
 * (or directory), then it is ignored and not included in the stream.
 *
 * The returned stream encapsulates one or more [DirectoryStream]s.
 * If timely disposal of file system resources is required, the
 * `try`-with-resources construct should be used to ensure that the
 * stream's [close][Stream.close] method is invoked after the stream
 * operations are completed.  Operating on a closed stream will result in an
 * [java.lang.IllegalStateException].
 *
 * If an [IOException] is thrown when accessing the directory
 * after this method has returned, it is wrapped in an
 * [UncheckedIOException] which will be thrown from the method that caused
 * the access to take place.
 *
 * @param maxDepth The maximum number of directory levels to visit. **(This is set to [Int.MAX_VALUE] by default.)**
 * @param options Options to configure the traversal.
 *
 * @throws IllegalArgumentException if the [maxDepth] parameter is negative.
 * @throws SecurityException
 *          If the security manager denies access to the starting file.
 *          In the case of the default provider, the
 *          [checkRead(String)][SecurityManager.checkRead] method is invoked
 *          to check read access to the directory.
 * @throws IOException
 *          if an I/O error is thrown when accessing the starting file.
 */
public fun Path.walk(maxDepth: Int = Int.MAX_VALUE, vararg options: FileVisitOption): PathStream =
    Files.walk(this, maxDepth, *options)!!

/**
 * Returns a [Stream] that is lazily populated with [Path] by searching for files in a file tree rooted at this [Path].
 *
 * This method walks the file tree in exactly the manner specified by
 * the [walk] method. For each file encountered, the given
 * [BiPredicate] is invoked with its [Path] and
 * [BasicFileAttributes]. The [Path] object is obtained as if by
 * [resolving(Path)][Path.resolve] the relative path against
 * [this] and is only included in the returned [Stream] if
 * the [BiPredicate] returns true. Compare to calling
 * [filter][java.util.stream.Stream.filter] on the [Stream]
 * returned by [walk] method, this method may be more efficient by
 * avoiding redundant retrieval of the [BasicFileAttributes].
 *
 * The returned stream encapsulates one or more [DirectoryStream]s.
 * If timely disposal of file system resources is required, the
 * `try`-with-resources construct should be used to ensure that the
 * stream's [close][Stream.close] method is invoked after the stream
 * operations are completed.  Operating on a closed stream will result in an
 * [java.lang.IllegalStateException].
 *
 * If an [IOException] is thrown when accessing the directory
 * after returned from this method, it is wrapped in an
 * [UncheckedIOException] which will be thrown from the method that caused
 * the access to take place.
 *
 * @param maxDepth The maximum number of directory levels to search. **(Set to [Int.MAX_VALUE] by default.)**
 * @param matcher The function used to decide whether a file should be included in the returned stream.
 * @param options Options to configure the traversal.
 *
 * @throws IllegalArgumentException If [maxDepth] is negative.
 * @throws  SecurityException
 *          If the security manager denies access to the starting file.
 *          In the case of the default provider, the
 *          [checkRead(String)][SecurityManager.checkRead] method is invoked
 *          to check read access to the directory.
 * @throws IOException If an I/O error is thrown when accessing the starting file.
 *
 * @see walk
 */
public fun Path.find(maxDepth: Int = Int.MAX_VALUE, matcher: PathMatcher, vararg options: FileVisitOption) =
    Files.find(this, maxDepth, matcher, *options)!!

/**
 * Read all lines from a file as a [Stream].
 *
 * Unlike [readAllLines(Path, Charset)][readAllLines], this method does not read all lines into a [List], but instead
 * populates lazily as the stream is consumed.
 *
 * Bytes from the file are decoded into characters using the specified
 * charset and the same line terminators as specified by
 * [readAllLines] are supported.
 *
 * After this method returns, then any subsequent I/O exception that
 * occurs while reading from the file or when a malformed or unmappable byte
 * sequence is read, is wrapped in an [UncheckedIOException] that will
 * be thrown from the
 * [java.util.stream.Stream] method that caused the read to take
 * place. In case an [IOException] is thrown when closing the file,
 * it is also wrapped as an `UncheckedIOException`.
 *
 * The returned stream encapsulates a [Reader].  If timely
 * disposal of file system resources is required, the try-with-resources
 * construct should be used to ensure that the stream's
 * [close][Stream.close] method is invoked after the stream operations
 * are completed.
 *
 * @return The lines from the file as a [Stream].
 *
 * @throws  IOException
 *          if an I/O error occurs opening the file
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to the file.
 *
 * @see readAllLines
 * @see newBufferedReader
 * @see java.io.BufferedReader.lines
 */
public val Path.lines: Stream<String> get() = Files.lines(this)

/**
 * Read all lines from a file as a [Stream].
 *
 * Unlike [readAllLines(Path, Charset)][readAllLines], this method does not read all lines into a [List], but instead
 * populates lazily as the stream is consumed.
 *
 * Bytes from the file are decoded into characters using the specified
 * charset and the same line terminators as specified by
 * [readAllLines] are supported.
 *
 * After this method returns, then any subsequent I/O exception that
 * occurs while reading from the file or when a malformed or unmappable byte
 * sequence is read, is wrapped in an [UncheckedIOException] that will
 * be thrown from the
 * [java.util.stream.Stream] method that caused the read to take
 * place. In case an [IOException] is thrown when closing the file,
 * it is also wrapped as an `UncheckedIOException`.
 *
 * The returned stream encapsulates a [Reader].  If timely
 * disposal of file system resources is required, the try-with-resources
 * construct should be used to ensure that the stream's
 * [close][Stream.close] method is invoked after the stream operations
 * are completed.
 *
 * @param charset The charset to use for decoding. *(Set to [UTF_8][StandardCharsets.UTF_8] by default)*
 *
 * @return  the lines from the file as a `Stream`
 *
 * @throws  IOException
 *          if an I/O error occurs opening the file
 * @throws  SecurityException
 *          In the case of the default provider, and a security manager is
 *          installed, the [checkRead(String)][SecurityManager.checkRead]
 *          method is invoked to check read access to the file.
 *
 * @see readAllLines
 * @see newBufferedReader
 * @see java.io.BufferedReader.lines
 */
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