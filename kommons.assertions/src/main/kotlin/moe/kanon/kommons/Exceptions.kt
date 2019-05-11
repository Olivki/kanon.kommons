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

@file:Suppress("MemberVisibilityCanBePrivate")

package moe.kanon.kommons

/**
 * Thrown to indicate that a [value] is not contained inside of the specified [bounds].
 *
 * @property [value] A [comparable][Comparable] value that should conform to the specified [bounds].
 * @property [bounds] The [bounds][ClosedRange] that the specified [value] should be conforming to.
 */
open class ValueOutOfBoundsException(
    val value: Comparable<*>,
    val bounds: ClosedRange<*>,
    message: String
) : IndexOutOfBoundsException(message) {
    companion object {
        /**
         * Returns a [ValueOutOfBoundsException] with a [message][ValueOutOfBoundsException.message] created from the
         * given `value` and `bounds`.
         */
        @JvmStatic
        fun from(value: Comparable<*>, bounds: ClosedRange<*>): ValueOutOfBoundsException =
            ValueOutOfBoundsException(value, bounds, "Value <$value> is outside of the specified bounds <$bounds>")

        @JvmStatic
        fun <C : Comparable<C>> from(value: C, min: C, max: C): ValueOutOfBoundsException = this.from(value, min..max)
    }
}