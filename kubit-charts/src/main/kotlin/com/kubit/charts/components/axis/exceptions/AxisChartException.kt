package com.kubit.charts.components.axis.exceptions

/**
 * Exceptions for [HorizontalAxisChart] and [VerticalAxisChart]
 */
internal sealed class AxisChartException(message: String) : Exception(message) {
    internal class AxisNotProcessedException :
        AxisChartException(
            "AxisStep.axisValueInPx is null. You must process the axis before drawing it with " +
                "AxisData.toCanvasCoordinatesVAxis or AxisData.toCanvasCoordinatesHAxis."
        )

    internal class AxisWithoutInternalDataException :
        AxisChartException(
            "AxisData.axisInternalData is null. You must process the axis before calling toCanvasPosition"
        )

    internal class AxisStepStyleNotRecognizedException :
        AxisChartException(
            "The axis step style is not recognized. Use AxisStepStyle.dashed or AxisStepStyle.solid."
        )

    internal class AxisScrollWithoutFixedStepException :
        AxisChartException(
            "If you want to use horizontalScroll, you need to provide a fixedUnitSize."
        )
}
