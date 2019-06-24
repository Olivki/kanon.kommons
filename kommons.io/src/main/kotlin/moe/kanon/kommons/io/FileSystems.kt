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

@file:JvmName("KFileSystems")

package moe.kanon.kommons.io

import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.PathMatcher

/**
 * Returns a [PathMatcher] that performs match operations on the [String] representation of [Path] objects by
 * interpreting a given pattern.
 *
 * **Note:** This function simply just invokes the [getPathMatcher][FileSystem.getPathMatcher] function of the
 * [default-file-system][FileSystems.getDefault], with the `syntaxAndPattern` parameter set to
 * "[syntax]**:**[pattern]".
 *
 * A [FileSystem] implementation supports the "`glob`" and "`regex`" syntaxes, and may support others. The value of the
 * syntax component is compared without regard to case.
 *
 * When the syntax is "`glob`" then the `String` representation of the path is matched using a limited pattern language
 * that resembles regular expressions but with a simpler syntax.
 *
 * For example:
 *
 * **The table that's supposed to be here is missing due to dokka not supporting markdown tables, please refer to
 * [FileSystem.getPathMatcher] for the actual proper documentation.**
 *
 * The following rules are used to interpret glob patterns:
 *
 * - The `*` character matches *zero* or *more* [characters][Char] of a [name][Path.getName] component without
 * crossing directory boundaries
 * - The `**` character matches *zero* or *more* [characters][Char] crossing directory boundaries.
 * - The `?` character matches exactly one character of a name component.
 * - The backslash character `\` is used to escape characters that would otherwise be interpreted as special characters.
 * The expression `\\` matches a single backslash and "`\{`" matches a left brace for example.
 * - The `[ ]` characters characters are a *bracket expression* that match a single character of a name component
 * out of a set of characters. For example, `[abc]` matches `"a"`, `"b"`, or `"c"`. The hyphen (`-`) may be used
 * to specify a range so `[a-z]` specifies a range that matches from `"a"` to `"z"` *(inclusive)*. These forms can be
 * mixed so `[abce-g]` matches `"a"`, `"b"`, `"c"`, `"e"`, `"f"` or `"g"`. If the character after the `[` is a `!` then
 * it is used for negation so `[!a-c]` matches any character except `"a"`, `"b"`, or `"c"`.
 *
 *    Within a bracket expression the `*`, `?` and `\` characters match themselves. The (`-`) character matches itself if
 *    it is the first character within the brackets, or the first character after the `!` if negating.
 * - The `{ }` characters are a group of sub-patterns, where the group matches if any sub-pattern in the group matches.
 * The `","` character is used to separate the sub-patterns. Groups cannot be nested.
 * - Leading period/dot characters in file name are treated as regular characters in match operations. For example,
 * the `"*"` glob pattern matches file name `".login"`. The [Path.isHidden] property may be used to test whether a file
 * is considered hidden.
 * - All other characters match themselves in an implementation dependent manner. This includes characters representing
 * any [name-separators][FileSystem.getSeparator].
 * - The matching of [root][Path.getRoot] components is highly implementation-dependent and is not specified.
 *
 * When the syntax is "`regex`" then the pattern component is a regular expression as defined by the
 * [Pattern][java.util.regex.Pattern] class.
 *
 * For both the glob and regex syntaxes, the matching details, such as whether the matching is case sensitive, are
 * implementation-dependent and therefore not specified.
 *
 * @param syntax the syntax to use
 *
 * @param pattern the pattern to use
 *
 * @return a path matcher that may be used to match paths against the pattern
 *
 * @throws java.util.regex.PatternSyntaxException if the pattern is invalid
 * @throws UnsupportedOperationException if the pattern syntax is not known to the implementation
 *
 * @since 0.6.0
 *
 * @see Path.newDirectoryStream
 * @see FileSystem.getPathMatcher
 */
fun pathMatcherOf(syntax: String, pattern: String): PathMatcher =
    FileSystems.getDefault().getPathMatcher("$syntax:$pattern")

/**
 * Returns a [PathMatcher] that performs match operations on the [String] representation of [Path] objects by
 * interpreting a given pattern.
 *
 * **Note:** This function simply just invokes the [getPathMatcher][FileSystem.getPathMatcher] function of `this`
 * [file-system][FileSystem], with the `syntaxAndPattern` parameter set to "[syntax]**:**[pattern]".
 *
 * A [FileSystem] implementation supports the "`glob`" and "`regex`" syntaxes, and may support others. The value of the
 * syntax component is compared without regard to case.
 *
 * When the syntax is "`glob`" then the `String` representation of the path is matched using a limited pattern language
 * that resembles regular expressions but with a simpler syntax.
 *
 * For example:
 *
 * **The table that's supposed to be here is missing due to dokka not supporting markdown tables, please refer to
 * [FileSystem.getPathMatcher] for the actual proper documentation.**
 *
 * The following rules are used to interpret glob patterns:
 *
 * - The `*` character matches *zero* or *more* [characters][Char] of a [name][Path.getName] component without
 * crossing directory boundaries
 * - The `**` character matches *zero* or *more* [characters][Char] crossing directory boundaries.
 * - The `?` character matches exactly one character of a name component.
 * - The backslash character `\` is used to escape characters that would otherwise be interpreted as special characters.
 * The expression `\\` matches a single backslash and "`\{`" matches a left brace for example.
 * - The `[ ]` characters characters are a *bracket expression* that match a single character of a name component
 * out of a set of characters. For example, `[abc]` matches `"a"`, `"b"`, or `"c"`. The hyphen (`-`) may be used
 * to specify a range so `[a-z]` specifies a range that matches from `"a"` to `"z"` *(inclusive)*. These forms can be
 * mixed so `[abce-g]` matches `"a"`, `"b"`, `"c"`, `"e"`, `"f"` or `"g"`. If the character after the `[` is a `!` then
 * it is used for negation so `[!a-c]` matches any character except `"a"`, `"b"`, or `"c"`.
 *
 *    Within a bracket expression the `*`, `?` and `\` characters match themselves. The (`-`) character matches itself if
 *    it is the first character within the brackets, or the first character after the `!` if negating.
 * - The `{ }` characters are a group of sub-patterns, where the group matches if any sub-pattern in the group matches.
 * The `","` character is used to separate the sub-patterns. Groups cannot be nested.
 * - Leading period/dot characters in file name are treated as regular characters in match operations. For example,
 * the `"*"` glob pattern matches file name `".login"`. The [Path.isHidden] property may be used to test whether a file
 * is considered hidden.
 * - All other characters match themselves in an implementation dependent manner. This includes characters representing
 * any [name-separators][FileSystem.getSeparator].
 * - The matching of [root][Path.getRoot] components is highly implementation-dependent and is not specified.
 *
 * When the syntax is "`regex`" then the pattern component is a regular expression as defined by the
 * [Pattern][java.util.regex.Pattern] class.
 *
 * For both the glob and regex syntaxes, the matching details, such as whether the matching is case sensitive, are
 * implementation-dependent and therefore not specified.
 *
 * @param syntax the syntax to use
 * @param pattern the pattern to use
 *
 * @return a path matcher that may be used to match paths against the pattern
 *
 * @throws java.util.regex.PatternSyntaxException if the pattern is invalid
 * @throws UnsupportedOperationException if the pattern syntax is not known to the implementation
 *
 * @since 0.6.0
 *
 * @see Path.newDirectoryStream
 * @see FileSystem.getPathMatcher
 */
fun FileSystem.pathMatcherOf(syntax: String, pattern: String): PathMatcher = this.getPathMatcher("$syntax:$pattern")
