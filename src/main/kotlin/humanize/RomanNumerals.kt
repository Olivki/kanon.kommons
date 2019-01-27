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

package moe.kanon.kextensions.humanize

import moe.kanon.kextensions.collections.treeMapOf
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
public fun Byte.toRomanNumeral(): String = this.toLong().toRomanNumeral()

/**
 * Converts this [Short] to a roman numeral in [String] format.
 *
 * @since 0.6.0
 */
public fun Short.toRomanNumeral(): String = this.toLong().toRomanNumeral()

/**
 * Converts this [Int] to a roman numeral in [String] format.
 *
 * @since 0.6.0
 */
public fun Int.toRomanNumeral(): String = this.toLong().toRomanNumeral()

/**
 * Converts this [Long] to a roman numeral in [String] format.
 *
 * @since 0.6.0
 */
public fun Long.toRomanNumeral(): String {
    // Gets the key that has the closest value rounding downwards to this long.
    val closestKey = ROMAN_NUMERALS.floorKey(this)
    return when (closestKey) {
        // If the closest key is the same value as this long, then just return the value that's stored under this key.
        this -> ROMAN_NUMERALS[this]!!
        // If not, just return the value of the closest key, and then recursively append the rest of the values.
        else -> ROMAN_NUMERALS[closestKey] + (this - closestKey).toRomanNumeral()
    }
}

// from

// Taken from Humanizer.jvm.
private fun isInvalidRomanNumeral(input: String): Boolean = !Pattern.compile(
    "^(?i:(?=[MDCLXVI])((M{0,3})((C[DM])|(D?C{0,3}))?((X[LC])|(L?XX{0,2})|L)?((I[VX])|(V?(II{0,2}))|V)?))$",
    Pattern.CASE_INSENSITIVE
).matcher(input).matches()

/**
 * Converts the roman numerals contained in this [String] to the appropriate [Long] value.
 */
// Originally taken from Humanizer.jvm, but it's been modified from the original source.
public fun String.fromRomanNumeral(): Long {
    val input = this.trim().toUpperCase()
    val length = input.length

    if (length <= 0 || isInvalidRomanNumeral(input)) throw IllegalArgumentException(
        "\"$input\" is either empty, or is not a valid roman numeral."
    )

    var total = 0L

    for (i in length downTo 0) {
        var digit = ROMAN_NUMERALS.entries.single { it.value == input[i - 1].toString() }.key

        if (i > 1) {
            val previousDigit = ROMAN_NUMERALS.entries.single { it.value == input[i - 2].toString() }.key

            if (previousDigit < digit) digit -= previousDigit
        }

        total += digit
    }

    return total
}