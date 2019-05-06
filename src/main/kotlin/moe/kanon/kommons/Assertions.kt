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

@file:JvmName("Assertions")
@file:Suppress("NOTHING_TO_INLINE", "FunctionName")

package moe.kanon.kommons

import kotlin.contracts.contract

const val UNSUPPORTED_OPERATION = "Operation is not supported"
const val FAILED_CHECK = "State did not satisfy the given condition"
const val FAILED_REQUIRE = "Argument did not satisfy the given condition"
const val FAILED_ASSERTION = "Assertion failed"

// ---- EXTENSION SCOPE ---- \\
// -- GENERAL -- \\
/**
 * Returns `this` value, or throws a [IllegalStateException] if `this` does *not* satisfy the given [condition].
 */
inline infix fun <T> T.satisfies(condition: (T) -> Boolean): T =
    if (!condition(this)) throw IllegalStateException("<$this> does not satisfy the given condition") else this

// -- RANGES -- \\
/**
 * Returns `this` or throws a [IllegalStateException] if `this` is outside of the given [range].
 */
inline infix fun <C : Comparable<C>> C.checkInRange(range: ClosedRange<C>): C =
    if (this !in range) throw IllegalStateException("<$this> is outside of range <$range>") else this

/**
 * Returns `this` or throws a [IllegalArgumentException] if `this` is outside of the given [range].
 */
inline infix fun <C : Comparable<C>> C.requireInRange(range: ClosedRange<C>): C =
    if (this !in range) throw IllegalArgumentException("<$this> is outside of range <$range>") else this

// -- AFFIRM -- \\
/**
 * Throws a [UnsupportedOperationException] with the result of invoking [lazyMsg] if the given [condition] returns
 * `false`.
 */
@FakeKeyword inline fun affirmThat(condition: () -> Boolean, lazyMsg: () -> String) {
    if (!condition()) throw UnsupportedOperationException(lazyMsg())
}

/**
 * Throws a [UnsupportedOperationException] with a default message if the given [condition] returns `false`.
 */
@FakeKeyword inline fun affirmThat(condition: () -> Boolean) = affirmThat(condition) { UNSUPPORTED_OPERATION }

/**
 * Throws a [UnsupportedOperationException] with the result of invoking [lazyMsg] if the given [condition] is `false`.
 */
@FakeKeyword inline fun affirmThat(condition: Boolean, lazyMsg: () -> String) {
    contract {
        returns() implies (condition)
    }
    if (!condition) throw UnsupportedOperationException(lazyMsg())
}

/**
 * Throws a [UnsupportedOperationException] with a default message if the given [condition] is `false`.
 */
@FakeKeyword inline fun affirmThat(condition: Boolean) = affirmThat(condition) { UNSUPPORTED_OPERATION }

// -- CHECK -- \\
/**
 * Throws a [IllegalStateException] with the result of invoking [lazyMsg] if the given [condition] returns `false`.
 */
@FakeKeyword inline fun checkThat(condition: () -> Boolean, lazyMsg: () -> String) {
    if (!condition()) throw IllegalStateException(lazyMsg())
}

/**
 * Throws a [IllegalStateException] with a default message if the given [condition] returns `false`.
 */
@FakeKeyword inline fun checkThat(condition: () -> Boolean) = checkThat(condition) { FAILED_CHECK }

/**
 * Throws a [IllegalStateException] with the result of invoking [lazyMsg] if the given [condition] is `false`.
 */
@FakeKeyword inline fun checkThat(condition: Boolean, lazyMsg: () -> String) {
    contract {
        returns() implies (condition)
    }
    if (!condition) throw IllegalStateException(lazyMsg())
}

/**
 * Throws a [IllegalStateException] with a default message if the given [condition] is `false`.
 */
@FakeKeyword inline fun checkThat(condition: Boolean) = checkThat(condition) { FAILED_CHECK }

// -- REQUIRE -- \\
/**
 * Throws a [IllegalArgumentException] with the result of invoking [lazyMsg] if the given [condition] returns `false`.
 */
@FakeKeyword inline fun requireThat(condition: () -> Boolean, lazyMsg: () -> String) {
    if (!condition()) throw IllegalArgumentException(lazyMsg())
}

/**
 * Throws a [IllegalArgumentException] with a default message if the given [condition] returns `false`.
 */
@FakeKeyword inline fun requireThat(condition: () -> Boolean) = requireThat(condition) { FAILED_REQUIRE }

/**
 * Throws a [IllegalArgumentException] with the result of invoking [lazyMsg] if the given [condition] is `false`.
 */
@FakeKeyword inline fun requireThat(condition: Boolean, lazyMsg: () -> String) {
    contract {
        returns() implies (condition)
    }
    if (!condition) throw IllegalArgumentException(lazyMsg())
}

/**
 * Throws a [IllegalArgumentException] with a default message if the given [condition] is `false`.
 */
@FakeKeyword inline fun requireThat(condition: Boolean) = requireThat(condition) { FAILED_REQUIRE }

// -- ASSERTIONS -- \\
/**
 * Throws a [AssertionError] with the result of invoking [lazyMsg] if the given [condition] returns `false`.
 */
@FakeKeyword inline fun assertThat(condition: () -> Boolean, lazyMsg: () -> String) {
    if (!condition()) throw AssertionError(lazyMsg())
}

/**
 * Throws a [AssertionError] with a default message if the given [condition] returns `false`.
 */
@FakeKeyword inline fun assertThat(condition: () -> Boolean) = assertThat(condition) { FAILED_ASSERTION }

/**
 * Throws a [AssertionError] with the result of invoking [lazyMsg] if the given [condition] is `false`.
 */
@FakeKeyword inline fun assertThat(condition: Boolean, lazyMsg: () -> String) {
    contract {
        returns() implies (condition)
    }
    if (!condition) throw AssertionError(lazyMsg())
}

/**
 * Throws a [AssertionError] with a default message if the given [condition] is `false`.
 */
@FakeKeyword inline fun assertThat(condition: Boolean) = assertThat(condition) { FAILED_ASSERTION }