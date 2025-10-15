package com.kubit.charts.storybook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
fun StorybookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    KubitChartsTheme(
        darkTheme = darkTheme
    ) {
        content()
    }
}
