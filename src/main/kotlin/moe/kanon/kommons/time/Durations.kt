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
@file:JvmName("KDurations")

package moe.kanon.kommons.time

import java.time.Duration
import java.time.temporal.TemporalUnit

// int
val Int.nanos: Duration inline get() = Duration.ofNanos(this.toLong())
val Int.millis: Duration inline get() = Duration.ofMillis(this.toLong())
val Int.seconds: Duration inline get() = Duration.ofSeconds(this.toLong())
val Int.minutes: Duration inline get() = Duration.ofMinutes(this.toLong())
val Int.hours: Duration inline get() = Duration.ofHours(this.toLong())
val Int.days: Duration inline get() = Duration.ofDays(this.toLong())

inline fun Int.toDuration(type: TemporalUnit) = Duration.of(this.toLong(), type)

// long
