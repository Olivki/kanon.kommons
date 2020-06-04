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

package moe.kanon.kommons

/**
 * Represents a [Map] where the backing type for the [keys][Map.keys] is always a [String].
 */
typealias StringMap<T> = Map<String, T>

@Deprecated(
    message = "Does not work, use 'JvmStatic' instead.",
    replaceWith = ReplaceWith("JvmStatic", "kotlin.jvm"),
    level = DeprecationLevel.ERROR
)
typealias static = JvmStatic

@Deprecated(
    message = "Does not work, use 'JvmSynthetic' instead.",
    replaceWith = ReplaceWith("JvmSynthetic", "kotlin.jvm"),
    level = DeprecationLevel.ERROR
)
typealias synthetic = JvmSynthetic

@Deprecated(
    message = "Does not work, use 'JvmName' instead.",
    replaceWith = ReplaceWith("JvmName", "kotlin.jvm"),
    level = DeprecationLevel.ERROR
)
typealias name = JvmName

@Deprecated(
    message = "Does not work, use 'JvmOverloads' instead.",
    replaceWith = ReplaceWith("JvmOverloads", "kotlin.jvm"),
    level = DeprecationLevel.ERROR
)
typealias overloads = JvmOverloads