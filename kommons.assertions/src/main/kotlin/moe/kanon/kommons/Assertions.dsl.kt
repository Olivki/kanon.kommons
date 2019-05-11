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
@file:JvmName("Assertions")
@file:Suppress("NOTHING_TO_INLINE", "FunctionName")

package moe.kanon.kommons

import kotlin.contracts.contract

// -- MARKER -- \\
@DslMarker annotation class AssertionMarker

// -- FUNCTIONS -- \\
@AssertionMarker inline fun <T> requireThat(item: T, scope: AssertionBlock<T>.() -> Unit): T {
    TODO()
}

@AssertionMarker class AssertionBlock<T> @PublishedApi internal constructor() {

}

@AssertionMarker open class GenericMatcher<T> @PublishedApi internal constructor(
    @PublishedApi internal val value: T
) {
    @AssertionMarker inline fun <reified E : T> isInstance() = result(value is E) { "" }

    @AssertionMarker fun isEqualTo(other: T): Boolean = value == other

    // -- NEGATED -- \\

    @AssertionMarker inline fun <reified E : T> isNotInstance(): Boolean = value is E

    @AssertionMarker fun isNotEqualTo(other: T): Boolean = value == other
}

@AssertionMarker open class CollectionMatcher<T : Collection<T>> @PublishedApi internal constructor(
    value: T
) : GenericMatcher<T>(value)  {

}

@AssertionMarker fun <T> GenericMatcher<T?>.isNull(): Boolean = value == null

data class AssertionResult @PublishedApi internal constructor(
    val result: Boolean,
    val message: String
) {
    @AssertionMarker operator fun not(): AssertionResult = copy(result = !result)
}

@AssertionMarker fun not(result: AssertionResult): AssertionResult = !result

@PublishedApi internal inline fun result(condition: Boolean, lazyMsg: () -> String): AssertionResult =
    AssertionResult(condition, lazyMsg())