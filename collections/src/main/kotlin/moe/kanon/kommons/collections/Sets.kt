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

import java.nio.file.StandardOpenOption
import java.util.*
import kotlin.reflect.KClass

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
fun <T> singletonSet(item: T): Set<T> = SingletonSet(item)

/**
 * Converts `this` set to a [EnumSet].
 */
inline fun <reified E : Enum<E>> Set<E>.toEnumSet(): EnumSet<E> = convertToEnumSet(this, E::class.java)

/**
 * Returns a new [EnumSet] that initially contains the all of the elements in `this` range.
 */
fun <E : Enum<E>> ClosedRange<E>.toEnumSet(): EnumSet<E> = EnumSet.range(this.start, this.endInclusive)

@PublishedApi
internal fun <E : Enum<E>> convertToEnumSet(origin: Set<E>, clz: Class<E>): EnumSet<E> = when (origin) {
    is EnumSet<E> -> origin
    else -> EnumSet.noneOf(clz).apply { addAll(origin) }
}

/**
 * Returns a new and empty [EnumSet] for the given [enum][E].
 */
inline fun <reified E : Enum<E>> emptyEnumSet(): EnumSet<E> = EnumSet.noneOf(E::class.java)

/**
 * Returns a new [EnumSet] containing all the enum constants of the given [enum][E].
 */
inline fun <reified E : Enum<E>> enumSetOf(): EnumSet<E> = emptyEnumSet<E>().apply { addAll(enumValues<E>()) }

/**
 * Returns a new [EnumSet] for the given [enum][E] that initially contains the given element.
 */
fun <E : Enum<E>> enumSetOf(e: E): EnumSet<E> = EnumSet.of(e)

/**
 * Returns a new [EnumSet] for the given [enum][E] that initially contains the given elements.
 */
fun <E : Enum<E>> enumSetOf(e1: E, e2: E): EnumSet<E> = EnumSet.of(e1, e2)

/**
 * Returns a new [EnumSet] for the given [enum][E] that initially contains the given elements.
 */
fun <E : Enum<E>> enumSetOf(e1: E, e2: E, e3: E): EnumSet<E> = EnumSet.of(e1, e2, e3)

/**
 * Returns a new [EnumSet] for the given [enum][E] that initially contains the given elements.
 */
fun <E : Enum<E>> enumSetOf(e1: E, e2: E, e3: E, e4: E): EnumSet<E> = EnumSet.of(e1, e2, e3, e4)

/**
 * Returns a new [EnumSet] for the given [enum][E] that initially contains the given elements.
 */
fun <E : Enum<E>> enumSetOf(e1: E, e2: E, e3: E, e4: E, e5: E): EnumSet<E> = EnumSet.of(e1, e2, e3, e4, e5)

/**
 * Returns a new [EnumSet] for the given [enum][E] that initially contains the [initial] element, and the [rest].
 */
fun <E : Enum<E>> enumSetOf(initial: E, vararg rest: E): EnumSet<E> = when (rest.size) {
    0 -> enumSetOf(initial)
    1 -> enumSetOf(initial, rest[0])
    2 -> enumSetOf(initial, rest[0], rest[1])
    3 -> enumSetOf(initial, rest[0], rest[1], rest[2])
    4 -> enumSetOf(initial, rest[0], rest[1], rest[2], rest[3])
    else -> EnumSet.of(initial, *rest)
}

// -- UTIL FUNCTIONS -- \\

