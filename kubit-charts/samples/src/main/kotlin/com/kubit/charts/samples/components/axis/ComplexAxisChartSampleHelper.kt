package com.kubit.charts.samples.components.axis

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.axis.AxisShade
import com.kubit.charts.components.axis.HorizontalAxisChart
import com.kubit.charts.components.axis.HorizontalAxisType
import com.kubit.charts.components.axis.VerticalAxisChart
import com.kubit.charts.components.axis.VerticalAxisType
import com.kubit.charts.components.axis.model.AxisData
import com.kubit.charts.components.axis.model.AxisPadding
import com.kubit.charts.components.axis.model.AxisStep
import com.kubit.charts.components.axis.model.DecorativeHeightPosition
import com.kubit.charts.components.axis.model.DecorativeWidthPosition
import com.kubit.charts.components.axis.model.ShadeRegion
import com.kubit.charts.components.axis.rememberHorizontalAxisDataState
import com.kubit.charts.components.axis.rememberVerticalAxisDataState

/**
 * A "complex" axis chart is a chart that has both horizontal and vertical axes AND the axes positions
 * (offsets) depends on a value of the other axis. The main use case this helper class helps to solve is
 * the case you want to set the horizontal axis Y padding and/or the vertical axis X padding to something
 * defined by other axis (e.g. the horizontal axis Y padding is defined by an Y-axis value)
 * This is a helper class that combines [com.kubit.charts.components.axis.HorizontalAxisChart] and [com.kubit.charts.components.axis.VerticalAxisChart] composable. The same
 * behavior can be achieved using that composables separately.
 * @param verticalAxisData The data for the vertical axis.
 * @param horizontalAxisData The data for the horizontal axis.
 * @param padding The padding for both axis. If you need different paddings for each axis you will
 * need to use [com.kubit.charts.components.axis.HorizontalAxisChart] and [com.kubit.charts.components.axis.VerticalAxisChart] separately.
 * @param axisDecorativeSize The size of the decorative width/height at the end of the axis.
 * @param verticalStepForHAxisOffset The step in the vertical axis that will be used to offset the horizontal axis.
 * @param horizontalStepForVAxisOffset The step in the horizontal axis that will be used to offset the vertical axis.
 * @param modifier Optional [Modifier] for the composable.
 * @param shadeRegion Optional shade region to draw in the chart.
 *
 * @see com.kubit.charts.components.axis.HorizontalAxisChart
 * @see com.kubit.charts.components.axis.VerticalAxisChart
 * @see ShadeRegion
 *
 * @sample com.kubit.charts.samples.components.axis.ComplexAxisPreview
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ComplexAxisChartSampleHelper(
    verticalAxisData: AxisData,
    horizontalAxisData: AxisData,
    padding: AxisPadding,
    axisDecorativeSize: Dp,
    verticalStepForHAxisOffset: AxisStep,
    horizontalStepForVAxisOffset: AxisStep,
    modifier: Modifier = Modifier,
    shadeRegion: ShadeRegion? = null
) {
    BoxWithConstraints(modifier = modifier) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        with(LocalDensity.current) {
            val horizontalAxisState = rememberHorizontalAxisDataState(
                data = horizontalAxisData,
                width = width.toFloat(),
                startPaddingInPx = padding.start.toPx(),
                endPaddingInPx = padding.end.toPx(),
                decorativeWidthInPx = axisDecorativeSize.toPx(),
                decorativeWidthPosition = DecorativeWidthPosition.Both
            )
            val horizontalAxisStep =
                horizontalAxisState.getAxisStepByValue(horizontalStepForVAxisOffset.axisValue)

            val verticalAxisState = rememberVerticalAxisDataState(
                data = verticalAxisData,
                height = height.toFloat(),
                topPaddingInPx = padding.top.toPx(),
                bottomPaddingInPx = padding.bottom.toPx(),
                decorativeHeightInPx = axisDecorativeSize.toPx(),
                decorativeHeightPosition = DecorativeHeightPosition.Both
            )
            val verticalAxisStep =
                verticalAxisState.getAxisStepByValue(verticalStepForHAxisOffset.axisValue)

            shadeRegion?.let {
                AxisShade(
                    fromX = horizontalAxisState.processedAxisData.toCanvasPosition(shadeRegion.fromX),
                    toX = horizontalAxisState.processedAxisData.toCanvasPosition(shadeRegion.toX),
                    fromY = verticalAxisState.processedAxisData.toCanvasPosition(shadeRegion.fromY),
                    toY = verticalAxisState.processedAxisData.toCanvasPosition(shadeRegion.toY),
                    color = it.color,
                )
            }

            HorizontalAxisChart(
                chartState = horizontalAxisState,
                type = HorizontalAxisType.Bottom,
                labelHeight = 0.dp,
                padding = padding,
                customYOffset = verticalAxisStep?.axisValueInPx?.toDp(),
            )

            VerticalAxisChart(
                chartState = verticalAxisState,
                type = VerticalAxisType.Start,
                labelWidth = 0.dp,
                padding = padding,
                customXOffset = horizontalAxisStep?.axisValueInPx?.toDp(),
            )
        }
    }
}
