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

@file:JvmName("IOAssertions")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.io

import moe.kanon.kommons.FakeKeyword
import moe.kanon.kommons.io.paths.UnsupportedDesktopException
import moe.kanon.kommons.io.paths.isDirectory
import moe.kanon.kommons.io.paths.isRegularFile
import moe.kanon.kommons.io.paths.notExists
import java.awt.Desktop
import java.io.IOException
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.NoSuchFileException
import java.nio.file.NotDirectoryException
import java.nio.file.Path

/**
 * Throws a [NoSuchFileException] with the result of calling [lazyMessage] if `this` file does not exist on the current
 * [file-system][FileSystem].
 *
 * @receiver the [Path] instance to check against.
 *
 * @throws [NoSuchFileException] if the `Path` receiver does not have an existing file on the `file-system`
 */
@FakeKeyword
@Throws(NoSuchFileException::class)
inline fun requireFileExistence(path: Path, lazyMessage: () -> Any) {
    if (path.notExists) throw NoSuchFileException(path.toString(), null, lazyMessage().toString())
}

/**
 * Throws a [NoSuchFileException] with a default message if `this` file does not exist on the current
 * [file-system][FileSystems.getDefault].
 *
 * @receiver the [Path] instance to check against.
 *
 * @throws [NoSuchFileException] if the `Path` receiver does not have an existing file on the `file-system`
 */
@FakeKeyword
@Throws(NoSuchFileException::class)
inline fun requireFileExistence(path: Path) = requireFileExistence(path) { "File <$path> doesn't exist!" }

/**
 * Throws a [IOException] with the result of calling [lazyMessage] if `this` file is *not* a regular file.
 *
 * @receiver the [Path] instance to check against
 *
 * @throws [NoSuchFileException] if the `Path` receiver does not have an existing file on the `file-system`
 * @throws [NotDirectoryException] if the `Path` receiver is *not* a regular file
 */
@FakeKeyword
inline fun requireRegularFile(path: Path, lazyMessage: () -> Any) {
    requireFileExistence(path)
    if (!path.isRegularFile) throw IOException(lazyMessage().toString())
}

/**
 * Throws a [IOException] with a default message if `this` file is *not* a regular file.
 *
 * @receiver the [Path] instance to check against
 *
 * @throws [NoSuchFileException] if the `Path` receiver does not have an existing file on the `file-system`
 * @throws [NotDirectoryException] if the `Path` receiver is *not* a regular file
 */
@FakeKeyword
inline fun requireRegularFile(path: Path) = requireRegularFile(path) { "<$path> is not a regular file" }

/**
 * Throws a [NotDirectoryException] with the result of calling [lazyMessage] if `this` file is *not* a directory.
 *
 * @receiver the [Path] instance to check against
 *
 * @throws [NoSuchFileException] if the `Path` receiver does not have an existing file on the `file-system`
 * @throws [NotDirectoryException] if the `Path` receiver is *not* a directory
 */
@FakeKeyword
@Throws(NoSuchFileException::class, NotDirectoryException::class)
inline fun requireDirectory(path: Path, lazyMessage: () -> Any) {
    requireFileExistence(path)
    if (!path.isDirectory) throw NotDirectoryException(lazyMessage().toString())
}

/**
 * Throws a [NotDirectoryException] with a default message if `this` file is *not* a directory.
 *
 * @receiver The [Path] instance to check against.
 *
 * @throws [NoSuchFileException] If the `Path` receiver does not have an existing file on the `file-system`.
 * @throws [NotDirectoryException] If the `Path` receiver is *not* a directory.
 */
@FakeKeyword
@Throws(NoSuchFileException::class, NotDirectoryException::class)
inline fun requireDirectory(path: Path) = requireDirectory(path) { "File <$path> needs to be a directory!" }

/**
 * Throws a [UnsupportedDesktopException] with the result of calling [lazyMessage] if the Java Desktop API does not
 * support the current desktop environment.
 */
@FakeKeyword
@Throws(UnsupportedDesktopException::class)
inline fun requireSupportedDesktop(lazyMessage: () -> Any) {
    if (!Desktop.isDesktopSupported()) throw UnsupportedDesktopException(lazyMessage().toString())
}

/**
 * Throws a [UnsupportedDesktopException] with a default message if the Java Desktop API does not support the current
 * desktop environment.
 */
@FakeKeyword
@Throws(UnsupportedDesktopException::class)
inline fun requireSupportedDesktop() = requireSupportedDesktop {
    "The Java Desktop API is not supported on the current platform. (OS:${System.getProperty("os.name")})"
}