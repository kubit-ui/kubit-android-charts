package com.kubit.charts.storybook.ui.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.kubit.charts.storybook.domain.model.Component

data object ComponentDetailsDestination : StorybookDestination {
    private const val componentArg: String = "component"

    override val route: String = "componentdetails/{$componentArg}"

    val arguments = listOf(
        navArgument(componentArg) { type = NavType.StringType }
    )

    fun createNavRoute(component: Component) = "componentdetails/$component"

    fun NavBackStackEntry.componentNavArg(): String? = arguments?.getString(componentArg)
}
