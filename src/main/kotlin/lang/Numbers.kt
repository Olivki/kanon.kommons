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

@file:JvmName("Numbers")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.lang

// Int
/**
 * Returns whether or not this [Int] number is odd.
 */
public val Int.isOdd: Boolean get() = this and 1 != 0

/**
 * Returns whether or not this [Int] number is even.
 */
public val Int.isEven: Boolean get() = this and 1 == 0

/**
 * Parses the given [value] as an [Int] number and returns the result.
 *
 * @throws [NumberFormatException] If the `string` is not a valid representation of a number.
 */
public inline fun Int.Companion.parse(value: String): Int = Integer.parseInt(value)

/**
 * Parses the given [value] as an [Int] number and returns the result.
 *
 * @throws [NumberFormatException]
 *
 * - The radix is either smaller than [MIN_RADIX][Character.MIN_RADIX] or larger than [MAX_RADIX][Character.MAX_RADIX].
 *
 * - Any character of the `string` is not a digit of the specified radix, except that the first character may be a
 * minus sign `'-'` (`'\u005Cu002D'`) or plus sign `'+'` (`'\u005Cu002B'`) provided that the `string` is longer than
 * length `1`.
 */
public inline fun Int.Companion.parse(value: String, radix: Int): Int = Integer.parseInt(value, radix)

// Long
/**
 * Returns whether or not this [Long] number is odd.
 */
public val Long.isOdd: Boolean get() = this and 1 != 0L

/**
 * Returns whether or not this [Long] number is even.
 */
public val Long.isEven: Boolean get() = this and 1 == 0L

/**
 * Parses the given [value] as a [Long] number and returns the result.
 *
 * @throws [NumberFormatException] If the `string` is not a valid representation of a number.
 */
public inline fun Long.Companion.parse(value: String): Long = java.lang.Long.parseLong(value)

/**
 * Parses the given [value] as a [Long] number and returns the result.
 *
 * @throws [NumberFormatException]
 *
 * - The radix is either smaller than [MIN_RADIX][Character.MIN_RADIX] or larger than [MAX_RADIX][Character.MAX_RADIX].
 *
 * - Any character of the `string` is not a digit of the specified radix, except that the first character may be a
 * minus sign `'-'` (`'\u005Cu002D'`) or plus sign `'+'` (`'\u005Cu002B'`) provided that the `string` is longer than
 * length `1`.
 */
public inline fun Long.Companion.parse(value: String, radix: Int): Long = java.lang.Long.parseLong(value, radix)