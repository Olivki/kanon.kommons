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
 */

@file:JvmName("KAnnotations")

package moe.kanon.kommons.reflection

import kotlin.reflect.KAnnotatedElement

/**
 * Returns `true` if `this` element is annotated with the given [annotation][A], otherwise `false`.
 */
inline fun <reified A : Annotation> KAnnotatedElement.hasAnnotation(): Boolean = this.annotations.any { it is A }

/**
 * Returns the *first* instance of the given [annotation][A] on `this` element, or throws a [NoSuchElementException]
 * if `this` element is not annotated with the given `annotation`.
 */
inline fun <reified A : Annotation> KAnnotatedElement.getAnnotation(): A = this.annotations
    .filterIsInstance<A>()
    .firstOrNull() ?: throw NoSuchElementException("<$this> is not annotated with <${A::class}>")

/**
 * Invokes the given [scope] function with the first instance of the given [annotation][A] if `this` element is
 * annotated by it, otherwise does nothing.
 */
inline fun <reified A : Annotation> KAnnotatedElement.ifHasAnnotation(scope: (A) -> Unit) {
    if (hasAnnotation<A>()) {
        scope(getAnnotation())
    }
}

/**
 * Invokes the [notExists] function if `this` element is not annotated with the given [annotation][A], otherwise
 * invokes the [exists] function, and returns the result.
 *
 * @return the result of invoking either the [notExists] or [exists] function
 */
// TODO: Rename?
inline fun <reified A : Annotation, R> KAnnotatedElement.foldAnnotation(notExists: () -> R, exists: (A) -> R): R =
    when {
        this.hasAnnotation<A>() -> exists(getAnnotation())
        else -> notExists()
    }