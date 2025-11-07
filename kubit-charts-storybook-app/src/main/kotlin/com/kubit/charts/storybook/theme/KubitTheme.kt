package com.kubit.charts.storybook.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Basic theme for charts which includes the default chart colors system injected using a provider
 */
@Composable
fun KubitTheme(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = if (isDarkTheme) {
        darkColorScheme
    } else {
        lightColorScheme
    }

    val kubitColor = if (isDarkTheme) {
        darkKubitColor
    } else {
        lightKubitColor
    }

    CompositionLocalProvider(
        LocalKubitColor provides kubitColor
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

object KubitTheme {

    val material: MaterialTheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme

    val color: KubitColor
        @Composable
        @ReadOnlyComposable
        get() = LocalKubitColor.current
}
