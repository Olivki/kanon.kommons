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
 *
 * ================== CEYLON LICENSE ==================
 *
 * Copyright (c) 2011-2017 Red Hat Inc. and/or its affiliates and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package moe.kanon.kommons.collections

import moe.kanon.kommons.collections.iterables.toTypedArray
import moe.kanon.kommons.collections.iterators.UnmodifiableIterator

/*
 * This implementation is ported from the Queue[1] class from the Ceylon SKD.
 *
 * [1]: https://github.com/eclipse/ceylon-sdk/blob/master/source/ceylon/collection/Queue.ceylon
 */

/**
 * An abstract read-only implementation of a [FIFO](https://en.wikipedia.org/wiki/FIFO_(computing_and_electronics)) data
 * type.
 *
 * A `Queue` has a well-defined [front] and [back].
 */
interface Queue<out T> : Kollection<T> {
    /**
     * The element that is currently at the back of `this` queue, or `null` if the queue is empty.
     */
    val back: T?
}

/**
 * An implementation of a [FIFO](https://en.wikipedia.org/wiki/FIFO_(computing_and_electronics)) data type.
 *
 * A `Queue` has a well-defined [front] and [back]. Elements may be added to the `back` of the queue by [offer], and
 * removed from the front of the queue by [accept].
 */
interface MutableQueue<T> : Queue<T> {
    /**
     * Adds a new element to the back of `this` queue.
     */
    fun enqueue(element: T)

    /**
     * Removes and returns the element that is at the front of `this` queue.
     */
    fun dequeue(): T?
}

class ArrayQueue<T>(elements: Iterable<T>) : MutableQueue<T> {
    private var backing: Array<T> = elements.toTypedArray()

    override val back: T? get() = backing.getOrNull(0)

    override fun enqueue(element: T) {
        TODO("not implemented")
    }

    override fun dequeue(): T? {
        TODO("not implemented")
    }

    override val size: Int
        get() = TODO("not implemented")

    override fun isEmpty(): Boolean {
        TODO("not implemented")
    }

    override fun contains(element: T): Boolean {
        TODO("not implemented")
    }

    override fun iterator(): UnmodifiableIterator<T> {
        TODO("not implemented")
    }
}