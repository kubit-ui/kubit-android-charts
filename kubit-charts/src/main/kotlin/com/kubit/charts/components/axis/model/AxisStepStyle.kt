package com.kubit.charts.components.axis.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Axis step style interface
 *
 * @field strokeWidth The width of the stroke
 * @field strokeColor The color of the stroke
 */
interface AxisStepStyle {
    val strokeWidth: Dp
    val strokeColor: Color
    companion object {
        /**
         * Creates an instance of [DashedStepStyle]
         */
        fun dashed(
            strokeWidth: Dp,
            strokeColor: Color,
            dashLength: Dp,
            gapLength: Dp,
            phase: Dp
        ) = DashedStepStyle(strokeWidth, strokeColor, dashLength, gapLength, phase)

        /**
         * Creates an instance of [SolidStepStyle]
         */
        fun solid(
            strokeWidth: Dp,
            strokeColor: Color
        ) = SolidStepStyle(strokeWidth, strokeColor)
    }
}

/**
 * Class used to define a solid step style
 *
 * @property strokeWidth The width of the stroke
 * @property strokeColor The color of the stroke
 */
@Immutable
data class SolidStepStyle internal constructor(
    override val strokeWidth: Dp,
    override val strokeColor: Color
) : AxisStepStyle

/**
 * Class used to define a dashed step style
 *
 * @property strokeWidth The width of the stroke
 * @property strokeColor The color of the stroke
 * @property dashLength The length of the dash (solid part)
 * @property gapLength The length of the gap (empty part)
 * @property phase The phase of the dash. It is the offset of the dash pattern, just in case you
 * want to leave a gap at the beginning of the line.
 */
@Immutable
data class DashedStepStyle internal constructor(
    override val strokeWidth: Dp,
    override val strokeColor: Color,
    val dashLength: Dp,
    val gapLength: Dp,
    val phase: Dp
) : AxisStepStyle
