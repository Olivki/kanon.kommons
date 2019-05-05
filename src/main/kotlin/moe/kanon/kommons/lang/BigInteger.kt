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

@file:JvmName("KBigInteger")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.lang

import java.math.BigInteger

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
inline fun Byte.toBigInteger(): BigInteger = BigInteger.valueOf(this.toLong())

/**
 * Returns the value of this [Short] number as a [BigInteger].
 */
inline fun Short.toBigInteger(): BigInteger = BigInteger.valueOf(this.toLong())

// Plus (+)
/**
 * Adds the value of the given [byte] to this [BigInteger], and returns the result.
 */
inline operator fun BigInteger.plus(byte: Byte): BigInteger = this.add(byte.toBigInteger())

/**
 * Adds the value of the given [short] to this [BigInteger], and returns the result.
 */
inline operator fun BigInteger.plus(short: Short): BigInteger = this.add(short.toBigInteger())

/**
 * Adds the value of the given [int] to this [BigInteger], and returns the result.
 */
inline operator fun BigInteger.plus(int: Int): BigInteger = this.add(int.toBigInteger())

/**
 * Adds the value of the given [long] to this [BigInteger], and returns the result.
 */
inline operator fun BigInteger.plus(long: Long): BigInteger = this.add(long.toBigInteger())

// Minus (-)
/**
 * Subtracts the value of the given [byte] from this [BigInteger], and returns the result.
 */
inline operator fun BigInteger.minus(byte: Byte): BigInteger = this.min(byte.toBigInteger())

/**
 * Subtracts the value of the given [short] from this [BigInteger], and returns the result.
 */
inline operator fun BigInteger.minus(short: Short): BigInteger = this.min(short.toBigInteger())

/**
 * Subtracts the value of the given [int] from this [BigInteger], and returns the result.
 */
inline operator fun BigInteger.minus(int: Int): BigInteger = this.min(int.toBigInteger())

/**
 * Subtracts the value of the given [long] from this [BigInteger], and returns the result.
 */
inline operator fun BigInteger.minus(long: Long): BigInteger = this.min(long.toBigInteger())

// Times/Multiply (*)
/**
 * Multiplies this [BigInteger] with the value of the given [byte], and returns the result.
 */
inline operator fun BigInteger.times(byte: Byte): BigInteger = this.multiply(byte.toBigInteger())

/**
 * Multiplies this [BigInteger] with the value of the given [short], and returns the result.
 */
inline operator fun BigInteger.times(short: Short): BigInteger = this.multiply(short.toBigInteger())

/**
 * Multiplies this [BigInteger] with the value of the given [int], and returns the result.
 */
inline operator fun BigInteger.times(int: Int): BigInteger = this.multiply(int.toBigInteger())

/**
 * Multiplies this [BigInteger] with the value of the given [long], and returns the result.
 */
inline operator fun BigInteger.times(long: Long): BigInteger = this.multiply(long.toBigInteger())

// Divide (/)
/**
 * Divides this [BigInteger] with the value of the given [byte], and returns the result.
 */
inline operator fun BigInteger.div(byte: Byte): BigInteger = this.divide(byte.toBigInteger())

/**
 * Divides this [BigInteger] with the value of the given [short], and returns the result.
 */
inline operator fun BigInteger.div(short: Short): BigInteger = this.divide(short.toBigInteger())

/**
 * Divides this [BigInteger] with the value of the given [int], and returns the result.
 */
inline operator fun BigInteger.div(int: Int): BigInteger = this.divide(int.toBigInteger())

/**
 * Divides this [BigInteger] with the value of the given [long], and returns the result.
 */
inline operator fun BigInteger.div(long: Long): BigInteger = this.divide(long.toBigInteger())

// Rem (%)
/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [byte], and returns the result.
 */
inline operator fun BigInteger.rem(byte: Byte): BigInteger = this.remainder(byte.toBigInteger())

/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [short], and returns the result.
 */
inline operator fun BigInteger.rem(short: Short): BigInteger = this.remainder(short.toBigInteger())

/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [int], and returns the result.
 */
inline operator fun BigInteger.rem(int: Int): BigInteger = this.remainder(int.toBigInteger())

/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [long], and returns the result.
 */
inline operator fun BigInteger.rem(long: Long): BigInteger = this.remainder(long.toBigInteger())

// Infix operations

// TODO: Add operators for unsigned numbers once they get to stable.
// TODO: Add support for the infix functions added by Kotlin for BigInteger.
