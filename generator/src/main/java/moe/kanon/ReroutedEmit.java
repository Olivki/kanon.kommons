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

package moe.kanon;

import com.squareup.kotlinpoet.CodeWriter;
import com.squareup.kotlinpoet.ParameterSpec;
import kotlin.jvm.functions.Function1;

import java.util.List;

// because we can't actually reference the 'CodeWriter' type in Kotlin, so we have to do it in a round-about way and
// reference it from Java, where it's not actually hidden from our view.
public class ReroutedEmit {
    public static CodeWriter emit(List<ParameterSpec> list, CodeWriter codeWriter, boolean forceNewLines,
                                  Function1<? super ParameterSpec, kotlin.Unit> emitBlock) {
        return (CodeWriter) UtilsKt.emit(list, codeWriter, forceNewLines, emitBlock);
    }
}
