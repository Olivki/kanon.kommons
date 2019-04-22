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

package moe.kanon.kommons.collections

import moe.kanon.kommons.collections.iterators.UnmodifiableIterator

interface ImmutableCollection<out T> : Iterable<T> {
    /**
     * The size of `this` collection.
     */
    val size: Int

    /**
     * Returns whether or not `this` collection is empty.
     */
    fun isEmpty(): Boolean

    /**
     * Returns whether or not `this` collection contains the specified [element].
     */
    operator fun contains(element: @UnsafeVariance T): Boolean

    /**
     * Returns a iterator specific to `this` collection.
     */
    override fun iterator(): UnmodifiableIterator<T>
}