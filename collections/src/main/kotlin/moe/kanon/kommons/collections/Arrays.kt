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

@file:JvmName("KArrays")

package moe.kanon.kommons.collections

/**
 * Creates a new generic [Array] of the specified [size] filled with `null` values.
 */
@Suppress("UNCHECKED_CAST")
fun <T> createArray(size: Int): Array<T> = arrayOfNulls<Any>(size) as Array<T>

/**
 * Creates a new generic [Array] of the specified [size] filled with the specified [default] value.
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> createArray(size: Int, default: T): Array<T> = Array<Any>(size) { default } as Array<T>

// TODO: implement an actual proper way of doing this rather than just having it be a wrapper

/**
 * Returns a [Map] where keys are elements from the given collection and values are produced by the [valueSelector]
 * function applied to each element.
 *
 * If any two elements are equal, the last one gets added to the map.
 *
 * The returned map preserves the entry iteration order of the original collection.
 */
inline fun <K, V> Array<K>.associateWith(valueSelector: (K) -> V): Map<K, V> = asIterable().associateWith(valueSelector)

/**
 * Populates and returns the [destination] mutable map with key-value pairs for each element of the given collection,
 * where key is the element itself and value is provided by the [valueSelector] function applied to that key.
 *
 * If any two elements are equal, the last one overwrites the former value in the map.
 */
inline fun <K, V, M : MutableMap<in K, in V>> Array<K>.associateWithTo(
    destination: M,
    valueSelector: (K) -> V
): M = asIterable().associateWithTo(destination, valueSelector)