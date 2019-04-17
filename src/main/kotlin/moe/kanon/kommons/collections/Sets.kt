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
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.collections

import java.util.*

// to


// collections jvm
/**
 * Returns an unmodifiable view of `this` set.
 *
 * This function allows modules to provide users with "read-only" access to internal sets.
 *
 * Query operations on the returned list "read through" to the specified set, and attempts to modify the returned set,
 * whether direct or via its iterator, result in an [UnsupportedOperationException].
 *
 * The returned list will be serializable if the specified set is serializable. Similarly, the returned set will
 * implement [RandomAccess] if the specified set does.
 *
 * @receiver the set for which an unmodifiable view is to be returned
 *
 * @param [V] the type that `this` set stores
 *
 * @return an unmodifiable view of `this` set
 */
@JvmName("toUnmodifiable")
fun <V> Set<V>.toUnmodifiableSet(): Set<V> = Collections.unmodifiableSet(this)

/**
 * Creates and returns a unmodifiable list containing the specified [values].
 *
 * @param [V] the type of the entries of the set
 * @param [values] the values to populate the set with
 *
 * @return the newly created set
 */
@JvmName("unmodifiable")
fun <V> unmodifiableSetOf(vararg values: V): Set<V> = Collections.unmodifiableSet(values.toSet())