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

@file:JvmName("Strings")

package moe.kanon.kommons.lang

/**
 * Returns whether or not `this` int is *not* greater than the [length][String.length] of the specified [string].
 */
infix fun Int.isInBoundsOf(string: String): Boolean = string.length > this

/**
 * TODO
 */
inline fun String.mapIf(predicate: (Char) -> Boolean, transformer: (Char) -> Char): String = buildString {
    for (char in this@mapIf) if (predicate(char)) append(transformer(char)) else append(char)
}

inline fun String.traverse(
    predicate: (Char) -> Boolean,
    transformer: (char: Char, state: StringTraverseState) -> Char
): String = buildString {
    for (char in this@traverse) {

    }
}

enum class StringTraverseState {
    BEFORE_MATCH,
    ON_MATCH,
    AFTER_MATCH,
    NORMAL
}