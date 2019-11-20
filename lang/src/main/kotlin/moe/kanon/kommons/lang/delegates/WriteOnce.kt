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

@file:Suppress("UNCHECKED_CAST")

package moe.kanon.kommons.lang.delegates

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

/**
 * A delegated property that can only be set once.
 *
 * This delegate is similar to that of [Delegates.notNull] and the `lateinit var` keyword but the key difference is that
 * this delegate only allows the string to be set *once* and any subsequent attempts to set it afterwards will result in
 * a [UnsupportedOperationException] being throw. This essentially makes this delegate into something resembling a
 * `lateinit val` construct.
 */
class WriteOnceProperty<T> : ReadWriteProperty<Any?, T> {
    private object NOT_SET

    private var value: Any? = null

    /**
     * Returns whether or not the string of the property has been set.
     */
    val isSet: Boolean get() = value != NOT_SET

    /**
     * Returns whether or not the string of the property has *not* been set.
     */
    val isNotSet: Boolean get() = !isSet

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        if (isNotSet) throw UnsupportedOperationException("Property <${property.name}> has not been set") else value as T

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (isSet) throw UnsupportedOperationException("Property <${property.name}> has already been set")
        this.value = value
    }
}

/**
 * Returns whether or not `this` [write-once][WriteOnceProperty] property has been set.
 *
 * @throws [IllegalAccessException]
 * - If `this` property is *not* a delegated property.
 * - If `this` property is *not* a `write-once` property.
 */
@Deprecated(
    "Ambiguous name in relation to what it does",
    ReplaceWith("isWriteOnceSet", "moe.kanon.kommons.lang.delegates"),
    DeprecationLevel.ERROR
)
val KProperty0<*>.isSet: Boolean
    get() {
        this.isAccessible = true
        return when (val delegate = this.getDelegate()) {
            null -> throw IllegalAccessException("Property <${this.name}> is not a delegated property")
            is WriteOnceProperty<*> -> delegate.isSet
            else -> throw IllegalAccessException("Property <${this.name}> is not a write-once property")
        }
    }

/**
 * Returns whether or not `this` [write-once][WriteOnceProperty] property has been set.
 *
 * @throws [IllegalAccessException]
 * - If `this` property is *not* a delegated property.
 * - If `this` property is *not* a `write-once` property.
 */
@Deprecated(
    "Ambiguous name in relation to what it does",
    ReplaceWith("isWriteOnceNotSet", "moe.kanon.kommons.lang.delegates"),
    DeprecationLevel.ERROR
)
val KProperty0<*>.isNotSet: Boolean
    get() {
        this.isAccessible = true
        return when (val delegate = this.getDelegate()) {
            null -> throw IllegalAccessException("Property <${this.name}> is not a delegated property")
            is WriteOnceProperty<*> -> delegate.isNotSet
            else -> throw IllegalAccessException("Property <${this.name}> is not a write-once property")
        }
    }

/**
 * Returns whether or not `this` [write-once][WriteOnceProperty] property has been set.
 *
 * @throws [IllegalAccessException]
 * - If `this` property is *not* a delegated property.
 * - If `this` property is *not* a `write-once` property.
 */
val KProperty0<*>.isWriteOnceSet: Boolean
    get() {
        this.isAccessible = true
        return when (val delegate = this.getDelegate()) {
            null -> throw IllegalAccessException("Property <${this.name}> is not a delegated property")
            is WriteOnceProperty<*> -> delegate.isSet
            else -> throw IllegalAccessException("Property <${this.name}> is not a write-once property")
        }
    }

/**
 * Returns whether or not `this` [write-once][WriteOnceProperty] property has been set.
 *
 * @throws [IllegalAccessException]
 * - If `this` property is *not* a delegated property.
 * - If `this` property is *not* a `write-once` property.
 */
val KProperty0<*>.isWriteOnceNotSet: Boolean
    get() {
        this.isAccessible = true
        return when (val delegate = this.getDelegate()) {
            null -> throw IllegalAccessException("Property <${this.name}> is not a delegated property")
            is WriteOnceProperty<*> -> delegate.isNotSet
            else -> throw IllegalAccessException("Property <${this.name}> is not a write-once property")
        }
    }