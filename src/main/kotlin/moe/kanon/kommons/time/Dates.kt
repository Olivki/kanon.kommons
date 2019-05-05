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

@file:JvmName("KDates")
@file:Suppress("ClassName", "UNUSED_PARAMETER", "NOTHING_TO_INLINE")

package moe.kanon.kommons.time

import moe.kanon.kommons.Ghost
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.Period
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.Temporal
import java.time.temporal.TemporalAccessor
import java.time.temporal.TemporalAmount
import java.time.temporal.TemporalUnit

// for syntactic sugar (from kxdate)
object ago : Ghost()

object fromNow : Ghost()

typealias `from now` = fromNow

// constants
inline val NOW: Instant @JvmName("getInstant") get() = Instant.now()
inline val CURRENT_DATE: LocalDate @JvmName("getCurrentDate") get() = LocalDate.now()
inline val CURRENT_TIME: LocalDateTime @JvmName("getCurrentTime") get() = LocalDateTime.now()

// Durations
inline val Duration.ago: LocalDateTime get() = CURRENT_TIME - this

inline val Duration.fromNow: LocalDateTime get() = CURRENT_TIME + this

inline infix fun Duration.and(other: Duration): Duration = this + other

// int
// properties
inline val Int.nanoseconds: Duration get() = Duration.ofNanos(this.toLong())

inline val Int.microseconds: Duration get() = Duration.ofNanos(this.toLong() * 1_000L)

inline val Int.milliseconds: Duration get() = Duration.ofMillis(this.toLong())

inline val Int.seconds: Duration get() = Duration.ofSeconds(this.toLong())

inline val Int.minutes: Duration get() = Duration.ofMinutes(this.toLong())

inline val Int.hours: Duration get() = Duration.ofHours(this.toLong())

// functions
inline infix fun Int.nanoseconds(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusNanos(this.toLong())

inline infix fun Int.nanoseconds(ago: ago): LocalDateTime = CURRENT_TIME.minusNanos(this.toLong())

inline infix fun Int.microseconds(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusNanos(this.toLong() * 1_000L)

inline infix fun Int.microseconds(ago: ago): LocalDateTime = CURRENT_TIME.minusNanos(this.toLong() * 1_000L)

inline infix fun Int.milliseconds(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusNanos(this.toLong() * 1_000_000L)

inline infix fun Int.milliseconds(ago: ago): LocalDateTime = CURRENT_TIME.minusNanos(this.toLong() * 1_000_000L)

inline infix fun Int.seconds(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusSeconds(this.toLong())

inline infix fun Int.seconds(ago: ago): LocalDateTime = CURRENT_TIME.minusSeconds(this.toLong())

inline infix fun Int.minutes(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusMinutes(this.toLong())

inline infix fun Int.minutes(ago: ago): LocalDateTime = CURRENT_TIME.minusMinutes(this.toLong())

inline infix fun Int.hours(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusHours(this.toLong())

inline infix fun Int.hours(ago: ago): LocalDateTime = CURRENT_TIME.minusHours(this.toLong())

inline fun Int.toDuration(type: TemporalUnit): Duration = Duration.of(this.toLong(), type)

// long
// properties
inline val Long.nanoseconds: Duration get() = Duration.ofNanos(this)

inline val Long.microseconds: Duration get() = Duration.ofNanos(this * 1000L)

inline val Long.milliseconds: Duration get() = Duration.ofMillis(this)

inline val Long.seconds: Duration get() = Duration.ofSeconds(this)

inline val Long.minutes: Duration get() = Duration.ofMinutes(this)

inline val Long.hours: Duration get() = Duration.ofHours(this)

// functions
inline infix fun Long.nanoseconds(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusNanos(this)

inline infix fun Long.nanoseconds(ago: ago): LocalDateTime = CURRENT_TIME.minusNanos(this)

inline infix fun Long.microseconds(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusNanos(this * 1_000L)

inline infix fun Long.microseconds(ago: ago): LocalDateTime = CURRENT_TIME.minusNanos(this * 1_000L)

inline infix fun Long.milliseconds(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusNanos(this * 1_000_000L)

inline infix fun Long.milliseconds(ago: ago): LocalDateTime = CURRENT_TIME.minusNanos(this * 1_000_000L)

inline infix fun Long.seconds(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusSeconds(this)

inline infix fun Long.seconds(ago: ago): LocalDateTime = CURRENT_TIME.minusSeconds(this)

inline infix fun Long.minutes(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusMinutes(this)

inline infix fun Long.minutes(ago: ago): LocalDateTime = CURRENT_TIME.minusMinutes(this)

inline infix fun Long.hours(fromNow: fromNow): LocalDateTime = CURRENT_TIME.plusHours(this)

inline infix fun Long.hours(ago: ago): LocalDateTime = CURRENT_TIME.minusHours(this)

inline fun Long.toDuration(type: TemporalUnit): Duration = Duration.of(this, type)

// Periods
inline val Period.ago: LocalDate get() = CURRENT_DATE - this

inline val Period.fromNow: LocalDate get() = CURRENT_DATE + this

inline infix fun Period.and(amount: TemporalAmount): Period = this + amount

// int
// properties
inline val Int.days: Period get() = Period.ofDays(this)

inline val Int.weeks: Period get() = Period.ofWeeks(this)

inline val Int.months: Period get() = Period.ofMonths(this)

inline val Int.years: Period get() = Period.ofYears(this)

// functions
inline infix fun Int.days(fromNow: fromNow): LocalDate = CURRENT_DATE.plusDays(this.toLong())

inline infix fun Int.days(ago: ago): LocalDate = CURRENT_DATE.minusDays(this.toLong())

inline infix fun Int.weeks(fromNow: fromNow): LocalDate = CURRENT_DATE.plusWeeks(toLong())

inline infix fun Int.weeks(ago: ago): LocalDate = CURRENT_DATE.minusWeeks(toLong())

inline infix fun Int.months(fromNow: fromNow): LocalDate = CURRENT_DATE.plusMonths(toLong())

inline infix fun Int.months(ago: ago): LocalDate = CURRENT_DATE.minusMonths(toLong())

inline infix fun Int.years(fromNow: fromNow): LocalDate = CURRENT_DATE.plusYears(toLong())

inline infix fun Int.years(ago: ago): LocalDate = CURRENT_DATE.minusYears(toLong())

// Instants
infix fun Instant.truncateTo(unit: TemporalUnit): Instant = this.truncatedTo(unit)

infix fun Instant.adjustTo(temporal: Temporal): Temporal = this.adjustInto(temporal)

infix fun Instant.withOffset(offset: ZoneOffset): OffsetDateTime = this.atOffset(offset)

infix fun Instant.withZone(zone: ZoneId): ZonedDateTime = this.atZone(zone)

// Comparables
// temporals
infix fun <T> T.isBefore(other: T): Boolean
    where T : Temporal,
          T : Comparable<T> = this < other

infix fun <T> T.isAfter(other: T): Boolean
    where T : Temporal,
          T : Comparable<T> = this > other

// temporal amounts
infix fun <T> T.isBefore(other: T): Boolean
    where T : TemporalAmount,
          T : Comparable<T> = this < other

infix fun <T> T.isAfter(other: T): Boolean
    where T : TemporalAmount,
          T : Comparable<T> = this > other

// temporal accessors
infix fun <T> T.isBefore(other: T): Boolean
    where T : TemporalAccessor,
          T : Comparable<T> = this < other

infix fun <T> T.isAfter(other: T): Boolean
    where T : TemporalAccessor,
          T : Comparable<T> = this > other