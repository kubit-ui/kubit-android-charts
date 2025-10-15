package com.kubit.charts.storybook.ui.componentscontent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kubit.charts.samples.components.axis.HorizontalAxisPreviewBottom
import com.kubit.charts.samples.components.axis.HorizontalAxisPreviewTop
import com.kubit.charts.samples.components.axis.MultipleAxisPreviewQ1
import com.kubit.charts.samples.components.axis.MultipleAxisPreviewQ1NoLabels
import com.kubit.charts.samples.components.axis.MultipleAxisPreviewQ2
import com.kubit.charts.samples.components.axis.MultipleAxisPreviewQ3
import com.kubit.charts.samples.components.axis.MultipleAxisPreviewQ4
import com.kubit.charts.samples.components.axis.MultipleAxisPreviewWithAxisShade
import com.kubit.charts.samples.components.axis.MultipleAxisRotatedPreview
import com.kubit.charts.samples.components.axis.VerticalAxisPreviewEnd
import com.kubit.charts.samples.components.axis.VerticalAxisPreviewStart
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookPreview

@Suppress("LongMethod")
@Composable
internal fun AxisChartContent() {
    val sampleHeight = 300.dp
    val sampleHeightExtended = 450.dp

    Column {
        StorybookPreview(
            title = "Horizontal axis bottom",
            modifier = Modifier.height(sampleHeight)
        ) {
            HorizontalAxisPreviewBottom()
        }
        StorybookPreview(title = "Horizontal axis top", modifier = Modifier.height(sampleHeight)) {
            HorizontalAxisPreviewTop()
        }
        StorybookPreview(title = "Vertical axis start", modifier = Modifier.height(sampleHeight)) {
            VerticalAxisPreviewStart()
        }
        StorybookPreview(title = "Vertical axis end", modifier = Modifier.height(sampleHeight)) {
            VerticalAxisPreviewEnd()
        }
        StorybookPreview(
            title = "Horizontal and vertical axes Q1",
            modifier = Modifier.height(sampleHeightExtended)
        ) {
            MultipleAxisPreviewQ1()
        }
        StorybookPreview(
            title = "Horizontal and vertical axes Q2",
            modifier = Modifier.height(sampleHeightExtended)
        ) {
            MultipleAxisPreviewQ2()
        }
        StorybookPreview(
            title = "Horizontal and vertical axes Q3",
            modifier = Modifier.height(sampleHeightExtended)
        ) {
            MultipleAxisPreviewQ3()
        }
        StorybookPreview(
            title = "Horizontal and vertical axes Q4",
            modifier = Modifier.height(sampleHeightExtended)
        ) {
            MultipleAxisPreviewQ4()
        }
        StorybookPreview(
            title = "Horizontal and vertical axes Q1 No Labels",
            modifier = Modifier.height(sampleHeightExtended)
        ) {
            MultipleAxisPreviewQ1NoLabels()
        }
        StorybookPreview(
            title = "Horizontal and vertical axes slanted labels",
            modifier = Modifier.height(sampleHeightExtended)
        ) {
            MultipleAxisRotatedPreview()
        }
        StorybookPreview(
            title = "Horizontal and vertical axes with shade",
            modifier = Modifier.height(sampleHeightExtended)
        ) {
            MultipleAxisPreviewWithAxisShade()
        }
    }
}
