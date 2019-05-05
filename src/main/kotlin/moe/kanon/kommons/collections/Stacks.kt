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

@file:JvmMultifileClass
@file:JvmName("Kollections")

package moe.kanon.kommons.collections

import moe.kanon.kommons.collections.iterables.EmptyIterable
import moe.kanon.kommons.collections.iterables.emptyIterable
import moe.kanon.kommons.collections.iterables.toTypedArray
import moe.kanon.kommons.collections.iterators.EmptyIterator
import moe.kanon.kommons.collections.iterators.SingletonIterator
import moe.kanon.kommons.collections.iterators.UnmodifiableIterator
import moe.kanon.kommons.func.Maybe
import moe.kanon.kommons.func.None
import moe.kanon.kommons.func.Some

/*
 * The 'Stack' & 'MutableStack' abstract implementations in this file is ported from the Stack[1] class from the Ceylon
 *  SKD.
 *
 * [1]: https://github.com/eclipse/ceylon-sdk/blob/master/source/ceylon/collection/Stack.ceylon
 */

// -- ABSTRACT IMPLEMENTATIONS -- \\

/**
 * An abstract read-only implementation of a [LIFO](https://en.wikipedia.org/wiki/Stack_(abstract_data_type)) data type.
 *
 * A `Stack` has a well-defined [top].
 */
interface Stack<out T> : KList<T> {
    /**
     * The element that is currently at the top of `this` stack, or `null` if the stack is empty.
     */
    val top: T?

    /**
     * Returns the element that is currently at the top of `this` stack, or throws a [NoSuchElementException] if `this`
     * stack is empty.
     */
    @JvmDefault
    fun peek(): T = top ?: throw NoSuchElementException("Stack is empty")
}

/**
 * An abstract implementation of a [LIFO](https://en.wikipedia.org/wiki/Stack_(abstract_data_type)) data type.
 *
 * A `Stack` has a well-defined [top]. Elements may be added to the top of the stack by [push], and removed from the
 * top of the stack by [pop].
 */
interface MutableStack<T> : Stack<T> {
    /**
     * Pushes a new `element` onto the top of `this` stack.
     */
    fun push(element: T)

    /**
     * Removes and returns the element at the top of `this` stack.
     */
    fun pop(): T?

    /**
     * Clears all the elements from `this` stack.
     */
    fun clear()

    /**
     * Pushes a new `element` onto the top of `this` stack.
     */
    @JvmDefault
    @JvmSynthetic
    operator fun plusAssign(element: T) = push(element)
}

// -- CONCRETE IMPLEMENTATIONS -- \\

/**
 * An implementation of a [Stack] that is *always* empty.
 */
object EmptyStack : Stack<Nothing> {
    override val top: Nothing? = null
    override val size: Int = 0

    override fun isEmpty(): Boolean = true

    override fun contains(element: Nothing): Boolean = false

    override fun get(index: Int): Nothing = throw NoSuchElementException()

    override fun iterator(): UnmodifiableIterator<Nothing> = EmptyIterator

    @JvmStatic
    @Suppress("UNUSED_PARAMETER")
    fun <T> of(clz: Class<T>): Stack<T> = EmptyStack
}

/**
 * An implementation of a [Stack] that contains only *one* element.
 */
class SingletonStack<T>(private val element: T) : Stack<T> {
    override val top: T? = element
    override val size: Int = 1

    override fun isEmpty(): Boolean = false

    override fun contains(element: T): Boolean = this.element == element

    override fun iterator(): UnmodifiableIterator<T> = SingletonIterator(element)
}

// TODO: This V
class UnmodifiableStack<T>

/**
 * A basic implementation of a [Stack].
 *
 * This implementation does *not* use an [array][Array] as a backing field, it only uses [Cell]s. This means that the
 * performance of this implementation may be less than ideal when the [size] grows large.
 *
 * @constructor Creates a new [LinkedStack] populated with the given `elements`.
 *
 * The `elements` are added to the `Stack` in reverse order, which means that the `first` entry in the `elements` will
 * be at the top of the stack, and the `last` entry will be at the bottom of the stack.
 *
 * @param [elements] the elements to populate the stack with
 */
class LinkedStack<T>(elements: Iterable<T>) : MutableStack<T> {
    constructor(vararg elements: T) : this(elements.toList())
    constructor() : this(emptyIterable())

    private var head: Maybe<Cell<T>> = None
    override val top: T? get() = head.fold({ null }, { it.element })
    override var size: Int = 0
        private set

    init {
        for (element in elements.reversed()) push(element)
    }

    /**
     * Pushes a new `element` onto the top of `this` stack.
     */
    override fun push(element: T) {
        if (head.isEmpty) {
            head = Some(Cell(element))
            size++
        } else {
            // V add to tail V
            //var iter = head
            //while (iter.value.next.isPresent) iter = iter.value.next
            //iter.value.next = Some(Cell(element))
            head = Some(Cell(element, head))
            size++
        }
    }

    /**
     * Removes and returns the element at the top of `this` stack.
     */
    override fun pop(): T? = head.fold({ null }, { cell ->
        this.head = cell.next
        size--
        cell.element
    })

    /**
     * Clears all the elements from `this` stack.
     */
    override fun clear() {
        head = None
    }

    override fun isEmpty(): Boolean = size <= 0

    override operator fun contains(element: T): Boolean = indexOf(element) >= 0

    override fun iterator(): UnmodifiableIterator<T> = head.fold({ EmptyIterator }, { CellIterator(it) })

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is LinkedStack<*> -> false
        head != other.head -> false
        size != other.size -> false
        else -> true
    }

    override fun hashCode(): Int {
        var result = head.hashCode()
        result = 31 * result + size
        return result
    }

    override fun toString(): String = "[${joinToString()}]"
}

class ArrayStack<T>(
    private val initialCapacity: Int,
    private val growthFactor: Float,
    elements: Iterable<T>
) : MutableStack<T> {
    private var backing: Array<T> = TODO()

    init {
        backing = elements.toTypedArray()
    }

    @JvmOverloads
    constructor(growthFactor: Float = 1.5F, elements: Iterable<T>) : this(elements.count(), growthFactor, elements)

    override val top: T?
        get() = TODO("not implemented")

    override val size: Int
        get() = TODO("not implemented")

    override fun push(element: T) {
        TODO("not implemented")
    }

    override fun pop(): T? {
        TODO("not implemented")
    }

    override fun clear() {
        TODO("not implemented")
    }

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

// -- FUNCTIONS - \\

/**
 * Returns a [Stack] populated with the given [elements].
 *
 * The `elements` are added to the `Stack` in reverse order, which means that the `first` entry in the `elements` will
 * be at the top of the stack, and the `last` entry will be at the bottom of the stack.
 */
fun <T> stackOf(vararg elements: T): Stack<T> = when (elements.size) {
    0 -> EmptyStack
    1 -> SingletonStack(elements[0])
    else -> LinkedStack(elements.toList())
}

/**
 * Returns a [EmptyStack] as a [Stack].
 */
@JvmName("emptyStack")
fun <T> emptyStackOf(): Stack<T> = EmptyStack

/**
 * Returns a [MutableStack] populated with the given [elements].
 *
 * The `elements` are added to the `Stack` in reverse order, which means that the `first` entry in the `elements` will
 * be at the top of the stack, and the `last` entry will be at the bottom of the stack.
 */
fun <T> mutableStackOf(vararg elements: T): MutableStack<T> = LinkedStack(elements.toList())

/**
 * Returns a [MutableStack] populated with the entries of `this` [Stack].
 */
fun <T> Stack<T>.toMutableStack(): MutableStack<T> = LinkedStack(this.toList())

/**
 * Returns a [Stack] populated with the entries of `this` [MutableStack].
 */
fun <T> MutableStack<T>.toStack(): Stack<T> = LinkedStack(this.toList())