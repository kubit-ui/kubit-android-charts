package com.kubit.charts.samples.components.utils

import androidx.compose.ui.geometry.Offset
import kotlin.random.Random

internal fun getLineChartData(listSize: Int, start: Int = 0, maxRange: Int): List<Offset> {
    val list = arrayListOf<Offset>()
    for (index in 0 until listSize) {
        list.add(
            Offset(
                index.toFloat(),
                (start until maxRange).random().toFloat()
            )
        )
    }
    return list
}

internal fun getLineChartData(
    fromX: Float,
    toX: Float,
    minY: Float,
    maxY: Float,
    steps: Int
): List<Offset> {
    val list =
        arrayListOf<Offset>()

    val stepX =
        (toX - fromX) / steps

    for (index in 0 until steps) {
        list.add(
            Offset(
                index * stepX,
                minY + (maxY - minY) * Random.nextFloat()
            )
        )
    }
    return list
}
