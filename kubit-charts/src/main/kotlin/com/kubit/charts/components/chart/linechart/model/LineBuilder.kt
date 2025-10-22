package com.kubit.charts.components.chart.linechart.model

import androidx.compose.ui.geometry.Offset

/**
 * Builder class for [Line]
 */
class LineBuilder {
    private val points = mutableListOf<Point>()
    private var lineStyle: LineStyle = LineStyle()
    private var shadowUnderLine: ShadowUnderLine? = null
    private var selectionHighlightPopUp: SelectionHighlightPopUp? = null
    private var selectionHighlightPoint: SelectionHighlightPoint? = null

    /**
     * Add a list of points to the line
     *
     * @param points The list of points to add to the line. It's an Offset list.
     * @param intersectionNode The function to get the intersection node for a given index. This is
     * used to manage how an intersection node is drawn (a circle, a rect, a painter, etc). If this
     * function returns null, nothing will be drawn.
     */
    fun addPoints(
        points: List<Offset>,
        intersectionNode: (index: Int) -> IntersectionNode? = { null }
    ): LineBuilder {
        this.points.addAll(points.mapIndexed { index, offset -> Point(offset.x, offset.y, intersectionNode(index)) })
        return this
    }

    /**
     * Add a point to the line
     *
     * @param x The x coordinate of the point
     * @param y The y coordinate of the point
     * @param intersectionNode The intersection node to be drawn on the point (a square, a circle,
     * a painter, etc). If null, the point won't be drawn.
     */
    fun addPoint(
        x: Float,
        y: Float,
        intersectionNode: IntersectionNode?
    ): LineBuilder {
        points.add(Point(x, y, intersectionNode))
        return this
    }

    /**
     * Add a point to the line
     *
     * @param point The point to add to the line
     */
    fun addPoint(
        point: Point
    ): LineBuilder {
        points.add(point)
        return this
    }

    /**
     * Add a points list to the line
     *
     * @param points The points to add to the line
     */
    fun addPoints(
        points: List<Point>
    ): LineBuilder {
        this.points.addAll(points)
        return this
    }

    /**
     * Set the [LineStyle] for the line
     */
    fun setLineStyle(lineStyle: LineStyle): LineBuilder {
        this.lineStyle = lineStyle
        return this
    }

    /**
     * Set the [ShadowUnderLine] for the line
     */
    fun setShadowUnderLine(shadowUnderLine: ShadowUnderLine): LineBuilder {
        this.shadowUnderLine = shadowUnderLine
        return this
    }

    /**
     * Set the [SelectionHighlightPopUp] for the line
     */
    fun setSelectionHighlightPopUp(selectionHighlightPopUp: SelectionHighlightPopUp): LineBuilder {
        this.selectionHighlightPopUp = selectionHighlightPopUp
        return this
    }

    /**
     * Set the [SelectionHighlightPoint] for the line
     */
    fun setSelectionHighlightPoint(selectionHighlightPoint: SelectionHighlightPoint): LineBuilder {
        this.selectionHighlightPoint = selectionHighlightPoint
        return this
    }

    /**
     * Build the [Line] with the provided data
     */
    fun build(): Line {
        return Line(
            dataPoints = points,
            lineStyle = lineStyle,
            shadowUnderLine = shadowUnderLine,
            selectionHighlightPopUp = selectionHighlightPopUp,
            selectionHighlightPoint = selectionHighlightPoint
        )
    }
}

/**
 * DSL function to create a [Line] using a [LineBuilder]
 *
 * @param builderAction The action to configure the [LineBuilder]
 */

inline fun lineBuilder(builderAction: LineBuilder.() -> Unit): Line {
    return LineBuilder().apply(builderAction).build()
}
