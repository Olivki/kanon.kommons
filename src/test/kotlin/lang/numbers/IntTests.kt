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

package moe.kanon.kommons.test.lang.numbers

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.BehaviorSpec
import moe.kanon.kommons.lang.isEven
import moe.kanon.kommons.lang.isOdd
import moe.kanon.kommons.test.forAll
import moe.kanon.kommons.test.setOfRandomIntegerNumbers

class IsIntOddTests : BehaviorSpec({
    val unsortedData = setOfRandomIntegerNumbers<Int>(
        size = 20,
        roof = Int.MAX_VALUE.toLong(),
        smallRoof = (Int.MAX_VALUE / 100).toLong(),
        sort = false
    ).associate { it to (it and 1 != 0) }

    Given("a list of random unsorted integers") {
        unsortedData.forAll { (input, expected) ->
            When("invoking \"isOdd\" on input \"$input\"") {
                Then("the return should be \"$expected\"") {
                    input.isOdd shouldBe expected
                    input.isEven shouldNotBe expected
                }
            }
        }
    }

    // Checking so that it still works even when sorted, so that the order of the numbers got nothing to do with it.
    val sortedData = unsortedData.toSortedMap()

    Given("a list of random integers sorted by their natural value") {
        sortedData.forAll { (input, expected) ->
            When("invoking \"isOdd\" on input \"$input\"") {
                Then("the return should be \"$expected\"") {
                    input.isOdd shouldBe expected
                    input.isEven shouldNotBe expected
                }
            }
        }
    }
})

class IsIntEvenTests : BehaviorSpec({
    val unsortedData = setOfRandomIntegerNumbers<Int>(
        size = 20,
        roof = Int.MAX_VALUE.toLong(),
        smallRoof = (Int.MAX_VALUE / 100).toLong(),
        sort = false
    ).associate { it to (it and 1 == 0) }

    Given("a list of random unsorted integers") {
        unsortedData.forAll { (input, expected) ->
            When("invoking \"isEven\" on input \"$input\"") {
                Then("the return should be \"$expected\"") {
                    input.isEven shouldBe expected
                    input.isOdd shouldNotBe expected
                }
            }
        }
    }

    val sortedData = unsortedData.toSortedMap()

    Given("a list of random integers sorted by their natural value") {
        sortedData.forAll { (input, expected) ->
            When("invoking \"isEven\" on input \"$input\"") {
                Then("the return should be \"$expected\"") {
                    input.isEven shouldBe expected
                    input.isOdd shouldNotBe expected
                }
            }
        }
    }
})

// Negative
class IsNegativeIntOddTests : BehaviorSpec({
    val unsortedData = setOfRandomIntegerNumbers<Int>(
        size = 20,
        roof = Int.MAX_VALUE.toLong(),
        smallRoof = (Int.MAX_VALUE / 100).toLong(),
        sort = false,
        start = Int.MIN_VALUE.toLong()
    ).associate { it to (it and 1 != 0) }

    Given("a list of random unsorted negative integers") {
        unsortedData.forAll { (input, expected) ->
            When("invoking \"isOdd\" on input \"$input\"") {
                Then("the return should be \"$expected\"") {
                    input.isOdd shouldBe expected
                }
            }
        }
    }

    val sortedData = unsortedData.toSortedMap()

    Given("a list of random negative integers sorted by their natural value") {
        sortedData.forAll { (input, expected) ->
            When("invoking \"isOdd\" on input \"$input\"") {
                Then("the return should be \"$expected\"") {
                    input.isOdd shouldBe expected
                }
            }
        }
    }
})

class IsNegativeIntEvenTests : BehaviorSpec({
    val unsortedData = setOfRandomIntegerNumbers<Int>(
        size = 20,
        roof = 0,
        smallRoof = (Int.MAX_VALUE / 100).toLong(),
        sort = false,
        start = Int.MIN_VALUE.toLong()
    ).associate { it to (it and 1 == 0) }

    Given("a list of random unsorted negative integers") {
        unsortedData.forAll { (input, expected) ->
            When("invoking \"isEven\" on input \"$input\"") {
                Then("the return should be \"$expected\"") {
                    input.isEven shouldBe expected
                    input.isOdd shouldNotBe expected
                }
            }
        }
    }

    val sortedData = unsortedData.toSortedMap()

    Given("a list of random negative integers sorted by their natural value") {
        sortedData.forAll { (input, expected) ->
            When("invoking \"isEven\" on input \"$input\"") {
                Then("the return should be \"$expected\"") {
                    input.isEven shouldBe expected
                    input.isOdd shouldNotBe expected
                }
            }
        }
    }
})