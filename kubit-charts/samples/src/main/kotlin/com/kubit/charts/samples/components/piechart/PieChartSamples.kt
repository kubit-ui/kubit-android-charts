package com.kubit.charts.samples.components.piechart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.chart.piechart.PieChart
import com.kubit.charts.components.chart.piechart.PieChartDirection
import com.kubit.charts.components.chart.piechart.PieChartHorizontalAlignment
import com.kubit.charts.components.chart.piechart.PieChartVerticalAlignment

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartBasicSample() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 0.dp,
        onPointSelect = { pieSection, offset -> },
        contentDescription = "This is a pie chart 360 degrees with no section spacing."
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartBasicWithLabelsSample() {
    PieChart(
        pie = PieChartTestDataTwo,
        sectionsSpacing = 0.dp,
        onPointSelect = { pieSection, offset -> },
        contentDescription = "This is a pie chart 360 degrees with no section spacing and labels,"
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartBasicWithLabelsAutoRotationSample() {
    PieChart(
        pie = PieChartTestDataTwoAutoRotate,
        sectionsSpacing = 0.dp,
        onPointSelect = { pieSection, offset -> },
        contentDescription = "This is a pie chart 360 degrees with no section spacing and rotated labels"
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartSemiCircleSample() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 0.dp,
        startAngle = 0f,
        endAngle = 180f,
        onPointSelect = { pieSection, offset -> },
        contentDescription = "This is a pie chart 180 degrees with no section spacing."
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartReverseBasicSample() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 0.dp,
        direction = PieChartDirection.Clockwise,
        onPointSelect = { pieSection, offset -> },
        contentDescription = "This is a reverse pie chart 360 degrees with no section spacing."
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartBasicSectionSpacingSample() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 5.dp,
        onPointSelect = { pieSection, offset -> },
        contentDescription = "This is a pie chart 360 degrees with section spacing."
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartBasicSectionSpacingWithSectionWidthSample() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 15.dp,
        sectionWidth = 80.dp,
        onPointSelect = { pieSection, offset -> },
        contentDescription = "This is a ring chart 360 degrees with section spacing."
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartSemiCircleWithSectionWidthSample(expandToFill: Boolean = false) {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 15.dp,
        sectionWidth = 80.dp,
        startAngle = 0f,
        endAngle = 180f,
        onPointSelect = { pieSection, offset -> },
        expandToFill = expandToFill,
        contentDescription = "This is a ring chart 180 degrees with section spacing."
    )
}

@Preview(widthDp = 400, heightDp = 500, showBackground = true)
@Composable
fun PieChartSemiCircleWithSectionWidthVerticalAlignmentSample() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 15.dp,
        sectionWidth = 80.dp,
        verticalAlignment = PieChartVerticalAlignment.Top,
        horizontalAlignment = PieChartHorizontalAlignment.Right,
        startAngle = 180f,
        endAngle = 360f,
        onPointSelect = { pieSection, offset -> }
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartLowSemiCircleWithSectionWidthSample() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 8.dp,
        sectionWidth = 80.dp,
        startAngle = 180f,
        endAngle = 360f,
        onPointSelect = { pieSection, offset -> }
    )
}

@Preview(widthDp = 600, heightDp = 300, showBackground = true)
@Composable
fun PieChartMiddleSemiCircleWithSectionWidthSample() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 15.dp,
        sectionWidth = 80.dp,
        horizontalAlignment = PieChartHorizontalAlignment.Center,
        startAngle = 45f,
        endAngle = 225f,
        expandToFill = true,
        onPointSelect = { pieSection, offset -> },
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartRightSemiCircleWithSectionWidthSample(adaptToContent: Boolean = false) {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 15.dp,
        sectionWidth = 80.dp,
        horizontalAlignment = PieChartHorizontalAlignment.Left,
        startAngle = 270f,
        endAngle = 450f,
        onPointSelect = { pieSection, offset -> },
        expandToFill = adaptToContent,
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartLeftSemiCircleWithSectionWidthSample() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 15.dp,
        sectionWidth = 80.dp,
        horizontalAlignment = PieChartHorizontalAlignment.Right,
        startAngle = 90f,
        endAngle = 270f,
        onPointSelect = { pieSection, offset -> }
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartMiddleSemiCircleWithSectionWidthSample2() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 15.dp,
        sectionWidth = 80.dp,
        startAngle = 120f,
        endAngle = 315f,
        onPointSelect = { pieSection, offset -> }
    )
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartSectionWidthWithContentSample() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 15.dp,
        sectionWidth = 80.dp,
        onPointSelect = { pieSection, offset -> }
    ) {
        Text("This is a pie chart!")
    }
}

@Preview(widthDp = 400, heightDp = 300, showBackground = true)
@Composable
fun PieChartSectionWidthWithContentNoAspectRatioSample() {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 15.dp,
        sectionWidth = 80.dp,
        onPointSelect = { pieSection, offset -> }
    ) {
        Text("This is a pie chart!")
    }
}

@Preview(widthDp = 400, heightDp = 200, showBackground = true)
@Composable
fun PieChartSemiCircleCustomRadiusWithSectionWidthSample(radius: Dp = 200.dp) {
    PieChart(
        pie = PieChartTestDataOne,
        sectionsSpacing = 15.dp,
        sectionWidth = 80.dp,
        startAngle = 0f,
        endAngle = 180f,
        expandToFill = true,
        onPointSelect = { pieSection, offset -> }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                "This is a pie chart!",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 48.dp)
            )
        }
    }
}
