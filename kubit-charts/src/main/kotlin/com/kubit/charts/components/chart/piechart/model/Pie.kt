package com.kubit.charts.components.chart.piechart.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Represents a pie chart with multiple sections.
 *
 * @property sections List of [PieSectionData] representing each section of the pie chart.
 */
@Immutable
data class Pie(
    val sections: List<PieSectionData>,
) {
    /**
     * Returns a new [Pie] instance with each section's value normalized to its percentage of the total.
     * The normalized value is stored in [PieSectionData.normalizedValue].
     *
     * @return A [Pie] with normalized section values.
     */
    fun normalizeData(): Pie {
        val totalValue = sections.sumOf { it.value.toDouble() }
        return this.copy(sections = sections.map {
            it.copy(normalizedValue = it.value / totalValue.toFloat())
        })
    }
}

/**
 * Represents a section of a pie chart.
 *
 * @property value The raw value of the section.
 * @property style The visual style of the section.
 * @property label Optional label text for the section.
 * @property labelAutoRotation If true, the label rotates automatically to align with the sector's center.
 * @property labelCustomRotation Custom rotation angle for the label (used if [labelAutoRotation] is false).
 * @property content Optional composable content to display inside the section.
 * @property normalizedValue The normalized value (percentage) of the section, set by [Pie.normalizeData].
 */
@Immutable
data class PieSectionData(
    val value: Float,
    val style: PieSectionStyle,
    val label: String? = null,
    val labelAutoRotation: Boolean = false,
    val labelCustomRotation: Float = 0f,
    val content: (@Composable () -> Unit)? = null,
    val normalizedValue: Float? = null,
)

/**
 * Defines the visual style for a pie chart section.
 *
 * @property sectorColor The color of the sector when not selected.
 * @property selectedSectorColor The color of the sector when selected.
 * @property labelColor The color of the label text (default is white).
 */
@Immutable
data class PieSectionStyle(
    val sectorColor: Color,
    val selectedSectorColor: Color,
    val labelColor: Color = Color.White
)
