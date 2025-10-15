package com.kubit.charts.components.axis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity

/**
 * Component to draw a shade region for an axis.
 *
 * You will need to create an [AxisChartState] using [rememberVerticalAxisDataState] or
 * [rememberHorizontalAxisDataState] to be able to convert values from the chart world to
 * values to the canvas world.
 *
 * @param fromX The starting X position of the shade region.
 * @param toX The ending X position of the shade region.
 * @param fromY The starting Y position of the shade region.
 * @param toY The ending Y position of the shade region.
 * @param color The color of the shade region.
 * @param modifier Optional [Modifier] for the composable.
 *
 * @sample com.kubit.charts.samples.components.axis.MultipleAxisPreviewWithAxisShade
 */
@Composable
fun AxisShade(
    fromX: Float,
    toX: Float,
    fromY: Float,
    toY: Float,
    color: Color,
    modifier: Modifier = Modifier,
) {
    with(LocalDensity.current) {
        Box(
            modifier = modifier
                .size(
                    (toX - fromX).toDp(),
                    -(toY - fromY).toDp()
                )
                .offset(
                    fromX.toDp(),
                    toY.toDp()
                )
                .background(color = color)
        )
    }
}
