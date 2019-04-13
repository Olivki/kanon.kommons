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

package moe.kanon.kommons.func

import java.util.function.Consumer
import java.util.function.UnaryOperator

typealias Executor = () -> Unit

// type-aliases to mimic the Java SAM interfaces.

// Consumers
// - single
/**
 * Represents an operation that accepts a single input argument and returns no result.
 *
 * Unlike most other functions, `Consumer` is expected to operate via side-effects.
 *
 * @param [P1] the type of the input to the operation
 *
 * @since 0.6.0
 *
 * @see java.util.function.Consumer
 */
typealias Consumer<P1> = (P1) -> Unit

/**
 * Type-alias for [java.util.function.Consumer].
 */
typealias JConsumer<P1> = java.util.function.Consumer<P1>

// - double
/**
 * Represents an operation that accepts two input arguments and returns no result.
 *
 * This is the two-arity specialization of [Consumer].
 *
 * Unlike most other functions, `BiConsumer` is expected to operate via side-effects.
 *
 * @param [P1] the type of the first argument to the operation
 * @param [P2] the type of the second argument to the operation
 *
 * @since 0.6.0
 *
 * @see Consumer
 * @see java.util.function.BiConsumer
 */
typealias BiConsumer<P1, P2> = (@ParameterName("a") P1, @ParameterName("b") P2) -> Unit

/**
 * Type-alias for [java.util.function.BiConsumer].
 */
typealias JBiConsumer<P1, P2> = java.util.function.BiConsumer<P1, P2>

// - triple
/**
 * Represents an operation that accepts three input arguments and returns no result.
 *
 * This is the three-arity specialization of [BiConsumer].
 *
 * Unlike most other functions, `TriConsumer` is expected to operate via side-effects.
 *
 * @param [P1] the type of the first argument to the operation
 * @param [P2] the type of the second argument to the operation
 * @param [P3] the type of the third argument to the operation
 *
 * @since 0.6.0
 *
 * @see BiConsumer
 */
typealias TriConsumer<P1, P2, P3> = (@ParameterName("a") P1, @ParameterName("b") P2, @ParameterName("c") P3) -> Unit

// - quadruple
/**
 * Represents an operation that accepts four input arguments and returns no result.
 *
 * This is the four-arity specialization of [BiConsumer].
 *
 * Unlike most other functions, `TriConsumer` is expected to operate via side-effects.
 *
 * @param [P1] the type of the first argument to the operation
 * @param [P2] the type of the second argument to the operation
 * @param [P3] the type of the third argument to the operation
 * @param [P4] the type of the fourth argument to the operation
 *
 * @since 0.6.0
 *
 * @see TriConsumer
 */
typealias QuadConsumer<P1, P2, P3, P4> = (
    @ParameterName("a") P1,
    @ParameterName("b") P2,
    @ParameterName("c") P3,
    @ParameterName("d") P4
) -> Unit

// Functions
// - single
/**
 * Represents a function that accepts one argument and produces a result.
 *
 * @param [P1] the type of the input to the function
 * @param [R] the type of the result of the function
 *
 * @since 0.6.0
 *
 * @see java.util.function.Function
 */
typealias Func<P1, R> = (P1) -> R

// - double
/**
 * Represents a function that accepts two arguments and produces a result.
 *
 * This is the two-arity specialization of [Func].
 *
 * @param [P1] the type of the first argument to the function
 * @param [P2] the type of the second argument to the function
 * @param [R] the type of the result of the function
 *
 * @see Func
 * @see java.util.function.BiFunction
 *
 * @since 0.6.0
 */
typealias BiFunc<P1, P2, R> = (@ParameterName("a") P1, @ParameterName("b") P2) -> R

// - triple
/**
 * Represents a function that accepts three arguments and produces a result.
 *
 * This is the three-arity specialization of [Func].
 *
 * @param [P1] the type of the first argument to the function
 * @param [P2] the type of the second argument to the function
 * @param [P3] the type of the third argument to the function
 * @param [R] the type of the result of the function
 *
 * @see BiFunc
 *
 * @since 0.6.0
 */
typealias TriFunc<P1, P2, P3, R> = (@ParameterName("a") P1, @ParameterName("b") P2, @ParameterName("c") P3) -> R

// - quadruple
/**
 * Represents a function that accepts four arguments and produces a result.
 *
 * This is the four-arity specialization of [Func].
 *
 * @param [P1] the type of the first argument to the function
 * @param [P2] the type of the second argument to the function
 * @param [P3] the type of the third argument to the function
 * @param [P4] the type of the fourth argument to the function
 * @param [R] the type of the result of the function
 *
 * @see TriFunc
 *
 * @since 0.6.0
 */
typealias QuadFunc<P1, P2, P3, P4, R> = (
    @ParameterName("a") P1,
    @ParameterName("b") P2,
    @ParameterName("c") P3,
    @ParameterName("d") P4
) -> R

// Suppliers
// - single
/**
 * Represents a supplier of a result.
 *
 * There is no requirement that a new or distinct result be returned each time the supplier is invoked.
 *
 * @param [T] the type of results supplied by `this` supplier
 *
 * @since 0.6.0
 *
 * @see java.util.function.Supplier
 */
typealias Supplier<T> = () -> T

/**
 * Type-alias for [java.util.function.Supplier].
 */
typealias JSupplier<T> = java.util.function.Supplier<T>

// - double
/**
 * Represents a supplier of a two results.
 *
 * This is the two-arity specialization of [Supplier].
 *
 * There is no requirement that a new or distinct result be returned each time the supplier is invoked.
 *
 * @param [A] the type of the first result supplied by `this` supplier
 * @param [B] the type of the second result supplied by `this` supplier
 *
 * @since 0.6.0
 */
typealias BiSupplier<A, B> = () -> Pair<A, B>

// - triple
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
 *
 * @since 0.6.0
 */
typealias TriSupplier<A, B, C> = () -> Triple<A, B, C>

// - quadruple
/**
 * Represents a supplier of a four results.
 *
 * This is the four-arity specialization of [Supplier].
 *
 * There is no requirement that a new or distinct result be returned each time the supplier is invoked.
 *
 * @param [A] the type of the first result supplied by `this` supplier
 * @param [B] the type of the second result supplied by `this` supplier
 * @param [C] the type of the third result supplied by `this` supplier
 * @param [D] the type of the fourth result supplied by `this` supplier
 *
 * @since 0.6.0
 */
typealias QuadSupplier<A, B, C, D> = () -> Quadruple<A, B, C, D>

// Predicate
// - single
/**
 * Represents a predicate *([boolean][Boolean]-valued [function][Func])* of one argument.
 *
 * @param [P1] the type of the input to the predicate
 *
 * @since 0.6.0
 *
 * @see java.util.function.Predicate
 */
typealias Predicate<P1> = (P1) -> Boolean

/**
 * Type-alias for [java.util.function.Predicate].
 */
typealias JPredicate<P1> = java.util.function.Predicate<P1>

// - double
/**
 * Represents a predicate *([boolean][Boolean]-valued [function][Func])* of two arguments.
 *
 * This is the two-arity specialization of [Predicate].
 *
 * @param [P1] the type of the first argument to the predicate
 * @param [P2] the type of the second argument to the predicate
 *
 * @since 0.6.0
 *
 * @see Predicate
 * @see java.util.function.Predicate
 */
typealias BiPredicate<P1, P2> = (@ParameterName("a") P1, @ParameterName("b") P2) -> Boolean

/**
 * Type-alias for [java.util.function.Predicate].
 */
typealias JBiPredicate<P1, P2> = java.util.function.BiPredicate<P1, P2>

// - triple
/**
 * Represents a predicate *([boolean][Boolean]-valued [function][Func])* of three arguments.
 *
 * This is the three-arity specialization of [Predicate].
 *
 * @param [P1] the type of the first argument to the predicate
 * @param [P2] the type of the second argument to the predicate
 * @param [P3] the type of the third argument to the predicate
 *
 * @since 0.6.0
 *
 * @see BiPredicate
 */
typealias TriPredicate<P1, P2, P3> = (@ParameterName("a") P1, @ParameterName("b") P2, @ParameterName("c") P3) -> Boolean

// - quadruple
/**
 * Represents a predicate *([boolean][Boolean]-valued [function][Func])* of four arguments.
 *
 * This is the four-arity specialization of [Predicate].
 *
 * @param [P1] the type of the first argument to the predicate
 * @param [P2] the type of the second argument to the predicate
 * @param [P3] the type of the third argument to the predicate
 * @param [P4] the type of the fourth argument to the predicate
 *
 * @since 0.6.0
 *
 * @see TriPredicate
 */
typealias QuadPredicate<P1, P2, P3, P4> = (
    @ParameterName("a") P1,
    @ParameterName("b") P2,
    @ParameterName("c") P3,
    @ParameterName("d") P4
) -> Boolean

// Operators
// - unary
/**
 * Represents an operation on a single operand that produces a result of the same type as its operand.
 *
 * This is a specialization of [Func] for the case where the operand and result are of the same type.
 *
 * @param [T] the type of the operand and result of the operator
 *
 * @since 0.6.0
 *
 * @see Function
 * @see BinaryOperator
 * @see java.util.function.UnaryOperator
 */
typealias UnaryOperator<T> = Func<T, T>

/**
 * Type-alias for [java.util.function.UnaryOperator].
 */
typealias JUnaryOperator<T> = java.util.function.UnaryOperator<T>

// - binary
/**
 * Represents an operation upon two operands of the same type, producing a result of the same type as the operands.
 *
 * This is a specialization of [BiFunc] for the case where the operands and the result are all of the same type.
 *
 * @param [T] the type of the operands and result of the operator
 *
 * @since 0.6.0
 *
 * @see BiFunc
 * @see UnaryOperator
 * @see java.util.function.BinaryOperator
 */
typealias BinaryOperator<T> = BiFunc<T, T, T>

/**
 * Type-alias for [java.util.function.BinaryOperator].
 */
typealias JBinaryOperator<T> = java.util.function.BinaryOperator<T>