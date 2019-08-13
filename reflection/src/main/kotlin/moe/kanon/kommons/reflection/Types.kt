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

@file:JvmName("KTypes")

package moe.kanon.kommons.reflection

import moe.kanon.kommons.FakeKeyword
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.isSupertypeOf
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.typeOf

/*
 * With Kotlin 1.3.40 the "typeOf" function has been implemented into the Kotlin std-lib for the JVM, which means that
 * we do not need to port the entire "TypeToken" system from Guava to Kotlin, and instead we can just provide utility
 * extension functions that operate on KType instances, making for a nicer experience for the user, avoiding having
 * to wrap the types in more layers.
 */

// ugly hack to circumvent having to use either 'UseExperimental' or the 'ExperimentalStdLibApi' annotation every time
// one wants to use the 'typeOf' function
//@FakeKeyword
//@UseExperimental(ExperimentalStdlibApi::class)
//inline fun <reified T> type(): KType = typeOf<T>()

/**
 * Returns `true` if `this` type contains the given [other] type, `false` otherwise.
 */
operator fun KType.contains(other: KType): Boolean = this.arguments.any { it.type == other }

/**
 * Returns `true` if `this` type contains the given [clz] class as a star projected type, `false` otherwise.
 */
operator fun KType.contains(clz: KClass<*>): Boolean = this.arguments.any { it.type == clz.starProjectedType }

/**
 * Returns `true` if `this` type contains the given [T] type, `false` otherwise.
 */
@UseExperimental(ExperimentalStdlibApi::class)
inline fun <reified T> KType.hasType(): Boolean = this.arguments.any { it.type == typeOf<T>() }

/**
 * Returns an `iterator` that iterates over the [arguments][KType.arguments] of `this` type.
 */
operator fun KType.iterator(): Iterator<KTypeProjection> = this.arguments.iterator()

/**
 * Returns `true` if `this` type is the same or is a supertype of [clz], `false` otherwise.
 */
fun KType.isSupertypeOf(clz: KClass<*>): Boolean = this.isSupertypeOf(clz.starProjectedType)

/**
 * Returns `true` if `this` type is the same or is a supertype of [T], `false` otherwise.
 */
@UseExperimental(ExperimentalStdlibApi::class)
inline fun <reified T> KType.isSupertypeOf(): Boolean = this.isSupertypeOf(typeOf<T>())

/**
 * Returns `true` if `this` type is the same or is a subtype of [clz], `false` otherwise.
 */
fun KType.isSubtypeOf(clz: KClass<*>): Boolean = this.isSubtypeOf(clz.starProjectedType)

/**
 * Returns `true` if `this` type is the same or is a subtype of [T], `false` otherwise.
 */
@UseExperimental(ExperimentalStdlibApi::class)
inline fun <reified T> KType.isSubtypeOf(): Boolean = this.isSubtypeOf(typeOf<T>())