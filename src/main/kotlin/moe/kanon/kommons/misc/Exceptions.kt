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

@file:JvmName("KExceptions")

package moe.kanon.kommons.misc

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Simulates the `multi-catch` feature from Java in Kotlin.
 *
 * **Usage:**
 *
 * ```kotlin
 *  try {
 *      ...
 *  } catch (e: Exception) {
 *      e.multiCatch(ExceptionOne::class, ExceptionTwo::class) {
 *          ...
 *      }
 *  }
 * ```
 *
 * If you want to just ignore any caught errors of the specific type. *(Do note that this is **very** bad practice.)*:
 *
 * ```kotlin
 *  try {
 *      ...
 *  } catch (e: Exception) {
 *      e.multiCatch(ExceptionOne::class, ExceptionTwo::class)
 *  }
 * ```
 *
 * @param classes Which classes this `multi-catch` block should actually catch.
 *
 * If an `exception` gets thrown where this function is used, and it's not listed in the `classes` vararg, then the
 * exception will just get re-thrown.
 *
 * (`{}` by default)
 *
 * @author carleslc
 * @since 0.6.0
 */
inline fun Throwable.multiCatch(vararg classes: KClass<*>, catchBlock: () -> Unit = {}) =
    if (classes.any { this::class.isSubclassOf(it) }) catchBlock() else throw this