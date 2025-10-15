package com.kubit.charts.components.chart.plotchart

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.axis.model.AxisData
import com.kubit.charts.components.chart.linechart.model.Point
import com.kubit.charts.components.chart.plotchart.model.PlotChartBackgroundData
import com.kubit.charts.components.chart.plotchart.model.PlotChartData
import com.kubit.charts.components.chart.plotchart.model.PlotCustomChartData
import com.kubit.charts.components.chart.plotchart.model.PlotShapeChartData
import kotlinx.collections.immutable.PersistentList

/**
 * Displays a plot chart using the provided axis data, step sizes, label size, and optional modifiers.
 *
 * ```kotlin
 * PlotChart(
 *   data = listOf(
 *     PlotShapeChartData(point = Point(1f, 2f), color = Color.Red),
 *     PlotCustomChartData(point = Point(2f, 3f)) { zoom -> /* Custom composable */ }
 *   ),
 *   xAxisData = xAxisData,
 *   yAxisData = yAxisData,
 *   xAxisStepSize = 32.dp,
 *   yaxisStepSize = 32.dp,
 *   labelSize = 16.dp,
 *   onPlotClick = { plot -> /* Handle click */ },
 *   modifier = Modifier,
 *   horizontalScroll = 0.dp,
 *   verticalScroll = 0.dp,
 *   zoom = 1f,
 *   backgroundData = null
 * )
 * ```
 *
 * @param data List of plot points to display on the chart.
 * @param xAxisData Data describing the X-axis.
 * @param yAxisData Data describing the Y-axis.
 * @param xAxisStepSize Step size for the X-axis.
 * @param yaxisStepSize Step size for the Y-axis.
 * @param onPlotClick Callback invoked when a plot point is clicked, passing the clicked [PlotChartData].
 * @param modifier Modifier applied to the chart container.
 * @param horizontalScroll Horizontal scroll offset for the chart. Default is 0.dp.
 * @param verticalScroll Vertical scroll offset for the chart. Default is 0.dp.
 * @param zoom Zoom level for the chart. Default is 1f (no zoom).
 * @param backgroundData Optional background data, including image and dimensions for the chart.
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun PlotChart(
    data: PersistentList<PlotChartData>,
    xAxisData: AxisData,
    yAxisData: AxisData,
    xAxisStepSize: Dp,
    yaxisStepSize: Dp,
    onPlotClick: (PlotChartData) -> Unit,
    modifier: Modifier = Modifier,
    horizontalScroll: Dp = 0.dp,
    verticalScroll: Dp = 0.dp,
    zoom: Float = 1f,
    backgroundData: PlotChartBackgroundData? = null,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
    ) {
        val density = LocalDensity.current
        val yBottom = constraints.maxHeight.toFloat()
        val minX = xAxisData.range.start
        val minY = yAxisData.range.start

        backgroundData?.let { data ->
            RenderBackground(
                background = data,
                xAxisStepSize = xAxisStepSize,
                yaxisStepSize = yaxisStepSize,
                horizontalScroll = horizontalScroll,
                verticalScroll = verticalScroll,
                minX = minX,
                minY = minY,
                yBottom = yBottom,
                zoom = zoom,
                density = density
            )
        }

        data.forEach { plot ->
            val position = calculatePlotPosition(
                point = plot.point,
                minX = minX,
                minY = minY,
                xAxisStepSize = xAxisStepSize,
                yaxisStepSize = yaxisStepSize,
                horizontalScroll = horizontalScroll,
                verticalScroll = verticalScroll,
                zoom = zoom,
                yBottom = yBottom,
                density = density
            )
            RenderPlot(
                plot = plot,
                position = position,
                zoom = zoom,
                onPlotClick = onPlotClick
            )
        }
    }
}

@Composable
private fun RenderPlot(
    plot: PlotChartData,
    position: Point,
    zoom: Float,
    onPlotClick: (PlotChartData) -> Unit
) {
    when (plot) {
        is PlotCustomChartData -> PlotCustom(
            point = position,
            zoom = zoom,
            content = plot.customPlot
        )

        is PlotShapeChartData -> PlotShape(
            size = plot.size * zoom,
            shape = plot.shape,
            color = plot.color,
            point = position,
            contentDescription = plot.contentDescription,
            onPlotClick = { onPlotClick(plot) }
        )
    }
}

@Composable
private fun PlotShape(
    size: Dp,
    shape: Shape,
    color: Color,
    point: Point,
    contentDescription: String?,
    onPlotClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .plotShapeModifier(
                density = LocalDensity.current,
                size = size,
                point = point,
                contentDescription = contentDescription,
                onClick = onPlotClick
            )
            .clip(shape)
            .background(color)
    )
}

@Composable
private fun PlotCustom(
    zoom: Float,
    point: Point,
    content: @Composable (Float) -> Unit
) {
    var composableWidth by remember {
        mutableIntStateOf(0)
    }

    var composableHeight by remember {
        mutableIntStateOf(0)
    }

    val density = LocalDensity.current
    val x = with(density) { (point.x - composableWidth / 2).toDp() }
    val y = with(density) { (point.y - composableHeight / 2).toDp() }

    Box(
        modifier = Modifier
            .onSizeChanged {
                composableHeight = it.height
                composableWidth = it.width
            }
            .offset(x, y)

    ) {
        content(zoom)
    }
}

@Composable
private fun RenderBackground(
    background: PlotChartBackgroundData,
    xAxisStepSize: Dp,
    yaxisStepSize: Dp,
    horizontalScroll: Dp,
    verticalScroll: Dp,
    zoom: Float,
    density: Density,
    minX: Float,
    minY: Float,
    yBottom: Float
) {
    with(density) {
        val chartWidth =
            (background.widthPoints.second - background.widthPoints.first) * xAxisStepSize.toPx() * zoom
        val chartHeight =
            (background.heightPoints.second - background.heightPoints.first) * yaxisStepSize.toPx() * zoom
        val offsetX =
            ((background.widthPoints.first - minX) * xAxisStepSize.toPx() * zoom) - horizontalScroll.toPx()
        val offsetY =
            (yBottom - chartHeight) - ((background.heightPoints.first - minY) * yaxisStepSize.toPx() * zoom) + verticalScroll.toPx()

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            with(background.backgroundImageBitmap) {
                drawImage(
                    image = this,
                    srcOffset = IntOffset(0, 0),
                    dstOffset = IntOffset(
                        offsetX.toInt(),
                        offsetY.toInt()
                    ),
                    dstSize = IntSize(
                        chartWidth.toInt(),
                        chartHeight.toInt()
                    )
                )
            }
        }
    }
}

private fun calculatePlotPosition(
    point: Point,
    minX: Float,
    minY: Float,
    xAxisStepSize: Dp,
    yaxisStepSize: Dp,
    horizontalScroll: Dp,
    verticalScroll: Dp,
    zoom: Float,
    yBottom: Float,
    density: Density
): Point {
    val finalX = density.run {
        ((point.x - minX) * zoom * xAxisStepSize.toPx()) - horizontalScroll.toPx()
    }
    val finalY = density.run {
        (yBottom - ((point.y - minY) * zoom * yaxisStepSize.toPx()) + verticalScroll.toPx())
    }
    return Point(finalX, finalY)
}

private fun Modifier.plotShapeModifier(
    density: Density,
    size: Dp,
    point: Point,
    contentDescription: String?,
    onClick: () -> Unit
): Modifier {
    val x = with(density) { point.x.toDp() - size / 2 }
    val y = with(density) { point.y.toDp() - size / 2 }
    return this
        .size(size)
        .offset(x = x, y = y)
        .then(if (contentDescription == null) {
            Modifier
        } else {
            Modifier.semantics {
                this.contentDescription = contentDescription
            }
        })
        .focusable()
        .clickable { onClick() }
}
