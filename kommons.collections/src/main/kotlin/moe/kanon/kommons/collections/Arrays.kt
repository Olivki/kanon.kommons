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

@file:JvmName("KArrays")

package moe.kanon.kommons.collections

/**
 * Creates a new generic [Array] of the specified [size] filled with `null` values.
 */
@Suppress("UNCHECKED_CAST")
fun <T> createArray(size: Int): Array<T> = arrayOfNulls<Any>(size) as Array<T>

/**
 * Creates a new generic [Array] of the specified [size] filled with the specified [default] value.
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> createArray(size: Int, default: T): Array<T> = Array<Any>(size) { default } as Array<T>