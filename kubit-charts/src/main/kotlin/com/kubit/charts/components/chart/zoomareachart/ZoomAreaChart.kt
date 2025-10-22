package com.kubit.charts.components.chart.zoomareachart

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.zIndex
import org.jetbrains.annotations.ApiStatus.Experimental

/**
 * A composable that displays a zoom area chart with two thumbs to select the range.
 *
 * ```kotlin
 * ZoomAreaChart(
 *   opacityColor = Color.Blue.copy(alpha = 0.3f),
 *   modifier = Modifier,
 *   backgroundColor = Color.White,
 *   thumbSize = 40.dp,
 *   leftHandlerAccessibilityItem = ZoomAreaChartAccessibilityItem(contentDescription = "Left thumb"),
 *   rightHandlerAccessibilityItem = ZoomAreaChartAccessibilityItem(contentDescription = "Right thumb"),
 *   zoomAreaAccessibilityItem = ZoomAreaChartAccessibilityItem(contentDescription = "Zoom area"),
 *   content = { /* Chart content here */ },
 *   startThumb = { /* Custom composable for start thumb */ },
 *   endThumb = { /* Custom composable for end thumb */ },
 *   thumbPosition = ThumbPosition.Middle,
 *   customStartOffset = 10f, // Only used if thumbPosition is Custom
 *   customEndOffset = -10f,  // Only used if thumbPosition is Custom
 *   initialStartFraction = 0.2f,
 *   initialEndFraction = 0.8f,
 *   onWidthChange = { width -> /* Handle width change */ },
 *   onHeightChange = { height -> /* Handle height change */ },
 *   onSelectionChange = { start, end -> /* Handle selection change */ }
 * )
 * ```
 *
 * @param opacityColor The color of the area between the thumbs.
 * @param modifier The modifier to be applied to the chart.
 * @param backgroundColor The background color of the chart.
 * @param thumbSize The size of the thumbs.
 * @param leftHandlerAccessibilityItem The accessibility item for the left thumb.
 * @param rightHandlerAccessibilityItem The accessibility item for the right thumb.
 * @param zoomAreaAccessibilityItem The accessibility item for the zoom area.
 * @param content The content of the chart.
 * @param startThumb The composable that will be displayed as the start thumb.
 * @param endThumb The composable that will be displayed as the end thumb.
 * @param thumbPosition The position of the thumbs relative to the zoom area. Options are Outside, Inside, Middle, and Custom.
 * @param customStartOffset The custom offset for the start thumb when thumbPosition is set to Custom. Positive values move the thumb to the right, negative values to the left.
 * @param customEndOffset The custom offset for the end thumb when thumbPosition is set to Custom. Positive values move the thumb to the right, negative values to the left.
 * @param initialStartFraction The initial position of the start thumb as a fraction of the total width (0f to 1f).
 * @param initialEndFraction The initial position of the end thumb as a fraction of the total width (0f to 1f).
 * @param onWidthChange A lambda that will be called when the width of the chart changes.
 * @param onHeightChange A lambda that will be called when the height of the chart changes.
 * @param onSelectionChange A lambda that will be called when the selection range changes.
 */
@Experimental
@SuppressLint("UnusedBoxWithConstraintsScope")
@Suppress("LongMethod")
@Composable
fun ZoomAreaChart(
    opacityColor: Color,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    thumbSize: Dp = 40.dp,
    leftHandlerAccessibilityItem: ZoomAreaChartAccessibilityItem? = null,
    rightHandlerAccessibilityItem: ZoomAreaChartAccessibilityItem? = null,
    zoomAreaAccessibilityItem: ZoomAreaChartAccessibilityItem? = null,
    content: @Composable () -> Unit = {},
    startThumb: @Composable () -> Unit = {},
    endThumb: @Composable () -> Unit = {},
    thumbPosition: ZoomAreaThumbPosition = ZoomAreaThumbPosition.Middle,
    customStartOffset: Float? = null,
    customEndOffset: Float? = null,
    initialStartFraction: Float = 0f,
    initialEndFraction: Float = 1f,
    onWidthChange: (Int) -> Unit = {},
    onHeightChange: (Int) -> Unit = {},
    onSelectionChange: (Float, Float) -> Unit = { _, _ -> }
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(thumbSize)
            .padding(horizontal = getDefaultHorizontalPadding(thumbPosition, thumbSize))
            .background(backgroundColor)
    ) {
        val parentWidthPx = constraints.maxWidth
        val thumbSizePx = with(LocalDensity.current) { thumbSize.toPx() }

        val thumbState = rememberThumbStateWithInitialFractions(
            parentWidthPx,
            thumbSizePx,
            initialStartFraction,
            initialEndFraction
        )
        val positionCalculator = rememberThumbPositions(
            parentWidthPx, thumbSizePx, thumbPosition, customStartOffset, customEndOffset
        )

        LaunchedEffect(parentWidthPx) { onWidthChange(parentWidthPx) }
        LaunchedEffect(constraints.maxHeight) { onHeightChange(constraints.maxHeight) }
        LaunchedEffect(thumbState.startOffset, thumbState.endOffset) {
            val (selectionStart, selectionEnd) = positionCalculator.calculateSelection(
                thumbState.startOffset,
                thumbState.endOffset
            )
            onSelectionChange(selectionStart, selectionEnd)
        }

        content()

        val startThumbDragHandler = remember(thumbState, thumbSizePx, parentWidthPx) {
            createStartThumbDragHandler(thumbState, thumbSizePx, parentWidthPx)
        }
        val endThumbDragHandler = remember(thumbState, thumbSizePx, parentWidthPx) {
            createEndThumbDragHandler(thumbState, thumbSizePx, parentWidthPx)
        }
        val zoomAreaDragHandler = remember(thumbState, thumbSizePx, parentWidthPx) {
            createZoomAreaDragHandler(thumbState, thumbSizePx, parentWidthPx)
        }

        ZoomArea(
            startOffset = thumbState.startOffset,
            endOffset = thumbState.endOffset,
            thumbSizePx = thumbSizePx,
            opacityColor = opacityColor,
            zoomAreaAccessibilityItem = zoomAreaAccessibilityItem,
            onDrag = zoomAreaDragHandler
        )

        Thumb(
            modifier = Modifier
                .offset {
                    Offset(
                        thumbState.startOffset.x + positionCalculator.visualStartShiftPx,
                        0f
                    ).round()
                }
                .zIndex(if (thumbState.activeThumb == ActiveThumb.Start) 2f else 1f),
            onDrag = startThumbDragHandler,
            onPress = { thumbState.activeThumb = ActiveThumb.Start },
            onRelease = { thumbState.activeThumb = null },
            thumbSize = thumbSize,
            handlerAccessibilityItem = leftHandlerAccessibilityItem,
            content = startThumb
        )

        Thumb(
            modifier = Modifier
                .offset {
                    Offset(
                        thumbState.endOffset.x + positionCalculator.visualEndShiftPx,
                        0f
                    ).round()
                }
                .zIndex(if (thumbState.activeThumb == ActiveThumb.End) 2f else 1f),
            onDrag = endThumbDragHandler,
            onPress = { thumbState.activeThumb = ActiveThumb.End },
            onRelease = { thumbState.activeThumb = null },
            thumbSize = thumbSize,
            content = endThumb,
            handlerAccessibilityItem = rightHandlerAccessibilityItem,
        )
    }
}

/**
 * A composable that displays a draggable thumb.
 *
 * @param onDrag A lambda that will be called when the thumb is dragged.
 * @param onPress A lambda that will be called when the thumb is pressed.
 * @param onRelease A lambda that will be called when the thumb is released.
 * @param thumbSize The size of the thumb.
 * @param handlerAccessibilityItem The accessibility item for the thumb.
 * @param content The content of the thumb.
 * @param modifier The modifier to be applied to the thumb.
 */
@Experimental
@Composable
fun Thumb(
    onDrag: (Offset) -> Unit,
    onPress: () -> Unit,
    onRelease: () -> Unit,
    thumbSize: Dp,
    handlerAccessibilityItem: ZoomAreaChartAccessibilityItem?,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val thumbSizePx = with(density) { thumbSize.toPx() }

    Surface(
        modifier = modifier
            .size(thumbSize)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        onPress()
                    },
                    onDragEnd = {
                        onRelease()
                    },
                    onDragCancel = {
                        onRelease()
                    }
                ) { change, dragAmount ->
                    change.consume()
                    onDrag(dragAmount)
                }
            }
            .focusable()
            .semantics {
                handlerAccessibilityItem?.let {
                    contentDescription = it.contentDescription
                    customActions = listOf(
                        CustomAccessibilityAction(it.leftCustomAction) {
                            onDrag(
                                Offset(
                                    -thumbSizePx / it.movementFraction,
                                    0f
                                )
                            )
                            true
                        },
                        CustomAccessibilityAction(it.rightCustomAction) {
                            onDrag(
                                Offset(
                                    thumbSizePx / it.movementFraction,
                                    0f
                                )
                            )
                            true
                        }
                    )
                }
            },
        color = Color.Transparent
    ) {
        content()
    }
}

@Composable
private fun rememberThumbPositions(
    parentWidthPx: Int,
    thumbSizePx: Float,
    thumbPosition: ZoomAreaThumbPosition,
    customStartOffset: Float?,
    customEndOffset: Float?
): ThumbPositionCalculator {
    return remember(parentWidthPx, thumbSizePx, thumbPosition, customStartOffset, customEndOffset) {
        ThumbPositionCalculator(
            parentWidthPx = parentWidthPx,
            thumbSizePx = thumbSizePx,
            thumbPosition = thumbPosition,
            customStartOffset = customStartOffset,
            customEndOffset = customEndOffset
        )
    }
}

@Composable
private fun rememberThumbStateWithInitialFractions(
    parentWidthPx: Int,
    thumbSizePx: Float,
    initialStartFraction: Float,
    initialEndFraction: Float
): ThumbState {
    return remember(parentWidthPx) {
        val initialStartOffset = Offset(
            (initialStartFraction * parentWidthPx) - thumbSizePx / 2,
            0f
        )
        val initialEndOffset = Offset(
            (initialEndFraction * parentWidthPx) - thumbSizePx / 2,
            0f
        )

        ThumbState(
            initialStartOffset = initialStartOffset,
            initialEndOffset = initialEndOffset
        )
    }
}

/**
 * A composable that displays the draggable zoom area between the thumbs.
 *
 * @param startOffset The offset of the start thumb in pixels.
 * @param endOffset The offset of the end thumb in pixels.
 * @param thumbSizePx The size of the thumb in pixels.
 * @param opacityColor The color of the zoom area between the thumbs.
 * @param zoomAreaAccessibilityItem Accessibility information for the zoom area.
 * @param onDrag Lambda called when the zoom area is dragged.
 * @param modifier Modifier to be applied to the zoom area.
 */
@Composable
private fun ZoomArea(
    startOffset: Offset,
    endOffset: Offset,
    thumbSizePx: Float,
    opacityColor: Color,
    zoomAreaAccessibilityItem: ZoomAreaChartAccessibilityItem?,
    onDrag: (Offset) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .offset { Offset(startOffset.x + thumbSizePx / 2, 0f).round() }
            .width(with(LocalDensity.current) { (endOffset.x - startOffset.x).toDp() })
            .height(with(LocalDensity.current) { thumbSizePx.toDp() })
            .background(opacityColor)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onDrag(dragAmount)
                }
            }
            .focusable()
            .semantics {
                zoomAreaAccessibilityItem?.let {
                    contentDescription = it.contentDescription
                    customActions = listOf(
                        CustomAccessibilityAction(it.leftCustomAction) {
                            onDrag(
                                Offset(
                                    -thumbSizePx / it.movementFraction,
                                    0f
                                )
                            )
                            true
                        },
                        CustomAccessibilityAction(it.rightCustomAction) {
                            onDrag(
                                Offset(
                                    thumbSizePx / it.movementFraction,
                                    0f
                                )
                            )
                            true
                        }
                    )
                }
            }
    )
}

private fun getDefaultHorizontalPadding(
    thumbPosition: ZoomAreaThumbPosition,
    thumbSize: Dp
): Dp =
    when (thumbPosition) {
        ZoomAreaThumbPosition.Custom -> 0.dp
        else -> thumbSize / 2
    }

private fun createStartThumbDragHandler(
    thumbState: ThumbState,
    thumbSizePx: Float,
    parentWidthPx: Int
): (Offset) -> Unit = { dragAmount ->
    val summed = thumbState.startOffset + dragAmount
    val newStartX = summed.x.coerceIn(
        -thumbSizePx / 2,
        parentWidthPx.toFloat() - thumbSizePx / 2
    )

    if (newStartX > thumbState.endOffset.x) {
        thumbState.endOffset = Offset(newStartX, 0f)
    }

    thumbState.startOffset = Offset(newStartX, 0f)
}

private fun createEndThumbDragHandler(
    thumbState: ThumbState,
    thumbSizePx: Float,
    parentWidthPx: Int
): (Offset) -> Unit = { dragAmount ->
    val summed = thumbState.endOffset + dragAmount
    val newEndX = summed.x.coerceIn(
        -thumbSizePx / 2,
        parentWidthPx.toFloat() - thumbSizePx / 2
    )

    if (newEndX < thumbState.startOffset.x) {
        thumbState.startOffset = Offset(newEndX, 0f)
    }

    thumbState.endOffset = Offset(newEndX, 0f)
}

private fun createZoomAreaDragHandler(
    thumbState: ThumbState,
    thumbSizePx: Float,
    parentWidthPx: Int
): (Offset) -> Unit = { dragAmount ->
    val dragX = dragAmount.x
    val newStartX = (thumbState.startOffset.x + dragX).coerceIn(
        -thumbSizePx / 2,
        parentWidthPx - (thumbState.endOffset.x - thumbState.startOffset.x) - thumbSizePx / 2
    )
    val newEndX = newStartX + (thumbState.endOffset.x - thumbState.startOffset.x)

    thumbState.startOffset = Offset(newStartX, 0f)
    thumbState.endOffset = Offset(newEndX, 0f)
}

/**
 * Represents accessibility information for the zoom area chart and its thumbs.
 *
 * @property contentDescription The content description for the accessibility item.
 * @property leftCustomAction The description for the left thumb accessibility action.
 * @property rightCustomAction The description for the right thumb accessibility action.
 * @property movementFraction Determines the speed of movement for accessibility drag actions. Higher values mean slower movement. Movement is calculated as: ThumbSize in pixels / movementFraction.
 */
data class ZoomAreaChartAccessibilityItem(
    val contentDescription: String,
    val leftCustomAction: String,
    val rightCustomAction: String,
    val movementFraction: Int = 10
)

@Stable
private class ThumbState(
    initialStartOffset: Offset,
    initialEndOffset: Offset
) {
    var startOffset by mutableStateOf(initialStartOffset)
    var endOffset by mutableStateOf(initialEndOffset)
    var activeThumb by mutableStateOf<ActiveThumb?>(null)
}

private class ThumbPositionCalculator(
    private val parentWidthPx: Int,
    private val thumbSizePx: Float,
    private val thumbPosition: ZoomAreaThumbPosition,
    private val customStartOffset: Float?,
    private val customEndOffset: Float?
) {
    val visualStartShiftPx: Float
        get() = when (thumbPosition) {
            ZoomAreaThumbPosition.Middle -> 0f
            ZoomAreaThumbPosition.Outside -> -thumbSizePx / 2
            ZoomAreaThumbPosition.Inside -> thumbSizePx / 2
            ZoomAreaThumbPosition.Custom -> customStartOffset ?: 0f
        }

    val visualEndShiftPx: Float
        get() = when (thumbPosition) {
            ZoomAreaThumbPosition.Middle -> 0f
            ZoomAreaThumbPosition.Outside -> thumbSizePx / 2
            ZoomAreaThumbPosition.Inside -> -thumbSizePx / 2
            ZoomAreaThumbPosition.Custom -> customEndOffset ?: 0f
        }

    fun calculateSelection(startOffset: Offset, endOffset: Offset): Pair<Float, Float> {
        val selectionStart = if (parentWidthPx > 0) {
            ((startOffset.x + thumbSizePx / 2) / parentWidthPx).coerceIn(0f, 1f)
        } else {
            0f
        }

        val selectionEnd = if (parentWidthPx > 0) {
            ((endOffset.x + thumbSizePx / 2) / parentWidthPx).coerceIn(0f, 1f)
        } else {
            1f
        }

        return selectionStart to selectionEnd
    }
}

private enum class ActiveThumb {
    Start, End
}

/**
 * Enum class representing the position of the zoom area chart thumbs.
 */
enum class ZoomAreaThumbPosition {
    Outside, Inside, Middle, Custom
}
