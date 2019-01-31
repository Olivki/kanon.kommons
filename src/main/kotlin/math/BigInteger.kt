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

@file:JvmName("BigIntegerUtils")

package moe.kanon.kommons.math

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
 * Returns the value of [this] [Byte] number as a [BigInteger].
 */
public fun Byte.toBigInteger(): BigInteger = BigInteger.valueOf(this.toLong())

/**
 * Returns the value of [this] [Short] number as a [BigInteger].
 */
public fun Short.toBigInteger(): BigInteger = BigInteger.valueOf(this.toLong())

// Plus (+)
public operator fun BigInteger.plus(other: Byte): BigInteger = this.add(other.toBigInteger())

public operator fun BigInteger.plus(other: Short): BigInteger = this.add(other.toBigInteger())

public operator fun BigInteger.plus(other: Int): BigInteger = this.add(other.toBigInteger())

public operator fun BigInteger.plus(other: Long): BigInteger = this.add(other.toBigInteger())

// Minus (-)
public operator fun BigInteger.minus(other: Byte): BigInteger = this.min(other.toBigInteger())

public operator fun BigInteger.minus(other: Short): BigInteger = this.min(other.toBigInteger())

public operator fun BigInteger.minus(other: Int): BigInteger = this.min(other.toBigInteger())

public operator fun BigInteger.minus(other: Long): BigInteger = this.min(other.toBigInteger())

// Times/Multiply (*)
public operator fun BigInteger.times(other: Byte): BigInteger = this.multiply(other.toBigInteger())

public operator fun BigInteger.times(other: Short): BigInteger = this.multiply(other.toBigInteger())

public operator fun BigInteger.times(other: Int): BigInteger = this.multiply(other.toBigInteger())

public operator fun BigInteger.times(other: Long): BigInteger = this.multiply(other.toBigInteger())

// Divide (/)
public operator fun BigInteger.div(other: Byte): BigInteger = this.divide(other.toBigInteger())

public operator fun BigInteger.div(other: Short): BigInteger = this.divide(other.toBigInteger())

public operator fun BigInteger.div(other: Int): BigInteger = this.divide(other.toBigInteger())

public operator fun BigInteger.div(other: Long): BigInteger = this.divide(other.toBigInteger())

// Rem (%)
public operator fun BigInteger.rem(other: Byte): BigInteger = this.remainder(other.toBigInteger())

public operator fun BigInteger.rem(other: Short): BigInteger = this.remainder(other.toBigInteger())

public operator fun BigInteger.rem(other: Int): BigInteger = this.remainder(other.toBigInteger())

public operator fun BigInteger.rem(other: Long): BigInteger = this.remainder(other.toBigInteger())

// TODO: Add operators for unsigned numbers once they get to stable.
// TODO: Add support for the infix functions added by Kotlin for BigInteger.
