package com.kubit.charts.storybook.ui.screens.sampledetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kubit.charts.storybook.domain.model.Sample
import com.kubit.charts.storybook.ui.screens.sampledetails.samplecontent.WeatherContent
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookScreen

@Composable
fun SampleDetailsScreen(
    sample: Sample,
    onNavigationAction: (SampleDetailsScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    StorybookScreen(
        modifier = modifier,
        title = sample.componentName,
        onNavigateBack = {
            onNavigationAction(SampleDetailsScreenAction.NavigateBack)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            ComponentContent(sample = sample)
        }
    }
}

@Composable
private fun ComponentContent(sample: Sample) {
    when (sample) {
        Sample.Weather -> {
            WeatherContent()
        }
    }
}
