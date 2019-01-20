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

@file:JvmName("NodeUtils")

package moe.kanon.kextensions.dom

import org.w3c.dom.DOMException
import org.w3c.dom.Node

/**
 * Adds the node [child] to the end of the list of children of this node.
 *
 * If the [child] is already in the tree, it is first removed from it's current position and re-added to be at the
 * bottom of the list of children.
 *
 * @param child The node to add. If it is a [DocumentFragment][org.w3c.dom.DocumentFragment] object, the entire
 * contents of the  document fragment are moved into the child list of this node
 *
 * @return The newly added node.
 * @exception DOMException
 *
 * `HIERARCHY_REQUEST_ERR`: Raised if this node is of a type that does not allow children of the type of the [child]
 * node, or if the node to append is one of this node's ancestors or this node itself, or if this node is of type
 * [Document][org.w3c.dom.Document] and the DOM application attempts to append a second
 * [DocumentType][org.w3c.dom.DocumentType] or [Element][org.w3c.dom.Element] node.
 *
 * `WRONG_DOCUMENT_ERR`: Raised if [child] was created from a different document than the one that created this node.
 *
 * `NO_MODIFICATION_ALLOWED_ERR`: Raised if this node is `readonly` or if the previous parent of the node being
 * inserted is `readonly`.
 *
 * `NOT_SUPPORTED_ERR`: if the [child] node is a child of the [Document][org.w3c.dom.Document] node, this exception
 * might be raised if the DOM implementation doesn't support the removal of the
 * [DocumentType][org.w3c.dom.DocumentType] child or [Element][org.w3c.dom.Element] child.
 *
 * @since DOM Level 3
 */
public operator fun Node.plusAssign(child: Node) {
    this.appendChild(child)
}

/**
 * Returns whether this node is the same node as the [other] node.
 *
 * This method provides a way to determine whether two [Node] references returned by the implementation reference
 * the same object. When two [Node] references are references to the same object, even if through a proxy, the
 * references may be used completely interchangeably, such that all attributes have the same values and calling the
 * same DOM method on either reference always has exactly the same effect.
 *
 * @param other The node to test against.
 *
 * @return Returns `true` if the nodes are the same, `false` otherwise.
 *
 * @since DOM Level 3
 */
public infix fun Node.isSame(other: Node): Boolean = this.isSameNode(other)