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

@file:JvmName("KPosixFilePermissions")

package moe.kanon.kommons.io

import java.nio.file.attribute.FileAttribute
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFilePermissions

/**
 * Creates a [FileAttribute], encapsulating a copy of the given file permissions, suitable for passing to the
 * [createChildFile] or [createChildDirectory] functions.
 *
 *
 * @return an attribute encapsulating the given file permissions with [FileAttribute.name] `"posix:permissions"`
 *
 * @throws [ClassCastException] if the set contains elements that are not of type [PosixFilePermission]
 */
fun Set<PosixFilePermission>.toFileAttribute(): FileAttribute<Set<PosixFilePermission>> =
    PosixFilePermissions.asFileAttribute(this)

/**
 * Creates a [FileAttribute] from [this] singular instance of [PosixFilePermission], suitable for passing to the
 * [createChildFile] or [createChildDirectory] functions.
 *
 * @return an attribute encapsulating the given file permissions with [FileAttribute.name] `"posix:permissions"`
 */
fun PosixFilePermission.toFileAttribute(): FileAttribute<Set<PosixFilePermission>> =
    PosixFilePermissions.asFileAttribute(setOf(this))

/**
 * Returns the [String] representation of a set of permissions. It is guaranteed that the returned [String] can be
 * parsed by the [PosixFilePermissions.fromString] function.
 *
 * If the set contains `null` or elements that are not of type [PosixFilePermission] then these elements are ignored.
 */
fun Set<PosixFilePermission>.toSerializedString(): String = PosixFilePermissions.toString(this)
