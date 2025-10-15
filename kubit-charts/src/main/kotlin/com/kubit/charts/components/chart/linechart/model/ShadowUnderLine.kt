package com.kubit.charts.components.chart.linechart.model

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill

/**
 * Controls the drawing behaviour of the area/section under the line.
 */
data class ShadowUnderLine(
    val color: Color = Color.Black,
    val brush: Brush? = null,
    val alpha: Float = 0.1f,
    val style: DrawStyle = Fill,
    val colorFilter: ColorFilter? = null,
    val blendMode: BlendMode = DrawScope.DefaultBlendMode,
    val draw: DrawScope.(Path) -> Unit = { path ->
        if (brush != null) {
            drawPath(path, brush, alpha, style, colorFilter, blendMode)
        } else {
            drawPath(path, color, alpha, style, colorFilter, blendMode)
        }
    },
    val drawMultiColor: DrawScope.(Path, Color, Brush?) -> Unit = { path, multiColor, multiColorBrush ->
        if (multiColorBrush != null) {
            drawPath(path, multiColorBrush, alpha, style, colorFilter, blendMode)
        } else {
            drawPath(path, multiColor, alpha, style, colorFilter, blendMode)
        }
    }
)
