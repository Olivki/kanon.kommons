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

package moe.kanon.kextensions.dom

import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node

/**
 * Retrieves a node specified by name.
 *
 * @param name The [nodeName][Node.getNodeName] of a node to retrieve.
 *
 * @return A [Node] (of any type) with the specified [name], or `null` if it does not identify any node in this map.
 */
public operator fun NamedNodeMap.get(name: String): Node? = this.getNamedItem(name)

/**
 * Returns the [indexth][index] item in the map. If [index] is greater than or equal to the number of nodes in this map,
 * this returns `null`.
 *
 * @param index The index of the node you want to access.
 *
 * @return The node at the [indexth][index] position in the map, or `null` if that is not a valid index.
 */
public operator fun NamedNodeMap.get(index: Int): Node? = this.item(index)

/**
 * Tests whether or not this [map][NamedNodeMap] contains the specified [name].
 */
public operator fun NamedNodeMap.contains(name: String): Boolean = this[name] != null

/**
 * Performs the given [action] on each [node][Node] in this node map.
 */
public inline fun NamedNodeMap.forEach(action: Node.() -> Unit) {
    val length = this.length

    for (i in 0 until length) action(this.item(i))
}

/**
 * Checks if this node map has no entries.
 */
public val NamedNodeMap.isEmpty: Boolean get() = this.length <= 0

/**
 * Creates a [Map] by collecting the entries from this [node map][NamedNodeMap] into a [HashMap][java.util.HashMap]
 * with [Node.getNodeName] as the `key`, and the [Node] instance as the `value`.
 *
 * > `this.nodeName:this`
 */
public fun NamedNodeMap.toMap(): Map<String, Node> {
    if (this.isEmpty) return emptyMap()

    val map = HashMap<String, Node>()

    this.forEach { map[this.nodeName] = this }

    return map
}

/**
 * Creates a [Set] by collecting the entries from this [node map][NamedNodeMap] into a [HashSet][java.util.HashSet]
 * with the [Node] instance as the value.
 */
public fun NamedNodeMap.toSet(): Set<Node> {
    if (this.isEmpty) return emptySet()

    val set = HashSet<Node>()

    this.forEach { set += this }

    return set
}
