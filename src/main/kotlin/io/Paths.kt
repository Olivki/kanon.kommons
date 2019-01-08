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
import moe.kanon.kextensions.collections.from
import moe.kanon.kextensions.collections.removeAll
import moe.kanon.kextensions.math.plus
import java.io.*
import java.math.BigInteger
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
 * Constructs a [Path] instance out of [this] [String].
 */
public fun String.toPath() = Paths.get(this)!!

/**
 * Constructs a [Path] instance out of an [Array`<String>`][Array].
 *
 * This takes the first entry of the array as the base path, and any subsequent entries will be appended as
 * additional paths.
 *
 * This means that the array **needs** to contain `> 1` entries, otherwise an [ArrayIndexOutOfBoundsException] will
 * be thrown.
 */
// To be expunged?
public fun Array<String>.toPath() = Paths.get(this[0], *from(1))!!

/**
 * Constructs a [Path] instance out of a [List`<String>`][List].
 *
 * This takes the first entry of the array as the base path, and any subsequent entries will be appended as
 * additional paths.
 *
 * This means that the list **needs** to contain `> 1` entries, otherwise an [ArrayIndexOutOfBoundsException] will
 * be thrown.
 */
// To be expunged?
public fun List<String>.toPath() = Paths.get(this[0], *from(1).toTypedArray())!!

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
/**
 * Converts [this] [String] into a [Path].
 */
public operator fun String.unaryPlus(): Path = toPath()

/**
 * Converts [this] [File] into a [Path].
 */
public operator fun File.unaryPlus(): Path = toPath()

// Wrappers.

// TODO: Port Java documentation over to these wrappers.

// Properties

public var Path.permissions
    get() = Files.getPosixFilePermissions(this)!!.toSet()
    public set(perms) {
        Files.setPosixFilePermissions(this, perms)!!
    }

public val Path.fileStore get() = Files.getFileStore(this)!!

public val Path.isHidden get() = Files.isHidden(this)

public val Path.contentType get() = Files.probeContentType(this)!!

// Functions
// Misc
public fun Path.isSameFile(other: Path) = Files.isSameFile(this, other)

public infix fun Path.sameFile(other: Path) = isSameFile(other)

// Streams
public fun Path.newInputStream(vararg options: OpenOption) = Files.newInputStream(this, *options)!!

public fun Path.newOutputStream(vararg options: OpenOption) = Files.newOutputStream(this, *options)!!

public fun Path.newByteChannel(vararg options: OpenOption) = Files.newByteChannel(this, *options)!!

// Because we can't access the original one, so we just gotta port it over here.
typealias PathDirStream = DirectoryStream<Path>

typealias PathDirFilter = DirectoryStream.Filter<Path>

internal class AcceptAllFilter private constructor() : PathDirFilter {

    override fun accept(entry: Path) = true

    companion object {
        internal val FILTER = AcceptAllFilter()
    }
}

public fun Path.newDirectoryStream(dirFilter: PathDirFilter = AcceptAllFilter.FILTER) =
    Files.newDirectoryStream(this, dirFilter)!!

public fun Path.newDirectoryStream(glob: String) = Files.newDirectoryStream(this, glob)!!

// File System Changes
// Creation Functions
// - Files
public fun Path.createFile(vararg attributes: FileAttribute<*>) = Files.createFile(this, *attributes)!!

// TODO: This creates a temp file using the current path as the directory for it, remember to document this.
public fun Path.createTempFile(name: String, extension: String, vararg attributes: FileAttribute<*>) =
    Files.createTempFile(this, name, extension, *attributes)!!

// - Directories
public fun Path.createDirectory(vararg attributes: FileAttribute<*>) =
    Files.createDirectory(this, *attributes)!!

public fun Path.createDirectories(vararg attributes: FileAttribute<*>) =
    Files.createDirectories(this, *attributes)!!

// TODO: This creates a temp dir using the current path as the directory for it, remember to document this.
public fun Path.createTempDirectory(name: String, vararg attributes: FileAttribute<*>) =
    Files.createTempDirectory(name, *attributes)!!

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

    override fun remove(key: String): Any? {
        // Not sure how to properly remove an attribute from a file.
        // TODO: See if I can implement this properly in the future.
        throw NotImplementedError("AttributeMap does not currently support removal operations.")
    }

    override fun get(key: String): Any? = path.getAttribute(key)

    override fun putAll(from: Map<out String, Any>) = TODO("putAll is not implemented")

    override fun clear() = TODO("clear is not implemented")

    override fun containsValue(value: Any): Boolean = TODO("containsValue is not implemented")

    override fun containsKey(key: String): Boolean = try {
        path.getAttribute(key)
        true
    } catch (e: IllegalArgumentException) { // God help me, for I have sinned.
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
public val Path.readable: Boolean get() = Files.isReadable(this)

public var Path.writable: Boolean
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

public val Path.executable: Boolean get() = Files.isExecutable(this)

// File Tree Visitors
typealias PathVisitor = FileVisitor<in Path>

public fun Path.walkFileTree(options: Set<FileVisitOption>, maxDepth: Int, visitor: PathVisitor) =
    Files.walkFileTree(this, options, maxDepth, visitor)!!

public fun Path.walkFileTree(visitor: PathVisitor) = Files.walkFileTree(this, visitor)!!

// Buffered Things
public fun Path.newBufferedReader(charset: Charset = StandardCharsets.UTF_8): BufferedReader =
    Files.newBufferedReader(this, charset)!!

public fun Path.newBufferedWriter(
    charset: Charset = StandardCharsets.UTF_8,
    vararg options: OpenOption
): BufferedWriter =
    Files.newBufferedWriter(this, charset, *options)!!

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
): Path =
    Files.write(this, lines, charset, *options)!!

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
typealias PathStream = Stream<Path>

typealias PathMatcher = BiPredicate<Path, BasicFileAttributes>

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
    return this.readAllLines(charset).joinToString(System.lineSeparator())
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