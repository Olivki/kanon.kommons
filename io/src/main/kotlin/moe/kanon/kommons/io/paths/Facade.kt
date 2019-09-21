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

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import java.io.UncheckedIOException
import java.io.Writer
import java.nio.channels.ByteChannel
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.CopyOption
import java.nio.file.DirectoryIteratorException
import java.nio.file.DirectoryNotEmptyException
import java.nio.file.DirectoryStream
import java.nio.file.FileAlreadyExistsException
import java.nio.file.FileStore
import java.nio.file.FileSystem
import java.nio.file.FileSystemLoopException
import java.nio.file.FileSystems
import java.nio.file.FileVisitOption
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.LinkPermission
import java.nio.file.NoSuchFileException
import java.nio.file.NotDirectoryException
import java.nio.file.NotLinkException
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.SecureDirectoryStream
import java.nio.file.SimpleFileVisitor
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import java.nio.file.StandardOpenOption.CREATE
import java.nio.file.StandardOpenOption.READ
import java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
import java.nio.file.StandardOpenOption.WRITE
import java.nio.file.attribute.BasicFileAttributeView
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.DosFileAttributes
import java.nio.file.attribute.FileAttribute
import java.nio.file.attribute.FileAttributeView
import java.nio.file.attribute.FileOwnerAttributeView
import java.nio.file.attribute.FileStoreAttributeView
import java.nio.file.attribute.FileTime
import java.nio.file.attribute.PosixFileAttributeView
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.UserPrincipal
import java.util.EnumSet
import java.util.stream.Stream
import kotlin.streams.asSequence

/**
 * Generally used in conjunction with [walkFileTree] or similar functions to make the syntax clearer.
 */
typealias SimplePathVisitor = SimpleFileVisitor<Path>

/**
 * Generally used in conjunction with [walkFileTree] or similar functions to make the syntax clearer.
 */
typealias PathVisitor = FileVisitor<Path>

/**
 * Opens `this` [file][Path], returning an input stream to read from the file.
 *
 * The stream will not be buffered, and is not required to support the [mark][InputStream.mark] or
 * [reset][InputStream.reset] methods. The stream will be safe for access by multiple concurrent threads. Reading
 * commences at the beginning of the file. Whether the returned stream is *asynchronously closeable* and/or
 * *interruptible* is highly file system provider specific and therefore not specified.
 *
 * The [options] parameter determines how the file is opened. If no options are present then it is equivalent to
 * opening the file with the [READ][StandardOpenOption.READ] option. In addition to the `READ` option, an
 * implementation may also support additional implementation specific options.
 *
 * @receiver the path to the file to open
 *
 * @throws IllegalArgumentException if an invalid combination of options is specified.
 * @throws UnsupportedOperationException if an unsupported option is specified.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the file.
 */
fun Path.newInputStream(vararg options: OpenOption = arrayOf(READ)): InputStream =
    Files.newInputStream(this, *options)

/**
 * Opens *(or creates)* `this` [file][Path], returning an output stream that may be used to write bytes to the file.
 *
 * The resulting stream will not be buffered. The stream will be safe for access by multiple concurrent threads.
 * Whether the returned stream is *asynchronously closeable* and/or *interruptible* is highly file system provider
 * specific and therefore not specified.
 *
 * This method opens or creates `this` [file][Path] in exactly the manner specified by the [newByteChannel] method with
 * the exception that the [READ][StandardOpenOption.READ] option may not be present in the array of options. If no
 * options are present then this method works as if the [CREATE][StandardOpenOption.CREATE],
 * [TRUNCATE_EXISTING][StandardOpenOption.TRUNCATE_EXISTING], and [WRITE][StandardOpenOption.WRITE] options are present.
 *
 * In other words, it opens the file for writing, creating the file if it doesn't exist, or initially truncating an
 * existing [regular-file][isRegularFile] to a size of `0` if it exists.
 *
 * **Usage Examples:**
 *
 * ```kotlin
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
 * @receiver the path to the file to open or create
 *
 * @throws IllegalArgumentException if an invalid combination of options is specified.
 * @throws UnsupportedOperationException if an unsupported option is specified.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the file. The
 * [checkDelete(String)][SecurityManager.checkDelete] method is invoked to check delete access if the file is opened
 * with the `DELETE_ON_CLOSE` option.
 */
fun Path.newOutputStream(vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)): OutputStream =
    Files.newOutputStream(this, *options)

/**
 * Opens or creates `this` [file][Path], returning a seekable byte channel to access `this` file.
 *
 * The [options] parameter determines how the file is opened. The [READ][StandardOpenOption.READ] and
 * [WRITE][StandardOpenOption.WRITE] options determine if the file should be opened for reading and/or writing.
 * If neither option (or the [APPEND][StandardOpenOption.APPEND] option) is present then the file is opened for reading.
 * By default reading or writing commence at the beginning of the file.
 *
 * An implementation may also support additional implementation specific options.
 *
 * The [attributes] parameter is optional [file-attributes][FileAttribute] to set atomically when a new file is created.
 *
 * In the case of the default provider, the returned seekable byte channel is a
 * [FileChannel][java.nio.channels.FileChannel].
 *
 * **Usage Examples:**
 *
 * ```kotlin
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
 * @receiver the path to the file to open or create
 *
 * @throws IllegalArgumentException if the [options] set contains an invalid combination of options.
 * @throws UnsupportedOperationException if an unsupported open option is specified or the array contains attributes
 * that cannot be set atomically when creating the file.
 * @throws FileAlreadyExistsException if `this` [file][Path] of that name already exists and the
 * [CREATE_NEW][StandardOpenOption.CREATE_NEW] option is specified. *(optional specific exception)*.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the path if the file is
 * opened for reading. The [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to
 * the path if the file is opened for writing. The [checkDelete(String)][SecurityManager.checkDelete] method is invoked
 * to check delete access if the file is opened with the `DELETE_ON_CLOSE` option.
 *
 * @see java.nio.channels.FileChannel.open
 */
fun Path.newByteChannel(options: Set<OpenOption> = EnumSet.of(READ), vararg attributes: FileAttribute<*>): ByteChannel =
    Files.newByteChannel(this, options.toMutableSet(), *attributes)

/**
 * Opens *(or creates)* `this` [file][Path], returning a seekable byte channel to access the file.
 *
 * This method opens or creates `this` [file][Path] in exactly the manner specified by the
 * [newByteChannel(options, vararg attributes)][Path.newByteChannel] method.
 *
 * @throws IllegalArgumentException if the set contains an invalid combination of options.
 * @throws UnsupportedOperationException if an unsupported open option is specified.
 * @throws FileAlreadyExistsException if `this` [file][Path] of that name already exists and the
 * [CREATE_NEW][StandardOpenOption.CREATE_NEW] option is specified. *(optional specific exception)*.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the path if the file is
 * opened for reading. The [checkWrite(String)][SecurityManager.checkWrite]  method is invoked to check write access to
 * the path if the file is opened for writing. The [checkDelete(String)][SecurityManager.checkDelete] method is invoked
 * to check delete access if the file is opened with the `DELETE_ON_CLOSE` option.
 *
 * @see java.nio.channels.FileChannel.open
 */
fun Path.newByteChannel(vararg options: OpenOption = arrayOf(READ)): ByteChannel = Files.newByteChannel(this, *options)

/**
 * A [DirectoryStream Filter][DirectoryStream.Filter] that accepts *all* path instances.
 */
object AcceptAllFilter : DirectoryStream.Filter<Path> {
    override fun accept(entry: Path?): Boolean = true
}

/**
 * Opens this [directory][Path], returning a [DirectoryStream] to iterate over the entries in the directory.
 *
 * The elements returned by the directory stream's [iterators][DirectoryStream] are of type  [Path], each one
 * representing an entry in the directory. The [Path] objects are obtained as if by [resolving][Path.resolve] the
 * name of the directory entry against this [directory][Path]. The entries returned by the iterators are filtered by
 * the given [filter][DirectoryStream.Filter].
 *
 * When not using the try-with-resources construct, then directory stream's [close][DirectoryStream.close] method
 * should be invoked after iteration is completed so as to free any resources held for the open directory.
 *
 * Where the filter terminates due to an uncaught error or runtime exception then it is propagated to the
 * [hasNext][Iterator.hasNext] or [next][Iterator.next] method. Where an [IOException] is thrown, it results in the
 * `hasNext` or `next` method throwing a [DirectoryIteratorException] with the `IOException` as the cause.
 *
 * When an implementation supports operations on entries in the directory that execute in a race-free manner then the
 * returned directory  stream is a [SecureDirectoryStream].
 *
 * **Usage Example:**
 *
 * Suppose we want to iterate over the files in a directory that are larger than 8k.
 *
 * ```kotlin
 *      val filter = PathDirectoryFilter { it.size > 8192L }
 *      val dir: Path = ...
 *      dir.newDirectoryStream(filter).use { ... }
 * ```
 *
 * @receiver the path to the directory
 *
 * @return a new and open [DirectoryStream] instance.
 *
 * @param filter the directory stream filter.
 *
 * ([AcceptAllFilter] by default.)
 *
 * @throws NotDirectoryException if `this` file could not otherwise be opened because it is not a directory. *(optional
 * specific exception)*.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the directory.
 */
@JvmOverloads
fun Path.newDirectoryStream(filter: DirectoryStream.Filter<Path> = AcceptAllFilter): DirectoryStream<Path> =
    Files.newDirectoryStream(this, filter)

/**
 * Opens this [directory][Path], returning a [DirectoryStream] to iterate over the entries in the directory.
 *
 * The elements returned by the directory stream's [iterators][DirectoryStream] are of type [Path], each one
 * representing an entry in the directory. The [Path] objects are obtained as if by [resolving][Path.resolve] the
 * name of the directory entry against [this]. The entries returned by the iterators are filtered by matching the
 * [String] representation  of their file names against the given [globbing][glob] pattern.
 *
 * **Usage Example:**
 *
 * Suppose we want to iterate over the files ending with ".java" in a directory:
 *
 * ```kotlin
 *      val dir: Path = ...
 *      dir.newDirectoryStream("*.java").use { ... }
 * ```
 *
 * The globbing pattern is specified by the [getPathMatcher][FileSystem.getPathMatcher] function.
 *
 * When not using the `try-with-resources` construct, then directory stream's [close][DirectoryStream] method should be
 * invoked after iteration is completed so as to free any resources held for the open directory.
 *
 * When an implementation supports operations on entries in the directory that execute in a race-free manner then the
 * returned directory stream is a [SecureDirectoryStream].
 *
 * @return a new and open [DirectoryStream] instance
 *
 * @throws java.util.regex.PatternSyntaxException if the pattern is invalid.
 * @throws NotDirectoryException if the file could not otherwise be opened because it is not a directory. *(optional
 * specific exception)*.
 * @throws IOException if an I/O error occurs
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] function is invoked to check read access to the directory.
 */
fun Path.newDirectoryStream(glob: String): DirectoryStream<Path> = Files.newDirectoryStream(this, glob)

/**
 * Creates a new and empty file, failing if the file already exists.
 *
 * The check for the existence of the file and the creation of the new file if it does not exist are a single operation
 * that is atomic with respect to all other filesystem activities that might affect the directory.
 *
 * The [attributes] parameter is optional [file-attributes][FileAttribute] to set atomically when creating the file.
 * Each attribute is identified by its [name][FileAttribute.name]. If more than one attribute of the same name is
 * included in the array then all but the last occurrence is ignored.
 *
 * @receiver the file to create
 *
 * @return the newly created file
 *
 * @throws UnsupportedOperationException if the array contains an attribute that cannot be set atomically when creating
 * the file
 * @throws FileAlreadyExistsException if `this` [file][Path] of that name already exists. *(optional specific exception)*.
 * @throws IOException if an I/O error occurs or the parent directory does not exist
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the new file
 */
fun Path.createFile(vararg attributes: FileAttribute<*>): Path = Files.createFile(this, *attributes)

/**
 * Creates a new directory.
 *
 * The check for the existence of the file and the creation of the directory if it does not exist are a single
 * operation that is atomic with respect to all other filesystem activities that might affect the directory.
 *
 * The [createDirectories] method should be used where it is required to create all nonexistent parent directories
 * first.
 *
 * The [attributes] parameter is optional [file-attributes][FileAttribute] to set atomically when creating the
 * directory. Each attribute is identified by its [name][FileAttribute.name]. If more than one attribute of the same
 * name is included in the array then all but the last occurrence is ignored.
 *
 * @receiver the directory to create
 *
 * @return the newly created directory
 *
 * @throws UnsupportedOperationException if the array contains an attribute that cannot be set atomically
 * when creating the directory
 * @throws FileAlreadyExistsException if a directory could not otherwise be created because `this` [file][Path] of
 * that name already exists *(optional specific exception)*
 * @throws IOException if an I/O error occurs or the parent directory does not exist
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the new directory
 */
fun Path.createDirectory(vararg attributes: FileAttribute<*>): Path = Files.createDirectory(this, *attributes)

/**
 * Creates a directory by creating all nonexistent parent directories first.
 *
 * Unlike the [createDirectory][Path.createChildDirectory] method, an exception  is not thrown if the directory could not be
 * created because it already exists.
 *
 * The [attributes] parameter is optional [file-attributes][FileAttribute] to set atomically when creating the
 * nonexistent directories. Each file attribute is identified by its [name][FileAttribute.name]. If more than one
 * attribute of the same name is included in the array then all but the last occurrence is ignored.
 *
 * If this method fails, then it may do so after creating some, but not all, of the parent directories.
 *
 * @receiver the directory to create.
 *
 * @return the newly created directory that didn't exist before this function being called.
 *
 * @throws  UnsupportedOperationException if the array contains an attribute that cannot be set atomically when
 * creating the directory.
 * @throws  FileAlreadyExistsException if `this` [file][Path] exists but is not a directory *(optional specific
 * exception)*
 * @throws  IOException if an I/O error occurs.
 * @throws  SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked prior to attempting to create a directory and
 * its [checkRead(String)][SecurityManager.checkRead] is invoked for each parent directory that is checked. If
 * `this` [file][Path] is not an absolute path then its [toAbsolutePath][Path.toAbsolutePath] may need to be invoked to
 * get its absolute path. This may invoke the security manager's
 * [checkPropertyAccess(String)][SecurityManager.checkPropertyAccess] method to check access to the system property
 * `user.dir`.
 */
fun Path.createDirectories(vararg attributes: FileAttribute<*>): Path = Files.createDirectories(this, *attributes)

/**
 * Creates a new empty file in `this` [directory][Path], using the given [prefix] and [suffix] strings to generate its
 * name.
 *
 * The resulting [Path] is associated with the same [FileSystem] as the given directory.
 *
 * The details as to how the name of the file is constructed is implementation dependent and therefore not specified.
 * Where possible the [prefix] and [suffix] are used to construct candidate names in the same manner as the
 * [createTempFile(String, String, File)][java.io.File.createTempFile] method.
 *
 * As with the [File.createTempFile] methods, this method is only part of a temporary-file facility. Where used as a
 * *work files*, the resulting file may be opened using the [DELETE_ON_CLOSE][StandardOpenOption.DELETE_ON_CLOSE]
 * option so that the file is deleted when the appropriate `close` method is invoked. Alternatively, a
 * [shutdown-hook][Runtime.addShutdownHook], or the [deleteOnExit][java.io.File.deleteOnExit] mechanism may be used to
 * delete the file automatically.
 *
 * The [attributes] parameter is optional [file-attributes][FileAttribute] to set atomically when creating the file.
 * Each attribute is identified by its [name][FileAttribute.name]. If more than one attribute of the same name is
 * included in the array then all but the last occurrence is ignored. When no file attributes are specified, then the
 * resulting file may have more restrictive access permissions to files created by the
 * [createTempFile(String, String, File)][java.io.File.createTempFile] method.
 *
 * @receiver The path to directory in which to create the file.
 *
 * @param prefix the prefix string to be used in generating the file's name; may be `null`.
 *
 * (`null` by default)
 * @param suffix the suffix string to be used in generating the file's name; may be `null`, in which case "`.tmp`"
 * is used.
 *
 * (`null` by default)
 * @param attributes an optional list of file attributes to set atomically when creating the file.
 *
 * @return the `path` to the newly created file that did not exist before this method was invoked.
 *
 * @throws IllegalArgumentException if the prefix or suffix parameters cannot be used to generate a candidate file name.
 * @throws UnsupportedOperationException if the array contains an attribute that cannot be set atomically when
 * creating the directory.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the file.
 *
 * @see Files.createTempFile
 */
@JvmOverloads
fun Path.createTempFile(
    prefix: String? = null,
    suffix: String? = null,
    vararg attributes: FileAttribute<*>
): Path = Files.createTempFile(this, prefix, suffix, *attributes)

/**
 * Creates an empty file in the default temporary-file directory, using the given [prefix] and [suffix] to generate
 * its name. The resulting [Path] is associated with the [default][FileSystems.getDefault] [FileSystem].
 *
 * This method works in exactly the manner specified by the
 * [createTempFile(String?, String?, vararg FileAttribute][Path.createTempFile] function for the case that the
 * `receiver` is the temporary-file directory.
 *
 * @param prefix the prefix string to be used in generating the file's name; may be `null`
 *
 * (`null` by default)
 * @param suffix the suffix string to be used in generating the file's name; may be `null`, in which case "`.tmp`"
 * is used
 *
 * (`null` by default)
 * @param attributes an optional list of file attributes to set atomically when creating the file
 *
 * @return the `path` to the newly created file that did not exist before this method was invoked
 *
 * @throws IllegalArgumentException if the prefix or suffix parameters cannot be used to generate a candidate file name
 * @throws UnsupportedOperationException if the array contains an attribute that cannot be set atomically when
 * creating the directory
 * @throws IOException if an I/O error occurs
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the file
 */
@JvmOverloads
fun createTmpFile(
    prefix: String? = null,
    suffix: String? = null,
    vararg attributes: FileAttribute<*>
): Path = Files.createTempFile(prefix, suffix, *attributes)

/**
 * Creates a new directory in this [directory][Path], using the given [name] to generate its name.
 *
 * The resulting [Path] is associated with the same [FileSystem] as the given directory.
 *
 * The details as to how the name of the directory is constructed is implementation dependent and therefore not
 * specified. Where possible the [name] parameter is used to construct candidate names.
 *
 * As with the [createTempFile][Path.createTempFile] methods, this method is only part of a temporary-file facility. A
 * [shutdown-hook][Runtime.addShutdownHook], or the [deleteOnExit][java.io.File.deleteOnExit] mechanism may be used to
 * delete the directory automatically.
 *
 * The [attributes] parameter is optional [file-attributes][FileAttribute] to set atomically when creating the
 * directory. Each attribute is identified by its [name][FileAttribute.name]. If more than one attribute of the same
 * name is included in the array then all but the last occurrence is ignored.
 *
 * @param name the name string to be used in generating the directory's name; may be `null`.
 *
 * (`null` by default)
 * @param attributes An optional list of file attributes to set atomically when creating the directory.
 *
 * @return The path to the newly created directory that did not exist before this method was invoked.
 *
 * @throws IllegalArgumentException if the prefix cannot be used to generate a candidate directory name.
 * @throws UnsupportedOperationException if the array contains an attribute that cannot be set atomically when
 * creating the directory
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access when creating the directory.
 *
 * @see Files.createTempDirectory
 */
@JvmOverloads
fun Path.createTempDirectory(name: String? = null, vararg attributes: FileAttribute<*>): Path =
    Files.createTempDirectory(this, name, *attributes)

/**
 * Creates a new directory in the default temporary-file directory, using the given [name] to generate its name. The
 * resulting [Path] is associated with the [default][FileSystems.getDefault] [FileSystem].
 *
 * This function works in exactly the manner specified by
 * [createTempDirectory(String, vararg FileAttribute][Path.createTempDirectory] function for the case that the `Path`
 * `receiver` is the temporary-file directory.
 *
 * @param name the name string to be used in generating the directory's name; may be `null`.
 *
 * (`null` by default)
 * @param attributes an optional list of file attributes to set atomically when creating the directory
 *
 * @return the path to the newly created directory that did not exist before this method was invoked
 *
 * @throws IllegalArgumentException if the prefix cannot be used to generate a candidate directory name
 * @throws UnsupportedOperationException if the array contains an attribute that cannot be set atomically when creating
 * the directory
 * @throws IOException if an I/O error occurs or the temporary-file directory does not exist
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access when creating the
 * directory
 */
@JvmOverloads
fun createTmpDirectory(name: String? = null, vararg attributes: FileAttribute<*>): Path =
    Files.createTempDirectory(name, *attributes)

/**
 * Creates a symbolic link to the [target]. *(optional operation)*.
 *
 * The [target] parameter is the target of the link. It may be an [absolute][Path.isAbsolute] or relative path and may
 * not exist. When the target is a relative path then file system operations on the resulting link are relative to the
 * path of the link.
 *
 * The [attributes] parameter is optional [attributes][FileAttribute] to set atomically when creating the link. Each
 * attribute is identified by its [name][FileAttribute.name]. If more than one attribute of the same name is included
 * in the array then all but the last occurrence is ignored.
 *
 * Where symbolic links are supported, but the underlying [FileStore] does not support symbolic links, then this may
 * fail with an [IOException]. Additionally, some operating systems may require that the Java virtual machine be
 * started with implementation specific privileges to create symbolic links, in which case this method may throw
 * [IOException].
 *
 * @return the path to the symbolic link.
 *
 * @throws UnsupportedOperationException if the implementation does not support symbolic links or the array contains an
 * attribute that cannot be set atomically when creating the symbolic link
 * @throws FileAlreadyExistsException If `this` [file][Path] with the name already exists. *(optional specific exception)*
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, it denies
 * [LinkPermission]`("symbolic")` or its [checkWrite(String)][SecurityManager.checkWrite] method denies write access
 * to the path of the symbolic link.
 */
fun Path.createSymbolicLinkTo(target: Path, vararg attributes: FileAttribute<*>): Path =
    Files.createSymbolicLink(this, target, *attributes)

/**
 * Attempts to read this [path][Path] as the target of a symbolic link. *(optional operation)*.
 *
 * If the file system supports `symbolic links` then this method is used to read the target of the link, failing
 * if the file is not a symbolic link. The target of the link need not exist. The returned [Path] object will be
 * associated with the same file system as this [path][Path].
 *
 * @return a [Path] object representing the target of the link
 *
 * @throws UnsupportedOperationException if the implementation does not support symbolic links.
 * @throws NotLinkException If the target could otherwise not be read because the file is not a symbolic link .
 * *(optional specific exception)*
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, it checks that
 * `FilePermission` has been  granted with the "`readlink`" action to read the link.
 */
fun Path.readSymbolicLink(): Path = Files.readSymbolicLink(this)

/**
 * Creates a new link (directory entry) for an existing file. *(optional operation)*.
 *
 * This [file][Path] is where the link will be created.
 *
 * The [existing] parameter is the path to an existing file. This method creates a new directory entry for the file so
 * that it can be accessed using `this` [file][Path] as the path. On some file systems this is known as creating a
 * "hard link". Whether the file attributes are maintained for the file or for each directory entry is file system
 * specific and therefore not specified. Typically, `this` [file][Path] system requires  that all links
 * (directory entries) for `this` [file][Path] be on the same file system. Furthermore, on some platforms, the Java
 * virtual machine may require to be started with implementation specific privileges to create hard links or to create
 * links to directories.
 *
 * @return The path to the link (directory entry).
 *
 * @throws UnsupportedOperationException if the implementation does not support adding an existing file to a directory.
 * @throws FileAlreadyExistsException If the entry could not otherwise be created because `this` [file][Path] of that
 * name already exists. *(optional specific exception)*
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, it denies
 * [LinkPermission]`("hard")` or its [checkWrite(String)][SecurityManager.checkWrite] method denies write access
 * to either the link or the existing file.
 */
fun Path.createLinkTo(existing: Path): Path = Files.createLink(this, existing)

/**
 * Deletes `this` [file][Path].
 *
 * An implementation may require to examine the file to determine if the file is a directory. Consequently this method
 * may not be atomic with respect to other file system operations.  If the file is a symbolic link then the symbolic
 * link itself, not the final target of the link, is deleted.
 *
 * If the file is a directory then the directory must be empty. In some implementations a directory has entries for
 * special files or links that are created when the directory is created. In such implementations a  directory is
 * considered empty when only the special entries exist. This method can be used with the
 * [walkFileTree][Path.walkFileTree] method to delete a directory and all entries in the directory, or an entire
 * *file-tree* where required.
 *
 * On some operating systems it may not be possible to remove `this` [file][Path] when it is open and in use by this Java
 * virtual machine or other programs.
 *
 * @throws NoSuchFileException If the file does not exist. *(optional specific exception)*.
 * @throws DirectoryNotEmptyException If the file is a directory and could not otherwise be deleted because the
 * directory is not empty. *(optional specific exception)*
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkDelete(String)][SecurityManager.checkDelete] method is invoked to check delete access to the file.
 */
fun Path.delete() = Files.delete(this)

/**
 * Deletes `this` [file][Path] if it exists.
 *
 * As with the [delete(Path)][Path.delete] method, an implementation may need to examine the file to determine if the
 * file is a directory. Consequently this method may not be atomic with respect to other file system operations.  If
 * the file is a symbolic link, then the symbolic link itself, not the final target of the link, is deleted.
 *
 * If the file is a directory then the directory must be empty. In some implementations a directory has entries for
 * special files or links that are created when the directory is created. In such implementations a directory is
 * considered empty when only the special entries exist.
 *
 * On some operating systems it may not be possible to remove `this` [file][Path] when it is open and in use by this Java
 * virtual machine or other programs.
 *
 * @return `true` if `this` [file][Path] was deleted by this method; `false` if `this` [file][Path] could not be deleted
 * because it did not exist.
 *
 * @throws DirectoryNotEmptyException If the file is a directory and could not otherwise be deleted because the
 * directory is not empty. *(optional specific exception)*
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkDelete(String)][SecurityManager.checkDelete] method is invoked to check delete access to the file.
 */
fun Path.deleteIfExists(): Boolean = Files.deleteIfExists(this)

/**
 * Moves `this` [file][Path] to the [target] file.
 *
 * By default, this method attempts to move the file to the target file, failing if the target file exists except if
 * the source and target are the [same][Path.isSameAs] file, in which case this method has no effect. If the file is
 * a symbolic link then the symbolic link  itself, not the target of the link, is moved. This method may be invoked to
 * move an empty directory. In some implementations a directory has entries for special files or links that are created
 * when the directory is created. In such implementations a directory is considered  empty when only the special
 * entries exist. When invoked to move a directory that is not empty then the directory is moved if it does not require
 * moving the entries in the directory.  For example, renaming a directory on the same [FileStore] will usually not
 * require moving the entries in the directory. When moving a directory requires that its entries be moved then this
 * method fails (by throwing an [IOException]). To move a *file tree* may involve copying rather than moving
 * directories and this can be done using the [walkFileTree] method in conjunction with the [copyTo] utility method.
 *
 * An implementation of this interface may support additional implementation specific options.
 *
 * Moving `this` [file][Path] will copy the [last-modified-time][BasicFileAttributes.lastModifiedTime] to the target
 * file if supported by both source and target file stores. Copying of file timestamps may result in precision loss.
 * An implementation may also  attempt to copy other file attributes but is not required to fail if the file attributes
 * cannot be copied. When the move is performed as a non-atomic operation, and an [IOException] is thrown, then the
 * state of the files is not defined. The original file and the target file may both exist, the target file may be
 * incomplete or some of its file attributes may not been copied from the original file.
 *
 * **Usage Examples:**
 *
 * Suppose we want to rename `this` [file][Path] to "newname", keeping the file in the same directory:
 *
 * ```kotlin
 *     val source: Path = ...
 *     source.name = "newname"
 *     // or if you want to apply additional options
 *     source.renameTo("newname", ...)
 * ```
 *
 * And if we *just* want to change the name of the file to "newname" and *not* change the extension any:
 *
 * ```kotlin
 *      val path: Path = ...
 *      source.simpleName = "newname".
 * ```
 *
 * Alternatively, suppose we want to move `this` [file][Path] to new directory, keeping the same file name, and replacing
 * any existing file of that name in the directory:
 *
 * ```kotlin
 *     val source: Path = ...
 *     val newDir: Path = ...
 *     source.moveTo(newDir, keepName = true, StandardCopyOptions.REPLACE_EXISTING)
 * ```
 *
 * @param [target] the path to the target file. *(may be associated with a different provider to the source path)*
 * @param [keepName] whether or not the name of `this` file should be kept when moving it
 * @param [options] options specifying how the move should be done.
 *
 * @return the path to the target file.
 *
 * @throws UnsupportedOperationException if the array contains a copy option that is not supported.
 * @throws FileAlreadyExistsException If the target file exists but cannot be replaced because the `REPLACE_EXISTING`
 * option is not specified. *(optional specific exception)*
 * @throws DirectoryNotEmptyException the `REPLACE_EXISTING` option is specified but the file cannot be replaced
 * because it is a non-empty directory. *(optional specific exception)*
 * @throws AtomicMoveNotSupportedException If the options array contains the `ATOMIC_MOVE` option but the file cannot
 * be moved as an atomic file system operation.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to both the source and
 * target file.
 */
fun Path.moveTo(target: Path, keepName: Boolean, vararg options: CopyOption): Path =
    Files.move(this, if (keepName) target.resolve(this.name) else target, *options)

/**
 * Moves `this` [file][Path] to the [target] file.
 *
 * By default, this method attempts to move the file to the target file, failing if the target file exists except if
 * the source and target are the [same][Path.isSameAs] file, in which case this method has no effect. If the file is
 * a symbolic link then the symbolic link  itself, not the target of the link, is moved. This method may be invoked to
 * move an empty directory. In some implementations a directory has entries for special files or links that are created
 * when the directory is created. In such implementations a directory is considered  empty when only the special
 * entries exist. When invoked to move a directory that is not empty then the directory is moved if it does not require
 * moving the entries in the directory.  For example, renaming a directory on the same [FileStore] will usually not
 * require moving the entries in the directory. When moving a directory requires that its entries be moved then this
 * method fails (by throwing an [IOException]). To move a *file tree* may involve copying rather than moving
 * directories and this can be done using the [walkFileTree] method in conjunction with the [copyTo] utility method.
 *
 * An implementation of this interface may support additional implementation specific options.
 *
 * Moving `this` [file][Path] will copy the [last-modified-time][BasicFileAttributes.lastModifiedTime] to the target
 * file if supported by both source and target file stores. Copying of file timestamps may result in precision loss.
 * An implementation may also  attempt to copy other file attributes but is not required to fail if the file attributes
 * cannot be copied. When the move is performed as a non-atomic operation, and an [IOException] is thrown, then the
 * state of the files is not defined. The original file and the target file may both exist, the target file may be
 * incomplete or some of its file attributes may not been copied from the original file.
 *
 * **Usage Examples:**
 *
 * Suppose we want to rename `this` [file][Path] to "newname", keeping the file in the same directory:
 *
 * ```kotlin
 *     val source: Path = ...
 *     source.name = "newname"
 *     // or if you want to apply additional options
 *     source.renameTo("newname", ...)
 * ```
 *
 * And if we *just* want to change the name of the file to "newname" and *not* change the extension any:
 *
 * ```kotlin
 *      val path: Path = ...
 *      source.simpleName = "newname".
 * ```
 *
 * Alternatively, suppose we want to move `this` [file][Path] to new directory, keeping the same file name, and replacing
 * any existing file of that name in the directory:
 *
 * ```kotlin
 *     val source: Path = ...
 *     val newDir: Path = ...
 *     source.moveTo(newDir, keepName = true, StandardCopyOptions.REPLACE_EXISTING)
 * ```
 *
 * @param target The path to the target file. *(may be associated with a different provider to the source path)*
 * @param options options specifying how the move should be done.
 *
 * @return The path to the target file.
 *
 * @throws UnsupportedOperationException if the array contains a copy option that is not supported.
 * @throws FileAlreadyExistsException If the target file exists but cannot be replaced because the `REPLACE_EXISTING`
 * option is not specified. *(optional specific exception)*
 * @throws DirectoryNotEmptyException the `REPLACE_EXISTING` option is specified but the file cannot be replaced
 * because it is a non-empty directory. *(optional specific exception)*
 * @throws AtomicMoveNotSupportedException If the options array contains the `ATOMIC_MOVE` option but the file cannot
 * be moved as an atomic file system operation.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to both the source and
 * target file.
 */
fun Path.moveTo(target: Path, vararg options: CopyOption): Path = moveTo(target, false, *options)

/**
 * Copies `this` [file][Path] to the [target] file.
 *
 * This method copies `this` [file][Path] to the target file with the [options] parameter specifying how the copy is
 * performed. By default, the copy fails if the target file already exists or is a symbolic link, except if the source
 * and target are the [same][Path.isSameAs] file, in which case the method completes without copying the file. File
 * attributes are not required to be copied to the target file. If symbolic links are supported, and the file is a
 * symbolic link, then the final target of the link is copied. If the file is a directory then it creates an empty
 * directory in the target location (entries in the directory are not copied). This method can be used with the
 * [walkFileTree][Path.walkFileTree] method to copy a directory and all entries in the directory, or an entire
 * *file-tree* where required.
 *
 *
 * An implementation of this interface may support additional implementation specific options.
 *
 * Copying `this` [file][Path] is not an atomic operation. If an [IOException] is thrown, then it is possible that the
 * target file is incomplete or some of its file attributes have not been copied from the source file. When the
 * `REPLACE_EXISTING` option is specified and the target file exists, then the target file is replaced. The check for
 * the existence of the file and the creation of the new file may not be atomic with respect to other file system
 * activities.
 *
 * **Usage Example:**
 *
 * Suppose we want to copy `this` [file][Path] into a directory, giving it the same file name as the source file:
 *
 * ```kotlin
 *     val source: Path = ...
 *     val newDir: Path = ...
 *     source.copyTo(target = newDir, keepName = true)
 * ```
 *
 * @param target the path to the target file. *(May be associated with a different provider to the source path)*
 * @param keepName whether or not `this` [file][Path] should keep its original name after being copied over to [target]
 * @param options options specifying how the copy should be done.
 *
 * @return the path to the target file
 *
 * @throws UnsupportedOperationException if the array contains a copy option that is not supported
 * @throws FileAlreadyExistsException if the target file exists but cannot be replaced because the REPLACE_EXISTING`
 * option is not specified. *(optional specific exception)*
 * @throws DirectoryNotEmptyException the `REPLACE_EXISTING` option is specified but the file cannot be replaced
 * because it is a non-empty directory. *(optional specific exception)*
 * @throws IOException if an I/O error occurs
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the source file, the
 * [checkWrite(String)][SecurityManager.checkWrite] is invoked to check write access to the target file. If a symbolic
 * link is copied the security manager is invoked to check [LinkPermission]`("symbolic")`
 */
fun Path.copyTo(target: Path, keepName: Boolean, vararg options: CopyOption): Path =
    Files.copy(this, if (keepName) target.resolve(this.name) else target, *options)

/**
 * Copies `this` [file][Path] to the [target] file.
 *
 * This method copies `this` [file][Path] to the target file with the [options] parameter specifying how the copy is
 * performed. By default, the copy fails if the target file already exists or is a symbolic link, except if the source
 * and target are the [same][Path.isSameAs] file, in which case the method completes without copying the file. File
 * attributes are not required to be copied to the target file. If symbolic links are supported, and the file is a
 * symbolic link, then the final target of the link is copied. If the file is a directory then it creates an empty
 * directory in the target location (entries in the directory are not copied). This method can be used with the
 * [walkFileTree][Path.walkFileTree] method to copy a directory and all entries in the directory, or an entire
 * *file-tree* where required.
 *
 *
 * An implementation of this interface may support additional implementation specific options.
 *
 * Copying `this` [file][Path] is not an atomic operation. If an [IOException] is thrown, then it is possible that the
 * target file is incomplete or some of its file attributes have not been copied from the source file. When the
 * `REPLACE_EXISTING` option is specified and the target file exists, then the target file is replaced. The check for
 * the existence of the file and the creation of the new file may not be atomic with respect to other file system
 * activities.
 *
 * **Usage Example:**
 *
 * Suppose we want to copy `this` [file][Path] into a directory, giving it the same file name as the source file:
 *
 * ```kotlin
 *     val source: Path = ...
 *     val newDir: Path = ...
 *     source.copyTo(target = newDir, keepName = true)
 * ```
 *
 * @param target The path to the target file. *(May be associated with a different provider to the source path)*
 * @param options options specifying how the copy should be done.
 *
 * @return The path to the target file.
 *
 * @throws UnsupportedOperationException if the array contains a copy option that is not supported.
 * @throws FileAlreadyExistsException if the target file exists but cannot be replaced because the REPLACE_EXISTING`
 * option is not specified. *(optional specific exception)*
 * @throws DirectoryNotEmptyException the `REPLACE_EXISTING` option is specified but the file cannot be replaced
 * because it is a non-empty directory. *(optional specific exception)*
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the source file, the
 * [checkWrite(String)][SecurityManager.checkWrite] is invoked to check write access to the target file. If a symbolic
 * link is copied the security manager is invoked to check [LinkPermission]`("symbolic")`.
 */
fun Path.copyTo(target: Path, vararg options: CopyOption): Path = this.copyTo(target, false, *options)

/**
 * Attempts to rename `this` [file][Path] to the given [name].
 *
 * @see Path.name
 */
fun Path.renameTo(name: String, vararg options: CopyOption): Path =
    moveTo(this.resolveSibling(name), options = *options)

/**
 * Returns the [FileStore] representing the file store where `this` [file][Path] is located.
 *
 * Once a reference to the [FileStore] is obtained it is implementation specific if operations on the returned
 * [FileStore], or [FileStoreAttributeView] objects obtained from it, continue to depend on the existence of the file.
 * In particular the behavior is not defined for the case that the file is deleted or moved to a different file store.
 *
 * @return the file store where the file is stored.
 *
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the file, and in addition
 * it checks [RuntimePermission]` ("getFileStoreAttributes")`
 */
inline val Path.fileStore: FileStore get() = Files.getFileStore(this)

/**
 * Tests if two paths locate the same file.
 *
 * If both [Path] objects are [equal(Object)][Path.equals] then this method returns `true` without checking if the
 * file exists. If the two [Path] objects are associated with different providers then this method returns `false`.
 * Otherwise, this method checks if both [Path] objects locate the same file, and depending on the implementation, may
 * require to open or access both files.
 *
 * If the file system and files remain static, then this method implements an equivalence relation for non-null
 * [Paths][Path].
 *
 * - It is *reflexive*: for [Path] `f`, `f isSameAs f` should return `true`.
 * - It is *symmetric*: for two [Paths][Path] `f` and `g`, `f isSameAs g` will equal `g isSameAs f`.
 * - It is *transitive*: for three [Paths][Path] `f`, `g`, and `h`, if `f isSameAs g` returns `true` and
 * `g isSameAs h` returns `true`, then `f isSameAs h` will return also `true`.
 *
 * @return `true` if, and only if, the two paths locate the same file.
 *
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file. If this method is invoked to
 * read security sensitive attributes then the security manager may be invoke to check for additional permissions
 *
 * @see java.nio.file.attribute.BasicFileAttributes.fileKey
 */
infix fun Path.isSameAs(other: Path): Boolean = Files.isSameFile(this, other)

/**
 * Tells whether or not `this` [file][Path] is considered *hidden*.
 *
 * The exact definition of hidden is platform or provider dependent. On UNIX for example `this` [file][Path] is
 * considered to be hidden if its name begins with a period character ('.'). On Windows `this` [file][Path] is considered
 * hidden if it isn't a directory and the DOS [hidden][DosFileAttributes.isHidden] attribute is set.
 *
 * Depending on the implementation this method may require to access the file system to determine if the file is
 * considered hidden.
 *
 * @return `true` if the file is considered hidden.
 *
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file. If this method is invoked to
 * read security sensitive attributes then the security manager may be invoke to check for additional permissions
 */
val Path.isHidden: Boolean get() = Files.isHidden(this)

/**
 * Probes the content type of `this` [file][Path].
 *
 * This method uses the installed [java.nio.file.spi.FileTypeDetector] implementations to probe the given file to
 * determine its content type. Each file type detector's
 * [probeContentType][java.nio.file.spi.FileTypeDetector.probeContentType] is invoked, in turn, to probe the file type.
 * If the file is recognized then the content type is returned. If the file is not recognized by any of the installed
 * file type detectors then a system-default file type detector is invoked to guess the content type.
 *
 * A given invocation of the Java virtual machine maintains a system-wide list of file type detectors. Installed file
 * type detectors are loaded using the service-provider loading facility defined by the [java.util.ServiceLoader] class.
 * Installed  file type detectors are loaded using the system class loader. If the system class loader cannot be found
 * then the extension class loader is used; If the extension class loader cannot be found then the bootstrap class
 * loader is used. File type detectors are typically installed by placing them in a JAR file on the application class
 * path or in the extension directory, the JAR file contains a provider-configuration file named
 * [java.nio.file.spi.FileTypeDetector] in the resource directory `META-INF/services`, and the file lists one or more
 * fully-qualified names of concrete subclass of [FileTypeDetector][java.nio.file.spi.FileTypeDetector] that have a zero
 * argument constructor. If the process of locating or instantiating the installed file type detectors fails then an
 * unspecified error is thrown. The ordering that installed providers are located is implementation specific.
 *
 * The return value of this method is the string form of the value of a Multipurpose Internet Mail Extension (MIME)
 * content type as defined by *[RFC 2045: Multipurpose Internet Mail Extensions (MIME) Part One: Format of Internet Message Bodies](http://www.ietf.org/rfc/rfc2045.txt)*.
 * The string is guaranteed to be parsable according to the grammar in the RFC.
 *
 * @return the content type of the file, or `null` if the content type cannot be determined.
 *
 * @throws IOException if an I/O error occurs
 * @throws SecurityException if a security manager is installed and it denies an unspecified permission required by
 * `this` [file][Path] type detector implementation.
 */
val Path.contentType: String? get() = Files.probeContentType(this)

/**
 * Returns `this` [file][Path] attribute view of a given type.
 *
 * A file attribute view provides a read-only or updatable view of a set of file attributes. This method is intended to
 * be used where the file attribute view defines type-safe methods to read or update the file attributes. The [type]
 * parameter is the type of the attribute view required and the method returns an instance of that type if supported.
 * The [BasicFileAttributeView] type supports access to the basic attributes of `this` [file][Path]. Invoking this method
 * to select this [file's][Path] attribute view of that type will always return an instance of that class.
 *
 * The [options] array may be used to indicate how symbolic links are handled for the case that the file is a symbolic
 * link. By default, symbolic links are followed and the file attribute of the final target  of the link is set. If the
 * option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * **Usage Example:**
 *
 * Suppose we want read or set `this` [file][Path]'s ACL, if supported:
 *
 * ```kotlin
 *     val path: Path = ...
 *     val view = path.getFileAttributeView(AclFileAttributeView.class)
 *     if (view != null) {
 *         val acl = view.acl
 *         ...
 *     }
 * ```
 *
 * @param T the [FileAttributeView] type
 * @param type the [Class] object corresponding to the file attribute view.
 * @param options options indicating how symbolic links are handled.
 *
 * @return This [file's][Path] attribute view of the specified type, or `null` if the attribute view type is not
 * available.
 */
fun <T : FileAttributeView> Path.getFileAttributeView(type: Class<T>, vararg options: LinkOption): T? =
    Files.getFileAttributeView(this, type, *options)

/**
 * Returns `this` [file][Path] attribute view of a given type.
 *
 * A file attribute view provides a read-only or updatable view of a set of file attributes. This method is intended to
 * be used where the file attribute view defines type-safe methods to read or update the file attributes. The [type]
 * parameter is the type of the attribute view required and the method returns an instance of that type if supported.
 * The [BasicFileAttributeView] type supports access to the basic attributes of `this` [file][Path]. Invoking this method
 * to select this [file's][Path] attribute view of that type will always return an instance of that class.
 *
 * The [options] array may be used to indicate how symbolic links are handled for the case that the file is a symbolic
 * link. By default, symbolic links are followed and the file attribute of the final target  of the link is set. If the
 * option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * **Usage Example:**
 *
 * Suppose we want read or set `this` [file][Path]'s ACL, if supported:
 *
 * ```kotlin
 *     val path: Path = ...
 *     val view = path.getFileAttributeView<AclFileAttributeView>()
 *     if (view != null) {
 *         val acl = view.acl
 *         ...
 *     }
 * ```
 *
 * @param T the [FileAttributeView] type
 * @param options options indicating how symbolic links are handled.
 *
 * @return This [file's][Path] attribute view of the specified type, or `null` if the attribute view type is not
 * available.
 */
inline fun <reified T : FileAttributeView> Path.getFileAttributeView(vararg options: LinkOption): T? =
    Files.getFileAttributeView(this, T::class.java, *options)

/**
 * Reads `this` [file][Path]'s attributes as a bulk operation.
 *
 * The [type] parameter is the type of the attributes required and this method returns an instance of that type if
 * supported. All implementations support a basic set of file attributes and so invoking this method with a [type]
 * parameter of `BasicFileAttributes.class` will not throw [UnsupportedOperationException].
 *
 * The [options] array may be used to indicate how symbolic links are handled for the case that the file is a symbolic
 * link. By default, symbolic links are followed and the file attribute of the final target  of the link is set. If the
 * option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * It is implementation specific if all file attributes are read as an atomic operation with respect to other file
 * system operations.
 *
 * **Usage Example:**
 *
 * Suppose we want to read `this` [file][Path]'s attributes in bulk:
 *
 * ```kotlin
 *    val path: Path = ...
 *    val attrs = path.readAttributes(BasicFileAttributes.class)
 * ```
 *
 * Alternatively, suppose we want to read file's POSIX attributes without following symbolic links:
 *
 * ```kotlin
 *    val attrs = path.readAttributes(PosixFileAttributes.class, NOFOLLOW_LINKS)
 * ```
 *
 * @param T the [BasicFileAttributes] type.
 * @param type the [Class] of the file attributes required to read.
 * @param options options indicating how symbolic links are handled.
 *
 * @return the file attributes.
 *
 * @throws UnsupportedOperationException if an attributes of the given type are not supported.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file. If this method is invoked to
 * read security sensitive attributes then the security manager may be invoke to check for additional permissions.
 *
 * @see attributes
 */
fun <T : BasicFileAttributes> Path.readAttributes(type: Class<T>, vararg options: LinkOption): T? =
    Files.readAttributes(this, type, *options)

/**
 * Reads `this` [file][Path]'s attributes as a bulk operation.
 *
 * The [type] parameter is the type of the attributes required and this method returns an instance of that type if
 * supported. All implementations support a basic set of file attributes and so invoking this method with a [type]
 * parameter of `BasicFileAttributes.class` will not throw [UnsupportedOperationException].
 *
 * The [options] array may be used to indicate how symbolic links are handled for the case that the file is a symbolic
 * link. By default, symbolic links are followed and the file attribute of the final target  of the link is set. If the
 * option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * It is implementation specific if all file attributes are read as an atomic operation with respect to other file
 * system operations.
 *
 * **Usage Example:**
 *
 * Suppose we want to read `this` [file][Path]'s attributes in bulk:
 *
 * ```kotlin
 *    val path: Path = ...
 *    val attrs = path.readAttributes<BasicFileAttributes>()
 * ```
 *
 * Alternatively, suppose we want to read file's POSIX attributes without following symbolic links:
 *
 * ```kotlin
 *    val attrs = path.readAttributes<PosixFileAttributes>(NOFOLLOW_LINKS)
 * ```
 *
 * @param T the [BasicFileAttributes] type.
 * @param type the [Class] of the file attributes required to read.
 * @param options options indicating how symbolic links are handled.
 *
 * @return the file attributes.
 *
 * @throws UnsupportedOperationException if an attributes of the given type are not supported.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file. If this method is invoked to
 * read security sensitive attributes then the security manager may be invoke to check for additional permissions.
 *
 * @see attributes
 */
inline fun <reified T : BasicFileAttributes> Path.readAttributes(vararg options: LinkOption): T? =
    Files.readAttributes(this, T::class.java, *options)

/**
 * Reads a set of file attributes from `this` [file][Path] as a bulk operation.
 *
 * The [attributes] parameter identifies the attributes to be read and takes the form:
 *
 * > `[view-name:]attribute-name`
 *
 * where square brackets `[...]` delineate an optional component and the character `':'` stands for itself.
 *
 * *view-name* is the [name][FileAttributeView.name] of a [FileAttributeView] that identifies a set of file attributes.
 * If not specified then it defaults to `"basic"`, the name of the file attribute view that identifies the basic set of
 * file attributes common to many file systems. *attribute-name* is the name of the attribute within the set.
 *
 * The *attribute-list* component is a comma separated list of zero or more names of attributes to read. If the list
 * contains the value `"*"` then all attributes are read. Attributes that are not supported are ignored and will not
 * be present in the returned map. It is implementation specific if all attributes are read as an atomic operation
 * with respect to other file system operations.
 *
 * The [options] array may be used to indicate how symbolic links are handled for the case that the file is a symbolic
 * link. By default, symbolic links are followed and the file attribute of the final target  of the link is set. If the
 * option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * @param attributes the attributes to read.
 * @param options options indicating how symbolic links are handled.
 *
 * @return a map of the attributes returned; The map's keys are the attribute names, its values are the attribute
 * values.
 *
 * @throws UnsupportedOperationException if the attribute view is not available.
 * @throws IllegalArgumentException if no attributes are specified or an unrecognized attributes is specified.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file. If this method is invoked to
 * read security sensitive attributes then the security manager may be invoke to check for additional permissions.
 *
 * @see attributes
 */
fun Path.readAttributes(attributes: String, vararg options: LinkOption): Map<String, Any> =
    Files.readAttributes(this, attributes, *options)

/**
 * Sets the value of this [file's][Path] attribute.
 *
 * The [attribute] parameter identifies the attribute to be set and takes the form:
 *
 * > `[view-name:]attribute-name`
 *
 * where square brackets `[...]` delineate an optional component and the character `':'` stands for itself.
 *
 * *view-name* is the [name][FileAttributeView.name] of a [FileAttributeView] that identifies a set of file attributes.
 * If not specified then it defaults to `"basic"`, the name of the file attribute view that identifies the basic set of
 * file attributes common to many file systems. *attribute-name* is the name of the attribute within the set.
 *
 * The [options] array may be used to indicate how symbolic links are handled for the case that the file is a symbolic
 * link. By default, symbolic links are followed and the file attribute of the final target  of the link is set. If the
 * option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * **Usage Example:**
 *
 * Suppose we want to set the DOS "hidden" attribute:
 *
 * ```kotlin
 *    val path: Path = ...
 *    path.setAttribute("dos:hidden", true)
 * ```
 *
 * *or*
 *
 * ```kotlin
 *    val path: Path = ...
 *    path.attributes["dos:hidden"] = true
 * ```
 *
 * @param attribute the attribute to set.
 * @param value the attribute value.
 * @param options options indicating how symbolic links are handled.
 *
 * @return `this` [file][Path].
 *
 * @throws UnsupportedOperationException if the attribute view is not available.
 * @throws IllegalArgumentException if the attribute name is not specified, or is not recognized, or the attribute
 * value is of the correct type but has an inappropriate value.
 * @throws ClassCastException if the attribute value is not of the expected type or is a collection containing elements
 * that are not of the expected type.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkWrite(String)][SecurityManager.checkWrite] method denies write access to the file. If this method is invoked
 * to set security sensitive attributes then the security manager may be invoked to check for additional permissions.
 *
 * @see attributes
 */
fun Path.setAttribute(attribute: String, value: Any?, vararg options: LinkOption): Path =
    Files.setAttribute(this, attribute, value, *options)

/**
 * Reads the value of `this` [file][Path] attribute.
 *
 * The [attributes] parameter identifies the attribute to be read and takes the form:
 *
 * > `[view-name:]attribute-name`
 *
 * where square brackets `[...]` delineate an optional component and the character `':'` stands for itself.
 *
 * *view-name* is the [name][FileAttributeView.name] of a [FileAttributeView] that identifies a set of file attributes.
 * If not specified then it defaults to `"basic"`, the name of the file attribute view that identifies the basic set of
 * file attributes common to many file systems. *attribute-name* is the name of the attribute.
 *
 * The [options] array may be used to indicate how symbolic links are handled for the case that the file is a symbolic
 * link. By default, symbolic links are followed and the file attribute of the final target of the link is read. If the
 * option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * **Usage Example:**
 *
 * Suppose we require the user ID of the file owner on a system that supports a "`unix`" view:
 *
 * ```kotlin
 *    val path: Path = ...
 *    val uid = path.getAttribute<Int>("unix:uid")
 * ```
 *
 * *or*
 *
 * ```kotlin
 *    val path: Path = ...
 *    val uid = path.attributes["unix:uid"] as Int
 * ```
 *
 * @param attribute the attribute to read.
 * @param options options indicating how symbolic links are handled.
 *
 * @return the attribute value.
 *
 * @throws UnsupportedOperationException if the attribute view is not available.
 * @throws IllegalArgumentException if the attribute name is not specified or is not recognized.
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file. If this method is invoked to
 * read security sensitive attributes then the security manager may be invoked to check for additional permissions.
 *
 * @see attributes
 */
@Suppress("UNCHECKED_CAST")
fun <T> Path.getAttribute(attribute: String, vararg options: LinkOption): T =
    Files.getAttribute(this, attribute, *options) as T

/**
 * Returns this [file's][Path] POSIX file permissions.
 *
 * The [Path] parameter is associated with a [FileSystem] that supports the [PosixFileAttributeView]. This attribute
 * view provides access to file attributes commonly associated with files on file systems used by operating systems
 * that implement the Portable Operating System Interface (POSIX) family of standards.
 *
 * The [options] array may be used to indicate how symbolic links are handled for the case that the file is a symbolic
 * link. By default, symbolic links are followed and the file attribute of the final target of the link is read. If the
 * option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * @param options options indicating how symbolic links are handled.
 *
 * @return the file permissions.
 *
 * @throws UnsupportedOperationException if the associated file system does not support the [PosixFileAttributeView].
 * @throws IOException if an I/O error occurs
 * @throws SecurityException in the case of the default provider, a security manager is installed, and it denies
 * [RuntimePermission]`("accessUserInformation")` or its [checkRead(String)][SecurityManager.checkRead] method
 *  denies read access to the file.
 */
fun Path.getPosixFilePermissions(vararg options: LinkOption): Set<PosixFilePermission> =
    Files.getPosixFilePermissions(this, *options)

/**
 * A property variant of [getPosixFilePermissions] and [Files.setPosixFilePermissions].
 *
 * Use this one if you don't need to use any of the additional LinkOptions from [getPosixFilePermissions].
 *
 * @see Files.setPosixFilePermissions
 */
var Path.permissions: Set<PosixFilePermission>
    get() = this.getPosixFilePermissions()
    set(perms) {
        Files.setPosixFilePermissions(this, perms)
    }

/**
 * Returns the owner of `this` [file][Path].
 *
 * The [Path] parameter is associated with a file system that supports [FileOwnerAttributeView]. This file attribute
 * view provides access to a file attribute that is the owner of `this` [file][Path].
 *
 * @param options options indicating how symbolic links are handled.
 *
 * @return a user principal representing the owner of the file.
 *
 * @throws UnsupportedOperationException if the associated file system does not support the [FileAttributeView].
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, it denies
 * [RuntimePermission]`("accessUserInformation")` or its [checkRead(String)][SecurityManager.checkRead]
 * method denies read access to the file.
 */
fun Path.getOwner(vararg options: LinkOption): UserPrincipal = Files.getOwner(this, *options)

/**
 * Represents the current owner of `this` [file][Path].
 *
 * The receiver `path` is associated with a file system that supports [FileOwnerAttributeView]. This file attribute
 * view provides access to a file attribute that is the owner of `this` [file][Path].
 *
 * @see Files.setOwner
 */
var Path.owner: UserPrincipal
    get() = this.getOwner()
    set(newOwner) {
        Files.setOwner(this, newOwner)
    }

/**
 * Tests whether `this` [file][Path] is a symbolic link.
 *
 * Where it is required to distinguish an I/O exception from the case that his [file][Path] is not a symbolic link then
 * the file attributes can be read with the [readAttributes(Path,Class,LinkOption[])][readAttributes] method and the
 * file type tested with the [BasicFileAttributes.isSymbolicLink] method.
 *
 * @return `true` if his [file][Path] is a symbolic link; `false` if `this` [file][Path] does not exist, is not a
 * symbolic link, or it cannot be determined if his [file][Path] is a symbolic link or not.
 *
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file.
 */
val Path.isSymbolicLink: Boolean get() = Files.isSymbolicLink(this)

/**
 * Tests whether `this` [file][Path] is a directory.
 *
 * **Use the property variant of this unless you need to set specific [LinkOptions][LinkOption].**
 *
 * The [options] array may be used to indicate how symbolic links are handled for the case that the file is a symbolic
 * link. By default, symbolic links are followed and the file attribute of the final target of the link is read. If the
 * option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * Where it is required to distinguish an I/O exception from the case that `this` [file][Path] is not a directory then
 * the file attributes can be read with the [readAttributes(Path, Class, vararg LinkOption)][readAttributes] method and
 * the file type tested with the [BasicFileAttributes.isDirectory] method.
 *
 * @param options options indicating how symbolic links are handled.
 *
 * @return `true` if `this` [file][Path] is a directory; `false` if `this` [file][Path] does not exist, is not a directory,
 * or it cannot be  determined if `this` [file][Path] is a directory or not.
 *
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file.
 */
fun Path.isDirectory(vararg options: LinkOption): Boolean = Files.isDirectory(this, *options)

/**
 * Tests whether `this` [file][Path] is a directory.
 *
 * Where it is required to distinguish an I/O exception from the case that `this` [file][Path] is not a directory then
 * the file attributes can be read with the [readAttributes(Path, Class, vararg LinkOption)][readAttributes] method and
 * the file type tested with the [BasicFileAttributes.isDirectory] method.
 *
 * @return `true` if `this` [file][Path] is a directory; `false` if `this` [file][Path] does not exist, is not a directory,
 * or it cannot be  determined if `this` [file][Path] is a directory or not.
 *
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file.
 */
val Path.isDirectory: Boolean get() = this.isDirectory()

/**
 * Tests whether `this` [file][Path] is a regular file with opaque content.
 *
 * The [options] array may be used to indicate how symbolic links are handled for the case that `this` [file][Path] is a
 * symbolic link. By default, symbolic links are followed and the file attribute of the final target of the link is
 * read. If the option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * Where it is required to distinguish an I/O exception from the case that `this` [file][Path] is not a regular file then
 * the file attributes can be read with the [readAttributes(Path, Class, vararg LinkOption)][readAttributes] method and
 * the file type tested with the [BasicFileAttributes.isRegularFile] method.
 *
 * @param options options indicating how symbolic links are handled.
 *
 * @return `true` if `this` [file][Path] is a regular file; `false` if `this` [file][Path] does not exist, is not a regular
 * file, or it cannot be determined if `this` [file][Path] is a regular file or not.
 *
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file.
 */
fun Path.isRegularFile(vararg options: LinkOption): Boolean = Files.isRegularFile(this, *options)

/**
 * Tests whether `this` [file][Path] is a regular file with opaque content.
 *
 * Where it is required to distinguish an I/O exception from the case that `this` [file][Path] is not a regular file then
 * the file attributes can be read with the [readAttributes(Path, Class, vararg LinkOption)][readAttributes] method and
 * the file type tested with the [BasicFileAttributes.isRegularFile] method.
 *
 * @return `true` if `this` [file][Path] is a regular file; `false` if `this` [file][Path] does not exist, is not a regular
 * file, or it cannot be determined if `this` [file][Path] is a regular file or not.
 *
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file.
 */
val Path.isRegularFile: Boolean get() = this.isRegularFile()

/**
 * Returns `this` [file's][Path] last modified time.
 *
 * The [options] array may be used to indicate how symbolic links are handled for the case that the file is a symbolic
 * link. By default, symbolic links are followed and the file attribute of the final target of the link is read. If the
 * option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is present then symbolic links are not followed.
 *
 * @param options options indicating how symbolic links are handled
 *
 * @return A [FileTime] representing the time the file was last modified, or an implementation specific default when a
 * time stamp to indicate the time of last modification is not supported by the file system
 *
 * @throws IOException if an I/O error occurs
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file.
 *
 * @see BasicFileAttributes.lastModifiedTime
 */
fun Path.getLastModifiedTime(vararg options: LinkOption): FileTime = Files.getLastModifiedTime(this, *options)

/**
 * A property variant of [getLastModifiedTime] and [Files.setLastModifiedTime].
 *
 * Use this one if you don't need to use any of the additional LinkOptions from [getLastModifiedTime].
 *
 * @see Files.setPosixFilePermissions
 */
var Path.lastModifiedTime: FileTime
    get() = this.getLastModifiedTime()
    set(value) {
        Files.setLastModifiedTime(this, value)
    }

/**
 * Returns the size of `this` [file][Path] (in bytes).
 *
 * The size may differ from the actual size on the file system due to compression, support for sparse files, or other
 * reasons. The size of files that are not [regular][isRegularFile] files is implementation specific and therefore
 * unspecified.
 *
 * @return the file size, in bytes.
 *
 * @throws IOException if an I/O error occurs.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, its
 * [checkRead(String)][SecurityManager.checkRead] method denies read access to the file.
 *
 * @see BasicFileAttributes.size
 */
val Path.size: Long get() = Files.size(this)

/**
 * Tests whether `this` [file][Path] exists.
 *
 * The [options] parameter may be used to indicate how symbolic links are handled for the case that the file is a
 * symbolic link. By default, symbolic links are followed. If the option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is
 * present then symbolic links are not followed.
 *
 * Note that the result of this method is immediately outdated. If this  method indicates the file exists then there is
 * no guarantee that a subsequent access will succeed. Care should be taken when using this method in security sensitive
 * applications.
 *
 * @param options options indicating how symbolic links are handled.
 *
 * @return`true` if `this` [file][Path] exists; `false` if `this` [file][Path] does not exist or its existence cannot be
 * determined.
 *
 * @throws SecurityException in the case of the default provider, the [checkRead(String)][SecurityManager.checkRead] is
 * invoked to check read access to the file.
 *
 * @see notExists
 */
fun Path.exists(vararg options: LinkOption): Boolean = Files.exists(this, *options)

/**
 * Tests whether `this` [file][Path] exists.
 *
 * Note that the result of this method is immediately outdated. If this  method indicates the file exists then there is
 * no guarantee that a subsequent access will succeed. Care should be taken when using this method in security sensitive
 * applications.
 *
 * @return `true` if `this` [file][Path] exists; `false` if `this` [file][Path] does not exist or its existence cannot be
 * determined.
 *
 * @throws SecurityException in the case of the default provider, the [checkRead(String)][SecurityManager.checkRead] is
 * invoked to check read access to the file.
 *
 * @see notExists
 */
inline val Path.exists: Boolean @JvmName("exists") get() = this.exists()

/**
 * Tests whether `this` [file][Path] does not exist.
 *
 * This method is intended for cases where it is required to take action when it can be confirmed that this
 * [file][Path] does not exist.
 *
 * The [options] parameter may be used to indicate how symbolic links are handled for the case that the file is a
 * symbolic link. By default, symbolic links are followed. If the option [NOFOLLOW_LINKS][LinkOption.NOFOLLOW_LINKS] is
 * present then symbolic links are not followed.
 *
 * Note that this method is not the complement of the [exists][Path.exists] method. Where it is not possible to
 * determine if `this` [file][Path] exists or not then both methods return `false`. As with the `exists` method, the
 * result of this method is immediately outdated. If this method indicates the file does exist then there is no
 * guarantee that a subsequent attempt to create the file will succeed. Care should be taken when using this method in
 * security sensitive applications.
 *
 * @return `true` if `this` [file][Path] does not exist; `false` if `this` [file][Path] exists or its existence cannot be
 * determined.
 *
 * @throws SecurityException in the case of the default provider, the [checkRead(String)][SecurityManager.checkRead] is
 * invoked to check read access to the file.
 */
fun Path.notExists(vararg options: LinkOption): Boolean = Files.notExists(this, *options)

/**
 * Tests whether `this` [file][Path] does not exist.
 *
 * This method is intended for cases where it is required to take action when it can be confirmed that this
 * [file][Path] does not exist.
 *
 * Note that this method is not the complement of the [exists][Path.exists] method. Where it is not possible to
 * determine if `this` [file][Path] exists or not then both methods return `false`. As with the `exists` method, the
 * result of this method is immediately outdated. If this method indicates the file does exist then there is no
 * guarantee that a subsequent attempt to create the file will succeed. Care should be taken when using this method in
 * security sensitive applications.
 *
 * @return `true` if `this` [file][Path] does not exist; `false` if `this` [file][Path] exists or its existence cannot be
 * determined.
 *
 * @throws SecurityException in the case of the default provider, the [checkRead(String)][SecurityManager.checkRead] is
 * invoked to check read access to the file.
 */
val Path.notExists: Boolean @JvmName("notExists") get() = this.notExists()

/**
 * Tests whether `this` [file][Path] is readable.
 *
 * This method checks that `this` [file][Path] exists and that this Java virtual machine has appropriate privileges that
 * would allow it open the file for reading. Depending on the implementation, this method may require to read file
 * permissions, access control lists, or other file attributes in order to check the effective access to the file.
 * Consequently, this method may not be atomic with respect to other file system operations.
 *
 * Note that the result of this method is immediately outdated, there is no guarantee that a subsequent attempt to open
 * the file for reading will succeed *(or even that it will access the same file)*. Care should be taken when using this
 * method in security sensitive applications.
 *
 * @return `true` if the file exists and is readable; `false` if the file does not exist, read access would be denied
 * because the Java virtual machine has insufficient privileges, or access cannot be determined
 *
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] is invoked to check read access to the file.
 */
val Path.isReadable: Boolean get() = Files.isReadable(this)

/**
 * A property variant of [Files.isWritable] and a custom setter.
 *
 * When setting this to `true` or `false`, the appropriate permissions of the file will be updated.
 *
 * @throws SecurityException in the case of the default provider, and a security manager is installed,
 * the [checkWrite(String)][SecurityManager.checkWrite] is invoked to check write access to the file.
 *
 * @see Files.isWritable
 * @see Path.permissions
 */
var Path.isWritable: Boolean
    get() = Files.isWritable(this)
    set(writable) {
        if (writable) {
            val perms = permissions.toMutableSet()

            permissions = perms.apply {
                remove(PosixFilePermission.OWNER_WRITE)
                remove(PosixFilePermission.GROUP_WRITE)
                remove(PosixFilePermission.OTHERS_WRITE)
            }
        } else {
            val perms = permissions.toMutableSet()

            permissions = perms.apply {
                add(PosixFilePermission.OWNER_WRITE)
                add(PosixFilePermission.GROUP_WRITE)
                add(PosixFilePermission.OTHERS_WRITE)
            }
        }
    }

/**
 * Tests whether `this` [file][Path] is executable.
 *
 * This method checks that `this` [file][Path] exists and that this Java virtual machine has appropriate privileges to
 * [execute][Runtime.exec] the file. The semantics may differ when checking access to a directory. For example, on UNIX
 * systems, checking for execute access checks that the Java virtual machine has permission to search the directory in
 * order to access file or subdirectories.
 *
 * Depending on the implementation, this method may require to read file permissions, access control lists, or other
 * file attributes in order to check the effective access to the file. Consequently, this method may not be atomic with
 * respect to other file system operations.
 *
 * Note that the result of this method is immediately outdated, there is no guarantee that a subsequent attempt to
 * execute the file will succeed *(or even that it will access the same file)*. Care should be taken when using this
 * method in security sensitive applications.
 *
 * @return `true` if the file exists and is executable; `false` if the file does not exist, execute access would be
 * denied because the Java virtual machine has insufficient privileges, or access cannot be determined
 *
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkExec(String)][SecurityManager.checkExec] is invoked to check execute access to the file.
 */
val Path.isExecutable: Boolean get() = Files.isExecutable(this)

/**
 * Opens `this` [file][Path] for reading, returning a [BufferedReader] that may be used to read text from the file in an
 * efficient manner.
 *
 * Bytes from the file are decoded into characters using the specified [charset].
 *
 * Reading commences at the beginning of the file.
 *
 * The [Reader] methods that read from the file throw [IOException] if a malformed or unmappable byte sequence is read.
 *
 * @param charset the charset to use for decoding.
 *
 * ([UTF_8][StandardCharsets.UTF_8] by default)
 *
 * @return A new buffered reader, with default buffer size, to read text from the file.
 *
 * @throws IOException if an I/O error occurs opening the file.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the file.
 *
 * @see Path.readLines
 */
@JvmOverloads
fun Path.newBufferedReader(charset: Charset = StandardCharsets.UTF_8): BufferedReader =
    Files.newBufferedReader(this, charset)

/**
 * Opens *(or creates it if it does not exist yet)* `this` [file][Path] for writing, returning a [BufferedWriter] that
 * may be used to write text to the file in an efficient manner.
 *
 * The [options] parameter specifies how the the file is created or opened. If no options are present then this method
 * works as if the [TRUNCATE_EXISTING][StandardOpenOption.TRUNCATE_EXISTING], [CREATE][StandardOpenOption.CREATE], and
 * [WRITE][StandardOpenOption.WRITE] options are present.
 *
 * In other words, it opens the file for writing, creating the file if it doesn't exist, or initially truncating an
 * existing [regular-file][Path.isRegularFile] to a size of `0` if it exists.
 *
 * The [Writer] methods to write text throw [IOException] if the text cannot be encoded using the specified charset.
 *
 * @param charset the charset to use for encoding.
 * @param options options specifying how the file is opened
 *
 * @return a new [BufferedWriter], with default buffer size, to write text to the file.
 *
 * @throws IOException if an I/O error occurs opening or creating the file.
 * @throws UnsupportedOperationException if an unsupported option is specified
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite]  method is invoked to check write access to the file.
 *
 * @see Path.writeBytes
 */
fun Path.newBufferedWriter(
    charset: Charset,
    vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)
): BufferedWriter = Files.newBufferedWriter(this, charset, *options)

/**
 * Opens *(or creates it if it does not exist yet)* `this` [file][Path] for writing, returning a [BufferedWriter] that
 * may be used to write text to the file in an efficient manner.
 *
 * The [options] parameter specifies how the the file is created or opened. If no options are present then this method
 * works as if the [TRUNCATE_EXISTING][StandardOpenOption.TRUNCATE_EXISTING], [CREATE][StandardOpenOption.CREATE], and
 * [WRITE][StandardOpenOption.WRITE] options are present.
 *
 * In other words, it opens the file for writing, creating the file if it doesn't exist, or initially truncating an
 * existing [regular-file][Path.isRegularFile] to a size of `0` if it exists.
 *
 * The [Writer] methods to write text throw [IOException] if the text cannot be encoded using the specified charset.
 *
 * @param options options specifying how the file is opened
 *
 * @return a new [BufferedWriter], with default buffer size, to write text to the file.
 *
 * @throws IOException if an I/O error occurs opening or creating the file.
 * @throws UnsupportedOperationException if an unsupported option is specified
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite]  method is invoked to check write access to the file.
 *
 * @see Path.writeBytes
 */
fun Path.newBufferedWriter(vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)): BufferedWriter =
    Files.newBufferedWriter(this, StandardCharsets.UTF_8, *options)

/**
 * Copies all bytes from an input stream to `this` [file][Path].
 *
 * On return, the input stream will be at end of the stream.
 *
 * By default, the copy fails if the target file already exists or is a symbolic link. If the
 * [REPLACE_EXISTING][StandardCopyOption.REPLACE_EXISTING] option is specified, and the target file already exists,
 * then it is replaced if it is not a non-empty directory. If the target file exists and is a symbolic link, then the
 * symbolic link is replaced. In this release, the `REPLACE_EXISTING` option is the only option required to be
 * supported by this method. Additional options may be  supported in future releases.
 *
 * If an I/O error occurs reading from the input stream or writing to this file, then it may do so after the target
 * file has been created and after some bytes have been read or written. Consequently the input stream may not be at
 * end of stream and may be in an inconsistent state. It is strongly recommended that the input stream be promptly
 * closed if an I/O error occurs.
 *
 * This method may block indefinitely reading from the [inputStream] *(or writing to the file)*. The behavior for the
 * case that the input stream is *asynchronously closed* or the thread interrupted during the copy is highly input
 * stream and file system provider specific and therefore not specified.
 *
 * **Usage example**:
 *
 * Suppose we want to capture a web page and save it to this a file:
 *
 * ```kotlin
 *     val path: Path = ...
 *     val uri = URI.create("http://java.sun.com/")
 *     uri.toURL().openStream().use { path.transform(it) }
 * ```
 *
 * @param inputStream the [InputStream] to read from.
 * @param options options specifying how the copy should be done.
 *
 * @return the number of bytes read or written.
 *
 * @throws IOException if an I/O error occurs when reading or writing
 * @throws FileAlreadyExistsException if the target file exists but cannot be replaced because the `REPLACE_EXISTING`
 * option is not specified *(optional specific exception)*
 * @throws DirectoryNotEmptyException the `REPLACE_EXISTING` option is specified but the file cannot be replaced
 * because it is a non-empty directory *(optional specific exception)*
 * @throws UnsupportedOperationException if [options] contains a copy option that is not supported.
 * @throws SecurityException in the case of the default provider, and a security manager is installed,
 * the [checkWrite(String)][SecurityManager.checkWrite]  method is invoked to check write access to the file. Where the
 * `REPLACE_EXISTING` option is specified, the security manager's [checkDelete(String)][SecurityManager.checkDelete]
 * method is invoked to check that an existing file can be deleted.
 */
fun Path.createFrom(inputStream: InputStream, vararg options: CopyOption): Long =
    Files.copy(inputStream, this, *options)

/**
 * Copies all bytes from `this` [file][Path] to the [outputStream].
 *
 * If an I/O error occurs reading from the file or writing to the output stream, then it may do so after some bytes
 * have been read or written. Consequently the output stream may be in an inconsistent state. It is strongly
 * recommended that the output stream be promptly closed if an I/O error occurs.
 *
 * This method may block indefinitely writing to the output stream *(or reading from the file)*.
 * The behavior for the case that the output stream is *asynchronously closed* or the thread interrupted during the
 * copy is highly output stream and file system provider specific and therefore not specified.
 *
 * Note that if the given output stream is [java.io.Flushable] then its [flush][java.io.Flushable.flush] method may
 * need to invoked after this method completes so as to flush any buffered output.
 *
 * @return the number of bytes read or written
 *
 * @throws IOException if an I/O error occurs when reading or writing
 * @throws SecurityException in the case of the default provider, and a security manager is  installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the file
 */
infix fun Path.copyTo(outputStream: OutputStream): Long = Files.copy(this, outputStream)

/**
 * Reads all the bytes from `this` [file][Path] into a [byte array][ByteArray].
 *
 * The method ensures that the file is closed when all bytes have been read or an I/O error, or other runtime exception,
 * is thrown.
 *
 * Note that this method is intended for simple cases where it is convenient to read all bytes into a [byte array][ByteArray].
 * It is not intended for reading in large files.
 *
 * @return a [byte array][ByteArray] containing the bytes read from the file
 *
 * @throws IOException if an I/O error occurs reading from the stream.
 * @throws OutOfMemoryError if an array of the required size cannot be allocated, for example the file is larger than
 * `2GB`.
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the file.
 */
fun Path.readBytes(): ByteArray = Files.readAllBytes(this)

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
fun Path.readLines(charset: Charset = StandardCharsets.UTF_8): List<String> = Files.readAllLines(this, charset)

/**
 * Writes the given [bytes] to `this` [file][Path].
 *
 * The [options] parameter specifies how the the file is created or opened. If no options are present then this method
 * works as if the [CREATE][StandardOpenOption.CREATE], [WRITE][StandardOpenOption.WRITE], and
 * [TRUNCATE_EXISTING][StandardOpenOption.TRUNCATE_EXISTING] options are present.
 *
 * In other words, it opens the file for writing, creating the file if it doesn't exist, or initially truncating an
 * existing [regular-file][isRegularFile] to a size of `0`. All bytes in the byte array are written to the file.
 * The method ensures that the file is closed when all bytes have been written *(or an I/O error or other runtime
 * exception is thrown)*. If an I/O error occurs then it may do so after the file has created or truncated,
 * or after some bytes have been written to the file.
 *
 * **Usage example**:
 *
 * By default the method creates a new file or overwrites an existing file. Suppose you instead want to append bytes
 * to an existing file:
 *
 * ```kotlin
 *     val path: Path = ...
 *     val bytes: ByteArray = ...
 *     path.writeBytes(bytes, StandardOpenOption.APPEND)
 * ```
 *
 * @param bytes the [byte array][ByteArray] that contains the bytes to write.
 * @param options options specifying how the file is opened
 *
 * @throws IOException if an I/O error occurs writing to or creating the file
 * @throws UnsupportedOperationException if an unsupported option is specified
 * @throws SecurityException in the case of the default provider, and a security manager is  installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the file
 */
fun Path.writeBytes(
    bytes: ByteArray,
    vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)
): Path = Files.write(this, bytes, *options)

/**
 * Writes the given [lines] of text to `this` [file][Path].
 *
 * Each line is a char sequence and is written to the file in sequence with each line terminated by the platform's
 * line separator, as defined by the system property [line.separator][System.lineSeparator]. Characters are encoded into
 * bytes using the specified [charset].
 *
 * The [options] parameter specifies how the the file is created or opened. If no options are present then this method
 * works as if the [CREATE][StandardOpenOption.CREATE], [WRITE][StandardOpenOption.WRITE], and
 * [TRUNCATE_EXISTING][StandardOpenOption.TRUNCATE_EXISTING] options are present.
 *
 * In other words, it opens the file for writing, creating the file if it doesn't exist, or initially truncating an
 * existing [regular-file][isRegularFile] to a size of `0`. The method ensures that the file is closed when all lines
 * have been written *(or an I/O error or other runtime exception is thrown)*. If an I/O error occurs then it may do so
 * after the file has created or truncated, or after some bytes have been written to the file.
 *
 * @param lines an object to iterate over the char sequences.
 * @param charset the charset to use for decoding.
 * @param options options specifying how the file is opened.
 *
 * @throws IOException if an I/O error occurs writing to or creating the file, or the text cannot be encoded using the
 * specified charset
 * @throws UnsupportedOperationException if an unsupported option is specified
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the file
 */
fun Path.writeLines(
    lines: Iterable<CharSequence>,
    charset: Charset,
    vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)
): Path = Files.write(this, lines, charset, *options)

/**
 * Writes the given [lines] of text to `this` [file][Path].
 *
 * Each line is a char sequence and is written to the file in sequence with each line terminated by the platform's
 * line separator, as defined by the system property [line.separator][System.lineSeparator]. Characters are encoded into
 * bytes using the specified [charset].
 *
 * The [options] parameter specifies how the the file is created or opened. If no options are present then this method
 * works as if the [CREATE][StandardOpenOption.CREATE], [WRITE][StandardOpenOption.WRITE], and
 * [TRUNCATE_EXISTING][StandardOpenOption.TRUNCATE_EXISTING] options are present.
 *
 * In other words, it opens the file for writing, creating the file if it doesn't exist, or initially truncating an
 * existing [regular-file][isRegularFile] to a size of `0`. The method ensures that the file is closed when all lines
 * have been written *(or an I/O error or other runtime exception is thrown)*. If an I/O error occurs then it may do so
 * after the file has created or truncated, or after some bytes have been written to the file.
 *
 * @param lines an object to iterate over the char sequences.
 * @param options options specifying how the file is opened.
 *
 * @throws IOException if an I/O error occurs writing to or creating the file, or the text cannot be encoded using the
 * specified charset
 * @throws UnsupportedOperationException if an unsupported option is specified
 * @throws SecurityException in the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the file
 */
fun Path.writeLines(
    lines: Iterable<CharSequence>,
    vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)
): Path = Files.write(this, lines, *options)

/**
 * Returns a lazily populated [Sequence], the elements of which are the entries of this directory, and the entries of
 * any subsequent sub-directories, and the entries of said sub-directories sub-directories, etc..
 *
 * @see Path.walk
 */
val Path.allEntries: Sequence<Path> get() = this.walk()

/**
 * Walks this [file's][Path] tree.
 *
 * This method walks `this` [file][Path] tree rooted at [this] file. The file tree traversal is *depth-first* with the given
 * [FileVisitor] invoked for each file encountered. File tree traversal completes when all accessible files in the tree
 * have been visited, or a visit method returns a result of [TERMINATE][FileVisitResult.TERMINATE]. Where a visit method
 * terminates due to an [IOException], an uncaught error, or runtime exception, then the traversal is terminated and the
 * error or exception is propagated to the caller of this method.
 *
 * For each file encountered this method attempts to read its
 * [BasicFileAttributes][java.nio.file.attribute.BasicFileAttributes]. If the file is not a directory then the
 * [visitFile][FileVisitor.visitFile] method is invoked with the file attributes. If the file attributes cannot be read,
 * due to an I/O exception, then the [visitFileFailed][FileVisitor.visitFileFailed] method is invoked with the I/O
 * exception.
 *
 * Where the file is a directory, and the directory could not be opened, then the `visitFileFailed` method is invoked
 * with the I/O exception, after which, the file tree walk continues, by default, at the next  *sibling* of the directory.
 *
 * Where the directory is opened successfully, then the entries in the directory, and their *descendants* are visited.
 * When all entries have been visited, or an I/O error occurs during iteration of the directory, then the directory is
 * closed and the visitor's [postVisitDirectory][FileVisitor.postVisitDirectory] method is invoked. The file tree walk
 * then continues, by default, at the next *sibling* of the directory.
 *
 * By default, symbolic links are not automatically followed by this method. If the [options] parameter contains the
 * [FOLLOW_LINKS][FileVisitOption.FOLLOW_LINKS] option then symbolic links are followed. When following links, and the
 * attributes of the target cannot be read, then this method attempts to get the [BasicFileAttributes] of the link. If
 * they can be read then the `visitFile` method is invoked with the attributes of the link (otherwise the
 * `visitFileFailed` method is invoked as specified above).
 *
 * If the [options] parameter contains the [FOLLOW_LINKS][FileVisitOption.FOLLOW_LINKS] option then this method keeps
 * track of directories visited so that cycles can be detected. A cycle arises when there is an entry in a directory
 * that is an ancestor of the directory. Cycle detection is done by recording the
 * [file-key][java.nio.file.attribute.BasicFileAttributes.fileKey] of directories, or if file keys are not available,
 * by invoking the [isSameFile][Path.isSameAs] method to test if a directory is the same file as an ancestor. When a
 * cycle is detected it is treated as an I/O error, and the [visitFileFailed][FileVisitor.visitFileFailed] method is
 * invoked with an instance of [FileSystemLoopException].
 *
 * The [maxDepth] parameter is the maximum number of levels of directories to visit. A value of `0` means that only the
 * starting file is visited, unless denied by the security manager. A value of [MAX_VALUE][Integer.MAX_VALUE] may be
 * used to indicate that all levels should be visited. The `visitFile` method is invoked for all files, including
 * directories, encountered at `maxDepth`, unless the basic file attributes cannot be read, in which case the
 * `visitFileFailed` method is invoked.
 *
 * If a visitor returns a result of `null` then [NullPointerException] is thrown.
 *
 * When a security manager is installed and it denies access to `this` [file][Path] (or directory), then it is ignored and the
 * visitor is not invoked for that file (or directory).
 *
 * @param options options to configure the traversal
 *
 * ([emptySet] by default])
 * @param maxDepth the maximum number of directory levels to visit
 *
 * ([Int.MAX_VALUE] by default)
 * @param visitor the [file visitor][PathVisitor] to invoke for each file
 *
 * @return the starting file
 *
 * @throws IllegalArgumentException if the `maxDepth` parameter is negative
 * @throws SecurityException if the security manager denies access to the starting file. In the case of the default
 * provider, the [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the directory.
 * @throws IOException if an I/O error is thrown by a visitor method.
 */
@JvmOverloads
fun Path.walkFileTree(
    options: Set<FileVisitOption> = emptySet(),
    maxDepth: Int = Int.MAX_VALUE,
    visitor: PathVisitor
): Path = Files.walkFileTree(this, options, maxDepth, visitor)

/**
 * Returns a [Sequence] that is lazily populated with `this` [file][Path] by walking the file tree rooted at this
 * [file][Path].
 *
 * The file tree is traversed *depth-first*, the elements in the stream are [Path] objects that are obtained as if by
 * [resolving(Path)][Path.resolve] the relative path against [this].
 *
 * The `stream` walks the file tree as elements are consumed.
 *
 * The [Sequence] returned is guaranteed to have at least one element, the starting file itself. For each file visited,
 * the stream attempts to read its [BasicFileAttributes]. If the file is a directory and can be opened successfully,
 * entries in the directory, and their *descendants* will follow the directory in the stream as they are encountered.
 * When all entries have been visited, then the  directory is closed. The file tree walk then continues at the next
 * *sibling* of the directory.
 *
 * The stream is *weakly consistent*. It does not freeze the file tree while iterating, so it may (or may not) reflect
 * updates to the file tree that occur after returned from this method.
 *
 * By default, symbolic links are not automatically followed by this method. If the [options] parameter contains the
 * [FOLLOW_LINKS][FileVisitOption.FOLLOW_LINKS] option then symbolic links are followed. When following links, and the
 * attributes of the target cannot be read, then this method attempts to get the [BasicFileAttributes] of the link.
 *
 * If the [options] parameter contains the [FOLLOW_LINKS][FileVisitOption.FOLLOW_LINKS] option then the stream keeps
 * track of directories visited so that cycles can be detected. A cycle arises when there is an entry in a directory
 * that is an ancestor of the directory. Cycle detection is done by recording the
 * [file-key][BasicFileAttributes.fileKey] of directories, or if file keys are not available, by invoking the
 * [isSameAs] method to test if a directory is the same file as an ancestor.
 * When a cycle is detected it is treated as an I/O error with an instance of [FileSystemLoopException].
 *
 * The [maxDepth] parameter is the maximum number of levels of directories to visit. A value of `0` means that only the
 * starting file is visited, unless denied by the security manager. A value of [MAX_VALUE][Integer.MAX_VALUE] may be
 * used to indicate that all levels should be visited.
 *
 * When a security manager is installed and it denies access to `this` [file][Path] (or directory), then it is ignored
 * and not included in the stream.
 *
 * The returned stream encapsulates one or more [DirectoryStreams][DirectoryStream].
 *
 * If timely disposal of file system resources is required, the `try-with-resources` construct should be used to ensure
 * that the stream's [close][Stream.close] method is invoked after the stream operations are completed.
 *
 * Operating on a closed stream will result in an [java.lang.IllegalStateException].
 *
 * If an [IOException] is thrown when accessing the directory after this method has returned, it is wrapped in an
 * [UncheckedIOException] which will be thrown from the method that caused the access to take place.
 *
 * @param maxDepth the maximum number of directory levels to visit.
 *
 * ([Int.MAX_VALUE] by default.)
 * @param options options to configure the traversal.
 *
 * @throws IllegalArgumentException if the [maxDepth] parameter is negative
 * @throws SecurityException if the security manager denies access to the starting file. In the case of the default provider,
 * the [checkRead(String)][SecurityManager.checkRead] method is invoked to check read access to the directory
 * @throws IOException if an I/O error is thrown when accessing the starting file
 */
@JvmOverloads
fun Path.walk(maxDepth: Int = Int.MAX_VALUE, vararg options: FileVisitOption): Sequence<Path> =
    Files.walk(this, maxDepth, *options).asSequence()