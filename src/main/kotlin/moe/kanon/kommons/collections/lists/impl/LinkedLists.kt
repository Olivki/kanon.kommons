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

import moe.kanon.kommons.collections.iterators.EmptyListIterator
import moe.kanon.kommons.requireNoNull

/*
 * This file contains very simple and probably naïve implementations of a linked-list. This is done solely for the sake
 * of education and learning, they are not intended to be any sort of replacement for the linked-lists already available
 * in the standard library.
 *      The goal of this was to created a linked-list that was *not* backed by an array. And as such, these
 * implementations do *not* extend 'List' & 'MutableList' but rather 'Collection' & 'MutableCollection', this is to
 * reduce the complexity of the classes, as they have no backing array which would make a lot of 'List' operations
 * somewhat expensive due to the additional overhead of indexing.
 */

/**
 * A basic abstract implementation of a read-only view of a linked-list.
 */
interface LinkedList<out T> : Collection<T> {
    /**
     * Returns the first object in `this` list, or `null` if `this` list is empty.
     */
    val head: T?

    /**
     * Returns the last object in `this` list, or `null` if `this` list is empty or if `this` list only contains one
     * object.
     */
    val tail: T?
}

/**
 * A basic abstract implementation of a linked-list.
 */
interface MutableLinkedList<T> : LinkedList<T>, MutableCollection<T>

class DoublyLinkedList<T>(other: Collection<T>) : MutableLinkedList<T>, AbstractMutableCollection<T>() {
    constructor() : this(emptyList())

    constructor(vararg items: T) : this(items.toList())

    init {
        addAll(other)
    }

    private var firstNode: Node? = null
    private var lastNode: Node? = null
    private val nodeIterator: MutableIterator<Node>
        get() = when (firstNode) {
            null -> EmptyListIterator()
            else -> NodeIterator(firstNode!!)
        }
    private val nodeSequence: Sequence<Node> get() = Sequence { nodeIterator }
    private val indexedSize: Int get() = size - 1
    override val head: T? get() = firstNode?.value
    override val tail: T? get() = lastNode?.value
    override val size: Int
        get() = when {
            firstNode == null -> 0
            lastNode == null -> 1
            else -> nodeSequence.count()
        }

    override fun add(element: T): Boolean {
        val first = firstNode
        return if (first == null) {
            firstNode = Node(element)
            true
        } else {
            var current = requireNoNull(firstNode)
            while (current.hasNext) current = current.next!!
            val last = Node(element)
            last.prev = current
            current.next = last
            lastNode = last
            true
        }
    }

    override fun iterator(): MutableIterator<T> = ValueIterator(nodeIterator)

    private inner class Node(val value: T) {
        var prev: Node? = null
            set(value) {
                when {
                    value != null && field != null -> {
                        value.prev = field
                        field = value
                    }
                    value == null && field != null -> {
                        val stored = field!!.prev
                        field = null
                        field = stored
                    }
                    else -> field = value
                }
            }
        val hasPrev: Boolean get() = prev != null
        var next: Node? = null
            set(value) {
                when {
                    // if we're attempting to replace a 'next' node that already exists we don't want to lose all the
                    // already connected links, so we make sure to just move our current 'next' node to the the 'value'
                    // node
                    value != null && field != null -> {
                        value.next = field
                        field = value
                    }
                    // if we're unlinking this node from the next node we want to shift the links contained by the 'next'
                    // node to this node so that we don't delete the entire list when removing one node
                    value == null && field != null -> {
                        val stored = field!!.next
                        // probably don't need to set 'field' to 'null' before replacing?
                        field = null
                        field = stored
                    }
                    // this should pretty much just be the equivalent to 'value == null && field == null'
                    else -> field = value
                }
            }
        val hasNext: Boolean get() = next != null

        fun removeNext() {
            next = null
        }

        override fun equals(other: Any?): Boolean {
            return when {
                this === other -> true
                javaClass != other?.javaClass -> false
                else -> {
                    other as DoublyLinkedList<*>.Node

                    if (value != other.value) return false
                    if (next != other.next) return false

                    true
                }
            }

        }

        override fun hashCode(): Int {
            var result = value?.hashCode() ?: 0
            result = 31 * result + (next?.hashCode() ?: 0)
            return result
        }

        override fun toString(): String = "Node(value=$value, prev=${prev?.value}, next=${next?.value})"

        fun friendlyString(): String = "[$value${prev?.let { ", [${it.friendlyString()}]" }}]"
    }

    private inner class NodeIterator(val start: Node) : MutableIterator<Node> {
        var flag = true
        var current: Node? = null

        override fun hasNext(): Boolean =
            if (current == null && flag) start.hasNext.also { flag = false } else current?.hasNext ?: false

        override fun next(): Node = when (current) {
            null -> {
                current = start
                start
            }
            else -> if (current!!.hasNext) {
                current = current!!.next
                current!!
            } else throw NoSuchElementException()
        }

        override fun remove() {
            val cur = requireNoNull(current) { "Can't remove from empty linked-list" }
            when {
                // it's a normal node
                cur.hasPrev && cur.hasNext -> {
                    val prev = cur.prev!!
                    prev.removeNext()
                    current = prev.next
                }
                // it's the last element (tail)
                cur.hasPrev && !cur.hasNext -> {
                    val prev = cur.prev!!
                    prev.removeNext()
                    current = prev
                    this@DoublyLinkedList.lastNode = current
                }
                // it's the first element in the collection (head)
                !cur.hasPrev && cur.hasNext -> {
                    val next = cur.next!!
                    next.prev = next
                    current = next
                    this@DoublyLinkedList.firstNode = current
                }
                // it's the only element in the collection
                !cur.hasPrev && !cur.hasNext -> {
                    current = null
                    this@DoublyLinkedList.firstNode = null
                    this@DoublyLinkedList.lastNode = null
                }
                else -> throw UnsupportedOperationException()
            }
        }
    }

    private inner class ValueIterator(val backing: MutableIterator<Node>) : MutableIterator<T> {
        override fun hasNext(): Boolean = backing.hasNext()

        override fun next(): T = backing.next().value

        override fun remove() = backing.remove()
    }
}

fun <T> doublyLinkedListOf(vararg items: T): LinkedList<T> = DoublyLinkedList(items.toList())

fun <T> mutableDoublyLinkedListOf(vararg items: T): MutableLinkedList<T> = DoublyLinkedList(items.toList())