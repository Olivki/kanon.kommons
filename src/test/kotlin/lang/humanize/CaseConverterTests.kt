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

/*import io.kotlintest.forAll
import io.kotlintest.inspectors.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import moe.kanon.kommons.humanize.CaseFormat
import moe.kanon.kommons.humanize.toCase

class ConvertToPascalCaseTests : BehaviorSpec({
    val data = listOf(
        "customer" to "Customer",
        "CUSTOMER" to "CUSTOMER",
        "CUStomer" to "CUStomer",
        "customer_name" to "CustomerName",
        "customer_first_name" to "CustomerFirstName",
        "customer_first_name_goes_here" to "CustomerFirstNameGoesHere",
        "customer name" to "Customer name"
    )

    Given("a list of sentences") {
        forAll(data) { (value, expected) ->
            When("calling pascalize on \"$value\"") {
                Then("it should be \"$expected\"") {
                    value.toCase(CaseFormat.PASCAL_CASE) shouldBe expected
                }
            }
        }
    }
})

class ConvertToCamelCaseTests : BehaviorSpec({
    val data = listOf(
        "customer" to "customer",
        "CUSTOMER" to "cUSTOMER",
        "CUStomer" to "cUStomer",
        "customer_name" to "customerName",
        "customer_first_name" to "customerFirstName",
        "customer_first_name_goes_here" to "customerFirstNameGoesHere",
        "customer name" to "customerName",
        "customer first name goes here" to "customerFirstNameGoesHere",
        "My name is Will Smith" to "myNameIsWillSmith",
        "yEt AnOtHeR mArKuP lAnGuAgE" to "yetAnotherMarkupLanguage"
    )

    Given("a sentence in a random style") {
        data.forAll { (value, expected) ->
            When("invoking 'toCase(Case.CAMEL_CASE)' on it ($value)") {
                Then("it should return '$expected'") {
                    value.toCase(CaseFormat.CAMEL_CASE) shouldBe expected
                }
            }
        }
    }
})

class ConvertToSnakeCaseTests : BehaviorSpec({
    val data = listOf(
        "SomeTitle" to "some_title",
        "someTitle" to "some_title",
        "some title" to "some_title",
        "some title that will be underscored" to "some_title_that_will_be_underscored",
        "SomeTitleThatWillBeUnderscored" to "some_title_that_will_be_underscored"
    )

    Given("a list of sentences") {
        forAll(data) { (value, expected) ->
            When("calling underscore on \"$value\"") {
                Then("it should be \"$expected\"") {
                    value.toCase(CaseFormat.SNAKE_CASE) shouldBe expected
                }
            }
        }
    }
})*/