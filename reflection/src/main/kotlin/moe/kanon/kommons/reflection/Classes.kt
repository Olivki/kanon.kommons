/*
 * Copyright 2020 Oliver Berg
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

@file:JvmName("KClasses")
@file:Suppress("UNCHECKED_CAST")

package moe.kanon.kommons.reflection

import kotlin.reflect.KClass

/**
 * Returns `true` if `this` is a "native" Kotlin class, otherwise `false`.
 */
val Class<*>.isKotlinClass: Boolean
    get() = this.declaredAnnotations.any { it.annotationClass.qualifiedName == "kotlin.Metadata" }

/**
 * Returns `true` if `this` is a "native" Kotlin class, otherwise `false`.
 */
val KClass<*>.isKotlinClass: Boolean get() = this.java.isKotlinClass

/**
 * Returns `true` if `this` [class][Class] is a `object`, otherwise `false`.
 */
val Class<*>.isKotlinObject: Boolean
    get() = this.isKotlinClass && this.declaredFields.any { it.type == this && it.name == "INSTANCE" }

/**
 * Returns `true` if `this` [class][Class] is a `object`, otherwise `false`.
 */
val KClass<*>.isObject: Boolean get() = this.java.isKotlinObject

/**
 * Returns the [KClass] instance that matches the given [fqName], or throws a [ClassNotFoundException] if none is found
 * on the given [loader].
 *
 * @throws [ClassNotFoundException] if no class could be found on the given [loader] with the given [fqName]
 */
@Throws(ClassNotFoundException::class)
fun classOf(
    fqName: String,
    initialize: Boolean = false,
    loader: ClassLoader = ClassLoader.getSystemClassLoader()
): KClass<*> = Class.forName(fqName, initialize, loader).kotlin

