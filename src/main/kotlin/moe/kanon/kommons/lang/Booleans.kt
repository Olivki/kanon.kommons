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
 *
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

@file:JvmName("KBooleans")

package moe.kanon.kommons.lang

/**
 * Converts the given [value] to a [Boolean].
 *
 * *This function is originally from `Apache Commons Lang 3`, specifically the
 * [BooleanUtils.java](https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/commons/lang3/BooleanUtils.java#L546)
 * class.*
 *
 * `"true"`, `"on"`, `"y"`, `"t"` or `"yes"` *(case insensitive)* will return `true`.
 *
 * `"false"`, `"off"`, `"n"`, `"f"` or `"no"` *(case insensitive)* will return `false`.
 *
 * **NOTE**: This returns null and will throw a NullPointerException if unboxed to a boolean.
 *
 * ```kotlin
 *   // N.B. case is not significant
 *   convert(null)    = Won't compile.
 *   convert("true")  = true
 *   convert("T")     = true // i.e. T[RUE]
 *   convert("false") = false
 *   convert("f")     = false // i.e. f[alse]
 *   convert("No")    = false
 *   convert("n")     = false // i.e. n[o]
 *   convert("on")    = true
 *   convert("ON")    = true
 *   convert("off")   = false
 *   convert("oFf")   = false
 *   convert("yes")   = true
 *   convert("Y")     = true // i.e. Y[ES]
 *   convert("blue")  = throws BooleanParseException
 *   convert("true ") = throws BooleanParseException // Trailing space (too long).
 *   convert("ono")   = throws BooleanParseException // Does not match on or no.
 * ```
 *
 * @param [value] the [String] to check; **UPPER** and **lower** case are treated as the same
 *
 * @throws [BooleanParseException] if the given [value] could not be converted into a [Boolean].
 *
 * @author Apache Software Foundation
 *
 * @since 0.6.0
 */
@Throws(BooleanParseException::class)
fun Boolean.Companion.parse(value: String) = when (value.length) {
    // Previously used equalsIgnoreCase, which was fast for interned 'true'.
    // Non interned 'true' matched 15 times slower.
    //
    // Optimisation provides same performance as before for interned 'true'.
    // Similar performance for null, 'false', and other strings not length 2/3/4.
    // 'true'/'TRUE' match 4 times slower, 'tRUE'/'True' 7 times slower
    1 -> {
        when (value[0]) {
            'y', 'Y', 't', 'T' -> true
            'n', 'N', 'f', 'F' -> false
            else -> throw BooleanParseException("<$value> is not a boolean value")
        }
    }
    2 -> {
        val ch0 = value[0]
        val ch1 = value[1]
        when {
            (ch0 == 'o' || ch0 == 'O') && (ch1 == 'n' || ch1 == 'N') -> true
            (ch0 == 'n' || ch0 == 'N') && (ch1 == 'o' || ch1 == 'O') -> false
            else -> throw BooleanParseException("<$value> is not a boolean value")
        }
    }
    3 -> {
        val ch0 = value[0]
        val ch1 = value[1]
        val ch2 = value[2]
        when {
            (ch0 == 'y' || ch0 == 'Y') &&
                (ch1 == 'e' || ch1 == 'E') &&
                (ch2 == 's' || ch2 == 'S') -> true
            (ch0 == 'o' || ch0 == 'O') &&
                (ch1 == 'f' || ch1 == 'F') &&
                (ch2 == 'f' || ch2 == 'F') -> false
            else -> throw BooleanParseException("<$value> is not a boolean value")
        }
    }
    4 -> {
        val ch0 = value[0]
        val ch1 = value[1]
        val ch2 = value[2]
        val ch3 = value[3]
        when {
            value == "true" -> true
            (ch0 == 't' || ch0 == 'T') &&
                (ch1 == 'r' || ch1 == 'R') &&
                (ch2 == 'u' || ch2 == 'U') &&
                (ch3 == 'e' || ch3 == 'E') -> true
            else -> throw BooleanParseException("<$value> is not a boolean value")
        }
    }
    5 -> {
        val ch0 = value[0]
        val ch1 = value[1]
        val ch2 = value[2]
        val ch3 = value[3]
        val ch4 = value[4]
        when {
            (ch0 == 'f' || ch0 == 'F') &&
                (ch1 == 'a' || ch1 == 'A') &&
                (ch2 == 'l' || ch2 == 'L') &&
                (ch3 == 's' || ch3 == 'S') &&
                (ch4 == 'e' || ch4 == 'E') -> false
            else -> throw BooleanParseException("<$value> is not a boolean value")
        }
    }
    else -> throw BooleanParseException("<$value> is not a boolean value")
}

fun Boolean.Companion.tryParse(value: String): Try<Boolean> = Try { this.parse(value) }

/**
 * Thrown if any problems were encountered when parsing a value into a boolean.
 */
open class BooleanParseException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    Exception(message, cause)