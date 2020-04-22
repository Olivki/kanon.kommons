/*
 * Copyright 2019-2020 Oliver Berg
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

package moe.kanon.kommons.lang

import java.time.Duration

/**
 * A structure for implementing operations that will only execute when a certain amount of time has passed.
 *
 * @property [threshold] The threshold that needs to be passed for this to execute anything.
 */
@Deprecated(message = "Class does not belong in kommons.lang.")
data class TimedExecutor(val threshold: Long) {
    constructor(duration: Duration) : this(duration.toMillis())

    private var lastPoll: Long = 0L

    /**
     * Returns whether or not the specified [threshold] has been passed in time.
     */
    val canExecute: Boolean
        @JvmName("canExecute")
        get() = ((System.nanoTime() / 1_000_000) - lastPoll) >= threshold

    /**
     * Refreshes the [lastPoll] property to be set to the current nano-time.
     */
    fun refreshPolling() {
        lastPoll = (System.nanoTime() / 1_000_000)
    }

    /**
     * Executes the specified [action] only if [can be executed][canExecute] and then refreshes the [lastPoll] property.
     */
    inline fun execute(action: () -> Unit) {
        if (canExecute) {
            action()
            refreshPolling()
        }
    }

    override fun toString(): String = "TimedExecutor(threshold=$threshold, lastPoll=$lastPoll)"
}