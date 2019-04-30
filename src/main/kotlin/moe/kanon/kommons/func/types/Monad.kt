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

@file:Suppress("FunctionName")

package moe.kanon.kommons.func.types

import kotlin.system.exitProcess

interface Monad<Self, out T> : Functor<Self, T> {
    infix fun <U> flatMap(binder: (T) -> Monad<Self, U>): Monad<Self, U>

    override infix fun <U> map(transformer: (T) -> U): Monad<Self, U>
}

/*data class Tuple1<out A>(val a: A) : Monad<Tuple1<*>, A> {
    override fun <U> map(transformer: (A) -> U): Tuple1<U> {
        TODO("not implemented")
    }

    override fun <U> flatMap(binder: (A) -> Monad<Tuple1<*>, U>): Tuple1<U> {
        TODO("not implemented")
    }
}

private typealias This2<A> = Tuple2<@UnsafeVariance A, *>

data class Tuple2<out A, out B>(val a: A, val b: B) : Monad<Tuple2<*, *>, Tuple2<*, *>> {
    override fun <U> flatMap(binder: (Tuple2<A, B>) -> Monad<This2<A>, U>): Monad<This2<A>, U> {
        TODO("not implemented")
    }

    // Monad<This2<A>, U>
    override fun <U> map(transformer: (Tuple2<*, *>) -> U): Tuple2<U, U> {
        TODO("not implemented")
    }
}*/

/*private typealias This<A> = Tuple2<@UnsafeVariance A, *>

data class Tuple2<out A, out B>(val a: A, val b: B) : Monad<This<A>, Tuple2<A, B>> {
    override fun <U, R> flatMap(binder: (Tuple2<A, B>) -> Monad<This<A>, U>): Monad<This<A>, U> {
        TODO("not implemented")
    }

    override fun <U> map(transformer: (Tuple2<A, B>) -> U): Functor<This<A>, U> {
        TODO("not implemented")
    }
}*/