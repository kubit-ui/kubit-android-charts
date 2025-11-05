package com.kubit.charts.storybook.ui.screens.componentdetails.componentscontent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kubit.charts.samples.components.piechart.PieChartBasicSample
import com.kubit.charts.samples.components.piechart.PieChartBasicSectionSpacingSample
import com.kubit.charts.samples.components.piechart.PieChartBasicSectionSpacingWithSectionWidthSample
import com.kubit.charts.samples.components.piechart.PieChartBasicWithLabelsSample
import com.kubit.charts.samples.components.piechart.PieChartLowSemiCircleWithSectionWidthSample
import com.kubit.charts.samples.components.piechart.PieChartReverseBasicSample
import com.kubit.charts.samples.components.piechart.PieChartSectionWidthWithContentSample
import com.kubit.charts.samples.components.piechart.PieChartSemiCircleCustomRadiusWithSectionWidthSample
import com.kubit.charts.samples.components.piechart.PieChartSemiCircleWithSectionWidthSample
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookPreview

@Suppress("LongMethod")
@Composable
internal fun PieChartContent() {
    StorybookPreview(title = "Pie Chart 360º full width") {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(SectionHeight)) {
            PieChartBasicSample()
        }
    }
    StorybookPreview(title = "Pie Chart 360º full width with labels") {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(SectionHeight)) {
            PieChartBasicWithLabelsSample()
        }
    }
    StorybookPreview(title = "Pie Chart reverse 360º full width") {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(SectionHeight)) {
            PieChartReverseBasicSample()
        }
    }
    StorybookPreview(title = "Pie Chart 360º full width with section spacing") {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(SectionHeight)) {
            PieChartBasicSectionSpacingSample()
        }
    }
    StorybookPreview(title = "Pie Chart 360º small width with section spacing") {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(SectionHeight)) {
            PieChartBasicSectionSpacingWithSectionWidthSample()
        }
    }
    StorybookPreview(title = "Pie Chart 180 upper small width with section spacing") {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(SectionHeight)) {
            PieChartSemiCircleWithSectionWidthSample()
        }
    }
    StorybookPreview(title = "Pie Chart 180 lower small width with section spacing") {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(SectionHeight)) {
            PieChartLowSemiCircleWithSectionWidthSample()
        }
    }
    StorybookPreview(title = "Pie Chart 360º small width with section spacing and content") {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(SectionHeight)) {
            PieChartSectionWidthWithContentSample()
        }
    }
    StorybookPreview(title = "Pie Chart 180 lower small width with section spacing and content") {
        var width by remember { mutableFloatStateOf(0f) }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(SectionHeightSmall)
            .onSizeChanged { width = it.width.toFloat() }) {
            with(LocalDensity.current) {
                PieChartSemiCircleCustomRadiusWithSectionWidthSample(radius = (width / 2).toDp())
            }
        }
    }
    /*StorybookPreview(title = "Pie Chart 180 upper small width with section spacing adaptToContent = true") {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(SectionHeightSmall)) {
            PieChartSemiCircleWithSectionWidthSample(adaptToContent = true)
        }
    }
    StorybookPreview(title = "Pie Chart 180º right with section spacing adaptToContent = true") {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(SectionHeightSmall)) {
            PieChartRightSemiCircleWithSectionWidthSample(adaptToContent = true)
        }
    }*/
}

private val SectionHeight = 300.dp
private val SectionHeightSmall = 200.dp
