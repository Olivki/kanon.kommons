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
 * ========================= CEYLON LICENSE =========================
 * Copyright (c) 2011-2017 Red Hat Inc. and/or its affiliates and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 */

@file:JvmName("KEnums")

package moe.kanon.kommons.lang

import moe.kanon.kommons.OverflowException
import moe.kanon.kommons.PortOf

// Should the names here be changed to 'enumOf' rather than 'valueOf'?

@PublishedApi
internal inline fun <reified E : Enum<E>> notFound(extra: () -> String): Nothing =
    throw NoSuchElementException("Could find no constant of enum <${E::class.java.name}> ${extra()}")

/**
 * Returns the first enum that matches the given [predicate], or `null` if none is found.
 *
 * @param [predicate] the `predicate` to match for
 */
inline fun <reified E : Enum<E>> valueOfOrNull(predicate: (E) -> Boolean): E? = enumValues<E>().find { predicate(it) }

/**
 * Returns the first enum that matches the given [predicate], or [default] if none is found.
 */
inline fun <reified E : Enum<E>> valueOfOrElse(predicate: (E) -> Boolean, default: E): E =
    valueOfOrNull<E>(predicate) ?: default


/**
 * Returns the first enum that has a matching [name][Enum.name] to the given [name], or `null` if none is found.
 */
inline fun <reified E : Enum<E>> valueOfOrNull(name: String, ignoreCase: Boolean = false): E? =
    valueOfOrNull<E> { it.name.equals(name, ignoreCase) }

/**
 * Returns the first enum that has a matching [name][Enum.name] to the given [name], or [default] if none is found.
 */
inline fun <reified E : Enum<E>> valueOfOrElse(name: String, ignoreCase: Boolean = false, default: E): E =
    valueOfOrNull<E> { it.name.equals(name, ignoreCase) } ?: default

/**
 * Returns the first enum that matches the given [predicate], or throws a [NoSuchElementException] if none is found.
 *
 * @param [predicate] the `predicate` to match for
 */
inline fun <reified E : Enum<E>> valueOf(predicate: (E) -> Boolean): E? =
    enumValues<E>().find { predicate(it) } ?: notFound<E> { "matching the given predicate" }

/**
 * Returns the first enum that has a matching [name][Enum.name] to the given [name], or throws a
 * [NoSuchElementException] if none is found.
 */
inline fun <reified E : Enum<E>> valueOf(name: String, ignoreCase: Boolean = false): E =
    valueOfOrNull<E> { it.name.equals(name, ignoreCase) } ?: notFound<E> { "with the name <$name>" }


/**
 * Returns the enum located at the given [index], or `null` if `index` is out of bounds.
 */
inline fun <reified E : Enum<E>> valueAtOrNull(index: Int): E? = enumValues<E>().getOrNull(index)

/**
 * Returns the enum located at the given [index], or [default] if `index` is out of bounds.
 */
inline fun <reified E : Enum<E>> valueAtOrElse(index: Int, default: E): E = valueAtOrNull<E>(index) ?: default

/**
 * Returns the enum located at the given [index], or throws a [NoSuchElementException] if `index` is out of bounds.
 */
inline fun <reified E : Enum<E>> valueAt(index: Int): E =
    valueAtOrNull<E>(index) ?: notFound<E> { "with the index <$index>" }

// -- EXTENSIONS -- \\
/**
 * Returns a [normalized][String.normalizeWordCasing] version of `this` enums [name][Enum.name].
 *
 * Examples:
 *
 * ```kotlin
 *  Enum.ENUM.normalizedName -> "Enum"
 *  Enum.ENUM_ONE.normalizedName -> "Enum One"
 *  Enum.MORE_VERBOSE_ENUM.normalizedName -> "More Verbose Enum"
 * ```
 */
inline val <reified E : Enum<E>> E.normalizedName: String
    get() = this.name.normalizeWordCasing('_')

/**
 * The indirect successor or predecessor at the given [offset], where:
 *
 * - `x.neighbour(0) == x`,
 * - `x.neighbour(i+1) == x.neighbour(i).successor`, and
 * - `x.neighbour(i-1) == x.neighbour(i).predecessor`.
 *
 * If the given `offset` is out of bounds, a [OverflowException] will be thrown.
 *
 * ### Port-Of Links
 * 1. [ceylon.language.Enumerable#neighbour](https://github.com/eclipse/ceylon/blob/master/language/src/ceylon/language/Enumerable.ceylon#L76)
 */
@PortOf("ceylon.language.Enumerable#neighbour")
inline infix fun <reified E : Enum<E>> E.neighbour(offset: Int): E =
    enumValues<E>().getOrNull(this.ordinal + offset)
        ?: throw OverflowException("<${ordinal + offset}> is outside of the range 0..${enumValues<E>().size}")

// TODO: Documentation

inline val <reified E : Enum<E>> E.successor: E
    @JvmName("successorOf")
    get() = this.neighbour(1)

inline val <reified E : Enum<E>> E.predecessor: E
    @JvmName("predecessorOf")
    get() = this.neighbour(-1)