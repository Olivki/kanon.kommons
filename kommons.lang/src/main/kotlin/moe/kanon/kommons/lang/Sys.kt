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

package moe.kanon.kommons.lang

import java.io.InputStream
import java.io.PrintStream
import java.util.*
import kotlin.NoSuchElementException

/**
 * A simple facade for the [System] class.
 */
object Sys {
    /**
     * The "standard" output stream.
     */
    var out: PrintStream
        get() = System.out
        set(value) {
            System.setOut(value)
        }

    /**
     * The "standard" input stream.
     */
    var `in`: InputStream
        get() = System.`in`
        set(value) {
            System.setIn(value)
        }

    /**
     * The "standard" error output stream.
     */
    var error: PrintStream
        get() = System.err
        set(value) {
            System.setErr(value)
        }

    var securityManager: SecurityManager
        get() = System.getSecurityManager()
        set(value) {
            System.setSecurityManager(value)
        }

    var properties: Properties
        get() = System.getProperties()
        set(value) {
            System.setProperties(value)
        }

    /**
     * Returns an unmodifiable string map view of the current system environment variables.
     */
    // this is 'lazy' as to ensure that we won't trigger any unwanted security exceptions just from this object being created
    val env: Map<String, String> by lazy { System.getenv() }

    val timeMillis: Long get() = System.currentTimeMillis()

    val nanoTime: Long get() = System.nanoTime()

    /**
     * Returns the current [nanoTime] in milli-second format.
     */
    val nanoTimeMillis: Long get() = nanoTime / 1000000

    val lineSeparator: String = System.lineSeparator()

    // -- PROPERTIES -- \\
    @JvmName("getProp")
    operator fun get(key: String): String =
        getOrNull(key) ?: throw NoSuchElementException("No system property found under the given key <$key>")

    @JvmName("getPropOrElse")
    fun getOrElse(key: String, default: String): String = System.getProperty(key, default)

    @JvmName("getPropOrNull")
    fun getOrNull(key: String): String? = System.getProperty(key)

    @JvmName("setProp")
    operator fun set(key: String, value: String): String = System.setProperty(key, value)

    // -- ENVIRONMENT VARIABLES -- \\
    /**
     * Returns the value of the environment variable stored under the given [name], or throws a [NoSuchElementException]
     * if none is found.
     *
     * An environment variable is a system-dependent external named value.
     */
    fun getEnv(name: String): String =
        getEnvOrNull(name) ?: throw NoSuchElementException("No environment variable found under given name <$name>")

    /**
     * Returns the value of the environment variable stored under the given [name], or [default] if none is found.
     *
     * An environment variable is a system-dependent external named value.
     */
    fun getEnvOrElse(name: String, default: String): String = getEnvOrNull(name) ?: default

    /**
     * Returns the value of the environment variable stored under the given [name], or `null` if none is found.
     *
     * An environment variable is a system-dependent external named value.
     *
     * @see System.getenv
     */
    fun getEnvOrNull(name: String): String? = System.getenv(name)
}