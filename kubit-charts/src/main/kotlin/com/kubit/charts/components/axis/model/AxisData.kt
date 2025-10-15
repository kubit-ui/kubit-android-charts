package com.kubit.charts.components.axis.model

import androidx.compose.runtime.Immutable
import com.kubit.charts.components.axis.exceptions.AxisChartException
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

/**
 * Represents the axis data, including all axis steps and methods for converting axis values to canvas coordinates.
 *
 * Provides functionality to prepare axis steps for vertical and horizontal axes, and to convert mathematical values
 * to their corresponding positions on the canvas, considering axis range, padding, decorative elements, and zoom.
 *
 * @property axisSteps Immutable list of axis steps for this axis.
 */
@Immutable
data class AxisData(
    val axisSteps: ImmutableList<AxisStep>
) {
    val range by lazy {
        @Suppress("SpacingAroundRangeOperator", "SpacingAroundCurly")
        axisSteps.minOf { it.axisValue }..axisSteps.maxOf { it.axisValue }
    }

    val rangeSize by lazy {
        range.endInclusive - range.start
    }

    // Cached internal data to be able to use methods like toCanvasPosition without sending all parameters
    private var axisInternalData: AxisInternalData? = null

    /**
     * Prepares the vertical axis by calculating the pixel position of each axis step.
     * Considers axis height, paddings, decorative height, its position, axis range, and zoom factor.
     *
     * @param height Height of the axis in pixels.
     * @param topPaddingInPx Top padding in pixels.
     * @param bottomPaddingInPx Bottom padding in pixels.
     * @param decorativeHeightInPx Height of the decorative end in pixels (for grid lines).
     * @param decorativeHeightPosition Position of the decorative height.
     * @param fixedUnitSize Fixed unit size for the axis. If null, unit size is calculated.
     * @param zoom Zoom factor to apply to the axis.
     * @return A new [AxisData] with updated axis step positions in pixels.
     */
    fun toCanvasCoordinatesVAxis(
        height: Float,
        topPaddingInPx: Float,
        bottomPaddingInPx: Float,
        decorativeHeightInPx: Float,
        decorativeHeightPosition: DecorativeHeightPosition,
        fixedUnitSize: Float? = null,
        zoom: Float = 1f,
    ): AxisData {
        val minValue = range.start
        val maxValue = range.endInclusive

        val processedHeight = getAxisHeight(
            height,
            topPaddingInPx,
            bottomPaddingInPx,
            decorativeHeightInPx,
            decorativeHeightPosition
        )

        val yPixelsPerUnit = fixedUnitSize?.times(zoom) ?: getHeightPixelsPerUnit(
            processedHeight,
            decorativeHeightInPx,
            minValue,
            maxValue,
            decorativeHeightPosition
        )

        val vAxisData = AxisInternalData.VerticalAxisInternalData(
            height = processedHeight,
            min = minValue,
            max = maxValue,
            yPixelsPerUnit = yPixelsPerUnit,
            topPaddingInPx = topPaddingInPx,
            bottomPaddingInPx = bottomPaddingInPx,
            decorativeHeightInPx = decorativeHeightInPx,
            decorativeHeightPosition = decorativeHeightPosition
        )

        axisInternalData = vAxisData

        return this.copy(axisSteps = axisSteps.map {
            it.copy(axisValueInPx = toCanvasPosition(it.axisValue))
        }.toPersistentList()).apply {
            setAxisInternalData(vAxisData)
        }
    }

    /**
     * Prepares the horizontal axis by calculating the pixel position of each axis step.
     * Considers axis width, paddings, decorative width, its position, axis range, and zoom factor.
     *
     * @param width Width of the axis in pixels.
     * @param startPaddingInPx Start padding in pixels.
     * @param endPaddingInPx End padding in pixels.
     * @param decorativeWidthInPx Width of the decorative end in pixels (for grid lines).
     * @param decorativeWidthPosition Position of the decorative width.
     * @param fixedUnitSize Fixed unit size for the axis. If null, unit size is calculated.
     * @param zoom Zoom factor to apply to the axis. Default is 1.
     * @return A new [AxisData] with updated axis step positions in pixels.
     */
    fun toCanvasCoordinatesHAxis(
        width: Float,
        startPaddingInPx: Float,
        endPaddingInPx: Float,
        decorativeWidthInPx: Float,
        decorativeWidthPosition: DecorativeWidthPosition,
        fixedUnitSize: Float? = null,
        zoom: Float = 1f,
    ): AxisData {
        val minValue = range.start
        val maxValue = range.endInclusive

        val processedWidth = getAxisWidth(
            width,
            startPaddingInPx,
            endPaddingInPx,
            decorativeWidthPosition
        )

        val xPixelsPerUnit = fixedUnitSize?.times(zoom) ?: getWidthPixelsPerUnit(
            processedWidth,
            minValue,
            maxValue,
            decorativeWidthInPx,
            decorativeWidthPosition
        )

        val xAxisInternalData = AxisInternalData.HorizontalAxisInternalData(
            width = processedWidth,
            min = minValue,
            max = maxValue,
            xPixelsPerUnit = xPixelsPerUnit,
            startPaddingInPx = startPaddingInPx,
            endPaddingInPx = endPaddingInPx,
            decorativeWidthInPx = decorativeWidthInPx,
            decorativeWidthPosition = decorativeWidthPosition
        )

        axisInternalData = xAxisInternalData

        return this.copy(axisSteps = axisSteps.map {
            it.copy(axisValueInPx = toCanvasPosition(it.axisValue))
        }.toPersistentList()).apply {
            setAxisInternalData(xAxisInternalData)
        }
    }

    /**
     * Converts a mathematical value to its corresponding canvas position using previously calculated axis parameters.
     *
     * You must call [toCanvasCoordinatesHAxis] or [toCanvasCoordinatesVAxis] before using this method, otherwise an exception is thrown.
     *
     * @param value Value to convert from axis space to canvas space.
     * @return The pixel position on the canvas.
     * @throws AxisChartException.AxisWithoutInternalDataException if axis internal data is not set.
     */
    fun toCanvasPosition(value: Float) = when (axisInternalData) {
        is AxisInternalData.VerticalAxisInternalData -> {
            val data = axisInternalData as AxisInternalData.VerticalAxisInternalData

            data.height - (value * data.yPixelsPerUnit - data.min * data.yPixelsPerUnit) +
                data.topPaddingInPx
        }

        is AxisInternalData.HorizontalAxisInternalData -> {
            val data = axisInternalData as AxisInternalData.HorizontalAxisInternalData

            with(data) {
                return when (decorativeWidthPosition) {
                    DecorativeWidthPosition.Start,
                    DecorativeWidthPosition.Both -> decorativeWidthInPx + startPaddingInPx +
                        value * xPixelsPerUnit - min * xPixelsPerUnit

                    DecorativeWidthPosition.End -> startPaddingInPx + value * xPixelsPerUnit -
                        min * xPixelsPerUnit
                }
            }
        }

        null -> throw AxisChartException.AxisWithoutInternalDataException()
    }

    private fun getAxisHeight(
        height: Float,
        topPaddingInPx: Float,
        bottomPaddingInPx: Float,
        decorativeEndHeightInPx: Float,
        decorativeHeightPosition: DecorativeHeightPosition
    ) = when (decorativeHeightPosition) {
        DecorativeHeightPosition.Bottom -> height - topPaddingInPx - bottomPaddingInPx - decorativeEndHeightInPx
        DecorativeHeightPosition.Top -> height - topPaddingInPx - bottomPaddingInPx
        DecorativeHeightPosition.Both -> height - topPaddingInPx - bottomPaddingInPx - decorativeEndHeightInPx * 2
    }

    private fun getHeightPixelsPerUnit(
        height: Float,
        decorativeEndHeightInPx: Float,
        min: Float,
        max: Float,
        decorativeHeightPosition: DecorativeHeightPosition
    ) = when (decorativeHeightPosition) {
        DecorativeHeightPosition.Bottom -> height / (max - min)
        DecorativeHeightPosition.Top -> (height - decorativeEndHeightInPx) / (max - min)
        DecorativeHeightPosition.Both -> (height - decorativeEndHeightInPx * 2) / (max - min)
    }

    private fun getAxisWidth(
        width: Float,
        startPaddingInPx: Float,
        endPaddingInPx: Float,
        decorativeWidthPosition: DecorativeWidthPosition
    ) = when (decorativeWidthPosition) {
        DecorativeWidthPosition.Start,
        DecorativeWidthPosition.End,
        DecorativeWidthPosition.Both -> width - startPaddingInPx - endPaddingInPx
    }

    private fun getWidthPixelsPerUnit(
        width: Float,
        min: Float,
        max: Float,
        decorativeEndWidthInPx: Float,
        decorativeWidthPosition: DecorativeWidthPosition
    ) = when (decorativeWidthPosition) {
        DecorativeWidthPosition.Start,
        DecorativeWidthPosition.End -> (width - decorativeEndWidthInPx) / (max - min)

        DecorativeWidthPosition.Both -> (width - decorativeEndWidthInPx * 2) / (max - min)
    }

    private fun setAxisInternalData(
        axisInternalData: AxisInternalData
    ) {
        this.axisInternalData = axisInternalData
    }
}

/**
 * Type used to establish the position of the decorative height in the axis.
 */
enum class DecorativeHeightPosition {
    Top,
    Bottom,
    Both
}

/**
 * Type used to establish the position of the decorative width in the axis.
 */
enum class DecorativeWidthPosition {
    Start,
    End,
    Both
}

/**
 * Internal data to cache calculations to be able to use methods like [toCanvasPosition] without
 * sending all parameters.
 */
private sealed class AxisInternalData {
    data class VerticalAxisInternalData(
        val height: Float,
        val min: Float,
        val max: Float,
        val yPixelsPerUnit: Float,
        val topPaddingInPx: Float,
        val bottomPaddingInPx: Float,
        val decorativeHeightInPx: Float,
        val decorativeHeightPosition: DecorativeHeightPosition
    ) : AxisInternalData()

    data class HorizontalAxisInternalData(
        val width: Float,
        val min: Float,
        val max: Float,
        val xPixelsPerUnit: Float,
        val startPaddingInPx: Float,
        val endPaddingInPx: Float,
        val decorativeWidthInPx: Float,
        val decorativeWidthPosition: DecorativeWidthPosition
    ) : AxisInternalData()
}
