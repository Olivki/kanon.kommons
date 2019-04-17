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

@file:JvmMultifileClass
@file:JvmName("KDesktop")

package moe.kanon.kommons.desktop

import moe.kanon.kommons.func.optionals.None
import moe.kanon.kommons.func.optionals.Optional
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

// TODO: Move the 'launch' stuff over from 'Paths.kt' to here

/**
 * Gets the default toolkit.
 *
 * @see Toolkit.getDefaultToolkit
 */
val toolkit: Toolkit get() = Toolkit.getDefaultToolkit()

/**
 * Gets the singleton instance of the system [Clipboard] which interfaces with clipboard facilities provided by the
 * native platform.
 *
 * @see [Toolkit.getSystemClipboard]
 */
val systemClipboard: Clipboard get() = toolkit.systemClipboard

inline fun <reified T> Clipboard.data(flavor: DataFlavor): T = this.getData(flavor) as T

/**
 * Gets or sets the current system clipboard.
 *
 * If the clipboard is not available currently it will return [None].
 */
var clipboard: Optional<String>
    get() {
        val flavor = DataFlavor.stringFlavor
        return if (systemClipboard.isDataFlavorAvailable(flavor)) systemClipboard.data(flavor) else Optional.empty()
    }
    set(value) {
        systemClipboard.setContents(StringSelection(value orElse ""), null)
    }