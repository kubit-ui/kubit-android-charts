package com.kubit.charts.components.chart.linechart.extensions

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.copy
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.kubit.charts.components.chart.linechart.getDrawStyleForPath
import com.kubit.charts.components.chart.linechart.isNotNull
import com.kubit.charts.components.chart.linechart.model.IntersectionNode
import com.kubit.charts.components.chart.linechart.model.Line
import com.kubit.charts.components.chart.linechart.model.LineStyle
import com.kubit.charts.components.chart.linechart.model.Point
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPoint
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPopUp

/**
 *
 * DrawScope.drawHighLightOnSelectedPoint extension method used for drawing and  highlight the selected
 * point when dragging.
 * @param dragLocks : List of points to be drawn on the canvas and that can be selected.
 * @param yBottom : Start position from below of the canvas.
 * @param selectionHighlightPoint : Data class to define all the styles to be drawn in [SelectionHighlightPoint]
 */
@Suppress("NestedBlockDepth")
fun DrawScope.drawHighLightOnSelectedPoint(
    dragLocks: MutableMap<Int, Pair<Point, Offset>>,
    yBottom: Float,
    selectionHighlightPoint: SelectionHighlightPoint?
) {
    if (selectionHighlightPoint.isNotNull()) {
        dragLocks.values.firstOrNull()?.let { (_, location) ->
            val (x, y) = location
            if (x >= 0 && x <= size.width) {
                selectionHighlightPoint?.draw?.let { it(this, Offset(x, y)) }
                if (selectionHighlightPoint?.isHighlightLineRequired == true) {
                    selectionHighlightPoint.drawHighlightLine(
                        this,
                        Offset(x, yBottom),
                        Offset(x, y)
                    )
                }
            }
        }
    }
}

/**
 * Used to draw the highlighted text
 * @param identifiedPoint : Selected points
 * @param selectedOffset: Offset selected
 * @param selectionHighlightPopUp : Data class with all styling related info [SelectionHighlightPopUp]
 */
fun DrawScope.drawHighlightText(
    identifiedPoint: Point,
    selectedOffset: Offset,
    selectionHighlightPopUp: SelectionHighlightPopUp?
) {
    selectionHighlightPopUp?.run {
        draw(selectedOffset, identifiedPoint)
    }
}

/**
 *
 * DrawScope.drawShadowUnderLine extension method used  for drawing a
 * shadow below the line graph points
 * @param path : Path used to draw the shadow
 * @param nextPath : Path used to draw the shadow for the next line graph. In this case it will be used as a cutting mask to avoid alpha overlapping of different shadows
 * @param pointsData : List of the points on the Line graph.
 * @param nextPointsData : List of the points on the next Line graph.
 * @param yBottom : Offset of X-Axis starting position i.e shade to be drawn until.
 * @param line : line on which shadow & intersectionPoints has to be drawn.
 */
fun DrawScope.drawShadowUnderLine(
    path: Path,
    nextPath: Path?,
    pointsData: List<Point>,
    nextPointsData: List<Point>?,
    yBottom: Float,
    line: Line,
) {
    val pathCopy = path.copy()
    val nextPathCopy = nextPath?.copy()

    if (line.shadowUnderLine.isNotNull()) {
        // These +X or -X added is to avoid showing the line in the limits of the canvas. This way we can "draw" that outside.
        pathCopy.lineTo(pointsData.last().x + line.lineStyle.width * 2f, pointsData.last().y)
        pathCopy.lineTo(pointsData.last().x + line.lineStyle.width * 2f, yBottom + line.lineStyle.width)
        pathCopy.lineTo(pointsData.first().x - line.lineStyle.width * 2f, yBottom + line.lineStyle.width)

        val drawPath = if (nextPathCopy != null && nextPointsData != null) {
            nextPathCopy.lineTo(nextPointsData.last().x + line.lineStyle.width * 2f, nextPointsData.last().y)
            nextPathCopy.lineTo(nextPointsData.last().x + line.lineStyle.width * 2f, yBottom + line.lineStyle.width)
            nextPathCopy.lineTo(nextPointsData.first().x - line.lineStyle.width * 2f, yBottom + line.lineStyle.width)

            pathCopy.minus(nextPathCopy)
        } else {
            pathCopy
        }

        line.shadowUnderLine?.draw?.let { it(this, drawPath) }
    }
}

/**
 *
 * DrawScope.drawPointOnLine extension method  used for drawing a circle/mark on a line for a given Point(x,y).
 * @param offset : Point at which circle/mark has to be drawn.
 * @param intersectionPoint : IntersectionPoint data class with all styling related info.
 */
internal fun DrawScope.drawPointOnLine(offset: Offset, intersectionPoint: IntersectionNode?) {
    intersectionPoint?.draw?.let { it(this, offset) }
}

/**
 * DrawScope.drawLineChart extension method used for drawing a line chart on the canvas. *
 * This is usually called after [getLineOrCubicPath] method.
 *
 * @param path to be drawn
 * @param lineStyle : All styles related to the path are included in [LineStyle].
 */
internal fun DrawScope.drawLineChart(
    path: Path,
    lineStyle: LineStyle
) = with(lineStyle) {
    drawPath(
        path,
        color = color,
        style = getDrawStyleForPath(lineType, lineStyle),
        alpha = alpha,
        colorFilter = colorFilter,
        blendMode = blendMode
    )
}
