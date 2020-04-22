/*
 * Copyright 2019-2020 Oliver Berg
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

@PublishedApi
internal const val FAILED_CHECK = "State did not satisfy the given condition"

@PublishedApi
internal const val FAILED_REQUIRE = "Argument did not satisfy the given condition"

@PublishedApi
internal const val FAILED_ASSERTION = "Assertion failed"

// -- GENERAL -- \\
/**
 * Returns `this` value, or throws a [IllegalStateException] if `this` does *not* satisfy the given [condition].
 */
inline infix fun <T> T.satisfies(condition: (T) -> Boolean): T =
    if (!condition(this)) throw IllegalStateException("<$this> does not satisfy the given condition") else this

// -- EXCEPTIONS -- \\
// TODO: Better clarify what errors are *actually* fatal, as not all errors are *actually* fatal (which breaks the contract set forth by Error, but oh well)
@FakeKeyword
inline fun requireNonFatal(throwable: Throwable) {
    contract {
        returns() implies (throwable !is Error)
    }
    if (throwable is Error) throw throwable
}

// -- AFFIRM -- \\
/**
 * Throws a [UnsupportedOperationException] with the result of invoking [lazyMsg] if the given [condition] returns
 * `false`.
 */
@FakeKeyword
inline fun affirmThat(condition: () -> Boolean, lazyMsg: () -> Any) {
    if (!condition()) throw UnsupportedOperationException(lazyMsg().toString())
}

/**
 * Throws a [UnsupportedOperationException] with a default message if the given [condition] returns `false`.
 */
@FakeKeyword
inline fun affirmThat(condition: () -> Boolean) {
    affirmThat(condition) { UNSUPPORTED_OPERATION }
}

/**
 * Throws a [UnsupportedOperationException] with the result of invoking [lazyMsg] if the given [condition] is `false`.
 */
@FakeKeyword
inline fun affirmThat(condition: Boolean, lazyMsg: () -> Any) {
    contract {
        returns() implies (condition)
    }
    if (!condition) throw UnsupportedOperationException(lazyMsg().toString())
}

/**
 * Throws a [UnsupportedOperationException] with a default message if the given [condition] is `false`.
 */
@FakeKeyword
inline fun affirmThat(condition: Boolean) {
    affirmThat(condition) { UNSUPPORTED_OPERATION }
}

/**
 * Throws a [UnsupportedOperationException] with a default prefix and the specified [code] if the given [condition] is
 * `false`.
 *
 * Note that the [code] string *should* be the exact same as what's typed in the [condition] boolean, just in string
 * form.
 */
@FakeKeyword
inline fun affirmThat(condition: Boolean, code: String) {
    affirmThat(condition) { "$UNSUPPORTED_OPERATION: ($code)" }
}

// -- CHECK -- \\
/**
 * Throws a [IllegalStateException] with the result of invoking [lazyMsg] if the given [condition] returns `false`.
 */
@FakeKeyword
inline fun checkThat(condition: () -> Boolean, lazyMsg: () -> Any) {
    if (!condition()) throw IllegalStateException(lazyMsg().toString())
}

/**
 * Throws a [IllegalStateException] with a default message if the given [condition] returns `false`.
 */
@FakeKeyword
inline fun checkThat(condition: () -> Boolean) {
    checkThat(condition) { FAILED_CHECK }
}

/**
 * Throws a [IllegalStateException] with the result of invoking [lazyMsg] if the given [condition] is `false`.
 */
@FakeKeyword
inline fun checkThat(condition: Boolean, lazyMsg: () -> Any) {
    contract {
        returns() implies (condition)
    }
    if (!condition) throw IllegalStateException(lazyMsg().toString())
}

/**
 * Throws a [IllegalStateException] with a default message if the given [condition] is `false`.
 */
@FakeKeyword
inline fun checkThat(condition: Boolean) {
    checkThat(condition) { FAILED_CHECK }
}

/**
 * Throws a [IllegalStateException] with a default prefix and the specified [code] if the given [condition] is `false`.
 *
 * Note that the [code] string *should* be the exact same as what's typed in the [condition] boolean, just in string
 * form.
 */
@FakeKeyword
inline fun checkThat(condition: Boolean, code: String) {
    checkThat(condition) { "$FAILED_CHECK: ($code)" }
}

// -- REQUIRE -- \\
/**
 * Throws an [IllegalArgumentException] with the result of invoking [lazyMsg] if the given [condition] returns `false`.
 */
@FakeKeyword
inline fun requireThat(condition: () -> Boolean, lazyMsg: () -> Any) {
    if (!condition()) throw IllegalArgumentException(lazyMsg().toString())
}

/**
 * Throws an [IllegalArgumentException] with a default message if the given [condition] returns `false`.
 */
@FakeKeyword
inline fun requireThat(condition: () -> Boolean) {
    requireThat(condition) { FAILED_REQUIRE }
}

/**
 * Throws an [IllegalArgumentException] with the result of invoking [lazyMsg] if the given [condition] is `false`.
 */
@FakeKeyword
inline fun requireThat(condition: Boolean, lazyMsg: () -> Any) {
    contract {
        returns() implies (condition)
    }
    if (!condition) throw IllegalArgumentException(lazyMsg().toString())
}

/**
 * Throws an [IllegalArgumentException] with a default message if the given [condition] is `false`.
 */
@FakeKeyword
inline fun requireThat(condition: Boolean) {
    requireThat(condition) { FAILED_REQUIRE }
}

/**
 * Throws an [IllegalArgumentException] with a default prefix and the specified [code] if the given [condition] is
 * `false`.
 *
 * Note that the [code] string *should* be the exact same as what's typed in the [condition] boolean, just in string
 * form.
 */
@FakeKeyword
inline fun requireThat(condition: Boolean, code: String) {
    requireThat(condition) { "$FAILED_REQUIRE: ($code)" }
}

// -- ASSERTIONS -- \\
/**
 * Throws a [AssertionError] with the result of invoking [lazyMsg] if the given [condition] returns `false`.
 */
@FakeKeyword
inline fun assertThat(condition: () -> Boolean, lazyMsg: () -> Any) {
    if (!condition()) throw AssertionError(lazyMsg().toString())
}

/**
 * Throws a [AssertionError] with a default message if the given [condition] returns `false`.
 */
@FakeKeyword
inline fun assertThat(condition: () -> Boolean) {
    assertThat(condition) { FAILED_ASSERTION }
}

/**
 * Throws a [AssertionError] with the result of invoking [lazyMsg] if the given [condition] is `false`.
 */
@FakeKeyword
inline fun assertThat(condition: Boolean, lazyMsg: () -> Any) {
    contract {
        returns() implies (condition)
    }
    if (!condition) throw AssertionError(lazyMsg().toString())
}

/**
 * Throws a [AssertionError] with a default message if the given [condition] is `false`.
 */
@FakeKeyword
inline fun assertThat(condition: Boolean) {
    assertThat(condition) { FAILED_ASSERTION }
}

/**
 * Throws an [AssertionError] with a default prefix and the specified [code] if the given [condition] is `false`.
 *
 * Note that the [code] string *should* be the exact same as what's typed in the [condition] boolean, just in string
 * form.
 */
@FakeKeyword
inline fun assertThat(condition: Boolean, code: String) {
    assertThat(condition) { "$FAILED_ASSERTION: ($code)" }
}