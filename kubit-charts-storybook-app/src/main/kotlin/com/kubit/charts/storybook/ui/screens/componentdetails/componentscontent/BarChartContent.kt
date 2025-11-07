package com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kubit.charts.samples.components.barchart.BarChartGroupedWithAxis
import com.kubit.charts.samples.components.barchart.BarChartHistogramWithAxis
import com.kubit.charts.samples.components.barchart.BarChartShapeVariants
import com.kubit.charts.samples.components.barchart.BarChartTextVariants
import com.kubit.charts.samples.components.barchart.BarChartWithAxis
import com.kubit.charts.samples.components.barchart.BarChartWithLineChartInAxis
import com.kubit.charts.samples.components.barchart.GroupedHorizontalBarChart
import com.kubit.charts.samples.components.barchart.GroupedVerticalBarChart
import com.kubit.charts.samples.components.barchart.SingleHorizontalBarChartBottomLabel
import com.kubit.charts.samples.components.barchart.SingleStackedHorizontalBarChart
import com.kubit.charts.samples.components.barchart.SingleStackedVerticalBarChart
import com.kubit.charts.samples.components.barchart.SingleVerticalBarChartTopLabel
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookPreview

@Suppress("MultipleEmitters")
@Composable
internal fun BarChartContent() {
    StorybookPreview(
        title = "Histogram in Axis",
        modifier = Modifier.height(350.dp)
    ) {
        BarChartHistogramWithAxis()
    }
    StorybookPreview(
        title = "Grouped in Axis",
        modifier = Modifier.height(350.dp)
    ) {
        BarChartGroupedWithAxis()
    }
    StorybookPreview(
        title = "Barchart with LineChart in Axis",
        modifier = Modifier.height(350.dp)
    ) {
        BarChartWithLineChartInAxis()
    }
    StorybookPreview(title = "Barchart Single Horizontal", modifier = Modifier.height(140.dp)) {
        SingleHorizontalBarChartBottomLabel()
    }
    StorybookPreview(title = "Barchart Single Vertical", modifier = Modifier.height(250.dp)) {
        SingleVerticalBarChartTopLabel()
    }
    StorybookPreview(title = "Barchart Stacked Horizontal", modifier = Modifier.height(150.dp)) {
        SingleStackedHorizontalBarChart()
    }
    StorybookPreview(title = "Barchart Stacked Vertical", modifier = Modifier.height(250.dp)) {
        SingleStackedVerticalBarChart()
    }
    StorybookPreview(title = "Barchart Grouped Horizontal", modifier = Modifier.height(200.dp)) {
        GroupedHorizontalBarChart()
    }
    StorybookPreview(title = "Barchart Grouped Vertical", modifier = Modifier.height(250.dp)) {
        GroupedVerticalBarChart()
    }
    StorybookPreview(title = "Barchart shapes", modifier = Modifier.height(600.dp)) {
        BarChartShapeVariants()
    }
    StorybookPreview(title = "Barchart labels", modifier = Modifier.height(600.dp)) {
        BarChartTextVariants()
    }
    StorybookPreview(title = "Barchart in Axis", modifier = Modifier.height(600.dp)) {
        BarChartWithAxis()
    }
}
