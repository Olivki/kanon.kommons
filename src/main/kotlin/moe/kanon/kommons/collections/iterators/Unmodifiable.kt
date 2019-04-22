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
@file:JvmName("KIterators")

package moe.kanon.kommons.collections.iterators

import moe.kanon.kommons.UNSUPPORTED

private const val UNMODIFIABLE_ITERATOR_MESSAGE = "This iterator is marked as unmodifiable"

/**
 * An iterator that will throw a [UnsupportedOperationException] when the [remove] function is invoked.
 */
abstract class UnmodifiableIterator<out T> : MutableIterator<T> {
    final override fun remove() = UNSUPPORTED(UNMODIFIABLE_ITERATOR_MESSAGE)
}

/**
 * Returns a [UnmodifiableIterator] that is backed by `this` iterator.
 */
@JvmName("unmodifiable")
fun <T> Iterator<T>.toUnmodifiableIterator(): Iterator<T> = object : UnmodifiableIterator<T>() {
    override fun hasNext(): Boolean = this@toUnmodifiableIterator.hasNext()

    override fun next(): T = this@toUnmodifiableIterator.next()
}

/**
 * A list-iterator that will throw a [UnsupportedOperationException] when the [remove], [set] or [add] functions are
 * invoked.
 */
abstract class UnmodifiableListIterator<out T> : MutableListIterator<@UnsafeVariance T>, UnmodifiableIterator<T>() {
    final override fun set(element: @UnsafeVariance T) = UNSUPPORTED(UNMODIFIABLE_ITERATOR_MESSAGE)

    final override fun add(element: @UnsafeVariance T) = UNSUPPORTED(UNMODIFIABLE_ITERATOR_MESSAGE)
}

/**
 * Returns a [UnmodifiableIterator] that is backed by `this` iterator.
 */
@JvmName("unmodifiableList")
fun <T> ListIterator<T>.toUnmodifiableListIterator(): Iterator<T> = object : UnmodifiableListIterator<T>() {
    override fun hasPrevious(): Boolean = this@toUnmodifiableListIterator.hasPrevious()

    override fun nextIndex(): Int = this@toUnmodifiableListIterator.nextIndex()

    override fun previous(): T = this@toUnmodifiableListIterator.previous()

    override fun previousIndex(): Int = this@toUnmodifiableListIterator.previousIndex()

    override fun hasNext(): Boolean = this@toUnmodifiableListIterator.hasNext()

    override fun next(): T = this@toUnmodifiableListIterator.next()
}