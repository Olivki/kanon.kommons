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

@file:JvmMultifileClass
@file:JvmName("KMaps")

package moe.kanon.kommons.collections.maps

/*
 * This file contains ports of internal utility functions used in the Kotlin standard library.
 */

const val INT_MAX_POWER_OF_TWO: Int = Int.MAX_VALUE / 2 + 1

/**
 * Calculate the initial capacity of a map, based on Guava's com.google.common.collect.Maps approach. '
 *
 * This is equivalent to the Collection constructor for HashSet, (c.size()/.75f) + 1, but provides further
 * optimisations for very small or very large sizes, allows support non-collection classes, and provides consistency
 * for all map based class construction.
 */
fun calculateMapCapacity(expectedSize: Int): Int = when {
    expectedSize < 3 -> expectedSize + 1
    expectedSize < INT_MAX_POWER_OF_TWO -> expectedSize + expectedSize / 3
    // any large value
    else -> Int.MAX_VALUE
}