package com.kubit.charts.storybook.ui.screens.componentlist

import com.kubit.charts.storybook.domain.model.Component

/**
 * Navigation actions from ComponentListScreen
 */
sealed interface ComponentListScreenAction {
    data class NavigateToComponentDetails(val component: Component) : ComponentListScreenAction
    data object NavigateBack : ComponentListScreenAction
}
