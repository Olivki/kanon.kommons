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
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.collections

import java.util.*

/**
 * Creates a new map based on this [map][Map] that's structurally the same, but the `key:value` positions have been
 * switched to `value:key`.
 *
 * Suppose you have a map with the following entries:
 *
 * - `"entry_one":1`
 * - `"entry_two":2`
 *
 * Invoking `flip` on `this` map would give you a map with the following entries:
 *
 * - `1:"entry_one"`
 * - `2:"entry_two"`
 *
 * @since 0.6.0
 */
public fun <K, V> Map<K, V>.flip(): Map<V, K> = this.asSequence().associate { it.value to it.key }

/**
 * Appends all entries from the [from] map onto `this` map.
 *
 * **Note:** This function is *generally* useless for most people, seeing as [MutableMap]s generally have the
 * [putAll][MutableMap.putAll] function in them. This is only useful in *very* specific circumstances.
 *
 * @receiver The map that the entries will be appended onto.
 *
 * @param [from] The map which the entries will be taken from.
 *
 * *(This can be either a [Map] instance, or a [MutableMap] instance.)*
 *
 * @since 0.6.0
 *
 * @see MutableMap.putAll
 */
public fun <K, V> MutableMap<K, V>.inherit(from: Map<out K, V>) {
    from.mapValuesTo(this) { it.value }
}

/**
 * Creates a [TreeMap] using the specified [pairs].
 *
 * @since 0.6.0
 */
public fun <K, V> treeMapOf(vararg pairs: Pair<K, V>): TreeMap<K, V> = TreeMap(pairs.toMap())

/**
 * Creates and returns a [EnumMap] containing the specified [entries].
 *
 * @since 0.6.0
 */
// we're using the enum class constructor rather than the one that accepts a map so that the function won't fail if
// the supplied 'entries' is empty.
public inline fun <reified E : Enum<E>, V> enumMapOf(vararg entries: Pair<E, V>): EnumMap<E, V> =
    EnumMap<E, V>(E::class.java).apply {
        for ((key, value) in entries) this[key] = value
    }

/**
 * Stores the specified [value] under the specified [key] in `this` map, and then returns the specified [value].
 *
 * @return the specified [value]
 *
 * @since 0.6.0
 */
public fun <K, V, M : MutableMap<K, V>> M.putAndReturn(key: K, value: V): V {
    this.put(key, value)
    return value
}

/**
 * Stores the specified [value] under the specified [key] in `this` map, and then returns the specified [value].
 *
 * @return the specified [value]
 *
 * @since 0.6.0
 */
public fun <K, V, M : MutableMap<K, V>> M.putAndReturn(key: K, value: () -> V): V =
    this.putAndReturn(key, value())

// collections jvm
/**
 * Returns an unmodifiable view of `this` map.
 *
 * This function allows modules to provide users with "read-only" access to internal maps.
 *
 * Query operations on the returned map "read through" to the specified map, and attempts to modify the returned map,
 * whether direct or via its collection views, result in an [UnsupportedOperationException].
 *
 * The returned map will be serializable if the specified map is serializable.
 *
 * @receiver the map for which an unmodifiable view is to be returned.
 *
 * @param [K] the type of the [map keys][Map.keys]
 * @param [V] the type of the [map values][Map.values]
 *
 * @return an unmodifiable view of `this` map
 *
 * @since 0.6.0
 */
public inline fun <K, V> Map<K, V>.toUnmodifiableMap(): Map<K, V> = Collections.unmodifiableMap(this)

/**
 * Creates and returns a unmodifiable map containing the specified [pairs].
 *
 * @param [K] the type of the [map keys][Map.keys]
 * @param [V] the type of the [map values][Map.values]
 * @param [pairs] the contents of the map
 *
 * @return the newly created map
 */
public fun <K, V> unmodifiableMapOf(vararg pairs: Pair<K, V>): Map<K, V> = pairs.toMap().toUnmodifiableMap()