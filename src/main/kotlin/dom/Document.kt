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

import org.w3c.dom.DOMException
import org.w3c.dom.Document


/**
 * Creates an element of the type specified.
 *
 * Note that the instance returned implements the [Element][org.w3c.dom.Element] interface, so attributes
 * can be specified directly on the returned object.
 *
 * In addition, if there are known attributes with default values, [Attr][org.w3c.dom.Attr] nodes representing them are
 * automatically created and attached to the element.
 *
 * To create an element with a qualified name and namespace URI, use the [createElementNS][Document.createElementNS]
 * method.
 *
 * @param tagName The name of the element type to instantiate. For XML, this is case-sensitive, otherwise it depends on
 * the case-sensitivity of the markup language in use. In that case, the name is mapped to the canonical form of that
 * markup by the DOM implementation.
 *
 * @return A new [Element][org.w3c.dom.Element] object with the [nodeName][org.w3c.dom.Element.getNodeName] attribute
 * set to [tagName], and [localName][org.w3c.dom.Element.getLocalName], [prefix][org.w3c.dom.Element.getPrefix], and
 *   [namespaceURI][org.w3c.dom.Element.getNamespaceURI] set to `null`.
 *
 * @exception DOMException
 * `INVALID_CHARACTER_ERR`: Raised if the specified name is not an XML name according to the XML version in use
 * specified in the [Document.xmlVersion][Document.getXmlVersion] attribute.
 */
public operator fun Document.plusAssign(tagName: String) {
    this.createElement(tagName)!!
}