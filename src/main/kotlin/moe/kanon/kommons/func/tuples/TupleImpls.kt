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
@file:Suppress("OVERRIDE_BY_INLINE")

package moe.kanon.kommons.func.tuples

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
 *      The equivalent tuple classes for 'Pair' and 'Triple' *('Tuple2/Duad' and 'Tupl3/Triad' respectively)* have also
 * been given functions for converting to their std-lib implementations to somewhat ease compatibility. They have also
 * been given the 'not' operator to make the syntax even cleaner, this operator is entirely optional, and should only
 * be used if you are sure that using it would not create reading issues, as it is a very subtle operator. There also
 * exists extension functions for 'Pair' and 'Triple' to convert them to their respective kommons implementations,
 * these also have the 'not' operator available for them.
 */

// TODO: If performance becomes an issue, just change the types of the functions from the interface reference to a
// direct class reference, like ': Tuple<A, B>' -> ': Duad<A, B>'

// Auto-generated
// A max of 24 tuples are created, if you need more you might be doing something you shouldn't be doing.
/**
 * Represents an generic implementation of a [1-tuple][Tuple1].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Single`s exhibits value semantics, i.e. two `single`s are equal if all one components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Single<out A>(override val a: A) : Tuple1<A> {
    /**
     * Creates a [tuple][Duad] from the values of `this` tuple and [that].
     */
    override infix fun <B> to(that: B): Duad<A, B> = Duad(a, that)

    override inline fun <R1> fold(transformer: (a: A) -> R1): R1 = transformer(a)

    override inline fun <A1> flatMap(transformer: (a: A) -> Tuple1<A1>): Tuple1<A1> = transformer(a)

    override inline fun any(predicate: (a: A) -> Boolean): Boolean = predicate(a)

    override inline fun none(predicate: (a: A) -> Boolean): Boolean = !predicate(a)

    override infix fun <A1> zip(that: Tuple1<A1>): Tuple2<Tuple1<A>, Tuple1<A1>> = this toT that

    override fun toString(): String = "($a)"
}

/**
 * Creates a [tuple][Tuple1] of `this`.
 *
 * @since 0.6.0
 */
@JvmName("single")
fun <T> T.toSingle(): Tuple1<T> = Single(this)

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Single<T>.toList(): List<T> = listOf(a)

/**
 * Represents an generic implementation of a [2-tuple][Tuple2].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duad`s exhibits value semantics, i.e. two `duad`s are equal if all two components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Duad<out A, out B>(override val a: A, override val b: B) : Tuple2<A, B> {
    /**
     * Creates a [tuple][Triad] from the values of `this` tuple and [that].
     */
    override infix fun <C> to(that: C): Triad<A, B, C> = Triad(a, b, that)

    override inline fun <R1> fold(transformer: (a: A, b: B) -> R1): R1 = transformer(a, b)

    override inline fun <A1, B1> flatMap(transformer: (a: A, b: B) -> Tuple2<A1, B1>): Tuple2<A1, B1> = transformer(a, b)

    override inline fun any(predicate: (a: A, b: B) -> Boolean): Boolean = predicate(a, b)

    override inline fun none(predicate: (a: A, b: B) -> Boolean): Boolean = !predicate(a, b)

    override infix fun <A1, B1> zip(that: Tuple2<A1, B1>): Tuple2<Tuple2<A, B>, Tuple2<A1, B1>> = this toT that

    override fun toString(): String = "($a, $b)"
}

/**
 * Creates a [tuple][Tuple2] from `this` and [that].
 *
 * @since 0.6.0
 */
@JvmName("pair")
infix fun <A, B> A.toT(that: B): Tuple2<A, B> = Duad(this, that)

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Duad<T, T>.toList(): List<T> = listOf(a, b)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Duad<K, V>.toMap(): Map<in K, V> = mapOf(a toT b)

/**
 * Converts `this` pair into a [tuple][Tuple2].
 *
 * @since 0.6.0
 */
@JvmName("fromPair")
fun <A, B> Pair<A, B>.toTuple(): Tuple2<A, B> = Duad(this.first, this.second)

/**
 * Converts `this` pair into a [tuple][Tuple2].
 *
 * @since 0.6.0
 */
@JvmName("fromPairNot")
@Suppress("NOTHING_TO_INLINE")
inline operator fun <A, B> Pair<A, B>.not(): Tuple2<A, B> = this.toTuple()

/**
 * Converts `this` entry into a [tuple][Tuple2].
 *
 * @since 0.6.0
 */
@JvmName("fromEntry")
fun <A, B> Map.Entry<A, B>.toTuple(): Tuple2<A, B> = Duad(this.key, this.value)

/**
 * Converts `this` entry into a [tuple][Tuple2].
 *
 * @since 0.6.0
 */
@JvmName("fromEntryNot")
@Suppress("NOTHING_TO_INLINE")
inline operator fun <A, B> Map.Entry<A, B>.not(): Tuple2<A, B> = this.toTuple()

/**
 * Represents an generic implementation of a [3-tuple][Tuple3].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Triad`s exhibits value semantics, i.e. two `triad`s are equal if all three components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Triad<out A, out B, out C>(override val a: A, override val b: B, override val c: C) : Tuple3<A, B, C> {
    /**
     * Creates a [tuple][Quadruple] from the values of `this` tuple and [that].
     */
    override infix fun <D> to(that: D): Quadruple<A, B, C, D> = Quadruple(a, b, c, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C) -> R1): R1 = transformer(a, b, c)

    override inline fun <A1, B1, C1> flatMap(transformer: (a: A, b: B, c: C) -> Tuple3<A1, B1, C1>): Tuple3<A1, B1, C1> = transformer(a, b, c)

    override inline fun any(predicate: (a: A, b: B, c: C) -> Boolean): Boolean = predicate(a, b, c)

    override inline fun none(predicate: (a: A, b: B, c: C) -> Boolean): Boolean = !predicate(a, b, c)

    override infix fun <A1, B1, C1> zip(that: Tuple3<A1, B1, C1>): Tuple2<Tuple3<A, B, C>, Tuple3<A1, B1, C1>> = this toT that

    override fun toString(): String = "($a, $b, $c)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Triad<T, T, T>.toList(): List<T> = listOf(a, b, c)

/**
 * Converts `this` triple into a [tuple][Tuple3].
 *
 * @since 0.6.0
 */
@JvmName("fromTriple")
fun <A, B, C> Triple<A, B, C>.toTuple(): Tuple3<A, B, C> = Triad(this.first, this.second, this.third)

/**
 * Converts `this` triple into a [tuple][Tuple3].
 *
 * @since 0.6.0
 */
@JvmName("fromTripleNot")
@Suppress("NOTHING_TO_INLINE")
inline operator fun <A, B, C> Triple<A, B, C>.not(): Tuple3<A, B, C> = this.toTuple()

/**
 * Represents an generic implementation of a [4-tuple][Tuple4].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quadruple`s exhibits value semantics, i.e. two `quadruple`s are equal if all four components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Quadruple<out A, out B, out C, out D>(override val a: A, override val b: B, override val c: C, override val d: D) : Tuple4<A, B, C, D> {
    /**
     * Creates a [tuple][Quintuple] from the values of `this` tuple and [that].
     */
    override infix fun <E> to(that: E): Quintuple<A, B, C, D, E> = Quintuple(a, b, c, d, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D) -> R1): R1 = transformer(a, b, c, d)

    override inline fun <A1, B1, C1, D1> flatMap(transformer: (a: A, b: B, c: C, d: D) -> Tuple4<A1, B1, C1, D1>): Tuple4<A1, B1, C1, D1> = transformer(a, b, c, d)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D) -> Boolean): Boolean = predicate(a, b, c, d)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D) -> Boolean): Boolean = !predicate(a, b, c, d)

    override infix fun <A1, B1, C1, D1> zip(that: Tuple4<A1, B1, C1, D1>): Tuple2<Tuple4<A, B, C, D>, Tuple4<A1, B1, C1, D1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(a, b, c, d)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Quadruple<K, V, K, V>.toMap(): Map<in K, V> = mapOf(a toT b, c toT d)

/**
 * Represents an generic implementation of a [5-tuple][Tuple5].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quintuple`s exhibits value semantics, i.e. two `quintuple`s are equal if all five components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Quintuple<out A, out B, out C, out D, out E>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E) : Tuple5<A, B, C, D, E> {
    /**
     * Creates a [tuple][Sextuple] from the values of `this` tuple and [that].
     */
    override infix fun <F> to(that: F): Sextuple<A, B, C, D, E, F> = Sextuple(a, b, c, d, e, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E) -> R1): R1 = transformer(a, b, c, d, e)

    override inline fun <A1, B1, C1, D1, E1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E) -> Tuple5<A1, B1, C1, D1, E1>): Tuple5<A1, B1, C1, D1, E1> = transformer(a, b, c, d, e)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E) -> Boolean): Boolean = predicate(a, b, c, d, e)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E) -> Boolean): Boolean = !predicate(a, b, c, d, e)

    override infix fun <A1, B1, C1, D1, E1> zip(that: Tuple5<A1, B1, C1, D1, E1>): Tuple2<Tuple5<A, B, C, D, E>, Tuple5<A1, B1, C1, D1, E1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Quintuple<T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e)

/**
 * Represents an generic implementation of a [6-tuple][Tuple6].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Sextuple`s exhibits value semantics, i.e. two `sextuple`s are equal if all six components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Sextuple<out A, out B, out C, out D, out E, out F>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F) : Tuple6<A, B, C, D, E, F> {
    /**
     * Creates a [tuple][Septuple] from the values of `this` tuple and [that].
     */
    override infix fun <G> to(that: G): Septuple<A, B, C, D, E, F, G> = Septuple(a, b, c, d, e, f, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F) -> R1): R1 = transformer(a, b, c, d, e, f)

    override inline fun <A1, B1, C1, D1, E1, F1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F) -> Tuple6<A1, B1, C1, D1, E1, F1>): Tuple6<A1, B1, C1, D1, E1, F1> = transformer(a, b, c, d, e, f)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F) -> Boolean): Boolean = predicate(a, b, c, d, e, f)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F) -> Boolean): Boolean = !predicate(a, b, c, d, e, f)

    override infix fun <A1, B1, C1, D1, E1, F1> zip(that: Tuple6<A1, B1, C1, D1, E1, F1>): Tuple2<Tuple6<A, B, C, D, E, F>, Tuple6<A1, B1, C1, D1, E1, F1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Sextuple<T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Sextuple<K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(a toT b, c toT d, e toT f)

/**
 * Represents an generic implementation of a [7-tuple][Tuple7].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Septuple`s exhibits value semantics, i.e. two `septuple`s are equal if all seven components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Septuple<out A, out B, out C, out D, out E, out F, out G>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G) : Tuple7<A, B, C, D, E, F, G> {
    /**
     * Creates a [tuple][Octuple] from the values of `this` tuple and [that].
     */
    override infix fun <H> to(that: H): Octuple<A, B, C, D, E, F, G, H> = Octuple(a, b, c, d, e, f, g, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G) -> R1): R1 = transformer(a, b, c, d, e, f, g)

    override inline fun <A1, B1, C1, D1, E1, F1, G1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G) -> Tuple7<A1, B1, C1, D1, E1, F1, G1>): Tuple7<A1, B1, C1, D1, E1, F1, G1> = transformer(a, b, c, d, e, f, g)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g)

    override infix fun <A1, B1, C1, D1, E1, F1, G1> zip(that: Tuple7<A1, B1, C1, D1, E1, F1, G1>): Tuple2<Tuple7<A, B, C, D, E, F, G>, Tuple7<A1, B1, C1, D1, E1, F1, G1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Septuple<T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g)

/**
 * Represents an generic implementation of a [8-tuple][Tuple8].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Octuple`s exhibits value semantics, i.e. two `octuple`s are equal if all eight components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Octuple<out A, out B, out C, out D, out E, out F, out G, out H>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H) : Tuple8<A, B, C, D, E, F, G, H> {
    /**
     * Creates a [tuple][Nonuple] from the values of `this` tuple and [that].
     */
    override infix fun <I> to(that: I): Nonuple<A, B, C, D, E, F, G, H, I> = Nonuple(a, b, c, d, e, f, g, h, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H) -> R1): R1 = transformer(a, b, c, d, e, f, g, h)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H) -> Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>): Tuple8<A1, B1, C1, D1, E1, F1, G1, H1> = transformer(a, b, c, d, e, f, g, h)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1> zip(that: Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>): Tuple2<Tuple8<A, B, C, D, E, F, G, H>, Tuple8<A1, B1, C1, D1, E1, F1, G1, H1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Octuple<T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Octuple<K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(a toT b, c toT d, e toT f, g toT h)

/**
 * Represents an generic implementation of a [9-tuple][Tuple9].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Nonuple`s exhibits value semantics, i.e. two `nonuple`s are equal if all nine components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Nonuple<out A, out B, out C, out D, out E, out F, out G, out H, out I>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I) : Tuple9<A, B, C, D, E, F, G, H, I> {
    /**
     * Creates a [tuple][Decuple] from the values of `this` tuple and [that].
     */
    override infix fun <J> to(that: J): Decuple<A, B, C, D, E, F, G, H, I, J> = Decuple(a, b, c, d, e, f, g, h, i, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I) -> Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>): Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1> = transformer(a, b, c, d, e, f, g, h, i)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1> zip(that: Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>): Tuple2<Tuple9<A, B, C, D, E, F, G, H, I>, Tuple9<A1, B1, C1, D1, E1, F1, G1, H1, I1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Nonuple<T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i)

/**
 * Represents an generic implementation of a [10-tuple][Tuple10].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Decuple`s exhibits value semantics, i.e. two `decuple`s are equal if all ten components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Decuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J) : Tuple10<A, B, C, D, E, F, G, H, I, J> {
    /**
     * Creates a [tuple][Undecuple] from the values of `this` tuple and [that].
     */
    override infix fun <K> to(that: K): Undecuple<A, B, C, D, E, F, G, H, I, J, K> = Undecuple(a, b, c, d, e, f, g, h, i, j, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J) -> Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>): Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1> = transformer(a, b, c, d, e, f, g, h, i, j)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1> zip(that: Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>): Tuple2<Tuple10<A, B, C, D, E, F, G, H, I, J>, Tuple10<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Decuple<T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Decuple<K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(a toT b, c toT d, e toT f, g toT h, i toT j)

/**
 * Represents an generic implementation of a [11-tuple][Tuple11].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Undecuple`s exhibits value semantics, i.e. two `undecuple`s are equal if all eleven components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Undecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K) : Tuple11<A, B, C, D, E, F, G, H, I, J, K> {
    /**
     * Creates a [tuple][Duodecuple] from the values of `this` tuple and [that].
     */
    override infix fun <L> to(that: L): Duodecuple<A, B, C, D, E, F, G, H, I, J, K, L> = Duodecuple(a, b, c, d, e, f, g, h, i, j, k, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K) -> Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>): Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1> = transformer(a, b, c, d, e, f, g, h, i, j, k)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1> zip(that: Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>): Tuple2<Tuple11<A, B, C, D, E, F, G, H, I, J, K>, Tuple11<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Undecuple<T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k)

/**
 * Represents an generic implementation of a [12-tuple][Tuple12].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duodecuple`s exhibits value semantics, i.e. two `duodecuple`s are equal if all twelve components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Duodecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L) : Tuple12<A, B, C, D, E, F, G, H, I, J, K, L> {
    /**
     * Creates a [tuple][Tredecuple] from the values of `this` tuple and [that].
     */
    override infix fun <M> to(that: M): Tredecuple<A, B, C, D, E, F, G, H, I, J, K, L, M> = Tredecuple(a, b, c, d, e, f, g, h, i, j, k, l, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L) -> Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>): Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1> zip(that: Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>): Tuple2<Tuple12<A, B, C, D, E, F, G, H, I, J, K, L>, Tuple12<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Duodecuple<T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Duodecuple<K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(a toT b, c toT d, e toT f, g toT h, i toT j, k toT l)

/**
 * Represents an generic implementation of a [13-tuple][Tuple13].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Tredecuple`s exhibits value semantics, i.e. two `tredecuple`s are equal if all thirteen components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Tredecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M) : Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M> {
    /**
     * Creates a [tuple][Quattuordecuple] from the values of `this` tuple and [that].
     */
    override infix fun <N> to(that: N): Quattuordecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N> = Quattuordecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M) -> Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>): Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1> zip(that: Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>): Tuple2<Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M>, Tuple13<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Tredecuple<T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m)

/**
 * Represents an generic implementation of a [14-tuple][Tuple14].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quattuordecuple`s exhibits value semantics, i.e. two `quattuordecuple`s are equal if all fourteen components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Quattuordecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N) : Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N> {
    /**
     * Creates a [tuple][Quindecuple] from the values of `this` tuple and [that].
     */
    override infix fun <O> to(that: O): Quindecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = Quindecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N) -> Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>): Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1> zip(that: Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>): Tuple2<Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>, Tuple14<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Quattuordecuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Quattuordecuple<K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(a toT b, c toT d, e toT f, g toT h, i toT j, k toT l, m toT n)

/**
 * Represents an generic implementation of a [15-tuple][Tuple15].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quindecuple`s exhibits value semantics, i.e. two `quindecuple`s are equal if all fifteen components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Quindecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O) : Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> {
    /**
     * Creates a [tuple][Sexdecuple] from the values of `this` tuple and [that].
     */
    override infix fun <P> to(that: P): Sexdecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = Sexdecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O) -> Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>): Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1> zip(that: Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>): Tuple2<Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>, Tuple15<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Quindecuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

/**
 * Represents an generic implementation of a [16-tuple][Tuple16].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Sexdecuple`s exhibits value semantics, i.e. two `sexdecuple`s are equal if all sixteen components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Sexdecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P) : Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> {
    /**
     * Creates a [tuple][Septendecuple] from the values of `this` tuple and [that].
     */
    override infix fun <Q> to(that: Q): Septendecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> = Septendecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P) -> Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>): Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1> zip(that: Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>): Tuple2<Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P>, Tuple16<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Sexdecuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Sexdecuple<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(a toT b, c toT d, e toT f, g toT h, i toT j, k toT l, m toT n, o toT p)

/**
 * Represents an generic implementation of a [17-tuple][Tuple17].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Septendecuple`s exhibits value semantics, i.e. two `septendecuple`s are equal if all seventeen components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Septendecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q) : Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> {
    /**
     * Creates a [tuple][Octodecuple] from the values of `this` tuple and [that].
     */
    override infix fun <R> to(that: R): Octodecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = Octodecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q) -> Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>): Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1> zip(that: Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>): Tuple2<Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>, Tuple17<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Septendecuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

/**
 * Represents an generic implementation of a [18-tuple][Tuple18].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Octodecuple`s exhibits value semantics, i.e. two `octodecuple`s are equal if all eighteen components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Octodecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R) : Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> {
    /**
     * Creates a [tuple][Novemdecuple] from the values of `this` tuple and [that].
     */
    override infix fun <S> to(that: S): Novemdecuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = Novemdecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R) -> Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>): Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1> zip(that: Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>): Tuple2<Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R>, Tuple18<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Octodecuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Octodecuple<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(a toT b, c toT d, e toT f, g toT h, i toT j, k toT l, m toT n, o toT p, q toT r)

/**
 * Represents an generic implementation of a [19-tuple][Tuple19].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Novemdecuple`s exhibits value semantics, i.e. two `novemdecuple`s are equal if all nineteen components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Novemdecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S) : Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> {
    /**
     * Creates a [tuple][Vigintuple] from the values of `this` tuple and [that].
     */
    override infix fun <T> to(that: T): Vigintuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> = Vigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S) -> Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>): Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1> zip(that: Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>): Tuple2<Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>, Tuple19<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Novemdecuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

/**
 * Represents an generic implementation of a [20-tuple][Tuple20].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Vigintuple`s exhibits value semantics, i.e. two `vigintuple`s are equal if all twenty components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Vigintuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S, override val t: T) : Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> {
    /**
     * Creates a [tuple][Unvigintuple] from the values of `this` tuple and [that].
     */
    override infix fun <U> to(that: U): Unvigintuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> = Unvigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T) -> Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>): Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1> zip(that: Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>): Tuple2<Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T>, Tuple20<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Vigintuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Vigintuple<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(a toT b, c toT d, e toT f, g toT h, i toT j, k toT l, m toT n, o toT p, q toT r, s toT t)

/**
 * Represents an generic implementation of a [21-tuple][Tuple21].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Unvigintuple`s exhibits value semantics, i.e. two `unvigintuple`s are equal if all twenty-one components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Unvigintuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S, override val t: T, override val u: U) : Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> {
    /**
     * Creates a [tuple][Duovigintuple] from the values of `this` tuple and [that].
     */
    override infix fun <W> to(that: W): Duovigintuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W> = Duovigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U) -> Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>): Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1> zip(that: Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>): Tuple2<Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U>, Tuple21<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Unvigintuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

/**
 * Represents an generic implementation of a [22-tuple][Tuple22].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Duovigintuple`s exhibits value semantics, i.e. two `duovigintuple`s are equal if all twenty-two components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Duovigintuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out W>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S, override val t: T, override val u: U, override val w: W) : Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W> {
    /**
     * Creates a [tuple][Trevigintuple] from the values of `this` tuple and [that].
     */
    override infix fun <X> to(that: X): Trevigintuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X> = Trevigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W) -> Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1>): Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1> zip(that: Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1>): Tuple2<Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W>, Tuple22<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u, $w)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Duovigintuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Duovigintuple<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(a toT b, c toT d, e toT f, g toT h, i toT j, k toT l, m toT n, o toT p, q toT r, s toT t, u toT w)

/**
 * Represents an generic implementation of a [23-tuple][Tuple23].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Trevigintuple`s exhibits value semantics, i.e. two `trevigintuple`s are equal if all twenty-three components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Trevigintuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out W, out X>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S, override val t: T, override val u: U, override val w: W, override val x: X) : Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X> {
    /**
     * Creates a [tuple][Quattuorvigintuple] from the values of `this` tuple and [that].
     */
    override infix fun <Y> to(that: Y): Quattuorvigintuple<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X, Y> = Quattuorvigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, that)

    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X) -> Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1>): Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1> zip(that: Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1>): Tuple2<Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X>, Tuple23<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u, $w, $x)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Trevigintuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x)

/**
 * Represents an generic implementation of a [24-tuple][Tuple24].
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * `Quattuorvigintuple`s exhibits value semantics, i.e. two `quattuorvigintuple`s are equal if all twenty-four components are equal.
 *
 * @since 0.6.0
 *
 */
@TupleClass
data class Quattuorvigintuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q, out R, out S, out T, out U, out W, out X, out Y>(override val a: A, override val b: B, override val c: C, override val d: D, override val e: E, override val f: F, override val g: G, override val h: H, override val i: I, override val j: J, override val k: K, override val l: L, override val m: M, override val n: N, override val o: O, override val p: P, override val q: Q, override val r: R, override val s: S, override val t: T, override val u: U, override val w: W, override val x: X, override val y: Y) : Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X, Y> {
    override inline fun <R1> fold(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X, y: Y) -> R1): R1 = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, y)

    override inline fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1> flatMap(transformer: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X, y: Y) -> Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1>): Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1> = transformer(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, y)

    override inline fun any(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X, y: Y) -> Boolean): Boolean = predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, y)

    override inline fun none(predicate: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X, y: Y) -> Boolean): Boolean = !predicate(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, y)

    override infix fun <A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1> zip(that: Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1>): Tuple2<Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X, Y>, Tuple24<A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, W1, X1, Y1>> = this toT that

    override fun toString(): String = "($a, $b, $c, $d, $e, $f, $g, $h, $i, $j, $k, $l, $m, $n, $o, $p, $q, $r, $s, $t, $u, $w, $x, $y)"
}

/**
 * Converts the values of `this` tuple into a list.
 *
 * @param [T] the type of the values stored in the list
 *
 * @since 0.6.0
 */
@JvmName("collect")
fun <T> Quattuorvigintuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, y)

/**
 * Converts the values of `this` tuple into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 *
 * @since 0.6.0
 */
@JvmName("group")
fun <K, V> Quattuorvigintuple<K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V, K, V>.toMap(): Map<in K, V> = mapOf(a toT b, c toT d, e toT f, g toT h, i toT j, k toT l, m toT n, o toT p, q toT r, s toT t, u toT w, x toT y)

/**
 * A type-alias for [Tuple] to enable "pseudo tuple literals".
 *
 * It can be used like this `TU[1, 4, 8, 6]` or `TU(1, 4, 8, 6)`, both will create a [tuple][Tuple4] containing the
 * values `(1, 4, 8, 6)`.
 */
typealias TU = Tuple

/**
 * A factory class for creating tuples.
 *
 * @since 0.6.0
 */
object Tuple {

    /**
     * Creates and returns a [tuple][Single] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A> invoke(a: A): Tuple1<A> = Single(a)

    /**
     * Creates and returns a [tuple][Duad] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B> invoke(a: A, b: B): Tuple2<A, B> = Duad(a, b)

    /**
     * Creates and returns a [tuple][Triad] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C> invoke(a: A, b: B, c: C): Tuple3<A, B, C> = Triad(a, b, c)

    /**
     * Creates and returns a [tuple][Quadruple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D> invoke(a: A, b: B, c: C, d: D): Tuple4<A, B, C, D> = Quadruple(a, b, c, d)

    /**
     * Creates and returns a [tuple][Quintuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E> invoke(a: A, b: B, c: C, d: D, e: E): Tuple5<A, B, C, D, E> = Quintuple(a, b, c, d, e)

    /**
     * Creates and returns a [tuple][Sextuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F> invoke(a: A, b: B, c: C, d: D, e: E, f: F): Tuple6<A, B, C, D, E, F> = Sextuple(a, b, c, d, e, f)

    /**
     * Creates and returns a [tuple][Septuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G): Tuple7<A, B, C, D, E, F, G> = Septuple(a, b, c, d, e, f, g)

    /**
     * Creates and returns a [tuple][Octuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H): Tuple8<A, B, C, D, E, F, G, H> = Octuple(a, b, c, d, e, f, g, h)

    /**
     * Creates and returns a [tuple][Nonuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I): Tuple9<A, B, C, D, E, F, G, H, I> = Nonuple(a, b, c, d, e, f, g, h, i)

    /**
     * Creates and returns a [tuple][Decuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J): Tuple10<A, B, C, D, E, F, G, H, I, J> = Decuple(a, b, c, d, e, f, g, h, i, j)

    /**
     * Creates and returns a [tuple][Undecuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K): Tuple11<A, B, C, D, E, F, G, H, I, J, K> = Undecuple(a, b, c, d, e, f, g, h, i, j, k)

    /**
     * Creates and returns a [tuple][Duodecuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L): Tuple12<A, B, C, D, E, F, G, H, I, J, K, L> = Duodecuple(a, b, c, d, e, f, g, h, i, j, k, l)

    /**
     * Creates and returns a [tuple][Tredecuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M): Tuple13<A, B, C, D, E, F, G, H, I, J, K, L, M> = Tredecuple(a, b, c, d, e, f, g, h, i, j, k, l, m)

    /**
     * Creates and returns a [tuple][Quattuordecuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N): Tuple14<A, B, C, D, E, F, G, H, I, J, K, L, M, N> = Quattuordecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n)

    /**
     * Creates and returns a [tuple][Quindecuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O): Tuple15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = Quindecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)

    /**
     * Creates and returns a [tuple][Sexdecuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P): Tuple16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = Sexdecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)

    /**
     * Creates and returns a [tuple][Septendecuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q): Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> = Septendecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)

    /**
     * Creates and returns a [tuple][Octodecuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R): Tuple18<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = Octodecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)

    /**
     * Creates and returns a [tuple][Novemdecuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S): Tuple19<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = Novemdecuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)

    /**
     * Creates and returns a [tuple][Vigintuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T): Tuple20<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T> = Vigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)

    /**
     * Creates and returns a [tuple][Unvigintuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U): Tuple21<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U> = Unvigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)

    /**
     * Creates and returns a [tuple][Duovigintuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W): Tuple22<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W> = Duovigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w)

    /**
     * Creates and returns a [tuple][Trevigintuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X): Tuple23<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X> = Trevigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x)

    /**
     * Creates and returns a [tuple][Quattuorvigintuple] from the specified arguments.
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X, Y> invoke(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, r: R, s: S, t: T, u: U, w: W, x: X, y: Y): Tuple24<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, W, X, Y> = Quattuorvigintuple(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, w, x, y)
}