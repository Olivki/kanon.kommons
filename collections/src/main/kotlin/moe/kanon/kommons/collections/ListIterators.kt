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

@file:JvmName("ListIterators")

package moe.kanon.kommons.collections

import moe.kanon.kommons.INDEX_NOT_FOUND

private const val EMPTY_LIST_ITERATOR = "Can not iterate over an empty list iterator."

private object EmptyListIterator : ListIterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun hasPrevious(): Boolean = false

    override fun next(): Nothing = throw UnsupportedOperationException(EMPTY_LIST_ITERATOR)

    override fun nextIndex(): Int = INDEX_NOT_FOUND

    override fun previous(): Nothing = throw UnsupportedOperationException(EMPTY_LIST_ITERATOR)

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
@JvmName("empty")
fun <T> emptyListIterator(): ListIterator<T> = EmptyListIterator

/**
 * Returns a new [ListIterator] instance that only iterates over the given [item].
 */
@Deprecated(
    message = "Use 'listIteratorOf(item)' instead.",
    replaceWith = ReplaceWith("listIteratorOf(item)", "moe.kanon.kommons.collections")
)
@JvmName("singletonOf")
fun <T> singletonListIteratorOf(item: T): ListIterator<T> = SingletonListIterator(item)

/**
 * Returns a new [ListIterator] instance that only iterates over the given [item].
 */
@JvmName("of")
fun <T> listIteratorOf(item: T): ListIterator<T> = SingletonListIterator(item)

/**
 * Returns an unmodifiable view of `this` iterator.
 *
 * Any attempts to invoke the `remove`, `set` and/or `add` operations will result in a [UnsupportedOperationException]
 * being thrown.
 */
@JvmName("unmodifiable")
fun <T> ListIterator<T>.asUnmodifiable(): ListIterator<T> = UnmodifiableListIterator(this)