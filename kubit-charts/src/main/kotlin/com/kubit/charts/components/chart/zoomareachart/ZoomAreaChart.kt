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
 * @param onWidthChange A lambda that will be called when the width of the chart changes.
 * @param onHeightChange A lambda that will be called when the height of the chart changes.
 * @param onSelectionChange A lambda that will be called when the selection range changes.
 */
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
    onWidthChange: (Int) -> Unit = {},
    onHeightChange: (Int) -> Unit = {},
    onSelectionChange: (Float, Float) -> Unit = { _, _ -> }
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(thumbSize)
            .padding(horizontal = thumbSize / 2)
            .background(backgroundColor)
    ) {
        val parentWidthPx = constraints.maxWidth
        val thumbSizePx = with(LocalDensity.current) { thumbSize.toPx() }
        var startOffset by remember { mutableStateOf(Offset(-thumbSizePx / 2, 0f)) }
        var endOffset by remember {
            mutableStateOf(
                Offset(
                    parentWidthPx.toFloat() - thumbSizePx / 2,
                    0f
                )
            )
        }

        onWidthChange(parentWidthPx)
        onHeightChange(constraints.maxHeight)

        val selectionStart = ((startOffset.x + thumbSizePx / 2) / parentWidthPx).coerceIn(0f, 1f)
        val selectionEnd = ((endOffset.x + thumbSizePx / 2) / parentWidthPx).coerceIn(0f, 1f)

        LaunchedEffect(selectionStart, selectionEnd) {
            onSelectionChange(selectionStart, selectionEnd)
        }

        content()

        if (selectionStart != 0f || selectionEnd != 1f) {
            ZoomArea(
                startOffset = startOffset,
                endOffset = endOffset,
                thumbSizePx = thumbSizePx,
                opacityColor = opacityColor,
                zoomAreaAccessibilityItem = zoomAreaAccessibilityItem,
                onDrag = { dragAmount ->
                    val dragX = dragAmount.x
                    val newStartX = (startOffset.x + dragX).coerceIn(
                        -thumbSizePx / 2,
                        parentWidthPx - (endOffset.x - startOffset.x) - thumbSizePx / 2
                    )
                    val newEndX = newStartX + (endOffset.x - startOffset.x)

                    startOffset = Offset(newStartX, 0f)
                    endOffset = Offset(newEndX, 0f)
                }
            )
        }

        Thumb(
            modifier = Modifier.offset { startOffset.round() },
            onDrag = { dragAmount ->
                val summed = startOffset + dragAmount
                startOffset = Offset(
                    x = summed.x.coerceIn(-thumbSizePx / 2, endOffset.x - thumbSizePx),
                    y = 0f
                )
            },
            thumbSize = thumbSize,
            handlerAccessibilityItem = leftHandlerAccessibilityItem,
            content = startThumb
        )

        Thumb(
            modifier = Modifier.offset { endOffset.round() },
            onDrag = { dragAmount ->
                val summed = endOffset + dragAmount
                endOffset = Offset(
                    x = summed.x.coerceIn(
                        startOffset.x + thumbSizePx,
                        parentWidthPx.toFloat() - thumbSizePx / 2
                    ),
                    y = 0f
                )
            },
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
 * @param thumbSize The size of the thumb.
 * @param handlerAccessibilityItem The accessibility item for the thumb.
 * @param content The content of the thumb.
 * @param modifier The modifier to be applied to the thumb.
 */
@Composable
fun Thumb(
    onDrag: (Offset) -> Unit,
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
                detectDragGestures { change, dragAmount ->
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
fun ZoomArea(
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
