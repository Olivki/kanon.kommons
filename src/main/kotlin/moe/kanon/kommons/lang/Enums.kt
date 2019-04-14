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

@file:JvmName("KEnums")

package moe.kanon.kommons.lang

/**
 * A normalized version of this enums [name][Enum.name].
 *
 * Examples:
 *
 * ```kotlin
 *  Enum.ENUM.normalizedName = "Enum"
 *  Enum.ENUM_ONE.normalizedName = "Enum One"
 *  Enum.MORE_VERBOSE_ENUM.normalizedName = "More Verbose Enum"
 * ```
 */
inline val <reified E : Enum<E>> E.normalizedName: String get() = this.name.normalizeWordCasing('_')

/**
 * Returns an enum that matches the specified [predicate], or `null` if none is found.
 *
 * @param [predicate] The `predicate` to match for.
 */
inline fun <reified E : Enum<E>> valueOf(predicate: (E) -> Boolean): E? = enumValues<E>().find { predicate(it) }

/**
 * Returns an enum that matches the specified [predicate], or [default] if none is found.
 *
 * @param [default] The value to return back if no match is found.
 * @param [predicate] The `predicate` to match for.
 */
inline fun <reified E : Enum<E>> valueOf(default: E, predicate: (E) -> Boolean): E =
    valueOf<E> { predicate(it) } ?: default

/**
 * Returns an enum that has a [name][Enum.name] matching the specified [name], or [default] if none can be found.
 *
 * @param [name] The [name][Enum.name] to match against.
 * @param [default] The value to return back if no match is found.
 * @param [ignoreCase] Whether or not the name check should be done while ignoring any case differences.
 *
 * (`false` by default)
 */
@JvmOverloads
inline fun <reified E : Enum<E>> valueOf(name: String, default: E, ignoreCase: Boolean = false): E =
    valueOf(default) { it.name.equals(name, ignoreCase) }

/**
 * Returns an enum that has a [normalizedName][normalizedName] matching the specified [name], or [default] if none can
 * be found.
 *
 * @param [name] The [name][normalizedName] to match against.
 * @param [default] The value to return back if no match is found.
 * @param [ignoreCase] Whether or not the name check should be done while ignoring any case differences.
 *
 * (`false` by default)
 */
@JvmOverloads
inline fun <reified E : Enum<E>> valueOfNormalizedName(name: String, default: E, ignoreCase: Boolean = false): E =
    valueOf(default) { it.name.equals(name, ignoreCase) }

/**
 * Returns an enum that has a [ordinal][Enum.ordinal] matching the specified [ordinal], or [default] if none can be
 * found.
 *
 * @param [ordinal] The [ordinal][Enum.ordinal] to match against.
 * @param [default] The value to return back if no match is found.
 */
inline fun <reified E : Enum<E>> valueOf(ordinal: Int, default: E): E =
    valueOf(default) { it.ordinal == ordinal }