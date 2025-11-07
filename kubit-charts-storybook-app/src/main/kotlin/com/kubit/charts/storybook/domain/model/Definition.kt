package com.kubit.charts.storybook.domain.model

interface Item {
    val componentName: String
    val description: String
    val icon: Int
    val category: Category
}

interface Category {
    val displayName: String
}
