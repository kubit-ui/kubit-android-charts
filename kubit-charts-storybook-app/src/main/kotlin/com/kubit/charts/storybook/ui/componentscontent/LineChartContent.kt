package com.kubit.charts.storybook.ui.componentscontent

import androidx.compose.runtime.Composable
import com.kubit.charts.samples.components.linechart.LineChartDottedSingleSample
import com.kubit.charts.samples.components.linechart.LineChartGradientColorSample
import com.kubit.charts.samples.components.linechart.LineChartMultiColorSample
import com.kubit.charts.samples.components.linechart.LineChartMultiLinesAreaSample
import com.kubit.charts.samples.components.linechart.LineChartMultipleLinesSample
import com.kubit.charts.samples.components.linechart.LineChartWithComposableNodesSample
import com.kubit.charts.samples.components.linechart.LineChartWithImageNodesSample
import com.kubit.charts.samples.components.linechart.LineChartWithPointsSample
import com.kubit.charts.samples.components.linechart.LineChartWithShapeNodesSample
import com.kubit.charts.samples.components.linechart.LineChartWithoutPointsSample
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookPreview

@Composable
internal fun LineChartContent() {
    StorybookPreview(title = "Single Line With Points") {
        LineChartWithPointsSample()
    }
    StorybookPreview(title = "Single line with image nodes") {
        LineChartWithImageNodesSample()
    }
    StorybookPreview(title = "Single line with shape nodes") {
        LineChartWithShapeNodesSample()
    }
    StorybookPreview(title = "Single line with composable nodes") {
        LineChartWithComposableNodesSample()
    }
    StorybookPreview(title = "Single Line Without Points") {
        LineChartWithoutPointsSample()
    }
    StorybookPreview(title = "Single Line Dotted") {
        LineChartDottedSingleSample()
    }
    StorybookPreview(title = "Single Line Gradient") {
        LineChartGradientColorSample()
    }
    StorybookPreview(title = "Single Line Multiple Color") {
        LineChartMultiColorSample()
    }
    StorybookPreview(title = "Multiple lines") {
        LineChartMultipleLinesSample()
    }
    StorybookPreview(title = "Multiple lines area chart") {
        LineChartMultiLinesAreaSample()
    }
}
