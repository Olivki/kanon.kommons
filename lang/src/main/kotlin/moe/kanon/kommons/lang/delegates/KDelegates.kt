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

package moe.kanon.kommons.lang.delegates

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

/**
 * A "container" for the different `delegate` properties offered by `kommons.lang`.
 *
 * This is made to keep in line with the [Delegates] object offered by the Kotlin standard library.
 */
object KDelegates {
    /**
     * Returns a property delegate for a read/write property that can only be set *once*, any subsequent set attempts
     * will result in a [UnsupportedOperationException] being thrown.
     *
     * If an attempt is made to retrieve the value of a unset `write-once` property, then a
     * [UnsupportedOperationException] will also be thrown.
     */
    fun <T> writeOnce(): ReadWriteProperty<Any?, T> = WriteOnceProperty()

    /**
     * Returns a property delegate for a read-only property that delegates all `get` operations towards the given
     * [target].
     */
    fun <T> alias(target: KProperty0<T>): ReadOnlyProperty<Any?, T> = object : ReadOnlyProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T = target.get()
    }

    /**
     * Returns a property delegate for a read-only property that delegates all `get` & `set` operations towards the
     * given [target].
     */
    fun <T> alias(target: KMutableProperty0<T>): ReadWriteProperty<Any?, T> = object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T = target.get()

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            target.set(value)
        }
    }
}