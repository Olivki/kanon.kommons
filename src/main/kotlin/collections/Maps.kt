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

@file:JvmName("MapUtils")

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
 * Invoking `flip` on this map would give you a map with the following entries:
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