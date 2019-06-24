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

@file:JvmName("PersistentLists")

package moe.kanon.kommons.collections.persistent

import moe.kanon.kommons.collections.calculateHashCode
import moe.kanon.kommons.collections.hasSameElementsAs

interface PersistentList<out T> : PersistentCollection<T> {
    companion object {
        @JvmName("of")
        @JvmStatic operator fun <T> invoke(vararg elements: T): PersistentList<T> = PersistentLinkedList(*elements)
    }

    // -- OVERRIDES -- \\
    // -- PREPEND -- \\
    /**
     * Returns a new [list][PersistentList] containing the specified [element] and the elements of `this` list.
     */
    override fun prepend(element: @UnsafeVariance T): PersistentList<T>

    /**
     * Returns a new [list][PersistentList] containing the specified [elements] and the elements of `this` list.
     */
    override fun prepend(elements: Iterable<@UnsafeVariance T>): PersistentList<T>

    /**
     * Returns a new [list][PersistentList] containing the specified [elements] and the elements of `this` list.
     */
    override fun prepend(elements: Array<out @UnsafeVariance T>): PersistentList<T>

    /**
     * Returns a new [list][PersistentList] containing the specified [elements] and the elements of `this` list.
     */
    override fun prepend(elements: Sequence<@UnsafeVariance T>): PersistentList<T>

    // -- APPEND -- \\
    /**
     * Returns a new [list][PersistentList] containing the elements of `this` list and the specified [element].
     */
    override fun append(element: @UnsafeVariance T): PersistentList<T>

    /**
     * Returns a new [list][PersistentList] containing the elements of `this` list and the specified [elements].
     */
    override fun append(elements: Iterable<@UnsafeVariance T>): PersistentList<T>

    /**
     * Returns a new [list][PersistentList] containing the elements of `this` list and the specified [elements].
     */
    override fun append(elements: Array<out @UnsafeVariance T>): PersistentList<T>

    /**
     * Returns a new [list][PersistentList] containing the elements of `this` list and the specified [elements].
     */
    override fun append(elements: Sequence<@UnsafeVariance T>): PersistentList<T>

    // -- REMOVE -- \\
    /**
     * Returns a new [list][PersistentList] with the first occurrence of the specified [element] removed, or returns
     * `this` if `this` collection does not contain the specified `element`.
     */
    override fun remove(element: @UnsafeVariance T): PersistentList<T>

    override fun removeAll(elements: Iterable<@UnsafeVariance T>): PersistentList<T>

    override fun removeAll(elements: Array<out @UnsafeVariance T>): PersistentList<T>

    override fun removeAll(elements: Sequence<@UnsafeVariance T>): PersistentList<T>
}

abstract class AbstractPersistentList<out T> : AbstractPersistentCollection<T>(), PersistentList<T> {
    override fun hashCode(): Int = this.calculateHashCode()

    override fun equals(other: Any?): Boolean = when (other) {
        this -> true
        !is PersistentList<*> -> false
        else -> this hasSameElementsAs other
    }
}

