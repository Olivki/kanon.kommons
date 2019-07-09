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

@file:JvmName("KInputStreams")

package moe.kanon.kommons.io

import moe.kanon.kommons.requireThat
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import kotlin.math.min


private const val MAX_SKIP_BUFFER_SIZE = 2048
private const val DEFAULT_BUFFER_SIZE = 8192
private const val MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8

/**
 * Reads up to a [specified number][len] of bytes from the input stream.
 *
 * This function blocks until the requested number of bytes have been read, end of stream is detected, or an exception
 * is thrown. This method does not close the input stream.
 *
 * The length of the returned array equals the number of bytes read from `this` stream. If [len] is zero, then no
 * bytes are read and an empty byte array is returned. Otherwise, up to `len` bytes are read from the stream. Fewer
 * than `len` bytes may be read if end of stream is encountered.
 *
 * When `this` stream reaches end of stream, further invocations of this function will return an empty byte array.
 *
 * Note that this function is intended for simple cases where it is convenient to read the specified number of bytes
 * into a byte array. The total amount of memory allocated by this method is proportional to the number of bytes read
 * from the stream which is bounded by `len`. Therefore, the method may be safely called with very large values of
 * `len` provided sufficient memory is available.
 *
 * The behavior for the case where the input stream is *asynchronously closed*, or the thread interrupted during the
 * read, is highly input stream specific, and therefore not specified.
 *
 * If an I/O error occurs reading from the input stream, then it may do so after some, but not all, bytes have been
 * read. Consequently the input stream may not be at end of stream and may be in an inconsistent state. It is strongly
 * recommended that the stream be promptly closed if an I/O error occurs.
 *
 * **NOTE:** The number of bytes allocated to read data from this stream and return the result is bounded by
 * `2*(long)len`, inclusive.
 *
 * @param [len] the maximum number of bytes to read

 * @return a byte array containing the bytes read from this input stream

 * @throws IllegalArgumentException if [len] is negative
 * @throws IOException if an I/O error occurs
 * @throws OutOfMemoryError if an array of the required size cannot be allocated.
 */
// https://hg.openjdk.java.net/jdk/jdk12/file/06222165c35f/src/java.base/share/classes/java/io/InputStream.java#l389
@Throws(IOException::class)
fun InputStream.readNBytes(len: Int): ByteArray {
    requireThat(len < 0) { "len < 0" }

    var buffers: MutableList<ByteArray>? = null
    var result: ByteArray? = null
    var total = 0
    var remaining = len
    var amount: Int

    do {
        val buffer = ByteArray(min(remaining, DEFAULT_BUFFER_SIZE))
        var nRead = 0

        // read to EOF which may read more or less than buffer size
        while (this.read(buffer, nRead, min(buffer.size - nRead, remaining)).also { amount = it } > 0) {
            nRead += amount
            remaining -= amount
        }

        if (nRead > 0) {
            if (MAX_BUFFER_SIZE - total < nRead) throw OutOfMemoryError("Required array size too large")

            total += nRead

            when (result) {
                null -> result = buffer
                else -> {
                    if (buffers == null) {
                        buffers = ArrayList()
                        buffers.add(result)
                    }

                    buffers.add(buffer)
                }
            }

        }
        // if the last call to read returned -1 or the number of bytes requested have been read then break
    } while (amount >= 0 && remaining > 0)

    if (buffers == null) return when (result) {
        null -> ByteArray(0)
        else -> if (result.size == total) result else result.copyOf(total)
    }

    result = ByteArray(total)
    var offset = 0
    remaining = total

    for (buffer in buffers) {
        val count = min(buffer.size, remaining)

        System.arraycopy(buffer, 0, result, offset, count)

        offset += count
        remaining -= count
    }

    return result
}

/**
 * Reads the requested number of bytes from `this` input stream into the given [byte array][array].
 *
 * This function blocks until [length] bytes of input data have  been read, end of stream is detected, or an exception
 * is thrown. The number of bytes actually read, possibly zero, is returned. This function does not close the input
 * stream.
 *
 * In the case where end of stream is reached before `length` bytes  have been read, then the actual number of bytes
 * read will be returned. When this stream reaches end of stream, further invocations of this function will return zero.
 *
 * If `length` is zero, then no bytes are read and `0` is returned; otherwise, there is an attempt to read up to
 * `length` bytes.
 *
 * The first byte read is stored into element `array[offset]`, the next one in to `array[offset + 1]`, and so on. The
 * number of bytes read is, at most, equal to `length`. Let *k* be the number of bytes actually read; these bytes will
 * be stored in elements `array[offset]` through `array[offset + k - 1]`, leaving elements `array[offset + k]` through
 * `array[offset + length - 1]` unaffected.
 *
 * The behavior for the case where the input stream is *asynchronously closed*, or the thread interrupted during the
 * read, is highly input stream specific, and therefore not specified.
 *
 * If an I/O error occurs reading from the input stream, then it may do so after some, but not all, bytes of [array]
 * have been updated with data from the input stream. Consequently the input stream and `array` may be in an
 * inconsistent state. It is strongly recommended that the  stream be promptly closed if an I/O error occurs.
 *
 * @param [array] the byte array into which the data is read
 * @param [offset] the start offset in [array] at which the data is written
 * @param [length] the maximum number of bytes to read
 *
 * @return the actual number of bytes read into the buffer
 *
 * @throws [IOException] if an I/O error occurs
 * @throws [IllegalArgumentException] If [offset] is negative, [length] is negative, or [length] is greater than
 * `array.size - offset`
 */
// https://hg.openjdk.java.net/jdk/jdk12/file/06222165c35f/src/java.base/share/classes/java/io/InputStream.java#l493
@Throws(IOException::class)
fun InputStream.readNBytes(array: ByteArray, offset: Int, length: Int): Int {
    requireThat((array.size or offset or length) < 0 || length > (array.size - offset))

    var amount = 0

    while (amount < length) {
        val count = this.read(array, offset + amount, length - amount)
        if (count < 0) break
        amount += count
    }

    return amount
}