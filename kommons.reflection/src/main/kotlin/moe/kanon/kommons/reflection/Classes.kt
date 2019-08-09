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

@file:JvmName("KClasses")
@file:Suppress("UNCHECKED_CAST")

package moe.kanon.kommons.reflection

import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

/**
 * Returns whether or nor `this` [class][Class] is a [kotlin class][KClass].
 */
val Class<*>.isKotlinClass: Boolean get() = this.annotations.any { it.javaClass.name == "kotlin.Metadata" }

val KClass<*>.isKotlinClass: Boolean get() = this.java.isKotlinClass

/**
 * Returns whether or not `this` class is an `object`.
 */
val KClass<*>.isObject: Boolean get() = this.objectInstance != null

fun classOf(
    fqName: String,
    initialize: Boolean = false,
    loader: ClassLoader = ClassLoader.getSystemClassLoader()
): KClass<*> = Class.forName(fqName, initialize, loader).kotlin