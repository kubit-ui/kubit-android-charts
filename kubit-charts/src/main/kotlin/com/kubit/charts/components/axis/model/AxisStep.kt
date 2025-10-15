package com.kubit.charts.components.axis.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle

/**
 * Represents a step in an axis.
 *
 * @property axisValue The value of the step in the axis.
 * @property axisLabel Optional. The label of the step.
 * @property labelStyle Optional. The [TextStyle] of the label.
 * @property stepStyle The [AxisStepStyle] of the step. If null, the step line won't be drawn.
 * @property axisValueInPx The value of the step in pixels. If null, the step won't be drawn.
 * Check [AxisData.toCanvasCoordinatesVAxis] or [AxisData.toCanvasCoordinatesHAxis] to process
 * the axis and obtain these pixel values.
 */
@Immutable
data class AxisStep(
    val axisValue: Float,
    val axisLabel: String? = null,
    val labelStyle: TextStyle? = null,
    val stepStyle: AxisStepStyle? = null,
    val axisValueInPx: Float? = null
)
