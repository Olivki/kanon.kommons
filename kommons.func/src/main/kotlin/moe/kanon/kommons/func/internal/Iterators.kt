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

package moe.kanon.kommons.func.internal

import moe.kanon.kommons.UNSUPPORTED

/*
 *  Internal implementations of some of the iterator classes that can be found in the kommons.collections library.
 *      These are here to avoid having this library depend on the collections library just for these classes, if you
 *   want to use these classes in a project, use the kommons.collections library.
 */

internal object EmptyIterator : Iterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun next(): Nothing = UNSUPPORTED("Can not iterate over a empty iterator")
}

internal class SingletonIterator<E>(private val item: E) : Iterator<E> {
    private var hasNext: Boolean = true

    override fun hasNext(): Boolean = hasNext

    override fun next(): E = if (hasNext) item.also { hasNext = false } else throw NoSuchElementException()
}