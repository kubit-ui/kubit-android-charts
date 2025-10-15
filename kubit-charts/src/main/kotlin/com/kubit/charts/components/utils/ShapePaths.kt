package com.kubit.charts.components.utils

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.cos
import kotlin.math.sin

/**
 * This file contains basic examples of paths that can be used to draw shapes on a canvas.
 *
 * This is in mainly used to draw intersection nodes using [IntersectionShape], but you can use
 * these paths in any canvas directly.
 */

/**
 * Returns a [Path] representing a square with the given [size].
 */
fun squarePath(size: Size, centered: Boolean) = Path().apply {
    val halfSizeX = size.center.x
    val halfSizeY = size.center.y
    reset()
    relativeLineTo(0f, size.height)
    relativeLineTo(size.width, 0f)
    relativeLineTo(0f, -size.height)
    close()
    if (centered) {
        translate(Offset(-halfSizeX, -halfSizeY))
    }
}

/**
 * Returns a [Path] representing a cross with the given [size].
 */
fun crossPath(size: Size, centered: Boolean) = Path().apply {
    val halfSizeX = size.center.x
    val halfSizeY = size.center.y
    reset()
    relativeMoveTo(-halfSizeX, 0f)
    relativeLineTo(size.width, 0f)
    relativeMoveTo(-halfSizeX, -halfSizeY)
    relativeLineTo(0f, size.height)
    close()
    if (centered) {
        translate(Offset(-halfSizeX, -halfSizeY))
    }
}

/**
 * Returns a [Path] representing a star with the given size and number of spikes.
 */
fun starPath(size: Size, spikes: Int, centered: Boolean) = Path().apply {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val radius = minOf(size.width, size.height) / 2f
    val step = Math.PI / spikes
    reset()
    for (i in 0 until spikes * 2) {
        val r = if (i % 2 == 0) radius else radius / StarInnerRadiusRatio
        val angle = i * step - StarStartAngle
        val x = cx + (r * cos(angle)).toFloat()
        val y = cy + (r * sin(angle)).toFloat()
        if (i == 0) moveTo(x, y) else lineTo(x, y)
    }
    close()
    if (!centered) {
        translate(offset = Offset(cx, cy))
    }
}

/**
 * Returns a [Path] representing a triangle with the given [size].
 */
fun trianglePath(size: Size, centered: Boolean = true) = Path().apply {
    reset()
    val halfWidth = size.width / 2f
    val height = size.height
    moveTo(halfWidth, 0f)
    lineTo(0f, height)
    lineTo(size.width, height)
    close()
    if (centered) {
        translate(offset = Offset(-halfWidth, -height / 2f))
    }
}

/**
 * Represents a star shape that can be used in a [Shape] context.
 */
@Immutable
class StarShape(private val spikes: Int = DefaultSpikes, private val centered: Boolean = true) :
    Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(starPath(size, spikes, centered))
    }
}

/**
 * Represents a triangle shape that can be used in a [Shape] context.
 *
 * @param centered If true, the triangle will be centered in the given size.
 */
@Immutable
class TriangleShape(private val centered: Boolean) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(trianglePath(size = size, centered = centered))
    }
}

/**
 * Represents a triangle shape that can be used in a [Shape] context.
 *
 * @param centered If true, the triangle will be centered in the given size.
 */
@Immutable
class SquaredShape(private val centered: Boolean) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(squarePath(size = size, centered = centered))
    }
}

private const val DefaultSpikes = 5
private const val StarInnerRadiusRatio = 2.5f
private const val StarStartAngle = Math.PI / 2
