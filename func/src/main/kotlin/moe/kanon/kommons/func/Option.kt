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

@file:JvmName("KOption")
@file:Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER", "NOTHING_TO_INLINE")

package moe.kanon.kommons.func

import moe.kanon.kommons.Identifiable
import moe.kanon.kommons.PortOf
import moe.kanon.kommons.func.internal.EmptyIterator
import moe.kanon.kommons.func.internal.SingletonIterator
import moe.kanon.kommons.requireNonFatal
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import kotlin.contracts.contract
import java.util.Optional as JOptional

/*
 * This is based on the Optional[1] class from the Java standard library, and the Option[2] class from the scala
 * standard library.
 *
 * The main goal of this class is to provide good interop when working with java, as a 'Option' class may not be
 * very needed in Kotlin as it has a good set of tools to deal with nullability and the like, but when designing
 * something that should also work with Java the Java side loses out on all the null-safety that Kotlin offers.
 *
 * Unlike the version available in Arrow, this port has been converted to be more "idiomatic" and more closely follow
 * the principles of Kotlin and its naming schemes.
 *
 * [1]: https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/file/cecd70d27b27/src/share/classes/java/util/Optional.java
 * [2]: https://github.com/scala/scala/blob/2.13.x/src/library/scala/Option.scala
 */

/**
 * A container object which may or may not contain a non-null value.
 *
 * If a value is present, [isPresent][Option.isPresent] will return `true` and [get][Option.value] will return the
 * value.
 */
typealias Optional<T> = Option<T>

/**
 * A container object which may or may not contain a non-null value.
 *
 * If a value is present, [isPresent][Option.isPresent] will return `true` and [get][Option.value] will return the
 * value.
 */
typealias Maybe<T> = Option<T>

/**
 * A container object which may or may not contain a non-null value.
 *
 * If a value is present, [isPresent] will return `true` and [value] will return the value.
 *
 * ### Port-Of Links
 * 1. [java.util.Optional](https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/file/cecd70d27b27/src/share/classes/java/util/Optional.java)
 * 1. [scala.Option](https://github.com/scala/scala/blob/2.13.x/src/library/scala/Option.scala)
 */
@PortOf("java.util.Optional", "scala.util.Option")
sealed class Option<out T> : Identifiable {
    companion object {
        // kotlin only
        /**
         * Returns a [Some] containing the specified [value], or [None] if `value` is `null`.
         */
        // enables the syntax of 'Optional(67)'
        @JvmName("of")
        @JvmStatic operator fun <T> invoke(value: T?): Option<T> = when (value) {
            null -> None
            else -> Some(value)
        }

        /**
         * Returns a [Some] containing the result of invoking the specified [value], or [None] if `value` is `null`.
         */
        // enables the syntax of 'Optional { 67 }'
        @JvmName("calculate")
        inline operator fun <T : Any> invoke(value: () -> T?): Option<T> = value().toOption()

        /**
         * Invokes the specified [value] wrapped in a `try catch` block, returns [Some] if no errors are thrown,
         * otherwise returns [None].
         *
         * Note that any fatal exceptions *(for now, this means any exceptions that inherit from [Error])* will not be
         * caught, and will simply be re-thrown.
         *
         * This function enables one to use the [Option] class as a type of [Try]. Do note that unlike `Result`,
         * no information regarding the thrown exception is saved in the `Optional` form, as `None` does not
         * contain any data.
         */
        inline fun <T> tryCatch(value: () -> T): Option<T> = try {
            Some(value())
        } catch (t: Throwable) {
            requireNonFatal(t)
            None
        }

        /**
         * Returns [None] cast to [T].
         */
        @JvmStatic fun <T> empty(): Option<T> = None
    }

    /**
     * Returns `true` if this has a value present, otherwise `false`.
     */
    abstract val isPresent: Boolean

    /**
     * Returns `true` if this has no value present, otherwise `false`.
     */
    abstract val isEmpty: Boolean

    /**
     * Returns the [item][Some.item] if `this` is [Some], or throws a [NoSuchElementException] if `this` is [None].
     */
    val value: T
        @JvmName("get") get() = when (this) {
            is None -> throw NoSuchElementException("No value is present in this optional")
            is Some -> item
        }

    /**
     * Returns a [SingletonIterator] if a value is present, otherwise returns a [SingletonIterator].
     */
    val iterator: Iterator<T>
        get() = when (this) {
            is None -> EmptyIterator
            is Some -> SingletonIterator(item)
        }

    /**
     * Creates and returns a [JOptional][java.util.Optional] based on `this` optional.
     *
     * After the first invocation of this function, any subsequent invocations on the same `Optional` instance will
     * return the same result, as the result is cached upon the first invocation.
     */
    @get:JvmName("toJava")
    val java: JOptional<@UnsafeVariance T> by lazy {
        JOptional.ofNullable(orNull())
    }

    /**
     * Returns the [value] of `this` optional if it is present, otherwise returns `null`.
     */
    @JvmName("getOrNull")
    fun orNull(): T? = when (this) {
        is None -> null
        is Some -> item
    }

    /**
     * Returns the value of `this` optional, or returns the value of the specified [default] if a value is not present.
     */
    @JvmName("getOrElse")
    inline infix fun orElse(default: () -> @UnsafeVariance T): T = when (this) {
        is None -> default()
        is Some -> item
    }

    /**
     * Returns the [value] of `this` optional if it is present, otherwise returns [default].
     */
    @JvmName("getOrElse")
    infix fun orElse(default: @UnsafeVariance T): T = orElse { default }

    /**
     * Returns the value of `this` optional, or throws the specified [exception] if a value is not present.
     *
     * Note that the [get] function *does* throw a [NoSuchElementException] if no value is present, so this should
     * generally only be used if a specialized exception is needed.
     */
    @JvmName("getOrThrow")
    inline infix fun <X : Exception> orThrow(exception: () -> X): T = when (this) {
        is None -> throw exception()
        is Some -> item
    }

    /**
     * Executes the specified [action] if a value [is present][isPresent].
     */
    inline fun ifPresent(action: (T) -> Unit) {
        if (isPresent) action(value)
    }

    /**
     * Returns the result of invoking either [ifEmpty] or [ifPresent] depending on whether or not a value is present.
     *
     * @param [R] the value to return
     *
     * @param [ifEmpty] invoked if there is no value present
     * @param [ifPresent] invoked with the [value] if it is present
     */
    inline fun <R> fold(ifEmpty: () -> R, ifPresent: (T) -> R): R = when (this) {
        is None -> ifEmpty()
        is Some -> ifPresent(item)
    }

    /**
     * Returns the result of applying [value] to the [transformer] if present, otherwise returns [None].
     *
     * Note that if the result of applying `value` to the `transformer` results in `null` then [None] is returned.
     */
    inline fun <R> map(transformer: (T) -> R?): Option<R> = when (this) {
        is None -> this
        is Some -> Option(transformer(item))
    }

    /**
     * Returns the result of applying [value] to the [transformer] if present, otherwise returns [None].
     *
     * This function is similar to [map], but the `transformer` is one whose result is already an `Optional`,
     * and if invoked, `flatMap` does not wrap it with an additional `Optional`.
     */
    inline fun <R> flatMap(transformer: (T) -> Option<R>): Option<R> = when (this) {
        is None -> this
        is Some -> transformer(item)
    }

    /**
     * Returns [Some] if a value that matches the [predicate] is present, otherwise returns [None].
     */
    inline fun filter(predicate: (T) -> Boolean): Option<T> =
        flatMap { if (predicate(it)) Some(it) else None }

    /**
     * Returns [Some] if a value that does *not* match the [predicate] is present, otherwise returns [None].
     */
    inline fun filterNot(predicate: (T) -> Boolean): Option<T> =
        flatMap { if (!predicate(it)) Some(it) else None }

    /**
     * Returns `false` if no value is present, otherwise returns the result of calling [predicate] with the [value].
     */
    inline fun any(predicate: (T) -> Boolean): Boolean = when (this) {
        is None -> false
        is Some -> predicate(item)
    }

    /**
     * Returns `true` if no value is present, otherwise returns the result of calling [predicate] with the [value].
     */
    inline fun all(predicate: (T) -> Boolean): Boolean = when (this) {
        is None -> true
        is Some -> predicate(item)
    }

    /**
     * Returns `false` if there's a value present and the [predicate] is met by it, otherwise returns `true`.
     */
    inline fun none(predicate: (T) -> Boolean): Boolean = when (this) {
        is None -> true
        is Some -> !predicate(item)
    }

    /**
     * If no value is present returns [None], otherwise returns [other].
     */
    infix fun <U> and(other: Option<U>): Option<U> = if (isEmpty) None else other

    /**
     * Returns `this` if a value is present, otherwise returns [other].
     */
    infix fun or(other: Option<@UnsafeVariance T>): Option<T> = if (isPresent) this else other

    /**
     * If a value is present, returns whether or not [value][Option.value] is equal to the specified [value],
     * otherwise returns `false`.
     */
    operator fun contains(value: @UnsafeVariance T): Boolean = when (this) {
        is None -> false
        is Some -> value == this.item
    }

    /**
     * Returns a [Iterable] that's based on the [iterator] of `this` optional.
     */
    fun asIterable(): Iterable<T> = Iterable { iterator }

    /**
     * Returns a [Sequence] that's based on the [iterator] of `this` optional.
     */
    fun asSequence(): Sequence<T> = Sequence { iterator }

    abstract operator fun component1(): T
}

/**
 * Represents an empty [Option] value.
 */
object None : Option<Nothing>() {
    override val isPresent: Boolean = false
    override val isEmpty: Boolean = true

    override fun hashCode(): Int = 0

    override fun equals(other: Any?): Boolean = other is None

    override operator fun component1(): Nothing = throw NoSuchElementException()

    override fun toString(): String = "None"
}

typealias Just<T> = Some<T>

/**
 * Represents a present [Option] value.
 */
data class Some<out T>(val item: T) : Option<T>() {
    override val isPresent: Boolean = true
    override val isEmpty: Boolean = false

    override fun hashCode(): Int = item.hashCode()

    override fun equals(other: Any?): Boolean = item?.equals(other) ?: false

    override fun toString(): String = "Some[$item]"
}

/**
 * Returns a [Some] containing `this`, or [None] if `this` is `null`.
 */
@JvmName("fromNullable")
fun <T> T?.toOption(): Option<T> = Option(this)

/**
 * Converts `this` [java.util.Optional] to a [Option] instance.
 *
 * If a value is not present in `this` then the resulting `Optional` will be [None].
 */
val <T> JOptional<T>.kotlin: Option<T>
    @JvmName("fromJava") get() = when {
        this.isPresent -> Some(this.get())
        else -> None
    }

/**
 * Returns `true` if this has a value present, otherwise `false`.
 */
@JvmName("isSome")
fun <T> Option<T>.isPresent(): Boolean {
    contract {
        returns(true) implies (this@isPresent is Some<T>)
        returns(false) implies (this@isPresent is None)
    }
    return this.isPresent
}

/**
 * Returns `true` if this has no value present, otherwise `false`.
 */
@JvmName("isNone")
fun <T> Option<T>.isEmpty(): Boolean {
    contract {
        returns(true) implies (this@isEmpty is None)
        returns(false) implies (this@isEmpty is Some<T>)
    }
    return this.isEmpty
}

/**
 * Returns [some][Some] containing the specified [item] if `this` is `true`, or [none][None] if `this` is `false`.
 */
fun <T> Boolean.asSome(item: T): Option<T> = if (this) Some(item) else None

/**
 * Returns [some][Some] containing the specified [lazyItem] if `this` is `true`, or [none][None] if `this` is `false`.
 *
 * Note that this function *lazily* evaluates the [lazyItem], meaning that `item` will only ever be evaluated if `this`
 * is `true`, and never if `this` is `false`.
 *
 * Example:
 * ```kotlin
 *  val string: String = ...
 *  val split = ('#' in string).asSome { string.split('#')[1] }
 * ```
 * If using the `Boolean.asSome(item: T)` function instead, the above code would fail at runtime with a
 * [IndexOutOfBoundsException].
 */
inline fun <T> Boolean.asSome(lazyItem: () -> T): Option<T> = if (this) Some(lazyItem()) else None

inline fun <T> maybe(scope: () -> Maybe<T> = { None }): Maybe<T> = scope()

// -- ITERABLE -- \\
/**
 * Returns single element, or `None` if the collection is empty or has more than one element.
 */
fun <T> Iterable<T>.singleOrNone(): Option<T> = Option(singleOrNull())

/**
 * Returns the first element, or [None] if `this` collection is empty.
 */
fun <T> Iterable<T>.firstOrNone(): Option<T> = Option(firstOrNull())

/**
 * Returns the first element matching the specified [predicate], or [None] if none is found.
 */
inline fun <T> Iterable<T>.firstOrNone(predicate: (T) -> Boolean) = Option(firstOrNull(predicate))

/**
 * Returns the last element, or [None] if `this` collection is empty.
 */
fun <T> Iterable<T>.lastOrNone(): Option<T> = Option(lastOrNull())

/**
 * Returns the last element matching the specified [predicate], or [None] if none is found.
 */
inline fun <T> Iterable<T>.lastOrNone(predicate: (T) -> Boolean): Option<T> = Option(lastOrNull(predicate))

/**
 * Returns the element at the given [index], or [None] if the `index` is out of bounds of `this` collection.
 */
fun <T> Iterable<T>.elementAtOrNone(index: Int): Option<T> = Option(elementAtOrNull(index))

// -- SEQUENCE -- \\
/**
 * Returns single element, or `None` if the collection is empty or has more than one element.
 *
 * The operation is _terminal_.
 */
fun <T> Sequence<T>.singleOrNone(): Option<T> = Option(singleOrNull())

/**
 * Returns the first element, or [None] if `this` sequence is empty.
 *
 * The operation is _terminal_.
 */
fun <T> Sequence<T>.firstOrNone(): Option<T> = Option(firstOrNull())

/**
 * Returns the first element matching the specified [predicate], or [None] if none is found.
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.firstOrNone(predicate: (T) -> Boolean) = Option(firstOrNull(predicate))

/**
 * Returns the last element, or [None] if `this` sequence is empty.
 *
 * The operation is _terminal_.
 */
fun <T> Sequence<T>.lastOrNone(): Option<T> = Option(lastOrNull())

/**
 * Returns the last element matching the specified [predicate], or [None] if none is found.
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.lastOrNone(predicate: (T) -> Boolean): Option<T> = Option(lastOrNull(predicate))

/**
 * Returns the element at the given [index], or [None] if the `index` is out of bounds of `this` collection.
 *
 * The operation is _terminal_.
 */
fun <T> Sequence<T>.elementAtOrNone(index: Int): Option<T> = Option(elementAtOrNull(index))

// -- LIST -- \\
/**
 * Returns single element, or `None` if the collection is empty or has more than one element.
 */
fun <T> List<T>.singleOrNone(): Option<T> = Option(singleOrNull())

/**
 * Returns the element at the given [index], or [None] if the `index` is out of bounds of `this` list.
 */
fun <T> List<T>.getOrNone(index: Int): Option<T> = Option(getOrNull(index))

/**
 * Returns the first element, or [None] if `this` list is empty.
 */
fun <T> List<T>.firstOrNone(): Option<T> = Option(firstOrNull())

/**
 * Returns the last element, or [None] if `this` list is empty.
 */
fun <T> List<T>.lastOrNone(): Option<T> = Option(lastOrNull())

// -- MAP -- \\
/**
 * Returns the value stored under the given [key] in `this` map, or [None] if no value is stored under the given `key`.
 */
@Deprecated(
    "The name does not follow naming convention",
    ReplaceWith("getOrNone(key)", "moe.kanon.kommons.func"),
    DeprecationLevel.ERROR
)
fun <K, V> Map<K, V>.getValueOrNone(key: K): Option<V> = Option(this[key])

/**
 * Returns the value stored under the given [key] in `this` map, or [None] if no value is stored under the given `key`.
 */
fun <K, V> Map<K, V>.getOrNone(key: K): Option<V> = Option(this[key])

// -- TO... FUNCTIONS -- \\
/**
 * Parses `this` string as a signed [Byte] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 */
fun String.toByteOrNone(): Option<Byte> = Option(this.toByteOrNull())

/**
 * Parses `this` string as a signed [Byte] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 *
 * @throws IllegalArgumentException when [radix] is not a valid radix for string to number conversion.
 */
fun String.toByteOrNone(radix: Int): Option<Byte> = Option(this.toByteOrNull(radix))

/**
 * Parses `this` string as a signed [Short] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 */
fun String.toShortOrNone(): Option<Short> = Option(this.toShortOrNull())

/**
 * Parses `this` string as a signed [Short] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 *
 * @throws IllegalArgumentException when [radix] is not a valid radix for string to number conversion.
 */
fun String.toShortOrNone(radix: Int): Option<Short> = Option(this.toShortOrNull(radix))

/**
 * Parses `this` string as a signed [Int] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 */
fun String.toIntOrNone(): Option<Int> = Option(this.toIntOrNull())

/**
 * Parses `this` string as a signed [Int] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 *
 * @throws IllegalArgumentException when [radix] is not a valid radix for string to number conversion.
 */
fun String.toIntOrNone(radix: Int): Option<Int> = Option(this.toIntOrNull(radix))

/**
 * Parses `this` string as a signed [Long] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 */
fun String.toLongOrNone(): Option<Long> = Option(this.toLongOrNull())

/**
 * Parses `this` string as a signed [Long] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 *
 * @throws IllegalArgumentException when [radix] is not a valid radix for string to number conversion.
 */
fun String.toLongOrNone(radix: Int): Option<Long> = Option(this.toLongOrNull(radix))

/**
 * Parses `this` string as a signed [BigInteger] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 */
fun String.toBigIntegerOrNone(): Option<BigInteger> = Option(this.toBigIntegerOrNull())

/**
 * Parses `this` string as a signed [BigInteger] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 *
 * @throws IllegalArgumentException when [radix] is not a valid radix for string to number conversion.
 */
fun String.toBigIntegerOrNone(radix: Int): Option<BigInteger> = Option(this.toBigIntegerOrNull(radix))

/**
 * Parses `this` string as a signed [Float] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 */
fun String.toFloatOrNone(): Option<Float> = Option(this.toFloatOrNull())

/**
 * Parses `this` string as a signed [Double] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 */
fun String.toDoubleOrNone(): Option<Double> = Option(this.toDoubleOrNull())

/**
 * Parses `this` string as a signed [BigDecimal] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 */
fun String.toBigDecimal(): Option<BigDecimal> = Option(this.toBigDecimalOrNull())

/**
 * Parses `this` string as a signed [BigDecimal] number and returns the result or [None] if `this` string is not a valid
 * representation of a number.
 *
 * @param [context] specifies the precision and the rounding mode
 * @throws [ArithmeticException] if the rounding is needed, but the rounding mode is
 * [java.math.RoundingMode.UNNECESSARY]
 */
fun String.toBigDecimal(context: MathContext): Option<BigDecimal> = Option(this.toBigDecimalOrNull(context))