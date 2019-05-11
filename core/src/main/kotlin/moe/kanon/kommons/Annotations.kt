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

package moe.kanon.kommons

/**
 * Used to mark features in the library that are considered experimental, meaning that they may change drastically, or
 * even be outright removed.
 */
@Experimental
@MustBeDocumented
annotation class ExperimentalFeature

/**
 * Used to mark top-level types in the Aland library.
 *
 * ### Notes
 * This annotation is generally used as a sort of "hack" to produce functions that look similar to actual keywords. This
 * is accomplished via the use of the [@DslMarker][DslMarker] annotation.
 */
@DslMarker
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class FakeKeyword

/**
 * Used to mark an implementation of a [data type](https://en.wikipedia.org/wiki/Algebraic_data_type).
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class DataType

/**
 * Used to mark a class *(generally an `interface`)* whose main purpose is to serve as a marker for other classes.
 *
 * @see Identifiable
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class MarkerType

/**
 * Used to mark an implementation that is a port from another language / library.
 *
 * Anything that is marked with this annotation should provide a link to the appropriate repository for each listed
 * fully-qualified name.
 *
 * @see Identifiable
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
annotation class PortOf(vararg val fqNames: String)