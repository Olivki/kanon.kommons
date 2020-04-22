/*
 * Copyright 2019-2020 Oliver Berg
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

import moe.kanon.kommons.StringMap
import moe.kanon.kommons.USER_HOME
import java.io.InputStream
import java.io.PrintStream
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Properties

@Deprecated(message = "Questionable class, will be removed in later versions.")
object Sys {
    /**
     * The "standard" output stream.
     */
    var out: PrintStream
        @Deprecated(
            message = "Use 'System.out' instead.",
            replaceWith = ReplaceWith("System.out")
        )
        @JvmName("out")
        get() = System.out
        @Deprecated(
            message = "Use 'System.setOut(value)' instead.",
            replaceWith = ReplaceWith("System.setOut(value)")
        )
        set(value) {
            System.setOut(value)
        }

    /**
     * The "standard" input stream.
     */
    var `in`: InputStream
        @Deprecated(
            message = "Use 'System.`in`' instead.",
            replaceWith = ReplaceWith("System.`in`")
        )
        @JvmName("in")
        get() = System.`in`
        @Deprecated(
            message = "Use 'System.setIn(value)' instead.",
            replaceWith = ReplaceWith("System.setIn(value)")
        )
        set(value) {
            System.setIn(value)
        }

    /**
     * The "standard" error output stream.
     */
    var error: PrintStream
        @Deprecated(
            message = "Use 'System.err' instead.",
            replaceWith = ReplaceWith("System.err")
        )
        @JvmName("error")
        get() = System.err
        @Deprecated(
            message = "Use 'System.setErr(value)' instead.",
            replaceWith = ReplaceWith("System.setErr(value)")
        )
        set(value) {
            System.setErr(value)
        }

    var securityManager: SecurityManager
        @Deprecated(
            message = "Use 'System.getSecurityManager()' instead.",
            replaceWith = ReplaceWith("System.getSecurityManager()")
        )
        get() = System.getSecurityManager()
        @Deprecated(
            message = "Use 'System.setSecurityManager(value)' instead.",
            replaceWith = ReplaceWith("System.setSecurityManager(value)")
        )
        set(value) {
            System.setSecurityManager(value)
        }

    var properties: Properties
        @Deprecated(
            message = "Use 'System.getProperties()' instead.",
            replaceWith = ReplaceWith("System.getProperties()")
        )
        get() = System.getProperties()
        @Deprecated(
            message = "Use 'System.setProperties(value)' instead.",
            replaceWith = ReplaceWith("System.setProperties(value)")
        )
        set(value) {
            System.setProperties(value)
        }

    /**
     * Returns an unmodifiable string map view of the current system environment variables.
     */
    @Deprecated(
        message = "Use 'System.getenv()' instead.",
        replaceWith = ReplaceWith("System.getenv()")
    )
    val env: StringMap<String> by lazy { System.getenv() }

    @Deprecated(
        message = "Use 'System.currentTimeMillis()' instead.",
        replaceWith = ReplaceWith("System.currentTimeMillis()")
    )
    val timeMillis: Long
        get() = System.currentTimeMillis()

    @Deprecated(
        message = "Use 'System.nanoTime()' instead.",
        replaceWith = ReplaceWith("System.nanoTime()")
    )
    val nanoTime: Long
        get() = System.nanoTime()

    /**
     * Returns the current [nanoTime] in milli-second format.
     */
    @Deprecated(
        message = "Use 'System.nanoTime() / 1_000_000' instead.",
        replaceWith = ReplaceWith("(System.nanoTime() / 1_000_000)")
    )
    val nanoTimeMillis: Long
        get() = nanoTime / 1_000_000

    @Deprecated(
        message = "Use 'System.lineSeparator()' instead.",
        replaceWith = ReplaceWith("System.lineSeparator()")
    )
    val lineSeparator: String = System.lineSeparator()

    val os: OperatingSystem = OperatingSystem

    val user: User = User

    // -- PROPERTIES -- \\
    @Deprecated(
        message = "Use 'getProp(key)' instead.",
        replaceWith = ReplaceWith("getProp(key)", "moe.kanon.kommons")
    )
    @JvmName("getProperty")
    operator fun get(key: String): String =
        getOrNull(key) ?: throw NoSuchElementException("No system property found under the given key <$key>")

    @Deprecated(
        message = "Use 'getPropOrDefault(key)' instead.",
        replaceWith = ReplaceWith("getPropOrDefault(key, default)", "moe.kanon.kommons")
    )
    @JvmName("getPropertyOrElse")
    fun getOrElse(key: String, default: String): String = System.getProperty(key, default)

    @Deprecated(
        message = "Use 'getPropOrNull(key)' instead.",
        replaceWith = ReplaceWith("getPropOrNull(key)", "moe.kanon.kommons")
    )
    @JvmName("getPropertyOrNull")
    fun getOrNull(key: String): String? = System.getProperty(key)

    @Deprecated(
        message = "Use 'setProp(key, value)' instead.",
        replaceWith = ReplaceWith("setProp(key, value)", "moe.kanon.kommons")
    )
    @JvmName("setProperty")
    operator fun set(key: String, value: String): String = System.setProperty(key, value)

    // -- ENVIRONMENT VARIABLES -- \\
    /**
     * Returns the value of the environment variable stored under the given [name], or throws a [NoSuchElementException]
     * if none is found.
     *
     * An environment variable is a system-dependent external named value.
     */
    @Deprecated(
        message = "Use 'getEnv(name)' instead.",
        replaceWith = ReplaceWith("getEnv(name)", "moe.kanon.kommons")
    )
    fun getEnv(name: String): String =
        getEnvOrNull(name) ?: throw NoSuchElementException("No environment variable found under given name <$name>")

    @Deprecated(
        message = "Use 'getEnvOrNull(name) ?: default' instead.",
        replaceWith = ReplaceWith("getEnvOrNull(name) ?: default", "moe.kanon.kommons")
    )
    /**
     * Returns the value of the environment variable stored under the given [name], or [default] if none is found.
     *
     * An environment variable is a system-dependent external named value.
     */
    fun getEnvOrElse(name: String, default: String): String = getEnvOrNull(name) ?: default

    @Deprecated(
        message = "Use 'getEnvOrNull(name)' instead.",
        replaceWith = ReplaceWith("getEnvOrNull(name)", "moe.kanon.kommons")
    )
    /**
     * Returns the value of the environment variable stored under the given [name], or `null` if none is found.
     *
     * An environment variable is a system-dependent external named value.
     *
     * @see System.getenv
     */
    fun getEnvOrNull(name: String): String? = System.getenv(name)

    /**
     * An object encapsulating very basic information regarding the current operating system the JVM is being ran under.
     */
    @Deprecated(message = "Questionable class, will be removed in later versions.")
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

    /**
     * An object encapsulating basic information regarding the current user.
     */
    @Deprecated(message = "Questionable class, will be removed in later versions.")
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

        // very basic implementations of the 'AppDirs' functionality available in the 'kommons.io' module.
        /**
         * Returns the [path][Path] to the data directory of the current operating system.
         *
         * Note that on Windows this will return the app-data directory located inside of roaming.
         */
        @Deprecated(
            message = "Use 'createDataDirectory(appName)' from kommons.io instead.",
            replaceWith = ReplaceWith("createDataDirectory()", "moe.kanon.kommons.io.paths")
        )
        val dataDir: Path by lazy {
            when (OPERATING_SYSTEM) {
                OperatingSystem.MAC_OS_X -> Paths.get(USER_HOME, "Library", "Application Support")
                OperatingSystem.UNIX -> Paths.get(USER_HOME, ".local", "share")
                OperatingSystem.WINDOWS -> Paths.get(APP_DATA)
            }
        }

        /**
         * Returns the [path][Path] to the config directory of the current operating system.
         *
         * Note that on Windows this will return the app-data directory located inside of roaming.
         */
        @Deprecated(
            message = "Use 'createConfigDirectory(appName)' from kommons.io instead.",
            replaceWith = ReplaceWith("createConfigDirectory()", "moe.kanon.kommons.io.paths")
        )
        val configDir: Path by lazy {
            when (OPERATING_SYSTEM) {
                OperatingSystem.MAC_OS_X -> dataDir
                OperatingSystem.UNIX -> Paths.get(USER_HOME, ".config")
                OperatingSystem.WINDOWS -> dataDir
            }
        }

        /**
         * Returns the [path][Path] to the cache directory of the current operating system.
         *
         * Note that Windows does *not* have a specific directory just for caches, so this will return a path pointing
         * towards a directory called "Cache" inside of the local app data directory.
         */
        @Deprecated(
            message = "Use 'createCacheDirectory(appName)' from kommons.io instead.",
            replaceWith = ReplaceWith("createCacheDirectory()", "moe.kanon.kommons.io.paths")
        )
        val cacheDir: Path by lazy {
            when (OPERATING_SYSTEM) {
                OperatingSystem.MAC_OS_X -> Paths.get(USER_HOME, "Library", "Caches")
                OperatingSystem.UNIX -> Paths.get(USER_HOME, ".cache")
                OperatingSystem.WINDOWS -> Paths.get(LOCAL_APP_DATA, "Cache")
            }
        }

        /**
         * Returns the [path][Path] to the logs directory of the current operating system.
         *
         * Note that Windows does *not* have a specific directory just for caches, so this will return a path pointing
         * towards a directory called "Logs" inside of the local app data directory.
         */
        @Deprecated(
            message = "Use 'createLogDirectory(appName)' from kommons.io instead.",
            replaceWith = ReplaceWith("createLogDirectory()", "moe.kanon.kommons.io.paths")
        )
        val logDir: Path by lazy {
            when (OPERATING_SYSTEM) {
                OperatingSystem.MAC_OS_X -> Paths.get(USER_HOME, "Library", "Logs")
                OperatingSystem.UNIX -> Paths.get(USER_HOME, ".cache")
                OperatingSystem.WINDOWS -> Paths.get(LOCAL_APP_DATA, "Logs")
            }
        }

        private val APP_DATA by lazy { getEnv("APPDATA") }
        private val LOCAL_APP_DATA by lazy { getEnv("LOCALAPPDATA") }
        private val OPERATING_SYSTEM = when {
            os.name.startsWith("windows", true) -> OperatingSystem.WINDOWS
            os.name.startsWith("mac os x", true) -> OperatingSystem.MAC_OS_X
            // assume it's some *nix based system
            else -> OperatingSystem.UNIX
        }

        private enum class OperatingSystem { WINDOWS, MAC_OS_X, UNIX }
    }
}