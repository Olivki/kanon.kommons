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

@file:JvmName("CaseConverter")

package moe.kanon.kommons.humanize

private val CASING_REGEX =
    Regex("(?<=[a-z])(?=[A-Z0-9])|(?<=[0-9])(?=[A-Za-z])|(?<=[A-Z])(?=[0-9])|(?<=[A-Z])(?=[A-Z][a-z])")
private val DASH_CASING_REGEX = Regex("$CASING_REGEX|(_)|(-)")

/**
 * @since 0.6.0
 */
// Title Case
public fun String.toTitleCase(minLength: Int = 4): String = TODO("implement this")


/**
 * Converts the casing format of this [String] into the specified [format], and returns the result.
 *
 * @param [format] The desired [CaseFormat] to convert to.
 *
 * @since 0.6.0
 */
public fun String.toCase(format: CaseFormat): String = format.convert(this)

// TODO: Documentation

internal fun String.trimDelimiters(): String = this.replace("(_|-)".toRegex(), "")

/**
 * Contains all the different types of case-formatting available.
 *
 * @since 0.6.0
 */
public enum class CaseFormat {

    /**
     * Sentence case
     */
    SENTENCE_CASE {
        override fun convert(input: String): String {
            TODO("not implemented")
        }
    },

    /**
     * camelCase
     */
    CAMEL_CASE {
        override fun convert(input: String): String {
            TODO("not implemented")
            var shouldCapitalize = false
            var result = ""

            for (char in input.decapitalize()) {
                if (char == '_' || char.isWhitespace()) shouldCapitalize = true
                if (Character.isLetterOrDigit(char) && shouldCapitalize) {
                    shouldCapitalize = false
                    result = "$result${char.toUpperCase()}"
                } else result = "$result$char"
            }

            return result.trimDelimiters()
        }
    },

    /**
     * pascalCase
     */
    PASCAL_CASE {
        override fun convert(input: String): String {
            TODO("not implemented")
            var previousWasUnderscore = false
            var result = ""

            for (char in input.capitalize()) {
                if (char == '_') previousWasUnderscore = true
                if (Character.isLetterOrDigit(char) && previousWasUnderscore) {
                    previousWasUnderscore = false
                    result = "$result${char.toUpperCase()}"
                } else result = "$result$char"
            }

            return result.replace("_", "")
        }
    },

    /**
     * kebab-case
     */
    KEBAB_CASE {
        override fun convert(input: String): String {
            TODO("not implemented")
        }
    },

    /**
     * Train-Case
     */
    TRAIN_CASE {
        override fun convert(input: String): String {
            TODO("not implemented")
        }
    },

    /**
     * snake_case
     */
    SNAKE_CASE {
        override fun convert(input: String): String {
            TODO("not implemented")
            var previousWasCapital = false
            var result = ""

            for (char in input.decapitalize()) {
                if (Character.isUpperCase(char)) previousWasCapital = true
                if (Character.isLetterOrDigit(char) && previousWasCapital) {
                    previousWasCapital = false
                    result = "${result}_${char.toLowerCase()}"
                } else result = "$result$char"
            }

            return result.replace(" ", "_").toLowerCase()
        }
    },

    /**
     * Darwin_Case
     */
    DARWIN_CASE {
        override fun convert(input: String): String {
            TODO("not implemented")
            var previousWasCapital = false
            var result = ""

            for (char in input.decapitalize()) {
                if (Character.isUpperCase(char)) previousWasCapital = true
                if (Character.isLetterOrDigit(char) && previousWasCapital) {
                    previousWasCapital = false
                    result = "${result}_${char.toUpperCase()}"
                } else result = "$result$char"
            }

            return result.replace(" ", "_")
        }
    },

    /**
     * StUdLyCaPs/STuDLyCaPS (basically random gibberish)
     */
    STUDLY_CASE {
        override fun convert(input: String): String {
            TODO("not implemented")
        }
    };

    /**
     * Converts the casing of the given [input] into the appropriate casing.
     *
     * @param [input] The string to convert the casing of.
     */
    abstract fun convert(input: String): String
}