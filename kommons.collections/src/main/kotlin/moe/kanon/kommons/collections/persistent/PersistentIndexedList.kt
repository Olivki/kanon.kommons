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

package moe.kanon.kommons.collections.persistent

import java.util.RandomAccess

/**
 * A [PersistentList] implementation that allows for fast random access to the stored elements via the
 * [get(index: Int)][PersistentIndexedList.get] function.
 */
interface PersistentIndexedList<out T> : PersistentList<T>, RandomAccess {
    /**
     * Returns the [element][T] stored under the specified [index], or throws a [IndexOutOfBoundsException] if the
     * specified `index` is outside of the bounds of `this` list.
     */
    operator fun get(index: Int): T

    /**
     * Returns the first index of the specified [element], or `-1` if the specified `element` is not stored in `this`
     * list.
     */
    fun indexOf(element: @UnsafeVariance T): Int

    /**
     * Returns the last index of the specified [element], or `-1` if the specified `element` is not stored in `this`
     * list.
     */
    fun lastIndexOf(element: @UnsafeVariance T): Int

    /**
     * Returns a new [list][PersistentIndexedList] containing the same elements as `this` list, except the element
     * located at the specified [index].
     *
     * @throws [IndexOutOfBoundsException] if the specified [index] is not inside of the bounds of `this` list
     */
    fun removeAt(index: Int): PersistentIndexedList<T>

    // -- OVERRIDES -- \\
    // -- PREPEND -- \\
    /**
     * Returns a new [list][PersistentIndexedList] containing the specified [element] and the elements of `this` list.
     */
    override fun prepend(element: @UnsafeVariance T): PersistentIndexedList<T>

    /**
     * Returns a new [list][PersistentIndexedList] containing the specified [elements] and the elements of `this` list.
     */
    override fun prepend(elements: Iterable<@UnsafeVariance T>): PersistentIndexedList<T>

    /**
     * Returns a new [list][PersistentIndexedList] containing the specified [elements] and the elements of `this` list.
     */
    override fun prepend(elements: Array<out @UnsafeVariance T>): PersistentIndexedList<T>

    /**
     * Returns a new [list][PersistentIndexedList] containing the specified [elements] and the elements of `this` list.
     */
    override fun prepend(elements: Sequence<@UnsafeVariance T>): PersistentIndexedList<T>

    // -- APPEND -- \\
    /**
     * Returns a new [list][PersistentIndexedList] containing the elements of `this` list and the specified [element].
     */
    override fun append(element: @UnsafeVariance T): PersistentIndexedList<T>

    /**
     * Returns a new [list][PersistentIndexedList] containing the elements of `this` list and the specified [elements].
     */
    override fun append(elements: Iterable<@UnsafeVariance T>): PersistentIndexedList<T>

    /**
     * Returns a new [list][PersistentIndexedList] containing the elements of `this` list and the specified [elements].
     */
    override fun append(elements: Array<out @UnsafeVariance T>): PersistentIndexedList<T>

    /**
     * Returns a new [list][PersistentIndexedList] containing the elements of `this` list and the specified [elements].
     */
    override fun append(elements: Sequence<@UnsafeVariance T>): PersistentIndexedList<T>

    // -- REMOVE -- \\
    /**
     * Returns a new [list][PersistentIndexedList] with the first occurrence of the specified [element] removed, or
     * returns `this` if `this` collection does not contain the specified `element`.
     */
    override fun remove(element: @UnsafeVariance T): PersistentIndexedList<T>

    override fun removeAll(elements: Iterable<@UnsafeVariance T>): PersistentIndexedList<T>

    override fun removeAll(elements: Array<out @UnsafeVariance T>): PersistentIndexedList<T>

    override fun removeAll(elements: Sequence<@UnsafeVariance T>): PersistentIndexedList<T>
}

abstract class AbstractPersistentIndexedList<out T> protected constructor() : AbstractPersistentList<T>(),
    PersistentIndexedList<T> {
    override fun indexOf(element: @UnsafeVariance T): Int = this.indexOfFirst { it == element }

    override fun lastIndexOf(element: @UnsafeVariance T): Int = this.indexOfLast { it == element }

    override fun iterator(): Iterator<T> = IteratorImpl()

    protected inner class IteratorImpl : Iterator<T> {
        private var index: Int = 0

        override fun hasNext(): Boolean = index < size

        override fun next(): T = if (hasNext()) get(index++) else throw NoSuchElementException()
    }
}