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

@file:JvmName("NamedNodeMapUtils")

package moe.kanon.kextensions.dom

import org.w3c.dom.DOMException
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node

/**
 * Adds a node using its [nodeName][Node.getNodeName] attribute.
 *
 * If a node with that name is already present in this map, it is replaced by the new one. Replacing a node by itself
 * has no effect.
 *
 * As the [nodeName][Node.getNodeName] attribute is used to derive the name which the node must be stored under,
 * multiple nodes of certain types *(those that have a "special" string value)* cannot be stored as the names would
 * clash. This is seen as preferable to allowing nodes to be aliased.
 *
 * @param child A node to store in this map. The node will later be accessible using the value of its
 * [nodeName][Node.getNodeName] attribute.
 *
 * @exception DOMException
 *
 * `WRONG_DOCUMENT_ERR`: Raised if [child] was created from a different document than the one that created this map.
 *
 * `NO_MODIFICATION_ALLOWED_ERR`: Raised if this map is readonly.
 *
 * `INUSE_ATTRIBUTE_ERR`: Raised if [child] is an [Attr][org.w3c.dom.Attr] that is already an attribute of another
 * [Element][org.w3c.dom.Element] object. The DOM user must explicitly clone [Attr][org.w3c.dom.Attr] nodes to re-use
 * them in other elements.
 *
 * `HIERARCHY_REQUEST_ERR`: Raised if an attempt is made to add a node doesn't belong in this NamedNodeMap.
 * Examples would include trying to insert something other than an [Attr][org.w3c.dom.Attr] node into an
 * [Element][org.w3c.dom.Element]'s map of attributes, or a non-Entity node into the
 * [DocumentType][org.w3c.dom.DocumentType]'s map of [Entities][org.w3c.dom.Entity].
 */
public operator fun NamedNodeMap.plusAssign(child: Node) {
    this.setNamedItem(child)
}

/**
 * Removes a node specified by name.
 *
 * When this map contains the attributes attached to an element, if the removed attribute is known to have a default
 * value, an attribute immediately appears containing the default value as well as the corresponding namespace URI,
 * local name, and prefix when applicable.
 *
 * @param name The [nodeName][Node.getNodeName] of the node to remove.
 *
 * @exception DOMException
 *
 * `NOT_FOUND_ERR`: Raised if there is no node named [name] in this map.
 *
 * `NO_MODIFICATION_ALLOWED_ERR`: Raised if this map is readonly.
 */
public operator fun NamedNodeMap.minusAssign(name: String) {
    this.removeNamedItem(name)
}

/**
 * Retrieves a node specified by name.
 *
 * @param name The [nodeName][Node.getNodeName] of a node to retrieve.
 *
 * @return A [Node] (of any type) with the specified [name], or `null` if it does not identify any node in this map.
 */
public operator fun NamedNodeMap.get(name: String): Node? = this.getNamedItem(name)

/**
 * Retrieves a node specified by local name and namespace URI.
 *
 * Per [XML Namespaces](http://www.w3.org/TR/1999/REC-xml-names-19990114/), applications must use the value `null` as
 * the [namespaceURI] parameter for methods if they wish to have no namespace.
 *
 * @param namespaceURI The namespace URI of the node to retrieve.
 * @param localName The local name of the node to retrieve.
 *
 * @return A [Node] *(of any type)* with the specified local name and namespace URI, or `null` if they do not identify
 * any node in this map.
 *
 * @exception DOMException
 *
 * `NOT_SUPPORTED_ERR`: May be raised if the implementation does not support the feature `"XML"` and the language
 * exposed through the Document does not support XML Namespaces *(such as
 * [HTML 4.01](http://www.w3.org/TR/1999/REC-html401-19991224/))*.
 *
 * @since DOM Level 2
 */
public operator fun NamedNodeMap.get(namespaceURI: String, localName: String): Node? =
    this.getNamedItemNS(namespaceURI, localName)

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
// This is not marked as "forEachIndexed" because the index of the retrieved value in NamedNodeMap is not relevant to
// anything, because the entries are NOT stored in any certain order, so the index of a specific Node will be different
// with each access.
public inline fun NamedNodeMap.forEach(action: Node.() -> Unit) {
    val length = this.length

    for (i in 0 until length) action(this.item(i))
}

/**
 * Checks if this node map has no entries.
 */
public fun NamedNodeMap.isEmpty(): Boolean = this.length <= 0

/**
 * Creates a [Map] by collecting the entries from this [node map][NamedNodeMap] into a [HashMap][java.util.HashMap]
 * with [Node.getNodeName] as the `key`, and the [Node] instance as the `value`.
 *
 * > `this.nodeName:this`
 */
public fun NamedNodeMap.toMap(): Map<String, Node> {
    if (this.isEmpty()) return emptyMap()

    val map = HashMap<String, Node>()

    this.forEach { map[this.nodeName] = this }

    return map
}

/**
 * Creates a [Set] by collecting the entries from this [node map][NamedNodeMap] into a [HashSet][java.util.HashSet]
 * with the [Node] instance as the value.
 */
public fun NamedNodeMap.toSet(): Set<Node> {
    if (this.isEmpty()) return emptySet()

    val set = HashSet<Node>()

    this.forEach { set += this }

    return set
}

/**
 * Returns an [Iterator] over the entries in the [NamedNodeMap].
 */
public fun NamedNodeMap.iterator(): Iterator<Node> = this.toSet().iterator()

/**
 * Adds all entries from the [other] collection into this node map.
 */
public fun NamedNodeMap.putAll(other: Collection<Node>) {
    for (node in other) this += node
}