package com.kubit.charts.components.chart.linechart

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.axis.model.AxisData
import com.kubit.charts.components.axis.model.axisBuilder
import com.kubit.charts.components.chart.common.extensions.drawHighlightPoints
import com.kubit.charts.components.chart.common.extensions.drawIntersectionPoints
import com.kubit.charts.components.chart.common.utils.getLineOrCubicPath
import com.kubit.charts.components.chart.linechart.extensions.drawLineChart
import com.kubit.charts.components.chart.linechart.extensions.drawShadowUnderLine
import com.kubit.charts.components.chart.linechart.model.IntersectionComposable
import com.kubit.charts.components.chart.linechart.model.Line
import com.kubit.charts.components.chart.linechart.model.LineStyle
import com.kubit.charts.components.chart.linechart.model.LineType
import com.kubit.charts.components.chart.linechart.model.Point
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * Displays a line chart with one or more lines, supporting selection, zoom, and accessibility.
 *
 * This composable renders a line chart using the provided lines and axis data. It supports selection of points,
 * zooming, scrolling, and accessibility features. Each line can have custom styles, highlight logic, and intersection nodes.
 *
 * ```kotlin
 * val lines = persistentListOf(
 *     Line(
 *         dataPoints = listOf(Point(0f, 1f), Point(1f, 2f), Point(2f, 1.5f)),
 *         lineStyle = LineStyle(color = Color.Blue)
 *     )
 * )
 * LineChart(
 *     lines = lines,
 *     xAxisData = AxisData(...),
 *     yAxisData = AxisData(...),
 *     xAxisStepSize = 32.dp
 * )
 * ```
 *
 * @param lines Immutable list of lines to be drawn on the chart.
 * @param xAxisData Data for the X-Axis.
 * @param yAxisData Data for the Y-Axis.
 * @param xAxisStepSize Step size for the X-Axis (distance between points).
 * @param modifier Modifier to be applied to the chart container.
 * @param yAxisStepSize Step size for the Y-Axis (distance between points). Defaults to xAxisStepSize.
 * @param backgroundColor Background color of the chart.
 * @param horizontalScroll Horizontal scroll offset in Dp.
 * @param verticalScroll Vertical scroll offset in Dp.
 * @param zoom Zoom factor for the chart. 1f means no zoom.
 * @param onPointSelect Callback invoked when a point is selected. Provides the selected [Point] and its [Offset] in canvas coordinates. If null, selection is disabled.
 * @param xMin Minimum value for the X-Axis. If null, calculated automatically from xAxisData.
 * @param yMin Minimum value for the Y-Axis. If null, calculated automatically from yAxisData.
 * @param onHorizontalScrollChangeRequest Callback invoked when a scroll change is requested (e.g., via accessibility or keyboard).
 * @param onVerticalScrollChangeRequest Callback invoked when a scroll change is requested (e.g., via accessibility or keyboard).
 *
 * @sample com.kubit.charts.samples.components.linechart.LineChartWithPointsSample
 */

@SuppressLint("UnusedBoxWithConstraintsScope")
@Suppress("LongMethod", "CyclomaticComplexMethod")
@Composable
fun LineChart(
    lines: ImmutableList<Line>,
    xAxisData: AxisData,
    yAxisData: AxisData,
    xAxisStepSize: Dp,
    modifier: Modifier = Modifier,
    yAxisStepSize: Dp = xAxisStepSize,
    backgroundColor: Color = Color.White,
    horizontalScroll: Dp = 0.dp,
    verticalScroll: Dp = 0.dp,
    zoom: Float = 1f,
    onPointSelect: ((Point, Offset) -> Unit)? = null,
    xMin: Float? = null,
    yMin: Float? = null,
    onHorizontalScrollChangeRequest: ((Float) -> Unit)? = null,
    onVerticalScrollChangeRequest: ((Float) -> Unit)? = null,
) {
    var xOffset by remember { mutableFloatStateOf(0f) }
    var selectedRawPoint by remember { mutableStateOf<Point?>(null) }
    var selectedRawOffset by remember { mutableStateOf<Offset?>(null) }
    var baseZoomApplied by remember { mutableFloatStateOf(1f) }

    var canvasWidth by remember { mutableFloatStateOf(0f) }
    var canvasHeight by remember { mutableFloatStateOf(0f) }

    val density = LocalDensity.current

    val xInternalMin = xMin ?: xAxisData.range.start
    val yInternalMin = yMin ?: yAxisData.range.start

    var userSelectedOffset by remember { mutableStateOf<Offset?>(null) }
    var selectedOffset by remember { mutableStateOf<Offset?>(null) }
    var zoomedSelectedOffset by remember { mutableStateOf<Offset?>(null) }

    var horizontalScrollInPxForSelection by remember { mutableFloatStateOf(0f) }

    val selectionEnabled = onPointSelect != null

    // This stores selectedPoint using the current horizontalScroll so that if the user change the scroll
    // the point remains the same place and it is correctly displayed (and it doesn't move itself with
    // the scroll).
    LaunchedEffect(userSelectedOffset) {
        horizontalScrollInPxForSelection = with(density) { horizontalScroll.toPx() }
        selectedOffset = userSelectedOffset?.let {
            Offset(
                it.x + horizontalScrollInPxForSelection,
                it.y
            )
        }
        zoomedSelectedOffset = null
        baseZoomApplied = zoom
    }

    // This is to position the selected point correctly when zoom is changing.
    LaunchedEffect(zoom) {
        zoomedSelectedOffset = userSelectedOffset?.let {
            Offset(
                (it.x + horizontalScrollInPxForSelection) * (zoom / baseZoomApplied),
                it.y,
            )
        }
    }

    // This is used to call onPointSelect once.
    LaunchedEffect(selectedRawPoint, selectedRawOffset) {
        if (selectionEnabled && selectedRawPoint != null && selectedRawOffset != null) {
            onPointSelect?.let {
                it(selectedRawPoint!!, selectedRawOffset!!)
            }
        }
    }

    BoxWithConstraints(modifier = modifier) {
        val preRenderLinesData = with(density) {
            canvasWidth = constraints.maxWidth.toFloat()
            canvasHeight = constraints.maxHeight.toFloat()

            val yBottom = canvasHeight
            val yOffset = yAxisStepSize.toPx() * zoom
            xOffset = xAxisStepSize.toPx() * zoom

            lines.map { line ->
                val pointsData = getMappingPointsToGraph(
                    line.dataPoints,
                    xInternalMin,
                    xOffset,
                    horizontalScroll.toPx(),
                    yBottom,
                    yInternalMin,
                    yOffset,
                    verticalScroll.toPx()
                )

                val pointsDataWithoutScroll = getMappingPointsToGraph(
                    line.dataPoints,
                    xInternalMin,
                    xOffset,
                    0f,
                    yBottom,
                    yInternalMin,
                    yOffset,
                    0f
                )

                val (cubicPoints1, cubicPoints2) = getCubicPoints(pointsData)

                LineChartPreRenderData(
                    path = getLineOrCubicPath(
                        pointsData,
                        cubicPoints1,
                        cubicPoints2,
                        line.lineStyle
                    ),
                    pointsData = pointsData,
                    pointsDataWithoutScroll = pointsDataWithoutScroll,
                    line = line
                )
            }
        }.toImmutableList()

        CanvasContainer(
            modifier = Modifier
                .fillMaxSize(),
            containerBackgroundColor = backgroundColor,
            onDraw = {
                val yBottom = size.height

                preRenderLinesData.forEachIndexed { index, linePreRenderData ->
                    with(linePreRenderData) {
                        // Draw Lines and Points and AreaUnderLine
                        // Draw area under curve
                        drawShadowUnderLine(
                            path = path,
                            nextPath = if (index < preRenderLinesData.size - 1) preRenderLinesData[index + 1].path else null,
                            pointsData = pointsData,
                            nextPointsData = if (index < preRenderLinesData.size - 1) preRenderLinesData[index + 1].pointsData else null,
                            yBottom = yBottom,
                            line = line,
                        )

                        drawLineChart(path, line.lineStyle)

                        // Points which are part of the line
                        drawIntersectionPoints(pointsData)

                        // Selected points
                        if (selectionEnabled) {
                            drawHighlightPoints(
                                line,
                                pointsDataWithoutScroll,
                                pointsData,
                                zoomedSelectedOffset ?: selectedOffset,
                                yBottom,
                                xOffset,
                                selectedRawPoint ?: Point.Zero
                            )
                        }
                    }
                }
            }
        )

        ComposableIntersectionNodeLayer(
            preRenderData = preRenderLinesData,
            lines = lines,
        )

        if (selectionEnabled) {
            InputIntersectionNodeLayer(
                preRenderData = preRenderLinesData,
                lines = lines,
                onCenterOnPoint = { point ->
                    onHorizontalScrollChangeRequest?.invoke(point.x - canvasWidth / 2f)
                    onVerticalScrollChangeRequest?.invoke(canvasHeight / 2f - point.y)
                },
                onPointSelect = { point, offset ->
                    selectedRawPoint = if (selectedRawPoint == point) null else point
                    selectedRawOffset = if (selectedRawOffset == offset) null else offset
                    userSelectedOffset = if (userSelectedOffset == offset) null else offset
                }
            )
        }
    }
}

/**
 * Overloaded LineChart composable that calculates axis data automatically if not provided and also
 * calculates the step sizes based on the container size.
 *
 * Use this to force all data to fit inside the available space.
 *
 * For previous reasons, this version does not support zoom and scroll.
 *
 * @param lines Immutable list of lines to be drawn on the chart.
 * @param modifier Modifier to be applied to the chart container.
 * @param xAxisData Optional data for the X-Axis. If null, calculated automatically
 * @param yAxisData Optional data for the Y-Axis. If null, calculated automatically
 * @param backgroundColor Background color of the chart.
 * @param onPointSelect Callback invoked when a point is selected. Provides the selected [Point] and its [Offset] in canvas coordinates. If null, selection is disabled.
 * @param xMin Minimum value for the X-Axis. If null, calculated automatically from xAxisData.
 * @param yMin Minimum value for the Y-Axis. If null, calculated automatically from yAxisData.
 *
 * @sample com.kubit.charts.samples.components.linechart.LineChartWithPointsFitsInContainerSample
 */

@Composable
fun LineChart(
    lines: ImmutableList<Line>,
    modifier: Modifier = Modifier,
    xAxisData: AxisData? = null,
    yAxisData: AxisData? = null,
    backgroundColor: Color = Color.White,
    onPointSelect: ((Point, Offset) -> Unit)? = null,
    xMin: Float? = null,
    yMin: Float? = null,
) {

    val internalXAxisData = xAxisData ?: axisBuilder {
        val allXValues = lines.flatMap { line -> line.dataPoints.map { point -> point.x } }

        val minX = allXValues.minOrNull() ?: 0f
        val maxX = allXValues.maxOrNull() ?: 0f

        addNodes(listOf(minX, maxX))
    }

    val internalYAxisData = yAxisData ?: axisBuilder {
        val allYValues = lines.flatMap { line -> line.dataPoints.map { point -> point.y } }

        val minY = allYValues.minOrNull() ?: 0f
        val maxY = allYValues.maxOrNull() ?: 0f

        addNodes(listOf(minY, maxY))
    }

    BoxWithConstraints(modifier = modifier) {
        val height = constraints.maxHeight
        val width = constraints.maxWidth

        LineChart(
            lines = lines,
            xAxisData = internalXAxisData,
            yAxisData = internalYAxisData,
            xAxisStepSize = with(LocalDensity.current) { (width / internalXAxisData.rangeSize).toDp() },
            yAxisStepSize = with(LocalDensity.current) { (height / internalYAxisData.rangeSize).toDp() },
            backgroundColor = backgroundColor,
            onPointSelect = onPointSelect,
            xMin = xMin,
            yMin = yMin
        )
    }
}

/**
 * Renders composable intersection nodes for points in the chart.
 *
 * This layer draws custom composable nodes at intersection points for each line, using the [IntersectionComposable] node type.
 *
 * @param preRenderData Pre-rendered data for each line, including mapped points and paths.
 * @param lines Immutable list of lines to be drawn.
 */
@Composable
private fun ComposableIntersectionNodeLayer(
    preRenderData: ImmutableList<LineChartPreRenderData>,
    lines: ImmutableList<Line>
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clipToBounds()) {
        lines.forEachIndexed { lineIndex, line ->
            line.dataPoints.mapIndexed { pointIndex, point ->
                if (point.intersectionNode is IntersectionComposable) {
                    point.copy(
                        x = preRenderData[lineIndex].pointsData[pointIndex].x,
                        y = preRenderData[lineIndex].pointsData[pointIndex].y
                    )
                } else {
                    null
                }
            }.filterNotNull()
                .forEach { point ->
                    val composableNode = point.intersectionNode as IntersectionComposable

                    var composableWidth by remember {
                        mutableIntStateOf(0)
                    }

                    var composableHeight by remember {
                        mutableIntStateOf(0)
                    }

                    composableNode.composable(
                        Modifier
                            // TODO Change this to use subcomposelayout
                            .onGloballyPositioned {
                                composableHeight = it.size.height
                                composableWidth = it.size.width
                            }
                            .offset {
                                IntOffset(
                                    (point.offset.x - composableWidth / 2).toInt(),
                                    (point.offset.y - composableHeight / 2).toInt()
                                )
                            },
                        point
                    )
                }
        }
    }
}

/**
 * Handles input and selection for intersection nodes in the chart.
 *
 * This layer draws tappable areas for intersection nodes (except composable nodes), supporting selection and accessibility.
 *
 * @param preRenderData Pre-rendered data for each line, including mapped points and paths.
 * @param lines Immutable list of lines to be drawn.
 * @param onCenterOnPoint Callback invoked to center the chart on a selected point.
 * @param onPointSelect Callback invoked when a point is selected, providing the [Point] and its [Offset].
 */
@Suppress("LongMethod")
@Composable
private fun InputIntersectionNodeLayer(
    preRenderData: ImmutableList<LineChartPreRenderData>,
    lines: ImmutableList<Line>,
    onCenterOnPoint: (Point) -> Unit,
    onPointSelect: (Point, Offset) -> Unit,
) {
    var focusedIndex by remember { mutableIntStateOf(-1) }
    var selectedIndex by remember { mutableIntStateOf(-1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
    ) {
        val baseTappableAreaSize = 32.dp
        val baseTappableAreaSizePx = with(LocalDensity.current) { baseTappableAreaSize.toPx() }

        lines.forEachIndexed { lineIndex, line ->
            line.dataPoints.mapIndexed { pointIndex, point ->
                if (point.intersectionNode != null && point.intersectionNode !is IntersectionComposable) {
                    // (Point with scroll, point without scroll)
                    Pair(
                        point.copy(
                            x = preRenderData[lineIndex].pointsData[pointIndex].x,
                            y = preRenderData[lineIndex].pointsData[pointIndex].y
                        ),
                        point.copy(
                            x = preRenderData[lineIndex].pointsDataWithoutScroll[pointIndex].x,
                            y = preRenderData[lineIndex].pointsDataWithoutScroll[pointIndex].y
                        )
                    )
                } else {
                    null
                }
            }
                .filterNotNull()
                .forEachIndexed { index, pairOfPoints ->
                    val tappableAreaSizePx = baseTappableAreaSizePx // zoom
                    val tappableAreaSizeDp =
                        with(LocalDensity.current) { tappableAreaSizePx.toDp() } // zoom

                    Box(
                        modifier = Modifier
                            .size(tappableAreaSizeDp)
                            .offset {
                                IntOffset(
                                    (pairOfPoints.first.offset.x - tappableAreaSizePx / 2).toInt(),
                                    (pairOfPoints.first.offset.y - tappableAreaSizePx / 2).toInt()
                                )
                            }
                            .onFocusChanged {
                                if (it.hasFocus) {
                                    focusedIndex = index
                                    onCenterOnPoint.invoke(pairOfPoints.second)
                                }
                            }
                            .then(pairOfPoints.first.intersectionNode?.let { Modifier.clip(it.focus.focusShape) }
                                ?: Modifier)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = pairOfPoints.first.intersectionNode?.focus?.focusIndication
                                    ?: ripple()
                            ) {
                                selectedIndex = index

                                onPointSelect(
                                    line.dataPoints[index],
                                    pairOfPoints.first.offset
                                )
                            }
                            .semantics {
                                contentDescription =
                                    pairOfPoints.first.intersectionNode?.accessibility?.contentDescription
                                        ?: "${pairOfPoints.first.x},${pairOfPoints.first.y}"
                                selected = selectedIndex == index
                            }
                    ) {
                    }
                }
        }
    }
}

/**
 * Container composable for drawing the chart canvas with the specified background and layout direction.
 *
 * @param onDraw Lambda to perform custom drawing on the canvas.
 * @param modifier Modifier to be applied to the container.
 * @param containerBackgroundColor Background color of the canvas.
 */
@Composable
private fun CanvasContainer(
    onDraw: DrawScope.() -> Unit,
    modifier: Modifier = Modifier,
    containerBackgroundColor: Color = Color.White,
) {
    Box(
        modifier = modifier.clipToBounds()
    ) {
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxHeight()
                .fillMaxWidth()
                .semantics {
                    this.testTag = "chart_canvas"
                }
                .background(containerBackgroundColor),
            onDraw = onDraw
        )
    }
}

/**
 * Maps input points to their positions on the chart container, applying axis scaling and scroll offsets.
 *
 * @param lineChartPoints Input data points.
 * @param xMin Minimum X-Axis value.
 * @param xUnitSize Distance between two X-Axis points.
 * @param horizontalScroll Total horizontal scroll offset.
 * @param yBottom Bottom offset for the X-Axis.
 * @param yMin Minimum Y-Axis value.
 * @param yUnitSize Distance between two Y-Axis points.
 * @param verticalScroll Total vertical scroll offset.
 * @return List of [Point] mapped to chart coordinates.
 */
private fun getMappingPointsToGraph(
    lineChartPoints: List<Point>,
    xMin: Float,
    xUnitSize: Float,
    horizontalScroll: Float,
    yBottom: Float,
    yMin: Float,
    yUnitSize: Float,
    verticalScroll: Float,
): List<Point> =
    lineChartPoints.mapIndexed { _, point ->
        val x = point.x
        val y = point.y
        val xLess = x - xMin
        val yLess = y - yMin
        val x1 = xLess * xUnitSize - horizontalScroll
        val y1 = yBottom - yLess * yUnitSize + verticalScroll
        Point(x1, y1, point.intersectionNode)
    }

/**
 * Calculates cubic control points for smooth curves between points in the line chart.
 *
 * @param pointsData List of points on the line graph.
 * @return Pair of lists containing left and right control points for cubic curves.
 */
private fun getCubicPoints(pointsData: List<Point>): Pair<MutableList<Offset>, MutableList<Offset>> {
    val cubicPoints1 = mutableListOf<Offset>()
    val cubicPoints2 = mutableListOf<Offset>()

    for (i in 1 until pointsData.size) {
        cubicPoints1.add(
            Offset(
                (pointsData[i].x + pointsData[i - 1].x) / 2,
                pointsData[i - 1].y
            )
        )
        cubicPoints2.add(
            Offset(
                (pointsData[i].x + pointsData[i - 1].x) / 2,
                pointsData[i].y
            )
        )
    }
    return Pair(cubicPoints1, cubicPoints2)
}

/**
 * Returns the [DrawStyle] for the path based on the line type and style.
 *
 * @param lineType Type of the line ([LineType]).
 * @param lineStyle Style configuration for the line.
 * @return [DrawStyle] to be used for drawing the path.
 */
internal fun getDrawStyleForPath(
    lineType: LineType,
    lineStyle: LineStyle
): DrawStyle = if (lineType.isDotted) {
    Stroke(
        width = lineStyle.width,
        pathEffect = PathEffect.dashPathEffect(lineType.intervals)
    )
} else {
    lineStyle.style
}

/**
 * Returns true if the object is not null.
 */
internal fun Any?.isNotNull() = this != null

private data class LineChartPreRenderData(
    val path: Path,
    val pointsData: List<Point>,
    val pointsDataWithoutScroll: List<Point>,
    val line: Line
)
