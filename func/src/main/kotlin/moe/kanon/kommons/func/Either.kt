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
 * ========================= SCALA LICENSE =========================
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
 */

@file:Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER", "NOTHING_TO_INLINE")
@file:JvmName("KEither")

package moe.kanon.kommons.func

import moe.kanon.kommons.Identifiable
import moe.kanon.kommons.PortOf
import moe.kanon.kommons.func.internal.EmptyIterator
import moe.kanon.kommons.func.internal.SingletonIterator
import moe.kanon.kommons.requireNonFatal
import kotlin.contracts.contract

typealias Disjoint<L, R> = Either<L, R>
typealias DisjointUnion<L, R> = Either<L, R>

/**
 * Represents a disjoint union where a value can either be of type [L] or type [R].
 *
 * ### Port-Of Links
 * 1. [scala.util.Either](https://github.com/scala/scala/blob/2.13.x/src/library/scala/util/Either.scala)
 *
 * ### Port Details
 * To more closely adhere to the Kotlin design of avoiding implicit behaviour when possible the right-bias found in the
 * original Scala implementation has been removed. That means that this class on its own does *not* provide any real
 * utility functions *(`map`, `flatMap`, `any`, etc...)*, instead to access those functions one needs to explicitly
 * define the [projection][EitherProjection] that they want to use. *`(Either.left or Either.right)`*
 *
 * Some of the functions have also been renamed to follow the general Kotlin naming style more closely, i.e;
 * `exists(predicate)` -> `any(predicate)`, `forall(predicate)` -> `all(predicate)`, etc..
 */
@PortOf("scala.util.Either")
sealed class Either<out L, out R> : Identifiable {
    companion object {
        /**
         * Returns a [left-side][Left] of a [disjoint union][Either], containing the specified [value].
         */
        @JvmStatic
        fun <L> left(value: L): Either<L, Nothing> = Left(value)

        /**
         * Returns a [right-side][Right] of a [disjoint union][Either], containing the specified [value].
         */
        @JvmStatic
        fun <R> right(value: R): Either<Nothing, R> = Right(value)

        /**
         * Returns a [right-side][Right] containing the given [value] if it is *not* `null`, or a [left-side][Left]
         * containing nothing if it *is* `null`.
         */
        @JvmStatic
        fun <T> fromNullable(value: T?): Either<Nothing?, T> = if (value == null) Left(value) else Right(value)

        /**
         * Wraps the invocation of the specified [closure] in a `try-catch` block and returns a [right-side][Right]
         * containing the result if it succeeded, otherwise returns a [left-side][Left] containing the
         * [cause][Throwable.cause] of the failure.
         *
         * Note that any fatal exceptions *(for now, this means any exceptions that inherit from [Error])* will not be
         * caught, and will simply be re-thrown.
         */
        @JvmStatic
        inline fun <T> tryCatch(closure: () -> T): Either<Throwable, T> = try {
            Right(closure())
        } catch (t: Throwable) {
            requireNonFatal(t)
            Left(t)
        }
    }

    /**
     * Returns `true` if this is the [left-side][Left] of the union, otherwise `false`.
     */
    abstract val isLeft: Boolean

    /**
     * Returns `true` if this is the [right-side][Right] of the union, otherwise `false`.
     */
    abstract val isRight: Boolean

    /**
     * Returns a [left-side-projection][LeftProjection] of `this` either instance.
     */
    @get:JvmName("left")
    val left: LeftProjection<L, R> by lazy { LeftProjection(this) }

    /**
     * Returns a [right-side-projection][RightProjection] of `this` either instance.
     */
    @get:JvmName("right")
    val right: RightProjection<L, R> by lazy { RightProjection(this) }

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

    /**
     * Represents the left-side of a [disjoint union][Either].
     */
    data class Left<out T>(val value: T) : Either<T, Nothing>() {
        override val isLeft: Boolean = true
        override val isRight: Boolean = false

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
        override val isLeft: Boolean = false
        override val isRight: Boolean = true

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
 * Returns a new [disjoint union][Either] based on the value of `this` boolean.
 *
 * The [left side][Either.Left] of the union gets mapped the given [ifTrue] value, and the [right side][Either.Right]
 * gets mapped to the given [ifFalse] value.
 */
inline fun <L, R> Boolean.asEither(ifTrue: () -> L, ifFalse: () -> R): Either<L, R> =
    if (this) Either.Left(ifTrue()) else Either.Right(ifFalse())


/**
 * Returns `true` if this is the [left-side][Either.Left] of the union, otherwise `false`.
 */
fun <L, R> Either<L, R>.isLeft(): Boolean {
    contract {
        returns(true) implies (this@isLeft is Either.Left<L>)
        returns(false) implies (this@isLeft is Either.Right<R>)
    }
    return this.isLeft
}

/**
 * Returns `true` if this is the [right-side][Either.Right] of the union, otherwise `false`.
 */
fun <L, R> Either<L, R>.isRight(): Boolean {
    contract {
        returns(true) implies (this@isRight is Either.Right<R>)
        returns(false) implies (this@isRight is Either.Left<L>)
    }
    return this.isRight
}

/**
 * Returns the [value][Either.Left.value] of `this` if it is [left][Either.Left], or throws a [NoSuchElementException]
 * if `this` is [right][Either.Right].
 */
operator fun <L, R> Either<L, R>.component1(): L = leftValue

/**
 * Returns the [value][Either.Right.value] of `this` if it is [right][Either.Right], or throws a
 * [NoSuchElementException] if `this` is [left][Either.Left].
 */
operator fun <L, R> Either<L, R>.component2(): R = rightValue

/**
 * Represents a projection of a side in a [disjoint-union][Either].
 */
sealed class EitherProjection<out L, out R> : Identifiable {
    /**
     * Returns `true` if this is the [left-side][Either.Left] of the union, otherwise `false`.
     */
    abstract val isLeft: Boolean

    /**
     * Returns `true` if this is the [right-side][Either.Right] of the union, otherwise `false`.
     */
    abstract val isRight: Boolean

    /**
     * Returns `this` either with the values swapped around.
     *
     * @return if `this` is [left][Either.Left] then it will return [right][Either.Right] or if `this` is `right` then
     * it will return `left`.
     */
    fun swap(): Either<R, L> = when (this) {
        is LeftProjection -> Either.Right(value)
        is RightProjection -> Either.Left(value)
    }
}

/**
 * Represents a projection of the [left-side][Either.Left] in a [disjoint-union][Either].
 */
class LeftProjection<out L, out R> internal constructor(val either: Either<L, R>) : EitherProjection<L, R>(),
    Identifiable by either {
    override val isLeft: Boolean = true
    override val isRight: Boolean = false

    val iterator: Iterator<L>
        get() = when (either) {
            is Either.Left -> SingletonIterator(either.value)
            is Either.Right -> EmptyIterator
        }

    val value: L
        @JvmName("get")
        get() = when (either) {
            is Either.Left -> either.value
            is Either.Right -> throw NoSuchElementException("Retrieving left-side value from right-side")
        }

    inline fun <U> map(transformer: (L) -> U): Either<U, R> = when (either) {
        is Either.Left -> Either.Left(transformer(either.value))
        is Either.Right -> either
    }

    inline fun <U> flatMap(transformer: (L) -> Either<U, @UnsafeVariance R>): Either<U, R> = when (either) {
        is Either.Left -> transformer(either.value)
        is Either.Right -> either
    }

    inline fun forEach(action: (L) -> Unit) = when (either) {
        is Either.Left -> action(either.value)
        is Either.Right -> Unit
    }

    inline fun any(predicate: (L) -> Boolean): Boolean = when (either) {
        is Either.Left -> predicate(either.value)
        is Either.Right -> false
    }

    inline fun all(predicate: (L) -> Boolean): Boolean = when (either) {
        is Either.Left -> predicate(either.value)
        is Either.Right -> true
    }

    inline fun none(predicate: (L) -> Boolean): Boolean = when (either) {
        is Either.Left -> !predicate(either.value)
        is Either.Right -> true
    }

    /**
     * Returns whether or not the specified [item] is equal to the [value][Either.Left.value] if `this` is
     * [left][Either.Left], or  returns `false` if `this` is [right][Either.Right].
     */
    operator fun contains(item: @UnsafeVariance L): Boolean = when (either) {
        is Either.Left -> item == either.value
        is Either.Right -> false
    }

    /**
     * Returns [Some] if `this` is `left`, or [None] if `this` is `right`.
     */
    fun toOptional(): Option<L> = when (either) {
        is Either.Left -> Some(either.value)
        is Either.Right -> None
    }

    /**
     * Returns [Success] if `this` is `left`, or [Failure] if `this` is `right`.
     */
    fun toTry(): Try<L> = when (either) {
        is Either.Left -> Success(either.value)
        is Either.Right -> Failure(WrongJunctionException("Expected left-side, got right-side"))
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

/**
 * Represents a projection of the [right-side][Either.Right] in a [disjoint-union][Either].
 */
class RightProjection<out L, out R> internal constructor(val either: Either<L, R>) : EitherProjection<L, R>(),
    Identifiable by either {
    override val isLeft: Boolean = false
    override val isRight: Boolean = true

    /**
     * Returns a [SingletonIterator] if `this` is [right][Either.Right], or a [EmptyIterator] if `this` is
     * [left][Either.Left].
     */
    val iterator: Iterator<R>
        get() = when (either) {
            is Either.Left -> EmptyIterator
            is Either.Right -> SingletonIterator(either.value)
        }

    val value: R
        @JvmName("get")
        get() = when (either) {
            is Either.Left -> throw NoSuchElementException("Retrieving right-side value from left-side")
            is Either.Right -> either.value
        }

    inline fun <U> map(transformer: (R) -> U): Either<L, U> = when (either) {
        is Either.Left -> either
        is Either.Right -> Either.Right(transformer(either.value))
    }

    inline fun <U> flatMap(transformer: (R) -> Either<@UnsafeVariance L, U>): Either<L, U> = when (either) {
        is Either.Left -> either
        is Either.Right -> transformer(either.value)
    }

    inline fun any(predicate: (R) -> Boolean): Boolean = when (either) {
        is Either.Left -> false
        is Either.Right -> predicate(either.value)
    }

    inline fun all(predicate: (R) -> Boolean): Boolean = when (either) {
        is Either.Left -> true
        is Either.Right -> predicate(either.value)
    }

    inline fun none(predicate: (R) -> Boolean): Boolean = when (either) {
        is Either.Left -> true
        is Either.Right -> predicate(either.value)
    }

    /**
     * Executes the given [action] if `this` is the [right-side][Either.Right].
     */
    inline fun forEach(action: (R) -> Unit) {
        if (either is Either.Right) action(either.value)
    }

    /**
     * Returns whether or not the specified [item] is equal to the [value][Either.Right.value] if `this` is
     * [right][Either.Right], or returns `false` if `this` is [left][Either.Left].
     */
    operator fun contains(item: @UnsafeVariance R): Boolean = when (either) {
        is Either.Left -> false
        is Either.Right -> item == either.value
    }

    /**
     * Returns [Some] if `this` is `right`, or [None] if `this` is `left`.
     */
    fun toOptional(): Option<R> = when (either) {
        is Either.Left -> None
        is Either.Right -> Some(either.value)
    }

    /**
     * Returns [Success] if `this` is `right`, or [Failure] if `this` is `left`.
     */
    fun toTry(): Try<R> = when (either) {
        is Either.Left -> Failure(WrongJunctionException("Expected right-side, got left-side"))
        is Either.Right -> Success(either.value)
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

/**
 * Returns `true` if this is the [left-side][Either.Left] of the union, otherwise `false`.
 */
fun <L, R> EitherProjection<L, R>.isLeft(): Boolean {
    contract {
        returns(true) implies (this@isLeft is LeftProjection<L, R>)
        returns(false) implies (this@isLeft is RightProjection<L, R>)
    }
    return this.isLeft
}

/**
 * Returns `true` if this is the [right-side][Either.Right] of the union, otherwise `false`.
 */
fun <L, R> EitherProjection<L, R>.isRight(): Boolean {
    contract {
        returns(true) implies (this@isRight is RightProjection<L, R>)
        returns(false) implies (this@isRight is LeftProjection<L, R>)
    }
    return this.isRight
}

class WrongJunctionException internal constructor(message: String) : RuntimeException(message)