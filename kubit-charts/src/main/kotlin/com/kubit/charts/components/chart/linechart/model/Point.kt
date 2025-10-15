package com.kubit.charts.components.chart.linechart.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset

/**
 * Represents a point in a chart, defined by its x and y coordinates.
 *
 * Optionally, an intersection node can be attached for custom drawing at the point location.
 *
 * @property x The x-coordinate value in the chart.
 * @property y The y-coordinate value in the chart.
 * @property intersectionNode Optional node to be drawn at the point (e.g., shape, painter, etc.).
 */
@Immutable
data class Point(
    val x: Float,
    val y: Float,
    val intersectionNode: IntersectionNode? = null
) {
    /**
     * Returns the [Offset] representation of this point.
     */
    val offset by lazy { Offset(x, y) }

    companion object {
        /**
         * A constant representing the origin point (0, 0).
         */
        val Zero = Point(0f, 0f)
    }
}
