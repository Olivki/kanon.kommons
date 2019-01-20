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

@file:JvmName("ElementUtils")

package moe.kanon.kextensions.dom

import org.w3c.dom.*

/**
 * A facade/wrapper for [NamedNodeMap] to enable nicer mutable operations.
 *
 * This class was made due to the fact that `NamedNodeMap` knows nothing of it's creator, which means that there's no
 * nice and easy way of creating attributes that are properly related to the parent [Node].
 *
 * Another problem that arises with `NamedNodeMap` is the fact that it's **not** specific to *just* attributes, as the
 * name implies, it's used by *any* node that has a name. This means that even if `NamedNodeMap` knew of it's creator,
 * adding extension methods to it that assumes that it's only adding attributes would end up with confusing syntax and
 * possible misunderstandings. Trying to solve those issues within just the `NamedNodeMap` would just leave us with
 * clunky syntax, bad looking code, and a lot of potential user errors due to misunderstandings.
 *
 * As this is just a facade/wrapper, none of the
 */
public class AttributeMap @PublishedApi internal constructor(private val parent: Element) : MutableMap<String, String> {

    private val document: Document = parent.ownerDocument

    private val nodes: NamedNodeMap = parent.attributes!!

    override val size: Int get() = nodes.length

    override val entries: MutableSet<MutableMap.MutableEntry<String, String>>
        get() = nodes.toSet().map { MutableEntry(it.nodeName, it.nodeValue) }.toMutableSet()

    override val keys: MutableSet<String> get() = nodes.toSet().map { it.nodeName }.toMutableSet()

    override val values: MutableCollection<String> get() = nodes.toSet().map { it.nodeValue }.toMutableList()

    override fun containsKey(key: String): Boolean = key in keys

    override fun containsValue(value: String): Boolean = value in values

    override fun get(key: String): String? = nodes[key]?.nodeValue

    override fun isEmpty(): Boolean = nodes.isEmpty()

    override fun clear() {
        parent
    }

    override fun put(key: String, value: String): String? {
        val oldValue = this[key]

        val attr = document.createAttribute(key)

        attr.value = value

        nodes += attr

        return oldValue
    }

    override fun putAll(from: Map<out String, String>) {
        for ((name, value) in from) {
            val node = document.createAttribute(name)
            node.value = value

            nodes += node
        }
    }

    override fun remove(key: String): String? {
        val oldValue = nodes[key]!!.nodeValue

        nodes -= key

        return oldValue
    }

    inner class MutableEntry(override val key: String, override val value: String) :
        MutableMap.MutableEntry<String, String> {

        override fun setValue(newValue: String): String {
            val oldValue: String = value

            nodes += document.createAttribute(newValue)

            return oldValue
        }
    }
}

public inline val Element.attributeMap: AttributeMap get() = AttributeMap(this)


// Standard Attribute
// - Name Attribute
// - - ADD
/**
 * Adds a new attribute.
 *
 * If an attribute with that name is already present in the element, its value is changed to be that of the value
 * parameter. This value is a simple string; it is not parsed as it is being set. So any markup (such as syntax to be
 * recognized as an entity reference) is treated as literal text, and needs to be appropriately escaped by the
 * implementation when it is written out.
 *
 * In order to assign an attribute value that contains entity references, the user must create an [Attr][org.w3c.dom.Attr]
 * node plus any [Text][org.w3c.dom.Text] and `[EntityReference][org.w3c.dom.EntityReference] nodes, build the
 * appropriate subtree, and use [setAttributeNode][Element.setAttributeNode] to assign it as the value of an attribute.
 *
 * **To set an attribute with a qualified name and namespace URI, use  the [Element.setAttributeNS] method.**
 *
 * @param name The name of the attribute to create or alter.
 * @param value Value to set in string form.
 * @exception DOMException
 *
 * `INVALID_CHARACTER_ERR`: Raised if the specified name is not an XML name according to the
 * XML version in use specified in the `Document.xmlVersion` attribute.
 *
 * `NO_MODIFICATION_ALLOWED_ERR`: Raised if this node is `readonly`.
 */
public operator fun Element.set(name: String, value: String) {
    this.setAttribute(name, value)
}

// - - REMOVE
/**
 * Removes an attribute by name.
 *
 * If a default value for the removed attribute is defined in the DTD, a new attribute immediately appears with the
 * default value as well as the corresponding namespace URI, local name, and prefix when applicable.
 *
 * The implementation may handle default values from other schemas similarly but applications should use
 * [Document.normalizeDocument][org.w3c.dom.Document.normalizeDocument] to guarantee this information is up-to-date.
 *
 * If no attribute with this name is found, this method has no effect.
 *
 * To remove an attribute by local name and namespace URI, use the [removeAttributeNS][Element.removeAttributeNS]
 * method.
 *
 * @param name The name of the attribute to remove.
 *
 * @exception DOMException
 *
 * `NO_MODIFICATION_ALLOWED_ERR`: Raised if this node is `readonly`.
 */
public operator fun Element.minusAssign(name: String) {
    this.removeAttribute(name)
}

// - - GET
/**
 * Retrieves an attribute value by name.
 *
 * @param name The name of the attribute to retrieve.
 *
 * @return The [Attr][org.w3c.dom.Attr] value as a string, or the empty string if that attribute does not have a
 * specified or default value.
 */
public operator fun Element.get(name: String): String = this.getAttribute(name)!!

// - - CONTAINS
/**
 * Returns `true` when an attribute with a given name is specified on this element or has a default value, `false`
 * otherwise.
 *
 * @param name The name of the attribute to look for.
 *
 * @return `true` if an attribute with the given name is specified on this element or has a default value, `false`
 * otherwise.
 *
 * @since DOM Level 2
 */
public operator fun Element.contains(name: String): Boolean = this.hasAttribute(name)

// - NS Attribute
// - - ADD
/**
 * Adds a new attribute.
 *
 * If an attribute with the same local name and namespace URI is already present on the element, its prefix is changed
 * to be the prefix part of the [qualifiedName], and  its value is changed to be the [value] parameter.
 *
 * This value is a simple string; it is not parsed as it is being set. So any markup *(such as syntax to be recognized
 * as an entity reference)* is treated as literal text, and needs to be appropriately escaped by the implementation when
 * it is written out. In order to assign an attribute value that contains entity references, the user must create
 * an [Attr][org.w3c.dom.Attr] node plus any [Text][org.w3c.dom.Text] and [EntityReference][org.w3c.dom.EntityReference]
 * nodes, build the appropriate subtree, and use [setAttributeNodeNS][Element.setAttributeNodeNS] or
 * [setAttributeNode][Element.setAttributeNode] to assign it as the value of an attribute.
 *
 * Per [XML Namespaces](http://www.w3.org/TR/1999/REC-xml-names-19990114/), applications must use the value `null` as
 * the [namespaceURI] parameter for methods if they wish to have no namespace.
 *
 * @param namespaceURI The namespace URI of the attribute to create or alter.
 * @param qualifiedName The qualified name of the attribute to create or alter.
 * @param value The value to set in string form.
 *
 * @exception DOMException
 *
 * `INVALID_CHARACTER_ERR`: Raised if the specified qualified name is not an XML name according to the XML version in
 * use specified in the `Document.xmlVersion` attribute.
 *
 * `NO_MODIFICATION_ALLOWED_ERR`: Raised if this node is `readonly`.
 *
 * `NAMESPACE_ERR`: Raised if the [qualifiedName] is malformed per the Namespaces in XML specification, if the
 * [qualifiedName] has a prefix and the [namespaceURI] is `null`, if the [qualifiedName] has a prefix that is `"xml"`
 * and the [namespaceURI] is different from
 * "[http://www.w3.org/XML/1998/namespace](http://www.w3.org/XML/1998/namespace)", if the [qualifiedName] or its prefix
 * is `"xmlns"` and the [namespaceURI] is different from
 * "[http://www.w3.org/2000/xmlns/](http://www.w3.org/2000/xmlns/)", or if the [namespaceURI] is
 * "[http://www.w3.org/2000/xmlns/](http://www.w3.org/2000/xmlns/)" and neither the [qualifiedName] nor its prefix is
 * `"xmlns"`.
 *
 * `NOT_SUPPORTED_ERR`: May be raised if the implementation does not support the feature `"XML"` and the language
 * exposed through the Document does not support XML Namespaces *(such as
 * [HTML 4.01](http://www.w3.org/TR/1999/REC-html401-19991224/))*.
 *
 * @since DOM Level 2
 */
public operator fun Element.set(namespaceURI: String, qualifiedName: String?, value: String) {
    this.setAttributeNS(namespaceURI, qualifiedName, value)
}

// - - REMOVE
/**
 * Removes an attribute by [namespace URI][Pair.first] and [local name][Pair.second].
 *
 * If a default value for the removed attribute is defined in the DTD, a new attribute immediately appears with the
 * default value as well as the corresponding namespace URI, local name, and prefix when applicable.
 *
 * The implementation may handle default values from other schemas similarly but applications should use
 * [Document.normalizeDocument][org.w3c.dom.Document.normalizeDocument] to guarantee this information is up-to-date.
 *
 *
 * If no attribute with this local name and namespace URI is found, this method has no effect.
 *
 * Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
 * , applications must use the value <code>null</code> as the
 * <code>namespaceURI</code> parameter for methods if they wish to have
 * no namespace.
 *
 * @param nsAttr The pair with `namespaceURI` as the [first][Pair.first] value, and `localName` as the
 * [second][Pair.second] value.
 *
 * @exception DOMException
 *
 * `NO_MODIFICATION_ALLOWED_ERR`: Raised if this node is `readonly`.
 *
 * `NOT_SUPPORTED_ERR`: May be raised if the implementation does not support the feature `"XML"` and the language exposed
 * through the Document does not support XML Namespaces *(such as
 * [HTML 4.01](http://www.w3.org/TR/1999/REC-html401-19991224/))*
 *
 * @since DOM Level 2
 */
public operator fun Element.minusAssign(nsAttr: Pair<String?, String>) {
    this.removeAttributeNS(nsAttr.first, nsAttr.second)
}

// - - GET
/**
 * Retrieves an attribute value by local name and namespace URI.
 *
 * Per (XML Namespaces)[http://www.w3.org/TR/1999/REC-xml-names-19990114/] , applications must use the value `null` as
 * the [namespaceURI] parameter for methods if they wish to have no namespace.
 *
 * @param namespaceURI The namespace URI of the attribute to retrieve.
 * @param localName The local name of the attribute to retrieve.
 *
 * @return The [Attr][org.w3c.dom.Attr] value as a string, or the empty string if that attribute does not have a
 * specified or default value.
 *
 * @exception DOMException
 *
 * `NOT_SUPPORTED_ERR`: May be raised if the implementation does not support the feature `"XML"` and the language exposed
 * through the Document does not support XML Namespaces *(such as
 * [HTML 4.01](http://www.w3.org/TR/1999/REC-html401-19991224/))*
 *
 * @since DOM Level 2
 */
public operator fun Element.get(namespaceURI: String?, localName: String): String =
    this.getAttributeNS(namespaceURI, localName)!!

// - - CONTAINS
/**
 * Returns `true` when an attribute with a given local name and namespace URI is specified on this element or has a
 * default value, `false` otherwise.
 *
 * Per (XML Namespaces)[http://www.w3.org/TR/1999/REC-xml-names-19990114/] , applications must use the value `null` as
 * the [namespaceURI][Pair.first] parameter for methods if they wish to have no namespace.
 *
 * @param attrPair The pair with `namespaceURI` as the [first][Pair.first] value, and `localName` as the
 * [second][Pair.second] value.
 *
 * @return `true` if an attribute with the given local name and namespace URI is specified or has a default value on
 * this element, `false` otherwise.
 *
 * @exception DOMException
 *
 * `NOT_SUPPORTED_ERR`: May be raised if the implementation does not support the feature `"XML"` and the language
 * exposed through the Document does not support XML Namespaces *(such as
 * [HTML 4.01](http://www.w3.org/TR/1999/REC-html401-19991224/))*.
 *
 * @since DOM Level 2
 */
public operator fun Element.contains(attrPair: Pair<String?, String>): Boolean =
    this.hasAttributeNS(attrPair.first, attrPair.second)

// ID Attribute
// - Name Attribute
// - - ADD
/**
 * If the parameter [isId] is `true`, this method declares the specified attribute to be a user-determined ID attribute.
 *
 * This affects the value of [org.w3c.dom.Attr.isId] and the behavior of [org.w3c.dom.Document.getElementById], but
 * does not change any schema that may be in use, in particular this does not affect the `Attr.schemaTypeInfo` of the
 * specified [org.w3c.dom.Attr] node.
 *
 * Use the value `false` for the parameter [isId] to undeclare an attribute for being a user-determined ID attribute.
 *
 * To specify an attribute by local name and namespace URI, use the [Element.setIdAttributeNS] method.
 *
 * @param name The name of the attribute.
 * @param isId Whether the attribute is a of type ID.
 * @exception DOMException
 *
 *`NO_MODIFICATION_ALLOWED_ERR`: Raised if this node is `readonly`.
 *
 *`NOT_FOUND_ERR`: Raised if the specified node is not an attribute of this element.
 *
 * @since DOM Level 3
 */
public operator fun Element.set(name: String, isId: Boolean) {
    this.setIdAttribute(name, isId)
}

// - NS Attribute
// - - ADD
/**
 * If the parameter [isId] is `true`, this method declares the specified attribute to be a user-determined ID attribute.
 *
 * This affects the value of [Attr.isId][org.w3c.dom.Attr.isId] and the behavior of
 * [Document.getElementById][org.w3c.dom.Document.getElementById], but does not change any schema that may be in use,
 * in particular this does not affect the `schemaTypeInfo` of the specified [Attr][org.w3c.dom.Attr] node.
 *
 * Use the value `false` for the parameter [isId] to undeclare an attribute for being a user-determined ID attribute.
 *
 * @param namespaceURI The namespace URI of the attribute.
 * @param localName The local name of the attribute.
 * @param isId Whether the attribute is a of type ID.
 *
 * @exception DOMException
 * `NO_MODIFICATION_ALLOWED_ERR`: Raised if this node is `readonly`.
 *
 * `NOT_FOUND_ERR`: Raised if the specified node is not an attribute of this element.
 *
 * @since DOM Level 3
 */
public operator fun Element.set(namespaceURI: String, localName: String, isId: Boolean) {
    this.setIdAttributeNS(namespaceURI, localName, isId)
}