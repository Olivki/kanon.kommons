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
import kotlin.contracts.contract

inline infix fun <T : Comparable<T>> T.shouldNotBeGreaterThan(roof: T): T =
    if (this > roof) throw IllegalArgumentException("Value <$this> is greater than max allowed value <$roof>") else this

inline infix fun <T : Comparable<T>> T.needsToBeIn(range: ClosedRange<T>) =
    requireValueInsideOfRange(this, range)

@ScopedFunction
@JvmName("checkValueInsideOfRange")
inline fun <T : Comparable<T>> requireValueInsideOfRange(value: T, range: ClosedRange<T>): T =
    requireValueInsideOfRange(
        value,
        range
    ) { "Value <$value> needs to be inside of the range <${range.getString()}>" }

@ScopedFunction
@JvmName("checkValueInsideOfRange")
inline fun <T : Comparable<T>> requireValueInsideOfRange(
    value: T,
    range: ClosedRange<T>,
    lazyMsg: Supplier<String>
): T {
    if (value !in range) throw ValueOutsideOfRangeException(value, range, lazyMsg())
    return value
}

/**
 * Throws `this` if it is a *fatal* throwable.
 */
inline fun <T : Throwable> T.requireNonFatal() {
    when (this) {
        is Error -> throw this
    }
}

@ScopedFunction
inline fun <T : Any> requireNoNull(value: T?): T {
    contract {
        returns() implies (value != null)
    }

    return requireNoNull(value) { "Value is not allowed to be null" }
}

@ScopedFunction
inline fun <T : Any> requireNoNull(value: T?, lazyMsg: () -> String): T {
    contract {
        returns() implies (value != null)
    }

    if (value == null) throw IllegalStateException(lazyMsg()) else return value
}

fun <T> T?.exists(): Boolean {
    contract {
        returns(true) implies (this@exists != null)
    }
    return this != null
}

inline infix fun <T, C : Collection<T>> C.shouldBeAtLeastSize(min: Int): C =
    if (this.size < min) throw IllegalStateException(
        "The size of collection <$this> is smaller than min <$min> [$size < $min]"
    ) else this

inline infix fun <T> T.needsToSatisfy(predicate: (T) -> Boolean): T =
    if (!predicate(this)) throw IllegalStateException("Value <$this> does not satisfy the set conditions") else this

inline fun <reified T> Any?.needsToBe(): T =
    if (this !is T) throw IllegalStateException("Value <$this> is not instance-of <${T::class}>") else this

@ScopedFunction
inline fun <reified T> requireInstanceOf(value: Any?) =
    requireInstanceOf<T>(value) { "Value <$it> is not instance of <${T::class}>" }

@ScopedFunction
inline fun <reified T> requireInstanceOf(value: Any?, lazyMsg: (Any?) -> String) {
    if (value !is T) throw IllegalArgumentException(lazyMsg(value))
}

@ScopedFunction
inline fun requireThat(condition: Boolean, lazyMsg: () -> String) {
    contract {
        returns() implies condition
    }
    if (!condition) throw IllegalArgumentException(lazyMsg())
}

@ScopedFunction
inline fun requireThat(condition: Boolean) = requireThat(condition) { "Condition not met" }

// assert
@ScopedFunction
inline fun assert(condition: () -> Boolean) = assert(condition) { "Assertion failed" }

@ScopedFunction
inline fun assert(condition: () -> Boolean, lazyMsg: () -> String) {
    if (!condition()) throw AssertionError(lazyMsg())
}

@ScopedFunction
inline fun assertThat(condition: Boolean, lazyMsg: () -> String) {
    contract {
        returns() implies condition
    }
    if (!condition) throw AssertionError(lazyMsg())
}

@ScopedFunction
@Suppress("FunctionName")
inline fun UNSUPPORTED(): Nothing = throw UnsupportedOperationException()

@ScopedFunction
@Suppress("FunctionName")
inline fun UNSUPPORTED(reason: String): Nothing = UNSUPPORTED { reason }

@ScopedFunction
@Suppress("FunctionName")
inline fun UNSUPPORTED(lazyReason: () -> String): Nothing = throw UnsupportedOperationException(lazyReason())