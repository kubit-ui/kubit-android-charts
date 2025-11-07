package com.kubit.charts.storybook.ui.screens.componentdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kubit.charts.storybook.domain.model.Component
import com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent.AxisAndLineChartContent
import com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent.AxisChartContent
import com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent.BarChartContent
import com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent.ChartLegendContent
import com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent.LineChartContent
import com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent.PieChartContent
import com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent.PlotChartContent
import com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent.ZoomAreaChartContent
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookScreen

@Composable
fun ComponentDetailsScreen(
    component: Component,
    onNavigationAction: (ComponentDetailsScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    StorybookScreen(
        modifier = modifier,
        title = component.componentName,
        onNavigateBack = {
            onNavigationAction(ComponentDetailsScreenAction.NavigateBack)
        }
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
