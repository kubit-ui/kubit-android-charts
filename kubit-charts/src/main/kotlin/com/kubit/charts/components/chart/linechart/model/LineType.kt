package com.kubit.charts.components.chart.linechart.model

/**
 * Represents the type of line to be drawn in the chart.
 *
 * There are two types of lines: Straight and SmoothCurve.
 */
@Suppress("MagicNumber")
sealed class LineType {
    abstract val isDotted: Boolean
    abstract var intervals: FloatArray

    /**
     * Draws straight lines connecting each point directly.
     *
     * @param isDotted True if the line should be dotted.
     * @param intervals Dash pattern for the line.
     */
    data class Straight(
        override val isDotted: Boolean = false,
        override var intervals: FloatArray = floatArrayOf(30f, 10f)
    ) : LineType() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Straight

            if (isDotted != other.isDotted) return false
            if (!intervals.contentEquals(other.intervals)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = isDotted.hashCode()
            result = 31 * result + intervals.contentHashCode()
            return result
        }
    }

    /**
     * Draws smooth curves using cubic paths between points.
     *
     * @param isDotted True if the curve should be dotted.
     * @param intervals Dash pattern for the curve.
     */
    data class SmoothCurve(
        override val isDotted: Boolean = false,
        override var intervals: FloatArray = floatArrayOf(30f, 10f)
    ) : LineType() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SmoothCurve

            if (isDotted != other.isDotted) return false
            if (!intervals.contentEquals(other.intervals)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = isDotted.hashCode()
            result = 31 * result + intervals.contentHashCode()
            return result
        }
    }
}
