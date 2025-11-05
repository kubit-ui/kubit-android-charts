package com.kubit.charts.storybook.ui.screens.samplelist

import com.kubit.charts.storybook.domain.model.Sample

/**
 * Navigation actions from ComponentListScreen
 */
sealed interface SampleListScreenAction {
    data class NavigateToSampleDetails(val sample: Sample) : SampleListScreenAction
    data object NavigateBack : SampleListScreenAction
}
