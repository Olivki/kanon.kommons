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

@file:JvmName("RomanNumerals")

package moe.kanon.kommons.lang.humanize

import moe.kanon.kommons.collections.maps.treeMapOf
import java.util.regex.Pattern

private val ROMAN_NUMERALS = treeMapOf(
    1000L to "M",
    900L to "CM",
    500L to "D",
    400L to "CD",
    100L to "C",
    90L to "XC",
    50L to "L",
    40L to "XL",
    10L to "X",
    9L to "IX",
    5L to "V",
    4L to "IV",
    1L to "I"
)

// to...

/**
 * Converts this [Byte] to a roman numeral in [String] format.
 *
 * @since 0.6.0
 */
fun Byte.toRomanNumeral(): String = this.toLong().toRomanNumeral()

/**
 * Converts this [Short] to a roman numeral in [String] format.
 *
 * @since 0.6.0
 */
fun Short.toRomanNumeral(): String = this.toLong().toRomanNumeral()

/**
 * Converts this [Int] to a roman numeral in [String] format.
 *
 * @since 0.6.0
 */
fun Int.toRomanNumeral(): String = this.toLong().toRomanNumeral()

/**
 * Converts this [Long] to a roman numeral in [String] format.
 *
 * @since 0.6.0
 */
fun Long.toRomanNumeral(): String = when (val closestKey = ROMAN_NUMERALS.floorKey(this)) {
    // If the closest key is the same value as this long, then just return the value that's stored under this key.
    this -> ROMAN_NUMERALS[this] ?: throw NoSuchElementException("'$this' does not exist in roman numeral tree.")
    // If not, just return the value of the closest key, and then recursively append the rest of the values.
    else -> ROMAN_NUMERALS[closestKey] + (this - closestKey).toRomanNumeral()
}

// from

// Originally taken from Humanizer.jvm, but the actual regex is not the same anymore.
// The old regex only worked with roman numerals up to 4000, while this one should work with most roman numerals,
// regardless how ridiculous they get.
private fun isInvalidRomanNumeral(input: String): Boolean = !Pattern.compile(
    "(^(?=[MDCLXVI])M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})\$)",
    Pattern.CASE_INSENSITIVE
).matcher(input).matches()

/**
 * Converts the roman numerals contained in this [String] to the appropriate [Long] value.
 *
 * @author Humanizer.jvm
 *
 * @since 0.6.0
 */
fun String.fromRomanNumeral(): Long {
    val input = this.trim().toUpperCase()
    val length = input.length

    if (length <= 0 || isInvalidRomanNumeral(input)) throw IllegalArgumentException(
        "\"$input\" is either empty, or is not a valid roman numeral."
    )

    var total = 0L
    var i = length

    while (i > 0) {
        var digit = ROMAN_NUMERALS.entries.single { it.value == input[i - 1].toString() }.key

        if (i > 1) {
            val previousDigit = ROMAN_NUMERALS.entries.single { it.value == input[i - 2].toString() }.key

            if (previousDigit < digit) {
                digit -= previousDigit
                i--
            }
        }

        i--
        total += digit
    }

    return total
}