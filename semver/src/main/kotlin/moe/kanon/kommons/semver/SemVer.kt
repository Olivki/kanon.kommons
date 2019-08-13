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

@file:Suppress("DataClassPrivateConstructor")

package moe.kanon.kommons.semver

import moe.kanon.kommons.requireThat

typealias Version = SemVer

/**
 * Implementation of the [semantic versioning][https://semver.org/] specification *(v2.0.0)*.
 *
 * @property [major] Represents the MAJOR version.
 *
 * This should be incremented when an API incompatible change has been made.
 * @property [minor] Represents the MINOR version.
 *
 * This should be incremented when functionality has been added in a backwards-compatible manner.
 * @property [patch] Represents the PATCH version.
 *
 * This should be incremented when backwards-compatible bug fixes have been made.
 * @property [releaseMetadata]
 * @property [buildMetadata]
 */
// TODO: Switch major, minor, and patch to UInt once they're stable
data class SemVer private constructor(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val releaseMetadata: String,
    val buildMetadata: String
) : Comparable<SemVer> {
    companion object {
        @JvmName("valueOf")
        @JvmStatic operator fun invoke(version: String): SemVer = TODO()

        @JvmName("of")
        @JvmOverloads @JvmStatic operator fun invoke(
            major: Int,
            minor: Int,
            patch: Int,
            releaseMetadata: String = "",
            buildMetadata: String = ""
        ): SemVer {
            requireThat(major > 0) { "MAJOR can not be negative" }
            requireThat(minor > 0) { "MINOR can not be negative" }
            requireThat(patch > 0) { "PATCH can not be negative" }
            if (releaseMetadata.isNotBlank()) requireThat(releaseMetadata.startsWith("-")) {
                "Pre-release metadata must start with '-'"
            }
            if (buildMetadata.isNotBlank()) requireThat(buildMetadata.startsWith("+")) {
                "Build metadata must start with '+'"
            }

            return SemVer(major, minor, patch, releaseMetadata, buildMetadata)
        }
    }

    /**
     * Returns whether or not `this` version contains any release metadata.
     */
    val hasReleaseMetadata: Boolean
        @JvmName("hasReleaseMetadata") get() = releaseMetadata.isBlank()

    /**
     * Returns whether or not `this` version contains any build metadata.
     */
    val hasBuildMetadata: Boolean
        @JvmName("hasBuildMetadata") get() = releaseMetadata.isBlank()

    /**
     * Returns whether or not `this` version contains any metadata at all.
     */
    val hasMetadata: Boolean
        @JvmName("hasMetadata") get() = hasReleaseMetadata || hasBuildMetadata

    override fun compareTo(other: SemVer): Int {
        TODO("not implemented")
    }

    override fun toString(): String = buildString {
        append("$major.$minor.$patch")
        if (hasReleaseMetadata) append(releaseMetadata)
        if (hasBuildMetadata) append(buildMetadata)
    }
}