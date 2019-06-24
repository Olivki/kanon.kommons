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

package moe.kanon

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asTypeName
import moe.kanon.kommons.writeOut
import net.bytebuddy.ByteBuddy
import net.bytebuddy.agent.ByteBuddyAgent
import net.bytebuddy.asm.Advice
import net.bytebuddy.description.modifier.Visibility
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy
import net.bytebuddy.implementation.MethodDelegation
import net.bytebuddy.implementation.bind.annotation.This
import net.bytebuddy.matcher.ElementMatcher
import net.bytebuddy.matcher.ElementMatchers
import java.io.Closeable
import java.io.IOException
import java.lang.Appendable
import java.lang.reflect.Type
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.util.Types
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaType
import kotlin.reflect.typeOf

const val FUNC_DIR = "kommons.func/src/main/kotlin/moe/kanon/kommons/func"

val Int.isEven: Boolean get() = this and 0x1 == 0

val Int.isOdd: Boolean get() = this and 0x1 == 1

val TUPLE_NAMES = mapOf(
    0 to "Unit",
    1 to "Single",
    2 to "Duad",
    3 to "Triad",
    4 to "Quadruple",
    5 to "Quintuple",
    6 to "Sextuple",
    7 to "Septuple",
    8 to "Octuple",
    9 to "Nonuple",
    10 to "Decuple",
    11 to "Undecuple",
    12 to "Duodecuple",
    13 to "Tredecuple",
    14 to "Quattuordecuple",
    15 to "Quindecuple",
    16 to "Sexdecuple",
    17 to "Septendecuple",
    18 to "Octodecuple",
    19 to "Novemdecuple",
    20 to "Vigintuple",
    21 to "Unvigintuple",
    22 to "Duovigintuple",
    23 to "Trevigintuple",
    24 to "Quattuorvigintuple",
    25 to "Quinvigintuple",
    26 to "Sexvigintuple",
    27 to "Septenvigintuple",
    28 to "Octovigintuple",
    29 to "Novemvigintuple",
    30 to "Trigintuple",
    31 to "Untrigintuple",
    32 to "Duotriguple",
    33 to "Tretriguple",
    34 to "Quattuortriguple",
    35 to "Quintriguple",
    36 to "Sextriguple",
    37 to "Septentriguple",
    38 to "Octotriguple",
    39 to "Novemtriguple",
    40 to "Quadraguple",
    41 to "Unquadraguple",
    42 to "Duoquadraguple",
    43 to "Trequadraguple",
    44 to "Quattuorquadraguple",
    45 to "Quinquadraguple",
    46 to "Sexquadraguple",
    47 to "Septenquadraguple",
    48 to "Octoquadraguple",
    49 to "Novemquadraguple",
    50 to "Quinquaguple",
    51 to "Unquinquaguple",
    52 to "Duoquinquaguple",
    53 to "Trequinquaguple",
    54 to "Quattuorquinquaguple",
    55 to "Quinquinquaguple",
    56 to "Sexquinquaguple",
    57 to "Septenquinquaguple",
    58 to "Octoquinquaguple",
    59 to "Novemquinquaguple",
    60 to "Sexaguple",
    61 to "Unsexaguple",
    62 to "Duosexaguple",
    63 to "Tresexaguple",
    64 to "Quattuorsexaguple",
    65 to "Quinsexaguple",
    66 to "Sexsexaguple",
    67 to "Septensexaguple",
    68 to "Octosexaguple",
    69 to "Novemsexaguple",
    70 to "Septuaguple",
    71 to "Unseptuaguple",
    72 to "Duoseptuaguple",
    73 to "Treseptuaguple",
    74 to "Quattuorseptuaguple",
    75 to "Quinseptuaguple",
    76 to "Sexseptuaguple",
    77 to "Septenseptuaguple",
    78 to "Octoseptuaguple",
    79 to "Novemseptuaguple",
    80 to "Octoguple",
    81 to "Unoctoguple",
    82 to "Duooctoguple",
    83 to "Treoctoguple",
    84 to "Quattuoroctoguple",
    85 to "Quinoctoguple",
    86 to "Sexoctoguple",
    87 to "Septoctoguple",
    88 to "Octooctoguple",
    89 to "Novemoctoguple",
    90 to "Nonaguple",
    91 to "Unnonaguple",
    92 to "Duononaguple",
    93 to "Trenonaguple",
    94 to "Quattuornonaguple",
    95 to "Quinnonaguple",
    96 to "Sexnonaguple",
    97 to "Septennonaguple",
    98 to "Octononaguple",
    99 to "Novemnonaguple",
    100 to "Centuple"
)

val ALPHABET_LOWERCASE = charArrayOf(
    'a',
    'b',
    'c',
    'd',
    'e',
    'f',
    'g',
    'h',
    'i',
    'j',
    'k',
    'l',
    'm',
    'n',
    'o',
    'p',
    'q',
    'r',
    's',
    't',
    'u',
    'v',
    'w',
    'x',
    'y',
    'z'
)

val ALPHABET_UPPERCASE = charArrayOf(
    'A',
    'B',
    'C',
    'D',
    'E',
    'F',
    'G',
    'H',
    'I',
    'J',
    'K',
    'L',
    'M',
    'N',
    'O',
    'P',
    'Q',
    'R',
    'S',
    'T',
    'U',
    'V',
    'W',
    'X',
    'Y',
    'Z'
)

val COPYRIGHT_HEADER = """
    |/*
    | * Copyright 2019 Oliver Berg
    | *
    | * Licensed under the Apache License, Version 2.0 (the "License");
    | * you may not use this file except in compliance with the License.
    | * You may obtain a copy of the License at
    | *
    | *        http://www.apache.org/licenses/LICENSE-2.0
    | *
    | * Unless required by applicable law or agreed to in writing, software
    | * distributed under the License is distributed on an "AS IS" BASIS,
    | * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    | * See the License for the specific language governing permissions and
    | * limitations under the License.
    | */
""".trimMargin()

// very hacky stuff going on here
// this is because there seems to be no inbuilt way to customize the max column limit when writing output, and when it
// comes to auto generated files, I want them to generally take up as few lines as possible, seeing as generally people
// shouldn't be reading them, so readability isn't key
fun List<ParameterSpec>.emit(
    codeWriter: Any,
    forceNewLines: Boolean = false,
    emitBlock: (ParameterSpec) -> Unit = { param -> param::class.functions.first { it.name == "emit" }.let {
        it.isAccessible = true
        it.call(param, codeWriter)
        Unit
    } }
): Any = with(codeWriter) {
    fun emit(out: String) = this::class.functions.first { it.name == "emit" }.let {
        it.isAccessible = true
        it.call(this, out, true)
        Unit
    }

    emit("(")
    if (size > 0) {
        val delimiter = ", "
        forEachIndexed { index, parameter ->
            if (index > 0) emit(delimiter)
            emitBlock(parameter)
        }
    }
    emit(")")
    return@with this
}

@UseExperimental(ExperimentalStdlibApi::class) fun enactWorldDomination() {
    ByteBuddyAgent.install()

    // removes the column limit from the 'writeTo(out)' function
    /*val fileSpecClass = Class.forName("com.squareup.kotlinpoet.FileSpec")//FileSpec::class.java
    ByteBuddy()
        .redefine(fileSpecClass)
        .defineMethod("writeTo$", Void.TYPE, Visibility.PUBLIC)
        .withParameter(Appendable::class.java, "out")
        .intercept(MethodDelegation.to(ReroutedFileSpecWriteTo::class.java))
        .method(ElementMatcher.Junction.Conjunction(
            ElementMatchers.named("writeTo"),
            ElementMatchers.takesArgument(0, Appendable::class.java)
        ))
        .intercept(MethodDelegation.to(ReroutedFileSpecWriteTo::class.java))
        .make()
        .load(fileSpecClass.classLoader, ClassReloadingStrategy.fromInstalledAgent())*/

    // removes the new line hardcoded setting for parameters
    val parameterSpecClass = Class.forName("com.squareup.kotlinpoet.ParameterSpecKt")
    ByteBuddy()
        .rebase(parameterSpecClass)
        .method(ElementMatchers.named("emit"))
        .intercept(MethodDelegation.to(ReroutedEmit::class.java))
        .make()
        .load(parameterSpecClass.classLoader, ClassReloadingStrategy.fromInstalledAgent())
}

@Suppress("LocalVariableName", "UNCHECKED_CAST") fun FileSpec.write(out: Appendable, columnLimit: Int = Integer.MAX_VALUE) {
    val DEFAULT_INDENT = "    "

    val NullAppendable = Class.forName("com.squareup.kotlinpoet.NullAppendable").kotlin.objectInstance!! as Appendable

    val writerClass = Class.forName("com.squareup.kotlinpoet.CodeWriter").kotlin
    val writerConst = writerClass.constructors.first()

    val memberImports = (this::class.memberProperties.first { it.name == "memberImports" } as KProperty1<FileSpec, Map<String, Any>>).let {
        it.isAccessible = true
        it.get(this)
    }

    fun CodeWriter(
        out: Appendable,
        indent: String = DEFAULT_INDENT,
        memberImports: Map<String, Any> = emptyMap(),
        importedTypes: Map<String, ClassName> = emptyMap(),
        importedMembers: Map<String, MemberName> = emptyMap(),
        limit: Int = columnLimit
    ) : Closeable = writerConst.call(out, indent, memberImports, importedTypes, importedMembers, limit) as Closeable

    fun emit(codeWriter: Any) = this::class.functions.first { it.name == "emit" }.let {
        it.isAccessible = true
        it.call(this, codeWriter)
        Unit
    }

    val importsCollector = CodeWriter(NullAppendable, DEFAULT_INDENT, memberImports, limit = Integer.MAX_VALUE)

    emit(importsCollector)

    val suggestedTypeImports = importsCollector::class.functions.first { it.name == "suggestedTypeImports" }.let {
        it.call(importsCollector) as Map<String, ClassName>
    }
    val suggestedMemberImports = importsCollector::class.functions.first { it.name == "suggestedMemberImports" }.let {
        it.call(importsCollector) as Map<String, MemberName>
    }

    importsCollector.close()

    CodeWriter(out, DEFAULT_INDENT, memberImports, suggestedTypeImports, suggestedMemberImports).use {
        emit(it)
    }
}

// end of hacky stuff

private object NumberToWords {
    fun toWords(value: Int): String = toWords(value.toLong())

    fun toWords(value: Long): String {
        var number = value
        if (number == 0L)
            return "zero"

        if (number < 0)
            return "minus ${toWords((number * -1))}"

        val parts = ArrayList<String>()

        if ((number / 1000000000) > 0) {
            parts.add("${toWords((number / 1000000000))} billion")
            number %= 1000000000
        }

        if ((number / 1000000) > 0) {
            parts.add("${toWords((number / 1000000))} million")
            number %= 1000000
        }

        if ((number / 1000) > 0) {
            parts.add("${toWords((number / 1000))} thousand")
            number %= 1000
        }

        if ((number / 100) > 0) {
            parts.add("${toWords(number / 100)} hundred")
            number %= 100
        }

        if (number > 0) {
            if (parts.count() != 0)
                parts.add("and")

            if (number < 20)
                parts.add(unitsMap[number.toInt()])
            else {
                var lastPart = tensMap[number.toInt() / 10]
                if ((number % 10) > 0)
                    lastPart += "-${unitsMap[number.toInt() % 10]}"

                parts.add(lastPart)
            }
        }

        return parts.joinToString(" ").trimStart()
    }

    fun toOrdinalWords(value: Int): String {
        val toWords: String
        // 9 => ninth
        if (exceptionNumbersToWords(value) != "")
            return exceptionNumbersToWords(value)

        // 21 => twenty first
        if (value > 20) {
            val exceptionPart: String
            if (exceptionNumbersToWords(value % 10) != "") {
                exceptionPart = exceptionNumbersToWords(value % 10)
                val normalPart = value - value % 10
                toWords = removeOnePrefix(toWords(normalPart))
                return "$toWords $exceptionPart"
            }
        }

        return normalNumberToWords(value)
    }

    private val unitsMap: List<String> = listOf(
        "zero",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
        "ten",
        "eleven",
        "twelve",
        "thirteen",
        "fourteen",
        "fifteen",
        "sixteen",
        "seventeen",
        "eighteen",
        "nineteen"
    )

    private val tensMap: List<String> = listOf(
        "zero",
        "ten",
        "twenty",
        "thirty",
        "forty",
        "fifty",
        "sixty",
        "seventy",
        "eighty",
        "ninety"
    )

    private val ordinalExceptions: Map<Int, String> = mapOf(
        1 to "first",
        2 to "second",
        3 to "third",
        4 to "fourth",
        5 to "fifth",
        8 to "eighth",
        9 to "ninth",
        12 to "twelfth"
    )

    private fun normalNumberToWords(number: Int): String {
        var toWords = toWords(number).replace('-', ' ').trimStart()

        toWords = removeOnePrefix(toWords)
        // twenty => twentieth
        if (toWords.endsWith("y"))
            toWords = "${toWords.substring(0, toWords.length - 1)}ie"

        return "${toWords}th"
    }

    private fun removeOnePrefix(toWords: String): String {
        // one hundred => hundredth
        var result = toWords

        if (toWords.startsWith("one ")) result = toWords.substring(4)

        return result
    }

    private fun exceptionNumbersToWords(number: Int): String =
        if (ordinalExceptions.count { it.key == number } == 1)
            ordinalExceptions.entries.first { it.key == number }.value else ""
}

fun Int.toWords(): String = this.toLong().toWords()

fun Long.toWords(): String = NumberToWords.toWords(this)

fun Int.toOrdinalWords(): String = NumberToWords.toOrdinalWords(this)


fun <T> Iterable<T>.shank(vararg elements: T, startIndex: Int = 0, deleteCount: Int = 0): List<T> {
    val list = this.toList()
    return list.slice(0 until startIndex) + elements + list.drop(startIndex + deleteCount)
}

fun <T> Iterable<T>.occurrencesOf(item: T): Int = this.count { it == item }

// originally taken from https://github.com/friedrich-goetze/kotlinpoet/blob/e0a01fe9c2f8a32b83ae9a74c7a1557815bb8fc3/src/main/java/com/squareup/kotlinpoet/Shortcuts.kt
// modified to make use of kotlin features to reduce some unneeded code typing

// -- UTILITY FUNCTIONS -- \\
// file spec
@UtilScope inline fun <reified A : Annotation> FileSpec.Builder.annotation() = addAnnotation(A::class)

// fun spec
inline fun <reified T> FunSpec.Builder.returns() = returns(T::class)

inline fun <reified T> FunSpec.Builder.returnsNullable() = returns(T::class.asTypeName().copy(nullable = true))

@UtilScope inline fun <reified A : Annotation> FunSpec.Builder.annotation() = addAnnotation(A::class)

inline fun <reified T> FunSpec.Builder.addParameter(name: String, vararg modifiers: KModifier) =
    addParameter(name, T::class, *modifiers)

// type spec
@UtilScope inline fun <reified A : Annotation> TypeSpec.Builder.annotation() = addAnnotation(A::class)

@UtilScope inline fun <reified T> TypeSpec.Builder.implements() = addSuperinterface(T::class)

@UtilScope fun TypeSpec.Builder.implements(superinterface: TypeName, delegate: CodeBlock = CodeBlock.builder().build()) =
    addSuperinterface(superinterface, delegate)

@UtilScope fun TypeSpec.Builder.implements(name: String) = addSuperinterface(ClassName.bestGuess(name))

@UtilScope fun TypeSpec.Builder.implements(name: ClassName) = addSuperinterface(name)

inline fun <reified T> TypeSpec.Builder.addProperty(name: String, vararg modifiers: KModifier) =
    addProperty(name, T::class, *modifiers)

inline fun <reified T> TypeSpec.Builder.addMutableProperty(name: String, vararg modifiers: KModifier) =
    addProperty(PropertySpec.builder(name, T::class, *modifiers).mutable(true).build())

// -- DSL FUNCTIONS -- \\
@DslMarker annotation class UtilScope

/*
 * Shortcuts for FileSpec
 */
@UtilScope inline fun file(packageName: String, fileName: String, block: FileSpec.Builder.() -> Unit) =
    FileSpec.builder(packageName, fileName).apply(block).build()

/*
 * Shortcuts for adding TypeSpec to FileSpec
 */
@UtilScope inline fun FileSpec.Builder.`class`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.classBuilder(name).apply(block).build())

@UtilScope inline fun FileSpec.Builder.`class`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.classBuilder(className).apply(block).build())

@UtilScope inline fun FileSpec.Builder.expectClass(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.expectClassBuilder(name).apply(block).build())

@UtilScope inline fun FileSpec.Builder.expectClass(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.expectClassBuilder(className).apply(block).build())

@UtilScope inline fun FileSpec.Builder.`object`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder =
    addType(TypeSpec.objectBuilder(name).apply(block).build())

@UtilScope inline fun FileSpec.Builder.`object`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.objectBuilder(className).apply(block).build())

@UtilScope inline fun FileSpec.Builder.`interface`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.interfaceBuilder(name).apply(block).build())

@UtilScope inline fun FileSpec.Builder.`interface`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.interfaceBuilder(className).apply(block).build())

@UtilScope inline fun FileSpec.Builder.`enum`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder =
    addType(TypeSpec.enumBuilder(name).apply(block).build())

@UtilScope inline fun FileSpec.Builder.`enum`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.enumBuilder(className).apply(block).build())

@UtilScope inline fun FileSpec.Builder.annotation(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.annotationBuilder(name).apply(block).build())

@UtilScope inline fun FileSpec.Builder.annotation(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.annotationBuilder(className).apply(block).build())

@UtilScope inline fun <reified A : Annotation> FileSpec.Builder.annotation(
    block: AnnotationSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addAnnotation(AnnotationSpec.builder(A::class).apply(block).build())

/*
 * Shortcuts for adding TypeSpec to TypeSpec
 */
@UtilScope inline fun TypeSpec.Builder.`class`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder =
    addType(TypeSpec.classBuilder(name).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.`class`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.classBuilder(className).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.expectClass(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.expectClassBuilder(name).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.expectClass(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.expectClassBuilder(className).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.`object`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder =
    addType(TypeSpec.objectBuilder(name).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.`object`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.objectBuilder(className).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.`interface`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.interfaceBuilder(name).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.`interface`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.interfaceBuilder(className).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.`enum`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.enumBuilder(name).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.`enum`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.enumBuilder(className).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.annotation(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.annotationBuilder(name).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.annotation(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.annotationBuilder(className).apply(block).build())

@UtilScope inline fun <reified A : Annotation> TypeSpec.Builder.annotation(
    block: AnnotationSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addAnnotation(AnnotationSpec.builder(A::class).apply(block).build())

/*
 * Shortcuts for adding PropertySpec to FileSpec
 */
@UtilScope inline fun FileSpec.Builder.value(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): FileSpec.Builder = addProperty(PropertySpec.builder(name, type, *modifiers).apply(block).build())

@UtilScope inline fun FileSpec.Builder.value(
    name: String,
    type: KType,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): FileSpec.Builder = addProperty(PropertySpec.builder(name, type.javaType, *modifiers).apply(block).build())

@UtilScope inline fun <reified T> FileSpec.Builder.value(
    name: String,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): FileSpec.Builder = addProperty(PropertySpec.builder(name, T::class, *modifiers).apply(block).build())

/*
 * Shortcuts for adding PropertySpec to TypeSpec
 */
@UtilScope inline fun TypeSpec.Builder.value(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder = addProperty(PropertySpec.builder(name, type, *modifiers).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.value(
    name: String,
    type: KType,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder = addProperty(PropertySpec.builder(name, type.javaType, *modifiers).apply(block).build())

@UtilScope inline fun <reified T> TypeSpec.Builder.value(
    name: String,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder = addProperty(PropertySpec.builder(name, T::class, *modifiers).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.variable(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder = addProperty(PropertySpec.builder(name, type, *modifiers).mutable(true).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.variable(
    name: String,
    type: KType,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder =
    addProperty(PropertySpec.builder(name, type.javaType, *modifiers).mutable(true).apply(block).build())

@UtilScope inline fun <reified T> TypeSpec.Builder.variable(
    name: String,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder = addProperty(PropertySpec.builder(name, T::class, *modifiers).mutable(true).apply(block).build())

/*
 * Shortcuts for adding FunctionSpec to FileSpec
 */
@UtilScope inline fun FileSpec.Builder.func(name: String, block: FunSpec.Builder.() -> Unit = {}): FileSpec.Builder =
    addFunction(FunSpec.builder(name).apply(block).build())

/*
 * Shortcuts for adding FunctionSpec to TypeSpec
 */
@UtilScope inline fun TypeSpec.Builder.func(name: String, block: FunSpec.Builder.() -> Unit = {}): TypeSpec.Builder =
    addFunction(FunSpec.builder(name).apply(block).build())

@UtilScope
inline fun TypeSpec.Builder.primaryConstructor(block: FunSpec.Builder.() -> Unit = {}): TypeSpec.Builder =
    primaryConstructor(FunSpec.constructorBuilder().apply(block).build())

@UtilScope
inline fun TypeSpec.Builder.nonPrimaryConstructor(block: FunSpec.Builder.() -> Unit = {}): TypeSpec.Builder =
    addFunction(FunSpec.constructorBuilder().apply(block).build())

@UtilScope inline fun TypeSpec.Builder.overriding(
    method: ExecutableElement,
    block: FunSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addFunction(FunSpec.overriding(method).apply(block).build())

@UtilScope inline fun TypeSpec.Builder.overriding(
    method: ExecutableElement,
    enclosing: DeclaredType,
    types: Types,
    block: FunSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addFunction(FunSpec.overriding(method, enclosing, types).apply(block).build())

/*
 * Shortcuts for using Function Spec as setter or getter
 */
@UtilScope inline fun PropertySpec.Builder.setter(block: FunSpec.Builder.() -> Unit = {}): PropertySpec.Builder =
    setter(FunSpec.setterBuilder().apply(block).build())

@UtilScope inline fun PropertySpec.Builder.getter(block: FunSpec.Builder.() -> Unit = {}): PropertySpec.Builder =
    getter(FunSpec.getterBuilder().apply(block).build())


@UtilScope inline fun <reified A : Annotation> FunSpec.Builder.annotation(
    block: AnnotationSpec.Builder.() -> Unit = {}
): FunSpec.Builder = addAnnotation(AnnotationSpec.builder(A::class).apply(block).build())

/*
 * Shortcuts for fun spec
 */
@UtilScope inline fun FunSpec.Builder.param(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    block: ParameterSpec.Builder.() -> Unit = {}
): FunSpec.Builder = addParameter(ParameterSpec.builder(name, type, *modifiers).apply(block).build())

@UtilScope inline fun FunSpec.Builder.param(
    name: String,
    type: KType,
    vararg modifiers: KModifier,
    block: ParameterSpec.Builder.() -> Unit = {}
): FunSpec.Builder = addParameter(ParameterSpec.builder(name, type.javaType, *modifiers).apply(block).build())

@UtilScope inline fun <reified T> FunSpec.Builder.param(
    name: String,
    vararg modifiers: KModifier,
    block: ParameterSpec.Builder.() -> Unit = {}
): FunSpec.Builder = addParameter(ParameterSpec.builder(name, T::class, *modifiers).apply(block).build())

/*
 * Shortcuts for creating type names
 */
@UtilScope fun clazz(fqName: String) = ClassName.bestGuess(fqName)

@UtilScope fun clazz(`package`: String, name: String, vararg nested: String) = ClassName(`package`, name, *nested)

@UtilScope inline fun <reified Receiver, reified Return> receiverLambda(vararg params: ParameterSpec) =
    LambdaTypeName.get(Receiver::class.asTypeName(), parameters = *params, returnType = Return::class.asTypeName())

@UtilScope inline fun <reified Return> lambda(vararg params: ParameterSpec) =
    LambdaTypeName.get(parameters = *params, returnType = Return::class.asTypeName())

@UtilScope fun lambda(vararg params: ParameterSpec, returns: TypeName = type("Unit")) =
    LambdaTypeName.get(parameters = *params, returnType = returns)

@UtilScope fun lambda(receiverType: TypeName, vararg params: ParameterSpec, returns: TypeName) =
    LambdaTypeName.get(receiverType, parameters = *params, returnType = returns)

// vararg typenames
@UtilScope inline fun <reified Receiver, reified Return> receiverLambda(vararg params: TypeName) =
    LambdaTypeName.get(Receiver::class.asTypeName(), parameters = *params, returnType = Return::class.asTypeName())

@UtilScope inline fun <reified Return> lambda(vararg params: TypeName) =
    LambdaTypeName.get(parameters = *params, returnType = Return::class.asTypeName())

@UtilScope fun lambda(vararg params: TypeName, returns: TypeName = type("Unit")) =
    LambdaTypeName.get(parameters = *params, returnType = returns)

@UtilScope fun lambda(receiverType: TypeName, vararg params: TypeName, returns: TypeName = type("Unit")) =
    LambdaTypeName.get(receiverType, parameters = *params, returnType = returns)

// typevar
@UtilScope fun type(name: String, variance: KModifier? = null) = TypeVariableName(name, variance)

@UtilScope fun type(name: String, vararg bounds: TypeName, variance: KModifier? = null) =
    TypeVariableName(name, *bounds, variance = variance)

@UtilScope fun type(name: String, vararg bounds: KClass<*>, variance: KModifier? = null) =
    TypeVariableName(name, *bounds, variance = variance)

@UtilScope fun type(name: String, vararg bounds: KType, variance: KModifier? = null) =
    TypeVariableName(name, *(bounds.map { it.javaType }.toTypedArray()), variance = variance)

// data class constructor
/**
 * Adds the FunSpec created from the specified [scope] as the primary-constructor of `this` type-spec, and creates
 * `val` properties for each parameter specified.
 */
@UtilScope inline fun TypeSpec.Builder.primaryDataConstructor(scope: FunSpec.Builder.() -> Unit): TypeSpec.Builder {
    val constructor = FunSpec.constructorBuilder().apply(scope)
    primaryConstructor(constructor.build())
    for (param in constructor.parameters) value(param.name, param.type) { initializer(param.name) }
    return this
}

/**
 * Adds the FunSpec created from the specified [scope] as the primary-constructor of `this` type-spec, and creates
 * `val` properties for each parameter specified and applies the specified [variableScope] to each created property.
 */
@UtilScope inline fun TypeSpec.Builder.primaryDataConstructor(
    scope: FunSpec.Builder.() -> Unit,
    variableScope: PropertySpec.Builder.() -> Unit
): TypeSpec.Builder {
    val constructor = FunSpec.constructorBuilder().apply(scope)
    primaryConstructor(constructor.build())
    for (param in constructor.parameters) value(param.name, param.type) {
        initializer(param.name)
        apply(variableScope)
    }
    return this
}