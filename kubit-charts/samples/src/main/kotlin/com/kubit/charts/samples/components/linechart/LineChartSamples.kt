@file:Suppress("MagicNumber", "LongMethod")

package com.kubit.charts.samples.components.linechart

import android.widget.Toast
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kubit.charts.components.axis.model.axisBuilder
import com.kubit.charts.components.chart.linechart.LineChart
import com.kubit.charts.components.chart.linechart.model.IntersectionComposable
import com.kubit.charts.components.chart.linechart.model.IntersectionNodeAccessibility
import com.kubit.charts.components.chart.linechart.model.IntersectionPainter
import com.kubit.charts.components.chart.linechart.model.IntersectionPoint
import com.kubit.charts.components.chart.linechart.model.IntersectionPointWithLine
import com.kubit.charts.components.chart.linechart.model.IntersectionRect
import com.kubit.charts.components.chart.linechart.model.IntersectionShape
import com.kubit.charts.components.chart.linechart.model.Line
import com.kubit.charts.components.chart.linechart.model.LineStyle
import com.kubit.charts.components.chart.linechart.model.LineType
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPoint
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPopUp
import com.kubit.charts.components.chart.linechart.model.ShadowUnderLine
import com.kubit.charts.components.chart.linechart.model.lineBuilder
import com.kubit.charts.components.scaffold.ChartScaffold
import com.kubit.charts.components.scaffold.ScrollableAccessibilityItem
import com.kubit.charts.components.utils.trianglePath
import com.kubit.charts.samples.R
import com.kubit.charts.samples.components.utils.ChartsSampleColors
import com.kubit.charts.samples.components.utils.getLineChartData
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
fun LineChartWithPointsFitsInContainerSample() {
    val pointsData = getLineChartData(
        25,
        start = 0,
        maxRange = 50
    )

    LineChart(
        modifier = Modifier.heightIn(max = 300.dp),
        lines = listOf(
            lineBuilder {
                addPoints(pointsData) { index ->
                    IntersectionPoint(
                        accessibility = IntersectionNodeAccessibility(
                            contentDescription = "Point ${pointsData[index].x} ${pointsData[index].y}"
                        ),
                        color = ChartsSampleColors.colorGreen75,
                        radius = 4.dp
                    )
                }
                setLineStyle(
                    LineStyle(
                        lineType = LineType.SmoothCurve(isDotted = false),
                        color = ChartsSampleColors.colorGreen75,
                        width = 5f
                    )
                )
                setSelectionHighlightPoint(SelectionHighlightPoint())
                setSelectionHighlightPopUp(
                    SelectionHighlightPopUp()
                )
            }
        ).toImmutableList(),
    )
}

@Preview
@Composable
fun LineChartWithPointsSample() {
    val pointsData = getLineChartData(
        100,
        start = 0,
        maxRange = 50
    )
    val xAxisData = getXAxisData(
        pointsData,
    )
    val yAxisData = getYAxisData(
        pointsData
    )

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp,
        accessibility = ScrollableAccessibilityItem(
            contentDescription = "Line chart with points",
            leftCustomAction = "Scroll left",
            rightCustomAction = "Scroll right",
            downCustomAction = "Scroll down",
            upCustomAction = "Scroll up",
            zoomInCustomAction = "Zoom in",
            zoomOutCustomAction = "Zoom out"
        )
    ) { contentData ->
        with(contentData) {
            LineChart(
                modifier = Modifier.heightIn(max = 300.dp),
                lines = listOf(
                    lineBuilder {
                        addPoints(pointsData) { index ->
                            IntersectionPoint(
                                accessibility = IntersectionNodeAccessibility(
                                    contentDescription = "Point ${pointsData[index].x} ${pointsData[index].y}"
                                ),
                                color = ChartsSampleColors.colorTurquoise60,
                                radius = 4.dp
                            )
                        }
                        setSelectionHighlightPoint(SelectionHighlightPoint())
                        setSelectionHighlightPopUp(
                            SelectionHighlightPopUp()
                        )
                        setLineStyle(
                            LineStyle(
                                color = ChartsSampleColors.colorTurquoise60,
                                width = 4f
                            )
                        )
                    }
                ).toImmutableList(),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                xAxisStepSize = 30.dp,
                yAxisStepSize = 5.dp,
                horizontalScroll = horizontalScroll,
                zoom = zoom,
                onPointSelect = { point, offset -> },
                onHorizontalScrollChangeRequest = onHorizontalScrollChangeRequest,
                onVerticalScrollChangeRequest = onVerticalScrollChangeRequest
            )
        }
    }
}

@Preview
@Composable
fun LineChartWithProminentPointsSample() {
    val pointsData = getLineChartData(
        100,
        start = 0,
        maxRange = 50
    )
    val xAxisData = getXAxisData(
        pointsData,
    )
    val yAxisData = getYAxisData(
        pointsData
    )

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp
    ) { contentData ->
        with(contentData) {
            LineChart(
                modifier = Modifier.heightIn(max = 300.dp),
                lines = listOf(
                    lineBuilder {
                        addPoints(pointsData) { index ->
                            if (index == 5) {
                                IntersectionPointWithLine()
                            } else {
                                IntersectionPoint(radius = 3.dp)
                            }
                        }
                        setSelectionHighlightPoint(SelectionHighlightPoint())
                        setSelectionHighlightPopUp(
                            SelectionHighlightPopUp()
                        )
                    }
                ).toImmutableList(),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                xAxisStepSize = 30.dp,
                yAxisStepSize = 5.dp,
                horizontalScroll = horizontalScroll,
                zoom = zoom,
                onPointSelect = { point, offset -> },
            )
        }
    }
}

@Preview
@Composable
fun LineChartWithImageNodesSample() {
    val pointsData = getLineChartData(
        100,
        start = 0,
        maxRange = 50
    )
    val xAxisData = getXAxisData(
        pointsData,
    )
    val yAxisData = getYAxisData(
        pointsData
    )

    val node = painterResource(id = R.drawable.circle)
    val painterSizePx = with(LocalDensity.current) { 12.dp.toPx() }

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp
    ) { contentData ->
        with(contentData) {
            LineChart(
                modifier = Modifier.heightIn(max = 300.dp),
                lines = listOf(
                    lineBuilder {
                        addPoints(pointsData) {
                            IntersectionPainter(
                                size = Size(
                                    painterSizePx,
                                    painterSizePx
                                ),
                                painter = node
                            )
                        }
                        setLineStyle(
                            LineStyle(
                                color = ChartsSampleColors.colorTurquoise60
                            )
                        )
                    }
                ).toImmutableList(),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                xAxisStepSize = 30.dp,
                yAxisStepSize = 5.dp,
                horizontalScroll = horizontalScroll,
                zoom = zoom,
                onPointSelect = { point, offset -> },
            )
        }
    }
}

@Preview
@Composable
fun LineChartWithSquareNodesSample() {
    val pointsData = getLineChartData(
        100,
        start = 0,
        maxRange = 50
    )
    val xAxisData = getXAxisData(
        pointsData,
    )
    val yAxisData = getYAxisData(
        pointsData
    )

    val rectSizePx = with(LocalDensity.current) { 10.dp.toPx() }

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp
    ) { contentData ->
        with(contentData) {
            LineChart(
                modifier = Modifier.heightIn(max = 300.dp),
                lines = listOf(
                    lineBuilder {
                        addPoints(pointsData) {
                            IntersectionRect(
                                size = Size(
                                    rectSizePx,
                                    rectSizePx
                                ),
                                color = ChartsSampleColors.colorRed30
                            )
                        }
                        setLineStyle(
                            LineStyle(
                                color = ChartsSampleColors.colorRed30,
                                width = 5f
                            )
                        )
                    }
                ).toImmutableList(),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                xAxisStepSize = 30.dp,
                yAxisStepSize = 5.dp,
                horizontalScroll = horizontalScroll,
                zoom = zoom,
                onPointSelect = { point, offset -> },
            )
        }
    }
}

@Preview
@Composable
fun LineChartWithShapeNodesSample() {
    val pointsData = getLineChartData(
        100,
        start = 0,
        maxRange = 50
    )
    val xAxisData = getXAxisData(
        pointsData,
    )
    val yAxisData = getYAxisData(
        pointsData
    )

    val density = LocalDensity.current

    val shapeWidth = with(density) { 12.dp.toPx() }
    val shapeHeight = with(density) { 12.dp.toPx() }

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp
    ) { contentData ->
        with(contentData) {
            LineChart(
                modifier = Modifier.heightIn(max = 300.dp),
                lines = listOf(
                    lineBuilder {
                        addPoints(pointsData) {
                            IntersectionShape(
                                color = ChartsSampleColors.colorPurple55,
                                style = Stroke(
                                    width = with(density) { 2.dp.toPx() }
                                ),
                                path = trianglePath(
                                    Size(shapeWidth, shapeHeight)
                                )
                            )
                        }
                        setLineStyle(
                            LineStyle(
                                color = ChartsSampleColors.colorPurple55,
                                width = 5f
                            )
                        )
                    }
                ).toImmutableList(),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                xAxisStepSize = 30.dp,
                yAxisStepSize = 5.dp,
                horizontalScroll = horizontalScroll,
                zoom = zoom,
                onPointSelect = { point, offset -> },
            )
        }
    }
}

@Preview
@Composable
fun LineChartWithComposableNodesSample() {
    val pointsData = getLineChartData(
        100,
        start = 0,
        maxRange = 50
    )
    val xAxisData = getXAxisData(
        pointsData,
    )
    val yAxisData = getYAxisData(
        pointsData
    )

    val context = LocalContext.current

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp
    ) { contentData ->
        with(contentData) {
            LineChart(
                modifier = Modifier.heightIn(max = 300.dp),
                lines = listOf(
                    lineBuilder {
                        addPoints(pointsData) {
                            IntersectionComposable { modifier, point ->
                                Button(
                                    modifier = modifier,
                                    onClick = {
                                        Toast.makeText(
                                            context,
                                            "Point(${point.x},${point.y}) clicked!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = ChartsSampleColors.colorYellow70)
                                ) {
                                    Text(text = "Test button!", fontSize = 6.sp)
                                }
                            }
                        }
                        setLineStyle(
                            LineStyle(
                                color = ChartsSampleColors.colorYellow70,
                                width = 5f
                            )
                        )
                    }
                ).toImmutableList(),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                xAxisStepSize = 30.dp,
                yAxisStepSize = 5.dp,
                horizontalScroll = horizontalScroll,
                zoom = zoom,
                onPointSelect = { point, offset -> },
            )
        }
    }
}

@Preview
@Composable
fun LineChartWithMixedNodesSample() {
    val pointsData = getLineChartData(
        100,
        start = 0,
        maxRange = 50
    )
    val xAxisData = getXAxisData(
        pointsData,
    )
    val yAxisData = getYAxisData(
        pointsData
    )

    val context = LocalContext.current

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp
    ) { contentData ->
        with(contentData) {
            LineChart(
                modifier = Modifier.heightIn(max = 300.dp),
                lines = listOf(
                    lineBuilder {
                        addPoints(pointsData) {
                        if (it % 2 == 0) {
                            IntersectionPoint(color = ChartsSampleColors.colorTurquoise60)
                        } else {
                            IntersectionComposable { modifier, point ->
                                Button(
                                    modifier = modifier,
                                    onClick = {
                                        Toast.makeText(
                                            context,
                                            "Point(${point.x},${point.y}) clicked!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = ChartsSampleColors.colorTurquoise60)
                                ) {
                                    Text(text = "Test button!", fontSize = 6.sp)
                                }
                            }
                        }
                        }
                        setLineStyle(
                            LineStyle(
                                color = ChartsSampleColors.colorTurquoise60,
                                width = 5f
                            )
                        )
                    }
                ).toImmutableList(),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                xAxisStepSize = 30.dp,
                yAxisStepSize = 5.dp,
                horizontalScroll = horizontalScroll,
                zoom = zoom,
                onPointSelect = { point, offset -> },
            )
        }
    }
}

@Preview
@Composable
fun LineChartWithoutPointsSample() {
    val pointsData = getLineChartData(
        100,
        start = 50,
        maxRange = 100
    )

    val xAxisData = getXAxisData(
        pointsData,
    )
    val yAxisData = getYAxisData(
        pointsData
    )

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp,
        content = { contentData ->
            with(contentData) {
                LineChart(
                    modifier = Modifier.heightIn(max = 300.dp),
                    lines = listOf(
                        lineBuilder {
                            addPoints(pointsData)
                        }
                    ).toImmutableList(),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    xAxisStepSize = 30.dp,
                    yAxisStepSize = 5.dp,
                    horizontalScroll = horizontalScroll,
                    zoom = zoom,
                    onPointSelect = { point, offset -> },
                )
            }
        }
    )
}
@Preview
@Composable
fun LineChartGradientColorSample() {
    val pointsData = getLineChartData(
        100,
        start = 50,
        maxRange = 100
    )

    val xAxisData = getXAxisData(
        pointsData,
    )

    val yAxisData = getYAxisData(
        pointsData
    )

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp,
        content = { contentData ->
            with(contentData) {
                LineChart(
                    modifier = Modifier.heightIn(max = 300.dp),
                    lines = listOf(
                        lineBuilder {
                            addPoints(pointsData)
                            setLineStyle(LineStyle(color = ChartsSampleColors.colorGreen75))
                            setSelectionHighlightPoint(
                                SelectionHighlightPoint()
                            )
                            setSelectionHighlightPopUp(
                                SelectionHighlightPopUp()
                            )
                            setShadowUnderLine(
                                ShadowUnderLine(
                                    brush = Brush.verticalGradient(
                                        listOf(
                                            ChartsSampleColors.colorGreen75,
                                            Color.Transparent
                                        )
                                    ),
                                    alpha = 0.3f,
                                )
                            )
                        }
                    ).toImmutableList(),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    xAxisStepSize = 30.dp,
                    yAxisStepSize = 5.dp,
                    horizontalScroll = horizontalScroll,
                    zoom = zoom,
                    onPointSelect = { point, offset -> },
                )
            }
        }
    )
}

@Preview
@Composable
fun LineChartDottedSingleSample() {
    val pointsData = getLineChartData(
        100,
        start = 50,
        maxRange = 100
    )

    val xAxisData = getXAxisData(
        pointsData,
    )
    val yAxisData = getYAxisData(
        pointsData
    )

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp,
        content = { contentData ->
            with(contentData) {
                LineChart(
                    modifier = Modifier.heightIn(max = 300.dp),
                    lines = listOf(
                        lineBuilder {
                            addPoints(pointsData)
                            setLineStyle(
                                LineStyle(
                                    lineType = LineType.SmoothCurve(
                                        isDotted = true
                                    ),
                                    color = ChartsSampleColors.colorSky85,
                                )
                            )
                            setSelectionHighlightPoint(
                                SelectionHighlightPoint()
                            )
                            setSelectionHighlightPopUp(
                                SelectionHighlightPopUp()
                            )
                            setShadowUnderLine(
                                ShadowUnderLine(
                                    brush = Brush.verticalGradient(
                                        listOf(
                                            ChartsSampleColors.colorSky85,
                                            Color.Transparent
                                        )
                                    ),
                                    alpha = 0.3f,
                                )
                            )
                        }
                    ).toImmutableList(),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    xAxisStepSize = 30.dp,
                    yAxisStepSize = 5.dp,
                    horizontalScroll =
                        horizontalScroll,
                    zoom = zoom,
                    onPointSelect =
                        { point, offset -> },
                )
            }
        }
    )
}

@Suppress("LongMethod")
@Preview
@Composable
fun LineChartMultiColorSample() {
    val pointsData = getLineChartData(
        100,
        start = 50,
        maxRange = 100
    )

    val xAxisData = getXAxisData(
        pointsData,
    )
    val yAxisData = getYAxisData(
        pointsData
    )

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp,
        content = { contentData ->
            with(contentData) {
                LineChart(
                    modifier = Modifier.heightIn(max = 300.dp),
                    lines = listOf(
                        lineBuilder {
                            addPoints(pointsData)
                            setLineStyle(
                                LineStyle(
                                    lineType = LineType.Straight(),
                                    color = ChartsSampleColors.colorBtcOrange
                                )
                            )
                            setSelectionHighlightPoint(
                                SelectionHighlightPoint()
                            )
                            setSelectionHighlightPopUp(
                                SelectionHighlightPopUp()
                            )
                        },

                        lineBuilder {
                            addPoints(
                            pointsData.subList(
                                0,
                                10
                            )
                        )
                            setLineStyle(
                                LineStyle(
                                    lineType = LineType.Straight(),
                                    color = ChartsSampleColors.colorSky85
                                )
                            )
                            setSelectionHighlightPoint(
                                SelectionHighlightPoint()
                            )
                            setSelectionHighlightPopUp(
                                SelectionHighlightPopUp()
                            )
                        },

                        lineBuilder {
                            addPoints(
                            pointsData.subList(
                                50,
                                80
                            )
                        )
                            setLineStyle(
                                LineStyle(
                                    lineType = LineType.Straight(),
                                    color = ChartsSampleColors.colorYellow70
                                )
                            )
                            setSelectionHighlightPoint(
                                SelectionHighlightPoint()
                            )
                            setSelectionHighlightPopUp(
                                SelectionHighlightPopUp()
                            )
                        },

                        lineBuilder {
                            addPoints(
                            pointsData.subList(
                                90,
                                100
                            )
                        )
                            setLineStyle(
                                LineStyle(
                                    lineType = LineType.Straight(),
                                    color = ChartsSampleColors.colorBtcGreen
                                )
                            )
                            setSelectionHighlightPoint(
                                SelectionHighlightPoint()
                            )
                            setSelectionHighlightPopUp(
                                SelectionHighlightPopUp()
                            )
                        },
                    ).toImmutableList(),
                    xAxisData =
                        xAxisData,
                    yAxisData =
                        yAxisData,
                    xAxisStepSize =
                        30.dp,
                    yAxisStepSize =
                        5.dp,
                    horizontalScroll =
                        horizontalScroll,
                    zoom = zoom,
                    onPointSelect =
                        { point, offset -> },
                )
            }
        }
    )
}

@Preview
@Composable
fun LineChartMultipleLinesSample() {
    val colorPaletteList =
        listOf(
            ChartsSampleColors.colorBtcGreen,
            ChartsSampleColors.colorBtcOrange,
            ChartsSampleColors.colorTurquoise60,
            Color.DarkGray
        )
    val pointsData =
        getLineChartData(
            100,
            start = 50,
            maxRange = 100
        )

    val lines =
        mutableListOf<Line>()
    for (i in 0 until 4) {
        lines.add(
            lineBuilder {
                addPoints(
                getLineChartData(
                    100,
                    start = 50,
                    maxRange = 100
                )
            )
                .setLineStyle(
                    LineStyle(
                        color = colorPaletteList[i]
                    )
                )
                .setSelectionHighlightPoint(
                    SelectionHighlightPoint()
                )
                .setSelectionHighlightPopUp(
                    SelectionHighlightPopUp()
                )
            }
        )
    }

    val xAxisData = getXAxisData(
        pointsData,
    )
    val yAxisData =
        getYAxisData(
            pointsData
        )

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp,
        content = { contentData ->
            with(
                contentData
            ) {
                LineChart(
                    modifier = Modifier.heightIn(
                        max = 300.dp
                    ),
                    lines = lines.toImmutableList(),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    xAxisStepSize = 30.dp,
                    yAxisStepSize = 5.dp,
                    horizontalScroll = horizontalScroll,
                    zoom = zoom,
                    onPointSelect = { point, offset -> },
                )
            }
        }
    )
}

@Suppress("LongMethod")
@Preview
@Composable
fun LineChartMultiLinesAreaSample() {
    val colorPaletteList =
        listOf(
            ChartsSampleColors.colorRed30,
            ChartsSampleColors.colorGreen75,
            ChartsSampleColors.colorPurple75
        )

    val lines =
        mutableListOf<Line>()
    for (i in raisingLinesData.indices) {
        lines.add(
            lineBuilder {
                addPoints(
                    raisingLinesData[i]
                )
                setLineStyle(
                    LineStyle(
                        color = colorPaletteList[i]
                    )
                )
                setSelectionHighlightPoint(
                    SelectionHighlightPoint()
                )
                setSelectionHighlightPopUp(
                    SelectionHighlightPopUp()
                )
                setShadowUnderLine(
                    ShadowUnderLine(
                        color = when (i) {
                            0 -> colorPaletteList[0]
                            1 -> colorPaletteList[1]
                            else -> colorPaletteList[2]
                        },
                        alpha = 0.3f,
                    )

                )
            }
        )
    }

    val pointsData = lines.fold(mutableListOf<Offset>()) { acc, line ->
        acc.addAll(
            line.dataPoints.map { it.offset })
        acc
    }

    val xAxisData =
        getXAxisData(
            pointsData,
        )
    val yAxisData =
        getYAxisData(
            pointsData
        )

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp,
        content = { contentData ->
            with(
                contentData
            ) {
                LineChart(
                    modifier = Modifier.heightIn(
                        max = 300.dp
                    ),
                    lines = lines.toImmutableList(),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    xAxisStepSize = 30.dp,
                    yAxisStepSize = 5.dp,
                    horizontalScroll = horizontalScroll,
                    zoom = zoom,
                    onPointSelect = { point, offset -> }
                )
            }
        }
    )
}

@Preview
@Composable
fun LineChartMultiLinesAreaTransparentSample() {
    val colorPaletteList =
        listOf(
            ChartsSampleColors.colorBtcGreen,
            ChartsSampleColors.colorBtcOrange,
            ChartsSampleColors.colorSky85)

    val lines =
        mutableListOf<Line>()
    for (i in raisingLinesData.indices) {
        lines.add(
            lineBuilder {
                addPoints(
                    raisingLinesData[i]
                )
                    .setLineStyle(
                        LineStyle(
                            color = colorPaletteList[i]
                        )
                    )
                setSelectionHighlightPoint(
                    SelectionHighlightPoint()
                )
                setSelectionHighlightPopUp(
                    SelectionHighlightPopUp()
                )
                setShadowUnderLine(
                    ShadowUnderLine(
                        color = when (i) {
                            0 -> ChartsSampleColors.colorBtcGreen

                            1 -> ChartsSampleColors.colorBtcOrange

                            else -> Color.Transparent
                        },
                        alpha = 0.3f,
                    )

                )
            }
        )
    }

    val pointsData = lines.fold(mutableListOf<Offset>()) { acc, line ->
        acc.addAll(
            line.dataPoints.map { it.offset })
        acc
    }

    val xAxisData =
        getXAxisData(
            pointsData,
        )
    val yAxisData =
        getYAxisData(
            pointsData
        )

    ChartScaffold(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xUnitSize = 30.dp,
        yUnitSize = 5.dp,
        content = { contentData ->
            with(
                contentData
            ) {
                LineChart(
                    modifier = Modifier.heightIn(
                        max = 300.dp
                    ),
                    lines = lines.toImmutableList(),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    xAxisStepSize = 30.dp,
                    yAxisStepSize = 5.dp,
                    horizontalScroll = horizontalScroll,
                    zoom = zoom,
                    onPointSelect = { point, offset -> },
                )
            }
        }
    )
}

private fun getXAxisData(
    points: List<Offset>
) =
    axisBuilder {
        addNodes(
            listOf(
                points.minByOrNull { it.x }!!.x,
                points.maxByOrNull { it.x }!!.x
            )
        )
    }

private fun getYAxisData(
    points: List<Offset>
) =
    axisBuilder {
        addNodes(
            listOf(
                points.minByOrNull { it.y }!!.y,
                points.maxByOrNull { it.y }!!.y
            )
        )
    }
