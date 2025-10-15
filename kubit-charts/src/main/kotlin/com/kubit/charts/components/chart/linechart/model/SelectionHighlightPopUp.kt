package com.kubit.charts.components.chart.linechart.model

import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextPaint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

/**
 * SelectionHighlightPopUp is a data class used to draw the pop on the given selected point on a line
 * to identify the dimensions of the selected point.All the styling related params are included here
 */
@Suppress("MagicNumber")
data class SelectionHighlightPopUp(
    val backgroundColor: Color = Color.White,
    val backgroundAlpha: Float = 0.7f,
    val backgroundCornerRadius: CornerRadius = CornerRadius(5f),
    val backgroundColorFilter: ColorFilter? = null,
    val backgroundBlendMode: BlendMode = DrawScope.DefaultBlendMode,
    val backgroundStyle: DrawStyle = Fill,
    val paddingBetweenPopUpAndPoint: Dp = 20.dp,
    val labelSize: TextUnit = 14.sp,
    val labelColor: Color = Color.Black,
    val labelAlignment: Paint.Align = Paint.Align.CENTER,
    val labelTypeface: Typeface = Typeface.DEFAULT,
    val popUpLabel: (Float, Float) -> (String) = { x, y ->
        val xLabel = "x : ${x.toInt()} "
        val yLabel = "y : ${String.format(Locale.getDefault(), "%.2f", y)}"
        "$xLabel $yLabel"
    },
    val draw: DrawScope.(Offset, Point) -> Unit = { selectedOffset, identifiedPoint ->
        val highlightTextPaint = TextPaint().apply {
            textSize = labelSize.toPx()
            color = labelColor.toArgb()
            textAlign = labelAlignment
            typeface = labelTypeface
        }
        val label = popUpLabel(identifiedPoint.x, identifiedPoint.y)
        drawContext.canvas.nativeCanvas.apply {
            val background = getTextBackgroundRect(
                selectedOffset.x,
                selectedOffset.y,
                label,
                highlightTextPaint
            )
            drawRoundRect(
                color = backgroundColor,
                topLeft = Offset(
                    background.left.toFloat(),
                    background.top.toFloat() - paddingBetweenPopUpAndPoint.toPx()
                ),
                size = Size(background.width().toFloat(), background.height().toFloat()),
                alpha = backgroundAlpha,
                cornerRadius = backgroundCornerRadius,
                colorFilter = backgroundColorFilter,
                blendMode = backgroundBlendMode,
                style = backgroundStyle
            )
            drawText(
                label,
                selectedOffset.x,
                selectedOffset.y - paddingBetweenPopUpAndPoint.toPx(),
                highlightTextPaint
            )
        }
    }
)

/**
 * Returns the background rect for the highlighted text.
 * @param x : X point.
 * @param y: Y point.
 * @param text: Text to be drawn inside the background.
 * @param paint: Background paint.
 */
private fun getTextBackgroundRect(
    x: Float,
    y: Float,
    text: String,
    paint: TextPaint
): Rect {
    val fontMetrics = paint.fontMetrics
    val textLength = paint.measureText(text)
    val xLess = x - textLength / 2
    val yMore = y + fontMetrics.top
    val xMore = x + textLength / 2
    val yBottom = y + fontMetrics.bottom
    return Rect(
        xLess.toInt(),
        yMore.toInt(),
        xMore.toInt(),
        yBottom.toInt()
    )
}
