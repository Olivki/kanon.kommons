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

@file:JvmName("KCollections")

package moe.kanon.kommons.collections

import java.util.Collections

// -- FACTORY FUNCTIONS -- \\
internal object EmptyCollection : AbstractCollection<Nothing>() {
    override val size: Int = 0

    override fun contains(element: Nothing): Boolean = false

    override fun containsAll(elements: Collection<Nothing>): Boolean = false

    override fun isEmpty(): Boolean = true

    override fun iterator(): Iterator<Nothing> = EmptyIterator
}

private class SingletonCollection<T>(val item: T) : AbstractCollection<T>() {
    override val size: Int = 0

    override fun contains(element: @UnsafeVariance T): Boolean = item == element

    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean = elements.all { it == item }

    override fun isEmpty(): Boolean = false

    override fun iterator(): Iterator<T> = singletonIteratorOf(item)
}

/**
 * Returns an empty [Collection].
 */
@JvmName("empty")
fun <T> emptyCollection(): Collection<T> = EmptyCollection

/**
 * Returns a new [Collection] that *only* contains the specified [item].
 */
@JvmName("singletonOf")
fun <T> singletonCollectionOf(item: T): Collection<T> = SingletonCollection(item)

/**
 * Returns an [unmodifiable view][Collections.unmodifiableCollection] of `this` collection.
 */
fun <T> Collection<T>.asUnmodifiableCollection(): Collection<T> = Collections.unmodifiableCollection(this)

// -- UTILITY FUNCTIONS -- \\
// fill with
/**
 * Adds the specified [elements] to `this` collection and returns `this`.
 */
@JvmName("populate")
fun <T, C : MutableCollection<in T>> C.fillWith(vararg elements: T): C = this.apply { addAll(elements) }

/**
 * Adds the specified [content] to `this` collection and returns `this`.
 */
fun <T, C : MutableCollection<in T>> C.fillWith(content: Iterable<T>): C = this.apply { addAll(content) }

/**
 * Adds the specified [content] to `this` collection and returns `this`.
 */
fun <T, C : MutableCollection<in T>> C.fillWith(content: Array<out T>): C = this.apply { addAll(content) }

/**
 * Adds the specified [content] to `this` collection and returns `this`.
 */
fun <T, C : MutableCollection<in T>> C.fillWith(content: Collection<T>): C = this.apply { addAll(content) }