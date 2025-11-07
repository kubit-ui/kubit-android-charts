package com.kubit.charts.storybook.domain.usecase

import com.kubit.charts.storybook.domain.model.Sample

object GetSamplesUseCase {
    operator fun invoke(): List<Sample> =
        enumValues<Sample>().toList().sortedBy { it.name }
}
