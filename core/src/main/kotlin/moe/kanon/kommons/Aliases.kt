package moe.kanon.kommons

/**
 * Represents a [Map] where the backing type for the [keys][Map.keys] is always a [String].
 */
typealias StringMap<T> = Map<String, T>

@Deprecated(
    message = "Does not work, use 'JvmStatic' instead.",
    replaceWith = ReplaceWith("JvmStatic", "kotlin.jvm"),
    level = DeprecationLevel.ERROR
)
typealias static = JvmStatic

@Deprecated(
    message = "Does not work, use 'JvmSynthetic' instead.",
    replaceWith = ReplaceWith("JvmSynthetic", "kotlin.jvm"),
    level = DeprecationLevel.ERROR
)
typealias synthetic = JvmSynthetic

@Deprecated(
    message = "Does not work, use 'JvmName' instead.",
    replaceWith = ReplaceWith("JvmName", "kotlin.jvm"),
    level = DeprecationLevel.ERROR
)
typealias name = JvmName

@Deprecated(
    message = "Does not work, use 'JvmOverloads' instead.",
    replaceWith = ReplaceWith("JvmOverloads", "kotlin.jvm"),
    level = DeprecationLevel.ERROR
)
typealias overloads = JvmOverloads