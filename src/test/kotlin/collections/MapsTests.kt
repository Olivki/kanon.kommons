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

package moe.kanon.kommons.test.collections

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import moe.kanon.kommons.collections.flip
import moe.kanon.kommons.test.forAll

class NativeMapFlipTest : BehaviorSpec({

    // TODO make the data random.

    val data = mapOf(
        0 to "zero",
        1 to "one",
        2 to "two",
        3 to "three",
        4 to "four",
        5 to "five",
        6 to "six",
        7 to "seven",
        8 to "eight",
        9 to "nine"
    )

    Given("a map (Int to String) with entries in a specific order") {
        When("invoking \"flip()\" on the map") {
            val iterator = data.iterator()
            val flipped = data.flip()
            flipped.size shouldBe data.size
            flipped.forAll { (flippedKey, flippedValue) ->
                val (originalKey, originalValue) = iterator.next()
                Then("the result should be a map (String to Int) in the same order") {
                    originalKey shouldBe flippedValue
                    originalValue shouldBe flippedKey
                }
            }
        }
    }
})