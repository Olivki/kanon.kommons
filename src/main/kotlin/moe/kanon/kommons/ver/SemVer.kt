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

@file:JvmName("SemVers")

package moe.kanon.kommons.ver

import java.io.Serializable

typealias Version = SemVer

// TODO: This

@Suppress("DataClassPrivateConstructor")
data class SemVer private constructor(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val preRelease: String,
    val buildMetadata: String
) : Comparable<SemVer>, Serializable {
    private val appendage: String
        get() = buildString {
            when {
                !preRelease.isBlank() -> appendln("-$preRelease")
                !buildMetadata.isBlank() -> appendln("-$buildMetadata")
            }
        }

    override fun compareTo(other: SemVer): Int {
        TODO("not implemented")
    }

    override fun toString(): String = "$major.$minor.$patch$appendage"

    companion object {
        @JvmStatic
        @JvmOverloads
        @JvmName("of")
        operator fun invoke(
            major: Int,
            minor: Int = 0,
            patch: Int = 0,
            preRelease: String = "",
            buildMetadata: String = ""
        ): SemVer = SemVer(major, minor, patch, preRelease, buildMetadata)

        @JvmStatic
        @JvmName("valueOf")
        operator fun invoke(ver: String): SemVer = TODO()
    }
}

class SemVerParsingException(val input: String, message: String, cause: Throwable? = null) :
    RuntimeException(message, cause)