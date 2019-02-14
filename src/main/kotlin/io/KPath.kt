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

@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.io

import java.net.URI
import java.nio.file.*
import java.nio.file.spi.FileSystemProvider

/**
 * A utility class that sets out to create a syntax for creating [Path]s that resemble that when creating a
 * [java.io.File].
 *
 * @since 0.5.2
 */
object KPath {

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
     * @param first The path string or initial part of the path string
     * @param more Additional strings to be joined to form the path string.
     *
     * @return The resulting `Path`.
     *
     * @throws InvalidPathException if the path string cannot be converted to a `Path`.
     *
     * @since 0.5.2
     *
     * @see FileSystem.getPath
     */
    public inline operator fun invoke(first: String, vararg more: String): Path = pathOf(first, *more)

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
     * @param uri The `URI` to convert.
     *
     * @return The resulting `Path`.
     *
     * @throws IllegalArgumentException If preconditions on the `uri` parameter do not hold. The format of the `URI` is
     * provider specific.
     * @throws FileSystemNotFoundException The file system, identified by the `URI`, does not exist and cannot be created
     * automatically, or the provider identified by the `URI`'s scheme component is not installed
     * @throws SecurityException If a security manager is installed and it denies an unspecified permission to access the
     * file system
     *
     * @since 0.5.2
     */
    public inline operator fun invoke(uri: URI): Path = pathOf(uri)

    /**
     * Converts a given path string to a [Path] and resolves it against the [parent] `Path` in exactly the manner
     * specified by the [resolve][Path.resolve] method.
     *
     * For example, suppose that the name separator is "`/`" and the `parent` path represents "`foo/bar`", then
     * invoking this  method with the path string "`gus`" will result in the `Path` "`foo/bar/gus`".
     *
     * @param child The path string to resolve against the `parent` path.
     *
     * @return The resulting path.
     *
     * @throws InvalidPathException If the path string cannot be converted to a `Path`.
     *
     * @since 0.5.2
     *
     * @see Path.resolve
     * @see FileSystem.getPath
     */
    public inline operator fun invoke(parent: Path, child: String): Path = parent.resolve(child)

}