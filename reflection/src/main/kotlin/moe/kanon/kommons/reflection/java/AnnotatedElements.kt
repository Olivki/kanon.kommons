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

@file:JvmName("KAnnotatedElements")

package moe.kanon.kommons.reflection.java

import java.lang.reflect.AnnotatedElement

/**
 * Returns `true` if an annotation of type [A] is present on `this` element, otherwise `false`.
 *
 * @see [AnnotatedElement.isAnnotationPresent]
 */
inline fun <reified A : Annotation> AnnotatedElement.isAnnotationPresent(): Boolean =
    isAnnotationPresent(A::class.java)

/**
 * Returns an annotation of type [A] if such an annotation [is present][AnnotatedElement.isAnnotationPresent] on `this`
 * element, otherwise `null`.
 */
inline fun <reified A : Annotation> AnnotatedElement.getAnnotationOrNull(): A? = getAnnotation(A::class.java)

/**
 * Returns an annotation of type [A] if such an annotation [is present][AnnotatedElement.isAnnotationPresent] on `this`
 * element, otherwise throws a [NoSuchElementException].
 *
 * @throws [NoSuchElementException] if no annotation of type [A] is present on `this` element
 */
inline fun <reified A : Annotation> AnnotatedElement.getAnnotation(): A = when {
    isAnnotationPresent<A>() -> getAnnotation(A::class.java)!!
    else -> throw NoSuchElementException("'$this' is not annotated with '${A::class.java.name}'.")
}