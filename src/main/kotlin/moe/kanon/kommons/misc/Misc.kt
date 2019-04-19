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

@file:JvmName("KMisc")

package moe.kanon.kommons.misc

/*
 * This file contains different functions / structures that have no proper place to put them for now.
 */

/**
 * Calls the [action] `n` amount of times.
 *
 * Examples:
 * ```kotlin
 *  3 * { println(it) } // 1\n2\n3
 *
 *  4 times { println(it) } // 1\n2\n3\n4
 * ```
 *
 * @receiver the [Int] that determines the value of `n`
 */
inline infix operator fun Int.times(action: (Int) -> Unit) {
    for (i in 0 until this) action(i)
}