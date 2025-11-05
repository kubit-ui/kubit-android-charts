@file:Suppress("MagicNumber", "LongMethod")

package com.kubit.charts.storybook.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class KubitColor(

    val categoryBasic: Color = KubitPallete.blue700,
    val categoryBasicContainer: Color = KubitPallete.cyan50,
    val categoryBasicSecondary: Color = KubitPallete.blue600,

    val categoryAdvanced: Color = KubitPallete.red700,
    val categoryAdvancedContainer: Color = KubitPallete.red50,
    val categoryAdvancedSecondary: Color = KubitPallete.red600,

    val categoryUtility: Color = KubitPallete.green700,
    val categoryUtilityContainer: Color = KubitPallete.gray100,
    val categoryUtilitySecondary: Color = KubitPallete.green600,

    val categoryScreen: Color = KubitPallete.yellow500,
    val categoryScreenContainer: Color = KubitPallete.yellow50,
    val categoryScreenSecondary: Color = KubitPallete.yellow600,

    )

val LocalKubitColor = staticCompositionLocalOf { KubitColor() }
