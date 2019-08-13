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

@file:JvmName("NumbersToWords")

package moe.kanon.kommons.lang.humanize

fun Int.toWords(): String = toLong().toWords()

fun Long.toWords(): String {
    var number = this
    when {
        number == 0L -> return "zero"
        number < 0 -> return "minus ${(number * -1).toWords()}"
        else -> {
            val parts = ArrayList<String>()

            if ((number / 1000000000) > 0) {
                parts.add("${(number / 1000000000).toWords()} billion")
                number %= 1000000000
            }

            if ((number / 1000000) > 0) {
                parts.add("${(number / 1000000).toWords()} million")
                number %= 1000000
            }

            if ((number / 1000) > 0) {
                parts.add("${(number / 1000).toWords()} thousand")
                number %= 1000
            }

            if ((number / 100) > 0) {
                parts.add("${(number / 100).toWords()} hundred")
                number %= 100
            }

            if (number > 0) {
                if (parts.count() != 0)
                    parts.add("and")

                if (number < 20)
                    parts.add(UNITS_MAP[number.toInt()])
                else {
                    var lastPart = TENS_MAP[number.toInt() / 10]
                    if ((number % 10) > 0)
                        lastPart += "-${UNITS_MAP[number.toInt() % 10]}"

                    parts.add(lastPart)
                }
            }

            return parts.joinToString(" ").trimStart()
        }
    }

}

fun Int.toOrdinalWords(): String {
    val toWords: String

    when {
        // 9 => ninth
        exceptionNumbersToWords(this) != "" -> return exceptionNumbersToWords(this)
        // 21 => twenty first
        this > 20 -> {
            val exceptionPart: String
            if (exceptionNumbersToWords(this % 10) != "") {
                exceptionPart = exceptionNumbersToWords(this % 10)
                val normalPart = this - this % 10
                toWords = normalPart.toWords().trimOnePrefix()
                return "$toWords $exceptionPart"
            }
        }
    }

    return normalNumberToWords(this)
}

private val UNITS_MAP = listOf(
    "zero",
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine",
    "ten",
    "eleven",
    "twelve",
    "thirteen",
    "fourteen",
    "fifteen",
    "sixteen",
    "seventeen",
    "eighteen",
    "nineteen"
)

private val TENS_MAP = listOf(
    "zero",
    "ten",
    "twenty",
    "thirty",
    "forty",
    "fifty",
    "sixty",
    "seventy",
    "eighty",
    "ninety"
)

private val ORDINAL_EXCEPTIONS = listOf(
    1 to "first",
    2 to "second",
    3 to "third",
    4 to "fourth",
    5 to "fifth",
    8 to "eighth",
    9 to "ninth",
    12 to "twelfth"
)

private fun normalNumberToWords(number: Int): String {
    var word = number.toWords().replace('-', ' ').trimStart()

    word = word.trimOnePrefix()
    // twenty => twentieth
    if (word.endsWith("y")) word = "${word.substring(0, word.length - 1)}ie"

    return "${word}th"
}

private fun String.trimOnePrefix(): String = if (startsWith("one ", true)) substring(4) else this

private fun exceptionNumbersToWords(number: Int): String = when {
    ORDINAL_EXCEPTIONS.count { it.first == number } == 1 -> ORDINAL_EXCEPTIONS.first { it.first == number }.second
    else -> ""
}