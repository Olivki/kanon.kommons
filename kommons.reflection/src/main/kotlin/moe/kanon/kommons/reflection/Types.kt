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

@file:JvmName("KTypes")

package moe.kanon.kommons.reflection

import kotlin.reflect.KType
import kotlin.reflect.typeOf

/*
 * With Kotlin 1.3.40 the "typeOf" function has been implemented into the Kotlin std-lib for the JVM, which means that
 * we do not need to port the entire "TypeToken" system from Guava to Kotlin, and instead we can just provide utility
 * extension functions that operate on KType instances, making for a nicer experience for the user, avoiding having
 * to wrap the types in more layers.
 */

// TODO: Extension functions for the KType class.