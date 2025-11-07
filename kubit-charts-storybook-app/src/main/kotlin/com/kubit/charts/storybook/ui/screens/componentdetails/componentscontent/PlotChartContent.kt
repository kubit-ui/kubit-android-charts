package com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kubit.charts.samples.components.plotchart.PlotChartSample
import com.kubit.charts.samples.components.plotchart.PlotChartWithBackgroundSample
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookPreview

@Composable
internal fun PlotChartContent() {
    Column {
        StorybookPreview(title = "PlotChart in axis", modifier = Modifier.height(600.dp)) {
            PlotChartSample()
        }

        StorybookPreview(
            title = "PlotChart with map background in axis",
            modifier = Modifier.height(600.dp)
        ) {
            PlotChartWithBackgroundSample()
        }
    }
}
