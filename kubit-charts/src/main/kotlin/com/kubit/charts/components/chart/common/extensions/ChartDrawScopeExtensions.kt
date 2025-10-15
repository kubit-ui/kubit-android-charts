package com.kubit.charts.components.chart.common.extensions

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.kubit.charts.components.chart.linechart.extensions.drawHighLightOnSelectedPoint
import com.kubit.charts.components.chart.linechart.extensions.drawHighlightText
import com.kubit.charts.components.chart.linechart.extensions.drawPointOnLine
import com.kubit.charts.components.chart.linechart.isNotNull
import com.kubit.charts.components.chart.linechart.model.IntersectionComposable
import com.kubit.charts.components.chart.linechart.model.Line
import com.kubit.charts.components.chart.linechart.model.Point

/**
 * This method draws intersection nodes on a canvas. Take into account that this doesn't draw the
 * [IntersectionComposable] nodes.
 *
 * @param pointsData List of points to be drawn on the canvas.
 */
internal fun DrawScope.drawIntersectionPoints(
    pointsData: List<Point>
) {
    pointsData.filter { it.intersectionNode !is IntersectionComposable }.forEach { point ->
        point.intersectionNode?.let {
            drawPointOnLine(point.offset, it)
        }
    }
}

internal fun DrawScope.drawHighlightPoints(
    line: Line,
    pointsDataWithoutScroll: List<Point>,
    pointsData: List<Point>,
    tapOffsetWithScroll: Offset?,
    canvasHeight: Float,
    xUnitWidth: Float,
    selectedPoint: Point = Point.Zero,
) {
    // <Index, <'Theoretical' point value, Real point value in canvas space>>
    val selectedPoints = mutableMapOf<Int, Pair<Point, Offset>>()

    pointsDataWithoutScroll.forEachIndexed { index, point ->
        if (tapOffsetWithScroll != null && point.offset.isTapped(
                tapOffsetWithScroll.x,
                xUnitWidth,
            )
        ) {
            // Dealing with only one line graph hence tapPointLocks[0]
            selectedPoints[0] = line.dataPoints[index] to pointsData[index].offset
        }
    }

    val selectedOffset = selectedPoints.values.firstOrNull()?.second
    if (selectedOffset.isNotNull()) {
        drawHighlightText(
            selectedPoint,
            selectedOffset ?: Offset(0f, 0f),
            line.selectionHighlightPopUp
        )
    }
    if (tapOffsetWithScroll != null) {
        drawHighLightOnSelectedPoint(
            selectedPoints,
            canvasHeight,
            line.selectionHighlightPoint
        )
    }
}

/**
 * Returns true if the given tap offset is selected point or not else false
 * @param tapOffset: Offset of the tapped point.
 * @param xOffset: Distance between two points in X-Axis.
 */
private fun Offset.isTapped(tapOffset: Float, xOffset: Float) =
    tapOffset > x - (xOffset / 2) && tapOffset < x + (xOffset / 2)
