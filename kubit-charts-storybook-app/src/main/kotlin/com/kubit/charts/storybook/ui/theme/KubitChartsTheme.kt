package com.kubit.charts.storybook.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Basic theme for charts which includes the default chart colors system injected using a provider
 */
@Composable
fun KubitTheme(
    colorScheme: KubitColor = KubitColor(),
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalKubitColor provides colorScheme
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme(),
            typography = Typography,
            content = content
        )
    }
}

object KubitTheme {
    val color: KubitColor
        @Composable
        @ReadOnlyComposable
        get() = LocalKubitColor.current
}
