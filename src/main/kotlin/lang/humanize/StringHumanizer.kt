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

@file:JvmName("StringHumanizer")

package moe.kanon.kommons.lang.humanize

import moe.kanon.kommons.humanize.trimDelimiters
import moe.kanon.kommons.lang.isAllUpperCase
import moe.kanon.kommons.lang.normalizeWordCasing

private val CASING_REGEX =
    Regex("(?<=[a-z])(?=[A-Z0-9])|(?<=[0-9])(?=[A-Za-z])|(?<=[A-Z])(?=[0-9])|(?<=[A-Z])(?=[A-Z][a-z])")
private val DASH_REGEX = Regex("$CASING_REGEX|(_)|(-)")

/**
 * Attempts to "humanize" the current [String]
 *
 * @receiver The string to humanize.
 *
 * @since 0.6.0
 */
@JvmOverloads
public fun String.humanize(casing: LetterCase = LetterCase.SENTENCE): String = casing.convert(this)

/**
 * Represents different style of letter casing in a sentence.
 *
 * **Note:** While all the example sentences used in the documentation for each enum are normal sentences, this enum
 * and the [humanize] function are designed to be used with `data` that's *not* very "human readable" for the layman,
 * e.g; `"thisIsACamelCaseSentence"`, `"ThisIsAPascalCaseSentence`, `"this-is-a-kebab-case-sentence"`, etc..
 * If you wish to convert `data` that you know is already "human readable", then there are other functions in this
 * library that would work better.
 *
 * @since 0.6.0
 */
public enum class LetterCase {

    /**
     * Represents the sentence case style, i.e:
     *
     * - The first word in the sentence will always be capitalized, i.e;
     *
     *    "patience is a virtue"` -> `"Patience is a virtue"`.
     * - If by itself, the letter `'i'` will always be capitalized, i.e;
     *
     *    `"Am i truly alive?"` -> `"Am I truly alive?"`
     * - Any fully upper-case words will be left as is, i.e;
     *
     *    `"I absolutely LOVE animals"` -> `"I absolutely LOVE animals"`.
     *
     * @since 0.6.0
     */
    SENTENCE {
        override fun convert(input: String): String = input.split(DASH_REGEX).joinToString(" ") {
            if (it.isAllUpperCase() && it.length > 1) it else it.toLowerCase()
        }.replace(" i ", " I ").capitalize()
    },

    /**
     * Represents a basic implementation of a title case style, i.e;
     *
     * - The first word of the given `input` will always be capitalized.
     * - Any word that is longer than 3 letter will be capitalized, if it shorter than that, it will be uncapitalized;
     *
     *    `"the greAtEst Beam To ever Live"` -> `"The Greatest Beam to Ever Live"`
     * - Unlike the [sentence-case][SENTENCE], fully upper-case words will *not* be left as is;
     *
     *     `"THE STORY OF A MACARON-LOVING GIRL WHO LIVED A THOUSAND YEARS SOMEHOW."` -> `"The Story of a Macaron
     *     Loving Girl who Lived a Thousand Years Somehow."`
     *
     * @since 0.6.0
     */
    TITLE {
        override fun convert(input: String): String {
            return input.split(DASH_REGEX).joinToString(" ") {
                if (it.length > 3) it.toLowerCase().capitalize() else it.toLowerCase()
            }.capitalize()
        }
    },

    /**
     * Represents a casing style where all the words in a sentence are capitalized;
     *
     * - The first character of a word will always be in upper-case;
     *
     *     `"my name is joseph stein"` -> `"My Name Is Joseph Stein"`
     * - The rest of the characters will all be in lower-case;
     *
     *     `"TO BE, OR NOT TO BE, THAT IS THE QUESTION"` -> `"To Be, Or Not To Be, That Is The Question"`
     *
     * @since 0.6.0
     */
    NORMALIZED {
        override fun convert(input: String): String = input.replace(DASH_REGEX, " $0").normalizeWordCasing()
    },

    /**
     * Represents a casing style where the entire sentence is in upper-case;
     *
     * - `"i'm very angry"` -> `"I'M VERY ANGRY"`
     * - `"NOT ALL THOSE WHO WANDER ARE LOST"` -> `"NOT ALL THOSE WHO WANDER ARE LOST"`
     *
     * @since 0.6.0
     */
    UPPER_CASE {
        override fun convert(input: String): String = input.replace(DASH_REGEX, " $0").trimDelimiters().toUpperCase()
    },

    /**
     * Represents a casing style where the entire sentence is in lower-case;
     *
     * - `"Get busy living, or get busy dying"` -> `"get busy living, or get busy dying"`
     * - `"DON'T PANIC"` -> `"don't panic"`
     *
     * @since 0.6.0
     */
    LOWER_CASE {
        override fun convert(input: String): String = input.replace(DASH_REGEX, " $0").trimDelimiters().toLowerCase()
    },

    /**
     * Represents a casing style that's exactly the same as the given input, but it's transformed into a more readable
     * format, e.g;
     *
     * - `"thisIsCamelCase"` -> `"this Is Camel Case"`
     * - `"ThisIsPascalCase"` -> `"This Is Pascal Case"`
     * - `"this-is-kebab-case"` -> `"this is kebab case"`
     * - `"THIS_IS_SCREAMING_SNAKE_CASE"` -> `"THIS IS SCREAMING SNAKE CASE"`
     *
     * @since 0.6.0
     */
    NONE {
        override fun convert(input: String): String = input.replace(DASH_REGEX, " $0")
    };

    /**
     * Converts the given [input] the the appropriate output letter casing style.
     *
     * @param [input] The string to convert the casing of.
     *
     * @since 0.6.0
     */
    public abstract fun convert(input: String): String
}