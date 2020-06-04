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
 * Returns `true` if an annotation of type [A] is present on `this` element, otherwise `false`.
 */
@Deprecated(
    message = "Use 'isAnnotationPresent()' instead.",
    replaceWith = ReplaceWith("this.isAnnotationPresent()", "moe.kanon.kommons.reflection")
)
inline fun <reified A : Annotation> KAnnotatedElement.hasAnnotation(): Boolean = annotations.any { it is A }

/**
 * Returns `true` if an annotation of type [A] is present on `this` element, otherwise `false`.
 */
inline fun <reified A : Annotation> KAnnotatedElement.isAnnotationPresent(): Boolean = annotations.any { it is A }

/**
 * Returns a list of all the annotations of type [A] that are present on `this` element, otherwise an empty list.
 */
inline fun <reified A : Annotation> KAnnotatedElement.getAnnotations(): List<A> = annotations.filterIsInstance<A>()

/**
 * Returns the *first* annotation of type [A] that is present on `this` element, otherwise `null`.
 *
 * @see [getAnnotations]
 * @see [getAnnotation]
 * @see [isAnnotationPresent]
 */
inline fun <reified A : Annotation> KAnnotatedElement.getAnnotationOrNull(): A? = getAnnotations<A>().firstOrNull()

/**
 * Returns the *first* annotation of type [A] that is present on `this` element, otherwise throws a
 * [NoSuchElementException].
 *
 * @throws [NoSuchElementException] if no annotations of type [A] are present on `this` element
 *
 * @see [getAnnotations]
 * @see [getAnnotationOrNull]
 * @see [isAnnotationPresent]
 */
inline fun <reified A : Annotation> KAnnotatedElement.getAnnotation(): A =
    getAnnotationOrNull<A>() ?: throw NoSuchElementException("<$this> is not annotated with <${A::class}>")

/**
 * Invokes the given [scope] function with the first instance of the given [annotation][A] if `this` element is
 * annotated by it, otherwise does nothing.
 */
inline fun <reified A : Annotation> KAnnotatedElement.ifHasAnnotation(scope: (A) -> Unit) {
    if (isAnnotationPresent<A>()) {
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
inline fun <reified A : Annotation, R> KAnnotatedElement.foldAnnotation(
    notExists: () -> R,
    exists: (A) -> R
): R = when {
    isAnnotationPresent<A>() -> exists(getAnnotation())
    else -> notExists()
}