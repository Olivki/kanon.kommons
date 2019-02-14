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

package moe.kanon.kommons.test.lang

import io.kotlintest.specs.BehaviorSpec

// Integer Numbers
// Int
class IsIntOddTest : BehaviorSpec({

    // Maybe move this kind of stuff into files?
    val data = mapOf(
        59 to true,
        135 to true,
        465 to true,
        605 to true,
        869 to true,
        1106 to false,
        1128 to false,
        1237 to true,
        1283 to true,
        1288 to false,
        1394 to false,
        1424 to false,
        1552 to false,
        1572 to false,
        1622 to false,
        1661 to true,
        1725 to true,
        1749 to true,
        1902 to false,
        1930 to false,
        1940 to false,
        1978 to false,
        2027 to true,
        118064457 to true,
        122247617 to true,
        307458506 to false,
        376788924 to false,
        389320341 to true,
        414750875 to true,
        557609177 to true,
        575643370 to false,
        626434093 to true,
        633200003 to true,
        735560156 to false,
        815130686 to false,
        850325763 to true,
        889492532 to false,
        921457140 to false,
        986323660 to false,
        1042973637 to true,
        1222528966 to false,
        1230952514 to false,
        1332479897 to true,
        1353869195 to true,
        1382565366 to false,
        1439983972 to false,
        1494514678 to false,
        1542071125 to true,
        1738098251 to true,
        1804170870 to false,
        1812925007 to true,
        1849162630 to false,
        1907856564 to false,
        1914781154 to false,
        2039497388 to false,
        2050166985 to true,
        2058075483 to true,
        2067727908 to false,
        2100559168 to false,
        2129275411 to true
    )
})

class IsNegativeIntOddTest : BehaviorSpec({

})

// Decimals

