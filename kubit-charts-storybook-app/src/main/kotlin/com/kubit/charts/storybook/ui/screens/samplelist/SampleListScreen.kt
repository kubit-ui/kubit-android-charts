package com.kubit.charts.storybook.ui.screens.samplelist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kubit.charts.storybook.R
import com.kubit.charts.storybook.domain.model.Sample
import com.kubit.charts.storybook.domain.usecase.GetSamplesUseCase
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookListScreen
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookScreen

@Composable
fun SampleListScreen(
    onNavigationAction: (SampleListScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val components = GetSamplesUseCase()
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    StorybookScreen(
        modifier = modifier,
        title = stringResource(R.string.data_visualization),
        onNavigateBack = {
            onNavigationAction(SampleListScreenAction.NavigateBack)
        }
    ) { paddingValues ->
        StorybookListScreen(
            components = components,
            paddingValues = paddingValues,
            isVisible = isVisible,
            onClick = { component ->
                onNavigationAction(SampleListScreenAction.NavigateToSampleDetails(component as Sample))
            }
        )
    }
}
