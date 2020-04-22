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

@file:JvmName("KSequences")

package moe.kanon.kommons.collections

import moe.kanon.kommons.requireThat

// -- UTILITY PROPERTIES -- \\
/**
 * Returns the size of `this` [Sequence].
 *
 * Note that this is accomplished by using the [count][Sequence.count] function, which means that depending on how many
 * elements are available in `this` sequence this may be a costly invocation.
 */
@Deprecated(
    message = "Use 'count()' instead.",
    replaceWith = ReplaceWith("this.count()", "kotlin.sequences"),
    level = DeprecationLevel.ERROR
)
val <T> Sequence<T>.size: Int
    @JvmName("sizeOf")
    get() = count()

/**
 * Returns `true` if `this` sequence has no elements, otherwise returns `false`.
 */
val <T> Sequence<T>.isEmpty: Boolean
    get() = none()

/**
 * Returns `true` if `this` sequence has elements, otherwise returns `false`.
 */
val <T> Sequence<T>.isNotEmpty: Boolean
    get() = any()

// -- UTILITY FUNCTIONS -- \\
/**
 * Executes the specified [action] every time an `element` matches the specified [predicate].
 */
inline fun <T> Sequence<T>.onMatch(predicate: (T) -> Boolean, action: (T) -> Unit) {
    for (entry in this) if (predicate(entry)) action(entry)
}

/**
 * Throws the specified [exception] every time an `element` matches the specified [predicate].
 */
inline fun <T, X : Throwable> Sequence<T>.throwOnMatch(predicate: (T) -> Boolean, exception: (T) -> X) =
    this.onMatch(predicate) { throw exception(it) }

/**
 * Returns a new [Sequence] containing all the entries from `this` sequence and the specified [other] sequences, the
 * order of all the elements is preserved.
 */
fun <T> Sequence<T>.concatWith(vararg other: Sequence<T>): Sequence<T> = this + other.asSequence().flatten()

/**
 * Returns a new [Sequence] containing all the entries from `this` sequence and the specified [other] sequences, the
 * order of all the elements is preserved.
 */
infix fun <T> Sequence<T>.concatWith(other: Sequence<Sequence<T>>): Sequence<T> = this + other.flatten()

/**
 * Returns a new [Sequence] containing the cross products of `this` and [that].
 */
infix fun <A, B> Sequence<A>.crossProductOf(that: Sequence<B>): Sequence<Pair<A, B>> =
    this.flatMap { first -> that.map { second -> first to second } }

/**
 * Returns a new [Sequence] that contains all the elements of `this` sequence, except for the ones that also exist in
 * [that] sequence, the elements are then transformed by the specified [transformer].
 */
fun <T, R> Sequence<T>.mapByDifference(that: Sequence<T>, transformer: (T) -> R): Sequence<R> =
    (this - that).map(transformer)

/**
 * Returns a new [Sequence] containing every [nth] element in `this` sequence.
 */
fun <T> Sequence<T>.everyNth(nth: Int): Sequence<T> = this.windowed(nth, nth, partialWindows = false).map { it.last() }

/**
 * Returns [n] maximum elements from `this`, or returns `this` *(sorted descending)* if `n` is greater than or equal to
 * the size of `this`.
 */
fun <T : Comparable<T>> Sequence<T>.takeMax(n: Int): Sequence<T> = this.sortedDescending().take(n)

/**
 * Returns [n] minimum elements from `this`, or returns `this` *(sorted)* if `n` is greater than or equal to
 * the size of `this`.
 */
fun <T : Comparable<T>> Sequence<T>.takeMin(n: Int): Sequence<T> = this.sorted().take(n)

// -- TERMINAL FUNCTIONS -- \\
/**
 * Returns the hash-code from combining all the elements in `this` sequence.
 *
 * The operation is _terminal_.
 */
fun Sequence<*>.calculateHashCode(): Int = fold(1) { hash, element -> 31 * hash + (element?.hashCode() ?: 0) }

/**
 * Returns `true` if `this` sequence contains the exact same elements as the specified [other] sequence, `false`
 * otherwise.
 *
 * The operation is _terminal_.
 */
infix fun Sequence<*>.hasSameElementsAs(other: Sequence<*>): Boolean {
    if (other.count() != this.count()) return false

    val otherIterator = other.iterator()
    for (elem in this) {
        val elemOther = otherIterator.next()
        if (elem != elemOther) return false
    }

    return true
}

/**
 * Returns `true` if all elements in `this` sequence are the same or `false` if they are not.
 *
 * The operation is _terminal_.
 */
fun <T> Sequence<T>.allEqual(): Boolean = if (this.isEmpty) false else this.all { it == first() }

/**
 * Returns how many times the specified [item] appears in `this` sequence.
 *
 * The operation is _terminal_.
 */
fun <T> Sequence<T>.occurrencesOf(item: T): Int = this.count { it == item }

/**
 * Returns [n] amount of random elements from `this` sequence.
 *
 * The operation is _terminal_.
 */
fun <T> Sequence<T>.sampleSize(n: Int): List<T> {
    requireThat(n <= count()) { "'n' is larger than sequence size" }
    return this.toList().shuffled().take(n)
}

/**
 * Circularly rotates the elements of `this` sequence by [n] amount to the left and returns the result.
 *
 * The operation is _terminal_.
 *
 * @throws [IllegalArgumentException] if [n] is negative
 */
infix fun <T> Sequence<T>.rotateLeft(n: Int): List<T> {
    requireThat(n >= 0) { "Can't rotate by a negative amount" }
    val list = this.toList()
    return list.slice(n until list.size) + list.slice(0 until n)
}

/**
 * Circularly rotates the elements of `this` sequence by [n] amount to the right and returns the result.
 *
 * The operation is _terminal_.
 *
 * @throws [IllegalArgumentException] if [n] is negative
 */
infix fun <T> Sequence<T>.rotateRight(n: Int): List<T> {
    requireThat(n >= 0) { "Can't rotate by a negative amount" }
    val list = this.toList()
    return list.takeLast(n % list.size) + list.dropLast(n % list.size)
}

/**
 * Applies [transformer] against an accumulator and each element in `this` *(from left to right)*, and returns the
 * result.
 *
 * The operation is _terminal_.
 */
inline fun <T, R> Sequence<T>.scan(identity: R, transformer: (R, T) -> R): List<R> =
    this.fold(emptyList()) { acc, item -> acc + transformer(acc.lastOrNull() ?: identity, item) }

/**
 * Returns a [List] containing all the computed permutations available for the elements of `this`.
 *
 * **NOTE**: This implementation uses non stack safe recursion.
 *
 * The operation is _terminal_.
 */
fun <T> Sequence<T>.permutations(): TwoDimList<T> = when (this.count()) {
    0 -> emptyList()
    1 -> listOf(this.toList())
    else -> foldIndexed(ArrayList()) { index, acc, item ->
        acc.fillWith(this.toList().removeAtIndex(index).permutations().map { item prependTo it })
    }
}

/**
 * Returns a new [List] containing all the elements of `this`, with the specified [element] inserted between all of
 * them.
 *
 * The operation is _terminal_.
 */
infix fun <T> Sequence<T>.intersperse(element: T): List<T> = List(this.count()) { listOf(this.elementAt(it), element) }
    .flatten()
    .dropLast(1)

/**
 * Returns `true` if only *one* element in `this` sequence matches the specified [predicate], otherwise returns `false`.
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.unique(predicate: (T) -> Boolean): Boolean = this.count(predicate) == 1

/**
 * Returns whether or not `this` sequence contains the specified [content].
 *
 * The operation is _terminal_.
 */
tailrec infix operator fun <T> Sequence<T>.contains(content: Sequence<T>): Boolean = when {
    content.isEmpty -> true
    this.isEmpty -> content.isEmpty
    this.take(content.count()) == content -> true
    else -> this.drop(1) contains content
}

/**
 * Returns a [IntArray] containing all the sequence of the specified [element].
 *
 * The operation is _terminal_.
 */
infix fun <T> Sequence<T>.indicesOf(element: T): IntArray = this.asSequence()
    .withIndex()
    .filter { it.value == element }
    .mapToIntArray { it.index }

// -- ARRAY CONVERSION FUNCTIONS -- \\
/*
 *  Due to the fact that sequences do not actually store any data regarding the size of itself, these array functions
 * are all implemented individually to make sure that they perform as fast as possible without having any extra overhead
 * from invoking 'count' multiple times.
 *
 * i.e; the 'Sequence<T>.mapToTypedArray(transform: (T) -> R)' function could just be implemented like this
 * ```kotlin
 *  fun <T, R> Sequence<T>.mapToTypedArray(transform: (T) -> R): Array<R> = mapTo(createArray<R>(this.count()), transform)
 * ```
 * but that would mean that the 'count()' function would be invoked *twice*, and depending on how many elements the
 * sequence is working on, that might cause some serious overhead, overhead which is completely not needed, as we know
 * that the array that we provide is of the correct size.
 *
 * One could also say that we should just remove the 'requireThat' check in each 'mapTo' function, but that would go
 * against the behaviour of the other 'mapTo' functions provided by the std-lib, and that might cause behaviour that
 * the user did not intend for.
 */

// mapTo
/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to the
 * given [destination].
 *
 * The operation is _terminal_.
 */
inline fun <T, R> Sequence<T>.mapTo(destination: Array<R>, transform: (T) -> R): Array<R> {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to the
 * given [destination].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapTo(destination: BooleanArray, transform: (T) -> Boolean): BooleanArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to the
 * given [destination].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapTo(destination: CharArray, transform: (T) -> Char): CharArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to the
 * given [destination].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapTo(destination: ByteArray, transform: (T) -> Byte): ByteArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to the
 * given [destination].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapTo(destination: ShortArray, transform: (T) -> Short): ShortArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to the
 * given [destination].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapTo(destination: IntArray, transform: (T) -> Int): IntArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to the
 * given [destination].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapTo(destination: LongArray, transform: (T) -> Long): LongArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to the
 * given [destination].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapTo(destination: FloatArray, transform: (T) -> Float): FloatArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to the
 * given [destination].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapTo(destination: DoubleArray, transform: (T) -> Double): DoubleArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(element)
    return destination
}

// map...Array
/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to a
 * newly created [Array].
 *
 * The operation is _terminal_.
 */
inline fun <T, R> Sequence<T>.mapToTypedArray(transform: (T) -> R): Array<R> {
    val destination = createArray<R>(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to a
 * newly created [BooleanArray].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapToBooleanArray(transform: (T) -> Boolean): BooleanArray {
    val destination = BooleanArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to a
 * newly created [CharArray].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapToCharArray(transform: (T) -> Char): CharArray {
    val destination = CharArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to a
 * newly created [ByteArray].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapToByteArray(transform: (T) -> Byte): ByteArray {
    val destination = ByteArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to a
 * newly created [ShortArray].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapToShortArray(transform: (T) -> Short): ShortArray {
    val destination = ShortArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to a
 * newly created [IntArray].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapToIntArray(transform: (T) -> Int): IntArray {
    val destination = IntArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to a
 * newly created [LongArray].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapToLongArray(transform: (T) -> Long): LongArray {
    val destination = LongArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to a
 * newly created [FloatArray].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapToFloatArray(transform: (T) -> Float): FloatArray {
    val destination = FloatArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(element)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original sequence and appends the results to a
 * newly created [DoubleArray].
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.mapToDoubleArray(transform: (T) -> Double): DoubleArray {
    val destination = DoubleArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(element)
    return destination
}

// mapIndexedTo
/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to the given [destination].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T, R> Sequence<T>.mapIndexedTo(destination: Array<R>, transform: (i: Int, e: T) -> R): Array<R> {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to the given [destination].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedTo(destination: BooleanArray, transform: (i: Int, e: T) -> Boolean): BooleanArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to the given [destination].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedTo(destination: CharArray, transform: (i: Int, e: T) -> Char): CharArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to the given [destination].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedTo(destination: ByteArray, transform: (i: Int, e: T) -> Byte): ByteArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to the given [destination].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedTo(destination: ShortArray, transform: (i: Int, e: T) -> Short): ShortArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to the given [destination].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedTo(destination: IntArray, transform: (i: Int, e: T) -> Int): IntArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to the given [destination].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedTo(destination: LongArray, transform: (i: Int, e: T) -> Long): LongArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to the given [destination].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedTo(destination: FloatArray, transform: (i: Int, e: T) -> Float): FloatArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to the given [destination].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedTo(destination: DoubleArray, transform: (i: Int, e: T) -> Double): DoubleArray {
    var index = 0
    for (element in this.take(destination.size)) destination[index++] = transform(index, element)
    return destination
}

// mapIndexed...Array
/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to a newly created [Array].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T, R> Sequence<T>.mapIndexedToTypedArray(transform: (i: Int, e: T) -> R): Array<R> {
    val destination = createArray<R>(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to a newly created [BooleanArray].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedToBooleanArray(transform: (i: Int, e: T) -> Boolean): BooleanArray {
    val destination = BooleanArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to a newly created [CharArray].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedToCharArray(transform: (i: Int, e: T) -> Char): CharArray {
    val destination = CharArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to a newly created [ByteArray].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedToByteArray(transform: (i: Int, e: T) -> Byte): ByteArray {
    val destination = ByteArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to a newly created [ShortArray].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedToShortArray(transform: (i: Int, e: T) -> Short): ShortArray {
    val destination = ShortArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to a newly created [IntArray].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedToIntArray(transform: (i: Int, e: T) -> Int): IntArray {
    val destination = IntArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to a newly created [LongArray].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedToLongArray(transform: (i: Int, e: T) -> Long): LongArray {
    val destination = LongArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to a newly created [FloatArray].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedToFloatArray(transform: (i: Int, e: T) -> Float): FloatArray {
    val destination = FloatArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original sequence and appends the
 * results to a newly created [DoubleArray].
 *
 * The operation is _terminal_.
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
inline fun <T> Sequence<T>.mapIndexedToDoubleArray(transform: (i: Int, e: T) -> Double): DoubleArray {
    val destination = DoubleArray(this.count())
    var index = 0
    for (element in this) destination[index++] = transform(index, element)
    return destination
}

// to...Array
/**
 * Returns a new generic [Array] populated by the elements of `this` iterable.
 *
 * The operation is _terminal_.
 */
fun <T> Sequence<T>.toTypedArray(): Array<T> {
    val result = createArray<T>(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [CharArray] containing the elements of `this` sequence.
 *
 * The operation is _terminal_.
 */
fun Sequence<Char>.toCharArray(): CharArray {
    val result = CharArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [BooleanArray] containing the elements of `this` sequence.
 *
 * The operation is _terminal_.
 */
fun Sequence<Boolean>.toBooleanArray(): BooleanArray {
    val result = BooleanArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [ByteArray] containing the elements of `this` sequence.
 *
 * The operation is _terminal_.
 */
fun Sequence<Byte>.toByteArray(): ByteArray {
    val result = ByteArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [ShortArray] containing the elements of `this` sequence.
 *
 * The operation is _terminal_.
 */
fun Sequence<Short>.toShortArray(): ShortArray {
    val result = ShortArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [IntArray] containing the elements of `this` sequence.
 *
 * The operation is _terminal_.
 */
fun Sequence<Int>.toIntArray(): IntArray {
    val result = IntArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [LongArray] containing the elements of `this` sequence.
 *
 * The operation is _terminal_.
 */
fun Sequence<Long>.toLongArray(): LongArray {
    val result = LongArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [FloatArray] containing the elements of `this` sequence.
 *
 * The operation is _terminal_.
 */
fun Sequence<Float>.toFloatArray(): FloatArray {
    val result = FloatArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}

/**
 * Returns a new [DoubleArray] containing the elements of `this` sequence.
 *
 * The operation is _terminal_.
 */
fun Sequence<Double>.toDoubleArray(): DoubleArray {
    val result = DoubleArray(this.count())
    var index = 0
    for (element in this) result[index++] = element
    return result
}
