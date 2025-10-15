package com.kubit.charts.components.chart.barchart.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * BarChartSegmentData is a data class that represents a segment in a bar chart.
 *
 * @param maxValue the ending value of the segment, this should be the highest value of the segment.
 * @param color the color of the segment.
 * @param contentDescription the content description for accessibility.
 * @param label the label of the segment.
 * @param minValue the starting value of the segment, this should be the lowest value of the segment.
 * @param labelColor the color of the label, default is black.
 * @param labelSize the size of the label, default is 14.sp.
 * @param labelSpacing the spacing between the label and the segment, default is 8.dp
 * @param labelPosition the position of the label.
 * @param labelRotation the rotation of the label in degrees, if null, the label will have default rotation.
 */
@Immutable
data class BarChartSegmentData(
    val maxValue: Double,
    val color: Color,
    val contentDescription: String,
    val label: String? = null,
    val minValue: Double = 0.0,
    val labelColor: Color = Color.Black,
    val labelSize: TextUnit = 14.sp,
    val labelSpacing: Dp = 8.dp,
    val labelPosition: BarChartLabelPosition? = null,
    val labelRotation: Float? = null,
) {
    init {
        require(minValue <= maxValue) { "minValue cannot be higher than maxValue" }
    }

    val size: Double get() = maxValue - minValue
}

/**
 * BarChartSegmentPosition is an enum class that represents the position of a segment in a bar chart.
 */
internal enum class BarChartSegmentPosition {
    Start,
    Middle,
    End
}

/**
 * BarChartStats is a data class that holds the statistics of the bar chart.
 *
 * @param globalMin the minimum value across all segments.
 * @param globalMax the maximum value across all segments.
 * @param totalRange the total range of values across all segments.
 */
@Immutable
internal data class BarChartStats(
    val globalMin: Double,
    val globalMax: Double,
    val totalRange: Double
)
