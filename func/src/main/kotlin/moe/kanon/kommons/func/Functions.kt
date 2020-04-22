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

@file:JvmName("KFuncs")

package moe.kanon.kommons.func

// -- TYPE-ALIASES -- \\
// -- CONSUMERS -- \\
/**
 * Represents an operation that accepts a single input argument and returns no result.
 *
 * Unlike most other functions, `Consumer` is expected to operate via side-effects.
 *
 * @param [T] the type of the input to the operation
 *
 * @see java.util.function.Consumer
 */
typealias Consumer<T> = (T) -> Unit

/**
 * Represents an operation that accepts two input arguments and returns no result.
 *
 * This is the two-arity specialization of [Consumer].
 *
 * Unlike most other functions, `BiConsumer` is expected to operate via side-effects.
 *
 * @param [A] the type of the first argument to the operation
 * @param [B] the type of the second argument to the operation
 *
 * @see Consumer
 * @see java.util.function.BiConsumer
 */
typealias BiConsumer<A, B> = (a: A, b: B) -> Unit

/**
 * Represents an operation that accepts three input arguments and returns no result.
 *
 * This is the three-arity specialization of [BiConsumer].
 *
 * Unlike most other functions, `TriConsumer` is expected to operate via side-effects.
 *
 * @param [A] the type of the first argument to the operation
 * @param [B] the type of the second argument to the operation
 * @param [C] the type of the third argument to the operation
 *
 * @see BiConsumer
 */
typealias TriConsumer<A, B, C> = (a: A, b: B, c: C) -> Unit

/**
 * Represents an operation that accepts four input arguments and returns no result.
 *
 * This is the four-arity specialization of [BiConsumer].
 *
 * Unlike most other functions, `TriConsumer` is expected to operate via side-effects.
 *
 * @param [A] the type of the first argument to the operation
 * @param [B] the type of the second argument to the operation
 * @param [C] the type of the third argument to the operation
 * @param [D] the type of the fourth argument to the operation
 *
 * @see TriConsumer
 */
typealias QuadConsumer<A, B, C, D> = (a: A, b: B, c: C, d: D) -> Unit

// -- FUNCS -- \\
/**
 * Represents a function that accepts one argument and produces a result.
 *
 * @param [T] the type of the input to the function
 * @param [R] the type of the result of the function
 *
 * @see java.util.function.Function
 */
typealias Func<T, R> = (T) -> R

/**
 * Represents a function that accepts two arguments and produces a result.
 *
 * This is the two-arity specialization of [Func].
 *
 * @param [A] the type of the first argument to the function
 * @param [B] the type of the second argument to the function
 * @param [R] the type of the result of the function
 *
 * @see Func
 * @see java.util.function.BiFunction
 */
typealias BiFunc<A, B, R> = (a: A, b: B) -> R

/**
 * Represents a function that accepts three arguments and produces a result.
 *
 * This is the three-arity specialization of [Func].
 *
 * @param [A] the type of the first argument to the function
 * @param [B] the type of the second argument to the function
 * @param [C] the type of the third argument to the function
 * @param [R] the type of the result of the function
 *
 * @see BiFunc
 */
typealias TriFunc<A, B, C, R> = (a: A, b: B, c: C) -> R

/**
 * Represents a function that accepts four arguments and produces a result.
 *
 * This is the four-arity specialization of [Func].
 *
 * @param [A] the type of the first argument to the function
 * @param [B] the type of the second argument to the function
 * @param [C] the type of the third argument to the function
 * @param [D] the type of the fourth argument to the function
 * @param [R] the type of the result of the function
 *
 * @see TriFunc
 */
typealias QuadFunc<A, B, C, D, R> = (a: A, b: B, c: C, d: D) -> R

// -- SUPPLIERS -- \\
/**
 * Represents a supplier of a result.
 *
 * There is no requirement that a new or distinct result be returned each time the supplier is invoked.
 *
 * @param [T] the type of results supplied by `this` supplier
 *
 * @see java.util.function.Supplier
 */
typealias Supplier<T> = () -> T

/**
 * Represents a supplier of a two results.
 *
 * This is the two-arity specialization of [Supplier].
 *
 * There is no requirement that a new or distinct result be returned each time the supplier is invoked.
 *
 * @param [A] the type of the first result supplied by `this` supplier
 * @param [B] the type of the second result supplied by `this` supplier
 */
typealias BiSupplier<A, B> = () -> Pair<A, B>

/**
 * Represents a supplier of a three results.
 *
 * This is the three-arity specialization of [Supplier].
 *
 * There is no requirement that a new or distinct result be returned each time the supplier is invoked.
 *
 * @param [A] the type of the first result supplied by `this` supplier
 * @param [B] the type of the second result supplied by `this` supplier
 * @param [C] the type of the third result supplied by `this` supplier
 */
typealias TriSupplier<A, B, C> = () -> Triple<A, B, C>

// -- PREDICATES -- \\
/**
 * Represents a predicate *([boolean][Boolean]-valued [function][Func])* of one argument.
 *
 * @param [T] the type of the input to the predicate
 *
 * @see java.util.function.Predicate
 */
typealias Predicate<T> = (T) -> Boolean

/**
 * Represents a predicate *([boolean][Boolean]-valued [function][Func])* of two arguments.
 *
 * This is the two-arity specialization of [Predicate].
 *
 * @param [A] the type of the first argument to the predicate
 * @param [B] the type of the second argument to the predicate
 *
 * @see Predicate
 * @see java.util.function.Predicate
 */
typealias BiPredicate<A, B> = (a: A, b: B) -> Boolean

/**
 * Represents a predicate *([boolean][Boolean]-valued [function][Func])* of three arguments.
 *
 * This is the three-arity specialization of [Predicate].
 *
 * @param [A] the type of the first argument to the predicate
 * @param [B] the type of the second argument to the predicate
 * @param [C] the type of the third argument to the predicate
 *
 * @see BiPredicate
 */
typealias TriPredicate<A, B, C> = (a: A, b: B, c: C) -> Boolean

/**
 * Represents a predicate *([boolean][Boolean]-valued [function][Func])* of four arguments.
 *
 * This is the four-arity specialization of [Predicate].
 *
 * @param [A] the type of the first argument to the predicate
 * @param [B] the type of the second argument to the predicate
 * @param [C] the type of the third argument to the predicate
 * @param [D] the type of the fourth argument to the predicate
 *
 * @see TriPredicate
 */
typealias QuadPredicate<A, B, C, D> = (a: A, b: B, c: C, d: D) -> Boolean

// -- OPERATORS -- \\
/**
 * Represents an operation on a single operand that produces a result of the same type as its operand.
 *
 * This is a specialization of [Func] for the case where the operand and result are of the same type.
 *
 * @param [T] the type of the operand and result of the operator
 *
 * @see Function
 * @see BinaryOperator
 * @see java.util.function.UnaryOperator
 */
typealias UnaryOperator<T> = Func<T, T>

/**
 * Represents an operation upon two operands of the same type, producing a result of the same type as the operands.
 *
 * This is a specialization of [BiFunc] for the case where the operands and the result are all of the same type.
 *
 * @param [T] the type of the operands and result of the operator
 *
 * @see BiFunc
 * @see UnaryOperator
 * @see java.util.function.BinaryOperator
 */
typealias BinaryOperator<T> = BiFunc<T, T, T>

// -- FUNCTIONS -- \\
/**
 * Returns a predicate function which returns `true` if *all* of the specified [predicates] return `true`, otherwise
 * it returns `false`.
 */
fun <T> allOf(vararg predicates: (T) -> Boolean): (T) -> Boolean = { item -> predicates.all { it(item) } }

/**
 * Returns a predicate function which returns `true` if *all* of the predicates in `this` collection return `true`,
 * otherwise it returns `false`.
 */
fun <T> Iterable<(T) -> Boolean>.allOf(): (T) -> Boolean = { item -> this.all { it(item) } }

/**
 * Returns a predicate function which returns `true` if *any* of the specified [predicates] return `true`, otherwise
 * it returns `false`.
 */
fun <T> anyOf(vararg predicates: (T) -> Boolean): (T) -> Boolean = { item -> predicates.any { it(item) } }

/**
 * Returns a predicate function which returns `true` if *any* of the predicates in `this` collection return `true`,
 * otherwise it returns `false`.
 */
fun <T> Iterable<(T) -> Boolean>.anyOf(): (T) -> Boolean = { item -> this.any { it(item) } }

/**
 * Returns a predicate function which returns `true` if *none* of the specified [predicates] return `true`, otherwise
 * it returns `false`.
 */
fun <T> noneOf(vararg predicates: (T) -> Boolean): (T) -> Boolean = { item -> predicates.none { it(item) } }

/**
 * Returns a predicate function which returns `true` if *none* of the predicates in `this` collection return `true`,
 * otherwise it returns `false`.
 */
fun <T> Iterable<(T) -> Boolean>.noneOf(): (T) -> Boolean = { item -> this.none { it(item) } }

/**
 * Returns a function that always returns itself.
 */
fun <T> identity(): UnaryOperator<T> = { it }

// -- CONSUMERS -- \\
/**
 * Returns a composed [Consumer] that performs, in sequence, `this` operation followed by the [after] operation.
 *
 * If performing either operation throws an exception, it is relayed to the caller of the composed operation. If
 * performing `this` operation throws an exception, the [after] operation will not be performed.
 *
 * @param [after] the operation to perform after `this` operation
 */
@JvmName("andThenConsumer")
inline infix fun <T> Consumer<T>.andThen(crossinline after: Consumer<in T>): Consumer<T> = {
    this(it)
    after(it)
}

/**
 * Returns a composed [BiConsumer] that performs, in sequence, `this` operation followed by the [after] operation.
 *
 * If performing either operation throws an exception, it is relayed to the caller of the composed operation. If
 * performing `this` operation throws an exception, the [after] operation will not be performed.
 *
 * @param [after] the operation to perform after `this` operation
 */
@JvmName("andThenBiConsumer")
inline infix fun <A, B> BiConsumer<A, B>.andThen(crossinline after: BiConsumer<in A, in B>): BiConsumer<A, B> =
    { a, b ->
        this(a, b)
        after(a, b)
    }

/**
 * Returns a composed [TriConsumer] that performs, in sequence, `this` operation followed by the [after] operation.
 *
 * If performing either operation throws an exception, it is relayed to the caller of the composed operation. If
 * performing `this` operation throws an exception, the [after] operation will not be performed.
 *
 * @param [after] the operation to perform after `this` operation
 */
@JvmName("andThenTriConsumer")
inline infix fun <A, B, C> TriConsumer<A, B, C>.andThen(
    crossinline after: TriConsumer<in A, in B, in C>
): TriConsumer<A, B, C> = { a, b, c ->
    this(a, b, c)
    after(a, b, c)
}

/**
 * Returns a composed [QuadConsumer] that performs, in sequence, `this` operation followed by the [after] operation.
 *
 * If performing either operation throws an exception, it is relayed to the caller of the composed operation. If
 * performing `this` operation throws an exception, the [after] operation will not be performed.
 *
 * @param [after] the operation to perform after `this` operation
 */
@JvmName("andThenQuadConsumer")
inline infix fun <A, B, C, D> QuadConsumer<A, B, C, D>.andThen(
    crossinline after: QuadConsumer<in A, in B, in C, in D>
): QuadConsumer<A, B, C, D> = { a, b, c, d ->
    this(a, b, c, d)
    after(a, b, c, d)
}

// -- FUNCS -- \\
/**
 * Returns a composed function that first applies the [before] function to its input, and then applies `this` function
 * to  the result.
 *
 *
 * If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
 *
 * @param [R2] the type of input to the [before] function, and to the composed function
 * @param [before] the function to apply before `this` function is applied
 */
@JvmName("composeFunction")
inline infix fun <T, R1, R2> Func<T, R1>.composeWith(
    crossinline before: Func<in R2, out T>
): Func<R2, R1> = { this(before(it)) }

/**
 * Returns a composed function that first applies `this` function to its input, and then applies the [after] function
 * to the result.
 *
 * If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
 *
 * @param [T] the type of the argument of `this` function
 * @param [R1] the return type of `this` function
 * @param [R2] the type of output of the [after] function, and of the composed function
 *
 * @param [after] the function to apply after `this` function is applied
 */
@JvmName("andThenFunction")
inline infix fun <T, R1, R2> Func<T, R1>.andThen(crossinline after: Func<in R1, out R2>): Func<T, R2> =
    { after(this(it)) }

/**
 * Returns a composed function that first applies `this` function to its input, and then applies the [after] function
 * to the result.
 *
 * If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
 *
 * @param [A] the type of the first argument of `this` function
 * @param [B] the type of the second argument of `this` function
 * @param [R1] the return type of `this` function
 * @param [R2] the type of output of the [after] function, and of the composed function
 *
 * @param [after] the function to apply after `this` function is applied
 */
@JvmName("andThenBiFunction")
inline infix fun <A, B, R1, R2> BiFunc<A, B, R1>.andThen(
    crossinline after: Func<in R1, out R2>
): BiFunc<A, B, R2> = { a, b -> after(this(a, b)) }

/**
 * Returns a composed function that first applies `this` function to its input, and then applies the [after] function
 * to the result.
 *
 * If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
 *
 * @param [A] the type of the first argument of `this` function
 * @param [B] the type of the second argument of `this` function
 * @param [C] the type of the third argument of `this` function
 * @param [R1] the return type of `this` function
 * @param [R2] the type of output of the [after] function, and of the composed function
 *
 * @param [after] the function to apply after `this` function is applied
 */
@JvmName("andThenTriFunction")
inline infix fun <A, B, C, R1, R2> TriFunc<A, B, C, R1>.andThen(
    crossinline after: Func<in R1, out R2>
): TriFunc<A, B, C, R2> = { a, b, c -> after(this(a, b, c)) }

/**
 * Returns a composed function that first applies `this` function to its input, and then applies the [after] function
 * to the result.
 *
 * If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
 *
 * @param [A] the type of the first argument of `this` function
 * @param [B] the type of the second argument of `this` function
 * @param [C] the type of the third argument of `this` function
 * @param [D] the type of the fourth argument of `this` function
 * @param [R1] the return type of `this` function
 * @param [R2] the type of output of the [after] function, and of the composed function
 *
 * @param [after] the function to apply after `this` function is applied
 */
@JvmName("andThenTriFunction")
inline infix fun <A, B, C, D, R1, R2> QuadFunc<A, B, C, D, R1>.andThen(
    crossinline after: Func<in R1, out R2>
): QuadFunc<A, B, C, D, R2> = { a, b, c, d -> after(this(a, b, c, d)) }

// -- PREDICATES -- \\
/**
 * Returns a composed predicate that represents a short-circuiting logical `AND` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `false`, then the `other` predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the `other` predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ANDed with `this` predicate
 */
@JvmName("andPredicate")
inline infix fun <T> Predicate<T>.and(crossinline other: Predicate<in T>): Predicate<T> = { this(it) && other(it) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 */
@JvmName("negatePredicate")
fun <T> Predicate<T>.negate(): Predicate<T> = { !this(it) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 */
@JvmName("notPredicate")
operator fun <T> Predicate<T>.not(): Predicate<T> = negate()

/**
 * Returns a composed predicate that represents a short-circuiting logical `OR` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `true`, then the [other] predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the [other] predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ORed with this predicate
 */
@JvmName("orPredicate")
inline infix fun <T> Predicate<T>.or(crossinline other: Predicate<in T>): Predicate<T> = { this(it) || other(it) }

/**
 * Returns a predicate that tests if `this` is equal to [T].
 *
 * @receiver the object reference with which to compare for equality, which may be `null`
 *
 * @param [T] the type of argument to the predicate
 */
fun <T> Any?.isEqual(): Predicate<T> = { this ?: this == it }

/**
 * Returns a composed predicate that represents a short-circuiting logical `AND` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `false`, then the `other` predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the `other` predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ANDed with `this` predicate
 */
@JvmName("andBiPredicate")
inline infix fun <A, B> BiPredicate<A, B>.and(crossinline other: BiPredicate<in A, in B>): BiPredicate<A, B> =
    { a, b -> this(a, b) && other(a, b) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 */
@JvmName("negateBiPredicate")
fun <A, B> BiPredicate<A, B>.negate(): BiPredicate<A, B> = { a, b -> !this(a, b) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 */
@JvmName("notBiPredicate")
operator fun <A, B> BiPredicate<A, B>.not(): BiPredicate<A, B> = negate()

/**
 * Returns a composed predicate that represents a short-circuiting logical `OR` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `true`, then the [other] predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the [other] predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ORed with this predicate
 */
@JvmName("orBiPredicate")
inline infix fun <A, B> BiPredicate<A, B>.or(crossinline other: BiPredicate<in A, in B>): BiPredicate<A, B> =
    { a, b -> this(a, b) || other(a, b) }

/**
 * Returns a composed predicate that represents a short-circuiting logical `AND` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `false`, then the `other` predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the `other` predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ANDed with `this` predicate
 */
@JvmName("andTriPredicate")
inline infix fun <A, B, C> TriPredicate<A, B, C>.and(
    crossinline other: TriPredicate<in A, in B, in C>
): TriPredicate<A, B, C> = { a, b, c -> this(a, b, c) && other(a, b, c) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 */
@JvmName("negateTriPredicate")
fun <A, B, C> TriPredicate<A, B, C>.negate(): TriPredicate<A, B, C> = { a, b, c -> !this(a, b, c) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 */
@JvmName("notTriPredicate")
operator fun <A, B, C> TriPredicate<A, B, C>.not(): TriPredicate<A, B, C> = negate()

/**
 * Returns a composed predicate that represents a short-circuiting logical `OR` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `true`, then the [other] predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the [other] predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ORed with this predicate
 */
@JvmName("orTriPredicate")
inline infix fun <A, B, C> TriPredicate<A, B, C>.or(
    crossinline other: TriPredicate<in A, in B, in C>
): TriPredicate<A, B, C> = { a, b, c -> this(a, b, c) || other(a, b, c) }

/**
 * Returns a composed predicate that represents a short-circuiting logical `AND` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `false`, then the `other` predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the `other` predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ANDed with `this` predicate
 */
@JvmName("andQuadPredicate")
inline infix fun <A, B, C, D> QuadPredicate<A, B, C, D>.and(
    crossinline other: QuadPredicate<in A, in B, in C, in D>
): QuadPredicate<A, B, C, D> = { a, b, c, d -> this(a, b, c, d) && other(a, b, c, d) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 */
@JvmName("negateQuadPredicate")
fun <A, B, C, D> QuadPredicate<A, B, C, D>.negate(): QuadPredicate<A, B, C, D> =
    { a, b, c, d -> !this(a, b, c, d) }

/**
 * Returns a predicate that represents the logical negation of `this` predicate.
 */
@JvmName("notQuadPredicate")
operator fun <A, B, C, D> QuadPredicate<A, B, C, D>.not(): QuadPredicate<A, B, C, D> = negate()

/**
 * Returns a composed predicate that represents a short-circuiting logical `OR` of `this` predicate and [other].
 *
 * When evaluating the composed predicate, if `this` predicate is `true`, then the [other] predicate is not evaluated.
 *
 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of `this`
 * predicate throws an exception, the [other] predicate will not be evaluated.
 *
 * @param [other] a predicate that will be logically-ORed with this predicate
 */
@JvmName("orQuadPredicate")
inline infix fun <A, B, C, D> QuadPredicate<A, B, C, D>.or(
    crossinline other: QuadPredicate<in A, in B, in C, in D>
): QuadPredicate<A, B, C, D> = { a, b, c, d -> this(a, b, c, d) || other(a, b, c, d) }

// -- OPERATORS -- \\
/**
 * Returns a function that always returns its input argument.
 *
 * @param [T] the type of the input and output objects to the function
 */
fun <T : Any> unary(): UnaryOperator<T> = { it }

/**
 * Returns a [BinaryOperator] which returns the lesser of two elements according to the specified [comparator].
 *
 * @param [T] the type of the input arguments of the comparator
 * @param [comparator] a [Comparator] for comparing the two values
 */
infix fun <T> BinaryOperator<T>.minBy(comparator: Comparator<T>): BinaryOperator<T> =
    { a, b -> if (comparator.compare(a, b) <= 0) a else b }

/**
 * Returns a [BinaryOperator] which returns the greater of two elements according to the specified [comparator].
 *
 * @param [T] the type of the input arguments of the comparator
 * @param [comparator] a [Comparator] for comparing the two values
 */
infix fun <T> BinaryOperator<T>.maxBy(comparator: Comparator<T>): BinaryOperator<T> =
    { a, b -> if (comparator.compare(a, b) >= 0) a else b }

/**
 * Returns a [BinaryOperator] which returns the lesser of two [Comparable] elements according to their natural values.
 *
 * @param [T] the type of the input arguments of the comparator
 */
@JvmName("minByComparable")
fun <T : Comparable<T>> minBy(): BinaryOperator<T> = { a, b -> if (a <= b) a else b }

/**
 * Returns a [BinaryOperator] which returns the greater of two [Comparable] elements according to their natural values.
 *
 * @param [T] the type of the input arguments of the comparator
 */
@JvmName("maxByComparable")
fun <T : Comparable<T>> maxBy(): BinaryOperator<T> = { a, b -> if (a >= b) a else b }

/**
 * Converts `this` pair into a [BinaryOperator].
 *
 * The first parameter of [closure] will be the [Pair.first] value, and the second parameter of `closure` will be the
 * [Pair.second] value of `this` pair.
 */
inline infix fun <T> Pair<T, T>.toBinaryOperator(crossinline closure: BinaryOperator<T>): BinaryOperator<T> =
    closure.apply { this(this@toBinaryOperator.first, this@toBinaryOperator.second) }
