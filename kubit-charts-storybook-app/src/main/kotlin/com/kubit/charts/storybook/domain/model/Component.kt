package com.kubit.charts.storybook.domain.model

import com.kubit.charts.storybook.R

enum class Component(
    override val componentName: String,
    override val description: String,
    override val icon: Int,
    override val category: ComponentCategory
) : Item {
    LineChart(
        componentName = "Line Chart",
        description = "Perfect for showing trends and changes over time",
        icon = R.drawable.show_chart,
        category = ComponentCategory.Basic
    ),
    PieChart(
        componentName = "Pie Chart",
        description = "Ideal for displaying proportions and percentages",
        icon = R.drawable.pie_chart,
        category = ComponentCategory.Basic
    ),
    AxisChart(
        componentName = "Axis Chart",
        description = "Customizable axes for advanced chart layouts",
        icon = R.drawable.line_axis,
        category = ComponentCategory.Utility
    ),
    AxisAndLineChart(
        componentName = "Axis & Line Chart",
        description = "Combined axis and line visualization",
        icon = R.drawable.timeline,
        category = ComponentCategory.Advanced
    ),
    ZoomAreaChart(
        componentName = "Zoom Area Chart",
        description = "Interactive charts with zoom and pan capabilities",
        icon = R.drawable.zoom_in,
        category = ComponentCategory.Utility
    ),
    BarChart(
        componentName = "Bar Chart",
        description = "Great for comparing different categories of data",
        icon = R.drawable.bar_chart,
        category = ComponentCategory.Basic
    ),
    ChartLegend(
        componentName = "Chart Legend",
        description = "Configurable legends for all chart types",
        icon = R.drawable.info,
        category = ComponentCategory.Utility
    ),
    PlotChart(
        componentName = "Plot Chart",
        description = "Advanced plotting for complex data visualization",
        icon = R.drawable.scatter_plot,
        category = ComponentCategory.Basic
    ),
}

enum class ComponentCategory(
    override val displayName: String
) : Category {
    Basic("Basic Charts"),
    Advanced("Advanced"),
    Utility("Utilities"),
}
