package com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent

import androidx.compose.runtime.Composable
import com.kubit.charts.samples.components.chartlegend.HorizontalLeftChartLegendAdaptToChartSample
import com.kubit.charts.samples.components.chartlegend.HorizontalLeftChartLegendSample
import com.kubit.charts.samples.components.chartlegend.HorizontalRightChartLegendSample
import com.kubit.charts.samples.components.chartlegend.MultipleChartLegendSample
import com.kubit.charts.samples.components.chartlegend.MultipleShapeChartLegendSample
import com.kubit.charts.samples.components.chartlegend.VerticalChartLegendNoColorSample
import com.kubit.charts.samples.components.chartlegend.VerticalChartLegendNoValueSample
import com.kubit.charts.samples.components.chartlegend.VerticalLeftChartLegendSample
import com.kubit.charts.samples.components.chartlegend.VerticalRightChartLegendSample
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookPreview

@Composable
internal fun ChartLegendContent() {
    StorybookPreview(title = "Vertical - Left ") {
        VerticalLeftChartLegendSample()
    }
    StorybookPreview(title = "Vertical - Right ") {
        VerticalRightChartLegendSample()
    }
    StorybookPreview(title = "Horizontal - Left ") {
        HorizontalLeftChartLegendSample()
    }
    StorybookPreview(title = "Horizontal - Right ") {
        HorizontalRightChartLegendSample()
    }
    StorybookPreview(title = "No color") {
        VerticalChartLegendNoColorSample()
    }
    StorybookPreview(title = "No value") {
        VerticalChartLegendNoValueSample()
    }
    StorybookPreview(title = "Adapt to chart (parent)") {
        HorizontalLeftChartLegendAdaptToChartSample()
    }
    StorybookPreview(title = "Multiple chartLegend") {
        MultipleChartLegendSample()
    }
    StorybookPreview(title = "Multiple shape") {
        MultipleShapeChartLegendSample()
    }
}
