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

@file:JvmName("KResult")
@file:Suppress("UNCHECKED_CAST")

package moe.kanon.kommons.func.result

import moe.kanon.kommons.func.Func
import moe.kanon.kommons.func.Predicate
import moe.kanon.kommons.func.Supplier
import moe.kanon.kommons.func.optional.None
import moe.kanon.kommons.func.optional.Optional
import moe.kanon.kommons.func.optional.Some
import java.lang.RuntimeException

typealias Try<T> = Result<T>

/**
 * Represents a result that a operation can have.
 *
 * A result can either be a [success][Success] or a [failure][Failure]. A `success` always carries some value with it,
 * while a `failure` only carries a exception with it.
 */
// unlike the 'Result' provided by the std-lib, this is *not* inline, which means that there will be overhead for
// from 'Success' because it wraps the value.
sealed class Result<T> {
    /**
     * Returns the result of invoking either [ifFailure] or [ifSuccess], depending on whether `this` is a
     * [success][Success] or a [failure][Failure].
     *
     * @param [ifFailure] invoked with the [cause][Failure.cause] of `this` [failure][Failure]
     * @param [ifSuccess] invoked with the [value][Success.value] of `this` [success][Success]
     */
    inline fun <R> fold(ifFailure: Func<Throwable, R>, ifSuccess: Func<T, R>): R = when (this) {
        is Failure -> ifFailure(cause)
        is Success -> ifSuccess(value)
    }

    /**
     * Returns the result of applying [value][Success.value] to the [transformer] if `this` is a [success][Success]
     * otherwise returns `this`.
     */
    inline fun <U> map(transformer: Func<in T, out U>): Result<U> = when (this) {
        is Failure -> this as Result<U>
        is Success -> Success(transformer(value))
    }

    /**
     * Returns the result of applying [value][Success.value] to the [transformer], otherwise returns `this`.
     *
     * This function is similar to [map], but the `transformer` is one whose result is already an `Result`,
     * and if invoked, `flatMap` does not wrap it with an additional `Result`.
     */
    inline fun <U> flatMap(transformer: Func<T, Result<U>>): Result<U> = when (this) {
        is Failure -> this as Result<U>
        is Success -> transformer(value)
    }

    /**
     * Returns the result of applying [cause][Failure.cause] to the [transformer], otherwise returns `this`.
     */
    inline fun mapFailure(transformer: Func<Throwable, T>): Result<T> = when (this) {
        is Failure -> Success(transformer(cause))
        is Success -> this
    }

    /**
     * Returns the result of applying [cause][Failure.cause] to the [transformer], otherwise returns `this`.
     *
     * This function is similar to [mapFailure], but the `transformer` is one whose result is already an `Result`,
     * and if invoked, `flatMapFailure` does not wrap it with an additional `Result`.
     */
    inline fun flatMapFailure(transformer: Func<Throwable, Result<T>>): Result<T> = when (this) {
        is Failure -> transformer(cause)
        is Success -> this
    }

    /**
     * Returns [Success] if `this` has a [value][Success.value] that matches the [predicate], otherwise returns
     * [Failure].
     */
    inline fun filter(predicate: Predicate<in T>): Result<T> =
        flatMap { if (predicate(it)) Success(it) else Failure<T>(PredicateException("Predicate did not match <$it>")) }

    /**
     * Returns [Success] if `this` has a [value][Success.value] that does *not* match the [predicate], otherwise
     * returns [Failure].
     */
    inline fun filterNot(predicate: Predicate<in T>): Result<T> =
        flatMap { if (!predicate(it)) Success(it) else Failure<T>(PredicateException("Predicate did not match <$it>")) }

    /**
     * Returns `false` if `this` is a [failure][Failure], otherwise returns the result of calling [predicate] with
     * the [value][Success.value].
     */
    inline fun any(predicate: Predicate<in T>): Boolean = fold({ false }, { predicate(it) })

    /**
     * Returns `true` if `this` is a [failure][Failure], otherwise returns the result of calling [predicate] with
     * the [value][Success.value].
     */
    inline fun all(predicate: Predicate<in T>): Boolean = fold({ true }, predicate)

    abstract override fun toString(): String

    abstract override fun hashCode(): Int

    abstract override fun equals(other: Any?): Boolean

    companion object {
        @JvmStatic
        fun <T> failure(exception: Exception): Result<T> = Failure(exception)

        @JvmStatic
        fun <T> success(value: T): Result<T> = Success(value)

        @JvmStatic
        inline fun <T> tryInvoke(value: Supplier<T>): Result<T> = try {
            Success(value())
        } catch (e: Exception) {
            Failure(e)
        }

        @JvmSynthetic
        inline operator fun <T> invoke(value: Supplier<T>): Result<T> = try {
            Success(value())
        } catch (e: Exception) {
            Failure(e)
        }
    }
}

/**
 * Represents an unsuccessful [result][Result].
 *
 * @property [cause] The [Throwable] that's the cause of the operation failing.
 */
@Suppress("DataClassPrivateConstructor")
data class Failure private constructor(val cause: Throwable) : Result<Nothing>() {
    override fun toString(): String = "Failure[${cause.message}]"

    companion object {
        // dirty hack
        @JvmStatic
        @JvmName("of")
        operator fun <T> invoke(cause: Throwable): Result<T> = Failure(cause) as Result<T>
    }
}

/**
 * Represents a successful [result][Result].
 *
 * @property [value] The underlying value that `this` result is wrapped around.
 */
data class Success<T>(val value: T) : Result<T>() {
    override fun toString(): String = "Success[$value]"
}

// exceptions
@PublishedApi
internal class PredicateException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)