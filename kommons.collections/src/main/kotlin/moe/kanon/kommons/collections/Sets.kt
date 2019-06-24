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

@file:JvmName("KSets")

package moe.kanon.kommons.collections

// -- CLASSES -- \\
/**
 * Represents a [set][Set] that only contains one element.
 */
class SingletonSet<out E>(private val element: E) : Set<E> {
    override val size: Int = 1

    override fun contains(element: @UnsafeVariance E): Boolean = element == this.element

    override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean = elements.all { it == element }

    override fun isEmpty(): Boolean = false

    override fun iterator(): Iterator<E> = SingletonIterator(element)
}

/**
 * Represents a [set][Set] that can't be modified.
 *
 * Any attempts to modify the `set` will result in a [UnsupportedOperationException] being thrown.
 *
 * @property [delegate] The underlying [Set] instance that `this` class delegates all operations to.
 */
class ImmutableSet<out E>(private val delegate: Set<E>) : Set<E> by delegate {
    override fun iterator(): ImmutableIterator<E> = ImmutableIterator(delegate.iterator())
}

// -- FACTORY FUNCTIONS -- \\
/**
 * Returns a new [singleton set][SingletonSet] wrapped around the specified [item].
 */
@JvmName("singletonOf")
fun <E> singletonSet(item: E): Set<E> = SingletonSet(item)

/**
 * Returns a new [immutable set][ImmutableSet] that contains the specified [elements].
 */
@JvmName("immutableOf")
fun <E> immutableSetOf(vararg elements: E): ImmutableSet<E> = when (elements.size) {
    0 -> ImmutableSet(emptySet())
    1 -> ImmutableSet(singletonSet(elements[0]))
    else -> ImmutableSet(elements.toCollection(LinkedHashSet()))
}

/**
 * Returns a new [immutable set][ImmutableSet] that contains the specified [elements].
 */
@JvmName("immutableHashOf")
fun <E> immutableHashSetOf(vararg elements: E): ImmutableSet<E> = when (elements.size) {
    0 -> ImmutableSet(emptySet())
    1 -> ImmutableSet(singletonSet(elements[0]))
    else -> ImmutableSet(elements.toCollection(HashSet()))
}

/**
 * Returns a new [immutable set][ImmutableSet] that contains the specified [elements].
 *
 * The [delegate][ImmutableSet.delegate] property will be set to the specified [backing] parameter.
 */
@JvmName("immutableOf")
fun <E> immutableSetOf(backing: MutableSet<E>, vararg elements: E): ImmutableSet<E> =
    ImmutableSet(backing.fillWith(elements))

// -- UTIL FUNCTIONS -- \\

