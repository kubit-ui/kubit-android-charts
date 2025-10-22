package com.kubit.charts.components.chartlegend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.annotations.ApiStatus.Experimental

/**
 * Displays a vertical chart legend with configurable title, value, color indicator, and spacing.
 *
 * ```kotlin
 * VerticalChartLegend(
 *   titleConfig = ChartLegendTitleConfig(title = "Legend Title"),
 *   valueConfig = ChartLegendValueConfig(value = "42"),
 *   colorConfig = ChartLegendColorConfig(color = Color.Red),
 *   spacingConfig = ChartLegendSpacingConfig(),
 *   contentDescription = "Legend for chart"
 * )
 * ```
 * @param titleConfig Configuration for the title (alignment, font size, color, style, text).
 * @param valueConfig Configuration for the value (font size, color, style, text).
 * @param colorConfig Configuration for the color indicator (shape, color, size).
 * @param spacingConfig Configuration for spacing between legend elements.
 * @param contentDescription Content description for accessibility.
 * @param modifier Modifier applied to the legend container.
 */
@Experimental
@Composable
fun VerticalChartLegend(
    titleConfig: ChartLegendTitleConfig,
    valueConfig: ChartLegendValueConfig?,
    colorConfig: ChartLegendColorConfig?,
    spacingConfig: ChartLegendSpacingConfig,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clearAndSetSemantics {
                this.contentDescription = contentDescription
            },
        horizontalAlignment = if (titleConfig.alignment == ChartLegendTitleAlignment.Start) Alignment.Start else Alignment.End
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            colorConfig?.let {
                ColorContainer(colorConfig = it)
                Spacer(modifier = Modifier.width(spacingConfig.colorSpacing))
            }

            Text(
                text = titleConfig.title,
                fontSize = titleConfig.fontSize,
                color = titleConfig.fontColor,
                fontStyle = titleConfig.fontStyle,
                overflow = TextOverflow.Clip,
                maxLines = Int.MAX_VALUE,
            )
        }

        valueConfig?.let {
            Spacer(modifier = Modifier.height(spacingConfig.valueRowHeightSpacing))
            Text(
                text = it.value,
                fontSize = valueConfig.fontSize,
                color = valueConfig.fontColor,
                fontStyle = valueConfig.fontStyle
            )
        }
    }
}

/**
 * Displays a horizontal chart legend with configurable title, value, color indicator, and spacing.
 *
 * @param titleConfig Configuration for the title (alignment, font size, color, style, text).
 * @param valueConfig Configuration for the value (font size, color, style, text).
 * @param colorConfig Configuration for the color indicator (shape, color, size).
 * @param spacingConfig Configuration for spacing between legend elements.
 * @param contentDescription Content description for accessibility.
 * @param modifier Modifier applied to the legend container.
 * @param adaptWidthToContent If true, adapts the legend width to its content.
 *
 * Example:
 * ```kotlin
 * HorizontalChartLegend(
 *   titleConfig = ChartLegendTitleConfig(title = "Legend Title", alignment = ChartLegendTitleAlignment.End),
 *   valueConfig = ChartLegendValueConfig(value = "42"),
 *   colorConfig = ChartLegendColorConfig(color = Color.Blue),
 *   spacingConfig = ChartLegendSpacingConfig(),
 *   contentDescription = "Legend for chart",
 *   adaptWidthToContent = true
 * )
 * ```
 */
@Composable
fun HorizontalChartLegend(
    titleConfig: ChartLegendTitleConfig,
    valueConfig: ChartLegendValueConfig?,
    colorConfig: ChartLegendColorConfig?,
    spacingConfig: ChartLegendSpacingConfig,
    contentDescription: String,
    modifier: Modifier = Modifier,
    adaptWidthToContent: Boolean = false,
) {
    var rowHeight by remember { mutableIntStateOf(0) }
    Row(
        modifier = modifier.horizontalLegendModifier(adaptWidthToContent, contentDescription)
    ) {
        if (titleConfig.alignment == ChartLegendTitleAlignment.Start) {
            RowWithColorAndTitle(
                rowHeight = rowHeight,
                colorConfig = colorConfig,
                spacingConfig = spacingConfig,
                titleConfig = titleConfig
            )

            takeIf { valueConfig != null }?.let {
                Spacer(modifier = Modifier.width(spacingConfig.horizontalValueTitleSpacing))

                valueConfig?.let { valueConfigItem ->
                    ValuesColumn(
                        valuesConfig = valueConfigItem,
                        adaptWidthToContent = adaptWidthToContent,
                        onSizeChange = { rowHeight = it }
                    )
                }
            }
        } else {
            Row(
                modifier = if (!adaptWidthToContent) Modifier.weight(Weight) else Modifier,
            ) {
                RowWithColorAndValue(
                    rowHeight = rowHeight,
                    colorConfig = colorConfig,
                    spacingConfig = spacingConfig,
                    valuesConfig = valueConfig,
                    onSizeChange = { rowHeight = it }
                )
            }

            Row(
                modifier = Modifier.rowHeight(rowHeight, LocalDensity.current),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = titleConfig.title,
                    fontSize = titleConfig.fontSize,
                    color = titleConfig.fontColor,
                    fontStyle = titleConfig.fontStyle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = MaxLine,
                )
            }
        }
    }
}

@Composable
private fun RowWithColorAndTitle(
    rowHeight: Int,
    colorConfig: ChartLegendColorConfig?,
    spacingConfig: ChartLegendSpacingConfig,
    titleConfig: ChartLegendTitleConfig
) {
    Row(
        modifier = Modifier.rowHeight(rowHeight, LocalDensity.current),
        verticalAlignment = Alignment.CenterVertically
    ) {
        colorConfig?.let {
            ColorContainer(colorConfig = it)
            Spacer(modifier = Modifier.width(spacingConfig.colorSpacing))
        }

        Text(
            text = titleConfig.title,
            fontSize = titleConfig.fontSize,
            color = titleConfig.fontColor,
            fontStyle = titleConfig.fontStyle,
            overflow = TextOverflow.Ellipsis,
            maxLines = MaxLine,
        )
    }
}

@Composable
private fun RowScope.ValuesColumn(
    valuesConfig: ChartLegendValueConfig,
    adaptWidthToContent: Boolean,
    onSizeChange: (Int) -> Unit
) {
    Column(
        if (!adaptWidthToContent) Modifier.weight(Weight) else Modifier,
        horizontalAlignment = Alignment.End
    ) {
        Value(
            valuesConfig = valuesConfig,
            modifier = Modifier,
            onSizeChange = onSizeChange
        )
    }
}

@Composable
private fun RowScope.RowWithColorAndValue(
    rowHeight: Int,
    colorConfig: ChartLegendColorConfig?,
    spacingConfig: ChartLegendSpacingConfig,
    valuesConfig: ChartLegendValueConfig?,
    onSizeChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.rowHeight(rowHeight, LocalDensity.current),
        verticalAlignment = Alignment.CenterVertically
    ) {
        colorConfig?.let {
            ColorContainer(colorConfig = it)
            Spacer(modifier = Modifier.width(spacingConfig.colorSpacing))
        }
    }

    takeIf { valuesConfig != null }?.let {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            valuesConfig?.let {
                Value(
                    valuesConfig = valuesConfig,
                    modifier = Modifier,
                    onSizeChange = onSizeChange
                )
            }
        }
        Spacer(modifier = Modifier.width(spacingConfig.horizontalValueTitleSpacing))
    }
}

@Composable
private fun Value(
    valuesConfig: ChartLegendValueConfig,
    modifier: Modifier = Modifier,
    onSizeChange: ((Int) -> Unit)? = null
) {
    Text(
        text = valuesConfig.value,
        fontSize = valuesConfig.fontSize,
        color = valuesConfig.fontColor,
        fontStyle = valuesConfig.fontStyle,
        modifier = modifier.onSizeChanged { size -> onSizeChange?.invoke(size.height) }
    )
}

@Composable
private fun ColorContainer(
    colorConfig: ChartLegendColorConfig
) {
    Box(
        Modifier
            .clip(colorConfig.shape)
            .size(width = colorConfig.containerWidth, height = colorConfig.containerHeight)
            .background(colorConfig.color)
    )
}

private fun Modifier.rowHeight(
    rowHeight: Int,
    density: Density
): Modifier {
    return this.then(Modifier.height(with(density) { rowHeight.toDp() }))
}

private fun Modifier.horizontalLegendModifier(
    adaptWidthToContent: Boolean,
    contentDescription: String
): Modifier {
    return if (adaptWidthToContent) {
        this.wrapContentWidth()
    } else {
        this.fillMaxWidth()
    }
        .clearAndSetSemantics {
            this.contentDescription = contentDescription
        }
}

/**
 * Defines the alignment options for the legend title.
 *
 * - Start: Aligns the title to the start of the legend.
 * - End: Aligns the title to the end of the legend.
 */
@Stable
enum class ChartLegendTitleAlignment {
    Start, End
}

/**
 * Configuration for the legend title.
 *
 * @property title The title text displayed in the legend.
 * @property alignment Alignment of the title (start or end).
 * @property fontSize Font size of the title text.
 * @property fontColor Color of the title text.
 * @property fontStyle Style of the title text (e.g., Normal, Italic).
 */
@Immutable
data class ChartLegendTitleConfig(
    val title: String,
    val alignment: ChartLegendTitleAlignment = ChartLegendTitleAlignment.Start,
    val fontSize: TextUnit = DefaultTitleFontSize,
    val fontColor: Color = DefaultTitleFontColor,
    val fontStyle: FontStyle? = DefaultTitleFontStyle
)

/**
 * Configuration for the legend value.
 *
 * @property value The value text displayed in the legend.
 * @property fontSize Font size of the value text.
 * @property fontColor Color of the value text.
 * @property fontStyle Style of the value text (e.g., Normal, Italic).
 */
@Immutable
data class ChartLegendValueConfig(
    val value: String,
    val fontSize: TextUnit = DefaultValueFontSize,
    val fontColor: Color = DefaultValueFontColor,
    val fontStyle: FontStyle? = DefaultValueFontStyle
)

/**
 * Configuration for the color indicator in the legend.
 *
 * @property color Color displayed in the indicator.
 * @property shape Shape of the indicator (default: rounded corner).
 * @property containerWidth Width of the indicator container.
 * @property containerHeight Height of the indicator container.
 */
@Immutable
data class ChartLegendColorConfig(
    val color: Color,
    val shape: Shape = RoundedCornerShape(DefaultColorRadius),
    val containerWidth: Dp = DefaultColorContainerWidth,
    val containerHeight: Dp = DefaultColorContainerHeight
)

/**
 * Configuration for spacing between elements in the legend.
 *
 * @property valueRowHeightSpacing Spacing between value rows.
 * @property horizontalValueTitleSpacing Horizontal spacing between value and title.
 * @property colorSpacing Spacing after the color indicator.
 */
@Immutable
data class ChartLegendSpacingConfig(
    val valueRowHeightSpacing: Dp = DefaultValueRowHeight,
    val horizontalValueTitleSpacing: Dp = DefaultHorizontalValueTitleSpacing,
    val colorSpacing: Dp = DefaultColorSpacing
)

private const val Weight = 1f
private const val MaxLine = 2
private val DefaultTitleFontSize = 16.sp
private val DefaultTitleFontColor = Color.Black
private val DefaultTitleFontStyle = FontStyle.Normal
private val DefaultValueFontSize = 14.sp
private val DefaultValueFontColor = Color.Black
private val DefaultValueFontStyle = FontStyle.Normal
private val DefaultColorRadius = 12.dp
private val DefaultColorContainerWidth = 16.dp
private val DefaultColorContainerHeight = 12.dp
private val DefaultColorSpacing = 8.dp
private val DefaultHorizontalValueTitleSpacing = 16.dp
private val DefaultValueRowHeight = 2.dp
