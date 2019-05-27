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

@file:JvmName("Anys")

package moe.kanon.kommons.lang

import java.util.*

val <T> T.hash: Int @JvmName("hashOf") get() = this.hashCode()

/**
 * Attempts to always return a human-readable `String` representation of `this` string.
 */
val <T> T.string: String @JvmName("stringOf") get() = when (this) {
    null -> "null"
    is Array<*> -> this.contentDeepToString()
    is Iterable<*> -> "[${this.joinToString()}]"
    else -> this.toString()
}