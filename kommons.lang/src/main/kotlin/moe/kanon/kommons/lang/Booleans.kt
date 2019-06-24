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
 * ========================= APACHE COMMONS LICENSE =========================
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmName("Booleans")
@file:Suppress("NOTHING_TO_INLINE", "PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package moe.kanon.kommons.lang

import moe.kanon.kommons.PortOf

/**
 * A type-alias for the java [Boolean][java.lang.Boolean] class.
 *
 * Kotlin *strongly* discourages the use of the Java primitive classes over its own, for very good reasons. And as such
 * this should only be used in very rare circumstances.
 */
typealias JBoolean = java.lang.Boolean
typealias Bool = Boolean

/**
 * Returns the binary representation of `this` boolean.
 */
val Boolean.binary: Int get() = if (this) 1 else 0

/**
 * Returns the hashCode of the specified [bool].
 *
 * @return `1231` if `bool` is `true`; `1237` if `bool` is `false`.
 */
fun Boolean.Companion.hashOf(bool: Boolean): Int = if (bool) 1231 else 1237

/**
 * Returns the hashCode of `this` boolean.
 *
 * @return `1231` if `this` is `true`; `1237` if `this` is `false`.
 */
val Boolean.hash: Int get() = if (this) 1231 else 1237

private inline fun String.raiseParseException(): Nothing = throw ParseException(this, "<$this> is not a Boolean value")

/**
 * Converts the given [value] to a [Boolean].
 *
 * `"true"`, `"on"`, `"y"`, `"t"` or `"yes"` *(case insensitive)* will return `true`.
 *
 * `"false"`, `"off"`, `"n"`, `"f"` or `"no"` *(case insensitive)* will return `false`.
 *
 * ```kotlin
 *   // N.B. case is not significant
 *   Boolean.parse(null)    = Won't compile.
 *   Boolean.parse("true")  = true
 *   Boolean.parse("T")     = true // i.e. T[RUE]
 *   Boolean.parse("false") = false
 *   Boolean.parse("f")     = false // i.e. f[alse]
 *   Boolean.parse("No")    = false
 *   Boolean.parse("n")     = false // i.e. n[o]
 *   Boolean.parse("on")    = true
 *   Boolean.parse("ON")    = true
 *   Boolean.parse("off")   = false
 *   Boolean.parse("oFf")   = false
 *   Boolean.parse("yes")   = true
 *   Boolean.parse("Y")     = true // i.e. Y[ES]
 *   Boolean.parse("blue")  = throws ParseException
 *   Boolean.parse("true ") = throws ParseException // Trailing space (too long).
 *   Boolean.parse("ono")   = throws ParseException // Does not match on or no.
 * ```
 *
 * ### Port Details
 * [org.apache.commons.lang.BooleanUtils#toBooleanObject](https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/commons/lang3/BooleanUtils.java#L546)
 *
 * @param [value] the [String] to check; **UPPER** and **lower** case are treated as the same
 *
 * @throws [ParseException] if the given [value] could not be converted into a [Boolean].
 *
 * @author Apache Software Foundation
 */
@Throws(ParseException::class)
@PortOf("org.apache.commons.lang3.BooleanUtils#toBooleanObject")
fun Boolean.Companion.parse(value: String) = when (value.length) {
    // Previously used equalsIgnoreCase, which was fast for interned 'true'.
    // Non interned 'true' matched 15 times slower.
    //
    // Optimisation provides same performance as before for interned 'true'.
    // Similar performance for null, 'false', and other strings not length 2/3/4.
    // 'true'/'TRUE' match 4 times slower, 'tRUE'/'True' 7 times slower
    1 -> when (value[0]) {
        'y', 'Y', 't', 'T' -> true
        'n', 'N', 'f', 'F' -> false
        else -> value.raiseParseException()
    }
    2 -> {
        val ch0 = value[0]
        val ch1 = value[1]
        when {
            (ch0 == 'o' || ch0 == 'O') && (ch1 == 'n' || ch1 == 'N') -> true
            (ch0 == 'n' || ch0 == 'N') && (ch1 == 'o' || ch1 == 'O') -> false
            else -> value.raiseParseException()
        }
    }
    3 -> {
        val ch0 = value[0]
        val ch1 = value[1]
        val ch2 = value[2]
        when {
            (ch0 == 'y' || ch0 == 'Y') && (ch1 == 'e' || ch1 == 'E') && (ch2 == 's' || ch2 == 'S') -> true
            (ch0 == 'o' || ch0 == 'O') && (ch1 == 'f' || ch1 == 'F') && (ch2 == 'f' || ch2 == 'F') -> false
            else -> value.raiseParseException()
        }
    }
    4 -> {
        val ch0 = value[0]
        val ch1 = value[1]
        val ch2 = value[2]
        val ch3 = value[3]
        when {
            value == "true" -> true
            (ch0 == 't' || ch0 == 'T') && (ch1 == 'r' || ch1 == 'R') && (ch2 == 'u' || ch2 == 'U') &&
                (ch3 == 'e' || ch3 == 'E') -> true
            else -> value.raiseParseException()
        }
    }
    5 -> {
        val ch0 = value[0]
        val ch1 = value[1]
        val ch2 = value[2]
        val ch3 = value[3]
        val ch4 = value[4]
        when {
            (ch0 == 'f' || ch0 == 'F') && (ch1 == 'a' || ch1 == 'A') && (ch2 == 'l' || ch2 == 'L') &&
                (ch3 == 's' || ch3 == 'S') && (ch4 == 'e' || ch4 == 'E') -> false
            else -> value.raiseParseException()
        }
    }
    else -> value.raiseParseException()
}
