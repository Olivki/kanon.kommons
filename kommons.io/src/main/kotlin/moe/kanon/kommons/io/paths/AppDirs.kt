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
@file:JvmName("KFiles")

package moe.kanon.kommons.io.paths

import moe.kanon.kommons.OS_NAME
import moe.kanon.kommons.USER_HOME
import moe.kanon.kommons.getEnv
import java.nio.file.Path

/*
 * This implementation is based on/inspired by https://github.com/harawata/appdirs and https://github.com/erayerdin/kappdirs
 * but with the intention of being a lot "simpler" by getting rid of the extra 'version' and 'author' parameters, as
 * those can easily be added by the user if they so feel like it and by "hiding" the actual "factories" behind simple
 * top-level functions.
 */
// this class is essentially an enum, but made in a way so that we can hide the singletons and only present the user
// with the generated instance that we want to show.
sealed class AppDirs(private val name: String) {
    companion object {
        private val INSTANCE: AppDirs by lazy {
            val os = OS_NAME.toLowerCase()
            when {
                os.startsWith("windows") -> Windows
                os.startsWith("mac os x") -> MacOsX
                // assume it's some *nix based system
                else -> Unix
            }
        }

        @JvmName("getInstance")
        @JvmStatic operator fun invoke(): AppDirs = INSTANCE
    }

    abstract fun getUserDataDir(name: String, roaming: Boolean): Path

    abstract fun getUserConfigDir(name: String, roaming: Boolean): Path

    abstract fun getUserCacheDir(name: String): Path

    abstract fun getUserLogDir(name: String): Path

    abstract fun getSiteDataDir(name: String, local: Boolean): Path

    abstract fun getSiteConfigDir(name: String): Path

    final override fun toString(): String = "AppDirs{$name}"

    private object MacOsX : AppDirs("Mac OS X") {
        override fun getUserDataDir(name: String, roaming: Boolean): Path =
            pathOf(USER_HOME, "Library", "Application Support", name)

        override fun getUserConfigDir(name: String, roaming: Boolean): Path = getUserDataDir(name, roaming)

        override fun getUserCacheDir(name: String): Path = pathOf(USER_HOME, "Library", "Caches", name)

        override fun getUserLogDir(name: String): Path = pathOf(USER_HOME, "Library", "Logs", name)

        override fun getSiteDataDir(name: String, local: Boolean): Path =
            pathOf("/", "Library", "Application Support", name)

        override fun getSiteConfigDir(name: String): Path = getSiteDataDir(name, false)
    }

    private object Unix : AppDirs("*nix") {
        override fun getUserDataDir(name: String, roaming: Boolean): Path = pathOf(USER_HOME, ".local", "share", name)

        override fun getUserConfigDir(name: String, roaming: Boolean): Path = pathOf(USER_HOME, ".config", name)

        override fun getUserCacheDir(name: String): Path = pathOf(USER_HOME, ".cache", name)

        override fun getUserLogDir(name: String): Path = pathOf(USER_HOME, ".cache", name, "logs")

        override fun getSiteDataDir(name: String, local: Boolean): Path =
            if (local) pathOf("/", "usr", "local", "share", name) else pathOf("/", "usr", "share", name)

        override fun getSiteConfigDir(name: String): Path = pathOf("/", "etc", name)
    }

    private object Windows : AppDirs("Windows") {
        // lazy so that we don't encounter any exceptions when this instance is created
        private val appData by lazy { getEnv("APPDATA") }
        private val localAppData by lazy { getEnv("LOCALAPPDATA") }
        private val programData by lazy { getEnv("PROGRAMDATA") }

        override fun getUserDataDir(name: String, roaming: Boolean): Path =
            pathOf(if (roaming) appData else localAppData, name)

        override fun getUserConfigDir(name: String, roaming: Boolean): Path = getUserDataDir(name, roaming)

        override fun getUserCacheDir(name: String): Path = pathOf(localAppData, name, "Cache")

        override fun getUserLogDir(name: String): Path = pathOf(localAppData, name, "Logs")

        override fun getSiteDataDir(name: String, local: Boolean): Path = pathOf(programData, name)

        override fun getSiteConfigDir(name: String): Path = getSiteDataDir(name, false)
    }
}

/**
 * Returns a newly created [Path] instance pointing to a `directory` made using the given [name], the directory is
 * located in the current operating systems user data directory.
 *
 * @param [appName] the name of the application to create the directory for
 * @param [roaming] Whether or not the directory should be created in the `roaming` directory.
 *
 * **Only used on Windows.**
 *
 * @return the newly created directory
 */
@JvmOverloads fun createDataDirectory(appName: String, roaming: Boolean = true): Path =
    AppDirs().getUserDataDir(appName, roaming).createDirectories()

/**
 * Returns a newly created [Path] instance pointing to a `directory` made using the given [name], the directory is
 * located in the current operating systems user config directory.
 *
 * @param [appName] the name of the application to create the directory for
 * @param [roaming] Whether or not the directory should be created in the `roaming` directory.
 *
 * **Only used on Windows.**
 *
 * @return the newly created directory
 */
@JvmOverloads fun createConfigDirectory(appName: String, roaming: Boolean = true): Path =
    AppDirs().getUserConfigDir(appName, roaming).createDirectories()

/**
 * Returns a newly created [Path] instance pointing to a `directory` made using the given [name], the directory is
 * located in the current operating systems user cache directory.
 *
 * @param [appName] the name of the application to create the directory for
 *
 * @return the newly created directory
 */
fun createCacheDirectory(appName: String): Path = AppDirs().getUserCacheDir(appName).createDirectories()

/**
 * Returns a newly created [Path] instance pointing to a `directory` made using the given [name], the directory is
 * located in the current operating systems user log directory.
 *
 * @param [appName] the name of the application to create the directory for
 *
 * @return the newly created directory
 */
fun createLogDirectory(appName: String): Path = AppDirs().getUserLogDir(appName).createDirectories()