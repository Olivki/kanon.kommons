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

/*
 * This file contains 24 abstract implementations of Tuples (25 if you count Tuple0). Each interface comes with utility
 * functions, and are made to be used in scenarios where one may want similar behaviour across vastly different classes,
 * mainly data classes.
 *      Everything except the Tuple0 interface in this file has been automatically generated.
 */

/**
 * Represents a `null-tuple`.
 *
 * This is generally just used as a *marker* interface for the generated tuple interfaces.
 */
interface Tuple0

typealias NullTuple = Tuple0

typealias Serializable = java.io.Serializable

typealias Couple<A, B> = Duad<A, B>

typealias Triplet<A, B, C> = Triad<A, B, C>

annotation class TupleClass

// Everything below is auto-generated
/**
 * Represents an abstract implementation of a 1-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Single].
 *
 * @since 0.6.0
 *
 * @see Single
 *
 * @property [a] The first value of `this` tuple.
 */
interface Tuple1<out A> : Serializable, Tuple0 {
    val a: A

    /**
     * Creates a [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <B> to(that: B): Tuple2<A, B>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1> flatMap(transformer: (a: A) -> Tuple1<A1>): Tuple1<A1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1> zip(that: Tuple1<A1>): Tuple2<Tuple1<A>, Tuple1<A1>>

    operator fun component1(): A

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 2-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Duad].
 *
 * @since 0.6.0
 *
 * @see Duad
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 */
interface Tuple2<out A, out B> : Serializable, Tuple0 {
    val a: A
    val b: B

    /**
     * Creates a [tuple][Tuple3] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <C> to(that: C): Tuple3<A, B, C>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1> flatMap(transformer: (a: A, b: B) -> Tuple2<A1, B1>): Tuple2<A1, B1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1> zip(that: Tuple2<A1, B1>): Tuple2<Tuple2<A, B>, Tuple2<A1, B1>>

    /**
     * Converts `this` tuple into a [pair][Pair].
     */
    @JvmDefault
    fun toPair(): Pair<A, B> = Pair(a, b)

    /**
     * Converts `this` tuple into a [pair][Pair].
     */
    @JvmDefault
    @JvmSynthetic
    @Suppress("NOTHING_TO_INLINE")
    operator fun not(): Pair<A, B> = toPair()

    operator fun component1(): A

    operator fun component2(): B

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 3-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Triad].
 *
 * @since 0.6.0
 *
 * @see Triad
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 */
interface Tuple3<out A, out B, out C> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C

    /**
     * Creates a [tuple][Tuple4] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <D> to(that: D): Tuple4<A, B, C, D>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1> flatMap(transformer: (a: A, b: B, c: C) -> Tuple3<A1, B1, C1>): Tuple3<A1, B1, C1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1> zip(that: Tuple3<A1, B1, C1>): Tuple2<Tuple3<A, B, C>, Tuple3<A1, B1, C1>>

    /**
     * Converts `this` tuple into a [triple][Triple].
     */
    @JvmDefault
    fun toTriple(): Triple<A, B, C> = Triple(a, b, c)

    /**
     * Converts `this` tuple into a [triple][Triple].
     */
    @JvmDefault
    @JvmSynthetic
    @Suppress("NOTHING_TO_INLINE")
    operator fun not(): Triple<A, B, C> = toTriple()

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 4-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Quadruple].
 *
 * @since 0.6.0
 *
 * @see Quadruple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 */
interface Tuple4<out A, out B, out C, out D> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D

    /**
     * Creates a [tuple][Tuple5] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <E> to(that: E): Tuple5<A, B, C, D, E>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1> flatMap(transformer: (a: A, b: B, c: C, d: D) -> Tuple4<A1, B1, C1, D1>): Tuple4<A1, B1, C1, D1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1> zip(that: Tuple4<A1, B1, C1, D1>): Tuple2<Tuple4<A, B, C, D>, Tuple4<A1, B1, C1, D1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 5-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Quintuple].
 *
 * @since 0.6.0
 *
 * @see Quintuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 */
interface Tuple5<out A, out B, out C, out D, out E> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E

    /**
     * Creates a [tuple][Tuple6] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <F> to(that: F): Tuple6<A, B, C, D, E, F>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E) -> Tuple5<A1, B1, C1, D1, E1>): Tuple5<A1, B1, C1, D1, E1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1> zip(that: Tuple5<A1, B1, C1, D1, E1>): Tuple2<Tuple5<A, B, C, D, E>, Tuple5<A1, B1, C1, D1, E1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 6-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Sextuple].
 *
 * @since 0.6.0
 *
 * @see Sextuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 */
interface Tuple6<out A, out B, out C, out D, out E, out F> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F

    /**
     * Creates a [tuple][Tuple7] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <G> to(that: G): Tuple7<A, B, C, D, E, F, G>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F) -> Tuple6<A1, B1, C1, D1, E1, F1>): Tuple6<A1, B1, C1, D1, E1, F1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1> zip(that: Tuple6<A1, B1, C1, D1, E1, F1>): Tuple2<Tuple6<A, B, C, D, E, F>, Tuple6<A1, B1, C1, D1, E1, F1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 7-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Septuple].
 *
 * @since 0.6.0
 *
 * @see Septuple
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 */
interface Tuple7<out A, out B, out C, out D, out E, out F, out G> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G

    /**
     * Creates a [tuple][Tuple8] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <H> to(that: H): Tuple8<A, B, C, D, E, F, G, H>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G) -> Tuple7<A1, B1, C1, D1, E1, F1, G1>): Tuple7<A1, B1, C1, D1, E1, F1, G1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1> zip(that: Tuple7<A1, B1, C1, D1, E1, F1, G1>): Tuple2<Tuple7<A, B, C, D, E, F, G>, Tuple7<A1, B1, C1, D1, E1, F1, G1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 8-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Octuple].
 *
 * @since 0.6.0
 *
 * @see Octuple
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
interface Tuple8<out A, out B, out C, out D, out E, out F, out G, out H> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H

    /**
     * Creates a [tuple][Tuple9] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <I> to(that: I): Tuple9<A, B, C, D, E, F, G, H, I>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H) -> Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>): Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1> zip(that: Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>): Tuple2<Tuple8<A, B, C, D, E, F, G, H>, Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 9-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Nonuple].
 *
 * @since 0.6.0
 *
 * @see Nonuple
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
interface Tuple9<out A, out B, out C, out D, out E, out F, out G, out H, out I> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I

    /**
     * Creates a [tuple][Tuple10] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <J> to(that: J): Tuple10<A, B, C, D, E, F, G, H, I, J>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I) -> Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>): Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1> zip(that: Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>): Tuple2<Tuple9<A, B, C, D, E, F, G, H, I>, Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 10-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Decuple].
 *
 * @since 0.6.0
 *
 * @see Decuple
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
interface Tuple10<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J

    /**
     * Creates a [tuple][Tuple11] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <K> to(that: K): Tuple11<A, B, C, D, E, F, G, H, I, J, K>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J) -> Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>): Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1> zip(that: Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>): Tuple2<Tuple10<A, B, C, D, E, F, G, H, I, J>, Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 11-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Undecuple].
 *
 * @since 0.6.0
 *
 * @see Undecuple
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
interface Tuple11<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K

    /**
     * Creates a [tuple][Tuple12] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <L> to(that: L): Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K) -> Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>): Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1> zip(that: Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>): Tuple2<Tuple11<A, B, C, D, E, F, G, H, I, J, K>, Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 12-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Duodecuple].
 *
 * @since 0.6.0
 *
 * @see Duodecuple
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
interface Tuple12<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L

    /**
     * Creates a [tuple][Tuple13] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <M> to(that: M): Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L) -> Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>): Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1> zip(that: Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>): Tuple2<Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>, Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 13-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Tredecuple].
 *
 * @since 0.6.0
 *
 * @see Tredecuple
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
interface Tuple13<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M

    /**
     * Creates a [tuple][Tuple14] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <N> to(that: N): Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M) -> Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>): Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1> zip(that: Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>): Tuple2<Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>, Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 14-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Quattuordecuple].
 *
 * @since 0.6.0
 *
 * @see Quattuordecuple
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
interface Tuple14<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M
    val n: N

    /**
     * Creates a [tuple][Tuple15] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <O> to(that: O): Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N) -> Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>): Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1> zip(that: Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>): Tuple2<Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>, Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    operator fun component14(): N

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 15-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Quindecuple].
 *
 * @since 0.6.0
 *
 * @see Quindecuple
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
interface Tuple15<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M
    val n: N
    val o: O

    /**
     * Creates a [tuple][Tuple16] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <P> to(that: P): Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O) -> Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>): Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1> zip(that: Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>): Tuple2<Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>, Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    operator fun component14(): N

    operator fun component15(): O

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 16-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Sexdecuple].
 *
 * @since 0.6.0
 *
 * @see Sexdecuple
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
interface Tuple16<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M
    val n: N
    val o: O
    val p: P

    /**
     * Creates a [tuple][Tuple17] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <Q> to(that: Q): Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P) -> Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>): Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1> zip(that: Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>): Tuple2<Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>, Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    operator fun component14(): N

    operator fun component15(): O

    operator fun component16(): P

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 17-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Septendecuple].
 *
 * @since 0.6.0
 *
 * @see Septendecuple
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
interface Tuple17<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M
    val n: N
    val o: O
    val p: P
    val q: Q

    /**
     * Creates a [tuple][Tuple18] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <R> to(that: R): Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q) -> Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>): Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1> zip(that: Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>): Tuple2<Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>, Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    operator fun component14(): N

    operator fun component15(): O

    operator fun component16(): P

    operator fun component17(): Q

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 18-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Octodecuple].
 *
 * @since 0.6.0
 *
 * @see Octodecuple
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
interface Tuple18<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M
    val n: N
    val o: O
    val p: P
    val q: Q
    val r: R

    /**
     * Creates a [tuple][Tuple19] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <S> to(that: S): Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R) -> Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>): Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1> zip(that: Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>): Tuple2<Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>, Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    operator fun component14(): N

    operator fun component15(): O

    operator fun component16(): P

    operator fun component17(): Q

    operator fun component18(): R

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 19-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Novemdecuple].
 *
 * @since 0.6.0
 *
 * @see Novemdecuple
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
interface Tuple19<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M
    val n: N
    val o: O
    val p: P
    val q: Q
    val r: R
    val s: S

    /**
     * Creates a [tuple][Tuple20] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <T> to(that: T): Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S) -> Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>): Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1> zip(that: Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>): Tuple2<Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>, Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    operator fun component14(): N

    operator fun component15(): O

    operator fun component16(): P

    operator fun component17(): Q

    operator fun component18(): R

    operator fun component19(): S

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 20-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Vigintuple].
 *
 * @since 0.6.0
 *
 * @see Vigintuple
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
interface Tuple20<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M
    val n: N
    val o: O
    val p: P
    val q: Q
    val r: R
    val s: S
    val t: T

    /**
     * Creates a [tuple][Tuple21] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <U> to(that: U): Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T) -> Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>): Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1> zip(that: Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>): Tuple2<Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>, Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    operator fun component14(): N

    operator fun component15(): O

    operator fun component16(): P

    operator fun component17(): Q

    operator fun component18(): R

    operator fun component19(): S

    operator fun component20(): T

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 21-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Unvigintuple].
 *
 * @since 0.6.0
 *
 * @see Unvigintuple
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
interface Tuple21<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M
    val n: N
    val o: O
    val p: P
    val q: Q
    val r: R
    val s: S
    val t: T
    val u: U

    /**
     * Creates a [tuple][Tuple22] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <W> to(that: W): Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U) -> Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>): Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1> zip(that: Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>): Tuple2<Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>, Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    operator fun component14(): N

    operator fun component15(): O

    operator fun component16(): P

    operator fun component17(): Q

    operator fun component18(): R

    operator fun component19(): S

    operator fun component20(): T

    operator fun component21(): U

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 22-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Duovigintuple].
 *
 * @since 0.6.0
 *
 * @see Duovigintuple
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
interface Tuple22<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out W> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M
    val n: N
    val o: O
    val p: P
    val q: Q
    val r: R
    val s: S
    val t: T
    val u: U
    val w: W

    /**
     * Creates a [tuple][Tuple23] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <X> to(that: X): Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W) -> Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1>): Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1> zip(that: Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1>): Tuple2<Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W>, Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    operator fun component14(): N

    operator fun component15(): O

    operator fun component16(): P

    operator fun component17(): Q

    operator fun component18(): R

    operator fun component19(): S

    operator fun component20(): T

    operator fun component21(): U

    operator fun component22(): W

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 23-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Trevigintuple].
 *
 * @since 0.6.0
 *
 * @see Trevigintuple
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
interface Tuple23<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out W, out X> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M
    val n: N
    val o: O
    val p: P
    val q: Q
    val r: R
    val s: S
    val t: T
    val u: U
    val w: W
    val x: X

    /**
     * Creates a [tuple][Tuple24] from the values of `this` tuple and [that].
     *
     * Whether or not this function is supported is implementation specific, and a class that implements `this`
     * tuple may choose to not provide an implementation.
     */
    infix fun <Y> to(that: Y): Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X, Y>

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X) -> Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1>): Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1> zip(that: Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1>): Tuple2<Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X>, Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    operator fun component14(): N

    operator fun component15(): O

    operator fun component16(): P

    operator fun component17(): Q

    operator fun component18(): R

    operator fun component19(): S

    operator fun component20(): T

    operator fun component21(): U

    operator fun component22(): W

    operator fun component23(): X

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}

/**
 * Represents an abstract implementation of a 24-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * For a generic implementation, see [Quattuorvigintuple].
 *
 * @since 0.6.0
 *
 * @see Quattuorvigintuple
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
interface Tuple24<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out W, out X, out Y> : Serializable, Tuple0 {
    val a: A
    val b: B
    val c: C
    val d: D
    val e: E
    val f: F
    val g: G
    val h: H
    val i: I
    val j: J
    val k: K
    val l: L
    val m: M
    val n: N
    val o: O
    val p: P
    val q: Q
    val r: R
    val s: S
    val t: T
    val u: U
    val w: W
    val x: X
    val y: Y

    /**
     * There exists no more tuples after this class.
     *
     * This function is just here to make sure that the user does not start creating a tuple that is deeply nested
     * inside of a [Pair].
     */
    @JvmDefault
    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("There exists no more tuples after this class.", level = DeprecationLevel.ERROR)
    infix fun <Z> to(that: Z): Nothing = throw UnsupportedOperationException()

    /**
     * Returns the result of applying the values of `this` tuple to the [transformer].
     */
    fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X, y: Y) -> R1): R1

    /**
     * Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].
     */
    fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X, y: Y) -> Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1>): Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1>

    /**
     * Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.
     */
    fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X, y: Y) -> Boolean): Boolean

    /**
     * Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.
     */
    fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X, y: Y) -> Boolean): Boolean

    /**
     * Creates a [tuple][Tuple2] that contains `this` tuple and [that] tuple.
     */
    infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1> zip(that: Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1>): Tuple2<Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X, Y>, Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1>>

    operator fun component1(): A

    operator fun component2(): B

    operator fun component3(): C

    operator fun component4(): D

    operator fun component5(): E

    operator fun component6(): F

    operator fun component7(): G

    operator fun component8(): H

    operator fun component9(): I

    operator fun component10(): J

    operator fun component11(): K

    operator fun component12(): L

    operator fun component13(): M

    operator fun component14(): N

    operator fun component15(): O

    operator fun component16(): P

    operator fun component17(): Q

    operator fun component18(): R

    operator fun component19(): S

    operator fun component20(): T

    operator fun component21(): U

    operator fun component22(): W

    operator fun component23(): X

    operator fun component24(): Y

    override fun toString(): String

    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}