package com.kubit.charts.samples.components.scaffold

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.axis.HorizontalAxisChart
import com.kubit.charts.components.axis.HorizontalAxisType
import com.kubit.charts.components.axis.VerticalAxisChart
import com.kubit.charts.components.axis.VerticalAxisType
import com.kubit.charts.components.axis.model.AxisPadding
import com.kubit.charts.components.axis.model.DecorativeHeightPosition
import com.kubit.charts.components.chart.linechart.LineChart
import com.kubit.charts.components.chart.linechart.model.IntersectionPoint
import com.kubit.charts.components.chart.linechart.model.LineStyle
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
import kotlinx.collections.immutable.toImmutableList

@Preview(heightDp = 400, widthDp = 600)
@Suppress("LongMethod", "MagicNumber")
@Composable
fun BTCSampleStandard() {
    val fixedUnitSize = 40.dp

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
                                color = ChartsSampleColors.colorBtcOrange
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
                    setLineStyle(LineStyle(
                        color = Color.Cyan,
                        width = 6f
                    ))
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
