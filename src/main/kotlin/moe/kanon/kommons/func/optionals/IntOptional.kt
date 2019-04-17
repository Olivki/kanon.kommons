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

@file:JvmName("IntOptionals")

package moe.kanon.kommons.func.optionals

import moe.kanon.kommons.func.Consumer
import moe.kanon.kommons.func.Func
import moe.kanon.kommons.func.Predicate
import moe.kanon.kommons.func.Supplier

typealias JOptionalInt = java.util.OptionalInt

sealed class OptionalInt {
    protected abstract val value: Int

    @get:JvmName("toJava")
    val java: JOptionalInt by lazy {
        when (this) {
            is NoInt -> JOptionalInt.empty()
            is SomeInt -> JOptionalInt.of(value)
        }
    }

    /**
     * Returns whether or not a value is present in `this` optional.
     */
    abstract fun isPresent(): Boolean

    /**
     * Returns whether or not a value is *not* present in `this` optional.
     */
    fun isNotPresent(): Boolean = !isPresent()

    // functions
    /**
     * If `this` optional has a [value] then it returns it, otherwise it will throw a [NoSuchElementException].
     */
    fun get(): Int = orThrow { NoSuchElementException("Optional has no value") }

    /**
     * Returns the value of `this` optional, or returns the value of the specified [default] if a value is not present.
     */
    inline infix fun orElseGet(default: Supplier<out Int>): Int = when (this) {
        is NoInt -> default()
        is SomeInt -> value
    }

    /**
     * Returns the value of `this` optional, or returns the specified [default] value if a value is not present.
     */
    infix fun orElse(default: Int): Int = orElseGet { default }

    /**
     * Returns the value of `this` optional, or throws the specified [exception] if a value is not present.
     *
     * Note that the [get] function *does* throw a [NoSuchElementException] if no value is present, so this should
     * generally only be used if a specialized exception is needed.
     */
    inline infix fun <X : Exception> orThrow(exception: Supplier<out X>): Int = when (this) {
        is NoInt -> throw exception()
        is SomeInt -> value
    }

    /**
     * Executes the specified [action] if a value [is present][isPresent].
     */
    inline infix fun ifPresent(action: Consumer<in Int>) {
        if (isPresent()) action(get())
    }

    /**
     * Executes either the [ifNotPresent] closure, or the [ifPresent] closure depending on whether a value
     * [is present][isPresent] or not.
     *
     * @param [R] the value to return
     *
     * @param [ifNotPresent] the closure to execute if a value [is not present][isNotPresent] in `this` optional
     * @param [ifPresent] the closure to execute if a value [is present][isPresent] in `this` optional
     *
     * @return the value from the [ifNotPresent] closure if a value [is not present][isNotPresent], or the value from the
     * [ifPresent] closure if a value [is present][isPresent].
     */
    inline fun <R> fold(ifNotPresent: Supplier<out R>, ifPresent: Func<in Int, out R>): R = when (this) {
        is NoInt -> ifNotPresent()
        is SomeInt -> ifPresent(value)
    }

    /**
     * If a value is present, apply the provided [mapper] function to it, and if the result is non-null, return [Some]
     * describing the result, otherwise return [None].
     *
     * @param [U] The type of the result of the mapping function
     *
     * @param [mapper] a mapping function to apply to the value, if present
     */
    inline fun <U> map(mapper: Func<in Int, out U?>): Optional<U> = when (this) {
        is NoInt -> Optional.empty()
        is SomeInt -> mapper(value).wrap()
    }

    /**
     * If a value is present, apply the provided [Optional]-bearing [mapper] function to it and return that result,
     * otherwise return [None].
     *
     * This method is similar to [map], but the provided mapper is one whose result is already an `Optional`,
     * and if invoked, `flatMap` does not wrap it with an additional `Optional`.
     *
     * @param [U] the type parameter of the `Optional` instance returned by the [mapper] function
     *
     * @param [mapper] a mapping function to apply to the value, if present
     */
    inline fun flatMap(mapper: Func<in Int, out OptionalInt>): OptionalInt = when (this) {
        is NoInt -> this
        is SomeInt -> mapper(value)
    }

    /**
     * If a value is present, and the value matches the given predicate, return [Some] describing the value, otherwise
     * return [None].
     *
     * @param [predicate] a predicate to apply to the value, if present
     */
    inline fun filter(predicate: Predicate<in Int>): OptionalInt =
        flatMap { if (predicate(it)) SomeInt(it) else NoInt }

    /**
     * If a value is present, and the value does *not* match the given predicate, return [Some] describing the value,
     * otherwise return [None].
     *
     * @param [predicate] a predicate to apply to the value, if present
     */
    inline fun filterNot(predicate: Predicate<in Int>): OptionalInt =
        flatMap { if (!predicate(it)) SomeInt(it) else NoInt }

    /**
     * Returns `false` if no value is present, if a value is present, it returns the result of calling [predicate] with
     * the value.
     */
    inline fun exists(crossinline predicate: Predicate<in Int>): Boolean = fold({ false }, { predicate(it) })

    /**
     * If no value is present returns [None], otherwise returns [other].
     */
    infix fun and(other: OptionalInt): OptionalInt = if (isNotPresent()) empty() else other

    /**
     * Returns `this` if a value is present, otherwise returns [other].
     */
    infix fun or(other: OptionalInt): OptionalInt = if (isPresent()) this else other

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is OptionalInt -> false
        value != other.value -> false
        else -> true
    }

    /**
     * Returns the hash-code of the value of `this` optional, or `0` if no value is present.
     */
    override fun hashCode(): Int = when (this) {
        is NoInt -> 0
        is SomeInt -> value.hashCode()
    }

    override fun toString(): String = when (this) {
        is NoInt -> "OptionalInt.empty"
        is SomeInt -> "OptionalInt[$value]"
    }

    companion object {
        @JvmStatic
        fun of(value: Int): OptionalInt = SomeInt(value)

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun empty(): OptionalInt = NoInt
    }
}

object NoInt : OptionalInt() {
    override val value: Nothing
        get() = throw UnsupportedOperationException("'NoneInt' has no value")

    override fun isPresent(): Boolean = false

    override fun toString(): String = "NoInt"

    override fun hashCode(): Int = 0
}

data class SomeInt(public override val value: Int) : OptionalInt() {
    override fun isPresent(): Boolean = true

    override fun toString(): String = "SomeInt[$value]"
}

val JOptionalInt.kotlin: OptionalInt
    @JvmName("fromJava") get() = when {
        this.isPresent -> OptionalInt.of(this.asInt)
        else -> OptionalInt.empty()
    }