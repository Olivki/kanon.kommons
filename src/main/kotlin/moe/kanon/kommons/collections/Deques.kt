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

package moe.kanon.kommons.collections

/**
 * An abstract read-only implementation of a double-ended queue.
 */
interface Deque<out T> : Container<T> {
    /**
     * The element at the front of the queue, or `null` if the queue is empty.
     */
    val front: T?
    /**
     * The element at the back of the queue, or `null` if the queue is empty.
     */
    val back: T?

    /**
     * Returns the element at the front of the queue *without* removing it, or throws a [NoSuchElementException] if
     * the queue is empty.
     */
    @JvmDefault
    fun peek(): T = front ?: throw NoSuchElementException("Deque is empty")
}

interface MutableDeque<T> : Deque<T> {
    // push
    fun addToFront(element: T)

    // enqueue / offer
    fun addToBack(element: T)

    // pop
    fun removeFromFront(): T?

    // dequeue / accept
    fun removeFromBack(): T?
}