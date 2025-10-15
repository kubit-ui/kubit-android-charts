package com.kubit.charts.components.axis.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.kubit.charts.components.axis.AxisShade

/**
 * Represents a rectangular region within an axis coordinate system for styling purposes.
 *
 * This data class defines a region that can be highlighted, shaded, or colored within
 * an axis chart. Common use cases include highlighting negative values, marking specific
 * time periods, or creating visual zones within the chart area.
 *
 * ## Usage Examples
 * ```kotlin
 * // Shade negative values on a horizontal axis
 * val negativeRegion = ShadeRegion(
 *     fromX = -10f,
 *     toX = 0f,
 *     fromY = 0f,
 *     toY = chartHeight
 * )
 *
 * // Highlight a specific time period
 * val highlightPeriod = ShadeRegion(
 *     fromX = startTime,
 *     toX = endTime,
 *     fromY = 0f,
 *     toY = chartHeight
 * )
 * ```
 *
 * @param fromX The starting X coordinate of the region (left edge).
 * @param toX The ending X coordinate of the region (right edge).
 * @param fromY The starting Y coordinate of the region (top edge).
 * @param toY The ending Y coordinate of the region (bottom edge).
 * @param color The color used to fill the shaded region.
 *
 * @see AxisShade
 */
@Immutable
data class ShadeRegion(
    val fromX: Float,
    val toX: Float,
    val fromY: Float,
    val toY: Float,
    val color: Color
)
