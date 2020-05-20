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

@file:JvmName("KMaps")

package moe.kanon.kommons.collections

import java.util.Collections
import java.util.EnumMap
import java.util.TreeMap

// -- FACTORY FUNCTIONS -- \\
/**
 * Returns a new [Map] that only contains the given [value] mapped to the given [key].
 */
@Deprecated(
    message = "Use 'mapOf(key, value)' instead.",
    replaceWith = ReplaceWith("mapOf(key, value)", "moe.kanon.kommons.collections")
)
fun <K, V> singletonMapOf(key: K, value: V): Map<K, V> = mapOf(key, value)

/**
 * Returns a new [Map] that only contains the given [value] mapped to the given [key].
 */
@JvmName("singletonOf")
fun <K, V> mapOf(key: K, value: V): Map<K, V> = Collections.singletonMap(key, value)

/**
 * Returns a new [Map] that only contains the given [value][Pair.second] mapped to the given [key][Pair.first].
 */
@JvmName("singletonOf")
@Deprecated(
    message = "Use 'mapOf(pair)' instead.",
    replaceWith = ReplaceWith("mapOf(pair)", "kotlin.collections")
)
fun <K, V> singletonMapOf(pair: Pair<K, V>): Map<K, V> = mapOf(pair)

/**
 * Returns an [unmodifiable view][Collections.unmodifiableMap] of `this` map.
 */
fun <K, V> Map<K, V>.asUnmodifiableMap(): Map<K, V> = Collections.unmodifiableMap(this)

/**
 * Returns a new [TreeMap] that contains the specified [pairs].
 */
fun <K : Comparable<K>, V> treeMapOf(vararg pairs: Pair<K, V>): TreeMap<K, V> = TreeMap(pairs.toMap())

// enum-map
/**
 * Returns a new [EnumMap] that contains the specified [entries].
 */
// we're using the enum class constructor rather than the one that accepts a map so that the function won't fail if
// the supplied 'entries' is empty.
inline fun <reified K : Enum<K>, V> enumMapOf(vararg entries: Pair<K, V>): EnumMap<K, V> =
    EnumMap<K, V>(K::class.java).fillWith(entries)

/**
 * Returns a new and empty [EnumMap] based on the specified [enum][K].
 */
inline fun <reified K : Enum<K>, V> emptyEnumMap(): EnumMap<K, V> = EnumMap(K::class.java)

/**
 * Returns a new [EnumMap] which is populated by the specified [init] function.
 */
@JvmName("enumMap")
inline fun <reified K : Enum<K>, V> enumMap(init: (K) -> V): EnumMap<K, V> =
    emptyEnumMap<K, V>().fillWith(enumValues<K>().associate { it to init(it) })

/**
 * Returns a new [enum-map][EnumMap] containing the entries of `this` map.
 */
inline fun <reified K : Enum<K>, V> Map<K, V>.toEnumMap(): EnumMap<K, V> = convertToEnumMap(this, K::class.java)

@PublishedApi
internal fun <K : Enum<K>, V> convertToEnumMap(origin: Map<K, V>, clz: Class<K>): EnumMap<K, V> = when (origin) {
    is EnumMap -> origin
    else -> EnumMap<K, V>(clz).apply { putAll(origin) }
}

// -- UTIL FUNCTIONS -- \\
/**
 * Creates a string from all the elements separated using [separator] and using the given [prefix] and [postfix] if
 * supplied.
 *
 * If the collection could be huge, you can specify a non-negative value of [limit], in which case only the first
 * [limit] elements will be appended, followed by the [truncated] string (which defaults to "...").
 */
fun <K, V> Map<K, V>.joinToString(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
    transform: ((Map.Entry<K, V>) -> CharSequence)? = null
): String = entries.joinToString(separator, prefix, postfix, limit, truncated, transform)

/**
 * Returns the value stored under the given [key], or throws a [NoSuchElementException] where the `message` is set to
 * the result of invoking [toString] on the given [lazyMessage] function.
 */
@Deprecated(
    message = "The name does not follow naming convention",
    replaceWith = ReplaceWith("this.getOrThrow(key, lazyMessage)", "moe.kanon.kommons.collections"),
    level = DeprecationLevel.ERROR
)
inline fun <K, V> Map<K, V>.getValueOrThrow(key: K, lazyMessage: () -> Any): V =
    this[key] ?: throw NoSuchElementException(lazyMessage().toString())

/**
 * Returns the value stored under the given [key], or throws a [NoSuchElementException] where the `message` is set to
 * the result of invoking [toString] on the given [lazyMessage] function if no value is stored under `key`.
 */
@Suppress("UNCHECKED_CAST")
inline fun <K, V> Map<K, V>.getOrThrow(key: K, lazyMessage: () -> Any): V {
    if (key !in this) throw NoSuchElementException(lazyMessage().toString())
    return this[key] as V
}

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

/**
 * Returns a map containing all entries that have a key that are instances of the specified type parameter [R].
 */
inline fun <reified R, V> Map<*, V>.filterKeysIsInstance(): Map<R, V> = this.filterKeys { it is R } as Map<R, V>

/**
 * Returns a map containing all entries that have a value that are instances of the specified type parameter [R].
 */
inline fun <K, reified R> Map<K, *>.filterValuesIsInstance(): Map<K, R> = this.filterValues { it is R } as Map<K, R>