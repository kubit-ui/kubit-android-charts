package com.kubit.charts.storybook.ui.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kubit.charts.storybook.R
import com.kubit.charts.storybook.domain.model.Component
import com.kubit.charts.storybook.domain.usecase.GetComponentsUseCase
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookListItem
import com.kubit.charts.storybook.ui.storybookcomponents.StorybookScreen

@Composable
fun MainScreen(
    onComponentClick: (Component) -> Unit,
    modifier: Modifier = Modifier
) {
    val components = GetComponentsUseCase()

    StorybookScreen(
        modifier = modifier,
        title = stringResource(R.string.main_title)
    ) { paddingValues ->

        LazyColumn(contentPadding = paddingValues) {
            items(components) {
                ListItem(
                    title = it.componentName,
                    onClick = { onComponentClick(it) }
                )
            }
        }
    }
}

@Composable
private fun ListItem(
    title: String,
    onClick: () -> Unit,
) {
    StorybookListItem(
        onClick = onClick,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text = title)
            }
        }
    )
}
