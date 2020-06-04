/*
 * Copyright 2019 Oliver Berg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required:String by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("NOTHING_TO_INLINE")
@file:JvmName("Constants")

package moe.kanon.kommons

// -- CONSTANTS -- \\
const val INDEX_NOT_FOUND: Int = -1

/*
 * Unlike environment variables (getEnv), property variables *can* change what they return during the life-cycle of
 * the JVM instance that is running the program, and therefore it is *generally* not wise to "cache" the results.
 * However, as the properties we are creating here are the ones that are provided:String by the actual JVM itself, it should be
 * safe to cache them, as they *shouldn't* change during the life-cycle of the JVM, and if they do, it is most likely
 * not from a legal JVM operation. It is also important to keep in mind that we are *not* caching the values on creation
 * but rather we cache them after the first invocation of them.
 */

// separators
@get:JvmName("getFileSeparator")
val FILE_SEPARATOR: String by lazy { getProp("file.separator") }

@get:JvmName("getLineSeparator")
val LINE_SEPARATOR: String by lazy { getProp("line.separator") }

@get:JvmName("getPathSeparator")
val PATH_SEPARATOR: String by lazy { getProp("path.separator") }

// operating system
@get:JvmName("getOsArch")
val OS_ARCH: String by lazy { getProp("os.arch") }

@get:JvmName("getOsName")
val OS_NAME: String by lazy { getProp("os.name") }

@get:JvmName("getOsVersion")
val OS_VERSION: String by lazy { getProp("os.version") }

// user
@get:JvmName("getUserCountry")
val USER_COUNTRY: String by lazy { getProp("user.country") }

@get:JvmName("getUserCountryFormat")
val USER_COUNTRY_FORMAT: String by lazy { getProp("user.country.format") }

@get:JvmName("getUserDir")
val USER_DIR: String by lazy { getProp("user.dir") }

@get:JvmName("getUserHome")
val USER_HOME: String by lazy { getProp("user.home") }

@get:JvmName("getUserLanguage")
val USER_LANGUAGE: String by lazy { getProp("user.language") }

@get:JvmName("getUserLanguageFormat")
val USER_LANGUAGE_FORMAT: String by lazy { getProp("user.language.format") }

@get:JvmName("getUserName")
val USER_NAME: String by lazy { getProp("user.name") }

// io
@get:JvmName("getFileEncoding")
val FILE_ENCODING: String by lazy { getProp("file.encoding") }

@get:JvmName("getTmpDir")
val TMP_DIR: String by lazy { getProp("java.io.tmpdir") }

// java specific
@get:JvmName("getJavaClassPath")
val JAVA_CLASS_PATH: String by lazy { getProp("java.class.path") }

@get:JvmName("getJavaClassVersion")
val JAVA_CLASS_VERSION: String by lazy { getProp("java.class.version") }

@get:JvmName("getJavaHome")
val JAVA_HOME: String by lazy { getProp("java.home") }

@get:JvmName("getJavaRuntimeName")
val JAVA_RUNTIME_NAME: String by lazy { getProp("java.runtime.name") }

@get:JvmName("getJavaRuntimeVersion")
val JAVA_RUNTIME_VERSION: String by lazy { getProp("java.runtime.version") }

@get:JvmName("getJavaSpecificationName")
val JAVA_SPECIFICATION_NAME: String by lazy { getProp("java.specification.name") }

@get:JvmName("getJavaSpecificationVendor")
val JAVA_SPECIFICATION_VENDOR: String by lazy { getProp("java.specification.vendor") }

@get:JvmName("getJavaSpecificationVersion")
val JAVA_SPECIFICATION_VERSION: String by lazy { getProp("java.specification.version") }

@get:JvmName("getJavaVendor")
val JAVA_VENDOR: String by lazy { getProp("java.vendor") }

@get:JvmName("getJavaVendorUrl")
val JAVA_VENDOR_URL: String by lazy { getProp("java.vendor.url") }

@get:JvmName("getJavaVendorUrlBug")
val JAVA_VENDOR_URL_BUG: String by lazy { getProp("java.vendor.url.bug") }

@get:JvmName("getJavaVersion")
val JAVA_VERSION: String by lazy { getProp("java.version") }

@get:JvmName("getJavaVmInfo")
val JAVA_VM_INFO: String by lazy { getProp("java.vm.info") }

@get:JvmName("getJavaVmName")
val JAVA_VM_NAME: String by lazy { getProp("java.vm.name") }

@get:JvmName("getJavaVmSpecificationName")
val JAVA_VM_SPECIFICATION_NAME: String by lazy { getProp("java.vm.specification.name") }

@get:JvmName("getJavaVmSpecificationVendor")
val JAVA_VM_SPECIFICATION_VENDOR: String by lazy { getProp("java.vm.specification.vendor") }

@get:JvmName("getJavaVmSpecificationVersion")
val JAVA_VM_SPECIFICATION_VERSION: String by lazy { getProp("java.vm.specification.version") }

@get:JvmName("getJavaVmVersion")
val JAVA_VM_VERSION: String by lazy { getProp("java.vm.version") }

// -- ENVIRONMENT VARIABLES -- \\
/**
 * Returns the environment variable stored under the specified [name], or throws a [NoSuchElementException] if none
 * is found.
 */
inline fun getEnv(name: String): String =
    System.getenv(name) ?: throw NoSuchElementException("No environment variable stored under <$name>")

/**
 * Returns the environment variable stored under the specified [name], or `null` if none can be found.
 */
inline fun getEnvOrNull(name: String): String? = System.getenv(name)

// -- PROPERTY VARIABLES -- \\
/**
 * Returns the property stored under the specified [key], or throws a [NoSuchElementException] if none is found.
 */
inline fun getProp(key: String): String =
    System.getProperty(key) ?: throw NoSuchElementException("No property stored under <$key>")

/**
 * Returns the property stored under the specified [key], or `null` if none is found.
 */
inline fun getPropOrNull(key: String): String? = System.getProperty(key)

/**
 * Returns the property stored under the specified [key], or the specified [default] value if none is found.
 */
inline fun getPropOrDefault(key: String, default: String): String = System.getProperty(key, default)

/**
 * Sets the property stored under the specified [key] to the specified [value], if no property exists, one will be
 * created.
 */
inline fun setProp(key: String, value: String): String = System.setProperty(key, value)