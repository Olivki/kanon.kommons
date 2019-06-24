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

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import moe.kanon.kommons.Identifiable
import java.io.Serializable
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import moe.kanon.kommons.LINE_SEPARATOR
import moe.kanon.kommons.writeOut
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

const val MAX_TUPLES = 24
private const val PACKAGE = "moe.kanon.kommons.func.tuples"

fun main() {
    enactWorldDomination()

    val outputDir = Paths.get("$FUNC_DIR/tuples/").toAbsolutePath()

    generateInterfaces(outputDir)
    generateImplementations(outputDir)
}

private fun typeLetterFrom(index: Int): String = ALPHABET_UPPERCASE[index].toString()

private fun propertyLetterFrom(index: Int): String = ALPHABET_LOWERCASE[index].toString()

private fun generateInterfaces(dir: Path) {
    val interfaces = file(PACKAGE, "Tuples") {
        annotation<JvmMultifileClass>()
        annotation<JvmName> { addMember("\"Tuples\"") }

        indent("    ") // because the default kotlin poet indent is 2 for some reason

        `interface`("TupleMarker") {
            addKdoc(
                """
                A marker for all the available tuple implementations.
                """.trimIndent()
            )
        }

        `interface`("Tuple0") {
            implements<Serializable>()
            implements<Identifiable>()
            implements(clazz(PACKAGE, "TupleMarker"))
            addKdoc(
                """
                Represents a `null` tuple.
                
                A `null` tuple does not hold any values.
                
                @see [Null]
                """.trimIndent()
            )
        }

        for (i in 1..MAX_TUPLES) {
            // why doesn't the copy function on 'TypeVariableName' include parameters for 'name' and 'variance'?
            val types = (0 until i).map { type(typeLetterFrom(it), KModifier.OUT) }
            val rawTypes = (0 until i).map { type(typeLetterFrom(it)) }
            val newRawTypes = (0 until i).map { type("${typeLetterFrom(it)}1") }
            val nextType = type(typeLetterFrom(i))
            val nextTuple = clazz(PACKAGE, "Tuple${i + 1}")
                .parameterizedBy(*types.toTypedArray() + nextType)
            val properties = rawTypes.map { it.name.toLowerCase() }
            val curTuple = "Tuple$i"
            val curTupleClass = clazz(PACKAGE, curTuple)

            `interface`("Tuple$i") {
                implements<Serializable>()
                implements<Identifiable>()
                implements(clazz(PACKAGE, "TupleMarker"))

                addKdoc(
                    """
                    |Represents an abstract implementation of a $i-tuple.
                    |
                    |Whether or not there is any meaning attached to the values represented by this tuple is implementation specific.
                    |
                    |@see [${TUPLE_NAMES[i]}]
                    |
                    |${buildString {
                        for ((j, type) in types.withIndex()) {
                            appendln("@property [${type.name.toLowerCase()}] The ${(j + 1).toOrdinalWords()} value of `this` tuple.")
                        }
                    }}
                    """.trimMargin()
                )

                for (type in types) {
                    addTypeVariable(type)
                    addProperty(type.name.toLowerCase(), type)
                }

                if (i == MAX_TUPLES) {
                    func("to") {
                        annotation<JvmDefault>()
                        annotation<Suppress> { addMember("\"DeprecatedCallableAddReplaceWith\"") }
                        annotation<Deprecated> {
                            addMember("\"You are now creating a TupleN class\"")
                        }

                        addKdoc("Returns a new [TupleN] that contains the values of `this` and [that].")

                        addModifiers(KModifier.INFIX)
                        addParameter("that", type("Any?"))
                        returns(type("TupleN"))
                        addStatement("return TupleN(${types.joinToString { it.name.toLowerCase() }}, that)")
                    }
                } else {
                    func("to") {
                        annotation<JvmDefault>()
                        addKdoc(
                            """
                            |Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
                            |
                            |Note that whether or not this function is supported is implementation specific, and a class that implements 
                            |`this` may choose to not provide an implementation.
                            """.trimMargin()
                        )
                        addTypeVariable(nextType)
                        addModifiers(KModifier.INFIX)
                        addParameter("that", nextType)
                        addStatement("return throw %T()", UnsupportedOperationException::class)
                        returns(nextTuple)
                    }
                }

                for ((j, type) in types.withIndex()) {
                    func("component${j + 1}") {
                        annotation<JvmDefault>()
                        addModifiers(KModifier.OPERATOR)
                        addStatement("return ${propertyLetterFrom(j)}")
                        returns(type)
                    }
                }
            }

            when (i) {
                2 -> {
                    func("toPair") {
                        addTypeVariables(rawTypes)
                        addKdoc("Returns a new [Pair] based on the values of `this` tuple.")
                        receiver(curTupleClass.parameterizedBy(*rawTypes.toTypedArray()))
                        returns(Pair::class.asClassName().parameterizedBy(*rawTypes.toTypedArray()))
                        addStatement("return Pair(${properties.joinToString()})")
                    }

                    func("not") {
                        annotation<JvmName> { addMember("\"toKotlin\"") }
                        addTypeVariables(rawTypes)
                        addModifiers(KModifier.OPERATOR)
                        addKdoc("Returns a new [Pair] based on the values of `this` tuple.")
                        receiver(curTupleClass.parameterizedBy(*rawTypes.toTypedArray()))
                        returns(Pair::class.asClassName().parameterizedBy(*rawTypes.toTypedArray()))
                        addStatement("return toPair()")
                    }
                }
                3 -> {
                    func("toTriple") {
                        addTypeVariables(rawTypes)
                        addKdoc("Returns a new [Triple] based on the values of `this` tuple.")
                        receiver(curTupleClass.parameterizedBy(*rawTypes.toTypedArray()))
                        returns(Triple::class.asClassName().parameterizedBy(*rawTypes.toTypedArray()))
                        addStatement("return Triple(${properties.joinToString()})")
                    }

                    func("not") {
                        annotation<JvmName> { addMember("\"toKotlin\"") }
                        addModifiers(KModifier.OPERATOR)
                        addTypeVariables(rawTypes)
                        addKdoc("Returns a new [Triple] based on the values of `this` tuple.")
                        receiver(curTupleClass.parameterizedBy(*rawTypes.toTypedArray()))
                        returns(Triple::class.asClassName().parameterizedBy(*rawTypes.toTypedArray()))
                        addStatement("return toTriple()")
                    }
                }
            }

            if (i == MAX_TUPLES) {
                func("plus") {
                    addKdoc("Returns a new [TupleN] that contains the values of `this` and [that].")
                    annotation<JvmName> { addMember("\"combine\"") }
                    addTypeVariables(rawTypes + nextType)
                    receiver(curTupleClass.parameterizedBy(*rawTypes.toTypedArray()))
                    addModifiers(KModifier.OPERATOR, KModifier.INFIX)
                    addParameter("that", type("Any?"))
                    returns(type("TupleN"))
                    addStatement("return this to that")
                }
            } else {
                func("plus") {
                    addKdoc(
                        """
                    |Returns a new [tuple][Tuple2] from the values of `this` tuple and [that].
                    |
                    |Note that whether or not this function is supported is implementation specific, and a class that implements 
                    |`this` may choose to not provide an implementation.
                    """.trimMargin()
                    )
                    annotation<JvmName> { addMember("\"combine\"") }
                    receiver(curTupleClass.parameterizedBy(*rawTypes.toTypedArray()))
                    addTypeVariables(rawTypes + nextType)
                    addModifiers(KModifier.OPERATOR, KModifier.INFIX)
                    addParameter("that", nextType)
                    addStatement("return this to that")
                    returns(nextTuple)
                }
            }

            //val nullableAny = Any::class.asTypeName().copy(nullable = true)

            // make functions for tuples to list and map
            func("toList") {
                annotation<JvmName> { addMember("\"collect\"") }
                addKdoc("Returns a new list containing all the values of `this` tuple.")
                val listType = type("T")
                addTypeVariable(listType)
                returns(List::class.asTypeName().parameterizedBy(listType))

                receiver(curTupleClass.parameterizedBy(*Array(i) { listType }))

                val params = (0 until i).joinToString { propertyLetterFrom(it) }

                addStatement("return listOf($params)")
            }

            if (i.isEven) {
                func("toMap") {
                    annotation<JvmName> { addMember("\"group\"") }
                    addKdoc(
                        """
                        |Returns a new map containing the values of `this` tuple.
                        |
                        |The entries of the resulting map are created by grouping the values at a odd index with the values at a even index.
                    """.trimMargin()
                    )
                    val mapTypes = arrayOf(type("K"), type("V"))

                    addTypeVariables(mapTypes.asIterable())
                    returns(Map::class.asTypeName().parameterizedBy(*mapTypes))

                    receiver(curTupleClass.parameterizedBy(*Array(i) { if (it.isOdd) mapTypes[1] else mapTypes[0] }))

                    val params = (0 until i).map { it to propertyLetterFrom(it) }.groupBy { (j, _) -> j.isEven }.let {
                        val keys = it.getValue(true).map { (_, l) -> l }
                        val values = it.getValue(false).map { (_, l) -> l }
                        List(keys.size) { j -> keys[j] to values[j] }
                    }.joinToString { "${it.first} to ${it.second}" }

                    //writeOut(params)

                    addStatement("return mapOf($params)")
                }
            }
        }
    }

    // some additional cleaning needs to be done on the output we receive from kotlin poet, missing some features for
    // allowing proper insertion of multi-line comments at logical places.

    val content = buildString {
        appendln(COPYRIGHT_HEADER)
        interfaces.write(this)
    }.split("\n") // because kotlin poet uses '\n' rather than 'System.getProperty("line.separator")' for some reason

    val comment = """
        |/*
        | * The auto-generated tuple classes all explicitly define some utility like functions inside of them, one of these is
        | * the 'to' function. The reason this function is explicitly defined inside of each tuple class rather than having
        | * them be 'extension' functions like they are in the standard library is to circumvent the 'to' infix function
        | * defined by the standard library.
        | *      Due to how the standard library works (all of the std-lib is always automatically imported in all classes) we
        | * can't reliably define a extension function called 'to', as the chances are very high that the 'to' extension
        | * function defined for creating 'Pair' instances will win the resolution battle. As that function also does the work
        | * on two generic parts, that means that unless we explicitly defined the 'to' function inside each tuple, we would
        | * need to have to result to doing something like 'toT' *(which is what we use to create the initial 'Duad' instance)*
        | * for each tuple instance to keep on chaining them, or use a different syntax all-together. But if we explicitly
        | * define them as a member of the tuple class we win the resolution battle, this is because extension functions are
        | * resolved statically and are not actual parts of the classes they work on. This means that an extension function
        | * will *always* lose the resolution battle against an actual member of the class.
        | *      The equivalent tuple classes for 'Pair' and 'Triple' *('Tuple2/Duad' and 'Tuple3/Triad' respectively)* have also
        | * been given functions for converting to their std-lib implementations to somewhat ease compatibility. They have also
        | * been given the 'not' operator to make the syntax even cleaner, this operator is entirely optional, and should only
        | * be used if you are sure that using it would not create reading issues, as it is a very subtle operator. There also
        | * exists extension functions for 'Pair' and 'Triple' to convert them to their respective kommons implementations,
        | * these also have the 'not' operator available for them.
        | */
    """.trimMargin().split(LINE_SEPARATOR).toTypedArray()

    val toWrite = content.shank(
        "",
        "// AUTO GENERATED, DO NOT EDIT",
        "",
        *comment,
        startIndex = content.indexOfLast { it.startsWith("import") } + 1
    )

    val file = Files.write(
        dir.resolve("Tuples.kt"),
        toWrite,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
    )
    writeOut(file)
}

private fun generateImplementations(dir: Path) {
    val nullableAny = Any::class.asTypeName().copy(nullable = true)

    val implementations = file(PACKAGE, "TuplesImpl") {
        annotation<JvmMultifileClass>()
        annotation<JvmName> { addMember("\"Tuples\"") }

        `object`("Null") {
            addKdoc("Represents a generic implementation of a [null-tuple][Tuple0].")

            implements(clazz(PACKAGE, "Tuple0"))

            func("equals") {
                addModifiers(KModifier.OVERRIDE)
                param("other", nullableAny)
                addStatement("return other is Null")
                returns<Boolean>()
            }

            func("hashCode") {
                addModifiers(KModifier.OVERRIDE)
                addStatement("return 0")
                returns<Int>()
            }

            func("toString") {
                addModifiers(KModifier.OVERRIDE)
                addStatement("return \"Null\"")
                returns<String>()
            }
        }

        for (i in 1..MAX_TUPLES) {
            val types = (0 until i).map { type(typeLetterFrom(it), KModifier.OUT) }
            val rawTypes = (0 until i).map { type(typeLetterFrom(it)) }
            val newRawTypes = (0 until i).map { type("${typeLetterFrom(it)}1") }
            val nextType = type(typeLetterFrom(i))
            val properties = rawTypes.map { it.name.toLowerCase() }
            val params = properties.joinToString()
            //class
            val curTuple = TUPLE_NAMES.getValue(i)
            val curTupleClass = clazz(PACKAGE, curTuple).parameterizedBy(*rawTypes.toTypedArray())
            val nextTuple = TUPLE_NAMES.getValue(i + 1)
            val nextTupleClass = clazz(PACKAGE, nextTuple).parameterizedBy(*types.toTypedArray() + nextType)
            //interface
            val nextTupleInterface = clazz(PACKAGE, "Tuple${i + 1}")
                .parameterizedBy(*types.toTypedArray() + nextType)
            val curTupleInterface = "Tuple$i"
            val curTupleInterfaceClass = clazz(PACKAGE, curTupleInterface).parameterizedBy(*rawTypes.toTypedArray())

            `class`(curTuple) {
                addKdoc(
                    """
                    |Represents a generic implementation of a [$i-tuple][$curTupleInterface].
                    |
                    |There is no meaning attached to values in this class, it can be used for any purpose.
                    |
                    |`${curTuple}s` exhibits value semantics, i.e. two `${curTuple}s` are equal if all ${i.toWords()} components are equal.
                    """.trimMargin()
                )
                addModifiers(KModifier.DATA)
                addTypeVariables(types)
                implements(curTupleInterfaceClass)
                primaryDataConstructor(
                    { for (prop in properties) param(prop, type(prop.toUpperCase())) },
                    { addModifiers(KModifier.OVERRIDE) }
                )

                if (i != MAX_TUPLES) {
                    func("to") {
                        addKdoc("Returns a new [tuple][$nextTuple] from the values of `this` tuple and [that].")
                        addTypeVariable(nextType)
                        addModifiers(KModifier.OVERRIDE, KModifier.INFIX)
                        addParameter("that", nextType)
                        addStatement("return $nextTuple($params, that)")
                        returns(nextTupleInterface)
                    }
                }

                func("toString") {
                    addModifiers(KModifier.OVERRIDE)
                    addStatement("return \"(${ properties.joinToString { "${'$'}$it" } })\"")
                    returns<String>()
                }
            }

            // extension functions
            when (i) {
                2 -> {
                    func("toT") {
                        addKdoc("Returns a new [tuple][$curTuple] containing `this` and [that].")
                        addModifiers(KModifier.INFIX)
                        addTypeVariables(rawTypes)
                        receiver(rawTypes[0])
                        returns(curTupleInterfaceClass)
                        param("that", rawTypes[1])
                        addStatement("return $curTuple(this, that)")
                    }

                    func("toTuple") {
                        addKdoc("Returns a new [tuple][$curTuple] containing the values of `this` pair.")
                        addTypeVariables(rawTypes)
                        receiver(Pair::class.asTypeName().parameterizedBy(*rawTypes.toTypedArray()))
                        returns(curTupleInterfaceClass)
                        addStatement("return $curTuple(first, second)")
                    }

                    func("not") {
                        addKdoc("Returns a new [tuple][$curTuple] containing the values of `this` pair.")
                        annotation<JvmName> { addMember("\"fromPair\"") }
                        addModifiers(KModifier.OPERATOR)
                        addTypeVariables(rawTypes)
                        receiver(Pair::class.asTypeName().parameterizedBy(*rawTypes.toTypedArray()))
                        returns(curTupleInterfaceClass)
                        addStatement("return toTuple()")
                    }
                }
                3 -> {
                    func("toTuple") {
                        addKdoc("Returns a new [tuple][$curTuple] containing the values of `this` triple.")
                        addTypeVariables(rawTypes)
                        receiver(Triple::class.asTypeName().parameterizedBy(*rawTypes.toTypedArray()))
                        returns(curTupleInterfaceClass)
                        addStatement("return $curTuple(first, second, third)")
                    }

                    func("not") {
                        addKdoc("Returns a new [tuple][$curTuple] containing the values of `this` triple.")
                        annotation<JvmName> { addMember("\"fromTriple\"") }
                        addModifiers(KModifier.OPERATOR)
                        addTypeVariables(rawTypes)
                        receiver(Triple::class.asTypeName().parameterizedBy(*rawTypes.toTypedArray()))
                        returns(curTupleInterfaceClass)
                        addStatement("return toTuple()")
                    }
                }
            }

            /*func("fold") {
                addKdoc("Returns the result of applying the values of `this` tuple to the [transformer].")
                addModifiers(KModifier.INLINE)
                receiver(curTupleInterfaceClass)
                val result = type("R1")
                addTypeVariables(rawTypes + result)
                val fncParams = types.map { ParameterSpec.builder(it.name.toLowerCase(), it).build() }.toTypedArray()
                param("transformer", lambda(*fncParams, returns = result))
                returns(result)
                addStatement("return transformer($params)")
            }

            func("flatMap") {
                addKdoc("Returns the result of applying the values of `this` tuple to the tuple bearing [transformer].")
                addModifiers(KModifier.INLINE)
                addTypeVariables(rawTypes + newRawTypes)
                receiver(curTupleInterfaceClass)
                val fncParams = types.map { ParameterSpec.builder(it.name.toLowerCase(), it).build() }.toTypedArray()
                val result = clazz(PACKAGE, curTupleInterface).parameterizedBy(*newRawTypes.toTypedArray())
                param("transformer", lambda(*fncParams, returns = result))
                returns(result)
                addStatement("return transformer($params)")
            }

            val predicate = lambda<Boolean>(*types.map {
                ParameterSpec.builder(it.name.toLowerCase(), it).build()
            }.toTypedArray())

            func("any") {
                addKdoc("Returns `true` if the [predicate] supplied with the values of `this` tuple passes, or `false` otherwise.")
                addModifiers(KModifier.INLINE)
                addTypeVariables(rawTypes)
                receiver(curTupleInterfaceClass)
                param("predicate", predicate)
                returns<Boolean>()
                addStatement("return predicate($params)")
            }

            func("none") {
                addKdoc("Returns `false` if the [predicate] supplied with the values of `this` tuple passes, or `true` otherwise.")
                addModifiers(KModifier.INLINE)
                addTypeVariables(rawTypes)
                receiver(curTupleInterfaceClass)
                param("predicate", predicate)
                returns<Boolean>()
                addStatement("return !predicate($params)")
            }*/

            func("zipWith") {
                addModifiers(KModifier.INFIX)
                addKdoc("Returns a [tuple][Tuple2] that contains `this` tuple and [that] tuple.")
                addTypeVariables(rawTypes + newRawTypes)
                receiver(curTupleInterfaceClass)
                val nTuple = clazz(PACKAGE, curTupleInterface).parameterizedBy(*newRawTypes.toTypedArray())
                param("that", nTuple)
                val nParams = properties.joinToString { "that.$it" }
                returns(
                    clazz(PACKAGE, "Tuple2").parameterizedBy(
                        clazz(curTupleInterface).parameterizedBy(*rawTypes.toTypedArray()),
                        nTuple
                    )
                )
                addStatement("return this toT that")
            }
        }

        `object`("Tuple") {
            for (i in 1..MAX_TUPLES) {
                val rawTypes = (0 until i).map { type(typeLetterFrom(it)) }
                val properties = rawTypes.map { it.name.toLowerCase() }
                val params = properties.joinToString()
                val curTuple = TUPLE_NAMES.getValue(i)
                val curTupleInterface = "Tuple$i"
                val curTupleInterfaceClass = clazz(PACKAGE, curTupleInterface).parameterizedBy(*rawTypes.toTypedArray())

                func("invoke") {
                    addKdoc("Returns a new [tuple][$curTupleInterface] containing the specified values.")
                    annotation<JvmStatic>()
                    annotation<JvmName> { addMember("\"of\"") }
                    addTypeVariables(rawTypes)
                    returns(curTupleInterfaceClass)
                    for (param in properties) param(param, type(param.toUpperCase()))
                    addStatement("return $curTuple($params)")
                }
            }

            func("invoke") {
                addKdoc(
                    """
                    |Returns a new [tuple][TupleN] containing the specified [values].
                    |
                    |Note that unlike the other factory functions in here, this function returns a [TupleN] rather than
                    |a concrete tuple implementation.
                    """.trimMargin()
                )
                annotation<JvmStatic>()
                annotation<JvmName> { addMember("\"of\"") }
                addModifiers(KModifier.OPERATOR)
                param("values", nullableAny, KModifier.VARARG)
                returns(clazz(PACKAGE, "TupleN"))
                addStatement("return TupleN(*values)")
            }
        }
    }

    val content = buildString {
        appendln(COPYRIGHT_HEADER)
        implementations.write(this)
    }.split("\n")

    val toWrite = content.shank(
        "",
        "// AUTO GENERATED, DO NOT EDIT",
        startIndex = content.indexOfLast { it.startsWith("import") } + 1
    )

    val file = Files.write(
        dir.resolve("TuplesImpl.kt"),
        toWrite,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
    )
    writeOut(file)
}