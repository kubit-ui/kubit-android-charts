package com.kubit.charts.components.chart.piechart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.kubit.charts.components.chart.piechart.model.Pie
import com.kubit.charts.components.chart.piechart.model.PieSectionData
import org.jetbrains.annotations.ApiStatus.Experimental
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * Displays a pie chart based on the provided [Pie] data and configuration.
 *
 * This composable draws a pie chart with customizable sections, alignment, spacing, and selection handling.
 * Supports accessibility and custom content in the center of the chart.
 *
 * ```kotlin
 * val pie = Pie(sections = listOf(
 *     PieSectionData(value = 40f, style = PieSectionStyle(Color.Red, Color.DarkRed)),
 *     PieSectionData(value = 60f, style = PieSectionStyle(Color.Blue, Color.DarkBlue))
 * ))
 * PieChart(
 *     pie = pie,
 *     radius = 100.dp,
 *     backgroundColor = Color.White
 * )
 * ```
 * @param pie The data for the pie chart, containing sections and their styles.
 * @param modifier Modifier to be applied to the chart.
 * @param radius The radius of the pie chart. If set to [Dp.Hairline], the chart uses the lesser of the canvas width and height.
 * @param verticalAlignment Vertical alignment of the pie chart within its container.
 * @param horizontalAlignment Horizontal alignment of the pie chart within its container.
 * @param sectionWidth The width of the sections. If [Dp.Hairline], the full circle radius is used.
 * @param startAngle The starting angle for the pie chart sections.
 * @param endAngle The ending angle for the pie chart sections.
 * @param direction The direction in which the sections are drawn (clockwise or counter-clockwise).
 * @param backgroundColor Background color of the chart.
 * @param sectionsSpacing Spacing between sections. If [Dp.Hairline], no spacing is applied.
 * @param expandToFill If true, the chart expands to fill the available space.
 * @param contentDescription Optional content description for accessibility.
 * @param onPointSelect Callback invoked when a section is selected, providing the [PieSectionData] and selection [Offset]. If null, selection is disabled.
 * @param content Optional composable content to display in the center of the chart.
 */
@Experimental
@Suppress("LongMethod")
@Composable
fun PieChart(
    pie: Pie,
    modifier: Modifier = Modifier,
    radius: Dp = Dp.Hairline,
    verticalAlignment: PieChartVerticalAlignment = PieChartVerticalAlignment.Center,
    horizontalAlignment: PieChartHorizontalAlignment = PieChartHorizontalAlignment.Center,
    sectionWidth: Dp = Dp.Hairline,
    startAngle: Float = 0f,
    endAngle: Float = 360f,
    direction: PieChartDirection = PieChartDirection.CounterClockwise,
    backgroundColor: Color = Color.White,
    sectionsSpacing: Dp = Dp.Hairline,
    expandToFill: Boolean = false,
    contentDescription: String? = null,
    onPointSelect: ((PieSectionData, Offset) -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
) {
    val normalizedPie = remember(pie) { pie.normalizeData() }
    var selectedOffset by remember { mutableStateOf<Offset?>(null) }
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    var pieSize by remember(pie) { mutableStateOf<Size?>(null) }

    Box(
        modifier = modifier
            .clipToBounds()
            .semantics {
                contentDescription?.let {
                    this.contentDescription = it
                }
            }
    ) {
        CanvasContainer(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .background(backgroundColor)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        selectedOffset = offset
                    }
                },
            onDraw = {
                drawPieChart(
                    normalizedPie = normalizedPie,
                    radius = if (pieSize == null) {
                        radius
                    } else {
                        with(density) { pieSize?.let { (it.maxDimension / 2f).toDp() } ?: radius }
                    },
                    verticalAlignment = verticalAlignment,
                    horizontalAlignment = horizontalAlignment,
                    sectionWidth = sectionWidth,
                    startAngle = startAngle,
                    endAngle = endAngle,
                    direction = direction,
                    sectionsSpacing = sectionsSpacing,
                    selectedOffset = selectedOffset,
                    onPointSelect = onPointSelect,
                    textMeasurer = textMeasurer,
                    expandToFill = expandToFill,
                    onPieSizeCalculated = {
                        if (pieSize == null) {
                            pieSize = it
                        }
                    },
                )
            }
        )

        Box(modifier = Modifier.align(Alignment.Center)) {
            content?.invoke()
        }
    }
}

@Suppress("LongMethod", "MagicNumber", "CyclomaticComplexMethod")
private fun DrawScope.drawPieChart(
    normalizedPie: Pie,
    radius: Dp,
    verticalAlignment: PieChartVerticalAlignment,
    horizontalAlignment: PieChartHorizontalAlignment,
    sectionWidth: Dp,
    startAngle: Float,
    endAngle: Float,
    direction: PieChartDirection,
    sectionsSpacing: Dp,
    selectedOffset: Offset?,
    onPointSelect: ((PieSectionData, Offset) -> Unit)?,
    textMeasurer: TextMeasurer,
    expandToFill: Boolean,
    onPieSizeCalculated: (Size) -> Unit,
) {
    val canvasWidth = size.width
    val canvasHeight = size.height

    // Determine pie chart size (width and height)
    // If radius is Dp.Hairline (by default), then pie chart will take the lesser of
    // canvas width and height.
    val pieWidth = if (radius == Dp.Hairline) {
        if (canvasWidth > canvasHeight) {
            canvasHeight
        } else {
            canvasWidth
        } + sectionsSpacing.div(2).toPx()
    } else {
        (radius * 2).toPx()
    }

    val pieHeight = if (radius == Dp.Hairline) {
        if (canvasWidth > canvasHeight) {
            canvasHeight
        } else {
            canvasWidth
        } + sectionsSpacing.div(2).toPx()
    } else {
        (radius * 2).toPx()
    }

    // Get the size of the portion of the pie chart that will be drawn to be able to align it
    // This method returns a rectangle with height and width in the range [0, 1]
    val sectorRect = getSectorRect(startAngle = startAngle, endAngle = endAngle)

    if (expandToFill && radius == Dp.Hairline) {
        val realPieWidth = pieWidth * sectorRect.width
        val realPieHeight = pieHeight * sectorRect.height

        val widthDelta = canvasWidth - realPieWidth
        val heightDelta = canvasHeight - realPieHeight

        val maxDelta = max(widthDelta, heightDelta)
        val minDelta = min(widthDelta, heightDelta)

        if (realPieWidth + maxDelta <= canvasWidth && realPieHeight + maxDelta <= canvasHeight) {
            // If the pie chart can fit in the canvas, then we use the maximum delta
            // to ensure that the pie chart is centered
            onPieSizeCalculated(Size(realPieWidth + maxDelta, realPieHeight + maxDelta))
        } else if (realPieWidth + minDelta <= canvasWidth && realPieHeight + minDelta <= canvasHeight) {
            // If the pie chart can fit in the canvas, then we use the minimum delta
            // to ensure that the pie chart is centered
            onPieSizeCalculated(Size(realPieWidth + minDelta, realPieHeight + minDelta))
        }
    }

    // Initial angle processing for drawing the chart
    var currentStartAngle = -startAngle
    val directionMultiplier =
        if (direction == PieChartDirection.CounterClockwise) -1f else 1f

    // This is against any intuition
    // topHeight is the height of the BOTTOM part of the pie chart
    // bottomHeight is the height of the TOP part of the pie chart
    // This is because the coordinate system of the canvas starts from the top left corner
    // Why don't you change the names, bastard? because Rect is used in getSectorRect function
    // and it is used in the same way. I prefer to keep the naming consistency to avoid confusion.

    // This piece of code is used to align the pie chart using the desired alignment vertically
    val topHeight = sectorRect.top
    val bottomHeight = 1 - sectorRect.bottom
    val deltaHeight = (abs(topHeight - bottomHeight) / 2f) * pieHeight

    val pieY = when (verticalAlignment) {
        PieChartVerticalAlignment.Center -> (canvasHeight - pieHeight) / 2 + if (topHeight < bottomHeight) {
            -deltaHeight
        } else {
            deltaHeight
        }

        PieChartVerticalAlignment.Top -> -bottomHeight * pieHeight - sectionsSpacing.div(4).toPx()
        PieChartVerticalAlignment.Bottom -> canvasHeight - pieHeight + topHeight * pieHeight + sectionsSpacing.div(
            4
        ).toPx()
    }

    // This piece of code is used to align the pie chart using the desired alignment horizontally
    val leftWidth = if (sectorRect.left >= 0f) sectorRect.left else 0f
    val rightWidth = 1 - sectorRect.right
    val deltaWidth = (abs(rightWidth - leftWidth) / 2f) * pieWidth

    val realWidthDelta = if (leftWidth < rightWidth) {
        deltaWidth
    } else {
        -deltaWidth
    }

    val pieX = when (horizontalAlignment) {
        PieChartHorizontalAlignment.Center -> (canvasWidth - pieWidth) / 2 + realWidthDelta
        PieChartHorizontalAlignment.Left -> -leftWidth * pieWidth - sectionsSpacing.div(4).toPx()
        PieChartHorizontalAlignment.Right -> canvasWidth - pieWidth + rightWidth * pieWidth + sectionsSpacing.div(
            4
        ).toPx()
    }

    // Selection
    val selectedOffsetVector = (selectedOffset ?: Offset.Infinite) - Offset(
        x = pieX + pieWidth / 2f,
        y = pieY + pieHeight / 2f
    )
    val selectedOffsetRadius = selectedOffsetVector.getDistanceSquared()
    var vectorAngle = atan2(
        y = selectedOffsetVector.y,
        x = selectedOffsetVector.x
    ) * 180f / PI.toFloat()

    vectorAngle = if (vectorAngle < 0f) {
        abs(vectorAngle)
    } else {
        360f - abs(vectorAngle)
    }

    val pieRadiusSquared = (pieWidth / 2f) * (pieWidth / 2f)

    val pieSectionMiddleAngles = mutableMapOf<PieSectionData, Float>()

    // Sector drawing.
    normalizedPie.sections.forEach { pieSectionData ->
        val range = endAngle - startAngle
        val currentAngleRange = (pieSectionData.normalizedValue
            ?: pieSectionData.value) * range * directionMultiplier

        val selectionStartAngle: Float
        val selectionEndAngle: Float
        var sectorCenterAngle = 0f

        if (direction == PieChartDirection.CounterClockwise) {
            selectionStartAngle = abs(currentStartAngle)
        } else {
            selectionStartAngle = (360f - currentStartAngle) - currentAngleRange
            sectorCenterAngle = (360f - currentStartAngle) - currentAngleRange / 2f
        }

        if (direction == PieChartDirection.CounterClockwise) {
            selectionEndAngle = abs(currentStartAngle + currentAngleRange)
            sectorCenterAngle = abs(currentStartAngle + currentAngleRange / 2f)
        } else {
            selectionEndAngle = 360f - currentStartAngle
        }

        pieSectionMiddleAngles[pieSectionData] = sectorCenterAngle

        val sectorSelected =
            selectedOffsetRadius <= pieRadiusSquared && vectorAngle in selectionStartAngle..selectionEndAngle

        if (sectorSelected) {
            onPointSelect?.invoke(pieSectionData, selectedOffset ?: Offset.Unspecified)
        }

        val sectorColor =
            if (sectorSelected) {
                pieSectionData.style.selectedSectorColor
            } else {
                pieSectionData.style.sectorColor
            }

        drawArc(
            color = sectorColor,
            startAngle = currentStartAngle,
            sweepAngle = currentAngleRange,
            useCenter = true,
            size = Size(pieWidth, pieHeight),
            topLeft = Offset(
                x = pieX,
                y = pieY
            ),
        )

        if (sectionsSpacing != Dp.Hairline) {
            drawArc(
                color = Color.Transparent,
                startAngle = currentStartAngle,
                sweepAngle = currentAngleRange,
                useCenter = true,
                size = Size(pieWidth, pieHeight),
                topLeft = Offset(
                    x = pieX,
                    y = pieY
                ),
                style = Stroke(width = sectionsSpacing.div(2).toPx()),
                blendMode = BlendMode.Clear
            )
        }

        currentStartAngle += currentAngleRange
    }

    normalizedPie.sections.forEach { pieSectionData ->
        pieSectionData.label?.let {
            val selectionMiddleAngle = pieSectionMiddleAngles[pieSectionData] ?: 0f
            val sectorCenter = Offset(
                x = pieX + pieWidth / 2f,
                y = pieY + pieHeight / 2f
            ) + Offset(
                x = (pieWidth / 4f) * cos(Math.toRadians(selectionMiddleAngle.toDouble())).toFloat(),
                y = -(pieHeight / 4f) * sin(Math.toRadians(selectionMiddleAngle.toDouble())).toFloat()
            )

            val result = textMeasurer.measure(pieSectionData.label)

            withTransform({
                rotate(
                    if (pieSectionData.labelAutoRotation) {
                        -selectionMiddleAngle
                    } else {
                        pieSectionData.labelCustomRotation
                    },
                    pivot = sectorCenter + Offset(
                        x = -result.size.width / 2f,
                        y = -result.size.height / 2f
                    )
                )
            }) {
                drawText(
                    textLayoutResult = result,
                    color = pieSectionData.style.labelColor,
                    topLeft = sectorCenter + Offset(
                        -result.size.width / 2f,
                        -result.size.height / 2f
                    )
                )
            }
        }
    }

    // This is to draw the cut version of the pie chart (without the center circle)
    if (sectionWidth != Dp.Hairline) {
        drawCircle(
            color = Color.Transparent,
            radius = (pieWidth - sectionWidth.toPx()) / 2,
            center = Offset(
                x = pieX + pieWidth / 2f,
                y = pieY + pieHeight / 2f,
            ),
            style = Fill,
            blendMode = BlendMode.Clear
        )
    }
}

/**
 * This function is a helper class to calculate the rectangle that contains the pie chart sector
 *
 * It returns a rectangle with height and width in the range [0, 1]
 */
@Suppress("MagicNumber")
private fun getSectorRect(startAngle: Float, endAngle: Float): Rect {

    fun isInInterval(theta: Float, startAngle: Float, endAngle: Float): Boolean {
        val i = if (startAngle > 360f) startAngle % 360f else startAngle
        val f = if (endAngle > 360f) endAngle % 360f else endAngle

        return if (i < f) {
            theta in i..f
        } else {
            !(theta < i && theta > f)
        }
    }

    fun getX(theta: Float): Float {
        return ((1 + cos(theta * 2 * PI / 360)) / 2.0).toFloat()
    }

    fun getY(theta: Float): Float {
        return ((1 + sin(theta * 2 * PI / 360)) / 2.0).toFloat()
    }

    val xi = getX(theta = startAngle)
    val yi = getY(theta = startAngle)
    val xf = getX(theta = endAngle)
    val yf = getY(theta = endAngle)

    val is0 = isInInterval(theta = 0f, startAngle = startAngle, endAngle = endAngle)
    val is90 = isInInterval(theta = 90f, startAngle = startAngle, endAngle = endAngle)
    val is180 = isInInterval(theta = 180f, startAngle = startAngle, endAngle = endAngle)
    val is270 = isInInterval(theta = 270f, startAngle = startAngle, endAngle = endAngle)

    val right = if (is0) 1f else max(xi, xf)
    val left = if (is180) 0f else min(xi, xf)
    val bottom = if (is90) 1f else max(yi, yf)
    val top = if (is270) 0f else min(yi, yf)

    return Rect(
        left = left,
        top = top,
        right = right,
        bottom = bottom
    )
}

@Composable
private fun CanvasContainer(
    onDraw: DrawScope.() -> Unit,
    modifier: Modifier = Modifier,
    containerBackgroundColor: Color = Color.White,
    layoutDirection: LayoutDirection? = null
) {
    val direction = layoutDirection ?: LocalLayoutDirection.current

    CompositionLocalProvider(LocalLayoutDirection provides direction) {
        Box(
            modifier = modifier.clipToBounds()
        ) {
            Canvas(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(containerBackgroundColor),
                onDraw = onDraw
            )
        }
    }
}

/**
 * Specifies the direction in which the pie chart sections are drawn.
 *
 * - [Clockwise]: Sections are drawn in a clockwise direction.
 * - [CounterClockwise]: Sections are drawn in a counter-clockwise direction.
 */
@Stable
enum class PieChartDirection {
    Clockwise,
    CounterClockwise
}

/**
 * Specifies the vertical alignment options for the pie chart within its container.
 *
 * - [Center]: Vertically centers the pie chart.
 * - [Top]: Aligns the pie chart to the top.
 * - [Bottom]: Aligns the pie chart to the bottom.
 */
@Stable
enum class PieChartVerticalAlignment {
    Center,
    Top,
    Bottom
}

/**
 * Specifies the horizontal alignment options for the pie chart within its container.
 *
 * - [Center]: Horizontally centers the pie chart.
 * - [Left]: Aligns the pie chart to the left.
 * - [Right]: Aligns the pie chart to the right.
 *
 * @sample
 * ```kotlin
 * val alignment = PieChartHorizontalAlignment.Right
 * ```
 */
@Stable
enum class PieChartHorizontalAlignment {
    Center,
    Left,
    Right
}
