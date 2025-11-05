package com.kubit.charts.storybook.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val lightColorScheme = lightColorScheme(
    primary = KubitPallete.red700,
    onPrimary = KubitPallete.white,
    primaryContainer = KubitPallete.red100,
    onPrimaryContainer = KubitPallete.red800,

    secondary = KubitPallete.red600,
    onSecondary = KubitPallete.white,
    secondaryContainer = KubitPallete.red200,
    onSecondaryContainer = KubitPallete.gray900,

    tertiary = KubitPallete.cyan500,
    onTertiary = KubitPallete.white,
    tertiaryContainer = KubitPallete.cyan50,
    onTertiaryContainer = KubitPallete.blue600,

    error = KubitPallete.red500,
    onError = KubitPallete.white,
    errorContainer = KubitPallete.red50,
    onErrorContainer = KubitPallete.red900,

    surface = KubitPallete.white,
    onSurface = KubitPallete.gray900,
    surfaceVariant = KubitPallete.gray100,
    onSurfaceVariant = KubitPallete.gray700,

    surfaceContainer = KubitPallete.gray100,
    surfaceContainerHigh = KubitPallete.gray200,
    surfaceContainerHighest = KubitPallete.gray300,
    surfaceContainerLow = KubitPallete.gray50,
    surfaceContainerLowest = KubitPallete.white,

    background = KubitPallete.white,
    onBackground = KubitPallete.gray900,

    outline = KubitPallete.gray500,
    outlineVariant = KubitPallete.gray300,

    inverseSurface = KubitPallete.gray900,
    inverseOnSurface = KubitPallete.white,
    inversePrimary = KubitPallete.red200,

    surfaceTint = KubitPallete.red700,
    scrim = KubitPallete.black,
)

val darkColorScheme = darkColorScheme(
    primary = KubitPallete.red700,
    onPrimary = KubitPallete.gray900,
    primaryContainer = KubitPallete.red800,
    onPrimaryContainer = KubitPallete.red200,

    secondary = KubitPallete.red600,
    onSecondary = KubitPallete.gray900,
    secondaryContainer = KubitPallete.gray700,
    onSecondaryContainer = KubitPallete.red200,

    tertiary = KubitPallete.cyan500,
    onTertiary = KubitPallete.gray900,
    tertiaryContainer = KubitPallete.blue600,
    onTertiaryContainer = KubitPallete.cyan50,

    error = KubitPallete.red500,
    onError = KubitPallete.gray900,
    errorContainer = KubitPallete.red900,
    onErrorContainer = KubitPallete.red50,

    surface = KubitPallete.gray900,
    onSurface = KubitPallete.white,
    surfaceVariant = KubitPallete.gray700,
    onSurfaceVariant = KubitPallete.gray300,

    surfaceContainer = KubitPallete.gray700,
    surfaceContainerHigh = KubitPallete.gray600,
    surfaceContainerHighest = KubitPallete.gray500,
    surfaceContainerLow = KubitPallete.gray800,
    surfaceContainerLowest = KubitPallete.gray900,

    background = KubitPallete.gray900,
    onBackground = KubitPallete.white,

    outline = KubitPallete.gray500,
    outlineVariant = KubitPallete.gray700,

    inverseSurface = KubitPallete.white,
    inverseOnSurface = KubitPallete.gray900,
    inversePrimary = KubitPallete.red700,

    surfaceTint = KubitPallete.red200,
    scrim = KubitPallete.black,
)

val lightKubitColor = KubitColor()

// actualmente mismos colores que ligth
val darkKubitColor = KubitColor()
