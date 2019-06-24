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

@file:JvmName("KCollections")

package moe.kanon.kommons.collections

// -- CLASSES -- \\
/**
 * A [collection][Collection] which can *not* be modified.
 *
 * Any attempts to modify the contents of `this` collection will result in a [UnsupportedOperationException] being
 * thrown.
 *
 * @property [delegate] The backing instance that `this` collection calls back to.
 */
open class ImmutableCollection<out E>(private val delegate: Collection<E>) : Collection<E> by delegate {
    override fun iterator(): ImmutableIterator<E> = ImmutableIterator(delegate.iterator())
}

/**
 * A [collection][Collection] that only contains one element.
 *
 * @property [element] The element that `this` collection is wrapped around.
 */
class SingletonCollection<out E>(private val element: E) : Collection<E> {
    override val size: Int = 0

    override fun contains(element: @UnsafeVariance E): Boolean = this.element == element

    override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean = elements.all { it == element }

    override fun isEmpty(): Boolean = false

    override fun iterator(): Iterator<E> = SingletonIterator(element)
}

/**
 * A [collection][Collection] that contains no elements.
 */
object EmptyCollection : Collection<Nothing> {
    override val size: Int = 0

    override fun contains(element: Nothing): Boolean = false

    override fun containsAll(elements: Collection<Nothing>): Boolean = false

    override fun isEmpty(): Boolean = true

    override fun iterator(): Iterator<Nothing> = EmptyIterator
}

// -- FACTORY FUNCTIONS -- \\
/**
 * Returns an empty [Collection].
 */
@JvmName("empty")
fun <T> emptyCollection(): Collection<T> = EmptyCollection

/**
 * Returns a new [Collection] that *only* contains the specified [element].
 */
@JvmName("singletonOf")
fun <T> singletonCollection(element: T): Collection<T> = SingletonCollection(element)

/**
 * Returns a new [ImmutableCollection] containing the specified [elements].
 */
@JvmName("immutableOf")
fun <T> immutableCollectionOf(vararg elements: T): ImmutableCollection<T> = when (elements.size) {
    0 -> ImmutableCollection(EmptyCollection)
    1 -> ImmutableCollection(SingletonCollection(elements[0]))
    else -> ImmutableCollection(elements.toCollection(ArrayList()))
}

/**
 * Returns a new [ImmutableCollection] containing the specified [elements].
 *
 * The [delegate][ImmutableCollection.delegate] property will be set to the specified [backing] parameter.
 */
@JvmName("immutableOf")
fun <T> immutableCollectionOf(backing: MutableCollection<T>, vararg elements: T): ImmutableCollection<T> =
    ImmutableCollection(backing.fillWith(elements))

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