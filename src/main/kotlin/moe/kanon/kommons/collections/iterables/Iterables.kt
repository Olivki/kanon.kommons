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

@file:JvmName("KIterables")

package moe.kanon.kommons.collections.iterables

import moe.kanon.kommons.collections.arrays.createArray
import moe.kanon.kommons.collections.iterators.EmptyIterator
import moe.kanon.kommons.collections.iterators.UnmodifiableIterator
import moe.kanon.kommons.lang.occurrencesOf
import moe.kanon.kommons.shouldNotBeGreaterThan
import sun.invoke.empty.Empty
import java.util.*

/**
 * Returns the number of elements in contained in the [iterator][Iterable.iterator] of `this` [Iterable].
 */
val <T> Iterable<T>.size: Int get() = count()

/**
 * Returns the first element contained in the [iterator][Iterable.iterator] of `this` [Iterable], or throws a
 * [NoSuchElementException] if there are no elements.
 */
val <T> Iterable<T>.head: T get() = first()

/**
 * Returns the last element contained in the [iterator][Iterable.iterator] of `this` [Iterable], or throws a
 * [NoSuchElementException] if there are no elements.
 */
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

// -- MAP TO EXISTING ARRAY -- \\

inline fun <T, R> Iterable<T>.mapTo(target: Array<R>, transformer: (T) -> R): Array<R> {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: ByteArray, transformer: (T) -> Byte): ByteArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: ShortArray, transformer: (T) -> Short): ShortArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: IntArray, transformer: (T) -> Int): IntArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: LongArray, transformer: (T) -> Long): LongArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: FloatArray, transformer: (T) -> Float): FloatArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: DoubleArray, transformer: (T) -> Double): DoubleArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

// -- MAP INDEXED TO EXISTING ARRAY -- \\

inline fun <T, R> Iterable<T>.mapIndexedTo(target: Array<R>, transformer: (i: Int, e: T) -> R): Array<R> {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: ByteArray, transformer: (i: Int, e: T) -> Byte): ByteArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: ShortArray, transformer: (i: Int, e: T) -> Short): ShortArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: IntArray, transformer: (i: Int, e: T) -> Int): IntArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: LongArray, transformer: (i: Int, e: T) -> Long): LongArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: FloatArray, transformer: (i: Int, e: T) -> Float): FloatArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: DoubleArray, transformer: (i: Int, e: T) -> Double): DoubleArray {
    this.size shouldNotBeGreaterThan target.size
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

// -- MAP TO NEW ARRAY -- \\

inline fun <T, R> Iterable<T>.mapIndexedArray(transformer: (i: Int, e: T) -> R): Array<R> =
    mapIndexedTo(createArray(this.size), transformer)

inline fun <T> Iterable<T>.mapIndexedByteArray(transformer: (i: Int, e: T) -> Byte): ByteArray =
    mapIndexedTo(ByteArray(this.size), transformer)

inline fun <T> Iterable<T>.mapIndexedShortArray(transformer: (i: Int, e: T) -> Short): ShortArray =
    mapIndexedTo(ShortArray(this.size), transformer)

inline fun <T> Iterable<T>.mapIndexedIntArray(transformer: (i: Int, e: T) -> Int): IntArray =
    mapIndexedTo(IntArray(this.size), transformer)

inline fun <T> Iterable<T>.mapIndexedLongArray(transformer: (i: Int, e: T) -> Long): LongArray =
    mapIndexedTo(LongArray(this.size), transformer)

inline fun <T> Iterable<T>.mapIndexedFloatArray(transformer: (i: Int, e: T) -> Float): FloatArray =
    mapIndexedTo(FloatArray(this.size), transformer)

inline fun <T> Iterable<T>.mapIndexedDoubleArray(transformer: (i: Int, e: T) -> Double): DoubleArray =
    mapIndexedTo(DoubleArray(this.size), transformer)

// -- MAP INDEXED TO NEW ARRAY -- \\

inline fun <T, R> Iterable<T>.mapArray(transformer: (T) -> R): Array<R> =
    mapTo(createArray(this.size), transformer)

inline fun <T> Iterable<T>.mapByteArray(transformer: (T) -> Byte): ByteArray =
    mapTo(ByteArray(this.size), transformer)

inline fun <T> Iterable<T>.mapShortArray(transformer: (T) -> Short): ShortArray =
    mapTo(ShortArray(this.size), transformer)

inline fun <T> Iterable<T>.mapIntArray(transformer: (T) -> Int): IntArray =
    mapTo(IntArray(this.size), transformer)

inline fun <T> Iterable<T>.mapLongArray(transformer: (T) -> Long): LongArray =
    mapTo(LongArray(this.size), transformer)

inline fun <T> Iterable<T>.mapFloatArray(transformer: (T) -> Float): FloatArray =
    mapTo(FloatArray(this.size), transformer)

inline fun <T> Iterable<T>.mapDoubleArray(transformer: (T) -> Double): DoubleArray =
    mapTo(DoubleArray(this.size), transformer)

// -- TO ARRAY -- \\

fun <T> Iterable<T>.toTypedArray(): Array<T> = mapArray { it }

fun Iterable<Byte>.toByteArray(): ByteArray = mapByteArray { it }

fun Iterable<Short>.toShortArray(): ShortArray = mapShortArray { it }

fun Iterable<Int>.toIntArray(): IntArray = mapIntArray { it }

fun Iterable<Long>.toLongArray(): LongArray = mapLongArray { it }

fun Iterable<Float>.toFloatArray(): FloatArray = mapFloatArray { it }

fun Iterable<Double>.toDoubleArray(): DoubleArray = mapDoubleArray { it }

// custom functions
/**
 * Returns a [List] that's sorted after the occurrences of the specific characters in the given [String].
 */
fun Iterable<Char>.sortByOccurrences(string: String): List<Char> = this.sortedBy { string.occurrencesOf(it) }

/**
 * An abstract implementation of a [Iterable] that only provides a [UnmodifiableIterator].
 */
interface UnmodifiableIterable<out T> : Iterable<T> {
    override fun iterator(): UnmodifiableIterator<T>
}

object EmptyIterable : UnmodifiableIterable<Nothing> {
    override fun iterator(): UnmodifiableIterator<Nothing> = EmptyIterator

    @JvmStatic
    fun <T> of(clz: Class<T>): UnmodifiableIterable<T> = this
}

fun <T> emptyIterable(): UnmodifiableIterable<T> = EmptyIterable