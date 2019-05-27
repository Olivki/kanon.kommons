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

@file:Suppress("NOTHING_TO_INLINE")
@file:JvmName("Constants")

package moe.kanon.kommons

// -- CONSTANTS -- \\
/*
 * As we can't *actually* make these into 'const val' constructs, we'll just make them inlined, which essentially
 * accomplishes the same thing in the end.
 *
 * Unlike environment variables (getEnv), property variables *can* change what they return during the life-cycle of
 * the JVM instance that is running the program, and therefore it is not wise to "cache" the results.
 */
// separators
val FILE_SEPARATOR @JvmName("getFileSeparator") get() = getProp("file.separator")

val LINE_SEPARATOR @JvmName("getLineSeparator") get() = getProp("line.separator")

val PATH_SEPARATOR @JvmName("getPathSeparator") get() = getProp("path.separator")

// operating system
val OS_ARCH @JvmName("getOsArch") get() = getProp("os.arch")

val OS_NAME @JvmName("getOsName") get() = getProp("os.name")

val OS_VERSION @JvmName("getOsVersion") get() = getProp("os.version")

// user
val USER_COUNTRY @JvmName("getUserCountry") get() = getProp("user.country")

val USER_COUNTRY_FORMAT @JvmName("getUserCountryFormat") get() = getProp("user.country.format")

val USER_DIR @JvmName("getUserDir") get() = getProp("user.dir")

val USER_LANGUAGE @JvmName("getUserLanguage") get() = getProp("user.language")

val USER_LANGUAGE_FORMAT @JvmName("getUserLanguageFormat") get() = getProp("user.language.format")

val USER_NAME @JvmName("getUserName") get() = getProp("user.name")

// io
val FILE_ENCODING @JvmName("getFileEncoding") get() = getProp("file.encoding")

val TMP_DIR @JvmName("getTmpDir") get() = getProp("java.io.tmpdir")

// java specific
val JAVA_CLASS_PATH @JvmName("getJavaClassPath") get() = getProp("java.class.path")

val JAVA_CLASS_VERSION @JvmName("getJavaClassVersion") get() = getProp("java.class.version")

val JAVA_HOME @JvmName("getJavaHome") get() = getProp("java.home")

val JAVA_RUNTIME_NAME @JvmName("getJavaRuntimeName") get() = getProp("java.runtime.name")

val JAVA_RUNTIME_VERSION @JvmName("getJavaRuntimeVersion") get() = getProp("java.runtime.version")

val JAVA_SPECIFICATION_NAME @JvmName("getJavaSpecificationName") get() = getProp("java.specification.name")

val JAVA_SPECIFICATION_VENDOR @JvmName("getJavaSpecificationVendor") get() = getProp("java.specification.vendor")

val JAVA_SPECIFICATION_VERSION @JvmName("getJavaSpecificationVersion") get() = getProp("java.specification.version")

val JAVA_VENDOR @JvmName("getJavaVendor") get() = getProp("java.vendor")

val JAVA_VENDOR_URL @JvmName("getJavaVendorUrl") get() = getProp("java.vendor.url")

val JAVA_VENDOR_URL_BUG @JvmName("getJavaVendorUrlBug") get() = getProp("java.vendor.url.bug")

val JAVA_VERSION @JvmName("getJavaVersion") get() = getProp("java.version")

val JAVA_VM_INFO @JvmName("getJavaVmInfo") get() = getProp("java.vm.info")

val JAVA_VM_NAME @JvmName("getJavaVmName") get() = getProp("java.vm.name")

val JAVA_VM_SPECIFICATION_NAME @JvmName("getJavaVmSpecificationName") get() = getProp("java.vm.specification.name")

val JAVA_VM_SPECIFICATION_VENDOR @JvmName("getJavaVmSpecificationVendor") get() = getProp("java.vm.specification.vendor")

val JAVA_VM_SPECIFICATION_VERSION @JvmName("getJavaVmSpecificationVersion") get() = getProp("java.vm.specification.version")

val JAVA_VM_VERSION @JvmName("getJavaVmVersion") get() = getProp("java.vm.version")

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