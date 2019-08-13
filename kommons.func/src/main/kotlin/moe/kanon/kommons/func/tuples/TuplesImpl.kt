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
@file:JvmMultifileClass
@file:JvmName("Tuples")

package moe.kanon.kommons.func.tuples

import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.Pair
import kotlin.String
import kotlin.Triple
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName
import kotlin.jvm.JvmStatic

// AUTO GENERATED, DO NOT EDIT

/**
 * Represents a generic implementation of a [null-tuple][Tuple0].
 */
object Null : Tuple0 {
    override fun equals(other: Any?): Boolean = other is Null

    override fun hashCode(): Int = 0

    override fun toString(): String = "Null"
}

fun tupleOf(): Tuple0 = Null

/**
 * Represents a generic implementation of a [1-tuple][Tuple1].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Singles` exhibits value semantics, i.e. two `Singles` are equal if all one components are equal.
 */
data class Single<out A>(override val a: A) : Tuple1<A> {
    /**
     * Returns a new [tuple][Duad] from the values of `this` tuple and [that].
     */
    override infix fun <B> to(that: B): Tuple2<A, B> = Duad(a, that)

    override fun toString(): String = "($a)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, A1> Tuple1<A>.zipWith(that: Tuple1<A1>): Tuple2<Tuple1<A>, Tuple1<A1>> = this toT that

fun <A> tupleOf(a: A): Tuple1<A> = Single(a)

/**
 * Represents a generic implementation of a [2-tuple][Tuple2].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duads` exhibits value semantics, i.e. two `Duads` are equal if all two components are equal.
 */
data class Duad<out A, out B>(override val a: A, override val b: B) : Tuple2<A, B> {
    /**
     * Returns a new [tuple][Triad] from the values of `this` tuple and [that].
     */
    override infix fun <C> to(that: C): Tuple3<A, B, C> = Triad(a, b, that)

    override fun toString(): String = "($a, $b)"
}

/**
 * Returns a new [tuple][Duad] containing `this` and [that].
 */
infix fun <A, B> A.toT(that: B): Tuple2<A, B> = Duad(this, that)

/**
 * Returns a new [tuple][Duad] containing the values of `this` pair.
 */
fun <A, B> Pair<A, B>.toTuple(): Tuple2<A, B> = Duad(first, second)

/**
 * Returns a new [tuple][Duad] containing the values of `this` pair.
 */
@JvmName("fromPair")
operator fun <A, B> Pair<A, B>.not(): Tuple2<A, B> = toTuple()

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, A1, B1> Tuple2<A, B>.zipWith(that: Tuple2<A1, B1>): Tuple2<Tuple2<A, B>, Tuple2<A1, B1>> = this toT that

fun <A, B> tupleOf(a: A, b: B): Tuple2<A, B> = Duad(a, b)

/**
 * Represents a generic implementation of a [3-tuple][Tuple3].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Triads` exhibits value semantics, i.e. two `Triads` are equal if all three components are equal.
 */
data class Triad<out A, out B, out C>(override val a: A, override val b: B, override val c: C) : Tuple3<A, B, C> {
    /**
     * Returns a new [tuple][Quadruple] from the values of `this` tuple and [that].
     */
    override infix fun <D> to(that: D): Tuple4<A, B, C, D> = Quadruple(a, b, c, that)

    override fun toString(): String = "($a, $b, $c)"
}

/**
 * Returns a new [tuple][Triad] containing the values of `this` triple.
 */
fun <A, B, C> Triple<A, B, C>.toTuple(): Tuple3<A, B, C> = Triad(first, second, third)

/**
 * Returns a new [tuple][Triad] containing the values of `this` triple.
 */
@JvmName("fromTriple")
operator fun <A, B, C> Triple<A, B, C>.not(): Tuple3<A, B, C> = toTuple()

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, A1, B1, C1> Tuple3<A, B, C>.zipWith(that: Tuple3<A1, B1, C1>): Tuple2<Tuple3<A, B, C>, Tuple3<A1, B1, C1>> = this toT that

fun <A, B, C> tupleOf(a: A, b: B, c: C): Tuple3<A, B, C> = Triad(a, b, c)

/**
 * Represents a generic implementation of a [4-tuple][Tuple4].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quadruples` exhibits value semantics, i.e. two `Quadruples` are equal if all four components are equal.
 */
data class Quadruple<out A, out B, out C, out D>(override val a: A, override val b: B, override val c: C, override val d: D) : Tuple4<A, B, C, D> {
    /**
     * Returns a new [tuple][Quintuple] from the values of `this` tuple and [that].
     */
    override infix fun <E> to(that: E): Tuple5<A, B, C, D, E> = Quintuple(a, b, c, d, that)

    override fun toString(): String = "($a, $b, $c, $d)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, A1, B1, C1, D1> Tuple4<A, B, C, D>.zipWith(that: Tuple4<A1, B1, C1, D1>): Tuple2<Tuple4<A, B, C, D>, Tuple4<A1, B1, C1, D1>> = this toT that

fun <A, B, C, D> tupleOf(a: A, b: B, c: C, d: D): Tuple4<A, B, C, D> = Quadruple(a, b, c, d)

/**
 * Represents a generic implementation of a [5-tuple][Tuple5].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quintuples` exhibits value semantics, i.e. two `Quintuples` are equal if all five components are equal.
 */
data class Quintuple<out A, out B, out C, out D, out E>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E) : Tuple5<A, B, C, D, E> {
    /**
     * Returns a new [tuple][Sextuple] from the values of `this` tuple and [that].
     */
    override infix fun <F> to(that: F): Tuple6<A, B, C, D, E, F> = Sextuple(a, b, c, d, e, that)

    override fun toString(): String = "($a, $b, $c, $d, $e)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, A1, B1, C1, D1, E1> Tuple5<A, B, C, D, E>.zipWith(that: Tuple5<A1, B1, C1, D1, E1>): Tuple2<Tuple5<A, B, C, D, E>, Tuple5<A1, B1, C1, D1, E1>> = this toT that

fun <A, B, C, D, E> tupleOf(a: A, b: B, c: C, d: D, e: E): Tuple5<A, B, C, D, E> = Quintuple(a, b, c, d, e)

/**
 * Represents a generic implementation of a [6-tuple][Tuple6].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Sextuples` exhibits value semantics, i.e. two `Sextuples` are equal if all six components are equal.
 */
data class Sextuple<out A, out B, out C, out D, out E, out F>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F) : Tuple6<A, B, C, D, E, F> {
    /**
     * Returns a new [tuple][Septuple] from the values of `this` tuple and [that].
     */
    override infix fun <G> to(that: G): Tuple7<A, B, C, D, E, F, G> = Septuple(a, b, c, d, e, f, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, A1, B1, C1, D1, E1, F1> Tuple6<A, B, C, D, E, F>.zipWith(that: Tuple6<A1, B1, C1, D1, E1, F1>): Tuple2<Tuple6<A, B, C, D, E, F>, Tuple6<A1, B1, C1, D1, E1, F1>> = this toT that

fun <A, B, C, D, E, F> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F): Tuple6<A, B, C, D, E, F> = Sextuple(a, b, c, d, e, f)

/**
 * Represents a generic implementation of a [7-tuple][Tuple7].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Septuples` exhibits value semantics, i.e. two `Septuples` are equal if all seven components are equal.
 */
data class Septuple<out A, out B, out C, out D, out E, out F, out G>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G) : Tuple7<A, B, C, D, E, F, G> {
    /**
     * Returns a new [tuple][Octuple] from the values of `this` tuple and [that].
     */
    override infix fun <H> to(that: H): Tuple8<A, B, C, D, E, F, G, H> = Octuple(a, b, c, d, e, f, g, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, A1, B1, C1, D1, E1, F1, G1> Tuple7<A, B, C, D, E, F, G>.zipWith(that: Tuple7<A1, B1, C1, D1, E1, F1, G1>): Tuple2<Tuple7<A, B, C, D, E, F, G>, Tuple7<A1, B1, C1, D1, E1, F1, G1>> = this toT that

fun <A, B, C, D, E, F, G> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G): Tuple7<A, B, C, D, E, F, G> = Septuple(a, b, c, d, e, f, g)

/**
 * Represents a generic implementation of a [8-tuple][Tuple8].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Octuples` exhibits value semantics, i.e. two `Octuples` are equal if all eight components are equal.
 */
data class Octuple<out A, out B, out C, out D, out E, out F, out G, out H>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H) : Tuple8<A, B, C, D, E, F, G, H> {
    /**
     * Returns a new [tuple][Nonuple] from the values of `this` tuple and [that].
     */
    override infix fun <I> to(that: I): Tuple9<A, B, C, D, E, F, G, H, I> = Nonuple(a, b, c, d, e, f, g, h, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, A1, B1, C1, D1, E1, F1, G1, H1> Tuple8<A, B, C, D, E, F, G, H>.zipWith(that: Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>): Tuple2<Tuple8<A, B, C, D, E, F, G, H>, Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>> = this toT that

fun <A, B, C, D, E, F, G, H> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H): Tuple8<A, B, C, D, E, F, G, H> = Octuple(a, b, c, d, e, f, g, h)

/**
 * Represents a generic implementation of a [9-tuple][Tuple9].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Nonuples` exhibits value semantics, i.e. two `Nonuples` are equal if all nine components are equal.
 */
data class Nonuple<out A, out B, out C, out D, out E, out F, out G, out H, out I>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I) : Tuple9<A, B, C, D, E, F, G, H, I> {
    /**
     * Returns a new [tuple][Decuple] from the values of `this` tuple and [that].
     */
    override infix fun <J> to(that: J): Tuple10<A, B, C, D, E, F, G, H, I, J> = Decuple(a, b, c, d, e, f, g, h, i, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, A1, B1, C1, D1, E1, F1, G1, H1, I1> Tuple9<A, B, C, D, E, F, G, H, I>.zipWith(that: Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>): Tuple2<Tuple9<A, B, C, D, E, F, G, H, I>, Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>> = this toT that

fun <A, B, C, D, E, F, G, H, I> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I): Tuple9<A, B, C, D, E, F, G, H, I> = Nonuple(a, b, c, d, e, f, g, h, i)

/**
 * Represents a generic implementation of a [10-tuple][Tuple10].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Decuples` exhibits value semantics, i.e. two `Decuples` are equal if all ten components are equal.
 */
data class Decuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J) : Tuple10<A, B, C, D, E, F, G, H, I, J> {
    /**
     * Returns a new [tuple][Undecuple] from the values of `this` tuple and [that].
     */
    override infix fun <K> to(that: K): Tuple11<A, B, C, D, E, F, G, H, I, J, K> = Undecuple(a, b, c, d, e, f, g, h, i, j, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1> Tuple10<A, B, C, D, E, F, G, H, I, J>.zipWith(that: Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>): Tuple2<Tuple10<A, B, C, D, E, F, G, H, I, J>, Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J): Tuple10<A, B, C, D, E, F, G, H, I, J> = Decuple(a, b, c, d, e, f, g, h, i, j)

/**
 * Represents a generic implementation of a [11-tuple][Tuple11].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Undecuples` exhibits value semantics, i.e. two `Undecuples` are equal if all eleven components are equal.
 */
data class Undecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K) : Tuple11<A, B, C, D, E, F, G, H, I, J, K> {
    /**
     * Returns a new [tuple][Duodecuple] from the values of `this` tuple and [that].
     */
    override infix fun <L> to(that: L): Tuple12<A, B, C, D, E, F, G, H, I, J, K, L> = Duodecuple(a, b, c, d, e, f, g, h, i, j, k, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1> Tuple11<A, B, C, D, E, F, G, H, I, J, K>.zipWith(that: Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>): Tuple2<Tuple11<A, B, C, D, E, F, G, H, I, J, K>, Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K): Tuple11<A, B, C, D, E, F, G, H, I, J, K> = Undecuple(a, b, c, d, e, f, g, h, i, j, k)

/**
 * Represents a generic implementation of a [12-tuple][Tuple12].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duodecuples` exhibits value semantics, i.e. two `Duodecuples` are equal if all twelve components are equal.
 */
data class Duodecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L) : Tuple12<A, B, C, D, E, F, G, H, I, J, K, L> {
    /**
     * Returns a new [tuple][Tredecuple] from the values of `this` tuple and [that].
     */
    override infix fun <M> to(that: M): Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M> = Tredecuple(a, b, c, d, e, f, g, h, i, j, k, l, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1> Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>.zipWith(that: Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>): Tuple2<Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>, Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L): Tuple12<A, B, C, D, E, F, G, H, I, J, K, L> = Duodecuple(a, b, c, d, e, f, g, h, i, j, k, l)

/**
 * Represents a generic implementation of a [13-tuple][Tuple13].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Tredecuples` exhibits value semantics, i.e. two `Tredecuples` are equal if all thirteen components are equal.
 */
data class Tredecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M) : Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M> {
    /**
     * Returns a new [tuple][Quattuordecuple] from the values of `this` tuple and [that].
     */
    override infix fun <N> to(that: N): Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N> = Quattuordecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1> Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>.zipWith(that: Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>): Tuple2<Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>, Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M): Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M> = Tredecuple(a, b, c, d, e, f, g, h, i, j, k, l, m)

/**
 * Represents a generic implementation of a [14-tuple][Tuple14].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quattuordecuples` exhibits value semantics, i.e. two `Quattuordecuples` are equal if all fourteen components are equal.
 */
data class Quattuordecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N) : Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N> {
    /**
     * Returns a new [tuple][Quindecuple] from the values of `this` tuple and [that].
     */
    override infix fun <O> to(that: O): Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = Quindecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1> Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>.zipWith(that: Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>): Tuple2<Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>, Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N): Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N> = Quattuordecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

/**
 * Represents a generic implementation of a [15-tuple][Tuple15].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quindecuples` exhibits value semantics, i.e. two `Quindecuples` are equal if all fifteen components are equal.
 */
data class Quindecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O) : Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> {
    /**
     * Returns a new [tuple][Sexdecuple] from the values of `this` tuple and [that].
     */
    override infix fun <P> to(that: P): Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = Sexdecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1> Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>.zipWith(that: Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>): Tuple2<Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>, Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O): Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = Quindecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

/**
 * Represents a generic implementation of a [16-tuple][Tuple16].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Sexdecuples` exhibits value semantics, i.e. two `Sexdecuples` are equal if all sixteen components are equal.
 */
data class Sexdecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P) : Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> {
    /**
     * Returns a new [tuple][Septendecuple] from the values of `this` tuple and [that].
     */
    override infix fun <Q> to(that: Q): Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> = Septendecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1> Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>.zipWith(that: Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>): Tuple2<Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>, Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P): Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = Sexdecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

/**
 * Represents a generic implementation of a [17-tuple][Tuple17].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Septendecuples` exhibits value semantics, i.e. two `Septendecuples` are equal if all seventeen components are equal.
 */
data class Septendecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q) : Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> {
    /**
     * Returns a new [tuple][Octodecuple] from the values of `this` tuple and [that].
     */
    override infix fun <R> to(that: R): Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = Octodecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1> Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>.zipWith(that: Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>): Tuple2<Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>, Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q): Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> = Septendecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

/**
 * Represents a generic implementation of a [18-tuple][Tuple18].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Octodecuples` exhibits value semantics, i.e. two `Octodecuples` are equal if all eighteen components are equal.
 */
data class Octodecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R) : Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> {
    /**
     * Returns a new [tuple][Novemdecuple] from the values of `this` tuple and [that].
     */
    override infix fun <S> to(that: S): Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = Novemdecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1> Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>.zipWith(that: Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>): Tuple2<Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>, Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R): Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = Octodecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

/**
 * Represents a generic implementation of a [19-tuple][Tuple19].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Novemdecuples` exhibits value semantics, i.e. two `Novemdecuples` are equal if all nineteen components are equal.
 */
data class Novemdecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S) : Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> {
    /**
     * Returns a new [tuple][Vigintuple] from the values of `this` tuple and [that].
     */
    override infix fun <T> to(that: T): Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> = Vigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1> Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>.zipWith(that: Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>): Tuple2<Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>, Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S): Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = Novemdecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

/**
 * Represents a generic implementation of a [20-tuple][Tuple20].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Vigintuples` exhibits value semantics, i.e. two `Vigintuples` are equal if all twenty components are equal.
 */
data class Vigintuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S, override val t: T) : Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> {
    /**
     * Returns a new [tuple][Unvigintuple] from the values of `this` tuple and [that].
     */
    override infix fun <U> to(that: U): Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> = Unvigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1> Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>.zipWith(that: Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>): Tuple2<Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>, Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T): Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> = Vigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

/**
 * Represents a generic implementation of a [21-tuple][Tuple21].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Unvigintuples` exhibits value semantics, i.e. two `Unvigintuples` are equal if all twenty-one components are equal.
 */
data class Unvigintuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S, override val t: T, override val u: U) : Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> {
    /**
     * Returns a new [tuple][Duovigintuple] from the values of `this` tuple and [that].
     */
    override infix fun <V> to(that: V): Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> = Duovigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1> Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>.zipWith(that: Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>): Tuple2<Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>, Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U): Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> = Unvigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

/**
 * Represents a generic implementation of a [22-tuple][Tuple22].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duovigintuples` exhibits value semantics, i.e. two `Duovigintuples` are equal if all twenty-two components are equal.
 */
data class Duovigintuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out V>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S, override val t: T, override val u: U, override val v: V) : Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> {
    /**
     * Returns a new [tuple][Trevigintuple] from the values of `this` tuple and [that].
     */
    override infix fun <W> to(that: W): Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W> = Trevigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u, $v)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1> Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V>.zipWith(that: Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1>): Tuple2<Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V>, Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, v: V): Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> = Duovigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v)

/**
 * Represents a generic implementation of a [23-tuple][Tuple23].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Trevigintuples` exhibits value semantics, i.e. two `Trevigintuples` are equal if all twenty-three components are equal.
 */
data class Trevigintuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out V, out W>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S, override val t: T, override val u: U, override val v: V, override val w: W) : Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W> {
    /**
     * Returns a new [tuple][Quattuorvigintuple] from the values of `this` tuple and [that].
     */
    override infix fun <X> to(that: X): Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X> = Quattuorvigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, that)

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u, $v, $w)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, W1> Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W>.zipWith(that: Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, W1>): Tuple2<Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W>, Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, W1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, v: V, w: W): Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W> = Trevigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w)

/**
 * Represents a generic implementation of a [24-tuple][Tuple24].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quattuorvigintuples` exhibits value semantics, i.e. two `Quattuorvigintuples` are equal if all twenty-four components are equal.
 */
data class Quattuorvigintuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out V, out W, out X>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S, override val t: T, override val u: U, override val v: V, override val w: W, override val x: X) : Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X> {
    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u, $v, $w, $x)"
}

/**
 * Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
 */
infix fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, W1, X1> Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X>.zipWith(that: Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, W1, X1>): Tuple2<Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X>, Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, W1, X1>> = this toT that

fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X> tupleOf(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, v: V, w: W, x: X): Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X> = Quattuorvigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x)

fun tupleOf(vararg objects: Any?): TupleN = TupleN(*objects)

object Tuple {
    /**
     * Returns a new [tuple][Tuple1] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A> invoke(a: A): Tuple1<A> = Single(a)

    /**
     * Returns a new [tuple][Tuple2] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B> invoke(a: A, b: B): Tuple2<A, B> = Duad(a, b)

    /**
     * Returns a new [tuple][Tuple3] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C> invoke(a: A, b: B, c: C): Tuple3<A, B, C> = Triad(a, b, c)

    /**
     * Returns a new [tuple][Tuple4] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D> invoke(a: A, b: B, c: C, d: D): Tuple4<A, B, C, D> = Quadruple(a, b, c, d)

    /**
     * Returns a new [tuple][Tuple5] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E> invoke(a: A, b: B, c: C, d: D, e: E): Tuple5<A, B, C, D, E> = Quintuple(a, b, c, d, e)

    /**
     * Returns a new [tuple][Tuple6] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F> invoke(a: A, b: B, c: C, d: D, e: E, f: F): Tuple6<A, B, C, D, E, F> = Sextuple(a, b, c, d, e, f)

    /**
     * Returns a new [tuple][Tuple7] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G): Tuple7<A, B, C, D, E, F, G> = Septuple(a, b, c, d, e, f, g)

    /**
     * Returns a new [tuple][Tuple8] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H): Tuple8<A, B, C, D, E, F, G, H> = Octuple(a, b, c, d, e, f, g, h)

    /**
     * Returns a new [tuple][Tuple9] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I): Tuple9<A, B, C, D, E, F, G, H, I> = Nonuple(a, b, c, d, e, f, g, h, i)

    /**
     * Returns a new [tuple][Tuple10] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J): Tuple10<A, B, C, D, E, F, G, H, I, J> = Decuple(a, b, c, d, e, f, g, h, i, j)

    /**
     * Returns a new [tuple][Tuple11] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K): Tuple11<A, B, C, D, E, F, G, H, I, J, K> = Undecuple(a, b, c, d, e, f, g, h, i, j, k)

    /**
     * Returns a new [tuple][Tuple12] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L): Tuple12<A, B, C, D, E, F, G, H, I, J, K, L> = Duodecuple(a, b, c, d, e, f, g, h, i, j, k, l)

    /**
     * Returns a new [tuple][Tuple13] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M): Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M> = Tredecuple(a, b, c, d, e, f, g, h, i, j, k, l, m)

    /**
     * Returns a new [tuple][Tuple14] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N): Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N> = Quattuordecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

    /**
     * Returns a new [tuple][Tuple15] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O): Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = Quindecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

    /**
     * Returns a new [tuple][Tuple16] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P): Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = Sexdecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

    /**
     * Returns a new [tuple][Tuple17] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q): Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> = Septendecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

    /**
     * Returns a new [tuple][Tuple18] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R): Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = Octodecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

    /**
     * Returns a new [tuple][Tuple19] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S): Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = Novemdecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

    /**
     * Returns a new [tuple][Tuple20] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T): Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> = Vigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

    /**
     * Returns a new [tuple][Tuple21] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U): Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> = Unvigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

    /**
     * Returns a new [tuple][Tuple22] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, v: V): Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> = Duovigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v)

    /**
     * Returns a new [tuple][Tuple23] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, v: V, w: W): Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W> = Trevigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w)

    /**
     * Returns a new [tuple][Tuple24] containing the specified values.
     */
    @JvmStatic
    @JvmName("of")
    fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, v: V, w: W, x: X): Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X> = Quattuorvigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x)

    /**
     * Returns a new [tuple][TupleN] containing the specified [values].
     *
     * Note that unlike the other factory functions in here, this function returns a [TupleN] rather than
     * a concrete tuple implementation.
     */
    @JvmStatic
    @JvmName("of")
    operator fun invoke(vararg values: Any?): TupleN = TupleN(*values)
}

