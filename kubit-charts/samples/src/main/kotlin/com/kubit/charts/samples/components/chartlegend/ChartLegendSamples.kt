package com.kubit.charts.samples.components.chartlegend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.chartlegend.ChartLegendColorConfig
import com.kubit.charts.components.chartlegend.HorizontalChartLegend
import com.kubit.charts.components.chartlegend.ChartLegendSpacingConfig
import com.kubit.charts.components.chartlegend.ChartLegendTitleAlignment
import com.kubit.charts.components.chartlegend.ChartLegendTitleConfig
import com.kubit.charts.components.chartlegend.ChartLegendValueConfig
import com.kubit.charts.components.chartlegend.VerticalChartLegend
import com.kubit.charts.components.utils.StarShape
import com.kubit.charts.components.utils.TriangleShape
import com.kubit.charts.samples.components.utils.ChartsSampleColors

@Preview(showBackground = true)
@Composable
fun VerticalRightChartLegendSample() {
    VerticalChartLegend(
        contentDescription = "Vertical Chart Legend Sample",
        titleConfig = ChartLegendTitleConfig(
            title = "Title",
            alignment = ChartLegendTitleAlignment.End
        ),
        valueConfig = ChartLegendValueConfig(
            value = "1.200.000.000"
        ),
        colorConfig = ChartLegendColorConfig(
            color = ChartsSampleColors.NeonFuchsia
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview(showBackground = true)
@Composable
fun VerticalLeftChartLegendSample() {
    VerticalChartLegend(
        contentDescription = "Vertical Chart Legend Sample",
        titleConfig = ChartLegendTitleConfig(
            title = "Title",
            alignment = ChartLegendTitleAlignment.Start
        ),
        valueConfig = ChartLegendValueConfig(
            value = "1.200.000.000"
        ),
        colorConfig = ChartLegendColorConfig(
            color = ChartsSampleColors.NeonFuchsia
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview(showBackground = true)
@Composable
fun HorizontalLeftChartLegendSample() {
    HorizontalChartLegend(
        adaptWidthToContent = true,
        contentDescription = "Horizontal Chart Legend Sample",
        titleConfig = ChartLegendTitleConfig(
            title = "Title",
            alignment = ChartLegendTitleAlignment.Start
        ),
        valueConfig = ChartLegendValueConfig(
            value = "1.200.000.000"
        ),
        colorConfig = ChartLegendColorConfig(
            color = ChartsSampleColors.NeonFuchsia
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview(showBackground = true)
@Composable
fun HorizontalLeftChartLegendAdaptToChartSample() {
    HorizontalChartLegend(
        adaptWidthToContent = false,
        contentDescription = "Horizontal Chart Legend Sample",
        titleConfig = ChartLegendTitleConfig(
            title = "Title",
            alignment = ChartLegendTitleAlignment.Start
        ),
        valueConfig = ChartLegendValueConfig(
            value = "1.200.000.000"
        ),
        colorConfig = ChartLegendColorConfig(
            color = ChartsSampleColors.ElectricTeal
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview(showBackground = true)
@Composable
fun HorizontalRightChartLegendSample() {
    HorizontalChartLegend(
        adaptWidthToContent = true,
        contentDescription = "Horizontal Chart Legend Sample",
        titleConfig = ChartLegendTitleConfig(
            title = "Title",
            alignment = ChartLegendTitleAlignment.End
        ),
        valueConfig = ChartLegendValueConfig(
            value = "1.200.000.000"
        ),
        colorConfig = ChartLegendColorConfig(
            color = ChartsSampleColors.NeonFuchsia
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview(showBackground = true)
@Composable
fun VerticalChartLegendNoColorSample() {
    VerticalChartLegend(
        contentDescription = "Vertical Chart Legend Sample",
        titleConfig = ChartLegendTitleConfig(
            title = "Title"
        ),
        valueConfig = ChartLegendValueConfig(
            value = "1.200.000.000"
        ),
        colorConfig = null,
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview(showBackground = true)
@Composable
fun VerticalChartLegendNoValueSample() {
    VerticalChartLegend(
        contentDescription = "Vertical Chart Legend Sample",
        titleConfig = ChartLegendTitleConfig(
            title = "Title"
        ),
        valueConfig = null,
        colorConfig = ChartLegendColorConfig(
            color = ChartsSampleColors.NeonFuchsia
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview(showBackground = true)
@Composable
fun MultipleChartLegendSample() {
    Column {
        HorizontalChartLegend(
            adaptWidthToContent = true,
            contentDescription = "Horizontal Chart Legend Sample",
            titleConfig = ChartLegendTitleConfig(
                title = "Title",
                alignment = ChartLegendTitleAlignment.Start
            ),
            valueConfig = ChartLegendValueConfig(
                value = "1.200.000.000",
                fontColor = ChartsSampleColors.black
            ),
            colorConfig = ChartLegendColorConfig(
                color = ChartsSampleColors.ElectricTeal
            ),
            spacingConfig = ChartLegendSpacingConfig()
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalChartLegend(
            adaptWidthToContent = true,
            contentDescription = "Horizontal Chart Legend Sample",
            titleConfig = ChartLegendTitleConfig(
                title = "Title",
                alignment = ChartLegendTitleAlignment.Start
            ),
            valueConfig = ChartLegendValueConfig(
                value = "1.200.000.000"
            ),
            colorConfig = ChartLegendColorConfig(
                color = ChartsSampleColors.ibrantAmber
            ),
            spacingConfig = ChartLegendSpacingConfig()
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalChartLegend(
            adaptWidthToContent = true,
            contentDescription = "Horizontal Chart Legend Sample",
            titleConfig = ChartLegendTitleConfig(
                title = "Title",
                alignment = ChartLegendTitleAlignment.Start
            ),
            valueConfig = ChartLegendValueConfig(
                value = "1.200.000.000"
            ),
            colorConfig = ChartLegendColorConfig(
                color = ChartsSampleColors.NeonFuchsia
            ),
            spacingConfig = ChartLegendSpacingConfig()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MultipleShapeChartLegendSample() {
    Column {
        HorizontalChartLegend(
            adaptWidthToContent = true,
            contentDescription = "Horizontal Chart Legend Sample",
            titleConfig = ChartLegendTitleConfig(
                title = "Title",
                alignment = ChartLegendTitleAlignment.Start
            ),
            valueConfig = ChartLegendValueConfig(
                value = "1.200.000.000"
            ),
            colorConfig = ChartLegendColorConfig(
                color = ChartsSampleColors.NeonFuchsia,
                shape = StarShape(),
                containerWidth = 10.dp,
                containerHeight = 10.dp
            ),
            spacingConfig = ChartLegendSpacingConfig()
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalChartLegend(
            adaptWidthToContent = true,
            contentDescription = "Horizontal Chart Legend Sample",
            titleConfig = ChartLegendTitleConfig(
                title = "Title",
                alignment = ChartLegendTitleAlignment.Start
            ),
            valueConfig = ChartLegendValueConfig(
                value = "1.200.000.000"
            ),
            colorConfig = ChartLegendColorConfig(
                color = ChartsSampleColors.NeonFuchsia,
                shape = TriangleShape(centered = false),
                containerWidth = 10.dp,
                containerHeight = 10.dp
            ),
            spacingConfig = ChartLegendSpacingConfig()
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalChartLegend(
            adaptWidthToContent = true,
            contentDescription = "Horizontal Chart Legend Sample",
            titleConfig = ChartLegendTitleConfig(
                title = "Title",
                alignment = ChartLegendTitleAlignment.Start
            ),
            valueConfig = ChartLegendValueConfig(
                value = "1.200.000.000"
            ),
            colorConfig = ChartLegendColorConfig(
                color = ChartsSampleColors.NeonFuchsia,
                containerWidth = 10.dp,
                containerHeight = 3.dp
            ),
            spacingConfig = ChartLegendSpacingConfig()
        )
    }
}
