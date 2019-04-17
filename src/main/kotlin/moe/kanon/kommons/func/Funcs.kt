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
@file:JvmName("KFuncs")
@file:Suppress("NOTHING_TO_INLINE")

package moe.kanon.kommons.func

// Utility Functions
// - Consumer
/**
 * Returns a composed [Consumer] that performs, in sequence, `this` operation followed by the [after] operation.
 *
 * If performing either operation throws an exception, it is relayed to the caller of the composed operation. If
 * performing `this` operation throws an exception, the [after] operation will not be performed.
 *
 * @param [after] the operation to perform after `this` operation
 *
 * @since 0.6.0
 */
@JvmName("andThenConsumer")
inline infix fun <P1> Consumer<P1>.andThen(crossinline after: Consumer<in P1>): Consumer<P1> = {
    this(it)
    after(it)
}


// - BiConsumer
/**
 * Returns a composed [BiConsumer] that performs, in sequence, `this` operation followed by the [after] operation.
 *
 * If performing either operation throws an exception, it is relayed to the caller of the composed operation. If
 * performing `this` operation throws an exception, the [after] operation will not be performed.
 *
 * @param [after] the operation to perform after `this` operation
 *
 * @since 0.6.0
 */
@JvmName("andThenBiConsumer")
inline infix fun <P1, P2> BiConsumer<P1, P2>.andThen(crossinline after: BiConsumer<in P1, in P2>): BiConsumer<P1, P2> =
    { a, b ->
        this(a, b)
        after(a, b)
    }


// - TriConsumer
/**
 * Returns a composed [TriConsumer] that performs, in sequence, `this` operation followed by the [after] operation.
 *
 * If performing either operation throws an exception, it is relayed to the caller of the composed operation. If
 * performing `this` operation throws an exception, the [after] operation will not be performed.
 *
 * @param [after] the operation to perform after `this` operation
 *
 * @since 0.6.0
 */
@JvmName("andThenTriConsumer")
inline infix fun <P1, P2, P3> TriConsumer<P1, P2, P3>.andThen(
    crossinline after: TriConsumer<in P1, in P2, in P3>
): TriConsumer<P1, P2, P3> = { a, b, c ->
    this(a, b, c)
    after(a, b, c)
}


// - QuadConsumer
/**
 * Returns a composed [QuadConsumer] that performs, in sequence, `this` operation followed by the [after] operation.
 *
 * If performing either operation throws an exception, it is relayed to the caller of the composed operation. If
 * performing `this` operation throws an exception, the [after] operation will not be performed.
 *
 * @param [after] the operation to perform after `this` operation
 *
 * @since 0.6.0
 */
@JvmName("andThenQuadConsumer")
inline infix fun <P1, P2, P3, P4> QuadConsumer<P1, P2, P3, P4>.andThen(
    crossinline after: QuadConsumer<in P1, in P2, in P3, in P4>
): QuadConsumer<P1, P2, P3, P4> = { a, b, c, d ->
    this(a, b, c, d)
    after(a, b, c, d)
}


// - Function
/**
 * Returns a composed function that first applies the [before] function to its input, and then applies `this` function
 * to  the result.
 *
 *
 * If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
 *
 * @param [R2] the type of input to the [before] function, and to the composed function
 * @param [before] the function to apply before `this` function is applied
 *
 * @since 0.6.0
 */
@JvmName("composeFunction")
inline infix fun <P1, R1, R2> Func<P1, R1>.composeWith(
    crossinline before: Func<in R2, out P1>
): Func<R2, R1> = { this(before(it)) }

/**
 * Returns a composed function that first applies `this` function to its input, and then applies the [after] function
 * to the result.
 *
 * If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
 *
 * @param [P1] the type of the argument of `this` function
 * @param [R1] the return type of `this` function
 * @param [R2] the type of output of the [after] function, and of the composed function
 *
 * @param [after] the function to apply after `this` function is applied
 *
 * @since 0.6.0
 */
@JvmName("andThenFunction")
inline infix fun <P1, R1, R2> Func<P1, R1>.andThen(crossinline after: Func<in R1, out R2>): Func<P1, R2> =
    { after(this(it)) }


// - BiFunction
/**
 * Returns a composed function that first applies `this` function to its input, and then applies the [after] function
 * to the result.
 *
 * If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
 *
 * @param [P1] the type of the first argument of `this` function
 * @param [P2] the type of the second argument of `this` function
 * @param [R1] the return type of `this` function
 * @param [R2] the type of output of the [after] function, and of the composed function
 *
 * @param [after] the function to apply after `this` function is applied
 *
 * @since 0.6.0
 */
@JvmName("andThenBiFunction")
inline infix fun <P1, P2, R1, R2> BiFunc<P1, P2, R1>.andThen(
    crossinline after: Func<in R1, out R2>
): BiFunc<P1, P2, R2> = { a, b -> after(this(a, b)) }


// - TriFunction
/**
 * Returns a composed function that first applies `this` function to its input, and then applies the [after] function
 * to the result.
 *
 * If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
 *
 * @param [P1] the type of the first argument of `this` function
 * @param [P2] the type of the second argument of `this` function
 * @param [P3] the type of the third argument of `this` function
 * @param [R1] the return type of `this` function
 * @param [R2] the type of output of the [after] function, and of the composed function
 *
 * @param [after] the function to apply after `this` function is applied
 *
 * @since 0.6.0
 */
@JvmName("andThenTriFunction")
inline infix fun <P1, P2, P3, R1, R2> TriFunc<P1, P2, P3, R1>.andThen(
    crossinline after: Func<in R1, out R2>
): TriFunc<P1, P2, P3, R2> = { a, b, c -> after(this(a, b, c)) }


// - QuadFunction
/**
 * Returns a composed function that first applies `this` function to its input, and then applies the [after] function
 * to the result.
 *
 * If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
 *
 * @param [P1] the type of the first argument of `this` function
 * @param [P2] the type of the second argument of `this` function
 * @param [P3] the type of the third argument of `this` function
 * @param [P4] the type of the fourth argument of `this` function
 * @param [R1] the return type of `this` function
 * @param [R2] the type of output of the [after] function, and of the composed function
 *
 * @param [after] the function to apply after `this` function is applied
 *
 * @since 0.6.0
 */
@JvmName("andThenTriFunction")
inline infix fun <P1, P2, P3, P4, R1, R2> QuadFunc<P1, P2, P3, P4, R1>.andThen(
    crossinline after: Func<in R1, out R2>
): QuadFunc<P1, P2, P3, P4, R2> = { a, b, c, d -> after(this(a, b, c, d)) }


// - Predicate
/**
 * Returns a composed predicate that represents a short-circuiting logical `AND` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `false`, then the `other` predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the `other` predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ANDed with `this` predicate
 *
 * @since 0.6.0
 */
@JvmName("andPredicate")
inline infix fun <P1> Predicate<P1>.and(crossinline other: Predicate<in P1>): Predicate<P1> = { this(it) && other(it) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 *
 * @since 0.6.0
 */
@JvmName("negatePredicate")
inline fun <P1> Predicate<P1>.negate(): Predicate<P1> = { !this(it) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 *
 * @since 0.6.0
 */
@JvmName("notPredicate")
inline operator fun <P1> Predicate<P1>.not(): Predicate<P1> = negate()

/**
 * Returns a composed predicate that represents a short-circuiting logical `OR` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `true`, then the [other] predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the [other] predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ORed with this predicate
 *
 * @since 0.6.0
 */
@JvmName("orPredicate")
inline infix fun <P1> Predicate<P1>.or(crossinline other: Predicate<in P1>): Predicate<P1> = { this(it) || other(it) }

/**
 * Returns a predicate that tests if `this` is equal to [P1].
 *
 * @receiver the object reference with which to compare for equality, which may be `null`
 *
 * @param [P1] the type of argument to the predicate
 *
 * @since 0.6.0
 */
inline fun <P1> Any?.isEqual(): Predicate<P1> = { this ?: this == it }


// - BiPredicate
/**
 * Returns a composed predicate that represents a short-circuiting logical `AND` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `false`, then the `other` predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the `other` predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ANDed with `this` predicate
 *
 * @since 0.6.0
 */
@JvmName("andBiPredicate")
inline infix fun <P1, P2> BiPredicate<P1, P2>.and(crossinline other: BiPredicate<in P1, in P2>): BiPredicate<P1, P2> =
    { a, b -> this(a, b) && other(a, b) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 *
 * @since 0.6.0
 */
@JvmName("negateBiPredicate")
inline fun <P1, P2> BiPredicate<P1, P2>.negate(): BiPredicate<P1, P2> = { a, b -> !this(a, b) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 *
 * @since 0.6.0
 */
@JvmName("notBiPredicate")
inline operator fun <P1, P2> BiPredicate<P1, P2>.not(): BiPredicate<P1, P2> = negate()

/**
 * Returns a composed predicate that represents a short-circuiting logical `OR` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `true`, then the [other] predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the [other] predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ORed with this predicate
 *
 * @since 0.6.0
 */
@JvmName("orBiPredicate")
inline infix fun <P1, P2> BiPredicate<P1, P2>.or(crossinline other: BiPredicate<in P1, in P2>): BiPredicate<P1, P2> =
    { a, b -> this(a, b) || other(a, b) }


// - TriPredicate
/**
 * Returns a composed predicate that represents a short-circuiting logical `AND` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `false`, then the `other` predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the `other` predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ANDed with `this` predicate
 *
 * @since 0.6.0
 */
@JvmName("andTriPredicate")
inline infix fun <P1, P2, P3> TriPredicate<P1, P2, P3>.and(
    crossinline other: TriPredicate<in P1, in P2, in P3>
): TriPredicate<P1, P2, P3> = { a, b, c -> this(a, b, c) && other(a, b, c) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 *
 * @since 0.6.0
 */
@JvmName("negateTriPredicate")
inline fun <P1, P2, P3> TriPredicate<P1, P2, P3>.negate(): TriPredicate<P1, P2, P3> = { a, b, c -> !this(a, b, c) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 *
 * @since 0.6.0
 */
@JvmName("notTriPredicate")
inline operator fun <P1, P2, P3> TriPredicate<P1, P2, P3>.not(): TriPredicate<P1, P2, P3> = negate()

/**
 * Returns a composed predicate that represents a short-circuiting logical `OR` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `true`, then the [other] predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the [other] predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ORed with this predicate
 *
 * @since 0.6.0
 */
@JvmName("orTriPredicate")
inline infix fun <P1, P2, P3> TriPredicate<P1, P2, P3>.or(
    crossinline other: TriPredicate<in P1, in P2, in P3>
): TriPredicate<P1, P2, P3> = { a, b, c -> this(a, b, c) || other(a, b, c) }


// - QuadPredicate
/**
 * Returns a composed predicate that represents a short-circuiting logical `AND` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `false`, then the `other` predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the `other` predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ANDed with `this` predicate
 *
 * @since 0.6.0
 */
@JvmName("andQuadPredicate")
inline infix fun <P1, P2, P3, P4> QuadPredicate<P1, P2, P3, P4>.and(
    crossinline other: QuadPredicate<in P1, in P2, in P3, in P4>
): QuadPredicate<P1, P2, P3, P4> = { a, b, c, d -> this(a, b, c, d) && other(a, b, c, d) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 *
 * @since 0.6.0
 */
@JvmName("negateQuadPredicate")
inline fun <P1, P2, P3, P4> QuadPredicate<P1, P2, P3, P4>.negate(): QuadPredicate<P1, P2, P3, P4> =
    { a, b, c, d -> !this(a, b, c, d) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 *
 * @since 0.6.0
 */
@JvmName("notQuadPredicate")
inline operator fun <P1, P2, P3, P4> QuadPredicate<P1, P2, P3, P4>.not(): QuadPredicate<P1, P2, P3, P4> = negate()

/**
 * Returns a composed predicate that represents a short-circuiting logical `OR` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `true`, then the [other] predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the [other] predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ORed with this predicate
 *
 * @since 0.6.0
 */
@JvmName("orQuadPredicate")
inline infix fun <P1, P2, P3, P4> QuadPredicate<P1, P2, P3, P4>.or(
    crossinline other: QuadPredicate<in P1, in P2, in P3, in P4>
): QuadPredicate<P1, P2, P3, P4> = { a, b, c, d -> this(a, b, c, d) || other(a, b, c, d) }


// - UnaryOperator
/**
 * Returns a function that always returns its input argument.
 *
 * @param [T] the type of the input and output objects to the function
 *
 * @since 0.6.0
 */
inline fun <T : Any> T.unary(): UnaryOperator<T> = { it }


// - BinaryOperator
/**
 * Returns a [BinaryOperator] which returns the lesser of two elements according to the specified [comparator].
 *
 * @param [T] the type of the input arguments of the comparator
 * @param [comparator] a [Comparator] for comparing the two values
 *
 * @since 0.6.0
 */
inline infix fun <T> BinaryOperator<T>.minBy(comparator: Comparator<T>): BinaryOperator<T> =
    { a, b -> if (comparator.compare(a, b) <= 0) a else b }

/**
 * Returns a [BinaryOperator] which returns the greater of two elements according to the specified [comparator].
 *
 * @param [T] the type of the input arguments of the comparator
 * @param [comparator] a [Comparator] for comparing the two values
 *
 * @since 0.6.0
 */
inline infix fun <T> BinaryOperator<T>.maxBy(comparator: Comparator<T>): BinaryOperator<T> =
    { a, b -> if (comparator.compare(a, b) >= 0) a else b }

/**
 * Returns a [BinaryOperator] which returns the lesser of two [Comparable] elements according to their natural values.
 *
 * @param [T] the type of the input arguments of the comparator
 *
 * @since 0.6.0
 */
@JvmName("minByComparable")
inline fun <T : Comparable<T>> BinaryOperator<T>.minBy(): BinaryOperator<T> =
    { a, b -> if (a <= b) a else b }

/**
 * Returns a [BinaryOperator] which returns the greater of two [Comparable] elements according to their natural values.
 *
 * @param [T] the type of the input arguments of the comparator
 *
 * @since 0.6.0
 */
@JvmName("maxByComparable")
inline fun <T : Comparable<T>> BinaryOperator<T>.maxBy(): BinaryOperator<T> =
    { a, b -> if (a >= b) a else b }

/**
 * Converts `this` pair into a [BinaryOperator].
 *
 * The first parameter of [closure] will be the [Pair.first] value, and the second parameter of `closure` will be the
 * [Pair.second] value of `this` pair.
 */
inline infix fun <T> Pair<T, T>.toBinaryOperator(crossinline closure: BinaryOperator<T>): BinaryOperator<T> =
    closure.apply { invoke(this@toBinaryOperator.first, this@toBinaryOperator.second) }

// Misc
/**
 * Returns [item]
 */
inline fun <T> identity(item: T): T = item

/**
 * Returns `this`
 */
inline fun <T> T.itself(): T = this

/**
 * Returns a [Supplier] that returns `this`.
 */
fun <T> T.toSupplier(): Supplier<out T> = { this }