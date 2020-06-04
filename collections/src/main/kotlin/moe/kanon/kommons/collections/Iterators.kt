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

@file:JvmName("KIterators")
@file:Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER")

package moe.kanon.kommons.collections

private const val EMPTY_ITERATOR = "Can not iterate over an empty iterator."

private object EmptyIterator : Iterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun next(): Nothing = throw UnsupportedOperationException(EMPTY_ITERATOR)
}

private class SingletonIterator<T>(item: T) : AbstractIterator<T>() {
    private var item: T? = item

    override fun computeNext() {
        if (item != null) {
            setNext(item!!)
            item = null
        } else {
            done()
        }
    }
}

private class UnmodifiableIterator<T>(val delegate: Iterator<T>) : Iterator<T> by delegate

/**
 * Returns an [Iterator] instance that iterates over no elements.
 */
@JvmName("empty")
fun <T> emptyIterator(): Iterator<T> = EmptyIterator

/**
 * Returns an [Iterator] instance that iterates over no elements.
 */
@JvmName("of")
fun <T> iteratorOf(): Iterator<T> = emptyIterator()

/**
 * Returns a new [Iterator] that only iterates over the given [item].
 */
@Deprecated(
    message = "Use 'iteratorOf(item)' instead.",
    replaceWith = ReplaceWith("iteratorOf(item)", "moe.kanon.kommons.collections")
)
@JvmName("singletonOf")
fun <T> singletonIteratorOf(item: T): Iterator<T> = SingletonIterator(item)

/**
 * Returns a new [Iterator] that only iterates over the given [item].
 */
@JvmName("of")
fun <T> iteratorOf(item: T): Iterator<T> = SingletonIterator(item)

/**
 * Returns a new [Iterator] that iterates over the given [items].
 */
fun <T> iteratorOf(vararg items: T): Iterator<T> = items.iterator()

/**
 * Returns a new [Iterator] that iterates over the given [items].
 */
@Deprecated(
    message = "Use 'iteratorOf(items)' instead.",
    replaceWith = ReplaceWith("iteratorOf(items)", "moe.kanon.kommons.collections")
)
fun <T> iteratorFor(vararg items: T): Iterator<T> = iteratorOf(*items)

/**
 * Returns an unmodifiable view of `this` iterator.
 *
 * Any attempts to invoke the `remove` operation on the returned instance will result in a
 * [UnsupportedOperationException] being thrown.
 */
@JvmName("unmodifiable")
fun <T> Iterator<T>.asUnmodifiable(): Iterator<T> = UnmodifiableIterator(this)

// -- ZIPPED ITERATOR -- \\
/**
 * An [iterator][Iterator] that returns elements from two iterators, [first] and [second].
 *
 * @property [first] The first [Iterator] instance that `this` zipped-iterator uses.
 * @property [second] The second [Iterator] instance that `this` zipped-iterator uses.
 */
class ZippedIterator<out A, out B>(
    private val first: Iterator<A>,
    private val second: Iterator<B>
) : Iterator<Pair<A, B>> {
    override fun hasNext(): Boolean = first.hasNext() && second.hasNext()

    override fun next(): Pair<A, B> = first.next() to second.next()
}

/**
 * Creates a [ZippedIterator] from `this` iterator and the given [other] instance.
 */
@JvmName("zippedOf")
infix fun <A, B> Iterator<A>.zipWith(other: Iterator<B>): Iterator<Pair<A, B>> = ZippedIterator(this, other)