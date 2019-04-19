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
 *
 * -------------------------------------------------------------------------
 *
 * Scala
 * Copyright (c) 2002-2019 EPFL
 * Copyright (c) 2011-2019 Lightbend, Inc.
 *
 * Scala includes software developed at
 * LAMP/EPFL (https://lamp.epfl.ch/) and
 * Lightbend, Inc. (https://www.lightbend.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmName("KTry")
@file:Suppress("UNCHECKED_CAST")

package moe.kanon.kommons.func.result

import moe.kanon.kommons.collections.iterators.EmptyIterator
import moe.kanon.kommons.collections.iterators.SingletonIterator
import moe.kanon.kommons.func.Supplier
import moe.kanon.kommons.func.optional.None
import moe.kanon.kommons.func.optional.Optional
import moe.kanon.kommons.func.optional.Some
import moe.kanon.kommons.precond.requireNonFatal

typealias Result<T> = Try<T>

/*
 * This is based on the Try[1] class from the Scala standard library.
 *
 * [1]: https://github.com/scala/scala/blob/2.13.x/src/library/scala/util/Try.scala
 */

/**
 * Represents a result that a operation can have.
 *
 * A result can either be a [success][Success] or a [failure][Failure]. A `success` always carries some value with it,
 * while a `failure` only carries a exception with it.
 */
sealed class Try<out T> : Iterable<T> {
    abstract fun isFailure(): Boolean
    abstract fun isSuccess(): Boolean

    /**
     * Returns [value][Success.value] if `this` is a [success][Success] or throws a [NoSuchElementException] if `this`
     * is a [failure][Failure].
     */
    fun get(): T = when (this) {
        is Failure -> throw NoSuchElementException("Failure contains no value")
        is Success -> value
    }

    /**
     * Returns [cause][Failure.cause] if `this` is a [failure][Failure] or throws a [NoSuchElementException] if `this`
     * is a [success][Success].
     */
    fun getCause(): Throwable = when (this) {
        is Failure -> cause
        is Success -> throw NoSuchElementException("Success contains no cause")
    }

    /**
     * Returns the result of invoking either [ifFailure] or [ifSuccess], depending on whether `this` is a
     * [success][Success] or a [failure][Failure].
     *
     * @param [ifFailure] invoked with the [cause][Failure.cause] of `this` [failure][Failure]
     * @param [ifSuccess] invoked with the [value][Success.value] of `this` [success][Success]
     */
    inline fun <R> fold(ifFailure: (Throwable) -> R, ifSuccess: (T) -> R): R = when (this) {
        is Failure -> ifFailure(cause)
        is Success -> ifSuccess(value)
    }

    /**
     * Returns the result of applying [value][Success.value] to the [transformer] if `this` is a [success][Success]
     * otherwise returns `this`.
     */
    inline fun <R> map(transformer: (T) -> R): Try<R> = when (this) {
        is Failure -> this
        is Success -> Success(transformer(value))
    }

    /**
     * Returns the result of applying [value][Success.value] to the [transformer], otherwise returns `this`.
     *
     * This function is similar to [map], but the `transformer` is one whose result is already an `Result`,
     * and if invoked, `flatMap` does not wrap it with an additional `Result`.
     */
    inline fun <R> flatMap(transformer: (T) -> Try<R>): Try<R> = when (this) {
        is Failure -> this
        is Success -> transformer(value)
    }

    /**
     * Returns the result of applying [cause][Failure.cause] to the [transformer], otherwise returns `this`.
     *
     * As the parameter that [transformer] asks for is just a [Throwable], if you want to filter for a specific
     * `throwable`, you can do it the following way;
     *
     * ```kotlin
     *  val result: Result<...> = ...
     *  result.mapFailure {
     *      when (it) {
     *          is DesiredException -> ...
     *          else -> ...
     *      }
     *  }
     * ```
     */
    inline fun mapFailure(transformer: (Throwable) -> @UnsafeVariance T): Try<T> = when (this) {
        is Failure -> Success(transformer(cause))
        is Success -> this
    }

    /**
     * Returns the result of applying [cause][Failure.cause] to the [transformer], otherwise returns `this`.
     *
     * This function is similar to [mapFailure], but the `transformer` is one whose result is already an `Result`,
     * and if invoked, `flatMapFailure` does not wrap it with an additional `Result`.
     *
     * As the parameter that [transformer] asks for is just a [Throwable], if you want to filter for a specific
     * `throwable`, you can do it the following way;
     *
     * ```kotlin
     *  val result: Result<...> = ...
     *  result.flatMapFailure {
     *      when (it) {
     *          is DesiredException -> ...
     *          else -> ...
     *      }
     *  }
     * ```
     */
    inline fun flatMapFailure(transformer: (Throwable) -> Try<@UnsafeVariance T>): Try<T> = when (this) {
        is Failure -> transformer(cause)
        is Success -> this
    }

    /**
     * Returns [Success] if `this` has a [value][Success.value] that matches the [predicate], otherwise returns
     * [Failure].
     */
    inline fun filter(predicate: (T) -> Boolean): Try<T> =
        flatMap { if (predicate(it)) Success(it) else Failure(PredicateException("Predicate did not match <$it>")) }

    /**
     * Returns [Success] if `this` has a [value][Success.value] that does *not* match the [predicate], otherwise
     * returns [Failure].
     */
    inline fun filterNot(predicate: (T) -> Boolean): Try<T> =
        flatMap { if (!predicate(it)) Success(it) else Failure(PredicateException("Predicate did not match <$it>")) }

    /**
     * Returns `false` if `this` is a [failure][Failure], otherwise returns the result of calling [predicate] with
     * the [value][Success.value].
     */
    inline fun any(predicate: (T) -> Boolean): Boolean = when (this) {
        is Failure -> false
        is Success -> predicate(value)
    }

    /**
     * Returns `true` if `this` is a [failure][Failure], otherwise returns the result of calling [predicate] with
     * the [value][Success.value].
     */
    inline fun all(predicate: (T) -> Boolean): Boolean = when (this) {
        is Failure -> true
        is Success -> predicate(value)
    }

    /**
     * Inverts `this` result and returns it.
     *
     * @return a [Success] containing the [cause][Failure.cause] if `this` is a [failure][Failure], or a [Failure]
     * containing a [UnsupportedOperationException] if `this` is a [success][Success].
     */
    fun invert(): Try<Throwable> = when (this) {
        is Failure -> Success(cause)
        is Success -> Failure(UnsupportedOperationException("Success[failure]"))
    }

    /**
     * Inverts `this` result and returns it.
     *
     * @return a [Success] wrapping the [cause][Failure.cause] if `this` is a [failure][Failure], or a [Failure]
     * containing a [UnsupportedOperationException] if `this` is a [success][Success].
     */
    @JvmName("negate")
    operator fun not(): Try<Throwable> = invert()

    // conversions
    /**
     * Returns a [Optional] based on `this` result, if it is a [failure][Failure] then [None] is returned, otherwise
     * [Some] is returned.
     *
     * Note that when converting a `result` to an `optional` if the `result` is a `failure` then the
     * [cause][Failure.cause] information will be erased as it will turned into a `None`, which stores no data.
     */
    fun toOptional(): Optional<T> = when (this) {
        is Failure -> None
        is Success -> Some(value)
    }

    // to enforce the contract
    abstract override fun toString(): String

    abstract override fun hashCode(): Int

    abstract override fun equals(other: Any?): Boolean

    companion object {
        @JvmStatic
        fun <T> failure(exception: Exception): Try<T> = Failure(exception)

        @JvmStatic
        fun <T> success(value: T): Try<T> = Success(value)

        @JvmStatic
        inline fun <T> tryInvoke(value: Supplier<T>): Try<T> = try {
            Success(value())
        } catch (e: Exception) {
            e.requireNonFatal()
            Failure(e)
        }

        @JvmSynthetic
        inline operator fun <T> invoke(value: Supplier<T>): Try<T> = try {
            Success(value())
        } catch (e: Exception) {
            e.requireNonFatal()
            Failure(e)
        }
    }
}

/**
 * Represents an unsuccessful [result][Try].
 *
 * @property [cause] The [Throwable] that's the cause of the operation failing.
 */
@Suppress("DataClassPrivateConstructor")
data class Failure(@get:JvmName("getUnderlyingCause") val cause: Throwable) : Try<Nothing>() {
    override fun isFailure(): Boolean = true
    override fun isSuccess(): Boolean = false
    override fun iterator(): Iterator<Nothing> = EmptyIterator
    override fun toString(): String = "Failure[${cause.message}]"
}

/**
 * Represents a successful [result][Try].
 *
 * @property [value] The underlying value that `this` result is wrapped around.
 */
data class Success<out T>(val value: T) : Try<T>() {
    override fun isFailure(): Boolean = false
    override fun isSuccess(): Boolean = true
    override fun iterator(): Iterator<T> = SingletonIterator(value)
    override fun toString(): String = "Success[$value]"
}

// exceptions
class PredicateException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)