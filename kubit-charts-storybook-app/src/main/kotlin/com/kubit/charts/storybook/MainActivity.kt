package com.kubit.charts.storybook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kubit.charts.storybook.theme.KubitTheme
import com.kubit.charts.storybook.domain.resources.StringResourcesProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize StringResourcesProvider with application context
        StringResourcesProvider.initialize(this)

        enableEdgeToEdge()
        setContent {
            KubitTheme {
                StorybookApp()
            }
        }
    }
}
