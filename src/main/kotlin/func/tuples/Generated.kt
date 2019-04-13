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

@file:JvmName("Tuples")

package moe.kanon.kommons.func.tuples

/**
 * Represents a `null-tuple`, used as a marker interface for the generated tuples.
 */
interface Tuple0

typealias NullTuple = Tuple0

// Auto-generated
// A max of 24 tuples are created, this is to adhere to the amount of auto-generated 'FunctionN' classes there are for
// the JVM side.
/**
 * Represents a 1-tuple.
 *
 * A 1-tuple is also known as a singleton.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Singleton`s exhibits value semantics, i.e. two `singleton`s are equal if all one components are equal.
 *
 * @since 0.6.0
 */
typealias Singleton<A> = Tuple1<A>

/**
 * Represents a 2-tuple.
 *
 * A 2-tuple is also known as a duad.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duad`s exhibits value semantics, i.e. two `duad`s are equal if all two components are equal.
 *
 * @since 0.6.0
 */
typealias Duad<A, B> = Tuple2<A, B>

/**
 * Represents a 3-tuple.
 *
 * A 3-tuple is also known as a triad.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Triad`s exhibits value semantics, i.e. two `triad`s are equal if all three components are equal.
 *
 * @since 0.6.0
 */
typealias Triad<A, B, C> = Tuple3<A, B, C>

/**
 * Represents a 4-tuple.
 *
 * A 4-tuple is also known as a quadruple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quadruple`s exhibits value semantics, i.e. two `quadruple`s are equal if all four components are equal.
 *
 * @since 0.6.0
 */
typealias Quadruple<A, B, C, D> = Tuple4<A, B, C, D>

/**
 * Represents a 5-tuple.
 *
 * A 5-tuple is also known as a quintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quintuple`s exhibits value semantics, i.e. two `quintuple`s are equal if all five components are equal.
 *
 * @since 0.6.0
 */
typealias Quintuple<A, B, C, D, E> = Tuple5<A, B, C, D, E>

/**
 * Represents a 6-tuple.
 *
 * A 6-tuple is also known as a sextuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Sextuple`s exhibits value semantics, i.e. two `sextuple`s are equal if all six components are equal.
 *
 * @since 0.6.0
 */
typealias Sextuple<A, B, C, D, E, F> = Tuple6<A, B, C, D, E, F>

/**
 * Represents a 7-tuple.
 *
 * A 7-tuple is also known as a septuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Septuple`s exhibits value semantics, i.e. two `septuple`s are equal if all seven components are equal.
 *
 * @since 0.6.0
 */
typealias Septuple<A, B, C, D, E, F, G> = Tuple7<A, B, C, D, E, F, G>

/**
 * Represents a 8-tuple.
 *
 * A 8-tuple is also known as a octuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Octuple`s exhibits value semantics, i.e. two `octuple`s are equal if all eight components are equal.
 *
 * @since 0.6.0
 */
typealias Octuple<A, B, C, D, E, F, G, H> = Tuple8<A, B, C, D, E, F, G, H>

/**
 * Represents a 9-tuple.
 *
 * A 9-tuple is also known as a nonuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Nonuple`s exhibits value semantics, i.e. two `nonuple`s are equal if all nine components are equal.
 *
 * @since 0.6.0
 */
typealias Nonuple<A, B, C, D, E, F, G, H, I> = Tuple9<A, B, C, D, E, F, G, H, I>

/**
 * Represents a 10-tuple.
 *
 * A 10-tuple is also known as a decuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Decuple`s exhibits value semantics, i.e. two `decuple`s are equal if all ten components are equal.
 *
 * @since 0.6.0
 */
typealias Decuple<A, B, C, D, E, F, G, H, I, J> = Tuple10<A, B, C, D, E, F, G, H, I, J>

/**
 * Represents a 11-tuple.
 *
 * A 11-tuple is also known as a undecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Undecuple`s exhibits value semantics, i.e. two `undecuple`s are equal if all eleven components are equal.
 *
 * @since 0.6.0
 */
typealias Undecuple<A, B, C, D, E, F, G, H, I, J, K> = Tuple11<A, B, C, D, E, F, G, H, I, J, K>

/**
 * Represents a 12-tuple.
 *
 * A 12-tuple is also known as a duodecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duodecuple`s exhibits value semantics, i.e. two `duodecuple`s are equal if all twelve components are equal.
 *
 * @since 0.6.0
 */
typealias Duodecuple<A, B, C, D, E, F, G, H, I, J, K, L> = Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>

/**
 * Represents a 13-tuple.
 *
 * A 13-tuple is also known as a tredecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Tredecuple`s exhibits value semantics, i.e. two `tredecuple`s are equal if all thirteen components are equal.
 *
 * @since 0.6.0
 */
typealias Tredecuple<A, B, C, D, E, F, G, H, I, J, K, L, M> = Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>

/**
 * Represents a 14-tuple.
 *
 * A 14-tuple is also known as a quattuordecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quattuordecuple`s exhibits value semantics, i.e. two `quattuordecuple`s are equal if all fourteen components are equal.
 *
 * @since 0.6.0
 */
typealias Quattuordecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N> = Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>

/**
 * Represents a 15-tuple.
 *
 * A 15-tuple is also known as a quindecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quindecuple`s exhibits value semantics, i.e. two `quindecuple`s are equal if all fifteen components are equal.
 *
 * @since 0.6.0
 */
typealias Quindecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>

/**
 * Represents a 16-tuple.
 *
 * A 16-tuple is also known as a sexdecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Sexdecuple`s exhibits value semantics, i.e. two `sexdecuple`s are equal if all sixteen components are equal.
 *
 * @since 0.6.0
 */
typealias Sexdecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>

/**
 * Represents a 17-tuple.
 *
 * A 17-tuple is also known as a septendecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Septendecuple`s exhibits value semantics, i.e. two `septendecuple`s are equal if all seventeen components are equal.
 *
 * @since 0.6.0
 */
typealias Septendecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> = Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>

/**
 * Represents a 18-tuple.
 *
 * A 18-tuple is also known as a octodecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Octodecuple`s exhibits value semantics, i.e. two `octodecuple`s are equal if all eighteen components are equal.
 *
 * @since 0.6.0
 */
typealias Octodecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>

/**
 * Represents a 19-tuple.
 *
 * A 19-tuple is also known as a novemdecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Novemdecuple`s exhibits value semantics, i.e. two `novemdecuple`s are equal if all nineteen components are equal.
 *
 * @since 0.6.0
 */
typealias Novemdecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>

/**
 * Represents a 20-tuple.
 *
 * A 20-tuple is also known as a vigintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Vigintuple`s exhibits value semantics, i.e. two `vigintuple`s are equal if all twenty components are equal.
 *
 * @since 0.6.0
 */
typealias Vigintuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> = Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>

/**
 * Represents a 21-tuple.
 *
 * A 21-tuple is also known as a unvigintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Unvigintuple`s exhibits value semantics, i.e. two `unvigintuple`s are equal if all twenty-one components are equal.
 *
 * @since 0.6.0
 */
typealias Unvigintuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> = Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>

/**
 * Represents a 22-tuple.
 *
 * A 22-tuple is also known as a duovigintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duovigintuple`s exhibits value semantics, i.e. two `duovigintuple`s are equal if all twenty-two components are equal.
 *
 * @since 0.6.0
 */
typealias Duovigintuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W> = Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W>

/**
 * Represents a 23-tuple.
 *
 * A 23-tuple is also known as a trevigintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Trevigintuple`s exhibits value semantics, i.e. two `trevigintuple`s are equal if all twenty-three components are equal.
 *
 * @since 0.6.0
 */
typealias Trevigintuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X> = Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X>

/**
 * Represents a 24-tuple.
 *
 * A 24-tuple is also known as a quattuorvigintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quattuorvigintuple`s exhibits value semantics, i.e. two `quattuorvigintuple`s are equal if all twenty-four components are equal.
 *
 * @since 0.6.0
 */
typealias Quattuorvigintuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X, Y> = Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X, Y>

typealias Serializable = java.io.Serializable

/**
 * Represents a 1-tuple.
 *
 * A 1-tuple is also known as a singleton.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Singleton`s exhibits value semantics, i.e. two `singleton`s are equal if all one components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple1].
 *
 * @param [a] the first value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 */
data class Tuple1<out A>(val a: A) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple2] from the values of `this` tuple, and [that]
     */
    infix fun <B> to(that: B): Tuple2<A, B> = Tuple2(a, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple1] bearing [transformer] and returns the result
     */
    inline fun <A1> flatMap(transformer: (@ParameterName("a") A) -> Tuple1<A1>): Tuple1<A1> = transformer(a)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A) -> R1): R1 = transformer(a)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A) -> Boolean): Boolean = predicate(a)

    override fun toString() = "($a)"
}

/**
 * Creates a [tuple][Tuple1] from `this`.
 */
@JvmName("from")
fun <T> T.toSingleton(): Tuple1<T> = Tuple1(this)

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple1<T>.toList(): List<T> = listOf(this.a)

/**
 * Represents a 2-tuple.
 *
 * A 2-tuple is also known as a duad.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duad`s exhibits value semantics, i.e. two `duad`s are equal if all two components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple2].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 */
data class Tuple2<out A, out B>(val a: A, val b: B) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple3] from the values of `this` tuple, and [that]
     */
    infix fun <C> to(that: C): Tuple3<A, B, C> = Tuple3(a, b, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple2] bearing [transformer] and returns the result
     */
    inline fun <A1, B1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B) -> Tuple2<A1, B1>): Tuple2<A1, B1> = transformer(a, b)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B) -> R1): R1 = transformer(a, b)

    /**
     * Converts `this` tuple into a [Pair].
     */
    fun toPair(): Pair<A, B> = Pair(a, b)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B) -> Boolean): Boolean = predicate(a, b)

    override fun toString() = "($a, $b)"
}

/**
 * Creates a [tuple][Tuple2] from `this` and [that].
 */
@JvmName("from")
infix fun <A, B> A.toT(that: B): Tuple2<A, B> = Tuple2(this, that)

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple2<T, T>.toList(): List<T> = listOf(this.a, this.b)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple2<K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b)

/**
 * Converts `this` pair into a [tuple][Tuple2].
 *
 * @since 0.6.0
 */
@JvmName("fromPair")
fun <A, B> Pair<A, B>.toTuple(): Tuple2<A, B> = Tuple2(this.first, this.second)

/**
 * Represents a 3-tuple.
 *
 * A 3-tuple is also known as a triad.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Triad`s exhibits value semantics, i.e. two `triad`s are equal if all three components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple3].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 */
data class Tuple3<out A, out B, out C>(val a: A, val b: B, val c: C) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple4] from the values of `this` tuple, and [that]
     */
    infix fun <D> to(that: D): Tuple4<A, B, C, D> = Tuple4(a, b, c, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple3] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C) -> Tuple3<A1, B1, C1>): Tuple3<A1, B1, C1> = transformer(a, b, c)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C) -> R1): R1 = transformer(a, b, c)

    /**
     * Converts `this` tuple into a [Triple].
     */
    fun toTriple(): Triple<A, B, C> = Triple(a, b, c)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C) -> Boolean): Boolean = predicate(a, b, c)

    override fun toString() = "($a, $b, $c)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple3<T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c)

/**
 * Converts `this` triple into a [tuple][Tuple3].
 *
 * @since 0.6.0
 */
@JvmName("fromTriple")
fun <A, B, C> Triple<A, B, C>.toTuple(): Tuple3<A, B, C> = Tuple3(this.first, this.second, this.third)

/**
 * Represents a 4-tuple.
 *
 * A 4-tuple is also known as a quadruple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quadruple`s exhibits value semantics, i.e. two `quadruple`s are equal if all four components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple4].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 */
data class Tuple4<out A, out B, out C, out D>(val a: A, val b: B, val c: C, val d: D) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple5] from the values of `this` tuple, and [that]
     */
    infix fun <E> to(that: E): Tuple5<A, B, C, D, E> = Tuple5(a, b, c, d, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple4] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D) -> Tuple4<A1, B1, C1, D1>): Tuple4<A1, B1, C1, D1> = transformer(a, b, c, d)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D) -> R1): R1 = transformer(a, b, c, d)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D) -> Boolean): Boolean = predicate(a, b, c, d)

    override fun toString() = "($a, $b, $c, $d)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple4<T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple4<K, V, K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b, this.c to this.d)

/**
 * Represents a 5-tuple.
 *
 * A 5-tuple is also known as a quintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quintuple`s exhibits value semantics, i.e. two `quintuple`s are equal if all five components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple5].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 */
data class Tuple5<out A, out B, out C, out D, out E>(val a: A, val b: B, val c: C, val d: D, val e: E) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple6] from the values of `this` tuple, and [that]
     */
    infix fun <F> to(that: F): Tuple6<A, B, C, D, E, F> = Tuple6(a, b, c, d, e, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple5] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E) -> Tuple5<A1, B1, C1, D1, E1>): Tuple5<A1, B1, C1, D1, E1> = transformer(a, b, c, d, e)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E) -> R1): R1 = transformer(a, b, c, d, e)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E) -> Boolean): Boolean = predicate(a, b, c, d, e)

    override fun toString() = "($a, $b, $c, $d, $e)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple5<T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e)

/**
 * Represents a 6-tuple.
 *
 * A 6-tuple is also known as a sextuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Sextuple`s exhibits value semantics, i.e. two `sextuple`s are equal if all six components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple6].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 */
data class Tuple6<out A, out B, out C, out D, out E, out F>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple7] from the values of `this` tuple, and [that]
     */
    infix fun <G> to(that: G): Tuple7<A, B, C, D, E, F, G> = Tuple7(a, b, c, d, e, f, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple6] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F) -> Tuple6<A1, B1, C1, D1, E1, F1>): Tuple6<A1, B1, C1, D1, E1, F1> = transformer(a, b, c, d, e, f)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F) -> R1): R1 = transformer(a, b, c, d, e, f)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F) -> Boolean): Boolean = predicate(a, b, c, d, e, f)

    override fun toString() = "($a, $b, $c, $d, $e, $f)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple6<T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple6<K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b, this.c to this.d, this.e to this.f)

/**
 * Represents a 7-tuple.
 *
 * A 7-tuple is also known as a septuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Septuple`s exhibits value semantics, i.e. two `septuple`s are equal if all seven components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple7].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 */
data class Tuple7<out A, out B, out C, out D, out E, out F, out G>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple8] from the values of `this` tuple, and [that]
     */
    infix fun <H> to(that: H): Tuple8<A, B, C, D, E, F, G, H> = Tuple8(a, b, c, d, e, f, g, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple7] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G) -> Tuple7<A1, B1, C1, D1, E1, F1, G1>): Tuple7<A1, B1, C1, D1, E1, F1, G1> = transformer(a, b, c, d, e, f, g)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G) -> R1): R1 = transformer(a, b, c, d, e, f, g)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple7<T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g)

/**
 * Represents a 8-tuple.
 *
 * A 8-tuple is also known as a octuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Octuple`s exhibits value semantics, i.e. two `octuple`s are equal if all eight components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple8].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 */
data class Tuple8<out A, out B, out C, out D, out E, out F, out G, out H>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple9] from the values of `this` tuple, and [that]
     */
    infix fun <I> to(that: I): Tuple9<A, B, C, D, E, F, G, H, I> = Tuple9(a, b, c, d, e, f, g, h, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple8] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H) -> Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>): Tuple8<A1, B1, C1, D1, E1, F1, G1, H1> = transformer(a, b, c, d, e, f, g, h)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H) -> R1): R1 = transformer(a, b, c, d, e, f, g, h)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple8<T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple8<K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b, this.c to this.d, this.e to this.f, this.g to this.h)

/**
 * Represents a 9-tuple.
 *
 * A 9-tuple is also known as a nonuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Nonuple`s exhibits value semantics, i.e. two `nonuple`s are equal if all nine components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple9].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 */
data class Tuple9<out A, out B, out C, out D, out E, out F, out G, out H, out I>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple10] from the values of `this` tuple, and [that]
     */
    infix fun <J> to(that: J): Tuple10<A, B, C, D, E, F, G, H, I, J> = Tuple10(a, b, c, d, e, f, g, h, i, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple9] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I) -> Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>): Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1> = transformer(a, b, c, d, e, f, g, h, i)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple9<T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i)

/**
 * Represents a 10-tuple.
 *
 * A 10-tuple is also known as a decuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Decuple`s exhibits value semantics, i.e. two `decuple`s are equal if all ten components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple10].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 */
data class Tuple10<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple11] from the values of `this` tuple, and [that]
     */
    infix fun <K> to(that: K): Tuple11<A, B, C, D, E, F, G, H, I, J, K> = Tuple11(a, b, c, d, e, f, g, h, i, j, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple10] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J) -> Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>): Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1> = transformer(a, b, c, d, e, f, g, h, i, j)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple10<T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple10<K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b, this.c to this.d, this.e to this.f, this.g to this.h, this.i to this.j)

/**
 * Represents a 11-tuple.
 *
 * A 11-tuple is also known as a undecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Undecuple`s exhibits value semantics, i.e. two `undecuple`s are equal if all eleven components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple11].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 */
data class Tuple11<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple12] from the values of `this` tuple, and [that]
     */
    infix fun <L> to(that: L): Tuple12<A, B, C, D, E, F, G, H, I, J, K, L> = Tuple12(a, b, c, d, e, f, g, h, i, j, k, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple11] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K) -> Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>): Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1> = transformer(a, b, c, d, e, f, g, h, i, j, k)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple11<T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k)

/**
 * Represents a 12-tuple.
 *
 * A 12-tuple is also known as a duodecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duodecuple`s exhibits value semantics, i.e. two `duodecuple`s are equal if all twelve components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple12].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 */
data class Tuple12<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple13] from the values of `this` tuple, and [that]
     */
    infix fun <M> to(that: M): Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M> = Tuple13(a, b, c, d, e, f, g, h, i, j, k, l, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple12] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L) -> Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>): Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple12<T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple12<K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b, this.c to this.d, this.e to this.f, this.g to this.h, this.i to this.j, this.k to this.l)

/**
 * Represents a 13-tuple.
 *
 * A 13-tuple is also known as a tredecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Tredecuple`s exhibits value semantics, i.e. two `tredecuple`s are equal if all thirteen components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple13].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 */
data class Tuple13<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple14] from the values of `this` tuple, and [that]
     */
    infix fun <N> to(that: N): Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N> = Tuple14(a, b, c, d, e, f, g, h, i, j, k, l, m, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple13] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M) -> Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>): Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple13<T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m)

/**
 * Represents a 14-tuple.
 *
 * A 14-tuple is also known as a quattuordecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quattuordecuple`s exhibits value semantics, i.e. two `quattuordecuple`s are equal if all fourteen components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple14].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 * @param [n] the fourteenth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 * @property [n] The fourteenth value of `this` tuple.
 */
data class Tuple14<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M, val n: N) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple15] from the values of `this` tuple, and [that]
     */
    infix fun <O> to(that: O): Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = Tuple15(a, b, c, d, e, f, g, h, i, j, k, l, m, n, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple14] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N) -> Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>): Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple14<T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple14<K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b, this.c to this.d, this.e to this.f, this.g to this.h, this.i to this.j, this.k to this.l, this.m to this.n)

/**
 * Represents a 15-tuple.
 *
 * A 15-tuple is also known as a quindecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quindecuple`s exhibits value semantics, i.e. two `quindecuple`s are equal if all fifteen components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple15].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 * @param [n] the fourteenth value of the tuple
 * @param [o] the fifteenth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 * @property [n] The fourteenth value of `this` tuple.
 * @property [o] The fifteenth value of `this` tuple.
 */
data class Tuple15<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M, val n: N, val o: O) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple16] from the values of `this` tuple, and [that]
     */
    infix fun <P> to(that: P): Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = Tuple16(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple15] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O) -> Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>): Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple15<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o)

/**
 * Represents a 16-tuple.
 *
 * A 16-tuple is also known as a sexdecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Sexdecuple`s exhibits value semantics, i.e. two `sexdecuple`s are equal if all sixteen components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple16].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 * @param [n] the fourteenth value of the tuple
 * @param [o] the fifteenth value of the tuple
 * @param [p] the sixteenth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 * @property [n] The fourteenth value of `this` tuple.
 * @property [o] The fifteenth value of `this` tuple.
 * @property [p] The sixteenth value of `this` tuple.
 */
data class Tuple16<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M, val n: N, val o: O, val p: P) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple17] from the values of `this` tuple, and [that]
     */
    infix fun <Q> to(that: Q): Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> = Tuple17(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple16] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P) -> Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>): Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple16<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple16<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b, this.c to this.d, this.e to this.f, this.g to this.h, this.i to this.j, this.k to this.l, this.m to this.n, this.o to this.p)

/**
 * Represents a 17-tuple.
 *
 * A 17-tuple is also known as a septendecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Septendecuple`s exhibits value semantics, i.e. two `septendecuple`s are equal if all seventeen components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple17].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 * @param [n] the fourteenth value of the tuple
 * @param [o] the fifteenth value of the tuple
 * @param [p] the sixteenth value of the tuple
 * @param [q] the seventeenth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 * @property [n] The fourteenth value of `this` tuple.
 * @property [o] The fifteenth value of `this` tuple.
 * @property [p] The sixteenth value of `this` tuple.
 * @property [q] The seventeenth value of `this` tuple.
 */
data class Tuple17<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M, val n: N, val o: O, val p: P, val q: Q) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple18] from the values of `this` tuple, and [that]
     */
    infix fun <R> to(that: R): Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = Tuple18(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple17] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q) -> Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>): Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple17<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q)

/**
 * Represents a 18-tuple.
 *
 * A 18-tuple is also known as a octodecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Octodecuple`s exhibits value semantics, i.e. two `octodecuple`s are equal if all eighteen components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple18].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 * @param [n] the fourteenth value of the tuple
 * @param [o] the fifteenth value of the tuple
 * @param [p] the sixteenth value of the tuple
 * @param [q] the seventeenth value of the tuple
 * @param [r] the eighteenth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 * @property [n] The fourteenth value of `this` tuple.
 * @property [o] The fifteenth value of `this` tuple.
 * @property [p] The sixteenth value of `this` tuple.
 * @property [q] The seventeenth value of `this` tuple.
 * @property [r] The eighteenth value of `this` tuple.
 */
data class Tuple18<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M, val n: N, val o: O, val p: P, val q: Q, val r: R) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple19] from the values of `this` tuple, and [that]
     */
    infix fun <S> to(that: S): Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = Tuple19(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple18] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R) -> Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>): Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple18<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple18<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b, this.c to this.d, this.e to this.f, this.g to this.h, this.i to this.j, this.k to this.l, this.m to this.n, this.o to this.p, this.q to this.r)

/**
 * Represents a 19-tuple.
 *
 * A 19-tuple is also known as a novemdecuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Novemdecuple`s exhibits value semantics, i.e. two `novemdecuple`s are equal if all nineteen components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple19].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 * @param [n] the fourteenth value of the tuple
 * @param [o] the fifteenth value of the tuple
 * @param [p] the sixteenth value of the tuple
 * @param [q] the seventeenth value of the tuple
 * @param [r] the eighteenth value of the tuple
 * @param [s] the nineteenth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 * @property [n] The fourteenth value of `this` tuple.
 * @property [o] The fifteenth value of `this` tuple.
 * @property [p] The sixteenth value of `this` tuple.
 * @property [q] The seventeenth value of `this` tuple.
 * @property [r] The eighteenth value of `this` tuple.
 * @property [s] The nineteenth value of `this` tuple.
 */
data class Tuple19<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M, val n: N, val o: O, val p: P, val q: Q, val r: R, val s: S) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple20] from the values of `this` tuple, and [that]
     */
    infix fun <T> to(that: T): Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> = Tuple20(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple19] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S) -> Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>): Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple19<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s)

/**
 * Represents a 20-tuple.
 *
 * A 20-tuple is also known as a vigintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Vigintuple`s exhibits value semantics, i.e. two `vigintuple`s are equal if all twenty components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple20].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 * @param [n] the fourteenth value of the tuple
 * @param [o] the fifteenth value of the tuple
 * @param [p] the sixteenth value of the tuple
 * @param [q] the seventeenth value of the tuple
 * @param [r] the eighteenth value of the tuple
 * @param [s] the nineteenth value of the tuple
 * @param [t] the twentieth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 * @property [n] The fourteenth value of `this` tuple.
 * @property [o] The fifteenth value of `this` tuple.
 * @property [p] The sixteenth value of `this` tuple.
 * @property [q] The seventeenth value of `this` tuple.
 * @property [r] The eighteenth value of `this` tuple.
 * @property [s] The nineteenth value of `this` tuple.
 * @property [t] The twentieth value of `this` tuple.
 */
data class Tuple20<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M, val n: N, val o: O, val p: P, val q: Q, val r: R, val s: S, val t: T) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple21] from the values of `this` tuple, and [that]
     */
    infix fun <U> to(that: U): Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> = Tuple21(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple20] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T) -> Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>): Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple20<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple20<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b, this.c to this.d, this.e to this.f, this.g to this.h, this.i to this.j, this.k to this.l, this.m to this.n, this.o to this.p, this.q to this.r, this.s to this.t)

/**
 * Represents a 21-tuple.
 *
 * A 21-tuple is also known as a unvigintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Unvigintuple`s exhibits value semantics, i.e. two `unvigintuple`s are equal if all twenty-one components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple21].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 * @param [n] the fourteenth value of the tuple
 * @param [o] the fifteenth value of the tuple
 * @param [p] the sixteenth value of the tuple
 * @param [q] the seventeenth value of the tuple
 * @param [r] the eighteenth value of the tuple
 * @param [s] the nineteenth value of the tuple
 * @param [t] the twentieth value of the tuple
 * @param [u] the twenty first value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 * @property [n] The fourteenth value of `this` tuple.
 * @property [o] The fifteenth value of `this` tuple.
 * @property [p] The sixteenth value of `this` tuple.
 * @property [q] The seventeenth value of `this` tuple.
 * @property [r] The eighteenth value of `this` tuple.
 * @property [s] The nineteenth value of `this` tuple.
 * @property [t] The twentieth value of `this` tuple.
 * @property [u] The twenty first value of `this` tuple.
 */
data class Tuple21<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M, val n: N, val o: O, val p: P, val q: Q, val r: R, val s: S, val t: T, val u: U) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple22] from the values of `this` tuple, and [that]
     */
    infix fun <W> to(that: W): Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W> = Tuple22(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple21] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U) -> Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>): Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple21<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u)

/**
 * Represents a 22-tuple.
 *
 * A 22-tuple is also known as a duovigintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duovigintuple`s exhibits value semantics, i.e. two `duovigintuple`s are equal if all twenty-two components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple22].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 * @param [n] the fourteenth value of the tuple
 * @param [o] the fifteenth value of the tuple
 * @param [p] the sixteenth value of the tuple
 * @param [q] the seventeenth value of the tuple
 * @param [r] the eighteenth value of the tuple
 * @param [s] the nineteenth value of the tuple
 * @param [t] the twentieth value of the tuple
 * @param [u] the twenty first value of the tuple
 * @param [w] the twenty second value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 * @property [n] The fourteenth value of `this` tuple.
 * @property [o] The fifteenth value of `this` tuple.
 * @property [p] The sixteenth value of `this` tuple.
 * @property [q] The seventeenth value of `this` tuple.
 * @property [r] The eighteenth value of `this` tuple.
 * @property [s] The nineteenth value of `this` tuple.
 * @property [t] The twentieth value of `this` tuple.
 * @property [u] The twenty first value of `this` tuple.
 * @property [w] The twenty second value of `this` tuple.
 */
data class Tuple22<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out W>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M, val n: N, val o: O, val p: P, val q: Q, val r: R, val s: S, val t: T, val u: U, val w: W) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple23] from the values of `this` tuple, and [that]
     */
    infix fun <X> to(that: X): Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X> = Tuple23(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple22] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U, @ParameterName("w") W) -> Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1>): Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U, @ParameterName("w") W) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U, @ParameterName("w") W) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u, $w)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple22<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u, this.w)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple22<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b, this.c to this.d, this.e to this.f, this.g to this.h, this.i to this.j, this.k to this.l, this.m to this.n, this.o to this.p, this.q to this.r, this.s to this.t, this.u to this.w)

/**
 * Represents a 23-tuple.
 *
 * A 23-tuple is also known as a trevigintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Trevigintuple`s exhibits value semantics, i.e. two `trevigintuple`s are equal if all twenty-three components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple23].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 * @param [n] the fourteenth value of the tuple
 * @param [o] the fifteenth value of the tuple
 * @param [p] the sixteenth value of the tuple
 * @param [q] the seventeenth value of the tuple
 * @param [r] the eighteenth value of the tuple
 * @param [s] the nineteenth value of the tuple
 * @param [t] the twentieth value of the tuple
 * @param [u] the twenty first value of the tuple
 * @param [w] the twenty second value of the tuple
 * @param [x] the twenty third value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 * @property [n] The fourteenth value of `this` tuple.
 * @property [o] The fifteenth value of `this` tuple.
 * @property [p] The sixteenth value of `this` tuple.
 * @property [q] The seventeenth value of `this` tuple.
 * @property [r] The eighteenth value of `this` tuple.
 * @property [s] The nineteenth value of `this` tuple.
 * @property [t] The twentieth value of `this` tuple.
 * @property [u] The twenty first value of `this` tuple.
 * @property [w] The twenty second value of `this` tuple.
 * @property [x] The twenty third value of `this` tuple.
 */
data class Tuple23<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out W, out X>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M, val n: N, val o: O, val p: P, val q: Q, val r: R, val s: S, val t: T, val u: U, val w: W, val x: X) : Serializable, Tuple0 {
    /**
     * Creates a [Tuple24] from the values of `this` tuple, and [that]
     */
    infix fun <Y> to(that: Y): Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X, Y> = Tuple24(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, that)

    /**
     * Passes all the values of `this` tuple to the specified [Tuple23] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U, @ParameterName("w") W, @ParameterName("x") X) -> Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1>): Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U, @ParameterName("w") W, @ParameterName("x") X) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U, @ParameterName("w") W, @ParameterName("x") X) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u, $w, $x)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple23<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u, this.w, this.x)

/**
 * Represents a 24-tuple.
 *
 * A 24-tuple is also known as a quattuorvigintuple.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quattuorvigintuple`s exhibits value semantics, i.e. two `quattuorvigintuple`s are equal if all twenty-four components are equal.
 *
 * @since 0.6.0
 *
 * @constructor Creates a new instance of [Tuple24].
 *
 * @param [a] the first value of the tuple
 * @param [b] the second value of the tuple
 * @param [c] the third value of the tuple
 * @param [d] the fourth value of the tuple
 * @param [e] the fifth value of the tuple
 * @param [f] the sixth value of the tuple
 * @param [g] the seventh value of the tuple
 * @param [h] the eighth value of the tuple
 * @param [i] the ninth value of the tuple
 * @param [j] the tenth value of the tuple
 * @param [k] the eleventh value of the tuple
 * @param [l] the twelfth value of the tuple
 * @param [m] the thirteenth value of the tuple
 * @param [n] the fourteenth value of the tuple
 * @param [o] the fifteenth value of the tuple
 * @param [p] the sixteenth value of the tuple
 * @param [q] the seventeenth value of the tuple
 * @param [r] the eighteenth value of the tuple
 * @param [s] the nineteenth value of the tuple
 * @param [t] the twentieth value of the tuple
 * @param [u] the twenty first value of the tuple
 * @param [w] the twenty second value of the tuple
 * @param [x] the twenty third value of the tuple
 * @param [y] the twenty fourth value of the tuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 * @property [h] The eighth value of `this` tuple.
 * @property [i] The ninth value of `this` tuple.
 * @property [j] The tenth value of `this` tuple.
 * @property [k] The eleventh value of `this` tuple.
 * @property [l] The twelfth value of `this` tuple.
 * @property [m] The thirteenth value of `this` tuple.
 * @property [n] The fourteenth value of `this` tuple.
 * @property [o] The fifteenth value of `this` tuple.
 * @property [p] The sixteenth value of `this` tuple.
 * @property [q] The seventeenth value of `this` tuple.
 * @property [r] The eighteenth value of `this` tuple.
 * @property [s] The nineteenth value of `this` tuple.
 * @property [t] The twentieth value of `this` tuple.
 * @property [u] The twenty first value of `this` tuple.
 * @property [w] The twenty second value of `this` tuple.
 * @property [x] The twenty third value of `this` tuple.
 * @property [y] The twenty fourth value of `this` tuple.
 */
data class Tuple24<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out W, out X, out Y>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J, val k: K, val l: L, val m: M, val n: N, val o: O, val p: P, val q: Q, val r: R, val s: S, val t: T, val u: U, val w: W, val x: X, val y: Y) : Serializable, Tuple0 {
    /**
     * There exists no more tuples after this class.
     *
     * This function is just here to make sure that the user does not start creating a tuple that is deeply nested
     * inside of a [Pair].
     */
    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("There exists no more tuples after this class.", level = DeprecationLevel.ERROR)
    infix fun <Z> to(that: Z): Nothing = throw UnsupportedOperationException()

    /**
     * Passes all the values of `this` tuple to the specified [Tuple24] bearing [transformer] and returns the result
     */
    inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1> flatMap(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U, @ParameterName("w") W, @ParameterName("x") X, @ParameterName("y") Y) -> Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1>): Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, y)

    /**
     * Passes all the values of `this` tuple to the specified [transformer] and returns the result
     */
    inline fun <R1> map(transformer: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U, @ParameterName("w") W, @ParameterName("x") X, @ParameterName("y") Y) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, y)

    /**
     * Returns the result of calling [predicate] with the values of `this` tuple.
     */
    inline fun test(predicate: (@ParameterName("a") A, @ParameterName("b") B, @ParameterName("c") C, @ParameterName("d") D, @ParameterName("e") E, @ParameterName("f") F, @ParameterName("g") G, @ParameterName("h") H, @ParameterName("i") I, @ParameterName("j") J, @ParameterName("k") K, @ParameterName("l") L, @ParameterName("m") M, @ParameterName("n") N, @ParameterName("o") O, @ParameterName("p") P, @ParameterName("q") Q, @ParameterName("r") R, @ParameterName("s") S, @ParameterName("t") T, @ParameterName("u") U, @ParameterName("w") W, @ParameterName("x") X, @ParameterName("y") Y) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, y)

    override fun toString() = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u, $w, $x, $y)"
}

/**
 * Converts `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tuple24<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u, this.w, this.x, this.y)

/**
 * Converts the values of `this` tuple into a map.
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Tuple24<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(this.a to this.b, this.c to this.d, this.e to this.f, this.g to this.h, this.i to this.j, this.k to this.l, this.m to this.n, this.o to this.p, this.q to this.r, this.s to this.t, this.u to this.w, this.x to this.y)
