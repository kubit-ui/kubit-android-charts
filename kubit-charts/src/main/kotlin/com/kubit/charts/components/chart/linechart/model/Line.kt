package com.kubit.charts.components.chart.linechart.model

import androidx.compose.runtime.Immutable

/**
 * Represents a line in the [LineChart].
 *
 * @property dataPoints List of [Point] objects that define the line.
 * @property lineStyle Styling options for the line path.
 * @property shadowUnderLine Logic for drawing the area under the line.
 * @property selectionHighlightPopUp Configuration for the selection popup.
 * @property selectionHighlightPoint Logic for drawing the highlight at the selected point. If null, no highlight is shown.
 */
@Immutable
data class Line(
    val dataPoints: List<Point>,
    val lineStyle: LineStyle = LineStyle(),
    val shadowUnderLine: ShadowUnderLine? = null,
    val selectionHighlightPopUp: SelectionHighlightPopUp? = null,
    val selectionHighlightPoint: SelectionHighlightPoint? = null,
)
