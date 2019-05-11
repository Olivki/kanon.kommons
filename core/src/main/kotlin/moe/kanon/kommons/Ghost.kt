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

package moe.kanon.kommons

/**
 * Represents a class that does not "exist".
 *
 * Any class that inherits from this will not be identifiable by most systems. Therefore only classes that are used for
 * very specific circumstances *(i.e; `object`s used for DSL syntax)* should be the only ones to ever inherit from it.
 */
abstract class Ghost {
    final override fun equals(other: Any?): Boolean = false

    final override fun hashCode(): Int = 0

    final override fun toString(): String = ""
}