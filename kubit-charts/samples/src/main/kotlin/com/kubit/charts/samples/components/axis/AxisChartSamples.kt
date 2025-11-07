@file:Suppress("TooManyFunctions")

package com.kubit.charts.samples.components.axis

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.axis.AxisLabelCenterAlignment
import com.kubit.charts.components.axis.AxisLabelHorizontalAlignment
import com.kubit.charts.components.axis.AxisLabelVerticalAlignment
import com.kubit.charts.components.axis.AxisShade
import com.kubit.charts.components.axis.HorizontalAxisChart
import com.kubit.charts.components.axis.HorizontalAxisType
import com.kubit.charts.components.axis.VerticalAxisChart
import com.kubit.charts.components.axis.VerticalAxisType
import com.kubit.charts.components.axis.model.AxisPadding
import com.kubit.charts.components.axis.model.DecorativeHeightPosition
import com.kubit.charts.components.axis.model.DecorativeWidthPosition
import com.kubit.charts.components.axis.model.ShadeRegion
import com.kubit.charts.components.axis.rememberHorizontalAxisDataState
import com.kubit.charts.components.axis.rememberVerticalAxisDataState
import com.kubit.charts.samples.components.utils.ChartsSampleColors

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun HorizontalAxisPreviewBottom() {
    HorizontalAxisChart(
        data = sampleHorizontalAxis,
        labelHeight = 50.dp,
        padding = AxisPadding(
            start = 10.dp,
            end = 10.dp,
            top = 10.dp
        )
    )
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun HorizontalAxisPreviewTop() {
    HorizontalAxisChart(
        data = sampleHorizontalAxis,
        type = HorizontalAxisType.Top,
        labelHeight = 50.dp,
        padding = AxisPadding(
            start = 10.dp,
            end = 10.dp,
            bottom = 10.dp
        )
    )
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun HorizontalAxisPreviewBottomRotated() {
    HorizontalAxisChart(
        data = sampleAxisYears,
        labelHeight = 50.dp,
        padding = AxisPadding(
            start = 100.dp,
            end = 100.dp,
            top = 10.dp
        ),
        labelRotation = -45f,
        labelVerticalAlignment = AxisLabelVerticalAlignment.Top,
        labelCenterAlignment = AxisLabelCenterAlignment.End,
        labelVerticalGap = 10.dp
    )
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun HorizontalAxisPreviewBottomRotatedLongLabels() {
    HorizontalAxisChart(
        data = sampleAxisMonths,
        labelHeight = 80.dp,
        padding = AxisPadding(
            start = 100.dp,
            end = 100.dp,
            top = 10.dp
        ),
        labelRotation = -45f,
        labelVerticalAlignment = AxisLabelVerticalAlignment.Top,
        labelCenterAlignment = AxisLabelCenterAlignment.End,
        labelVerticalGap = 10.dp
    )
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun HorizontalAxisPreviewTopRotated() {
    HorizontalAxisChart(
        data = sampleAxisYears,
        type = HorizontalAxisType.Top,
        labelHeight = 50.dp,
        padding = AxisPadding(
            start = 100.dp,
            end = 100.dp,
            bottom = 10.dp
        ),
        labelRotation = -45f,
        labelVerticalAlignment = AxisLabelVerticalAlignment.Bottom,
        labelCenterAlignment = AxisLabelCenterAlignment.Start,
        labelVerticalGap = 10.dp
    )
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun HorizontalAxisPreviewTopRotatedLongLabels() {
    HorizontalAxisChart(
        data = sampleAxisMonths,
        type = HorizontalAxisType.Top,
        labelHeight = 80.dp,
        padding = AxisPadding(
            start = 100.dp,
            end = 100.dp,
            bottom = 10.dp
        ),
        labelRotation = -45f,
        labelVerticalAlignment = AxisLabelVerticalAlignment.Bottom,
        labelCenterAlignment = AxisLabelCenterAlignment.Start,
        labelVerticalGap = 10.dp
    )
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun VerticalAxisPreviewStart() {
    VerticalAxisChart(
        data = sampleVerticalAxis,
        labelWidth = 50.dp,
        padding = AxisPadding(
            end = 10.dp,
            bottom = 10.dp,
            top = 10.dp
        )
    )
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun VerticalAxisPreviewEnd() {
    VerticalAxisChart(
        data = sampleVerticalAxis,
        type = VerticalAxisType.End,
        labelWidth = 50.dp,
        padding = AxisPadding(
            start = 10.dp,
            bottom = 10.dp,
            top = 10.dp
        )
    )
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun VerticalAxisPreviewStartRotated() {
    VerticalAxisChart(
        data = sampleAxisMonths,
        labelWidth = 80.dp,
        padding = AxisPadding(
            end = 10.dp,
            bottom = 80.dp,
            top = 80.dp
        ),
        labelRotation = -45f,
        labelHorizontalGap = 10.dp,
        labelHorizontalAlignment = AxisLabelHorizontalAlignment.End,
        labelCenterAlignment = AxisLabelCenterAlignment.End
    )
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun VerticalAxisPreviewEndRotated() {
    VerticalAxisChart(
        data = sampleAxisMonths,
        labelWidth = 70.dp,
        type = VerticalAxisType.End,
        padding = AxisPadding(
            start = 10.dp,
            end = 0.dp,
            bottom = 80.dp,
            top = 80.dp
        ),
        labelRotation = -45f,
        labelHorizontalGap = 10.dp,
        labelHorizontalAlignment = AxisLabelHorizontalAlignment.Start,
        labelCenterAlignment = AxisLabelCenterAlignment.Start
    )
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun MultipleAxisRotatedPreview() {
    Box {
        VerticalAxisChart(
            data = sampleAxisMonths,
            labelWidth = 65.dp,
            padding = AxisPadding(
                end = 10.dp,
                bottom = 50.dp,
                top = 10.dp
            ),
            decorativeHeight = 20.dp,
            decorativeHeightPosition = DecorativeHeightPosition.Top,
            labelRotation = -45f,
            labelHorizontalAlignment = AxisLabelHorizontalAlignment.End,
            labelCenterAlignment = AxisLabelCenterAlignment.End,
            labelHorizontalGap = 10.dp
        )
        HorizontalAxisChart(
            data = sampleAxisYears,
            type = HorizontalAxisType.Bottom,
            labelHeight = 50.dp,
            padding = AxisPadding(
                start = 65.dp,
                end = 10.dp,
                top = 10.dp
            ),
            decorativeWidth = 20.dp,
            labelRotation = -45f,
            labelCenterAlignment = AxisLabelCenterAlignment.End,
            labelVerticalAlignment = AxisLabelVerticalAlignment.Top,
            labelVerticalGap = 10.dp
        )
    }
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun MultipleAxisPreviewQ1() {
    Box {
        VerticalAxisChart(
            data = sampleVerticalAxis,
            labelWidth = 50.dp,
            padding = AxisPadding(
                end = 10.dp,
                bottom = 50.dp,
                top = 10.dp
            ),
            decorativeHeight = 40.dp,
            decorativeHeightPosition = DecorativeHeightPosition.Top
        )
        HorizontalAxisChart(
            data = sampleHorizontalAxis,
            type = HorizontalAxisType.Bottom,
            labelHeight = 50.dp,
            padding = AxisPadding(
                start = 50.dp,
                end = 10.dp,
                top = 10.dp
            ),
            decorativeWidth = 40.dp,
        )
    }
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun MultipleAxisPreviewQ1RangeStartEndLabels() {
    Box {
        VerticalAxisChart(
            data = sampleVerticalAxisMiddleNoLabels,
            labelWidth = 50.dp,
            padding = AxisPadding(
                end = 10.dp,
                bottom = 50.dp,
                top = 10.dp
            ),
            decorativeHeight = 40.dp,
            decorativeHeightPosition = DecorativeHeightPosition.Top
        )
        HorizontalAxisChart(
            data = sampleHorizontalAxisMiddleNoLabels,
            type = HorizontalAxisType.Bottom,
            labelHeight = 50.dp,
            padding = AxisPadding(
                start = 50.dp,
                end = 10.dp,
                top = 10.dp
            ),
            decorativeWidth = 40.dp,
        )
    }
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun MultipleAxisPreviewQ1NoLabels() {
    Box {
        VerticalAxisChart(
            data = sampleVerticalAxisNoLabels,
            labelWidth = 0.dp,
            padding = AxisPadding(
                start = 10.dp,
                end = 10.dp,
                bottom = 10.dp,
                top = 10.dp
            ),
            decorativeHeight = 40.dp,
            decorativeHeightPosition = DecorativeHeightPosition.Top
        )
        HorizontalAxisChart(
            data = sampleHorizontalAxisNoLabels,
            type = HorizontalAxisType.Bottom,
            labelHeight = 0.dp,
            padding = AxisPadding(
                start = 10.dp,
                end = 10.dp,
                top = 10.dp,
                bottom = 10.dp
            ),
            decorativeWidth = 40.dp,
        )
    }
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun MultipleAxisPreviewQ2() {
    Box {
        VerticalAxisChart(
            data = sampleVerticalAxis,
            type = VerticalAxisType.End,
            labelWidth = 50.dp,
            padding = AxisPadding(
                start = 10.dp,
                bottom = 50.dp,
                top = 10.dp
            ),
            decorativeHeight = 40.dp,
            decorativeHeightPosition = DecorativeHeightPosition.Top
        )
        HorizontalAxisChart(
            data = sampleHorizontalAxis,
            type = HorizontalAxisType.Bottom,
            labelHeight = 50.dp,
            padding = AxisPadding(
                start = 10.dp,
                end = 50.dp,
                top = 10.dp
            ),
            decorativeWidth = 40.dp,
            decorativeWidthPosition = DecorativeWidthPosition.Start
        )
    }
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun MultipleAxisPreviewQ3() {
    Box {
        VerticalAxisChart(
            data = sampleVerticalAxis,
            labelWidth = 50.dp,
            padding = AxisPadding(
                start = 10.dp,
                bottom = 10.dp,
                top = 50.dp
            ),
            type = VerticalAxisType.End,
            decorativeHeight = 40.dp,
            decorativeHeightPosition = DecorativeHeightPosition.Bottom
        )
        HorizontalAxisChart(
            data = sampleHorizontalAxis,
            labelHeight = 50.dp,
            padding = AxisPadding(
                start = 10.dp,
                end = 50.dp,
                bottom = 10.dp
            ),
            type = HorizontalAxisType.Top,
            decorativeWidth = 40.dp,
            decorativeWidthPosition = DecorativeWidthPosition.Start
        )
    }
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun MultipleAxisPreviewQ4() {
    Box {
        VerticalAxisChart(
            data = sampleVerticalAxis,
            labelWidth = 50.dp,
            padding = AxisPadding(
                end = 10.dp,
                bottom = 10.dp,
                top = 50.dp
            ),
            decorativeHeight = 40.dp,
            decorativeHeightPosition = DecorativeHeightPosition.Bottom
        )
        HorizontalAxisChart(
            data = sampleHorizontalAxis,
            labelHeight = 50.dp,
            padding = AxisPadding(
                start = 10.dp,
                end = 50.dp,
                bottom = 10.dp
            ),
            type = HorizontalAxisType.Top,
            decorativeWidth = 40.dp,
            decorativeWidthPosition = DecorativeWidthPosition.Start
        )
    }
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@Composable
fun ComplexAxisPreview() {
    ComplexAxisChartSampleHelper(
        verticalAxisData = sampleVerticalAxis,
        horizontalAxisData = sampleHorizontalAxis,
        padding = AxisPadding(
            start = 10.dp,
            end = 10.dp,
            bottom = 10.dp,
            top = 10.dp
        ),
        axisDecorativeSize = 10.dp,
        verticalStepForHAxisOffset = sampleVerticalAxis.axisSteps[2],
        horizontalStepForVAxisOffset = sampleHorizontalAxis.axisSteps[3],
        shadeRegion = ShadeRegion(
            fromX = -6f,
            toX = 0f,
            fromY = -4f,
            toY = 0f,
            color = ChartsSampleColors.darkGray.copy(alpha = 0.5f)
        )
    )
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500
)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Suppress("MagicNumber")
@Composable
fun MultipleAxisPreviewWithAxisShade() {
    BoxWithConstraints {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        with(LocalDensity.current) {
            val horizontalAxisState = rememberHorizontalAxisDataState(
                data = sampleHorizontalAxis,
                width = width.toFloat(),
                startPaddingInPx = 50.dp.toPx(),
                endPaddingInPx = 10.dp.toPx(),
                decorativeWidthInPx = 40.dp.toPx(),
                decorativeWidthPosition = DecorativeWidthPosition.End
            )

            val verticalAxisState = rememberVerticalAxisDataState(
                data = sampleVerticalAxis,
                height = height.toFloat(),
                topPaddingInPx = 10.dp.toPx(),
                bottomPaddingInPx = 50.dp.toPx(),
                decorativeHeightInPx = 40.dp.toPx(),
                decorativeHeightPosition = DecorativeHeightPosition.Top
            )

            AxisShade(
                fromX = horizontalAxisState.processedAxisData.toCanvasPosition(-6f),
                toX = horizontalAxisState.processedAxisData.toCanvasPosition(0f),
                fromY = verticalAxisState.processedAxisData.toCanvasPosition(-4f),
                toY = verticalAxisState.processedAxisData.toCanvasPosition(0f),
                color = ChartsSampleColors.darkGray.copy(alpha = 0.5f),
            )

            HorizontalAxisChart(
                chartState = horizontalAxisState,
                type = HorizontalAxisType.Bottom,
                labelHeight = 50.dp,
                padding = AxisPadding(
                    start = 50.dp,
                    end = 10.dp,
                    top = 10.dp
                )
            )

            VerticalAxisChart(
                chartState = verticalAxisState,
                labelWidth = 50.dp,
                padding = AxisPadding(
                    end = 10.dp,
                    bottom = 50.dp,
                    top = 10.dp
                )
            )
        }
    }
}
