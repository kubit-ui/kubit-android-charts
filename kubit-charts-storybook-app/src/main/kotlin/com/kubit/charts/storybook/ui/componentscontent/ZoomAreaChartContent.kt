package com.kubit.charts.storybook.ui.componentscontent

import androidx.compose.runtime.Composable
import com.kubit.charts.samples.components.zoomarea.ZoomAreaChartThumbSample
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookPreview

@Composable
internal fun ZoomAreaChartContent() {

    StorybookPreview(title = "ZoomAreaChart with LineChart") {
        ZoomAreaChartThumbSample()
    }
}
