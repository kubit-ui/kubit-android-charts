package com.kubit.charts.components.chart.barchart

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.axis.model.AxisBuilder
import com.kubit.charts.components.axis.model.AxisData
import com.kubit.charts.components.axis.model.axisBuilder
import com.kubit.charts.components.chart.barchart.extensions.drawRoundRectWithSpecificCorners
import com.kubit.charts.components.chart.barchart.extensions.drawUnifiedBarChart
import com.kubit.charts.components.chart.barchart.model.BarChartAlignment
import com.kubit.charts.components.chart.barchart.model.BarChartAppearance
import com.kubit.charts.components.chart.barchart.model.BarChartData
import com.kubit.charts.components.chart.barchart.model.BarChartLabelPosition
import com.kubit.charts.components.chart.barchart.model.BarChartOrientation
import com.kubit.charts.components.chart.barchart.model.BarChartSegmentData
import com.kubit.charts.components.chart.barchart.model.BarChartSegmentPosition
import com.kubit.charts.components.chart.barchart.model.BarChartStats
import com.kubit.charts.components.chart.barchart.model.BarChartType
import com.kubit.charts.components.chart.linechart.isNotNull
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlin.math.max
import org.jetbrains.annotations.ApiStatus.Experimental

/**
 * BarChart is a composable function that displays a bar chart with the provided data.
 * It supports different types of bar charts (single, stacked, grouped) and orientations (horizontal, vertical).
 *
 * This signature requires axes to work properly, for a simplified version that calculates axes automatically use
 * the other [BarChart] signature:
 *
 * ```kotlin
 * BarChart(
 *     data: BarChartData,
 *     modifier: Modifier = Modifier,
 *     onBarClick: (BarChartSegmentData) -> Unit = {},
 * )
 * ```
 *
 * @param data the list of [BarChartData] to be displayed in the chart.
 * @param xAxisData the data for the x-axis.
 * @param yAxisData the data for the y-axis.
 * @param xAxisStepSize the step size for the x-axis.
 * @param modifier the modifier to be applied to the chart.
 * @param yAxisStepSize the step size for the y-axis, defaults to the same as xAxisStepSize.
 * @param horizontalScroll the horizontal scroll offset, defaults to 0.dp.
 * @param verticalScroll the vertical scroll offset, defaults to 0.dp.
 * @param zoom the zoom factor for the chart, defaults to 1f.
 * @param onBarClick a callback that is invoked when a bar/segment is clicked, defaults to an empty lambda.
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Suppress("LongMethod")
@Composable
fun BarChart(
    data: PersistentList<BarChartData>,
    xAxisData: AxisData,
    yAxisData: AxisData,
    xAxisStepSize: Dp,
    modifier: Modifier = Modifier,
    yAxisStepSize: Dp = xAxisStepSize,
    horizontalScroll: Dp = 0.dp,
    verticalScroll: Dp = 0.dp,
    zoom: Float = 1f,
    onBarClick: (BarChartSegmentData) -> Unit = {},
) {
    val textMeasurer = rememberTextMeasurer()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
    ) {
        val density = LocalDensity.current
        val yBottomDp = with(density) { constraints.maxHeight.toDp() }
        val canvasWidth = maxWidth
        val canvasHeight = maxHeight
        val barParamsMap = remember(
            data,
            xAxisStepSize,
            yAxisStepSize,
            zoom,
            xAxisData,
            yAxisData,
            horizontalScroll,
            verticalScroll,
            yBottomDp
        ) {
            data.associateWith { barChart ->
                calculateBarParams(
                    barChart = barChart,
                    xAxisStepSize = xAxisStepSize,
                    yAxisStepSize = yAxisStepSize,
                    zoom = zoom,
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    horizontalScroll = horizontalScroll,
                    verticalScroll = verticalScroll,
                    yBottomDp = yBottomDp
                )
            }
        }

        Canvas(
            modifier = Modifier
                .size(canvasWidth, canvasHeight)
                .pointerInput(data) {
                    detectTapGestures { offset ->
                        data.forEach { barChart ->
                            barParamsMap[barChart]?.let { barParams ->
                                handleCanvasClick(
                                    offset = offset,
                                    barChart = barChart,
                                    barParams = barParams,
                                    zoom = zoom,
                                    density = density,
                                    onBarClick = onBarClick
                                )
                            }
                        }
                    }
                }
        ) {
            data.forEach { barChart ->
                barParamsMap[barChart]?.let { barParams ->
                    drawUnifiedBarChart(
                        barChart = barChart,
                        barParams = barParams,
                        zoom = zoom,
                        density = density,
                        textMeasurer = textMeasurer
                    )
                }
            }
        }

        data.forEach { barChart ->
            barParamsMap[barChart]?.let { barParams ->
                RenderBarChartAccessibility(
                    barChart = barChart,
                    barParams = barParams,
                    zoom = zoom,
                    onBarClick = onBarClick
                )
            }
        }
    }
}

/**
 * A simplified version of the BarChart composable for rendering a single BarChartData instance
 *
 * @param data the [BarChartData] to be displayed in the chart.
 * @param modifier the modifier to be applied to the chart.
 * @param onBarClick a callback that is invoked when a bar/segment is clicked, defaults to an empty lambda.
 */
@Experimental
@SuppressLint("UnusedBoxWithConstraintsScope")
@Suppress("LongMethod")
@Composable
fun BarChart(
    data: BarChartData,
    modifier: Modifier = Modifier,
    onBarClick: (BarChartSegmentData) -> Unit = {},
) {
    BoxWithConstraints(modifier = modifier) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        val globalMin = data.segments.minBy { it.minValue }.minValue
        val globalMax = data.segments.maxBy { it.maxValue }.maxValue

        val unitAxis = AxisBuilder().addNode(0f).addNode(1f).build()
        val mainAxis =
            AxisBuilder().addNode(0f).addNode(globalMin.toFloat()).addNode(globalMax.toFloat())
                .build()

        val processedBarChartAlignment = if (data.orientation == BarChartOrientation.Horizontal) {
            BarChartAlignment.Start
        } else {
            BarChartAlignment.End
        }

        val textMeasurer = rememberTextMeasurer()

        val extraPaddings = calculateExtraPaddings(
            data = data,
            density = LocalDensity.current,
            textMeasurer = textMeasurer
        )

        var xAxisData = when (data.type) {
            BarChartType.Single,
            BarChartType.Stacked -> {
                if (data.orientation == BarChartOrientation.Horizontal) {
                    mainAxis
                } else {
                    unitAxis
                }
            }

            BarChartType.Grouped -> {
                if (data.orientation == BarChartOrientation.Horizontal) {
                    mainAxis
                } else {
                    AxisBuilder().addNode(0f).addNode(data.segments.size.toFloat()).build()
                }
            }
        }

        var yAxisData = when (data.type) {
            BarChartType.Single,
            BarChartType.Stacked -> {
                if (data.orientation == BarChartOrientation.Horizontal) {
                    unitAxis
                } else {
                    mainAxis
                }
            }

            BarChartType.Grouped -> {
                if (data.orientation == BarChartOrientation.Horizontal) {
                    AxisBuilder().addNode(0f).addNode(data.segments.size.toFloat()).build()
                } else {
                    mainAxis
                }
            }
        }

        with(LocalDensity.current) {
            // Step size taking into account the extra paddings derived from labels
            val xAxisStepSize =
                ((width - (extraPaddings.start + extraPaddings.end)) / xAxisData.rangeSize).toDp()
            val yAxisStepSize =
                ((height - (extraPaddings.top + extraPaddings.bottom)) / yAxisData.rangeSize).toDp()

            yAxisData = generateBarChartYAxis(
                data = data,
                globalMin = globalMin,
                globalMax = globalMax,
                extraPaddings = extraPaddings,
                yAxisStepSize = yAxisStepSize,
                density = this
            )

            xAxisData = generateBarChartXAxis(
                data = data,
                globalMin = globalMin,
                globalMax = globalMax,
                extraPaddings = extraPaddings,
                xAxisStepSize = xAxisStepSize,
                density = this
            )

            val newThickNess = when (data.type) {
                BarChartType.Stacked,
                BarChartType.Single -> if (data.orientation == BarChartOrientation.Horizontal) {
                    height - (extraPaddings.top + extraPaddings.bottom)
                } else {
                    width - (extraPaddings.start + extraPaddings.end)
                }

                BarChartType.Grouped -> if (data.orientation == BarChartOrientation.Horizontal) {
                    (((height - (extraPaddings.top + extraPaddings.bottom)) - (data.barSpacing.toPx()
                        .toInt() * (data.segments.size - 1))) / data.segments.size)
                } else {
                    ((width - (extraPaddings.start + extraPaddings.end) - (data.barSpacing.toPx()
                        .toInt() * (data.segments.size - 1))) / data.segments.size)
                }
            }.toDp()

            val processedData = data.copy(
                barThickness = newThickNess,
                stepPosition = 0f,
                barChartAlignment = processedBarChartAlignment
            )

            BarChart(
                data = persistentListOf(processedData),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                xAxisStepSize = xAxisStepSize,
                yAxisStepSize = yAxisStepSize,
                onBarClick = onBarClick
            )
        }
    }
}

private fun generateBarChartYAxis(
    data: BarChartData,
    globalMin: Double,
    globalMax: Double,
    extraPaddings: ExtraPaddingsForTextInPixels,
    yAxisStepSize: Dp,
    density: Density
) = axisBuilder {
    with(density) {
        addNode(0f)
        if (data.orientation == BarChartOrientation.Vertical) {
            addNode(globalMin.toFloat())
            addNode(globalMax.toFloat())

            if (extraPaddings.bottom > 0) {
                addNode(globalMin.toFloat() - (extraPaddings.bottom / yAxisStepSize.toPx()))
            }
            if (extraPaddings.top > 0) {
                addNode(globalMax.toFloat() + (extraPaddings.top / yAxisStepSize.toPx()))
            }
        } else {
            addNode(1f)

            if (extraPaddings.bottom > 0) {
                addNode(-(extraPaddings.bottom / yAxisStepSize.toPx()))
            }
            if (extraPaddings.top > 0) {
                addNode(1f + (extraPaddings.top / yAxisStepSize.toPx()))
            }
        }
    }
}

private fun generateBarChartXAxis(
    data: BarChartData,
    globalMin: Double,
    globalMax: Double,
    extraPaddings: ExtraPaddingsForTextInPixels,
    xAxisStepSize: Dp,
    density: Density
) = axisBuilder {
    with(density) {
        addNode(0f)
        if (data.orientation == BarChartOrientation.Horizontal) {
            addNode(globalMin.toFloat())
            addNode(globalMax.toFloat())

            if (extraPaddings.start > 0) {
                addNode(globalMin.toFloat() - (extraPaddings.start / xAxisStepSize.toPx()))
            }
            if (extraPaddings.end > 0) {
                addNode(globalMax.toFloat() + (extraPaddings.end / xAxisStepSize.toPx()))
            }
        } else {
            addNode(1f)

            if (extraPaddings.start > 0) {
                addNode(-(extraPaddings.start / xAxisStepSize.toPx()))
            }
            if (extraPaddings.end > 0) {
                addNode(1f + (extraPaddings.end / xAxisStepSize.toPx()))
            }
        }
    }
}

private fun calculateExtraPaddings(
    data: BarChartData,
    density: Density,
    textMeasurer: TextMeasurer
): ExtraPaddingsForTextInPixels {

    return when (data.type) {
        BarChartType.Single -> singleBarChartExtraPaddings(
            data = data,
            density = density,
            textMeasurer = textMeasurer
        )

        BarChartType.Stacked -> stackedBarChartExtraPaddings(
            data = data,
            density = density,
            textMeasurer = textMeasurer
        )

        BarChartType.Grouped -> groupedBarChartExtraPaddings(
            data = data,
            density = density,
            textMeasurer = textMeasurer
        )
    }
}

private fun singleBarChartExtraPaddings(
    data: BarChartData,
    density: Density,
    textMeasurer: TextMeasurer
): ExtraPaddingsForTextInPixels {
    var labelSpacing: Dp
    val segment = data.segments.first()
    return if (segment.label != null && segment.labelPosition != null && !segment.labelPosition.isCenter()) {
        labelSpacing = segment.labelSpacing
        val labelSpacingPx = with(density) { labelSpacing.toPx() }
        val result = textMeasurer.measure(
            text = segment.label,
            style = TextStyle(color = segment.labelColor, fontSize = segment.labelSize)
        )

        if (segment.labelPosition.isTop()) {
            ExtraPaddingsForTextInPixels(
                top = result.size.height.toFloat() + labelSpacingPx,
                bottom = 0f
            )
        } else if (segment.labelPosition.isCenterOutside()) {
            ExtraPaddingsForTextInPixels(
                start = if (segment.labelPosition == BarChartLabelPosition.CenterStartOutside) result.size.width.toFloat() + labelSpacingPx else 0f,
                end = if (segment.labelPosition == BarChartLabelPosition.CenterEndOutside) result.size.width.toFloat() + labelSpacingPx else 0f,
            )
        } else {
            ExtraPaddingsForTextInPixels(
                top = 0f,
                bottom = result.size.height.toFloat() + labelSpacingPx
            )
        }
    } else {
        ExtraPaddingsForTextInPixels()
    }
}

private fun stackedBarChartExtraPaddings(
    data: BarChartData,
    density: Density,
    textMeasurer: TextMeasurer
): ExtraPaddingsForTextInPixels {
    var topLabel = false
    var bottomLabel = false

    val labelSpacing: Dp = data.segments.maxBy { it.labelSpacing }.labelSpacing
    val labelSpacingPx = with(density) { labelSpacing.toPx() }

    var maxValue = 0

    val segmentsWidth: MutableMap<BarChartSegmentData, Float> = mutableMapOf()

    data.segments.forEach {
        if (it.label != null && it.labelPosition != null && !it.labelPosition.isCenter()) {
            val result = textMeasurer.measure(
                text = it.label,
                style = TextStyle(color = it.labelColor, fontSize = it.labelSize)
            )

            topLabel = topLabel || it.labelPosition.isTop()

            bottomLabel =
                bottomLabel || it.labelPosition.isBottom()

            segmentsWidth[it] = result.size.width.toFloat()

            maxValue = max(maxValue, result.size.height)
        }
    }

    val minSegment = data.segments.minBy { it.minValue }
    val startLabel = minSegment.labelPosition == BarChartLabelPosition.CenterStartOutside
    val maxSegment = data.segments.maxBy { it.maxValue }
    val endLabel = maxSegment.labelPosition == BarChartLabelPosition.CenterEndOutside

    return ExtraPaddingsForTextInPixels(
        top = if (topLabel) maxValue.toFloat() + labelSpacingPx else 0f,
        bottom = if (bottomLabel) maxValue.toFloat() + labelSpacingPx else 0f,
        start = if (startLabel) (segmentsWidth[minSegment] ?: 0f) + labelSpacingPx else 0f,
        end = if (endLabel) (segmentsWidth[maxSegment] ?: 0f) + labelSpacingPx else 0f
    )
}

@Suppress("LongMethod", "CyclomaticComplexMethod")
private fun groupedBarChartExtraPaddings(
    data: BarChartData,
    density: Density,
    textMeasurer: TextMeasurer
): ExtraPaddingsForTextInPixels {
    // TODO Try to refactor this method to reduce complexity and longitude
    return if (data.orientation == BarChartOrientation.Horizontal) {
        val firstSegment = data.segments.first()
        var labelSpacingPx =
            with(density) { firstSegment.labelSpacing.toPx() }

        val topPadding =
            if (firstSegment.label != null && firstSegment.labelPosition != null && firstSegment.labelPosition.isTop()) {
                val result = textMeasurer.measure(
                    text = firstSegment.label,
                    style = TextStyle(
                        color = firstSegment.labelColor,
                        fontSize = firstSegment.labelSize
                    )
                )

                result.size.height.toFloat() + labelSpacingPx
            } else {
                0f
            }

        val lastSegment = data.segments.last()
        labelSpacingPx = with(density) { lastSegment.labelSpacing.toPx() }

        val bottomPadding =
            if (lastSegment.label != null && lastSegment.labelPosition != null && lastSegment.labelPosition.isBottom()) {
                val result = textMeasurer.measure(
                    text = lastSegment.label,
                    style = TextStyle(
                        color = lastSegment.labelColor,
                        fontSize = lastSegment.labelSize
                    )
                )

                result.size.height.toFloat() + labelSpacingPx
            } else {
                0f
            }

        val minSegment = data.segments.minBy { it.minValue }
        val minSegments = data.segments.filter { it.minValue == minSegment.minValue }

        val startPadding = minSegments.maxOf {
            if (it.label != null && it.labelPosition != null && it.labelPosition == BarChartLabelPosition.CenterStartOutside) {
                labelSpacingPx = with(density) { it.labelSpacing.toPx() }

                val result = textMeasurer.measure(
                    text = it.label,
                    style = TextStyle(
                        color = it.labelColor,
                        fontSize = it.labelSize
                    )
                )

                result.size.width.toFloat() + labelSpacingPx
            } else {
                0f
            }
        }

        val maxSegment = data.segments.maxBy { it.maxValue }
        val maxSegments = data.segments.filter { it.maxValue == maxSegment.maxValue }

        val endPadding = maxSegments.maxOf {
            if (it.label != null && it.labelPosition != null && it.labelPosition == BarChartLabelPosition.CenterEndOutside) {
                labelSpacingPx = with(density) { it.labelSpacing.toPx() }

                val result = textMeasurer.measure(
                    text = it.label,
                    style = TextStyle(
                        color = it.labelColor,
                        fontSize = it.labelSize
                    )
                )

                result.size.width.toFloat() + labelSpacingPx
            } else {
                0f
            }
        }

        ExtraPaddingsForTextInPixels(
            top = topPadding,
            bottom = bottomPadding,
            start = startPadding,
            end = endPadding
        )
    } else {
        val minSegment = data.segments.minBy { it.minValue }
        val minSegments = data.segments.filter { it.minValue == minSegment.minValue }

        var labelSpacingPx: Float

        val bottomPadding = minSegments.maxOf {
            if (it.label != null && it.labelPosition != null && it.labelPosition.isBottom()) {
                labelSpacingPx = with(density) { it.labelSpacing.toPx() }

                val result = textMeasurer.measure(
                    text = it.label,
                    style = TextStyle(
                        color = it.labelColor,
                        fontSize = it.labelSize
                    )
                )

                result.size.height.toFloat() + labelSpacingPx
            } else {
                0f
            }
        }

        val maxSegment = data.segments.maxBy { it.maxValue }
        val maxSegments = data.segments.filter { it.maxValue == maxSegment.maxValue }

        val topPadding = maxSegments.maxOf {
            if (it.label != null && it.labelPosition != null && it.labelPosition.isTop()) {
                labelSpacingPx = with(density) { it.labelSpacing.toPx() }

                val result = textMeasurer.measure(
                    text = it.label,
                    style = TextStyle(
                        color = it.labelColor,
                        fontSize = it.labelSize
                    )
                )

                result.size.height.toFloat() + labelSpacingPx
            } else {
                0f
            }
        }

        val firstSegment = data.segments.first()
        labelSpacingPx =
            with(density) { firstSegment.labelSpacing.toPx() }

        val startPadding =
            if (firstSegment.label != null && firstSegment.labelPosition != null && firstSegment.labelPosition == BarChartLabelPosition.CenterStartOutside) {
                val result = textMeasurer.measure(
                    text = firstSegment.label,
                    style = TextStyle(
                        color = firstSegment.labelColor,
                        fontSize = firstSegment.labelSize
                    )
                )

                result.size.width.toFloat() + labelSpacingPx
            } else {
                0f
            }

        val lastSegment = data.segments.last()
        labelSpacingPx = with(density) { lastSegment.labelSpacing.toPx() }

        val endPadding =
            if (lastSegment.label != null && lastSegment.labelPosition != null && lastSegment.labelPosition == BarChartLabelPosition.CenterEndOutside) {
                val result = textMeasurer.measure(
                    text = lastSegment.label,
                    style = TextStyle(
                        color = lastSegment.labelColor,
                        fontSize = lastSegment.labelSize
                    )
                )

                result.size.width.toFloat() + labelSpacingPx
            } else {
                0f
            }

        ExtraPaddingsForTextInPixels(
            top = topPadding,
            bottom = bottomPadding,
            start = startPadding,
            end = endPadding
        )
    }
}

private fun handleCanvasClick(
    offset: Offset,
    barChart: BarChartData,
    barParams: BarChartParams,
    zoom: Float,
    density: Density,
    onBarClick: (BarChartSegmentData) -> Unit
) {
    val scaledBarThickness = barChart.barThickness * zoom
    val scaledBarSpacing = barChart.barSpacing * zoom
    val isVertical = barChart.orientation == BarChartOrientation.Vertical
    val stats = BarChartStatsCalculator.calculate(barChart.segments)
    val isStacked = barChart.type == BarChartType.Stacked

    with(density) {
        val chartOffset = Offset(
            x = barParams.offsetX.toPx(),
            y = barParams.offsetY.toPx()
        )

        val localOffset = Offset(
            x = offset.x - chartOffset.x,
            y = offset.y - chartOffset.y
        )

        if (isStacked) {
            val barSizePx = barParams.maxBarSize.toPx()
            val barThicknessPx = scaledBarThickness.toPx()

            checkStackedBarClick(
                localOffset = localOffset,
                segments = SegmentSorter.sortForStacking(barChart.segments, isVertical),
                stats = stats,
                barSizePx = barSizePx,
                barThicknessPx = barThicknessPx,
                isVertical = isVertical,
                onBarClick = onBarClick
            )
        } else {
            val dimensions = calculateDimensions(
                density = this,
                maxBarSize = barParams.maxBarSize,
                barThickness = scaledBarThickness,
                barSpacing = scaledBarSpacing,
                segmentCount = barChart.segments.size,
                isVertical = isVertical
            )
            checkRegularBarClick(
                localOffset = localOffset,
                segments = barChart.segments,
                stats = stats,
                dimensions = dimensions,
                isVertical = isVertical,
                onBarClick = onBarClick
            )
        }
    }
}

private fun checkRegularBarClick(
    localOffset: Offset,
    segments: List<BarChartSegmentData>,
    stats: BarChartStats,
    dimensions: ChartDimensions,
    isVertical: Boolean,
    onBarClick: (BarChartSegmentData) -> Unit
) {
    segments.forEachIndexed { index, segment ->
        val barMetrics = BarMetrics.calculate(segment, stats, index, dimensions)

        val isInBar = if (isVertical) {
            localOffset.x >= barMetrics.x &&
                    localOffset.x <= barMetrics.x + barMetrics.barThickness &&
                    localOffset.y >= barMetrics.startY &&
                    localOffset.y <= barMetrics.startY + barMetrics.barHeight
        } else {
            localOffset.x >= barMetrics.startX &&
                    localOffset.x <= barMetrics.startX + barMetrics.barWidth &&
                    localOffset.y >= barMetrics.y &&
                    localOffset.y <= barMetrics.y + barMetrics.barThickness
        }

        if (isInBar) {
            onBarClick(segment)
            return
        }
    }
}

private fun checkStackedBarClick(
    localOffset: Offset,
    segments: List<BarChartSegmentData>,
    stats: BarChartStats,
    barSizePx: Float,
    barThicknessPx: Float,
    isVertical: Boolean,
    onBarClick: (BarChartSegmentData) -> Unit
) {
    if (isVertical) {
        var currentY = barSizePx
        segments.forEach { segment ->
            val fraction = ((segment.maxValue - segment.minValue) / stats.totalRange).toFloat()
            val segmentHeight = barSizePx * fraction
            currentY -= segmentHeight

            val isInSegment = segmentHeight > 0f &&
                    currentY >= 0f &&
                    localOffset.x in 0f..barThicknessPx &&
                    localOffset.y in currentY..(currentY + segmentHeight)

            if (isInSegment) {
                onBarClick(segment)
                return
            }
        }
    } else {
        var currentX = 0f
        segments.forEach { segment ->
            val fraction = ((segment.maxValue - segment.minValue) / stats.totalRange).toFloat()
            val segmentWidth = barSizePx * fraction

            val isInSegment = localOffset.x >= currentX &&
                    localOffset.x <= currentX + segmentWidth &&
                    localOffset.y >= 0f &&
                    localOffset.y <= barThicknessPx

            if (isInSegment) {
                onBarClick(segment)
                return
            }

            currentX += segmentWidth
        }
    }
}

private fun calculateBarParams(
    barChart: BarChartData,
    xAxisStepSize: Dp,
    yAxisStepSize: Dp,
    zoom: Float,
    xAxisData: AxisData,
    yAxisData: AxisData,
    horizontalScroll: Dp,
    verticalScroll: Dp,
    yBottomDp: Dp
): BarChartParams {
    val fixedStepSize = if (barChart.orientation == BarChartOrientation.Horizontal) {
        xAxisStepSize
    } else {
        yAxisStepSize
    }

    val maxBarSize = (fixedStepSize * (
            barChart.segments.maxOf { it.maxValue.toFloat() } -
                    barChart.segments.minOf { it.minValue.toFloat() }
            )) * zoom

    val (offsetX, offsetY) = getBarInitialPoint(
        type = barChart.type,
        segmentsNumber = barChart.segments.size,
        yBottom = yBottomDp,
        x = if (barChart.orientation == BarChartOrientation.Horizontal) {
            barChart.segments.minOf { it.minValue.toFloat() } * zoom
        } else {
            barChart.stepPosition * zoom
        },
        y = if (barChart.orientation == BarChartOrientation.Horizontal) {
            barChart.stepPosition * zoom
        } else {
            barChart.segments.maxOf { it.maxValue.toFloat() } * zoom
        },
        xMin = xAxisData.range.start * zoom,
        yMin = yAxisData.range.start * zoom,
        xUnitSize = xAxisStepSize.value,
        yUnitSize = yAxisStepSize.value,
        barThickness = barChart.barThickness * zoom,
        barSpacing = barChart.barSpacing * zoom,
        horizontalScroll = horizontalScroll,
        verticalScroll = verticalScroll,
        orientation = barChart.orientation,
        barChartAlignment = barChart.barChartAlignment,
    )

    return BarChartParams(offsetX, offsetY, maxBarSize)
}

private fun getBarInitialPoint(
    type: BarChartType,
    segmentsNumber: Int,
    yBottom: Dp,
    x: Float,
    y: Float,
    xMin: Float,
    yMin: Float,
    xUnitSize: Float,
    yUnitSize: Float,
    barThickness: Dp,
    barSpacing: Dp,
    horizontalScroll: Dp,
    verticalScroll: Dp,
    orientation: BarChartOrientation,
    barChartAlignment: BarChartAlignment,
): Pair<Dp, Dp> {
    val isHorizontal = orientation == BarChartOrientation.Horizontal
    val main = if (isHorizontal) {
        (x - xMin) * xUnitSize - horizontalScroll.value
    } else {
        (x - xMin) * xUnitSize - horizontalScroll.value
    }
    val cross = if (isHorizontal) (y - yMin) * yUnitSize else (y - yMin) * yUnitSize

    val barCount = if (segmentsNumber > 1 && type != BarChartType.Stacked) segmentsNumber else 1
    val barSize = barThickness.value + barSpacing.value

    val (x1, y1) = if (isHorizontal) {
        val yPos = when (barChartAlignment) {
            BarChartAlignment.Start -> yBottom.value - barSize * barCount + barSpacing.value - cross + verticalScroll.value
            BarChartAlignment.Center -> yBottom.value - barSize * barCount / 2 + barSpacing.value / 2 - cross + verticalScroll.value
            BarChartAlignment.End -> yBottom.value - cross + verticalScroll.value
        }
        Pair(main.dp, yPos.dp)
    } else {
        val xPos = when (barChartAlignment) {
            BarChartAlignment.Start -> main - barSize * barCount + barSpacing.value
            BarChartAlignment.Center -> main - barSize * barCount / 2 + barSpacing.value / 2
            BarChartAlignment.End -> main
        }
        val yPos = yBottom.value - cross + verticalScroll.value
        Pair(xPos.dp, yPos.dp)
    }
    return Pair(x1, y1)
}

internal fun calculateLabelOffset(
    labelPosition: BarChartLabelPosition,
    metrics: BarMetrics,
    dimensions: ChartDimensions,
    isVertical: Boolean,
    labelHeightPx: Float,
    labelWidthPx: Float,
    labelSpacingPx: Float
): Offset {
    return if (isVertical) {
        calculateVerticalLabelOffset(
            labelPosition,
            metrics,
            dimensions,
            labelHeightPx,
            labelWidthPx,
            labelSpacingPx
        )
    } else {
        calculateHorizontalLabelOffset(
            labelPosition,
            metrics,
            dimensions,
            labelHeightPx,
            labelWidthPx,
            labelSpacingPx
        )
    }
}

private fun calculateVerticalLabelOffset(
    labelPosition: BarChartLabelPosition,
    metrics: BarMetrics,
    dimensions: ChartDimensions,
    labelHeightPx: Float,
    labelWidthPx: Float,
    labelSpacingPx: Float
): Offset {
    val barCenterX = metrics.x + (dimensions.barThicknessPx / 2)
    val barTop = metrics.startY
    val barBottom = metrics.startY + metrics.barHeight

    return when (labelPosition) {
        BarChartLabelPosition.TopCenter -> Offset(
            x = barCenterX - labelWidthPx / 2f,
            y = barTop - labelHeightPx - labelSpacingPx
        )

        BarChartLabelPosition.BottomCenter -> Offset(
            x = barCenterX - labelWidthPx / 2f,
            y = barBottom + labelSpacingPx
        )

        BarChartLabelPosition.TopStart -> Offset(
            x = metrics.x,
            y = barTop - labelHeightPx - labelSpacingPx
        )

        BarChartLabelPosition.TopEnd -> Offset(
            x = metrics.x + dimensions.barThicknessPx - labelWidthPx,
            y = barTop - labelHeightPx - labelSpacingPx
        )

        BarChartLabelPosition.BottomStart -> Offset(
            x = metrics.x,
            y = barBottom + labelSpacingPx
        )

        BarChartLabelPosition.BottomEnd -> Offset(
            x = metrics.x + dimensions.barThicknessPx - labelWidthPx,
            y = barBottom + labelSpacingPx
        )

        BarChartLabelPosition.Center -> Offset(
            x = barCenterX - labelWidthPx / 2f,
            y = barTop + (metrics.barHeight - labelHeightPx) / 2
        )

        BarChartLabelPosition.CenterStart -> Offset(
            x = metrics.x,
            y = barTop + (metrics.barHeight - labelHeightPx) / 2
        )

        BarChartLabelPosition.CenterEnd -> Offset(
            x = metrics.x + dimensions.barThicknessPx - labelWidthPx,
            y = barTop + (metrics.barHeight - labelHeightPx) / 2
        )

        BarChartLabelPosition.CenterStartOutside -> Offset(
            x = metrics.x - labelWidthPx - labelSpacingPx,
            y = barTop + (metrics.barHeight - labelHeightPx) / 2
        )

        BarChartLabelPosition.CenterEndOutside -> Offset(
            x = metrics.x + dimensions.barThicknessPx + labelSpacingPx,
            y = barTop + (metrics.barHeight - labelHeightPx) / 2
        )
    }
}

private fun calculateHorizontalLabelOffset(
    labelPosition: BarChartLabelPosition,
    metrics: BarMetrics,
    dimensions: ChartDimensions,
    labelHeightPx: Float,
    labelWidthPx: Float,
    labelSpacingPx: Float
): Offset {
    val barCenterX = metrics.startX + ((metrics.barWidth - labelWidthPx) / 2f)
    val barLeft = metrics.startX
    val barRight = metrics.startX + metrics.barWidth
    val barBottom = metrics.y + dimensions.barThicknessPx

    return when (labelPosition) {
        BarChartLabelPosition.TopCenter -> Offset(
            x = barCenterX,
            y = metrics.y - labelHeightPx - labelSpacingPx
        )

        BarChartLabelPosition.BottomCenter -> Offset(
            x = barCenterX,
            y = barBottom + labelSpacingPx
        )

        BarChartLabelPosition.TopStart -> Offset(
            x = barLeft,
            y = metrics.y - labelHeightPx - labelSpacingPx
        )

        BarChartLabelPosition.TopEnd -> Offset(
            x = metrics.startX + metrics.barWidth - labelWidthPx,
            y = metrics.y - labelHeightPx - labelSpacingPx
        )

        BarChartLabelPosition.BottomStart -> Offset(
            x = barLeft,
            y = barBottom + labelSpacingPx
        )

        BarChartLabelPosition.BottomEnd -> Offset(
            x = barRight - labelWidthPx,
            y = barBottom + labelSpacingPx
        )

        BarChartLabelPosition.Center -> Offset(
            x = barCenterX,
            y = barBottom - (dimensions.barThicknessPx + labelHeightPx) / 2
        )

        BarChartLabelPosition.CenterStart -> Offset(
            x = barLeft,
            y = barBottom - (dimensions.barThicknessPx + labelHeightPx) / 2
        )

        BarChartLabelPosition.CenterEnd -> Offset(
            x = barRight - labelWidthPx,
            y = barBottom - (dimensions.barThicknessPx + labelHeightPx) / 2
        )

        BarChartLabelPosition.CenterStartOutside -> Offset(
            x = barLeft - labelWidthPx - labelSpacingPx,
            y = barBottom - (dimensions.barThicknessPx + labelHeightPx) / 2
        )

        BarChartLabelPosition.CenterEndOutside -> Offset(
            x = barRight + labelSpacingPx,
            y = barBottom - (dimensions.barThicknessPx + labelHeightPx) / 2
        )
    }
}

internal data class StackedSegmentMetrics(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val centerX: Float,
    val centerY: Float,
    val top: Float,
    val bottom: Float,
    val left: Float,
    val right: Float
)

internal fun calculateStackedSegmentMetrics(
    segment: BarChartSegmentData,
    segmentIndex: Int,
    allSegments: List<BarChartSegmentData>,
    stats: BarChartStats,
    barSizePx: Float,
    barThicknessPx: Float,
    isVertical: Boolean
): StackedSegmentMetrics {
    val normalizedStart =
        ((segment.minValue - stats.globalMin) / stats.totalRange).toFloat().coerceIn(0f, 1f)
    val normalizedEnd =
        ((segment.maxValue - stats.globalMin) / stats.totalRange).toFloat().coerceIn(0f, 1f)

    return if (isVertical) {
        val segmentTop = barSizePx * (1f - normalizedEnd)
        val segmentBottom = barSizePx * (1f - normalizedStart)
        val segmentHeight = segmentBottom - segmentTop

        StackedSegmentMetrics(
            x = 0f,
            y = segmentTop,
            width = barThicknessPx,
            height = segmentHeight,
            centerX = barThicknessPx / 2,
            centerY = segmentTop + segmentHeight / 2,
            top = segmentTop,
            bottom = segmentBottom,
            left = 0f,
            right = barThicknessPx
        )
    } else {
        var currentX = 0f
        for (i in 0 until segmentIndex) {
            val prevFraction =
                ((allSegments[i].maxValue - allSegments[i].minValue) / stats.totalRange).toFloat()
            currentX += barSizePx * prevFraction
        }
        val fraction = ((segment.maxValue - segment.minValue) / stats.totalRange).toFloat()
        val segmentWidth = barSizePx * fraction

        StackedSegmentMetrics(
            x = currentX,
            y = 0f,
            width = segmentWidth,
            height = barThicknessPx,
            centerX = currentX + segmentWidth / 2,
            centerY = barThicknessPx / 2,
            top = 0f,
            bottom = barThicknessPx,
            left = currentX,
            right = currentX + segmentWidth
        )
    }
}

internal fun calculateStackedLabelOffset(
    labelPosition: BarChartLabelPosition,
    metrics: StackedSegmentMetrics,
    labelSpacingPx: Float,
    labelHeightPx: Float,
    labelWidthPx: Float,
): Offset {
    val halfLabelWidth = labelWidthPx / 2
    val halfLabelHeight = labelHeightPx / 2

    return when (labelPosition) {
        BarChartLabelPosition.TopCenter ->
            Offset(
                x = metrics.centerX - halfLabelWidth,
                y = metrics.top - labelHeightPx - labelSpacingPx
            )

        BarChartLabelPosition.TopStart ->
            Offset(x = metrics.left, y = metrics.top - labelHeightPx - labelSpacingPx)

        BarChartLabelPosition.TopEnd ->
            Offset(
                x = metrics.right - labelWidthPx,
                y = metrics.top - labelHeightPx - labelSpacingPx
            )

        BarChartLabelPosition.BottomCenter ->
            Offset(x = metrics.centerX - halfLabelWidth, y = metrics.bottom + labelSpacingPx)

        BarChartLabelPosition.BottomStart ->
            Offset(x = metrics.left, y = metrics.bottom + labelSpacingPx)

        BarChartLabelPosition.BottomEnd ->
            Offset(x = metrics.right - labelWidthPx, y = metrics.bottom + labelSpacingPx)

        BarChartLabelPosition.Center ->
            Offset(x = metrics.centerX - halfLabelWidth, y = metrics.centerY - halfLabelHeight)

        BarChartLabelPosition.CenterStart -> Offset(
            x = metrics.left,
            y = metrics.centerY - halfLabelHeight
        )

        BarChartLabelPosition.CenterEnd -> Offset(
            x = metrics.right - labelWidthPx,
            y = metrics.centerY - halfLabelHeight
        )

        BarChartLabelPosition.CenterStartOutside -> Offset(
            x = metrics.left - labelWidthPx - labelSpacingPx,
            y = metrics.centerY - halfLabelHeight
        )

        BarChartLabelPosition.CenterEndOutside -> Offset(
            x = metrics.right + labelSpacingPx,
            y = metrics.centerY - halfLabelHeight
        )
    }
}

internal data class StackedChartDimensions(
    val totalWidth: Dp,
    val totalHeight: Dp,
    val barSizePx: Float,
    val barThicknessPx: Float
)

internal data class BarChartParams(
    val offsetX: Dp,
    val offsetY: Dp,
    val maxBarSize: Dp
)

internal data class ChartDimensions(
    val totalWidth: Dp,
    val totalHeight: Dp,
    val maxBarSizePx: Float,
    val barThicknessPx: Float,
    val barSpacingPx: Float
) {
    companion object {
        fun calculate(
            density: Density,
            maxBarSize: Dp,
            barThickness: Dp,
            barSpacing: Dp,
            segmentCount: Int,
            isVertical: Boolean
        ): ChartDimensions {
            val maxBarSizePx = with(density) { maxBarSize.toPx() }
            val barThicknessPx = with(density) { barThickness.toPx() }
            val barSpacingPx = with(density) { barSpacing.toPx() }

            val totalWidth = if (isVertical) {
                segmentCount * barThicknessPx + (segmentCount - 1) * barSpacingPx
            } else {
                maxBarSizePx
            }

            val totalHeight = if (isVertical) {
                maxBarSizePx
            } else {
                segmentCount * barThicknessPx + (segmentCount - 1) * barSpacingPx
            }

            return ChartDimensions(
                totalWidth = with(density) { totalWidth.toDp() },
                totalHeight = with(density) { totalHeight.toDp() },
                maxBarSizePx = maxBarSizePx,
                barThicknessPx = barThicknessPx,
                barSpacingPx = barSpacingPx
            )
        }
    }
}

internal data class BarMetrics(
    val x: Float,
    val y: Float,
    val startX: Float,
    val startY: Float,
    val barWidth: Float,
    val barHeight: Float,
    val barThickness: Float,
    val normalizedStart: Float,
    val normalizedEnd: Float
) {
    companion object {
        fun calculate(
            segment: BarChartSegmentData,
            stats: BarChartStats,
            index: Int,
            dimensions: ChartDimensions
        ): BarMetrics {
            val normalizedStart = ((segment.minValue - stats.globalMin) / stats.totalRange)
                .toFloat().coerceIn(0f, 1f)
            val normalizedEnd = ((segment.maxValue - stats.globalMin) / stats.totalRange)
                .toFloat().coerceIn(0f, 1f)

            val x = index * (dimensions.barThicknessPx + dimensions.barSpacingPx)
            val y = index * (dimensions.barThicknessPx + dimensions.barSpacingPx)
            val startY = dimensions.maxBarSizePx * (1f - normalizedEnd)
            val startX = dimensions.maxBarSizePx * normalizedStart
            val barHeight = dimensions.maxBarSizePx * (normalizedEnd - normalizedStart)
            val barWidth = dimensions.maxBarSizePx * (normalizedEnd - normalizedStart)

            return BarMetrics(
                x = x,
                y = y,
                startX = startX,
                startY = startY,
                barWidth = barWidth,
                barHeight = barHeight,
                barThickness = dimensions.barThicknessPx,
                normalizedStart = normalizedStart,
                normalizedEnd = normalizedEnd
            )
        }
    }
}

internal object BarChartStatsCalculator {
    fun calculate(segments: List<BarChartSegmentData>): BarChartStats {
        val globalMin = segments.minOf { it.minValue }
        val globalMax = segments.maxOf { it.maxValue }
        val totalRange = globalMax - globalMin
        return BarChartStats(globalMin, globalMax, totalRange)
    }
}

internal object SegmentSorter {
    fun sortForStacking(
        segments: List<BarChartSegmentData>,
        isVertical: Boolean
    ): PersistentList<BarChartSegmentData> {
        return if (isVertical) {
            segments.sortedBy { it.minValue }.reversed()
        } else {
            segments.sortedBy { it.minValue }
        }.toPersistentList()
    }
}

internal data class CornerRadiusConfig(
    val topLeft: Float = 0f,
    val topRight: Float = 0f,
    val bottomLeft: Float = 0f,
    val bottomRight: Float = 0f
)

internal object CornerRadiusCalculator {
    fun getRegularCornerRadiusConfig(
        appearance: BarChartAppearance,
        barThicknessPx: Float,
        isVertical: Boolean
    ): CornerRadiusConfig {
        val radius = barThicknessPx / 2

        return when (appearance) {
            BarChartAppearance.Squared -> CornerRadiusConfig()

            BarChartAppearance.Rounded -> if (isVertical) {
                CornerRadiusConfig(
                    topLeft = radius,
                    topRight = radius,
                    bottomLeft = radius,
                    bottomRight = radius
                )
            } else {
                CornerRadiusConfig(
                    topLeft = radius,
                    topRight = radius,
                    bottomLeft = radius,
                    bottomRight = radius
                )
            }

            BarChartAppearance.Mixed -> if (isVertical) {
                CornerRadiusConfig(topLeft = radius, topRight = radius)
            } else {
                CornerRadiusConfig(topRight = radius, bottomRight = radius)
            }
        }
    }

    fun getStackedCornerRadiusConfig(
        index: Int,
        totalSegments: Int,
        appearance: BarChartAppearance,
        barThicknessPx: Float,
        isVertical: Boolean
    ): CornerRadiusConfig {
        val radius = barThicknessPx / 2
        val segmentPosition = when (index) {
            0 -> BarChartSegmentPosition.Start
            totalSegments - 1 -> BarChartSegmentPosition.End
            else -> BarChartSegmentPosition.Middle
        }

        return when (appearance) {
            BarChartAppearance.Squared -> CornerRadiusConfig()

            BarChartAppearance.Rounded -> when (segmentPosition) {
                BarChartSegmentPosition.Start -> if (isVertical) {
                    CornerRadiusConfig(topLeft = radius, topRight = radius)
                } else {
                    CornerRadiusConfig(topLeft = radius, bottomLeft = radius)
                }

                BarChartSegmentPosition.End -> if (isVertical) {
                    CornerRadiusConfig(bottomLeft = radius, bottomRight = radius)
                } else {
                    CornerRadiusConfig(topRight = radius, bottomRight = radius)
                }

                BarChartSegmentPosition.Middle -> CornerRadiusConfig()
            }

            BarChartAppearance.Mixed -> when (segmentPosition) {
                BarChartSegmentPosition.End -> if (isVertical) {
                    CornerRadiusConfig()
                } else {
                    CornerRadiusConfig(topRight = radius, bottomRight = radius)
                }

                BarChartSegmentPosition.Start -> if (isVertical) {
                    CornerRadiusConfig(topLeft = radius, topRight = radius)
                } else {
                    CornerRadiusConfig()
                }

                else -> CornerRadiusConfig()
            }
        }
    }
}

internal object BarDrawer {
    fun drawVerticalRegularBar(
        segment: BarChartSegmentData,
        metrics: BarMetrics,
        cornerConfig: CornerRadiusConfig,
        drawScope: DrawScope
    ) {
        drawScope.drawRoundRectWithSpecificCorners(
            color = segment.color,
            topLeft = Offset(metrics.x, metrics.startY),
            size = Size(metrics.barThickness, metrics.barHeight),
            cornerConfig = cornerConfig
        )
    }

    fun drawHorizontalRegularBar(
        segment: BarChartSegmentData,
        metrics: BarMetrics,
        cornerConfig: CornerRadiusConfig,
        drawScope: DrawScope
    ) {
        drawScope.drawRoundRectWithSpecificCorners(
            color = segment.color,
            topLeft = Offset(metrics.startX, metrics.y),
            size = Size(metrics.barWidth, metrics.barThickness),
            cornerConfig = cornerConfig
        )
    }
}

internal object StackedBarDrawer {
    fun draw(
        segments: List<BarChartSegmentData>,
        stats: BarChartStats,
        appearance: BarChartAppearance,
        barSizePx: Float,
        barThicknessPx: Float,
        isVertical: Boolean,
        drawScope: DrawScope
    ) {
        if (isVertical) {
            drawVerticalStacked(
                segments,
                stats,
                appearance,
                barSizePx,
                barThicknessPx,
                drawScope
            )
        } else {
            drawHorizontalStacked(
                segments,
                stats,
                appearance,
                barSizePx,
                barThicknessPx,
                drawScope
            )
        }
    }

    private fun drawVerticalStacked(
        segments: List<BarChartSegmentData>,
        stats: BarChartStats,
        appearance: BarChartAppearance,
        barSizePx: Float,
        barThicknessPx: Float,
        drawScope: DrawScope
    ) {
        segments.forEachIndexed { index, segment ->
            val normalizedStart =
                ((segment.minValue - stats.globalMin) / stats.totalRange).toFloat()
                    .coerceIn(0f, 1f)
            val normalizedEnd =
                ((segment.maxValue - stats.globalMin) / stats.totalRange).toFloat()
                    .coerceIn(0f, 1f)
            val segmentTop = barSizePx * (1f - normalizedEnd)
            val segmentBottom = barSizePx * (1f - normalizedStart)
            val segmentHeight = segmentBottom - segmentTop

            val cornerConfig = CornerRadiusCalculator.getStackedCornerRadiusConfig(
                index, segments.size, appearance, barThicknessPx, isVertical = true
            )

            drawScope.drawRoundRectWithSpecificCorners(
                color = segment.color,
                topLeft = Offset(0f, segmentTop),
                size = Size(barThicknessPx, segmentHeight),
                cornerConfig = cornerConfig
            )
        }
    }

    private fun drawHorizontalStacked(
        segments: List<BarChartSegmentData>,
        stats: BarChartStats,
        appearance: BarChartAppearance,
        barSizePx: Float,
        barThicknessPx: Float,
        drawScope: DrawScope
    ) {
        var currentX = 0f
        segments.forEachIndexed { index, segment ->
            val fraction = ((segment.maxValue - segment.minValue) / stats.totalRange).toFloat()
            val segmentWidth = barSizePx * fraction

            val cornerConfig = CornerRadiusCalculator.getStackedCornerRadiusConfig(
                index, segments.size, appearance, barThicknessPx, isVertical = false
            )

            drawScope.drawRoundRectWithSpecificCorners(
                color = segment.color,
                topLeft = Offset(currentX, 0f),
                size = Size(segmentWidth, barThicknessPx),
                cornerConfig = cornerConfig
            )

            currentX += segmentWidth
        }
    }
}

@Composable
private fun RenderRegularAccessibility(
    segments: PersistentList<BarChartSegmentData>,
    stats: BarChartStats,
    dimensions: ChartDimensions,
    isVertical: Boolean,
    onBarClick: (BarChartSegmentData) -> Unit,
    baseOffset: Offset = Offset.Zero
) {
    val density = LocalDensity.current

    segments.forEachIndexed { index, segment ->
        val barMetrics = BarMetrics.calculate(segment, stats, index, dimensions)
        val interactionSource = remember { MutableInteractionSource() }
        val label = segment.label.takeIf { it.isNotNull() }
        val contentDesc = if (label != null) {
            segment.contentDescription + ". " + label
        } else {
            segment.contentDescription
        }
        Box(
            modifier = Modifier
                .size(
                    width = with(density) {
                        if (isVertical) {
                            dimensions.barThicknessPx.toDp()
                        } else {
                            barMetrics.barWidth.toDp()
                        }
                    },
                    height = with(density) {
                        if (isVertical) {
                            barMetrics.barHeight.toDp()
                        } else {
                            dimensions.barThicknessPx.toDp()
                        }
                    }
                )
                .offset(
                    x = with(density) {
                        baseOffset.x.toDp() + if (isVertical) {
                            barMetrics.x.toDp()
                        } else {
                            barMetrics.startX.toDp()
                        }
                    },
                    y = with(density) {
                        baseOffset.y.toDp() + if (isVertical) {
                            barMetrics.startY.toDp()
                        } else {
                            barMetrics.y.toDp()
                        }
                    }
                )
                .semantics {
                    contentDescription = contentDesc
                    role = Role.Button
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple()
                ) {
                    onBarClick(segment)
                }
        )
    }
}

@Composable
private fun RenderStackedAccessibility(
    segments: PersistentList<BarChartSegmentData>,
    stats: BarChartStats,
    dimensions: StackedChartDimensions,
    isVertical: Boolean,
    onBarClick: (BarChartSegmentData) -> Unit,
    baseOffset: Offset = Offset.Zero
) {
    val density = LocalDensity.current

    segments.forEachIndexed { index, segment ->
        val stackedMetrics = calculateStackedSegmentMetrics(
            segment = segment,
            segmentIndex = index,
            allSegments = segments,
            stats = stats,
            barSizePx = dimensions.barSizePx,
            barThicknessPx = dimensions.barThicknessPx,
            isVertical = isVertical
        )
        val interactionSource = remember { MutableInteractionSource() }
        val label = segment.label
        val contentDesc = if (label != null) {
            segment.contentDescription + ". " + label
        } else {
            segment.contentDescription
        }
        with(density) {
            Box(
                modifier = Modifier
                    .size(
                        width = stackedMetrics.width.toDp(),
                        height = stackedMetrics.height.toDp()
                    )
                    .offset(
                        x = (baseOffset.x + stackedMetrics.x).toDp(),
                        y = (baseOffset.y + stackedMetrics.y).toDp()
                    )
                    .semantics {
                        contentDescription = contentDesc
                        role = Role.Button
                    }
                    .clickable(
                        interactionSource = interactionSource,
                        indication = ripple()
                    ) {
                        onBarClick(segment)
                    }
            )
        }
    }
}

@Composable
private fun RenderBarChartAccessibility(
    barChart: BarChartData,
    barParams: BarChartParams,
    zoom: Float,
    onBarClick: (BarChartSegmentData) -> Unit
) {
    val scaledBarThickness = barChart.barThickness * zoom
    val density = LocalDensity.current
    val isVertical = barChart.orientation == BarChartOrientation.Vertical

    val stats = remember(barChart.segments) {
        BarChartStatsCalculator.calculate(barChart.segments)
    }

    val isStacked = barChart.type == BarChartType.Stacked

    val dimensions = if (isStacked) {
        val barSizePx = with(density) { barParams.maxBarSize.toPx() }
        val barThicknessPx = with(density) { scaledBarThickness.toPx() }

        StackedChartDimensions(
            totalWidth = if (isVertical) scaledBarThickness else barParams.maxBarSize,
            totalHeight = if (isVertical) barParams.maxBarSize else scaledBarThickness,
            barSizePx = barSizePx,
            barThicknessPx = barThicknessPx
        )
    } else {
        calculateDimensions(
            density = density,
            maxBarSize = barParams.maxBarSize,
            barThickness = scaledBarThickness,
            barSpacing = barChart.barSpacing,
            segmentCount = barChart.segments.size,
            isVertical = isVertical
        )
    }

    if (isStacked) {
        RenderStackedAccessibility(
            segments = SegmentSorter.sortForStacking(barChart.segments, isVertical),
            stats = stats,
            dimensions = dimensions as StackedChartDimensions,
            isVertical = isVertical,
            onBarClick = onBarClick,
            baseOffset = calculateChartOffset(barParams, density)
        )
    } else {
        RenderRegularAccessibility(
            segments = barChart.segments.toPersistentList(),
            stats = stats,
            dimensions = dimensions as ChartDimensions,
            isVertical = isVertical,
            onBarClick = onBarClick,
            baseOffset = calculateChartOffset(barParams, density)
        )
    }
}

private fun calculateChartOffset(barParams: BarChartParams, density: Density): Offset {
    return Offset(
        x = with(density) { barParams.offsetX.toPx() },
        y = with(density) { barParams.offsetY.toPx() }
    )
}

internal fun calculateDimensions(
    density: Density,
    maxBarSize: Dp,
    barThickness: Dp,
    barSpacing: Dp,
    segmentCount: Int,
    isVertical: Boolean
): ChartDimensions {
    return ChartDimensions.calculate(
        density = density,
        maxBarSize = maxBarSize,
        barThickness = barThickness,
        barSpacing = barSpacing,
        segmentCount = segmentCount,
        isVertical = isVertical
    )
}

private data class ExtraPaddingsForTextInPixels(
    val start: Float = 0f,
    val end: Float = 0f,
    val top: Float = 0f,
    val bottom: Float = 0f
)
