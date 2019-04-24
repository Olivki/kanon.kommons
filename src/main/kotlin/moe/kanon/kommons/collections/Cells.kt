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

import moe.kanon.kommons.collections.iterators.UnmodifiableIterator
import moe.kanon.kommons.func.Maybe
import moe.kanon.kommons.func.Some

/*
 * The classes in this file are ported from the Cell[1] and LinkedCell[2] implementations found in the Ceylon SDK.
 *
 * [1]: https://github.com/eclipse/ceylon-sdk/blob/master/source/ceylon/collection/Cell.ceylon
 * [2]: https://github.com/eclipse/ceylon-sdk/blob/master/source/ceylon/collection/LinkedCell.ceylon
 */

/**
 * Represents a node in a [singly linked list](https://en.wikipedia.org/wiki/Linked_list#Singly_linked_list).
 *
 * @property [element] The element belonging to `this` node.
 * @property [next] The next node in the list.
 */
data class Cell<T> @JvmOverloads constructor(val element: T, var next: Maybe<Cell<T>> = Maybe.empty()) {
    override fun toString(): String = next.fold(
        { "Cell[$element]" },
        { "Cell[$element -> ${it.element}]" }
    )
}

class CellIterator<T>(start: Cell<T>) : UnmodifiableIterator<T>() {
    private var iter: Maybe<Cell<T>> = Some(start)

    override fun hasNext(): Boolean = iter.isPresent

    override fun next(): T = iter.getOrThrow { NoSuchElementException() }.let {
        this.iter = it.next
        it.element
    }
}

/**
 * Represents a node in a [singly linked list](https://en.wikipedia.org/wiki/Linked_list#Singly_linked_list) that has
 * a attribute to cache hash-codes.
 *
 * @property [element] The element belonging to `this` node.
 * @property [keyHash] The hash-code of the [element] *(sets)* or key *(maps)* for `this` cell.
 * @property [rest] The next node in the list.
 */
open class CachingCell<T> @JvmOverloads constructor(
    open val element: T,
    open val keyHash: Int,
    open var rest: Maybe<CachingCell<T>> = Maybe.empty()
) {
    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is CachingCell<*> -> false
        element != other.element -> false
        keyHash != other.keyHash -> false
        rest != other.rest -> false
        else -> true
    }

    override fun hashCode(): Int {
        var result = element?.hashCode() ?: 0
        result = 31 * result + keyHash
        result = 31 * result + rest.hashCode()
        return result
    }

    override fun toString(): String = rest.fold(
        { "Cell[$element]" },
        { "Cell[$element -> ${it.element}]" }
    )

    open operator fun component1(): T = element

    open operator fun component2(): Int = keyHash

    open operator fun component3(): Maybe<CachingCell<T>> = rest
}

class CachingCellIterator<T>(start: CachingCell<T>) : UnmodifiableIterator<T>() {
    private var iter: Maybe<CachingCell<T>> = Maybe(start)

    override fun hasNext(): Boolean = iter.fold({ false }, { it.rest.isPresent })

    override fun next(): T = iter.getOrThrow { NoSuchElementException() }.let {
        this.iter = it.rest
        it.element
    }
}

// linked
/**
 * A [Cell] with two traversal modes:
 *
 * - [rest] for storage.
 * - [next]/[previous] for stable iteration.
 *
 * This allows us to use the same `Cell` object in two different lists which have the same elements, but different
 * iteration order.
 */
@Suppress("DataClassPrivateConstructor")
data class LinkedCell<T> private constructor(
    override val element: T,
    override val keyHash: Int,
    override var rest: Maybe<CachingCell<T>>,
    var previous: Maybe<LinkedCell<T>>,
    var next: Maybe<LinkedCell<T>>
) : CachingCell<T>(element, keyHash, rest) {
    constructor(
        element: T,
        keyHash: Int,
        rest: Maybe<CachingCell<T>>,
        previous: Maybe<LinkedCell<T>>
    ) : this(element, keyHash, rest, previous, Maybe.empty())

    override fun toString(): String = when {
        previous.isEmpty && next.isEmpty -> "LinkedCell[$element]"
        previous.isEmpty && next.isPresent -> "LinkedCell[$element -> ${next.value}]"
        previous.isPresent && next.isPresent -> "LinkedCell[${previous.value} -> $element -> ${next.value}]"
        else -> "LinkedCell[$element]"
    }
}

class LinkedCellIterator<T>(start: LinkedCell<T>) : UnmodifiableIterator<T>() {
    private var iter: Maybe<LinkedCell<T>> = Maybe(start)

    override fun hasNext(): Boolean = iter.fold({ false }, { it.next.isPresent })

    override fun next(): T = iter.getOrThrow { NoSuchElementException() }.let {
        this.iter = it.next
        it.element
    }
}