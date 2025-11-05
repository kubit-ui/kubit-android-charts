package com.kubit.charts.storybook.ui.screens.componentlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kubit.charts.storybook.R
import com.kubit.charts.storybook.domain.model.Component
import com.kubit.charts.storybook.domain.usecase.GetComponentsUseCase
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookListScreen
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookScreen

@Composable
fun ComponentListScreen(
    onNavigationAction: (ComponentListScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val components = GetComponentsUseCase()
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    StorybookScreen(
        modifier = modifier,
        title = stringResource(R.string.data_visualization),
        onNavigateBack = {
            onNavigationAction(ComponentListScreenAction.NavigateBack)
        }
    ) { paddingValues ->
        StorybookListScreen(
            components = components,
            paddingValues = paddingValues,
            isVisible = isVisible,
            onClick = { component ->
                onNavigationAction(ComponentListScreenAction.NavigateToComponentDetails(component as Component))
            }
        )
    }
}
