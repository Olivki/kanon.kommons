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

package moe.kanon.kommons.collections.persistent

import moe.kanon.kommons.collections.Cell
import moe.kanon.kommons.collections.CellIterator
import moe.kanon.kommons.collections.EmptyIterator
import moe.kanon.kommons.collections.head
import moe.kanon.kommons.collections.isEmpty
import moe.kanon.kommons.collections.last
import moe.kanon.kommons.writeOut
import java.util.ArrayList

class PersistentLinkedList<out T> private constructor(override val size: Int, private val head: Cell<T>?) :
    AbstractPersistentList<T>() {
    companion object {
        private val EMPTY = PersistentLinkedList<Any>(0, null)

        /**
         * Returns an empty [PersistentLinkedList].
         */
        @Suppress("UNCHECKED_CAST")
        @JvmStatic fun <T> empty(): PersistentLinkedList<T> = EMPTY as PersistentLinkedList<T>

        @JvmName("populate")
        @JvmStatic inline operator fun <T> invoke(size: Int, transformer: (Int) -> T): PersistentLinkedList<T> =
            invoke((0 until size).map(transformer))

        /**
         * Returns a new [PersistentLinkedList] that contains the specified [elements].
         */
        @JvmName("of")
        @JvmStatic operator fun <T> invoke(elements: Iterable<T>): PersistentLinkedList<T> {
            if (elements.isEmpty) return empty()

            var size = 0
            var head: Cell<T>? = null

            for (element in elements.reversed()) {
                size++
                head = (if (head == null) Cell(element) else Cell(element, head))
            }

            return PersistentLinkedList(size, head)
        }

        /**
         * Returns a new [PersistentLinkedList] that contains the specified [elements].
         */
        @JvmName("of")
        @JvmStatic operator fun <T> invoke(vararg elements: T): PersistentLinkedList<T> {
            if (elements.isEmpty()) return empty()

            var size = 0
            var head: Cell<T>? = null

            for (element in elements.reversed()) {
                size++
                head = (if (head == null) Cell(element) else Cell(element, head))
            }

            return PersistentLinkedList(size, head)
        }
    }

    // -- PREPEND -- \\
    private fun prependList(list: PersistentLinkedList<@UnsafeVariance T>): PersistentLinkedList<T> = when (list.head) {
        null -> if (isEmpty) list else empty()
        else -> PersistentLinkedList(list.size + size, list.head.also { it.last().next = this.head })
    }

    override fun prepend(element: @UnsafeVariance T): PersistentLinkedList<T> = when (head) {
        null -> PersistentLinkedList(1, Cell(element))
        else -> PersistentLinkedList(size + 1, Cell(element, head))
    }

    override fun prepend(elements: Iterable<@UnsafeVariance T>): PersistentLinkedList<T> = when (head) {
        null -> if (elements.isEmpty) empty() else invoke(elements)
        else -> prependList(invoke(elements))
    }

    override fun prepend(elements: Array<out @UnsafeVariance T>): PersistentLinkedList<T> = when (head) {
        null -> if (elements.isEmpty()) empty() else invoke(elements.asIterable())
        else -> prependList(invoke(elements.asIterable()))
    }

    override fun prepend(elements: Sequence<@UnsafeVariance T>): PersistentLinkedList<T> = when (head) {
        null -> if (elements.isEmpty) empty() else invoke(elements.asIterable())
        else -> prependList(invoke(elements.asIterable()))
    }

    // -- APPEND -- \\
    private fun appendList(list: PersistentLinkedList<@UnsafeVariance T>): PersistentLinkedList<T> = when (head) {
        null -> if (list.isEmpty) empty() else list
        else -> PersistentLinkedList(size + list.size, head.also { it.last().next = list.head })
    }

    override fun append(element: @UnsafeVariance T): PersistentLinkedList<T> = when (head) {
        null -> PersistentLinkedList(1, Cell(element))
        else -> PersistentLinkedList(size + 1, head.also { it.last().next = Cell(element) })
    }

    override fun append(elements: Iterable<@UnsafeVariance T>): PersistentLinkedList<T> = when (head) {
        null -> if (elements.isEmpty) empty() else invoke(elements)
        else -> appendList(invoke(elements))
    }

    override fun append(elements: Array<out @UnsafeVariance T>): PersistentLinkedList<T> = when (head) {
        null -> if (elements.isEmpty()) empty() else invoke(elements.asIterable())
        else -> appendList(invoke(elements.asIterable()))
    }

    override fun append(elements: Sequence<@UnsafeVariance T>): PersistentLinkedList<T> = when (head) {
        null -> if (elements.isEmpty) empty() else invoke(elements.asIterable())
        else -> appendList(invoke(elements.asIterable()))
    }


    // -- REMOVE -- \\
    override fun remove(element: @UnsafeVariance T): PersistentLinkedList<T> {
        TODO("not implemented")
    }

    override fun removeAll(elements: Iterable<@UnsafeVariance T>): PersistentLinkedList<T> {
        TODO("not implemented")
    }

    override fun removeAll(elements: Array<out @UnsafeVariance T>): PersistentLinkedList<T> {
        TODO("not implemented")
    }

    override fun removeAll(elements: Sequence<@UnsafeVariance T>): PersistentLinkedList<T> {
        TODO("not implemented")
    }

    override fun iterator(): Iterator<T> = if (head == null) EmptyIterator else CellIterator(head)
}