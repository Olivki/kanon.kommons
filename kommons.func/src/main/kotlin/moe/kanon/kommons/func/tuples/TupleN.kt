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

package moe.kanon.kommons.func.tuples

import java.lang.UnsupportedOperationException

/**
 * A [tuple][Tuple0] that can store a variable arity amount of values inside of it.
 *
 * To retrieve values from this tuple use either [get] or [invoke] for null-safe retrieval.
 */
class TupleN(private vararg val objects: Any?) : Iterable<Any?> {
    /**
     * Returns the value stored under the specified [index] of `this` tuple casted to [T].
     */
    @Suppress("UNCHECKED_CAST")
    @JvmName("getNullable")
    operator fun <T> get(index: Int): T? = objects[index] as T?

    /**
     * Returns the value stored under the specified [index] of `this` tuple casted to [T].
     *
     * Unlike [get] this function is `null` safe, meaning that it will never return a `null` value.
     *
     * @throws [UnsupportedOperationException] if the value stored under the specified `index` is `null`
     */
    @Suppress("UNCHECKED_CAST")
    @JvmName("get")
    operator fun <T> invoke(index: Int): T =
        objects[index] as T? ?: throw UnsupportedOperationException("Value under index <$index> is null")

    override fun iterator(): Iterator<Any?> = objects.iterator()

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        this.javaClass != other?.javaClass -> false
        other !is TupleN -> false
        else -> objects.contentEquals(other.objects)
    }

    override fun hashCode(): Int = objects.contentHashCode()

    override fun toString(): String = "(${objects.joinToString()})"
}