package com.kubit.charts.components.chart.linechart.model

import androidx.compose.foundation.Indication
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Represents a node that can be drawn at the intersection of a line in the chart.
 *
 * @property color The color to be applied to the node.
 * @property colorFilter Optional color filter for the node.
 * @property blendMode Blending algorithm for the node.
 * @property accessibility Accessibility information for the node.
 * @property focus Focus configuration for the node.
 * @property draw Custom draw logic for the node.
 */
open class IntersectionNode(
    val color: Color = Color.Black,
    val colorFilter: ColorFilter? = null,
    val blendMode: BlendMode = DrawScope.DefaultBlendMode,
    val accessibility: IntersectionNodeAccessibility? = null,
    val focus: IntersectionNodeFocus = IntersectionNodeFocus(),
    val draw: DrawScope.(Offset) -> Unit = {},
)

/**
 * Represents a circular intersection node in the chart.
 *
 * @param radius The radius of the circle.
 * @param alpha The opacity of the circle (0.0f = transparent, 1.0f = opaque).
 * @param style The drawing style (stroke or fill).
 * @param color The color of the circle.
 * @param colorFilter Optional color filter for the circle.
 * @param blendMode Blending algorithm for the circle.
 * @param accessibility Accessibility information for the node.
 * @param focus Focus configuration for the node.
 */
class IntersectionPoint(
    val radius: Dp = 6.dp,
    val alpha: Float = 1.0f,
    val style: DrawStyle = Fill,
    color: Color = Color.Black,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    accessibility: IntersectionNodeAccessibility? = null,
    focus: IntersectionNodeFocus = IntersectionNodeFocus(),
) : IntersectionNode(color, colorFilter, blendMode, accessibility, focus, { center ->
    drawCircle(
        color = color,
        radius = radius.toPx(),
        center = center,
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode,
    )
})

/**
 * Represents a circular intersection node with a line extending to the bottom of the chart.
 *
 * @param radius The radius of the circle.
 * @param alpha The opacity of the circle.
 * @param style The drawing style.
 * @param color The color of the circle.
 * @param colorFilter Optional color filter for the circle.
 * @param blendMode Blending algorithm for the circle.
 * @param accessibility Accessibility information for the node.
 * @param focus Focus configuration for the node.
 */
@Suppress("MagicNumber")
class IntersectionPointWithLine(
    val radius: Dp = 12.dp,
    val alpha: Float = 1.0f,
    val style: DrawStyle = Stroke(width = 4f),
    color: Color = Color.Black,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    accessibility: IntersectionNodeAccessibility? = null,
    focus: IntersectionNodeFocus = IntersectionNodeFocus(),
) : IntersectionNode(color, colorFilter, blendMode, accessibility, focus, { center ->
    drawCircle(
        color = color,
        radius = radius.toPx(),
        center = center,
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode
    )

    drawLine(
        color = color,
        start = center.copy(y = center.y + radius.toPx()),
        end = center.copy(y = size.height),
        4f,
        Stroke.DefaultCap,
        alpha = alpha,
        colorFilter = colorFilter,
        blendMode = blendMode
    )
})

/**
 * Represents a rectangle on the line graph
 *
 * @param size The size of the rect
 * @param alpha Opacity to be applied to the shape from 0.0f to 1.0f
 * @param style Whether or not the shape is stroked or filled in. Use [Fill] for filled shapes and [Stroke] for stroked shapes
 * @param color The color or fill to be applied to the shape
 * @param colorFilter ColorFilter to apply to the [color] when drawn into the destination
 * @param blendMode Blending algorithm to be applied to the brush
 * @param accessibility The accessibility information of the intersection node
 * @param focus The focus configuration of the intersection node
 */
class IntersectionRect(
    val size: Size,
    val alpha: Float = 1.0f,
    val style: DrawStyle = Fill,
    color: Color = Color.Black,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    accessibility: IntersectionNodeAccessibility? = null,
    focus: IntersectionNodeFocus = IntersectionNodeFocus(),

    ) : IntersectionNode(color, colorFilter, blendMode, accessibility, focus, { center ->
    val halfYSize = size.height / 2
    val halfXSize = size.width / 2
    drawRect(
        color = color,
        topLeft = center - Offset(halfXSize, halfYSize),
        size = size,
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode
    )
})

/**
 * Represents a shape on the line graph
 *
 * @param path The path to be drawn to represent the shape
 * @param alpha Opacity to be applied to the shape from 0.0f to 1.0f
 * @param style Whether or not the shape is stroked or filled in. Use [Fill] for filled shapes and [Stroke] for stroked shapes
 * @param color The color or fill to be applied to the shape
 * @param colorFilter ColorFilter to apply to the [color] when drawn into the destination
 * @param blendMode Blending algorithm to be applied to the brush
 * @param accessibility The accessibility information of the intersection node
 * @param focus The focus configuration of the intersection node
 */
class IntersectionShape(
    path: Path,
    val alpha: Float = 1.0f,
    val style: DrawStyle = Fill,
    color: Color = Color.Black,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    accessibility: IntersectionNodeAccessibility? = null,
    focus: IntersectionNodeFocus = IntersectionNodeFocus(),
) : IntersectionNode(color, colorFilter, blendMode, accessibility, focus, { center ->
    translate(
        center.x,
        center.y
    ) {
        drawPath(
            path = path,
            color = color,
            alpha = alpha,
            style = style,
            colorFilter = colorFilter,
            blendMode = blendMode
        )
    }
})

/**
 * Draws any painter as a node of the chart
 *
 * @param size The desired size of the painter
 * @param painter The painter to be drawn
 * @param offset The offset to be applied to the painter
 */
class IntersectionPainter(
    val size: Size,
    val painter: Painter,
    val offset: Offset? = null,
) : IntersectionNode(draw = { center ->
    val pivot = Offset(
        center.x - size.center.x - (offset?.x ?: 0f),
        center.y - size.center.y - (offset?.y ?: 0f)
    )

    with(painter) {
        withTransform({
            scale(
                size.width / intrinsicSize.width,
                size.height / intrinsicSize.height,
                pivot = pivot
            )
            translate(
                pivot.x,
                pivot.y
            )
        }) {
            draw(painter.intrinsicSize)
        }
    }
})

/**
 * Draws any composable as a node of the chart
 *
 * @param composable The composable to be drawn. This lambda receives two parameters of type [Modifier] and [Point]
 */
class IntersectionComposable(
    val composable: @Composable (Modifier, Point) -> Unit
) : IntersectionNode()

/**
 * Represents the accessibility information of the intersection node
 *
 * @property contentDescription The content description of the intersection node
 */
data class IntersectionNodeAccessibility(
    val contentDescription: String,
)

/**
 * Represents the focus indication of the intersection node
 *
 * @property focusIndication The indication to be applied to the intersection node when it is focused
 * @property focusShape The shape to be applied to the indication of the intersection node when it is focused
 */
data class IntersectionNodeFocus(
    val focusIndication: Indication = ripple(),
    val focusShape: Shape = RoundedCornerShape(24.dp),
)
