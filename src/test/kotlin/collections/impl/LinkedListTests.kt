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

package moe.kanon.kommons.test.collections.impl

import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.kotlintest.specs.ExpectSpec
import moe.kanon.kommons.collections.lists.impl.doublyLinkedListOf
import moe.kanon.kommons.collections.lists.impl.mutableDoublyLinkedListOf

class DoublyLinkedListTest : ExpectSpec({
    val data = doublyLinkedListOf(17, 7532, -3321, 8563, -5, 597, 10256)
    context("given a linked-list containing a sequence of numbers") {
        expect("that the linked-list should contain the numbers") {
            data shouldContainExactly listOf(17, 7532, -3321, 8563, -5, 597, 10256)
        }
        expect("that the linked-list can be sorted") {
            data.sorted() shouldContainExactly listOf(-3321, -5, 17, 597, 7532, 8563, 10256)
            data.sortedDescending() shouldContainExactly listOf(10256, 8563, 7532, 597, 17, -5, -3321)
        }
    }
})

class MutableDoublyLinkedListTest : BehaviorSpec({
    val data = mutableDoublyLinkedListOf(13, 957, -653)
    Given("a mutable linked-list containing a sequence of numbers") {
        When("adding a number new number to it") {
            data += 1337
            Then("the linked-list should now contain the new number") {
                data shouldContainExactly listOf(13, 957, -653, 1337)
            }
        }
        When("removing the newly added number from it") {
            data -= 1337
            Then("the linked-list should not contain the number") {
                data shouldContainExactly listOf(13, 957, -653)
            }
        }
        When("removing the first element (head) <13> from the list") {
            data -= 13
            Then("the new head of the list should be <957>") {
                data.head shouldBe 957
                data shouldContainExactly listOf(957, -653)
            }
        }
        When("removing the last element (tail) <-653> from the list") {
            data -= -653
            Then("the new tail of the list should be <957>") {
                data.tail shouldBe 957
            }
        }
    }
})