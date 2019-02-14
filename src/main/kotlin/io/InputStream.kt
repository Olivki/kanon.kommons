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

@file:JvmName("InputStreamUtils")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.io

import java.io.InputStream
import java.nio.file.*

/**
 * Copies all bytes from [this] to the [target] file. On return, the input stream will be at end of the stream.
 *
 * By default, the copy fails if the target file already exists or is a symbolic link.
 * If the [REPLACE_EXISTING][StandardCopyOption.REPLACE_EXISTING] option is specified, and the target file already
 * exists, then it is replaced if it is not a non-empty directory. If the target ile exists and is a symbolic link,
 * then the symbolic link is replaced.
 *
 * In this release, the `REPLACE_EXISTING` option is the only option required to be supported by this method.
 * Additional options may be supported in future releases.
 *
 * If an I/O error occurs reading from the input stream or writing to the [target] file, then it may do so after the
 * target file has been created and after some bytes have been read or written. Consequently the input stream may not
 * be at end of stream and may be in an inconsistent state.
 *
 * It is strongly recommended that the input stream be promptly closed if an I/O error occurs.
 *
 * This method may block indefinitely reading from the [inputStream] (or writing to the file).
 * The behavior for the case that the input stream is *asynchronously closed* or the thread interrupted during the copy
 * is highly [InputStream] and [FileSystem] provider specific and therefore not specified.
 *
 * **Usage example**:
 *
 * Suppose we want to capture a web page and save it to a file:
 *
 * ```Kotlin
 *     val path: Path = ...
 *     val uri = URI.create("http://java.sun.com/")
 *     uri.toURL().openStream().use { it.copyTo(path) }
 * ```
 *
 * @param target The path to the target file.
 * @param options Options specifying how the copy should be done.
 *
 * @return The number of bytes read or written.
 *
 * @throws java.io.IOException If an I/O error occurs when reading or writing.
 * @throws FileAlreadyExistsException if the target file exists but cannot be replaced because the `REPLACE_EXISTING`
 * option is not specified *(optional specific exception)*.
 * @throws DirectoryNotEmptyException The `REPLACE_EXISTING` option is specified but the file cannot be replaced
 * because it is a non-empty directory *(optional specific exception)*.
 * @throws UnsupportedOperationException If [options] contains a copy option that is not supported.
 * @throws  SecurityException In the case of the default provider, and a security manager is installed, the
 * [checkWrite(String)][SecurityManager.checkWrite] method is invoked to check write access to the file.
 * Where the `REPLACE_EXISTING` option is specified, the security manager's
 * [checkDelete(String)][SecurityManager.checkDelete] method is invoked to check that an existing file can be deleted.
 */
public inline fun InputStream.copyTo(target: Path, vararg options: CopyOption): Long = Files.copy(this, target, *options)