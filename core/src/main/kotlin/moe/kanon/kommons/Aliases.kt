package moe.kanon.kommons

/**
 * Represents a [Map] where the backing type for the [keys][Map.keys] is always a [String].
 */
typealias StringMap<T> = Map<String, T>

typealias static = JvmStatic
typealias synthetic = JvmSynthetic
typealias name = JvmName
typealias overloads = JvmOverloads