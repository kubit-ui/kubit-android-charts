package com.kubit.charts.components.chart.linechart.model

import androidx.compose.runtime.Immutable

/**
 * Represents the type of line to be drawn in the chart.
 *
 * There are two types of lines: Straight and SmoothCurve.
 */
@Suppress("MagicNumber")
sealed class LineType {
    abstract val dashed: Boolean
    abstract val intervals: FloatArray

    /**
     * Draws straight lines connecting each point directly.
     *
     * @param dashed True if the line should be dotted.
     * @param intervals Dash pattern for the line.
     */
    @Immutable
    data class Straight(
        override val dashed: Boolean = false,
        override val intervals: FloatArray = floatArrayOf(30f, 10f)
    ) : LineType() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Straight

            if (dashed != other.dashed) return false
            if (!intervals.contentEquals(other.intervals)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = dashed.hashCode()
            result = 31 * result + intervals.contentHashCode()
            return result
        }
    }

    /**
     * Draws smooth curves using cubic paths between points.
     *
     * @param dashed True if the curve should be dotted.
     * @param intervals Dash pattern for the curve.
     */
    @Immutable
    data class SmoothCurve(
        override val dashed: Boolean = false,
        override val intervals: FloatArray = floatArrayOf(30f, 10f)
    ) : LineType() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SmoothCurve

            if (dashed != other.dashed) return false
            if (!intervals.contentEquals(other.intervals)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = dashed.hashCode()
            result = 31 * result + intervals.contentHashCode()
            return result
        }
    }
}
