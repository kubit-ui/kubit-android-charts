package com.kubit.charts.storybook.ui.screens.componentdetails

/**
 * Navigation actions from ComponentDetailsScreen
 */
sealed interface ComponentDetailsScreenAction {
    data object NavigateBack : ComponentDetailsScreenAction
}
