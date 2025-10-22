package com.kubit.charts.components.chart.linechart.maths

import androidx.compose.ui.geometry.Offset
import com.kubit.charts.components.chart.linechart.model.Point

/**
 * Fritsch-Carlson algorithm for computing control points of a cubic Bezier spline that
 * interpolates a given set of points while preserving monotonicity.
 *
 * Based on the paper:
 * Fritsch, F. N., & Carlson, R. E. (1980). Monotone piecewise cubic interpolation.
 * SIAM Journal on Numerical Analysis, 17(2), 238-246.
 *
 * @param points List of data points to interpolate.
 * @return Pair of lists containing the first and second control points for each segment.
 */
@Suppress("MagicNumber", "LongMethod", "CyclomaticComplexMethod")
internal fun getCubicPointsFristschCarlson(points: List<Point>): Pair<MutableList<Offset>, MutableList<Offset>> {
    val n = points.size
    if (n < 2) return Pair(mutableListOf(), mutableListOf())

    // Extract x,y as FloatArray for speed
    val x = FloatArray(n) { points[it].x }
    val y = FloatArray(n) { points[it].y }

    val m = FloatArray(n) // slopes at knots
    val h = FloatArray(n - 1) // intervals
    val d = FloatArray(n - 1) // secant slopes (delta)

    // Compute intervals and secant slopes safely
    for (i in 0 until n - 1) {
        val dx = x[i + 1] - x[i]
        h[i] = dx
        d[i] = if (dx != 0f) (y[i + 1] - y[i]) / dx else 0f
    }

    fun sameSign(a: Float, b: Float): Boolean = (a > 0f && b > 0f) || (a < 0f && b < 0f)

    // Endpoint slopes (non-centered, with simple monotonicity guards)
    if (n == 2) {
        // Only one segment: use its secant slope at both ends
        m[0] = d[0]
        m[1] = d[0]
    } else {
        // m0 based on d0, guard if neighboring secants disagree or are zero
        m[0] = when {
            d[0] == 0f -> 0f
            d[0] != 0f && !sameSign(d[0], d[1]) -> 0f
            else -> kotlin.math.sign(d[0]) * minOf(kotlin.math.abs(d[0]), 3f * kotlin.math.abs(d[0]))
        }
        // m_{n-1} based on d_{n-2}, guard if neighboring secants disagree or are zero
        m[n - 1] = when {
            d[n - 2] == 0f -> 0f
            (n - 3) >= 0 && !sameSign(d[n - 3], d[n - 2]) -> 0f
            else -> kotlin.math.sign(d[n - 2]) * minOf(kotlin.math.abs(d[n - 2]), 3f * kotlin.math.abs(d[n - 2]))
        }
    }

    // Interior slopes via Fritsch–Carlson (weighted harmonic mean) + simple clamping
    for (k in 1 until n - 1) {
        val dkMinus = d[k - 1]
        val dk = d[k]
        if (dkMinus == 0f || dk == 0f || !sameSign(dkMinus, dk)) {
            m[k] = 0f
        } else {
            val hkMinus = h[k - 1]
            val hk = h[k]
            // Weighted harmonic mean (Fritsch–Carlson)
            val w1 = 2f * hk + hkMinus
            val w2 = hk + 2f * hkMinus
            val denom = (w1 / dkMinus) + (w2 / dk)
            val mk = if (denom != 0f) (w1 + w2) / denom else 0f
            // Simple limiter to keep within the monotone region
            val sgn = kotlin.math.sign(dk)
            m[k] = sgn * minOf(
                kotlin.math.abs(mk),
                3f * kotlin.math.abs(dkMinus),
                3f * kotlin.math.abs(dk)
            )
        }
    }

    // Convert piecewise Hermite (x, y, m) to cubic Bezier control points
    val c1 = ArrayList<Offset>(n - 1)
    val c2 = ArrayList<Offset>(n - 1)
    for (k in 0 until n - 1) {
        val hk = h[k]
        val xk = x[k]
        val yk = y[k]
        val xk1 = x[k + 1]
        val yk1 = y[k + 1]

        val mk = m[k]
        val mk1 = m[k + 1]

        // Guard against zero/negative hk to avoid NaNs; if hk<=0, collapse control points to knots' thirds
        val oneThirdH = hk / 3f
        val c1x = xk + oneThirdH
        val c1y = yk + mk * oneThirdH
        val c2x = xk1 - oneThirdH
        val c2y = yk1 - mk1 * oneThirdH

        c1 += Offset(c1x, c1y)
        c2 += Offset(c2x, c2y)
    }

    return Pair(c1, c2)
}

/**
 * Calculates cubic control points for smooth curves between points in the line chart.
 * This algorithm creates basic cubic Bezier control points by averaging the x-coordinates.
 *
 * @param pointsData List of points on the line graph.
 * @return Pair of lists containing left and right control points for cubic curves.
 */
internal fun getCubicPointsBasic(pointsData: List<Point>): Pair<MutableList<Offset>, MutableList<Offset>> {
    val cubicPoints1 = mutableListOf<Offset>()
    val cubicPoints2 = mutableListOf<Offset>()

    for (i in 1 until pointsData.size) {
        cubicPoints1.add(
            Offset(
                (pointsData[i].x + pointsData[i - 1].x) / 2,
                pointsData[i - 1].y
            )
        )
        cubicPoints2.add(
            Offset(
                (pointsData[i].x + pointsData[i - 1].x) / 2,
                pointsData[i].y
            )
        )
    }
    return Pair(cubicPoints1, cubicPoints2)
}

/**
 * Helper enum class to use as parameter when selecting the cubic algorithm.
 */
enum class CubicAlgorithm {
    Basic,
    FritschCarlson
}
