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

@file:JvmName("KDocuments")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.dom

import org.w3c.dom.CDATASection
import org.w3c.dom.Comment
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.EntityReference
import org.w3c.dom.ProcessingInstruction
import org.w3c.dom.Text

/**
 * Creates an [Element] from the specified [tagName] and then appends it to this document.
 *
 * @since 0.4.0
 */
inline fun Document.appendElement(tagName: String): Element =
    this.appendChild(this.createElement(tagName))!! as Element

/**
 * Creates an [Element] from the given [namespaceURI] and [qualifiedName] and then appends it to this document.
 *
 * @since 0.4.0
 */
inline fun Document.appendElementNS(namespaceURI: String?, qualifiedName: String): Element =
    this.appendChild(this.createElementNS(namespaceURI, qualifiedName))!! as Element

/**
 * Creates a [Comment] of the specified [data] and then appends it to this document.
 *
 * @since 0.4.0
 */
inline fun Document.appendComment(data: String): Comment =
    this.appendChild(this.createComment(data))!! as Comment

/**
 * Creates a [TextNode][Text] of the specified [data] and then appends it to this document.
 *
 * @since 0.4.0
 */
inline fun Document.appendTextNode(data: String): Text =
    this.appendChild(this.createTextNode(data))!! as Text

/**
 * Creates a [Element] with the specified [name] that only contains a [TextNode][Text] containing the specified [data],
 * then appends it to this document.
 *
 * @since 0.4.1
 */
inline fun Document.appendTextContainer(name: String, data: String): Element {
    val parent = this.createElement(name)

    parent.appendTextNode(data)

    return this.appendChild(parent)!! as Element
}

/**
 * Creates a [CDATASection] of the specified [data] and then appends it to this document.
 *
 * @since 0.4.0
 */
inline fun Document.appendCDataSection(data: String): CDATASection =
    this.appendChild(this.createCDATASection(data))!! as CDATASection

/**
 * Creates a [ProcessingInstruction] from the specified [target] and [data] and then appends it to this document.
 *
 * @since 0.4.0
 */
inline fun Document.appendProcessingInstruction(target: String, data: String): ProcessingInstruction =
    this.appendChild(this.createProcessingInstruction(target, data))!! as ProcessingInstruction

/**
 * Creates a [EntityReference] from the specified [name] and then appends it to this document.
 *
 * @since 0.4.0
 */
inline fun Document.appendEntityReference(name: String): EntityReference =
    this.appendChild(this.createEntityReference(name))!! as EntityReference