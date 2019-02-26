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
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import moe.kanon.kommons.lang.humanize.LetterCase
import moe.kanon.kommons.lang.humanize.humanize
import moe.kanon.kommons.lang.isAllUpperCase
import moe.kanon.kommons.test.forAll

class HumanizeStringPascalCaseTests : BehaviorSpec({
    val data = listOf(
        "PascalCaseInputStringIsTurnedIntoSentence" to "Pascal case input string is turned into sentence",
        "WhenIUseAnInputAHere" to "When I use an input a here",
        "10IsInTheBeginning" to "10 is in the beginning",
        "NumberIsAtTheEnd100" to "Number is at the end 100",
        "XIsFirstWordInTheSentence" to "X is first word in the sentence",
        "HTML" to "HTML",
        "TheHTMLLanguage" to "The HTML language",
        "HTMLIsTheLanguage" to "HTML is the language",
        "TheLanguageIsHTML" to "The language is HTML",
        "HTML5" to "HTML 5",
        "1HTML" to "1 HTML"
    )

    Given("a list of sentences formatted in PascalCase") {
        forAll(data) { (input, expected) ->
            When("calling humanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.humanize() shouldBe expected
                }
            }
        }
    }
})

class HumanizeStringUnderscoreTests : BehaviorSpec({
    val data = mapOf(
        "Underscored_input_string_is_turned_into_sentence" to "Underscored input string is turned into sentence"
    )

    Given("a list of sentences where ' ' has been replaced with '_'") {
        data.forAll { (input, expected) ->
            When("calling humanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.humanize() shouldBe expected
                }
            }
        }
    }
})

class HumanizeStringToTitleCaseTests : BehaviorSpec({
    val data = mapOf(
        "CanReturnTitleCase" to "Can Return Title Case",
        "Can_return_title_Case" to "Can Return Title Case",
        "Title_humanization_Does_NOT_Honor_ALLCAPS" to "Title Humanization Does not Honor Allcaps"
    )

    Given("a list of sentences with different casings") {
        data.forAll { (input, expected) ->
            When("calling humanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.humanize(LetterCase.TITLE) shouldBe expected
                }
            }
        }
    }
})

class HumanizeStringToLowerCaseTests : BehaviorSpec({
    val data = mapOf(
        "CanReturnLowerCase" to "can return lower case",
        "Can_Return_Lower_Case" to "can return lower case",
        "LOWERCASE" to "lowercase"
    )

    Given("a list of sentences with different casings") {
        data.forAll { (input, expected) ->
            When("calling humanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.humanize(LetterCase.LOWER_CASE) shouldBe expected
                }
            }
        }
    }
})

class HumanizeStringToSentenceCaseTests : BehaviorSpec({
    val data = mapOf(
        "CanReturnSentenceCase" to "Can return sentence case",
        "Can_Return_Sentence_Case" to "Can return sentence case",
        "" to ""
    )

    Given("a list of sentences with different casings") {
        data.forAll { (input, expected) ->
            When("calling humanize on \"$input\"") {
                Then("it should become \"$expected\"") {
                    input.humanize(LetterCase.SENTENCE) shouldBe expected
                }
            }
        }
    }
})

class HumanizeStringToUpperCaseTests : BehaviorSpec({
    val data = mapOf(
        "CanHumanizeIntoUpperCase" to "CAN HUMANIZE INTO UPPER CASE",
        "Can_Humanize_into_Upper_case" to "CAN HUMANIZE INTO UPPER CASE",
        "Can-Humanize-into-Upper-case" to "CAN HUMANIZE INTO UPPER CASE"
    )

    data.forAll { (input, expected) ->
        Given("a string ($input)") {
            When("invoking 'humanize(LetterCase.UPPER_CASE)' on it") {
                Then("it should return '$expected'") {
                    input.humanize(LetterCase.UPPER_CASE) shouldBe expected
                }
            }
        }
    }
})

// TODO: Move to (K)Strings Test
class StringIsAllUpperCaseTests : BehaviorSpec({
    Given("a string where all characters are in upper-case (HTML)") {
        When("invoking 'isAllUpperCase' on it") {
            Then("it should return true") {
                "HTML".isAllUpperCase().shouldBeTrue()
            }
        }
    }

    Given("a string where all the characters are in lower-case (html)") {
        When("invoking 'isAllUpperCase' on it") {
            Then("it should return false") {
                "html".isAllUpperCase().shouldBeFalse()
            }
        }
    }
})