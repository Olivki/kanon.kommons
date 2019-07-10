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

import moe.kanon.kommons.OS_NAME
import moe.kanon.kommons.StringMap
import moe.kanon.kommons.USER_HOME
import moe.kanon.kommons.USER_NAME
import moe.kanon.kommons.static
import java.io.InputStream
import java.io.PrintStream
import java.nio.file.Path
import java.nio.file.Paths
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
        @JvmName("out") get() = System.out
        set(value) {
            System.setOut(value)
        }

    /**
     * The "standard" input stream.
     */
    var `in`: InputStream
        @JvmName("in") get() = System.`in`
        set(value) {
            System.setIn(value)
        }

    /**
     * The "standard" error output stream.
     */
    var error: PrintStream
        @JvmName("error") get() = System.err
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
    val env: StringMap<String> by lazy { System.getenv() }

    val timeMillis: Long get() = System.currentTimeMillis()

    val nanoTime: Long get() = System.nanoTime()

    /**
     * Returns the current [nanoTime] in milli-second format.
     */
    val nanoTimeMillis: Long get() = nanoTime / 1000000

    val lineSeparator: String = System.lineSeparator()

    val os: OperatingSystem = OperatingSystem

    val user: User = User

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

    object OperatingSystem {
        /**
         * Returns the name of the current operating system, as specified by the `"os.name"` system property.
         */
        val name: String by lazy { Sys["os.name"] }

        /**
         * Returns the version of the current operating system, as specified by the `"os.version"` system property.
         */
        val version: String by lazy { Sys["os.version"] }
    }

    object User {
        /**
         * Returns the name of the current user, as specified by the `"user.name"` system property.
         */
        val name: String by lazy { Sys["user.name"] }

        /**
         * Returns the language of the current user, as specified by the `"user.language"` system property.
         */
        val language: String by lazy { Sys["user.language"] }

        /**
         * Returns the country of the current user, as specified by the `"user.country"` system property.
         */
        val country: String by lazy { Sys["user.country"] }

        val dirs: Dirs = Dirs

        object Dirs {
            val data: Path by lazy {
                when (os) {
                    OperatingSystem.WINDOWS -> Paths.get()
                    OperatingSystem.MAC_OS_X -> Paths.get(USER_HOME, "Library", "Application Support", name)
                    OperatingSystem.UNIX -> Paths.get("")
                }
            }

            val config: Path by lazy {
                when (os) {
                    OperatingSystem.WINDOWS -> Paths.get("")
                    OperatingSystem.MAC_OS_X -> Paths.get("")
                    OperatingSystem.UNIX -> Paths.get("")
                }
            }

            val cache: Path by lazy {
                when (os) {
                    OperatingSystem.WINDOWS -> Paths.get("")
                    OperatingSystem.MAC_OS_X -> Paths.get("")
                    OperatingSystem.UNIX -> Paths.get("")
                }
            }

            val logs: Path by lazy {
                when (os) {
                    OperatingSystem.WINDOWS -> Paths.get("")
                    OperatingSystem.MAC_OS_X -> Paths.get("")
                    OperatingSystem.UNIX -> Paths.get("")
                }
            }

            private val os by lazy {
                when {
                    Sys.os.name.startsWith("windows", true) -> OperatingSystem.WINDOWS
                    Sys.os.name.startsWith("mac os x", true) -> OperatingSystem.MAC_OS_X
                    // assume it's some *nix based system
                    else -> OperatingSystem.UNIX
                }
            }

            private enum class OperatingSystem { WINDOWS, MAC_OS_X, UNIX }
        }
    }
}