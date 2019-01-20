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

package moe.kanon.kextensions

/**
 * An annotation signifying that a feature of this library is experimental.
 *
 * A feature being experimental means that it may be unstable, or that the feature might be removed/remade at a later
 * date.
 *
 * This annotation is also marked with the [Experimental] annotation from Kotlin, it will not throw an error if the
 * user hasn't accepted the usage of experimental features, but it will warn the user. This may be changed at a later
 * date.
 */
@Experimental(Experimental.Level.WARNING)
@Retention(AnnotationRetention.BINARY)
public annotation class ExperimentalFeature