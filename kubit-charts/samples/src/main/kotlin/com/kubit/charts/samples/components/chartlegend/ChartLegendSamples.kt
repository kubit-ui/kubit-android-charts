package com.kubit.charts.samples.components.chartlegend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Preview
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
            color = Color.Blue
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview
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
            color = Color.Blue
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview
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
            color = Color.Blue
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview
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
            color = Color.Blue
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview
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
            color = Color.Blue
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview
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

@Preview
@Composable
fun VerticalChartLegendNoValueSample() {
    VerticalChartLegend(
        contentDescription = "Vertical Chart Legend Sample",
        titleConfig = ChartLegendTitleConfig(
            title = "Title"
        ),
        valueConfig = null,
        colorConfig = ChartLegendColorConfig(
            color = Color.Blue
        ),
        spacingConfig = ChartLegendSpacingConfig()
    )
}

@Preview
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
                value = "1.200.000.000"
            ),
            colorConfig = ChartLegendColorConfig(
                color = Color.Blue
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
                color = Color.Yellow
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
                color = Color.Red
            ),
            spacingConfig = ChartLegendSpacingConfig()
        )
    }
}

@Preview
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
                color = Color.Blue,
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
                color = Color.Blue,
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
                color = Color.Blue,
                containerWidth = 10.dp,
                containerHeight = 3.dp
            ),
            spacingConfig = ChartLegendSpacingConfig()
        )
    }
}
