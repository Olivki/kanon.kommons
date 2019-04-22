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

package moe.kanon.kommons.func

import moe.kanon.kommons.func.tuples.Duad
import moe.kanon.kommons.func.tuples.Tuple2

/**
 * Represents a state of changing from [one value][before] to [another value][after].
 */
class State<out T>(val before: T, val after: T) : Tuple2<T, T> by Duad(before, after) {
    override fun toString(): String = "($before -> $after)"
}