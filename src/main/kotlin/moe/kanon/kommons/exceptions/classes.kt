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
@file:JvmName("KExceptions")

package moe.kanon.kommons.exceptions

import moe.kanon.kommons.lang.ranges.getString

class ValueOutsideOfRangeException @JvmOverloads constructor(
    val value: Comparable<*>,
    val range: ClosedRange<*>,
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause) {
    companion object {
        @JvmStatic
        @JvmOverloads
        fun <T : Comparable<T>> of(
            value: T,
            range: ClosedRange<T>,
            cause: Throwable? = null
        ): ValueOutsideOfRangeException = ValueOutsideOfRangeException(
            value,
            range,
            "Value <$value> needs to be inside of the range <${range.getString()}>",
            cause
        )
    }
}