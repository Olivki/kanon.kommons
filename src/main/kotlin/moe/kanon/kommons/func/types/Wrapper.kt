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

package moe.kanon.kommons.func.types

interface Wrapper<out T> {
    /**
     * The value that `this` container is wrapped around.
     *
     * If the implementation container does *not* actually hold a value, then any invocations of this property should
     * throw a [NoSuchElementException].
     *
     * @throws [NoSuchElementException] if `this` container does not contain a value
     */
    val value: T @JvmSynthetic get

    /**
     * Calls the given [action] *once* with the [value] of `this` wrapper if it is present or returns [Unit] if it is
     * not.
     */
    infix fun forEach(action: (T) -> Unit)

    @JvmDefault
    fun get(): T = value

    fun getOrNull(): T?

    infix fun getOrElse(default: @UnsafeVariance T): T

    infix fun getOrElse(default: () -> @UnsafeVariance T): T

    infix fun <X : Throwable> getOrThrow(throwable: () -> X): T

    operator fun contains(other: @UnsafeVariance T): Boolean

    fun asList(): List<T>
}

@Suppress("OVERRIDE_BY_INLINE")
object EmptyWrapper : Wrapper<Nothing> {
    override val value: Nothing get() = throw NoSuchElementException("Container is empty")

    override inline fun forEach(action: (Nothing) -> Unit) {}

    override fun getOrNull(): Nothing? = null

    override fun getOrElse(default: Nothing): Nothing = default

    override inline fun getOrElse(default: () -> Nothing): Nothing = default()

    override inline fun <X : Throwable> getOrThrow(throwable: () -> X): Nothing = throw throwable()

    override fun contains(other: Nothing): Boolean = false

    override fun asList(): List<Nothing> = emptyList()

    override fun toString(): String = "EmptyWrapper"
}

abstract class AbstractWrapper<out T> : Wrapper<T> {
    override fun forEach(action: (T) -> Unit) = action(value)

    override fun getOrNull(): T? = value

    override fun getOrElse(default: @UnsafeVariance T): T = value

    override fun getOrElse(default: () -> @UnsafeVariance T): T = value

    override fun <X : Throwable> getOrThrow(throwable: () -> X): T = value

    override fun contains(other: @UnsafeVariance T): Boolean = other == value

    override fun asList(): List<T> = listOf(value)

    override fun toString(): String = "Wrapper[$value]"
}

data class BasicWrapper<out T>(override val value: T) : AbstractWrapper<T>() {
    override fun toString(): String = "Wrapper[$value]"
}