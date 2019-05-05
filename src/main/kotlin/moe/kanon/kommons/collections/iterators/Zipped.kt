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

@file:JvmMultifileClass
@file:JvmName("KIterators")

package moe.kanon.kommons.collections.iterators

import moe.kanon.kommons.func.tuples.Tuple2
import moe.kanon.kommons.func.tuples.toT

class ZippedIterator<F, S>(private val first: Iterator<F>, private val second: Iterator<S>) : Iterator<Tuple2<F, S>> {
    override fun hasNext(): Boolean = first.hasNext() && second.hasNext()

    override fun next(): Tuple2<F, S> = first.next() toT second.next()
}

infix fun <F, S> Iterator<F>.zipWith(other: Iterator<S>): Iterator<Tuple2<F, S>> = ZippedIterator(this, other)