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

package moe.kanon.kommons.collections

import moe.kanon.kommons.collections.persistent.PersistentCollection
import moe.kanon.kommons.requireThat

// -- UTILITY PROPERTIES -- \\
/**
 * Returns the size of `this` iterable.
 */
val <T> Iterable<T>.size: Int
    @JvmName("sizeOf") get() = when (this) {
        is Collection -> size
        is PersistentCollection -> size
        else -> count()
    }

/**
 * Returns the first element in `this` iterable.
 *
 * @throws [NoSuchElementException] if `this` list is iterable.
 */
val <T> Iterable<T>.head: T @JvmName("headOf") get() = first()

/**
 * Returns all the elements in `this` iterable except for the first.
 */
val <T> Iterable<T>.tail: List<T> @JvmName("tailOf") get() = drop(1)

/**
 * Returns `true` if `this` iterable has no elements to iterate over or `false` if it does.
 */
val Iterable<*>.isEmpty: Boolean
    get() = when (this) {
        is Collection -> isEmpty()
        is PersistentCollection -> isEmpty
        else -> none()
    }

/**
 * Returns `false` if `this` iterable has no elements to iterate over or `true` if it does.
 */
val Iterable<*>.isNotEmpty: Boolean get() = !isEmpty

val <T : Comparable<T>> Iterable<T>.sortOrder: SortOrder
    get() = when (this.sorted()) {
        this -> SortOrder.ASCENDING
        this.reversed() -> SortOrder.DESCENDING
        else -> SortOrder.NONE
    }

enum class SortOrder { ASCENDING, DESCENDING, NONE }

// -- UTILITY FUNCTIONS -- \\
/**
 * Returns the hash-code from combining all the elements in `this` iterable.
 */
fun Iterable<*>.calculateHashCode(): Int {
    var hashCode = 1
    for (e in this) hashCode = 31 * hashCode + (e?.hashCode() ?: 0)
    return hashCode
}

/**
 * Returns `true` if `this` iterable contains the exact same elements as the specified [other] iterable, `false`
 * otherwise.
 */
infix fun Iterable<*>.hasSameElementsAs(other: Iterable<*>): Boolean {
    if (other.size != this.size) return false

    val otherIterator = other.iterator()
    for (elem in this) {
        val elemOther = otherIterator.next()
        if (elem != elemOther) return false
    }

    return true
}

/**
 * Executes the specified [action] every time an `element` matches the specified [predicate].
 */
inline fun <T> Iterable<T>.onMatch(predicate: (T) -> Boolean, action: (T) -> Unit) {
    for (entry in this) if (predicate(entry)) action(entry)
}

/**
 * Throws the specified [exception] every time an `element` matches the specified [predicate].
 */
inline fun <T, X : Throwable> Iterable<T>.throwOnMatch(predicate: (T) -> Boolean, exception: (T) -> X) =
    this.onMatch(predicate) { throw exception(it) }

/**
 * Returns `true` if all elements in `this` iterable are the same or `false` if they are not.
 */
fun <T> Iterable<T>.allEqual(): Boolean = if (this.isEmpty) false else this.all { it == first() }

/**
 * Returns how many times the specified [item] appears in `this` iterable.
 */
fun <T> Iterable<T>.occurrencesOf(item: T): Int = this.count { it == item }

/**
 * Returns a new [List] containing all the entries from `this` iterable and the specified [other] iterables, the order
 * of all the elements is preserved.
 */
fun <T> Iterable<T>.concatWith(vararg other: Iterable<T>): List<T> = this + other.toList().flatten()

/**
 * Returns a new [List] containing all the entries from `this` iterable and the specified [other] iterables, the order
 * of all the elements is preserved.
 */
infix fun <T> Iterable<T>.concatWith(other: Iterable<Iterable<T>>): List<T> = this + other.flatten()

/**
 * Returns a new [List] containing the cross products of `this` and [that].
 */
infix fun <A, B> Iterable<A>.crossProductOf(that: Iterable<B>): List<Pair<A, B>> =
    this.flatMap { first -> that.map { second -> first to second } }

/**
 * Returns a new [List] that contains all the elements of `this` iterable, except for the ones that also exist in [that]
 * iterable, the elements are then transformed by the specified [transformer].
 */
inline fun <T, R> Iterable<T>.mapByDifference(that: Iterable<T>, transformer: (T) -> R): List<R> =
    (this - that).map(transformer)

/**
 * Returns a new [List] containing every [nth] element in `this` iterable.
 */
fun <T> Iterable<T>.everyNth(nth: Int): List<T> = this.windowed(nth, nth, partialWindows = false).map { it.last() }

/**
 * Returns `true` if only *one* element in `this` iterable matches the specified [predicate], otherwise returns `false`.
 */
inline fun <T> Iterable<T>.unique(predicate: (T) -> Boolean): Boolean = this.count(predicate) == 1

/**
 * Returns whether or not `this` iterable contains the specified [content].
 */
tailrec infix operator fun <T> Iterable<T>.contains(content: Iterable<T>): Boolean = when {
    content.isEmpty -> true
    this.isEmpty -> content.isEmpty
    this.take(content.size) == content -> true
    else -> this.drop(1) contains content
}

/**
 * Returns a [IntArray] containing all the indices of the specified [element].
 */
infix fun <T> Iterable<T>.indicesOf(element: T): IntArray = this.asSequence()
    .withIndex()
    .filter { it.value == element }
    .map { it.index }
    .toIntArray()

/**
 * Returns a new [List] containing all the elements of `this`, with the specified [element] inserted between all of
 * them.
 */
infix fun <T> Iterable<T>.intersperse(element: T): List<T> = List(this.size) { listOf(this.elementAt(it), element) }
    .flatten()
    .dropLast(1)

/**
 * Returns [n] maximum elements from `this`, or returns `this` *(sorted descending)* if `n` is greater than or equal to
 * the size of `this`.
 */
fun <T : Comparable<T>> Iterable<T>.takeMax(n: Int): List<T> = this.sortedDescending().take(n)

/**
 * Returns [n] minimum elements from `this`, or returns `this` *(sorted)* if `n` is greater than or equal to
 * the size of `this`.
 */
fun <T : Comparable<T>> Iterable<T>.takeMin(n: Int): List<T> = this.sorted().take(n)

/**
 * Returns a [List] with `this` as the first element, and the contents of [iterable] as the remaining elements.
 */
infix fun <T> T.prependTo(iterable: Iterable<T>): List<T> = (listOf(this).plus(iterable))

/**
 * Returns a [List] containing all the computed permutations available for the elements of `this`.
 *
 * **NOTE**: This implementation uses non stack safe recursion.
 */
fun <T> Iterable<T>.permutations(): TwoDimList<T> = when (this.size) {
    0 -> emptyList()
    1 -> listOf(this.toList())
    else -> foldIndexed(ArrayList()) { index, acc, item ->
        acc.fillWith(this.toList().removeAtIndex(index).permutations().map { item prependTo it })
    }
}

/**
 * Applies [transformer] against an accumulator and each element in `this` *(from left to right)*, and returns the
 * result.
 */
inline fun <T, R> Iterable<T>.scan(identity: R, transformer: (R, T) -> R): List<R> =
    this.fold(emptyList()) { acc, item -> acc + transformer(acc.lastOrNull() ?: identity, item) }

/**
 * Circularly rotates the elements of `this` iterable by [n] amount to the left and returns the result.
 *
 * @throws [IllegalArgumentException] if [n] is negative
 */
infix fun <T> Iterable<T>.rotateLeft(n: Int): List<T> {
    requireThat(n >= 0) { "Can't rotate by a negative amount" }
    val list = this.toList()
    return list.slice(n until list.size) + list.slice(0 until n)
}

/**
 * Circularly rotates the elements of `this` iterable by [n] amount to the right and returns the result.
 *
 * @throws [IllegalArgumentException] if [n] is negative
 */
infix fun <T> Iterable<T>.rotateRight(n: Int): List<T> {
    requireThat(n >= 0) { "Can't rotate by a negative amount" }
    val list = this.toList()
    return list.takeLast(n % list.size) + list.dropLast(n % list.size)
}

/**
 * Returns [n] amount of random elements from `this` iterable.
 */
fun <T> Iterable<T>.sampleSize(n: Int): List<T> {
    requireThat(n <= size) { "'n' is larger than collection size" }
    return this.shuffled().take(n)
}

/**
 * Returns a new [List] based on the elements of `this` iterable, with certain elements removed/added.
 *
 * @param [elements] the new elements to add to the list, starting from [startIndex]
 * @param [startIndex] the index at which the modification should start
 * @param [deleteCount] the amount of elements to remove, starting from [startIndex]
 */
@JvmOverloads fun <T> Iterable<T>.shank(vararg elements: T, startIndex: Int = 0, deleteCount: Int = 0): List<T> {
    val list = this.toList()
    return list.slice(0 until startIndex) + elements + list.drop(startIndex + deleteCount)
}



// TODO: Documentation V
// -- ARRAY CONVERSION FUNCTIONS -- \\
// mapTo
inline fun <T, R> Iterable<T>.mapTo(target: Array<R>, transformer: (T) -> R): Array<R> {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: BooleanArray, transformer: (T) -> Boolean): BooleanArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: CharArray, transformer: (T) -> Char): CharArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: ByteArray, transformer: (T) -> Byte): ByteArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: ShortArray, transformer: (T) -> Short): ShortArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: IntArray, transformer: (T) -> Int): IntArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: LongArray, transformer: (T) -> Long): LongArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: FloatArray, transformer: (T) -> Float): FloatArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

inline fun <T> Iterable<T>.mapTo(target: DoubleArray, transformer: (T) -> Double): DoubleArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(e)
    return target
}

// mapIndexedTo
inline fun <T, R> Iterable<T>.mapIndexedTo(target: Array<R>, transformer: (i: Int, e: T) -> R): Array<R> {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: BooleanArray, transformer: (i: Int, e: T) -> Boolean): BooleanArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: CharArray, transformer: (i: Int, e: T) -> Char): CharArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: ByteArray, transformer: (i: Int, e: T) -> Byte): ByteArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: ShortArray, transformer: (i: Int, e: T) -> Short): ShortArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: IntArray, transformer: (i: Int, e: T) -> Int): IntArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: LongArray, transformer: (i: Int, e: T) -> Long): LongArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: FloatArray, transformer: (i: Int, e: T) -> Float): FloatArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

inline fun <T> Iterable<T>.mapIndexedTo(target: DoubleArray, transformer: (i: Int, e: T) -> Double): DoubleArray {
    requireThat(this.size <= target.size, "this.size <= target.size")
    for ((i, e) in withIndex()) target[i] = transformer(i, e)
    return target
}

// mapIndexed...Array
inline fun <T, R> Iterable<T>.mapIndexedArray(transformer: (i: Int, e: T) -> R): Array<R> =
    mapIndexedTo(createArray(this.size), transformer)

inline fun <T> Iterable<T>.mapIndexedBooleanArray(transformer: (i: Int, e: T) -> Boolean): BooleanArray =
    mapIndexedTo(BooleanArray(this.size), transformer)

inline fun <T> Iterable<T>.mapIndexedCharArray(transformer: (i: Int, e: T) -> Char): CharArray =
    mapIndexedTo(CharArray(this.size), transformer)

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

// map...Array
inline fun <T, R> Iterable<T>.mapArray(transformer: (T) -> R): Array<R> = mapTo(createArray(this.size), transformer)

inline fun <T> Iterable<T>.mapBooleanArray(transformer: (T) -> Boolean): BooleanArray =
    mapTo(BooleanArray(this.size), transformer)

inline fun <T> Iterable<T>.mapCharArray(transformer: (T) -> Char): CharArray = mapTo(CharArray(this.size), transformer)

inline fun <T> Iterable<T>.mapByteArray(transformer: (T) -> Byte): ByteArray = mapTo(ByteArray(this.size), transformer)

inline fun <T> Iterable<T>.mapShortArray(transformer: (T) -> Short): ShortArray =
    mapTo(ShortArray(this.size), transformer)

inline fun <T> Iterable<T>.mapIntArray(transformer: (T) -> Int): IntArray = mapTo(IntArray(this.size), transformer)

inline fun <T> Iterable<T>.mapLongArray(transformer: (T) -> Long): LongArray = mapTo(LongArray(this.size), transformer)

inline fun <T> Iterable<T>.mapFloatArray(transformer: (T) -> Float): FloatArray =
    mapTo(FloatArray(this.size), transformer)

inline fun <T> Iterable<T>.mapDoubleArray(transformer: (T) -> Double): DoubleArray =
    mapTo(DoubleArray(this.size), transformer)

// to...Array
/**
 * Returns a new generic [Array] populated by the elements of `this` iterable.
 */
fun <T> Iterable<T>.toTypedArray(): Array<T> = mapArray { it }

/**
 * Returns a new [BooleanArray] populated by the elements of `this` iterable.
 */
fun Iterable<Boolean>.toBooleanArray(): BooleanArray = mapBooleanArray { it }

/**
 * Returns a new [CharArray] populated by the elements of `this` iterable.
 */
fun Iterable<Char>.toCharArray(): CharArray = mapCharArray { it }

/**
 * Returns a new [ByteArray] populated by the elements of `this` iterable.
 */
fun Iterable<Byte>.toByteArray(): ByteArray = mapByteArray { it }

/**
 * Returns a new [ShortArray] populated by the elements of `this` iterable.
 */
fun Iterable<Short>.toShortArray(): ShortArray = mapShortArray { it }

/**
 * Returns a new [IntArray] populated by the elements of `this` iterable.
 */
fun Iterable<Int>.toIntArray(): IntArray = mapIntArray { it }

/**
 * Returns a new [LongArray] populated by the elements of `this` iterable.
 */
fun Iterable<Long>.toLongArray(): LongArray = mapLongArray { it }

/**
 * Returns a new [FloatArray] populated by the elements of `this` iterable.
 */
fun Iterable<Float>.toFloatArray(): FloatArray = mapFloatArray { it }

/**
 * Returns a new [DoubleArray] populated by the elements of `this` iterable.
 */
fun Iterable<Double>.toDoubleArray(): DoubleArray = mapDoubleArray { it }