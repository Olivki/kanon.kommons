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

@file:JvmName("KLists")
@file:Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER")

package moe.kanon.kommons.collections

import moe.kanon.kommons.INDEX_NOT_FOUND
import moe.kanon.kommons.requireThat
import kotlin.NoSuchElementException

typealias TwoDimList<T> = List<List<T>>

// -- CLASSES -- \\
/**
 * Represents a [List] which can *not* be modified.
 *
 * Please note that when used from the Java side, any attempts to invoke any of the functions that modify the data
 * of the list in any way will result in a [UnsupportedOperationException] being thrown.
 *
 * @property [delegate] The backing instance that `this` list calls back to.
 */
open class ImmutableList<out E>(private val delegate: List<E>) : List<E> by delegate {
    companion object {
        /**
         * Creates a new [immutable list][ImmutableList] where each element is calculated by calling the specified
         * [init] function.
         *
         * @param [size] the size of the list
         * @param [init] the function that calculates each element, this should return a list element based on the
         * given `index`.
         */
        @JvmName("create")
        @JvmStatic inline operator fun <E> invoke(size: Int, init: (index: Int) -> E): ImmutableList<E> =
            ImmutableList(List(size, init))
    }

    override fun iterator(): ImmutableIterator<E> = ImmutableIterator(delegate.iterator())

    override fun listIterator(): ImmutableListIterator<E> = ImmutableListIterator(delegate.listIterator())

    override fun listIterator(index: Int): ImmutableListIterator<E> =
        ImmutableListIterator(delegate.listIterator(index))

    override fun subList(fromIndex: Int, toIndex: Int): ImmutableList<E> =
        ImmutableList(delegate.subList(fromIndex, toIndex))
}

/**
 * Represents a [List] that only contains one [element].
 *
 * @property [element] The underlying element that `this` list is storing.
 */
class SingletonList<out E>(private val element: E) : List<E> {
    override val size: Int = 1

    override fun contains(element: @UnsafeVariance E): Boolean = element == this.element

    override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean = elements.all { it == element }

    override fun get(index: Int): E = if (index == 0) element else throw IndexOutOfBoundsException()

    override fun indexOf(element: @UnsafeVariance E): Int = if (element == this.element) 0 else INDEX_NOT_FOUND

    override fun isEmpty(): Boolean = false

    override fun iterator(): Iterator<E> = SingletonIterator(element)

    override fun lastIndexOf(element: @UnsafeVariance E): Int = if (element == this.element) 0 else INDEX_NOT_FOUND

    override fun listIterator(): ListIterator<E> = SingletonListIterator(element)

    override fun listIterator(index: Int): ListIterator<E> =
        if (index == 0) SingletonListIterator(element) else EmptyListIterator

    override fun subList(fromIndex: Int, toIndex: Int): List<E> =
        if (fromIndex == 0 && toIndex == 0) this else emptyList()
}

// -- FACTORY FUNCTIONS -- \\
/**
 * Returns a new [List] that *only* contains the specified [element].
 */
@JvmName("singletonOf")
fun <T> singletonList(element: T): List<T> = SingletonList(element)

// Immutable
/**
 * Returns a new [immutable list][ImmutableList] containing the given [elements].
 */
@JvmName("immutableOf")
fun <T> immutableListOf(vararg elements: T): ImmutableList<T> = when (elements.size) {
    0 -> ImmutableList(emptyList())
    1 -> ImmutableList(singletonList(elements[0]))
    else -> ImmutableList(elements.toCollection(ArrayList()))
}

/**
 * Returns a new [immutable list][ImmutableList] containing the given [elements].
 *
 * The [delegate][ImmutableList.delegate] property will be set to the specified [backing] parameter.
 */
fun <T> immutableListOf(backing: MutableList<T>, vararg elements: T): ImmutableList<T> =
    ImmutableList(backing.fillWith(elements))

/**
 * Returns a [immutable list][ImmutableList] that is wrapped around `this` list.
 */
@JvmName("toImmutable")
fun <T> List<T>.toImmutableList(): ImmutableList<T> = ImmutableList(this)

/**
 * Creates a new two-dimensional list from the specified [width] and [height], with all values set to the specified
 * [default].
 */
@JvmName("twoDimensionalList")
fun <T> TwoDimList(width: Int, height: Int, default: T): TwoDimList<T> =
    List(width) { List(height) { default } }

// -- UTILITY FUNCTIONS -- \\
/**
 * Returns the first element in `this` list.
 *
 * @throws [NoSuchElementException] if `this` list is empty.
 */
val <T> List<T>.head: T @JvmName("headOf") get() = this.first()

/**
 * Returns all the elements in `this` list except for the first.
 */
val <T> List<T>.tail: List<T> @JvmName("tailOf") get() = this.drop(1)

/**
 * Returns `true` if all elements in `this` iterable are the same or `false` if they are not.
 */
fun <T> List<T>.allEqual(): Boolean = if (this.isEmpty()) false else this.all { it == this[0] }

/**
 * Returns whether or not `this` list starts with [that] list.
 */
infix fun <T> List<T>.startsWith(that: Iterable<T>): Boolean = this.take(that.size) == that

/**
 * Returns whether or not `this` list ends with [that] list.
 */
infix fun <T> List<T>.endsWith(that: Iterable<T>): Boolean = this.takeLast(that.size) == that

/**
 * Returns a copy of `this` list with the element at the specified [index] removed.
 */
fun <T> List<T>.removeAtIndex(index: Int): List<T> = take(index) + drop(index + 1)

// slice
/**
 * Returns a list containing all the entries of `this` list starting from the specified [start index][start] until the
 * end of `this` list.
 */
@JvmName("sliceFrom")
infix fun <T> List<T>.from(start: Int): List<T> {
    requireThat(size > start) { "Given index is larger than list size ($start > $size)" }
    requireThat(start >= 0) { "Given index can not be negative" }
    return this.slice(start until size)
}

/**
 * Returns a list containing all the entries of `this` list starting from the [first index of][List.indexOf] the
 * specified [element], or an empty list if `this` list does not contain `element`.
 */
@JvmName("sliceFromFirst")
infix fun <T> List<T>.fromFirst(element: T): List<T> =
    if (element in this) this.slice(this.indexOf(element) until size) else emptyList()

/**
 * Returns a list containing all the entries of `this` list starting from the [last index of][List.lastIndexOf] the
 * specified [element], or an empty list if `this` list does not contain `element`.
 */
@JvmName("sliceFromLast")
infix fun <T> List<T>.fromLast(element: T): List<T> =
    if (element in this) this.slice(this.lastIndexOf(element) until size) else emptyList()

/**
 * Returns a list containing all the entries of `this` list starting from the beginning of `this` list until the
 * specified [end index][end].
 */
@JvmName("sliceUntil")
infix fun <T> List<T>.until(end: Int): List<T> {
    requireThat(size > end) { "Given index is larger than list size ($end > $size)" }
    requireThat(end >= 0) { "Given index can not be negative" }
    return this.slice(0..end)
}

/**
 * Returns a list containing all the entries of `this` list starting from the beginning of `this` list until the
 * [first index of][List.indexOf] the specified [element], or an empty list if `this` list does not contain `element`.
 */
@JvmName("sliceUntilFirst")
infix fun <T> List<T>.untilFirst(element: T): List<T> =
    if (element in this) this.slice(0..this.indexOf(element)) else emptyList()

/**
 * Returns a list containing all the entries of `this` list starting from the beginning of `this` list until the
 * [last index of][List.lastIndexOf] the specified [element], or an empty list if `this` list does not contain `element`.
 */
@JvmName("sliceUntilLast")
infix fun <T> List<T>.untilLast(element: T): List<T> =
    if (element in this) this.slice(0..this.lastIndexOf(element)) else emptyList()