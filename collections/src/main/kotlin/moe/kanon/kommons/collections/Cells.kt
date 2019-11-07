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
 * ========================= CEYLON LICENSE =========================
 *
 * Copyright (c) 2011-2017 Red Hat Inc. and/or its affiliates and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 */

@file:JvmName("KCells")

package moe.kanon.kommons.collections

import moe.kanon.kommons.PortOf

/**
 * Represents a node in a singly linked list.
 *
 * ### Port Details
 * [ceylon.collection.Cell](https://github.com/eclipse/ceylon-sdk/blob/master/source/ceylon/collection/Cell.ceylon#L10)
 *
 * @property [element] The element belonging to `this` node.
 * @property [next] The next node in the chain.
 */
@PortOf("ceylon.collection.Cell")
data class Cell<out T> @JvmOverloads constructor(val element: T, var next: Cell<@UnsafeVariance T>? = null) {
    /**
     * Returns `this` cell as an [Iterable] instance, that iterates over the [next] chain of `this` cell.
     */
    fun asIterable(): Iterable<Cell<@UnsafeVariance T>> = object : Iterable<Cell<@UnsafeVariance T>> {
        override fun iterator(): Iterator<Cell<T>> = object : AbstractIterator<Cell<@UnsafeVariance T>>() {
            override fun computeNext() {
                if (next == null) {
                    done()
                } else {
                    var currentCell = next
                    while (currentCell?.next != null) currentCell = currentCell.next
                    setNext(currentCell!!)
                }
            }
        }
    }

    override fun toString(): String = if (next == null) "Cell[$element]" else "Cell[$element -> ${next!!.element}]"
}

/**
 * Returns the last [Cell] in the [next][Cell.next] chain of `this` cell, or throws [NoSuchElementException] if `this`
 * cell has no `next` cell.
 */
@JvmName("getLast")
fun <T> Cell<T>.last(): Cell<T> {
    if (next == null) throw NoSuchElementException()
    var currentCell = next
    while (currentCell?.next != null) currentCell = currentCell.next
    return currentCell!!
}

/**
 * Returns the last [Cell] in the [next][Cell.next] chain of `this` cell, or `null` if `this` cell has no `next` cell.
 */
@JvmName("getLastOrNull")
fun <T> Cell<T>.lastOrNull(): Cell<T>? {
    if (next == null) return null
    var currentCell = next
    while (currentCell?.next != null) currentCell = currentCell.next
    return currentCell!!
}

/**
 * An iterator that iterates over [Cells][Cell] and returns the underlying values.
 *
 * ### Port Details
 * [ceylon.collection.CellIterator](https://github.com/eclipse/ceylon-sdk/blob/master/source/ceylon/collection/Cell.ceylon#L21)
 */
@PortOf("ceylon.collection.CellIterator")
class CellIterator<out T>(start: Cell<T>) : Iterator<T> {
    private var currentCell: Cell<T>? = start

    override fun hasNext(): Boolean = currentCell != null

    override fun next(): T = if (!hasNext()) throw NoSuchElementException() else currentCell!!.let {
        currentCell = it.next
        it.element
    }
}

/**
 * A [Cell] that knows of the [previous] element and the [next] element in a chain.
 *
 * @property [next] The next node in the chain.
 * @property [previous] The previous node in the chain.
 */
data class LinkedCell<out T> @JvmOverloads constructor(
    val element: T,
    var previous: LinkedCell<@UnsafeVariance T>? = null,
    var next: LinkedCell<@UnsafeVariance T>? = null
) {
    /**
     * Returns `this` cell as an [Iterable] instance, that iterates over the [next] chain of `this` cell.
     */
    fun asNextIterable(): Iterable<LinkedCell<@UnsafeVariance T>> = object : Iterable<LinkedCell<@UnsafeVariance T>> {
        override fun iterator(): Iterator<LinkedCell<T>> = object : AbstractIterator<LinkedCell<@UnsafeVariance T>>() {
            override fun computeNext() {
                if (next == null) {
                    done()
                } else {
                    var currentCell = next
                    while (currentCell?.next != null) currentCell = currentCell.next
                    setNext(currentCell!!)
                }
            }
        }
    }

    /**
     * Returns `this` cell as an [Iterable] instance, that iterates over the [previous] chain of `this` cell.
     */
    fun asPreviousIterable(): Iterable<LinkedCell<@UnsafeVariance T>> = object : Iterable<LinkedCell<@UnsafeVariance T>> {
        override fun iterator(): Iterator<LinkedCell<T>> = object : AbstractIterator<LinkedCell<@UnsafeVariance T>>() {
            override fun computeNext() {
                if (previous == null) {
                    done()
                } else {
                    var currentCell = previous
                    while (currentCell?.previous != null) currentCell = currentCell.previous
                    setNext(currentCell!!)
                }
            }
        }
    }

    override fun toString(): String = when {
        previous == null && next == null -> "LinkedCell[$element]"
        previous == null && next != null -> "LinkedCell[$element -> ${next!!.element}]"
        previous != null && next != null -> "LinkedCell[${previous!!.element} -> $element -> ${next!!.element}]"
        else -> "LinkedCell[$element]"
    }
}

/**
 * Returns the first [LinkedCachingCell] in the [previous][LinkedCachingCell.previous] chain of `this` cell, or throws
 * [NoSuchElementException] if `this` cell has no `previous` cell.
 */
@JvmName("getFirst")
fun <T> LinkedCell<T>.first(): LinkedCell<T> {
    if (previous == null) throw NoSuchElementException()
    var currentCell = previous
    while (currentCell?.previous != null) currentCell = currentCell.previous
    return currentCell!!
}

/**
 * Returns the last [LinkedCachingCell] in the [next][LinkedCachingCell.next] chain of `this` cell, or throws [NoSuchElementException]
 * if `this` cell has no `next` cell.
 */
@JvmName("getLast")
fun <T> LinkedCell<T>.last(): LinkedCell<T> {
    if (next == null) throw NoSuchElementException()
    var currentCell = next
    while (currentCell?.next != null) currentCell = currentCell.next
    return currentCell!!
}

/**
 * Returns the first [LinkedCell] in the [previous][LinkedCell.previous] chain of `this` cell, or `null` if `this` cell
 * has no `previous` cell.
 */
@JvmName("getFirstOrNull")
fun <T> LinkedCell<T>.firstOrNull(): LinkedCell<T>? {
    if (previous == null) return null
    var currentCell = previous
    while (currentCell?.previous != null) currentCell = currentCell.previous
    return currentCell!!
}

/**
 * Returns the last [LinkedCell] in the [next][LinkedCell.next] chain of `this` cell, or `null` if `this` cell has no
 * `next` cell.
 */
@JvmName("getLastOrNull")
fun <T> LinkedCell<T>.lastOrNull(): LinkedCell<T>? {
    if (next == null) return null
    var currentCell = next
    while (currentCell?.next != null) currentCell = currentCell.next
    return currentCell!!
}

/**
 * An iterator that iterates over [LinkedCells][LinkedCell] and returns the underlying values.
 */
class LinkedCellIterator<out T>(start: LinkedCell<T>) : Iterator<T> {
    private var currentCell: LinkedCell<T>? = start

    override fun hasNext(): Boolean = if (currentCell == null) false else currentCell!!.next != null

    override fun next(): T = if (!hasNext()) throw NoSuchElementException() else currentCell!!.let {
        currentCell = it.next
        it.element
    }
}

/**
 * Represents a node in a singly linked list that has a attribute to cache hash-codes.
 *
 * ### Port Details
 * [ceylon.collection.CachingCell](https://github.com/eclipse/ceylon-sdk/blob/master/source/ceylon/collection/Cell.ceylon#L36)
 *
 * @property [element] The element belonging to `this` node.
 * @property [keyHash] The hash-code of the [element] *(sets)* or key *(maps)* for `this` cell.
 * @property [rest] The next node in the chain.
 */
@PortOf("ceylon.collection.CachingCell")
open class CachingCell<out T> @JvmOverloads constructor(
    open val element: T,
    open val keyHash: Int,
    open var rest: CachingCell<@UnsafeVariance T>? = null
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

    override fun toString(): String = when (rest) {
        null -> "CachingCell[$element]"
        else -> "CachingCell[$element -> ${rest!!.element}]"
    }

    open operator fun component1(): T = element

    open operator fun component2(): Int = keyHash

    open operator fun component3(): CachingCell<T>? = rest
}

/**
 * Returns the last [CachingCell] in the [rest][CachingCell.rest] chain of `this` cell, or throws
 * [NoSuchElementException] if `this` cell has no `rest` cell.
 */
@JvmName("getLast")
fun <T> CachingCell<T>.last(): CachingCell<T> {
    if (rest == null) throw NoSuchElementException()
    var currentCell = rest
    while (currentCell?.rest != null) currentCell = currentCell.rest
    return currentCell!!
}

/**
 * Returns the last [CachingCell] in the [rest][CachingCell.rest] chain of `this` cell, or `null` if `this` cell has no
 * `rest` cell.
 */
@JvmName("getLastOrNull")
fun <T> CachingCell<T>.lastOrNull(): CachingCell<T>? {
    if (rest == null) return null
    var currentCell = rest
    while (currentCell?.rest != null) currentCell = currentCell.rest
    return currentCell!!
}

/**
 * An iterator that iterates over [CachingCells][CachingCell] and returns the underlying values.
 *
 * ### Port Details
 * [ceylon.collection.CachingCellIterator](https://github.com/eclipse/ceylon-sdk/blob/master/source/ceylon/collection/Cell.ceylon#L49)
 */
@PortOf("ceylon.collection.CachingCellIterator")
class CachingCellIterator<out T>(start: CachingCell<T>) : Iterator<T> {
    private var currentCell: CachingCell<T>? = start

    override fun hasNext(): Boolean = if (currentCell == null) false else currentCell!!.rest != null

    override fun next(): T = if (!hasNext()) throw NoSuchElementException() else currentCell!!.let {
        currentCell = it.rest
        it.element
    }
}

/**
 * A [Cell] with two traversal modes:
 *
 * - [rest] for storage.
 * - [next]/[previous] for stable iteration.
 *
 * This allows us to use the same `Cell` object in two different lists which have the same elements, but different
 * iteration order.
 *
 * ### Port Details
 * [ceylon.collection.LinkedCell](https://github.com/eclipse/ceylon-sdk/blob/master/source/ceylon/collection/LinkedCell.ceylon#L18)
 *
 * @property [next] The next node in the chain.
 * @property [previous] The previous node in the chain.
 */
@PortOf("ceylon.collection.LinkedCell")
data class LinkedCachingCell<out T> @JvmOverloads constructor(
    override val element: T,
    override val keyHash: Int,
    override var rest: CachingCell<@UnsafeVariance T>?,
    var previous: LinkedCachingCell<@UnsafeVariance T>?,
    var next: LinkedCachingCell<@UnsafeVariance T>? = null
) : CachingCell<T>(element, keyHash, rest) {
    override fun toString(): String = when {
        previous == null && next == null && rest != null -> "LinkedCachingCell[$element -> ${rest!!.element}]"
        previous == null && next == null -> "LinkedCachingCell[$element]"
        previous == null && next != null -> "LinkedCachingCell[$element -> ${next!!.element}]"
        previous != null && next != null -> "LinkedCachingCell[${previous!!.element} -> $element -> ${next!!.element}]"
        else -> "LinkedCachingCell[$element]"
    }
}

/**
 * Returns the first [LinkedCachingCell] in the [previous][LinkedCachingCell.previous] chain of `this` cell, or throws
 * [NoSuchElementException] if `this` cell has no `previous` cell.
 */
@JvmName("getFirst")
fun <T> LinkedCachingCell<T>.first(): LinkedCachingCell<T> {
    if (previous == null) throw NoSuchElementException()
    var currentCell = previous
    while (currentCell?.previous != null) currentCell = currentCell.previous
    return currentCell!!
}

/**
 * Returns the last [LinkedCachingCell] in the [next][LinkedCachingCell.next] chain of `this` cell, or throws [NoSuchElementException]
 * if `this` cell has no `next` cell.
 */
@JvmName("getLast")
fun <T> LinkedCachingCell<T>.last(): LinkedCachingCell<T> {
    if (next == null) throw NoSuchElementException()
    var currentCell = next
    while (currentCell?.next != null) currentCell = currentCell.next
    return currentCell!!
}

/**
 * Returns the first [LinkedCachingCell] in the [previous][LinkedCachingCell.previous] chain of `this` cell, or `null`
 * if `this` cell has no `previous` cell.
 */
@JvmName("getFirstOrNull")
fun <T> LinkedCachingCell<T>.firstOrNull(): LinkedCachingCell<T>? {
    if (previous == null) return null
    var currentCell = previous
    while (currentCell?.previous != null) currentCell = currentCell.previous
    return currentCell!!
}

/**
 * Returns the last [LinkedCachingCell] in the [next][LinkedCachingCell.next] chain of `this` cell, or `null` if `this`
 * cell has no `next` cell.
 */
@JvmName("getLastOrNull")
fun <T> LinkedCachingCell<T>.lastOrNull(): LinkedCachingCell<T>? {
    if (next == null) return null
    var currentCell = next
    while (currentCell?.next != null) currentCell = currentCell.next
    return currentCell!!
}

/**
 * An iterator that iterates over [LinkedCells][LinkedCachingCell] and returns the underlying values.
 *
 * ### Port Details
 * [ceylon.collection.LinkedCellIterator](https://github.com/eclipse/ceylon-sdk/blob/master/source/ceylon/collection/LinkedCell.ceylon#L25)
 */
@PortOf("ceylon.collection.LinkedCellIterator")
class LinkedCachingCellIterator<out T>(start: LinkedCachingCell<T>) : Iterator<T> {
    private var currentCell: LinkedCachingCell<T>? = start

    override fun hasNext(): Boolean = if (currentCell == null) false else currentCell!!.next != null

    override fun next(): T = if (!hasNext()) throw NoSuchElementException() else currentCell!!.let {
        currentCell = it.next
        it.element
    }
}
