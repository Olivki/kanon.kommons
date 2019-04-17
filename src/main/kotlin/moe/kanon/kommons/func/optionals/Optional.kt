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

@file:JvmName("KOptional")
@file:Suppress("NOTHING_TO_INLINE", "MemberVisibilityCanBePrivate")

package moe.kanon.kommons.func.optionals

import moe.kanon.kommons.func.Consumer
import moe.kanon.kommons.func.Func
import moe.kanon.kommons.func.Predicate
import moe.kanon.kommons.func.Supplier

typealias JOptional<T> = java.util.Optional<T>

/**
 * A container object which may or may not contain a non-null value.
 *
 * If a value is present, [isPresent] will return `true` and [get] will return the value.
 */
// mainly ported from the java version, with some things taken from arrow (which themselves are taken from scala)
sealed class Optional<T> {
    protected abstract val value: T

    @get:JvmName("toJava")
    val java: JOptional<T> by lazy {
        JOptional.ofNullable(orNull())
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
    fun get(): T = orThrow { NoSuchElementException("Optional has no value") }

    /**
     * Returns the value of `this` optional if it [is present][isPresent], otherwise returns `null`.
     */
    fun orNull(): T? = when (this) {
        is None -> null
        is Some<T> -> value
    }

    /**
     * Returns the value of `this` optional, or returns the value of the specified [default] if a value is not present.
     */
    inline infix fun orElseGet(default: Supplier<out T>): T = when (this) {
        is None -> default()
        is Some<T> -> value
    }

    /**
     * Returns the value of `this` optional, or returns the specified [default] value if a value is not present.
     */
    infix fun orElse(default: T): T = orElseGet { default }

    /**
     * Returns the value of `this` optional, or throws the specified [exception] if a value is not present.
     *
     * Note that the [get] function *does* throw a [NoSuchElementException] if no value is present, so this should
     * generally only be used if a specialized exception is needed.
     */
    inline infix fun <X : Exception> orThrow(exception: Supplier<out X>): T = when (this) {
        is None -> throw exception()
        is Some<T> -> value
    }

    /**
     * Executes the specified [action] if a value [is present][isPresent].
     */
    inline infix fun ifPresent(action: Consumer<in T>) {
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
    inline fun <R> fold(ifNotPresent: Supplier<out R>, ifPresent: Func<in T, out R>): R = when (this) {
        is None -> ifNotPresent()
        is Some<T> -> ifPresent(value)
    }

    /**
     * If a value is present, apply the provided [transformer] function to it, and if the result is non-null, return [Some]
     * describing the result, otherwise return [None].
     *
     * @param [U] The type of the result of the mapping function
     *
     * @param [transformer] a mapping function to apply to the value, if present
     */
    inline fun <U> map(transformer: Func<in T, out U?>): Optional<U> = when (this) {
        is None -> empty()
        is Some<T> -> transformer(value).wrap()
    }

    /**
     * If a value is present, apply the provided [Optional]-bearing [transformer] function to it and return that result,
     * otherwise return [None].
     *
     * This method is similar to [map], but the provided mapper is one whose result is already an `Optional`,
     * and if invoked, `flatMap` does not wrap it with an additional `Optional`.
     *
     * @param [U] the type parameter of the `Optional` instance returned by the [transformer] function
     *
     * @param [transformer] a mapping function to apply to the value, if present
     */
    inline fun <U> flatMap(transformer: Func<T, Optional<U>>): Optional<U> = when (this) {
        is None -> empty()
        is Some<T> -> transformer(value)
    }

    /**
     * If a value is present, and the value matches the given predicate, return [Some] describing the value, otherwise
     * return [None].
     *
     * @param [predicate] a predicate to apply to the value, if present
     */
    inline fun filter(predicate: Predicate<in T>): Optional<T> =
        flatMap { if (predicate(it)) Some(it) else empty<T>() }

    /**
     * If a value is present, and the value does *not* match the given predicate, return [Some] describing the value,
     * otherwise return [None].
     *
     * @param [predicate] a predicate to apply to the value, if present
     */
    inline fun filterNot(predicate: Predicate<in T>): Optional<T> =
        flatMap { if (!predicate(it)) Some(it) else empty<T>() }

    /**
     * Returns `false` if no value is present, if a value is present, it returns the result of calling [predicate] with
     * the value.
     */
    inline fun exists(predicate: Predicate<in T>): Boolean = fold({ false }, { predicate(it) })

    /**
     * If no value is present returns [None], otherwise returns [other].
     */
    infix fun <U> and(other: Optional<U>): Optional<U> = if (isNotPresent()) empty() else other

    /**
     * Returns `this` if a value is present, otherwise returns [other].
     */
    infix fun or(other: Optional<T>): Optional<T> = if (isPresent()) this else other

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is Optional<*> -> false
        value != other.value -> false
        else -> true
    }

    /**
     * Returns the hash-code of the value of `this` optional, or `0` if no value is present.
     */
    override fun hashCode(): Int = when (this) {
        is None -> 0
        is Some<T> -> value.hashCode()
    }

    override fun toString(): String = when (this) {
        is None -> "Optional.empty"
        is Some<T> -> "Optional[$value]"
    }

    companion object {
        @JvmStatic
        fun <T> of(value: T): Optional<T> = Some(value)

        @JvmStatic
        fun <T> ofNullable(value: T?): Optional<T> = when (value) {
            null -> empty()
            else -> Some(value)
        }

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T> empty(): Optional<T> = None as Optional<T>
    }
}

/**
 * Represents an empty [Optional] value.
 */
object None : Optional<Nothing>() {
    override val value: Nothing
        get() = throw UnsupportedOperationException("'None' has no value")

    override fun isPresent(): Boolean = false

    override fun toString(): String = "None"

    override fun hashCode(): Int = 0
}

/**
 * Represents a present [Optional] value.
 */
data class Some<T>(public override val value: T) : Optional<T>() {
    override fun isPresent(): Boolean = true

    override fun toString(): String = "Some[$value]"
}

@JvmName("fromNullable")
fun <T> T?.wrap(): Optional<T> = Optional.ofNullable(this)

val <T> JOptional<T>.kotlin: Optional<out T>
    @JvmName("fromJava") get() = when {
        this.isPresent -> Optional.of(this.get())
        else -> Optional.empty()
    }

/**
 * If `this` boolean is `true`, returns the specified [item] wrapped in a [Some], otherwise returns [None].
 */
@JvmName("fromBoolean")
infix fun <T> Boolean.maybe(item: T): Optional<T> = if (this) Optional.of(item) else Optional.empty()