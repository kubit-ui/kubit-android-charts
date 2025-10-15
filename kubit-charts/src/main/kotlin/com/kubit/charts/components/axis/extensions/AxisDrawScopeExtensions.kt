package com.kubit.charts.components.axis.extensions

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.axis.AxisLabelCenterAlignment
import com.kubit.charts.components.axis.AxisLabelHorizontalAlignment
import com.kubit.charts.components.axis.AxisLabelVerticalAlignment
import com.kubit.charts.components.axis.HorizontalAxisType
import com.kubit.charts.components.axis.VerticalAxisType
import com.kubit.charts.components.axis.exceptions.AxisChartException
import com.kubit.charts.components.axis.model.AxisPadding
import com.kubit.charts.components.axis.model.AxisStep
import com.kubit.charts.components.axis.model.AxisStepStyle
import com.kubit.charts.components.axis.model.DashedStepStyle
import com.kubit.charts.components.axis.model.SolidStepStyle
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

//region Vertical axis labels
internal fun DrawScope.drawVerticalAxisLabels(
    axisSteps: List<AxisStep>,
    textMeasurer: TextMeasurer,
    type: VerticalAxisType,
    labelWidth: Dp,
    padding: AxisPadding,
    axisCustomXOffset: Dp? = null,
    labelHorizontalAlignment: AxisLabelHorizontalAlignment = AxisLabelHorizontalAlignment.Center,
    labelRotation: Float = 0f,
    labelCenterAlignment: AxisLabelCenterAlignment = AxisLabelCenterAlignment.End,
    labelHorizontalGap: Dp = 0.dp,
    verticalScroll: Dp = 0.dp,
) {
    val canvasWidth = this.drawContext.size.width

    axisSteps.forEach { axisStep ->
        if (axisStep.axisValueInPx == null) {
            throw AxisChartException.AxisNotProcessedException()
        }
        if (axisStep.axisLabel != null) {
            val textLayoutResult = textMeasurer.measure(
                axisStep.axisLabel,
                style = axisStep.labelStyle ?: TextStyle.Default
            )
            val textYCenter = textLayoutResult.size.center.y
            val textXCenter = textLayoutResult.size.center.x
            val textXSize = textLayoutResult.size.width

            val labelWidthInPx = labelWidth.toPx()
            val axisCustomXOffsetInPx = axisCustomXOffset?.toPx()
            val verticalScrollInPx = verticalScroll.toPx()

            val xOffset = when (type) {
                VerticalAxisType.Start -> getStartVerticalAxisLabelsXOffset(
                    labelHorizontalAlignment,
                    labelWidthInPx,
                    padding.start.toPx(),
                    textXSize.toFloat(),
                    axisCustomXOffsetInPx
                )

                VerticalAxisType.End -> getEndVerticalAxisLabelsXOffset(
                    canvasWidth,
                    labelHorizontalAlignment,
                    labelWidthInPx,
                    padding.end.toPx(),
                    textXSize.toFloat(),
                    axisCustomXOffsetInPx
                )
            }

            val xCenterTranslation = when (labelCenterAlignment) {
                AxisLabelCenterAlignment.Start -> 0f
                AxisLabelCenterAlignment.End -> textXSize.toFloat()
                AxisLabelCenterAlignment.Center -> textXCenter.toFloat()
            }

            val rotatedGap =
                labelHorizontalGap.toPx() + textYCenter * abs(sin(labelRotation.toRadians()).toFloat())

            withTransform({
                translate(
                    left = if (type == VerticalAxisType.Start) -rotatedGap else rotatedGap,
                    top = verticalScrollInPx
                )
                rotate(
                    labelRotation,
                    pivot = Offset(xOffset + xCenterTranslation, axisStep.axisValueInPx)
                )
            }) {
                drawText(
                    textLayoutResult,
                    topLeft = Offset(xOffset, axisStep.axisValueInPx - textYCenter)
                )
            }
        }
    }
}

private fun getStartVerticalAxisLabelsXOffset(
    labelHorizontalAlignment: AxisLabelHorizontalAlignment,
    labelWidthInPx: Float,
    startPaddingInPx: Float,
    textWidth: Float,
    axisCustomXOffsetInPx: Float?
) = when (axisCustomXOffsetInPx) {
    null -> when (labelHorizontalAlignment) {
        AxisLabelHorizontalAlignment.Start ->
            startPaddingInPx

        AxisLabelHorizontalAlignment.End -> labelWidthInPx - textWidth

        AxisLabelHorizontalAlignment.Center -> startPaddingInPx +
            (labelWidthInPx - textWidth) / 2f
    }

    else -> axisCustomXOffsetInPx
}

private fun getEndVerticalAxisLabelsXOffset(
    canvasWidth: Float,
    labelHorizontalAlignment: AxisLabelHorizontalAlignment,
    labelWidthInPx: Float,
    endPaddingInPx: Float,
    textWidth: Float,
    axisCustomXOffsetInPx: Float?
) =
    when (axisCustomXOffsetInPx) {
        null -> when (labelHorizontalAlignment) {
            AxisLabelHorizontalAlignment.Center -> canvasWidth -
                endPaddingInPx -
                labelWidthInPx +
                (labelWidthInPx - textWidth) / 2f

            AxisLabelHorizontalAlignment.Start -> canvasWidth - endPaddingInPx -
                labelWidthInPx

            AxisLabelHorizontalAlignment.End -> canvasWidth - endPaddingInPx -
                textWidth
        }

        else -> axisCustomXOffsetInPx
    }

//endregion

//region Vertical axis grid lines
@Suppress("LongMethod")
internal fun DrawScope.drawVerticalAxisGridLines(
    axisSteps: List<AxisStep>,
    type: VerticalAxisType,
    labelWidth: Dp,
    padding: AxisPadding,
    axisCustomXOffset: Dp? = null,
    verticalScroll: Dp = 0.dp,
) {
    val canvasWidth = this.drawContext.size.width

    var startOffset: Offset
    var endOffset: Offset

    val axisCustomXOffsetInPx = axisCustomXOffset?.toPx()
    val verticalScrollInPx = verticalScroll.toPx()

    axisSteps.forEach { axisStep ->
        if (axisStep.axisValueInPx == null) {
            throw AxisChartException.AxisNotProcessedException()
        }
        axisStep.stepStyle?.let { style ->
            val strokeWidthInPx = style.strokeWidth.toPx()
            val labelWidthInPx = labelWidth.toPx()

            getVerticalGridLinesStartAndEndOffset(
                type,
                labelWidthInPx,
                canvasWidth,
                axisStep.axisValueInPx + verticalScrollInPx,
                padding.start.toPx(),
                padding.end.toPx(),
                axisCustomXOffsetInPx
            ).let { (start, end) ->
                startOffset = start
                endOffset = end
            }

            drawLine(
                color = style.strokeColor,
                start = startOffset,
                end = endOffset,
                strokeWidth = strokeWidthInPx,
                pathEffect = getPathEffect(style)
            )
        }
    }
}

private fun DrawScope.getPathEffect(style: AxisStepStyle) = when (style) {
    is SolidStepStyle ->
        null // Default behaviour of drawLine is to paint a solid line.
    is DashedStepStyle ->
        PathEffect.dashPathEffect(
            floatArrayOf(
                style.dashLength.toPx(),
                style.gapLength.toPx()
            ),
            style.phase.toPx()
        )

    else -> throw AxisChartException.AxisStepStyleNotRecognizedException()
}

private fun getVerticalGridLinesStartAndEndOffset(
    type: VerticalAxisType,
    labelWidthInPx: Float,
    canvasWidth: Float,
    axisValueInPx: Float,
    paddingStartInPx: Float,
    paddingEndInPx: Float,
    axisCustomXOffsetInPx: Float?,
) =
    when (type) {
        VerticalAxisType.Start -> {
            val startOffset = when (axisCustomXOffsetInPx) {
                null -> Offset(
                    paddingStartInPx + labelWidthInPx,
                    axisValueInPx
                )

                else -> Offset(
                    paddingStartInPx,
                    axisValueInPx
                )
            }
            val endOffset = Offset(
                canvasWidth - paddingEndInPx,
                axisValueInPx
            )

            Pair(startOffset, endOffset)
        }

        VerticalAxisType.End -> {
            val startOffset = when (axisCustomXOffsetInPx) {
                null -> Offset(
                    canvasWidth - paddingEndInPx - labelWidthInPx,
                    axisValueInPx
                )

                else -> Offset(
                    canvasWidth - paddingEndInPx,
                    axisValueInPx
                )
            }
            val endOffset = Offset(
                paddingStartInPx,
                axisValueInPx
            )

            Pair(startOffset, endOffset)
        }
    }

//endregion

//region Horizontal axis labels
@Suppress("LongMethod")
internal fun DrawScope.drawHorizontalAxisLabels(
    axisSteps: List<AxisStep>,
    textMeasurer: TextMeasurer,
    type: HorizontalAxisType,
    labelHeight: Dp,
    padding: AxisPadding,
    axisCustomYOffset: Dp? = null,
    labelVerticalAlignment: AxisLabelVerticalAlignment = AxisLabelVerticalAlignment.Center,
    labelRotation: Float = 0f,
    labelCenterAlignment: AxisLabelCenterAlignment = AxisLabelCenterAlignment.End,
    labelVerticalGap: Dp = 0.dp,
    horizontalScroll: Dp = 0.dp,
) {
    val canvasHeight = this.drawContext.size.height

    axisSteps.forEachIndexed { index, axisStep ->
        if (axisStep.axisValueInPx == null) {
            throw AxisChartException.AxisNotProcessedException()
        }
        if (axisStep.axisLabel != null) {
            val textLayoutResult = textMeasurer.measure(
                axisStep.axisLabel,
                style = axisStep.labelStyle ?: TextStyle.Default
            )

            val labelHeightInPx = labelHeight.toPx()
            val axisCustomYOffsetInPx = axisCustomYOffset?.toPx()
            val textXCenter = textLayoutResult.size.center.x
            val textYCenter = textLayoutResult.size.center.y
            val horizontalScrollInPx = horizontalScroll.toPx()

            val yOffset = when (type) {
                HorizontalAxisType.Top -> getTopHorizontalAxisLabelsYOffset(
                    labelVerticalAlignment,
                    labelHeightInPx,
                    padding.top.toPx(),
                    textLayoutResult.size.height.toFloat(),
                    axisCustomYOffsetInPx
                )

                HorizontalAxisType.Bottom -> getBottomHorizontalAxisLabelsYOffset(
                    canvasHeight,
                    labelVerticalAlignment,
                    labelHeightInPx,
                    padding.bottom.toPx(),
                    textLayoutResult.size.height.toFloat(),
                    axisCustomYOffsetInPx
                )
            }

            val xCenterTranslation = when (labelCenterAlignment) {
                AxisLabelCenterAlignment.Start -> textXCenter.toFloat()
                AxisLabelCenterAlignment.End -> -textXCenter.toFloat()
                AxisLabelCenterAlignment.Center -> 0f
            }

            val rotatedGap =
                labelVerticalGap.toPx() - textYCenter * (1 - cos(labelRotation.toRadians())).toFloat()

            withTransform({
                translate(
                    left = xCenterTranslation - horizontalScrollInPx,
                    top = if (type == HorizontalAxisType.Bottom) rotatedGap else -rotatedGap
                )
                rotate(
                    labelRotation,
                    pivot = Offset(
                        axisStep.axisValueInPx - xCenterTranslation,
                        yOffset + textYCenter
                    )
                )
            }) {
                drawText(
                    textLayoutResult,
                    topLeft = Offset(
                        axisStep.axisValueInPx - textLayoutResult.size.center.x,
                        yOffset
                    )
                )
            }
        }
    }
}

private fun getTopHorizontalAxisLabelsYOffset(
    labelVerticalAlignment: AxisLabelVerticalAlignment,
    labelHeightInPx: Float,
    topPaddingInPx: Float,
    textHeight: Float,
    axisCustomYOffsetInPx: Float?
) = when (axisCustomYOffsetInPx) {
    null -> when (labelVerticalAlignment) {
        AxisLabelVerticalAlignment.Center -> topPaddingInPx + (labelHeightInPx -
            textHeight) / 2

        AxisLabelVerticalAlignment.Bottom -> topPaddingInPx +
            labelHeightInPx - textHeight

        AxisLabelVerticalAlignment.Top -> topPaddingInPx
    }

    else -> axisCustomYOffsetInPx
}

private fun getBottomHorizontalAxisLabelsYOffset(
    canvasHeight: Float,
    labelVerticalAlignment: AxisLabelVerticalAlignment,
    labelHeightInPx: Float,
    bottomPaddingInPx: Float,
    textHeight: Float,
    axisCustomYOffsetInPx: Float?
) = when (axisCustomYOffsetInPx) {
    null -> when (labelVerticalAlignment) {
        AxisLabelVerticalAlignment.Center -> canvasHeight - bottomPaddingInPx -
            labelHeightInPx + (labelHeightInPx - textHeight) / 2

        AxisLabelVerticalAlignment.Bottom -> canvasHeight - bottomPaddingInPx -
            -textHeight

        AxisLabelVerticalAlignment.Top -> canvasHeight - bottomPaddingInPx -
            labelHeightInPx
    }

    else -> axisCustomYOffsetInPx
}

//endregion

//region Horizontal axis grid lines
@Suppress("LongMethod")
internal fun DrawScope.drawHorizontalAxisGridLines(
    axisSteps: List<AxisStep>,
    type: HorizontalAxisType,
    labelHeight: Dp,
    padding: AxisPadding,
    axisCustomYOffset: Dp? = null,
    horizontalScroll: Dp = 0.dp,
) {
    val canvasHeight = this.drawContext.size.height

    var startYOffset: Offset
    var endYOffset: Offset

    val labelHeightInPx = labelHeight.toPx()
    val axisCustomYOffsetInPx = axisCustomYOffset?.toPx()
    val horizontalScrollInPx = horizontalScroll.toPx()

    axisSteps.forEach { axisStep ->
        if (axisStep.axisValueInPx == null) {
            throw AxisChartException.AxisNotProcessedException()
        }
        axisStep.stepStyle?.let { style ->
            val strokeWidthInPixels = style.strokeWidth.toPx()

            getHorizontalGridLinesStartAndEndOffset(
                type,
                labelHeightInPx,
                canvasHeight,
                axisStep.axisValueInPx - horizontalScrollInPx,
                padding.bottom.toPx(),
                padding.top.toPx(),
                axisCustomYOffsetInPx
            ).let { (start, end) ->
                startYOffset = start
                endYOffset = end
            }

            drawLine(
                color = style.strokeColor,
                start = startYOffset,
                end = endYOffset,
                strokeWidth = strokeWidthInPixels,
                pathEffect = getPathEffect(style)
            )
        }
    }
}

private fun getHorizontalGridLinesStartAndEndOffset(
    type: HorizontalAxisType,
    labelHeightInPx: Float,
    canvasHeight: Float,
    axisValueInPx: Float,
    bottomPaddingInPx: Float,
    topPaddingInPx: Float,
    axisCustomYOffsetInPx: Float?
) =
    when (type) {
        HorizontalAxisType.Bottom -> {
            val startYOffset = when (axisCustomYOffsetInPx) {
                null -> Offset(
                    axisValueInPx,
                    canvasHeight - labelHeightInPx - bottomPaddingInPx
                )

                else -> Offset(
                    axisValueInPx,
                    canvasHeight - bottomPaddingInPx
                )
            }
            val endYOffset = Offset(
                axisValueInPx,
                topPaddingInPx
            )

            Pair(startYOffset, endYOffset)
        }

        HorizontalAxisType.Top -> {
            val startYOffset = when (axisCustomYOffsetInPx) {
                null -> Offset(
                    axisValueInPx,
                    labelHeightInPx + topPaddingInPx
                )

                else -> Offset(
                    axisValueInPx,
                    topPaddingInPx
                )
            }
            val endYOffset = Offset(
                axisValueInPx,
                canvasHeight - bottomPaddingInPx
            )

            Pair(startYOffset, endYOffset)
        }
    }

//endregion

@Suppress("MagicNumber")
private fun Float.toRadians() = this * Math.PI / 180f
