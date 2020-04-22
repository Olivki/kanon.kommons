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

package moe.kanon.kommons.func

import moe.kanon.kommons.requireThat
import kotlin.reflect.typeOf

/**
 * A tuple that can store a variable arity amount of values inside of it.
 *
 * To retrieve values from this tuple use either the [get] or [invoke] operators for `null`-safe retrieval.
 */
class TupleN(private vararg val objects: Any?) : Iterable<Any?> {
    /**
     * Returns how many objects are stored in `this` tuple.
     */
    val size: Int
        get() = objects.size

    // -- GET VALUE -- \\
    /**
     * Returns the value stored under the given [index], cast to [T].
     *
     * @param [T] the type to cast the value to
     *
     * @throws [IllegalArgumentException] if `index` < `0`, `index` > [size], the value stored under the given
     * `index` is `null` or the value stored under `index` is *not* of the given [type][T]
     */
    @JvmSynthetic
    @OptIn(ExperimentalStdlibApi::class)
    inline operator fun <reified T> get(index: Int): T {
        val value = getRaw(index)
        requireThat(value is T) { "Value stored under index <$index> is not of type <${typeOf<T>()}>" }
        return value
    }

    /**
     * Returns the value stored under the given [index], cast to [T].
     *
     * @param [T] the type to cast the value to
     *
     * @throws [IllegalArgumentException] if `index` < `0`, `index` > [size], the value stored under the given
     * `index` is `null` or the value stored under `index` is *not* of the given [type][T]
     */
    @JvmSynthetic
    inline operator fun <reified T> invoke(index: Int): T = this[index]

    /**
     * Returns the `nullable` value stored under the given [index], cast to [T].
     *
     * @param [T] the type to cast the value to
     *
     * @throws [IllegalArgumentException] if `index` < `0`, `index` > [size] or the value stored under `index` is *not*
     * of the given [type][T]
     */
    @JvmSynthetic
    @OptIn(ExperimentalStdlibApi::class)
    inline fun <reified T> getNullable(index: Int): T? {
        val value = getRawNullable(index)
        requireThat(value is T?) { "Value stored under index <$index> is not of type <${typeOf<T>()}?>" }
        return value
    }

    /**
     * Returns the raw value stored under the given [index].
     *
     * @throws [IllegalArgumentException] if `index` < `0`, `index` > [size] or the value stored under the given
     * `index` is `null`
     */
    @JvmName("getRawValue")
    fun getRaw(index: Int): Any {
        requireThat(index >= 0, "index >= 0")
        requireThat(index < size, "index < this.size")
        val value = objects[index]
        requireThat(value != null) { "Value under index <$index> is null" }
        return value
    }

    /**
     * Returns the raw `nullable` value stored under the given [index].
     *
     * @throws [IllegalArgumentException] if `index` < `0` or `index` > [size]
     */
    @JvmName("getRawNullableValue")
    fun getRawNullable(index: Int): Any? {
        requireThat(index >= 0, "index >= 0")
        requireThat(index < size, "index < this.size")
        return objects[index]
    }

    // -- OVERRIDES -- \\
    override fun iterator(): Iterator<Any?> = objects.iterator()

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        this.javaClass != other?.javaClass -> false
        other !is TupleN -> false
        else -> objects.contentDeepEquals(other.objects)
    }

    override fun hashCode(): Int = objects.contentDeepHashCode()

    override fun toString(): String = when {
        objects.isEmpty() -> "()"
        else -> {
            val content = objects.contentDeepToString()
            "(${content.substring(1 until content.length)})"
        }
    }
}