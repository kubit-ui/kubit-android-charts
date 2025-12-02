package com.kubit.charts.samples.components.piechart.health

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kubit.charts.components.chart.barchart.BarChart
import com.kubit.charts.components.chart.barchart.model.BarChartAlignment
import com.kubit.charts.components.chart.barchart.model.BarChartAppearance
import com.kubit.charts.components.chart.barchart.model.BarChartData
import com.kubit.charts.components.chart.barchart.model.BarChartOrientation
import com.kubit.charts.components.chart.barchart.model.BarChartSegmentData
import com.kubit.charts.components.chart.barchart.model.BarChartType
import com.kubit.charts.components.chart.piechart.PieChart
import com.kubit.charts.components.chart.piechart.model.Pie
import com.kubit.charts.components.chart.piechart.model.PieSectionData
import com.kubit.charts.components.chart.piechart.model.PieSectionStyle
import com.kubit.charts.components.chartlegend.ChartLegendColorConfig
import com.kubit.charts.components.chartlegend.ChartLegendSpacingConfig
import com.kubit.charts.components.chartlegend.ChartLegendTitleAlignment
import com.kubit.charts.components.chartlegend.ChartLegendTitleConfig
import com.kubit.charts.components.chartlegend.ChartLegendValueConfig
import com.kubit.charts.components.chartlegend.VerticalChartLegend
import com.kubit.charts.samples.components.utils.ChartsSampleColors

@Composable
fun HealthAppLayoutSample() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
    ) {
        HealthProgressChart()
        HealthStats()
        SleepQualitySection()
    }
}

@Composable
private fun HealthProgressChart() {
    val configuration = LocalConfiguration.current
    val isLandscape =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        val containerWidth = maxWidth

        val outerChartWidth = containerWidth * HealthAppConstants.OUTER_CHART_WIDTH_FACTOR
        val middleChartWidth = containerWidth * HealthAppConstants.MIDDLE_CHART_WIDTH_FACTOR
        val innerChartWidth = containerWidth * HealthAppConstants.INNER_CHART_WIDTH_FACTOR

        val outerRadius = outerChartWidth / 2
        val middleRadius = middleChartWidth / 2
        val innerRadius = innerChartWidth / 2

        val sectionWidth = containerWidth * HealthAppConstants.SECTION_WIDTH_FACTOR
        val chartHeight = outerRadius + sectionWidth

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartHeight),
            contentAlignment = Alignment.BottomCenter
        ) {
            SemiCircleChart(
                current = HealthData.CALORIES.current,
                goal = HealthData.CALORIES.goal,
                color = HealthData.CALORIES.color,
                sectionWidth = sectionWidth,
                radius = outerRadius
            )
            SemiCircleChart(
                current = HealthData.STEPS.current,
                goal = HealthData.STEPS.goal,
                color = HealthData.STEPS.color,
                sectionWidth = sectionWidth,
                radius = middleRadius
            )
            SemiCircleChart(
                current = HealthData.HOURS.current,
                goal = HealthData.HOURS.goal,
                color = HealthData.HOURS.color,
                sectionWidth = sectionWidth,
                radius = innerRadius
            )
        }
    }
}

@Composable
private fun SemiCircleChart(
    current: Int,
    goal: Int,
    color: Color,
    sectionWidth: Dp,
    radius: Dp
) {
    PieChart(
        radius = radius,
        sectionWidth = sectionWidth,
        startAngle = HealthAppConstants.SEMI_CIRCLE_START_ANGLE,
        endAngle = HealthAppConstants.SEMI_CIRCLE_END_ANGLE,
        pie = Pie(
            sections = listOf(
                PieSectionData(
                    value = (goal - current).toFloat().coerceAtLeast(0f),
                    style = PieSectionStyle(
                        sectorColor = color.copy(alpha = HealthAppConstants.INACTIVE_SECTION_ALPHA),
                        selectedSectorColor = color.copy(alpha = HealthAppConstants.INACTIVE_SECTION_ALPHA)
                    )
                ),
                PieSectionData(
                    value = current.toFloat(),
                    style = PieSectionStyle(
                        sectorColor = color,
                        selectedSectorColor = color
                    )
                )
            )
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(radius + sectionWidth)
    )
}

@Composable
private fun HealthStats() {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = HealthAppConstants.CARD_ELEVATION),
        shape = RoundedCornerShape(HealthAppConstants.CARD_CORNER_RADIUS),
        colors = CardDefaults.cardColors(containerColor = ChartsSampleColors.darkGray.copy(alpha = HealthAppConstants.CARD_BACKGROUND_ALPHA)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(HealthAppConstants.CARD_PADDING)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(HealthAppConstants.CARD_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = HealthAppConstants.TODAY_STATS_TITLE,
                style = MaterialTheme.typography.titleLarge,
                color = ChartsSampleColors.white,
                modifier = Modifier.padding(bottom = HealthAppConstants.TITLE_BOTTOM_PADDING)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(HealthAppConstants.CARD_PADDING),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                HealthData.ALL_STATS.forEach { stat ->
                    MiniStatCard(
                        title = stat.title,
                        current = stat.current,
                        goal = stat.goal,
                        color = stat.color
                    )
                }
            }
        }
    }
}

@Composable
private fun MiniStatCard(
    title: String,
    current: Int,
    goal: Int,
    color: Color
) {
    val stat = HealthData.ALL_STATS.find { it.title == title }
    val unit = stat?.unit ?: ""

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(HealthAppConstants.STAT_SPACING)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = ChartsSampleColors.white
        )

        PieChart(
            sectionWidth = HealthAppConstants.MINI_SECTION_WIDTH,
            pie = Pie(
                sections = listOf(
                    PieSectionData(
                        value = current.toFloat(),
                        style = PieSectionStyle(
                            sectorColor = color,
                            selectedSectorColor = color
                        )
                    ),
                    PieSectionData(
                        value = (goal - current).toFloat().coerceAtLeast(0f),
                        style = PieSectionStyle(
                            sectorColor = color.copy(alpha = HealthAppConstants.INACTIVE_SECTION_ALPHA),
                            selectedSectorColor = color.copy(alpha = HealthAppConstants.INACTIVE_SECTION_ALPHA)
                        )
                    )
                )
            ),
            modifier = Modifier
                .width(HealthAppConstants.MINI_CHART_SIZE)
                .height(HealthAppConstants.MINI_CHART_SIZE)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(HealthAppConstants.TEXT_SPACING)
        ) {
            Text(
                text = "$current",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Text(
                text = "/$goal $unit",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Suppress("LongMethod")
@Composable
private fun SleepQualitySection() {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = HealthAppConstants.CARD_ELEVATION),
        shape = RoundedCornerShape(HealthAppConstants.CARD_CORNER_RADIUS),
        colors = CardDefaults.cardColors(containerColor = ChartsSampleColors.darkGray.copy(alpha = HealthAppConstants.CARD_BACKGROUND_ALPHA)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(HealthAppConstants.CARD_PADDING)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(HealthAppConstants.CARD_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = HealthAppConstants.SLEEP_QUALITY_TITLE,
                style = MaterialTheme.typography.titleLarge,
                color = ChartsSampleColors.white,
                modifier = Modifier.padding(bottom = HealthAppConstants.TITLE_BOTTOM_PADDING)
            )

            val proportions = calculateStackedProportions(SleepData.ALL_SLEEP_PHASES)

            BarChart(
                data = BarChartData(
                    type = BarChartType.Stacked,
                    orientation = BarChartOrientation.Horizontal,
                    appearance = BarChartAppearance.Rounded,
                    segments = SleepData.ALL_SLEEP_PHASES.mapIndexed { index, phase ->
                        val (start, end) = proportions[index]
                        BarChartSegmentData(
                            minValue = start,
                            maxValue = end,
                            color = phase.color,
                            contentDescription = "${phase.title}: ${phase.hours} ${HealthAppConstants.HOURS_UNIT}"
                        )
                    },
                    barChartAlignment = BarChartAlignment.Start
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HealthAppConstants.BAR_CHART_HEIGHT)
                    .padding(horizontal = HealthAppConstants.BAR_CHART_HORIZONTAL_PADDING)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = HealthAppConstants.LEGEND_TOP_PADDING),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SleepData.ALL_SLEEP_PHASES.forEach { phase ->
                    VerticalChartLegend(
                        titleConfig = ChartLegendTitleConfig(
                            title = phase.title,
                            fontSize = HealthAppConstants.LEGEND_FONT_SIZE,
                            fontColor = ChartsSampleColors.white,
                            alignment = ChartLegendTitleAlignment.End
                        ),
                        valueConfig = ChartLegendValueConfig(
                            value = "${phase.hours} ${HealthAppConstants.HOURS_SUFFIX}",
                            fontSize = HealthAppConstants.LEGEND_FONT_SIZE,
                            fontColor = ChartsSampleColors.white
                        ),
                        colorConfig = ChartLegendColorConfig(
                            color = phase.color,
                            containerWidth = HealthAppConstants.LEGEND_ICON_SIZE,
                            containerHeight = HealthAppConstants.LEGEND_ICON_SIZE
                        ),
                        spacingConfig = ChartLegendSpacingConfig(
                            colorSpacing = HealthAppConstants.LEGEND_COLOR_SPACING
                        ),
                        contentDescription = "${phase.title} legend"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HealthAppLayoutSamplePreview() {
    HealthAppLayoutSample()
}

private fun calculateStackedProportions(phases: List<SleepPhase>): List<Pair<Double, Double>> {
    var accumulated = 0.0
    return phases.map { phase ->
        val start = accumulated
        accumulated += phase.hours
        start to accumulated
    }
}

private object HealthAppConstants {
    // Chart dimensions and factors
    const val OUTER_CHART_WIDTH_FACTOR = 0.8f
    const val MIDDLE_CHART_WIDTH_FACTOR = 0.6f
    const val INNER_CHART_WIDTH_FACTOR = 0.4f
    const val SECTION_WIDTH_FACTOR = 0.2f

    val MINI_CHART_SIZE = 80.dp
    val MINI_SECTION_WIDTH = 20.dp

    // Alpha values
    const val INACTIVE_SECTION_ALPHA = 0.3f
    const val CARD_BACKGROUND_ALPHA = 0.3f

    // Chart angles
    const val SEMI_CIRCLE_START_ANGLE = 0f
    const val SEMI_CIRCLE_END_ANGLE = 180f

    // Layout dimensions
    val CARD_ELEVATION = 8.dp
    val CARD_CORNER_RADIUS = 16.dp
    val CARD_PADDING = 16.dp
    val STAT_SPACING = 8.dp
    val TEXT_SPACING = 2.dp
    val TITLE_BOTTOM_PADDING = 16.dp
    val LEGEND_TOP_PADDING = 16.dp
    val BAR_CHART_HEIGHT = 40.dp
    val BAR_CHART_HORIZONTAL_PADDING = 16.dp
    val LEGEND_ICON_SIZE = 12.dp
    val LEGEND_COLOR_SPACING = 8.dp

    // Font sizes
    val LEGEND_FONT_SIZE = 12.sp

    // Text labels
    const val TODAY_STATS_TITLE = "Today's Stats"
    const val SLEEP_QUALITY_TITLE = "Sleep Quality"
    const val HOURS_SUFFIX = "hrs"
    const val HOURS_UNIT = "hours"
}

private data class HealthStat(
    val title: String,
    val current: Int,
    val goal: Int,
    val color: Color,
    val unit: String
)

private object HealthData {
    val CALORIES = HealthStat(
        title = "Calories",
        current = 800,
        goal = 1000,
        color = ChartsSampleColors.colorRed30,
        unit = "kcal"
    )

    val STEPS = HealthStat(
        title = "Steps",
        current = 5000,
        goal = 7000,
        color = ChartsSampleColors.colorOrange,
        unit = "steps"
    )

    val HOURS = HealthStat(
        title = "Standing",
        current = 4,
        goal = 12,
        color = ChartsSampleColors.colorGreen75,
        unit = "hours"
    )

    val ALL_STATS = listOf(CALORIES, STEPS, HOURS)
}

private data class SleepPhase(
    val title: String,
    val hours: Double,
    val color: Color
)

private object SleepData {
    val LIGHT = SleepPhase(
        title = "Light",
        hours = 2.5,
        color = ChartsSampleColors.SkyBlueColor
    )

    val PROFOUND = SleepPhase(
        title = "Profound",
        hours = 3.0,
        color = ChartsSampleColors.colorColdBlue
    )

    val REM = SleepPhase(
        title = "REM",
        hours = 1.5,
        color = ChartsSampleColors.colorBlue65
    )

    val AWAKE = SleepPhase(
        title = "Awake",
        hours = 1.0,
        color = ChartsSampleColors.colorOrange
    )

    val ALL_SLEEP_PHASES = listOf(LIGHT, PROFOUND, REM, AWAKE)
}
