package com.kubit.charts.storybook.domain.usecase

import com.kubit.charts.storybook.domain.model.Component

object GetComponentsUseCase {
    operator fun invoke(): List<Component> =
        enumValues<Component>().toList().sortedBy { it.name }
}
