/*
 * Copyright 2019-2020 Oliver Berg
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

@file:JvmName("KStrings")

package moe.kanon.kommons.lang

/**
 * Capitalizes all the words in the [CharSequence] that are pre-faced by any of the given [delimiters].
 *
 * Only the *first* character of the word is capitalized, to also convert the rest of the words to lower-case at the
 * same time, use [String.normalizeWordCasing].
 *
 * **Note:** the `' '` character works as a sort of wildcard, i.e; if `' '` is present in the [delimiters], then this
 * function will match *all* whitespace characters in place of it, and not *just* `' '`.
 *
 * @param [delimiters] the characters that determine what words get capitalized
 */
@JvmOverloads
fun String.capitalizeWords(vararg delimiters: Char = charArrayOf(' ')): String = buildString {
    fun Char.isDelimiter(): Boolean = when {
        ' ' in delimiters -> this.isWhitespace()
        else -> this@isDelimiter in delimiters
    }

    // set to true at the start so that the first word gets capitalized.
    var capitalizeNext = true

    for (char in this@capitalizeWords) {
        when {
            char.isDelimiter() -> {
                append(char)
                capitalizeNext = true
            }
            capitalizeNext -> {
                append(char.toUpperCase())
                capitalizeNext = false
            }
            else -> append(char)
        }
    }
}

/**
 * Capitalizes all the words in the [CharSequence] where the given [predicate] returns `true`.
 *
 * @param [predicate] the `predicate` that determines whether or not the following character will be in upper-case
 *
 * @since 0.6.0
 */
// no inline for now because of possible generated bloat.
fun String.capitalizeWords(predicate: (Char) -> Boolean): String = buildString {
    // set to true at the start so that the first word gets capitalized.
    var capitalizeNext = true

    for (char in this@capitalizeWords) {
        when {
            predicate(char) -> {
                append(char)
                capitalizeNext = true
            }
            capitalizeNext -> {
                append(char.toUpperCase())
                capitalizeNext = false
            }
            else -> append(char)
        }
    }
}

/**
 * Un-capitalizes all the words in the [CharSequence] that are pre-faced by any of the given [delimiters], only the
 * the first character of the word is uncapitalized, the rest are left as is.
 *
 * **Note:** the `' '` character works as a sort of wildcard, i.e; if `' '` is present in the [delimiters], then this
 * function will match *all* whitespace characters in place of it, and not *just* `' '`.
 *
 * @param [delimiters] the characters that determine what words get capitalized
 *
 * (`charArrayOf(' ')` by default)
 *
 * @since 0.6.0
 */
@JvmOverloads
fun String.uncapitalizeWords(vararg delimiters: Char = charArrayOf(' ')): String = buildString {
    fun Char.isDelimiter(): Boolean = when {
        ' ' in delimiters -> this.isWhitespace()
        else -> this@isDelimiter in delimiters
    }

    // set to true at the start so that the first word gets uncapitalized.
    var uncapitalizeNext = true

    for (char in this@uncapitalizeWords) {
        when {
            char.isDelimiter() -> {
                append(char)
                uncapitalizeNext = true
            }
            uncapitalizeNext -> {
                append(char.toLowerCase())
                uncapitalizeNext = false
            }
            else -> append(char)
        }
    }
}

/**
 * Un-capitalizes all the words in the [CharSequence] where the given [predicate] returns `true`.
 *
 * @param [predicate] the `predicate` that determines whether or not the following character will be in lower-case
 */
// no inline for now because of possible generated bloat.
fun String.uncapitalizeWords(predicate: (Char) -> Boolean): String = buildString {
    // set to true at the start so that the first word gets capitalized.
    var uncapitalizeNext = true

    for (char in this@uncapitalizeWords) {
        when {
            predicate(char) -> {
                append(char)
                uncapitalizeNext = true
            }
            uncapitalizeNext -> {
                append(char.toLowerCase())
                uncapitalizeNext = false
            }
            else -> append(char)
        }
    }
}

/**
 * Converts all the words separated by the given [delimiters] into capitalized words, i.e; each word starts with one
 * upper-case character, and the rest of the characters are all in lower-case.
 *
 * **Note:** the `' '` character works as a sort of wildcard, i.e; if `' '` is present in the [delimiters], then this
 * function will match *all* whitespace characters in place of it, and not *just* `' '`.
 *
 * @param [delimiters] the characters that determine what words get capitalized
 */
@JvmOverloads
fun String.normalizeWordCasing(vararg delimiters: Char = charArrayOf(' ')): String =
    this.toLowerCase().capitalizeWords(*delimiters)

/**
 * Returns `true` if all the characters in `this` sequence are upper case, otherwise `false`.
 */
fun CharSequence.isUpperCase(): Boolean = this.all { it.isUpperCase() }

/**
 * Returns `true` if all the characters in `this` sequence are lower case, otherwise `false`.
 */
fun CharSequence.isLowerCase(): Boolean = this.all { it.isLowerCase() }