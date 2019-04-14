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

@file:JvmName("KNumbers")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.lang

import kotlin.experimental.and

// Byte
/**
 * Returns whether or not this [Byte] number is odd.
 */
val Byte.isOdd: Boolean get() = this and 1 != 0.toByte()

/**
 * Returns whether or not this [Byte] number is even.
 */
val Byte.isEven: Boolean get() = this and 1 == 0.toByte()

/**
 * Parses the given [value] as a [Byte] number and returns the result.
 *
 * @throws [NumberFormatException] If the `string` is not a valid representation of a number.
 */
fun Byte.Companion.parse(value: String): Byte = java.lang.Byte.parseByte(value)

/**
 * Parses the given [value] as a [Byte] number and returns the result.
 *
 * @throws [NumberFormatException]
 *
 * - The radix is either smaller than [MIN_RADIX][Character.MIN_RADIX] or larger than [MAX_RADIX][Character.MAX_RADIX].
 *
 * - Any character of the `string` is not a digit of the specified radix, except that the first character may be a
 * minus sign `'-'` (`'\u005Cu002D'`) or plus sign `'+'` (`'\u005Cu002B'`) provided that the `string` is longer than
 * length `1`.
 */
fun Byte.Companion.parse(value: String, radix: Int): Byte = java.lang.Byte.parseByte(value, radix)

// Short
/**
 * Returns whether or not this [Short] number is odd.
 */
val Short.isOdd: Boolean get() = this and 1 != 0.toShort()

/**
 * Returns whether or not this [Short] number is even.
 */
val Short.isEven: Boolean get() = this and 1 == 0.toShort()

/**
 * Parses the given [value] as a [Short] number and returns the result.
 *
 * @throws [NumberFormatException] If the `string` is not a valid representation of a number.
 */
fun Short.Companion.parse(value: String): Short = java.lang.Short.parseShort(value)

/**
 * Parses the given [value] as a [Short] number and returns the result.
 *
 * @throws [NumberFormatException]
 *
 * - The radix is either smaller than [MIN_RADIX][Character.MIN_RADIX] or larger than [MAX_RADIX][Character.MAX_RADIX].
 *
 * - Any character of the `string` is not a digit of the specified radix, except that the first character may be a
 * minus sign `'-'` (`'\u005Cu002D'`) or plus sign `'+'` (`'\u005Cu002B'`) provided that the `string` is longer than
 * length `1`.
 */
fun Short.Companion.parse(value: String, radix: Int): Short = java.lang.Short.parseShort(value, radix)

// Int
/**
 * Returns whether or not this [Int] number is odd.
 */
val Int.isOdd: Boolean get() = this and 1 != 0

/**
 * Returns whether or not this [Int] number is even.
 */
val Int.isEven: Boolean get() = this and 1 == 0

/**
 * Parses the given [value] as an [Int] number and returns the result.
 *
 * @throws [NumberFormatException] If the `string` is not a valid representation of a number.
 */
fun Int.Companion.parse(value: String): Int = java.lang.Integer.parseInt(value)

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
fun Int.Companion.parse(value: String, radix: Int): Int = java.lang.Integer.parseInt(value, radix)

// Long
/**
 * Returns whether or not this [Long] number is odd.
 */
val Long.isOdd: Boolean get() = this and 1 != 0L

/**
 * Returns whether or not this [Long] number is even.
 */
val Long.isEven: Boolean get() = this and 1 == 0L

/**
 * Parses the given [value] as a [Long] number and returns the result.
 *
 * @throws [NumberFormatException] If the `string` is not a valid representation of a number.
 */
fun Long.Companion.parse(value: String): Long = java.lang.Long.parseLong(value)

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
fun Long.Companion.parse(value: String, radix: Int): Long = java.lang.Long.parseLong(value, radix)

// Float
/**
 * Parses the given [value] as a [Long] number and returns the result.
 *
 * @throws [NumberFormatException] If the `string` is not a valid representation of a number.
 */
fun Float.Companion.parse(value: String): Float = java.lang.Float.parseFloat(value)

// Double
/**
 * Parses the given [value] as a [Long] number and returns the result.
 *
 * @throws [NumberFormatException] If the `string` is not a valid representation of a number.
 */
fun Double.Companion.parse(value: String): Double = java.lang.Double.parseDouble(value)