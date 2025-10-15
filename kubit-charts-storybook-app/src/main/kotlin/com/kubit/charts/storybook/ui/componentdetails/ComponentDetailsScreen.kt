package com.kubit.charts.storybook.ui.componentdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kubit.charts.storybook.domain.model.Component
import com.kubit.charts.storybook.ui.componentscontent.AxisAndLineChartContent
import com.kubit.charts.storybook.ui.componentscontent.AxisChartContent
import com.kubit.charts.storybook.ui.componentscontent.BarChartContent
import com.kubit.charts.storybook.ui.componentscontent.ChartLegendContent
import com.kubit.charts.storybook.ui.componentscontent.LineChartContent
import com.kubit.charts.storybook.ui.componentscontent.PieChartContent
import com.kubit.charts.storybook.ui.componentscontent.PlotChartContent
import com.kubit.charts.storybook.ui.componentscontent.ZoomAreaChartContent
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookScreen

@Composable
fun ComponentDetailsScreen(
    onNavigateBack: () -> Unit,
    component: Component,
    modifier: Modifier = Modifier,
) {
    StorybookScreen(
        modifier = modifier,
        title = component.componentName,
        onNavigateBack = onNavigateBack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            ComponentContent(okComponent = component)
        }
    }
}

@Composable
private fun ComponentContent(okComponent: Component) {
    when (okComponent) {
        Component.LineChart -> LineChartContent()
        Component.AxisAndLineChart -> AxisAndLineChartContent()
        Component.AxisChart -> AxisChartContent()
        Component.PieChart -> PieChartContent()
        Component.ZoomAreaChart -> ZoomAreaChartContent()
        Component.BarChart -> BarChartContent()
        Component.ChartLegend -> ChartLegendContent()
        Component.PlotChart -> PlotChartContent()
    }
}
