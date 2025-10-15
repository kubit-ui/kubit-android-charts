package com.kubit.charts.storybook

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kubit.charts.storybook.ui.componentdetails.ComponentDetailsScreen
import com.kubit.charts.storybook.ui.componentlist.ComponentListScreen
import com.kubit.charts.storybook.ui.main.MainScreen
import com.kubit.charts.storybook.ui.navigation.ComponentDetailsDestination
import com.kubit.charts.storybook.ui.navigation.ComponentDetailsDestination.componentNavArg
import com.kubit.charts.storybook.ui.navigation.ComponentListDestination
import com.kubit.charts.storybook.ui.navigation.MainDestination

@Composable
fun StorybookNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainDestination.route) {
        mainDestination(navController)
        componentsDestination(navController)
        componentDetailsDestination(navController)
    }
}

private fun NavGraphBuilder.mainDestination(navController: NavHostController) {
    composable(MainDestination.route) {
        MainScreen(
            onComponentClick = {
                navController.navigate(
                    ComponentDetailsDestination.createNavRoute(it)
                )
            },
        )
    }
}

private fun NavGraphBuilder.componentsDestination(navController: NavHostController) {
    composable(ComponentListDestination.route) {
        ComponentListScreen(
            onComponentClick = { component ->
                navController.navigate(
                    ComponentDetailsDestination.createNavRoute(component)
                )
            },
            onNavigateBack = { navController.navigateUp() }
        )
    }
}

private fun NavGraphBuilder.componentDetailsDestination(navController: NavHostController) {
    composable(
        route = ComponentDetailsDestination.route,
        arguments = ComponentDetailsDestination.arguments
    ) { backStackEntry ->
        val component = backStackEntry.componentNavArg()
        component?.let {
            ComponentDetailsScreen(
                component = enumValueOf(it),
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
