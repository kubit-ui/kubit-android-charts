package com.kubit.charts.storybook.ui.storybookcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun StorybookPreview(
    title: String?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = StorybookLightPreviewColor,
    content: @Composable () -> Unit
) {

    Column(
        modifier = modifier.padding(StorybookPreviewPadding)
    ) {
        if (!title.isNullOrBlank()) {
            Text(text = title)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(StorybookPreviewShape))
                .background(color = backgroundColor)
        ) {
            Box(modifier = Modifier.padding(StorybookPreviewInnerPadding)) {
                content()
            }
        }
    }
}

private val StorybookPreviewPadding: Dp = 16.dp
private val StorybookPreviewInnerPadding: Dp = 16.dp
private val StorybookPreviewShape: Dp = 12.dp
private val StorybookLightPreviewColor: Color = Color(0xFFF3F3F3)
