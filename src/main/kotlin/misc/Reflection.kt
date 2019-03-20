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

@file:JvmName("KotlinReflectionUtils")

package moe.kanon.kommons.misc

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

/**
 * Returns whether or not this element has any occurrences of the specified [Annotation].
 *
 * @receiver the element to check against.
 */
public inline fun <reified A : Annotation> KAnnotatedElement.hasAnnotation(): Boolean = this.annotations.any { it is A }

/**
 * Returns whether or not `this` [KClass] is an actual Kotlin class.
 *
 * @receiver the [KClass] to check against.
 *
 * @since 0.6.0
 */
public val KClass<*>.isKotlinClass: Boolean get() = this.hasAnnotation<kotlin.Metadata>()

/**
 * Returns whether or not `this` [Class] is a Kotlin class.
 *
 * @receiver the [KClass] to check against.
 *
 * @since 0.6.0
 */
public val Class<*>.isKotlinClass: Boolean
    get() = this.declaredAnnotations.any { it.annotationClass.qualifiedName == "kotlin.Metadata" }