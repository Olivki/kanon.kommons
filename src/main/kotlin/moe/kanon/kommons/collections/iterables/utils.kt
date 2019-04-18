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

@file:JvmMultifileClass
@file:JvmName("KIterables")

package moe.kanon.kommons.collections.iterables

import moe.kanon.kommons.lang.occurrencesOf

/**
 * Returns a [List] that's sorted after the occurrences of the specific characters in the given [String].
 */
fun Iterable<Char>.sortByOccurrences(string: String): List<Char> = this.sortedBy { string.occurrencesOf(it) }