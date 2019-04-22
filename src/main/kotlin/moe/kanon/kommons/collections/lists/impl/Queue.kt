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

package moe.kanon.kommons.collections.lists.impl

import moe.kanon.kommons.collections.arrays.createArray
import java.lang.System.arraycopy
import kotlin.math.min

interface Queue<out T> : Iterable<T> {
    val size: Int

    fun peek(): T

    fun isEmpty(): Boolean

    operator fun contains(item: @UnsafeVariance T): Boolean
}

interface MutableQueue<T> : Queue<T> {
    fun enqueue(element: T)

    @JvmDefault
    operator fun plusAssign(element: T) = enqueue(element)

    fun dequeue()

    @JvmDefault
    operator fun dec(): MutableQueue<T> = apply { dequeue() }
}

private const val DEFAULT_CAPACITY = 10

@Suppress("UNCHECKED_CAST")
class QueueImpl<T> @JvmOverloads constructor(initialCapacity: Int = DEFAULT_CAPACITY) : MutableQueue<T> {
    private var backing: Array<T> = when  {
        initialCapacity == 0 -> createArray(DEFAULT_CAPACITY)
        initialCapacity > 0 -> createArray(initialCapacity)
        else -> throw IllegalArgumentException("Size <$initialCapacity> is not valid")
    }
    private val capacity: Int get() = backing.size
    override var size: Int = 0
        private set

    private fun indexOf(item: T): Int = backing.indexOf(item)

    private fun checkOverflow(amount: Int): Boolean = (size + amount) > capacity

    // adapted from https://github.com/korlibs/kds/blob/master/kds/src/commonMain/kotlin/com/soywiz/kds/Deque.kt#L26
    private fun checkSize(amount: Int) {
        if (checkOverflow(amount)) {
            val oldBacking = backing
            val newBacking = createArray<T>(capacity * 2)
        }
    }

    private fun Array<T>.copyTo(out: Array<T>, start: Int, amount: Int) {
        val inSize = min(this.size - start, amount)
        val outSize = amount - inSize
        //this.copyInto(out, startIndex = inSize, endIndex = outSize)
        //arraycopy()
        //if (outSize > )
    }

    override fun isEmpty(): Boolean = size <= 0

    override fun peek(): T {
        TODO("not implemented")
    }

    override fun enqueue(element: T) {
        TODO("not implemented")
    }

    override fun dequeue() {
        TODO("not implemented")
    }

    override fun iterator(): Iterator<T> {
        TODO("not implemented")
    }

    override fun contains(item: T): Boolean = indexOf(item) >= 0
}