package com.kubit.charts.samples.components.piechart

import androidx.compose.runtime.Composable
import com.kubit.charts.components.chart.piechart.model.Pie
import com.kubit.charts.components.chart.piechart.model.PieSectionData
import com.kubit.charts.components.chart.piechart.model.PieSectionStyle
import com.kubit.charts.samples.components.utils.ChartsSampleColors

val PieChartTestDataOne: Pie
    @Composable
    get() = Pie(
        sections = listOf(
            PieSectionData(
                value = 10f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorRed30,
                    selectedSectorColor = ChartsSampleColors.colorRed30.copy(alpha = 0.5f)
                ),
            ),
            PieSectionData(
                value = 5f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorRed50,
                    selectedSectorColor = ChartsSampleColors.colorRed50.copy(alpha = 0.5f)
                ),
            ),
            PieSectionData(
                value = 20f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorBlue50,
                    selectedSectorColor = ChartsSampleColors.colorBlue50.copy(alpha = 0.5f),
                ),
            ),
            PieSectionData(
                value = 45f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorBlue65,
                    selectedSectorColor = ChartsSampleColors.colorBlue65.copy(alpha = 0.5f)
                ),
            ),
            PieSectionData(
                value = 20f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorTurquoise60,
                    selectedSectorColor = ChartsSampleColors.colorTurquoise60.copy(alpha = 0.5f)
                ),
            ),
        )
    )

val PieChartTestDataTwo: Pie
    @Composable
    get() = Pie(
        sections = listOf(
            PieSectionData(
                value = 10f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorRed30,
                    selectedSectorColor = ChartsSampleColors.colorRed30.copy(alpha = 0.5f)
                ),
                label = "10%",
            ),
            PieSectionData(
                value = 5f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorRed50,
                    selectedSectorColor = ChartsSampleColors.colorRed50.copy(alpha = 0.5f)
                ),
                label = "5%",
            ),
            PieSectionData(
                value = 20f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorBlue50,
                    selectedSectorColor = ChartsSampleColors.colorBlue50.copy(alpha = 0.5f),
                ),
                label = "20%",
            ),
            PieSectionData(
                value = 45f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorBlue65,
                    selectedSectorColor = ChartsSampleColors.colorBlue65.copy(alpha = 0.5f)
                ),
                label = "45%",
            ),
            PieSectionData(
                value = 20f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorTurquoise60,
                    selectedSectorColor = ChartsSampleColors.colorTurquoise60.copy(alpha = 0.5f)
                ),
                label = "20%",
            ),
        )
    )

val PieChartTestDataTwoAutoRotate: Pie
    @Composable
    get() = Pie(
        sections = listOf(
            PieSectionData(
                value = 10f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorRed30,
                    selectedSectorColor = ChartsSampleColors.colorRed30.copy(alpha = 0.5f)
                ),
                label = "10%",
                labelAutoRotation = true
            ),
            PieSectionData(
                value = 5f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorRed50,
                    selectedSectorColor = ChartsSampleColors.colorRed50.copy(alpha = 0.5f)
                ),
                label = "5%",
                labelAutoRotation = true
            ),
            PieSectionData(
                value = 20f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorBlue50,
                    selectedSectorColor = ChartsSampleColors.colorBlue50.copy(alpha = 0.5f),
                ),
                label = "20%",
                labelAutoRotation = true
            ),
            PieSectionData(
                value = 45f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorBlue65,
                    selectedSectorColor = ChartsSampleColors.colorBlue65.copy(alpha = 0.5f)
                ),
                label = "45%",
                labelAutoRotation = true
            ),
            PieSectionData(
                value = 20f,
                style = PieSectionStyle(
                    sectorColor = ChartsSampleColors.colorTurquoise60,
                    selectedSectorColor = ChartsSampleColors.colorTurquoise60.copy(alpha = 0.5f)
                ),
                label = "20%",
                labelAutoRotation = true
            ),
        )
    )
