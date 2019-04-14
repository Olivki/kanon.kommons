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

@file:JvmName("KReflect")

package moe.kanon.kommons.reflection

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

/**
 * Returns whether or not this element has any occurrences of the specified [Annotation].
 *
 * @receiver the element to check against.
 */
inline fun <reified A : Annotation> KAnnotatedElement.hasAnnotation(): Boolean = this.annotations.any { it is A }

/**
 * Executes the specified [action] only if `this` [KAnnotatedElement] has the specified [annotation][A] and then
 * returns the resulting [Annotation], or `null` if it does not have the specified `annotation`.
 *
 * @receiver the element to check against.
 *
 * @param [A] the annotation to find.
 * @param [action] the action to execute if `this` element has the specified [annotation][A].
 *
 * @return the found annotation after applying the specified [action] on it, or `null` if `this` element does not have
 * the specified `annotation`.
 *
 * @since 0.6.0
 */
inline fun <reified A : Annotation> KAnnotatedElement.ifHasAnnotation(action: (A) -> Unit): A? =
    this.findAnnotation<A>()?.apply(action)

/**
 * Returns the specified [annotation][A], or it throws a [NoSuchFieldException] if the specified `annotation` could not
 * be found on `this` class.
 *
 * @receiver the element to check against.
 *
 * @param [A] the annotation to find.
 *
 * @since 0.6.0
 *
 * @see findAnnotation
 */
inline fun <reified A : Annotation> KAnnotatedElement.getAnnotation(): A =
    this.findAnnotation()
        ?: throw NoSuchFieldException("The class <${this::class}> does not have a property matching <${A::class}>")

/**
 * Returns whether or not `this` [KClass] is an actual Kotlin class.
 *
 * @receiver the [KClass] to check against.
 *
 * @since 0.6.0
 */
val KClass<*>.isKotlinClass: Boolean get() = this::class.java.isKotlinClass

/**
 * Returns whether or not `this` [Class] is a Kotlin class.
 *
 * @receiver the [KClass] to check against.
 *
 * @since 0.6.0
 */
val Class<*>.isKotlinClass: Boolean
    get() = this.declaredAnnotations.any { it.annotationClass.qualifiedName == "kotlin.Metadata" }