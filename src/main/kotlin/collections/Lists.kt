/*
 * Copyright 2019 Oliver Berg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmName("KLists")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.collections

import java.util.*

public inline fun <V> List<V>.from(start: Int) = slice(start until size)

// collections jvm
/**
 * Returns an unmodifiable view of `this` list.
 *
 * This function allows modules to provide users with "read-only" access to internal lists.
 *
 * Query operations on the returned list "read through" to the specified list, and attempts to modify the returned list,
 * whether direct or via its iterator, result in an [UnsupportedOperationException].
 *
 * The returned list will be serializable if the specified list is serializable. Similarly, the returned list will
 * implement [RandomAccess] if the specified list does.
 *
 * @receiver the list for which an unmodifiable view is to be returned
 *
 * @param [V] the type that `this` list stores
 *
 * @return an unmodifiable view of `this` list
 */
public inline fun <V> List<V>.toUnmodifiableList(): List<V> = Collections.unmodifiableList(this)

/**
 * Creates and returns a unmodifiable list containing the specified [values].
 *
 * @param [V] the type of the entries of the list
 * @param [values] the values to populate the list with
 *
 * @return the newly created list
 */
public fun <V> unmodifiableListOf(vararg values: V): List<V> = Collections.unmodifiableList(values.toList())