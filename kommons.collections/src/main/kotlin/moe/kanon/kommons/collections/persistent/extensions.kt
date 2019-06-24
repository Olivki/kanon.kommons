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

@file:JvmName("PersistentCollections")

package moe.kanon.kommons.collections.persistent

// TODO: Documentation?

// -- PLUS -- \\
// persistent-collection
operator fun <T> T.plus(collection: PersistentCollection<T>): PersistentCollection<T> = collection.prepend(this)

operator fun <T> PersistentCollection<T>.plus(element: T): PersistentCollection<T> = append(element)
operator fun <T> PersistentCollection<T>.plus(elements: Iterable<T>): PersistentCollection<T> = append(elements)
operator fun <T> PersistentCollection<T>.plus(elements: Array<out T>): PersistentCollection<T> = append(elements)
operator fun <T> PersistentCollection<T>.plus(elements: Sequence<T>): PersistentCollection<T> = append(elements)

// persistent-list
operator fun <T> T.plus(list: PersistentList<T>): PersistentList<T> = list.prepend(this)

operator fun <T> PersistentList<T>.plus(element: T): PersistentList<T> = append(element)
operator fun <T> PersistentList<T>.plus(elements: Iterable<T>): PersistentList<T> = append(elements)
operator fun <T> PersistentList<T>.plus(elements: Array<out T>): PersistentList<T> = append(elements)
operator fun <T> PersistentList<T>.plus(elements: Sequence<T>): PersistentList<T> = append(elements)

// persistent-indexed-list
operator fun <T> T.plus(list: PersistentIndexedList<T>): PersistentIndexedList<T> = list.prepend(this)

operator fun <T> PersistentIndexedList<T>.plus(element: T): PersistentIndexedList<T> = append(element)
operator fun <T> PersistentIndexedList<T>.plus(elements: Iterable<T>): PersistentIndexedList<T> = append(elements)
operator fun <T> PersistentIndexedList<T>.plus(elements: Array<out T>): PersistentIndexedList<T> = append(elements)
operator fun <T> PersistentIndexedList<T>.plus(elements: Sequence<T>): PersistentIndexedList<T> = append(elements)

// -- MINUS -- \\
// persistent-collection
operator fun <T> PersistentCollection<T>.minus(element: T): PersistentCollection<T> = remove(element)
operator fun <T> PersistentCollection<T>.minus(elements: Iterable<T>): PersistentCollection<T> = removeAll(elements)
operator fun <T> PersistentCollection<T>.minus(elements: Array<out T>): PersistentCollection<T> = removeAll(elements)
operator fun <T> PersistentCollection<T>.minus(elements: Sequence<T>): PersistentCollection<T> = removeAll(elements)

// persistent-list
operator fun <T> PersistentList<T>.minus(element: T): PersistentList<T> = remove(element)
operator fun <T> PersistentList<T>.minus(elements: Iterable<T>): PersistentList<T> = removeAll(elements)
operator fun <T> PersistentList<T>.minus(elements: Array<out T>): PersistentList<T> = removeAll(elements)
operator fun <T> PersistentList<T>.minus(elements: Sequence<T>): PersistentList<T> = removeAll(elements)

// persistent-indexed-list
operator fun <T> PersistentIndexedList<T>.minus(element: T): PersistentIndexedList<T> = remove(element)
operator fun <T> PersistentIndexedList<T>.minus(elements: Iterable<T>): PersistentIndexedList<T> = removeAll(elements)
operator fun <T> PersistentIndexedList<T>.minus(elements: Array<out T>): PersistentIndexedList<T> = removeAll(elements)
operator fun <T> PersistentIndexedList<T>.minus(elements: Sequence<T>): PersistentIndexedList<T> = removeAll(elements)