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
 * ========================= CEYLON LICENSE =========================
 * Copyright (c) 2011-2017 Red Hat Inc. and/or its affiliates and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package moe.kanon.kommons

/**
 * Represents a class that has *explicit* custom implementations of the `equals`, `hashCode`, and `toString` functions.
 *
 * ### Port-Of Links
 * 1. [ceylon.language.Identifiable](https://github.com/eclipse/ceylon/blob/master/language/src/ceylon/language/Identifiable.ceylon)
 */
// TODO: Function documentation
@MarkerType
@PortOf("ceylon.language.Identifiable")
interface Identifiable : Representable {
    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}