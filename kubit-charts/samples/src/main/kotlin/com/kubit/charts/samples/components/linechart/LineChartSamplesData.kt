package com.kubit.charts.samples.components.linechart

import androidx.compose.ui.geometry.Offset
import com.kubit.charts.samples.components.utils.getLineChartData

val sampleLineChartData1 = listOf(
    Offset(-6f, -2f),
    Offset(-4f, -1f),
    Offset(-2f, 0f),
    Offset(0f, 2f),
    Offset(2f, 5f),
    Offset(4f, 7f),
    Offset(6f, 9f),
    Offset(8f, 10f),
    Offset(10f, 8f),
    Offset(12f, 6f),
    Offset(14f, 12f),
)

val btcPoints = getLineChartData(
    fromX = 0f,
    toX = 8f,
    minY = 104000f,
    maxY = 108500f,
    steps = 30
)

val raisingLineChartData1 = listOf(
    Offset(-6f, -2f),
    Offset(10f, 50f)
)

val raisingLineChartData2 = listOf(
    Offset(-6f, -2f),
    Offset(10f, 35f)
)

val raisingLineChartData3 = listOf(
    Offset(-6f, -2f),
    Offset(10f, 20f)
)

val raisingLinesData = arrayOf(
    raisingLineChartData1,
    raisingLineChartData2,
    raisingLineChartData3
)
