package com.kubit.charts.samples.components.zoomarea

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.axis.model.AxisBuilder
import com.kubit.charts.components.axis.model.AxisData
import com.kubit.charts.components.chart.linechart.LineChart
import com.kubit.charts.components.chart.linechart.model.LineBuilder
import com.kubit.charts.components.chart.linechart.model.LineStyle
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPoint
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPopUp
import com.kubit.charts.components.chart.linechart.model.ShadowUnderLine
import com.kubit.charts.components.chart.zoomareachart.ZoomAreaChart
import com.kubit.charts.components.chart.zoomareachart.ZoomAreaChartAccessibilityItem
import com.kubit.charts.components.chart.zoomareachart.ZoomAreaThumbPosition
import com.kubit.charts.components.scaffold.ChartScaffold
import com.kubit.charts.samples.R
import com.kubit.charts.samples.components.utils.ChartsSampleColors
import com.kubit.charts.samples.components.utils.getLineChartData
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
fun ZoomAreaChartSample() {
    ZoomAreaChart(
        startThumb = { Thumb() },
        endThumb = { Thumb() },
        zoomAreaAccessibilityItem = ZoomAreaChartAccessibilityItem(
            contentDescription = ZoomAreaContentDescription,
            leftCustomAction = LeftActionDescription,
            rightCustomAction = RightActionDescription
        ),
        leftHandlerAccessibilityItem = ZoomAreaChartAccessibilityItem(
            contentDescription = ThumbContentDescription,
            leftCustomAction = LeftActionDescription,
            rightCustomAction = RightActionDescription
        ),
        rightHandlerAccessibilityItem = ZoomAreaChartAccessibilityItem(
            contentDescription = ThumbContentDescription,
            leftCustomAction = LeftActionDescription,
            rightCustomAction = RightActionDescription
        ),
        opacityColor = Color.Red.copy(alpha = 0.2f),
        onWidthChange = {},
        onHeightChange = {},
        onSelectionChange = { _, _ -> }
    )
}

@Preview
@Suppress("MagicNumber")
@Composable
private fun Thumb() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(40.dp)
            .width(34.dp)
            .background(Color.Transparent)
    ) {

        Box(
            modifier = Modifier
                .size(34.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFF666666),
                    shape = RoundedCornerShape(size = 34.dp)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(size = 34.dp)
                )
                .padding(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.star),
                contentDescription = "Point 4, Custom Icon",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview
@Suppress("MagicNumber")
@Composable
private fun ArrowLeft() {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .height(40.dp)
            .width(34.dp)
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        bottomStart = 12.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        bottomStart = 12.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp
                    )
                ),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_area),
                contentDescription = "Arrow left icon",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer {
                        scaleX = -1f
                    }
            )
        }
    }
}

@Preview
@Suppress("MagicNumber")
@Composable
private fun ArrowRight() {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .height(40.dp)
            .width(34.dp)
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 0.dp,
                        topEnd = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 0.dp,
                        topEnd = 12.dp,
                        bottomEnd = 12.dp
                    )
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_area),
                contentDescription = "Arrow left icon",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Suppress("MagicNumber", "LongMethod", "CyclomaticComplexMethod")
@Composable
fun ZoomAreaChartThumbSample(thumbPosition: ZoomAreaThumbPosition = ZoomAreaThumbPosition.Middle) {
    val density = LocalDensity.current
    var parentZoomWidthPx by rememberSaveable { mutableIntStateOf(0) }
    var parentWidthPx by rememberSaveable { mutableIntStateOf(0) }
    var parentHeightPx by rememberSaveable { mutableIntStateOf(0) }
    var startFraction by rememberSaveable { mutableFloatStateOf(0f) }
    var endFraction by rememberSaveable { mutableFloatStateOf(1f) }

    val selectedPoints = remember(startFraction, endFraction) {
        val xMin = XAxisData.range.start
        val xMax = XAxisData.range.endInclusive
        val selectedXStart = xMin + (xMax - xMin) * startFraction
        val selectedXEnd = xMin + (xMax - xMin) * endFraction

        val filteredPoints = PointsData.filter { point ->
            point.x in selectedXStart..selectedXEnd
        }

        filteredPoints.ifEmpty {
            listOf(PointsData.minByOrNull {
                kotlin.math.abs(it.x - (selectedXStart + selectedXEnd) / 2)
            } ?: Offset(0f, 0f))
        }
    }
    val selectedXAxisData = getXAxisData(selectedPoints)
    val selectedYAxisData = getYAxisData(selectedPoints)
    val globalYMin = YAxisData.range.start

    Column {
        ChartScaffold(
            xAxisData = selectedXAxisData,
            yAxisData = selectedYAxisData,
            xUnitSize = with(density) { (parentZoomWidthPx / (selectedXAxisData.range.endInclusive - selectedXAxisData.range.start)).toDp() },
            yUnitSize = 5.dp,
            content = { contentData ->
                with(contentData) {
                    LineChart(
                        modifier = Modifier
                            .heightIn(max = 300.dp)
                            .onGloballyPositioned {
                                parentZoomWidthPx = it.size.width
                            },
                        lines = listOf(
                            LineBuilder()
                                .addPoints(selectedPoints)
                                .setLineStyle(LineStyle(color = ChartsSampleColors.colorSky85))
                                .setSelectionHighlightPoint(SelectionHighlightPoint())
                                .setSelectionHighlightPopUp(SelectionHighlightPopUp())
                                .setShadowUnderLine(
                                    ShadowUnderLine(
                                        brush = Brush.verticalGradient(
                                            listOf(ChartsSampleColors.colorSky85, Color.Transparent)
                                        ),
                                        alpha = 0.3f,
                                    )
                                )
                                .build()
                        ).toImmutableList(),
                        xAxisData = selectedXAxisData,
                        yAxisData = selectedYAxisData,
                        xAxisStepSize = with(density) { (parentZoomWidthPx / (selectedXAxisData.range.endInclusive - selectedXAxisData.range.start)).toDp() },
                        yAxisStepSize = 5.dp,
                        horizontalScroll = horizontalScroll,
                        zoom = zoom,
                        yMin = globalYMin
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ZoomAreaChart(
            modifier = if (thumbPosition == ZoomAreaThumbPosition.Custom) {
                Modifier
                    .padding(start = ThumbSize / 2, end = 0.dp)
            } else {
                Modifier
            },
            thumbSize = ThumbSize,
            thumbPosition = thumbPosition,
            startThumb = {
                when (thumbPosition) {
                    ZoomAreaThumbPosition.Outside -> ArrowLeft()
                    ZoomAreaThumbPosition.Inside -> ArrowRight()
                    ZoomAreaThumbPosition.Middle -> Thumb()
                    ZoomAreaThumbPosition.Custom -> ArrowLeft()
                }
            },
            endThumb = {
                when (thumbPosition) {
                    ZoomAreaThumbPosition.Outside -> ArrowRight()
                    ZoomAreaThumbPosition.Inside -> ArrowLeft()
                    ZoomAreaThumbPosition.Middle -> Thumb()
                    ZoomAreaThumbPosition.Custom -> Thumb()
                }
            },
            customStartOffset = if (thumbPosition == ZoomAreaThumbPosition.Custom) -50f else null,
            customEndOffset = if (thumbPosition == ZoomAreaThumbPosition.Custom) -50f else null,
            opacityColor = ChartsSampleColors.colorSky85.copy(alpha = 0.2f),
            onWidthChange = { width -> parentWidthPx = width },
            onHeightChange = { height -> parentHeightPx = height },
            initialStartFraction = startFraction,
            initialEndFraction = if (thumbPosition == ZoomAreaThumbPosition.Custom) 0.7f else endFraction,
            onSelectionChange = { newStartFraction, newEndFraction ->
                startFraction = newStartFraction
                endFraction = newEndFraction
            },
            zoomAreaAccessibilityItem = ZoomAreaChartAccessibilityItem(
                contentDescription = ZoomAreaContentDescription,
                leftCustomAction = LeftActionDescription,
                rightCustomAction = RightActionDescription,
            ),
            leftHandlerAccessibilityItem = ZoomAreaChartAccessibilityItem(
                contentDescription = ThumbContentDescription,
                leftCustomAction = LeftActionDescription,
                rightCustomAction = RightActionDescription
            ),
            rightHandlerAccessibilityItem = ZoomAreaChartAccessibilityItem(
                contentDescription = ThumbContentDescription,
                leftCustomAction = LeftActionDescription,
                rightCustomAction = RightActionDescription
            ),
            content = {
                ChartScaffold(
                    xAxisData = XAxisData,
                    yAxisData = YAxisData,
                    xUnitSize = with(density) { (parentWidthPx / (XAxisData.range.endInclusive - XAxisData.range.start)).toDp() },
                    yUnitSize = with(density) { (parentHeightPx / (YAxisData.range.endInclusive - YAxisData.range.start)).toDp() },
                    content = { contentData ->
                        with(contentData) {
                            LineChart(
                                lines = listOf(
                                    LineBuilder()
                                        .addPoints(PointsData)
                                        .setLineStyle(
                                            LineStyle(
                                                color = ChartsSampleColors.colorSky85,
                                                width = 4f
                                            )
                                        )
                                        .setSelectionHighlightPoint(SelectionHighlightPoint())
                                        .setSelectionHighlightPopUp(SelectionHighlightPopUp())
                                        .setShadowUnderLine(
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
                                        .build()
                                ).toImmutableList(),
                                xAxisData = XAxisData,
                                yAxisData = YAxisData,
                                xAxisStepSize = with(density) { (parentWidthPx / (XAxisData.range.endInclusive - XAxisData.range.start)).toDp() },
                                yAxisStepSize = with(density) { (parentHeightPx / (YAxisData.range.endInclusive - YAxisData.range.start)).toDp() },
                                horizontalScroll = horizontalScroll,
                                zoom = zoom
                            )
                        }
                    }
                )
            }
        )
    }
}

private fun getXAxisData(points: List<Offset>): AxisData {
    if (points.isEmpty()) {
        return AxisBuilder().addNodes(listOf(0f, 1f)).build()
    }

    return AxisBuilder().addNodes(
        listOf(
            points.minByOrNull { it.x }?.x ?: 0f,
            points.maxByOrNull { it.x }?.x ?: 1f
        )
    ).build()
}

private fun getYAxisData(points: List<Offset>): AxisData {
    if (points.isEmpty()) {
        return AxisBuilder().addNodes(listOf(0f, 1f)).build()
    }

    return AxisBuilder().addNodes(
        listOf(
            points.minByOrNull { it.y }?.y ?: 0f,
            points.maxByOrNull { it.y }?.y ?: 1f
        )
    ).build()
}

val PointsData = getLineChartData(100, start = 50, maxRange = 100)
val XAxisData = getXAxisData(PointsData)
val YAxisData = getYAxisData(PointsData)
val ThumbSize = 40.dp

private const val ThumbContentDescription = "Thumb Handler"
private const val ZoomAreaContentDescription = "Zoom Area"
private const val LeftActionDescription = "Move left"
private const val RightActionDescription = "Move right"
