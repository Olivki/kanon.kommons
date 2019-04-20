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

@file:JvmName("KEither")
@file:Suppress("DataClassPrivateConstructor", "NOTHING_TO_INLINE")

package moe.kanon.kommons.func

/*
 * This is based/ported on the Either[1] class from the Scala standard library.
 *
 * The code has been somewhat heavily "kotlinified" to make it more idiomatic to use from Kotlin.
 *
 * [1]: https://github.com/scala/scala/blob/2.13.x/src/library/scala/util/Either.scala
 */

/**
 * Represents a value of one or two possible types *(a disjoint union)*.
 *
 * This class is based/ported on/from the
 * [Either](https://github.com/scala/scala/blob/2.13.x/src/library/scala/util/Either.scala) class from the Scala
 * standard library.
 *
 * To more closely adhere to the Kotlin design of avoiding implicit behaviour when possible the right-bias found in the
 * original Scala implementation has been removed. That means that this class on its own does *not* provide any real
 * utility functions *(`map`, `flatMap`, `any`, etc...)*, instead to access those functions one needs to explicitly
 * define the [projection][EitherProjection] that they want to use. *`(Either.left or Either.right)`*
 *
 * Some of the functions have also been renamed to follow the general Kotlin naming style more closely, i.e;
 * `exists(predicate)` -> `any(predicate)`, `forall(predicate)` -> `all(predicate)`, etc..
 */
sealed class Either<out L, out R> {
    abstract val isLeft: Boolean
    abstract val isRight: Boolean

    /**
     * Returns a [left-side-projection][LeftProjection] of `this` either instance.
     */
    val left: LeftProjection<L, R> by lazy { LeftProjection(this) }

    /**
     * Returns a [right-side-projection][RightProjection] of `this` either instance.
     */
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

    inline fun <U> fold(ifLeft: (L) -> U, ifRight: (R) -> U): U = when (this) {
        is Left -> ifLeft(value)
        is Right -> ifRight(value)
    }

    /**
     * Executes the given [action] if, and *only* if, `this` is [left][Left].
     */
    inline fun ifLeft(action: (L) -> Unit) {
        if (this is Left) action(value)
    }

    /**
     * Executes the given [action] if, and *only* if, `this` is [right][Right].
     */
    inline fun ifRight(action: (R) -> Unit) {
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
     * Returns whether or not `this` either contains the specified [item].
     */
    operator fun contains(item: Any): Boolean = when (this) {
        is Left -> value == item
        is Right -> value == item
    }

    abstract override fun toString(): String

    abstract override fun hashCode(): Int

    abstract override fun equals(other: Any?): Boolean

    companion object {
        /**
         * Creates a [left-based][Left] [Either] wrapped around the given [value].
         */
        @JvmStatic
        fun <L> left(value: L): Either<L, Nothing> = Left(value)

        /**
         * Creates a [right-based][Right] [Either] wrapped around the given [value].
         */
        @JvmStatic
        fun <R> right(value: R): Either<Nothing, R> = Right(value)
    }
}

/**
 * Up-casts `this` `Left<T, Nothing>` to `Either<T, R>`.
 */
fun <L, R> Either<L, Nothing>.withRight(): Either<L, R> = this

/**
 * Up-casts `this` `Right<Nothing, T>` to `Either<L, T>`.
 */
fun <L, R> Either<Nothing, R>.withLeft(): Either<L, R> = this

/**
 * Represents the left-side in a [disjoint union][Either], the direct opposite of [Right].
 *
 * @property [value] The underlying value that `this` is wrapped around.
 */
data class Left<out T>(val value: T) : Either<T, Nothing>() {
    override val isLeft: Boolean = true
    override val isRight: Boolean = false

    // these two functions need to be split up into two different ones to make sure that interop with Java is good,
    // as Java has a lot weaker type inference and casting abilities than Kotlin does.

    /**
     * Up-casts `this` `Left<T, Nothing>` to `Either<T, R>`.
     *
     * @param [clz] used to determine the type of [R]
     */
    @Suppress("UNUSED_PARAMETER")
    infix fun <R : Any> withRight(clz: Class<R>): Either<T, R> = this

    /**
     * Up-casts `this` `Left<T, Nothing>` to `Either<T, R>`.
     */
    inline fun <reified R : Any> withRight(): Either<T, R> = withRight(R::class.java)

    override fun toString(): String = "Left[$value]"
}

/**
 * Represents the right-side in a [disjoint union][Either], the direct opposite of [Left].
 *
 * @property [value] The underlying value that `this` is wrapped around.
 */
data class Right<out T>(val value: T) : Either<Nothing, T>() {
    override val isLeft: Boolean = false
    override val isRight: Boolean = true

    // these two functions need to be split up into two different ones to make sure that interop with Java is good,
    // as Java has a lot weaker type inference and casting abilities than Kotlin does.

    /**
     * Up-casts `this` `Right<Nothing, T>` to `Either<L, T>`.
     *
     * @param [clz] used to determine the type of [L]
     */
    @Suppress("UNUSED_PARAMETER")
    infix fun <L : Any> withLeft(clz: Class<L>): Either<L, T> = this

    /**
     * Up-casts `this` `Right<Nothing, T>` to `Either<L, T>`.
     */
    inline fun <reified L : Any> withLeft(): Either<L, T> = withLeft(L::class.java)

    override fun toString(): String = "Right[$value]"
}

/**
 * A marker interface for projections for sides in a [disjoint union][Either].
 */
interface EitherProjection<out L, out R> {
    /**
     * Returns `this` either with the values swapped around.
     *
     * @return if `this` is [left][Left] then it will return [right][Right] or if `this` is `right` then it will return
     * `left`.
     */
    @JvmDefault
    fun swap(): Either<R, L> = when (this) {
        is LeftProjection -> Right(value)
        is RightProjection -> Left(value)
        else -> throw NoSuchElementException()
    }
}

/*
 * The two [LeftProjection] and [RightProjection] classes could have been made to be 'inline' classes to remove any
 * overhead that may be created from the extra wrapping, but as to make sure that the [Either] class can be used in a
 * nice way from the Java side, this was decided against. If the goal is to make something *only* for the Kotlin side,
 * the best choice would be to make them as 'inline'.
 */

/**
 * A projection of the [left-side][Left] in a [disjoint union][Either].
 *
 * All functions in this class are left-side biased, unless explicitly stated otherwise.
 */
data class LeftProjection<out L, out R>(
    @PublishedApi
    @get:JvmSynthetic
    internal val backing: Either<L, R>
) : EitherProjection<L, R> {
    val value: L
        get() = when (backing) {
            is Left -> backing.value
            is Right -> throw NoSuchElementException("Retrieving left-side value from right-side")
        }

    inline fun <U> map(transformer: (L) -> U): Either<U, R> = when (backing) {
        is Left -> Left(transformer(backing.value))
        is Right -> backing
    }

    inline fun <U> flatMap(transformer: (L) -> Either<U, @UnsafeVariance R>): Either<U, R> = when (backing) {
        is Left -> transformer(backing.value)
        is Right -> backing
    }

    inline fun any(predicate: (L) -> Boolean): Boolean = when (backing) {
        is Left -> predicate(backing.value)
        is Right -> false
    }

    inline fun all(predicate: (L) -> Boolean): Boolean = when (backing) {
        is Left -> predicate(backing.value)
        is Right -> true
    }

    inline fun none(predicate: (L) -> Boolean): Boolean = when (backing) {
        is Left -> !predicate(backing.value)
        is Right -> true
    }

    inline fun forEach(action: (L) -> Unit) {
        if (backing is Left) action(backing.value)
    }

    operator fun contains(item: @UnsafeVariance L): Boolean = item == value
}

/**
 * A projection of the [right-side][Right] in a [disjoint union][Either].
 *
 * All functions in this class are right-side biased, unless explicitly stated otherwise.
 */
data class RightProjection<out L, out R>(
    @PublishedApi
    @get:JvmSynthetic
    internal val backing: Either<L, R>
) : EitherProjection<L, R> {
    val value: R
        get() = when (backing) {
            is Left -> throw NoSuchElementException("Retrieving right-side value from left-side")
            is Right -> backing.value
        }

    inline fun <U> map(transformer: (R) -> U): Either<L, U> = when (backing) {
        is Left -> backing
        is Right -> Right(transformer(backing.value))
    }

    inline fun <U> flatMap(transformer: (R) -> Either<@UnsafeVariance L, U>): Either<L, U> = when (backing) {
        is Left -> backing
        is Right -> transformer(backing.value)
    }

    inline fun any(predicate: (R) -> Boolean): Boolean = when (backing) {
        is Left -> false
        is Right -> predicate(backing.value)
    }

    inline fun all(predicate: (R) -> Boolean): Boolean = when (backing) {
        is Left -> true
        is Right -> predicate(backing.value)
    }

    inline fun none(predicate: (R) -> Boolean): Boolean = when (backing) {
        is Left -> true
        is Right -> predicate(backing.value)
    }

    inline fun forEach(action: (R) -> Unit) {
        if (backing is Right) action(backing.value)
    }

    operator fun contains(item: @UnsafeVariance R): Boolean = item == value
}