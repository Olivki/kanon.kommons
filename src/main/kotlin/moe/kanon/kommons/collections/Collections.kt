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

@file:JvmName("KCollections")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.collections

import java.util.*

/**
 * Removes all of the supplied [elements] from the collection and returns this [collection][MutableCollection].
 */
public inline fun <V, C : MutableCollection<V>> C.removeAll(vararg elements: V): C {
    this.removeAll(elements)
    return this
}

/**
 * Adds all of the supplied [elements] to the collection and returns this [collection][MutableCollection].
 */
public inline fun <V, C : MutableCollection<V>> C.addAll(vararg elements: V): C {
    this.removeAll(elements)
    return this
}

/**
 * Invokes the specified [action] only if `this` collection is empty.
 *
 * @receiver the [Collection] to check against.
 */
public inline fun <reified T, reified C : Collection<T>> C.ifEmpty(action: (C) -> Unit): C =
    if (this.isEmpty()) this.apply(action) else this

/**
 * Invokes the specified [action] only if `this` collection is not empty.
 *
 * @receiver the [Collection] to check against.
 */
public inline fun <reified T, reified C : Collection<T>> C.ifNotEmpty(action: (C) -> Unit): C =
    if (this.isNotEmpty()) this.apply(action) else this

// collections jvm
/**
 * Returns an unmodifiable view of `this` collection.
 *
 * This function allows modules to provide users with "read-only" access to internal collections.
 *
 * Query operations on the returned collection "read through" to the specified collection, and attempts to modify the
 * returned collection, whether direct or via its iterator, result in an [UnsupportedOperationException].
 *
 * The returned collection does *not* pass the hashCode and equals operations through to the backing collection, but
 * relies on `Any`'s `equals` and `hashCode` function.  This is necessary to preserve the contracts of these operations
 * in the case that the backing collection is a [Set] or a [List].
 *
 * The returned collection will be serializable if the specified collection is serializable.
 *
 * @receiver the collection for which an unmodifiable view is to be returned
 *
 * @param [V] the type that `this` collection stores
 *
 * @return an unmodifiable view of `this` collection
 */
public inline fun <V> Collection<V>.toUnmodifiableCollection(): Collection<V> = Collections.unmodifiableCollection(this)