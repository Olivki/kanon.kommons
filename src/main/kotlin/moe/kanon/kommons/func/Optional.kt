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

package moe.kanon.kommons.func

import moe.kanon.kommons.ScopedFunction
import moe.kanon.kommons.collections.iterators.EmptyIterator
import moe.kanon.kommons.collections.iterators.SingletonIterator
import moe.kanon.kommons.requireNonFatal
import kotlin.contracts.contract

/*
 * This is based on the Optional[1] class from the Java standard library, and the Option[2] class from the scala
 * standard library.
 *
 * The main goal of this class is to provide good interop when working with java, as a 'Option' class may not be
 * very needed in Kotlin as it has a good set of tools to deal with nullability and the like, but when designing
 * something that should also work with Java the Java side loses out on all the null-safety that Kotlin offers.
 *
 * Unlike the version available in Arrow, this port has been converted to be more "idiomatic" and more closely follow
 * the principles of Kotlin and its naming schemes.
 *
 * [1]: https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/file/cecd70d27b27/src/share/classes/java/util/Optional.java
 * [2]: https://github.com/scala/scala/blob/2.13.x/src/library/scala/Option.scala
 */

typealias JOptional<T> = java.util.Optional<T>

/**
 * A container object which may or may not contain a non-null value.
 *
 * If a value is present, [isPresent][Optional.isPresent] will return `true` and [get][Optional.value] will return the
 * value.
 */
typealias Option<T> = Optional<T>

/**
 * A container object which may or may not contain a non-null value.
 *
 * If a value is present, [isPresent][Optional.isPresent] will return `true` and [get][Optional.value] will return the
 * value.
 */
typealias Maybe<T> = Optional<T>

/**
 * A container object which may or may not contain a non-null value.
 *
 * If a value is present, [isPresent] will return `true` and [value] will return the value.
 */
sealed class Optional<out T> {
    /**
     * Returns the [item][Some.item] if `this` is [Some], or throws a [NoSuchElementException] if `this` is [None].
     */
    val value: T
        @JvmName("get") get() = when (this) {
            is None -> throw NoSuchElementException("No value is present in this optional")
            is Some -> item
        }

    /**
     * Returns a [SingletonIterator] if a value is present, otherwise returns a [SingletonIterator].
     */
    // TODO: Change this to a function
    val iterator: Iterator<T>
        get() = when (this) {
            is None -> EmptyIterator
            is Some -> SingletonIterator(item)
        }

    abstract val isPresent: Boolean

    val isEmpty: Boolean get() = !isPresent

    /**
     * Creates and returns a [JOptional][java.util.Optional] based on `this` optional.
     *
     * After the first invocation of this function, any subsequent invocations on the same `Optional` instance will
     * return the same result, as the result is cached upon the first invocation.
     */
    @get:JvmName("toJava")
    val java: JOptional<@UnsafeVariance T> by lazy {
        JOptional.ofNullable(getOrNull())
    }

    /**
     * Returns the [value] of `this` optional if it is present, otherwise returns `null`.
     */
    fun getOrNull(): T? = when (this) {
        is None -> null
        is Some -> item
    }

    /**
     * Returns the value of `this` optional, or returns the value of the specified [default] if a value is not present.
     */
    inline fun getOrElse(default: () -> @UnsafeVariance T): T = when (this) {
        is None -> default()
        is Some -> item
    }

    /**
     * Returns the [value] of `this` optional if it is present, otherwise returns [default].
     */
    infix fun getOrElse(default: @UnsafeVariance T): T = getOrElse { default }

    /**
     * Returns the value of `this` optional, or throws the specified [exception] if a value is not present.
     *
     * Note that the [get] function *does* throw a [NoSuchElementException] if no value is present, so this should
     * generally only be used if a specialized exception is needed.
     */
    inline fun <X : Exception> getOrThrow(exception: () -> X): T = when (this) {
        is None -> throw exception()
        is Some -> item
    }

    /**
     * Executes the specified [action] if a value [is present][isPresent].
     */
    inline fun ifPresent(action: (T) -> Unit) {
        if (isPresent) action(value)
    }

    /**
     * Returns the result of invoking either [ifEmpty] or [ifPresent] depending on whether or not a value is present.
     *
     * @param [R] the value to return
     *
     * @param [ifEmpty] invoked if there is no value present
     * @param [ifPresent] invoked with the [value] if it is present
     */
    inline fun <R> fold(ifEmpty: () -> R, ifPresent: (T) -> R): R = when (this) {
        is None -> ifEmpty()
        is Some -> ifPresent(item)
    }

    /**
     * Returns the result of applying [value] to the [transformer] if present, otherwise returns [None].
     *
     * Note that if the result of applying `value` to the `transformer` results in `null` then [None] is returned.
     */
    inline fun <R> map(transformer: (T) -> R?): Optional<R> = when (this) {
        is None -> this
        is Some -> transformer(item).wrap()
    }

    /**
     * Returns the result of applying [value] to the [transformer] if present, otherwise returns [None].
     *
     * This function is similar to [map], but the `transformer` is one whose result is already an `Optional`,
     * and if invoked, `flatMap` does not wrap it with an additional `Optional`.
     */
    inline fun <R> flatMap(transformer: (T) -> Optional<R>): Optional<R> = when (this) {
        is None -> this
        is Some -> transformer(item)
    }

    /**
     * Returns [Some] if a value that matches the [predicate] is present, otherwise returns [None].
     */
    inline fun filter(predicate: (T) -> Boolean): Optional<T> =
        flatMap { if (predicate(it)) Some(it) else None }

    /**
     * Returns [Some] if a value that does *not* match the [predicate] is present, otherwise returns [None].
     */
    inline fun filterNot(predicate: (T) -> Boolean): Optional<T> =
        flatMap { if (!predicate(it)) Some(it) else None }

    /**
     * Returns `false` if no value is present, otherwise returns the result of calling [predicate] with the [value].
     */
    inline fun any(predicate: (T) -> Boolean): Boolean = when (this) {
        is None -> false
        is Some -> predicate(item)
    }

    /**
     * Returns `true` if no value is present, otherwise returns the result of calling [predicate] with the [value].
     */
    inline fun all(predicate: (T) -> Boolean): Boolean = when (this) {
        is None -> true
        is Some -> predicate(item)
    }

    /**
     * Returns `false` if there's a value present and the [predicate] is met by it, otherwise returns `true`.
     */
    inline fun none(predicate: (T) -> Boolean): Boolean = when (this) {
        is None -> true
        is Some -> !predicate(item)
    }

    /**
     * If no value is present returns [None], otherwise returns [other].
     */
    infix fun <U> and(other: Optional<U>): Optional<U> = if (isEmpty) None else other

    /**
     * Returns `this` if a value is present, otherwise returns [other].
     */
    infix fun or(other: Optional<@UnsafeVariance T>): Optional<T> = if (isPresent) this else other

    /**
     * If a value is present, returns whether or not [value][Optional.value] is equal to the specified [value],
     * otherwise returns `false`.
     */
    operator fun contains(value: @UnsafeVariance T): Boolean = when (this) {
        is None -> false
        is Some -> value == this.item
    }

    /**
     * Returns a [Iterable] that's based on the [iterator] of `this` optional.
     */
    fun asIterable(): Iterable<T> = Iterable { iterator }

    /**
     * Returns a [Sequence] that's based on the [iterator] of `this` optional.
     */
    fun asSequence(): Sequence<T> = Sequence { iterator }

    abstract operator fun component1(): T

    /**
     * Returns the hash-code of the [value] of `this` optional, or `0` if no value is present.
     */
    abstract override fun hashCode(): Int

    abstract override fun equals(other: Any?): Boolean

    abstract override fun toString(): String

    companion object {
        // kotlin only
        /**
         * Returns a [Some] containing the specified [value], or [None] if `value` is `null`.
         */
        // enables the syntax of 'Optional(67)'
        @JvmName("from")
        operator fun <T> invoke(value: T?): Optional<T> = ofNullable(value)

        /**
         * Returns a [Some] containing the result of invoking the specified [value], or [None] if `value` is `null`.
         */
        // enables the syntax of 'Optional { 67 }'
        @JvmName("calculate")
        inline operator fun <T : Any> invoke(value: () -> T?): Optional<T> = value().wrap()

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
            null -> None
            else -> Some(value)
        }

        /**
         * Invokes the specified [value] wrapped in a `try catch` block, returns [Some] if no errors are thrown,
         * otherwise returns [None].
         *
         * This function enables one to use the [Optional] class as a type of [Try]. Do note that unlike `Result`,
         * no information regarding the thrown exception is saved in the `Optional` form, as `None` does not
         * contain any data.
         */
        inline fun <T> tryCatch(value: Supplier<T>): Optional<T> = try {
            Some(value())
        } catch (e: Exception) {
            e.requireNonFatal()
            None
        }

        /**
         * Returns [None] cast to [T].
         */
        @JvmStatic
        fun <T> empty(): Optional<T> = None
    }
}

/**
 * Represents an empty [Optional] value.
 */
object None : Optional<Nothing>() {
    override val isPresent: Boolean = false

    override fun hashCode(): Int = 0

    override fun equals(other: Any?): Boolean = when (other) {
        this -> true
        else -> false
    }

    override operator fun component1(): Nothing = throw NoSuchElementException()

    override fun toString(): String = "None"
}

typealias Just<T> = Some<T>

/**
 * Represents a present [Optional] value.
 */
data class Some<out T>(val item: T) : Optional<T>() {
    override val isPresent: Boolean = true

    override fun hashCode(): Int = item.hashCode()

    override fun equals(other: Any?): Boolean = item?.equals(other) ?: false

    override fun toString(): String = "Some[$item]"
}

/**
 * Returns a [Some] containing `this`, or [None] if `this` is `null`.
 */
@JvmName("fromNullable")
fun <T> T?.wrap(): Optional<T> = Optional(this)

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
val <T> JOptional<T>.kotlin: Optional<T>
    @JvmName("fromJava") get() = when {
        this.isPresent -> Some(this.get())
        else -> None
    }

@JvmName("isSome")
inline fun <T> Optional<T>.isPresent(): Boolean {
    contract {
        returns(true) implies (this@isPresent is Some<T>)
        returns(false) implies (this@isPresent is None)
    }
    return this.isPresent
}

@JvmName("isNone")
inline fun <T> Optional<T>.isEmpty(): Boolean {
    contract {
        returns(false) implies (this@isEmpty is Some<T>)
        returns(true) implies (this@isEmpty is None)
    }
    return this.isEmpty
}

/**
 * If `this` boolean is `true`, returns the specified [item] wrapped in a [Some], otherwise returns [None].
 */
@JvmName("fromBoolean")
infix fun <T> Boolean.maybe(item: T): Optional<T> = if (this) Some(item) else None

@ScopedFunction
inline fun <T> maybe(scope: () -> Maybe<T> = { None }): Maybe<T> = scope()