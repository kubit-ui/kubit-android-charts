package com.kubit.charts.components.axis

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kubit.charts.components.axis.exceptions.AxisChartException
import com.kubit.charts.components.axis.extensions.drawHorizontalAxisGridLines
import com.kubit.charts.components.axis.extensions.drawHorizontalAxisLabels
import com.kubit.charts.components.axis.extensions.drawVerticalAxisGridLines
import com.kubit.charts.components.axis.extensions.drawVerticalAxisLabels
import com.kubit.charts.components.axis.model.AxisData
import com.kubit.charts.components.axis.model.AxisPadding
import com.kubit.charts.components.axis.model.AxisStep
import com.kubit.charts.components.axis.model.AxisStepStyle
import com.kubit.charts.components.axis.model.DecorativeHeightPosition
import com.kubit.charts.components.axis.model.DecorativeWidthPosition
import kotlinx.collections.immutable.toPersistentList

/**
 * A Composable that renders a horizontal axis chart with customizable labels, grid lines, and decorative elements.
 *
 * This component provides a flexible way to display horizontal axes with support for scrolling, zooming,
 * label rotation, and various styling options. The axis can be positioned at the top or bottom of the container.
 *
 * ## Usage Examples
 * ```kotlin
 * // Basic horizontal axis at the bottom
 * HorizontalAxisChart(
 *     data = myAxisData,
 *     labelHeight = 32.dp
 * )
 *
 * // Scrollable axis with fixed unit size
 * HorizontalAxisChart(
 *     data = myAxisData,
 *     labelHeight = 32.dp,
 *     fixedUnitSize = 50.dp,
 *     horizontalScroll = 100.dp
 * )
 * ```
 *
 * @param data The [AxisData] containing the axis steps, labels, and styling information.
 * @param labelHeight The vertical space reserved for labels. This affects layout but not text size.
 * @param modifier Optional [Modifier] for customizing the composable's appearance and behavior.
 * @param type The vertical position of the axis. Use [HorizontalAxisType.Bottom] (default) or [HorizontalAxisType.Top].
 * @param padding The spacing around the axis content. Use [AxisPadding] to control start/end margins.
 * @param decorativeWidth Additional length for grid lines extending beyond the axis boundaries.
 * @param decorativeWidthPosition Where to apply the decorative width. Use [DecorativeWidthPosition.End] (default) or [DecorativeWidthPosition.Start].
 * @param customYOffset Override the automatic Y positioning. When set, ignores [type], [padding], and [labelHeight] for positioning.
 * @param labelVerticalAlignment How labels align vertically within their reserved space. Use [AxisLabelVerticalAlignment].
 * @param labelCenterAlignment The pivot point for label rotation. Use [AxisLabelCenterAlignment].
 * @param labelRotation Rotation angle in degrees applied to labels around the [labelCenterAlignment] point.
 * @param labelVerticalGap Additional vertical spacing between labels and grid lines.
 * @param fixedUnitSize Fixed spacing between axis steps. When null, spacing is calculated automatically. Required for scrolling.
 * @param horizontalScroll Horizontal scroll offset. Requires [fixedUnitSize] to be set when non-zero.
 * @param zoom Scale factor for the entire axis. Values > 1.0 zoom in, < 1.0 zoom out.
 * @param labelsBackgroundColor Background color behind the labels area. Defaults to transparent.
 *
 * @throws AxisChartException.AxisScrollWithoutFixedStepException When [horizontalScroll] is non-zero but [fixedUnitSize] is null.
 *
 * @sample com.kubit.charts.samples.components.axis.HorizontalAxisPreviewBottom
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HorizontalAxisChart(
    data: AxisData,
    labelHeight: Dp,
    modifier: Modifier = Modifier,
    type: HorizontalAxisType = HorizontalAxisType.Bottom,
    padding: AxisPadding = AxisPadding(),
    decorativeWidth: Dp = 0.dp,
    decorativeWidthPosition: DecorativeWidthPosition = DecorativeWidthPosition.End,
    customYOffset: Dp? = null,
    labelVerticalAlignment: AxisLabelVerticalAlignment = AxisLabelVerticalAlignment.Center,
    labelCenterAlignment: AxisLabelCenterAlignment = AxisLabelCenterAlignment.Center,
    labelRotation: Float = 0f,
    labelVerticalGap: Dp = 0.dp,
    fixedUnitSize: Dp? = null,
    horizontalScroll: Dp = 0.dp,
    zoom: Float = 1f,
    labelsBackgroundColor: Color = Color.Transparent,
) {
    if (fixedUnitSize == null && horizontalScroll != 0.dp) {
        throw AxisChartException.AxisScrollWithoutFixedStepException()
    }

    BoxWithConstraints(modifier = modifier) {
        val width = constraints.maxWidth

        with(LocalDensity.current) {
            val state = rememberHorizontalAxisDataState(
                data,
                width.toFloat(),
                padding.start.toPx(),
                padding.end.toPx(),
                decorativeWidth.toPx(),
                decorativeWidthPosition,
                fixedUnitSize?.toPx(),
                zoom
            )

            HorizontalAxisChart(
                chartState = state,
                labelHeight = labelHeight,
                type = type,
                padding = padding,
                customYOffset = customYOffset,
                labelVerticalAlignment = labelVerticalAlignment,
                labelCenterAlignment = labelCenterAlignment,
                labelRotation = labelRotation,
                labelVerticalGap = labelVerticalGap,
                horizontalScroll = horizontalScroll,
                labelsBackgroundColor = labelsBackgroundColor,
            )
        }
    }
}

/**
 * A Composable that renders a horizontal axis chart using a pre-computed state for performance optimization.
 *
 * This is the stateful version that accepts an [AxisChartState] instead of raw [AxisData]. Use this when
 * you need to share state between multiple axes or when you have complex coordination requirements.
 *
 * ## When to use this variant:
 * - When coordinating multiple related axes
 * - When you need access to processed axis information in the parent
 * - When implementing custom state management
 *
 * @param chartState Pre-computed [AxisChartState] containing processed axis data and canvas coordinates.
 * @param labelHeight The vertical space reserved for labels. This affects layout but not text size.
 * @param modifier Optional [Modifier] for customizing the composable's appearance and behavior.
 * @param type The vertical position of the axis. Use [HorizontalAxisType.Bottom] (default) or [HorizontalAxisType.Top].
 * @param padding The spacing around the axis content. Use [AxisPadding] to control margins.
 * @param customYOffset Override the automatic Y positioning. When set, ignores [type], [padding], and [labelHeight] for positioning.
 * @param labelVerticalAlignment How labels align vertically within their reserved space. Use [AxisLabelVerticalAlignment].
 * @param labelCenterAlignment The pivot point for label rotation. Use [AxisLabelCenterAlignment].
 * @param labelRotation Rotation angle in degrees applied to labels around the [labelCenterAlignment] point.
 * @param labelVerticalGap Additional vertical spacing between labels and grid lines.
 * @param horizontalScroll Horizontal scroll offset. The chartState must be configured with appropriate fixedUnitSize.
 * @param labelsBackgroundColor Background color behind the labels area. Defaults to transparent.
 *
 * @sample com.kubit.charts.samples.components.axis.HorizontalAxisPreviewBottom
 */
@Composable
fun HorizontalAxisChart(
    chartState: AxisChartState,
    labelHeight: Dp,
    modifier: Modifier = Modifier,
    type: HorizontalAxisType = HorizontalAxisType.Bottom,
    padding: AxisPadding = AxisPadding(),
    customYOffset: Dp? = null,
    labelVerticalAlignment: AxisLabelVerticalAlignment = AxisLabelVerticalAlignment.Center,
    labelCenterAlignment: AxisLabelCenterAlignment = AxisLabelCenterAlignment.Center,
    labelRotation: Float = 0f,
    labelVerticalGap: Dp = 0.dp,
    horizontalScroll: Dp = 0.dp,
    labelsBackgroundColor: Color = Color.Transparent,
) {
    val textMeasurer = rememberTextMeasurer(cacheSize = chartState.processedAxisData.axisSteps.size)

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(labelHeight)
                .background(labelsBackgroundColor)
                .align(
                    when (type) {
                        HorizontalAxisType.Top -> Alignment.TopCenter
                        HorizontalAxisType.Bottom -> Alignment.BottomCenter
                    }
                )
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            drawHorizontalAxisLabels(
                axisSteps = chartState.processedAxisData.axisSteps,
                textMeasurer = textMeasurer,
                type = type,
                labelHeight = labelHeight,
                padding = padding,
                axisCustomYOffset = customYOffset,
                labelVerticalAlignment = labelVerticalAlignment,
                labelCenterAlignment = labelCenterAlignment,
                labelRotation = labelRotation,
                labelVerticalGap = labelVerticalGap,
                horizontalScroll = horizontalScroll,
            )
            drawHorizontalAxisGridLines(
                axisSteps = chartState.processedAxisData.axisSteps,
                type = type,
                labelHeight = labelHeight,
                padding = padding,
                axisCustomYOffset = customYOffset,
                horizontalScroll = horizontalScroll,
            )
        }
    }
}

/**
 * Composable that draws a vertical axis chart.
 *
 * @param data The [AxisData] for the axis.
 * @param labelWidth The width of the labels in the axis. This is not the text size, it's the space
 * the label will occupy in the axis.
 * @param modifier Optional [Modifier] for the composable.
 * @param type The type of the axis (Start by default). Use [VerticalAxisType]
 * @param padding The padding for the axis. Use [AxisPadding]
 * @param decorativeHeight The size of the decorative height at the position established by [decorativeHeightPosition].
 * The decorativeHeight is the extra length the grid lines will have at the start or end of the axis.
 * @param decorativeHeightPosition The position of the decorative height. Use [DecorativeHeightPosition]
 * @param customXOffset Custom X offset for the axis. If null, the axis will be drawn at position
 * determined by [type].
 * @param labelHorizontalAlignment Horizontal alignment for the labels. Use [AxisLabelHorizontalAlignment]
 * @param labelCenterAlignment Alignment of the pivot point for the rotation of the labels. Use [AxisLabelCenterAlignment]
 * @param labelRotation Rotation of the labels in degrees around [labelCenterAlignment]
 * @param labelHorizontalGap Horizontal gap between labels and grid lines
 * @param fixedUnitSize Fixed unit size for the axis. If null, the unit size will be calculated
 * automatically using the container size and the axis steps data.
 * @param verticalScroll Vertical scroll amount for the axis. The default value is 0. [fixedUnitSize]
 * can't be 0 if [verticalScroll] is different from 0.
 * @param zoom Zoom factor for the axis. The default value is 1.
 * @param labelsBackgroundColor Background color for the labels. The default color is transparent
 *
 * @sample com.kubit.charts.samples.components.axis.VerticalAxisPreviewStart
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun VerticalAxisChart(
    data: AxisData,
    labelWidth: Dp,
    modifier: Modifier = Modifier,
    type: VerticalAxisType = VerticalAxisType.Start,
    padding: AxisPadding = AxisPadding(),
    decorativeHeight: Dp = 0.dp,
    decorativeHeightPosition: DecorativeHeightPosition = DecorativeHeightPosition.Top,
    customXOffset: Dp? = null,
    labelHorizontalAlignment: AxisLabelHorizontalAlignment = AxisLabelHorizontalAlignment.Center,
    labelCenterAlignment: AxisLabelCenterAlignment = AxisLabelCenterAlignment.Center,
    labelRotation: Float = 0f,
    labelHorizontalGap: Dp = 0.dp,
    fixedUnitSize: Dp? = null,
    verticalScroll: Dp = 0.dp,
    zoom: Float = 1f,
    labelsBackgroundColor: Color = Color.Transparent,
) {
    BoxWithConstraints(modifier = modifier) {
        val height = constraints.maxHeight

        with(LocalDensity.current) {
            val state = rememberVerticalAxisDataState(
                data,
                height.toFloat(),
                padding.top.toPx(),
                padding.bottom.toPx(),
                decorativeHeight.toPx(),
                decorativeHeightPosition,
                fixedUnitSize?.toPx(),
                zoom
            )

            VerticalAxisChart(
                chartState = state,
                labelWidth = labelWidth,
                type = type,
                padding = padding,
                customXOffset = customXOffset,
                labelHorizontalAlignment = labelHorizontalAlignment,
                labelCenterAlignment = labelCenterAlignment,
                labelRotation = labelRotation,
                labelHorizontalGap = labelHorizontalGap,
                verticalScroll = verticalScroll,
                labelsBackgroundColor = labelsBackgroundColor,
            )
        }
    }
}

/**
 * Composable that draws a vertical axis chart using a hoisted state
 *
 * @param chartState [AxisChartState] to be used to draw the axis.
 * @param labelWidth The width of the labels in the axis.
 * @param modifier Optional [Modifier] for the composable.
 * @param type The type of the axis (Start by default). Use [VerticalAxisType]
 * @param padding The padding for the axis. Use [AxisPadding]
 * @param customXOffset Custom X offset for the axis. If null, the axis will be drawn at position
 * determined by [type].
 * @param labelHorizontalAlignment Horizontal alignment for the labels. Use [AxisLabelHorizontalAlignment]
 * @param labelCenterAlignment Alignment of the pivot point for the rotation of the labels. Use [AxisLabelCenterAlignment]
 * @param labelRotation Rotation of the labels in degrees around [labelCenterAlignment]
 * @param labelHorizontalGap Horizontal gap between labels and grid lines
 * @param verticalScroll Vertical scroll amount for the axis. The default value is 0.
 * @param labelsBackgroundColor Background color for the labels. The default color is transparent
 *
 * @sample com.kubit.charts.samples.components.axis.ComplexAxisPreview
 */
@Composable
fun VerticalAxisChart(
    chartState: AxisChartState,
    labelWidth: Dp,
    modifier: Modifier = Modifier,
    type: VerticalAxisType = VerticalAxisType.Start,
    padding: AxisPadding = AxisPadding(),
    customXOffset: Dp? = null,
    labelHorizontalAlignment: AxisLabelHorizontalAlignment = AxisLabelHorizontalAlignment.Center,
    labelCenterAlignment: AxisLabelCenterAlignment = AxisLabelCenterAlignment.Center,
    labelRotation: Float = 0f,
    labelHorizontalGap: Dp = 0.dp,
    verticalScroll: Dp = 0.dp,
    labelsBackgroundColor: Color = Color.Transparent,
) {
    val textMeasurer = rememberTextMeasurer(cacheSize = chartState.processedAxisData.axisSteps.size)

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(labelWidth)
                .background(labelsBackgroundColor)
                .align(
                    when (type) {
                        VerticalAxisType.Start -> Alignment.CenterStart
                        VerticalAxisType.End -> Alignment.CenterEnd
                    }
                )
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            drawVerticalAxisLabels(
                axisSteps = chartState.processedAxisData.axisSteps,
                textMeasurer = textMeasurer,
                type = type,
                labelWidth = labelWidth,
                padding = padding,
                axisCustomXOffset = customXOffset,
                labelHorizontalAlignment = labelHorizontalAlignment,
                labelCenterAlignment = labelCenterAlignment,
                labelRotation = labelRotation,
                labelHorizontalGap = labelHorizontalGap,
                verticalScroll = verticalScroll,
            )
            drawVerticalAxisGridLines(
                axisSteps = chartState.processedAxisData.axisSteps,
                type = type,
                labelWidth = labelWidth,
                padding = padding,
                axisCustomXOffset = customXOffset,
                verticalScroll = verticalScroll,
            )
        }
    }
}

/**
 * Remembers and manages the state of a vertical axis chart for performance optimization and state hoisting.
 *
 * This function creates a persistent [AxisChartState] that survives configuration changes and recomposition.
 * You typically don't need to use this function directly unless you have specific requirements like:
 * - Coordinating multiple axes that depend on each other
 * - Accessing processed axis information in the parent composable
 * - Implementing custom state management or complex interactions
 *
 * ## Performance Benefits
 * - State is cached and only recalculated when inputs change
 * - Expensive coordinate transformations are performed once
 * - Survives configuration changes through Compose's saveable state
 *
 * @param data The [AxisData] containing raw axis information (steps, labels, styles).
 * @param height The total height available for the axis in pixels.
 * @param topPaddingInPx The top padding in pixels, reducing the effective drawing area.
 * @param bottomPaddingInPx The bottom padding in pixels, reducing the effective drawing area.
 * @param decorativeHeightInPx Additional height for grid line extensions beyond the axis boundaries.
 * @param decorativeHeightPosition Where to apply the decorative height. Use [DecorativeHeightPosition].
 * @param fixedUnitSize Fixed spacing between axis steps in pixels. When null, spacing is calculated automatically based on available space.
 * @param zoom Scale factor applied to the entire axis. Values > 1.0 zoom in, < 1.0 zoom out.
 *
 * @return [AxisChartState] containing processed axis data with canvas coordinates ready for drawing.
 *
 * @see rememberHorizontalAxisDataState
 * @see AxisChartState
 */
@Composable
fun rememberVerticalAxisDataState(
    data: AxisData,
    height: Float,
    topPaddingInPx: Float,
    bottomPaddingInPx: Float,
    decorativeHeightInPx: Float,
    decorativeHeightPosition: DecorativeHeightPosition,
    fixedUnitSize: Float? = null,
    zoom: Float = 1f
): AxisChartState = rememberSaveable(
    data,
    topPaddingInPx,
    bottomPaddingInPx,
    decorativeHeightInPx,
    decorativeHeightPosition,
    zoom,
    saver = AxisChartState.getSaver()
) {
    AxisChartState(
        data.toCanvasCoordinatesVAxis(
            height,
            topPaddingInPx,
            bottomPaddingInPx,
            decorativeHeightInPx,
            decorativeHeightPosition,
            fixedUnitSize,
            zoom
        )
    )
}

/**
 * Remembers and manages the state of a horizontal axis chart for performance optimization and state hoisting.
 *
 * This function creates a persistent [AxisChartState] that survives configuration changes and recomposition.
 * You typically don't need to use this function directly unless you have specific requirements like:
 * - Coordinating multiple axes that depend on each other
 * - Accessing processed axis information in the parent composable
 * - Implementing custom state management or complex interactions
 *
 * ## Performance Benefits
 * - State is cached and only recalculated when inputs change
 * - Expensive coordinate transformations are performed once
 * - Survives configuration changes through Compose's saveable state
 *
 * @param data The [AxisData] containing raw axis information (steps, labels, styles).
 * @param width The total width available for the axis in pixels.
 * @param startPaddingInPx The start (left) padding in pixels, reducing the effective drawing area.
 * @param endPaddingInPx The end (right) padding in pixels, reducing the effective drawing area.
 * @param decorativeWidthInPx Additional width for grid line extensions beyond the axis boundaries.
 * @param decorativeWidthPosition Where to apply the decorative width. Use [DecorativeWidthPosition].
 * @param fixedUnitSize Fixed spacing between axis steps in pixels. When null, spacing is calculated automatically based on available space.
 * @param zoom Scale factor applied to the entire axis. Values > 1.0 zoom in, < 1.0 zoom out.
 *
 * @return [AxisChartState] containing processed axis data with canvas coordinates ready for drawing.
 *
 * @see rememberVerticalAxisDataState
 * @see AxisChartState
 */
@Composable
fun rememberHorizontalAxisDataState(
    data: AxisData,
    width: Float,
    startPaddingInPx: Float,
    endPaddingInPx: Float,
    decorativeWidthInPx: Float,
    decorativeWidthPosition: DecorativeWidthPosition,
    fixedUnitSize: Float? = null,
    zoom: Float = 1f
): AxisChartState = rememberSaveable(
    data,
    width,
    startPaddingInPx,
    endPaddingInPx,
    decorativeWidthInPx,
    decorativeWidthPosition,
    fixedUnitSize,
    zoom,
    saver = AxisChartState.getSaver()
) {
    AxisChartState(
        data.toCanvasCoordinatesHAxis(
            width,
            startPaddingInPx,
            endPaddingInPx,
            decorativeWidthInPx,
            decorativeWidthPosition,
            fixedUnitSize,
            zoom
        )
    )
}

/**
 * Represents the persistent state of an axis chart with processed data ready for rendering.
 *
 * This class encapsulates the processed axis data after coordinate transformations have been applied.
 * It's designed to be used with Compose's state management system and provides efficient access
 * to axis steps by their values.
 *
 * ## Key Features
 * - Contains pre-processed canvas coordinates for optimal rendering performance
 * - Integrates with Compose's saveable state mechanism to survive configuration changes
 * - Provides utility methods for finding specific axis steps
 * - Immutable data structure that's safe for concurrent access
 *
 * @param processedAxisData The [AxisData] containing axis steps with calculated canvas positions.
 *
 * @see rememberHorizontalAxisDataState
 * @see rememberVerticalAxisDataState
 */
@Stable
data class AxisChartState(
    val processedAxisData: AxisData
) {
    /**
     * Get the axis step by value, this is used to get the corresponding canvas position
     * of an axis step in pixels.
     *
     * @param value The value of the axis step you want to get its canvas position
     */
    fun getAxisStepByValue(value: Float): AxisStep? {
        return processedAxisData.axisSteps.firstOrNull { it.axisValue == value }
    }

    companion object {
        /**
         * The default [Saver] implementation for [AxisChartState].
         * Saves current [AxisData] value and recreates a [AxisChartState] with the saved value as
         * initial value.
         */
        fun getSaver(): Saver<AxisChartState, *> = Saver(
            save = { it.processedAxisData.axisSteps.toList() },
            restore = { AxisChartState(AxisData(it.toPersistentList())) }
        )
    }
}

/**
 * Defines the positioning options for horizontal axes within their container.
 *
 * This enum controls whether a horizontal axis appears at the top or bottom of its container.
 * The positioning affects both the visual placement and the direction of grid lines and labels.
 *
 * ## Usage Examples
 * ```kotlin
 * // Axis at the bottom (most common)
 * HorizontalAxisChart(type = HorizontalAxisType.Bottom)
 *
 * // Axis at the top
 * HorizontalAxisChart(type = HorizontalAxisType.Top)
 * ```
 */
@Stable
enum class HorizontalAxisType {
    /** Places the axis at the top of the container with labels above the grid line */
    Top,
    /** Places the axis at the bottom of the container with labels below the grid line */
    Bottom
}

/**
 * Defines the positioning options for vertical axes within their container.
 *
 * This enum controls whether a vertical axis appears at the start (left in LTR) or
 * end (right in LTR) of its container. The positioning affects both the visual placement
 * and the direction of grid lines and labels.
 *
 * ## Usage Examples
 * ```kotlin
 * // Axis at the start/left (most common)
 * VerticalAxisChart(type = VerticalAxisType.Start)
 *
 * // Axis at the end/right
 * VerticalAxisChart(type = VerticalAxisType.End)
 * ```
 */
@Stable
enum class VerticalAxisType {
    /** Places the axis at the start (left in LTR) of the container with labels to the left of the grid line */
    Start,
    /** Places the axis at the end (right in LTR) of the container with labels to the right of the grid line */
    End
}

/**
 * Defines horizontal alignment options for vertical axis labels.
 *
 * This enum controls how labels are horizontally positioned within their allocated space
 * on vertical axes. The alignment affects the visual relationship between the label text
 * and the axis grid lines.
 *
 * ## Visual Examples
 * ```
 * Start:   "Label"  |
 * Center:  "Label"  |
 * End:     "Label"  |
 * ```
 */
@Stable
enum class AxisLabelHorizontalAlignment {
    /** Aligns labels to the start (left in LTR) of their allocated space */
    Start,
    /** Aligns labels to the end (right in LTR) of their allocated space */
    End,
    /** Centers labels within their allocated space */
    Center
}

/**
 * Defines vertical alignment options for horizontal axis labels.
 *
 * This enum controls how labels are vertically positioned within their allocated space
 * on horizontal axes. The alignment affects the visual relationship between the label text
 * and the axis grid lines.
 *
 * ## Visual Examples
 * ```
 * Top:    "Label"
 *         -------
 *
 * Center: "Label"
 *         -------
 *
 * Bottom: -------
 *         "Label"
 * ```
 */
@Stable
enum class AxisLabelVerticalAlignment {
    /** Aligns labels to the bottom of their allocated space */
    Bottom,
    /** Aligns labels to the top of their allocated space */
    Top,
    /** Centers labels within their allocated space */
    Center
}

/**
 * Defines the pivot point for label rotation transformations.
 *
 * This enum determines around which point labels rotate when [labelRotation] is applied.
 * The choice of center alignment affects how rotated labels appear relative to their
 * original position and the axis grid lines.
 *
 * ## Rotation Behavior
 * - **Start**: Labels rotate around their starting edge
 * - **Center**: Labels rotate around their center point (most common)
 * - **End**: Labels rotate around their ending edge
 */
@Stable
enum class AxisLabelCenterAlignment {
    /** Rotates labels around their start (left in LTR) edge */
    Start,
    /** Rotates labels around their end (right in LTR) edge */
    End,
    /** Rotates labels around their center point */
    Center
}

/**
 * Default styling configurations for axis labels.
 *
 * This object provides pre-configured [TextStyle] instances that follow the Kubit design system
 * guidelines for axis labels. Use these defaults to ensure consistent typography across your charts.
 *
 * ## Usage Examples
 * ```kotlin
 * // Using the default style
 * AxisBuilder().setDefaultLabelStyle(AxisLabelStyleDefaults.default)
 *
 * // Customizing based on the default
 * val customStyle = AxisLabelStyleDefaults.default.copy(
 *     color = Color.Blue,
 *     fontSize = 14.sp
 * )
 * ```
 *
 * @see AxisStepStyleDefaults
 */
object AxisLabelStyleDefaults {
    /**
     * The default text style for axis labels following Kubit design system guidelines.
     *
     * Provides a neutral gray color with caption medium typography that ensures good
     * readability and accessibility in most chart contexts.
     */
    val default = TextStyle(
        color = Color.DarkGray,
        textAlign = TextAlign.Start,
        fontSize = 12.sp,
        fontWeight = FontWeight(400),
        lineHeight = 14.sp
    )
}

/**
 * Default styling configurations for axis grid lines and steps.
 *
 * This object provides pre-configured [AxisStepStyle] instances for common axis line patterns.
 * Use these defaults to maintain visual consistency and follow established design patterns.
 *
 * ## Usage Examples
 * ```kotlin
 * // Using solid lines for major grid lines
 * AxisBuilder().setDefaultStepStyle(AxisStepStyleDefaults.solid)
 *
 * // Using dashed lines for minor grid lines
 * AxisBuilder().addNode(1f, "Label", style = AxisStepStyleDefaults.dashed)
 * ```
 *
 * @see AxisLabelStyleDefaults
 */
object AxisStepStyleDefaults {
    /**
     * Solid line style for prominent axis steps and grid lines.
     *
     * Use this for major grid lines, axis baselines, or steps that need to stand out.
     * Features a 1dp black line with no transparency.
     */
    val solid = AxisStepStyle.solid(
        strokeWidth = 1.dp,
        strokeColor = Color.DarkGray
    )

    /**
     * Dashed line style for subtle axis steps and grid lines.
     *
     * Use this for minor grid lines or secondary axis steps that should be visible
     * but not dominant. Features a 1dp line with 25% transparency and 5dp dash pattern.
     */
    val dashed = AxisStepStyle.dashed(
        strokeWidth = 1.dp,
        strokeColor = Color.DarkGray,
        dashLength = 5.dp,
        gapLength = 5.dp,
        phase = 0.dp
    )
}
