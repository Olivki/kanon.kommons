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
@file:JvmName("KIterables")

package moe.kanon.kommons.collections.iterables

import moe.kanon.kommons.lang.occurrencesOf

val <T> Iterable<T>.head: T get() = first()
val <T> Iterable<T>.tail: T get() = last()

// ports of internal only functions from the std-lib
/**
 * Returns the size of `this` iterable if it is known, or `null` otherwise.
 */
fun <T> Iterable<T>.collectionSizeOrNull(): Int? = if (this is Collection<*>) this.size else null

/**
 * Returns the size of `this` iterable if it is known, or the specified [default] value otherwise.
 */
fun <T> Iterable<T>.collectionSizeOrDefault(default: Int): Int = if (this is Collection<*>) this.size else default

// custom functions
/**
 * Returns a [List] that's sorted after the occurrences of the specific characters in the given [String].
 */
fun Iterable<Char>.sortByOccurrences(string: String): List<Char> = this.sortedBy { string.occurrencesOf(it) }