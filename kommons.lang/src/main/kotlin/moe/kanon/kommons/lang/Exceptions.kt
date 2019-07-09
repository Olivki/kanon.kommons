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

package moe.kanon.kommons.lang

import java.lang.Exception

/**
 * Thrown to indicate that a problem occurred when attempting to parse a string into a value.
 *
 * @property [string] The string that caused `this` exception to be thrown.
 */
open class ParseException @JvmOverloads constructor(
    val string: String,
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {
    companion object {
        @JvmStatic fun from(string: String): ParseException =
            ParseException(string, "Failed to parse the string <$string>")

        @JvmStatic fun from(string: String, cause: Throwable): ParseException =
            ParseException(string, "Failed to parse the string <$string>, cause <${cause.message}>", cause)
    }
}