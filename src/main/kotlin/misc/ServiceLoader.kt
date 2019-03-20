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

@file:JvmName("KServiceLoader")

package moe.kanon.kommons.misc

import java.util.*

/**
 * Creates a [ServiceLoader] for the specified [S] type.
 *
 * @since 0.6.0
 */
public inline fun <reified S> serviceLoaderOf(): ServiceLoader<S> = ServiceLoader.load(S::class.java)

/**
 * Creates a [ServiceLoader] for the specified [S] type, with the specified [loader].
 *
 * @since 0.6.0
 */
public inline fun <reified S> serviceLoaderOf(loader: ClassLoader): ServiceLoader<S> =
    ServiceLoader.load(S::class.java, loader)