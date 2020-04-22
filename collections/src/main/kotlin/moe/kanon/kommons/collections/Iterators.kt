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

@file:JvmName("KIterators")
@file:Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER")

package moe.kanon.kommons.collections

import moe.kanon.kommons.INDEX_NOT_FOUND

private const val EMPTY_ITERATOR = "Can not iterate over a empty iterator"

private object EmptyIterator : Iterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun next(): Nothing = throw UnsupportedOperationException(EMPTY_ITERATOR)
}

private class SingletonIterator<T>(val item: T) : AbstractIterator<T>() {
    override fun computeNext() {
        setNext(item)
        done()
    }
}

private class UnmodifiableIterator<T>(val delegate: Iterator<T>) : Iterator<T> by delegate

/**
 * Returns an [Iterator] instance that iterates over no elements.
 */
@JvmName("empty")
fun <T> emptyIterator(): Iterator<T> = EmptyIterator

/**
 * Returns a new [Iterator] that only iterates over the given [item].
 */
@JvmName("singletonOf")
fun <T> singletonIteratorOf(item: T): Iterator<T> = SingletonIterator(item)

/**
 * Returns an unmodifiable view of `this` iterator.
 *
 * Any attempts to invoke the `remove` operation on the returned instance will result in a
 * [UnsupportedOperationException] being thrown.
 */
@JvmName("unmodifiable")
fun <T> Iterator<T>.asUnmodifiable(): Iterator<T> = UnmodifiableIterator(this)

// -- LIST ITERATOR -- \\
private object EmptyListIterator : ListIterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun hasPrevious(): Boolean = false

    override fun next(): Nothing = throw UnsupportedOperationException(EMPTY_ITERATOR)

    override fun nextIndex(): Int = INDEX_NOT_FOUND

    override fun previous(): Nothing = throw UnsupportedOperationException(EMPTY_ITERATOR)

    override fun previousIndex(): Int = INDEX_NOT_FOUND
}

private class SingletonListIterator<T>(val item: T) : ListIterator<T> {
    private var hasNext = true

    override fun hasNext(): Boolean = hasNext

    override fun hasPrevious(): Boolean = !hasNext

    override fun next(): T = if (hasNext) item.also { hasNext = false } else throw NoSuchElementException()

    override fun nextIndex(): Int = 0

    override fun previous(): T = if (!hasNext) item.also { hasNext = true } else throw NoSuchElementException()

    override fun previousIndex(): Int = 0
}

private class UnmodifiableListIterator<T>(val delegate: ListIterator<T>) : ListIterator<T> by delegate

/**
 * Returns a [ListIterator] instance that iterates over no elements.
 */
fun <T> emptyListIterator(): ListIterator<T> = EmptyListIterator

/**
 * Returns a new [ListIterator] instance that only iterates over the given [item].
 */
fun <T> singletonListIteratorOf(item: T): ListIterator<T> = SingletonListIterator(item)

/**
 * Returns an unmodifiable view of `this` iterator.
 *
 * Any attempts to invoke the `remove`, `set` and/or `add` operations will result in a [UnsupportedOperationException]
 * being thrown.
 */
@JvmName("unmodifiableListIterator")
fun <T> ListIterator<T>.asUnmodifiable(): ListIterator<T> = UnmodifiableListIterator(this)

// -- ZIPPED ITERATOR -- \\
/**
 * An [iterator][Iterator] that returns elements from two iterators, [first] and [second].
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

/**
 * Creates a [ZippedIterator] from `this` iterator and the given [other] instance.
 */
@JvmName("zippedOf")
infix fun <A, B> Iterator<A>.zipWith(other: Iterator<B>): Iterator<Pair<A, B>> = ZippedIterator(this, other)

// -- OTHER -- \\
/**
 * Returns a new [Iterator] that iterates over the given [items].
 */
@JvmName("of")
fun <T> iteratorFor(vararg items: T): Iterator<T> = items.iterator()