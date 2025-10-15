package com.kubit.charts.components.chart.common.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import com.kubit.charts.components.chart.linechart.model.LineStyle
import com.kubit.charts.components.chart.linechart.model.LineType
import com.kubit.charts.components.chart.linechart.model.Point

/**
 * DrawScope.drawStraightOrCubicLine extension method used for drawing a straight/cubic line for a given Point(x,y).
 * @param pointsData : List of points to be drawn on the canvas
 * @param cubicPoints1 : List of average left side values for a given Point(x,y).
 * @param cubicPoints2 : List of average right side values for a given Point(x,y).
 * @param lineStyle : All styles related to the path are included in [LineStyle].
 */
internal fun getLineOrCubicPath(
    pointsData: List<Point>,
    cubicPoints1: MutableList<Offset>,
    cubicPoints2: MutableList<Offset>,
    lineStyle: LineStyle
): Path {
    val path = Path()
    path.moveTo(pointsData.first().x, pointsData.first().y)
    for (i in 1 until pointsData.size) {
        when (lineStyle.lineType) {
            is LineType.Straight -> {
                path.lineTo(pointsData[i].x, pointsData[i].y)
            }

            is LineType.SmoothCurve -> {
                path.cubicTo(
                    cubicPoints1[i - 1].x,
                    cubicPoints1[i - 1].y,
                    cubicPoints2[i - 1].x,
                    cubicPoints2[i - 1].y,
                    pointsData[i].x,
                    pointsData[i].y
                )
            }
        }
    }

    return path
}
