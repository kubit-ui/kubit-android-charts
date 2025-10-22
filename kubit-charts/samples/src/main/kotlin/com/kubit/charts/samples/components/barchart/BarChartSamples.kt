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
import com.kubit.charts.components.axis.AxisLabelStyleDefaults
import com.kubit.charts.components.axis.HorizontalAxisChart
import com.kubit.charts.components.axis.HorizontalAxisType
import com.kubit.charts.components.axis.VerticalAxisChart
import com.kubit.charts.components.axis.VerticalAxisType
import com.kubit.charts.components.axis.model.AxisBuilder
import com.kubit.charts.components.axis.model.AxisPadding
import com.kubit.charts.components.axis.model.AxisStepStyle
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
import com.kubit.charts.components.chart.linechart.model.LineStyle
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPoint
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPopUp
import com.kubit.charts.components.chart.linechart.model.ShadowUnderLine
import com.kubit.charts.components.chart.linechart.model.lineBuilder
import com.kubit.charts.components.scaffold.ChartScaffold
import com.kubit.charts.samples.components.utils.ChartsSampleColors
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
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
                    label = "B",
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

@Preview(heightDp = 350)
@Composable
@Suppress("LongMethod", "MagicNumber")
fun BarChartWithLineChartInAxis() {

    ChartScaffold(
        modifier = Modifier.background(Color.DarkGray),
        isPinchZoomEnabled = true,
        xAxisData = Steps,
        yAxisData = Steps,
        xUnitSize = FixedUnitSize,
        yUnitSize = FixedUnitSize,
        axisPadding = AxisPadding(start = LabelSize, bottom = LabelSize),
        horizontalAxis = { horizontalScroll, zoom, padding ->
            HorizontalAxisChart(
                data = HorizontalSteps,
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
                                    maxValue = 2.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorBtcGreen.copy(alpha = 0.5f),
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
                                    color = ChartsSampleColors.colorBtcGreen.copy(alpha = 0.5f),
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
                                    maxValue = 3.0,
                                    label = "Test",
                                    color = ChartsSampleColors.colorBtcGreen.copy(alpha = 0.5f),
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
                        lineBuilder {
                            addPoints(
                                listOf(
                                    Offset(-1f, -1f),
                                    Offset(0f, 1f),
                                    Offset(2f, 3f),
                                    Offset(4f, 4f),
                                    Offset(8f, 8f),
                                )
                            ) { IntersectionPoint(color = ChartsSampleColors.colorBtcOrange) }
                                setSelectionHighlightPoint(
                                    SelectionHighlightPoint()
                                )
                                setSelectionHighlightPopUp(
                                    SelectionHighlightPopUp()
                                )
                            setLineStyle(
                                LineStyle(
                                    color = ChartsSampleColors.colorBtcOrange,
                                    width = 4f
                                )
                            )
                            setShadowUnderLine(
                                ShadowUnderLine(
                                    color = ChartsSampleColors.colorBtcOrange.copy(alpha = 0.1f),
                                )
                            )
                        }
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

@Preview(heightDp = HistogramHeightDp, widthDp = HistogramWidthDp)
@Composable
@Suppress("LongMethod", "MagicNumber")
fun BarChartHistogramWithAxis() {
    val yUnitSize = 22.dp
    val xUnitSize = 30.dp
    val startPadding = 50.dp
    val barThickness = 25.dp

    ChartScaffold(
            modifier =
                Modifier.background(Color.DarkGray)
                .padding(end = 10.dp),
            isPinchZoomEnabled = true,
            xAxisData = HistogramHorizontalSteps,
            yAxisData = HistogramVerticalSteps,
            xUnitSize = xUnitSize,
            yUnitSize = yUnitSize,
            axisPadding = AxisPadding(start = startPadding, bottom = LabelSize),
            horizontalAxis = { horizontalScroll, zoom, padding ->
                HorizontalAxisChart(
                    data = HistogramHorizontalSteps,
                    type = HorizontalAxisType.Bottom,
                    fixedUnitSize = xUnitSize,
                    labelHeight = LabelSize,
                    padding = padding,
                    horizontalScroll = horizontalScroll,
                    zoom = zoom
                )
            },
            verticalAxis = { verticalScroll, zoom, padding ->
                VerticalAxisChart(
                    data = HistogramVerticalSteps,
                    type = VerticalAxisType.Start,
                    labelWidth = startPadding,
                    fixedUnitSize = yUnitSize,
                    padding = padding,
                    verticalScroll = verticalScroll,
                    zoom = zoom
                )
            },
            content = { contentData ->
                with(contentData) {
                    BarChart(
                        data =
                            HistogramPoints.map {
                                BarChartData(
                                    type = BarChartType.Single,
                                    orientation = BarChartOrientation.Vertical,
                                    appearance = BarChartAppearance.Squared,
                                    segments = listOf(
                                        BarChartSegmentData(
                                            minValue = 0.0,
                                            maxValue = it.y.toDouble(),
                                            label = "Test",
                                            color = ChartsSampleColors.colorTurquoise60,
                                            contentDescription = "Test Bar Red"
                                        )
                                    ),
                                    barThickness = barThickness,
                                    stepPosition = it.x,
                                    barChartAlignment = BarChartAlignment.Center,
                                )
                            }.toPersistentList(),
                        xAxisData = HistogramHorizontalSteps,
                        yAxisData = HistogramVerticalSteps,
                        xAxisStepSize = xUnitSize,
                        yAxisStepSize = yUnitSize,
                        horizontalScroll = horizontalScroll,
                        verticalScroll = verticalScroll,
                        zoom = zoom,
                    )
                }
            }
        )
}

@Preview(heightDp = HistogramHeightDp, widthDp = HistogramWidthDp)
@Composable
@Suppress("LongMethod", "MagicNumber")
fun BarChartGroupedWithAxis() {
    val yUnitSize = 22.dp
    val xUnitSize = 60.dp
    val startPadding = 50.dp
    val barThickness = 25.dp

    ChartScaffold(
        modifier =
            Modifier.background(Color.DarkGray)
                .padding(end = 10.dp),
        isPinchZoomEnabled = true,
        xAxisData = HistogramHorizontalSteps,
        yAxisData = HistogramVerticalSteps,
        xUnitSize = xUnitSize,
        yUnitSize = yUnitSize,
        axisPadding = AxisPadding(start = startPadding, bottom = LabelSize),
        horizontalAxis = { horizontalScroll, zoom, padding ->
            HorizontalAxisChart(
                data = HistogramHorizontalSteps,
                type = HorizontalAxisType.Bottom,
                fixedUnitSize = xUnitSize,
                labelHeight = LabelSize,
                padding = padding,
                horizontalScroll = horizontalScroll,
                zoom = zoom
            )
        },
        verticalAxis = { verticalScroll, zoom, padding ->
            VerticalAxisChart(
                data = GroupedVerticalSteps,
                type = VerticalAxisType.Start,
                labelWidth = startPadding,
                fixedUnitSize = yUnitSize,
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
                            segments = persistentListOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 1.0,
                                    color = ChartsSampleColors.colorBtcOrange,
                                    contentDescription = "Test Bar Segment Magenta",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 4.0,
                                    color = ChartsSampleColors.colorTurquoise60,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 5.0,
                                    color = ChartsSampleColors.colorBtcGreen,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                            ),
                            barThickness = BarThickness,
                            barChartAlignment = BarChartAlignment.Center
                        ),
                        BarChartData(
                            type = BarChartType.Grouped,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = persistentListOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 3.0,
                                    color = ChartsSampleColors.colorBtcOrange,
                                    contentDescription = "Test Bar Segment Magenta",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 6.0,
                                    color = ChartsSampleColors.colorTurquoise60,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 5.0,
                                    color = ChartsSampleColors.colorBtcGreen,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                            ),
                            barThickness = BarThickness,
                            barChartAlignment = BarChartAlignment.Center,
                            stepPosition = 1f
                        ),
                        BarChartData(
                            type = BarChartType.Grouped,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = persistentListOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 4.0,
                                    color = ChartsSampleColors.colorBtcOrange,
                                    contentDescription = "Test Bar Segment Magenta",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 8.0,
                                    color = ChartsSampleColors.colorTurquoise60,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 3.0,
                                    color = ChartsSampleColors.colorBtcGreen,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                            ),
                            barThickness = BarThickness,
                            barChartAlignment = BarChartAlignment.Center,
                            stepPosition = 2f
                        ),
                        BarChartData(
                            type = BarChartType.Grouped,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = persistentListOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 3.4,
                                    color = ChartsSampleColors.colorBtcOrange,
                                    contentDescription = "Test Bar Segment Magenta",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 6.2,
                                    color = ChartsSampleColors.colorTurquoise60,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 5.5,
                                    color = ChartsSampleColors.colorBtcGreen,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                            ),
                            barThickness = BarThickness,
                            barChartAlignment = BarChartAlignment.Center,
                            stepPosition = 3f
                        ),
                        BarChartData(
                            type = BarChartType.Grouped,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = persistentListOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 3.0,
                                    color = ChartsSampleColors.colorBtcOrange,
                                    contentDescription = "Test Bar Segment Magenta",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 6.0,
                                    color = ChartsSampleColors.colorTurquoise60,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 5.0,
                                    color = ChartsSampleColors.colorBtcGreen,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                            ),
                            barThickness = BarThickness,
                            barChartAlignment = BarChartAlignment.Center,
                            stepPosition = 4f
                        ),
                        BarChartData(
                            type = BarChartType.Grouped,
                            orientation = BarChartOrientation.Vertical,
                            appearance = BarChartAppearance.Squared,
                            segments = persistentListOf(
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 4.0,
                                    color = ChartsSampleColors.colorBtcOrange,
                                    contentDescription = "Test Bar Segment Magenta",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 7.0,
                                    color = ChartsSampleColors.colorTurquoise60,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                                BarChartSegmentData(
                                    minValue = 0.0,
                                    maxValue = 2.0,
                                    color = ChartsSampleColors.colorBtcGreen,
                                    contentDescription = "Test Bar Segment Yellow",
                                    labelPosition = BarChartLabelPosition.TopCenter
                                ),
                            ),
                            barThickness = BarThickness,
                            barChartAlignment = BarChartAlignment.Center,
                            stepPosition = 5f
                        ),
                    ),
                    xAxisData = HistogramHorizontalSteps,
                    yAxisData = HistogramVerticalSteps,
                    xAxisStepSize = xUnitSize,
                    yAxisStepSize = yUnitSize,
                    horizontalScroll = horizontalScroll,
                    verticalScroll = verticalScroll,
                    zoom = zoom,
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
    .setDefaultStepStyle(
        AxisStepStyle.solid(
            strokeColor = Color.LightGray,
            strokeWidth = 1.dp,
        )
    )
    .setDefaultLabelStyle(AxisLabelStyleDefaults.default.copy(color = Color.LightGray))
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

private val StepsFromZero = AxisBuilder()
    .setDefaultStepStyle(
        AxisStepStyle.solid(
            strokeColor = Color.LightGray,
            strokeWidth = 1.dp,
        )
    )
    .setDefaultLabelStyle(AxisLabelStyleDefaults.default.copy(color = Color.LightGray))
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

private val HorizontalSteps = AxisBuilder()
    .setDefaultStepStyle(null)
    .setDefaultLabelStyle(AxisLabelStyleDefaults.default.copy(color = Color.LightGray.copy(alpha = 0.2f)))
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

private val HistogramHorizontalSteps = AxisBuilder()
    .setDefaultStepStyle(null)
    .setDefaultLabelStyle(AxisLabelStyleDefaults.default.copy(color = Color.LightGray.copy(alpha = 0.2f)))
    .addNode(-1f, "")
    .addNode(0f, "Jan")
    .addNode(1f, "Feb")
    .addNode(2f, "Mar")
    .addNode(3f, "Apr")
    .addNode(4f, "May")
    .addNode(5f, "Jun")
    .addNode(6f, "Jul")
    .addNode(7f, "Aug")
    .addNode(8f, "Sep")
    .addNode(9f, "Oct")
    .addNode(10f, "Nov")
    .addNode(11f, "Dec")
    .build()

private val HistogramVerticalSteps = AxisBuilder()
    .setDefaultStepStyle(
        AxisStepStyle.solid(
            strokeColor = Color.LightGray.copy(alpha = 0.3f),
            strokeWidth = 1.dp,
        )
    )
    .setDefaultLabelStyle(AxisLabelStyleDefaults.default.copy(color = Color.LightGray))
    .addNode(0f, "0")
    .addNode(1f, "1000")
    .addNode(2f, "2000")
    .addNode(3f, "3000")
    .addNode(4f, "4000")
    .addNode(5f, "5000")
    .addNode(6f, "6000")
    .addNode(7f, "7000")
    .addNode(8f, "8000")
    .addNode(9f, "9000")
    .addNode(10f, "10000")
    .build()

private val GroupedVerticalSteps = AxisBuilder()
    .setDefaultStepStyle(
        AxisStepStyle.solid(
            strokeColor = Color.LightGray.copy(alpha = 0.3f),
            strokeWidth = 1.dp,
        )
    )
    .setDefaultLabelStyle(AxisLabelStyleDefaults.default.copy(color = Color.LightGray))
    .addNode(0f, "0")
    .addNode(2f, "2000")
    .addNode(4f, "4000")
    .addNode(6f, "6000")
    .addNode(8f, "8000")
    .addNode(10f, "10000")
    .build()

private val HistogramPoints = listOf(
    Offset(0f, 4f),
    Offset(1f, 3f),
    Offset(2f, 4f),
    Offset(3f, 5f),
    Offset(4f, 6f),
    Offset(5f, 5f),
    Offset(6f, 3f),
    Offset(7f, 4f),
    Offset(8f, 5f),
    Offset(9f, 7f),
    Offset(10f, 8f),
    Offset(11f, 9f),
    )

private const val HistogramHeightDp = 250
private const val HistogramWidthDp = 450
