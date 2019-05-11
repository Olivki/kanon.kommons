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

@file:JvmName("Utilities")
@file:Suppress("NOTHING_TO_INLINE", "FunctionName")

package moe.kanon.kommons

const val UNSUPPORTED_OPERATION = "Operation is not supported"

/**
 * Throws a [UnsupportedOperationException] with the specified [message].
 */
@FakeKeyword inline fun UNSUPPORTED(message: String): Nothing = throw UnsupportedOperationException(message)

/**
 * Throws a [UnsupportedOperationException] with a default message.
 */
@FakeKeyword inline fun UNSUPPORTED(): Nothing = throw UnsupportedOperationException(UNSUPPORTED_OPERATION)