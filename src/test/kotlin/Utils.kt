@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.test

import moe.kanon.kommons.io.lines
import moe.kanon.kommons.io.pathOf
import java.nio.file.Path
import java.util.*
import kotlin.collections.HashSet
import kotlin.random.nextLong

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

/**
 * Enables the use of the `forAll` function on Maps.
 *
 * This is so that you don't need to do `map.entries.forAll { ... } / forAll(map.entries) { ... }` just to use a map
 * for data driven testing.
 *
 * @receiver The map to use for the data driven testing.
 */
internal inline fun <K, V> Map<K, V>.forAll(noinline fn: (Map.Entry<K, V>) -> Unit) =
    io.kotlintest.forAll(this.entries, fn)

// ugly code ahead, turn back whilst you can.

@Suppress("UNCHECKED_CAST")
internal fun <N : Number> setOfRandomIntegerNumbers(
    size: Long,
    roof: Long,
    smallRoof: Long,
    sort: Boolean,
    start: Long = 0
): Set<N> {
    val cache: MutableSet<N> = if (sort) TreeSet() else HashSet()

    tailrec fun randomNumber(limit: Long): N {
        val random = kotlin.random.Random.nextLong(start..limit) as N
        return if (random !in cache) {
            cache += random
            random
        } else randomNumber(limit)
    }

    for (i in 1..size) if (kotlin.random.Random.nextBoolean()) randomNumber(roof) else randomNumber(smallRoof)

    return cache
}

internal inline fun <reified C, reified K, reified V> createDataMap(folder: String, fileName: String): Map<K, V> =
    getResourceDirectory<C>().resolve(folder).parseChildTestDataToMap(fileName)

internal inline fun <reified C> getResourceDirectory(): Path {
    val start = pathOf(System.getProperty("user.dir"))
    return pathOf(start, "src", "test", "resources", "test_data")
}

internal inline fun <reified K, reified V> Path.parseChildTestDataToMap(fileName: String): Map<K, V> =
    this.resolve(fileName).parseTestDataToMap()

internal inline fun <reified K, reified V> Path.parseTestDataToMap(): Map<K, V> =
    this.lines.filterNot { it.isBlank() || '=' !in it }.associate {
        val input = it.split('=')
        val key: K = ConversionTool.to(K::class.java, input[0], emptyArray()) as K
        val value: V = ConversionTool.to(V::class.java, input[1], emptyArray()) as V

        key to value
    }

