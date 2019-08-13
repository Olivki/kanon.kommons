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

@file:JvmName("KIterators")
@file:Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER")

package moe.kanon.kommons.collections

import moe.kanon.kommons.INDEX_NOT_FOUND

private const val EMPTY_ITERATOR = "Can not iterate over a empty iterator"

// -- CLASSES -- \\
// -- ITERATOR -- \\
/**
 * An [Iterator] that does not allow any of its elements to be modified.
 */
class UnmodifiableIterator<out T>(private val delegate: Iterator<T>) : Iterator<T> by delegate

/**
 * Returns `this` iterator wrapped in a [UnmodifiableIterator], which does not allow any of it's elements to be
 * modified.
 */
fun <T> Iterator<T>.asUnmodifiable(): Iterator<T> = UnmodifiableIterator(this)

/**
 * Represents a [iterator][Iterator] that only iterates over one element.
 *
 * @property [element] The single value that `this` iterator iterates over.
 */
class SingletonIterator<out T>(private val element: T) : Iterator<T> {
    private var hasNext: Boolean = true

    override fun hasNext(): Boolean = hasNext

    override fun next(): T = if (hasNext) element.also { hasNext = false } else throw NoSuchElementException()
}

/**
 * Represents a [iterator][Iterator] that iterates over nothing.
 */
object EmptyIterator : Iterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun next(): Nothing = throw UnsupportedOperationException(EMPTY_ITERATOR)
}

// -- LIST ITERATOR -- \\
/**
 * A [ListIterator] that does not allow any of its elements to be modified.
 */
class UnmodifiableListIterator<out T>(private val delegate: ListIterator<T>) : ListIterator<T> by delegate

/**
 * Returns `this` iterator wrapped in a [UnmodifiableListIterator], which does not allow any of it's elements to be
 * modified.
 */
fun <T> ListIterator<T>.asUnmodifiable(): ListIterator<T> = UnmodifiableListIterator(this)

/**
 * Represents a [list-iterator][ListIterator] that iterates over a single object.
 */
class SingletonListIterator<out T>(private val item: T) : ListIterator<T> {
    private var hasNext = true

    override fun hasNext(): Boolean = hasNext

    override fun hasPrevious(): Boolean = !hasNext

    override fun next(): T = if (hasNext) item.also { hasNext = false } else throw NoSuchElementException()

    override fun nextIndex(): Int = 0

    override fun previous(): T = if (!hasNext) item.also { hasNext = true } else throw NoSuchElementException()

    override fun previousIndex(): Int = 0
}

/**
 * Represents a [ListIterator] that iterates over no elements.
 */
object EmptyListIterator : ListIterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun hasPrevious(): Boolean = false

    override fun next(): Nothing = throw UnsupportedOperationException(EMPTY_ITERATOR)

    override fun nextIndex(): Int = INDEX_NOT_FOUND

    override fun previous(): Nothing = throw UnsupportedOperationException(EMPTY_ITERATOR)

    override fun previousIndex(): Int = INDEX_NOT_FOUND
}

// -- ZIPPED ITERATOR -- \\
/**
 * Represents a [iterator][Iterator] that returns elements from two iterators, [first] and [second].
 *
 * @property [first] The first [Iterator] instance that `this` zipped-iterator uses.
 * @property [second] The second [Iterator] instance that `this` zipped-iterator uses.
 */
class ZippedIterator<out A, out B>(
    private val first: Iterator<A>,
    private val second: Iterator<B>
) : Iterator<Pair<A, B>> {
    override fun hasNext(): Boolean = first.hasNext() && second.hasNext()

    override fun next(): Pair<A, B> = first.next() to second.next()
}

// -- FACTORY FUNCTIONS -- \\
fun <T> emptyIterator(): Iterator<T> = EmptyIterator

fun <T> emptyListIterator(): ListIterator<T> = EmptyListIterator

/**
 * Creates a [ZippedIterator] from `this` iterator and the given [other] instance.
 */
@JvmName("zippedOf")
infix fun <A, B> Iterator<A>.zipWith(other: Iterator<B>): Iterator<Pair<A, B>> = ZippedIterator(this, other)