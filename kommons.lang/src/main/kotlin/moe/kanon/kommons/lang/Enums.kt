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
 * ========================= CEYLON LICENSE =========================
 * Copyright (c) 2011-2017 Red Hat Inc. and/or its affiliates and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 */

@file:JvmName("Enums")

package moe.kanon.kommons.lang

import moe.kanon.kommons.FakeKeyword
import moe.kanon.kommons.OverflowException
import moe.kanon.kommons.PortOf

// -- TOP-LEVEL -- \\
@PublishedApi internal inline fun <reified E : Enum<E>> ENUM_NOT_FOUND(extra: () -> String): Nothing =
    throw NoSuchElementException("Could find no constant of enum <${E::class.java.name}> ${extra()}")

inline fun <reified E : Enum<E>> getEnum(name: String, ignoreCase: Boolean = false): E =
    enumValues<E>().find { it.name.equals(name, ignoreCase) } ?: ENUM_NOT_FOUND<E> { "with the name <$name>" }

inline fun <reified E : Enum<E>> getEnumOrNull(name: String, ignoreCase: Boolean = false): E? =
    enumValues<E>().find { it.name.equals(name, ignoreCase) }

inline fun <reified E : Enum<E>> getEnum(index: Int): E =
    enumValues<E>().find { it.ordinal == index } ?: ENUM_NOT_FOUND<E> { "with the index <$index>" }

inline fun <reified E : Enum<E>> getEnumOrNull(index: Int): E? =
    enumValues<E>().find { it.ordinal == index }

// -- EXTENSIONS -- \\
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
inline fun <reified E : Enum<E>> E.neighbour(offset: Int): E =
    enumValues<E>().getOrNull(this.ordinal + offset) ?: throw OverflowException("<${ordinal + offset}> is outside of the range 0..${enumValues<E>().size}")

// TODO: Documentation

inline val <reified E : Enum<E>> E.successor: E @JvmName("getSuccessorOf") get() = this.neighbour(1)

inline val <reified E : Enum<E>> E.predecessor: E @JvmName("getPredecessorOf") get() = this.neighbour(-1)