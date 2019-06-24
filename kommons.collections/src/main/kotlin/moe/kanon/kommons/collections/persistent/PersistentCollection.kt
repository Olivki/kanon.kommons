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

import moe.kanon.kommons.collections.ImmutableIterator

interface PersistentCollection<out T> : Iterable<T> {
    /**
     * Returns the size of `this` collection.
     */
    val size: Int

    /**
     * Returns `true` if `this` collection has no elements, `false` otherwise.
     */
    val isEmpty: Boolean

    /**
     * Returns `true` if the specified [element] is stored in `this` collection, `false` otherwise.
     */
    operator fun contains(element: @UnsafeVariance T): Boolean

    /**
     * Returns `true` if `this` collection contains all the elements of the specified [collection], `false` otherwise.
     */
    fun containsAll(collection: Iterable<@UnsafeVariance T>): Boolean

    // -- PREPEND -- \\
    /**
     * Returns a new [collection][PersistentCollection] containing the specified [element] and the elements of `this`
     * collection.
     */
    fun prepend(element: @UnsafeVariance T): PersistentCollection<T>

    /**
     * Returns a new [collection][PersistentCollection] containing the specified [elements] and the elements of `this`
     * collection.
     */
    fun prepend(elements: Iterable<@UnsafeVariance T>): PersistentCollection<T>

    /**
     * Returns a new [collection][PersistentCollection] containing the specified [elements] and the elements of `this`
     * collection.
     */
    fun prepend(elements: Array<out @UnsafeVariance T>): PersistentCollection<T>

    /**
     * Returns a new [collection][PersistentCollection] containing the specified [elements] and the elements of `this`
     * collection.
     */
    fun prepend(elements: Sequence<@UnsafeVariance T>): PersistentCollection<T>

    // -- APPEND -- \\
    /**
     * Returns a new [collection][PersistentCollection] containing the elements of `this` collection and the specified
     * [element].
     */
    fun append(element: @UnsafeVariance T): PersistentCollection<T>

    /**
     * Returns a new [collection][PersistentCollection] containing the elements of `this` collection and the specified
     * [elements].
     */
    fun append(elements: Iterable<@UnsafeVariance T>): PersistentCollection<T>

    /**
     * Returns a new [collection][PersistentCollection] containing the elements of `this` collection and the specified
     * [elements].
     */
    fun append(elements: Array<out @UnsafeVariance T>): PersistentCollection<T>

    /**
     * Returns a new [collection][PersistentCollection] containing the elements of `this` collection and the specified
     * [elements].
     */
    fun append(elements: Sequence<@UnsafeVariance T>): PersistentCollection<T>

    // -- REMOVE -- \\
    /**
     * Returns a new [collection][PersistentCollection] with the first occurrence of the specified [element] removed,
     * or returns `this` if `this` collection does not contain the specified `element`.
     */
    fun remove(element: @UnsafeVariance T): PersistentCollection<T>

    // TODO: This is documentation doesn't make much sense.
    /**
     * Returns a new [collection][PersistentCollection] that contains the same elements as `this` collection, but any
     * elements that are contained in the specified [elements] have been removed.
     */
    fun removeAll(elements: Iterable<@UnsafeVariance T>): PersistentCollection<T>

    /**
     * Returns a new [collection][PersistentCollection] that contains the same elements as `this` collection, but any
     * elements that are contained in the specified [elements] have been removed.
     */
    fun removeAll(elements: Array<out @UnsafeVariance T>): PersistentCollection<T>

    /**
     * Returns a new [collection][PersistentCollection] that contains the same elements as `this` collection, but any
     * elements that are contained in the specified [elements] have been removed.
     */
    fun removeAll(elements: Sequence<@UnsafeVariance T>): PersistentCollection<T>

    /**
     * Returns a new [Iterator] that iterates over the elements of `this` collection.
     */
    override fun iterator(): Iterator<T>
}

abstract class AbstractPersistentCollection<out T> protected constructor() : PersistentCollection<T> {
    override val isEmpty: Boolean get() = size == 0

    override fun contains(element: @UnsafeVariance T): Boolean = this.any { it == element }

    override fun containsAll(collection: Iterable<@UnsafeVariance T>): Boolean = collection.all { it in this }

    override fun toString(): String = joinToString(", ", "[", "]") {
        if (it === this) "(this Collection)" else it.toString()
    }
}