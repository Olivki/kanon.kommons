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
@file:JvmName("KTuples")

package moe.kanon.kommons.func

import java.io.Serializable

/*
   The infix functions can't use "to" like the Pair one does, because of name clashes and how the Pair function works.
   So instead we'll use the name "and", which would normally clash with the infix function for bitwise operations,
    (& literally), but no bitwise operations will be done on these classes.

   Because of that, the syntax for creating a triple & quadruple from the infix functions becomes something akin to;
        10 to 5.0F and 'c' // Triple
        10 to 5.0F and 'c' and false // Quadruple

   The Pair class and it's infix function is able to enclose nigh infinite amount of instances of itself, so one
    might argue that [Tripe] and [Quadruple] serve no real purpose.
   However, while Pair *is* able to do this, a problem/annoyance arises rather quickly when one attempts this.
   Due to the way the Pair class achieves this behaviour, if one wants to actually retrieve the value of a big Tuple
    that was created using the Pair class, a lot of unboxing would need to be done.

   Example;
        val tuple = 0 to 1 to 2 to 3 to 4 to 5 to 6 to 7 to 8 to 9 to 10
    The above code would create a Pair class with one ***very*** deeply nested [Pair] containing everything from 0
     (the first value given) up to 9 (the second-to-last value given.).
    The actual syntax for the class would look like this;
        Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Int, Int>, Int>, Int>, Int>, Int>, Int>, Int>, Int>, Int>, Int>
    Which would mean that if you wanted to get the first value given to the Tuple, you'd need to do the following;
        tuple.first.first.first.first.first.first.first.first.first.first
    Needless to say, it's not very pretty.
    Granted, something *as* deeply nested as the tuple showcased here should never actually be done this way, if one
     has that many values that needs to be stored, a List or an Array would be more optimal.
    This is of course also a very extreme case, but I find it to be good to quickly showcase the flaw of the design,
     and using it that way.

    If we wanted to use the Pair "way" to create a Quadruple, it would be something like;
        val quadruple = 0 to 1 to 2 to 3
    Which would generate the following Pair class;
        Pair<Pair<Pair<Int, Int>, Int>, Int>
    This is not *as* bad as the extreme example shown above, but it's still far from optimal, for to access the first
     given value, you'd need to do;
        quad.first.first.first
    Which still doesn't look very good, and would definitely confuse a first-time reader.

    Compare this to how it would look if we used the provided Quadruple class;
        val quadruple = 0 to 1 and 2 and 3
    Which generates the following Quadruple class;
        Quadruple<Int, Int, Int, Int>
    And the syntax for getting the first given value is simply;
        quadruple.first
    Much easier to read, *and* understand what it's actually doing.
*/

typealias Duo<A, B> = Pair<A, B>
typealias Triad<A, B, C> = Triple<A, B, C>
typealias Tetrad<A, B, C, D> = Quadruple<A, B, C, D>
typealias Quad<A, B, C, D> = Quadruple<A, B, C, D>
typealias Tuples = Tuple
typealias T = Tuple

@get:JvmName("getTupleNames")
val TUPLE_NAMES = mapOf(
    0 to "Unit",
    1 to "Singleton",
    2 to "Duad",
    3 to "Triad",
    4 to "Quadruple",
    5 to "Quintuple",
    6 to "Sextuple",
    7 to "Septuple",
    8 to "Octuple",
    9 to "Nonuple",
    10 to "Decuple",
    11 to "Undecuple",
    12 to "Duodecuple",
    13 to "Tredecuple",
    14 to "Quattuordecuple",
    15 to "Quindecuple",
    16 to "Sexdecuple",
    17 to "Septendecuple",
    18 to "Octodecuple",
    19 to "Novemdecuple",
    20 to "Vigintuple",
    21 to "Unvigintuple",
    22 to "Duovigintuple",
    23 to "Trevigintuple",
    24 to "Quattuorvigintuple",
    25 to "Quinvigintuple",
    26 to "Sexvigintuple",
    27 to "Septenvigintuple",
    28 to "Octovigintuple",
    29 to "Novemvigintuple",
    30 to "Trigintuple",
    31 to "Untrigintuple",
    32 to "Duotriguple",
    33 to "Tretriguple",
    34 to "Quattuortriguple",
    35 to "Quintriguple",
    36 to "Sextriguple",
    37 to "Septentriguple",
    38 to "Octotriguple",
    39 to "Novemtriguple",
    40 to "Quadraguple",
    41 to "Unquadraguple",
    42 to "Duoquadraguple",
    43 to "Trequadraguple",
    44 to "Quattuorquadraguple",
    45 to "Quinquadraguple",
    46 to "Sexquadraguple",
    47 to "Septenquadraguple",
    48 to "Octoquadraguple",
    49 to "Novemquadraguple",
    50 to "Quinquaguple",
    51 to "Unquinquaguple",
    52 to "Duoquinquaguple",
    53 to "Trequinquaguple",
    54 to "Quattuorquinquaguple",
    55 to "Quinquinquaguple",
    56 to "Sexquinquaguple",
    57 to "Septenquinquaguple",
    58 to "Octoquinquaguple",
    59 to "Novemquinquaguple",
    60 to "Sexaguple",
    61 to "Unsexaguple",
    62 to "Duosexaguple",
    63 to "Tresexaguple",
    64 to "Quattuorsexaguple",
    65 to "Quinsexaguple",
    66 to "Sexsexaguple",
    67 to "Septensexaguple",
    68 to "Octosexaguple",
    69 to "Novemsexaguple",
    70 to "Septuaguple",
    71 to "Unseptuaguple",
    72 to "Duoseptuaguple",
    73 to "Treseptuaguple",
    74 to "Quattuorseptuaguple",
    75 to "Quinseptuaguple",
    76 to "Sexseptuaguple",
    77 to "Septenseptuaguple",
    78 to "Octoseptuaguple",
    79 to "Novemseptuaguple",
    80 to "Octoguple",
    81 to "Unoctoguple",
    82 to "Duooctoguple",
    83 to "Treoctoguple",
    84 to "Quattuoroctoguple",
    85 to "Quinoctoguple",
    86 to "Sexoctoguple",
    87 to "Septoctoguple",
    88 to "Octooctoguple",
    89 to "Novemoctoguple",
    90 to "Nonaguple",
    91 to "Unnonaguple",
    92 to "Duononaguple",
    93 to "Trenonaguple",
    94 to "Quattuornonaguple",
    95 to "Quinnonaguple",
    96 to "Sexnonaguple",
    97 to "Septennonaguple",
    98 to "Octononaguple",
    99 to "Novemnonaguple",
    100 to "Centuple"
)

/**
 * Creates a [Triple] from the preceding [Pair] and [that].
 */
@JvmName("toTriple")
infix fun <A, B, C> Pair<A, B>.and(that: C): Triple<A, B, C> = Triple(first, second, that)

/**
 * Converts the values of `this` [Quadruple] into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Pair<K, V>.toMap(): Map<K, V> = mapOf(first to second)

/**
 * Represents a tetrad of values.
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 *
 * Quadruple exhibits value semantics, i.e. two quadruples are equal if all four components are equal.
 *
 * @constructor Creates a new instance of `Quadruple`.
 *
 * @param [A] type of the first value.
 * @param [B] type of the second value.
 * @param [C] type of the third value.
 * @param [D] type of the fourth value.
 *
 * @property [first] First value.
 * @property [second] Second value.
 * @property [third] Third value.
 * @property [fourth] Fourth value.
 */
data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth)"
}

/**
 * Creates a [Quadruple] from the preceding [Triple] and [that].
 */
@JvmName("toQuadruple")
infix fun <A, B, C, D> Triple<A, B, C>.and(that: D): Quad<A, B, C, D> = Quad(first, second, third, that)

/**
 * Converts `this` [Quadruple] into a list.
 */
@JvmName("collect")
fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)

/**
 * Converts the values of `this` [Quadruple] into a map.
 *
 * The entries of the resulting map are populated by putting all values which have a odd index as the key, and the
 * values that are after the odd indexed value are put as the value.
 *
 * @param [K] the type of the [keys][Map.keys] of the map
 * @param [V] the type of the [values][Map.values] of the map
 */
@JvmName("collect")
fun <K, V> Quadruple<K, V, K, V>.toMap(): Map<K, V> = mapOf(first to second, third to fourth)

/**
 * Factory class for creating tuples.
 */
object Tuple {
    /**
     * Creates and returns a `tuple` of the type [Pair] from the [first] and [second] values.
     *
     * @param [first] the first value in the pair
     * @param [second] the second and final value in the pair
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B> invoke(first: A, second: B): Pair<A, B> = Pair(first, second)

    /**
     * Creates and returns a `tuple` of the type [Triple] from the [first], [second] and [third] values.
     *
     * @param [first] the first value in the triad
     * @param [second] the second value in the triad
     * @param [third] the third and final value in the triad
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C> invoke(first: A, second: B, third: C): Triple<A, B, C> = Triple(first, second, third)

    /**
     * Creates and returns a `tuple` of the type [Quadruple] from the [first], [second], [third] and [fourth] values.
     *
     * @param [first] the first value in the tetrad
     * @param [second] the second value in the tetrad
     * @param [third] the third value in the tetrad
     * @param [fourth] the fourth and final value in the tetrad
     */
    @JvmStatic
    @JvmName("of")
    operator fun <A, B, C, D> invoke(first: A, second: B, third: C, fourth: D): Quad<A, B, C, D> =
        Quad(first, second, third, fourth)

    // for pseudo tuple literals
    // these are marked as 'JvmSynthetic' to hide them if this class is looked at from the Java side
    // they can be used like: Tuple[5, 67, 80] or T[5, 67, 80]
    /**
     * Creates and returns a `tuple` of the type [Pair] from the [first] and [second] values.
     *
     * @param [first] the first value in the pair
     * @param [second] the second and final value in the pair
     */
    @JvmSynthetic
    operator fun <A, B> get(first: A, second: B): Pair<A, B> = Pair(first, second)

    /**
     * Creates and returns a `tuple` of the type [Triple] from the [first], [second] and [third] values.
     *
     * @param [first] the first value in the triad
     * @param [second] the second value in the triad
     * @param [third] the third and final value in the triad
     */
    @JvmSynthetic
    operator fun <A, B, C> get(first: A, second: B, third: C): Triple<A, B, C> = Triple(first, second, third)

    /**
     * Creates and returns a `tuple` of the type [Quadruple] from the [first], [second], [third] and [fourth] values.
     *
     * @param [first] the first value in the tetrad
     * @param [second] the second value in the tetrad
     * @param [third] the third value in the tetrad
     * @param [fourth] the fourth and final value in the tetrad
     */
    @JvmSynthetic
    operator fun <A, B, C, D> get(first: A, second: B, third: C, fourth: D): Quad<A, B, C, D> =
        Quad(first, second, third, fourth)
}

