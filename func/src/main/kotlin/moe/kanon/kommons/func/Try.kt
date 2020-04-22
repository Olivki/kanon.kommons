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

@file:JvmName("KTry")
@file:Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER", "NOTHING_TO_INLINE")

package moe.kanon.kommons.func

import moe.kanon.kommons.Identifiable
import moe.kanon.kommons.PortOf
import moe.kanon.kommons.requireNonFatal
import kotlin.contracts.contract

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
 *
 * ### Port-Of Links
 * 1. [scala.util.Try](https://github.com/scala/scala/blob/2.13.x/src/library/scala/util/Try.scala)
 */
@PortOf("scala.util.Try")
sealed class Try<out T> : Identifiable {
    companion object {
        /**
         * Returns a new [failure][Failure] carrying a [GenericTryException] with the given [message] and [cause].
         */
        @JvmOverloads
        @JvmStatic fun <T> failure(message: String, cause: Throwable? = null): Try<T> =
            Failure(GenericTryException(message, cause))

        /**
         * Returns a new [failure][Failure] wrapped around the given [exception].
         */
        @JvmStatic fun <T> failure(exception: Exception): Try<T> = Failure(exception)

        /**
         * Returns a new [success][Success] wrapped around the given [value].
         */
        @JvmStatic fun <T> success(value: T): Try<T> = Success(value)

        /**
         * Wraps [value] in a `try catch` block, and returns the result of invoking it; [Success] if no exceptions
         * were thrown, or [Failure] if any exceptions were thrown.
         *
         * Note that any fatal exceptions *(for now, this means any exceptions that inherit from [Error])* will not be
         * caught, and will simply be re-thrown.
         *
         * Note that any exceptions that are considered **fatal** are simply passed up the trace and are ***not***
         * caught by this function. A exception is generally considered to be fatal if it a child of [Error].
         */
        @JvmName("run")
        @JvmStatic inline operator fun <T> invoke(value: () -> T): Try<T> = try {
            Success(value())
        } catch (t: Throwable) {
            requireNonFatal(t)
            Failure(t)
        }
    }

    /**
     * Returns `true` if this is a [failure][Failure], otherwise `false`.
     */
    abstract val isFailure: Boolean

    /**
     * Returns `true` if this is a [success][Success], otherwise `false`.
     */
    abstract val isSuccess: Boolean

    /**
     * Executes the given [action] if `this` is a [success][Success].
     */
    inline fun ifSuccess(action: (T) -> Unit) {
        if (this is Success) action(item)
    }

    /**
     * Executes the given [action] if `this` is a [success][Success].
     */
    inline fun forEach(action: (T) -> Unit) {
        if (this is Success) action(item)
    }

    /**
     * Executes the given [action] if `this` is a [failure][Failure].
     */
    inline fun ifFailure(action: (Throwable) -> Unit) {
        if (this is Failure) action(underlyingCause)
    }

    /**
     * Executes the given [action] if `this` is a [failure][Failure].
     */
    inline fun forFailure(action: (Throwable) -> Unit) {
        if (this is Failure) action(underlyingCause)
    }

    /**
     * Returns [value][Success.item] if `this` is a [success][Success] or throws a [NoSuchElementException] if `this`
     * is a [failure][Failure].
     */
    val value: T
        @JvmName("get") get() = when (this) {
            is Failure -> throw NoSuchElementException("Failure contains no value")
            is Success -> item
        }

    /**
     * Returns [cause][Failure.underlyingCause] if `this` is a [failure][Failure] or throws a [NoSuchElementException]
     * if `this` is a [success][Success].
     */
    val cause: Throwable
        get() = when (this) {
            is Failure -> underlyingCause
            is Success -> throw NoSuchElementException("Success contains no cause")
        }

    /**
     * Returns [value][Success.item] if `this` is a [success][Success] or throws the [cause][Failure.cause] if `this`
     * is a [failure][Failure].
     */
    fun unwrap(): T = when (this) {
        is Failure -> throw underlyingCause
        is Success -> item
    }

    /**
     * Returns the result of invoking either [ifFailure] or [ifSuccess], depending on whether `this` is a
     * [success][Success] or a [failure][Failure].
     *
     * @param [ifFailure] invoked with the [cause][Failure.underlyingCause] of `this` [failure][Failure]
     * @param [ifSuccess] invoked with the [value][Success.item] of `this` [success][Success]
     */
    inline fun <R> fold(ifFailure: (Throwable) -> R, ifSuccess: (T) -> R): R = when (this) {
        is Failure -> ifFailure(underlyingCause)
        is Success -> ifSuccess(item)
    }

    /**
     * Returns the result of applying [value][Success.item] to the [transformer] if `this` is a [success][Success]
     * otherwise returns `this`.
     */
    inline fun <R> map(transformer: (T) -> R): Try<R> = when (this) {
        is Failure -> this
        is Success -> Try { transformer(item) }
    }

    /**
     * Returns the result of applying [value][Success.item] to the [transformer], otherwise returns `this`.
     *
     * This function is similar to [map], but the `transformer` is one whose result is already an `Result`,
     * and if invoked, `flatMap` does not wrap it with an additional `Result`.
     */
    inline fun <R> flatMap(transformer: (T) -> Try<R>): Try<R> = when (this) {
        is Failure -> this
        is Success -> transformer(item)
    }

    /**
     * Returns the result of applying [cause][Failure.underlyingCause] to the [transformer], otherwise returns `this`.
     *
     * As the parameter that [transformer] asks for is just a [Throwable], if you want to filter for a specific
     * `throwable`, you can do it the following way;
     *
     * ```kotlin
     *  val result: Try<...> = ...
     *  result.recover {
     *      when (it) {
     *          is DesiredException -> ...
     *          else -> ...
     *      }
     *  }
     * ```
     */
    inline fun recover(transformer: (Throwable) -> @UnsafeVariance T): Try<T> = when (this) {
        is Failure -> Try { transformer(underlyingCause) }
        is Success -> this
    }

    /**
     * Returns the result of applying [cause][Failure.underlyingCause] to the [transformer], otherwise returns `this`.
     *
     * This function is similar to [recover], but the `transformer` is one whose result is already an `Try`,
     * and if invoked, `recoverWith` does not wrap it with an additional `Try`.
     *
     * As the parameter that [transformer] asks for is just a [Throwable], if you want to filter for a specific
     * `throwable`, you can do it the following way;
     *
     * ```kotlin
     *  val result: Try<...> = ...
     *  result.recoverWith {
     *      when (it) {
     *          is DesiredException -> ...
     *          else -> ...
     *      }
     *  }
     * ```
     */
    inline fun recoverWith(transformer: (Throwable) -> Try<@UnsafeVariance T>): Try<T> = when (this) {
        is Failure -> transformer(underlyingCause)
        is Success -> this
    }

    /**
     * Returns [Success] if `this` has a [value][Success.item] that matches the [predicate], otherwise returns
     * [Failure].
     */
    inline fun filter(predicate: (T) -> Boolean): Try<T> =
        flatMap { if (predicate(it)) Success(it) else Failure(FailedPredicateException()) }

    /**
     * Returns [Success] if `this` has a [value][Success.item] that does *not* match the [predicate], otherwise
     * returns [Failure].
     */
    inline fun filterNot(predicate: (T) -> Boolean): Try<T> =
        flatMap { if (!predicate(it)) Success(it) else Failure(FailedPredicateException()) }

    /**
     * Returns `false` if `this` is a [failure][Failure], otherwise returns the result of calling [predicate] with
     * the [value][Success.item].
     */
    inline fun any(predicate: (T) -> Boolean): Boolean = when (this) {
        is Failure -> false
        is Success -> predicate(item)
    }

    /**
     * Returns `true` if `this` is a [failure][Failure], otherwise returns the result of calling [predicate] with
     * the [value][Success.item].
     */
    inline fun all(predicate: (T) -> Boolean): Boolean = when (this) {
        is Failure -> true
        is Success -> predicate(item)
    }

    /**
     * Inverts `this` result and returns it.
     *
     * @return a [Success] containing the [cause][Failure.underlyingCause] if `this` is a [failure][Failure], or a
     * [Failure] containing a [UnsupportedOperationException] if `this` is a [success][Success].
     */
    fun invert(): Try<Throwable> = when (this) {
        is Failure -> Success(underlyingCause)
        is Success -> Failure(UnsupportedOperationException("Success[failure]"))
    }

    /**
     * Inverts `this` and returns it.
     *
     * @return a [Success] wrapping the [cause][Failure.underlyingCause] if `this` is a [failure][Failure], or a [Failure]
     * containing a [UnsupportedOperationException] if `this` is a [success][Success].
     */
    @JvmSynthetic inline operator fun not(): Try<Throwable> = invert()

    /**
     * Returns a [Option] based on `this`, if it is a [failure][Failure] then [Option.None] is returned, otherwise
     * [Option.Some] is returned.
     *
     * Note that when converting a `result` to an `optional` if the `result` is a `failure` then the
     * [cause][Failure.underlyingCause] information will be erased as it will turned into a `None`, which stores no
     * data, if this is the behaviour that is wanted from the start, consider using `Optional.tryCatch { ... }`.
     */
    fun toOptional(): Option<T> = when (this) {
        is Failure -> Option.None
        is Success -> Option.Some(item)
    }

    /**
     * Returns a [Either] instance based on `this`.
     *
     * @return if `this` is a [failure][Failure] then a [right-side][Either.Right] containing the [cause] will be
     * returned, otherwise if `this` is a [success][Success] then a [left-side][Either.Left] containing the [value]
     * will be returned.
     */
    fun toEither(): Either<Throwable, T> = when (this) {
        is Failure -> Either.Left(underlyingCause)
        is Success -> Either.Right(item)
    }

    abstract operator fun component1(): T

    abstract operator fun component2(): Throwable
}

/**
 * Represents an unsuccessful [result][Try].
 *
 * @property [underlyingCause] The [Throwable] that's the cause of the operation failing.
 */
class Failure(val underlyingCause: Throwable) : Try<Nothing>() {
    override val isFailure: Boolean = true
    override val isSuccess: Boolean = false

    val message: String? = underlyingCause.message

    override fun component1(): Nothing = throw NoSuchElementException("Can't retrieve item from Failure")

    override fun component2(): Throwable = underlyingCause

    override fun equals(other: Any?): Boolean = underlyingCause == other

    override fun hashCode(): Int = underlyingCause.hashCode()

    override fun toString(): String = when (val name = underlyingCause::class.simpleName) {
        null -> "Failure[\"${underlyingCause.message}\"]"
        else -> "Failure { $name(\"${underlyingCause.message}\") }"
    }
}

/**
 * Represents a successful [result][Try].
 *
 * @property [item] The underlying value that `this` result is wrapped around.
 */
class Success<out T>(val item: T) : Try<T>() {
    override val isFailure: Boolean = false
    override val isSuccess: Boolean = true

    override fun component1(): T = item

    override fun component2(): Throwable = throw NoSuchElementException("Can't retrieve cause from Success")

    override fun equals(other: Any?): Boolean = item == other

    override fun hashCode(): Int = item.hashCode()

    override fun toString(): String = "Success[$item]"
}

// -- EXCEPTIONS -- \\
class FailedPredicateException @JvmOverloads constructor(
    message: String = "Predicate did not match",
    cause: Throwable? = null
) : RuntimeException(message, cause)

class GenericTryException internal constructor(message: String, cause: Throwable? = null) : Exception(message, cause)

// -- EXTENSIONS -- \\
/**
 * Returns `true` if this is a [success][Success], otherwise `false`.
 */
fun <T> Try<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is Success<T>)
        returns(false) implies (this@isSuccess is Failure)
    }
    return this.isSuccess
}

/**
 * Returns `true` if this is a [failure][Failure], otherwise `false`.
 */
fun <T> Try<T>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is Failure)
        returns(false) implies (this@isFailure is Success<T>)
    }
    return this.isFailure
}

/**
 * Returns a new [failure][Failure] wrapped around `this` throwable.
 */
fun <T> Throwable.toFailure(): Try<T> = Failure(this)

/**
 * Returns a new [success][Success] wrapped around `this` value.
 */
fun <T> T.toSuccess(): Try<T> = Success(this)

// -- FAKE FACTORY FUNCTIONS -- \\
fun Byte.Companion.tryParse(input: String): Try<Byte> = Try { input.toByte() }

fun Byte.Companion.tryParse(input: String, radix: Int): Try<Byte> = Try { input.toByte(radix) }

fun Short.Companion.tryParse(input: String): Try<Short> = Try { input.toShort() }

fun Short.Companion.tryParse(input: String, radix: Int): Try<Short> = Try { input.toShort(radix) }

fun Int.Companion.tryParse(input: String): Try<Int> = Try { input.toInt() }

fun Int.Companion.tryParse(input: String, radix: Int): Try<Int> = Try { input.toInt(radix) }

fun Long.Companion.tryParse(input: String): Try<Long> = Try { input.toLong() }

fun Long.Companion.tryParse(input: String, radix: Int): Try<Long> = Try { input.toLong(radix) }

fun Float.Companion.tryParse(input: String): Try<Float> = Try { input.toFloat() }

fun Double.Companion.tryParse(input: String): Try<Double> = Try { input.toDouble() }