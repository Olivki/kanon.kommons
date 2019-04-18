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
@file:Suppress("NOTHING_TO_INLINE", "MemberVisibilityCanBePrivate", "UNCHECKED_CAST")

package moe.kanon.kommons.func.optional

import moe.kanon.kommons.collections.iterators.EmptyIterator
import moe.kanon.kommons.collections.iterators.SingletonIterator
import moe.kanon.kommons.func.result.Result
import moe.kanon.kommons.func.Consumer
import moe.kanon.kommons.func.Func
import moe.kanon.kommons.func.Predicate
import moe.kanon.kommons.func.Supplier

typealias JOptional<T> = java.util.Optional<T>

/**
 * A container object which may or may not contain a non-null value.
 *
 * If a value is present, [isPresent] will return `true` and [get] will return the value.
 *
 * This is a [value-based](https://docs.oracle.com/javase/8/docs/api/java/lang/doc-files/ValueBased.html) class; use of
 * identity-sensitive operations (including reference equality (`==`), identity hash code, or synchronization) on
 * instances of this may have unpredictable results and should be avoided.
 */
// mainly ported from the java version, with some things taken from arrow (which themselves are taken from scala)
// the <T> generic is not marked as 'out' here to enable the use of the 'getOrElse' functions inside of the actual
// Optional class rather than have to make them as extension functions, this is done to make sure that this class
// does not feel clunky to use from within java.
sealed class Optional<T> : Iterable<T> {
    /**
     * The underlying value of `this` optional.
     *
     * What this property actually returns is implementation specific.
     */
    protected abstract val value: T

    /**
     * Creates and returns a [JOptional][java.util.Optional] based on `this` optional.
     *
     * After the first invocation of this function, any subsequent invocations on the same `Optional` instance will
     * return the same result, as the result is cached upon the first invocation.
     */
    @get:JvmName("toJava")
    val java: JOptional<T> by lazy {
        JOptional.ofNullable(getOrNull())
    }

    abstract fun isPresent(): Boolean

    fun isEmpty(): Boolean = !isPresent()

    // functions
    /**
     * Returns the [value] of `this` optional if it is present, otherwise throws a [NoSuchElementException].
     */
    fun get(): T = getOrThrow { NoSuchElementException("Optional has no value") }

    /**
     * Returns the [value] of `this` optional if it is present, otherwise returns `null`.
     */
    fun getOrNull(): T? = when (this) {
        is None -> null
        is Some<T> -> value
    }

    /**
     * Returns the value of `this` optional, or returns the value of the specified [default] if a value is not present.
     */
    inline infix fun getOrElse(default: Supplier<out T>): T = when (this) {
        is None -> default()
        is Some<T> -> value
    }

    /**
     * Returns the [value] of `this` optional if it is present, otherwise returns [default].
     */
    infix fun getOrElse(default: T): T = getOrElse { default }

    /**
     * Returns the value of `this` optional, or throws the specified [exception] if a value is not present.
     *
     * Note that the [get] function *does* throw a [NoSuchElementException] if no value is present, so this should
     * generally only be used if a specialized exception is needed.
     */
    inline infix fun <X : Exception> getOrThrow(exception: Supplier<out X>): T = when (this) {
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
     * Returns the result of invoking either [ifEmpty] or [ifPresent] depending on whether or not a value is present.
     *
     * @param [R] the value to return
     *
     * @param [ifEmpty] invoked if there is no value present
     * @param [ifPresent] invoked with the [value] if it is present
     */
    inline fun <R> fold(ifEmpty: Supplier<out R>, ifPresent: Func<in T, out R>): R = when (this) {
        is None -> ifEmpty()
        is Some<T> -> ifPresent(value)
    }

    /**
     * Returns the result of applying [value] to the [transformer] if present, otherwise returns [None].
     *
     * Note that if the result of applying `value` to the `transformer` results in `null` then [None] is returned.
     */
    inline fun <U> map(transformer: Func<in T, out U?>): Optional<U> = when (this) {
        is None -> None()
        is Some -> transformer(value).wrap()
    }

    /**
     * Returns the result of applying [value] to the [transformer] if present, otherwise returns [None].
     *
     * This function is similar to [map], but the `transformer` is one whose result is already an `Optional`,
     * and if invoked, `flatMap` does not wrap it with an additional `Optional`.
     */
    inline fun <U> flatMap(transformer: Func<T, Optional<U>>): Optional<U> = when (this) {
        is None -> None()
        is Some -> transformer(value)
    }

    /**
     * Returns [Some] if a value that matches the [predicate] is present, otherwise returns [None].
     */
    inline fun filter(predicate: Predicate<in T>): Optional<T> =
        flatMap { if (predicate(it)) Some(it) else None<T>() }

    /**
     * Returns [Some] if a value that does *not* match the [predicate] is present, otherwise returns [None].
     */
    inline fun filterNot(predicate: Predicate<in T>): Optional<T> =
        flatMap { if (!predicate(it)) Some(it) else None<T>() }

    /**
     * Returns `false` if no value is present, otherwise returns the result of calling [predicate] with the [value].
     */
    inline fun any(predicate: Predicate<in T>): Boolean = fold({ false }, { predicate(it) })

    /**
     * Returns `true` if no value is present, otherwise returns the result of calling [predicate] with the [value].
     */
    inline fun all(predicate: Predicate<in T>): Boolean = fold({ true }, predicate)

    /**
     * If no value is present returns [None], otherwise returns [other].
     */
    infix fun <U> and(other: Optional<U>): Optional<U> = if (isEmpty()) None() else other

    /**
     * Returns `this` if a value is present, otherwise returns [other].
     */
    infix fun or(other: Optional<T>): Optional<T> = if (isPresent()) this else other

    /**
     * If a value is present, returns whether or not [value][Optional.value] is equal to the specified [value],
     * otherwise returns `false`.
     */
    @JvmName("isValueEqualTo")
    operator fun contains(value: T): Boolean = when (this) {
        is None -> false
        is Some -> value == this.value
    }

    /**
     * Returns the hash-code of the [value] of `this` optional, or `0` if no value is present.
     */
    abstract override fun hashCode(): Int

    abstract override fun equals(other: Any?): Boolean

    abstract override fun toString(): String

    /**
     * If a [value] is present returns a [SingletonIterator], otherwise returns a [EmptyIterator].
     */
    abstract override fun iterator(): Iterator<T>

    companion object {
        // kotlin only
        /**
         * Returns a [Some] containing the specified [value], or [None] if `value` is `null`.
         */
        // enables the syntax of 'Optional(67)'
        @JvmSynthetic
        operator fun <T> invoke(value: T?): Optional<T> = ofNullable(value)

        /**
         * Returns a [Some] containing the result of invoking the specified [value], or [None] if `value` is `null`.
         */
        // enables the syntax of 'Optional { 67 }'
        @JvmSynthetic
        inline operator fun <reified T : Any> invoke(value: Supplier<T?>): Optional<T> = value().wrap()

        // jvm & kotlin
        /**
         * Returns a [Some] containing the specified [value].
         */
        @JvmStatic
        fun <T> of(value: T): Optional<T> = Some(value)

        /**
         * Returns a [Some] containing the specified [value], or [None] if `value` is `null`.
         */
        @JvmStatic
        fun <T> ofNullable(value: T?): Optional<T> = when (value) {
            null -> None()
            else -> Some(value)
        }

        /**
         * Invokes the specified [value] wrapped in a `try catch` block, returns [Some] if no errors are thrown,
         * otherwise returns [None].
         *
         * This function enables one to use the [Optional] class as a type of [Result]. Do note that unlike `Result`,
         * no information regarding the thrown exception is saved in the `Optional` form, as `None` does not
         * contain any data.
         */
        inline fun <T> tryCatch(value: Supplier<T>): Optional<T> = try {
            Some(value())
        } catch (e: Exception) {
            None()
        }

        /**
         * Returns [None] cast to [T].
         */
        @JvmStatic
        fun <T> empty(): Optional<T> = None()
    }
}

/**
 * Represents an empty [Optional] value.
 */
object None : Optional<Nothing>() {
    override val value: Nothing
        get() = throw UnsupportedOperationException("Can not retrieve value of 'None'")

    override fun isPresent(): Boolean = false

    override fun hashCode(): Int = 0

    override fun equals(other: Any?): Boolean = when (other) {
        this -> true
        else -> false
    }

    override fun toString(): String = "None"

    override fun iterator(): Iterator<Nothing> = EmptyIterator

    // dirty hacks
    @JvmStatic
    fun <T> castTo(clz: Class<T>): Optional<T> = None as Optional<T>

    @JvmSynthetic
    inline operator fun <T> invoke(): Optional<T> = None as Optional<T>
}

/**
 * Represents a present [Optional] value.
 */
@Suppress("EqualsOrHashCode", "DataClassPrivateConstructor")
data class Some<T> private constructor(public override val value: T) : Optional<T>() {
    override fun isPresent(): Boolean = true

    override fun hashCode(): Int = value.hashCode()

    override fun equals(other: Any?): Boolean = value?.equals(other) ?: false

    override fun toString(): String = "Some[$value]"

    override fun iterator(): Iterator<T> = SingletonIterator(value)

    companion object {
        @JvmStatic
        @JvmName("of")
        operator fun <T> invoke(value: T): Optional<T> = Some(value)
    }
}

/**
 * Returns a [Some] containing `this`, or [None] if `this` is `null`.
 */
@JvmName("fromNullable")
fun <T> T?.wrap(): Optional<T> = Optional.ofNullable(this)

/**
 * Returns the [value][Optional.value] of `this` optional if it is present, otherwise returns `null`.
 */
@JvmName("toValue")
fun <T> Optional<T>.unwrap(): T? = getOrNull()

/**
 * Converts `this` [java.util.Optional] to a [Optional] instance.
 *
 * If a value is not present in `this` then the resulting `Optional` will be [None].
 */
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