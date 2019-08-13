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

package moe.kanon.kommons.lang

import java.math.BigInteger
import kotlin.experimental.and

// -- BYTE -- \\
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

// -- SHORT -- \\
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

// -- INT -- \\
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

// -- LONG -- \\
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

// -- FLOAT -- \\
/**
 * Parses the given [value] as a [Long] number and returns the result.
 *
 * @throws [NumberFormatException] If the `string` is not a valid representation of a number.
 */
fun Float.Companion.parse(value: String): Float = java.lang.Float.parseFloat(value)

// -- DOUBLE -- \\
/**
 * Parses the given [value] as a [Long] number and returns the result.
 *
 * @throws [NumberFormatException] If the `string` is not a valid representation of a number.
 */
fun Double.Companion.parse(value: String): Double = java.lang.Double.parseDouble(value)

// -- BIG INTEGER -- \\
/*
    So, sadly due to the way that IntelliJ works, it won't auto-import / give you the suggestion of importing
    any of these operators if you're trying to do an assignment with them, i.e: += (plusAssign) etc.

    The way Kotlin is made however, you can use a normal operator in an assign operation, it's the last thing the
    compiler does however. (It basically changes the syntax from 'a += b' to 'a = a + b'.) But because it is the last
     thing the compiler does if it can't find a normal *Assign operator, IntelliJ seems to not play nicely with it,
     and thus you need to manually import stuff from this class.

    This becomes a problem because we can't create a plusAssign operator because BigInteger is immutable, and Kotlin
    doesn't want to auto-import, so we're stuck with manually importing.
 */
// toBigInteger()
/**
 * Returns the value of this [Byte] number as a [BigInteger].
 */
fun Byte.toBigInteger(): BigInteger = BigInteger.valueOf(this.toLong())

/**
 * Returns the value of this [Short] number as a [BigInteger].
 */
fun Short.toBigInteger(): BigInteger = BigInteger.valueOf(this.toLong())

// Plus (+)
/**
 * Adds the value of the given [byte] to this [BigInteger], and returns the result.
 */
operator fun BigInteger.plus(byte: Byte): BigInteger = this.add(byte.toBigInteger())

/**
 * Adds the value of the given [short] to this [BigInteger], and returns the result.
 */
operator fun BigInteger.plus(short: Short): BigInteger = this.add(short.toBigInteger())

/**
 * Adds the value of the given [int] to this [BigInteger], and returns the result.
 */
operator fun BigInteger.plus(int: Int): BigInteger = this.add(int.toBigInteger())

/**
 * Adds the value of the given [long] to this [BigInteger], and returns the result.
 */
operator fun BigInteger.plus(long: Long): BigInteger = this.add(long.toBigInteger())

// Minus (-)
/**
 * Subtracts the value of the given [byte] from this [BigInteger], and returns the result.
 */
operator fun BigInteger.minus(byte: Byte): BigInteger = this.min(byte.toBigInteger())

/**
 * Subtracts the value of the given [short] from this [BigInteger], and returns the result.
 */
operator fun BigInteger.minus(short: Short): BigInteger = this.min(short.toBigInteger())

/**
 * Subtracts the value of the given [int] from this [BigInteger], and returns the result.
 */
operator fun BigInteger.minus(int: Int): BigInteger = this.min(int.toBigInteger())

/**
 * Subtracts the value of the given [long] from this [BigInteger], and returns the result.
 */
operator fun BigInteger.minus(long: Long): BigInteger = this.min(long.toBigInteger())

// Times/Multiply (*)
/**
 * Multiplies this [BigInteger] with the value of the given [byte], and returns the result.
 */
operator fun BigInteger.times(byte: Byte): BigInteger = this.multiply(byte.toBigInteger())

/**
 * Multiplies this [BigInteger] with the value of the given [short], and returns the result.
 */
operator fun BigInteger.times(short: Short): BigInteger = this.multiply(short.toBigInteger())

/**
 * Multiplies this [BigInteger] with the value of the given [int], and returns the result.
 */
operator fun BigInteger.times(int: Int): BigInteger = this.multiply(int.toBigInteger())

/**
 * Multiplies this [BigInteger] with the value of the given [long], and returns the result.
 */
operator fun BigInteger.times(long: Long): BigInteger = this.multiply(long.toBigInteger())

// Divide (/)
/**
 * Divides this [BigInteger] with the value of the given [byte], and returns the result.
 */
operator fun BigInteger.div(byte: Byte): BigInteger = this.divide(byte.toBigInteger())

/**
 * Divides this [BigInteger] with the value of the given [short], and returns the result.
 */
operator fun BigInteger.div(short: Short): BigInteger = this.divide(short.toBigInteger())

/**
 * Divides this [BigInteger] with the value of the given [int], and returns the result.
 */
operator fun BigInteger.div(int: Int): BigInteger = this.divide(int.toBigInteger())

/**
 * Divides this [BigInteger] with the value of the given [long], and returns the result.
 */
operator fun BigInteger.div(long: Long): BigInteger = this.divide(long.toBigInteger())

// Rem (%)
/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [byte], and returns the result.
 */
operator fun BigInteger.rem(byte: Byte): BigInteger = this.remainder(byte.toBigInteger())

/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [short], and returns the result.
 */
operator fun BigInteger.rem(short: Short): BigInteger = this.remainder(short.toBigInteger())

/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [int], and returns the result.
 */
operator fun BigInteger.rem(int: Int): BigInteger = this.remainder(int.toBigInteger())

/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [long], and returns the result.
 */
operator fun BigInteger.rem(long: Long): BigInteger = this.remainder(long.toBigInteger())