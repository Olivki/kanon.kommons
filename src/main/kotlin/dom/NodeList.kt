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

@file:JvmName("NodeListUtils")

package moe.kanon.kextensions.dom

import org.w3c.dom.Node
import org.w3c.dom.NodeList

/**
 * Returns the [index]th item in the collection.
 *
 * If [index] is greater than or equal to the number of nodes in the list, this returns `null`.
 *
 * @param index Index into the collection.
 *
 * @return The node at the [index]th position in this [NodeList], or `null` if that is not a valid index.
 */
public operator fun NodeList.get(index: Int): Node = this.item(index)

/**
 * Performs the given [action] on each element.
 */
public inline fun NodeList.forEach(action: Node.() -> Unit) {
    for (i in 0 until this.length) action(this[i])
}

/**
 * Performs the given [action] on each element, providing sequential index with the element.
 *
 * @param action Function that takes the index of an element and the element itself and performs the desired action
 * on the element.
 */
public inline fun NodeList.forEachIndexed(action: (index: Int, Node) -> Unit) {
    for (i in 0 until this.length) action(i, this[i])
}

/**
 * Checks whether or not this node list is empty.
 */
public fun NodeList.isEmpty(): Boolean = this.length <= 0

/**
 * Creates a [List] with the same content as this node list.
 */
public fun NodeList.toList(): List<Node> {
    if (this.isEmpty()) emptyList<Node>()

    val list = mutableListOf<Node>()

    for (i in 0 until this.length) list += this[i]

    return list
}