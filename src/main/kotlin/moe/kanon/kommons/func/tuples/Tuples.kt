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

import moe.kanon.kommons.collections.iterables.collectionSizeOrDefault
import moe.kanon.kommons.collections.maps.calculateMapCapacity
import moe.kanon.kommons.collections.maps.plusAssign
import moe.kanon.kommons.collections.maps.toUnmodifiableMap

/**
 * Represents a `null-tuple`, used as a marker interface for the generated tuples.
 */
interface Tuple0

typealias NullTuple = Tuple0

fun <A, B> Iterable<Tuple2<A, B>>.mapToPairs(): List<Pair<A, B>> = map { (a, b) -> a to b }

fun <A, B> Iterable<Pair<A, B>>.mapToTuples(): List<Tuple2<A, B>> = map { (a, b) -> a toT b }

inline fun <T, K, V, M : MutableMap<in K, in V>> Iterable<T>.associateTo(
    destination: M,
    transform: (T) -> Tuple2<K, V>
): M {
    for (element in this) destination += transform(element)
    return destination
}

inline fun <T, K, V> Iterable<T>.associate(transformer: (T) -> Tuple2<K, V>): Map<K, V> {
    val capacity = calculateMapCapacity(collectionSizeOrDefault(10)).coerceAtLeast(16)
    return associateTo(LinkedHashMap(capacity), transformer)
}

@get:JvmName("getTupleNames")
val TUPLE_NAMES = mapOf(
    0 to "Unit",
    1 to "Single",
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
).toUnmodifiableMap()