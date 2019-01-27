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

@file:JvmName("CaseUtils")

package moe.kanon.kextensions.humanize

/**
 * @since 0.6.0
 */
// Title Case
// TODO: Implement a proper title case converter based on https://titlecaseconverter.com/rules/
public fun String.toTitleCase(minLength: Int = 4): String = ""

public fun String.toCase(style: CaseStyles): String = style.convert(this)

/**
 * @since 0.6.0
 */
public enum class CaseStyles {

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
        }
    },

    /**
     * pascalCase
     */
    PASCAL_CASE {
        override fun convert(input: String): String {
            TODO("not implemented")
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
        }
    },

    /**
     * Darwin_Case
     */
    DARWIN_CASE {
        override fun convert(input: String): String {
            TODO("not implemented")
        }
    },

    /**
     * StUdLyCaPs/STuDLyCaPS
     */
    STUDLY_CASE {
        override fun convert(input: String): String {
            TODO("not implemented")
        }
    };

    abstract fun convert(input: String): String
}