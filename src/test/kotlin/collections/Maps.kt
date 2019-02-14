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

import forAll
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.BehaviorSpec
import moe.kanon.kommons.collections.flip

class NativeMapFlipTest : BehaviorSpec({

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

    val expectedData = mapOf(
        "zero" to 0,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    // TODO: Write more cohesively

    Given("A map (Int to String) with entries in a specific order") {
        When("Invoking \"flip()\" on the map") {
            data.flip().forAll { (key) ->
                Then("It should be possible to use the new \"key\" as a key on the \"expectedData\" map.") {
                    expectedData[key] shouldNotBe null
                }
            }
        }
    }
})