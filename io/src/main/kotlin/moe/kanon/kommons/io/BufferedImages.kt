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

@file:JvmName("KBufferedImages")

package moe.kanon.kommons.io

import moe.kanon.kommons.io.paths.extension
import moe.kanon.kommons.io.paths.newInputStream
import moe.kanon.kommons.io.paths.newOutputStream
import org.imgscalr.Scalr
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImageOp
import java.awt.image.ConvolveOp
import java.awt.image.RescaleOp
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.StandardOpenOption.CREATE
import java.nio.file.StandardOpenOption.READ
import java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
import java.nio.file.StandardOpenOption.WRITE
import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream
import javax.imageio.stream.ImageOutputStream

/**
 * Returns a newly created [BufferedImage], which will have the given [width] and [height], containing only the given
 * [color].
 */
fun createBufferedImage(width: Int, height: Int, color: Color = Color.BLACK): BufferedImage =
    BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB).apply {
        createGraphics().apply {
            setColor(color)
            fillRect(0, 0, width, height)
            dispose()
        }
    }

/**
 * [Creates a buffered-image][createBufferedImage] of the given [width] and [height], containing only the given [color],
 * and then writes it to the given [target] using the given [options].
 */
fun writeBlankImageTo(
    target: Path,
    width: Int,
    height: Int,
    color: Color = Color.BLACK,
    vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)
): Path = createBufferedImage(width, height, color).writeTo(target, "png", *options)

/**
 * Returns a new [BufferedImage] based on the contents of `this` file.
 *
 * @see [ImageIO.read]
 */
fun Path.toBufferedImage(vararg options: OpenOption = arrayOf(READ)): BufferedImage =
    this.newInputStream(*options).use { ImageIO.read(it) }

/**
 * Returns a new [ImageInputStream] to `this` file.
 *
 * @see [ImageIO.createImageInputStream]
 */
fun Path.newImageInputStream(vararg options: OpenOption = arrayOf(READ)): ImageInputStream =
    this.newInputStream(*options).use { ImageIO.createImageInputStream(it) }

/**
 * Returns a new [ImageOutputStream] to `this` file.
 *
 * @see [ImageIO.createImageInputStream]
 */
fun Path.newImageOutputStream(
    vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)
): ImageOutputStream = this.newImageInputStream(*options).use { ImageIO.createImageOutputStream(it) }

/**
 * Writes `this` image to the given [target], using the [extension][Path.extension] of `target` as the `format`, and
 * the given [options].
 */
fun BufferedImage.writeTo(target: Path, vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)): Path =
    this.writeTo(target, target.extension?.toLowerCase() ?: "", *options)

/**
 * Writes `this` image to the given [target], using the given [format] and the given [options].
 */
fun BufferedImage.writeTo(
    target: Path,
    format: String,
    vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING)
): Path {
    target.newOutputStream(*options).use { ImageIO.write(this, format, it) }
    return target
}

/**
 * Writes `this` image to the given [target], using the given [format].
 */
fun BufferedImage.writeTo(target: OutputStream, format: String): Boolean = ImageIO.write(this, format, target)

/**
 * Returns the content of `this` image as a [ByteArray].
 */
fun BufferedImage.toBytes(): ByteArray {
    val out = ByteArrayOutputStream()
    writeTo(out, "png")
    return out.toByteArray()
}

// -- SCALR -- \\
/**
 * Used to define the different scaling hints that the algorithm can use.
 */
typealias ImageScalingMethod = Scalr.Method

/**
 * Used to define the different modes of resizing that the algorithm can use.
 */
typealias ImageResizeMode = Scalr.Mode

/**
 * Used to define the different types of rotations that can be applied to an image during a resize operation.
 */
typealias ImageRotation = Scalr.Rotation

/**
 * A [ConvolveOp] using a very light "blur" kernel that acts like an anti-aliasing filter *(softens the image a bit)*
 * when applied to an image.
 *
 * @see [Scalr.OP_ANTIALIAS]
 */
inline val OPERATION_ANTI_ALIAS: ConvolveOp
    @JvmName("getAntiAliasOperation") get() = Scalr.OP_ANTIALIAS

/**
 * A [RescaleOp] used to make any input image 10% darker.
 *
 * @see [Scalr.OP_DARKER]
 */
inline val OPERATION_DARKEN: RescaleOp
    @JvmName("getDarkenOperation") get() = Scalr.OP_DARKER

/**
 * A [RescaleOp] used to make any input image 10% brighter.
 *
 * @see [Scalr.OP_BRIGHTER]
 */
inline val OPERATION_BRIGHTEN: RescaleOp
    @JvmName("getBrightenOperation") get() = Scalr.OP_BRIGHTER

/**
 * Applies the given [operations] to `this` image.
 *
 * @see [Scalr.apply]
 */
fun BufferedImage.apply(vararg operations: BufferedImageOp): BufferedImage = Scalr.apply(this, *operations)

/**
 * Crops `this` image from the top-left corner to the given [width] and [height], and applies any of given optional
 * [operations] to the result before returning it.
 *
 * @see [Scalr.crop]
 */
fun BufferedImage.cropTo(width: Int, height: Int, vararg operations: BufferedImageOp): BufferedImage =
    Scalr.crop(this, width, height, *operations)

/**
 * Crops `this` image using the given [x], [y], [width] and [height] and applies any of given optional [operations] to
 * the result before returning it.
 *
 * @see [Scalr.crop]
 */
fun BufferedImage.cropTo(x: Int, y: Int, width: Int, height: Int, vararg operations: BufferedImageOp): BufferedImage =
    Scalr.crop(this, x, y, width, height, *operations)

/**
 * Applies the given [padding] around the edges of `this` image, using the given [color] to fill the extra padded space,
 * and then returns the result.
 *
 * @see [Scalr.pad]
 */
fun BufferedImage.pad(padding: Int, color: Color = Color.BLACK, vararg operations: BufferedImageOp): BufferedImage =
    Scalr.pad(this, padding, color, *operations)

/**
 * Resizes `this` image *(whether or not the proportions of `this` image are kept depends on the given [resizeMode])*
 * to a `width` and `height` no larger than the given [targetSize] and applies the given optional [operations] to the
 * result before returning it.
 *
 * @see [Scalr.resize]
 */
fun BufferedImage.resizeTo(
    targetSize: Int,
    scalingMethod: ImageScalingMethod = ImageScalingMethod.AUTOMATIC,
    resizeMode: ImageResizeMode = ImageResizeMode.AUTOMATIC,
    vararg operations: BufferedImageOp
): BufferedImage = Scalr.resize(this, scalingMethod, resizeMode, targetSize, targetSize, *operations)

/**
 * Resizes `this` image *(whether or not the proportions of `this` image are kept depends on the given [resizeMode])*
 * to the given [targetWidth] and [targetHeight] and applies the given optional [operations] to the result before
 * returning it.
 *
 * @see [Scalr.resize]
 */
fun BufferedImage.resizeTo(
    targetWidth: Int,
    targetHeight: Int,
    scalingMethod: ImageScalingMethod = ImageScalingMethod.AUTOMATIC,
    resizeMode: ImageResizeMode = ImageResizeMode.AUTOMATIC,
    vararg operations: BufferedImageOp
): BufferedImage = Scalr.resize(this, scalingMethod, resizeMode, targetWidth, targetHeight, *operations)

/**
 * Rotates `this` image according to the given [rotation] and applies the optional [operations] to the result before
 * returning it.
 */
fun BufferedImage.rotateTo(rotation: ImageRotation, vararg operations: BufferedImageOp): BufferedImage =
    Scalr.rotate(this, rotation, *operations)



