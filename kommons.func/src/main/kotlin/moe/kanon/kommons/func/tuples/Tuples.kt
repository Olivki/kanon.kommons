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

import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Deprecated
import kotlin.Pair
import kotlin.Suppress
import kotlin.Triple
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.jvm.JvmDefault
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName
import moe.kanon.kommons.Identifiable

// AUTO GENERATED, DO NOT EDIT

/*
 * The auto-generated tuple classes all explicitly define some utility like functions inside of them, one of these is
 * the 'to' function. The reason this function is explicitly defined inside of each tuple class rather than having
 * them be 'extension' functions like they are in the standard library is to circumvent the 'to' infix function
 * defined by the standard library.
 *      Due to how the standard library works (all of the std-lib is always automatically imported in all classes) we
 * can't reliably define a extension function called 'to', as the chances are very high that the 'to' extension
 * function defined for creating 'Pair' instances will win the resolution battle. As that function also does the work
 * on two generic parts, that means that unless we explicitly defined the 'to' function inside each tuple, we would
 * need to have to result to doing something like 'toT' *(which is what we use to create the initial 'Duad' instance)*
 * for each tuple instance to keep on chaining them, or use a different syntax all-together. But if we explicitly
 * define them as a member of the tuple class we win the resolution battle, this is because extension functions are
 * resolved statically and are not actual parts of the classes they work on. This means that an extension function
 * will *always* lose the resolution battle against an actual member of the class.
 *      The equivalent tuple classes for 'Pair' and 'Triple' *('Tuple2/Duad' and 'Tuple3/Triad' respectively)* have also
 * been given functions for converting to their std-lib implementations to somewhat ease compatibility. They have also
 * been given the 'not' operator to make the syntax even cleaner, this operator is entirely optional, and should only
 * be used if you are sure that using it would not create reading issues, as it is a very subtle operator. There also
 * exists extension functions for 'Pair' and 'Triple' to convert them to their respective kommons implementations,
 * these also have the 'not' operator available for them.
 */

/**
 * A marker for all the available tuple implementations.
 */
interface TupleMarker

/**
 * Represents a `null` tuple.
 *
 * A `null` tuple does not hold any values.
 *
 * @see [Null]
 */
interface Tuple0 : Serializable, Identifiable, TupleMarker

/**
 * Represents an abstract implementation of a 1-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Single]
 *
 * @property [a] The first value of `this` tuple.
 */
interface Tuple1<out A> : Serializable, Identifiable, TupleMarker {
    val a: A

    /**
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <B> to(that: B): Tuple2<A, B> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B> Tuple1<A>.plus(that: B): Tuple2<A, B> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple1<T>.toList(): List<T> = listOf(a)

/**
 * Represents an abstract implementation of a 2-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Duad]
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 */
interface Tuple2<out A, out B> : Serializable, Identifiable, TupleMarker {
    val a: A

    val b: B

    /**
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <C> to(that: C): Tuple3<A, B, C> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b
}

/**
 * Returns a new [Pair] based on the values of `this` tuple.
 */
fun <A, B> Tuple2<A, B>.toPair(): Pair<A, B> = Pair(a, b)

/**
 * Returns a new [Pair] based on the values of `this` tuple.
 */
@JvmName("toKotlin")
operator fun <A, B> Tuple2<A, B>.not(): Pair<A, B> = toPair()

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C> Tuple2<A, B>.plus(that: C): Tuple3<A, B, C> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple2<T, T>.toList(): List<T> = listOf(a, b)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple2<K, V>.toMap(): Map<K, V> = mapOf(a to b)

/**
 * Represents an abstract implementation of a 3-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Triad]
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 */
interface Tuple3<out A, out B, out C> : Serializable, Identifiable, TupleMarker {
    val a: A

    val b: B

    val c: C

    /**
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <D> to(that: D): Tuple4<A, B, C, D> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c
}

/**
 * Returns a new [Triple] based on the values of `this` tuple.
 */
fun <A, B, C> Tuple3<A, B, C>.toTriple(): Triple<A, B, C> = Triple(a, b, c)

/**
 * Returns a new [Triple] based on the values of `this` tuple.
 */
@JvmName("toKotlin")
operator fun <A, B, C> Tuple3<A, B, C>.not(): Triple<A, B, C> = toTriple()

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D> Tuple3<A, B, C>.plus(that: D): Tuple4<A, B, C, D> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple3<T, T, T>.toList(): List<T> = listOf(a, b, c)

/**
 * Represents an abstract implementation of a 4-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Quadruple]
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 */
interface Tuple4<out A, out B, out C, out D> : Serializable, Identifiable, TupleMarker {
    val a: A

    val b: B

    val c: C

    val d: D

    /**
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <E> to(that: E): Tuple5<A, B, C, D, E> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E> Tuple4<A, B, C, D>.plus(that: E): Tuple5<A, B, C, D, E> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple4<T, T, T, T>.toList(): List<T> = listOf(a, b, c, d)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple4<K, V, K, V>.toMap(): Map<K, V> = mapOf(a to b, c to d)

/**
 * Represents an abstract implementation of a 5-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Quintuple]
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 */
interface Tuple5<out A, out B, out C, out D, out E> : Serializable, Identifiable, TupleMarker {
    val a: A

    val b: B

    val c: C

    val d: D

    val e: E

    /**
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <F> to(that: F): Tuple6<A, B, C, D, E, F> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F> Tuple5<A, B, C, D, E>.plus(that: F): Tuple6<A, B, C, D, E, F> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple5<T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e)

/**
 * Represents an abstract implementation of a 6-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Sextuple]
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 */
interface Tuple6<out A, out B, out C, out D, out E, out F> : Serializable, Identifiable, TupleMarker {
    val a: A

    val b: B

    val c: C

    val d: D

    val e: E

    val f: F

    /**
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <G> to(that: G): Tuple7<A, B, C, D, E, F, G> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G> Tuple6<A, B, C, D, E, F>.plus(that: G): Tuple7<A, B, C, D, E, F, G> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple6<T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple6<K, V, K, V, K, V>.toMap(): Map<K, V> = mapOf(a to b, c to d, e to f)

/**
 * Represents an abstract implementation of a 7-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Septuple]
 *
 * @property [a] The first value of `this` tuple.
 * @property [b] The second value of `this` tuple.
 * @property [c] The third value of `this` tuple.
 * @property [d] The fourth value of `this` tuple.
 * @property [e] The fifth value of `this` tuple.
 * @property [f] The sixth value of `this` tuple.
 * @property [g] The seventh value of `this` tuple.
 */
interface Tuple7<out A, out B, out C, out D, out E, out F, out G> : Serializable, Identifiable, TupleMarker {
    val a: A

    val b: B

    val c: C

    val d: D

    val e: E

    val f: F

    val g: G

    /**
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <H> to(that: H): Tuple8<A, B, C, D, E, F, G, H> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H> Tuple7<A, B, C, D, E, F, G>.plus(that: H): Tuple8<A, B, C, D, E, F, G, H> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple7<T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g)

/**
 * Represents an abstract implementation of a 8-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Octuple]
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
interface Tuple8<out A, out B, out C, out D, out E, out F, out G, out H> : Serializable, Identifiable, TupleMarker {
    val a: A

    val b: B

    val c: C

    val d: D

    val e: E

    val f: F

    val g: G

    val h: H

    /**
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <I> to(that: I): Tuple9<A, B, C, D, E, F, G, H, I> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I> Tuple8<A, B, C, D, E, F, G, H>.plus(that: I): Tuple9<A, B, C, D, E, F, G, H, I> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple8<T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple8<K, V, K, V, K, V, K, V>.toMap(): Map<K, V> = mapOf(a to b, c to d, e to f, g to h)

/**
 * Represents an abstract implementation of a 9-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Nonuple]
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
interface Tuple9<out A, out B, out C, out D, out E, out F, out G, out H, out I> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <J> to(that: J): Tuple10<A, B, C, D, E, F, G, H, I, J> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J> Tuple9<A, B, C, D, E, F, G, H, I>.plus(that: J): Tuple10<A, B, C, D, E, F, G, H, I, J> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple9<T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i)

/**
 * Represents an abstract implementation of a 10-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Decuple]
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
interface Tuple10<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <K> to(that: K): Tuple11<A, B, C, D, E, F, G, H, I, J, K> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K> Tuple10<A, B, C, D, E, F, G, H, I, J>.plus(that: K): Tuple11<A, B, C, D, E, F, G, H, I, J, K> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple10<T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple10<K, V, K, V, K, V, K, V, K, V>.toMap(): Map<K, V> = mapOf(a to b, c to d, e to f, g to h, i to j)

/**
 * Represents an abstract implementation of a 11-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Undecuple]
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
interface Tuple11<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <L> to(that: L): Tuple12<A, B, C, D, E, F, G, H, I, J, K, L> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L> Tuple11<A, B, C, D, E, F, G, H, I, J, K>.plus(that: L): Tuple12<A, B, C, D, E, F, G, H, I, J, K, L> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple11<T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k)

/**
 * Represents an abstract implementation of a 12-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Duodecuple]
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
interface Tuple12<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <M> to(that: M): Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M> Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>.plus(that: M): Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple12<T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple12<K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<K, V> = mapOf(a to b, c to d, e to f, g to h, i to j, k to l)

/**
 * Represents an abstract implementation of a 13-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Tredecuple]
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
interface Tuple13<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <N> to(that: N): Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N> Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>.plus(that: N): Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple13<T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m)

/**
 * Represents an abstract implementation of a 14-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Quattuordecuple]
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
interface Tuple14<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <O> to(that: O): Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m

    @JvmDefault
    operator fun component14(): N = n
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>.plus(that: O): Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple14<T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple14<K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<K, V> = mapOf(a to b, c to d, e to f, g to h, i to j, k to l, m to n)

/**
 * Represents an abstract implementation of a 15-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Quindecuple]
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
interface Tuple15<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <P> to(that: P): Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m

    @JvmDefault
    operator fun component14(): N = n

    @JvmDefault
    operator fun component15(): O = o
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>.plus(that: P): Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple15<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

/**
 * Represents an abstract implementation of a 16-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Sexdecuple]
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
interface Tuple16<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <Q> to(that: Q): Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m

    @JvmDefault
    operator fun component14(): N = n

    @JvmDefault
    operator fun component15(): O = o

    @JvmDefault
    operator fun component16(): P = p
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>.plus(that: Q): Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple16<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple16<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<K, V> = mapOf(a to b, c to d, e to f, g to h, i to j, k to l, m to n, o to p)

/**
 * Represents an abstract implementation of a 17-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Septendecuple]
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
interface Tuple17<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <R> to(that: R): Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m

    @JvmDefault
    operator fun component14(): N = n

    @JvmDefault
    operator fun component15(): O = o

    @JvmDefault
    operator fun component16(): P = p

    @JvmDefault
    operator fun component17(): Q = q
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>.plus(that: R): Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple17<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

/**
 * Represents an abstract implementation of a 18-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Octodecuple]
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
interface Tuple18<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <S> to(that: S): Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m

    @JvmDefault
    operator fun component14(): N = n

    @JvmDefault
    operator fun component15(): O = o

    @JvmDefault
    operator fun component16(): P = p

    @JvmDefault
    operator fun component17(): Q = q

    @JvmDefault
    operator fun component18(): R = r
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>.plus(that: S): Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple18<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple18<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<K, V> = mapOf(a to b, c to d, e to f, g to h, i to j, k to l, m to n, o to p, q to r)

/**
 * Represents an abstract implementation of a 19-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Novemdecuple]
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
interface Tuple19<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <T> to(that: T): Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m

    @JvmDefault
    operator fun component14(): N = n

    @JvmDefault
    operator fun component15(): O = o

    @JvmDefault
    operator fun component16(): P = p

    @JvmDefault
    operator fun component17(): Q = q

    @JvmDefault
    operator fun component18(): R = r

    @JvmDefault
    operator fun component19(): S = s
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>.plus(that: T): Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple19<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

/**
 * Represents an abstract implementation of a 20-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Vigintuple]
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
interface Tuple20<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <U> to(that: U): Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m

    @JvmDefault
    operator fun component14(): N = n

    @JvmDefault
    operator fun component15(): O = o

    @JvmDefault
    operator fun component16(): P = p

    @JvmDefault
    operator fun component17(): Q = q

    @JvmDefault
    operator fun component18(): R = r

    @JvmDefault
    operator fun component19(): S = s

    @JvmDefault
    operator fun component20(): T = t
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>.plus(that: U): Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple20<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple20<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<K, V> = mapOf(a to b, c to d, e to f, g to h, i to j, k to l, m to n, o to p, q to r, s to t)

/**
 * Represents an abstract implementation of a 21-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Unvigintuple]
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
interface Tuple21<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U> : Serializable, Identifiable, TupleMarker {
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
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <V> to(that: V): Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m

    @JvmDefault
    operator fun component14(): N = n

    @JvmDefault
    operator fun component15(): O = o

    @JvmDefault
    operator fun component16(): P = p

    @JvmDefault
    operator fun component17(): Q = q

    @JvmDefault
    operator fun component18(): R = r

    @JvmDefault
    operator fun component19(): S = s

    @JvmDefault
    operator fun component20(): T = t

    @JvmDefault
    operator fun component21(): U = u
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>.plus(that: V): Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple21<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

/**
 * Represents an abstract implementation of a 22-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Duovigintuple]
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
 * @property [v] The twenty second value of `this` tuple.
 */
interface Tuple22<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out V> : Serializable, Identifiable, TupleMarker {
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

    val v: V

    /**
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <W> to(that: W): Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m

    @JvmDefault
    operator fun component14(): N = n

    @JvmDefault
    operator fun component15(): O = o

    @JvmDefault
    operator fun component16(): P = p

    @JvmDefault
    operator fun component17(): Q = q

    @JvmDefault
    operator fun component18(): R = r

    @JvmDefault
    operator fun component19(): S = s

    @JvmDefault
    operator fun component20(): T = t

    @JvmDefault
    operator fun component21(): U = u

    @JvmDefault
    operator fun component22(): V = v
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W> Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V>.plus(that: W): Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple22<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple22<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<K, V> = mapOf(a to b, c to d, e to f, g to h, i to j, k to l, m to n, o to p, q to r, s to t, u to v)

/**
 * Represents an abstract implementation of a 23-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Trevigintuple]
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
 * @property [v] The twenty second value of `this` tuple.
 * @property [w] The twenty third value of `this` tuple.
 */
interface Tuple23<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out V, out W> : Serializable, Identifiable, TupleMarker {
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

    val v: V

    val w: W

    /**
     * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
     *
     * Note that whether or not this function is supported is implementation specific, and a class that implements 
     * `this` may choose to not provide an implementation.
     */
    @JvmDefault
    infix fun <X> to(that: X): Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X> = throw UnsupportedOperationException()

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m

    @JvmDefault
    operator fun component14(): N = n

    @JvmDefault
    operator fun component15(): O = o

    @JvmDefault
    operator fun component16(): P = p

    @JvmDefault
    operator fun component17(): Q = q

    @JvmDefault
    operator fun component18(): R = r

    @JvmDefault
    operator fun component19(): S = s

    @JvmDefault
    operator fun component20(): T = t

    @JvmDefault
    operator fun component21(): U = u

    @JvmDefault
    operator fun component22(): V = v

    @JvmDefault
    operator fun component23(): W = w
}

/**
 * Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
 *
 * Note that whether or not this function is supported is implementation specific, and a class that implements 
 * `this` may choose to not provide an implementation.
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X> Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W>.plus(that: X): Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X> = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple23<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w)

/**
 * Represents an abstract implementation of a 24-tuple.
 *
 * Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
 *
 * @see [Quattuorvigintuple]
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
 * @property [v] The twenty second value of `this` tuple.
 * @property [w] The twenty third value of `this` tuple.
 * @property [x] The twenty fourth value of `this` tuple.
 */
interface Tuple24<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out V, out W, out X> : Serializable, Identifiable, TupleMarker {
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

    val v: V

    val w: W

    val x: X

    /**
     * Returns a new [TupleN] that contains the values of `this` and [that].
     */
    @JvmDefault
    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("You are now creating a TupleN class")
    infix fun to(that: Any?): TupleN = TupleN(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, that)

    @JvmDefault
    operator fun component1(): A = a

    @JvmDefault
    operator fun component2(): B = b

    @JvmDefault
    operator fun component3(): C = c

    @JvmDefault
    operator fun component4(): D = d

    @JvmDefault
    operator fun component5(): E = e

    @JvmDefault
    operator fun component6(): F = f

    @JvmDefault
    operator fun component7(): G = g

    @JvmDefault
    operator fun component8(): H = h

    @JvmDefault
    operator fun component9(): I = i

    @JvmDefault
    operator fun component10(): J = j

    @JvmDefault
    operator fun component11(): K = k

    @JvmDefault
    operator fun component12(): L = l

    @JvmDefault
    operator fun component13(): M = m

    @JvmDefault
    operator fun component14(): N = n

    @JvmDefault
    operator fun component15(): O = o

    @JvmDefault
    operator fun component16(): P = p

    @JvmDefault
    operator fun component17(): Q = q

    @JvmDefault
    operator fun component18(): R = r

    @JvmDefault
    operator fun component19(): S = s

    @JvmDefault
    operator fun component20(): T = t

    @JvmDefault
    operator fun component21(): U = u

    @JvmDefault
    operator fun component22(): V = v

    @JvmDefault
    operator fun component23(): W = w

    @JvmDefault
    operator fun component24(): X = x
}

/**
 * Returns a new [TupleN] that contains the values of `this` and [that].
 */
@JvmName("combine")
infix operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y> Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X>.plus(that: Any?): TupleN = this to that

/**
 * Returns a new list containing all the values of `this` tuple.
 */
@JvmName("collect")
fun <T> Tuple24<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x)

/**
 * Returns a new map containing the values of `this` tuple.
 *
 * The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
 */
@JvmName("group")
fun <K, V> Tuple24<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<K, V> = mapOf(a to b, c to d, e to f, g to h, i to j, k to l, m to n, o to p, q to r, s to t, u to v, w to x)

