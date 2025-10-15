package com.kubit.charts.components.chart.barchart.extensions

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset
import com.kubit.charts.components.chart.barchart.BarChartParams
import com.kubit.charts.components.chart.barchart.BarChartStatsCalculator
import com.kubit.charts.components.chart.barchart.BarDrawer
import com.kubit.charts.components.chart.barchart.BarMetrics
import com.kubit.charts.components.chart.barchart.ChartDimensions
import com.kubit.charts.components.chart.barchart.CornerRadiusCalculator
import com.kubit.charts.components.chart.barchart.CornerRadiusConfig
import com.kubit.charts.components.chart.barchart.SegmentSorter
import com.kubit.charts.components.chart.barchart.StackedBarDrawer
import com.kubit.charts.components.chart.barchart.StackedChartDimensions
import com.kubit.charts.components.chart.barchart.calculateDimensions
import com.kubit.charts.components.chart.barchart.calculateLabelOffset
import com.kubit.charts.components.chart.barchart.calculateStackedLabelOffset
import com.kubit.charts.components.chart.barchart.calculateStackedSegmentMetrics
import com.kubit.charts.components.chart.barchart.model.BarChartAppearance
import com.kubit.charts.components.chart.barchart.model.BarChartData
import com.kubit.charts.components.chart.barchart.model.BarChartOrientation
import com.kubit.charts.components.chart.barchart.model.BarChartSegmentData
import com.kubit.charts.components.chart.barchart.model.BarChartStats
import com.kubit.charts.components.chart.barchart.model.BarChartType
import kotlin.collections.forEachIndexed

@Suppress("LongMethod")
internal fun DrawScope.drawUnifiedBarChart(
    barChart: BarChartData,
    barParams: BarChartParams,
    zoom: Float,
    density: Density,
    textMeasurer: TextMeasurer
) {
    val scaledBarThicknessPx = with(density) { barChart.barThickness.toPx() * zoom }
    if (scaledBarThicknessPx <= 0f || barParams.maxBarSize.value <= 0f || barChart.segments.isEmpty()) return
    val scaledBarSpacingPx = with(density) { barChart.barSpacing.toPx() * zoom }

    with(density) {
        val offsetXPx = barParams.offsetX.toPx()
        val offsetYPx = barParams.offsetY.toPx()
        val isVertical = barChart.orientation == BarChartOrientation.Vertical
        val stats = BarChartStatsCalculator.calculate(barChart.segments)
        val isStacked = barChart.type == BarChartType.Stacked

        translate(offsetXPx, offsetYPx) {
            if (isStacked) {
                val barSizePx = barParams.maxBarSize.toPx()
                val dimensions = StackedChartDimensions(
                    totalWidth = if (isVertical) scaledBarThicknessPx.toDp() else barParams.maxBarSize,
                    totalHeight = if (isVertical) barParams.maxBarSize else scaledBarThicknessPx.toDp(),
                    barSizePx = barSizePx,
                    barThicknessPx = scaledBarThicknessPx
                )
                val sortedSegments = SegmentSorter.sortForStacking(barChart.segments, isVertical)
                drawStackedBars(
                    segments = sortedSegments,
                    stats = stats,
                    appearance = barChart.appearance,
                    dimensions = dimensions,
                    isVertical = isVertical
                )
                drawStackedLabels(
                    segments = sortedSegments,
                    stats = stats,
                    dimensions = dimensions,
                    isVertical = isVertical,
                    density = this,
                    zoom = zoom,
                    textMeasurer = textMeasurer
                )
            } else {
                val dimensions = calculateDimensions(
                    density = this,
                    maxBarSize = barParams.maxBarSize,
                    barThickness = scaledBarThicknessPx.toDp(),
                    barSpacing = scaledBarSpacingPx.toDp(),
                    segmentCount = barChart.segments.size,
                    isVertical = isVertical
                )
                drawRegularBars(
                    segments = barChart.segments,
                    stats = stats,
                    appearance = barChart.appearance,
                    dimensions = dimensions,
                    isVertical = isVertical
                )
                drawRegularLabels(
                    segments = barChart.segments,
                    stats = stats,
                    dimensions = dimensions,
                    isVertical = isVertical,
                    zoom = zoom,
                    textMeasurer = textMeasurer
                )
            }
        }
    }
}

internal fun DrawScope.drawRegularBars(
    segments: List<BarChartSegmentData>,
    stats: BarChartStats,
    appearance: BarChartAppearance,
    dimensions: ChartDimensions,
    isVertical: Boolean
) {
    segments.forEachIndexed { index, segment ->
        val barMetrics = BarMetrics.calculate(segment, stats, index, dimensions)
        val cornerConfig = CornerRadiusCalculator.getRegularCornerRadiusConfig(
            appearance,
            dimensions.barThicknessPx,
            isVertical
        )
        if (isVertical) {
            BarDrawer.drawVerticalRegularBar(
                segment = segment,
                metrics = barMetrics,
                cornerConfig = cornerConfig,
                drawScope = this
            )
        } else {
            BarDrawer.drawHorizontalRegularBar(
                segment = segment,
                metrics = barMetrics,
                cornerConfig = cornerConfig,
                drawScope = this
            )
        }
    }
}

internal fun DrawScope.drawStackedBars(
    segments: List<BarChartSegmentData>,
    stats: BarChartStats,
    appearance: BarChartAppearance,
    dimensions: StackedChartDimensions,
    isVertical: Boolean
) {
    StackedBarDrawer.draw(
        segments = segments,
        stats = stats,
        appearance = appearance,
        barSizePx = dimensions.barSizePx,
        barThicknessPx = dimensions.barThicknessPx,
        isVertical = isVertical,
        drawScope = this
    )
}

@Suppress("MagicNumber")
internal fun DrawScope.drawRegularLabels(
    segments: List<BarChartSegmentData>,
    stats: BarChartStats,
    dimensions: ChartDimensions,
    isVertical: Boolean,
    zoom: Float,
    textMeasurer: TextMeasurer
) {
    segments.forEachIndexed { index, segment ->
        if (segment.labelPosition != null && segment.label != null) {

            val textLayoutResult = textMeasurer.measure(
                text = segment.label,
                style = TextStyle(
                    color = segment.labelColor,
                    fontSize = segment.labelSize * zoom,
                )
            )

            val labelHeight = textLayoutResult.size.height.toFloat()
            val labelWidth = textLayoutResult.size.width.toFloat()

            val labelSpacing = with(density) { segment.labelSpacing.toPx() }

            val barMetrics = BarMetrics.calculate(segment, stats, index, dimensions)
            val labelOffset = calculateLabelOffset(
                labelPosition = segment.labelPosition,
                metrics = barMetrics,
                dimensions = dimensions,
                isVertical = isVertical,
                labelHeightPx = labelHeight,
                labelWidthPx = labelWidth,
                labelSpacingPx = labelSpacing
            )

            withTransform({
                translate(
                    left = labelOffset.x,
                    top = labelOffset.y
                )
                rotate(
                    degrees = segment.labelRotation ?: 0f,
                    pivot = textLayoutResult.size.center.toOffset()
                )
            }) {
                drawText(
                    textLayoutResult
                )
            }
        }
    }
}

@Suppress("MagicNumber")
internal fun DrawScope.drawStackedLabels(
    segments: List<BarChartSegmentData>,
    stats: BarChartStats,
    dimensions: StackedChartDimensions,
    isVertical: Boolean,
    density: Density,
    zoom: Float,
    textMeasurer: TextMeasurer,
) {
    segments.forEachIndexed { index, segment ->
        if (segment.labelPosition != null && segment.label != null) {

            val textLayoutResult = textMeasurer.measure(
                text = segment.label,
                style = TextStyle(
                    color = segment.labelColor,
                    fontSize = segment.labelSize * zoom,
                )
            )

            val labelHeight = textLayoutResult.size.height.toFloat()
            val labelWidth = textLayoutResult.size.width.toFloat()

            val stackedMetrics = calculateStackedSegmentMetrics(
                segment = segment,
                segmentIndex = index,
                allSegments = segments,
                stats = stats,
                barSizePx = dimensions.barSizePx,
                barThicknessPx = dimensions.barThicknessPx,
                isVertical = isVertical
            )
            val labelOffset = calculateStackedLabelOffset(
                labelPosition = segment.labelPosition,
                metrics = stackedMetrics,
                labelSpacingPx = with(density) { segment.labelSpacing.toPx() },
                labelWidthPx = labelWidth,
                labelHeightPx = labelHeight
            )

            withTransform({
                translate(
                    left = labelOffset.x,
                    top = labelOffset.y
                )
                rotate(
                    degrees = segment.labelRotation ?: 0f,
                    pivot = textLayoutResult.size.center.toOffset()
                )
            }) {
                drawText(
                    textLayoutResult,
                )
            }
        }
    }
}

@Suppress("LongMethod")
internal fun DrawScope.drawRoundRectWithSpecificCorners(
    color: Color,
    topLeft: Offset,
    size: Size,
    cornerConfig: CornerRadiusConfig
) {
    val path = Path().apply {
        val rect = androidx.compose.ui.geometry.Rect(topLeft, size)
        moveTo(rect.left + cornerConfig.topLeft, rect.top)
        lineTo(rect.right - cornerConfig.topRight, rect.top)
        if (cornerConfig.topRight > 0f) {
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    rect.right - cornerConfig.topRight * 2,
                    rect.top,
                    rect.right,
                    rect.top + cornerConfig.topRight * 2
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        } else {
            lineTo(rect.right, rect.top)
        }
        lineTo(rect.right, rect.bottom - cornerConfig.bottomRight)
        if (cornerConfig.bottomRight > 0f) {
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    rect.right - cornerConfig.bottomRight * 2,
                    rect.bottom - cornerConfig.bottomRight * 2,
                    rect.right,
                    rect.bottom
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        } else {
            lineTo(rect.right, rect.bottom)
        }
        lineTo(rect.left + cornerConfig.bottomLeft, rect.bottom)
        if (cornerConfig.bottomLeft > 0f) {
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    rect.left,
                    rect.bottom - cornerConfig.bottomLeft * 2,
                    rect.left + cornerConfig.bottomLeft * 2,
                    rect.bottom
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        } else {
            lineTo(rect.left, rect.bottom)
        }
        lineTo(rect.left, rect.top + cornerConfig.topLeft)
        if (cornerConfig.topLeft > 0f) {
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    rect.left,
                    rect.top,
                    rect.left + cornerConfig.topLeft * 2,
                    rect.top + cornerConfig.topLeft * 2
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        } else {
            lineTo(rect.left, rect.top)
        }
        close()
    }
    drawPath(path, color)
}
