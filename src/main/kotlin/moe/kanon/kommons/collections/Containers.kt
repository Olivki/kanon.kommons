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

package moe.kanon.kommons.collections

import moe.kanon.kommons.collections.iterables.UnmodifiableIterable
import moe.kanon.kommons.collections.iterators.UnmodifiableIterator
import moe.kanon.kommons.func.Maybe
import moe.kanon.kommons.func.Some
import moe.kanon.kommons.func.None

interface Container<out T> : UnmodifiableIterable<T> {
    /**
     * The size of `this` collection.
     */
    val size: Int

    /**
     * Returns the size of `this` container.
     */
    @JvmDefault
    fun size(): Int = size

    /**
     * Returns whether or not `this` collection is empty.
     */
    fun isEmpty(): Boolean

    /**
     * Returns whether or not `this` collection contains the specified [element].
     */
    @JvmDefault
    operator fun contains(element: @UnsafeVariance T): Boolean = indexOf(element) >= 0

    /**
     * Returns a iterator specific to `this` collection.
     *
     * Any [Iterator] instances returned by a [Container] inheritor *needs* to be a child of the
     * [UnmodifiableIterator] class, this is to make sure that we're communicating the fact that any mutable operations
     * are *not* supported on it.
     */
    override fun iterator(): UnmodifiableIterator<T>
}

/**
 * An abstract implementation of a [Container] which allows its elements to be accessed via a `index`.
 */
interface KList<out T> : Container<T> {
    /**
     * Returns the element stored under the specified [index].
     *
     * Implementations may either throw a [NoSuchElementException] or a [IndexOutOfBoundsException] if there is no
     * element located at `index`. By `default` this function will throw a [IndexOutOfBoundsException] if no element
     * is found.
     */
    @JvmDefault
    operator fun get(index: Int): T = elementAt(index)

    /**
     * Returns the element stored under the specified [index], or `null` if no element is found.
     */
    @JvmDefault
    fun getOrNull(index: Int): T? = elementAtOrNull(index)

    /**
     * Returns a [Some] wrapping the element under the specified [index], or [None] if no element is found.
     */
    @JvmDefault
    fun getOrNone(index: Int): Maybe<T> = Maybe(getOrNull(index))

    /**
     * Returns the index of the *first* element that matches the specified [element], or `-1` if no matching element
     * could be found.
     */
    @JvmDefault
    operator fun get(element: @UnsafeVariance T): Int = indexOfFirst { it == element }
}

/**
 * An abstract implementation of a collection that can not be modified.
 *
 * This differs from the [Container] interface in that a class that inherits from `this` should never have any
 * sub-classes that provide any sort of mutable operations, while something that inherits from `Kollection` may.
 *
 * An immutable collection is different from the *read-only* views provided by the Kotlin standard library *([listOf],
 * [mapOf], [setOf], etc..)* in that a immutable collection is *fully* immutable and not just a *read-only view*. This
 * means that the immutability view is also preserved when using it from the Java side, as the [List] interface is
 * just seen as a normal [java.util.List] when used from the Java side.
 */
interface ImmutableContainer<out T> : Container<T>