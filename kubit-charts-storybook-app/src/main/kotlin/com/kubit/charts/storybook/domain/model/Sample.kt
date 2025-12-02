package com.kubit.charts.storybook.domain.model

import com.kubit.charts.storybook.R

enum class Sample(
    override val componentName: String,
    override val description: String,
    override val icon: Int,
    override val category: SampleCategory
) : Item {
    Weather(
        componentName = "Weather App",
        description = "A weather application showcasing various charts types.",
        icon = R.drawable.weather_mix,
        category = SampleCategory.Screen
    ),
    Health(
        componentName = "Health Dashboard",
        description = "A health dashboard displaying fitness and health metrics.",
        icon = R.drawable.health_metrics,
        category = SampleCategory.Screen
    )
}

enum class SampleCategory(
    override val displayName: String
) : Category {
    Screen("UI Screen"),
    Chart("Real Chart"),
}
