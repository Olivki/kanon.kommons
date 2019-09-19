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

@file:JvmName("KMaps")

package moe.kanon.kommons.collections

import java.util.*
import kotlin.NoSuchElementException

// -- CLASSES -- \\
class SingletonMap<K, out V>(key: K, value: V) : Map<K, V> {
    private val entry: Entry<K, V> = Entry(key, value)

    override val entries: Set<Map.Entry<K, V>> = SingletonSet(entry)
    override val keys: Set<K> = SingletonSet(entry.key)
    override val size: Int = 1
    override val values: Collection<V> = SingletonCollection(entry.value)

    override fun containsKey(key: @UnsafeVariance K): Boolean = key == entry.key

    override fun containsValue(value: @UnsafeVariance V): Boolean = value == entry.value

    override fun get(key: K): V? = if (key == entry.key) entry.value else null

    override fun isEmpty(): Boolean = false

    private data class Entry<out K, out V>(override val key: K, override val value: V) : Map.Entry<K, V>
}

// -- FACTORY FUNCTIONS -- \\
// custom
/**
 * Returns a new [Map] that only contains one entry, which is created from the specified [key] and [value].
 */
@JvmName("singletonOf")
fun <K, V> singletonMap(key: K, value: V): Map<K, V> = SingletonMap(key, value)

/**
 * Returns a new [Map] that only contains one entry, which is created from the values of the specified [pair].
 */
@JvmName("singleton")
fun <K, V> singletonMap(pair: Pair<K, V>): Map<K, V> = SingletonMap(pair.first, pair.second)

// java
/**
 * Returns an [unmodifiable view][Collections.unmodifiableMap] of `this` map.
 */
fun <K, V> Map<K, V>.asUnmodifiableMap(): Map<K, V> = Collections.unmodifiableMap(this)

/**
 * Returns a new [TreeMap] that contains the specified [pairs].
 */
fun <K : Comparable<K>, V> treeMapOf(vararg pairs: Pair<K, V>): TreeMap<K, V> = TreeMap(pairs.toMap())

/**
 * Returns a new [emptyEnumMap] that contains the specified [entries].
 */
// we're using the enum class constructor rather than the one that accepts a map so that the function won't fail if
// the supplied 'entries' is empty.
inline fun <reified K : Enum<K>, V> enumMapOf(vararg entries: Pair<K, V>): Map<K, V> =
    EnumMap<K, V>(K::class.java).fillWith(entries)

/**
 * Returns a new and empty [emptyEnumMap] based on the specified [enum][K].
 */
@JvmName("emptyEnumMap")
inline fun <reified K : Enum<K>, V> emptyEnumMap(): EnumMap<K, V> = EnumMap(K::class.java)

/**
 * Returns a new [emptyEnumMap] which is populated by the specified [init] function.
 */
@JvmName("enumMap")
inline fun <reified K : Enum<K>, V> enumMap(init: (K) -> V): EnumMap<K, V> =
    emptyEnumMap<K, V>().fillWith(enumValues<K>().associate { it to init(it) })

// -- UTIL FUNCTIONS -- \\
/**
 * Returns the value stored under the given [key], or throws a [NoSuchElementException] where the `message` is set to
 * the result of invoking [toString] on the given [lazyMessage] function.
 */
inline fun <K, V> Map<K, V>.getValueOrThrow(key: K, lazyMessage: () -> Any): V =
    this[key] ?: throw NoSuchElementException(lazyMessage().toString())

/**
 * Executes the specified [action] every time an `entry` matches the specified [predicate].
 */
inline fun <K, V> Map<K, V>.onMatch(predicate: (Map.Entry<K, V>) -> Boolean, action: (Map.Entry<K, V>) -> Unit) {
    for (entry in this) if (predicate(entry)) action(entry)
}

/**
 * Throws the specified [exception] every time an `entry` matches the specified [predicate].
 */
inline fun <K, V, X : Throwable> Map<K, V>.throwOnMatch(
    predicate: (Map.Entry<K, V>) -> Boolean,
    exception: (Map.Entry<K, V>) -> X
) = this.onMatch(predicate) { throw exception(it) }

/**
 * Adds the specified [pairs] to `this` map and returns `this`.
 */
@JvmName("populate")
fun <K, V, M : MutableMap<K, V>> M.fillWith(vararg pairs: Pair<K, V>): M = this.apply { putAll(pairs) }

/**
 * Adds the specified [pairs] to `this` map and returns `this`.
 */
fun <K, V, M : MutableMap<K, V>> M.fillWith(pairs: Iterable<Pair<K, V>>): M = this.apply { putAll(pairs) }

/**
 * Adds the specified [pairs] to `this` map and returns `this`.
 */
fun <K, V, M : MutableMap<K, V>> M.fillWith(pairs: Array<out Pair<K, V>>): M = this.apply { putAll(pairs) }

/**
 * Adds the contents of the specified [map] to `this` map, and returns `this`.
 */
fun <K, V, M : MutableMap<K, V>> M.fillWith(map: Map<K, V>): M = this.apply { putAll(map) }