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

@file:Suppress("NOTHING_TO_INLINE", "FunctionName")

package moe.kanon.kommons

@PublishedApi
internal const val UNSUPPORTED_OPERATION = "Operation is not supported"

/**
 * Throws a [UnsupportedOperationException] with the specified [message].
 */
inline fun UNSUPPORTED(message: String): Nothing = throw UnsupportedOperationException(message)

/**
 * Throws a [UnsupportedOperationException] with a default message.
 */
inline fun UNSUPPORTED(): Nothing = throw UnsupportedOperationException(UNSUPPORTED_OPERATION)

@JvmOverloads
inline fun writeOut(out: Any? = "") = println(out)

@JvmOverloads
inline fun writeError(out: Any? = "") = System.err.println(out)

@JvmOverloads
fun readIn(out: Any? = ""): String? {
    if (out != null) writeOut(out)
    return readLine()
}