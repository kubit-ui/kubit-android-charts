package com.kubit.charts.samples.components.plotchart

import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.axis.HorizontalAxisChart
import com.kubit.charts.components.axis.HorizontalAxisType
import com.kubit.charts.components.axis.VerticalAxisChart
import com.kubit.charts.components.axis.VerticalAxisType
import com.kubit.charts.components.axis.model.AxisBuilder
import com.kubit.charts.components.axis.model.AxisPadding
import com.kubit.charts.components.chart.linechart.model.Point
import com.kubit.charts.components.chart.plotchart.PlotChart
import com.kubit.charts.components.chart.plotchart.model.PlotChartBackgroundData
import com.kubit.charts.components.chart.plotchart.model.PlotCustomChartData
import com.kubit.charts.components.chart.plotchart.model.PlotShapeChartData
import com.kubit.charts.components.scaffold.ChartScaffold
import com.kubit.charts.components.utils.SquaredShape
import com.kubit.charts.components.utils.TriangleShape
import com.kubit.charts.samples.R
import androidx.compose.ui.platform.LocalResources
import com.kubit.charts.samples.components.utils.ChartsSampleColors
import kotlinx.collections.immutable.persistentListOf

@Preview(heightDp = 400)
@Composable
fun PlotChartSample() {
    ChartScaffold(
        modifier = Modifier
            .background(Color.White)
            .clipToBounds(),
        isPinchZoomEnabled = true,
        xAxisData = Steps,
        yAxisData = Steps,
        xUnitSize = FixedUnitSize,
        yUnitSize = FixedUnitSize,
        axisPadding = AxisPadding(start = LabelSize, bottom = LabelSize),
        horizontalAxis = { horizontalScroll, zoom, padding ->
            HorizontalAxisChart(
                data = Steps,
                type = HorizontalAxisType.Bottom,
                fixedUnitSize = FixedUnitSize,
                labelHeight = LabelSize,
                padding = padding,
                horizontalScroll = horizontalScroll,
                zoom = zoom
            )
        },
        verticalAxis = { verticalScroll, zoom, padding ->
            VerticalAxisChart(
                data = Steps,
                type = VerticalAxisType.Start,
                labelWidth = LabelSize,
                fixedUnitSize = FixedUnitSize,
                padding = padding,
                verticalScroll = verticalScroll,
                zoom = zoom
            )
        },
        content = { contentData ->
            with(contentData) {
                PlotChart(
                    data = getPlotList(),
                    xAxisData = Steps,
                    yAxisData = Steps,
                    xAxisStepSize = FixedUnitSize,
                    yaxisStepSize = FixedUnitSize,
                    horizontalScroll = horizontalScroll,
                    verticalScroll = verticalScroll,
                    zoom = zoom,
                    onPlotClick = {},
                )
            }
        }
    )
}

@Preview(widthDp = 400, heightDp = 300)
@Composable
@Suppress("LongMethod", "MagicNumber")
fun PlotChartWithBackgroundSample() {

    val density = LocalResources.current.displayMetrics.run {
        Density(density)
    }

    val width = with(density) { 400.dp.toPx() }
    val height = with(density) { 200.dp.toPx() }

    val backgroundData =
        PlotChartBackgroundData(
            widthPoints = Pair(0f, 8f),
            heightPoints = Pair(0f, 7f),
            backgroundImageBitmap = painterResource(R.drawable.world).toImageBitmap(
                size = Size(width, height),
                density = density,
                layoutDirection = LayoutDirection.Ltr
            )

        )

    ChartScaffold(
        modifier = Modifier
            .background(Color.White)
            .height(FixedUnitSize.times(StepsMapY.axisSteps.size))
            .clipToBounds(),
        isPinchZoomEnabled = true,
        xAxisData = StepsMapX,
        yAxisData = StepsMapY,
        xUnitSize = FixedUnitSize,
        yUnitSize = FixedUnitSize,
        horizontalAxis = { horizontalScroll, zoom, padding ->
        },
        verticalAxis = { verticalScroll, zoom, padding ->
        },
        content = { contentData ->
            with(contentData) {
                PlotChart(
                    data = getPlotListData(),
                    xAxisData = StepsMapX,
                    yAxisData = StepsMapY,
                    xAxisStepSize = FixedUnitSize,
                    yaxisStepSize = FixedUnitSize,
                    horizontalScroll = horizontalScroll,
                    verticalScroll = verticalScroll,
                    zoom = zoom,
                    backgroundData = backgroundData,
                    onPlotClick = {},
                )
            }
        }
    )
}

fun Painter.toImageBitmap(
    size: Size,
    density: Density,
    layoutDirection: LayoutDirection,
): ImageBitmap {
    val bitmap = ImageBitmap(size.width.toInt(), size.height.toInt())
    val canvas = Canvas(bitmap)
    CanvasDrawScope().draw(density, layoutDirection, canvas, size) {
        draw(size)
    }
    return bitmap
}

// CHART VARIABLES
@Suppress("MagicNumber")
@Composable
private fun getPlotList() = persistentListOf(
    PlotShapeChartData(
        point = Point(3f, 1f),
        size = 40.dp,
        color = ChartsSampleColors.colorRed65,
        contentDescription = "Point 1, circle shape"
    ),
    PlotCustomChartData(
        point = Point(2f, 2f),
        customPlot = { zoom ->
            Box(
                modifier = Modifier
                    .size(40.dp.times(zoom))
                    .clip(SquaredShape(centered = false))
                    .background(ChartsSampleColors.colorGreen75)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        enabled = true,
                        onClick = { }
                    )
                    .semantics {
                        contentDescription = "Point 2, squared shape"
                    }
            )
        }
    ),
    PlotCustomChartData(
        point = Point(1f, 3f),
        customPlot = { zoom ->
            Box(
                modifier = Modifier
                    .size(40.dp.times(zoom))
                    .clip(TriangleShape(centered = false))
                    .background(ChartsSampleColors.colorGreen75)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        enabled = true,
                        onClick = { }
                    )
                    .semantics {
                        contentDescription = "Point 3, triangle shape"
                    }
            )
        }
    ),
    PlotCustomChartData(
        point = Point(0f, 4f),
        customPlot = { zoom ->
            Icon(
                painter = painterResource(R.drawable.star),
                contentDescription = "Point 4, Custom Icon",
                modifier = Modifier.size(40.dp.times(zoom))
            )
        }
    ),
)

@Suppress("MagicNumber")
@Composable
private fun getPlotListData() = persistentListOf(
    PlotShapeChartData(
        point = Point(2f, 5f),
        size = 20.dp,
        color = ChartsSampleColors.colorRed50,
        contentDescription = "Point 1"
    ),
    PlotShapeChartData(
        point = Point(3f, 2.5f),
        size = 40.dp,
        color = ChartsSampleColors.colorBlue50,
        contentDescription = "Point 2"
    ),
    PlotShapeChartData(
        point = Point(5f, 4f),
        size = 12.dp,
        color = ChartsSampleColors.colorGreen75,
        contentDescription = "Point 3"
    ),
)

private val Steps = AxisBuilder()
    .addNode(-1f, "-1")
    .addNode(0f, "0")
    .addNode(1f, "1")
    .addNode(2f, "2")
    .addNode(3f, "3")
    .addNode(4f, "4")
    .addNode(5f, "5")
    .addNode(6f, "6")
    .addNode(7f, "7")
    .addNode(8f, "8")
    .addNode(9f, "9")
    .addNode(10f, "10")
    .build()

private val StepsMapX = AxisBuilder()
    .addNode(0f, "0")
    .addNode(1f, "1")
    .addNode(2f, "2")
    .addNode(3f, "3")
    .addNode(4f, "4")
    .addNode(5f, "5")
    .addNode(6f, "6")
    .addNode(7f, "7")
    .addNode(8f, "8")
    .addNode(9f, "9")
    .build()

private val StepsMapY = AxisBuilder()
    .addNode(0f, "0")
    .addNode(1f, "1")
    .addNode(2f, "2")
    .addNode(3f, "3")
    .addNode(4f, "4")
    .addNode(5f, "5")
    .addNode(6f, "6")
    .addNode(7f, "7")
    .build()

private val FixedUnitSize = 50.dp
private val LabelSize = 20.dp
