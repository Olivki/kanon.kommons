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

package moe.kanon.kommons.test.lang.humanize

import io.kotlintest.forAll
import io.kotlintest.inspectors.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import moe.kanon.kommons.lang.humanize.fromRomanNumeral
import moe.kanon.kommons.lang.humanize.toRomanNumeral

class RomanNumeralTests : BehaviorSpec({
    val data = listOf(
        1 to "I",
        2 to "II",
        3 to "III",
        4 to "IV",
        5 to "V",
        6 to "VI",
        7 to "VII",
        8 to "VIII",
        9 to "IX",
        10 to "X",
        11 to "XI",
        12 to "XII",
        40 to "XL",
        50 to "L",
        90 to "XC",
        100 to "C",
        400 to "CD",
        500 to "D",
        3999 to "MMMCMXCIX",
        4566 to "MMMMDLXVI",
        27586 to "MMMMMMMMMMMMMMMMMMMMMMMMMMMDLXXXVI" // I see nothing wrong here.
    )

    data.forAll { (input, expected) ->
        Given("an integer ($input)") {
            When("invoking 'toRomanNumeral' on it") {
                Then("it should return '$expected'") {
                    input.toRomanNumeral() shouldBe expected
                }
            }
        }
    }

    forAll(data) { (expected, input) ->
        Given("a string representing a roman numeral ($input)") {
            When("invoking 'fromRomanNumeral' on it") {
                Then("it should return '$expected'") {
                    input.fromRomanNumeral() shouldBe expected
                }
            }
        }
    }
})
