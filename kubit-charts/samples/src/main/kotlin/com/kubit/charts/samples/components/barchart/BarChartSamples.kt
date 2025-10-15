package com.kubit.charts.samples.components.barchart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kubit.charts.components.axis.HorizontalAxisChart
import com.kubit.charts.components.axis.HorizontalAxisType
import com.kubit.charts.components.axis.VerticalAxisChart
import com.kubit.charts.components.axis.VerticalAxisType
import com.kubit.charts.components.axis.model.AxisBuilder
import com.kubit.charts.components.axis.model.AxisPadding
import com.kubit.charts.components.chart.barchart.BarChart
import com.kubit.charts.components.chart.barchart.model.BarChartAlignment
import com.kubit.charts.components.chart.barchart.model.BarChartAppearance
import com.kubit.charts.components.chart.barchart.model.BarChartData
import com.kubit.charts.components.chart.barchart.model.BarChartLabelPosition
import com.kubit.charts.components.chart.barchart.model.BarChartOrientation
import com.kubit.charts.components.chart.barchart.model.BarChartSegmentData
import com.kubit.charts.components.chart.barchart.model.BarChartType
import com.kubit.charts.components.chart.linechart.LineChart
import com.kubit.charts.components.chart.linechart.model.IntersectionPoint
import com.kubit.charts.components.chart.linechart.model.LineBuilder
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPoint
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPopUp
import com.kubit.charts.components.scaffold.ChartScaffold
import com.kubit.charts.samples.components.utils.ChartsSampleColors
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.annotations.ApiStatus.Experimental

@Preview(heightDp = 25, showBackground = true)
@Composable
fun SingleHorizontalBarChartBottomLabel() {
    BarChart(
        data = BarChartData(
            type = BarChartType.Single,
            orientation = BarChartOrientation.Horizontal,
            appearance = BarChartAppearance.Squared,
            segments = listOf(
                BarChartSegmentData(
                    minValue = 0.0,
                    maxValue = 3.0,
                    label = "BarChart",
                    labelColor = Color.Black,
                    color = ChartsSampleColors.colorPurple75,
                    contentDescription = "Test Bar Red",
                    labelPosition = BarChartLabelPosition.BottomCenter,
                    labelSpacing = 4.dp,
                    labelSize = 8.sp
                ),
            ),
            barChartAlignment = BarChartAlignment.Start
        ),
    )
}

@Preview(heightDp = 25, showBackground = true)
@Composable
fun SingleHorizontalBarChartTopLabel() {
    BarChart(
        data = BarChartData(
            type = BarChartType.Single,
            orientation = BarChartOrientation.Horizontal,
            appearance = BarChartAppearance.Squared,
            segments = listOf(
                BarChartSegmentData(
                    minValue = 0.0,
                    maxValue = 3.0,
                    label = "BarChart",
                    color = ChartsSampleColors.colorPurple75,
                    labelColor = Color.Black,
                    contentDescription = "Test Bar Red",
                    labelPosition = BarChartLabelPosition.TopCenter,
                    labelSpacing = 4.dp,
                    labelSize = 8.sp
                ),
            ),
            barChartAlignment = BarChartAlignment.Start
        ),
    )
}

@Preview(widthDp = 60, heightDp = 100, showBackground = true)
@Composable
fun SingleVerticalBarChartBottomLabel() {
    BarChart(
        data = BarChartData(
            type = BarChartType.Single,
            orientation = BarChartOrientation.Vertical,
            appearance = BarChartAppearance.Squared,
            segments = listOf(
                BarChartSegmentData(
                    minValue = 0.0,
                    maxValue = 5.0,
                    label = "BarChart",
                    color = ChartsSampleColors.colorPurple75,
                    contentDescription = "Test Bar Red",
                    labelPosition = BarChartLabelPosition.CenterStartOutside
                ),
            ),
            barChartAlignment = BarChartAlignment.Start
        ),
    )
}

@Preview(widthDp = 25, heightDp = 100, showBackground = true)
@Composable
fun SingleVerticalBarChartTopLabel() {
    BarChart(
        data = BarChartData(
            type = BarChartType.Single,
            orientation = BarChartOrientation.Vertical,
            appearance = BarChartAppearance.Squared,
            segments = listOf(
                BarChartSegmentData(
                    minValue = 0.0,
                    maxValue = 5.0,
                    label = "Test",
                    color = ChartsSampleColors.colorPurple75,
                    contentDescription = "Test Bar Red",
                    labelPosition = BarChartLabelPosition.TopCenter
                ),
            ),
            barChartAlignment = BarChartAlignment.Start
        ),
        modifier = Modifier.width(25.dp)
    )
}

@Preview(heightDp = 45, showBackground = true)
@Composable
fun SingleStackedHorizontalBarChart() {
    BarChart(
        data = BarChartData(
            type = BarChartType.Stacked,
            orientation = BarChartOrientation.Horizontal,
            appearance = BarChartAppearance.Squared,
            segments = listOf(
                BarChartSegmentData(
                    minValue = -1.0,
                    maxValue = 0.0,
                    label = "BarChart",
                    color = ChartsSampleColors.colorPurple75,
                    contentDescription = "Test Bar Red",
                    labelPosition = BarChartLabelPosition.CenterStartOutside,
                    labelSize = 10.sp,
                    labelSpacing = 2.dp
                ),
                BarChartSegmentData(
                    minValue = 0.0,
                    maxValue = 1.0,
                    label = "BarChart",
                    color = ChartsSampleColors.colorBlue50,
                    contentDescription = "Test Bar Blue",
                    labelPosition = BarChartLabelPosition.Center,
                    labelSize = 10.sp,
                    labelSpacing = 2.dp
                ),
                BarChartSegmentData(
                    minValue = 1.0,
                    maxValue = 2.0,
                    label = "BarChart",
                    color = ChartsSampleColors.colorGreen75,
                    contentDescription = "Test Bar Green",
                    labelPosition = BarChartLabelPosition.CenterEndOutside,
                    labelSize = 10.sp,
                    labelSpacing = 2.dp
                ),
            ),
        ),
        modifier = Modifier.height(30.dp)
    )
}

@OptIn(Experimental::class)
@Preview(heightDp = 100, widthDp = 85, showBackground = true)
@Composable
fun SingleStackedVerticalBarChart() {
    BarChart(
        data = BarChartData(
            type = BarChartType.Stacked,
            orientation = BarChartOrientation.Vertical,
            appearance = BarChartAppearance.Squared,
            segments = listOf(
                BarChartSegmentData(
                    minValue = -1.0,
                    maxValue = 0.0,
                    label = "Test",
                    color = ChartsSampleColors.colorPurple75,
                    contentDescription = "Test Bar Red",
                    labelPosition = BarChartLabelPosition.CenterStartOutside
                ),
                BarChartSegmentData(
                    minValue = 0.0,
                    maxValue = 1.0,
                    label = "Test",
                    color = ChartsSampleColors.colorBlue50,
                    contentDescription = "Test Bar Blue",
                    labelPosition = BarChartLabelPosition.Center
                ),
                BarChartSegmentData(
                    minValue = 1.0,
                    maxValue = 2.0,
                    label = "Test",
                    color = ChartsSampleColors.colorGreen75,
                    contentDescription = "Test Bar Green",
                    labelPosition = BarChartLabelPosition.CenterEndOutside
                ),
            ),
        ),
        modifier = Modifier.width(100.dp)
    )
}

@OptIn(Experimental::class)
@Preview(heightDp = 100, showBackground = true)
@Composable
fun GroupedHorizontalBarChart() {
    BarChart(
        data = BarChartData(
            type = BarChartType.Grouped,
            orientation = BarChartOrientation.Horizontal,
            appearance = BarChartAppearance.Squared,
            segments = getGroupedBarListHorizontal(),
            barThickness = BarThickness,
            barChartAlignment = BarChartAlignment.Center
        ),
    )
}

@OptIn(Experimental::class)
@Preview(heightDp = 200, widthDp = 120, showBackground = true)
@Composable
fun GroupedVerticalBarChart() {
    BarChart(
        data = BarChartData(
            type = BarChartType.Grouped,
            orientation = BarChartOrientation.Vertical,
            appearance = BarChartAppearance.Squared,
            segments = getGroupedBarListVertical(),
            barThickness = BarThickness,
            barChartAlignment = BarChartAlignment.End
        ),
        modifier = Modifier.width(120.dp)
    )
}

@Preview(heightDp = 400)
@Composable
@Suppress("LongMethod", "MagicNumber")
fun BarChartShapeVariants() {
    ChartScaffold(
        modifier = Modifier.background(Color.White),
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
                BarChart(
                    data = persistentListOf(
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 5f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Rounded,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 4f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Mixed,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 3f,
                        ),
                        BarChartData(
                            type = BarChartType.Stacked,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = -1.0,
                                    maxValue = 0.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorTurquoise60,
                                    contentDescription = "Test Bar Blue"
                                ),
                                BarChartSegmentData(
                                    minValue = 1.0,
                                    maxValue = 2.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorYellow70,
                                    contentDescription = "Test Bar Green"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 2f,
                        ),
                        BarChartData(
                            type = BarChartType.Stacked,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Rounded,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = -1.0,
                                    maxValue = 0.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    color = Color.Blue,
                                    contentDescription = "Test Bar Blue"
                                ),
                                BarChartSegmentData(
                                    minValue = 1.0,
                                    maxValue = 2.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorYellow70,
                                    contentDescription = "Test Bar Green"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 1f,
                        ),
                        BarChartData(
                            type = BarChartType.Stacked,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Mixed,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = -1.0,
                                    maxValue = 0.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    color = Color.Blue,
                                    contentDescription = "Test Bar Blue"
                                ),
                                BarChartSegmentData(
                                    minValue = 1.0,
                                    maxValue = 2.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorYellow70,
                                    contentDescription = "Test Bar Green"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 0f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 0 - 1.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 3f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Rounded,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = -1.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 4f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Mixed,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = -1.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 5f,
                        ),
                        BarChartData(
                            type = BarChartType.Stacked,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 2.0,
                                    maxValue = 3.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                                BarChartSegmentData(
                                    minValue = 3.0,
                                    maxValue = 4.0,
                                    label = "Test",
                                    color = Color.Blue,
                                    contentDescription = "Test Bar Blue"
                                ),
                                BarChartSegmentData(
                                    minValue = 4.0,
                                    maxValue = 5.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorTurquoise60,
                                    contentDescription = "Test Bar Green"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 3f,
                        ),
                        BarChartData(
                            type = BarChartType.Stacked,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Rounded,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 2.0,
                                    maxValue = 3.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                                BarChartSegmentData(
                                    minValue = 3.0,
                                    maxValue = 4.0,
                                    label = "Test",
                                    color = Color.Blue,
                                    contentDescription = "Test Bar Blue"
                                ),
                                BarChartSegmentData(
                                    minValue = 4.0,
                                    maxValue = 5.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorTurquoise60,
                                    contentDescription = "Test Bar Green"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 4f,
                        ),
                        BarChartData(
                            type = BarChartType.Stacked,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Mixed,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 2.0,
                                    maxValue = 3.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                                BarChartSegmentData(
                                    minValue = 3.0,
                                    maxValue = 4.0,
                                    label = "Test",
                                    color = Color.Blue,
                                    contentDescription = "Test Bar Blue"
                                ),
                                BarChartSegmentData(
                                    minValue = 4.0,
                                    maxValue = 5.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorGreen75,
                                    contentDescription = "Test Bar Green"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 5f,
                        ),
                    ),
                    xAxisData = Steps,
                    yAxisData = Steps,
                    xAxisStepSize = FixedUnitSize,
                    yAxisStepSize = FixedUnitSize,
                    horizontalScroll = horizontalScroll,
                    verticalScroll = verticalScroll,
                    zoom = zoom,
                )
            }
        }
    )
}

@Preview(heightDp = 400)
@Composable
@Suppress("LongMethod", "MagicNumber")
fun BarChartTextVariants() {
    ChartScaffold(
        modifier = Modifier.background(Color.White),
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
                BarChart(
                    data = persistentListOf(
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    labelPosition = BarChartLabelPosition.Center,
                                    color = ChartsSampleColors.colorYellow70,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 5f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    labelPosition = BarChartLabelPosition.BottomStart,
                                    color = ChartsSampleColors.colorTurquoise60,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 4f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    labelPosition = BarChartLabelPosition.TopEnd,
                                    color = ChartsSampleColors.colorTurquoise60,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 2f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    labelPosition = BarChartLabelPosition.BottomEnd,
                                    color = ChartsSampleColors.colorPurple75,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 1f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 5.0,
                                    label = "Test",
                                    labelPosition = BarChartLabelPosition.Center,
                                    color = ChartsSampleColors.colorGreen75,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 2f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 5.0,
                                    label = "Test",
                                    labelPosition = BarChartLabelPosition.BottomCenter,
                                    color = ChartsSampleColors.colorRed65,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = 20.dp,
                            stepPosition = 3f,
                        ),
                    ),
                    xAxisData = Steps,
                    yAxisData = Steps,
                    xAxisStepSize = FixedUnitSize,
                    yAxisStepSize = FixedUnitSize,
                    horizontalScroll = horizontalScroll,
                    verticalScroll = verticalScroll,
                    zoom = zoom,
                )
            }
        }
    )
}

@Preview(heightDp = 450)
@Composable
@Suppress("LongMethod", "MagicNumber")
fun BarChartWithAxis() {
    ChartScaffold(
        modifier = Modifier.background(Color.White),
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
                BarChart(
                    data = persistentListOf(
                        BarChartData(
                            type = BarChartType.Grouped,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = getBarList(),
                            barThickness = BarThickness,
                            stepPosition = 5f,
                            barChartAlignment = BarChartAlignment.End,
                        ),
                        BarChartData(
                            type = BarChartType.Stacked,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            barThickness = BarThickness * 4,
                            segments = getStackedBarData(),
                            barChartAlignment = BarChartAlignment.Center
                        ),
                        BarChartData(
                            type = BarChartType.Grouped,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Squared,
                            barThickness = BarThickness,
                            segments = getBarList(),
                            stepPosition = 5f,
                            barChartAlignment = BarChartAlignment.Start
                        ),
                        BarChartData(
                            type = BarChartType.Stacked,
                            orientation = BarChartOrientation.Horizontal,
                            appearance = BarChartAppearance.Squared,
                            barThickness = BarThickness,
                            segments = getStackedBarData(),
                            stepPosition = -0.5f,
                            barChartAlignment = BarChartAlignment.Start
                        ),
                    ),
                    xAxisData = Steps,
                    yAxisData = Steps,
                    xAxisStepSize = FixedUnitSize,
                    yAxisStepSize = FixedUnitSize,
                    horizontalScroll = horizontalScroll,
                    verticalScroll = verticalScroll,
                    zoom = zoom,
                )
            }
        }
    )
}

@Preview(heightDp = 450)
@Composable
@Suppress("LongMethod", "MagicNumber")
fun BarChartWithLineChartInAxis() {
    ChartScaffold(
        modifier = Modifier.background(Color.White),
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
                BarChart(
                    data = persistentListOf(
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = -1.0,
                                    maxValue = 1.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorRed50,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = BarThickness,
                            stepPosition = 0f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = -1.0,
                                    maxValue = 3.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorRed50,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = BarThickness,
                            stepPosition = 2f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = -1.0,
                                    maxValue = 4.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorRed50,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = BarThickness,
                            stepPosition = 4f,
                        ),
                        BarChartData(
                            type = BarChartType.Single,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = listOf(
                                BarChartSegmentData(
                                    minValue = -1.0,
                                    maxValue = 8.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorRed50,
                                    contentDescription = "Test Bar Red"
                                ),
                            ),
                            barThickness = BarThickness,
                            stepPosition = 8f,
                        ),
                    ),
                    xAxisData = Steps,
                    yAxisData = Steps,
                    xAxisStepSize = FixedUnitSize,
                    yAxisStepSize = FixedUnitSize,
                    horizontalScroll = horizontalScroll,
                    verticalScroll = verticalScroll,
                    zoom = zoom,
                )
                LineChart(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.Transparent),
                    lines = listOf(
                        LineBuilder().addPoints(
                            listOf(
                                Offset(-1f, -1f),
                                Offset(0f, 1f),
                                Offset(2f, 3f),
                                Offset(4f, 4f),
                                Offset(8f, 8f),
                            )
                        ) { IntersectionPoint() }
                            .setSelectionHighlightPoint(
                                SelectionHighlightPoint()
                            )
                            .setSelectionHighlightPopUp(
                                SelectionHighlightPopUp()
                            )
                            .build()
                    ).toImmutableList(),
                    backgroundColor = Color.Transparent,
                    xAxisStepSize = FixedUnitSize,
                    xAxisData = Steps,
                    yAxisData = Steps,
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

// Bar data
@Composable
private fun getStackedBarData() = listOf(
    BarChartSegmentData(
        minValue = 1.0,
        maxValue = 2.0,
        label = "Test",
        color = ChartsSampleColors.colorRed50,
        contentDescription = "Test Bar Segment Red",
        labelPosition = BarChartLabelPosition.BottomCenter,
        labelRotation = 90f
    ),
    BarChartSegmentData(
        minValue = 2.0,
        maxValue = 3.0,
        label = "Test 2",
        color = Color.Blue,
        contentDescription = "Test Bar Segment Blue",
        labelPosition = BarChartLabelPosition.Center
    ),
    BarChartSegmentData(
        minValue = 3.0,
        maxValue = 3.7,
        label = "Test 3",
        color = ChartsSampleColors.colorGreen75,
        contentDescription = "Test Bar Segment Green",
        labelPosition = BarChartLabelPosition.TopCenter,
        labelRotation = 90f
    ),
)

@Composable
private fun getBarList() = listOf(
    BarChartSegmentData(
        minValue = 0.0,
        maxValue = 1.0,
        label = "Test",
        color = ChartsSampleColors.colorPurple75,
        contentDescription = "Test Bar Segment Magenta",
        labelPosition = BarChartLabelPosition.TopEnd
    ),
    BarChartSegmentData(
        minValue = 1.0,
        maxValue = 4.0,
        label = "Test",
        color = ChartsSampleColors.colorYellow80,
        contentDescription = "Test Bar Segment Yellow",
        labelPosition = BarChartLabelPosition.TopStart
    ),
    BarChartSegmentData(
        minValue = 2.0,
        maxValue = 5.0,
        label = "Test",
        color = ChartsSampleColors.colorGreen75,
        contentDescription = "Test Bar Segment Yellow",
        labelPosition = BarChartLabelPosition.TopEnd
    ),
)

@Composable
private fun getGroupedBarListHorizontal() = listOf(
    BarChartSegmentData(
        minValue = 0.0,
        maxValue = 3.0,
        label = "BarChart",
        color = ChartsSampleColors.colorPurple75,
        contentDescription = "Test Bar Segment Magenta",
        labelPosition = BarChartLabelPosition.CenterEndOutside
    ),
    BarChartSegmentData(
        minValue = 0.0,
        maxValue = 4.0,
        label = "BarChart",
        color = ChartsSampleColors.colorYellow80,
        contentDescription = "Test Bar Segment Yellow",
        labelPosition = BarChartLabelPosition.CenterEndOutside
    ),
    BarChartSegmentData(
        minValue = 0.0,
        maxValue = 5.0,
        label = "BarChart",
        color = ChartsSampleColors.colorGreen75,
        contentDescription = "Test Bar Segment Yellow",
        labelPosition = BarChartLabelPosition.CenterEndOutside
    ),
)

@Composable
private fun getGroupedBarListVertical() = listOf(
    BarChartSegmentData(
        minValue = 0.0,
        maxValue = 1.0,
        label = "Test",
        color = ChartsSampleColors.colorPurple75,
        contentDescription = "Test Bar Segment Magenta",
        labelPosition = BarChartLabelPosition.TopCenter
    ),
    BarChartSegmentData(
        minValue = 0.0,
        maxValue = 4.0,
        label = "Test",
        color = ChartsSampleColors.colorYellow80,
        contentDescription = "Test Bar Segment Yellow",
        labelPosition = BarChartLabelPosition.TopCenter
    ),
    BarChartSegmentData(
        minValue = 0.0,
        maxValue = 5.0,
        label = "Test",
        color = ChartsSampleColors.colorGreen75,
        contentDescription = "Test Bar Segment Yellow",
        labelPosition = BarChartLabelPosition.TopCenter
    ),
)

// CHART VARIABLES
private val BarThickness = 15.dp
private val FixedUnitSize = 50.dp
private val LabelSize = 20.dp
private val BarPadding = 10.dp

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
