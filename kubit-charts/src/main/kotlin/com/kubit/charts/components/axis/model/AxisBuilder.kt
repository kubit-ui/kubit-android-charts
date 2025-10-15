package com.kubit.charts.components.axis.model

import androidx.compose.ui.text.TextStyle
import com.kubit.charts.components.axis.AxisLabelStyleDefaults
import com.kubit.charts.components.axis.AxisStepStyleDefaults
import kotlinx.collections.immutable.toPersistentList

/**
 * Helper class to make [AxisData] generation easier.
 *
 * Example:
 * ```kotlin
 * val axisBuilder = AxisBuilder()
 *     .setDefaultLabelStyle(myTextStyle)
 *     .setDefaultStepStyle(myStepStyle)
 *     .addNode(value = 0f, label = "Zero")
 *     .addNode(value = 1f, label = "One")
 * val axisData = axisBuilder.build()
 * ```
 */
class AxisBuilder {
    private val nodes = mutableListOf<AxisStep>()

    private var defaultTextStyle: TextStyle? = AxisLabelStyleDefaults.default
    private var defaultStepStyle: AxisStepStyle? = AxisStepStyleDefaults.solid

    /**
     * Set the default label style for all the axis steps
     *
     * @param textStyle The style of the label. If this method is not called, the default style will
     * be [AxisLabelStyleDefaults.default]
     */
    fun setDefaultLabelStyle(textStyle: TextStyle): AxisBuilder {
        defaultTextStyle = textStyle
        return this
    }

    /**
     * Set the default step style for all the axis steps
     *
     * @param style The style of the step. If this method is not called, the default style will
     * be [AxisStepStyleDefaults.solid]
     */
    fun setDefaultStepStyle(style: AxisStepStyle?): AxisBuilder {
        defaultStepStyle = style
        return this
    }

    /**
     * Add a new node to the axis.
     *
     * @param value The value of the node
     * @param label The label of the node. If null, the value will be used as the label
     * @param labelStyle The style of the label. If null, the default style will be used. See [setDefaultLabelStyle]
     * @param style The style of the step. If null, the default style will be used. See [setDefaultStepStyle]
     */
    fun addNode(
        value: Float,
        label: String? = null,
        labelStyle: TextStyle? = null,
        style: AxisStepStyle? = null
    ): AxisBuilder {
        nodes.add(
            AxisStep(
                axisValue = value,
                axisLabel = label,
                labelStyle = labelStyle ?: defaultTextStyle,
                stepStyle = style ?: defaultStepStyle
            )
        )
        return this
    }

    /**
     * Adds multiple nodes
     *
     * @param values node values
     * @param labels lambda that returns the label associated to a value
     * @param labelStyle lambda that returns the label style associated to a value
     * @param stepStyle lambda that returs the step style associated to a value     *
     */
    fun addNodes(
        values: List<Float>,
        labels: ((Float) -> String?)? = null,
        labelStyle: ((Float) -> TextStyle?)? = null,
        stepStyle: ((Float) -> AxisStepStyle?)? = null
    ): AxisBuilder {
        values.forEach {
            addNode(
                value = it,
                label = labels?.invoke(it),
                labelStyle = labelStyle?.invoke(it),
                style = stepStyle?.invoke(it)
            )
        }
        return this
    }

    /**
     * Adds multiple nodes evenly distributed between a range
     *
     * @param from start value
     * @param to end value
     * @param steps number of steps (including the start and end values)
     * @param labels lambda that returns the label associated to a value
     * @param labelStyle lambda that returns the label style associated to a value
     * @param stepStyle lambda that returs the step style associated to a value
     */

    fun addNodes(
        from: Float,
        to: Float,
        steps: Int,
        labels: ((step: Int, value: Float) -> String?)? = null,
        labelStyle: ((step: Int, value: Float) -> TextStyle?)? = null,
        stepStyle: ((step: Int, value: Float) -> AxisStepStyle?)? = null
    ): AxisBuilder {
        for (i in 0..steps) {
            val value = from + i * (to - from) / steps
            addNode(
                value = value,
                label = labels?.invoke(i, value),
                labelStyle = labelStyle?.invoke(i, value),
                style = stepStyle?.invoke(i, value)
            )
        }
        return this
    }

    /**
     * Build the axis data using the nodes added using [addNode] and the configuration established.
     */
    fun build(): AxisData = AxisData(axisSteps = nodes.toPersistentList())
}

/**
 * DSL function to create an [AxisData] using an [AxisBuilder].
 *
 * Example:
 * ```kotlin
 * val axisData = axisBuilder {
 *     setDefaultLabelStyle(myTextStyle)
 *     setDefaultStepStyle(myStepStyle)
 *     addNode(value = 0f, label = "Zero")
 *     addNode(value = 1f, label = "One")
 * }
 * ```
 */
inline fun axisBuilder(builderAction: AxisBuilder.() -> Unit): AxisData {
    return AxisBuilder().apply(builderAction).build()
}
