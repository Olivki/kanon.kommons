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

@file:JvmName("KSequences")

package moe.kanon.kommons.collections

// -- UTILITY PROPERTIES -- \\
/**
 * Returns the size of `this` [Sequence].
 *
 * Note that this is accomplished by using the [count][Sequence.count] function, which means that depending on how many
 * elements are available in `this` sequence this may be a costly invocation.
 */
val <T> Sequence<T>.size: Int @JvmName("sizeOf") get() = count()

/**
 * Returns `true` if `this` sequence has no elements, otherwise returns `false`.
 */
val <T> Sequence<T>.isEmpty: Boolean get() = none()

/**
 * Returns `true` if `this` sequence has elements, otherwise returns `false`.
 */
val <T> Sequence<T>.isNotEmpty: Boolean get() = any()

// -- UTILITY FUNCTIONS -- \\
/**
 * Returns a new [CharArray] containing the elements of `this` sequence.
 */
fun Sequence<Char>.toCharArray(): CharArray {
    val result = CharArray(size)
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [BooleanArray] containing the elements of `this` sequence.
 */
fun Sequence<Boolean>.toBooleanArray(): BooleanArray {
    val result = BooleanArray(size)
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [ByteArray] containing the elements of `this` sequence.
 */
fun Sequence<Byte>.toByteArray(): ByteArray {
    val result = ByteArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [ShortArray] containing the elements of `this` sequence.
 */
fun Sequence<Short>.toShortArray(): ShortArray {
    val result = ShortArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [IntArray] containing the elements of `this` sequence.
 */
fun Sequence<Int>.toIntArray(): IntArray {
    val result = IntArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [LongArray] containing the elements of `this` sequence.
 */
fun Sequence<Long>.toLongArray(): LongArray {
    val result = LongArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [FloatArray] containing the elements of `this` sequence.
 */
fun Sequence<Float>.toFloatArray(): FloatArray {
    val result = FloatArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [DoubleArray] containing the elements of `this` sequence.
 */
fun Sequence<Double>.toDoubleArray(): DoubleArray {
    val result = DoubleArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}