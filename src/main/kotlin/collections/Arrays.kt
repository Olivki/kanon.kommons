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

@file:JvmName("KArrays")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.collections

import moe.kanon.kommons.lang.occurrencesOf

// Generic Array
/**
 * Returns an array inherited from this [array][Array] that's been spliced from [start] until the size of the array.
 *
 * @see take
 * @see sliceArray
 */
public fun <V> Array<V>.from(start: Int): Array<V> = sliceArray(start until size)

/**
 * Returns an array inherited from this [array][Array] that's been spliced from index 0 until [end].
 *
 * @see takeLast
 * @see sliceArray
 */
public fun <V> Array<V>.until(end: Int): Array<V> = sliceArray(0 until end)

/**
 * Checks whether this [array][Array] contains all the values of the [other] array.
 */
public operator fun <V> Array<V>.contains(other: Array<V>): Boolean = when {
    other.isEmpty() -> false
    else -> other.all { it in this }
}

/**
 * Checks whether this [array][Array] contains all the values of the [other] collection.
 */
public operator fun <V> Array<V>.contains(other: Collection<V>): Boolean = when {
    other.isEmpty() -> false
    else -> other.all { it in this }
}

// TODO: Add all of the primitive arrays that can use ranges.

// IntArray
/**
 * Checks whether this [array][Array] array contains the given [range].
 */
public operator fun IntArray.contains(range: IntRange): Boolean = when {
    isEmpty() -> false
    else -> range.all { it in this }
}

/**
 * Returns a [List] that's sorted after the occurrences of the specific characters in the given [String].
 */
public fun CharArray.sortByOccurrences(string: String): List<Char> = this.sortedBy { string.occurrencesOf(it) }

/**
 * Returns a [List] that's sorted after the occurrences of the specific characters in the given [String].
 */
public fun Array<Char>.sortByOccurrences(string: String): List<Char> = this.sortedBy { string.occurrencesOf(it) }

