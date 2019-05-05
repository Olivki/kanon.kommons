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

@file:JvmName("KSystem")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.system

import moe.kanon.kommons.LINE_SEPARATOR
import moe.kanon.kommons.func.Optional
import moe.kanon.kommons.func.Try

inline val <T> T.print get() = print(this)

inline val <T> T.println get() = System.out.println(this)

fun getProperty(key: String): Optional<String> =
    Optional { System.getProperty(key) }

fun getProperty(key: String, def: String): Optional<String> =
    Optional { System.getProperty(key, def) }

inline fun getPropertyOrThrow(key: String, lazyMsg: () -> String): String =
    getProperty(key).getOrThrow { NoSuchElementException(lazyMsg()) }

inline fun getPropertyOrThrow(key: String): String = getPropertyOrThrow(key) { "No property found under key <$key>" }

inline fun getPropertyOrThrow(key: String, def: String, lazyMsg: () -> String): String =
    getProperty(key, def).getOrThrow { NoSuchElementException(lazyMsg()) }

inline fun getPropertyOrThrow(key: String, def: String): String =
    getPropertyOrThrow(key, def) { "No property found under key <$key> and def <$def>" }

fun getEnv(name: String): Optional<String> =
    Optional { System.getenv(name) }

inline fun getEnvOrThrow(name: String, lazyMsg: () -> String): String =
    getEnv(name).getOrThrow { NoSuchElementException(lazyMsg()) }

inline fun getEnvOrThrow(name: String): String = getEnvOrThrow(name) { "No env-variable found under name <$name>" }

@get:JvmName("getEnvs")
inline val ENVIRONMENT_VARIABLES: Map<String, String>
    get() = System.getenv()

// runtime stuff
inline fun <T> runProcess(vararg commands: String, closure: Process.() -> T): T {
    val builder = ProcessBuilder(*commands)
    builder.redirectErrorStream(true)
    return with(builder.start(), closure)
}

fun exec(vararg commands: String): Try<String> = runProcess(*commands) {
    Try {
        buildString {
            this@runProcess.inputStream.bufferedReader().use {
                appendln(it.lineSequence().filterNotNull().joinToString(LINE_SEPARATOR))
                this@runProcess.waitFor()
            }
        }.trim()
    }
}