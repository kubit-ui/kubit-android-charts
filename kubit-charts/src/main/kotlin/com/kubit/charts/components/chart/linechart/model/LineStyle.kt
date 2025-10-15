package com.kubit.charts.components.chart.linechart.model

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * Defines the visual style for a line in the chart.
 *
 * @property lineType The type of line (straight or smooth curve).
 * @property color The color of the line.
 * @property width The width of the line in pixels.
 * @property alpha The opacity of the line (0.0f = transparent, 1.0f = opaque).
 * @property style The drawing style (stroke or fill).
 * @property colorFilter Optional color filter to apply to the line.
 * @property blendMode The blend mode used when drawing the line.
 */
data class LineStyle(
    val lineType: LineType = LineType.SmoothCurve(isDotted = false),
    val color: Color = Color.Black,
    val width: Float = 8f,
    val alpha: Float = 1.0f,
    val style: DrawStyle = Stroke(width = width),
    val colorFilter: ColorFilter? = null,
    val blendMode: BlendMode = DrawScope.DefaultBlendMode
)
