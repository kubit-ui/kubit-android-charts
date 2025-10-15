package com.kubit.charts.components.chart.barchart.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * BarChartData is a data class that represents the data for a bar chart.
 *
 * @param type the type of bar chart (single, stacked, or grouped).
 * @param segments the list of segments to be displayed in the chart.
 * @param appearance the appearance of the bars (square, rounded, or mixed).
 * @param barThickness the thickness of the bars in the chart.
 * @param barSpacing the spacing between bars in the chart.
 * @param orientation the orientation of the chart (horizontal or vertical).
 * @param stepPosition the position that indicates where the bar should be placed in the chart. If is vertical chart, this is the x position of the bar, if horizontal chart, this is the y position of the bar.
 * @param barChartAlignment the alignment of the bar chart in an axis step (before, center, or after).
 */
@Immutable
data class BarChartData(
    val type: BarChartType,
    val segments: List<BarChartSegmentData>,
    val appearance: BarChartAppearance = BarChartAppearance.Squared,
    val barThickness: Dp = DefaultBarThickness,
    val barSpacing: Dp = DefaultBarSpacing,
    val orientation: BarChartOrientation = BarChartOrientation.Horizontal,
    val stepPosition: Float = 0f,
    val barChartAlignment: BarChartAlignment = BarChartAlignment.Center
) {
    init {
        when (this.type) {
            BarChartType.Single -> require(this.segments.size == 1) {
                "Single BarChart must contain 1 segment only"
            }

            BarChartType.Grouped -> require(this.segments.size >= 2) {
                "Grouped BarChart must contain at least 2 segments"
            }

            BarChartType.Stacked -> if (this.segments.isNotEmpty()) {
                for (i in 1 until this.segments.size) {
                    require(this.segments[i - 1].maxValue == this.segments[i].minValue) {
                        "On stacked variant, segments should be sequential and not overlap: segment $i"
                    }
                }
            }
        }
    }
}

/**
 * BarChartType is an enum class that represents the type of bar chart.
 * It can be a single bar chart, a stacked bar chart, or a grouped bar chart.
 */
enum class BarChartType {
    Single,
    Stacked,
    Grouped
}

/**
 * BarChartAppearance is an enum class that represents the appearance of a bar chart.
 * It can be square, rounded, or mixed.
 */
enum class BarChartAppearance {
    Squared,
    Rounded,
    Mixed
}

/**
 * BarChartOrientation is an enum class that represents the orientation of a bar chart.
 * It can be horizontal or vertical.
 */
enum class BarChartOrientation {
    Horizontal,
    Vertical
}

/**
 * BarChartLabelPosition is an enum class that represents the position of a label in a bar chart.
 */
enum class BarChartLabelPosition {
    TopStart,
    TopEnd,
    BottomStart,
    BottomEnd,
    TopCenter,
    BottomCenter,

    Center,
    CenterStart,
    CenterEnd,
    CenterStartOutside,
    CenterEndOutside;

    /**
     * This function checks if the label position is at the TopCenter, TopStart, or TopEnd.
     */
    fun isTop() = this == TopStart || this == TopEnd || this == TopCenter

    /**
     * This function checks if the label position is at the BottomCenter, BottomStart, or BottomEnd.
     */
    fun isBottom() = this == BottomStart || this == BottomEnd || this == BottomCenter

    /**
     * This function checks if the label position is at the Center, CenterStart, or CenterEnd.
     */
    fun isCenter() = this == Center || this == CenterStart || this == CenterEnd

    /**
     * This function checks if the label position is at the CenterStartOutside or CenterEndOutside.
     */
    fun isCenterOutside() = this == CenterStartOutside || this == CenterEndOutside
}

/**
 * BarChartAlignment is an enum class that represents the alignment of a bar chart in an axis step.
 * It can be start, center, or end.
 */
enum class BarChartAlignment {
    Start,
    Center,
    End
}

private val DefaultBarThickness = 8.dp
private val DefaultBarSpacing = 2.dp
