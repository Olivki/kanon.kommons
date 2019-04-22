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

@file:JvmName("KPreconditions")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons

import moe.kanon.kommons.exceptions.ValueOutsideOfRangeException
import moe.kanon.kommons.func.Supplier
import moe.kanon.kommons.lang.ranges.getString
import java.lang.Error
import kotlin.contracts.contract

inline infix fun <T : Comparable<T>> T.needsToBeIn(range: ClosedRange<T>) =
    requireValueInsideOfRange(this, range)

@JvmName("checkValueInsideOfRange")
inline fun <T : Comparable<T>> requireValueInsideOfRange(value: T, range: ClosedRange<T>) =
    requireValueInsideOfRange(
        value,
        range
    ) { "Value <$value> needs to be inside of the range <${range.getString()}>" }

@JvmName("checkValueInsideOfRange")
inline fun <T : Comparable<T>> requireValueInsideOfRange(value: T, range: ClosedRange<T>, lazyMsg: Supplier<String>) {
    if (value !in range) throw ValueOutsideOfRangeException(value, range, lazyMsg())
}

/**
 * Throws `this` if it is a *fatal* throwable.
 */
inline fun <T : Throwable> T.requireNonFatal() {
    when (this) {
        is Error -> throw this
    }
}

inline fun <T : Any> requireNoNull(value: T?): T {
    contract {
        returns() implies (value != null)
    }

    return requireNoNull(value) { "Value is not allowed to be null" }
}

inline fun <T : Any> requireNoNull(value: T?, lazyMsg: () -> String): T {
    contract {
        returns() implies (value != null)
    }

    if (value == null) throw UnsupportedOperationException(lazyMsg()) else return value
}

fun <T> T?.exists(): Boolean {
    contract {
        returns(true) implies (this@exists != null)
    }
    return this != null
}

@Suppress("FunctionName")
inline fun UNSUPPORTED(): Nothing = throw UnsupportedOperationException()

@Suppress("FunctionName")
inline fun UNSUPPORTED(message: String): Nothing = throw UnsupportedOperationException(message)