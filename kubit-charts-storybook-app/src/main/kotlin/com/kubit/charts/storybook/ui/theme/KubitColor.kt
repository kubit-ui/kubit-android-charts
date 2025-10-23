package com.kubit.charts.storybook.ui.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Suppress("MagicNumber")
data class KubitColor(
    // Brand colors
    val colorBrandFont50: Color = Color(0xFFDF2B51),
    val colorBrandIcon50: Color = Color(0xFFDF2B51),
    val colorBrandBorder50: Color = Color(0xFFDF2B51),
    val colorBrandBg50: Color = Color(0xFFDF2B51),
    val colorBrandBorder100: Color = Color(0xFF050505),
    val colorBrandFont100: Color = Color(0xFF050505),

    // Secondary colors
    val colorSecondaryFont50: Color = Color(0xFF000000),
    val colorSecondaryFont100: Color = Color(0xFFDF2B51),
    val colorSecondaryFont150: Color = Color(0xFFFFC8D3),
    val colorSecondaryIcon50: Color = Color(0xFF000000),
    val colorSecondaryIcon100: Color = Color(0xFFDF2B51),
    val colorSecondaryIcon150: Color = Color(0xFFFFC8D3),
    val colorSecondaryBorder50: Color = Color(0xFF000000),
    val colorSecondaryBorder100: Color = Color(0xFFDF2B51),
    val colorSecondaryBorder150: Color = Color(0xFFFFC8D3),
    val colorSecondaryBg50: Color = Color(0xFF000000),
    val colorSecondaryBg100: Color = Color(0xFFDF2B51),
    val colorSecondaryBg150: Color = Color(0xFFFFC8D3),
    val colorSecondaryBg200: Color = Color(0xFFFFEFF2),

    // Neutral colors
    val colorNeutralFont50: Color = Color(0xFF1A1A1A),
    val colorNeutralFont100: Color = Color(0xFF4F4F4F),
    val colorNeutralFont150: Color = Color(0xFF767676),
    val colorNeutralFont200: Color = Color(0xFFD1D1D1),
    val colorNeutralFont250: Color = Color(0xFFFFFFFF),
    val colorNeutralIcon50: Color = Color(0xFF1A1A1A),
    val colorNeutralIcon100: Color = Color(0xFF4F4F4F),
    val colorNeutralIcon150: Color = Color(0xFF767676),
    val colorNeutralIcon200: Color = Color(0xFFD1D1D1),
    val colorNeutralIcon250: Color = Color(0xFFFFFFFF),
    val colorNeutralBorder50: Color = Color(0xFF1A1A1A),
    val colorNeutralBorder100: Color = Color(0xFF4F4F4F),
    val colorNeutralBorder150: Color = Color(0xFF767676),
    val colorNeutralBorder200: Color = Color(0xFFD1D1D1),
    val colorNeutralBorder250: Color = Color(0xFFFFFFFF),
    val colorNeutralBg50: Color = Color(0xFF1A1A1A),
    val colorNeutralBg100: Color = Color(0xFF4F4F4F),
    val colorNeutralBg150: Color = Color(0xFF767676),
    val colorNeutralBg200: Color = Color(0xFFF4F4F4),
    val colorNeutralBg250: Color = Color(0xFFFFFFFF),

    // Accent default colors
    val colorAccentDefaultFont50: Color = Color(0xFF000000),
    val colorAccentDefaultFont100: Color = Color(0xFFDF2B51),
    val colorAccentDefaultFont150: Color = Color(0xFFFFFFFF),
    val colorAccentDefaultIcon50: Color = Color(0xFF000000),
    val colorAccentDefaultIcon100: Color = Color(0xFFDF2B51),
    val colorAccentDefaultIcon150: Color = Color(0xFFFFFFFF),
    val colorAccentDefaultBorder50: Color = Color(0xFF000000),
    val colorAccentDefaultBorder100: Color = Color(0xFFDF2B51),
    val colorAccentDefaultBorder150: Color = Color(0xFFFFFFFF),
    val colorAccentDefaultBg50: Color = Color(0xFF000000),
    val colorAccentDefaultBg100: Color = Color(0xFFDF2B51),
    val colorAccentDefaultBg150: Color = Color(0xFFFFFFFF),

    // Accent hover colors
    val colorAccentHoverFont200: Color = Color(0xFF000000),
    val colorAccentHoverIcon150: Color = Color(0xFF696969),
    val colorAccentHoverIcon200: Color = Color(0xFF000000),
    val colorAccentHoverBg50: Color = Color(0xFFE44B66),
    val colorAccentHoverBg100: Color = Color(0xFFF6F6F6),
    val colorAccentHoverBg150: Color = Color(0xFF696969),

    // Accent pressed colors
    val colorAccentPressedFont50: Color = Color(0xFFA01D39),
    val colorAccentPressedFont100: Color = Color(0xFFF4F4F4),
    val colorAccentPressedFont150: Color = Color(0xFF4F4F4F),
    val colorAccentPressedFont200: Color = Color(0xFF000000),
    val colorAccentPressedFont250: Color = Color(0xFFFFFFFF),
    val colorAccentPressedIcon50: Color = Color(0xFFA01D39),
    val colorAccentPressedIcon100: Color = Color(0xFFF4F4F4),
    val colorAccentPressedIcon150: Color = Color(0xFF4F4F4F),
    val colorAccentPressedIcon200: Color = Color(0xFF000000),
    val colorAccentPressedIcon250: Color = Color(0xFFFFFFFF),
    val colorAccentPressedBg50: Color = Color(0xFFA01D39),
    val colorAccentPressedBg100: Color = Color(0xFFF4F4F4),
    val colorAccentPressedBg150: Color = Color(0xFF4F4F4F),
    val colorAccentPressedBg200: Color = Color(0xFF767676),
    val colorAccentPressedBorder50: Color = Color(0xFFA01D39),

    // Keyboard focus colors
    val colorAccentKeyboardFocusBorder50: Color = Color(0xFF2360C5),
    val colorAccentKeyboardFocusBorder100: Color = Color(0xFFFFFFFF),

    // Disabled colors
    val colorAccentDisabledFont50: Color = Color(0xFF444444),
    val colorAccentDisabledFont100: Color = Color(0xFFAAAAAA),
    val colorAccentDisabledFont150: Color = Color(0xFFE6E6E6),
    val colorAccentDisabledIcon50: Color = Color(0xFF444444),
    val colorAccentDisabledIcon100: Color = Color(0xFFAAAAAA),
    val colorAccentDisabledIcon150: Color = Color(0xFFE6E6E6),
    val colorAccentDisabledBorder50: Color = Color(0xFF444444),
    val colorAccentDisabledBorder100: Color = Color(0xFFAAAAAA),
    val colorAccentDisabledBorder150: Color = Color(0xFFE6E6E6),
    val colorAccentDisabledBg50: Color = Color(0xFF444444),
    val colorAccentDisabledBg100: Color = Color(0xFFAAAAAA),
    val colorAccentDisabledBg150: Color = Color(0xFFE6E6E6),

    // Feedback colors
    val colorFeedbackErrorFont50: Color = Color(0xFFCC0000),
    val colorFeedbackSuccessFont50: Color = Color(0xFF008035),
    val colorFeedbackInfoIcon50: Color = Color(0xFF00A4A4),
    val colorFeedbackWarningIcon50: Color = Color(0xFF856300),
    val colorFeedbackSuccessIcon50: Color = Color(0xFF5CA40A),
    val colorFeedbackSuccessIcon100: Color = Color(0xFF008035),
    val colorFeedbackErrorIcon50: Color = Color(0xFFFF003C),
    val colorFeedbackErrorIcon100: Color = Color(0xFFCC0000),
    val colorFeedbackInfoBorder50: Color = Color(0xFF00A4A4),
    val colorFeedbackInfoBorder100: Color = Color(0xFF23779A),
    val colorFeedbackWarningBorder50: Color = Color(0xFFFFC000),
    val colorFeedbackWarningBorder100: Color = Color(0xFF856300),
    val colorFeedbackSuccessBorder50: Color = Color(0xFF5CA40A),
    val colorFeedbackSuccessBorder100: Color = Color(0xFF008035),
    val colorFeedbackErrorBorder50: Color = Color(0xFFFF003C),
    val colorFeedbackErrorBorder100: Color = Color(0xFFCC0000),
    val colorFeedbackInfoBg50: Color = Color(0xFFE6F6F6),
    val colorFeedbackInfoBg100: Color = Color(0xFF00A4A4),
    val colorFeedbackInfoBg150: Color = Color(0xFF23779A),
    val colorFeedbackWarningBg50: Color = Color(0xFFFFF9E6),
    val colorFeedbackWarningBg100: Color = Color(0xFFFFC000),
    val colorFeedbackWarningBg150: Color = Color(0xFF856300),
    val colorFeedbackSuccessBg50: Color = Color(0xFFEFF7E7),
    val colorFeedbackSuccessBg100: Color = Color(0xFF5CA40A),
    val colorFeedbackSuccessBg150: Color = Color(0xFF008035),
    val colorFeedbackErrorBg50: Color = Color(0xFFFFE6EC),
    val colorFeedbackErrorBg100: Color = Color(0xFFFF003C),
    val colorFeedbackErrorBg150: Color = Color(0xFFCC0000),
)

internal val LocalKubitColor: ProvidableCompositionLocal<KubitColor> = staticCompositionLocalOf { KubitColor() }
