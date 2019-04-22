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
@file:JvmName("KLists")

package moe.kanon.kommons.collections.lists

import moe.kanon.kommons.UNSUPPORTED
import moe.kanon.kommons.collections.iterators.SingletonIterator

private const val SINGLETON_LIST_EXCEPTION = "This operation is not supported for singleton lists"

class SingletonList<T>(private val item: T) : List<T> {
    override val size: Int = 1

    override fun contains(element: T): Boolean = item == element

    override fun containsAll(elements: Collection<T>): Boolean = elements.all { it == item }

    override fun get(index: Int): T = when (index) {
        0 -> item
        else -> throw IndexOutOfBoundsException()
    }

    override fun indexOf(element: T): Int = when (element) {
        item -> 0
        else -> -1
    }

    override fun isEmpty(): Boolean = false

    override fun iterator(): Iterator<T> = SingletonIterator(item)

    override fun lastIndexOf(element: T): Int = when (element) {
        item -> 0
        else -> -1
    }

    override fun listIterator(): ListIterator<T> {
        TODO("not implemented")
    }

    override fun listIterator(index: Int): ListIterator<T> = UNSUPPORTED(SINGLETON_LIST_EXCEPTION)

    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        TODO("not implemented")
    }
}

