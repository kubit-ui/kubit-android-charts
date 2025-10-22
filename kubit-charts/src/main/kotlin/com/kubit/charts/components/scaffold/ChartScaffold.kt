package com.kubit.charts.components.scaffold

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.axis.model.AxisData
import com.kubit.charts.components.axis.model.AxisPadding
import com.kubit.charts.components.utils.checkAndGetMaxHorizontalScroll
import com.kubit.charts.components.utils.checkAndGetMaxVerticalScroll
import org.jetbrains.annotations.ApiStatus.Experimental
import kotlin.math.abs

/**
 * Provides a scaffold for drawing interactive charts with scroll and zoom support.
 *
 * Example:
 * ```kotlin
 * ChartScaffold(
 *   xAxisData = xAxis,
 *   yAxisData = yAxis,
 *   xUnitSize = 32.dp,
 *   axisPadding = AxisPadding(),
 *   scrollOrientation = ChartScrollOrientation.Both,
 *   isPinchZoomEnabled = true,
 *   accessibility = ScrollableAccessibilityItem(contentDescription = "Chart"),
 *   horizontalAxis = { scroll, zoom -> /* Draw horizontal axis */ },
 *   verticalAxis = { scroll, zoom -> /* Draw vertical axis */ },
 *   content = { data -> /* Draw chart content using data */ }
 * )
 * ```
 *
 * @param xAxisData Data for the X-Axis ([AxisData]).
 * @param yAxisData Data for the Y-Axis ([AxisData]).
 * @param xUnitSize Size of a unit in the X-Axis in Dp.
 * @param modifier Modifier for the scaffold container.
 * @param axisPadding Padding for the axes ([AxisPadding]), used to calculate max scroll.
 * @param yUnitSize Size of a unit in the Y-Axis in Dp. Default is xUnitSize.
 * @param scrollOrientation Orientation of the scroll ([ChartScrollOrientation]). Default is Both.
 * @param isPinchZoomEnabled Enables pinch zoom gestures if true. Default is false.
 * @param accessibility Accessibility configuration ([ScrollableAccessibilityItem]) for a11y support.
 * @param horizontalAxis Composable for the horizontal axis. Receives scroll and zoom as parameters.
 * @param verticalAxis Composable for the vertical axis. Receives scroll and zoom as parameters.
 * @param content Main content of the scaffold, usually a chart. Receives [ChartScaffoldContentData].
 */
@Experimental
@Suppress("LongMethod")
@Composable
fun ChartScaffold(
    xAxisData: AxisData,
    yAxisData: AxisData,
    xUnitSize: Dp,
    modifier: Modifier = Modifier,
    axisPadding: AxisPadding = AxisPadding(),
    yUnitSize: Dp = xUnitSize,
    scrollOrientation: ChartScrollOrientation = ChartScrollOrientation.Both,
    isPinchZoomEnabled: Boolean = false,
    accessibility: ScrollableAccessibilityItem? = null,
    horizontalAxis: @Composable (scroll: Dp, zoom: Float, padding: AxisPadding) -> Unit = { _, _, _ -> },
    verticalAxis: @Composable (scroll: Dp, zoom: Float, padding: AxisPadding) -> Unit = { _, _, _ -> },
    content: @Composable (ChartScaffoldContentData) -> Unit = { _ -> },
) {
    var horizontalScroll by remember { mutableFloatStateOf(0f) }
    var maxHorizontalScroll by remember { mutableFloatStateOf(0f) }
    var verticalScroll by remember { mutableFloatStateOf(0f) }
    var maxVerticalScroll by remember { mutableFloatStateOf(0f) }
    var zoom by remember { mutableFloatStateOf(1f) }
    val density = LocalDensity.current

    fun updateHorizontalScroll(hScroll: Float) {
        horizontalScroll = checkAndGetMaxHorizontalScroll(
            hScroll,
            maxHorizontalScroll
        )
    }

    fun updateVerticalScroll(vScroll: Float) {
        verticalScroll = checkAndGetMaxVerticalScroll(
            vScroll,
            maxVerticalScroll
        )
    }

    val contentData = remember(
        horizontalScroll,
        verticalScroll,
        zoom,
        ::updateVerticalScroll,
        ::updateHorizontalScroll
    ) {
        with(density) {
            ChartScaffoldContentData(
                horizontalScroll = horizontalScroll.toDp(),
                verticalScroll = verticalScroll.toDp(),
                onHorizontalScrollChangeRequest = ::updateHorizontalScroll,
                onVerticalScrollChangeRequest = ::updateVerticalScroll,
                zoom = zoom
            )
        }
    }

    with(density) {
        BoxWithConstraints {
            val width = constraints.maxWidth
            val height = constraints.maxHeight

            maxHorizontalScroll = getMaxHorizontalScrollDistance(
                xMin = xAxisData.range.start,
                xMax = xAxisData.range.endInclusive,
                unitSize = xUnitSize.toPx() * zoom,
                canvasWidth = width.toFloat(),
                axisStartPadding = axisPadding.start.toPx(),
                axisEndPadding = axisPadding.end.toPx(),
            )

            maxVerticalScroll = getMaxVerticalScrollDistance(
                yMin = yAxisData.range.start,
                yMax = yAxisData.range.endInclusive,
                unitSize = yUnitSize.toPx() * zoom,
                canvasHeight = height.toFloat(),
                axisBottomPadding = axisPadding.bottom.toPx(),
                axisTopPadding = axisPadding.top.toPx(),
            )

            Box(
                modifier = modifier
                    .detectScrollGesture(
                        scrollOrientation = scrollOrientation,
                        onVerticalScroll = {
                            verticalScroll += it
                            verticalScroll = checkAndGetMaxVerticalScroll(
                                verticalScroll + it,
                                maxVerticalScroll
                            )
                        },
                        onHorizontalScroll = {
                            updateHorizontalScroll(horizontalScroll - it)
                        }
                    )
                    .pointerInput(Unit) {
                        detectZoomGesture(
                            isZoomAllowed = isPinchZoomEnabled,
                            onZoom = { z ->
                                zoom *= z

                                horizontalScroll = checkAndGetMaxHorizontalScroll(
                                    horizontalScroll,
                                    maxHorizontalScroll
                                )

                                verticalScroll = checkAndGetMaxVerticalScroll(
                                    verticalScroll,
                                    maxVerticalScroll
                                )
                            }
                        )
                    }
                    .semantics {
                        accessibility?.let { a11y ->
                            this@semantics.contentDescription = a11y.contentDescription

                            customActions = buildList {
                                a11y.leftCustomAction?.let {
                                    add(CustomAccessibilityAction(it) {
                                        horizontalScroll = checkAndGetMaxHorizontalScroll(
                                            horizontalScroll - a11y.scrollStepX.toPx(),
                                            maxHorizontalScroll
                                        )
                                        true
                                    })
                                }
                                a11y.rightCustomAction?.let {
                                    add(CustomAccessibilityAction(it) {
                                        horizontalScroll = checkAndGetMaxHorizontalScroll(
                                            horizontalScroll + a11y.scrollStepX.toPx(),
                                            maxHorizontalScroll
                                        )
                                        true
                                    })
                                }
                                a11y.upCustomAction?.let {
                                    add(CustomAccessibilityAction(it) {
                                        verticalScroll = checkAndGetMaxVerticalScroll(
                                            verticalScroll + a11y.scrollStepY.toPx(),
                                            maxVerticalScroll
                                        )
                                        true
                                    })
                                }
                                a11y.downCustomAction?.let {
                                    add(CustomAccessibilityAction(it) {
                                        verticalScroll = checkAndGetMaxVerticalScroll(
                                            verticalScroll - a11y.scrollStepY.toPx(),
                                            maxVerticalScroll
                                        )
                                        true
                                    })
                                }

                                if (isPinchZoomEnabled) {
                                    a11y.zoomInCustomAction?.let {
                                        add(CustomAccessibilityAction(it) {
                                            zoom += a11y.zoomStep
                                            true
                                        })
                                    }
                                    a11y.zoomOutCustomAction?.let {
                                        add(CustomAccessibilityAction(it) {
                                            zoom -= a11y.zoomStep
                                            true
                                        })
                                    }
                                }
                            }
                        }
                    }
            ) {
                horizontalAxis(
                    horizontalScroll.toDp(),
                    zoom,
                    AxisPadding(start = axisPadding.start, end = axisPadding.end)
                )
                verticalAxis(
                    verticalScroll.toDp(),
                    zoom,
                    AxisPadding(top = axisPadding.top, bottom = axisPadding.bottom)
                )
                Box(
                    modifier = Modifier.padding(
                        start = axisPadding.start,
                        bottom = axisPadding.bottom,
                        end = axisPadding.end,
                        top = axisPadding.top
                    )
                ) {
                    content(contentData)
                }
            }
        }
    }
}

/**
 * Simplified version of a scaffold for drawing interactive charts without scroll or zoom support.
 *
 * Example:
 * ```kotlin
 * ChartScaffold(
 *   xAxisData = xAxis,
 *   yAxisData = yAxis,
 *   axisPadding = AxisPadding(),
 *   horizontalAxis = { /* Draw horizontal axis */ },
 *   verticalAxis = { /* Draw vertical axis */ },
 *   content = { /* Draw chart content using data */ }
 * )
 * ```
 *
 * @param xAxisData Data for the X-Axis ([AxisData]).
 * @param yAxisData Data for the Y-Axis ([AxisData]).
 * @param modifier Modifier for the scaffold container.
 * @param axisPadding Padding for the axes ([AxisPadding]), used to calculate max scroll.
 * @param horizontalAxis Composable for the horizontal axis. Receives scroll and zoom as parameters.
 * @param verticalAxis Composable for the vertical axis. Receives scroll and zoom as parameters.
 * @param content Main content of the scaffold, usually a chart. Receives [ChartScaffoldContentData].
 */
@Experimental
@Suppress("LongMethod")
@Composable
fun ChartScaffold(
    xAxisData: AxisData,
    yAxisData: AxisData,
    modifier: Modifier = Modifier,
    axisPadding: AxisPadding = AxisPadding(),
    horizontalAxis: @Composable (padding: AxisPadding) -> Unit = { _ -> },
    verticalAxis: @Composable (padding: AxisPadding) -> Unit = { _ -> },
    content: @Composable () -> Unit = { },
) {
    Box(modifier = modifier) {
        horizontalAxis(AxisPadding(start = axisPadding.start, end = axisPadding.end))
        verticalAxis(AxisPadding(top = axisPadding.top, bottom = axisPadding.bottom))
        Box(
            modifier = Modifier.padding(
                start = axisPadding.start,
                bottom = axisPadding.bottom,
                end = axisPadding.end,
                top = axisPadding.top
            )
        ) {
            content()
        }
    }
}

private fun Modifier.detectScrollGesture(
    scrollOrientation: ChartScrollOrientation,
    onVerticalScroll: (Float) -> Unit,
    onHorizontalScroll: (Float) -> Unit
) = this.then(Modifier.pointerInput(Unit) {
    detectDragGestures { change, dragAmount ->
        change.consume()

        val scrollX = when (scrollOrientation) {
            ChartScrollOrientation.Horizontal,
            ChartScrollOrientation.Both -> dragAmount.x

            else -> 0f
        }

        val scrollY = when (scrollOrientation) {
            ChartScrollOrientation.Vertical,
            ChartScrollOrientation.Both -> dragAmount.y

            else -> 0f
        }

        onHorizontalScroll(scrollX)
        onVerticalScroll(scrollY)
    }
})

/**
 * Gesture support to detect and filter pointer scopes to give a zoom start callback.
 *
 * @param isZoomAllowed True if user is allowed to zoom.
 * @param onZoom Callback invoked when a zoom gesture is detected, passing the zoom factor.
 *
 * This function is internal and used by [ChartScaffold] to enable pinch zoom gestures.
 */
@Suppress("CyclomaticComplexMethod")
internal suspend fun PointerInputScope.detectZoomGesture(
    isZoomAllowed: Boolean = true,
    onZoom: (zoom: Float) -> Unit
) {
    if (isZoomAllowed) {
        awaitEachGesture {
            awaitFirstDown(requireUnconsumed = false)

            var zoom = 1f
            var pastTouchSlop = false
            val touchSlop = viewConfiguration.touchSlop

            do {
                val event = awaitPointerEvent()
                val canceled = event.changes.any { it.isConsumed }
                if (event.changes.size == 1) {
                    break
                } else if (event.changes.size == 2) {
                    if (!canceled) {
                        val zoomChange = event.calculateZoom()
                        if (!pastTouchSlop) {
                            zoom *= zoomChange
                            val centroidSize =
                                event.calculateCentroidSize(useCurrent = false)
                            val zoomMotion = abs(1 - zoom) * centroidSize
                            if (zoomMotion > touchSlop) pastTouchSlop = true
                        }
                        if (pastTouchSlop) {
                            if (zoomChange != 1f) onZoom(zoomChange)
                            event.changes.forEach { if (it.positionChanged()) it.consume() }
                        }
                    }
                } else {
                    break
                }
            } while (!canceled && event.changes.any { it.pressed })
        }
    }
}

/**
 * Enum class to define the orientation of the scroll.
 */
enum class ChartScrollOrientation {
    Horizontal,
    Vertical,
    Both
}

/**
 * A data class that represents the accessibility item for the zoom area chart.
 * @param contentDescription The content description for the accessibility item.
 * @param leftCustomAction The content description for the accessibility the left thumb.
 * @param rightCustomAction TThe content description for the accessibility right thumb.
 * @param upCustomAction The content description for the accessibility up thumb.
 * @param downCustomAction The content description for the accessibility down thumb.
 * @param zoomInCustomAction The content description for the accessibility zoom in thumb.
 * @param zoomOutCustomAction The content description for the accessibility zoom out thumb.
 * @param scrollStepX the applied offset (in dp) when the user scrolls the chart to the left or right.
 * @param scrollStepY the applied offset (in dp) when the user scrolls the chart up or down.
 * @param zoomStep the applied zoom increment/decrement when the user zooms in or out.
 */
data class ScrollableAccessibilityItem(
    val contentDescription: String,
    val leftCustomAction: String? = null,
    val rightCustomAction: String? = null,
    val upCustomAction: String? = null,
    val downCustomAction: String? = null,
    val zoomInCustomAction: String? = null,
    val zoomOutCustomAction: String? = null,
    val scrollStepX: Dp = 24.dp,
    val scrollStepY: Dp = 24.dp,
    val zoomStep: Float = 0.2f
)

/**
 *
 * returns the max scrollable distance based on the points to be drawn along with padding etc.
 * @param xMax : Max X-Axis value.
 * @param xMin: Min X-Axis value.
 * @param unitSize: Total distance between two X-Axis points.
 * @param canvasWidth : Total available canvas width.
 * @param axisStartPadding: Padding from the start of the axis.
 * @param axisEndPadding Padding from the end of the axis
 */
private fun getMaxHorizontalScrollDistance(
    xMax: Float,
    xMin: Float,
    unitSize: Float,
    canvasWidth: Float,
    axisStartPadding: Float,
    axisEndPadding: Float
): Float {
    val xLastPoint = (xMax - xMin) * unitSize + axisStartPadding + axisEndPadding
    return if (xLastPoint > canvasWidth) {
        xLastPoint - canvasWidth
    } else {
        0f
    }
}

/**
 *
 * returns the max scrollable distance based on the points to be drawn along with padding etc.
 * @param yMax : Max Y-Axis value.
 * @param yMin: Min Y-Axis value.
 * @param unitSize: Total distance between two Y-Axis points.
 * @param canvasHeight : Total available canvas width.
 * @param axisBottomPadding: Padding from the bottom of the axis.
 * @param axisTopPadding: Padding from the top of the axis
 */
private fun getMaxVerticalScrollDistance(
    yMax: Float,
    yMin: Float,
    unitSize: Float,
    canvasHeight: Float,
    axisBottomPadding: Float,
    axisTopPadding: Float
): Float {
    val yLastPoint = (yMax - yMin) * unitSize + axisBottomPadding + axisTopPadding
    return if (yLastPoint > canvasHeight) {
        yLastPoint - canvasHeight
    } else {
        0f
    }
}

/**
 * A data class that represents the content data for the chart scaffold.
 *
 * This is used to send info about scroll and to the composable content lambda.
 * It also provides parameters to control the scroll when a chart requires forced scroll movement
 * for a11y or keyboard focus mainly.
 *
 * @property horizontalScroll The horizontal scroll amount in Dp.
 * @property verticalScroll The vertical scroll amount in Dp.
 * @property onHorizontalScrollChangeRequest A callback to request a change in the horizontal scroll amount.
 * @property onVerticalScrollChangeRequest A callback to request a change in the vertical scroll amount.
 * @property zoom The zoom level of the chart. It's a multiplier with 1f being 100% zoom.
 */
@Immutable
data class ChartScaffoldContentData(
    val horizontalScroll: Dp = 0.dp,
    val verticalScroll: Dp = 0.dp,
    val onHorizontalScrollChangeRequest: ((Float) -> Unit)? = null,
    val onVerticalScrollChangeRequest: ((Float) -> Unit)? = null,
    val zoom: Float = 1f
)
