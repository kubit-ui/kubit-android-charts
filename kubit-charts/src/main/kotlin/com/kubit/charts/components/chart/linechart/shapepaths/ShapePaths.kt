package com.kubit.charts.components.chart.linechart.shapepaths

/**
 * This file contains basic examples of paths that can be used to draw shapes on a canvas.
 *
 * This is in mainly used to draw intersection nodes using [IntersectionShape], but you can use
 * these paths in any canvas directly.
 */

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Path

/**
 * Returns a [Path] representing a square with the given [size].
 */
fun squarePath(size: Size) = Path().apply {
    val halfSizeX = size.center.x
    val halfSizeY = size.center.y
    reset()

    relativeMoveTo(-halfSizeX, -halfSizeY)
    relativeLineTo(0f, size.height)
    relativeLineTo(size.width, 0f)
    relativeLineTo(0f, -size.height)

    close()
}

/**
 * Returns a [Path] representing a cross with the given [size].
 */
fun crossPath(size: Size) = Path().apply {
    val halfSizeX = size.center.x
    val halfSizeY = size.center.y
    reset()

    relativeMoveTo(-halfSizeX, 0f)
    relativeLineTo(size.width, 0f)
    relativeMoveTo(-halfSizeX, -halfSizeY)
    relativeLineTo(0f, size.height)

    close()
}

/**
 * Returns a [Path] representing a cross with the given [size].
 */
fun trianglePath(size: Size) = Path().apply {
    val halfSizeX = size.center.x
    val halfSizeY = size.center.y
    reset()

    relativeMoveTo(-halfSizeX, halfSizeY)
    relativeLineTo(halfSizeX, -size.height)
    relativeLineTo(halfSizeX, size.height)

    close()
}
