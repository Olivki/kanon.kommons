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

@file:JvmName("KFuncs")

package moe.kanon.kommons.func

/**
 * Returns a predicate function which returns `true` if *all* of the specified [predicates] return `true`, otherwise
 * it returns `false`.
 */
fun <T> allOf(vararg predicates: (T) -> Boolean): (T) -> Boolean = { item -> predicates.all { it(item) } }

/**
 * Returns a predicate function which returns `true` if *all* of the predicates in `this` collection return `true`,
 * otherwise it returns `false`.
 */
fun <T> Iterable<(T) -> Boolean>.allOf(): (T) -> Boolean = { item -> this.all { it(item) } }

/**
 * Returns a predicate function which returns `true` if *any* of the specified [predicates] return `true`, otherwise
 * it returns `false`.
 */
fun <T> anyOf(vararg predicates: (T) -> Boolean): (T) -> Boolean = { item -> predicates.any { it(item) } }

/**
 * Returns a predicate function which returns `true` if *any* of the predicates in `this` collection return `true`,
 * otherwise it returns `false`.
 */
fun <T> Iterable<(T) -> Boolean>.anyOf(): (T) -> Boolean = { item -> this.any { it(item) } }

/**
 * Returns a predicate function which returns `true` if *none* of the specified [predicates] return `true`, otherwise
 * it returns `false`.
 */
fun <T> noneOf(vararg predicates: (T) -> Boolean): (T) -> Boolean = { item -> predicates.none { it(item) } }

/**
 * Returns a predicate function which returns `true` if *none* of the predicates in `this` collection return `true`,
 * otherwise it returns `false`.
 */
fun <T> Iterable<(T) -> Boolean>.noneOf(): (T) -> Boolean = { item -> this.none { it(item) } }