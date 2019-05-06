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

@file:JvmName("Annotations")

package moe.kanon.kommons.reflection

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

/**
 * Returns whether or not `this` [annotated element][KAnnotatedElement] is annotated with the specified [annotation][A].
 */
inline fun <reified A : Annotation> KAnnotatedElement.hasAnnotation(): Boolean = this.annotations.any { it is A }

/**
 * Returns the instance of specified the [annotation][A] that `this` element is annotated with or throws a
 * [NoSuchElementException] if `this` is not annotated with the specified `annotation`.
 */
inline fun <reified A : Annotation> KAnnotatedElement.getAnnotation(): A =
    this.annotations.filterIsInstance<A>().firstOrNull() ?: throw NoSuchElementException(
        "<$this> is not annotated with <${A::class}>"
    )

/**
 * Calls [notExists] if `this` element does *not* contain the specified [annotation][A] otherwise calls [exists] if
 * it does contain it.
 */
inline fun <reified A : Annotation, R> KAnnotatedElement.foldAnnotation(notExists: () -> R, exists: (A) -> R): R = when {
    this.hasAnnotation<A>() -> exists(getAnnotation())
    else -> notExists()
}