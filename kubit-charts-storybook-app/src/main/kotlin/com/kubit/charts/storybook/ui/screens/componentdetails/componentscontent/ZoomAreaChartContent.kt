package com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent

import androidx.compose.runtime.Composable
import com.kubit.charts.components.chart.zoomareachart.ZoomAreaThumbPosition
import com.kubit.charts.samples.components.zoomarea.ZoomAreaChartThumbSample
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookPreview

@Composable
internal fun ZoomAreaChartContent() {

    StorybookPreview(title = "ZoomAreaChart with LineChart") {
        ZoomAreaChartThumbSample(thumbPosition = ZoomAreaThumbPosition.Middle)
    }
    StorybookPreview(title = "ZoomAreaChart with LineChart Inside") {
        ZoomAreaChartThumbSample(thumbPosition = ZoomAreaThumbPosition.Inside)
    }
    StorybookPreview(title = "ZoomAreaChart with LineChart Outside") {
        ZoomAreaChartThumbSample(thumbPosition = ZoomAreaThumbPosition.Outside)
    }
    StorybookPreview(title = "ZoomAreaChart with LineChart Custom") {
        ZoomAreaChartThumbSample(thumbPosition = ZoomAreaThumbPosition.Custom)
    }
}
