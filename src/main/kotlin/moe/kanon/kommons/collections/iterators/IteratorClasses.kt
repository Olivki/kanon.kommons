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
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.collections.iterators

import moe.kanon.kommons.func.tuples.Duad
import moe.kanon.kommons.func.tuples.Tuple2
import moe.kanon.kommons.func.tuples.toT

private const val EMPTY_ITERATOR = "Can not iterate over a empty iterator"
private const val SINGLETON_ITERATOR = "Can not add or remove from a singleton iterator"

// TODO: Spliterators

// empty iterators
/**
 * Represents a [iterator] that does not iterate over *any* objects.
 *
 * This is a port of the `iterator` returned by the `emptyIterator()` function in [java.util.Collections].
 */
object EmptyIterator : Iterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun next(): Nothing = throw UnsupportedOperationException(EMPTY_ITERATOR)

    /**
     * Returns `this` empty-iterator as a [Iterator] cast to [T].
     *
     * This function is intended for the Java side, for the Kotlin side, simply do `EmptyIterator<T>()`.
     *
     * @param [clz] only used for knowing what to cast `this` to
     */
    @JvmStatic
    @Suppress("UNUSED_PARAMETER")
    // the clz is needed here because java needs some sort of parameter containing the value of a generic to know
    // how to handle it.
    // 'for' would've probably been a better name for this function, but that's a keyword in both java and kotlin
    // so it doesn't work
    fun <T> of(clz: Class<T>): Iterator<T> = EmptyIterator

    @JvmSynthetic
    inline operator fun <T> invoke(): Iterator<T> = EmptyIterator
}

@Suppress("UNCHECKED_CAST")
object EmptyListIterator : MutableListIterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun hasPrevious(): Boolean = false

    override fun next(): Nothing = throw UnsupportedOperationException(EMPTY_ITERATOR)

    override fun nextIndex(): Int = -1

    override fun previous(): Nothing = throw UnsupportedOperationException(EMPTY_ITERATOR)

    override fun previousIndex(): Int = -1

    // mutable operations
    override fun add(element: Nothing) = throw UnsupportedOperationException(EMPTY_ITERATOR)

    override fun remove() = throw UnsupportedOperationException(EMPTY_ITERATOR)

    override fun set(element: Nothing) = throw UnsupportedOperationException(EMPTY_ITERATOR)

    // custom
    /**
     * Returns `this` empty-list-iterator as a [Iterator] cast to [T].
     *
     * This function is intended for the Java side, for the Kotlin side, simply do `EmptyListIterator<T>()`.
     *
     * @param [clz] only used for knowing what to cast `this` to
     */
    @JvmStatic
    @Suppress("UNUSED_PARAMETER")
    fun <T> of(clz: Class<T>): MutableListIterator<T> = EmptyListIterator as MutableListIterator<T>

    @JvmName("of")
    operator fun <T> invoke(): MutableListIterator<T> = EmptyListIterator as MutableListIterator<T>
}

// singleton iterators

/**
 * Represents a [iterator][Iterator] that iterates over a single object.
 *
 * This is a port of the `iterator` returned by the package-private `singletonIterator()` function in
 * [java.util.Collections], to allow usage of it from anywhere.
 */
class SingletonIterator<T>(private val item: T) : Iterator<T> {
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
class SingletonListIterator<T>(private val item: T) : MutableListIterator<T> {
    private var hasNext = true

    override fun hasNext(): Boolean = hasNext

    override fun hasPrevious(): Boolean = !hasNext

    override fun next(): T = if (hasNext) item.also { hasNext = false } else throw NoSuchElementException()

    override fun nextIndex(): Int = 0

    override fun previous(): T = if (!hasNext) item.also { hasNext = true } else throw NoSuchElementException()

    override fun previousIndex(): Int = 0

    // mutable operations
    override fun add(element: T) = throw UnsupportedOperationException(SINGLETON_ITERATOR)

    override fun remove() = throw UnsupportedOperationException(SINGLETON_ITERATOR)

    override fun set(element: T) = throw UnsupportedOperationException(SINGLETON_ITERATOR)
}

// zip
class ZippedIterator<A, B>(private val first: Iterator<A>, private val second: Iterator<B>) : Iterator<Tuple2<A, B>> {
    override fun hasNext(): Boolean = first.hasNext() && second.hasNext()

    override fun next(): Tuple2<A, B> = first.next() toT second.next()
}

infix fun <A, B> Iterator<A>.zipWith(other: Iterator<B>): Iterator<Tuple2<A, B>> = ZippedIterator(this, other)