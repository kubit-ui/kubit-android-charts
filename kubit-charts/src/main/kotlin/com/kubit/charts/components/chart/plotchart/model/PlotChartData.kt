package com.kubit.charts.components.chart.plotchart.model

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.chart.linechart.model.Point

/**
 * Represents a data point in a plot chart.
 *
 * This is the base class for all plot chart data types, holding the coordinates of the point.
 *
 * @property point The coordinates of the point in the chart.
 */
abstract class PlotChartData(
    val point: Point
)

/**
 * Stores information about a shape to be drawn in a plot chart.
 *
 * @param point The coordinates of the point in the chart.
 * @param contentDescription Description for accessibility purposes.
 * @param size Size of the point (default: Medium).
 * @param shape Shape of the point (default: Circle).
 * @param color Color of the point (default: Black).
 */
@Immutable
class PlotShapeChartData(
    point: Point,
    val contentDescription: String? = null,
    val size: Dp = 24.dp,
    val shape: Shape = CircleShape,
    val color: Color = Color.Black,
) : PlotChartData(point = point) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlotShapeChartData

        if (other.point != point) return false
        if (size != other.size) return false
        if (shape != other.shape) return false
        if (color != other.color) return false
        if (contentDescription != other.contentDescription) return false

        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = size.hashCode()
        result = 31 * result + point.hashCode()
        result = 31 * result + shape.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + contentDescription.hashCode()
        return result
    }
}

/**
 * Stores information about a custom composable to be drawn in a plot chart.
 *
 * @param point The coordinates of the point in the chart.
 * @param customPlot Custom composable to render. Receives the current zoom as parameter.
 */
@Immutable
class PlotCustomChartData(
    point: Point,
    val customPlot: @Composable (zoom: Float) -> Unit
) : PlotChartData(point = point) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlotCustomChartData

        if (other.point != point) return false
        if (other.customPlot != customPlot) return false

        return true
    }

    override fun hashCode(): Int {
        var result = point.hashCode()
        result = 31 * result + customPlot.hashCode()

        return result
    }
}

/**
 * Represents the background data for a plot chart.
 * Includes the width and height of the chart in points and a background image.
 *
 * @property widthPoints Chart width in points, as a Pair of Float values.
 * @property heightPoints Chart height in points, as a Pair of Float values.
 * @property backgroundImageBitmap Background image for the chart.
 */
@Immutable
data class PlotChartBackgroundData(
    val widthPoints: Pair<Float, Float>,
    val heightPoints: Pair<Float, Float>,
    val backgroundImageBitmap: ImageBitmap
)
