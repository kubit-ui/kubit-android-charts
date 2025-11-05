@file:Suppress("MagicNumber", "LongMethod")

package com.kubit.charts.storybook.ui.storybookcomponents

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kubit.charts.storybook.domain.model.Category
import com.kubit.charts.storybook.domain.model.ComponentCategory
import com.kubit.charts.storybook.domain.model.ComponentCategory.Advanced
import com.kubit.charts.storybook.domain.model.ComponentCategory.Basic
import com.kubit.charts.storybook.domain.model.ComponentCategory.Utility
import com.kubit.charts.storybook.domain.model.Item
import com.kubit.charts.storybook.domain.model.SampleCategory
import com.kubit.charts.storybook.domain.model.SampleCategory.Chart
import com.kubit.charts.storybook.domain.model.SampleCategory.Screen
import com.kubit.charts.storybook.theme.KubitTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentMap

@Composable
fun StorybookListScreen(
    components: ImmutableList<Item>,
    paddingValues: PaddingValues,
    isVisible: Boolean,
    onClick: (Item) -> Unit
) {
    val animationProgress by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(800), label = ""
    )

    LazyColumn(
        contentPadding = PaddingValues(
            top = paddingValues.calculateTopPadding() + 16.dp,
            bottom = paddingValues.calculateBottomPadding() + 16.dp,
            start = 16.dp,
            end = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .alpha(animationProgress)
    ) {
        // Agrupar por categorías
        val groupedComponents = components
            .groupBy { it.category }
            .toSortedMap(compareBy { it.displayName })
            .toPersistentMap()

        groupedComponents.forEach { (category, categoryComponents) ->
            item {
                CategoryHeader(
                    category = category,
                    animationProgress = animationProgress
                )
            }

            itemsIndexed(categoryComponents) { index, component ->
                ModernComponentCard(
                    component = component,
                    onClick = { onClick(component) },
                    animationDelay = index * 0.1f,
                    animationProgress = animationProgress
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun CategoryHeader(
    category: Category,
    animationProgress: Float
) {
    val headerScale by animateFloatAsState(
        targetValue = if (animationProgress > 0.3f) 1f else 0.8f,
        animationSpec = tween(600), label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(headerScale)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(4.dp, 24.dp)
                .background(
                    color = getCategoryColor(category),
                    shape = RoundedCornerShape(2.dp)
                )
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = category.displayName,
            style = KubitTheme.material.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = KubitTheme.material.colorScheme.onSurface
        )
    }
}

@Composable
private fun ModernComponentCard(
    component: Item,
    onClick: () -> Unit,
    animationDelay: Float,
    animationProgress: Float
) {
    val cardScale by animateFloatAsState(
        targetValue = if (animationProgress > 0.5f) 1f else 0.8f,
        animationSpec = tween(600, delayMillis = (animationDelay * 1000).toInt()), label = ""
    )

    val gradientColors = getCategoryGradientColors(component.category)
    val colors = getCategoryColor(component.category)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = KubitTheme.material.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = gradientColors.map { it.copy(alpha = 0.08f) }
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon Section
                Card(
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colors.copy(alpha = 0.15f)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(component.icon),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = colors
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Content Section
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = component.componentName,
                        style = KubitTheme.material.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = KubitTheme.material.colorScheme.onSurface
                    )

                    Text(
                        text = component.description,
                        style = KubitTheme.material.typography.bodySmall.copy(
                            fontSize = 13.sp
                        ),
                        color = KubitTheme.material.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    // Category badge
                    Surface(
                        modifier = Modifier.padding(top = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = colors.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = component.category.displayName,
                            style = KubitTheme.material.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = colors,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }

                // Arrow Icon
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = null,
                    tint = colors.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }

            // Left accent border
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(4.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = gradientColors.map { it.copy(alpha = 0.4f) }
                        ),
                        shape = RoundedCornerShape(topEnd = 2.dp, bottomEnd = 2.dp)
                    )
            )
        }
    }
}

@Composable
private fun getCategoryColor(category: Category): Color {
    return when (category) {
        is ComponentCategory -> when (category) {
            Basic -> KubitTheme.color.categoryBasic
            Advanced -> KubitTheme.color.categoryAdvanced
            Utility -> KubitTheme.color.categoryUtility
        }

        is SampleCategory -> when (category) {
            Screen -> KubitTheme.color.categoryScreen
            Chart -> TODO()
        }

        else -> {
            return KubitTheme.material.colorScheme.primary
        }
    }
}

@Composable
private fun getCategoryGradientColors(category: Category): List<Color> {
    return when (category) {
        is ComponentCategory -> when (category) {
            Basic -> listOf(
                KubitTheme.color.categoryBasic,
                KubitTheme.color.categoryBasicSecondary
            )

            Advanced -> listOf(
                KubitTheme.color.categoryAdvanced,
                KubitTheme.color.categoryAdvancedSecondary
            )

            Utility -> listOf(
                KubitTheme.color.categoryUtility,
                KubitTheme.color.categoryUtilitySecondary
            )
        }

        is SampleCategory -> when (category) {
            Screen -> listOf(
                KubitTheme.color.categoryScreen,
                KubitTheme.color.categoryScreenSecondary
            )

            Chart -> TODO()
        }

        else -> {
            listOf(
                KubitTheme.material.colorScheme.primary,
                KubitTheme.material.colorScheme.primaryContainer
            )
        }
    }
}
