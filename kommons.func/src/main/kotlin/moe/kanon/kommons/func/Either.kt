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

@file:Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER", "NOTHING_TO_INLINE")
@file:JvmName("Eithers")

package moe.kanon.kommons.func

import moe.kanon.kommons.Identifiable
import moe.kanon.kommons.func.internal.EmptyIterator
import moe.kanon.kommons.func.internal.SingletonIterator

sealed class Either<out L, out R> : Identifiable {
    /*
     * I've opted to *not* include properties for "isLeft" and "isRight", as it's better practice to just do
     * ```kotlin
     *  val joint: Either<String, Int> = ...
     *  if (joint is Left) ... else ...
     * ```
     * as the Kotlin compiler will then also smart cast the `joint` value.
     *
     * This is also because from the data I've seen, a 'is' (instanceof on the java side) check is no slower than a
     * boolean check, so there is no real performance to gain, if there is any it's beyond minimal.
     */

    /**
     * Returns a [left-side-projection][LeftProjection] of `this` either instance.
     */
    @get:JvmName("left") val left: LeftProjection<L, R> by lazy { LeftProjection(this) }

    /**
     * Returns a [right-side-projection][RightProjection] of `this` either instance.
     */
    @get:JvmName("right") val right: RightProjection<L, R> by lazy { RightProjection(this) }

    /**
     * Returns the [value][Left.value] of `this` if it is [left][Left], or throws a [NoSuchElementException] if `this`
     * is [right][Right].
     */
    val leftValue: L
        get() = when (this) {
            is Left -> value
            is Right -> throw NoSuchElementException("Right side does not contain value of Left side")
        }

    /**
     * Returns the [value][Right.value] of `this` if it is [right][Right], or throws a [NoSuchElementException] if
     * `this` is [left][Left].
     */
    val rightValue: R
        get() = when (this) {
            is Left -> throw NoSuchElementException("Left side does not contain value of Right side")
            is Right -> value
        }

    /**
     * Returns the result of executing [ifLeft] if `this` is a [left][Left] or [ifRight] if `this` is a [right][Right].
     */
    inline fun <U> fold(ifLeft: (L) -> U, ifRight: (R) -> U): U = when (this) {
        is Left -> ifLeft(value)
        is Right -> ifRight(value)
    }

    /**
     * Executes the given [action] if, and *only* if, `this` is [left][Left].
     */
    inline infix fun ifLeft(action: (L) -> Unit) {
        if (this is Left) action(value)
    }

    /**
     * Executes the given [action] if, and *only* if, `this` is [right][Right].
     */
    inline infix fun ifRight(action: (R) -> Unit) {
        if (this is Right) action(value)
    }

    /**
     * Returns `this` either with the values swapped around.
     *
     * @return if `this` is [left][Left] then it will return [right][Right] or if `this` is `right` then it will return
     * `left`.
     */
    fun swap(): Either<R, L> = when (this) {
        is Left -> Right(value)
        is Right -> Left(value)
    }

    /**
     * Returns `this` either with the values swapped around.
     *
     * @return if `this` is [left][Left] then it will return [right][Right] or if `this` is `right` then it will return
     * `left`.
     */
    @JvmSynthetic
    inline operator fun not(): Either<R, L> = swap()

    companion object {
        @JvmStatic infix fun <L> left(value: L): Either<L, Nothing> = Left(value)

        @JvmStatic infix fun <R> right(value: R): Either<Nothing, R> = Right(value)

        /**
         * Returns a [left-side][Left] containing the given [value] if it is *not* `null`, or a [right-side][Right]
         * containing nothing if it *is* `null`.
         */
        @JvmStatic fun <T> fromNullable(value: T?): Either<T, Nothing?> =
            if (value != null) Left(value) else Right(value)
    }
}

/**
 * Up-casts `this` `Either<T, Nothing>` to `Either<T, R>`.
 */
fun <L, R> Either<L, Nothing>.withRight(): Either<L, R> = this

/**
 * Up-casts `this` `Either<Nothing, T>` to `Either<L, T>`.
 */
fun <L, R> Either<Nothing, R>.withLeft(): Either<L, R> = this

/**
 * Represents the left-side of a [disjoint union][Either].
 */
data class Left<out T>(val value: T) : Either<T, Nothing>() {
    /**
     * Up-casts `this` `Left<T>` *`(Either<T, Nothing>)`* to `Either<T, R>`.
     *
     * @param [clz] used to determine the type of [R]
     */
    @Suppress("UNUSED_PARAMETER")
    infix fun <R : Any> withRight(clz: Class<R>): Either<T, R> = this

    /**
     * Up-casts `this` `Left<T>` *`(Either<T, Nothing>)`* to `Either<T, R>`.
     */
    fun <R> withRight(): Either<T, R> = this

    override fun toString(): String = "Left[$value]"
}

/**
 * Represents the right-side of a [disjoint union][Either].
 */
data class Right<out T>(val value: T) : Either<Nothing, T>() {
    /**
     * Up-casts `this` `Right<T>` *`(Either<Nothing, T>)`* to `Either<L, T>`.
     *
     * @param [clz] used to determine the type of [L]
     */
    @Suppress("UNUSED_PARAMETER")
    infix fun <L : Any> withLeft(clz: Class<L>): Either<L, T> = this

    /**
     * Up-casts `this` `Right<T>` *`(Either<Nothing, T>)`* to `Either<L, T>`.
     */
    fun <L> withLeft(): Either<L, T> = this

    override fun toString(): String = "Right[$value]"
}

sealed class EitherProjection<out L, out R> : Identifiable {
    /**
     * Returns `this` either with the values swapped around.
     *
     * @return if `this` is [left][Left] then it will return [right][Right] or if `this` is `right` then it will return
     * `left`.
     */
    fun swap(): Either<R, L> = when (this) {
        is LeftProjection -> Right(value)
        is RightProjection -> Left(value)
    }

}

class LeftProjection<out L, out R> internal constructor(
    @PublishedApi
    @get:JvmSynthetic
    internal val either: Either<L, R>
) : EitherProjection<L, R>(), Identifiable by either {
    val iterator: Iterator<L>
        get() = when (either) {
            is Left -> SingletonIterator(either.value)
            is Right -> EmptyIterator
        }

    val value: L
        @JvmName("get") get() = when (either) {
            is Left -> either.value
            is Right -> throw NoSuchElementException("Retrieving left-side value from right-side")
        }

    inline fun <U> map(transformer: (L) -> U): Either<U, R> = when (either) {
        is Left -> Left(transformer(either.value))
        is Right -> either
    }

    inline fun <U> flatMap(transformer: (L) -> Either<U, @UnsafeVariance R>): Either<U, R> = when (either) {
        is Left -> transformer(either.value)
        is Right -> either
    }

    inline fun forEach(action: (L) -> Unit) = when (either) {
        is Left -> action(either.value)
        is Right -> Unit
    }

    inline fun any(predicate: (L) -> Boolean): Boolean = when (either) {
        is Left -> predicate(either.value)
        is Right -> false
    }

    inline fun all(predicate: (L) -> Boolean): Boolean = when (either) {
        is Left -> predicate(either.value)
        is Right -> true
    }

    inline fun none(predicate: (L) -> Boolean): Boolean = when (either) {
        is Left -> !predicate(either.value)
        is Right -> true
    }

    /**
     * Returns whether or not the specified [item] is equal to the [value][Left.value] if `this` is [left][Left], or
     * returns `false` if `this` is [right][Right].
     */
    operator fun contains(item: @UnsafeVariance L): Boolean = when (either) {
        is Left -> item == either.value
        is Right -> false
    }

    /**
     * Returns a [Iterable] that's based on the [iterator] of `this`.
     */
    fun asIterable(): Iterable<L> = Iterable { iterator }

    /**
     * Returns a [Sequence] that's based on the [iterator] of `this`.
     */
    fun asSequence(): Sequence<L> = Sequence { iterator }

    override fun toString(): String = "LeftProjection[$either]"
}

class RightProjection<out L, out R> internal constructor(
    @PublishedApi
    @get:JvmSynthetic
    internal val either: Either<L, R>
) : EitherProjection<L, R>(), Identifiable by either {
    /**
     * Returns a [SingletonIterator] if `this` is [right][Right], or a [EmptyIterator] if `this` is [left][Left].
     */
    val iterator: Iterator<R>
        get() = when (either) {
            is Left -> EmptyIterator
            is Right -> SingletonIterator(either.value)
        }

    val value: R
        @JvmName("get") get() = when (either) {
            is Left -> throw NoSuchElementException("Retrieving right-side value from left-side")
            is Right -> either.value
        }

    inline fun <U> map(transformer: (R) -> U): Either<L, U> = when (either) {
        is Left -> either
        is Right -> Right(transformer(either.value))
    }

    inline fun <U> flatMap(transformer: (R) -> Either<@UnsafeVariance L, U>): Either<L, U> = when (either) {
        is Left -> either
        is Right -> transformer(either.value)
    }

    inline fun any(predicate: (R) -> Boolean): Boolean = when (either) {
        is Left -> false
        is Right -> predicate(either.value)
    }

    inline fun all(predicate: (R) -> Boolean): Boolean = when (either) {
        is Left -> true
        is Right -> predicate(either.value)
    }

    inline fun none(predicate: (R) -> Boolean): Boolean = when (either) {
        is Left -> true
        is Right -> predicate(either.value)
    }

    /**
     * Executes the given [action] if `this` is [right][Right].
     */
    inline fun forEach(action: (R) -> Unit) {
        if (either is Right) action(either.value)
    }

    /**
     * Returns whether or not the specified [item] is equal to the [value][Right.value] if `this` is [right][Right], or
     * returns `false` if `this` is [left][Left].
     */
    operator fun contains(item: @UnsafeVariance R): Boolean = when (either) {
        is Left -> false
        is Right -> item == either.value
    }

    /**
     * Returns a [Iterable] that's based on the [iterator] of `this`.
     */
    fun asIterable(): Iterable<R> = Iterable { iterator }

    /**
     * Returns a [Sequence] that's based on the [iterator] of `this`.
     */
    fun asSequence(): Sequence<R> = Sequence { iterator }

    override fun toString(): String = "RightProjection[$either]"
}

private class WrongJunctionException(message: String) : RuntimeException(message)