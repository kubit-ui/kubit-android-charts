package com.kubit.charts.samples.components.utils

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Immutable
object ChartsSamplesFonts {
    val captionMedium = KubitFont(
        fontSize = 12.sp,
        fontWeight = FontWeight(400),
        lineHeight = 16.sp
    )
}

data class KubitFont(
    val fontSize: TextUnit,
    val fontWeight: FontWeight,
    val lineHeight: TextUnit
)
