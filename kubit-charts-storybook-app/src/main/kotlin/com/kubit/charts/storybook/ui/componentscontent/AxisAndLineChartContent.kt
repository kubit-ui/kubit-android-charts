package com.kubit.charts.storybook.ui.componentscontent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kubit.charts.samples.components.scaffold.AxisAndLineChartSampleHorizontalTop
import com.kubit.charts.samples.components.scaffold.AxisAndLineChartSampleStandard
import com.kubit.charts.samples.components.scaffold.AxisAndLineChartSampleVerticalEnd
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookPreview

@Composable
internal fun AxisAndLineChartContent() {
    Column {
        StorybookPreview(title = "VAxis, HAxis and LineChart", modifier = Modifier.height(600.dp)) {
            AxisAndLineChartSampleStandard()
        }
        StorybookPreview(title = "VAxis.End, HAxis and LineChart", modifier = Modifier.height(600.dp)) {
            AxisAndLineChartSampleVerticalEnd()
        }
        StorybookPreview(title = "VAxis, HAxis.Top and LineChart", modifier = Modifier.height(600.dp)) {
            AxisAndLineChartSampleHorizontalTop()
        }
    }
}
