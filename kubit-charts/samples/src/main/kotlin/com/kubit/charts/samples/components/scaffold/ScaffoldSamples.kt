package com.kubit.charts.samples.components.scaffold

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kubit.charts.components.axis.HorizontalAxisChart
import com.kubit.charts.components.axis.HorizontalAxisType
import com.kubit.charts.components.axis.VerticalAxisChart
import com.kubit.charts.components.axis.VerticalAxisType
import com.kubit.charts.components.axis.model.AxisPadding
import com.kubit.charts.components.axis.model.AxisStepStyle
import com.kubit.charts.components.axis.model.DecorativeHeightPosition
import com.kubit.charts.components.axis.model.axisBuilder
import com.kubit.charts.components.chart.linechart.LineChart
import com.kubit.charts.components.chart.linechart.model.IntersectionNode
import com.kubit.charts.components.chart.linechart.model.IntersectionNodeAccessibility
import com.kubit.charts.components.chart.linechart.model.IntersectionNodeFocus
import com.kubit.charts.components.chart.linechart.model.IntersectionPoint
import com.kubit.charts.components.chart.linechart.model.LineStyle
import com.kubit.charts.components.chart.linechart.model.LineType
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPoint
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPopUp
import com.kubit.charts.components.chart.linechart.model.ShadowUnderLine
import com.kubit.charts.components.chart.linechart.model.lineBuilder
import com.kubit.charts.components.scaffold.ChartScaffold
import com.kubit.charts.components.scaffold.ScrollableAccessibilityItem
import com.kubit.charts.samples.components.axis.sampleHorizontalAxisLight
import com.kubit.charts.samples.components.axis.sampleHorizontalAxisDark
import com.kubit.charts.samples.components.axis.sampleHorizontalBTC
import com.kubit.charts.samples.components.axis.sampleVerticalAxisLight
import com.kubit.charts.samples.components.axis.sampleVerticalAxisLong
import com.kubit.charts.samples.components.axis.sampleVerticalAxisLongDark
import com.kubit.charts.samples.components.axis.sampleVerticalBTC
import com.kubit.charts.samples.components.linechart.btcPoints
import com.kubit.charts.samples.components.linechart.sampleLineChartData1
import com.kubit.charts.samples.components.utils.ChartsSampleColors
import com.kubit.charts.samples.components.utils.getLineChartData
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Preview(heightDp = 400, widthDp = 600)
@Suppress("LongMethod", "MagicNumber")
@Composable
fun BTCSampleStandard() {
    val fixedUnitSize = 40.dp

    val gradient = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to ChartsSampleColors.colorBtcOrange.copy(alpha = 0.3f),
            1.0f to ChartsSampleColors.colorBtcOrange.copy(alpha = 0f)
        )
    )

    ChartScaffold(
        modifier = Modifier
            .background(Color.DarkGray)
            .clipToBounds(),
        horizontalAxis = { horizontalScroll, zoom, padding ->
            HorizontalAxisChart(
                data = sampleHorizontalBTC,
                type = HorizontalAxisType.Bottom,
                labelHeight = 50.dp,
                padding = padding,
                decorativeWidth = 40.dp,
                labelsBackgroundColor = Color.DarkGray,
                zoom = zoom
            )
        },
        verticalAxis = { verticalScroll, zoom, padding ->
            VerticalAxisChart(
                data = sampleVerticalBTC,
                labelWidth = 50.dp,
                padding = padding,
                decorativeHeight = 40.dp,
                decorativeHeightPosition = DecorativeHeightPosition.Top,
                labelsBackgroundColor = Color.DarkGray,
                zoom = zoom,
            )
        },
        axisPadding = AxisPadding(start = 50.dp, bottom = 50.dp),
        xUnitSize = fixedUnitSize,
        xAxisData = sampleHorizontalBTC,
        yAxisData = sampleVerticalBTC,
        isPinchZoomEnabled = true,
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
                modifier = Modifier
                    .fillMaxHeight(),
                lines = listOf(
                    lineBuilder {
                        addPoints(btcPoints)
                        setLineStyle(
                            LineStyle(
                                color = ChartsSampleColors.colorBtcOrange,
                                width = 6f
                            )
                        )
                        setShadowUnderLine(
                            ShadowUnderLine(
                                brush = gradient
                            )
                        )
                    }
                ).toImmutableList(),
                backgroundColor = Color.Transparent,
                xAxisData = sampleHorizontalBTC,
                yAxisData = sampleVerticalBTC,
                onPointSelect = { point, offset ->
                    // Handle point selection
                    Log.d("LineChart", "Point selected: (${point.x},${point.y}) at $offset")
                },
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 500)
@Suppress("LongMethod")
@Composable
fun AxisAndLineChartSampleStandard() {
    val fixedUnitSize = 40.dp

    ChartScaffold(
        modifier = Modifier
            .background(Color.DarkGray)
            .clipToBounds(),
        horizontalAxis = { horizontalScroll, zoom, padding ->
            HorizontalAxisChart(
                data = sampleHorizontalAxisLight,
                type = HorizontalAxisType.Bottom,
                labelHeight = 50.dp,
                padding = padding,
                decorativeWidth = 40.dp,
                fixedUnitSize = fixedUnitSize,
                horizontalScroll = horizontalScroll,
                zoom = zoom,
                labelsBackgroundColor = Color.DarkGray
            )
        },
        verticalAxis = { verticalScroll, zoom, padding ->
            VerticalAxisChart(
                data = sampleVerticalAxisLong,
                labelWidth = 50.dp,
                padding = padding,
                decorativeHeight = 40.dp,
                decorativeHeightPosition = DecorativeHeightPosition.Top,
                fixedUnitSize = fixedUnitSize,
                verticalScroll = verticalScroll,
                labelsBackgroundColor = Color.DarkGray,
                zoom = zoom,
            )
        },
        axisPadding = AxisPadding(start = 50.dp, bottom = 50.dp),
        xUnitSize = fixedUnitSize,
        xAxisData = sampleHorizontalAxisLight,
        yAxisData = sampleVerticalAxisLong,
        isPinchZoomEnabled = true,
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
                lines = listOf(lineBuilder {
                    addPoints(sampleLineChartData1) {
                        IntersectionPoint(color = Color.Cyan)
                    }
                    setSelectionHighlightPoint(
                        SelectionHighlightPoint()
                    )
                    setSelectionHighlightPopUp(
                        SelectionHighlightPopUp()
                    )
                    setLineStyle(
                        LineStyle(
                            color = Color.Cyan,
                            width = 6f
                        )
                    )
                }
                ).toImmutableList(),
                backgroundColor = Color.Transparent,
                xAxisStepSize = fixedUnitSize,
                xAxisData = sampleHorizontalAxisLight,
                yAxisData = sampleVerticalAxisLight,
                horizontalScroll = horizontalScroll,
                verticalScroll = verticalScroll,
                zoom = zoom,
                onPointSelect = { point, offset ->
                    // Handle point selection
                    Log.d("LineChart", "Point selected: (${point.x},${point.y}) at $offset")
                },
                onHorizontalScrollChangeRequest = this.onHorizontalScrollChangeRequest,
                onVerticalScrollChangeRequest = this.onVerticalScrollChangeRequest
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 500)
@Suppress("LongMethod")
@Composable
fun AxisAndLineChartSampleVerticalEnd() {
    val fixedUnitSize = 40.dp

    ChartScaffold(
        modifier = Modifier
            .background(Color.DarkGray)
            .clipToBounds(),
        horizontalAxis = { horizontalScroll, zoom, padding ->
            HorizontalAxisChart(
                data = sampleHorizontalAxisLight,
                type = HorizontalAxisType.Bottom,
                labelHeight = 50.dp,
                padding = padding,
                decorativeWidth = 40.dp,
                fixedUnitSize = fixedUnitSize,
                horizontalScroll = horizontalScroll,
                labelsBackgroundColor = Color.DarkGray,
                zoom = zoom,
            )
        },
        verticalAxis = { verticalScroll, zoom, padding ->
            VerticalAxisChart(
                data = sampleVerticalAxisLong,
                labelWidth = 50.dp,
                padding = padding,
                decorativeHeight = 40.dp,
                decorativeHeightPosition = DecorativeHeightPosition.Top,
                fixedUnitSize = fixedUnitSize,
                verticalScroll = verticalScroll,
                labelsBackgroundColor = Color.DarkGray,
                zoom = zoom,
                type = VerticalAxisType.End
            )
        },
        axisPadding = AxisPadding(end = 50.dp, bottom = 50.dp),
        xUnitSize = fixedUnitSize,
        xAxisData = sampleHorizontalAxisLight,
        yAxisData = sampleVerticalAxisLong,
        isPinchZoomEnabled = true,
        accessibility = ScrollableAccessibilityItem(
            contentDescription = "Line chart with points",
            leftCustomAction = "Scroll left",
            rightCustomAction = "Scroll right",
            downCustomAction = "Scroll down",
            upCustomAction = "Scroll up",
            zoomInCustomAction = "Zoom in",
            zoomOutCustomAction = "Zoom out"
        ),
        content = { contentData ->
            with(contentData) {
                LineChart(
                    modifier = Modifier.fillMaxHeight(),
                    lines = listOf(
                        lineBuilder {
                            addPoints(sampleLineChartData1) { IntersectionPoint(color = ChartsSampleColors.colorBtcOrange) }
                            setSelectionHighlightPoint(
                                SelectionHighlightPoint()
                            )
                            setSelectionHighlightPopUp(
                                SelectionHighlightPopUp()
                            )
                            setLineStyle(
                                LineStyle(
                                    color = ChartsSampleColors.colorBtcOrange,
                                    width = 6f
                                )
                            )
                        }
                    ).toImmutableList(),
                    backgroundColor = Color.Transparent,
                    xAxisStepSize = fixedUnitSize,
                    xAxisData = sampleHorizontalAxisLight,
                    yAxisData = sampleVerticalAxisLight,
                    horizontalScroll = horizontalScroll,
                    verticalScroll = verticalScroll,
                    zoom = zoom,
                    onPointSelect = { point, offset ->
                        // Handle point selection
                        Log.d("LineChart", "Point selected: (${point.x},${point.y}) at $offset")
                    },
                )
            }
        }
    )
}

@Preview(showBackground = true, heightDp = 500)
@Suppress("LongMethod")
@Composable
fun AxisAndLineChartSampleHorizontalTop() {
    val fixedUnitSize = 40.dp

    ChartScaffold(
        modifier = Modifier
            .background(Color.White)
            .clipToBounds(),
        horizontalAxis = { horizontalScroll, zoom, padding ->
            HorizontalAxisChart(
                data = sampleHorizontalAxisDark,
                type = HorizontalAxisType.Top,
                labelHeight = 50.dp,
                padding = padding,
                decorativeWidth = 40.dp,
                fixedUnitSize = fixedUnitSize,
                horizontalScroll = horizontalScroll,
                labelsBackgroundColor = Color.White,
                zoom = zoom,
            )
        },
        verticalAxis = { verticalScroll, zoom, padding ->
            VerticalAxisChart(
                data = sampleVerticalAxisLongDark,
                labelWidth = 50.dp,
                padding = padding,
                decorativeHeight = 40.dp,
                decorativeHeightPosition = DecorativeHeightPosition.Bottom,
                fixedUnitSize = fixedUnitSize,
                verticalScroll = verticalScroll,
                labelsBackgroundColor = Color.White,
                zoom = zoom,
            )
        },
        axisPadding = AxisPadding(start = 50.dp, top = 50.dp),
        xUnitSize = fixedUnitSize,
        xAxisData = sampleHorizontalAxisLight,
        yAxisData = sampleVerticalAxisLong,
        isPinchZoomEnabled = true,
        accessibility = ScrollableAccessibilityItem(
            contentDescription = "Line chart with points",
            leftCustomAction = "Scroll left",
            rightCustomAction = "Scroll right",
            downCustomAction = "Scroll down",
            upCustomAction = "Scroll up",
            zoomInCustomAction = "Zoom in",
            zoomOutCustomAction = "Zoom out"
        ),
        content = { contentData ->
            with(contentData) {
                LineChart(
                    modifier = Modifier.fillMaxHeight(),
                    lines = listOf(
                        lineBuilder {
                            addPoints(sampleLineChartData1) { IntersectionPoint() }
                            setSelectionHighlightPoint(
                                SelectionHighlightPoint()
                            )
                            setSelectionHighlightPopUp(
                                SelectionHighlightPopUp()
                            )
                            setLineStyle(
                                LineStyle(
                                    color = ChartsSampleColors.colorTurquoise60,
                                    width = 6f
                                )
                            )
                        }
                    ).toImmutableList(),
                    backgroundColor = Color.Transparent,
                    xAxisStepSize = fixedUnitSize,
                    xAxisData = sampleHorizontalAxisLight,
                    yAxisData = sampleVerticalAxisLight,
                    horizontalScroll = horizontalScroll,
                    verticalScroll = verticalScroll,
                    zoom = zoom,
                    onPointSelect = { point, offset ->
                        // Handle point selection
                        Log.d("LineChart", "Point selected: (${point.x},${point.y}) at $offset")
                    },
                )
            }
        }
    )
}

@Suppress("MagicNumber", "LongMethod")
@Preview(heightDp = 260)
@Composable
fun LineChartMultiLineMultipleNodesSample() {
    val pointsData = getLineChartData(
        25,
        start = 0,
        maxRange = 50
    )

    // First line is split in two because the first part is solid and the second part is dotted
    // Turquoise line
    val firstLinePointDataPart1 = persistentListOf(
        Offset(1f, 1f),
        Offset(3f, 2f),
        Offset(5f, 30f),
        Offset(7f, 7f),
        Offset(9f, 28f),
        Offset(11f, 28f),
    )

    val firstLinePointDataPart2 = persistentListOf(
        Offset(11f, 28f),
        Offset(13f, 120f),
        Offset(15f, 140f)
    )

    // Black line
    val secondLinePointData = persistentListOf(
        Offset(1f, 20f),
        Offset(3f, 40f),
        Offset(5f, 50f),
        Offset(7f, 30f),
        Offset(9f, 70f),
        Offset(11f, 50f),
        Offset(13f, 90f),
        Offset(15f, 110f)
    )

    val labelsByIndex = listOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Jun",
        "Jul",
        "Aug",
    )

    AxisStepStyle

    // X Axis config
    val xAxisData = axisBuilder {
        setDefaultStepStyle(null)
        addNodes(
            from = 0f,
            to = 16f,
            steps = 16,
            labels = { step, value -> if ((step + 1) % 2 == 0) labelsByIndex[step / 2] else "" },
            labelStyle = { step, value ->
                TextStyle(
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray.copy(alpha = 0.5f)
                )
            },
            stepStyle = { step, value ->
                if (step % 2 == 0) {
                    AxisStepStyle.solid(
                        strokeWidth = 2.dp,
                        strokeColor = Color.LightGray.copy(alpha = 0.3f)
                    )
                } else {
                    null
                }
            }
        )
    }

    // Y Axis config
    val yAxisData = axisBuilder {
        addNodes(from = -5f, to = 160f, 1)
    }

    val textMeasurer = rememberTextMeasurer(cacheSize = 16)

    ChartScaffold(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .clipToBounds(),
        horizontalAxis = { padding ->
            HorizontalAxisChart(
                data = xAxisData,
                type = HorizontalAxisType.Bottom,
                labelHeight = 20.dp,
                padding = padding,
            )
        },
        axisPadding = AxisPadding(bottom = 20.dp),
        xAxisData = sampleHorizontalBTC,
        yAxisData = sampleVerticalBTC,
    ) {
        LineChart(
            backgroundColor = Color.Transparent,
            lines = listOf(
                lineBuilder {
                    addPoints(firstLinePointDataPart2) { index ->
                        if (index == 0) {
                            null
                        } else if (index < 2) {
                            IntersectionPointWithLabel(
                                label = firstLinePointDataPart2[index].y.toInt().toString(),
                                accessibility = IntersectionNodeAccessibility(
                                    contentDescription = "Point ${pointsData[index].x} ${pointsData[index].y}"
                                ),
                                color = ChartsSampleColors.colorTurquoise70,
                                radius = 3.dp,
                                textMeasurer = textMeasurer,
                                textStyle = TextStyle(
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        } else {
                            IntersectionPointWithLabelCentered(
                                label = firstLinePointDataPart2[index].y.toInt().toString(),
                                accessibility = IntersectionNodeAccessibility(
                                    contentDescription = "Point ${pointsData[index].x} ${pointsData[index].y}"
                                ),
                                color = ChartsSampleColors.colorTurquoise70,
                                radius = 12.dp,
                                textMeasurer = textMeasurer,
                                textStyle = TextStyle(
                                    fontSize = 10.sp,
                                    color = ChartsSampleColors.colorTurquoise70.copy(alpha = 0.5f),
                                ),
                                style = Stroke(
                                    width = 4f
                                )
                            )
                        }
                    }
                    setLineStyle(
                        LineStyle(
                            lineType = LineType.SmoothCurve(
                                dashed = true,
                                intervals = floatArrayOf(10f, 5f)
                            ),
                            color = ChartsSampleColors.colorTurquoise70,
                            width = 5f
                        )
                    )
                    setSelectionHighlightPoint(SelectionHighlightPoint())
                    setSelectionHighlightPopUp(
                        SelectionHighlightPopUp()
                    )
                },
                lineBuilder {
                    addPoints(firstLinePointDataPart1) { index ->
                        if (index != 5 && index != 7) {
                            IntersectionPointWithLabel(
                                label = firstLinePointDataPart1[index].y.toInt().toString(),
                                accessibility = IntersectionNodeAccessibility(
                                    contentDescription = "Point ${pointsData[index].x} ${pointsData[index].y}"
                                ),
                                color = ChartsSampleColors.colorTurquoise70,
                                radius = 3.dp,
                                textMeasurer = textMeasurer,
                                textStyle = TextStyle(
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        } else {
                            IntersectionPointWithLabelCentered(
                                label = firstLinePointDataPart1[index].y.toInt().toString(),
                                accessibility = IntersectionNodeAccessibility(
                                    contentDescription = "Point ${pointsData[index].x} ${pointsData[index].y}"
                                ),
                                color = ChartsSampleColors.colorTurquoise70,
                                radius = 12.dp,
                                textMeasurer = textMeasurer,
                                textStyle = TextStyle(
                                    fontSize = 10.sp,
                                    color = ChartsSampleColors.colorTurquoise70.copy(alpha = 0.5f),
                                ),
                                style = Stroke(
                                    width = 4f
                                )
                            )
                        }
                    }
                    setLineStyle(
                        LineStyle(
                            lineType = LineType.SmoothCurve(dashed = false),
                            color = ChartsSampleColors.colorTurquoise70,
                            width = 5f
                        )
                    )
                    setSelectionHighlightPoint(SelectionHighlightPoint())
                    setSelectionHighlightPopUp(
                        SelectionHighlightPopUp()
                    )
                },
                lineBuilder {
                    addPoints(secondLinePointData) { index ->
                        IntersectionPointWithLabel(
                            label = secondLinePointData[index].y.toInt().toString(),
                            accessibility = IntersectionNodeAccessibility(
                                contentDescription = "Point ${pointsData[index].x} ${pointsData[index].y}"
                            ),
                            color = ChartsSampleColors.colorSky85,
                            radius = 3.dp,
                            textMeasurer = textMeasurer,
                            textStyle = TextStyle(
                                fontSize = 8.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                    }
                    setLineStyle(
                        LineStyle(
                            lineType = LineType.SmoothCurve(dashed = false),
                            color = ChartsSampleColors.colorSky85,
                            width = 5f
                        )
                    )
                }

            ).toImmutableList(),
            xAxisData = xAxisData,
            yAxisData = yAxisData
        )
    }
}

/**
 * Represents a circular intersection node with a label above it.
 *
 * This is just an example about how to create custom IntersectionNodes.
 */
class IntersectionPointWithLabel(
    val label: String,
    val textMeasurer: TextMeasurer,
    val textStyle: TextStyle,
    val radius: Dp = 6.dp,
    val alpha: Float = 1.0f,
    val style: DrawStyle = Fill,
    color: Color = Color.Black,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    accessibility: IntersectionNodeAccessibility? = null,
    focus: IntersectionNodeFocus = IntersectionNodeFocus(),
) : IntersectionNode(color, colorFilter, blendMode, accessibility, focus, { center ->

    val measuredResult = textMeasurer.measure(text = label, style = textStyle)

    drawText(
        measuredResult,
        topLeft = Offset(
            center.x - measuredResult.size.width / 2,
            center.y - radius.toPx() - measuredResult.size.height - 4.dp.toPx()
        )
    )

    drawCircle(
        color = color,
        radius = radius.toPx(),
        center = center,
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode,
    )
})

/**
 * Represents a circular intersection node with a label centered within it.
 *
 * This is just an example about how to create custom IntersectionNodes.
 */
class IntersectionPointWithLabelCentered(
    val label: String,
    val textMeasurer: TextMeasurer,
    val textStyle: TextStyle,
    val radius: Dp = 6.dp,
    val alpha: Float = 1.0f,
    val style: DrawStyle = Fill,
    color: Color = Color.Black,
    colorFilter: ColorFilter? = null,
    backgroundColor: Color = Color.White,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    accessibility: IntersectionNodeAccessibility? = null,
    focus: IntersectionNodeFocus = IntersectionNodeFocus(),
) : IntersectionNode(color, colorFilter, blendMode, accessibility, focus, { center ->

    val measuredResult = textMeasurer.measure(text = label, style = textStyle)

    drawCircle(
        color = backgroundColor,
        radius = radius.toPx(),
        center = center,
        alpha = alpha,
        style = Fill,
        colorFilter = colorFilter,
        blendMode = blendMode,
    )

    drawCircle(
        color = color,
        radius = radius.toPx(),
        center = center,
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode,
    )

    drawText(
        measuredResult,
        topLeft = Offset(
            center.x - measuredResult.size.width / 2,
            center.y - measuredResult.size.height / 2
        )
    )
})
