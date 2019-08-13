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

import java.util.*

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

// -- FACTORY FUNCTIONS -- \\
/**
 * Returns an [unmodifiable view][Collections.unmodifiableSet] of `this` set.
 */
fun <T> Set<T>.asUnmodifiableSet(): Set<T> = Collections.unmodifiableSet(this)

/**
 * Returns a new [singleton set][SingletonSet] wrapped around the specified [item].
 */
@JvmName("singletonOf")
fun <E> singletonSet(item: E): Set<E> = SingletonSet(item)

// -- UTIL FUNCTIONS -- \\

