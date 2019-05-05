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

private const val SINGLETON_ITERATOR = "Can not add or remove from a singleton iterator"

/**
 * Represents a [iterator][Iterator] that iterates over a single object.
 *
 * This is a port of the `iterator` returned by the package-private `singletonIterator()` function in
 * [java.util.Collections], to allow usage of it from anywhere.
 */
class SingletonIterator<T>(private val item: T) : UnmodifiableIterator<T>() {
    private var hasNext = true

    override fun hasNext(): Boolean = hasNext

    override fun next(): T = if (hasNext) item.also { hasNext = false } else throw NoSuchElementException()
}

/**
 * Represents a [list-iterator][ListIterator] that iterates over a single object.
 *
 * While this class *is* marked as a [MutableListIterator], this is solely for the purpose of allowing it to be used
 * with mutable-list implementations, any attempts to actually invoke the mutable operation finctions will result in a
 * [UnsupportedOperationException] being thrown.
 */
class SingletonListIterator<T>(private val item: T) : UnmodifiableListIterator<T>() {
    private var hasNext = true

    override fun hasNext(): Boolean = hasNext

    override fun hasPrevious(): Boolean = !hasNext

    override fun next(): T = if (hasNext) item.also { hasNext = false } else throw NoSuchElementException()

    override fun nextIndex(): Int = 0

    override fun previous(): T = if (!hasNext) item.also { hasNext = true } else throw NoSuchElementException()

    override fun previousIndex(): Int = 0
}