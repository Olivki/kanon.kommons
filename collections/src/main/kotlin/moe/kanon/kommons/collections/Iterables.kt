/*
 * Copyright 2019-2020 Oliver Berg
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

import moe.kanon.kommons.require

// -- UTILITY PROPERTIES -- \\
/**
 * Returns the count of how many elements are contained within `this` iterable.
 */
@Deprecated(
    message = "Use 'count()' instead.",
    replaceWith = ReplaceWith("this.count()", "kotlin.collections")
)
val <T> Iterable<T>.size: Int
    @JvmName("sizeOf")
    get() = when (this) {
        is Collection -> size
        else -> count()
    }

/**
 * Returns the first element in `this` iterable.
 *
 * @throws [NoSuchElementException] if `this` list is iterable.
 */
val <T> Iterable<T>.head: T
    @JvmName("headOf")
    get() = first()

/**
 * Returns all the elements in `this` iterable except for the first.
 */
val <T> Iterable<T>.tail: List<T>
    @JvmName("tailOf")
    get() = drop(1)

/**
 * Returns `true` if `this` iterable has no elements to iterate over or `false` if it does.
 */
@Deprecated(
    message = "Use 'none()' instead.",
    replaceWith = ReplaceWith("this.none()", "kotlin.collections")
)
val Iterable<*>.isEmpty: Boolean
    get() = none()

/**
 * Returns `false` if `this` iterable has no elements to iterate over or `true` if it does.
 */
@Deprecated(
    message = "Use 'any()' instead.",
    replaceWith = ReplaceWith("this.any()", "kotlin.collections")
)
val Iterable<*>.isNotEmpty: Boolean
    get() = any()

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
fun Iterable<*>.calculateHashCode(): Int = fold(1) { hash, element -> 31 * hash + (element?.hashCode() ?: 0) }

/**
 * Returns `true` if `this` iterable contains the exact same elements as the specified [other] iterable, `false`
 * otherwise.
 */
infix fun Iterable<*>.hasSameElementsAs(other: Iterable<*>): Boolean {
    if (other.count() != this.count()) return false

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
fun <T> Iterable<T>.allEqual(): Boolean = if (this.none<Any?>()) false else this.all { it == first() }

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
    content.none() -> true
    this.none() -> content.none()
    this.take(content.count()) == content -> true
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
infix fun <T> Iterable<T>.intersperse(element: T): List<T> = List(this.count()) { listOf(this.elementAt(it), element) }
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
fun <T> Iterable<T>.permutations(): TwoDimList<T> = when (this.count()) {
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
    require(n >= 0) { "Can't rotate by a negative amount" }
    val list = this.toList()
    return list.slice(n until list.size) + list.slice(0 until n)
}

/**
 * Circularly rotates the elements of `this` iterable by [n] amount to the right and returns the result.
 *
 * @throws [IllegalArgumentException] if [n] is negative
 */
infix fun <T> Iterable<T>.rotateRight(n: Int): List<T> {
    require(n >= 0) { "Can't rotate by a negative amount" }
    val list = this.toList()
    return list.takeLast(n % list.size) + list.dropLast(n % list.size)
}

/**
 * Returns [n] amount of random elements from `this` iterable.
 */
fun <T> Iterable<T>.sampleSize(n: Int): List<T> {
    require(n <= count()) { "'n' is larger than collection size" }
    return this.shuffled().take(n)
}

/**
 * Returns a new [List] based on the elements of `this` iterable, with certain elements removed/added.
 *
 * @param [elements] the new elements to add to the list, starting from [startIndex]
 * @param [startIndex] the index at which the modification should start
 * @param [deleteCount] the amount of elements to remove, starting from [startIndex]
 */
@JvmOverloads
fun <T> Iterable<T>.shank(vararg elements: T, startIndex: Int = 0, deleteCount: Int = 0): List<T> {
    val list = this.toList()
    return list.slice(0 until startIndex) + elements + list.drop(startIndex + deleteCount)
}

// -- ARRAY CONVERSION FUNCTIONS -- \\
// mapTo
/**
 * Applies the given [transform] function to each element of the original collection and appends the results to the
 * given [destination].
 */
inline fun <T, R> Iterable<T>.mapTo(destination: Array<R>, transform: (T) -> R): Array<R> {
    require(this.count() <= destination.size, "this.size <= target.size")
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to the
 * given [destination].
 */
inline fun <T> Iterable<T>.mapTo(destination: BooleanArray, transform: (T) -> Boolean): BooleanArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to the
 * given [destination].
 */
inline fun <T> Iterable<T>.mapTo(destination: CharArray, transform: (T) -> Char): CharArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to the
 * given [destination].
 */
inline fun <T> Iterable<T>.mapTo(destination: ByteArray, transform: (T) -> Byte): ByteArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to the
 * given [destination].
 */
inline fun <T> Iterable<T>.mapTo(destination: ShortArray, transform: (T) -> Short): ShortArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to the
 * given [destination].
 */
inline fun <T> Iterable<T>.mapTo(destination: IntArray, transform: (T) -> Int): IntArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to the
 * given [destination].
 */
inline fun <T> Iterable<T>.mapTo(destination: LongArray, transform: (T) -> Long): LongArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to the
 * given [destination].
 */
inline fun <T> Iterable<T>.mapTo(destination: FloatArray, transform: (T) -> Float): FloatArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to the
 * given [destination].
 */
inline fun <T> Iterable<T>.mapTo(destination: DoubleArray, transform: (T) -> Double): DoubleArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

// map...Array
/**
 * Applies the given [transform] function to each element of the original collection and appends the results to a
 * newly created [Array].
 */
inline fun <T, reified R> Iterable<T>.mapToTypedArray(transform: (T) -> R): Array<R> = mapTo(createArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to a
 * newly created [BooleanArray].
 */
inline fun <T> Iterable<T>.mapToBooleanArray(transform: (T) -> Boolean): BooleanArray =
    mapTo(BooleanArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to a
 * newly created [CharArray].
 */
inline fun <T> Iterable<T>.mapToCharArray(transform: (T) -> Char): CharArray = mapTo(CharArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to a
 * newly created [ByteArray].
 */
inline fun <T> Iterable<T>.mapToByteArray(transform: (T) -> Byte): ByteArray = mapTo(ByteArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to a
 * newly created [ShortArray].
 */
inline fun <T> Iterable<T>.mapToShortArray(transform: (T) -> Short): ShortArray =
    mapTo(ShortArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to a
 * newly created [IntArray].
 */
inline fun <T> Iterable<T>.mapToIntArray(transform: (T) -> Int): IntArray = mapTo(IntArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to a
 * newly created [LongArray].
 */
inline fun <T> Iterable<T>.mapToLongArray(transform: (T) -> Long): LongArray = mapTo(LongArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to a
 * newly created [FloatArray].
 */
inline fun <T> Iterable<T>.mapToFloatArray(transform: (T) -> Float): FloatArray =
    mapTo(FloatArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element of the original collection and appends the results to a
 * newly created [DoubleArray].
 */
inline fun <T> Iterable<T>.mapToDoubleArray(transform: (T) -> Double): DoubleArray =
    mapTo(DoubleArray(this.count()), transform)

// mapIndexedTo
/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T, R> Iterable<T>.mapIndexedTo(destination: Array<R>, transform: (i: Int, e: T) -> R): Array<R> {
    require(this.count() <= destination.size, "this.size <= target.size")
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedTo(destination: BooleanArray, transform: (i: Int, e: T) -> Boolean): BooleanArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedTo(destination: CharArray, transform: (i: Int, e: T) -> Char): CharArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedTo(destination: ByteArray, transform: (i: Int, e: T) -> Byte): ByteArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedTo(destination: ShortArray, transform: (i: Int, e: T) -> Short): ShortArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedTo(destination: IntArray, transform: (i: Int, e: T) -> Int): IntArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedTo(destination: LongArray, transform: (i: Int, e: T) -> Long): LongArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedTo(destination: FloatArray, transform: (i: Int, e: T) -> Float): FloatArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedTo(destination: DoubleArray, transform: (i: Int, e: T) -> Double): DoubleArray {
    require(this.count() <= destination.size, "this.size <= target.size")
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

// mapIndexed...Array
/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to a newly created [Array].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T, reified R> Iterable<T>.mapIndexedToTypedArray(transform: (i: Int, e: T) -> R): Array<R> =
    mapIndexedTo(createArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to a newly created [BooleanArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedToBooleanArray(transform: (i: Int, e: T) -> Boolean): BooleanArray =
    mapIndexedTo(BooleanArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to a newly created [CharArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedToCharArray(transform: (i: Int, e: T) -> Char): CharArray =
    mapIndexedTo(CharArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to a newly created [ByteArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedToByteArray(transform: (i: Int, e: T) -> Byte): ByteArray =
    mapIndexedTo(ByteArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to a newly created [ShortArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedToShortArray(transform: (i: Int, e: T) -> Short): ShortArray =
    mapIndexedTo(ShortArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to a newly created [IntArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedToIntArray(transform: (i: Int, e: T) -> Int): IntArray =
    mapIndexedTo(IntArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to a newly created [LongArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedToLongArray(transform: (i: Int, e: T) -> Long): LongArray =
    mapIndexedTo(LongArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to a newly created [FloatArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedToFloatArray(transform: (i: Int, e: T) -> Float): FloatArray =
    mapIndexedTo(FloatArray(this.count()), transform)

/**
 * Applies the given [transform] function to each element and its index in the original collection and appends the
 * results to a newly created [DoubleArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Iterable<T>.mapIndexedToDoubleArray(transform: (i: Int, e: T) -> Double): DoubleArray =
    mapIndexedTo(DoubleArray(this.count()), transform)

// to...Array
/**
 * Returns a new generic [Array] populated by the elements of `this` iterable.
 */
inline fun <reified T> Iterable<T>.toTypedArray(): Array<T> = mapToTypedArray { it }

/**
 * Returns a new [BooleanArray] populated by the elements of `this` iterable.
 */
fun Iterable<Boolean>.toBooleanArray(): BooleanArray = mapToBooleanArray { it }

/**
 * Returns a new [CharArray] populated by the elements of `this` iterable.
 */
fun Iterable<Char>.toCharArray(): CharArray = mapToCharArray { it }

/**
 * Returns a new [ByteArray] populated by the elements of `this` iterable.
 */
fun Iterable<Byte>.toByteArray(): ByteArray = mapToByteArray { it }

/**
 * Returns a new [ShortArray] populated by the elements of `this` iterable.
 */
fun Iterable<Short>.toShortArray(): ShortArray = mapToShortArray { it }

/**
 * Returns a new [IntArray] populated by the elements of `this` iterable.
 */
fun Iterable<Int>.toIntArray(): IntArray = mapToIntArray { it }

/**
 * Returns a new [LongArray] populated by the elements of `this` iterable.
 */
fun Iterable<Long>.toLongArray(): LongArray = mapToLongArray { it }

/**
 * Returns a new [FloatArray] populated by the elements of `this` iterable.
 */
fun Iterable<Float>.toFloatArray(): FloatArray = mapToFloatArray { it }

/**
 * Returns a new [DoubleArray] populated by the elements of `this` iterable.
 */
fun Iterable<Double>.toDoubleArray(): DoubleArray = mapToDoubleArray { it }