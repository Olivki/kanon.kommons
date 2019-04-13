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

@file:JvmName("DeepDarkSecrets")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.misc

typealias Bool = Boolean
typealias BoolArray = BooleanArray
typealias BoolIterator = BooleanIterator

// some men just want to watch the world burn
@DslMarker
internal annotation class FakeKeywordMarker

/**
 * `true`
 */
@FakeKeywordMarker
const val yes: Boolean = true

/**
 * `false`
 */
@FakeKeywordMarker
const val no: Boolean = false

/**
 * `this == that`
 */
inline infix fun <A : Any> A.isEqualTo(that: A): Boolean = this == that

/**
 * `this > that`
 */
inline infix fun <C : Comparable<C>> C.isGreaterThan(that: C): Boolean = this > that

/**
 * `this >= that`
 */
inline infix fun <C : Comparable<C>> C.isGreaterThanOrEqualTo(that: C): Boolean = this >= that

/**
 * `this < that`
 */
inline infix fun <C : Comparable<C>> C.isLesserThan(that: C): Boolean = this < that

/**
 * `this <= that`
 */
inline infix fun <C : Comparable<C>> C.isLesserThanOrEqualTo(that: C): Boolean = this <= that