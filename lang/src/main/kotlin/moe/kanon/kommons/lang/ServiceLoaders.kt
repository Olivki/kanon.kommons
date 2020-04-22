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

package moe.kanon.kommons.lang

import java.util.*

/**
 * Returns a new [service loader][ServiceLoader] for the given [service type][S] using the current thread's
 * [context class loader][Thread.getContextClassLoader].
 *
 * @param [S] the type of the service to load
 */
@Deprecated(
    message = "Use 'loadServices' from kommons.reflection instead.",
    replaceWith = ReplaceWith("loadServices()", "moe.kanon.kommons.reflection")
)
inline fun <reified S> serviceLoaderFor(): ServiceLoader<S> = ServiceLoader.load(S::class.java)

/**
 * Returns a new [service loader][ServiceLoader] for the given [service type][S] and class loader.
 *
 * @param [S] the type of the service to load
 * @param [loader] the class loader to be used to load provider-configuration files and provider classes, or
 * `null` if the system class loader *(or, failing that, the bootstrap class loader)* is to be used
 */
@Deprecated(
    message = "Use 'loadServices' from kommons.reflection instead.",
    replaceWith = ReplaceWith("loadServices(loader)", "moe.kanon.kommons.reflection")
)
inline fun <reified S> serviceLoaderFor(loader: ClassLoader?): ServiceLoader<S> =
    ServiceLoader.load(S::class.java, loader)

// fake constructor functions
/**
 * Returns a new [service loader][ServiceLoader] for the given [service type][S] using the current thread's
 * [context class loader][Thread.getContextClassLoader].
 *
 * @param [S] the type of the service to load
 */
@Deprecated(
    message = "Use 'loadServices' from kommons.reflection instead.",
    replaceWith = ReplaceWith("loadServices()", "moe.kanon.kommons.reflection")
)
inline fun <reified S> ServiceLoader(): ServiceLoader<S> = ServiceLoader.load(S::class.java)

/**
 * Returns a new [service loader][ServiceLoader] for the given [service type][S] and class loader.
 *
 * @param [S] the type of the service to load
 * @param [loader] the class loader to be used to load provider-configuration files and provider classes, or
 * `null` if the system class loader *(or, failing that, the bootstrap class loader)* is to be used
 */
@Deprecated(
    message = "Use 'loadServices' from kommons.reflection instead.",
    replaceWith = ReplaceWith("loadServices(loader)", "moe.kanon.kommons.reflection")
)
inline fun <reified S> ServiceLoader(loader: ClassLoader?): ServiceLoader<S> =
    ServiceLoader.load(S::class.java, loader)
