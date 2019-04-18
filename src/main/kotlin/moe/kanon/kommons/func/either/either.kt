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
@file:Suppress("DataClassPrivateConstructor")

package moe.kanon.kommons.func.either

import moe.kanon.kommons.func.Func
import moe.kanon.kommons.func.Supplier

/**
 * Represents a value of one or two possible types.
 */
@Suppress("UNCHECKED_CAST")
sealed class Either<L, R> {

    abstract fun isLeft(): Boolean

    abstract fun isRight(): Boolean

    inline fun <U> fold(ifLeft: Func<in L, out U>, ifRight: Func<in R, out U>): U = when (this) {
        is Left -> ifLeft(value)
        is Right -> ifRight(value)
    }

    inline fun <U> flatMapLeft(mapper: Func<L, Either<U, R>>): Either<U, R> = when (this) {
        is Left -> mapper(value)
        is Right -> this as Either<U, R>
    }

    inline fun <U> flatMapRight(mapper: Func<R, Either<L, U>>): Either<L, U> = when (this) {
        is Left -> this as Either<L, U>
        is Right -> mapper(value)
    }

    companion object {
        @JvmStatic
        fun <L> left(value: L): Either<L, Nothing> = Left(value)

        @JvmStatic
        fun <R> right(value: R): Either<Nothing, R> = Right(value)

        @JvmStatic
        fun <L, R> of(left: L, right: R): Either<L, R> = TODO()
    }
}

data class Left<L> private constructor(val value: L) : Either<L, Nothing>() {
    override fun isLeft(): Boolean = true
    override fun isRight(): Boolean = false

    override fun toString(): String = "Left[$value]"

    companion object {
        @JvmStatic
        @JvmName("of")
        inline operator fun <T> invoke(value: Supplier<T>): Either<T, Nothing> = invoke(value())

        @JvmStatic
        @JvmName("of")
        operator fun <T> invoke(value: T): Either<T, Nothing> = Left(value)
    }
}

data class Right<R> private constructor(val value: R) : Either<Nothing, R>() {
    override fun isLeft(): Boolean = false
    override fun isRight(): Boolean = true

    override fun toString(): String = "Right[$value]"

    companion object {
        @JvmStatic
        @JvmName("of")
        inline operator fun <T> invoke(value: Supplier<T>): Either<Nothing, T> = invoke(value())

        @JvmStatic
        @JvmName("of")
        operator fun <T> invoke(value: T): Either<Nothing, T> = Right(value)
    }
}